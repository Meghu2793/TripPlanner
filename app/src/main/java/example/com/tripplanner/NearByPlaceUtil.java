package example.com.tripplanner;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NearByPlaceUtil {
    public static class NearByJSONParser{
    static ArrayList<NearByPlace> nearbyplacesearch(String in) throws JSONException {
        ArrayList<NearByPlace> nearByPlaces = new ArrayList<>();
        JSONObject root = new JSONObject(in);
        JSONArray jsonArray = root.getJSONArray("results");

        for (int i =0; i<jsonArray.length();i++){
            NearByPlace nearByPlace = new NearByPlace();
            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
            JSONObject jsonObject3 = jsonObject2.getJSONObject("geometry");
            JSONObject jsonObject4 = jsonObject3.getJSONObject("location");

            nearByPlace.setLat(jsonObject4.getDouble("lat"));
            nearByPlace.setLon(jsonObject4.getDouble("lng"));

            nearByPlace.setIcon(jsonObject2.getString("icon"));
            nearByPlace.setNearby_place(jsonObject2.getString("name"));
            nearByPlaces.add(nearByPlace);
        }
        return nearByPlaces;
    }
}
}
