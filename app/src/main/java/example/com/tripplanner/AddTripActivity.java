package example.com.tripplanner;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AddTripActivity extends AppCompatActivity implements GetTripAsync.IData,TripSearchAdapter.textData {

    EditText tripName,searchCity;
    Button search, addTrip;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    Place place;
    public static ArrayList<Trip> tripArrayList = new ArrayList<Trip>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);
        setTitle("Add Trips");
        tripName = findViewById(R.id.EnterTripName);
        searchCity = findViewById(R.id.editTextSearchCity);

        mRecyclerView = (RecyclerView) findViewById(R.id.addTripSearchList);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        findViewById(R.id.buttonSearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isConnectedOnline()) {
                    if(searchCity.getText().toString()!=null && !searchCity.getText().toString().isEmpty()){
                    StringBuilder str = new StringBuilder();
                    String url = "https://maps.googleapis.com/maps/api/place/autocomplete/json?key=AIzaSyD0dl9vqCdXwBWc6eajjBgJLu4ghfNp9PM&types=(cities)&input=";
                    String input = searchCity.getText().toString();
                    str.append(url);
                    str.append(input);
                    Log.d("demo", str.toString());
                    GetTripAsync getTripAsync = new GetTripAsync();
                    getTripAsync.context = AddTripActivity.this;
                    getTripAsync.execute(str);
                    } else{
                        Toast.makeText(AddTripActivity.this,"Enter City to search",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(AddTripActivity.this,"Check Network Connection",Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.buttonAddTrip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(searchCity.getText().toString()!=null && !searchCity.getText().toString().isEmpty() &&
                        tripName.getText().toString()!=null && !tripName.getText().toString().isEmpty() ){
                    Trip trip = new Trip();
                    String tripNamestr = tripName.getText().toString();
                    String tripPlacestr = searchCity.getText().toString();
                    trip.setTripName(tripNamestr);
                    trip.setTripCityName(place.getDesc());
                    trip.setTripID(place.getPlace_id());
                    mRootRef.child(tripNamestr).setValue(trip);
                    Intent intent = new Intent(AddTripActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(AddTripActivity.this,"Enter City and Trip Name",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isConnectedOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    @Override
    public void returnValues(ArrayList<Place> places) {
        mAdapter = new TripSearchAdapter(this, places);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void tripName(Place s) {
        searchCity.setText(s.getDesc());
        place = s;
    }
}
