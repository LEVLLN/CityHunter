package lk.simplecode.kz.cityhunter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lk.simplecode.kz.cityhunter.model.Institution;
import lk.simplecode.kz.cityhunter.model.MenuCityHunter;
import lk.simplecode.kz.cityhunter.network.RetrofitFacade;
import retrofit.Callback;
import retrofit.Response;

public class MainActivity extends AppCompatActivity {
    Button button;
    ListView listView;
    MyAdapter myAdapter;
    TextView textView;
    final List<MenuCityHunter> listMenu = new ArrayList<MenuCityHunter>();
    final List<Institution> listInstitution = new ArrayList<Institution>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.lvMain);
        button = (Button) findViewById(R.id.get_institution);
        RetrofitFacade.getInstance().getMenu(new Callback<List<MenuCityHunter>>() {
            @Override
            public void onResponse(Response<List<MenuCityHunter>> response) {
                listMenu.addAll(response.body());
                System.out.println((listMenu));
                myAdapter = new MyAdapter(MainActivity.this, listMenu);
                listView.setAdapter(myAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                        Log.i("Menu_Items", listMenu.get(position).getName());
                        Long menuId = listMenu.get(position).getId();
                        RetrofitFacade.getInstance().getInstitution(menuId, new Callback<List<Institution>>() {
                            @Override
                            public void onResponse(Response<List<Institution>> response) {
                                listInstitution.addAll(response.body());
                                for (Institution a:listInstitution) {
                                    Log.i("Instution",a.getTitle());

                                }

                            }

                            @Override
                            public void onFailure(Throwable t) {
                                t.printStackTrace();
                            }
                        });
                    }
                });
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });


//        RetrofitFacade.getInstance().getMenu(new Callback<List<MenuCityHunter>>() {
//            @Override
//            public void onResponse(final Response<List<MenuCityHunter>> response) {
//                listMenu.addAll(response.body());
//                System.out.println(listMenu);
//                myAdapter = new MyAdapter(MainActivity.this, listMenu);
//                listView.setAdapter(new MyAdapter(MainActivity.this, listMenu));
//                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        Log.i("test", String.valueOf(listMenu.get(position).getName()));
//
//
//                        RetrofitFacade.getInstance().getInstitution(listMenu.get(position).getId(),new Callback<List<Institution>>() {{
//                            @Override
//                            public void onResponse(Response<List<Institution>> response) {
//                                listInstitution.addAll(response.body());
//                                System.out.println(listInstitution);
//
//                            }
//
//                            @Override
//                            public void onFailure(Throwable t) {
//                                t.printStackTrace();
//                            }
//                        });
//                    }
//                });
//
//            }
//
//
//            @Override
//            public void onFailure(Throwable t) {
//                t.printStackTrace();
//            }
//        });
    }


    public void onClick(View view) {

    }
}
