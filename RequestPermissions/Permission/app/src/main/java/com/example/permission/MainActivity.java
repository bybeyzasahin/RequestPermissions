package com.example.permission;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textPhone, textMessage;
    EditText editTextPhone, editTextMessage;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textPhone = (TextView) findViewById(R.id.textPhone);
        textMessage = (TextView) findViewById(R.id.textMessage);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editTextMessage = (EditText) findViewById(R.id.editTextMessage);
        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(
                        editTextPhone.getText().toString(),
                        null,
                        editTextMessage.getText().toString(),
                        null,
                        null);

                textPhone.setText("YOUR MESSAGE HAS BEEN SENT");
            }
        });



        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            Log.d("PERMISSION", "Permission is not granted, requesting");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
            button.setEnabled(false);
        } else {
            Log.d("PERMISSION", "Permission is granted");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("PERMISSION", "Permission has been granted");
                textPhone.setText("You can send SMS!");
                button.setEnabled(true);
            } else {
                Log.d("PERMISSION", "Permission has been denied or request cancelled");
                textPhone.setText("You can not send SMS!");
                button.setEnabled(false);
            }
        }
    }
}
