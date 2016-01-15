package lk.simplecode.kz.cityhunter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lk.simplecode.kz.cityhunter.model.MenuOfOrganization;

public class MenuAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<MenuOfOrganization> mOrganizationList = new ArrayList<>();


    MenuAdapter(Context context1, List<MenuOfOrganization> organizationList) {
        mContext = context1;
        mOrganizationList = organizationList;
        mLayoutInflater = LayoutInflater.from(mContext);
    }


    @Override
    public int getCount() {
        return mOrganizationList.size();
    }

    @Override
    public Object getItem(int position) {
        return mOrganizationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = mLayoutInflater.inflate(R.layout.menu_item, parent, false);

        }

        MenuOfOrganization menu = (MenuOfOrganization) getItem(position);

        ((TextView) view.findViewById(R.id.menu_name)).setText(menu.getName());
//        ((TextView) view.findViewById(R.id.menu_id)).setText(String.valueOf(menu.getId()));

        return view;
    }

    MenuOfOrganization getProduct(int position) {
        return ((MenuOfOrganization) getItem(position));
    }


}