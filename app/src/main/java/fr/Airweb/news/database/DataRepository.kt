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

    fun getNews(): LiveData<List<News>> {
        return mDatabase.newsDao().getCampaigns()
    }

    fun getNews(idCampaign: Int): LiveData<News> {
        return mDatabase.newsDao().getCampaign(idCampaign)
    }

    suspend fun insertNews(campaign: News) {
        mDatabase.newsDao().insert(campaign)
    }

    suspend fun deleteAllNews() {
        mDatabase.newsDao().deleteAll()
    }
}