package com.example.botica;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.botica.Adaptador.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VentasFragment extends Fragment {
    View root;
    RecyclerView recyclerView;
    VentaAdaptador Vadaptador;
    List<Venta> Listaventa;
    public static final String URL="http://192.168.1.34/tesis/ListarVentas.php";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_ventas, container, false);
        Listaventa = new ArrayList<>();
        recyclerView=(RecyclerView) root.findViewById(R.id.RecicladoSales);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        cargar(root);
        return root;
    }
    void cargar(View root)
    {
        StringRequest stringRequest= new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray sales  = new JSONArray(response);
                            for (int i=0; i<sales.length(); i++)
                            {
                                JSONObject ventaobject = sales.getJSONObject(i);

                                String id=ventaobject.getString("S.id");
                                String total=ventaobject.getString("total");
                                String items=ventaobject.getString("items");
                                String comprobante=ventaobject.getString("comprobante");
                                String estato=ventaobject.getString("S.status");
                                String clientename=ventaobject.getString("C.name");
                                String username=ventaobject.getString("U.name");
                                String fecha=ventaobject.getString("S.created_at");

                               Bundle bundle= new Bundle();
                               bundle.putString("S.id",id);
                               getParentFragmentManager().setFragmentResult("key",bundle);

                                Venta venta= new Venta(id,total,items,comprobante,estato,clientename,username,fecha);
                                Listaventa.add(venta);
                            }
                            Vadaptador= new VentaAdaptador(getActivity(),Listaventa);
                            recyclerView.setAdapter(Vadaptador);

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