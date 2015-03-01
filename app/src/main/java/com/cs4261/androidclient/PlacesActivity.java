package com.cs4261.androidclient;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cs4261.androidclient.model.Places;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PlacesActivity extends ListActivity {

    final private static String TAG = "PlacesActivity";
    final public static String PLACES_BASE_URL = "https://agile-tor-1071.herokuapp.com/places";

    private String mCategory;
    private List<Places> mPlacesList = new ArrayList<>();
    Map<String, String> params = new HashMap<>();

    //partie ajoutee
    private String mLocation;
    private String mGPS;

    ListView myListView;

    GPSTracker gps;

    double latitude;
    double longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        mCategory = getIntent().getStringExtra("category");

        mLocation = getIntent().getStringExtra("location");

       //partie ajoutee
        myListView = (ListView) findViewById(android.R.id.list);
        myListView.setOnItemClickListener(onListClick);

    }

    //partie ajoutee
    private AdapterView.OnItemClickListener onListClick = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent,
                                View view, int position,
                                long id)
        {
            //String place = String.valueOf(parent.getItemAtPosition(position));
            Places place = (Places) parent.getItemAtPosition(position);
            //create intent
            Intent i  = new Intent(PlacesActivity.this, TwitterPlacesActivity.class);
            i.putExtra("id_value", String.valueOf(position));
            i.putExtra("place_name",place.getName());
            i.putExtra("place_location",place.getLocation());
            i.putExtra("place_phone",place.getPhone());
            i.putExtra("place_img_url",place.getImage_url());
            i.putExtra("place_rating",Float.toString(place.getRating()));

            startActivity(i);
        }
    };

    @Override
    protected void onStart() {
        super.onStart();

        //if (mLocation == "metz"){
            //partie ajoutee
            gps = new GPSTracker(PlacesActivity.this);
            if(gps.canGetLocation()){
                latitude = gps.getLatitude();
                longitude = gps.getLongitude();

                Toast.makeText(PlacesActivity.this,"your location is -\nLat: "+latitude+"\nLong"+longitude,Toast.LENGTH_LONG).show();
            }
            String lat_s = Double.toString(latitude);
            String long_s = Double.toString(longitude);
            params.put("loc",lat_s+","+long_s);
            //params.put("loc","49.0,6.10");//Metz location
        //}
        /*else{
            //do the conversion from city name to GPS value
            //mGPS = conversion(mLocation);
            //params.put("loc",mGPS);
            params.put("loc","48.51,2.21");//Paris location
        }*/



        params.put("section", mCategory);

        String url = PLACES_BASE_URL + "?" + RequestQueueSingleton.getEncodedParams(params);
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mPlacesList = PlacesParser.parseFeed(response, PlacesActivity.this);
                        PlacesAdapter adapter = new PlacesAdapter(PlacesActivity.this, mPlacesList);
                        getListView().setAdapter(adapter);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.getMessage());
                    }
                }
        );

        RequestQueueSingleton.getInstance(this).addToRequestQueue(req);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_places, menu);
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
