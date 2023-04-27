package com.company;

import com.company.stun.HolePunchSocketUtilSingleton;
import de.javawi.jstun.util.UtilityException;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws Exception {
        Settings.USERNAME=args[0];
        HolePunchSocket holePunchSocket=new HolePunchSocket(args[1]);
        //HolePunchSocketUtilSingleton.getDatagram();
    }
}
