package com.hemanth.cricbuzz.ui.articleDetail

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import javax.inject.Inject

/**
 *<h1>ArticleDetailViewModel</h1>
 * @author : Hemanth
 * @since : 11 Nov 2020
 * @version : 1.0
 */
class ArticleDetailViewModel @Inject constructor() : ViewModel() {

    val loading = ObservableBoolean(true)
}