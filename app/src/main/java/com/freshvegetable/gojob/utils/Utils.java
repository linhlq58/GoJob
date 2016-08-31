package com.freshvegetable.gojob.utils;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Build;
import android.provider.Settings;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by NamVp on 24/08/2016.
 *
 */

@SuppressWarnings("unused")
public class Utils {


    /**
     * Set list height based on sum of it's child view height of a
     *
     * @param listView disable listView scroll,
     *                 so you need to put it in to a scrollView or something like that
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    /**
     * Return device Id
     */
    public static String deviceID() {
        return Settings.Secure.ANDROID_ID;
    }

    /**
     * Return device model
     */
    public static String deviceModel() {
        return Build.MODEL;
    }

    /**
     * Return device SDK version
     */
    public static int deviceVersion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * Return device product
     */
    public static String deviceProduct() {
        return Build.PRODUCT;
    }


    /**
     * Get device screen size functions set
     * getScreenDisplay is a private base function to next others function
     *
     * @param context context where you need to use this function
     * @return screen width and height by size and pixel
     */
    private static Display getScreenDisplay(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return manager.getDefaultDisplay();
    }


    public static int getScreenWidthByPixel(Context context) {
        Point p = new Point();
        getScreenDisplay(context).getSize(p);
        return p.x;
    }

    public static int getScreenHeightByPixel(Context context) {
        Point p = new Point();
        getScreenDisplay(context).getSize(p);
        return p.y;
    }

    public static int deviceSize(Context context) {
        if ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_UNDEFINED)
            return 0;
        if ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_SMALL)
            return 1;
        if ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL)
            return 2;
        if ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE)
            return 3;
        if ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE)
            return 4;
        return 0;
    }

    /** End of get screen size functions set */
    /**
     * Device network connection set
     */
    private static ConnectivityManager getConnectivityManager(Context context) {
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public static boolean isNetworkConnected(Context context) {
        NetworkInfo networkInfo = getConnectivityManager(context).getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnectedOrConnecting());
    }

    /**
     * Device wifi functions set
     */
    private static NetworkInfo getWifiInfo(Context context) {
        return getConnectivityManager(context).getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    }

    private static WifiManager getWifiManager(Context context) {
        return (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }

    /**
     * Return whether Wi-Fi is available or not.
     *
     * @return {@code true} if Wi-Fi is available or connecting
     */
    public static boolean isWifiAvailable(Context context) {
        return getWifiInfo(context).isAvailable();
    }

    /**
     * Return whether Wi-Fi is connected or connecting or not.
     *
     * @return {@code true} if Wi-Fi is connected or connecting
     */
    public static boolean isWifiConnected(Context context) {
        return getWifiInfo(context).isConnectedOrConnecting();
    }

    /**
     * Return whether Wi-Fi is enabled or disabled.
     *
     * @return {@code true} if Wi-Fi is enabled
     */
    public static boolean isWifiEnable(Context context) {
        return getWifiManager(context).isWifiEnabled();
    }

    /** End of wifi functions set */
    /**
     * Device Bluetooth functions set
     */
    private static NetworkInfo getBluetoothInfo(Context context) {
        return getConnectivityManager(context).getNetworkInfo(ConnectivityManager.TYPE_BLUETOOTH);
    }

    private static BluetoothAdapter getBluetoothAdapter() {
        return BluetoothAdapter.getDefaultAdapter();
    }

    private static BluetoothManager getBluetoothManager(Context context) {
        return (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
    }

    /**
     * Return whether bluetooth is available or not
     *
     * @return {@code true} if Bluetooth is available
     */
    public static boolean isBluetoothAvailable(Context context) {
        return getBluetoothInfo(context).isAvailable();
    }

    /**
     * Return whether Bluetooth is connected of disconnected
     *
     * @param context where function is called
     * @return {@code true} if Bluetooth is connected
     */
    public static boolean isBluetoothConnected(Context context) {
        return getBluetoothInfo(context).isConnectedOrConnecting();
    }

    /**
     * Return whether Bluetooth is enabled or not
     *
     * @return {@code true} id Bluetooth is enabled
     * required uses-permission android.permission.BLUETOOTH;
     */
//    public static boolean isBluetoothEnable() {
//        if (getBluetoothAdapter() == null) return false;
//        return getBluetoothAdapter().isEnabled();
//    }

    /** End of Bluetooth functions set*/
    /**
     * Mobile data connect function set
     */

    private static NetworkInfo getMobileDataInfo(Context context) {
        return getConnectivityManager(context).getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    }

    public static boolean isMobileDataAvailable(Context context) {
        return getMobileDataInfo(context).isAvailable();
    }

    public static boolean isMobileDataConnected(Context context) {
        return getMobileDataInfo(context).isConnectedOrConnecting();
    }

    public static boolean isMobileDataEnable(Context context) {
        return ((getMobileDataInfo(context) != null) && isMobileDataConnected(context));
    }

    /** End of Mobile data functions set */
    /**
     * End of Device network functions set
     */

    private static LocationManager getLocationManager(Context context) {
        return (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public static boolean isGPSEnable(Context context) {
        return getLocationManager(context).isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private static AudioManager getAudioManager(Context context) {
        return (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    private static BatteryManager getBatteryManager(Context context) {
        return (BatteryManager) context.getSystemService(Context.BATTERY_SERVICE);
    }


}
