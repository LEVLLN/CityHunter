package lk.simplecode.kz.cityhunter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import lk.simplecode.kz.cityhunter.model.Organization;
import lk.simplecode.kz.cityhunter.network.RetrofitFacade;
import retrofit.Callback;
import retrofit.Response;


public class OrganizationListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
    private OrganizationListRecyplerAdapter mOrganizationAdapter;
    private List<Organization> mListOrganization = new ArrayList<Organization>();


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.organization_activity_recypler_view);
        Intent intent = getIntent();
        long menuId = intent.getLongExtra("category_id", -1l);
        String title = intent.getExtras().getString("title");
        setTheme(R.style.AppTheme);
        setTitle(title);
        Toolbar toolbar = (Toolbar) findViewById(R.id.organization_list_toolbar);
        setSupportActionBar(toolbar);
        final ProgressBar firstBar;
        firstBar = (ProgressBar) findViewById(R.id.organization_list_pb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RetrofitFacade.getInstance().getOrganization(menuId, "", new Callback<List<Organization>>() {
            @Override
            public void onResponse(Response<List<Organization>> response) {
                if (response.isSuccess()) {
                    mListOrganization.addAll(response.body());
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mOrganizationAdapter = new OrganizationListRecyplerAdapter(OrganizationListActivity.this, mListOrganization);
                    mRecyclerView.setAdapter(mOrganizationAdapter);
                    firstBar.setVisibility(View.GONE);
                }
                if (!response.isSuccess()) {
                    firstBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                firstBar.setVisibility(View.VISIBLE);
                t.printStackTrace();
            }
        });
    }


}
