package com.company;

import com.company.stun.HolePunchSocketUtilSingleton;
import com.company.stun.PrimaryServerSingleton;
import de.javawi.jstun.util.UtilityException;

import java.io.IOException;

public class StunClient {
    public static void main(String[] args) throws UtilityException, IOException, InterruptedException {
        HolePunchSocketUtilSingleton.getDatagram();
        System.out.println(PrimaryServerSingleton.getInstance().getIp("iishanto"));
    }
}