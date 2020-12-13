package fr.Airweb.news.database.news

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NewsDao {

    @Query("SELECT * from news_table ORDER BY news_id ASC")
    fun getCampaigns(): LiveData<List<News>>

    @Query("SELECT * from news_table WHERE news_id = :news_id")
    fun getCampaign(news_id: Int): LiveData<News>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(news: News)

    @Query("DELETE FROM news_table")
    suspend fun deleteAll()
}