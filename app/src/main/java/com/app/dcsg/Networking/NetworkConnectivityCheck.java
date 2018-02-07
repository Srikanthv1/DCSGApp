package com.app.dcsg.Networking;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Srikanth on 1/02/18
 */

public class NetworkConnectivityCheck implements ConnectivityChecker{

    private final ConnectivityManager mConMgr;

    public NetworkConnectivityCheck(ConnectivityManager mConMgr) {
        this.mConMgr = mConMgr;
    }

    @Override
    public boolean isConnected() {

        boolean response = false;
        NetworkInfo activeNetwork = mConMgr.getActiveNetworkInfo();
        if(activeNetwork != null && activeNetwork.isConnected()){
            response = true;
        }
        return response;
    }
}
