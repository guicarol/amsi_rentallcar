package pt.ipleiria.estg.dei.rentallcar.vistas;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import pt.ipleiria.estg.dei.rentallcar.R;
import pt.ipleiria.estg.dei.rentallcar.modelo.Reserva;
import pt.ipleiria.estg.dei.rentallcar.modelo.SingletonGestorVeiculos;

public class DetalhesReservaActivity extends AppCompatActivity {

    private Reserva reserva;
    private int idprofile, idreserva;
    private TextView etMarca, etModelo, tvLocalL, etseguro, tvDataL, tvLocalD, tvDataD,tvPreco;
    private ImageView imgCapa;
    private FloatingActionButton fabGuardar;
    private Button btnPedirAssistencia;
    public static final String IDVEICULO = "IDVEICULO";
    public static final int MIN_CHAR = 3;
    public static final int MIN_NUMERO = 4;
    public static final String IDRESERVA = "IDRESERVA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_reserva);
        etMarca = findViewById(R.id.etMarca);
        etModelo = findViewById(R.id.etModelo);
        etseguro = findViewById(R.id.etSeguro);
        tvLocalL = findViewById(R.id.tvLocalL);
        tvDataL = findViewById(R.id.tvDataL);
        tvLocalD = findViewById(R.id.tvLocalD);
        tvDataD = findViewById(R.id.tvDataD);
        tvPreco=findViewById(R.id.tvPreco);
        idreserva = getIntent().getIntExtra(IDRESERVA, 0);

        btnPedirAssistencia = findViewById(R.id.btnPedirAssistencia);
        btnPedirAssistencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetalhesReservaActivity.this, AssistenciaActivity.class);
                intent.putExtra(DetalhesReservaActivity.IDRESERVA, idreserva);
                startActivity(intent);
                finish();
            }
        });

        reserva = SingletonGestorVeiculos.getInstance(getBaseContext()).getReserva(idreserva);
        if (reserva != null) {
            carregarVeiculo();
        } else {
            setTitle(getString(R.string.act_detalhes));
        }
    }


    private void carregarVeiculo() {
        Resources res = getResources();
        String nome = String.format(res.getString(R.string.act_livro), reserva.getMarca() + " " + reserva.getModelo());
        setTitle(nome);
        etMarca.setText(reserva.getMarca());
        etModelo.setText(reserva.getModelo());
        etseguro.setText(reserva.getSeguro());
        tvLocalL.setText(reserva.getLocalizacao_levantamento());
        tvDataL.setText(reserva.getData_inicio() + "");
        tvLocalD.setText(reserva.getLocalizacao_devolucao());
        tvDataD.setText(reserva.getData_fim() + "");
        tvPreco.setText(reserva.getVeiculo_id()+"");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (reserva != null) {
            getMenuInflater().inflate(R.menu.menu_detalhes_livro, menu);
            return super.onCreateOptionsMenu(menu);
        }
        return false;
    }


}