package com.it.skinchangehelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.it.skinchangehelper.base.BaseActivity;
import com.it.skinchangehelper.simple.LocalActivity;
import com.it.skinchangehelper.simple.SettingActivity;
import com.it.skinchangehelper.simple.TestActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin_dynamic);

        //跳转到动态换肤设置页面
        findViewById(R.id.btn_setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SettingActivity.class));
            }
        });

        //跳转到app内置换肤测试页面
        findViewById(R.id.btn_local).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LocalActivity.class));
            }
        });

        //跳转下个页面
        findViewById(R.id.btn_jump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), TestActivity.class));
            }
        });
    }
}
