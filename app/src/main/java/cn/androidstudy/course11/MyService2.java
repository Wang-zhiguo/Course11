package cn.androidstudy.course11;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService2 extends Service {
    private static final String TAG = "MyService2";
    public MyService2() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind: ");
        return new MyBinds();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "onUnbind: ");
        return super.onUnbind(intent);
    }

    class MyBinds extends Binder{
        public void callMethodInService(){
            methodInService();
        }
    }

    private void methodInService() {
        Log.i(TAG, "methodInService: ");
    }
}
