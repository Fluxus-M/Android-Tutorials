package com.practice.sample.dialogs;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UserEventActivity extends AppCompatActivity {
    private TextView textView;
    private EditText editText;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_event_2);

        textView = (TextView) findViewById(R.id.textview);
        editText = (EditText) findViewById(R.id.edittext);
        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    public void showTextView(View view) {
        Editable input = editText.getText();
        textView.setText(input);
    }

    public void showToast() {
        Editable input = editText.getText();

        Context context = getApplicationContext();

        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, input, duration);

        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);

        toast.show();
    }

    public void showDialog() {
        DatePickerDialog datePickerDialog
                = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //year 선택된 년 , month 선택된 월 , dayOfMonth 선택된 일
            }
        }, 2017, 0, 26);

        datePickerDialog.setTitle("DatePickerDialog");
        datePickerDialog.setMessage("날짜 선택 다이얼로그 입니다");
        datePickerDialog.show();
    }
}
