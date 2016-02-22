package lk.simplecode.kz.cityhunter.module.main;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import lk.simplecode.kz.cityhunter.module.organizations.OrganizationListActivity;
import lk.simplecode.kz.cityhunter.R;
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
        boolean isRestaurant = mCategoryList.get(position).getName().contains("Рестораны");
        boolean isBar = mCategoryList.get(position).getName().contains("Пабы бары");
        boolean isCafe = mCategoryList.get(position).getName().contains("Кафе");
        boolean isBanquet = mCategoryList.get(position).getName().contains("Банкетные залы");
        boolean isBath = mCategoryList.get(position).getName().contains("Бани сауны");
        boolean isClub = mCategoryList.get(position).getName().contains("Ночные клубы караоке");
        boolean isBowling = mCategoryList.get(position).getName().contains("Бильярд боулинг");
        boolean isHotel = mCategoryList.get(position).getName().contains("Гостиницы");
        boolean isRecreaction = mCategoryList.get(position).getName().contains("Активный отдых");
        boolean isBeauty = mCategoryList.get(position).getName().contains("здоровье");

        StringBuilder sb = new StringBuilder(mCategoryList.get(position).getName());
        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        final String title = sb.toString().replace("Караганды", "");
        holder.mTextView.setText(title);
        if(isRestaurant){getIcon(R.drawable.restaurant,holder.mImageView);}
        if(isBar){getIcon(R.drawable.beer,holder.mImageView);}
        if(isCafe){getIcon(R.drawable.cafe,holder.mImageView);}
        if(isBanquet){getIcon(R.drawable.banquet,holder.mImageView);}
        if(isBath){getIcon(R.drawable.bath,holder.mImageView);}
        if(isClub){getIcon(R.drawable.club,holder.mImageView);}
        if(isBowling){getIcon(R.drawable.bowling,holder.mImageView);}
        if(isHotel){getIcon(R.drawable.hotel,holder.mImageView);}
        if(isRecreaction){getIcon(R.drawable.recreation,holder.mImageView);}
        if(isBeauty){getIcon(R.drawable.beauty,holder.mImageView);}

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long menuId = mCategoryList.get(position).getId();
                Intent intent = new Intent(mContext, OrganizationListActivity.class);
                intent.putExtra("category_id", menuId);
                intent.putExtra("title", title);
                mContext.startActivity(intent);
            }
        });
    }
    public void getIcon(int icon,ImageView holder){
        Picasso.with(mContext)
                .load(icon)
                .into(holder);
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
