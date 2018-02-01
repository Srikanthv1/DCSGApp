package com.app.dcsg.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        mConnectivityChecker = new NetworkConnectivityCheck((ConnectivityManager) MainActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE));
        dStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mConnectivityChecker.isConnected()){

                    Intent venueIntent = new Intent(MainActivity.this, VenueActivity.class);
                    startActivity(venueIntent);

                }else{
                    Toast.makeText(MainActivity.this, "Network not Available. Please check the connection", Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}
