package lk.simplecode.kz.cityhunter.module.organizations;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lk.simplecode.kz.cityhunter.R;
import lk.simplecode.kz.cityhunter.model.Organization;
import lk.simplecode.kz.cityhunter.network.RetrofitFacade;
import retrofit.Callback;
import retrofit.Response;


public class OrganizationListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
    private OrganizationListRecyplerAdapter mOrganizationAdapter;
    private List<Organization> mListOrganization = new ArrayList<Organization>();
    private ProgressBar mProgressBar;
    private ImageView mRefresh;
    private TextView mError;

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
        mError = (TextView) findViewById(R.id.organization_list_error);
        mRefresh = (ImageView) findViewById(R.id.organization_list_refresh);
        Intent intent = getIntent();
        long menuId = intent.getLongExtra("category_id", -1l);
        String title = intent.getExtras().getString("title");
        setTheme(R.style.AppTheme);
        setTitle(title);
        Toolbar toolbar = (Toolbar) findViewById(R.id.organization_list_toolbar);
        setSupportActionBar(toolbar);
        mProgressBar = (ProgressBar) findViewById(R.id.organization_list_pb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mError.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
        mRefresh.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
        getData(menuId);

    }

    public void getData(final long menuId) {
        RetrofitFacade.getInstance().getOrganization(menuId, "", new Callback<List<Organization>>() {
            @Override
            public void onResponse(Response<List<Organization>> response) {
                if (response.isSuccess()) {
                    mProgressBar.setVisibility(View.GONE);
                    mRefresh.setVisibility(View.GONE);
                    mError.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mListOrganization.addAll(response.body());
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mOrganizationAdapter = new OrganizationListRecyplerAdapter(OrganizationListActivity.this, mListOrganization);
                    mRecyclerView.setAdapter(mOrganizationAdapter);

                }
                if (!response.isSuccess()) {
                    mProgressBar.setVisibility(View.VISIBLE);
                    mRefresh.setVisibility(View.GONE);
                    mError.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                mProgressBar.setVisibility(View.GONE);
                mError.setVisibility(View.VISIBLE);
                mRefresh.setVisibility(View.VISIBLE);
                mRefresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mProgressBar.setVisibility(View.VISIBLE);
                        mError.setVisibility(View.GONE);
                        mRefresh.setVisibility(View.GONE);
                        getData(menuId);
                    }
                });
                t.printStackTrace();
            }
        });
    }

}
