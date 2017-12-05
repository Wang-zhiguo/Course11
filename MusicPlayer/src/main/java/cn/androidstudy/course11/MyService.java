package cn.androidstudy.course11;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MyService extends Service {
    public MediaPlayer mediaPlayer;
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getStringExtra("action");
        String path = intent.getStringExtra("path");
        if ("play".equals(action)){
            play(path);
        }else if("pause".equals(action)){
            pause();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
    }
    public void play(String path) {
        try {
            if (mediaPlayer == null) {
                // 创建一个MediaPlayer播放器
                mediaPlayer = new MediaPlayer();
                // 指定参数为音频文件
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                // 指定播放的路径
                mediaPlayer.setDataSource(path);
                // 准备播放
                mediaPlayer.prepare();
                mediaPlayer.setOnPreparedListener(
                        new MediaPlayer.OnPreparedListener() {
                            public void onPrepared(MediaPlayer mp) {
                                // 开始播放
                                mediaPlayer.start();
                            }
                        });
            } else {
                int position = getCurrentProgress();
                mediaPlayer.seekTo(position);
                try {
                    mediaPlayer.prepare();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mediaPlayer.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // 暂停播放
    public void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause(); // 暂停播放
        } else if (mediaPlayer != null && (!mediaPlayer.isPlaying())) {
            mediaPlayer.start();
        }
    }
    // 获取当前进度
    public int getCurrentProgress() {
        if (mediaPlayer != null & mediaPlayer.isPlaying()) {
            return mediaPlayer.getCurrentPosition();
        } else if (mediaPlayer != null & (!mediaPlayer.isPlaying())) {
            return mediaPlayer.getCurrentPosition();
        }
        return 0;
    }

}
