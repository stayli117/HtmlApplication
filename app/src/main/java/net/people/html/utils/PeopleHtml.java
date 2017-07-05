package net.people.html.utils;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.webkit.WebView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PeopleHtml {
    private static Context mContext = null;

    private static class Holder {
        private static final PeopleHtml INSTANCE = new PeopleHtml();
    }

    private PeopleHtml() {
    }

    public static PeopleHtml getInstance(Context context) {
        if (context != null) {
            if (context instanceof Application) {}else{
                mContext = context.getApplicationContext();
            }
        }
        return Holder.INSTANCE;
    }

    public void execute(final String url, final WebView webView) throws Exception {
        String decodedURL = java.net.URLDecoder.decode(url, "UTF-8");
        Log.d("people-2-->", decodedURL);
        if (decodedURL.startsWith("people")) {
            String str = url.substring(7);
            JSONObject jsonObj = new JSONObject(str);
            String functionName = jsonObj.getString("functionName");
            JSONArray args = jsonObj.getJSONArray("arguments");
            if (functionName.equals("getAndroid")) {
                getAndroid(args, webView);
            } else {
                Class<PeopleHtml> classType = PeopleHtml.class;
                Method method = classType.getDeclaredMethod(functionName, JSONArray.class);
                method.invoke(getInstance(mContext), args);
            }
        } else {
            webView.loadUrl(url);
        }

    }

    private void getAndroid(JSONArray args, WebView webView) {
        Log.d("People", "getAndroid  args:" + args.toString());
        try {
            String deviceId = "Android_DATA";
            String callBack = args.getString(0);
            webView.loadUrl("javascript:" + callBack + "('" + deviceId + "')");
            webView.loadUrl("http://baidu.weather.com.cn/mweather/101010200.shtml?t=1498037783");
        } catch (Exception e) {
            e.toString();
        }
    }


    private void onCCEvent(final JSONArray args) throws JSONException {
        Log.d("People", "onCCEvent  args:" + args.toString());
        JSONArray array = args.getJSONArray(0);
        List<String> ck = new ArrayList<String>();
        for (int i = 0; i < array.length(); i++) {
            ck.add(array.getString(i));
        }
        int value = args.getInt(1);
        String label = args.getString(2);

    }


    private void onEvent(final JSONArray args) throws JSONException {
        Log.d("People", "onEvent  args:" + args.toString());
        String eventId = args.getString(0);
    }


    private void onEventWithLabel(final JSONArray args) throws JSONException {
        Log.d("People", "onEventWithLabel  args:" + args.toString());
        String eventId = args.getString(0);
        String label = args.getString(1);

    }


    private void onEventWithParameters(final JSONArray args) throws JSONException {
        Log.d("People", "onEventWithParameters  args:" + args.toString());
        String eventId = args.getString(0);
        JSONObject obj = args.getJSONObject(1);
        Map<String, String> map = new HashMap<String, String>();
        Iterator<String> it = obj.keys();
        while (it.hasNext()) {
            String key = String.valueOf(it.next());
            Object o = obj.get(key);
            if (o instanceof Integer) {
                String value = String.valueOf(o);
                map.put(key, value);
            } else if (o instanceof String) {
                String strValue = (String) o;
                map.put(key, strValue);
            }
        }
    }


    private void onEventWithCounter(final JSONArray args) throws JSONException {
        Log.d("People", "onEventWithCounter  args:" + args.toString());
        String eventId = args.getString(0);
        JSONObject obj = args.getJSONObject(1);
        Map<String, String> map = new HashMap<String, String>();
        Iterator<String> it = obj.keys();
        while (it.hasNext()) {
            String key = String.valueOf(it.next());
            Object o = obj.get(key);
            if (o instanceof Integer) {
                String value = String.valueOf(o);
                map.put(key, value);
            } else if (o instanceof String) {
                String strValue = (String) o;
                map.put(key, strValue);
            }
        }
        int value = args.getInt(2);
    }


    private void onPageBegin(final JSONArray args) throws JSONException {
        Log.d("People", "onPageBegin  args:" + args.toString());
        String pageName = args.getString(0);

    }


    private void onPageEnd(final JSONArray args) throws JSONException {
        Log.d("People", "onPageEnd  args:" + args.toString());
        String pageName = args.getString(0);

    }


    private void profileSignInWithPUID(final JSONArray args) throws JSONException {
        Log.d("People", "profileSignInWithPUID  args:" + args.toString());
        String puid = args.getString(0);

    }


    private void profileSignInWithPUIDWithProvider(final JSONArray args) throws JSONException {
        Log.d("People", "profileSignInWithPUIDWithProvider  args:" + args.toString());
        String puid = args.getString(0);
        String provider = args.getString(1);
    }


    private void profileSignOff(final JSONArray args) throws JSONException {
        Log.d("People", "profileSignOff");
    }

}
