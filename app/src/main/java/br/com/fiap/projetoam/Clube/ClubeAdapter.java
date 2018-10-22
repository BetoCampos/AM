package br.com.fiap.projetoam.Clube;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.fiap.projetoam.R;

public class ClubeAdapter extends BaseAdapter {
    private ArrayList<Clube> clubeArrayList;
    LayoutInflater mInflater;
    private Context context;

    ClubeAdapter(ArrayList<Clube> clubeArrayList, Context context) {
        this.clubeArrayList = clubeArrayList;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
    }


    @Override
    public int getCount() {
        return clubeArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return clubeArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Clube clube = clubeArrayList.get(position);

        if(view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_clube, parent, false);
        }

        TextView textNome = view.findViewById(R.id.txtNomeC);
        textNome.setText(clube.getNome());

        TextView textEstadio = view.findViewById(R.id.txtEstadioC);
        String aux = "Estádio: "+ String.valueOf(clube.getEstadio());
        textEstadio.setText(aux);

        TextView textDivisao = view.findViewById(R.id.txtDivisaoC);
        aux = "Divisão: "+ String.valueOf(clube.getDivisao());
        textDivisao.setText(aux);

        return view;
    }
}
