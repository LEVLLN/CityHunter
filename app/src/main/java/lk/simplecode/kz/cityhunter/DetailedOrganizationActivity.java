package lk.simplecode.kz.cityhunter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;

import java.util.ArrayList;
import java.util.List;

import lk.simplecode.kz.cityhunter.model.DetailedOrganization;
import lk.simplecode.kz.cityhunter.model.Info;
import lk.simplecode.kz.cityhunter.network.RetrofitFacade;
import retrofit.Callback;
import retrofit.Response;

public class DetailedOrganizationActivity extends AppCompatActivity {

    private LinearLayout mLinearLayout;
    private DetailedOrganization mDetailedOrganization;
    private static ViewPager mPager;
    private PageIndicator mPageIndicator;
    private LayoutParams mAddressParams;
    private TextView mFeatureTv;
    private LayoutParams mIconParams;
    private TextView mTvValue;
    private Toolbar mToolbar;
    private ProgressBar mProgressBar;
    private LinearLayout mAddressLayout;
    private ImageView icon;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_organization);

        mLinearLayout = (LinearLayout) findViewById(R.id.detail_linear_layout_content);
        mToolbar = (Toolbar) findViewById(R.id.detail_organization_toolbar);
        mProgressBar = (ProgressBar) findViewById(R.id.detail_organization_pb);


//        float d = this.getResources().getDisplayMetrics().density;
//        int margin_left = (int) (14 * d); // margin in pixels
//        int margin_right = (int) (14 * d);
//        int margin_top = (int) (20 * d);


//        mAddressParams = new LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        mAddressParams.setMargins(margin_left, margin_top, margin_right, 0);
//        mIconParams = new LayoutParams(50, 50);
//        icon.setLayoutParams(mIconParams);

        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        long menuId = intent.getLongExtra("post_id", -1l);
        String title = intent.getStringExtra("title");
        setTitle(Html.fromHtml(title));


        RetrofitFacade.getInstance().getDetailedOrganization(menuId, new Callback<DetailedOrganization>() {
            @Override
            public void onResponse(Response<DetailedOrganization> response) {
                if (!response.isSuccess()) {
                    mProgressBar.setVisibility(View.VISIBLE);
                }
                if (response.isSuccess()) {
                    mDetailedOrganization = response.body();
                    Log.i("detail", mDetailedOrganization.toString());
                    mPager = (ViewPager) findViewById(R.id.image_slider_view_pager);
                    String[] images = mDetailedOrganization.getImages();
                    mPager.setAdapter(new SlidingImage_Adapter(DetailedOrganizationActivity.this, images));
                    mPageIndicator = (CirclePageIndicator) findViewById(R.id.circle_page_indicator);
                    mPageIndicator.setViewPager(mPager);
                    mProgressBar.setVisibility(View.GONE);
                    Log.i("MY_TEST", mDetailedOrganization.getInfo().toString());
                    List<Info> infoList = new ArrayList<Info>();
                    Info info;
                    infoList = mDetailedOrganization.getInfo();
                    for (Info s : infoList) {
                        boolean isAddress = s.getCaption().contains("Адрес");
                        boolean isTimeWork = s.getCaption().contains("Время работы");
                        boolean isKitchen = s.getCaption().contains("Кухня");

                        if (isAddress) {
                            LayoutInflater layoutInflater = LayoutInflater.from(DetailedOrganizationActivity.this);
                            final View view = layoutInflater.inflate(R.layout.item_content, mLinearLayout, false);
                            LinearLayout linearLayoutHorizont = (LinearLayout) view.findViewById(R.id.horiz_content_ll);
                            icon = (ImageView) view.findViewById(R.id.icon_item);
                            mTvValue = (TextView) view.findViewById(R.id.tv_item_content);
                            Log.i("TEST_ADRESS", s.getValue());
                            mTvValue.setText(s.getValue());
                            icon.setBackgroundColor(Color.BLACK);
                            mLinearLayout.addView(view);

                        }

                        if (isTimeWork) {
                            LayoutInflater layoutInflater = LayoutInflater.from(DetailedOrganizationActivity.this);
                            final View view = layoutInflater.inflate(R.layout.item_content, mLinearLayout, false);
                            LinearLayout linearLayoutHorizont = (LinearLayout) view.findViewById(R.id.horiz_content_ll);
                            icon = (ImageView) view.findViewById(R.id.icon_item);
                            mTvValue = (TextView) view.findViewById(R.id.tv_item_content);
                            Log.i("TEST_ADRESS", s.getValue());
                            mTvValue.setText(s.getValue());
                            icon.setBackgroundColor(Color.BLACK);
                            mLinearLayout.addView(view);
                        }

                        if (isKitchen) {
                            LayoutInflater layoutInflater = LayoutInflater.from(DetailedOrganizationActivity.this);
                            final View view = layoutInflater.inflate(R.layout.item_content, mLinearLayout, false);
                            LinearLayout linearLayoutHorizont = (LinearLayout) view.findViewById(R.id.horiz_content_ll);
                            icon = (ImageView) view.findViewById(R.id.icon_item);
                            mTvValue = (TextView) view.findViewById(R.id.tv_item_content);
                            Log.i("TEST_ADRESS", s.getValue());
                            mTvValue.setText(s.getValue());
                            icon.setBackgroundColor(Color.BLACK);
                            mLinearLayout.addView(view);
                        }
//                        if(!isAddress|!isTimeWork|!isKitchen){
//                            LayoutInflater layoutInflater = LayoutInflater.from(DetailedOrganizationActivity.this);
//                            final View view = layoutInflater.inflate(R.layout.item_content_features, mLinearLayout, false);
//                            LinearLayout linearLayoutHorizont = (LinearLayout) view.findViewById(R.id.features_ll);
//                            mFeatureTv = (TextView) view.findViewById(R.id.caption_features_content);
//                            mTvValue = (TextView) view.findViewById(R.id.value_features_content);
//                            mTvValue.setText(s.getValue());
//                            mFeatureTv.setText(s.getCaption());
//                            mLinearLayout.addView(view);
//                        }

                    }

                }
            }

            @Override
            public void onFailure(Throwable t) {
                mProgressBar.setVisibility(View.VISIBLE);
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

    public void toFullScreen(View view) {

    }

}
