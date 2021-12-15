package com.test.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.progressindicator.CircularProgressIndicator;

public class MaterialLoadingActivity extends AppCompatActivity {

    boolean ran = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_loading);
        // 구글에서 제공 하는 로딩 뷰

        CircularProgressIndicator progress_indi = findViewById(R.id.progress_indi);

        Button decate = findViewById(R.id.decate);
        Button indecate = findViewById(R.id.indecate);

        final int[] num = {0};
        decate.setOnClickListener(view -> {
            if (ran) {
                num[0] = 0;
                ran = false;
            }
            num[0] += 2;
            progress_indi.setProgressCompat(num[0],true);
        });

        // 진행도가 랜덤 > 정해진 진행도 > 랜덤으로 형태를 변경할 경우 오류 발생. 숨겼다가 > 랜덤변경 > 보여주기 순서로 해야 오류 없음.
        indecate.setOnClickListener(view -> {
            progress_indi.setVisibility(View.INVISIBLE);
            progress_indi.setIndeterminate(true);
            progress_indi.setVisibility(View.VISIBLE);
            ran = true;
        });

    }
}