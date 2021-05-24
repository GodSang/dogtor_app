package com.example.capstone_design.fcm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.capstone_design.MainActivity;
import com.example.capstone_design.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class MyFirebaseMessagingService extends FirebaseMessagingService{
    Long timestamp;
    DateFormat format = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분");
    String fcm_event_time = "";

    private String TAG = "FirebaseService";

    // 파이버베이스 토큰 가져오기
    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.d(TAG, "new Token: " + token);

    }

    // 새로운 FCM 메시지가 있을 때 마다 메세지를 받음
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        if(remoteMessage.getData()!=null){
            sendNotification(remoteMessage.getData().get("body"), remoteMessage.getData().get("title"));
        }else{
            sendNotification("" ,"");
        }
    }

    // FCM 메시지를 보내는 메시지
    private void sendNotification(String body, String title) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        SharedPreferences saveShared = getSharedPreferences("DB", MODE_PRIVATE);
        SharedPreferences.Editor sharedEditor = saveShared.edit();

        sharedEditor.putString("fcm_body", body);
        sharedEditor.commit();

        String ChannelId = "FCM";
        String ChannelName = "알림";

        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        // 푸시알람 부가설정
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, ChannelId)
                .setSmallIcon(R.drawable.dogtor_logo_icon)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(notificationSound)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.dogtor_logo_icon));

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    ChannelId, ChannelName, NotificationManager.IMPORTANCE_HIGH);

            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0, notificationBuilder.build());

    }

}
