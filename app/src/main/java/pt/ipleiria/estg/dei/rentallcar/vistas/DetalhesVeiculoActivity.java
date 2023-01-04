package pt.ipleiria.estg.dei.rentallcar.vistas;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

import pt.ipleiria.estg.dei.rentallcar.MenuMainActivity;
import pt.ipleiria.estg.dei.rentallcar.R;
import pt.ipleiria.estg.dei.rentallcar.modelo.SingletonGestorVeiculos;
import pt.ipleiria.estg.dei.rentallcar.modelo.Veiculo;

public class DetalhesVeiculoActivity extends AppCompatActivity {

    private Veiculo veiculo;
    private EditText etTitulo, etSerie, etAno, etAutor;
    private ImageView imgCapa;
    private FloatingActionButton fabGuardar;
    public static final String IDLIVRO = "IDLIVRO";
    public static final int MIN_CHAR = 3;
    public static final int MIN_NUMERO = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_veiculo);
        etTitulo = findViewById(R.id.etTitulo);
        etAutor = findViewById(R.id.etAutor);
        etSerie = findViewById(R.id.etSerie);
        etAno = findViewById(R.id.etAno);
        imgCapa = findViewById(R.id.imgCapa);
        fabGuardar = findViewById(R.id.fabGuardar);

        int id = getIntent().getIntExtra(IDLIVRO, 0);

        veiculo = SingletonGestorVeiculos.getInstance(getApplicationContext()).getLivro(id);
        if (veiculo != null) {
            carregarLivro();
            fabGuardar.setImageResource(R.drawable.ic_action_guardar);
        } else {
            setTitle(getString(R.string.act_detalhes));
            fabGuardar.setImageResource(R.drawable.ic_action_adicionar);
        }
        fabGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLivroValido()) {
                    String titulo = etTitulo.getText().toString();
                    String autor = etAutor.getText().toString();
                    String serie = etSerie.getText().toString();
                    int ano = Integer.parseInt(etAno.getText().toString());
                    Intent intent = new Intent();
                    if (veiculo != null) {
                        //editar livro
                        //livroAux= new Livro(ano,livro.getCapa(),titulo,serie,autor);
                        veiculo.setMarca(titulo);
                        veiculo.setCombustivel(autor);
                        veiculo.setModelo(serie);
                        veiculo.setPreco(ano);
                        SingletonGestorVeiculos.getInstance(getApplicationContext()).editarLivroBD(veiculo);
                        intent.putExtra(MenuMainActivity.OPERACAO, MenuMainActivity.EDIT);


                    } else {
                        //criar Livro
                        Veiculo livroAux = new Veiculo(0, ano, "http://amsi.dei.estg.ipleiria.pt/img/ipl_semfundo.png", titulo, serie, autor);
                        SingletonGestorVeiculos.getInstance(getApplicationContext()).adicionarLivroAPI(livroAux, getApplicationContext());

                    }
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });
    }

    private boolean isLivroValido() {
        String titulo = etTitulo.getText().toString();
        String autor = etAutor.getText().toString();
        String serie = etSerie.getText().toString();
        String ano = etAno.getText().toString();
        if (titulo.length() < MIN_CHAR) {
            etTitulo.setError("Titulo invalido");
            return false;
        }

        if (titulo.length() < MIN_CHAR) {
            etAutor.setError("Serie invalida");
            return false;
        } else if (autor.length() < MIN_CHAR) {
            etAutor.setError("autor invalido");
            return false;
        } else if (serie.length() < MIN_CHAR) {
            etSerie.setError("serie invalido");
            return false;
        } else {
            int anoNumero = Integer.parseInt(ano);
            if (anoNumero < 1900 || anoNumero > Calendar.getInstance().get(Calendar.YEAR)) {
                etAno.setError("Ano invalido");
                return false;
            }
        }
        return true;
    }

    private void carregarLivro() {
        Resources res = getResources();
        String nome = String.format(res.getString(R.string.act_livro), veiculo.getMarca());
        setTitle(nome);
        etTitulo.setText(veiculo.getMarca());
        etSerie.setText(veiculo.getModelo());
        etAutor.setText(veiculo.getCombustivel());
        etAno.setText(veiculo.getPreco() + "");
        Glide.with(this)
                .load(veiculo.getDescricao())
                .placeholder(R.drawable.logoipl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgCapa);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (veiculo != null) {
            getMenuInflater().inflate(R.menu.menu_detalhes_livro, menu);
            return super.onCreateOptionsMenu(menu);
        }
        return false;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemRemover:
                dialogRemove();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void dialogRemove() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Remover Livro")
                .setMessage("Pretende mesmo remover o livro")
                .setIcon(android.R.drawable.ic_delete)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SingletonGestorVeiculos.getInstance(getApplicationContext()).removerLivroBD(veiculo.getId());
                        Intent intent = new Intent();
                        intent.putExtra(MenuMainActivity.OPERACAO, MenuMainActivity.DELETE);
                        setResult(RESULT_OK, intent);

                        finish();

                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }
}