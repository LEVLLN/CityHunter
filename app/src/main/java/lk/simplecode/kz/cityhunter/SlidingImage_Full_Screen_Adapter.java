package lk.simplecode.kz.cityhunter;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Picasso;

public class SlidingImage_Full_Screen_Adapter extends PagerAdapter {


    private String[] IMAGES;
    private LayoutInflater inflater;
    private Context context;


    public SlidingImage_Full_Screen_Adapter(Context context, String[] images) {
        this.context = context;
        this.IMAGES = images;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return IMAGES.length;
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.fragment_fullscreen_image_slider, view, false);

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout
                .findViewById(R.id.image_fullscreen_slider_iv);
        final ProgressBar progressBar = (ProgressBar) imageLayout.findViewById(R.id.loading_image_pb);
        progressBar.setVisibility(View.VISIBLE);
        Picasso.with(context)
                .load("http://" + IMAGES[position])
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