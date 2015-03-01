package com.cs4261.androidclient;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;


public class MainActivity extends ActionBarActivity {

    final private static String TAG = "MainActivity";

    ImageButton barsBtn;
    ImageButton clubsBtn;
    ImageButton cafesBtn;
    ImageButton restaurantsBtn;

    CharSequence location_entered  = "";

    SearchView loc_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        barsBtn = (ImageButton) findViewById(R.id.bars);
        clubsBtn = (ImageButton) findViewById(R.id.clubs);
        cafesBtn = (ImageButton) findViewById(R.id.cafes);
        restaurantsBtn = (ImageButton) findViewById(R.id.restaurants);

        loc_search = (SearchView) findViewById(R.id.searchView);

        barsBtn.setOnClickListener(onButtonClick);
        clubsBtn.setOnClickListener(onButtonClick);
        cafesBtn.setOnClickListener(onButtonClick);
        restaurantsBtn.setOnClickListener(onButtonClick);

        location_entered = loc_search.getQuery();
    }

    final private View.OnClickListener onButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            Intent i = new Intent(MainActivity.this, PlacesActivity.class);

            switch (id) {
                case R.id.bars:
                    i.putExtra("category","bars");
                    break;
                case R.id.clubs:
                    i.putExtra("category","danceclubs");
                    break;
                case R.id.cafes:
                    i.putExtra("category","cafes");
                    break;
                case R.id.restaurants:
                    i.putExtra("category","restaurants");
                    break;
            }

            i.putExtra("location",location_entered);

            startActivity(i);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
