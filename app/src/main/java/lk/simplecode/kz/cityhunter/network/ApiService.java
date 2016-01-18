package lk.simplecode.kz.cityhunter.network;

import java.util.List;

import lk.simplecode.kz.cityhunter.model.Organization;
import lk.simplecode.kz.cityhunter.model.Category;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

public interface ApiService {
    @GET("menu")
    Call<List<Category>> menuList();
    @GET("posts")
    Call<List<Organization>> organizationList(@Query("category_id") Long categoryId);
}
