package com.example.detailsapp

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.RemoteInput
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.room.Room
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class add : AppCompatActivity() {

    lateinit var database: DetailsDatabase
    lateinit var notificationManager : NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    lateinit var pendingIntent: PendingIntent
    lateinit var soundUri: Uri
    lateinit var audioAttr: AudioAttributes
    private val channelId = "My Channel ID"
    private val description = "New Detail Added"
    private val title="Details sheet has some changes"

    val myKey = "Remote Key"
    val notificationId =1234

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add)
        database = Room.databaseBuilder(applicationContext, DetailsDatabase::class.java,"detailsDb").fallbackToDestructiveMigration().build()

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        var btn = findViewById<Button>(R.id.addBtn)
        var cancel = findViewById<Button>(R.id.cancel)

        btn.setOnClickListener(){
            var id = findViewById<EditText>(R.id.idIn)
            var phone = findViewById<EditText>(R.id.phoneID)

            var name = findViewById<EditText>(R.id.nameId)
            var age = findViewById<EditText>(R.id.ageID)
            GlobalScope.launch {
                database.DetailsDAO().insert(Details(id.text.toString().toLong(),name.text.toString(),phone.text.toString(),age.text.toString()))
            }

            val intent1 = Intent(this, NotificationViewExample::class.java)
            pendingIntent = PendingIntent.getActivity(this,0,intent1,PendingIntent.FLAG_MUTABLE)

            soundUri = Uri.parse(
                ContentResolver.SCHEME_ANDROID_RESOURCE + "://"+
                        applicationContext.packageName+"/"+R.raw.add)
            audioAttr = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build()

            // remoteCollapsedViews = RemoteViews(packageName, R.layout.activity_splash_screen_example)
            //remoteExpandedViews = RemoteViews(packageName, R.layout.activity_splash_screen_example_main)
            myNotificationChannel()



            notificationManager.notify(notificationId,builder.build())

            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        cancel.setOnClickListener(){
            startActivity(Intent(this,MainActivity::class.java))
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun myNotificationChannel(){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            run {
                notificationChannel = NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
                notificationChannel.enableLights(true)
                notificationChannel.lightColor = Color.GREEN
                notificationChannel.enableVibration(false)
                notificationChannel.setSound(soundUri, audioAttr)
                notificationManager.createNotificationChannel(notificationChannel)

                builder = Notification.Builder(this, channelId)
                    .setSmallIcon(R.drawable.icon)
                    .setContentTitle(title)
                    .setContentText(description)
                    .setLargeIcon(BitmapFactory.decodeResource(this.resources,R.drawable.list))
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)

            }

        else
            run {
                builder = Notification.Builder(this, channelId)
                    .setSmallIcon(android.R.drawable.btn_star)
                    .setContentTitle(title)
                    .setContentText(description)
                    .setLargeIcon(
                        BitmapFactory.decodeResource(
                            this.resources,
                            R.drawable.icon2
                        )
                    )
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
            }
    }


}