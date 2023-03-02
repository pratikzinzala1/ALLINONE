package com.example.myapplication.utils

import android.annotation.SuppressLint
import android.app.*
import android.content.ClipData
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.*
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.FileProvider
import com.pratik.filedownloader.R
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.net.URL


class TestService : Service() {
    private var serviceLooper: Looper? = null
    private var serviceHandler: ServiceHandler? = null

    private var CHANNEL_ID = "99"

    lateinit var destinationFilename: String
    lateinit var extention: String
    private var progression = 0


    private inner class ServiceHandler(looper: Looper) : Handler(looper) {

        override fun handleMessage(msg: Message) {
            // Normally we would do some work here, like download a file.
            // For our sample, we just sleep for 5 seconds.

            val data = msg.data
            val url = data.getString("URL")!!
            extention = getMimeType(applicationContext, Uri.parse(url))!!

            destinationFilename =
                Environment.getExternalStorageDirectory().path + "/Download" + File.separatorChar + "abc." + extention



            try {
                creatnotification()
                val notification = returnnotification().setProgress(100, progression, false)
                startForeground(1, notification.build())
                bufferdownloadFile(URL(url))

            } catch (e: InterruptedException) {
                // Restore interrupt status.
                Thread.currentThread().interrupt()
            }

            // Stop the service using the startId, so that we don't stop
            // the service in the middle of handling another job
            stopSelf(msg.arg1)
        }
    }

    override fun onCreate() {

        HandlerThread("ServiceStartArguments", android.os.Process.THREAD_PRIORITY_DEFAULT).apply {
            start()
            // Get the HandlerThread's Looper and use it for our Handler
            serviceLooper = looper
            serviceHandler = ServiceHandler(looper)
        }
    }

    @SuppressLint("MissingPermission")
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val i = intent
        val Url = i.getStringExtra("URL")
        Log.d("DATA", Url.toString())
        val b = Bundle()
        b.putString("URL", Url)
        Toast.makeText(this, "Download Strated", Toast.LENGTH_SHORT).show()



        serviceHandler?.obtainMessage()?.also { msg ->
            msg.arg1 = startId
            msg.data = b
            serviceHandler?.sendMessage(msg)
        }

        createNotificationChannel()




        return START_NOT_STICKY
    }


    @SuppressLint("MissingPermission")
    fun creatnotification() {


        val notification = returnnotification().setProgress(100, progression, false)
        with(NotificationManagerCompat.from(applicationContext)) {
            notify(1, notification.build())
        }


    }

    @SuppressLint("MissingPermission", "IntentReset")
    fun returnnotification(): NotificationCompat.Builder {

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(getString(R.string.notification_title))
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOngoing(true)
            .setOnlyAlertOnce(true)




        return notification
    }


    @SuppressLint("MissingPermission")
    override fun onDestroy() {
        super.onDestroy()
        val fileURI: Uri = FileProvider.getUriForFile(
            this,
            getApplicationContext().getPackageName() + ".provider", File(destinationFilename)
        )


        val notificationIntent = Intent()
        notificationIntent.action = Intent.ACTION_VIEW
        notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        notificationIntent.clipData = ClipData.newRawUri("", fileURI)
        notificationIntent.setDataAndType(fileURI, "*/*")
        notificationIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        notificationIntent.addCategory(Intent.CATEGORY_DEFAULT)

        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(getString(R.string.notification_done))
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)


        with(NotificationManagerCompat.from(applicationContext)) {
            notify(1, notification.build())
        }
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show()
    }


    fun bufferdownloadFile(url: URL) {

        var fileLength = url.openConnection().contentLength

        url.openStream().use { inp ->
            BufferedInputStream(inp).use { bis ->
                FileOutputStream(destinationFilename).use { fos ->
                    val data = ByteArray(1024)
                    var count: Int


                    while (bis.read(data, 0, 1024).also { count = it } != -1) {
                        val mediaFile: File = File(Uri.parse(destinationFilename).getPath()!!)
                        val fileSizeInBytes = mediaFile.length()
                        progression = ((fileSizeInBytes * 100) / fileLength).toInt()
                        Log.d("SIZE", progression.toString())
                        fos.write(data, 0, count)

                        creatnotification()


                    }
                }

            }
        }


    }


    fun getMimeType(context: Context, uri: Uri): String? {
        val extension: String?

        //Check uri format to avoid null
        extension = if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
            //If scheme is a content
            val mime = MimeTypeMap.getSingleton()
            mime.getExtensionFromMimeType(context.getContentResolver().getType(uri))
        } else {
            //If scheme is a File
            //This will replace white spaces with %20 and also other special characters. This will avoid returning null values on file name with spaces and special characters.
            MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(File(uri.path)).toString())
        }
        return extension
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID, "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager!!.createNotificationChannel(serviceChannel)
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        // We don't provide binding, so return null
        return null
    }
}
