package com.example.botica;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
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
import java.util.List;

public class ProductFragment extends Fragment {
    public static final String URL="http://192.168.1.34/tesis/ListarProducto.php";

    Button añadir;
    SearchView buscador;
    RecyclerView recyclerView;
    ProductoAdaptador PROadaptador;
    List<Producto> listaproducto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_product, container, false);

        listaproducto = new ArrayList<>();
        recyclerView=(RecyclerView) root.findViewById(R.id.recycler_product);
        añadir=(Button)root.findViewById(R.id.Añadir_product);
        añadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.product_addFragment);
            }
        });
        buscador=(SearchView)root.findViewById(R.id.product_buscar);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        CARGARproduct(root);
        return root;
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

                                int id_product=productobject.getInt("id_product");
                                String pro_name=productobject.getString("product_name");
                                String pro_price=productobject.getString("prize");
                                String pro_stock=productobject.getString("stock");
                                String categoria=productobject.getString("cat_name");

                                Producto producto= new Producto(id_product,pro_name,pro_price,pro_stock,categoria);
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