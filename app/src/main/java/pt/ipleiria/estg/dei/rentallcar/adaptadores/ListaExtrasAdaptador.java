package pt.ipleiria.estg.dei.rentallcar.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.rentallcar.R;
import pt.ipleiria.estg.dei.rentallcar.modelo.Extras;

public class ListaExtrasAdaptador extends BaseAdapter {

    private ArrayList<Extras> extras;
    private LayoutInflater inflater;
    private Context context;

    public ListaExtrasAdaptador(ArrayList<Extras> extras, Context context) {
        this.extras = extras;
        this.context = context;
    }

    @Override
    public int getCount() {
        return extras.size();
    }

    @Override
    public Object getItem(int position) {
        return extras.get(position);
    }

    @Override
    public long getItemId(int position) {
        return extras.get(position).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if(inflater==null)
            inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(view == null)
            view = inflater.inflate(R.layout.item_extras,null);

        /*otimização para não estar a repetir várias vezes*/
        ViewHolderLista viewHolder=(ViewHolderLista) view.getTag();
        if(viewHolder==null){
            viewHolder=new ViewHolderLista(view);
            view.setTag(viewHolder);
        }

        viewHolder.update(extras.get(position));

        return view;
    }


    private class ViewHolderLista{
        private TextView tvHorarioPartida, tvHorarioChegada;

        public ViewHolderLista(View view){
            tvHorarioPartida=view.findViewById(R.id.tvExtra);
           // tvHorarioChegada=view.findViewById(R.id.tvHorarioChegada);
        }

        public void update(Extras extras){
            tvHorarioPartida.setText(" " + extras.getDescricao());
           // tvHorarioChegada.setText(" " + horarios.getHoraFim());
        }

    }
}
