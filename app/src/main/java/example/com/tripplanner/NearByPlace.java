package example.com.tripplanner;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class NearByPlace implements Serializable {
    String nearby_place, icon;
    Double lat, lon;
    boolean bool = false;

    public boolean isBool() {
        return bool;
    }

    public void setBool(boolean bool) {
        this.bool = bool;
    }

    @Override
    public String toString() {
        return "NearByPlace{" +
                "nearby_place='" + nearby_place + '\'' +
                ", icon='" + icon + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                '}';
    }

    public String getNearby_place() {
        return nearby_place;
    }

    public void setNearby_place(String nearby_place) {
        this.nearby_place = nearby_place;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("nearby_place", nearby_place);
        result.put("icon", icon);
        result.put("lat", lat);
        result.put("lon", lon);
        return result;
    }
}
