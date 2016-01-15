package lk.simplecode.kz.cityhunter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lk.simplecode.kz.cityhunter.model.MenuOfOrganization;
import lk.simplecode.kz.cityhunter.model.Organization;
import lk.simplecode.kz.cityhunter.network.RetrofitFacade;
import retrofit.Callback;
import retrofit.Response;

//recipler view
//html
public class MainActivity extends AppCompatActivity {
    private ListView menuListView;
    private MenuAdapter menuAdapter;
    private final List<MenuOfOrganization> listMenu = new ArrayList<MenuOfOrganization>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        menuListView = (ListView) findViewById(R.id.main_activitity_menu_listview);
        RetrofitFacade.getInstance().getMenu(new Callback<List<MenuOfOrganization>>() {
            @Override
            public void onResponse(Response<List<MenuOfOrganization>> response) {
                listMenu.addAll(response.body());
                System.out.println((listMenu));
                menuAdapter = new MenuAdapter(MainActivity.this, listMenu);
                menuListView.setAdapter(menuAdapter);
                menuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                        Log.i("Menu_Items", listMenu.get(position).getName());
                        Long menuId = listMenu.get(position).getId();
                        //Log.i("text",listMenu.toString());
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
