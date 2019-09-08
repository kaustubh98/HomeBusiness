package SupportClasses;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.homebusinessfinal.R;

import java.util.List;

public class BusinessListAdapter extends RecyclerView.Adapter<BusinessListAdapter.ViewHolder> {

    private List<BusinessData> listOfBusinesses;

    public BusinessListAdapter(List<BusinessData> list){
        listOfBusinesses = list;
    }

    //create the ViewHolder object
    @Override
    public BusinessListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.businesslist_view,parent,false);
        return new ViewHolder(v);
    }

    //add the data to the layout of a row
    @Override
    public void onBindViewHolder(BusinessListAdapter.ViewHolder holder, int position) {
        BusinessData business = listOfBusinesses.get(position);
        TextView title = holder.Title;
        title.setText(business.getName());
        TextView description = holder.Description;
        description.setText(business.getDescription());

    }

    //return the number of objects
    @Override
    public int getItemCount() {
        return listOfBusinesses.size();
    }

    //define the type of view holder
    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView Title;
        public TextView Description;

        public ViewHolder(View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.businessTitle);
            Description = itemView.findViewById(R.id.businessDescription);
        }
    }
}
