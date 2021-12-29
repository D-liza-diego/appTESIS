package com.example.botica.Adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.botica.R;

import java.util.ArrayList;
import java.util.Map;

public class ListaAdaptador extends BaseExpandableListAdapter {
    private ArrayList<String> ListAyuda;
    private Map<String, ArrayList> mapchild;
    private Context context;

    public ListaAdaptador(ArrayList<String> listAyuda, Map<String, ArrayList> mapchild, Context context) {
        ListAyuda = listAyuda;
        this.mapchild = mapchild;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return ListAyuda.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return mapchild.get(ListAyuda.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return ListAyuda.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return mapchild.get(ListAyuda.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String titulos = (String) getGroup(i);
        view = LayoutInflater.from(context).inflate(R.layout.listparent,null);
        TextView txtparent= (TextView) view.findViewById(R.id.Lparent);
        txtparent.setText(titulos);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        String items = (String) getChild(i,i1);
        view = LayoutInflater.from(context).inflate(R.layout.listchild,null);
        TextView txtchild= (TextView) view.findViewById(R.id.Lchild);
        txtchild.setText(items);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
