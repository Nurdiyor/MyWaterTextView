package com.microstar.watertextviewShow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "HEllo world funs", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "HEllo world funs", Toast.LENGTH_SHORT).show();
    }
}
