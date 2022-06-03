package com.example.budilnik

import android.app.PendingIntent.getActivity
import android.content.Context
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.budilnik.controller.AlarmController
import com.example.budilnik.controller.MyReceiver
import com.example.budilnik.databinding.ActivityExperimentBinding
import android.media.Ringtone
import android.net.Uri


class ExperimentActivity : AppCompatActivity() {
    lateinit var mContext: Context
    private lateinit var alarmController: AlarmController
    private lateinit var dd: String

    private var mediaPlayer: MediaPlayer?=null
    private lateinit var binding: ActivityExperimentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExperimentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        alarmController = AlarmController(this)

        val intent = intent
        val message = intent.getStringExtra("NotificationMessage")








}

}