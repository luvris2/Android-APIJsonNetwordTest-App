package com.luvris2.apidatagettestapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.luvris2.apidatagettestapp.Adapter.Adapter;
import com.luvris2.apidatagettestapp.Model.Employee;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Adapter viewAdapter;
    ArrayList<Employee> employeeList;
    ProgressBar pb;


    final String URL = "http://dummy.restapiexample.com/api/v1/employees";

    public ActivityResultLauncher<Intent> startActivityResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        int id = result.getData().getIntExtra("employeeId", 0);
                        int age = result.getData().getIntExtra("employeeAge", 0);
                        int salary = result.getData().getIntExtra("employeeSalary", 0);

                        Employee employee = employeeList.get(id);
                        employee.employee_age = age;
                        employee.employee_salary = salary;

                        viewAdapter.notifyDataSetChanged();
                    }
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 액션바 호출
        getSupportActionBar().setTitle(R.string.title_name);

        pb = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        getData();


    }

    public void getData() {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        // 네트워크 통신을 위한 JsonObjectRequest 객체 생성
        // 생성자 : http Method, API URL, 전달 할 데이터, 실행 코드(Listener)
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null,
                response -> {
                    // API 호출 결과 실행
                    try {
                        employeeList = new ArrayList<>();

                        JSONArray jarr = response.getJSONArray("data");

                        for (int i=0; i<jarr.length(); i++){
                            JSONObject c = jarr.optJSONObject(i);
                            int id = c.optInt("id");
                            String name = c.optString("employee_name");
                            int age = c.optInt("employee_age");
                            int salary = c.optInt("employee_salary");
                            Employee employee = new Employee(id, name, age, salary);
                            employeeList.add(employee);

                        }
                        viewAdapter = new Adapter(MainActivity.this, employeeList);
                        recyclerView.setAdapter(viewAdapter);
                        pb.setVisibility(View.INVISIBLE);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        pb.setVisibility(View.INVISIBLE);
                    }
                }, error -> {
                    Log.i("onErrorResponse", ""+error);
                    pb.setVisibility(View.INVISIBLE);
        });
        pb.setVisibility(View.VISIBLE);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add((jsonObjectRequest));
    }
}