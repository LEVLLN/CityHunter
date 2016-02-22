package lk.simplecode.kz.cityhunter.module.finder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import lk.simplecode.kz.cityhunter.R;
import lk.simplecode.kz.cityhunter.model.Organization;
import lk.simplecode.kz.cityhunter.module.organizations.OrganizationListRecyplerAdapter;
import lk.simplecode.kz.cityhunter.network.RetrofitFacade;
import retrofit.Callback;
import retrofit.Response;

public class SearchEngineActivity extends AppCompatActivity {
    @Bind(R.id.search_recycler)
    RecyclerView mRecyclerView;
    @Bind(R.id.search_pb)
    ProgressBar mProgressBar;
    @Bind(R.id.search_engine_toolbar)
    Toolbar mToolbar;
    @Bind(R.id.search_not_found)
    TextView mNotFoundTv;
    @Bind(R.id.search_refresh)
    ImageView mRefresh;
    @Bind(R.id.search_error)
    TextView mError;
    @Bind(R.id.search_by_title)
    EditText mSearchTitleEv;
//    @Bind(R.id.search_find_go)
//    TextView mFindGoTv;

    private RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
    private OrganizationListRecyplerAdapter mOrganizationAdapter;
    private List<Organization> organizationList = new ArrayList<Organization>();
    private List<Organization> foundOrganizations = new ArrayList<Organization>();
    private String[] mCategories;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_engine);
        ButterKnife.bind(this);
        mToolbar.setTitle(getString(R.string.searching));
        setSupportActionBar(mToolbar);
        mRecyclerView.setVisibility(View.GONE);
        mError.setVisibility(View.GONE);
        mNotFoundTv.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
        mRefresh.setVisibility(View.GONE);
        getCategories();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mSearchTitleEv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getRecyclerView();
            }

            @Override
            public void afterTextChanged(Editable s) {
                getRecyclerView();
            }
        });
    }


    public void getCategories() {
        RetrofitFacade.getInstance().getOrganization(null, "", new Callback<List<Organization>>() {


            @Override
            public void onResponse(Response<List<Organization>> response) {
                if (response.isSuccess()) {
                    mProgressBar.setVisibility(View.GONE);
                    mRefresh.setVisibility(View.GONE);
                    mError.setVisibility(View.GONE);
                    organizationList = response.body();
                    getRecyclerView();
                }
                if (!response.isSuccess()) {
                    mProgressBar.setVisibility(View.VISIBLE);
                    mRefresh.setVisibility(View.GONE);
                    mError.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                mNotFoundTv.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.GONE);
                mError.setVisibility(View.VISIBLE);
                mRefresh.setVisibility(View.VISIBLE);
                mRefresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mProgressBar.setVisibility(View.VISIBLE);
                        mError.setVisibility(View.GONE);
                        mRefresh.setVisibility(View.GONE);
                        getCategories();
                    }
                });
                t.printStackTrace();
            }
        });
    }

    public void findByTitle(String pattern) {

        for (Organization k : organizationList) {
            if (pattern != "") {
                //  Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
                //   Pattern patternLatin = Pattern.compile(doKirillToLatin(pattern), Pattern.CASE_INSENSITIVE);
                //  Pattern patternKirill = Pattern.compile(doLatinToKirill(pattern), Pattern.CASE_INSENSITIVE);

                if (k.getTitle() != null) {
                    //  Matcher matcher = p.matcher(Html.fromHtml(k.getTitle()));
                    //    Matcher matcher_latin = patternLatin.matcher(Html.fromHtml(k.getTitle()));
                    //   Matcher matcher_kirill = patternKirill.matcher(Html.fromHtml(k.getTitle()));
//                if (matcher.matches() || matcher_latin.matches()||matcher_kirill.matches()) {
                    boolean isFind = k.getTitle().toLowerCase().contains(pattern.toLowerCase());
                    boolean isFindLatin = k.getTitle().toLowerCase().contains(doKirillToLatin(pattern));
                    boolean isFindKirill = k.getTitle().toLowerCase().contains(doLatinToKirill(pattern));
                    if (isFind || isFindLatin || isFindKirill) {
                        //    Log.i("TRUE", k.getTitle() + " " + k.getAddress());
                        foundOrganizations.add(k);
                    }
                }
            }

        }


    }

    public void findByAddress(String pattern) {

        for (Organization z : organizationList) {
            if (pattern != "" || pattern.toCharArray().length >= 1) {
                //    Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
                //     Pattern patternLatin = Pattern.compile(doKirillToLatin(pattern), Pattern.CASE_INSENSITIVE);
                //   Pattern patternKirill = Pattern.compile(doLatinToKirill(pattern), Pattern.CASE_INSENSITIVE);

                if (z.getAddress() != null) {
                    boolean isFind = z.getAddress().toLowerCase().contains(pattern.toLowerCase());
                    boolean isFindLatin = z.getAddress().toLowerCase().contains(doKirillToLatin(pattern));
                    boolean isFindKirill = z.getAddress().toLowerCase().contains(doLatinToKirill(pattern));
                    //     Matcher matcher = p.matcher(Html.fromHtml(z.getAddress()));
                    //     Matcher matcher_latin = patternLatin.matcher(Html.fromHtml(z.getAddress()));
                    //      Matcher matcher_kirill = patternKirill.matcher(Html.fromHtml(z.getAddress()));
                    if (z.getAddress().toLowerCase().contains(pattern.toLowerCase()) || z.getAddress().toLowerCase().contains(doKirillToLatin(pattern))) {
                        //  Log.i("TRUE", z.getTitle() + " " + z.getAddress());
                        foundOrganizations.add(z);
                    }
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getRecyclerView() {

        foundOrganizations.clear();
        if (mSearchTitleEv.getText() != null) {
            findByTitle(String.valueOf(mSearchTitleEv.getText()));
            findByAddress(String.valueOf(mSearchTitleEv.getText()));
            if (!foundOrganizations.isEmpty()) {
                mNotFoundTv.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mOrganizationAdapter = new OrganizationListRecyplerAdapter(SearchEngineActivity.this, foundOrganizations);
                mRecyclerView.setAdapter(mOrganizationAdapter);
            } else if (foundOrganizations.isEmpty()) {
                mNotFoundTv.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
            }
        }
        //  Log.i("FOUND", foundOrganizations.toString());
    }

    public String doKirillToLatin(String in) {
        String ALPHA_RUS = new String("абвгдеёжзиыйклмнопрстуфхцчшщьэюя");
        String[] ALPHA_EN = {"a", "b", "v", "g", "d", "e", "yo", "g", "z", "i", "y", "i",
                "k", "l", "m", "n", "o", "p", "r", "s", "t", "u",
                "f", "h", "ts", "ch", "sh", "sh'", "'", "e", "yu", "ya"};
        in = in.toLowerCase();
        StringBuffer latinString = new StringBuffer("");
        char[] check = in.toCharArray();

        for (int i = 0; i < check.length; i++) {
            int j = ALPHA_RUS.indexOf(check[i]);
            if (j != -1) {
                latinString.append(ALPHA_EN[j]);
            } else {
                latinString.append(check[i]);
            }
        }
        String strLatin = String.valueOf(latinString);
        return strLatin;
    }

    public String doLatinToKirill(String in) {
        String ALPHA_EN = new String("abcdefghijklmnopqrstuvwxyz");
        String[] ALPHA_RUS = {"а", "б", "ц", "д", "е", "ф", "г", "х", "и", "ж", "к", "л",
                "м", "н", "о", "п", "ку", "р", "с", "т", "у", "в",
                "в", "кс", "ы", "з"};
        in = in.toLowerCase();
        StringBuffer kirillSB = new StringBuffer("");
        char[] check = in.toCharArray();

        for (int i = 0; i < check.length; i++) {
            int j = ALPHA_EN.indexOf(check[i]);
            if (j != -1) {
                kirillSB.append(ALPHA_RUS[j]);
            } else {
                kirillSB.append(check[i]);
            }
        }
        String strKirill = String.valueOf(kirillSB);
        return strKirill;
    }
}
