package com.trabalho.controledecursos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar tbrMain = findViewById(R.id.tbrMain);
        setSupportActionBar(tbrMain);

        FloatingActionButton fabAdicionarCurso = findViewById(R.id.fabAddCurso);
        fabAdicionarCurso.setOnClickListener(v -> {
            Intent it = new Intent(MainActivity.this, DadosCursoActivity.class);
            startActivity(it);
        });
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
}