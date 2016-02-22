package lk.simplecode.kz.cityhunter.module.detail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import lk.simplecode.kz.cityhunter.R;
import lk.simplecode.kz.cityhunter.model.DetailedOrganization;
import lk.simplecode.kz.cityhunter.model.Info;
import lk.simplecode.kz.cityhunter.module.slideshow.FullScreenImageSliderActivity;
import lk.simplecode.kz.cityhunter.network.RetrofitFacade;
import retrofit.Callback;
import retrofit.Response;

public class DetailedOrganizationActivity extends AppCompatActivity {

    private LinearLayout mLinearLayout;
    private DetailedOrganization mDetailedOrganization;
    private static ViewPager mPager;
    private PageIndicator mPageIndicator;
    private LayoutParams mTabParams;
    private LayoutParams mIconParams;
    private Toolbar mToolbar;
    private ProgressBar mProgressBar;
    private ImageView mRefresh;
    private TextView mError;
    private LinearLayout mAddressLayout;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private String[] images;
    private static List<String> sImportantCapions;
    private String mDescription;

    static {
        sImportantCapions = new ArrayList<>();
        sImportantCapions.add("Адрес");
        sImportantCapions.add("Время работы");
        sImportantCapions.add("Кухня");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_organization);
        mLinearLayout = (LinearLayout) findViewById(R.id.detail_linear_layout_content);
        mToolbar = (Toolbar) findViewById(R.id.detail_organization_toolbar);
        mError = (TextView) findViewById(R.id.detail_error);
        mRefresh = (ImageView) findViewById(R.id.detail_refresh);
        mProgressBar = (ProgressBar) findViewById(R.id.detail_organization_pb);

        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent mIntent = getIntent();
        long menuId = mIntent.getLongExtra("post_id", -1l);
        String title = mIntent.getStringExtra("title");
        mDescription = mIntent.getStringExtra("description");
        setTitle(Html.fromHtml(title));
        mError.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
        mRefresh.setVisibility(View.GONE);
        mLinearLayout.setVisibility(View.GONE);
        getData(menuId);
    }


    public void getData(final long menuId) {
        RetrofitFacade.getInstance().getDetailedOrganization(menuId, new Callback<DetailedOrganization>() {
            @Override
            public void onResponse(Response<DetailedOrganization> response) {
                if (!response.isSuccess()) {
                    mProgressBar.setVisibility(View.VISIBLE);
                    mError.setVisibility(View.GONE);
                    mRefresh.setVisibility(View.GONE);
                }
                if (response.isSuccess()) {
                    mError.setVisibility(View.GONE);
                    mRefresh.setVisibility(View.GONE);
                    mProgressBar.setVisibility(View.GONE);
                    mLinearLayout.setVisibility(View.VISIBLE);
                    mDetailedOrganization = response.body();
                    Log.i("detail", mDetailedOrganization.toString());
                    mPager = (ViewPager) findViewById(R.id.image_slider_view_pager);
                    images = mDetailedOrganization.getImages();
                    NUM_PAGES = images.length;
                    mPager.setAdapter(new SlidingImage_Adapter(DetailedOrganizationActivity.this, images));
                    mPageIndicator = (CirclePageIndicator) findViewById(R.id.circle_page_indicator);
                    mPageIndicator.setViewPager(mPager);
                    final Handler handler;
                    handler = new Handler();
                    final Runnable update;
                    update = new Runnable() {
                        public void run() {

                            if (currentPage == NUM_PAGES) {
                                currentPage = 0;
                            }

                            mPager.setCurrentItem(currentPage++, true);
                        }
                    };


                    Timer swipeTimer = new Timer();
                    swipeTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            handler.post(update);
                        }
                    }, 10000, 10000);

                    // Pager listener over indicator
                    mPageIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                        @Override
                        public void onPageSelected(int position) {
                            currentPage = position;

                        }

                        @Override
                        public void onPageScrolled(int pos, float arg1, int arg2) {

                        }

                        @Override
                        public void onPageScrollStateChanged(int pos) {

                        }
                    });
                    Log.i("MY_TEST", mDetailedOrganization.getInfo().toString());
                    List<Info> infoList = new ArrayList<Info>();
                    infoList = mDetailedOrganization.getInfo();
                    List<Info> featureList = new ArrayList<Info>();
                    List<Info> importantInfoList = new ArrayList<Info>();
                    List<Info> telephoneNumList = new ArrayList<Info>();

                    for (Info s : infoList) {

                        boolean isTelephone = s.getCaption().contains("Телефон");
                        boolean isTitle = s.getCaption().contains("Заведение");

                        if (s.getCaption().contains("Телефон")) {
                            telephoneNumList.add(s);
                            continue;
                        }
                        if (sImportantCapions.contains(s.getCaption())) {
                            importantInfoList.add(s);
                            continue;
                        }
                        if (isTitle) continue;
                        featureList.add(s);
                    }

                    for (Info important : importantInfoList) {
                        boolean isAddress = important.getCaption().contains("Адрес");
                        boolean isTimeWork = important.getCaption().contains("Время работы");
                        boolean isKitchen = important.getCaption().contains("Кухня");
                        LayoutInflater layoutInflater = LayoutInflater.from(DetailedOrganizationActivity.this);
                        final View view = layoutInflater.inflate(R.layout.item_content, mLinearLayout, false);
                        ImageView iconImportant = (ImageView) view.findViewById(R.id.icon_item);
                        TextView tvValue = (TextView) view.findViewById(R.id.tv_item_content);
                        tvValue.setText(important.getValue());
                        // iconImportant.setBackgroundColor(Color.BLACK);
                        mLinearLayout.addView(view);
                        if (isAddress) {
                            Picasso.with(DetailedOrganizationActivity.this)
                                    .load(R.drawable.address)
                                    .into(iconImportant);
                        }
                        if (isTimeWork) {
                            Picasso.with(DetailedOrganizationActivity.this)
                                    .load(R.drawable.clock)
                                    .into(iconImportant);
                        }
                        if (isKitchen) {
                            Picasso.with(DetailedOrganizationActivity.this)
                                    .load(R.drawable.fork)
                                    .into(iconImportant);
                        }
                    }
                    LayoutInflater line = LayoutInflater.from(DetailedOrganizationActivity.this);
                    final View viewLine = line.inflate(R.layout.item_line, mLinearLayout, false);
                    mLinearLayout.addView(viewLine);

                    for (Info feature : featureList) {
                        LayoutInflater layoutInflater = LayoutInflater.from(DetailedOrganizationActivity.this);
                        final View view = layoutInflater.inflate(R.layout.item_content_features, mLinearLayout, false);
                        TextView featureTv = (TextView) view.findViewById(R.id.caption_features_content);
                        TextView tvValue = (TextView) view.findViewById(R.id.value_features_content);
                        tvValue.setText(feature.getValue());
                        featureTv.setText(feature.getCaption());
                        mLinearLayout.addView(view);
                    }
                    String[] nums = new String[0];
                    for (int i = 0; i < telephoneNumList.size(); i++) {
                        Log.i("size", String.valueOf(i));
                        nums = telephoneNumList.get(i).getValue().split(";");
                    }
//                    if (!featureList.isEmpty()) {
//                        LayoutInflater marginFlater = LayoutInflater.from(DetailedOrganizationActivity.this);
//                        final View marginView = marginFlater.inflate(R.layout.item_line, mLinearLayout, false);
//                        marginView.setBackgroundColor(Color.WHITE);
//                        mTabParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//                        mTabParams.setMargins(0, 0, 0, dpToPx(30));
//                        marginView.setLayoutParams(mTabParams);
//                        mLinearLayout.addView(marginView);
//                    }

                    if (!mDescription.equals("")) {
                        LayoutInflater layoutInflaterDescription = LayoutInflater.from(DetailedOrganizationActivity.this);
                        final View descriptionView = layoutInflaterDescription.inflate(R.layout.item_content_features, mLinearLayout, false);
                        TextView featur = (TextView)descriptionView.findViewById(R.id.caption_features_content);
                        TextView tvValue = (TextView) descriptionView.findViewById(R.id.value_features_content);
                        featur.setVisibility(View.GONE);
                        tvValue.setText(Html.fromHtml(mDescription));
                        mLinearLayout.addView(descriptionView);
                    }

                    for (String telephoneNumber : nums) {
                        LayoutInflater layoutInflater = LayoutInflater.from(DetailedOrganizationActivity.this);
                        final View view = layoutInflater.inflate(R.layout.item_content_telephone, mLinearLayout, false);
                        final TextView telephoneTv = (TextView) view.findViewById(R.id.telephone_number);
                        ImageView telephoneIcon = (ImageView) view.findViewById(R.id.icon_telephone);
                        telephoneTv.setText(telephoneNumber);
                        mLinearLayout.addView(view);
                        telephoneTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.i("TEL:", String.valueOf(telephoneTv.getText()));
                                Intent myIntent = new Intent(Intent.ACTION_DIAL);
                                String phNum = "tel:" + telephoneTv.getText();
                                myIntent.setData(Uri.parse(phNum));
                                startActivity(myIntent);
                            }
                        });

                    }

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

    @Override
    public void onBackPressed() {
        currentPage = 0;
        finish();
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            currentPage = 0;
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void toFullScreen(View view) {
        Intent intent = new Intent(DetailedOrganizationActivity.this, FullScreenImageSliderActivity.class);
        intent.putExtra("images", images);
        intent.putExtra("page", currentPage);
        startActivity(intent);
    }

    public int dpToPx(int dp) {

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

}
