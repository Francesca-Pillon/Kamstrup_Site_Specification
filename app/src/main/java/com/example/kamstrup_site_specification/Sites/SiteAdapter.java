package com.example.kamstrup_site_specification.Sites;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kamstrup_site_specification.R;
import com.example.kamstrup_site_specification.Sites.Database_site.SiteEntry;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;


public class SiteAdapter extends RecyclerView.Adapter<SiteAdapter.SiteViewHolder>{


    private static final String date_format = "dd/mm/yyyy";
    final private ItemClickListener myItemClickListener;


    private List<SiteEntry> siteEntries;
    private Context context;

    private SimpleDateFormat dateFormat = new SimpleDateFormat(date_format, Locale.getDefault());



    public SiteAdapter(Context context, ItemClickListener listener){
        this.context = context;
        this.myItemClickListener = listener;
    }




    @NonNull
    @Override
    public SiteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflates the site layout to a view
        View view = LayoutInflater.from(context).inflate(R.layout.site_sitelist_instance_layout, parent, false);

        return new SiteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SiteViewHolder holder, int position) {

        //Determine the values of wanted data
        SiteEntry siteEntry = siteEntries.get(position);
        String siteAddress = siteEntry.getAddress();
        int siteType = siteEntry.getSitetype();
        String updateAt = dateFormat.format(siteEntry.getUpdatedAt());

        //set values

        holder.siteAddressView.setText(siteAddress);
        holder.updatedAtView.setText(updateAt);

        //Setting the sitetype and color for the views
        //String siteTypeString = ""+siteType; //converts the int to string type
        //holder.siteTypeView.setText(siteTypeString);

        //GradientDrawable siteTypeCircle = (GradientDrawable) holder.siteAddressView.getBackground();
        //Get the appropriate background color based on the site type

    }

    @Override
    public int getItemCount() {
        if (siteEntries == null){
            return 0;
        }

        return siteEntries.size();
    }

    public List<SiteEntry> getSiteEntries(){
        return siteEntries;
    }

    public void setSites(List<SiteEntry> siteEntries){
        this.siteEntries = siteEntries;
        notifyDataSetChanged();
    }

    public interface ItemClickListener{
        void onItemClickListener(int itemId);
    }

    public class SiteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //Class variables for the site address and date
        TextView siteAddressView;
        TextView updatedAtView;
        TextView siteTypeView;

        /*
        * @param itemView --> The view inflated in onCreateViewHolder*/

        public SiteViewHolder(@NonNull View itemView) {
            super(itemView);

            siteAddressView = itemView.findViewById(R.id.site_address);
            updatedAtView = itemView.findViewById(R.id.taskUpdatedAt);


            itemView.setOnClickListener(this);


        }


        @Override
        public void onClick(View view) {
            int elementID = siteEntries.get(getAdapterPosition()).getId();
            myItemClickListener.onItemClickListener(elementID);


        }
    }
}
