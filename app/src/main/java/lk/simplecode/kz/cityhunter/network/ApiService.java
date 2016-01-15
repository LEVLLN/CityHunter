package lk.simplecode.kz.cityhunter.network;

import java.util.List;

import lk.simplecode.kz.cityhunter.model.Organization;
import lk.simplecode.kz.cityhunter.model.MenuOfOrganization;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

public interface ApiService {
    @GET("menu")
    Call<List<MenuOfOrganization>> menuList();
    @GET("posts")
    Call<List<Organization>> intitutionList(@Query("category_id") Long categoryId);
    

}
