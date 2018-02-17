package com.practice.sample.audiomedia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class AudioMediaActivity extends AppCompatActivity {
    private TextView statusTextView;
    private Button playButton;
    private Button recordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_media);

        statusTextView = (TextView) findViewById(R.id.status_textview);
        playButton = (Button) findViewById(R.id.play_button);
        recordButton = (Button) findViewById(R.id.record_button);
    }
}
