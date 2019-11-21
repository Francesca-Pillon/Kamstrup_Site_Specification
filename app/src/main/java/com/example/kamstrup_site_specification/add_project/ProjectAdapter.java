package com.example.kamstrup_site_specification.add_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kamstrup_site_specification.MainActivitySite;
import com.example.kamstrup_site_specification.R;
import com.example.kamstrup_site_specification.add_project.Database_project.ProjectEntry;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder>{



    private static final String date_format = "dd/mm/yyyy";
    final private ItemClickListener myItemClickListener;


    private List<ProjectEntry> projectEntries;
    private Context context;

    private SimpleDateFormat dateFormat = new SimpleDateFormat(date_format, Locale.getDefault());

    private MainActivitySite sites;


    public ProjectAdapter(Context context, ItemClickListener listener){
        this.context = context;
        this.myItemClickListener = listener;

    }


    @NonNull
    @Override
    public ProjectAdapter.ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflates the project layout to a view
        View view = LayoutInflater.from(context).inflate(R.layout.projectlist_instance_layout, parent, false);

        return new ProjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectAdapter.ProjectViewHolder holder, int position) {

        //Determine the values of wanted data
        ProjectEntry projectEntry = projectEntries.get(position);
        String projectName = projectEntry.getProjectName();
        String updateAt = dateFormat.format(projectEntry.getUpdatedAt());

        //set values

        holder.projectNameView.setText(projectName);
        holder.updatedAtView.setText(updateAt);

    }

    @Override
    public int getItemCount() {
        if (projectEntries == null){
            return 0;
        }

        return projectEntries.size();
    }

    public List<ProjectEntry> getProjectEntries(){
        return projectEntries;
    }

    public void setProjects(List<ProjectEntry> projectEntries){
        this.projectEntries = projectEntries;
        notifyDataSetChanged();
    }

    public interface ItemClickListener{
        void onItemClickListener(int itemId);
    }

    public class ProjectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //Class variables for the projectname and date
        TextView projectNameView;
        TextView updatedAtView;



        /*
        * @param itemView --> The view inflated in onCreateViewHolder*/

        public ProjectViewHolder(@NonNull View itemView) {
            super(itemView);

            projectNameView = itemView.findViewById(R.id.projectNameInstance);
            updatedAtView = itemView.findViewById(R.id.taskUpdatedAt);





            itemView.setOnClickListener(this);





        }


        @Override
        public void onClick(View view) {

            /*Intent intent = new Intent(this, MainActivitySite.class);
            startActivity(intent);*/

           // Toast.makeText(context, "update", Toast.LENGTH_SHORT).show();


            int elementID = projectEntries.get(getAdapterPosition()).getId();
            myItemClickListener.onItemClickListener(elementID);


        }
    }
}
