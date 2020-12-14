package fr.Airweb.news.ui.newsDescription

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import fr.Airweb.news.database.AppDatabase
import fr.Airweb.news.database.DataRepository
import fr.Airweb.news.database.news.News

class NewsDescriptionViewModel(idNews: Int, application: Application) : AndroidViewModel(application) {

    private val repository: DataRepository = DataRepository().getInstance(AppDatabase.getDatabase(application))!!
    private var mIdNews = idNews

    fun getNews(): LiveData<News> {

        return repository.getNews(mIdNews)
    }
}