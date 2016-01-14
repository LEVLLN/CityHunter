package lk.simplecode.kz.cityhunter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lk.simplecode.kz.cityhunter.model.MenuCityHunter;

public class MyAdapter extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater;
    List<MenuCityHunter> productList = new ArrayList<>();


    MyAdapter(Context context1, List<MenuCityHunter> listMenu) {
        context = context1;
        productList = listMenu;
        layoutInflater = LayoutInflater.from(context);
        //   layoutInflater = (LayoutInflater) context.getSystemService(Context.LAUNCHER_APPS_SERVICE);

    }




    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.item, parent, false);

        }

        MenuCityHunter menu = (MenuCityHunter) getItem(position);

        ((TextView) view.findViewById(R.id.menu_name)).setText(menu.getName());
//        ((TextView) view.findViewById(R.id.menu_id)).setText(String.valueOf(menu.getId()));

        return view;
    }

    MenuCityHunter getProduct(int position) {
        return ((MenuCityHunter) getItem(position));
    }



}