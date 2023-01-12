package pt.ipleiria.estg.dei.rentallcar.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;

import pt.ipleiria.estg.dei.rentallcar.modelo.Extras;

public class RotasJsonParser {

    public static ArrayList<Extras> parseJsonRotas (JSONArray response){
        ArrayList<Extras> rotas = new ArrayList<>();
        try {
        for(int i = 0; i <response.length(); i++){
                JSONObject rota = (JSONObject) response.get(i);
                int id = rota.getInt("idRota");
                String nome = rota.getString("nome");
                String coordenadas = rota.getString("coordenadas");
                float preco = BigDecimal.valueOf(rota.getDouble("preco")).floatValue();
                Extras auxRota = new Extras(id, nome, coordenadas, preco);
            rotas.add(auxRota);
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return rotas;
    }

    public static Boolean isConnectionInternet(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}