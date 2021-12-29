package com.example.botica;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.botica.Adaptador.Cliente;
import com.example.botica.Adaptador.ClienteAdaptador;
import com.example.botica.Adaptador.Proveedor;
import com.example.botica.Adaptador.ProveedorAdaptador;
import com.example.botica.databinding.FragmentHomeBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProveedorFragment extends Fragment {
    public static final String URL="http://192.168.1.34/tesis/ListaProveedor.php";

    Button añadir;
    SearchView buscador;
    RecyclerView recyclerView;
    ProveedorAdaptador Padaptador;
    List<Proveedor> Listaproveedor;
    View root;
    String nombre, ruc,estado, direccion,contacto;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       root = inflater.inflate(R.layout.fragment_proveedor, container, false);

        Listaproveedor = new ArrayList<>();
        recyclerView=(RecyclerView) root.findViewById(R.id.recyclerP);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        CargarP(root);
        return root;


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
       inflater.inflate(R.menu.menuproveedor,menu);
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem item= menu.findItem(R.id.buscar_proveedor);
        MenuItem item1 = menu.findItem(R.id.add_proveedor);
        item1.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Navigation.findNavController(root).navigate(R.id.ayudaFragment);
                return false;
            }
        });
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Padaptador.filtrado(s);
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            /*case R.id.RAscendente:
                Collections.sort(Listaproveedor, Proveedor.RucASC);
                Padaptador.notifyDataSetChanged();
                return true;
            case R.id.RDescendente:
                Collections.sort(Listaproveedor, Proveedor.RucDES);
                Padaptador.notifyDataSetChanged();
                return true;*/
            case R.id.NAscendente:
                Collections.sort(Listaproveedor, Proveedor.NombreASC);
                Padaptador.notifyDataSetChanged();
                return true;
            case R.id.NDescendente:
                Collections.sort(Listaproveedor, Proveedor.NombreDES);
                Padaptador.notifyDataSetChanged();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void CargarP(View root)
    {
        StringRequest stringRequest= new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray supplier  = new JSONArray(response);
                            for (int i=0; i<supplier.length(); i++)
                            {
                                JSONObject proveedorobject = supplier.getJSONObject(i);

                                nombre=proveedorobject.getString("nombre");
                                ruc=proveedorobject.getString("ruc");
                                direccion=proveedorobject.getString("direccion");
                                estado=proveedorobject.getString("estado");
                                contacto=proveedorobject.getString("contacto");
                                Proveedor proveedor= new Proveedor(nombre,ruc,direccion,estado,contacto);
                                Listaproveedor.add(proveedor);
                            }
                            Padaptador= new ProveedorAdaptador(getActivity(), Listaproveedor);
                            recyclerView.setAdapter(Padaptador);

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