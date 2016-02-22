package lk.simplecode.kz.cityhunter.module.main;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import lk.simplecode.kz.cityhunter.R;
import lk.simplecode.kz.cityhunter.module.finder.SearchEngineActivity;

//recipler view
//html
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.pagerTitleStrip);
        setSupportActionBar(toolbar);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        pager.setAdapter(new PagerAdapterFragment(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(pager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.search_item){
            Intent intent = new Intent(this, SearchEngineActivity.class);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }
}
