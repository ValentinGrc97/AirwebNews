package fr.Airweb.news.ui.newsList

import android.app.Application
import android.content.SharedPreferences
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import fr.Airweb.news.database.AppDatabase
import fr.Airweb.news.database.DataRepository
import fr.Airweb.news.database.news.ListNews
import fr.Airweb.news.database.news.News
import fr.Airweb.news.interfaces.APIService
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: DataRepository = DataRepository().getInstance(AppDatabase.getDatabase(application))!!

    init {

        requestNews()
    }

    fun getListNewsByTypeAndOrderBy(type: String, orderBy: String): LiveData<List<News>> {

        return repository.getListNewsByTypeAndOrderBy(type, orderBy)
    }

    fun getListNewsByName(name: String, sortType: String): LiveData<List<News>> {

        return repository.getListNewsByName(name, sortType)
    }

    fun requestNews() {

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(APIService.BASE_URL + "/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        val apiServiceInterface = retrofit.create(APIService::class.java)
        val myCall = apiServiceInterface.news
        myCall.enqueue(object : Callback<ListNews> {
            override fun onResponse(call: Call<ListNews>, response: Response<ListNews>) {
                val listNews = response.body()?.news
                if(listNews != null) {
                    deleteAllNews()
                    for (news in listNews) {
                        insert(news)
                    }
                }
            }

            override fun onFailure(call: Call<ListNews>, t: Throwable) {
                Toast.makeText(getApplication(), "Error", Toast.LENGTH_LONG).show()
            }
        })
    }

    fun insert(news: News) = viewModelScope.launch {

        repository.insertNews(news)
    }

    fun deleteAllNews() = viewModelScope.launch {

        repository.deleteAllNews()
    }
}