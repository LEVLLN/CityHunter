package lk.simplecode.kz.cityhunter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import lk.simplecode.kz.cityhunter.model.MenuOfOrganization;
import lk.simplecode.kz.cityhunter.model.Organization;
import lk.simplecode.kz.cityhunter.network.RetrofitFacade;
import retrofit.Callback;
import retrofit.Response;


public class OrganizationActivity extends AppCompatActivity {
    private ListView organizationListView;
    private OrganizationAdapter organizationAdapter;
    private List<Organization> listOrganization = new ArrayList<Organization>();
    private MenuOfOrganization menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organization_activity);
        Intent intent = getIntent();
        long menuId = intent.getLongExtra("category_id", -1l);
        organizationListView = (ListView) findViewById(R.id.organization_activity_listview);
        RetrofitFacade.getInstance().getInstitution(menuId, new Callback<List<Organization>>() {
            @Override
            public void onResponse(Response<List<Organization>> response) {
                listOrganization.addAll(response.body());
                organizationAdapter = new OrganizationAdapter(OrganizationActivity.this, listOrganization);
                organizationListView.setAdapter(organizationAdapter);
                organizationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.i("organization", listOrganization.get(position).getTitle());
                    }
                });
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });

    }


}
