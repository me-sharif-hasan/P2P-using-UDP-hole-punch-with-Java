package com.company.JSTUN;

import com.company.HolePunchReceiver;
import com.company.HolePunchSocket;
import com.company.Settings;
import com.company.stun.HolePunchSocketUtilSingleton;
import de.javawi.jstun.attribute.ChangeRequest;
import de.javawi.jstun.attribute.MappedAddress;
import de.javawi.jstun.attribute.MessageAttribute;
import de.javawi.jstun.attribute.MessageAttributeParsingException;
import de.javawi.jstun.header.MessageHeader;
import de.javawi.jstun.util.UtilityException;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;

public class demo {
    public static void main(String[] args) throws UtilityException, IOException, InterruptedException {
        MessageHeader receiveMH = new MessageHeader(MessageHeader.MessageHeaderType.BindingResponse);
        MessageHeader sendMH = new MessageHeader(MessageHeader.MessageHeaderType.BindingRequest);

        ChangeRequest changeRequest = new ChangeRequest();
        sendMH.addMessageAttribute(changeRequest);
        byte []messageHeader = sendMH.getBytes();

        DatagramPacket p = new DatagramPacket(messageHeader, messageHeader.length, InetAddress.getByName(Settings.DEFAULT_STUN_SERVER[0]), Settings.DEFAULT_STUN_PORT);
        HolePunchSocketUtilSingleton.getDatagram().send(p);


        DatagramPacket rp = new DatagramPacket(new byte[1024], 1024);
        System.out.println("Waiting for data");
        HolePunchSocketUtilSingleton.getDatagram().receive(rp);
        System.out.println("Data received");
        try {
            byte[] buffer = Arrays.copyOf( rp.getData(),rp.getLength());
            String data = new String(buffer).substring(0, 2);
            System.out.println("Checking stun response");
            receiveMH.parseAttributes(buffer);
            MappedAddress ma = (MappedAddress) receiveMH.getMessageAttribute(MessageAttribute.MessageAttributeType.MappedAddress);
            System.out.println(ma.getAddress()+" "+ma.getPort());
            tr();
            //updatePublicIp(/*ma.getAddress()*/"127.0.0.1" + ":" + datagramSocket.getLocalPort()/*ma.getPort()*/);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void tr()throws Exception{
        MessageHeader receiveMH = new MessageHeader(MessageHeader.MessageHeaderType.BindingResponse);
        MessageHeader sendMH = new MessageHeader(MessageHeader.MessageHeaderType.BindingRequest);

        ChangeRequest changeRequest = new ChangeRequest();
        sendMH.addMessageAttribute(changeRequest);
        byte []messageHeader = sendMH.getBytes();

        DatagramPacket p = new DatagramPacket(messageHeader, messageHeader.length, InetAddress.getByName("stun2.l.google.com"), Settings.DEFAULT_STUN_PORT);
        HolePunchSocketUtilSingleton.getDatagram().send(p);


        DatagramPacket rp = new DatagramPacket(new byte[1024], 1024);
        System.out.println("Waiting for data");
        HolePunchSocketUtilSingleton.getDatagram().receive(rp);
        System.out.println("Data received");
        try {
            byte[] buffer = Arrays.copyOf( rp.getData(),rp.getLength());
            String data = new String(buffer).substring(0, 2);
            System.out.println("Checking stun response");
            receiveMH.parseAttributes(buffer);
            MappedAddress ma = (MappedAddress) receiveMH.getMessageAttribute(MessageAttribute.MessageAttributeType.MappedAddress);
            System.out.println(ma.getAddress()+" "+ma.getPort());
            //updatePublicIp(/*ma.getAddress()*/"127.0.0.1" + ":" + datagramSocket.getLocalPort()/*ma.getPort()*/);
        } catch (MessageAttributeParsingException e) {
            e.printStackTrace();
        }
    }
}
