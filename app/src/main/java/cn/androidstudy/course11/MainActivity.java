package cn.androidstudy.course11;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private boolean isStartService = false;
    private boolean isBindService = false;
    private MyService2.MyBinds myBinds;
    private MyConn myConn;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void startService(View view){
        intent = new Intent(this,MyService.class);
        Button btn = (Button)view;
        if(isStartService){
            btn.setText("Start Service");
            isStartService=false;
            stopService(intent);
        }else {
            btn.setText("Stop Service");
            isStartService=true;
            startService(intent);
        }
    }

    private class MyConn implements ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBinds = (MyService2.MyBinds) service;
            Log.i(TAG, "onServiceConnected: ");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG, "onServiceDisconnected: ");
        }
    }

    public void bindService(View view){
        intent = new Intent(this,MyService2.class);
        Button btn = (Button)view;
        if(isBindService){
            btn.setText("Bind Service");
            isBindService=false;
            unbindService(myConn);
            myConn=null;
        }else {
            btn.setText("Unbind Service");
            isBindService=true;
            if(myConn==null){
                myConn=new MyConn();
            }
            bindService(intent,myConn,BIND_AUTO_CREATE);
        }
    }
    public void callMethod(View view){
        myBinds.callMethodInService();
    }

}
