package lk.simplecode.kz.cityhunter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import lk.simplecode.kz.cityhunter.model.DetailedOrganization;
import lk.simplecode.kz.cityhunter.network.RetrofitFacade;
import retrofit.Callback;
import retrofit.Response;

public class DetailedOrganizationActivity extends AppCompatActivity {
    private TextView mTextView;
    private DetailedOrganization mDetailedOrganization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_organization);
        mTextView = (TextView) findViewById(R.id.detail_title);
        Intent intent = getIntent();
        long menuId = intent.getLongExtra("post_id", -1l);
        RetrofitFacade.getInstance().getDetailedOrganization(menuId, new Callback<DetailedOrganization>() {
            @Override
            public void onResponse(Response<DetailedOrganization> response) {
                mDetailedOrganization = response.body();
                Log.i("detail", mDetailedOrganization.toString());
                mTextView.setText(Html.fromHtml(mDetailedOrganization.getTitle()));
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
