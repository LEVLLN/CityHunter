package lk.simplecode.kz.cityhunter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import lk.simplecode.kz.cityhunter.model.MenuCityHunter;
import lk.simplecode.kz.cityhunter.network.ApiService;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class RetroFitActivity extends AppCompatActivity {
    TextView textView;
    EditText editText;
    Button button;
    private Gson gson = new GsonBuilder().create();
    private Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("http://cityhunter.kz/api/")
            .build();
    private ApiService apiService = retrofit.create(ApiService.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retrofit_activity);
        button = (Button) findViewById(R.id.translate);
        editText = (EditText) findViewById(R.id.enter_text);
        textView = (TextView) findViewById(R.id.translated_text);

    }

    public void click(View view) {
        final List<MenuCityHunter> listMenu = new ArrayList<MenuCityHunter>();
        apiService.menuList().enqueue(new Callback<List<MenuCityHunter>>() {
            @Override
            public void onResponse(Response<List<MenuCityHunter>> response) {
                for (MenuCityHunter a : response.body()) {
                    listMenu.add(a);
                    System.out.println(a.getName());
                }


            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }


}
