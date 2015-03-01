package com.cs4261.androidclient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class TwitterPlacesActivity extends ActionBarActivity {

    final private static String TAG = "TwitterPlacesActivity";
    final public static String MEDIAS_BASE_URL = "https://agile-tor-1071.herokuapp.com/places";

    //String mIdValue;
    String mPlaceName;
    String mPlaceLocation;
    String mPlacePhone;
    String mPlaceImgUrl;
    String mPlaceRating;

    //private TextView idTextView;
    private TextView PlaceNameView;
    private TextView PlaceLocationView;
    private TextView PlacePhoneView;
    private ImageView PlaceImgUrlView;
    private TextView PlaceRatingView;

    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter_places);

        //get the id value from upper activity
        //mIdValue = getIntent().getStringExtra("id_value");
        mPlaceName = getIntent().getStringExtra("place_name");
        mPlaceLocation = getIntent().getStringExtra("place_location");
        mPlacePhone = getIntent().getStringExtra("place_phone");
        mPlaceImgUrl = getIntent().getStringExtra("place_img_url");
        mPlaceRating = getIntent().getStringExtra("place_rating");

        //idTextView = (TextView)findViewById(R.id.text);
        PlaceNameView = (TextView)findViewById(R.id.name);
        PlaceLocationView = (TextView)findViewById(R.id.location);
        PlacePhoneView = (TextView)findViewById(R.id.phone);
        PlaceRatingView = (TextView)findViewById(R.id.rating);
        PlaceImgUrlView = (ImageView)findViewById(R.id.image);

        //idTextView.setText("This is the id value get from the list =" + mIdValue);
        PlaceNameView.setText(mPlaceName);
        PlaceLocationView.setText(mPlaceLocation);
        PlacePhoneView.setText(mPlacePhone);
        PlaceRatingView.setText(mPlaceRating);

        /*bitmap = getBitmapFromURL(mPlaceImgUrl);
        PlaceImgUrlView.setImageBitmap(bitmap);*/

        String url = mPlaceImgUrl;
        int pixelSize = 200;
        if (!url.equals(TwitterPlacesActivity.this.getString(R.string.no_image_url))) {

            ImageLoader loader = RequestQueueSingleton.getInstance(TwitterPlacesActivity.this).getImageLoader();
            ImageLoader.ImageListener listener = ImageLoader.getImageListener(PlaceImgUrlView, R.drawable.tb_default, R.drawable.tb_default);

            loader.get(url,listener, pixelSize, pixelSize, ImageView.ScaleType.CENTER_CROP
            );

        }


    }

    public Bitmap getBitmapFromURL(String src){
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_twitter_places, menu);
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
