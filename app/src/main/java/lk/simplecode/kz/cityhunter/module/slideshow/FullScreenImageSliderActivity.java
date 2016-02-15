package lk.simplecode.kz.cityhunter.module.slideshow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;

import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;

import java.util.Arrays;

import lk.simplecode.kz.cityhunter.R;

public class FullScreenImageSliderActivity extends AppCompatActivity {
    private static ViewPager mPager;
    private PageIndicator mPageIndicator;
    private ProgressBar mProgressBar;
    private int currentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_slider_fullscreen);
        mPager = (ViewPager) findViewById(R.id.image_slider_full_screen_view_pager);
        mPageIndicator = (CirclePageIndicator) findViewById(R.id.fullscreen_p_i);
//        mProgressBar = (ProgressBar) findViewById(R.id.loading_image_pb);
        Intent intent = getIntent();
        currentPage = intent.getIntExtra("page", -1);
        String[] images;
        images = intent.getExtras().getStringArray("images");
        Log.i("  ", Arrays.toString(images));
        mPager.setAdapter(new SlidingImage_Full_Screen_Adapter(this, images));
        mPager.setCurrentItem(currentPage);
    }
}
