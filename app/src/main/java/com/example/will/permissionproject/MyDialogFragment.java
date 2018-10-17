package com.example.will.permissionproject;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

/**
 * 创建 DialogFragment 有两种方式：
 * 1:
 * 覆写其 onCreateDialog 方法
 * 应用场景：一般用于创建替代传统的 Dialog 对话框的场景，UI 简单，功能单一。
 * <p>
 * 2:
 * 方法:覆写其 onCreateView 方法
 * 应用场景：
 * 一般用于创建复杂内容弹窗或全屏展示效果的场景，UI 复杂，功能复杂，一般有网络请求等异步操作。
 */

/**
 * DialogFragment的使用像Dialog一样的简单、灵活，同时也保持了DialogFragment的优点，可以在任何的类中使用.
 * DialogFragment不需要我们手动的管理生命周期并且他有自己的回退栈.
 */

public class MyDialogFragment extends AppCompatDialogFragment implements View.OnClickListener {
    private Context mContext;

    /*---------------------------------简单方式---------------------------------------------------*/
    /*--------1.系统简单的-------------*/
    /*@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //为了样式统一和兼容性，可以使用 V7 包下的 AlertDialog.Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // 设置主题的构造方法
        // AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomDialog);
        builder.setTitle("注意：")
                .setMessage("是否退出应用？")
                .setPositiveButton("确定", null)
                .setNegativeButton("取消", null)
                .setCancelable(false);
        //builder.show(); // 不能在这里使用 show() 方法
        return builder.create();
    }*/

    /*--------2.自定义view-------------*/
   /* @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // 设置主题的构造方法
        // AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomDialog);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog, null);
        builder.setView(view);
        // Do Someting,eg: TextView tv = view.findViewById(R.id.tv);
        return builder.create();
    }*/


    /*---------------------------------无标题栏两种方法---------------------------------------------*/
    /*--------1,onCreateDialog------------*/
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        LayoutInflater inflater = getActivity().getLayoutInflater();
//        View view = inflater.inflate(R.layout.fragment_dialog, null);
//        Dialog dialog = new Dialog(getActivity());
//        // 关闭标题栏，setContentView() 之前调用
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(view);
//        dialog.setCanceledOnTouchOutside(true);
//        return dialog;
//    }

    /*--------2,onCreate------------*/
    /*@Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        */

    /**
     * setStyle() 的第一个参数有四个可选值：
     * STYLE_NORMAL|STYLE_NO_TITLE|STYLE_NO_FRAME|STYLE_NO_INPUT
     * 其中 STYLE_NO_TITLE 和 STYLE_NO_FRAME 可以关闭标题栏
     * 每一个参数的详细用途可以直接看 Android 源码的说明
     *//*
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.Dialog_FullScreen);
    }*/


    /*---------------------------------创建复杂UI的方式,采用这种方式即可---------------------------------------------*/
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initBackground();
        View v = inflater.inflate(R.layout.dialog, container, false);
        initView(v);
        return v;
    }

    private void initView(View v) {
        v.findViewById(R.id.sure).setOnClickListener(this);
        v.findViewById(R.id.cancel).setOnClickListener(this);
    }

    private void initBackground() {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sure:
                if (mOnDialogClick != null) {
                    mOnDialogClick.clickSure();
                }
                break;
            case R.id.cancel:
                if (mOnDialogClick != null) {
                    mOnDialogClick.clickCancel();
                }
                break;
        }
    }

    interface OnDialogClick {
        void clickSure();

        void clickCancel();
    }

    public void setOnDialogClick(OnDialogClick onDialogClick) {
        mOnDialogClick = onDialogClick;
    }

    OnDialogClick mOnDialogClick;
}
