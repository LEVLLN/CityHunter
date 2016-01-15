package lk.simplecode.kz.cityhunter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import lk.simplecode.kz.cityhunter.model.Organization;

public class OrganizationAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Organization> organizationList;

    public OrganizationAdapter(Context context, List<Organization> organizationList) {
        this.context = context;
        this.organizationList = organizationList;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return organizationList.size();
    }

    @Override
    public Object getItem(int position) {
        return organizationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.organization_item, parent, false);
        }
        Organization organization = (Organization) getItem(position);
        ((TextView) view.findViewById(R.id.organization_title)).setText("Название: " + Html.fromHtml(organization.getTitle()));
        ((TextView) view.findViewById(R.id.organization_description)).setText("Описание: " + Html.fromHtml(organization.getDescription()));
        ((TextView) view.findViewById(R.id.organization_address)).setText("Адрес: " + organization.getAddress());

        return view;
    }
}
