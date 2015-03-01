package com.cs4261.androidclient;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.cs4261.androidclient.model.Places;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jibweb on 21/02/15.
 */
public class PlacesAdapter implements ListAdapter {

    final private static String TAG = "PlacesAdapter";

    private Context mContext;
    private List<Places> mPlacesList;
    private LayoutInflater mInflater;
    private List<DataSetObserver> mObservers;

    public PlacesAdapter(Context context, List<Places> objects) {
        this.mContext = context;
        this.mPlacesList = new ArrayList<Places>();
        this.mPlacesList.addAll(objects);
        this.mObservers = new ArrayList<DataSetObserver>();
        this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        mObservers.add(observer);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        mObservers.remove(observer);
    }

    @Override
    public int getCount() {
        return mPlacesList.size();
    }

    @Override
    public Object getItem(int position) {
        return mPlacesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Places place = mPlacesList.get(position);

        View viewElement = null;

        if (convertView == null) {
            viewElement = mInflater.inflate(R.layout.places_view, null);
        } else {
            viewElement = convertView;
        }

        TextView name = (TextView) viewElement.findViewById(R.id.place_name);
        TextView rating = (TextView) viewElement.findViewById(R.id.place_rating);
        TextView location = (TextView) viewElement.findViewById(R.id.place_location);
        TextView phone = (TextView) viewElement.findViewById(R.id.place_phone);
        final ImageView image = (ImageView) viewElement.findViewById(R.id.place_image);

        name.setText(place.getName());
        rating.setText(Float.toString(place.getRating()));
        location.setText(place.getLocation());
        phone.setText(place.getPhone());

        String url = place.getImage_url();
        int pixelSize = 200;
        if (!url.equals(mContext.getString(R.string.no_image_url))) {

            ImageLoader loader = RequestQueueSingleton.getInstance(mContext).getImageLoader();
            ImageLoader.ImageListener listener = ImageLoader.getImageListener(image, R.drawable.tb_default, R.drawable.tb_default);

            loader.get(url,listener, pixelSize, pixelSize, ImageView.ScaleType.CENTER_CROP
            );

        }

        return viewElement;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }
}
