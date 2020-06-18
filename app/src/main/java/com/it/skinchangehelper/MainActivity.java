package com.it.skinchangehelper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin_dynamic);

        //模拟动态换肤开关
        findViewById(R.id.btn_changeskin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               skinDynamic();
            }
        });


        findViewById(R.id.btn_default).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skinDefault();
            }
        });
    }

}
