package com.practice.sample.cameramedia;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CameraMediaActivity extends AppCompatActivity {
    private static final int REQUEST_USED_PERMISSION = 200;

    private Button recordButton;
    private Button playButton;

    private static final String[] needPermissions = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        boolean permissionToRecordAccepted = true;

        switch (requestCode){
            case REQUEST_USED_PERMISSION:
                for (int result : grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        permissionToRecordAccepted = false;
                        break;
                    }
                }
                break;
        }

        if (permissionToRecordAccepted == false){
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        for (String permission : needPermissions) {
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, needPermissions, REQUEST_USED_PERMISSION);
                break;
            }
        }

        setContentView(R.layout.activity_camera_media);

        recordButton = (Button) findViewById(R.id.record_button);
        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CameraMediaActivity.this, RecordActivity.class);
                startActivity(intent);
            }
        });

        playButton = (Button) findViewById(R.id.play_button);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CameraMediaActivity.this, PlayActivity.class);
                startActivity(intent);
            }
        });
    }
}
