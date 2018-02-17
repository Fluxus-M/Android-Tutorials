package com.practice.sample.location;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.google.android.gms.maps.model.LatLng;

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
