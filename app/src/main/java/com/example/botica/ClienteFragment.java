package com.example.botica;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.botica.Adaptador.Cliente;
import com.example.botica.Adaptador.ClienteAdaptador;
import com.example.botica.Adaptador.Proveedor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ClienteFragment extends Fragment{

    public static final String URL="http://192.168.1.34/tesis/listarClientes.php";

    SearchView buscador;
    RecyclerView recyclerView;
    ClienteAdaptador Cadaptador;
    List<Cliente> Listacliente;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_cliente, container, false);

        Listacliente = new ArrayList<>();
        recyclerView=(RecyclerView) root.findViewById(R.id.Reciclado);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        
        cargarC(root);
        return root;





    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menuclient,menu);
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem item= menu.findItem(R.id.buscar_client);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Cadaptador.filtrado(s);
                return false;
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.DNIASC:
                Collections.sort(Listacliente, Cliente.DNIASC);
                Cadaptador.notifyDataSetChanged();
                return true;
            case R.id.DNIDES:
                Collections.sort(Listacliente, Cliente.DNIDES);
                Cadaptador.notifyDataSetChanged();
                return true;
            case R.id.ApellidoASC:
                Collections.sort(Listacliente, Cliente.AASC);
                Cadaptador.notifyDataSetChanged();
                return true;
            case R.id.ApellidoDES:
                Collections.sort(Listacliente, Cliente.ADESC);
                Cadaptador.notifyDataSetChanged();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void cargarC(View root){
        StringRequest stringRequest= new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray client  = new JSONArray(response);
                            for (int i=0; i<client.length(); i++)
                            {
                                JSONObject clientobject = client.getJSONObject(i);

                                int id=clientobject.getInt("idCliente");
                                String apellido=clientobject.getString("Apellidos");
                                String nombre=clientobject.getString("Nombre");
                                int dni=clientobject.getInt("dni");
                                Cliente cliente= new Cliente(apellido,nombre,dni,id);
                                Listacliente.add(cliente);
                            }
                            Cadaptador= new ClienteAdaptador(getActivity(), Listacliente);
                            recyclerView.setAdapter(Cadaptador);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        Volley.newRequestQueue(getContext()).add(stringRequest);

    }



}