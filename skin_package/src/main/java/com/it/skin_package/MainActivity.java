package com.it.skin_package;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

//这个Model仅仅是用来打包的，作为皮肤包，只需要在res配置即可，
// 实际不需要JAVA类，这里是为了测试不报错而已
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
