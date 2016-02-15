package lk.simplecode.kz.cityhunter.module.slideshow;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Picasso;

import lk.simplecode.kz.cityhunter.R;

public class SlidingImage_Full_Screen_Adapter extends PagerAdapter {


    private String[] mImages;
    private LayoutInflater mInflater;
    private Context mContext;


    public SlidingImage_Full_Screen_Adapter(Context mContext, String[] images) {
        this.mContext = mContext;
        this.mImages = images;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mImages.length;
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = mInflater.inflate(R.layout.fragment_fullscreen_image_slider, view, false);

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout
                .findViewById(R.id.image_fullscreen_slider_iv);
        final ProgressBar progressBar = (ProgressBar) imageLayout.findViewById(R.id.loading_image_pb);
        progressBar.setVisibility(View.VISIBLE);
        Picasso.with(mContext)
                .load("http://" + mImages[position])
                .into(imageView);
        view.addView(imageLayout, 0);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


}