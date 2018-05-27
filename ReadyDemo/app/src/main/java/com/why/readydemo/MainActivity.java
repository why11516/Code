package com.why.readydemo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.why.readydemo.fragment.CameraFragmentJava;
import com.why.readydemo.fragment.PullFragment;
import com.why.readydemo.manager.SuspensionWindowManager;
import com.why.readydemo.ui.SuspensionWindow;

public class MainActivity extends FragmentActivity {
    private static final String CHOOSE_TV_FRAGMENT = "CHOOSE_TV_FRAGMENT";
    private SuspensionWindow mSWindow;
    private SuspensionWindowManager mSWindowManager;
    private RecyclerView recyclerView;
    private Context mContext;
    private WindowManager mWindowManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        //textRecyclerView();
        //addWindow();
        requestPower();
        initFragment();
    }

    public void requestPower() {
        //判断是否已经赋予权限
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            //如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true。
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {//这里可以写个对话框之类的项向用户解释为什么要申请权限，并在对话框的确认键后续再次申请权限
            } else {
                //申请权限，字符串数组内是一个或多个要申请的权限，1是申请权限结果的返回参数，在onRequestPermissionsResult可以得知申请结果
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA,}, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "" + "权限" + permissions[i] + "申请成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "" + "权限" + permissions[i] + "申请失败", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mPullFragment.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    private PullFragment mPullFragment;
    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        mPullFragment = new PullFragment();
        transaction
                .add(R.id.fragment_pull, new CameraFragmentJava())
                .add(R.id.fragment_pull, mPullFragment, CHOOSE_TV_FRAGMENT)
                .commit();
    }

    private void addWindow() {
        mWindowManager = getWindowManager();
        mContext = getApplicationContext();
        mSWindowManager = SuspensionWindowManager.getSuspensionWindowManager(mContext);
        mSWindow = mSWindowManager.getSuspensionWindow(mContext, mWindowManager);
    }

//    private void textRecyclerView() {
//        recyclerView = (RecyclerView) findViewById(R.id.rv_text);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
//        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.addItemDecoration(new SpaceItemDecoration(20));
//        new DefaultItemAnimator();
//        recyclerView.setItemAnimator(new ViewItemAnimator());
//        recyclerView.setAdapter(new RecyclerViewAdapter(getApplicationContext()));
//    }
//    class Inner {}
}
