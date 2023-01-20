package pt.ipleiria.estg.dei.rentallcar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;

import pt.ipleiria.estg.dei.rentallcar.vistas.FavoritosFragment;
import pt.ipleiria.estg.dei.rentallcar.vistas.ListaReservaFragment;
import pt.ipleiria.estg.dei.rentallcar.vistas.ListaVeiculoFragment;
import pt.ipleiria.estg.dei.rentallcar.vistas.ResultadoPesquisa;
import pt.ipleiria.estg.dei.rentallcar.vistas.SobreFragment;
import pt.ipleiria.estg.dei.rentallcar.vistas.UtilizadorFragment;

public class MenuMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private String email;
    private FragmentManager fragmentManager;
    public static final String EMAIL = "EMAIL", SHARED_FILE = "DADOS_USER", OPERACAO = "OPERACAO";
    public static final int EDIT = 10, ADD = 20, DELETE = 30;

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
        fragmentManager = getSupportFragmentManager();
        carregarFragmentoInicial();
    }

    private boolean carregarFragmentoInicial() {
        Menu menu = navigationView.getMenu();
        MenuItem item = menu.getItem(0);
        item.setChecked(true);
        return onNavigationItemSelected(item);
    }

    private void carregarCabecalho() {
        email = getIntent().getStringExtra(EMAIL);
        SharedPreferences sharedInfoUser = getSharedPreferences(SHARED_FILE, Context.MODE_PRIVATE);
        if (email != null) {
            SharedPreferences.Editor editor = sharedInfoUser.edit();
            editor.putString(EMAIL, email);
            editor.apply();
        } else
            email = sharedInfoUser.getString(EMAIL, "Sem Email");

        View hView = navigationView.getHeaderView(0);
        TextView tvEmail = hView.findViewById(R.id.tvEmail);
        tvEmail.setText(email);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.utilizador:
                fragment = new UtilizadorFragment();
                setTitle(item.getTitle());
                break;
            case R.id.pesquisar:
                fragment = new ListaVeiculoFragment();
                setTitle(item.getTitle());
                break;

            case R.id.reservas:
                fragment = new ListaReservaFragment();
                setTitle(item.getTitle());
                break;
            case R.id.favoritos:
                fragment = new FavoritosFragment();
                setTitle(item.getTitle());
                break;
            case R.id.sobre:
                fragment = new SobreFragment();
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
        Intent intent = new Intent(this, ResultadoPesquisa.class);
        startActivity(intent);
    }
}