package com.practice.sample.userevent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UserEventNextActivity extends Activity {
    private TextView textView;
    private EditText editText;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_event_next);

        textView = (TextView) findViewById(R.id.textview);
        editText = (EditText) findViewById(R.id.edittext);
        button = (Button) findViewById(R.id.button);

        Intent intent = getIntent();
        String input = intent.getStringExtra("input");
        textView.setText(input);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Editable result = editText.getText();

                Intent intent = new Intent();
                intent.putExtra("result", result.toString());

                setResult(Activity.RESULT_OK, intent);

                finish();
            }
        });
    }

}
