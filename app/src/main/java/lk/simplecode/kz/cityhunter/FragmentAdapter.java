package lk.simplecode.kz.cityhunter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FragmentAdapter extends FragmentPagerAdapter {
    private Fragment mFragment;
    public FragmentAdapter(FragmentManager mgr) {
        super(mgr);
    }
    private String CATALOG = "КАТАЛОГ";
    private String NEW = "НОВОЕ";
    @Override

    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(int position) {


        if (position == 0) {
            mFragment = CategoryFragment.newInstance(position);   }
        if (position==1){
            mFragment =NewOrganizationFragment.newInstance(position);}
            return mFragment;

    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return CATALOG;
            case 1:
            default:
                return NEW;
        }
    }
}