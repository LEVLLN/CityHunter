package lk.simplecode.kz.cityhunter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.DisplayMetrics;
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
import java.util.Timer;
import java.util.TimerTask;

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
    private LayoutParams mTabParams;
    private LayoutParams mIconParams;
    private Toolbar mToolbar;
    private ProgressBar mProgressBar;
    private LinearLayout mAddressLayout;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;

    private static List<String> sImportantCapions;

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
        mProgressBar = (ProgressBar) findViewById(R.id.detail_organization_pb);

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
                    final Handler handler = new Handler();
                    final Runnable Update = new Runnable() {
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
                            handler.post(Update);
                        }
                    }, 3000, 3000);

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
                    mProgressBar.setVisibility(View.GONE);
                    Log.i("MY_TEST", mDetailedOrganization.getInfo().toString());
                    List<Info> infoList = new ArrayList<Info>();
                    infoList = mDetailedOrganization.getInfo();
                    List<Info> featureList = new ArrayList<Info>();
                    List<Info> importantInfoList = new ArrayList<Info>();
                    List<Info> telephoneNumList = new ArrayList<Info>();

                    for (Info s : infoList) {
                        boolean isAddress = s.getCaption().contains("Адрес");
                        boolean isTimeWork = s.getCaption().contains("Время работы");
                        boolean isKitchen = s.getCaption().contains("Кухня");
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
                        if (isTitle) {
                            continue;
                        }
                        featureList.add(s);
                    }

                    for (Info important : importantInfoList) {
                        LayoutInflater layoutInflater = LayoutInflater.from(DetailedOrganizationActivity.this);
                        final View view = layoutInflater.inflate(R.layout.item_content, mLinearLayout, false);
                        ImageView iconImportant = (ImageView) view.findViewById(R.id.icon_item);
                        TextView tvValue = (TextView) view.findViewById(R.id.tv_item_content);
                        Log.i("TEST_ADRESS", important.getValue());
                        tvValue.setText(important.getValue());
                        iconImportant.setBackgroundColor(Color.BLACK);
                        mLinearLayout.addView(view);
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

                    for (String telephoneNumber : nums) {
                        LayoutInflater layoutInflater = LayoutInflater.from(DetailedOrganizationActivity.this);
                        final View view = layoutInflater.inflate(R.layout.item_content_telephone, mLinearLayout, false);
                        final TextView telephoneTv = (TextView) view.findViewById(R.id.telephone_number);
                        ImageView telephoneIcon = (ImageView) view.findViewById(R.id.icon_telephone);
                        telephoneTv.setText(telephoneNumber);
                        telephoneIcon.setBackgroundColor(Color.BLUE);
                        mLinearLayout.addView(view);

                        Log.i("TEL:", telephoneNumber);

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

    public int dpToPx(int dp) {

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

}
