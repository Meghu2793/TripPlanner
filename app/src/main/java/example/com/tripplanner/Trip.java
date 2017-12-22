package example.com.tripplanner;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Trip {
    String tripName,tripID,tripCityName;
   ArrayList<NearByPlace> nearByplaceArrayList;

    public String getTripCityName() {
        return tripCityName;
    }

    public void setTripCityName(String tripCityName) {
        this.tripCityName = tripCityName;
    }

    @Override

    public String toString() {
        return "Trip{" +
                "tripName='" + tripName + '\'' +
                ", tripID='" + tripID + '\'' +
               // ", placeArrayList=" + placeArrayList +
                '}';
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getTripID() {
        return tripID;
    }

    public void setTripID(String tripID) {
        this.tripID = tripID;
    }

//   // public ArrayList<Place> getPlaceArrayList() {
//        return placeArrayList;
//    }

//    public void setPlaceArrayList(ArrayList<Place> placeArrayList) {
//        this.placeArrayList = placeArrayList;
//    }
@Exclude
public Map<String, Object> toMap() {
    HashMap<String, Object> result = new HashMap<>();
    result.put("tripName", tripName);
    result.put("tripID", tripID);
    result.put("tripCityName", tripCityName);
    result.put("nearByplaceArrayList", nearByplaceArrayList);
    return result;
}
}
