package cn.androidstudy.course11;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private Intent intent;
    private myConn conn;
    MusicService.MyBinder binder;
    private boolean isGranted = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        conn = new myConn();
        intent = new Intent(this, MusicService.class);
        bindService(intent, conn, BIND_AUTO_CREATE);

        //intent = new Intent(this,MyService.class);
        grantedAndRequest();
    }
    //判断是否授权，如未授权，则申请授权
    private void grantedAndRequest() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permission  = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            //和下面语句等效
            //int permission  = checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE");
            //如果已授权
            if (permission == PackageManager.PERMISSION_GRANTED) {
                isGranted = true;
            }else{
                //未授权，弹对话框，申请授权
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            }
        }else{
            isGranted = true;
        }
    }
    /**
     * 处理权限请求结果
     *
     * @param requestCode
     *          请求权限时传入的请求码，用于区别是哪一次请求的
     *
     * @param permissions
     *          所请求的所有权限的数组
     *
     * @param grantResults
     *          权限授予结果，和 permissions 数组参数中的权限一一对应，元素值为两种情况，如下:
     *          授予: PackageManager.PERMISSION_GRANTED
     *          拒绝: PackageManager.PERMISSION_DENIED
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                isGranted = true;
            }
        }
    }
    public void btnClick1(View view){
        String pathway = "Music/hjbj.mp3";
        File SDpath = Environment.getExternalStorageDirectory();
        File file = new File(SDpath, pathway);
        String path = file.getAbsolutePath();
        switch (view.getId()) {
            case R.id.button:
                if (file.exists() && file.length() > 0) {
                    binder.play (path);
                } else {
                    Toast.makeText(this, "找不到音乐文件", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.button2:
                binder.pause();
                break;
            default:
                break;
        }
    }
    public void btnClick2(View view){
        String pathway = "Music/hjbj.mp3";
        File SDpath = Environment.getExternalStorageDirectory();
        File file = new File(SDpath, pathway);
        String path = file.getAbsolutePath();
        switch (view.getId()) {
            case R.id.button3:
                if (file.exists() && file.length() > 0) {
                    intent.putExtra("action","play");
                    intent.putExtra("path",path);
                    startService(intent);
                } else {
                    Toast.makeText(this, "找不到音乐文件", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.button4:
                intent.putExtra("action","pause");
                //intent.putExtra("path",path);
                stopService(intent);
                break;
            default:
                break;
        }
    }

    private class myConn implements ServiceConnection {
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (MusicService.MyBinder) service;
        }
        public void onServiceDisconnected(ComponentName name) {
        }
    }
}
