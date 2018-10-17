package com.example.will.permissionproject;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/**
 * 权限检测使用activity的继承方式.
 */
public class MainActivity extends CheckPermissionsActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_one).setOnClickListener(this);
        findViewById(R.id.btn_two).setOnClickListener(this);
    }

    @Override
    protected void beGivenPermission() {
        Toast.makeText(this, "开始收集用户的私人资料了啊,哈哈", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void beRefusePermission() {
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_one:
                checkPermission();
                break;
            case R.id.btn_two:
                startActivity(new Intent(this, Main2Activity.class));
                break;
        }
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= 23 && getApplicationInfo().targetSdkVersion >= 23) {
            if (isNeedCheck) {
                checkPermissions(externalsdcardpermission);
            }
        }
    }
}
