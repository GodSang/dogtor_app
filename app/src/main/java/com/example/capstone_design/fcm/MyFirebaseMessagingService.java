package com.example.capstone_design.fcm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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

    private static final String CHANNEL_NAME = "FCM";
    private static final String CHANNEL_DESC = "Firebase Cloud Messaging";

    private String TAG = "FirebaseService";

    String saveSharedName = "FCM_DB";

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

        // 앱이 foreground 상태에서 Notification을 받는 경우
        if(remoteMessage.getNotification() != null){
            sendNotification(remoteMessage.getNotification().getBody(), remoteMessage.getNotification().getTitle());


            timestamp = remoteMessage.getNotification().getEventTime();
            fcm_event_time = format.format(timestamp);
//
//            SharedPreferences saveShared = getSharedPreferences(saveSharedName, MODE_PRIVATE);
//            SharedPreferences.Editor sharedEditor = saveShared.edit();
//            sharedEditor.putString("fcm_body", remoteMessage.getNotification().getBody());
//            sharedEditor.putString("fcm_time", fcm_event_time);
//            Log.d("fcm_body", "push fcm_body: " + remoteMessage.getNotification().getBody());
//            sharedEditor.commit();

        }else{
            sendNotification(remoteMessage.getNotification().getBody(), remoteMessage.getNotification().getTitle());

            timestamp = remoteMessage.getNotification().getEventTime();
            fcm_event_time = format.format(timestamp);
//
//            SharedPreferences saveShared = getSharedPreferences(saveSharedName, MODE_PRIVATE);
//            SharedPreferences.Editor sharedEditor = saveShared.edit();
//            sharedEditor.putString("fcm_body", remoteMessage.getNotification().getBody());
//            sharedEditor.putString("fcm_time", fcm_event_time);
//            Log.d("fcm_body", "push fcm_body: " + remoteMessage.getNotification().getBody());
//            sharedEditor.commit();
        }
    }

    // FCM 메시지를 보내는 메시지
    private void sendNotification(String body, String title) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


        Log.d("hyeals_m", "일단 로그 찍어봄");

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        // 푸시알람 부가설정
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, getString(R.string.notification_channel_id))
                .setSmallIcon(R.drawable.dogtor_logo_icon)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(notificationSound)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.dogtor_logo_icon))
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    getString(R.string.notification_channel_id), CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription(CHANNEL_DESC);
            channel.setShowBadge(true);
            channel.canShowBadge();
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500});

            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }

        assert notificationManager != null;
        notificationManager.notify(0, notificationBuilder.build());

    }

}
