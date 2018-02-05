package com.app.dcsg.Activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.app.dcsg.Adapters.VenueListAdapter;
import com.app.dcsg.DataProvider.VenueDataProvider;
import com.app.dcsg.Listener.VenueDataListener;
import com.app.dcsg.Model.ResponseModel.Venue;
import com.app.dcsg.Model.ResponseModel.VenueResponseModel;
import com.app.dcsg.Model.VenueDetails;
import com.app.dcsg.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Srikanth
 */

public class VenueActivity extends AppCompatActivity implements LocationListener {

    private static final String TAG = "VenueActivity";
    public static final int LOCATION_UPDATE_INTERVAL = 10000; // In Milliseconds
    public static final int MINIMUM_DISTANCE_INTERVAL = 100; //In Meters

    LocationManager mLocationManager;
    String mProvider;

    RecyclerView mVenueListView;
    List<VenueDetails> mVenueDetails;
    List<VenueDetails> mSortedVenueDetails;

    android.location.Location location;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_venue_list);

        mVenueDetails = new ArrayList<>();
        mSortedVenueDetails = new ArrayList<>();

        mVenueListView = findViewById(R.id.recyclerView);
        mVenueListView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(VenueActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mVenueListView.setLayoutManager(layoutManager);
        //venueListView.setOverScrollMode(View.OVER_SCROLL_IF_CONTENT_SCROLLS);

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request Permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }
        // Check if the LocationManager object is set
        statusCheck();
        //else set the LocationManager object
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Creating a criteria object
        Criteria criteria = new Criteria();

        // Getting the name of the provider that meets the criteria
        mProvider = mLocationManager.getBestProvider(criteria, false);

        if (mProvider != null && !mProvider.equals("")) {
            if (!mProvider.contains("gps")) {
                // Gps is Disabled
                final Intent notifyUser = new Intent();
                notifyUser.setClassName("com.android.settings",
                        "com.android.settings.widget.SettingsAppWidgetProvider");
                notifyUser.addCategory(Intent.CATEGORY_ALTERNATIVE);
                notifyUser.setData(Uri.parse("3"));
                sendBroadcast(notifyUser);
            }
            // Get the location from the given provider
            location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    LOCATION_UPDATE_INTERVAL, MINIMUM_DISTANCE_INTERVAL, this);

            if (location != null) {
                onLocationChanged(location);
            } else {
                location = mLocationManager.getLastKnownLocation(mProvider);
            }
            if (location != null) {
                onLocationChanged(location);
            } else {  // If the Location is still Null
                Toast.makeText(getBaseContext(), R.string.location_not_found,
                        Toast.LENGTH_SHORT).show();
            }
        } else { //GPS, Cellular and WiFi are not available
            Toast.makeText(getBaseContext(), R.string.no_provider_found, Toast.LENGTH_SHORT).show();
        }

    }

    public void statusCheck() {
        final LocationManager manager =(LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(
                R.string.gps_disabled)
                .setCancelable(false).setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(
                                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        VenueDataProvider venueDataProvider = new VenueDataProvider();
        venueDataProvider.fetchVenueDetails(new VenueDataListener() {
            @Override
            public void onSuccess(VenueResponseModel venueResponse) {

                //Fetch the JSON response using Retrofit2
                List<Venue> venues = venueResponse.getVenues();

                for (Venue venue : venues) {
                    if (venue.getVerified()) {
                        VenueDetails venueDetails = new VenueDetails();
                        venueDetails.setName(venue.getName());
                        venueDetails.setRating(venue.getRating());
                        venueDetails.setAddress(venue.getLocation().getAddress());
                        venueDetails.setCity(venue.getLocation().getCity());
                        venueDetails.setLatitude(venue.getLocation().getLatitude());
                        venueDetails.setLongitude(venue.getLocation().getLongitude());
                        if (venue.getPhotos().size() > 0) {
                            venueDetails.setUrl(venue.getPhotos().get(0).getUrl());
                        } else {
                            venueDetails.setUrl("Image Not Found");
                        }
                        //Add the data to the List
                        mVenueDetails.add(venueDetails);
                    }
                }

                if (location != null) {
                    getCurrentLocation(location);
                }
                //Invoke the Favorite functionality
                setFavoriteList();
            }

            @Override
            public void onError(String errorMessage) {

                Toast.makeText(VenueActivity.this, errorMessage, Toast.LENGTH_LONG).show();

            }
        });
    }


    @Override
    public void onLocationChanged(android.location.Location currentLocation) {
        // Passing Current Location in getCurrentLocation()
        getCurrentLocation(currentLocation);
    }

    public void getCurrentLocation(android.location.Location currentLocation) {
        //sort the Venue List based on Current Location
        mSortedVenueDetails = sortLocations(mVenueDetails, currentLocation.getLatitude(),
                currentLocation.getLongitude());
    }

    public static List<VenueDetails> sortLocations(
            List<VenueDetails> venueLocations, final double myLatitude, final double myLongitude) {
        Comparator comparator = new Comparator<VenueDetails>() {

            @Override
            public int compare(VenueDetails origin1, VenueDetails origin2) {
                float[] result1 = new float[3];
                android.location.Location.distanceBetween(myLatitude, myLongitude,
                        origin1.getLatitude(), origin1.getLongitude(), result1);
                Float distance1 = result1[0];

                float[] result2 = new float[3];
                android.location.Location.distanceBetween(myLatitude, myLongitude,
                        origin2.getLatitude(), origin2.getLongitude(), result2);
                Float distance2 = result2[0];

                return distance1.compareTo(distance2);
            }
        };

        Collections.sort(venueLocations, comparator);
        return venueLocations;
    }

    private void setFavoriteList() {

        mVenueListView.setAdapter(new VenueListAdapter(VenueActivity.this,
                mSortedVenueDetails, new VenueListAdapter.VenueViewHolder.IOnFavoriteSelection() {
            @Override
            public void setFavoriteItem(int position) {

                //Save the Data
                VenueDetails venueObject = new VenueDetails();
                venueObject.setName(mVenueDetails.get(position).getName());
                venueObject.setAddress(mVenueDetails.get(position).getAddress());
                venueObject.setCity(mVenueDetails.get(position).getCity());
                venueObject.setRating(mVenueDetails.get(position).getRating());
                venueObject.setLatitude(mVenueDetails.get(position).getLatitude());
                venueObject.setLongitude(mVenueDetails.get(position).getLongitude());
                venueObject.setUrl(mVenueDetails.get(position).getUrl());
                //Remove the item for current position
                mVenueDetails.remove(position);
                //Add the item to first position
                mVenueDetails.add(0, venueObject);
                mVenueListView.getAdapter().notifyDataSetChanged();
            }
        }));

    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions once more
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}