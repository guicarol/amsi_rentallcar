package pt.ipleiria.estg.dei.rentallcar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import pt.ipleiria.estg.dei.rentallcar.vistas.Favoritos;
import pt.ipleiria.estg.dei.rentallcar.vistas.Pesquisar;
import pt.ipleiria.estg.dei.rentallcar.vistas.ResultadoPesquisa;
import pt.ipleiria.estg.dei.rentallcar.vistas.Utilizador;

public class MenuMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private String email;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close);
        toggle.syncState();
        drawer.addDrawerListener(toggle);

        carregarCabecalho();
        navigationView.setNavigationItemSelectedListener(this);
        fragmentManager=getSupportFragmentManager();
        carregarFragmentoInicial();
    }

    private boolean carregarFragmentoInicial() {
        Menu menu= navigationView.getMenu();
        MenuItem item= menu.getItem(0);
        item.setChecked(true);
        return onNavigationItemSelected(item);
    }

    private void carregarCabecalho() {
        email= getIntent().getStringExtra("EMAIL");
        if(email!=null){
            View hView= navigationView.getHeaderView(0);
            TextView tvEmail = hView.findViewById(R.id.tvEmail);
            tvEmail.setText(email);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.pesquisar:
                fragment = new Pesquisar();
                setTitle(item.getTitle());
                break;
            case R.id.utilizador:
                fragment = new Utilizador();
                setTitle(item.getTitle());
                break;
            case R.id.favoritos:
                fragment = new Favoritos();
                setTitle(item.getTitle());
                break;
            case R.id.logout:
                onDestroy();
                break;
        }
        if (fragment != null)
            fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).commit();

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onClickPesquisar(View view) {
        Intent intent=new Intent(this, ResultadoPesquisa.class);
        startActivity(intent);
    }

}