package com.trabalho.controledecursos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CursosAdapter extends RecyclerView.Adapter<CursosAdapter.ViewHolder> {
    private final String[] cursos;
    private final Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtNomeCurso;

        public ViewHolder(View view) {
            super(view);
            txtNomeCurso = view.findViewById(R.id.txtNomeCurso);
        }
    }

    public CursosAdapter(String[] cursos, Context context) {
        this.cursos = cursos;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                    .inflate(R.layout.curso, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CursosAdapter.ViewHolder holder, int position) {
        holder.txtNomeCurso.setText(cursos[position]);
    }

    @Override
    public int getItemCount() {
        return cursos.length;
    }
}
