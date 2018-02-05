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

public class MainActivity extends AppCompatActivity {

    ConnectivityChecker mConnectivityChecker;

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
                    //Call the Activity to display Store Information
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
