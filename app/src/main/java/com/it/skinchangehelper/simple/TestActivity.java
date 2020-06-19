package com.it.skinchangehelper.simple;

import android.os.Bundle;

import com.it.skinchangehelper.R;
import com.it.skinchangehelper.base.BaseActivity;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

/**
 * Created by lgc on 2020-06-19.
 *
 * @Author lgc
 * @Description
 */
public class TestActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.add(R.id.container, new TestFragment()).commit();


    }


}
