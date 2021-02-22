package com.example.audiolibros;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ConcurrentModificationException;
import java.util.List;

public class AdaptadorLibro extends RecyclerView.Adapter<AdaptadorLibro.LibrosViewHolder> implements View.OnClickListener {
    List<Libro> listaLibros;
    Context context;
    private View.OnClickListener onClickListener;
    public AdaptadorLibro(List<Libro> listaLibros, Context context){
        this.listaLibros=listaLibros;
        this.context=context;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public LibrosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.list_elements,parent,false);
        view.setOnClickListener(onClickListener);

        LibrosViewHolder viewHolder=new LibrosViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LibrosViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return listaLibros.size();
    }

    @Override
    public void onClick(View v) {
        if(onClickListener!=null){
            onClickListener.onClick(v);
        }
    }

    class LibrosViewHolder extends RecyclerView.ViewHolder{

        TextView txtTitulo;
        ImageView ivPortada;

        //Es el metodo que se inicia primero y debemos declarar las variables en el
        public LibrosViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitulo=itemView.findViewById(R.id.txtTitulo);
            ivPortada=itemView.findViewById(R.id.ivPortada);
        }

        //Se creamo el objeto que se va a poner en cada recyclerview
         public void bind(int listaIndex){
            Libro libro=listaLibros.get(listaIndex);
            txtTitulo.setText(String.valueOf(libro.getTitulo()));
            ivPortada.setImageResource(libro.getPortada());
        }
    }

}
