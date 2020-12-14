package fr.Airweb.news.interfaces;

import fr.Airweb.news.database.news.ListNews;
import retrofit2.Call;
import retrofit2.http.POST;

public interface APIService {

    String BASE_URL = "https://airweb-demo.airweb.fr";

    @POST("psg/psg.json")
    Call<ListNews> getNews();
}
