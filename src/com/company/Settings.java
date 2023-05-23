package com.company;

import java.util.Vector;

public class Settings {
    private Settings(){};
    public static String USERNAME;
    public static String FRIEND_NAME="jui";
    public static Vector <String> connections=new Vector<String>();
    public static long DEFAULT_STUN_INTERVAL_MILS=10000;
    public static long DEFAULT_HOLE_PUNCH_INTERVAL_MILS=1000;
    public static String []DEFAULT_STUN_SERVER={"stun.l.google.com","stun3.l.google.com"};//"127.0.0.1";//"stun.l.google.com";
    public static int DEFAULT_STUN_PORT=19302;
}
