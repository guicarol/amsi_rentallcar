package pt.ipleiria.estg.dei.rentallcar.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class FavoritoHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "favorite_items.db";
    public static final String TABLE_NAME = "favorite_items_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "ITEM_NAME";
    public static final String COL_3 = "ITEM_PRICE";
    private static final int DB_VERSION = 1;
    private static final String ID = "id", MARCA = "marca", MODELO = "modelo", COMBUSTIVEL = "combustivel", PRECO = "preco", DESCRICAO = "descricao", MATRICULA = "matricula";
    private final SQLiteDatabase db;

    public FavoritoHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createSQLTableLivro = "CREATE TABLE " + TABLE_NAME + "(" +
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
        String deleteSQLTableLivro = "DROP TABLE IF EXISTS " + TABLE_NAME;
        sqLiteDatabase.execSQL(deleteSQLTableLivro);
        this.onCreate(sqLiteDatabase);
    }

    public Favorito adicionarLivroBD(Favorito favorito) {

        ContentValues values = new ContentValues();
        values.put(ID, favorito.getId());
        values.put(MARCA, favorito.getMarca());
        values.put(MODELO, favorito.getModelo());
        values.put(COMBUSTIVEL, favorito.getCombustivel());
        values.put(PRECO, favorito.getPreco()+"");
        values.put(DESCRICAO, favorito.getDescricao());
        values.put(MATRICULA, favorito.getMatricula());
        db.insert(TABLE_NAME, null, values);

        return favorito;
    }

    public boolean removerLivroBD(int id) {
        return db.delete(TABLE_NAME, ID + "=?", new String[]{id + ""}) == 1;
    }

    public void removerAllLivrosBD() {
        db.delete(TABLE_NAME, null, null);

    }

    public ArrayList<Favorito> getAllLivroBD() {
        ArrayList<Favorito> favoritos = new ArrayList<>();
        Cursor cursor = db.query(TABLE_NAME, new String[]{ID, PRECO, DESCRICAO, MARCA, MODELO, COMBUSTIVEL, MATRICULA}, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Favorito veiculoAux = new Favorito(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
                favoritos.add(veiculoAux);

            } while (cursor.moveToNext());
            cursor.close();

        }
        return favoritos;
    }
}