package lk.simplecode.kz.cityhunter.network;

import com.squareup.okhttp.ResponseBody;

import java.util.List;

import lk.simplecode.kz.cityhunter.model.Institution;
import lk.simplecode.kz.cityhunter.model.MenuCityHunter;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

public interface ApiService {
    @GET("menu")
    Call<List<MenuCityHunter>> menuList();
    @GET("posts")
    Call<List<Institution>> intitutionList(@Query("category_id") Long categoryId);

}
