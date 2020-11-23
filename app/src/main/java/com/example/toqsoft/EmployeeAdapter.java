package com.example.toqsoft;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder> implements Filterable {


    private List<EmployeeModel> employs;
    private List<EmployeeModel> employs_list_filtered;
    private Context context;


    public EmployeeAdapter(Context context, List<EmployeeModel> employdata) {
        this.employs_list_filtered=employdata;
        this.context=context;
    }

    @NonNull
    @Override
    public EmployeeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.employ_list_item,viewGroup,false);
        return new EmployeeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeAdapter.ViewHolder viewHolder, int i) {
        EmployeeModel employeeModel=employs_list_filtered.get(i);

        viewHolder.employ_name.setText("Employ Name : "+employeeModel.getEmployee_name());
        viewHolder.employ_salary.setText("Employ Salary : "+employeeModel.getEmployee_salary());
        viewHolder.employ_age.setText("Employ Age : "+employeeModel.getEmployee_age());


        //Picasso.get().load(employs.get(i).getProfile_image()).into(viewHolder.emply_image);
    }

    @Override
    public int getItemCount() {
        return employs_list_filtered!=null?employs_list_filtered.size():0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    employs = employs_list_filtered;
                } else {
                    List<EmployeeModel> filteredList = new ArrayList<>();
                    for (EmployeeModel row : employs_list_filtered) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getEmployee_name().toLowerCase().contains(charString.toLowerCase()) ) {
                            filteredList.add(row);
                        }
                    }

                    employs = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = employs;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                employs_list_filtered = (List<EmployeeModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView emply_image;
        private TextView employ_name,employ_salary,employ_age;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            emply_image=(ImageView)itemView.findViewById(R.id.employ_image);
            employ_name=(TextView) itemView.findViewById(R.id.employ_name);
            employ_age=(TextView)itemView.findViewById(R.id.employ_age);
            employ_salary=(TextView)itemView.findViewById(R.id.employ_salary);

        }
    }
}

