package example.com.tripplanner;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class TripListAdapter extends RecyclerView.Adapter<TripListAdapter.RecyclerViewHolder> {

    public textDataTrip data;
    private ArrayList<Trip> mData;
    private Activity mContext;

    public TripListAdapter(Activity mContext, ArrayList<Trip> mData) {
        this.mData = mData;
        this.mContext = mContext;
        data = (textDataTrip)mContext;
    }

    @Override
    public TripListAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_trip, parent, false);
        TripListAdapter.RecyclerViewHolder viewHolder = new TripListAdapter.RecyclerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TripListAdapter.RecyclerViewHolder holder, final int position) {
        holder.textViewItem.setText(mData.get(position).getTripName());
        holder.textView.setText(mData.get(position).getTripCityName());

        RecyclerView mRecyclerView;
        RecyclerView.LayoutManager mLayoutManager;
        RecyclerView.Adapter mAdapter;


        mRecyclerView = holder.recyclerView;
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);

        if(mData.get(position).nearByplaceArrayList != null) {
           holder.nearByPlaces = mData.get(position).nearByplaceArrayList;
            mAdapter = new NearByPlaceListAdapter(mContext, holder.nearByPlaces);
            mRecyclerView.setAdapter(mAdapter);
        }

        holder.imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<NearByPlace> nearByPlaceArrayList = mData.get(position).nearByplaceArrayList;
                Intent intent = new Intent(mContext,MapsActivity.class);
                intent.putExtra("latlon",nearByPlaceArrayList);
                mContext.startActivity(intent);
            }
        });

        holder.imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strin = mData.get(position).getTripID();
                Log.d("demo",strin);
                data.sendValues(strin,mData.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView textViewItem, textView;
        ImageButton imageButton1, imageButton2;
        ArrayList<NearByPlace> nearByPlaces;
        RecyclerView recyclerView;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            textViewItem =  itemView.findViewById(R.id.textViewtripName);
            textView =  itemView.findViewById(R.id.textViewCity);
            imageButton1 =  itemView.findViewById(R.id.imageButtonLocation);
            imageButton2 =  itemView.findViewById(R.id.imageButtonadd);
            recyclerView = itemView.findViewById(R.id.nearbyPlaceList);
        }
    }
interface textDataTrip{
        void sendValues(String st, Trip t);
}
}
