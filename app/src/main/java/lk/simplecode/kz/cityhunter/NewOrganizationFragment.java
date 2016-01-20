package lk.simplecode.kz.cityhunter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import lk.simplecode.kz.cityhunter.model.Organization;
import lk.simplecode.kz.cityhunter.network.RetrofitFacade;
import retrofit.Callback;
import retrofit.Response;

public class NewOrganizationFragment extends Fragment {
    private OrganizationListRecyplerAdapter mOrganizationListAdapter;
    private OrganizationListRecyplerAdapter mOrganizationAdapter;
    private List<Organization> mOrganizationList = new ArrayList<Organization>();

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
        final RecyclerView recyclerView;
        recyclerView = (RecyclerView) result.findViewById(R.id.new_organization_rv);
        final ProgressBar firstBar;
        firstBar = (ProgressBar) result.findViewById(R.id.new_organization_pb);
        RetrofitFacade.getInstance().getOrganization(null, "new", new Callback<List<Organization>>() {

            @Override
            public void onResponse(Response<List<Organization>> response) {
                if (!response.isSuccess()) {
                    firstBar.setVisibility(View.VISIBLE);
                }
                if (response.isSuccess()) {

                    mOrganizationList.addAll(response.body());
                    recyclerView.setLayoutManager(new LinearLayoutManager(result.getContext()));
                    mOrganizationAdapter = new OrganizationListRecyplerAdapter(result.getContext(), mOrganizationList);
                    recyclerView.setAdapter(mOrganizationAdapter);
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
