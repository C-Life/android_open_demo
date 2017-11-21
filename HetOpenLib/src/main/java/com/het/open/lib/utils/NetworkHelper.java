package com.het.open.lib.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import java.util.List;

/**
 * 网络状态辅助类
 * @author xuchao
 *
 */
public class NetworkHelper {
	public static boolean hasInternetPermission(Context context) {
		if (context != null) {
			return (context
					.checkCallingOrSelfPermission("android.permission.INTERNET") == PackageManager.PERMISSION_GRANTED);
		}

		return true;
	}

	public static boolean isNetworkAvailable(Context context) {
		if (context != null) {
			NetworkInfo info = getActiveNetworkInfo(context);
			return ((info != null) && (info.isConnected()));
		}

		return false;
	}

	public static boolean isWifiValid(Context context) {
		if (context != null) {
			NetworkInfo info = getActiveNetworkInfo(context);

			return ((info != null) && (1 == info.getType()) && (info
					.isConnected()));
		}

		return false;
	}

	public static boolean isMobileNetwork(Context context) {
		if (context != null) {
			NetworkInfo info = getActiveNetworkInfo(context);

			if (info == null) {
				return false;
			}

			return ((info != null) && (info.getType() == 0) && (info
					.isConnected()));
		}

		return false;
	}

	public static NetworkInfo getActiveNetworkInfo(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService("connectivity");
		return connectivity.getActiveNetworkInfo();
	}

	public static NetworkInfo getNetworkInfo(Context context, int networkType) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService("connectivity");
		return connectivityManager.getNetworkInfo(networkType);
	}

	public static int getNetworkType(Context context) {
		if (context != null) {
			NetworkInfo info = getActiveNetworkInfo(context);

			return ((info == null) ? -1 : info.getType());
		}

		return -1;
	}

	public static int getWifiState(Context context) {
		WifiManager wifi = (WifiManager) context.getSystemService("wifi");

		if (wifi == null) {
			return 4;
		}

		return wifi.getWifiState();
	}

	public static NetworkInfo.DetailedState getWifiConnectivityState(
			Context context) {
		NetworkInfo networkInfo = getNetworkInfo(context, 1);
		return ((networkInfo == null) ? NetworkInfo.DetailedState.FAILED
				: networkInfo.getDetailedState());
	}

	public static boolean wifiConnection(Context context, String wifiSSID,
			String password) {
		boolean isConnection = false;
		WifiManager wifi = (WifiManager) context.getSystemService("wifi");
		String strQuotationSSID = "\"" + wifiSSID + "\"";

		WifiInfo wifiInfo = wifi.getConnectionInfo();
		if ((wifiInfo != null)
				&& (((wifiSSID.equals(wifiInfo.getSSID())) || (strQuotationSSID
						.equals(wifiInfo.getSSID()))))) {
			isConnection = true;
		} else {
			List scanResults = wifi.getScanResults();
			if ((scanResults != null) && (scanResults.size() != 0)) {
				for (int nAllIndex = scanResults.size() - 1; nAllIndex >= 0; --nAllIndex) {
					String strScanSSID = ((ScanResult) scanResults
							.get(nAllIndex)).SSID;
					if ((wifiSSID.equals(strScanSSID))
							|| (strQuotationSSID.equals(strScanSSID))) {
						WifiConfiguration config = new WifiConfiguration();
						config.SSID = strQuotationSSID;
						config.preSharedKey = "\"" + password + "\"";
						config.status = 2;

						int nAddWifiId = wifi.addNetwork(config);
						isConnection = wifi.enableNetwork(nAddWifiId, false);
						break;
					}
				}
			}
		}

		return isConnection;
	}

	public static void clearCookies(Context context) {
		CookieSyncManager.createInstance(context);
		CookieManager cookieManager = CookieManager.getInstance();
		cookieManager.removeAllCookie();
		CookieSyncManager.getInstance().sync();
	}

//	public static String generateUA(Context ctx) {
//		StringBuilder buffer = new StringBuilder();
//		buffer.append("Android");
//		buffer.append("__");
//		buffer.append("weibo");
//		buffer.append("__");
//		buffer.append("sdk");
//		buffer.append("__");
//		try {
//			PackageManager pm = ctx.getPackageManager();
//			PackageInfo pi = null;
//			pi = pm.getPackageInfo(ctx.getPackageName(), 16);
//			String versionCode = pi.versionName;
//			buffer.append(versionCode.replaceAll("\\s+", "_"));
//		} catch (Exception localE) {
//			buffer.append("unknown");
//		}
//		return buffer.toString();
//	}
}
