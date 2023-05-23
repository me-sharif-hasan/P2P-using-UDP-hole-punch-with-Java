package com.company.stun;

import com.company.Settings;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class PrimaryServerSingleton {
    private final String baseUrl="https://testproject.iishanto.com/";
    private PrimaryServerSingleton(){

    }

    public void updateIp(String ip_port,String id) throws IOException {
        String target=baseUrl+"?id="+id+"&ext="+ip_port;
        URL url=new URL(target);
        HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
        httpURLConnection.connect();
        httpURLConnection.getInputStream();
        httpURLConnection.disconnect();
    }
    public String getIp(String friend_id) throws IOException {
        String target=baseUrl+"?get="+friend_id;
        URL url=new URL(target);
        HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
        httpURLConnection.connect();
        byte []buff=new byte[1024];
        int len=httpURLConnection.getInputStream().read(buff);
        String ip_port=new String(buff,0,len);
        httpURLConnection.getInputStream();
        httpURLConnection.disconnect();
        return ip_port;
    }

    public boolean handshakeWith(String id) throws Exception{
        String target=baseUrl+"handshake/?from="+ Settings.USERNAME+"&to="+id;
        URL url=new URL(target);
        HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
        httpURLConnection.connect();
        byte []buff=new byte[1024];
        int len=httpURLConnection.getInputStream().read(buff);
        String msg=new String(buff,0,len);
        httpURLConnection.getInputStream();
        httpURLConnection.disconnect();
        if(msg.equals("handshake_marked")){
            return true;
        }
        throw new Exception("Error in handshake");
    }

    private static PrimaryServerSingleton instance;
    public static PrimaryServerSingleton getInstance() {
        if(instance==null) instance=new PrimaryServerSingleton();
        return instance;
    }
}
