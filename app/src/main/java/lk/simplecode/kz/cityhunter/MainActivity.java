package lk.simplecode.kz.cityhunter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import lk.simplecode.kz.cityhunter.model.MenuOfOrganization;
import lk.simplecode.kz.cityhunter.network.RetrofitFacade;
import retrofit.Callback;
import retrofit.Response;

//recipler view
//html
public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyplerView;
    private RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
    private List<MenuOfOrganization> mMenuList = new ArrayList<MenuOfOrganization>();
    private MenuRecyplerAdapter mMenuRecyplerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyplerView = (RecyclerView) findViewById(R.id.main_activitity_menu_recypler_view);
        RetrofitFacade.getInstance().getMenu(new Callback<List<MenuOfOrganization>>() {
            @Override
            public void onResponse(Response<List<MenuOfOrganization>> response) {
                mMenuList.addAll(response.body());
                Log.i("Text", mMenuList.toString());
                mRecyplerView.setLayoutManager(mLayoutManager);
                mMenuRecyplerAdapter = new MenuRecyplerAdapter(MainActivity.this, mMenuList);
                mRecyplerView.setAdapter(mMenuRecyplerAdapter);
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });

    }

}
