package com.webmobrilweatherapp.activities.usernotification


import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.activities.AppConstant
import com.webmobrilweatherapp.activities.CommonMethod
import com.webmobrilweatherapp.activities.ForecastScreen
import com.webmobrilweatherapp.activities.MetrologistAlertActivity
import com.webmobrilweatherapp.activities.NotificationActivity
import com.webmobrilweatherapp.activities.PostActivity
import com.webmobrilweatherapp.activities.UserAlertActivity
import com.webmobrilweatherapp.activities.UserChatActivity
import com.webmobrilweatherapp.activities.metrologistactivity.MetrologistChatActivity
import com.webmobrilweatherapp.activities.metrologistactivity.MetrologistForecastChallengeActivity
import com.webmobrilweatherapp.activities.metrologistactivity.MetrologistNotificationActivity
import com.webmobrilweatherapp.activities.metrologistactivity.MetrologistPostActivity
import com.webmobrilweatherapp.model.notification.NotificationResponse
import com.webmobrilweatherapp.utilise.NotificationUtils
import java.util.Objects


class MyFireBaseMessagingService : FirebaseMessagingService() {

    //2 user
    //3 metrologist

    var notificationType=0
    var receiverid=0
    var senderId=0
    var userId=0
    var comment=0
    var like=0
    var userType=0
    var applicationId="0"
    private val notificationIcon: Int

        get() {
            val useWhiteIcon = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
            return if (useWhiteIcon) R.drawable.appicon else R.drawable.appicon
        }


    @SuppressLint("SuspiciousIndentation")
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        applicationId = CommonMethod.getInstance(this).getPreference(AppConstant.Key_ApplicationId,"0")

        /* if (userType == 2) {*/
     //   Toast.makeText(this, applicationId.toString(), Toast.LENGTH_LONG).show()


        Log.e("ApplicationId", "" + applicationId)
        Log.e("MyFireBase", "" + "engio"+ remoteMessage.data.toString())
        Log.e("MyFireBase", "" + "engio"+ Gson().toJson(remoteMessage.notification.toString()))

//        Log.e("MyFireBase123", "" + "engio"+ Gson().toJson(remoteMessage))
        notificationType = remoteMessage.data["notificationType"]?.toIntOrNull() ?: 0





        // Notify the home screen to update badge



     /*   if(applicationId.equals("2")) {
            if (remoteMessage.data["user_type"] != "") {

            } else {
                userType = remoteMessage.data["user_type"]!!.toInt()

            }




            if (remoteMessage.data["receiver_id"] != "") {

            } else {
                receiverid = remoteMessage.data["receiver_id"]!!.toInt()
            }


            if (remoteMessage.data["sender_id"] != "") {

            } else {
                senderId = remoteMessage.data["sender_id"]!!.toInt()
            }
            if (remoteMessage.data["user_id"] != "") {

            } else {
                userId = remoteMessage.data["user_id"]!!.toInt()

            }

            if (remoteMessage.data["post_id"] != "") {

            } else {
                like = remoteMessage.data["post_id"]!!.toInt()

            }
        }*/
       /*  if(applicationId.equals("1"))
        {*/


            if (remoteMessage.data.containsKey("user_type")) {

               // userType = remoteMessage.data["user_type"]!!.toInt()
                if (remoteMessage.data["user_type"] == "") {

                } else {
                    userType = remoteMessage.data["user_type"]!!.toInt()

                }

            }

            if (remoteMessage.data.containsKey("receiver_id")) {
               // receiverid = remoteMessage.data["receiver_id"]!!.toInt()
                if (remoteMessage.data["receiver_id"] == "") {

                } else {
                    receiverid = remoteMessage.data["receiver_id"]!!.toInt()
                }

            }


            if (remoteMessage.data.containsKey("sender_id")) {

                //senderId = remoteMessage.data["sender_id"]!!.toInt()
                if (remoteMessage.data["sender_id"] == "") {

                } else {
                    senderId = remoteMessage.data["sender_id"]!!.toInt()
                }

            }

            if (remoteMessage.data.containsKey("user_id")) {

               // userId = remoteMessage.data["user_id"]!!.toInt()
                if (remoteMessage.data["user_id"] == "") {

                } else {
                    userId = remoteMessage.data["user_id"]!!.toInt()

                }

            }

            if (remoteMessage.data.containsKey("post_id")) {
               // like = remoteMessage.data["post_id"]!!.toInt()
                if (remoteMessage.data["post_id"] == "") {

                } else {
                    like = remoteMessage.data["post_id"]!!.toInt()

                }
            }
      //  }


        Log.e("notificationType", notificationType.toString())


        try {
            val response =
                remoteMessage.data.toString().substring(0, remoteMessage.data.toString().length - 1)
            val editResponse = response.replace("{headers={}, original=", "").toString()
            try {

                val pushResponse: NotificationResponse =
                    Gson().fromJson(editResponse, NotificationResponse::class.java)

                Log.e("pushResponse body", "---" + pushResponse.message!!)
                sendNotification(pushResponse.message.toString())

            } catch (e: Exception) {
                e.printStackTrace()
                val title = remoteMessage.data["title"].toString()
                sendNotification(title)
            }


        } catch (e: Exception) {
            val title = remoteMessage.data["title"].toString()
            sendNotification(title)
        }

    }


    override fun onNewToken(p0: String) {

        CommonMethod.getInstance(applicationContext).deviceToken = p0
        Log.e(TAG, "Refreshed token: $p0")


    }

    private fun sendNotification(title: String) {

        if (applicationId.equals("1")) {


            val intent = Intent("com.webmobrilweatherapp.NOTIFICATION_COUNT_UPDATED")
            LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent)
            NotificationUtils.incrementNotificationCount(applicationContext)

            if (notificationType == 7) {
                val intent: Intent? =
                    Intent(this, NotificationActivity::class.java)

                intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

                var pendingIntent: PendingIntent? = null
                pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE)
                } else {
                    PendingIntent.getActivity(this, 0, intent,
                        PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE)
                }


                //   val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)


                val channelId = getString(R.string.app_name)
                val defaultSoundUri =
                    RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                val notificationBuilder = NotificationCompat.Builder(this, channelId)
                    .setSmallIcon(notificationIcon)
                    .setContentTitle("Weather Platform")
                    .setContentText(title)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent)


                val notificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                // Since android Oreo notification channel is needed.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val channel = NotificationChannel(
                        channelId,
                        getString(R.string.app_name),
                        NotificationManager.IMPORTANCE_DEFAULT
                    )
                    Objects.requireNonNull(notificationManager).createNotificationChannel(channel)
                }
                Objects.requireNonNull(notificationManager).notify(0, notificationBuilder.build())
            } else if (notificationType == 5) {

                var intent = Intent(this, UserChatActivity::class.java)
                intent.putExtra("userIds", senderId)
                intent.putExtra("UserType",userType)


                intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                var pendingIntent: PendingIntent? = null
                pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE)
                } else {
                    PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
                }
                // val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

                val channelId = getString(R.string.app_name)
                val defaultSoundUri =
                    RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                val notificationBuilder = NotificationCompat.Builder(this, channelId)
                    .setSmallIcon(notificationIcon)
                    .setContentTitle("Weather Platform")
                    .setContentText(title)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent)


                val notificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


                // Since android Oreo notification channel is needed.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val channel = NotificationChannel(
                        channelId,
                        getString(R.string.app_name),
                        NotificationManager.IMPORTANCE_DEFAULT
                    )
                    Objects.requireNonNull(notificationManager).createNotificationChannel(channel)
                }
                Objects.requireNonNull(notificationManager).notify(0, notificationBuilder.build())
            } else if (notificationType == 8) {

/*            var intent = Intent(this, NotificationActivity::class.java)
            // intent.putExtra("userIds", userId)
          //  intent.putExtra("type", 0)

            intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

           var pendingIntent: PendingIntent? = null
            pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE)
                } else {
                    PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
                }

            }

            else {



                PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

            }*/


                val channelId = getString(R.string.app_name)
                val defaultSoundUri =
                    RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                val notificationBuilder = NotificationCompat.Builder(this, channelId)
                    .setSmallIcon(notificationIcon)
                    .setContentTitle("Weather Platform")
                    .setContentText(title)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                // .setContentIntent(pendingIntent)

                val notificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


                // Since android Oreo notification channel is needed.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val channel = NotificationChannel(
                        channelId,
                        getString(R.string.app_name),
                        NotificationManager.IMPORTANCE_DEFAULT
                    )


                    Objects.requireNonNull(notificationManager).createNotificationChannel(channel)
                }
                Objects.requireNonNull(notificationManager).notify(0, notificationBuilder.build())
            } else if (notificationType == 10 || notificationType == 9) {

                val intent = Intent(this, PostActivity::class.java)
                intent.putExtra("PostId", like.toString());

                intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                var pendingIntent: PendingIntent? = null
                pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE)
                } else {
                    PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
                }

                //val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

                val channelId = getString(R.string.app_name)
                val defaultSoundUri =
                    RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                val notificationBuilder = NotificationCompat.Builder(this, channelId)
                    .setSmallIcon(notificationIcon)
                    .setContentTitle("Weather Platform")
                    .setContentText(title)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent)


                val notificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


                // Since android Oreo notification channel is needed.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val channel = NotificationChannel(
                        channelId,
                        getString(R.string.app_name),
                        NotificationManager.IMPORTANCE_DEFAULT

                    )
                    Objects.requireNonNull(notificationManager).createNotificationChannel(channel)
                }

                Objects.requireNonNull(notificationManager).notify(0, notificationBuilder.build())

            } else if (notificationType == 6) {
                val intent = Intent(this, ForecastScreen::class.java)

                intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK


                var pendingIntent: PendingIntent? = null
                pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE)
                } else {
                    PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
                }
                //  val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

                val channelId = getString(R.string.app_name)
                val defaultSoundUri =
                    RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                val notificationBuilder = NotificationCompat.Builder(this, channelId)
                    .setSmallIcon(notificationIcon)
                    .setContentTitle("Weather Platform")
                    .setContentText(title)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent)


                val notificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


                // Since android Oreo notification channel is needed.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val channel = NotificationChannel(
                        channelId,
                        getString(R.string.app_name),
                        NotificationManager.IMPORTANCE_DEFAULT
                    )
                    Objects.requireNonNull(notificationManager).createNotificationChannel(channel)
                }
                Objects.requireNonNull(notificationManager).notify(0, notificationBuilder.build())

            }


        }
        else if (notificationType == 15) {
            val intent = Intent(this, UserAlertActivity::class.java)

            intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK


            var pendingIntent: PendingIntent? = null
            pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE)
            } else {
                PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
            }
            //  val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

            val channelId = getString(R.string.app_name)
            val defaultSoundUri =
                RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val notificationBuilder = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(notificationIcon)
                .setContentTitle("Weather Platform")
                .setContentText(title)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)


            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


            // Since android Oreo notification channel is needed.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    channelId,
                    getString(R.string.app_name),
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                Objects.requireNonNull(notificationManager).createNotificationChannel(channel)
            }
            Objects.requireNonNull(notificationManager).notify(0, notificationBuilder.build())

        }

        //metrologist Notification
        else if(applicationId.equals("2"))
        {

            val intent = Intent("com.webmobrilweatherapp.NOTIFICATION_COUNT_UPDATED")
            LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent)
            NotificationUtils.incrementNotificationCountMeterological(applicationContext)
            if (notificationType == 7) {
                val intent: Intent? =
                    Intent(this, MetrologistNotificationActivity::class.java)

                intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

                var pendingIntent: PendingIntent? = null
                pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE)
                } else {
                    PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
                }


                //   val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)


                val channelId = getString(R.string.app_name)
                val defaultSoundUri =
                    RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                val notificationBuilder = NotificationCompat.Builder(this, channelId)
                    .setSmallIcon(notificationIcon)
                    .setContentTitle("Weather Platform")
                    .setContentText(title)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent)


                val notificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                // Since android Oreo notification channel is needed.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val channel = NotificationChannel(
                        channelId,
                        getString(R.string.app_name),
                        NotificationManager.IMPORTANCE_DEFAULT
                    )
                    Objects.requireNonNull(notificationManager).createNotificationChannel(channel)
                }
                Objects.requireNonNull(notificationManager).notify(0, notificationBuilder.build())
            }
            else if (notificationType == 5) {

                var intent = Intent(this, MetrologistChatActivity::class.java)
                intent.putExtra("userIds", senderId)
                intent.putExtra("UserType",userType)


                intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                var pendingIntent: PendingIntent? = null
                pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE)
                } else {
                    PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
                }
                // val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

                val channelId = getString(R.string.app_name)
                val defaultSoundUri =
                    RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                val notificationBuilder = NotificationCompat.Builder(this, channelId)
                    .setSmallIcon(notificationIcon)
                    .setContentTitle("Weather Platform")
                    .setContentText(title)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent)


                val notificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


                // Since android Oreo notification channel is needed.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val channel = NotificationChannel(
                        channelId,
                        getString(R.string.app_name),
                        NotificationManager.IMPORTANCE_DEFAULT
                    )
                    Objects.requireNonNull(notificationManager).createNotificationChannel(channel)
                }
                Objects.requireNonNull(notificationManager).notify(0, notificationBuilder.build())
            }
            else if (notificationType == 10 || notificationType == 9) {

                val intent = Intent(this, MetrologistPostActivity::class.java)
                intent.putExtra("PostId", like.toString());

                intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                var pendingIntent: PendingIntent? = null
                pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE)
                } else {
                    PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
                }

                //val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

                val channelId = getString(R.string.app_name)
                val defaultSoundUri =
                    RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                val notificationBuilder = NotificationCompat.Builder(this, channelId)
                    .setSmallIcon(notificationIcon)
                    .setContentTitle("Weather Platform")
                    .setContentText(title)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent)


                val notificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


                // Since android Oreo notification channel is needed.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val channel = NotificationChannel(
                        channelId,
                        getString(R.string.app_name),
                        NotificationManager.IMPORTANCE_DEFAULT

                    )
                    Objects.requireNonNull(notificationManager).createNotificationChannel(channel)
                }

                Objects.requireNonNull(notificationManager).notify(0, notificationBuilder.build())

            }
            else if (notificationType == 6) {
                val intent = Intent(this, MetrologistForecastChallengeActivity::class.java)

                intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK


                var pendingIntent: PendingIntent? = null
                pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE)
                } else {
                    PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
                }
                //  val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

                val channelId = getString(R.string.app_name)
                val defaultSoundUri =
                    RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                val notificationBuilder = NotificationCompat.Builder(this, channelId)
                    .setSmallIcon(notificationIcon)
                    .setContentTitle("Weather Platform")
                    .setContentText(title)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent)


                val notificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


                // Since android Oreo notification channel is needed.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val channel = NotificationChannel(
                        channelId,
                        getString(R.string.app_name),
                        NotificationManager.IMPORTANCE_DEFAULT
                    )
                    Objects.requireNonNull(notificationManager).createNotificationChannel(channel)
                }
                Objects.requireNonNull(notificationManager).notify(0, notificationBuilder.build())

            }

            else if (notificationType == 15) {
                val intent = Intent(this, MetrologistAlertActivity::class.java)

                intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK


                var pendingIntent: PendingIntent? = null
                pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE)
                } else {
                    PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
                }
                //  val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

                val channelId = getString(R.string.app_name)
                val defaultSoundUri =
                    RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                val notificationBuilder = NotificationCompat.Builder(this, channelId)
                    .setSmallIcon(notificationIcon)
                    .setContentTitle("Weather Platform")
                    .setContentText(title)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent)


                val notificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


                // Since android Oreo notification channel is needed.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val channel = NotificationChannel(
                        channelId,
                        getString(R.string.app_name),
                        NotificationManager.IMPORTANCE_DEFAULT
                    )
                    Objects.requireNonNull(notificationManager).createNotificationChannel(channel)
                }
                Objects.requireNonNull(notificationManager).notify(0, notificationBuilder.build())

            }

        }
    }

    companion object {
        private val TAG = MyFireBaseMessagingService::class.java.simpleName
    }

}
