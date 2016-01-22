package lk.simplecode.kz.cityhunter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lk.simplecode.kz.cityhunter.model.Category;
import lk.simplecode.kz.cityhunter.network.RetrofitFacade;
import retrofit.Callback;
import retrofit.Response;

public class CategoryFragment extends Fragment {
    private List<Category> mCategoryList = new ArrayList<Category>();
    private CategoryRecyplerAdapter mCategoryRecyplerAdapter;
    private View mResult;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private TextView mErrorTv;

    public static CategoryFragment newInstance(int page) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putInt("num", page);
        fragment.setArguments(args);
        return fragment;
    }

    public CategoryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int pageNumber = getArguments() != null ? getArguments().getInt("num") : 1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mResult = inflater.inflate(R.layout.fragment_category, container, false);

        mRecyclerView = (RecyclerView) mResult.findViewById(R.id.main_activitity_menu_recypler_view);
        mProgressBar = (ProgressBar) mResult.findViewById(R.id.progressBar);
        mErrorTv = (TextView) mResult.findViewById(R.id.category_error);
        mErrorTv.setVisibility(View.GONE);


        RetrofitFacade.getInstance().getMenu(new Callback<List<Category>>() {
            @Override
            public void onResponse(Response<List<Category>> response) {
                if (!response.isSuccess()) {
                    mProgressBar.setVisibility(View.VISIBLE);
                }
                if (response.isSuccess()) {
                    mCategoryList.addAll(response.body());
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(mResult.getContext()));
                    mCategoryRecyplerAdapter = new CategoryRecyplerAdapter(mResult.getContext(), mCategoryList);
                    mRecyclerView.setAdapter(mCategoryRecyplerAdapter);
                    mProgressBar.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Throwable t) {
                mProgressBar.setVisibility(View.GONE);
                mErrorTv.setVisibility(View.VISIBLE);
                t.printStackTrace();
            }
        });

        return mResult;
    }
}