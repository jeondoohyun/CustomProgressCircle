package com.test.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static java.lang.System.currentTimeMillis;

public class MainActivity extends AppCompatActivity {

    EditText et_1, et_2;
    TextView tv_result;

    AlertDialog.Builder we;

    ProgressBarCircularIndeterminate ani;

    public static Canvas can;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ani = (ProgressBarCircularIndeterminate) findViewById(R.id.ani);
    }// onCreate..

    public void resetClick(View view) {
//        ani.drawSecondAnimation(can);
//        Log.e("수치",ani.draw+"");
        ani.click();
    }

    public void startOrEndClick(View view) {
        ani.startOrEndProgress();
    }
}