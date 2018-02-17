package com.practice.sample.location;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class GoogleRouteApi extends Thread{

    public interface EventListener {
        void onApiResult(String result);
        void onApiFailed();
    }

    private Handler handler;
    private EventListener listener;
    private String apiKey;
    private LatLng startPoint;
    private LatLng endPoint;

    public GoogleRouteApi(Context context, LatLng startPoint, LatLng endPoint, EventListener eventListener) {
        this.handler = new Handler(Looper.getMainLooper());
        this.listener = eventListener;
        this.apiKey = context.getResources().getString(R.string.google_api_key);
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    @Override
    public void run() {
        HttpsURLConnection httpsURLConnection = null;

        try {
            URL url = new URL(
                    "https://maps.googleapis.com/maps/api/directions/json?origin="
                            + startPoint.latitude + "," + startPoint.longitude
                            + "&destination="
                            + endPoint.latitude + "," + endPoint.longitude
                            + "&mode=transit&language=ko&key="
                            + apiKey
            );

            httpsURLConnection = (HttpsURLConnection) url.openConnection();

            if (httpsURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = httpsURLConnection.getInputStream();

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");

                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String line;
                StringBuilder stringBuilder = new StringBuilder();

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }

                bufferedReader.close();

                String result = stringBuilder.toString();

                onApiResult(result);
            } else {
                onApiFailed();
            }

        } catch (Exception e) {
            e.printStackTrace();
            onApiFailed();
        } finally {
            if (httpsURLConnection != null) {
                httpsURLConnection.disconnect();
            }
        }
    }

    private void onApiResult(final String result) {
        if (listener != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    listener.onApiResult(result);
                }
            });
        }
    }

    private void onApiFailed() {
        if (listener != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    listener.onApiFailed();
                }
            });
        }
    }
}
