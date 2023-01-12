package pt.ipleiria.estg.dei.rentallcar.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.rentallcar.modelo.Veiculo;

public class VeiculoJsonParser {

    public static ArrayList<Veiculo> parserJsonLivros(JSONArray response) {
        ArrayList<Veiculo> livros = new ArrayList<>();
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject livro = (JSONObject) response.get(i);
                int id = livro.getInt("id");
                String titulo = livro.getString("titulo");
                String serie = livro.getString("serie");
                String autor = livro.getString("autor");
                int ano = livro.getInt("ano");
                String descricao = livro.getString("descricao");
                String matricula = livro.getString("matricula");
                Veiculo livroAux = new Veiculo(id, ano, descricao, titulo, serie, autor, matricula);
                livros.add(livroAux);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Veiculo parserJsonLivro(String response) {
        Veiculo livroAux = null;
        try {
            JSONObject livro = new JSONObject(response);
            int id = livro.getInt("id");
            String titulo = livro.getString("titulo");
            String serie = livro.getString("serie");
            String autor = livro.getString("autor");
            int ano = livro.getInt("ano");
            String descricao = livro.getString("descricao");
            String matricula = livro.getString("matricula");

            livroAux = new Veiculo(id, ano, descricao, titulo, serie, autor, matricula);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return livroAux;
    }

    public static String parserJsonLogin(String response) {
        String token = null;
        try {
            JSONObject login = new JSONObject(response);
            if (login.getBoolean("success"))
                token = login.getString("token");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return token;
    }

    public static boolean isConnectionInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();

        return ni != null && ni.isConnected();
    }
}
