package com.hemanth.cricbuzz.ui.home

import android.net.Uri
import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipeline
import com.facebook.imagepipeline.request.ImageRequest
import com.facebook.imagepipeline.request.ImageRequestBuilder
import com.hemanth.cricbuzz.common.Constants
import com.hemanth.cricbuzz.common.NoConnectivityException
import com.hemanth.cricbuzz.data.model.NewsResponse
import com.hemanth.cricbuzz.data.repository.HomeRepository
import com.hemanth.cricbuzz.utils.Event
import com.hemanth.cricbuzz.utils.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 *<h1>HomeViewModel</h1>
 * @author : Hemanth
 * @since : 11 Nov 2020
 * @version : 1.0
 */
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {

    val loading = ObservableBoolean(true)
    val sortByViewVisible = ObservableBoolean(false)

    lateinit var newsList: ArrayList<NewsResponse.Article>

    private val _eventNewsArticle = MutableLiveData<Event<Pair<String, *>>>()
    val eventNewsArticle: LiveData<Event<Pair<String, *>>> = _eventNewsArticle

    private val _notifyListAdapter = MutableLiveData<Boolean>()
    val notifyListAdapter: LiveData<Boolean> = _notifyListAdapter

    /**
     * <h2>getNewsDetails</h2>
     * this is the method to get news response from Api
     */
    fun getNewsDetails() {
        setLoading(true)
        val disposableObserver =
            object : DisposableSingleObserver<Response<NewsResponse>>() {
                override fun onSuccess(value: Response<NewsResponse>) {
                    when (value.code()) {
                        200 -> {
                            saveResponse(value.body() as NewsResponse)
                            _eventNewsArticle.postValue(
                                Event(Pair(Constants.SUCCESS, value.body()))
                            )
                        }
                        else -> _eventNewsArticle.postValue(
                            Event(Pair(Constants.ERROR, value.code().toString()))
                        )
                    }
                    setLoading(false)
                }

                override fun onError(e: Throwable) {
                    setLoading(false)
                    if (e is NoConnectivityException)
                        _eventNewsArticle.postValue(Event(Pair(Constants.NO_INTERNET, e.message)))
                    else
                        _eventNewsArticle.postValue(Event(Pair(Constants.ERROR, e.message)))
                }
            }
        homeRepository.getNewsDetails().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(disposableObserver)

    }

    private fun saveResponse(newsResponse: NewsResponse?) {
        newsList.clear()
        newsResponse?.let {
            newsList.addAll(it.articles)
        }
        newsList.forEach {
            it.urlToImage?.let { imageUrl -> prefetchImage(imageUrl) }
            it.timeStamp = Utils.ISODateToMillis(it.publishedAt)
        }
    }

    /**
     *<h2>prefetchImage</h2>
     *this method is for loading images in cache
     */
    private fun prefetchImage(imageUrl: String) {
        val pipeline: ImagePipeline = Fresco.getImagePipeline()
        val mainUri = Uri.parse(imageUrl)
        val mainImageRequest: ImageRequest = ImageRequestBuilder
            .newBuilderWithSource(mainUri)
            .build()
        pipeline.prefetchToDiskCache(mainImageRequest, null)
    }

    fun onRefresh() {
        getNewsDetails()
        dismissSort()
    }

    fun setLoading(isLoading: Boolean) {
        loading.set(isLoading)
    }

    /**
     * <h2>sortList</h2>
     * this is the method for showing sorting option
     */
    fun sortList() {
        sortByViewVisible.set(true)
    }

    /**
     * <h2>sortByTitle</h2>
     * this is the method for sorting list by news Title
     */
    fun sortByTitle() {
        val sortedList = newsList.sortedBy { it.title }
        newsList.clear()
        newsList.addAll(sortedList)
        _notifyListAdapter.postValue(true)
        dismissSort()
    }

    /**
     * <h2>sortByTitle</h2>
     * this is the method for sorting list by published time
     */
    fun sortByTime() {
        val sortedList = newsList.sortedBy { it.timeStamp }
        newsList.clear()
        newsList.addAll(sortedList)
        _notifyListAdapter.postValue(true)
        dismissSort()
    }

    fun dismissSort() {
        sortByViewVisible.set(false)
    }


}