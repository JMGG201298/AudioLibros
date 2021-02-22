package com.example.audiolibros;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.security.Provider;
import java.util.List;
import java.util.Map;

public class Servicio extends Service {
    private Thread hilo=null;
    private MediaPlayer mediaPlayer;

    private Looper serviceLooper;
    private ServiceHandler serviceHandler;

    // Handler that receives messages from the thread
    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {
            // Normally we would do some work here, like download a file.
            // For our sample, we just sleep for 5 seconds.
            // Stop the service using the startId, so that we don't stop
            // the service in the middle of handling another job
            stopSelf(msg.arg1);
        }
    }
    @Override
    public void onCreate() {
        super.onCreate();
        HandlerThread thread = new HandlerThread("ServiceStartArguments",
                Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        // Get the HandlerThread's Looper and use it for our Handler
        serviceLooper = thread.getLooper();
        serviceHandler = new ServiceHandler(serviceLooper);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(hilo==null||!hilo.isAlive()){
            hilo=new Thread(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void run() {
                    Libro libro=(Libro) intent.getSerializableExtra("servicioLibro");
                        crearNotificacion("hola","mm");
                        startForegroundService(intent);
                        mediaPlayer=MediaPlayer.create(getApplicationContext(),libro.getAudio());
                        mediaPlayer.start();
                    }
            });
            hilo.start();
        }else {
            hilo.interrupt();
        }
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();


    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void crearNotificacion(String titulo, String mensaje){
        //Creamos un intent explicito apara abrir la aplicación
        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.putExtra("rep", "Servicio Primer plano");


        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 1002, notificationIntent, 0);

        Notification notification =
                new Notification.Builder(this, FragmentDetalleLibro.ID_CHANNEL)
                        .setContentTitle(titulo)
                        .setContentText(mensaje+" Reproduciendo")
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentIntent(pendingIntent)
                        .setTicker("Se inicio el servicio")
                        .build();

        // Notification ID cannot be 0.
        startForeground(1000, notification);

    }
}

