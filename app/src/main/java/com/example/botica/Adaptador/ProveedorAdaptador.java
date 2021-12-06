package com.example.botica.Adaptador;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.botica.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProveedorAdaptador extends RecyclerView.Adapter<ProveedorAdaptador.ProveedorViewHolder> {
    private Context mCtx;
    private List<Proveedor> Listaproveedor;
    private List<Proveedor> Listafiltrada;
    String idruc;
    public static final String delete="http://192.168.1.34/tesis/DeleteProveedor.php";

    public ProveedorAdaptador(Context mCtx, List<Proveedor> listaproveedor) {
        this.mCtx = mCtx;
        Listaproveedor = listaproveedor;
        Listafiltrada= new ArrayList<>();
        Listafiltrada.addAll(listaproveedor);
    }

    @NonNull
    @Override
    public ProveedorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(mCtx);
        View view   = inflater.inflate(R.layout.cardproveedor,null);
        return new ProveedorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProveedorViewHolder holder, int position) {
        Proveedor proveedor = Listaproveedor.get(position);
        holder.txtruc.setText(proveedor.getRUC());
        holder.txtnombre.setText(proveedor.getRazonSocial());
        holder.txtestado.setText(proveedor.getEstado());
        holder.txtborrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder= new AlertDialog.Builder(view.getContext());
                builder.setMessage("¿Desea eliminar al proveedor?");
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        idruc=proveedor.getRUC();
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, delete,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Toast.makeText(view.getContext(), response, Toast.LENGTH_SHORT).show();
                                        Listaproveedor.remove(position);
                                        Listafiltrada.clear();
                                        Listafiltrada.addAll(Listaproveedor);
                                        notifyDataSetChanged();
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(view.getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                               Map<String, String> parametros=new HashMap<>();
                               parametros.put("RUC",idruc);
                               return parametros;
                            }
                        }; Volley.newRequestQueue(view.getContext()).add(stringRequest);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(view.getContext(), "Cancelado", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.create();
                builder.show();
            }
        });

    }
    public void filtrado (String buscador)
    {
        int longitud= buscador.length();
        if(longitud==0)
        {
            Listaproveedor.clear();
            Listaproveedor.addAll(Listafiltrada);
        }else
        {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N)
            {
                List<Proveedor> collecion = Listaproveedor.stream()
                        .filter(i->i.getRazonSocial().toLowerCase().contains(buscador.toLowerCase()))
                        .collect(Collectors.toList());
                Listaproveedor.clear();
                Listaproveedor.addAll(collecion);
            }else
            {
                for(Proveedor pr:Listafiltrada)
                {
                    if(pr.getRazonSocial().toLowerCase().contains(buscador.toLowerCase()))
                    { Listaproveedor.add(pr); }
                }
            }
        }notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {return Listaproveedor.size();   }

    class ProveedorViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtruc, txtnombre,txtestado,txtborrar;
        public ProveedorViewHolder( View itemView) {
            super(itemView);
            txtruc=itemView.findViewById(R.id.CVP_ruc);
            txtnombre=itemView.findViewById(R.id.CVP_Rsocial);
            txtestado=itemView.findViewById(R.id.CVP_estadp);
            txtborrar=itemView.findViewById(R.id.deleteP);
        }
    }
}
