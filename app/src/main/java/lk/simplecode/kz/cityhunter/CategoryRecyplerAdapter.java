package lk.simplecode.kz.cityhunter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lk.simplecode.kz.cityhunter.model.Category;

public class CategoryRecyplerAdapter extends RecyclerView.Adapter<CategoryRecyplerAdapter.ViewHolder> {
    private Context mContext;
    private List<Category> mCategoryList = new ArrayList<Category>();

    public CategoryRecyplerAdapter(Context context, List<Category> mCategoryList) {
        this.mContext = context;
        this.mCategoryList = mCategoryList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category_list, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        StringBuilder sb = new StringBuilder(mCategoryList.get(position).getName());
        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        final String title = sb.toString().replace("Караганды","");
        holder.mTextView.setText(title);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long menuId = mCategoryList.get(position).getId();
                Intent intent = new Intent(mContext, OrganizationListActivity.class);
                intent.putExtra("category_id", menuId);
                intent.putExtra("title",title);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return mCategoryList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private CardView mCardView;
        private TextView mTextView;
        private ImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.menu_name);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_menu);
            mCardView = (CardView) itemView.findViewById(R.id.cv_menu);

        }
    }
}
