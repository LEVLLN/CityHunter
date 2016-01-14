package lk.simplecode.kz.cityhunter.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import lk.simplecode.kz.cityhunter.model.Institution;
import lk.simplecode.kz.cityhunter.model.MenuCityHunter;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class RetrofitFacade {
    private static RetrofitFacade sInstance;

    private Retrofit mRetrofit;
    private ApiService mApiService;

    public RetrofitFacade() {
        Gson gson = new GsonBuilder()
                .setDateFormat("dd.MM.yyyy")
                .create();
        mRetrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("http://cityhunter.kz/api/")
                .build();
        mApiService = mRetrofit.create(ApiService.class);
    }

    public static void init() {
        sInstance = new RetrofitFacade();
    }

    public static RetrofitFacade getInstance() {
        return sInstance;
    }

    public void getMenu(Callback<List<MenuCityHunter>> callback) {
        mApiService.menuList().enqueue(callback);

    }

    public void getInstitution(Long id, Callback<List<Institution>> callback) {
        mApiService.intitutionList(id).enqueue(callback);
    }
}
