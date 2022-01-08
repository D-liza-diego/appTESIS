package com.example.botica.Adaptador;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Matrix;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.botica.R;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;
import java.util.stream.Collectors;

public class VentaAdaptador extends  RecyclerView.Adapter<VentaAdaptador.VentaViewHolder>  {
    private Context mCtx;
    String id;
    public static String URL="http://192.168.1.34/tesis/ListarVentaDetail.php";
    private List<Venta> ListaVenta;
    private List<Venta> Listafiltrada;

    //Clase detalle
    RecyclerView recyclerView;
    VentaDetalleAdaptador VDadaptador;
    List<VentaDetalle> ListaVD;

    public VentaAdaptador(Context mCtx, List<Venta> listaVenta) {
        this.mCtx = mCtx;
        ListaVenta = listaVenta;
        Listafiltrada= new ArrayList<>();
        Listafiltrada.addAll(listaVenta);
    }

    @NonNull
    @NotNull
    @Override
    public VentaAdaptador.VentaViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.card_ventas, null);
        return new VentaAdaptador.VentaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull VentaAdaptador.VentaViewHolder holder, int position) {
        Venta venta = ListaVenta.get(position);
        holder.txtfecha.setText(venta.getFecha());
        holder.txtgasto.setText("Costo: "+venta.getTotal());
        holder.txtcom.setText("Comprobante: "+venta.getComprobante());
        holder.txtcliente.setText(venta.getClname());
        holder.txtdetalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                final View customLayout = view.inflate(view.getContext(), R.layout.ventas_detail, null);
                TextView detaill=customLayout.findViewById(R.id.sales_detail_ver);
                id=venta.getIdventa();

                TextView txtsfecha=customLayout.findViewById(R.id.sales_detail_date);
                txtsfecha.setText(venta.getFecha());

                TextView txtsclient=customLayout.findViewById(R.id.sales_detail_client);
                txtsclient.setText(venta.getClname().toLowerCase());

                TextView txtsuser=customLayout.findViewById(R.id.sales_detail_user);
                txtsuser.setText(venta.getUsname());

                TextView txtsstate=customLayout.findViewById(R.id.sales_detail_estado);
                txtsstate.setText(venta.getState());

                TextView txtscom=customLayout.findViewById(R.id.sales_detail_voucher);
                txtscom.setText(venta.getComprobante());

                TextView txtscant=customLayout.findViewById(R.id.sales_detail_items);
                txtscant.setText(venta.getItems());

                TextView txttotal=customLayout.findViewById(R.id.sales_detail_total);
                txttotal.setText(venta.getTotal());

                builder.setView(customLayout);
                AlertDialog dialog= builder.create();
                dialog.show();


                detaill.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i("mensaje","dog");
                        //Navigation.findNavController(holder.itemView).navigate(R.id.ventaDetalleFragment);
                        dialog.dismiss();
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(view.getContext());
                        final View customLayout = view.inflate(view.getContext(), R.layout.fragment_venta_detalle, null);
                        ListaVD = new ArrayList<>();
                        recyclerView=(RecyclerView) customLayout.findViewById(R.id.recyclerVD);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(mCtx));

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
                                            VDadaptador= new VentaDetalleAdaptador(mCtx,ListaVD);
                                            recyclerView.setAdapter(VDadaptador);
                                        } catch (JSONException e) {e.printStackTrace();}
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(mCtx, error.getMessage(), Toast.LENGTH_SHORT).show();}
                        })
                        {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String,String> parametros=new HashMap<>();
                                parametros.put("sale_id",id);
                                return parametros;
                            }
                        };
                        Volley.newRequestQueue(mCtx).add(stringRequest);
                        builder1.setView(customLayout);
                        AlertDialog dialog1= builder1.create();
                        dialog1.show();
                    }
                });
            }
        });

    }
    public void filtrado (String buscador)
    {
        int longitud= buscador.length();
        if(longitud==0)
        {
            ListaVenta.clear();
            ListaVenta.addAll(Listafiltrada);
        }else
        {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N)
            {
                List<Venta> collecion = ListaVenta.stream()
                        .filter(i->i.getClname().toLowerCase().contains(buscador.toLowerCase()))
                        .collect(Collectors.toList());
                ListaVenta.clear();
                ListaVenta.addAll(collecion);
            }else
            {
                for(Venta venta:Listafiltrada)
                {
                    if(venta.getClname().toLowerCase().contains(buscador.toLowerCase()))
                    { ListaVenta.add(venta); }
                }
            }
        }notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return ListaVenta.size();
    }

    class VentaViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtfecha,txtgasto,txtcom,txtcliente,txtdetalle;
        Button detalle;
        public VentaViewHolder(View itemView) {
            super(itemView);
            txtfecha=itemView.findViewById(R.id.sales_date);
            txtgasto=itemView.findViewById(R.id.sales_precio);
            txtcom=itemView.findViewById(R.id.sales_voucher);
            txtcliente=itemView.findViewById(R.id.sales_cliente);
            txtdetalle=itemView.findViewById(R.id.view_sales);
        }
    }


}
