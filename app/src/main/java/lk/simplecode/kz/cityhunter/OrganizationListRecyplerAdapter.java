package lk.simplecode.kz.cityhunter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import lk.simplecode.kz.cityhunter.model.Organization;

public class OrganizationListRecyplerAdapter extends RecyclerView.Adapter<OrganizationListRecyplerAdapter.OrganizationViewHolder> {
    private Context mContext;
    private List<Organization> mOrganizationList = new ArrayList<Organization>();

    public OrganizationListRecyplerAdapter(Context context, List<Organization> mOrganizationList) {
        this.mContext = context;
        this.mOrganizationList = mOrganizationList;
    }


    @Override
    public OrganizationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_organization_list, parent, false);

        return new OrganizationViewHolder(v);
    }

    @Override
    public void onBindViewHolder(OrganizationViewHolder holder, int position) {
        Collections.sort(mOrganizationList, new Organization.OrganizationComparator());
        Log.i("image", mOrganizationList.get(position).getImageUrl());
        Picasso.with(mContext)
                .load("http://" + mOrganizationList.get(position).getImageUrl())
                .into(holder.mImageViewCaption);
        if (mOrganizationList.get(position).isRec()) {
            holder.mTVRecomended.setVisibility(View.VISIBLE);
        } else {
            holder.mTVRecomended.setVisibility(View.GONE);
        }
        holder.mTVTitle.setText(Html.fromHtml(mOrganizationList.get(position).getTitle()));
        holder.mTVAddress.setText(mOrganizationList.get(position).getAddress());
        if (mOrganizationList.get(position).getDescription().equals("")) {
            holder.mTVDescription.setVisibility(View.GONE);
        } else {
            holder.mTVDescription.setVisibility(View.VISIBLE);
            holder.mTVDescription.setMaxLines(3);
            holder.mTVDescription.setText(Html.fromHtml(mOrganizationList.get(position).getDescription()));
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {

        return mOrganizationList.size();
    }

    public class OrganizationViewHolder extends RecyclerView.ViewHolder {
        private CardView mCardView;
        private TextView mTVTitle;
        private TextView mTVAddress;
        private TextView mTVDescription;
        private ImageView mImageViewCaption;
        private TextView mTVRecomended;

        public OrganizationViewHolder(View itemView) {
            super(itemView);
            mTVRecomended = (TextView) itemView.findViewById(R.id.tv_recomended);
            mCardView = (CardView) itemView.findViewById(R.id.card_view_organization);
            mTVTitle = (TextView) itemView.findViewById(R.id.organization_title);
            mTVAddress = (TextView) itemView.findViewById(R.id.organization_address);
            mTVDescription = (TextView) itemView.findViewById(R.id.organization_description);
            mImageViewCaption = (ImageView) itemView.findViewById(R.id.iv_organization);

        }
    }

}
