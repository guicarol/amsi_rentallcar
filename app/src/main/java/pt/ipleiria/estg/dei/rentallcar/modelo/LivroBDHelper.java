package pt.ipleiria.estg.dei.rentallcar.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class LivroBDHelper  extends SQLiteOpenHelper {

    private static final String DB_NAME = "bdlivros";
    private static final int DB_VERSION = 1;
    private static final String TABLE_LIVROS = "livros";
    private static final String ID = "id", TITULO = "titulo", SERIE = "serie", AUTOR = "autor", ANO = "ano", CAPA = "capa";
    private final SQLiteDatabase db;

    public LivroBDHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createSQLTableLivro = "CREATE TABLE " + TABLE_LIVROS + "(" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TITULO + " TEXT NOT NULL, " +
                SERIE + " TEXT NOT NULL, " +
                AUTOR + " TEXT NOT NULL, " +
                ANO + " INTEGER NOT NULL, " +
                CAPA + " INTEGER" +
                ");";
        sqLiteDatabase.execSQL(createSQLTableLivro);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String deleteSQLTableLivro = "DROP TABLE IF EXISTS " + TABLE_LIVROS;
        sqLiteDatabase.execSQL(deleteSQLTableLivro);
        this.onCreate(sqLiteDatabase);

    }

    public Veiculo adicionarLivroBD(Veiculo veiculo) {

        ContentValues values = new ContentValues();
        values.put(TITULO, veiculo.getMarca());
        values.put(SERIE, veiculo.getModelo());
        values.put(AUTOR, veiculo.getCombustivel());
        values.put(ANO, veiculo.getPreco());
        values.put(CAPA, veiculo.getCapa());

        long id = db.insert(TABLE_LIVROS, null, values);

        if (id > -1) {
            veiculo.setId((int) id);
            return veiculo;
        }
        return null;
    }

    public boolean editarLivroBD(Veiculo veiculo) {

        ContentValues values = new ContentValues();
        values.put(TITULO, veiculo.getMarca());
        values.put(SERIE, veiculo.getModelo());
        values.put(AUTOR, veiculo.getCombustivel());
        values.put(ANO, veiculo.getPreco());
        values.put(CAPA, veiculo.getCapa());

        return db.update(TABLE_LIVROS, values, ID + "=?", new String[]{veiculo.getId() + ""}) == 1;


    }

    public boolean removerLivroBD(int id) {
        return db.delete(TABLE_LIVROS, ID + "=?", new String[]{id + ""}) == 1;
    }

    public ArrayList<Veiculo> getAllLivroBD() {
        ArrayList<Veiculo> veiculos = new ArrayList<>();
        Cursor cursor = db.query(TABLE_LIVROS, new String[]{ANO, CAPA, TITULO, SERIE, AUTOR, ID}, null, null, null, null, null);

        if (cursor.moveToFirst()){
            do{
                Veiculo veiculoAux =new Veiculo(cursor.getInt(0),cursor.getInt(1),cursor.getString(2),cursor.getString(3),cursor.getString(4));

                veiculoAux.setId(cursor.getInt(5));
                veiculos.add(veiculoAux);

            }while (cursor.moveToNext());
            cursor.close();
        }
        return veiculos;
    }


}
