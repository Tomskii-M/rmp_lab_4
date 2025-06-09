package com.example.rmp_lab_4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private Button goToSecondActivityButton;
    private TextView dateTextView; // Для вывода полученной даты

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация представлений
        usernameEditText = findViewById(R.id.usernameEditText);
        goToSecondActivityButton = findViewById(R.id.goToSecondActivityButton);
        dateTextView = findViewById(R.id.dateTextView);

        goToSecondActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                if (username.isEmpty()) {
                    usernameEditText.setError("Введите имя пользователя");
                    return;
                }
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("username", username);
                startActivityForResult(intent, 1); // Запускаем вторую активность с ожиданием результата
            }
        });
    }

    // Принимаем результат из SecondActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            long dateMillis = data.getLongExtra("dateMillis", -1);
            if (dateMillis != -1) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                String dateString = sdf.format(new Date(dateMillis));
                dateTextView.setText("Выбранная дата: " + dateString);
            }
        }
    }
}