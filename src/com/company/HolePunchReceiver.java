package com.company;

import com.company.stun.HolePunchSocketUtilSingleton;
import com.company.stun.PrimaryServerSingleton;
import de.javawi.jstun.attribute.MappedAddress;
import de.javawi.jstun.attribute.MessageAttribute;
import de.javawi.jstun.attribute.MessageAttributeParsingException;
import de.javawi.jstun.header.MessageHeader;
import de.javawi.jstun.util.UtilityException;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HolePunchReceiver extends Thread{
    MessageHeader receiveMH;
    private HolePunchReceiver(MessageHeader mh){
        receiveMH=mh;
    }
    @Override
    public void run() {
        DatagramSocket datagramSocket= null;
        try {
            datagramSocket = HolePunchSocketUtilSingleton.getDatagram();
            byte []buff=new byte[512];
            DatagramPacket rp = new DatagramPacket(buff, buff.length);
            while(true){
                try {
                    rp.setData(new byte[512]);
                    System.out.println("Waiting for data");
                    datagramSocket.receive(rp);
                    System.out.println("Data received");
                    try {
                        byte[] buffer = Arrays.copyOf( rp.getData(),rp.getLength());
                        String data = new String(buffer).substring(0, 2);
                        System.out.println("Checking stun response");
                        if(data.equals("II")){
                            /*
                            If receiver for specific target_ip exists
                            Send data to this receiver to handle further.
                            Else continue without handling
                             */
                            System.out.println(new String(buffer));
                            continue;
                        }
                        receiveMH.parseAttributes(buffer);
                        MappedAddress ma = (MappedAddress) receiveMH.getMessageAttribute(MessageAttribute.MessageAttributeType.MappedAddress);
                        updatePublicIp(ma.getAddress() + ":" + ma.getPort());
                        System.out.println(ma.getAddress()+" "+ma.getPort());
                    } catch (MessageAttributeParsingException e) {
                        e.printStackTrace();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UtilityException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void updatePublicIp(String ip_port) throws IOException, UtilityException, InterruptedException {
        System.out.println("UPDATING IP_PORT:"+ip_port+" "+HolePunchSocketUtilSingleton.getDatagram().getLocalPort());
        PrimaryServerSingleton.getInstance().updateIp(ip_port,Settings.USERNAME);
    }

    private static HolePunchReceiver instance;
    public static HolePunchReceiver getInstance(MessageHeader mh){
        if(instance==null) instance=new HolePunchReceiver(mh);
        return instance;
    }
}
