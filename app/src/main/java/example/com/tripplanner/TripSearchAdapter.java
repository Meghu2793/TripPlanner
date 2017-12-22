package example.com.tripplanner;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class TripSearchAdapter extends RecyclerView.Adapter<TripSearchAdapter.RecyclerViewHolder> {

    public  textData data;

    private ArrayList<Place> mData;
    private Activity mContext;

    public TripSearchAdapter(Activity mContext, ArrayList<Place> mData) {
        this.mData = mData;
        this.mContext = mContext;
        this.data = (textData) mContext;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_searchcity, parent, false);
        TripSearchAdapter.RecyclerViewHolder viewHolder = new TripSearchAdapter.RecyclerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
        holder.textViewItem.setText(mData.get(position).getDesc());
        holder.place = mData.get(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView textViewItem;
        Place place;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            textViewItem = (TextView) itemView.findViewById(R.id.textViewCityState);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int k = getAdapterPosition();
                    Place place = mData.get(k);
                    data.tripName(place);
                }
            });
        }
    }

    interface textData {
        void tripName(Place s);
    }
}


