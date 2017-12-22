package example.com.tripplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TripListAdapter.textDataTrip, GetLatLonAsync.IData_
, GetPlacesAsync.IDataPlaces{

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    public  static Trip trip;
    public static ArrayList<NearByPlace> nearByPlacesArray = new ArrayList<NearByPlace>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Trips");

        mRecyclerView = (RecyclerView) findViewById(R.id.trips);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){
                    ArrayList<Trip> tripArrayList = new ArrayList<>();
                    for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
                        Trip temp = new Trip();
                        //DataSnapshot s1 = snapshot.child("NearByPlaces");
                        if (snapshot.hasChild("NearByPlaces")) {
                            ArrayList<NearByPlace> nearByPlaces = new ArrayList<>();
                            //for (DataSnapshot dataSnapshot2 : dataSnapshot1.child("currentFriends").getChildren()) {
                            for (DataSnapshot s2 : snapshot.child("NearByPlaces").getChildren()) {
                                nearByPlaces.add(s2.getValue(NearByPlace.class));
                            }
                            temp.nearByplaceArrayList = nearByPlaces;
                        }
                        String tempCityName = snapshot.child("tripCityName").getValue(String.class);
                        String tempTripID = snapshot.child("tripID").getValue(String.class);
                        String tempTripName = snapshot.child("tripName").getValue(String.class);
                        temp.setTripID(tempTripID);
                        temp.setTripCityName(tempCityName);
                        temp.setTripName(tempTripName);
                        //snapshot.getValue(Trip.class);
                        tripArrayList.add(temp);

                    }
                    mAdapter = new TripListAdapter(MainActivity.this, tripArrayList);
                    mRecyclerView.setAdapter(mAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        findViewById(R.id.imageButtonPlus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_main = new Intent(MainActivity.this, AddTripActivity.class);
                startActivity(intent_main);
            }
        });
        findViewById(R.id.imageButtonHome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void sendValues(String st, Trip t) {
        trip = t;
        StringBuilder s = new StringBuilder();
        String url = "https://maps.googleapis.com/maps/api/place/details/json?key=AIzaSyD0dl9vqCdXwBWc6eajjBgJLu4ghfNp9PM&placeid=";
        s.append(url);
        s.append(st);
        Log.d("demo", String.valueOf(s));
        GetLatLonAsync getTripAsync = new GetLatLonAsync();
        getTripAsync.context = MainActivity.this;
        getTripAsync.execute(s);
    }

    @Override
    public void returnValues(PlaceLatLon places) {
        StringBuilder s = new StringBuilder();
        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?key=AIzaSyD0dl9vqCdXwBWc6eajjBgJLu4ghfNp9PM&location=";
        s.append(url);
        s.append(places.getLat()+",");
        s.append(places.getLon()+"&radius=1000");
        GetPlacesAsync getTripAsync = new GetPlacesAsync();
        getTripAsync.context = MainActivity.this;
        getTripAsync.execute(s);
    }

    @Override
    public void returnNearByPlaces(ArrayList<NearByPlace> nearByPlaces) {
        nearByPlacesArray = nearByPlaces;
        mAdapter = new PlaceListAdapter(MainActivity.this, nearByPlacesArray,trip);
        mRecyclerView.setAdapter(mAdapter);
    }
}
