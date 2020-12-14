package fr.Airweb.news.database.news

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NewsDao {

    @Query("SELECT * from news_table ORDER BY news_id ASC")
    fun getListNews(): LiveData<List<News>>

    @Query("SELECT * from news_table WHERE news_id = :news_id")
    fun getNews(news_id: Int): LiveData<News>

    @Query("SELECT * from news_table WHERE news_type = :news_type")
    fun getListNewsByType(news_type: String): LiveData<List<News>>

    @Query("SELECT * from news_table WHERE news_type = :news_type ORDER BY :orderBy")
    fun getListNewsByTypeAndOrderBy(news_type: String, orderBy: String): LiveData<List<News>>

    @Query("SELECT * from news_table WHERE news_title LIKE :name AND news_type = :sortType")
    fun getListNewsByName(name: String, sortType: String): LiveData<List<News>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(news: News)

    @Query("DELETE FROM news_table")
    suspend fun deleteAll()
}