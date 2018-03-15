package org.darkbyte.internhomework;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;

/**
 * Created by root on 2/6/17.
 */

public class sharedprefs {
    public static final String myprefs = "myprefs";
    public static final String login_pin="login_pin";

    public sharedprefs(Context context) {
        sharedpreferences = context.getSharedPreferences(myprefs, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        this.context = context;
    }

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    Context context;
    public void saveuid(String uid){

        editor.putString("uid",uid);
        editor.commit();
    }
    public void clearuid(String uid){

        editor.putString("uid",null);
        editor.commit();
    }
   /* public  String getuid() {
        Random r = new Random();
        int i1 = r.nextInt(80-65)+65;
        return sharedpreferences.getInt("i1",);
    }*/

    public String getuid(){

        return sharedpreferences.getString("uid",null);
    }
    public String getfile(){

        return sharedpreferences.getString("randomnumber",null);
    }
    public void saveprefs(String loginstatus){

        editor.putString(login_pin,loginstatus);
        editor.commit();
    }
    public void savefilename(String randomnmber){

        editor.putString("randomnumber",randomnmber);
        editor.commit();
    }
    public void clearprefs(String loginstatus){

        editor.putString(login_pin,null);
        editor.commit();
    }
    public  String getloginstatus() {
        return sharedpreferences.getString(login_pin,null);
    }
    public void saveprivateandpublickey(String privatekey,String publickey,String digital_sign){

        editor.putString("privatekey",privatekey);
        editor.putString("publickey",publickey);
        editor.putString("digitalsignature",digital_sign);
        editor.commit();
    }
    public void clearkeys(){

        editor.putString("privatekey",null);
        editor.putString("publickey",null);
        editor.putString("digitalsignature",null);
        editor.commit();
    }



    public void saveusercertdetails(String commonName ,String country_code,String statename , String localityName, String organizationalUnitName){

        editor.putString("commonName",commonName);
        editor.putString("country_code",country_code);
        editor.putString("statename",statename);
        editor.putString("localityName",localityName);
        editor.putString("organizationalUnitName",organizationalUnitName);
        editor.commit();
    }
    public void clearusercertdetails(){

        editor.putString("commonName",null);
        editor.putString("country_code",null);
        editor.putString("statename",null);
        editor.putString("localityName",null);
        editor.putString("organizationalUnitName",null);
        editor.commit();
    }
    public  String getcommonname() {
        return sharedpreferences.getString("commonName",null);
    }
    public  String getcountrycode() {
        return sharedpreferences.getString("country_code",null);
    }
    public  String getstatename(){return  sharedpreferences.getString("statename",null);}
    public  String getlocalityname() {
        return sharedpreferences.getString("localityName",null);
    }
    public  String getorganisationalname() {
        return sharedpreferences.getString("organizationalUnitName",null);
    }


    public  String getprivatekey() {
        return sharedpreferences.getString("privatekey",null);
    }
    public  String getpublickey() {
        return sharedpreferences.getString("publickey",null);
    }
    public  String getdigitalsign(){return  sharedpreferences.getString("digitalsignature",null);}

    public String getIMEI(Activity activity) {
        TelephonyManager telephonyManager = (TelephonyManager) activity
                .getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }
    public void savecertresponse(String status) {
        editor.putString("certresponse",status);
        editor.commit();
    }
    public String getcertresponse(){
        return sharedpreferences.getString("certresponse",null);
    }


}
