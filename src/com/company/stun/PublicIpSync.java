package com.company.stun;

import com.company.HolePunchReceiver;
import com.company.Settings;
import de.javawi.jstun.attribute.ChangeRequest;
import de.javawi.jstun.attribute.MappedAddress;
import de.javawi.jstun.attribute.MessageAttribute;
import de.javawi.jstun.attribute.MessageAttributeParsingException;
import de.javawi.jstun.header.MessageHeader;
import de.javawi.jstun.util.UtilityException;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

class PublicIpSync extends Thread {
    DatagramSocket datagramSocket;
    byte []messageHeader;
    MessageHeader receiveMH = new MessageHeader(MessageHeader.MessageHeaderType.BindingResponse);
    public PublicIpSync(DatagramSocket datagramSocket) throws UtilityException {
        this.datagramSocket=datagramSocket;
        MessageHeader sendMH = new MessageHeader(MessageHeader.MessageHeaderType.BindingRequest);

        ChangeRequest changeRequest = new ChangeRequest();
        sendMH.addMessageAttribute(changeRequest);
        messageHeader = sendMH.getBytes();
        initiateHolePunchReceiver();
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("Initiating public ip:port sync");
                String ip_port=getIpAddress();
                //System.out.println(ip_port);
                //PrimaryServerSingleton.getInstance().updateIp(ip_port, Settings.USERNAME);
                TimeUnit.MILLISECONDS.sleep(Settings.DEFAULT_STUN_INTERVAL_MILS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void initiateHolePunchReceiver(){
        System.out.println("Hole punch receiver initiated");
        HolePunchReceiver.getInstance(receiveMH).start();
    }



    private String getIpAddress() throws IOException {
        DatagramPacket p = new DatagramPacket(messageHeader, messageHeader.length, InetAddress.getByName(Settings.DEFAULT_STUN_SERVER), Settings.DEFAULT_STUN_PORT);
        datagramSocket.send(p);
        System.out.println("Datagram stun packet sent");
        // retrieving response
//        DatagramPacket rp;
//
//        rp = new DatagramPacket(new byte[32], 32);
//        datagramSocket.receive(rp);
//        try {
//            byte []buffer=rp.getData();
//            String data=new String(buffer).substring(0,2);
//            System.out.println(data);
//
//            receiveMH.parseAttributes(buffer);
//            MappedAddress ma = (MappedAddress) receiveMH
//                    .getMessageAttribute(MessageAttribute.MessageAttributeType.MappedAddress);
//            System.out.println(ma.getAddress()+"OKAY");
//            return ma.getAddress()+":"+ma.getPort();
//        } catch (MessageAttributeParsingException e) {
//            e.printStackTrace();
//        }
        return null;
    }
}
