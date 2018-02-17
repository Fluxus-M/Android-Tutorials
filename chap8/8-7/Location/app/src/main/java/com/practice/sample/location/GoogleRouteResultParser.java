package com.practice.sample.location;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GoogleRouteResultParser {
    public static Route getRoute(String result) {
        Route route = new Route();

        try {
            JSONObject root = new JSONObject(result);

            JSONArray routes = root.getJSONArray("routes");

            JSONObject routes1 = routes.getJSONObject(0);

            JSONArray legs = routes1.getJSONArray("legs");

            JSONObject legs1 = legs.getJSONObject(0);

            JSONObject duration = legs1.getJSONObject("duration");

            String routeTime = duration.getString("text");
            route.routeTime = routeTime;

            route.steps = new ArrayList<>();
            JSONArray steps = legs1.getJSONArray("steps");

            for (int i = 0; i < steps.length(); i++) {
                Step step = new Step();

                JSONObject routeStep = steps.getJSONObject(i);

                String travelMode = routeStep.getString("travel_mode");

                step.stepText = "";

                if (routeStep.has("html_instructions")) {
                    String htmlInstructions = routeStep.getString("html_instructions");
                    step.stepText = htmlInstructions;
                }


                if (travelMode.equals("TRANSIT") && routeStep.has("transit_details")) {
                    JSONObject transitDetails = routeStep.getJSONObject("transit_details");

                    if (transitDetails.has("num_stops")) {
                        int numStops = transitDetails.getInt("num_stops");
                        step.transitStopNumber = numStops;
                    }

                    JSONObject line = transitDetails.getJSONObject("line");

                    if (line.has("short_name")) {
                        String shortName = line.getString("short_name");
                        step.transitName = shortName;
                    }

                }
                route.steps.add(step);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return route;
    }

}
