package fr.Airweb.news.database.news

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news_table")
class News(@PrimaryKey @ColumnInfo(name = "news_id") val nid: Int,
               @ColumnInfo(name = "news_type") val type: String?,
               @ColumnInfo(name = "news_date") val date: String?,
               @ColumnInfo(name = "news_title") val title: String?,
               @ColumnInfo(name = "news_picture") val picture: String?,
               @ColumnInfo(name = "news_content") val content: String?,
               @ColumnInfo(name = "news_dateformated") val dateformated: String?)