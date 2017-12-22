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

public class NearByPlaceListAdapter extends RecyclerView.Adapter<NearByPlaceListAdapter.RecyclerViewHolder> {

    private ArrayList<NearByPlace> mData;
    private Activity mContext;

    public NearByPlaceListAdapter(Activity mContext, ArrayList<NearByPlace> mData) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public NearByPlaceListAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_nearbyplaceselected, parent, false);
        NearByPlaceListAdapter.RecyclerViewHolder viewHolder = new NearByPlaceListAdapter.RecyclerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NearByPlaceListAdapter.RecyclerViewHolder holder, final int position) {
        holder.textViewItem.setText(mData.get(position).getNearby_place());
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
            textViewItem =  itemView.findViewById(R.id.textViewNearByplacesText);
            imageButton1 =  itemView.findViewById(R.id.imageButtonNearByplacesImg);
            imageButton2 =  itemView.findViewById(R.id.imageButtonNearByplacesImg2);
        }
    }
}
