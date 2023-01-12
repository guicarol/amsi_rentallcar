package pt.ipleiria.estg.dei.rentallcar.vistas;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import pt.ipleiria.estg.dei.rentallcar.R;
import pt.ipleiria.estg.dei.rentallcar.modelo.SingletonGestorVeiculos;

public class Extras extends AppCompatActivity {

    private TextView tvNomeRota;
    private Button btComprarBilhete;
    private pt.ipleiria.estg.dei.rentallcar.modelo.Extras rota;
    private ListView listaExtras;

    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_veiculo);

//        tvNomeRota = findViewById(R.id.tvNomeRota);
  //      btComprarBilhete = findViewById(R.id.btComprarBilhete);
        listaExtras = findViewById(R.id.listaExtras);

        id = getIntent().getIntExtra("ID_ROTA", 0);

        rota = SingletonGestorVeiculos.getInstance(getApplicationContext()).getExtra(id);

        //SingletonGestorVeiculos.getInstance(this).setVeiculosListener(this);
        //SingletonGestorVeiculos.getInstance(this).getAllHorariosEXPAPI(this, id);

        //setTitle("Detalhes: " + rota.getNome());
        // tvNomeRota.setText(rota.getNome());
        //btComprarBilhete.setText("Comprar bilhete \n" + rota.getPreco() + "€");

    }
/*
    @Override
    public void onRefreshListaHorarios(ArrayList<Horarios> horarios) {
        if(horarios != null)
            listaHorarios.setAdapter(new ListaHorariosAdaptador(horarios,this));

    }

    @Override
    public void onRefreshListaCompra() {
        Intent intent = new Intent(this,PassageiroActivity.class);
        intent.putExtra("CODE",3);
        startActivity(intent);
        finish();
    }

    public void onClickComprarBilhete(View view) {
        rota = SingletonBusIT.getInstance(getApplicationContext()).getRota(id);

        String Hora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        bilhetes = new Bilhetes(0, "NaoUsado", rota.getPreco(), Hora, 12,  id);
        SingletonBusIT.getInstance(getApplicationContext()).adicionarBilheteAPI(bilhetes, getBaseContext());
    }*/
}