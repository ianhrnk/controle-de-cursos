package com.trabalho.controledecursos.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trabalho.controledecursos.R;
import com.trabalho.controledecursos.viewmodel.AppViewModel;

public class CursosFragment extends Fragment {
    private CursosAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        AppViewModel viewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);
        viewModel.getTodosCursos().observe(getViewLifecycleOwner(), cursos -> adapter.setCursos(cursos));
        viewModel.getCursoAlunos().observe(requireActivity(), DadosCursoActivity::atualizarCursoAlunos);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cursos, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_cursos);

        adapter = new CursosAdapter(view.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        return view;
    }
}