package com.example.will.permissionproject;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

/**
 * 讲解原生的系统方法,也是最正确的方法
 */
public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        findViewById(R.id.callphone).setOnClickListener(this);
    }

    public void testCall() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) !=
                PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(Main2Activity.this,
                    Manifest.permission.CALL_PHONE)) {

//            用于被拒绝了一次的,用户解释说明的.若是用户第一次选择了"禁止后不再询问"将不会出现的.
                showMissingPermissionDialog();

            } else {

//               此权限申请的dialog是由系统设计的不可自定义,并且返回键不可取消外部也不可取消,并且由于屏幕翻转导致的
//                生命周期问题将由系统控制我们不管.
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},
                        MY_PERMISSIONS_REQUEST_CALL_PHONE);

            }

        } else {

            callPhone();

        }
    }

    @SuppressLint("MissingPermission")
    public void callPhone() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + "10086");
        intent.setData(data);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callPhone();
            } else {
                // Permission Denied
                Toast.makeText(Main2Activity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                beRefusePermission();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    /**
     * 显示提示信息,这里的生命周期有问题,需要使用DialogFragment进行替代.
     */
    private void showMissingPermissionDialog() {

        /*AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.notifyTitle);
        builder.setMessage(R.string.notifyMsg);

        // 拒绝, 退出应用
        builder.setNegativeButton(R.string.cancel,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        beRefusePermission();
                    }
                });

        builder.setPositiveButton(R.string.setting,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startAppSettings();
                    }
                });

        builder.setCancelable(false);

        builder.show();*/

        showDialogFragment();
    }

    protected void beGivenPermission() {
        Toast.makeText(this, "开始收集用户的私人资料了啊,哈哈", Toast.LENGTH_SHORT).show();
    }

    protected void beRefusePermission() {
        finish();
    }

    private void showDialogFragment() {
        MyDialogFragment mdf = new MyDialogFragment();

        Bundle bundle = new Bundle();
        bundle.putString("billingData", "1111111111111");
        mdf.setArguments(bundle);
        mdf.setOnDialogClick(new MyDialogFragment.OnDialogClick() {
            @Override
            public void clickSure() {
                startAppSettings();
            }

            @Override
            public void clickCancel() {
                beRefusePermission();
            }
        });

        mdf.show(getSupportFragmentManager(), "df");
    }

    /**
     * 启动应用的设置
     *
     * @since 2.5.0
     */
    private void startAppSettings() {
        Intent intent = new Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.callphone:
                testCall();
                break;
        }
    }
}
