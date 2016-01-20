package lk.simplecode.kz.cityhunter;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import lk.simplecode.kz.cityhunter.model.Category;
import lk.simplecode.kz.cityhunter.network.RetrofitFacade;
import retrofit.Callback;
import retrofit.Response;

public class CategoryFragment extends Fragment {
    private List<Category> mCategoryList = new ArrayList<Category>();
    private CategoryRecyplerAdapter mCategoryRecyplerAdapter;

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

        final View result = inflater.inflate(R.layout.fragment_category, container, false);
        final RecyclerView recyclerView;

        recyclerView = (RecyclerView) result.findViewById(R.id.main_activitity_menu_recypler_view);
        final ProgressBar firstBar;
        firstBar = (ProgressBar) result.findViewById(R.id.progressBar);
        RetrofitFacade.getInstance().getMenu(new Callback<List<Category>>() {
            @Override
            public void onResponse(Response<List<Category>> response) {
                if (!response.isSuccess()) {
                    firstBar.setVisibility(View.VISIBLE);
                }
                if (response.isSuccess()) {
                    mCategoryList.addAll(response.body());
                    recyclerView.setLayoutManager(new LinearLayoutManager(result.getContext()));
                    mCategoryRecyplerAdapter = new CategoryRecyplerAdapter(result.getContext(), mCategoryList);
                    recyclerView.setAdapter(mCategoryRecyplerAdapter);
                    firstBar.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Throwable t) {
                firstBar.setVisibility(View.VISIBLE);
                t.printStackTrace();
            }
        });

        return result;
    }
}