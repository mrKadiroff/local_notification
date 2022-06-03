package com.example.budilnik.controller

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.budilnik.R
import android.app.PendingIntent
import com.example.budilnik.ExperimentActivity
import com.example.budilnik.MainActivity


class MyReceiver : BroadcastReceiver() {
    private val TAG = "MyReceiver"

    @SuppressLint("InvalidWakeLockTag")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.e(TAG, "onReceive: taskComplete")

        var mp: MediaPlayer = MediaPlayer.create(context,R.raw.moneyhesit)
        mp.start()



        val fullScreenIntent = Intent(context, MainActivity::class.java)
        val fullScreenPendingIntent = PendingIntent.getActivity(context, 0,
            fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val taskName = intent?.getStringExtra("taskName")

        val notificationIntent = Intent(context, ExperimentActivity::class.java)
        notificationIntent.putExtra("NotificationMessage", taskName)
        notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        notificationIntent.action = Intent.ACTION_MAIN
        notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        val resultIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0)





        val defaultSoundUri: Uri =
            RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationManager =
            context?.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val builder = NotificationCompat.Builder(context, "channelId")
        builder.setSmallIcon(R.drawable.ic_baseline_alarm_24)
        builder.setContentTitle("Do your task")
        builder.setContentText(taskName)
        builder.setFullScreenIntent(fullScreenPendingIntent, true)
        builder.setSound(defaultSoundUri, AudioManager.STREAM_NOTIFICATION)
        builder.setContentIntent(resultIntent)
        builder.setAutoCancel(true)
        val notification = builder.build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel("channelId", "Name", importance)
            mChannel.description = "descriptionText"
            notificationManager.createNotificationChannel(mChannel)
        }
        notificationManager.notify(1, notification)
        val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        val wl: PowerManager.WakeLock =
            pm.newWakeLock(PowerManager.FULL_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG")
        wl.acquire(15000)
    }
    fun stop(mp: MediaPlayer) {
        mp.stop()
    }
}