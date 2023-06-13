package com.company.stun;

import com.company.DatagramReceiverInterface;
import com.company.HolePunchStreamWriterThread;
import de.javawi.jstun.util.UtilityException;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HolePunchSocketUtilSingleton {
    DatagramSocket datagramSocket;
    private HolePunchSocketUtilSingleton() throws SocketException, UtilityException, InterruptedException {
        datagramSocket = new DatagramSocket(8989);
        //datagramSocket.setReuseAddress(true);
        System.out.println("Datagram socket created");
        beginIpSync();
       // TimeUnit.MILLISECONDS.sleep(1000);
    }

    private void beginIpSync() throws UtilityException {
        new PublicIpSync(datagramSocket).start();
    }
    private static Map <String,DatagramReceiverInterface> datagramReceiverInterfaceMap=new HashMap<String, DatagramReceiverInterface>();
    public static void setDatagramReceiver(String targetName, DatagramReceiverInterface datagramReceiverInterface){
        datagramReceiverInterfaceMap.put(targetName,datagramReceiverInterface);
    }
    public static DatagramReceiverInterface getDatagramReceiver(String target){
        if(datagramReceiverInterfaceMap.containsKey(target))
        return datagramReceiverInterfaceMap.get(target);
        else return null;
    }

    Map<String, HolePunchStreamWriterThread> holePunchWriters=new HashMap<String, HolePunchStreamWriterThread>();
    public void registerWriter(String target_id,HolePunchStreamWriterThread holePunchStreamWriterThread){
        holePunchWriters.put(target_id,holePunchStreamWriterThread);
    }

    private static HolePunchSocketUtilSingleton instance;
    synchronized public static DatagramSocket getDatagram() throws SocketException, UtilityException, InterruptedException {
        if(instance==null) instance=new HolePunchSocketUtilSingleton();
        return instance.datagramSocket;
    }
    public static HolePunchSocketUtilSingleton getInstance() throws SocketException, UtilityException, InterruptedException {
        if(instance==null) instance=new HolePunchSocketUtilSingleton();
        return instance;
    }
}
