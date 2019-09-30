package com.mxlapps.app.gearspopguide.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.mxlapps.app.gearspopguide.Adapter.CustomPagerAdapter;
import com.mxlapps.app.gearspopguide.Model.NewsModel;
import com.mxlapps.app.gearspopguide.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    CardView pins, decks, clans, account;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            toolbar.setTitle("Gears POP Guide");
        }

        ArrayList<NewsModel> news_element = new ArrayList<>();
        for (int x = 0; x < 5; x++){

            NewsModel model = new NewsModel();
            model.setTitle("iygu");
            news_element.add(model);
        }

        ViewPager viewPager = findViewById(R.id.viewpager_news);
        CustomPagerAdapter heroDetailAdapter = new CustomPagerAdapter(getSupportFragmentManager(), news_element);
        viewPager.setOffscreenPageLimit(5);
        viewPager.setClipToPadding(false);
        viewPager.setPageMargin(0);

        viewPager.setAdapter(heroDetailAdapter);

        initViews();
        initEvents();



    }

    private void initEvents() {
        pins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PinListActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });


        decks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, DeckListActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            }
        });


        clans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PinListActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PinListActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            }
        });

    }

    private void initViews() {
        pins = findViewById(R.id.cardView_pins);
        decks = findViewById(R.id.cardView_decks);
        clans = findViewById(R.id.cardView_clans);
        account = findViewById(R.id.cardView_account);

        intent = new Intent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_settings) {
            Toast.makeText(MainActivity.this, "Action clicked", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
