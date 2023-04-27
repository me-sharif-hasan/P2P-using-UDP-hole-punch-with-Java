package com.company;

public class IpCollectorSingleton {
    private IpCollectorSingleton(){}
    public void beginIpUpdate(){
        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }).start();
    }

    public String getIp(String id){
        return null;
    }
    public String getPort(String id){
        return null;
    }

    private static IpCollectorSingleton instance;
    public static IpCollectorSingleton getInstance(){
        if(instance==null) instance=new IpCollectorSingleton();
        return instance;
    }
}
