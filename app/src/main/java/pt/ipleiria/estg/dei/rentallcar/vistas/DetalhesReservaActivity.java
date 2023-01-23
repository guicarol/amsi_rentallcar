package pt.ipleiria.estg.dei.rentallcar.vistas;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import pt.ipleiria.estg.dei.rentallcar.R;
import pt.ipleiria.estg.dei.rentallcar.modelo.Reserva;
import pt.ipleiria.estg.dei.rentallcar.modelo.SingletonGestorVeiculos;

public class DetalhesReservaActivity extends AppCompatActivity {

    private Reserva reserva;
    private int idprofile, idreserva;
    private TextView etMarca, etModelo, tvLocalL, etseguro, tvDataL, tvLocalD, tvDataD, tvPreco, tvMatricula;
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
        tvPreco = findViewById(R.id.tvPreco);
        tvMatricula = findViewById(R.id.tvMatricula);
        idreserva = getIntent().getIntExtra(IDRESERVA, 0);
        imgCapa = findViewById(R.id.imgCapa);




        btnPedirAssistencia = findViewById(R.id.btnPedirAssistencia);
        btnPedirAssistencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetalhesReservaActivity.this, AssistenciaActivity.class);
                intent.putExtra(DetalhesReservaActivity.IDVEICULO, reserva.getVeiculo_id());
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
        tvPreco.setText(reserva.getPreco() + "€");
        tvMatricula.setText(reserva.getMatricula());
        imgCapa.setImageBitmap(makeqr(reserva.getId() + ""));


        String dateFormat = reserva.getData_inicio();
        String dateFormat2 = reserva.getData_fim();
        SimpleDateFormat format = new SimpleDateFormat("d/MM/yyyy");
        Date date = new Date();
        try {

            Date dataInicio = format.parse(dateFormat);
            Date dataFim = format.parse(dateFormat2);



            if(date.compareTo(dataInicio) >= 0 && date.compareTo(dataFim) <= 0 ){
                btnPedirAssistencia.setVisibility(View.VISIBLE);


            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    public static Bitmap makeqr(String id){


        QRGEncoder qrgEncoder = new QRGEncoder(id , null, QRGContents.Type.TEXT, 500);
        qrgEncoder.setColorBlack(Color.WHITE);
        qrgEncoder.setColorWhite(Color.BLACK);
        try {
            Bitmap bitmap = qrgEncoder.getBitmap();
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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