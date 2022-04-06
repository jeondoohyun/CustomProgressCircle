package com.test.progresscircle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class CustomLoadingActivity extends AppCompatActivity {

    //99

    EditText et_1, et_2;
    TextView tv_result;

    AlertDialog.Builder we;

    ProgressBarCircularIndeterminate ani;

    public static Canvas can;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_loading);

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

    public void activityMove(View view) {
        startActivity(new Intent(this, MaterialLoadingActivity.class));
    }
}