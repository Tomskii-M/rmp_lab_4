package com.example.rmp_lab_4;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class SecondActivity extends AppCompatActivity {

    private TextView nameTextView;
    private Button pickDateTimeButton;
    private Button okButton;
    private Calendar calendar;
    private long selectedMillis = -1; // Выбранное время в мс

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Инициализация представлений
        nameTextView = findViewById(R.id.nameTextView);
        pickDateTimeButton = findViewById(R.id.pickDateTimeButton);
        okButton = findViewById(R.id.okButton);
        calendar = Calendar.getInstance();

        // Получаем и отображаем имя пользователя
        String username = getIntent().getStringExtra("username");
        if (username != null && !username.isEmpty()) {
            nameTextView.setText(username);
        } else {
            nameTextView.setText("Пользователь не задан");
        }

        // Обработчик выбора даты и времени
        pickDateTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(SecondActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        // После выбора даты — выбор времени
                        new TimePickerDialog(SecondActivity.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                calendar.set(Calendar.MINUTE, minute);
                                selectedMillis = calendar.getTimeInMillis();
                            }
                        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // Обработчик кнопки ОК: если дата выбрана, возвращаем ее в MainActivity
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedMillis != -1) {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("dateMillis", selectedMillis);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                } else {
                    Toast.makeText(SecondActivity.this, "Дата не выбрана", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}