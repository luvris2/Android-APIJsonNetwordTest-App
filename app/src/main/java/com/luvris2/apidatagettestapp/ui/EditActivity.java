package com.luvris2.apidatagettestapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.luvris2.apidatagettestapp.Model.Employee;
import com.luvris2.apidatagettestapp.R;

public class EditActivity extends AppCompatActivity {

    EditText editAge, editSalary;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        // 액션바 타이틀 설정
        getSupportActionBar().setTitle(R.string.title_edit);
        // 액션바 back 버튼 설정
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editAge = findViewById(R.id.editAge);
        editSalary = findViewById(R.id.editSalary);
        btnSave = findViewById(R.id.btnSave);

        Employee employee = (Employee) getIntent().getSerializableExtra("employee");

        editAge.setText(employee.employee_age+"");
        editSalary.setText(employee.employee_salary+"");

        btnSave.setOnClickListener(view -> {
            int age = Integer.parseInt(editAge.getText().toString().trim());
            int salary = Integer.parseInt(editSalary.getText().toString().trim());

            // 데이터를 전달하기 위한 intent
            Intent intent = new Intent();
            int index = getIntent().getIntExtra("index", 0);
            intent.putExtra("employeeId", index);
            intent.putExtra("employeeAge", age);
            intent.putExtra("employeeSalary", salary);
            setResult(RESULT_OK, intent);
            finish();
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        // back 버튼 설정법
        // 1. finish()
        // 2. 기계의 back 버튼 눌렀을때의 콜백 메소드 onBackPressed();
        finish();
        return true;
    }
}