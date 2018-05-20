package com.blesswater.rob.alertray;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* setContentView(R.layout.activity_main); */
        Toast.makeText(getApplicationContext(), "AlertRay Running\nVer: 0.0.2", Toast.LENGTH_LONG).show();
        finish();
    }
}
