package example.com.tripplanner;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Place {
    String desc;
    String place_id;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    @Override
    public String toString() {
        return "Place{" +
                "desc='" + desc + '\'' +
                ", place_id='" + place_id + '\'' +
                '}';
    }

}
