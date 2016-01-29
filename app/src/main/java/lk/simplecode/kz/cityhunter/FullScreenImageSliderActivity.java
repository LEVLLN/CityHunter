package lk.simplecode.kz.cityhunter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;

import java.util.Arrays;

public class FullScreenImageSliderActivity extends AppCompatActivity {
    private static ViewPager mPager;
    private ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_slider_fullscreen);
        mPager = (ViewPager) findViewById(R.id.image_slider_full_screen_view_pager);
//        mProgressBar = (ProgressBar) findViewById(R.id.loading_image_pb);
        Intent intent = getIntent();
        String[] images;
        images = intent.getExtras().getStringArray("images");
        Log.i("  ", Arrays.toString(images));
        mPager.setAdapter(new SlidingImage_Full_Screen_Adapter(this, images));

    }
}
