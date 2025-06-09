package com.example.rmp_lab_4;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class SecondActivity extends AppCompatActivity {

    private TextView nameTextView;
    private Button pickDateTimeButton;
    private Button okButton;

    private Calendar calendar;
    private long selectedMillis = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        nameTextView = findViewById(R.id.nameTextView);
        pickDateTimeButton = findViewById(R.id.pickDateTimeButton);
        okButton = findViewById(R.id.okButton);
        calendar = Calendar.getInstance();

        // Принимаем имя из MainActivity и отображаем его
        String username = getIntent().getStringExtra("username");
        if (username != null) {
            nameTextView.setText(username);
        }

        pickDateTimeButton.setOnClickListener(v -> {
            // Сначала выбираем дату
            new DatePickerDialog(SecondActivity.this, (DatePicker datePicker, int year, int month, int dayOfMonth) -> {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                // Затем выбираем время
                new TimePickerDialog(SecondActivity.this, (TimePicker timePicker, int hourOfDay, int minute) -> {
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calendar.set(Calendar.MINUTE, minute);
                    selectedMillis = calendar.getTimeInMillis();
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });
    }

    // Нажатие на кнопку OK передаёт выбранную дату в MainActivity
    @Override
    protected void onResume() {
        super.onResume();
        okButton.setOnClickListener(v -> {
            if (selectedMillis != -1) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("dateMillis", selectedMillis);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}
