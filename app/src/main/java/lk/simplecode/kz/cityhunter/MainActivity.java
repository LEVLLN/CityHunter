package lk.simplecode.kz.cityhunter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    private ListView mMenuListView;
    private MenuAdapter mMenuAdapter;
    private final List<MenuOfOrganization> mListMenu = new ArrayList<MenuOfOrganization>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMenuListView = (ListView) findViewById(R.id.main_activitity_menu_listview);
        RetrofitFacade.getInstance().getMenu(new Callback<List<MenuOfOrganization>>() {
            @Override
            public void onResponse(Response<List<MenuOfOrganization>> response) {
                mListMenu.addAll(response.body());
                System.out.println((mListMenu));
                mMenuAdapter = new MenuAdapter(MainActivity.this, mListMenu);
                mMenuListView.setAdapter(mMenuAdapter);
                mMenuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                        Log.i("Menu_Items", mListMenu.get(position).getName());
                        Long menuId = mListMenu.get(position).getId();
                        //Log.i("text",mListMenu.toString());
                        Intent intent = new Intent(MainActivity.this, OrganizationActivity.class);
                        intent.putExtra("category_id", menuId);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }


    public void onClick(View view) {

    }
}
