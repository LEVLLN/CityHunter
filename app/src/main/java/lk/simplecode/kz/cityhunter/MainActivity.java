package lk.simplecode.kz.cityhunter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import lk.simplecode.kz.cityhunter.model.Category;
import lk.simplecode.kz.cityhunter.network.RetrofitFacade;
import retrofit.Callback;
import retrofit.Response;

//recipler view
//html
public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyplerView;
    private RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
    private List<Category> mCategoryList = new ArrayList<Category>();
    private CategoryRecyplerAdapter mCategoryRecyplerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyplerView = (RecyclerView) findViewById(R.id.main_activitity_menu_recypler_view);
        RetrofitFacade.getInstance().getMenu(new Callback<List<Category>>() {
            @Override
            public void onResponse(Response<List<Category>> response) {
                mCategoryList.addAll(response.body());
                Log.i("Text", mCategoryList.toString());
                mRecyplerView.setLayoutManager(mLayoutManager);
                mCategoryRecyplerAdapter = new CategoryRecyplerAdapter(MainActivity.this, mCategoryList);
                mRecyplerView.setAdapter(mCategoryRecyplerAdapter);
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });

    }

}
