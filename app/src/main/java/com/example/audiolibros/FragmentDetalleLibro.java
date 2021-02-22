package com.example.audiolibros;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentDetalleLibro#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentDetalleLibro extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Libro libro;
    public FragmentDetalleLibro() {
        // Required empty public constructor


    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentDetalleLibro.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentDetalleLibro newInstance(String param1, String param2) {
        FragmentDetalleLibro fragment = new FragmentDetalleLibro();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    TextView txtTitulo;
    ImageView ivPortada;
    Button btnReproducir;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_detalle_libro, container, false);
        txtTitulo=view.findViewById(R.id.txtTituloDetalle);
        ivPortada=view.findViewById(R.id.iVPortadaDetalle);
        btnReproducir=view.findViewById(R.id.btnReproducir);

        Bundle bundle=getArguments();
        libro=(Libro) bundle.getSerializable("libro");
        if(libro!=null){
            txtTitulo.setText(libro.getTitulo());
            ivPortada.setImageResource(libro.getPortada());
            btnReproducir.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(View v) {
                    crearCanalNotificacion();
                    //crearNotificacion(libro.getTitulo(),libro.getDescripcion());
                    iniciarServicio();
                    denetenerServicio();

                }
            });
        }



        return view;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void iniciarServicio(){

        Intent intent=new Intent(getContext(),Servicio.class);
        intent.putExtra("servicioLibro",libro);
        getContext().startService(intent);
    }
    public void denetenerServicio(){
        Intent intent=new Intent(getContext(),Servicio.class);
        getContext().stopService(intent);
    }
    public static String ID_CHANNEL="1;";

    public void crearCanalNotificacion(){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            //Nombre del canal
            CharSequence nombre=getString(R.string.Mi_CANNAL_STRING);

            //Descripcion del canal
            String descripcion=getString(R.string.DESCRIPCION_CANAL);

            //Importancia del canal
            int importancia= NotificationManager.IMPORTANCE_DEFAULT;

            //Declaración del canal
            NotificationChannel notificationChannel=new NotificationChannel(ID_CHANNEL,nombre,importancia);

            NotificationManager notificationManager=getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);

        }
    }


    public void crearNotificacion(String titulo, String mensaje){
        //Creamos un intent explicito apara abrir la aplicación
        Intent intent=new Intent(getContext(), MainActivity.class);
        intent.setAction("Cerrar");
        intent.putExtra("Id", 0);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent=PendingIntent.getActivity(getContext(),0,intent,0);



        NotificationCompat.Builder builder=new NotificationCompat.Builder(getContext(),ID_CHANNEL);

        builder.setSmallIcon(R.drawable.ic_baseline_play_arrow_24);
        builder.setContentTitle(titulo);
        builder.setContentText(mensaje);
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(""));
        builder.setPriority(NotificationManager.IMPORTANCE_DEFAULT);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);

        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(getContext());

        notificationManagerCompat.notify(1,builder.build());

    }

}