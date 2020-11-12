package com.hemanth.cricbuzz.ui.home

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.hemanth.cricbuzz.R
import com.hemanth.cricbuzz.common.Constants
import com.hemanth.cricbuzz.data.model.NewsResponse
import com.hemanth.cricbuzz.databinding.ActivityHomeBinding
import com.hemanth.cricbuzz.ui.articleDetail.ArticleDetailActivity
import com.hemanth.cricbuzz.ui.home.adapter.HomeNewsListAdapter
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


/**
 *<h1>HomeActivity</h1>
 *<p>this is activity for news feeds</p>
 * @author : Hemanth
 * @since : 11 Nov 2020
 * @version : 1.0
 */
class HomeActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var newsListAdapter: HomeNewsListAdapter

    @Inject
    lateinit var newsList: ArrayList<NewsResponse.Article>

    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
    }

    private lateinit var mBinding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        mBinding.viewModel = viewModel
        viewModel.newsList = newsList
        viewModel.getNewsDetails()
        init()
        setupObserver()
        setUpNotifyAdapterObserver()
    }

    private fun init() {
        mBinding.rvHomeNewsList.adapter = newsListAdapter
        newsListAdapter.onItemSelectedListener = {
            launchArticleDetail(it)
        }
    }

    private fun launchArticleDetail(it: NewsResponse.Article) {
        val intent = Intent(this, ArticleDetailActivity::class.java)
        intent.putExtra(Constants.ARTICLE_DETAILS, it)
        startActivity(intent)
    }

    /**
     * <h2>setupObserver</h2>
     * this method is for observing the response from api
     */
    private fun setupObserver() {
        viewModel.eventNewsArticle.observe(this, Observer {
            when (it.getContentIfNotPending()?.first) {
                Constants.SUCCESS -> notifyAdapter()
                Constants.ERROR -> showError(it.getContent().second.toString())
                Constants.NO_INTERNET -> showInternetError()
            }
        })
    }

    private fun showInternetError() {
        AlertDialog.Builder(this)
            .setMessage(getString(R.string.no_internet_error))
            .setPositiveButton(getString(R.string.retry)) { _, _ -> viewModel.getNewsDetails() }
            .setOnCancelListener { viewModel.getNewsDetails() }
            .show()
    }

    private fun showError(error: String) {
        val snackBar: Snackbar = Snackbar.make(mBinding.root, error, Snackbar.LENGTH_SHORT)
        snackBar.view.setBackgroundColor(
            ContextCompat.getColor(this, R.color.snackbar_error_background_color)
        )
        snackBar.show()
    }


    /**
     * <h2>notifyAdapter</h2>
     * this is method for notifying news list adapter
     */
    private fun notifyAdapter() {
        newsListAdapter.notifyDataSetChanged()
    }

    /**
     * <h2>setUpNotifyAdapterObserver</h2>
     * this is method for observing to notifying news list adapter
     */
    private fun setUpNotifyAdapterObserver() {
        viewModel.notifyListAdapter.observe(this, Observer {
            if (it) {
                notifyAdapter()
            }
        })
    }

    override fun onBackPressed() {
        exitConfirmation()
    }

    private fun exitConfirmation() {
        AlertDialog.Builder(this)
            .setMessage(getString(R.string.exit_msg))
            .setCancelable(false)
            .setPositiveButton(getString(R.string.yes)) { _, _ -> super.onBackPressed() }
            .setNegativeButton(getString(R.string.no), null)
            .show()
    }
}