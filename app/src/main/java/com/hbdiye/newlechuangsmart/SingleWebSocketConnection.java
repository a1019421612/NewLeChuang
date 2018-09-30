package com.hbdiye.newlechuangsmart;

import de.tavendo.autobahn.WebSocketConnection;

public class SingleWebSocketConnection {
    private static WebSocketConnection mConnection;
    public static WebSocketConnection getInstance(){
        if (mConnection==null){
            mConnection=new WebSocketConnection();
        }
        return mConnection;
    }

}
