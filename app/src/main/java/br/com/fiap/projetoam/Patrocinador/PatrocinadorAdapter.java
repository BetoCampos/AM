package br.com.fiap.projetoam.Patrocinador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.fiap.projetoam.R;

public class PatrocinadorAdapter extends BaseAdapter {
    private ArrayList<Patrocinador> patrocinadorArrayList;
    LayoutInflater mInflater;
    private Context context;

    PatrocinadorAdapter(ArrayList<Patrocinador> patrocinadorArrayList, Context context) {
        this.patrocinadorArrayList = patrocinadorArrayList;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
    }


    @Override
    public int getCount() { return patrocinadorArrayList.size(); }

    @Override
    public Object getItem(int position) {
        return patrocinadorArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Patrocinador patrocinador = patrocinadorArrayList.get(position);

        if(view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_patrocinador, parent, false);
        }

        TextView textNome = view.findViewById(R.id.txtNomeP);
        textNome.setText(patrocinador.getNome());

        TextView textRamo = view.findViewById(R.id.txtRamoP);
        String aux = "Ramo: "+ String.valueOf(patrocinador.getRamo());
        textRamo.setText(aux);

        TextView textPorte = view.findViewById(R.id.txtPorteP);
        aux = "Porte: "+ String.valueOf(patrocinador.getPorte());
        textPorte.setText(aux);

        TextView textValor = view.findViewById(R.id.txtValorP);
        aux = "Valor: "+ String.valueOf(patrocinador.getValor());
        textValor.setText(aux);

        return view;
    }
}
