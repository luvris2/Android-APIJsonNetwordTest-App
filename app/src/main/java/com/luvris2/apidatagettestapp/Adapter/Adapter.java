package com.luvris2.apidatagettestapp.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.luvris2.apidatagettestapp.MainActivity;
import com.luvris2.apidatagettestapp.Model.Employee;
import com.luvris2.apidatagettestapp.R;
import com.luvris2.apidatagettestapp.ui.EditActivity;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    Context context;
    ArrayList<Employee> employeeList;

    public Adapter(Context context, ArrayList<Employee> employeeList) {
        this.context = context;
        this.employeeList = employeeList;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.employee_row, parent, false);
        return new Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Employee employee = employeeList.get(position);
        holder.txtName.setText(employee.employee_name);
        holder.txtAge.setText("나이 : " + employee.employee_age);
        holder.txtSalary.setText("연봉 : " + employee.employee_salary);
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtAge, txtSalary;
        ImageView imgDelete;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtAge = itemView.findViewById(R.id.txtAge);
            txtSalary = itemView.findViewById(R.id.txtSalary);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            cardView = itemView.findViewById(R.id.cardView);

            imgDelete.setOnClickListener(view -> {
                int index = getAdapterPosition();

                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle(R.string.alert_title);
                alert.setMessage(R.string.alert_message);
                alert.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        employeeList.remove(index);
                        notifyDataSetChanged();
                    }
                });
                alert.setNegativeButton("아니오", null);
                alert.show();
            });

            cardView.setOnClickListener(view -> {
                int index = getAdapterPosition();
                Intent intent = new Intent(context, EditActivity.class);

                Employee employee = employeeList.get(index);
                intent.putExtra("employee", employee);
                intent.putExtra("index", index);
                ((MainActivity)context).startActivityResult.launch(intent);
            });
        }
    }
}