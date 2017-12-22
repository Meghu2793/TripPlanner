package example.com.tripplanner;


import org.json.JSONException;
import org.json.JSONObject;

public class LatLonSearchUtil {
    public static class LatLonSearchJSONParser{
        static PlaceLatLon parseLatLonSearch(String in) throws JSONException {
            PlaceLatLon placeLatLon = new PlaceLatLon();
            JSONObject jsonObject = new JSONObject(in);
            JSONObject jsonObject1 = jsonObject.getJSONObject("result");
            JSONObject jsonObject2 = jsonObject1.getJSONObject("geometry");
            JSONObject jsonObject3 = jsonObject2.getJSONObject("location");
            Double d1 = jsonObject3.getDouble("lat");
            Double d2 = jsonObject3.getDouble("lng");
            placeLatLon.setLat(d1);
            placeLatLon.setLon(d2);

            return placeLatLon;
        }
    }
}
