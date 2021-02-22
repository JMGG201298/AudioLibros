package com.example.audiolibros;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentListaLibros#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentListaLibros extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentListaLibros() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentListaLibros.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentListaLibros newInstance(String param1, String param2) {
        FragmentListaLibros fragment = new FragmentListaLibros();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    Activity activity;
    iComunicaFragment iComunicaFragment;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof Activity){
            this.activity=(Activity) context;
            iComunicaFragment= (com.example.audiolibros.iComunicaFragment) this.activity;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    RecyclerView recyclerView;
    public void llenar(){
        Libro libro1=new Libro(1,"El quinto infierno","descripcion1",R.drawable.elquintoinfierno,R.raw.avecillaaudio);
        Libro libro2=new Libro(2,"La Semilla Feliz","descripcion2",R.drawable.lasemillafeliz,R.raw.kappaaudio);
        Libro libro3=new Libro(3,"Lavandera","descripcion3",R.drawable.lavandera,0);
        Libro libro4=new Libro(4,"Memory","descripcion4",R.drawable.memory,0);
        Libro libro5=new Libro(5,"Moby Dick","descripcion5",R.drawable.mobydick,0);
        List<Libro> libroList=new ArrayList<Libro>();
        libroList.add(libro1);
        libroList.add(libro2);
        libroList.add(libro3);
        libroList.add(libro4);
        libroList.add(libro5);
        AdaptadorLibro adaptadorLibro=new AdaptadorLibro(libroList,getContext());
        adaptadorLibro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Libro libro=libroList.get(recyclerView.getChildAdapterPosition(v));
                Toast.makeText(getContext(),libro.getTitulo(),Toast.LENGTH_SHORT).show();
                iComunicaFragment.enviarLibro(libroList.get(recyclerView.getChildAdapterPosition(v)));
                //showSelectedFragment();
            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.setAdapter(adaptadorLibro);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_lista_libros, container, false);
        recyclerView=view.findViewById(R.id.rVListaLibros);
        llenar();
        return view;
    }
}