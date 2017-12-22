package example.com.tripplanner;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TripSearchUtil {
    static public class TripSearchJSONParser{
       static ArrayList<Place> parseTripSearch (String in) throws JSONException {
           ArrayList<Place> places = new ArrayList<Place>();

           JSONObject root = new JSONObject(in);
           JSONArray jsonArray = root.getJSONArray("predictions");
           for (int i =0; i<jsonArray.length();i++){
               Place place = new Place();
               JSONObject jsonObject = jsonArray.getJSONObject(i);
               String desc = jsonObject.getString("description");
               int m = desc.lastIndexOf(',');
               String place_str = desc.substring(0,m);

               String id = jsonObject.getString("place_id");

               place.setDesc(place_str);
               place.setPlace_id(id);
                places.add(place);
           }
           Log.d("demo places",places.toString());
           return places;
       }
    }
}
