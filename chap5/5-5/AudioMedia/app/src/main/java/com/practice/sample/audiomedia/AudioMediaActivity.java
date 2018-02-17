package com.practice.sample.audiomedia;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class AudioMediaActivity extends AppCompatActivity {
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;

    private TextView statusTextView;
    private Button playButton;
    private Button recordButton;

    private String fileName;

    private MediaRecorder mediaRecorder = null;
    private MediaPlayer mediaPlayer = null;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean permissionToRecordAccepted = false;
        switch (requestCode){
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (permissionToRecordAccepted == false){
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_RECORD_AUDIO_PERMISSION);
        }

        setContentView(R.layout.activity_audio_media);

        fileName = getExternalCacheDir().getAbsolutePath() + "/record.3gp";

        statusTextView = (TextView) findViewById(R.id.status_textview);
        playButton = (Button) findViewById(R.id.play_button);
        recordButton = (Button) findViewById(R.id.record_button);

        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaRecorder == null) {
                    startRecording();
                } else {
                    stopRecording();
                }
            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer == null) {
                    startPlaying();
                } else {
                    stopPlaying();
                }
            }
        });
    }

    private void startRecording() {
        statusTextView.setText("녹음중");
        recordButton.setText("녹음중지");

        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(fileName);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();

            Toast.makeText(this, "녹음에 실패하였습니다.", Toast.LENGTH_LONG).show();

            statusTextView.setText("대기상태");
            recordButton.setText("녹음시작");

            mediaRecorder = null;
        }

        mediaRecorder.start();
    }

    private void stopRecording() {
        statusTextView.setText("대기상태");
        recordButton.setText("녹음시작");

        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }

    private void startPlaying() {
        statusTextView.setText("재생중");
        playButton.setText("재생중지");

        mediaPlayer = new MediaPlayer();

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopPlaying();
            }
        });

        try {
            mediaPlayer.setDataSource(fileName);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();

            Toast.makeText(this, "재생에 실패하였습니다.", Toast.LENGTH_LONG).show();

            statusTextView.setText("대기상태");
            playButton.setText("재생시작");

            mediaPlayer = null;
        }
    }

    private void stopPlaying() {
        statusTextView.setText("대기상태");
        playButton.setText("재생시작");

        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
