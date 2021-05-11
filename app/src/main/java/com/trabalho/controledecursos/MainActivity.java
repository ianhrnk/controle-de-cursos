package com.trabalho.controledecursos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.trabalho.controledecursos.db.AppDatabase;

public class MainActivity extends AppCompatActivity {
    AppDatabase db;
    Toolbar tbrMain;
    TabLayout tabLayout;
    ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = AppDatabase.getDatabase(this);

        tbrMain = findViewById(R.id.tbr_main);
        setSupportActionBar(tbrMain);

        FloatingActionButton fabAdicionarCurso = findViewById(R.id.fabAddCurso);
        fabAdicionarCurso.setOnClickListener(v -> {
            Intent it = new Intent(MainActivity.this, DadosCursoActivity.class);
            startActivity(it);
        });

        tabLayout = findViewById(R.id.tablayout_main);
        viewPager = findViewById(R.id.viewpager_main);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    if (position == 0)
                        tab.setText("Cursos");
                    else
                        tab.setText("Alunos");
                }
        ).attach();
    }

    @Override
    protected void onResume() {
        super.onResume();
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

    public class ViewPagerAdapter extends FragmentStateAdapter {

        public ViewPagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            if (position == 0)
                return new CursosFragment(db.cursoDao());
            else
                return new AlunosFragment(db.alunoDao());
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }
}