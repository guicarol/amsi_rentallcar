package pt.ipleiria.estg.dei.rentallcar.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.rentallcar.R;
import pt.ipleiria.estg.dei.rentallcar.modelo.Veiculo;

public class ListaVeiculoAdaptador extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Veiculo> veiculos;

    public ListaVeiculoAdaptador(Context context, ArrayList<Veiculo> veiculos) {
        this.context = context;
        this.veiculos = veiculos;
    }

    @Override
    public int getCount() {
        return veiculos.size();
    }

    @Override
    public Object getItem(int i) {
        return veiculos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return veiculos.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null)
            view = inflater.inflate(R.layout.item_lista_veiculo, null);

        /*otimizaçao*/

        ViewHolderlista viewHolder = (ViewHolderlista) view.getTag();
        if (viewHolder == null) {
            viewHolder = new ViewHolderlista(view);
            view.setTag(viewHolder);
        }

        viewHolder.update(veiculos.get(i));

        return view;
    }

    private class ViewHolderlista {
        private TextView tvMarca, tvModelo, tvCombustivel, tvPreco;


        public ViewHolderlista(View view) {
            tvMarca = view.findViewById(R.id.tvMarca);
            tvModelo = view.findViewById(R.id.tvModelo);
            tvCombustivel = view.findViewById(R.id.tvCombustivel);
            tvPreco = view.findViewById(R.id.tvPreco);

        }

        public void update(Veiculo veiculo) {
            if(veiculo!=null) {
                tvMarca.setText(veiculo.getMarca());
                tvModelo.setText(veiculo.getModelo());
                tvCombustivel.setText(veiculo.getCombustivel());
                tvPreco.setText(veiculo.getPreco() + "");
            }
        }
    }
}
