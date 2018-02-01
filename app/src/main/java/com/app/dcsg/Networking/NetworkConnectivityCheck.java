package com.app.dcsg.Networking;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by srika on 1/31/2018.
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
