package com.example.capstone_design.fcm;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.capstone_design.MainActivity;
import com.example.capstone_design.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
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

        // 앱이 foreground 상태에서 Notification을 받는 경우
        if(remoteMessage.getNotification() != null){
            sendNotification(remoteMessage.getNotification().getBody(), remoteMessage.getNotification().getTitle());
            Log.d(TAG, "GET MESSAGE: "+ remoteMessage.getNotification().getBody());
            Log.d(TAG, "GET MESSAGE: "+ remoteMessage.getNotification().getTitle());
        }else{
            sendNotification(remoteMessage.getNotification().getBody(), remoteMessage.getNotification().getTitle());
            Log.d(TAG, "GET MESSAGE: "+ remoteMessage.getNotification().getBody());
            Log.d(TAG, "GET MESSAGE: "+ remoteMessage.getNotification().getTitle());
        }
    }

    // FCM 메시지를 보내는 메시지
    private void sendNotification(String body, String title) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        String channelId = "Notification";

        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        // 푸시알람 부가설정
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.dogtor_logo_icon)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(notificationSound)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());

    }

}
