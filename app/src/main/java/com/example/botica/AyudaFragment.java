package com.example.botica;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.botica.Adaptador.ListaAdaptador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AyudaFragment extends Fragment {
    View root;
    private ExpandableListView listaex;
    private ListaAdaptador adapter;
    private ArrayList<String> listayuda;
    private Map<String, ArrayList> mapChild;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         root = inflater.inflate(R.layout.fragment_ayuda, container, false);
        listaex=(ExpandableListView) root.findViewById(R.id.Expandible);
        listayuda= new ArrayList<>();
        mapChild = new HashMap<>();
        llenar(root);
        return root;

    }
    void llenar(View root)
    {
        ArrayList<String> list1 = new ArrayList<>();
        ArrayList<String> list2 = new ArrayList<>();
        listayuda.add("Funcionalidad");
        listayuda.add("Acceso");
        list1.add("funcion 1");
        list1.add("funcion 2");
        list2.add("acceso 1");
        list2.add("acceso 2");
        mapChild.put(listayuda.get(0),list1);
        mapChild.put(listayuda.get(1),list2);
        adapter = new ListaAdaptador(listayuda,mapChild,getContext());
        listaex.setAdapter(adapter);

    }
}