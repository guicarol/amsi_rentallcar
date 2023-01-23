package pt.ipleiria.estg.dei.rentallcar.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class VeiculoBDHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "bdveiculos";
    private static final int DB_VERSION = 1;
    private static final String TABLE_VEICULOS = "veiculos";
    private static final String ID = "id_veiculo", MARCA = "marca", MODELO = "modelo", COMBUSTIVEL = "combustivel", PRECO = "preco", DESCRICAO = "descricao", MATRICULA = "matricula";
    private final SQLiteDatabase db;

    public VeiculoBDHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createSQLTableLivro = "CREATE TABLE " + TABLE_VEICULOS + "(" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MARCA + " TEXT NOT NULL, " +
                MODELO + " TEXT NOT NULL, " +
                COMBUSTIVEL + " TEXT NOT NULL, " +
                PRECO + " INTEGER NOT NULL, " +
                DESCRICAO + " TEXT NOT NULL," +
                MATRICULA + " TEXT NOT NULL" +
                ");";
        sqLiteDatabase.execSQL(createSQLTableLivro);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String deleteSQLTableLivro = "DROP TABLE IF EXISTS " + TABLE_VEICULOS;
        sqLiteDatabase.execSQL(deleteSQLTableLivro);
        this.onCreate(sqLiteDatabase);
    }

    public Veiculo adicionarLivroBD(Veiculo veiculo) {

        ContentValues values = new ContentValues();
        values.put(ID,veiculo.getId());
        values.put(MARCA, veiculo.getMarca());
        values.put(MODELO, veiculo.getModelo());
        values.put(COMBUSTIVEL, veiculo.getCombustivel());
        values.put(PRECO, veiculo.getPreco());
        values.put(DESCRICAO, veiculo.getDescricao());
        values.put(MATRICULA,veiculo.getMatricula());
        db.insert(TABLE_VEICULOS, null, values);

        return veiculo;
    }

    public boolean editarLivroBD(Veiculo veiculo) {

        ContentValues values = new ContentValues();
        values.put(MARCA, veiculo.getMarca());
        values.put(MODELO, veiculo.getModelo());
        values.put(COMBUSTIVEL, veiculo.getCombustivel());
        values.put(PRECO, veiculo.getPreco());
        values.put(DESCRICAO, veiculo.getDescricao());

        return db.update(TABLE_VEICULOS, values, ID + "=?", new String[]{veiculo.getId() + ""}) == 1;


    }

    public boolean removerLivroBD(int id) {
        return db.delete(TABLE_VEICULOS, ID + "=?", new String[]{id + ""}) == 1;
    }


    public void removerAllLivrosBD() {
        db.delete(TABLE_VEICULOS, null, null);

    }

    public ArrayList<Veiculo> getAllLivroBD() {
        ArrayList<Veiculo> veiculos = new ArrayList<>();
        Cursor cursor = db.query(TABLE_VEICULOS, new String[]{PRECO, DESCRICAO, MARCA, MODELO, COMBUSTIVEL, ID, MATRICULA}, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Veiculo veiculoAux = new Veiculo(cursor.getInt(5), cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(6));
                veiculos.add(veiculoAux);

            } while (cursor.moveToNext());
            cursor.close();
        }
        return veiculos;
    }
}