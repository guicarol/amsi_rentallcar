package pt.ipleiria.estg.dei.rentallcar.adaptadores;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.rentallcar.R;
import pt.ipleiria.estg.dei.rentallcar.modelo.Favorito;

public class ItemAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Favorito> favoritos;

    public ItemAdapter(Activity context, ArrayList<Favorito> favoritos) {
        this.context = context;
        this.favoritos = favoritos;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null)
            view = inflater.inflate(R.layout.item_lista_veiculo, null);

        /*otimizaçao*/

        ItemAdapter.ViewHolderlista viewHolder = (ItemAdapter.ViewHolderlista) view.getTag();
        if (viewHolder == null) {
            viewHolder = new ItemAdapter.ViewHolderlista(view);
            view.setTag(viewHolder);
        }

        viewHolder.update(favoritos.get(i));

        return view;
    }
    private class ViewHolderlista {
        private TextView tvMarca, tvModelo, tvCombustivel, tvPreco;
         private Button btnDelete;


        public ViewHolderlista(View view) {
            tvMarca = view.findViewById(R.id.tvMarca);
            tvModelo = view.findViewById(R.id.tvModelo);
            btnDelete= view.findViewById(R.id.btnDelete);


        }

        public void update(Favorito favorito) {
            if(favorito!=null) {
                tvMarca.setText(favorito.getMarca());
                tvModelo.setText(favorito.getModelo());

            }
        }
    }
}

