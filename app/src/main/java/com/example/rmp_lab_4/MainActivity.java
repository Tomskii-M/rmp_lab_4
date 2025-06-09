package com.example.rmp_lab_4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private Button goToSecondActivityButton;
    private TextView dateTextView; // для вывода полученной даты

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameEditText = findViewById(R.id.usernameEditText);
        goToSecondActivityButton = findViewById(R.id.goToSecondActivityButton);
        dateTextView = findViewById(R.id.dateTextView);

        goToSecondActivityButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            intent.putExtra("username", username);
            startActivityForResult(intent, 1); // открываем активность и ожидаем результат
        });
    }

    // Принимаем результат из SecondActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            long dateMillis = data.getLongExtra("dateMillis", -1);
            if (dateMillis != -1) {
                // Преобразуем миллисекунды в строку даты и выводим
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm");
                String dateString = sdf.format(new java.util.Date(dateMillis));
                dateTextView.setText("Выбранная дата: " + dateString);
            }
        }
    }
}
