package com.example.botica;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.botica.Adaptador.Cliente;
import com.example.botica.Adaptador.ClienteAdaptador;
import com.example.botica.Adaptador.Producto;
import com.example.botica.Adaptador.ProductoAdaptador;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductFragment extends Fragment {
    public static final String URL="http://192.168.1.34/tesis/ListarProducto.php";

    Button añadir;
    SearchView buscador;
    RecyclerView recyclerView;
    ProductoAdaptador PROadaptador;
    List<Producto> listaproducto;
    int i,j;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_product, container, false);

        listaproducto = new ArrayList<>();
        recyclerView=(RecyclerView) root.findViewById(R.id.recycler_product);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        CARGARproduct(root);
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menuproduct,menu);
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem item= menu.findItem(R.id.buscar_product);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                PROadaptador.filtrado(s);
                return false;
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.PNOMBREASC:
                i++;
                Handler handler= new Handler();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        i=0;
                    }
                };
                if (i==1){
                    Collections.sort(listaproducto, Producto.NASC);
                    PROadaptador.notifyDataSetChanged();
                }
                if (i==2)
                {
                    Collections.sort(listaproducto, Producto.NDESC);
                    PROadaptador.notifyDataSetChanged();
                }
                if (i==3)
                {
                    i=0;
                }

                return true;

            case R.id.PSTOCKASC:
                j++;
                Handler h= new Handler();
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        j=0;
                    }
                };
                if (j==1){
                    Collections.sort(listaproducto, Producto.SASC);
                    PROadaptador.notifyDataSetChanged();
                }
                if (j==2)
                {
                    Collections.sort(listaproducto, Producto.SDESC);
                    PROadaptador.notifyDataSetChanged();
                }
                if (j==3)
                {
                    j=0;
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void CARGARproduct(View root){
        StringRequest stringRequest= new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray product  = new JSONArray(response);
                            for (int i=0; i<product.length(); i++)
                            {
                                JSONObject productobject = product.getJSONObject(i);

                                int id_product=productobject.getInt("id");
                                String pro_name=productobject.getString("name");
                                String pro_price=productobject.getString("price");
                                String pro_stock=productobject.getString("stock");
                                String pro_description=productobject.getString("description");
                                String categoria=productobject.getString("C.name");
                                String pro_barcode=productobject.getString("barcode");
                                String imagen=productobject.getString("image");

                                Producto producto= new Producto(id_product,pro_name,pro_price,pro_barcode,pro_stock,categoria,pro_description,imagen);
                                listaproducto.add(producto);
                            }
                            PROadaptador= new ProductoAdaptador(getActivity(), listaproducto);
                            recyclerView.setAdapter(PROadaptador);

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