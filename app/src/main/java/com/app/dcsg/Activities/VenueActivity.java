package com.app.dcsg.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.app.dcsg.Adapters.VenueListAdapter;
import com.app.dcsg.DataProvider.VenueDataProvider;
import com.app.dcsg.Listener.VenueDataListener;
import com.app.dcsg.Model.ResponseModel.Location;
import com.app.dcsg.Model.ResponseModel.Venue;
import com.app.dcsg.Model.ResponseModel.VenueResponseModel;
import com.app.dcsg.Model.VenueDetails;
import com.app.dcsg.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by srika on 1/31/2018.
 */

public class VenueActivity extends AppCompatActivity {

    private static final String TAG = "VenueActivity";

    RecyclerView venueListView;
    List<VenueDetails> mVenueDetails;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_venue_list);

        mVenueDetails = new ArrayList<>();

        venueListView = findViewById(R.id.recyclerView);
        venueListView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(VenueActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        venueListView.setLayoutManager(layoutManager);

    }

    @Override
    protected void onResume() {
        super.onResume();

        VenueDataProvider venueDataProvider = new VenueDataProvider();
        venueDataProvider.fetchVenueDetails(new VenueDataListener() {
            @Override
            public void onSuccess(VenueResponseModel venueResponse) {

                //VenueResponseModel responseModel = venueResponse;
                List<Venue> venues = venueResponse.getVenues();

                for (Venue venue : venues) {
                    //Log.e(TAG, venue.getName());
                    if(venue.getVerified()) {
                        VenueDetails venueDetails = new VenueDetails();
                        venueDetails.setName(venue.getName());
                        venueDetails.setRating(venue.getRating());
                        venueDetails.setAddress(venue.getLocation().getAddress());
                        venueDetails.setCity(venue.getLocation().getCity());
                        mVenueDetails.add(venueDetails);
                    }else{
                        continue;
                    }
                }

                venueListView.setAdapter(new VenueListAdapter(VenueActivity.this, mVenueDetails));
            }

            @Override
            public void onError(String errorMessage) {

                Toast.makeText(VenueActivity.this, errorMessage, Toast.LENGTH_LONG).show();

            }
        });
    }
}
