package com.example.audiolibros;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements iComunicaFragment {

    FrameLayout contenedorFragment;
    FragmentDetalleLibro fragmentDetalleLibro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contenedorFragment=findViewById(R.id.contenedorFragment);

        FragmentListaLibros fragmentListaLibros=new FragmentListaLibros();

        showSelectedFragment(fragmentListaLibros,"ghh");

    }
    private void showSelectedFragment(Fragment fragment, String tag){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contenedorFragment,fragment,tag)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    public void enviarLibro(Libro libro) {
        fragmentDetalleLibro=new FragmentDetalleLibro();
        Bundle bundle=new Bundle();
        bundle.putSerializable("libro",libro);
        fragmentDetalleLibro.setArguments(bundle);

        fragmentManager=getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contenedorFragment,fragmentDetalleLibro);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
}