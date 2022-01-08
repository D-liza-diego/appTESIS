package com.example.botica;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.*;
import android.widget.SearchView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import java.util.Collections;
import java.util.List;

public class VentasFragment extends Fragment {
    View root;
    int i,j;
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
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menuventas,menu);
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem item= menu.findItem(R.id.buscar_venta);

        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Vadaptador.filtrado(s);
                return false;
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.ventafecha:
                i++;
                Handler handler= new Handler();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        i=0;
                    }
                };
                if (i==1){
                    Collections.sort(Listaventa, Venta.VentaFasc);
                    Vadaptador.notifyDataSetChanged();
                }
                if (i==2)
                {
                    Collections.sort(Listaventa, Venta.VentaFdesc);
                    Vadaptador.notifyDataSetChanged();
                }
                if (i==3)
                {
                    i=0;
                }

                return true;

            case R.id.ventamonto:
                j++;
                Handler h= new Handler();
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        j=0;
                    }
                };
                if (j==1){
                    Collections.sort(Listaventa, Venta.VentaMasc);
                    Vadaptador.notifyDataSetChanged();
                }
                if (j==2)
                {
                    Collections.sort(Listaventa, Venta.VentaMdsc);
                    Vadaptador.notifyDataSetChanged();
                }
                if (j==3)
                {
                    j=0;
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
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