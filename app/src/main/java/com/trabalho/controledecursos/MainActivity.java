package com.trabalho.controledecursos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.trabalho.controledecursos.db.AppDatabase;

public class MainActivity extends AppCompatActivity {
    AppDatabase db;
    RecyclerView recyclerView;
    DividerItemDecoration dividerItemDecoration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = AppDatabase.getDatabase(this);

        Toolbar tbrMain = findViewById(R.id.tbr_main);
        setSupportActionBar(tbrMain);

        FloatingActionButton fabAdicionarCurso = findViewById(R.id.fabAddCurso);
        fabAdicionarCurso.setOnClickListener(v -> {
            Intent it = new Intent(MainActivity.this, DadosCursoActivity.class);
            startActivity(it);
        });

        recyclerView = findViewById(R.id.recyclerview_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        CursosAdapter cursosAdapter = new CursosAdapter(this, db.cursoDao());
        recyclerView.setAdapter(cursosAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_search:
                // TODO
                return true;
            case R.id.configuration:
                // TODO
                return true;
            case R.id.about_us:
                // TODO
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        // Não faz nada ao pressionar o botão de voltar
    }
}