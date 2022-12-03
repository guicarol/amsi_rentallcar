package pt.ipleiria.estg.dei.rentallcar.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
        if(inflater==null)
            inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view==null)
            view=inflater.inflate(R.layout.item_lista_veiculo, null);

        /*otimizaçao*/

        ViewHolderlista viewHolder=(ViewHolderlista) view.getTag();
        if (viewHolder== null){
            viewHolder=new ViewHolderlista(view);
            view.setTag(viewHolder);
        }

        viewHolder.update(veiculos.get(i));

        return view;
    }
    private class ViewHolderlista{
        private TextView tvTitulo, tvSerie,tvAno, tvAutor;
        private ImageView imgCapa;

        public ViewHolderlista(View view){
            tvTitulo=view.findViewById(R.id.tvTitulo);
            tvSerie=view.findViewById(R.id.tvSerie);
            tvAutor=view.findViewById(R.id.tvAutor);
            tvAno=view.findViewById(R.id.tvAno);
            imgCapa=view.findViewById(R.id.imgCapa);
        }
        public void update(Veiculo veiculo){
            tvTitulo.setText(veiculo.getMarca());
            tvSerie.setText(veiculo.getModelo());
            tvAutor.setText(veiculo.getCombustivel());
            tvAno.setText(veiculo.getPreco()+"");
            imgCapa.setImageResource(veiculo.getCapa());
        }
    }
}
