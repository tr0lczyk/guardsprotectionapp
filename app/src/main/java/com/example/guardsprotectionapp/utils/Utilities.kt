package com.example.guardsprotectionapp.utils

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.guardsprotectionapp.MainActivity
import com.example.guardsprotectionapp.R
import java.text.SimpleDateFormat
import java.util.*


class Utilities {

    companion object {
        fun convertDateToString(date: Date): String {
            val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            return originalFormat.format(date)
        }
    }

    object NotificationHelper {

        fun displayNotification(context: Context, title: String, body: String) {

            val intent = Intent(context, MainActivity::class.java)

            val pendingIntent = PendingIntent.getActivity(
                context,
                100,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT
            )

            val mBuilder = NotificationCompat.Builder(context, MainActivity.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle(title)
                .setContentText(body)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)

            val mNotificationMgr = NotificationManagerCompat.from(context)
            mNotificationMgr.notify(1, mBuilder.build())

        }

    }

}
