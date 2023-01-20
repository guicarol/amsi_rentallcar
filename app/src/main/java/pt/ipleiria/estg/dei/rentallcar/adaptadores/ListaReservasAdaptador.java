package pt.ipleiria.estg.dei.rentallcar.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import pt.ipleiria.estg.dei.rentallcar.R;
import pt.ipleiria.estg.dei.rentallcar.modelo.Reserva;

public class ListaReservasAdaptador extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Reserva> reserva;

    public ListaReservasAdaptador(Context context, ArrayList<Reserva> reserva) {
        this.context = context;
        this.reserva = reserva;
    }

    @Override
    public int getCount() {
        return reserva.size();
    }

    @Override
    public Object getItem(int i) {
        return reserva.get(i);
    }

    @Override
    public long getItemId(int i) {
        return reserva.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null)
            view = inflater.inflate(R.layout.item_lista_reservas, null);

        /*otimizaçao*/

        ViewHolderlista viewHolder = (ViewHolderlista) view.getTag();
        if (viewHolder == null) {
            viewHolder = new ViewHolderlista(view);
            view.setTag(viewHolder);
        }

        viewHolder.update(reserva.get(i));

        return view;
    }

    private class ViewHolderlista {
        private TextView tvMarca, tvModelo, tvDataLevantamento, tvDataDevolucao;
        private Button btnPedirAssistencia;


        public ViewHolderlista(View view) {
            tvMarca = view.findViewById(R.id.tvMarca);
            tvModelo = view.findViewById(R.id.tvModelo);
            tvDataLevantamento = view.findViewById(R.id.tvDataLevantamento);
            tvDataDevolucao = view.findViewById(R.id.tvDataDevolucao);
            btnPedirAssistencia = view.findViewById(R.id.btnPedirAssistencia);




        }

        public void update(Reserva reserva) {
            if(reserva!=null) {
                tvMarca.setText(reserva.getMarca());
                tvModelo.setText(reserva.getModelo());
                tvDataLevantamento.setText(reserva.getData_inicio());
                tvDataDevolucao.setText(reserva.getData_fim());


                String dateFormat = reserva.getData_inicio();
                String dateFormat2 = reserva.getData_inicio();
                SimpleDateFormat format = new SimpleDateFormat("d/m/yyyy");
                Date date = new Date();
                try {

                    Date dataInicio = format.parse(dateFormat);
                    Date dataFim = format.parse(dateFormat2);

                    if(date.compareTo(dataInicio) >= 0 && date.compareTo(dataFim)<= 0 ){
                        btnPedirAssistencia.setVisibility(View.VISIBLE);


                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }








            }
        }
    }
}
