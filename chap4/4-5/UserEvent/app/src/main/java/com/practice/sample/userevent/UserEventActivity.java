package com.practice.sample.userevent;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UserEventActivity extends AppCompatActivity {
    private static final String NOTIFICATION_CHANNEL_ID = "channel1_ID";
    private static final String NOTIFICATION_CHANNEL_NAME = "channel1";

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
        final Editable input = editText.getText();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(input);
        builder.setTitle("대화상자 타이틀 입니다.");

        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(UserEventActivity.this, UserEventNextActivity.class);

                intent.putExtra("input", input.toString());

                startActivityForResult(intent, 1);
            }
        });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "취소 버튼을 눌렀습니다", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog dialog = builder.create();

        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {

            if(resultCode == Activity.RESULT_OK){
                String result = data.getStringExtra("result");

                Context context = getApplicationContext();
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, result, duration);
                toast.show();
            } else if (resultCode == Activity.RESULT_CANCELED) {

                Context context = getApplicationContext();
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, "전달 받은 값이 없습니다", duration);
                toast.show();
            }
        }
    }

    public void showNotification() {
        NotificationManager notificationManager
                = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_LOW;

            NotificationChannel notificationChannel
                    = new NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    NOTIFICATION_CHANNEL_NAME,
                    importance);

            notificationManager.createNotificationChannel(notificationChannel);
        }

        int notificationId = 0;

        Editable input = editText.getText();

        NotificationCompat.Builder builder
                = new NotificationCompat.Builder(getApplicationContext(),
                NOTIFICATION_CHANNEL_ID);

        builder.setSmallIcon(R.drawable.nougat_36);
        builder.setContentTitle("알림 타이틀 입니다");
        builder.setContentText(input);

        notificationManager.notify(notificationId, builder.build());
    }

}
