package lk.simplecode.kz.cityhunter.module.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lk.simplecode.kz.cityhunter.R;
import lk.simplecode.kz.cityhunter.model.Organization;
import lk.simplecode.kz.cityhunter.module.organizations.OrganizationListRecyplerAdapter;
import lk.simplecode.kz.cityhunter.network.RetrofitFacade;
import retrofit.Callback;
import retrofit.Response;

public class NewOrganizationFragment extends Fragment {
    private OrganizationListRecyplerAdapter mOrganizationListAdapter;
    private OrganizationListRecyplerAdapter mOrganizationAdapter;
    private List<Organization> mOrganizationList = new ArrayList<Organization>();
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private TextView mErrorMessage;
    private ImageView mRefresh;

    public static NewOrganizationFragment newInstance(int page) {

        NewOrganizationFragment fragment = new NewOrganizationFragment();
        Bundle args = new Bundle();
        args.putInt("num", page);
        fragment.setArguments(args);
        return fragment;
    }

    public NewOrganizationFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View result = inflater.inflate(R.layout.fragment_new_organization, container, false);
        mRecyclerView = (RecyclerView) result.findViewById(R.id.new_organization_rv);
        mProgressBar = (ProgressBar) result.findViewById(R.id.new_organization_pb);
        mErrorMessage = (TextView) result.findViewById(R.id.new_organization_error);
        mRefresh = (ImageView) result.findViewById(R.id.new_organization_refresh);
        mRefresh.setVisibility(View.GONE);
        mErrorMessage.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
        getData();
        return result;
    }
        public void getData(){
            RetrofitFacade.getInstance().getOrganization(null, "new", new Callback<List<Organization>>() {
                @Override
                public void onResponse(Response<List<Organization>> response) {
                    if (!response.isSuccess()) {
                        mProgressBar.setVisibility(View.VISIBLE);
                        mRefresh.setVisibility(View.GONE);
                        mErrorMessage.setVisibility(View.GONE);
                    }
                    if (response.isSuccess()) {
                        mRecyclerView.setVisibility(View.VISIBLE);
                        mRefresh.setVisibility(View.GONE);
                        mErrorMessage.setVisibility(View.GONE);
                        mOrganizationList.addAll(response.body());
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        mOrganizationAdapter = new OrganizationListRecyplerAdapter(getContext(), mOrganizationList);
                        mRecyclerView.setAdapter(mOrganizationAdapter);
                        mProgressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    mProgressBar.setVisibility(View.GONE);
                    mErrorMessage.setVisibility(View.VISIBLE);
                    mRefresh.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.GONE);
                    mRefresh.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mProgressBar.setVisibility(View.VISIBLE);
                            mErrorMessage.setVisibility(View.GONE);
                            mRefresh.setVisibility(View.GONE);
                            getData();
                        }
                    });
                    t.printStackTrace();
                }
            });
        }
}
