package com.example.botica;

import android.os.Bundle;

import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.anychart.anychart.*;
import com.example.botica.Adaptador.Producto;
import com.example.botica.Adaptador.ProductoAdaptador;
import com.example.botica.Adaptador.Proveedor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class PrincipalFragment extends Fragment {
    public static final String URL="http://192.168.1.34/tesis/ListarProducto.php";
    View root;
    List<DataEntry> datos;
    AnyChartView anyChart;
    List<Producto> Lproductos;

    String[] months ={"lunes","martes"};
    int [] salary ={150,152,53};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_principal, container, false);
        Lproductos = new ArrayList<>();
        datos= new ArrayList<>();
        anyChart=(AnyChartView) root.findViewById(R.id.grafico_pie);
        grafico1(root);
        return root;
    }
    void grafico1 (View root)
    {
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
                                Lproductos.add(producto);
                                datos.add(new ValueDataEntry(productobject.getString("name"), Integer.valueOf(productobject.getString("stock"))));
                            }
                            Pie pie=AnyChart.pie();
                            pie.data(datos);
                            pie.setTitle("Salary");
                            anyChart.setChart(pie);
                        } catch (JSONException e) {e.printStackTrace();}
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();}
        });
        Volley.newRequestQueue(getContext()).add(stringRequest);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem item1 = menu.findItem(R.id.item_help);
        item1.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Navigation.findNavController(root).navigate(R.id.ayudaFragment);
                return false;
            }
        });
    }
}