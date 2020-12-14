package fr.Airweb.news.ui.newsDescription

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class NewsDescriptionViewModelFactory() : ViewModelProvider.Factory {

    private var mIdNews = 0
    private var mApplication: Application? = null

    constructor(idNews: Int, application: Application) : this() {

        mIdNews = idNews
        mApplication = application
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(NewsDescriptionViewModel::class.java)) return NewsDescriptionViewModel(mIdNews, mApplication!!) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}