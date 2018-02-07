package com.app.dcsg.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.app.dcsg.Networking.ConnectivityChecker;
import com.app.dcsg.Networking.NetworkConnectivityCheck;
import com.app.dcsg.R;

/**
 * This is the Main Activity which loads the UI components for displaying the Logo
 * and Button (Store Locator) through which Store details can be accessed.
 * Created by Srikanth on 1/02/18
 */
public class MainActivity extends AppCompatActivity {

    //Interface to check if the Network is available
    ConnectivityChecker mConnectivityChecker;

    /**
     * This method is the starting point of the project where the User is displayed
     * with a Store locator button. This method also check for Network connectivity and
     * alerts the User if the device has no network connectivity.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button dStore = findViewById(R.id.storeButton);
        //Initialize the Network Connectivity check
        mConnectivityChecker = new NetworkConnectivityCheck(
                (ConnectivityManager) MainActivity.this.
                        getSystemService(Context.CONNECTIVITY_SERVICE));
        dStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mConnectivityChecker.isConnected()){
                    //Network is Available
                    Intent venueIntent = new Intent(MainActivity.this,
                            VenueActivity.class);
                    //Call the Venue Activity to display Store Information on
                    // tapping the Button - Store Locator
                    startActivity(venueIntent);

                }else{
                    //Network not Found
                    Toast.makeText(MainActivity.this,
                            R.string.no_network_found,
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}