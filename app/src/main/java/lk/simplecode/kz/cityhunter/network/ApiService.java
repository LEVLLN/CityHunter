package lk.simplecode.kz.cityhunter.network;

import java.util.List;

import lk.simplecode.kz.cityhunter.model.DetailedOrganization;
import lk.simplecode.kz.cityhunter.model.Organization;
import lk.simplecode.kz.cityhunter.model.Category;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface ApiService {
    @GET("menu")
    Call<List<Category>> menuList();

    @GET("posts")
    Call<List<Organization>> organizationList(@Query("category_id") Long categoryId,@Query("type") String newOrganization);

    @GET("posts/{post_id}")
    Call<DetailedOrganization> clarification(@Path("post_id") Long organizationId);

//    @GET("posts")
//    Call<List<Organization>> resultOfSearch();
}
