package fr.Airweb.news.database

import androidx.lifecycle.LiveData
import fr.Airweb.news.database.news.News

class DataRepository() {

    private lateinit var mDatabase: AppDatabase
    private var sInstance: DataRepository? = null

    constructor(database: AppDatabase) : this() {
        mDatabase = database
    }

    fun getInstance(database: AppDatabase?): DataRepository? {
        if (sInstance == null) {
            synchronized(DataRepository::class.java) {
                if (sInstance == null) {
                    sInstance = DataRepository(database!!)
                }
            }
        }
        return sInstance
    }

    //News

    fun getNews(idNews: Int): LiveData<News> {
        return mDatabase.newsDao().getNews(idNews)
    }

    fun getListNewsByTypeAndOrderBy(type: String, orderBy: String): LiveData<List<News>> {
        var order = "news_title"
        if (orderBy == "date") {
            order = "news_date"
        }
        return mDatabase.newsDao().getListNewsByTypeAndOrderBy(type, order)
    }

    suspend fun insertNews(campaign: News) {
        mDatabase.newsDao().insert(campaign)
    }

    suspend fun deleteAllNews() {
        mDatabase.newsDao().deleteAll()
    }
}