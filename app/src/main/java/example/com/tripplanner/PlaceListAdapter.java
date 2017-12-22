package example.com.tripplanner;


import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class PlaceListAdapter extends RecyclerView.Adapter<PlaceListAdapter.RecyclerViewHolder> {

    private ArrayList<NearByPlace> mData;
    private Activity mContext;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private Trip t1;

    public PlaceListAdapter(Activity mContext, ArrayList<NearByPlace> mData, Trip trip) {
        this.mData = mData;
        this.mContext = mContext;
        t1 = trip;
    }

    @Override
    public PlaceListAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_place, parent, false);
        PlaceListAdapter.RecyclerViewHolder viewHolder = new PlaceListAdapter.RecyclerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PlaceListAdapter.RecyclerViewHolder holder, final int position) {
       holder.textViewItem.setText(mData.get(position).getNearby_place());
       holder.imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRootRef.child(t1.getTripName()).child("NearByPlaces").push().setValue(mData.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView textViewItem;
        ImageButton imageButton1, imageButton2;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            textViewItem =  itemView.findViewById(R.id.textView);
            imageButton1 =  itemView.findViewById(R.id.imageButtonPlace);
            imageButton2 =  itemView.findViewById(R.id.imageButtonAddPlace);
        }
    }
}
