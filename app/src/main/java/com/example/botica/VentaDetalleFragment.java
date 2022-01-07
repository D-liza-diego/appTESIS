package com.example.botica;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.botica.Adaptador.Venta;
import com.example.botica.Adaptador.VentaAdaptador;
import com.example.botica.Adaptador.VentaDetalle;
import com.example.botica.Adaptador.VentaDetalleAdaptador;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VentaDetalleFragment extends Fragment {
    View root;
    RecyclerView recyclerView;
    VentaDetalleAdaptador VDadaptador;
    List<VentaDetalle> ListaVD;
    String id;
    Button detalle;
    public static String URL="http://192.168.1.34/tesis/ListarVentaDetail.php";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_venta_detalle, container, false);

        getParentFragmentManager().setFragmentResultListener("key", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull @NotNull String requestKey, @NonNull @NotNull Bundle result) {
                id=result.getString("S.id");
            }
        });

        ListaVD = new ArrayList<>();
        recyclerView=(RecyclerView) root.findViewById(R.id.recyclerVD);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        cargar(root);
        return root;
    }
    void cargar(View root)
    {
        StringRequest stringRequest= new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        try {
                            JSONArray salesD  = new JSONArray(response);
                            for (int i=0; i<salesD.length(); i++)
                            {
                                JSONObject ventaDobject = salesD.getJSONObject(i);

                                String product_name=ventaDobject.getString("P.name");
                                String product_cantidad=ventaDobject.getString("S.quantity");
                                String product_price=ventaDobject.getString("S.price");

                                VentaDetalle ventaDetalle= new VentaDetalle(product_name,product_cantidad,product_price);
                                ListaVD.add(ventaDetalle);
                            }
                            VDadaptador= new VentaDetalleAdaptador(getActivity(),ListaVD);
                            recyclerView.setAdapter(VDadaptador);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros=new HashMap<>();
                parametros.put("sale_id",id);
                return parametros;
            }
        };
        Volley.newRequestQueue(getContext()).add(stringRequest);
    }
}