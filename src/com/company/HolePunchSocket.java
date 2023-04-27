package com.company;

import com.company.stun.HolePunchSocketUtilSingleton;
import com.company.stun.PrimaryServerSingleton;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

public class HolePunchSocket {
    private final String target;
    private final InputStream is;
    private final OutputStream os;
    public HolePunchSocket(String target_id) throws Exception {
        target=target_id;
        File isf=File.createTempFile("input_stream_",".bin");
        File osf=File.createTempFile("output_stream_",".bin");
        is=new FileInputStream(isf);
        os=new FileOutputStream(osf);
        HolePunchSocketUtilSingleton.getDatagram();
        new Thread(new Runnable() {
            @Override
            public void run() {
                beginHolePunch();
            }
        }).start();
        /*
        Register receiver here with target so that every data for the target handle by the handler of this socket
         */
        HolePunchSocketUtilSingleton.getInstance().registerWriter(target_id,new HolePunchStreamWriterThread(isf));
    }

    public void beginHolePunch(){
        while(true){
            try {
                String udpData="II_"+Settings.USERNAME+"\n";
                DatagramSocket datagramSocket = HolePunchSocketUtilSingleton.getDatagram();
                byte []buff=udpData.getBytes();
                String []ip_port=PrimaryServerSingleton.getInstance().getIp(target).split(":");
                String ip=ip_port[0];
                int port=Integer.parseInt(ip_port[1]);
                DatagramPacket datagramPacket=new DatagramPacket(buff,buff.length, InetAddress.getByName(ip),port);
                datagramSocket.send(datagramPacket);
                System.out.println("DATA SENT "+new String(buff));
                TimeUnit.MILLISECONDS.sleep(Settings.DEFAULT_HOLE_PUNCH_INTERVAL_MILS);
            }catch (Exception e){
                System.err.println("Check if primary server is okay!");
                System.err.println(e.getLocalizedMessage());
                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                }catch (Exception x){

                }
            }
        }
    }

    public InputStream getInputStream(){
        return is;
    }
    public OutputStream getOutputStream(){
        return os;
    }

}
