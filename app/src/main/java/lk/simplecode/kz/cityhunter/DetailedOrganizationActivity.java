package lk.simplecode.kz.cityhunter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;

import lk.simplecode.kz.cityhunter.model.DetailedOrganization;
import lk.simplecode.kz.cityhunter.network.RetrofitFacade;
import retrofit.Callback;
import retrofit.Response;

public class DetailedOrganizationActivity extends AppCompatActivity {
    private TextView mTextView;
    private DetailedOrganization mDetailedOrganization;
    private static ViewPager mPager;
    private PageIndicator mPageIndicator;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_organization);

        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_organization_toolbar);
        setSupportActionBar(toolbar);
        final ProgressBar firstBar;
        firstBar = (ProgressBar) findViewById(R.id.detail_organization_pb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        long menuId = intent.getLongExtra("post_id", -1l);
        String title = intent.getStringExtra("title");
        setTitle(Html.fromHtml(title));
        mTextView = (TextView) findViewById(R.id.detail_title);

        RetrofitFacade.getInstance().getDetailedOrganization(menuId, new Callback<DetailedOrganization>() {
            @Override
            public void onResponse(Response<DetailedOrganization> response) {
                if (!response.isSuccess()) {
                    firstBar.setVisibility(View.VISIBLE);
                }
                if (response.isSuccess()) {
                    mDetailedOrganization = response.body();
                    Log.i("detail", mDetailedOrganization.toString());
                    mTextView.setText(Html.fromHtml(mDetailedOrganization.getTitle()));
                    mPager = (ViewPager) findViewById(R.id.image_slider_view_pager);
                    String [] images = mDetailedOrganization.getImages();
                    mPager.setAdapter(new SlidingImage_Adapter(DetailedOrganizationActivity.this,images));
                    mPageIndicator = (CirclePageIndicator)findViewById(R.id.circle_page_indicator);
                    mPageIndicator.setViewPager(mPager);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
