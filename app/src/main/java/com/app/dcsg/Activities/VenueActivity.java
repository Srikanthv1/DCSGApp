package com.app.dcsg.Activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
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
 * This Class handles fetching the Location of User and retrieving & displaying
 * Store details based on the Web API response.
 * Created by Srikanth on 1/02/18
 */

public class VenueActivity extends AppCompatActivity implements LocationListener {

    private static final int LOCATION_UPDATE_INTERVAL = 10000; // In Milliseconds
    private static final int MINIMUM_DISTANCE_INTERVAL = 100; //In Meters
    private static final String IMAGE_NOT_FOUND = "Image Not Found";

    //Location Manager object
    LocationManager mLocationManager;
    //Location Provider used to keep track of GPS, Network Providers
    String mProvider;

    //Recycler View
    RecyclerView mVenueListView;
    //List of Venue Details
    List<VenueDetails> mVenueDetails;
    //Sorted List of Venue details based on current location
    List<VenueDetails> mSortedVenueDetails;
    //Location object
    Location location;

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
            } else {
                // Location cannot be retrieved
                Toast.makeText(getBaseContext(), R.string.location_not_found,
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            //GPS, Network Provider are not available
            Toast.makeText(getBaseContext(), R.string.no_provider_found, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This method initializes LocationManager object and triggers an Alert if the GPS is disabled
     */
    public void statusCheck() {
        final LocationManager manager =(LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        }
    }

    /**
     * This method will generate an Alert to ask User's consent for enabling the GPS
     * if the GPS has been disabled
     */
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

        //  Fetch the JSON response using Venue DataProvider on Success method
        VenueDataProvider venueDataProvider = new VenueDataProvider();
        venueDataProvider.fetchVenueDetails(new VenueDataListener() {
            @Override
            public void onSuccess(VenueResponseModel venueResponse) {

                List<Venue> venues = venueResponse.getVenues();
                //Create a Venue helper model to map the response contents of venueResponse.
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
                            venueDetails.setUrl(IMAGE_NOT_FOUND);
                        }
                        //Add the data to the List
                        mVenueDetails.add(venueDetails);
                    }
                }

                //Invoke the getCurrentLocation to capture the Current User Location
                if (location != null) {
                    getCurrentLocation(location);
                }
                //Invoke the Favorite functionality
                setFavoriteList();
            }

            /**
             *  Display the customized error message to User using toast message.
             * @param errorMessage Contains the error details
             */
            @Override
            public void onError(String errorMessage) {
                //The request to Fetch data was not successful.
                Toast.makeText(VenueActivity.this, errorMessage, Toast.LENGTH_LONG).show();

            }
        });
    }

    /**
     * This method is called as soon as User's location is changed
     * based on min. distance interval and min. time interval set.
     * @param currentLocation Location captured based on User's current position
     */
    @Override
    public void onLocationChanged(Location currentLocation) {
        // Passing Current Location in getCurrentLocation()
        getCurrentLocation(currentLocation);
    }

    /**
     * This method fetches current location and shares the details for sorting Venue data
     * @param currentLocation Location captured based on User's current position
     */
    public void getCurrentLocation(Location currentLocation) {
        //sort the Venue List based on Current Location
        mSortedVenueDetails = sortLocations(mVenueDetails, currentLocation.getLatitude(),
                currentLocation.getLongitude());
    }

    /**
     * This method Sorts the Venue Locations based on the current Geo-Location of User
     * @param venueLocations List of Venue Locations containing Latitude and Longitude
     * @param myLatitude User's current Latitude
     * @param myLongitude User's current Longitude
     * @return returns a List of sorted Venue Location
     */
    public static List<VenueDetails> sortLocations(
            List<VenueDetails> venueLocations, final double myLatitude, final double myLongitude) {
        Comparator comparator = new Comparator<VenueDetails>() {

            @Override
            public int compare(VenueDetails origin1, VenueDetails origin2) {
                float[] result1 = new float[3];
                Location.distanceBetween(myLatitude, myLongitude,
                        origin1.getLatitude(), origin1.getLongitude(), result1);
                Float distance1 = result1[0];

                float[] result2 = new float[3];
                Location.distanceBetween(myLatitude, myLongitude,
                        origin2.getLatitude(), origin2.getLongitude(), result2);
                Float distance2 = result2[0];

                return distance1.compareTo(distance2);
            }
        };

        Collections.sort(venueLocations, comparator);
        return venueLocations;
    }

    /**
     * This method is used to set the favorite store based on tapping the Favorite icon.
     */
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

    /**
     * This method is called when the provider is disabled
     * @param provider Provider can be either GPS / Network provider
     */
    @Override
    public void onProviderDisabled(String provider) {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions once more
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }
    }
}