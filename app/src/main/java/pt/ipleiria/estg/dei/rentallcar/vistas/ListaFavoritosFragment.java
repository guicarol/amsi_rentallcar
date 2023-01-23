package pt.ipleiria.estg.dei.rentallcar.vistas;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.rentallcar.R;
import pt.ipleiria.estg.dei.rentallcar.modelo.DatabaseHelper;

public class ListaFavoritosFragment extends Fragment {

    DatabaseHelper myDb;
    ListView listView;
    ArrayList<String> itemList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lista_favoritos, container, false);
        myDb = new DatabaseHelper(getActivity());
        listView = view.findViewById(R.id.list_view);
        itemList = new ArrayList<>();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("user_info", MODE_PRIVATE);
        int idprofile = sharedPreferences.getInt("id", -1);
        // Retrieve data from the database
        Cursor res = myDb.getAllData(idprofile);

        // Check if there are any data in the cursor
        if (res.getCount() == 0) {
            // Show message if there are no data
            showMessage("Error", "Não tem favoritos");
            return view;
        }

        // Move the cursor to the first row
        res.moveToFirst();

        // Loop through the cursor
        while (!res.isAfterLast()) {
            // Append the data to the itemList
            itemList.add("ID: " + res.getString(0) + " - ID perfil: " + res.getString(1) + " - Veiculo: " + res.getString(2));


            // Move the cursor to the next row
            res.moveToNext();
        }

        // Create an ArrayAdapter to display the data in the listView
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, itemList);

        // Set the adapter for the listView
        listView.setAdapter(arrayAdapter);

        return view;
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}