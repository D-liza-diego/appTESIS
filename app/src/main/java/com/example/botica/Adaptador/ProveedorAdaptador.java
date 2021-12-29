package com.example.botica.Adaptador;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
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

public class ProveedorAdaptador extends RecyclerView.Adapter<ProveedorAdaptador.ProveedorViewHolder>  {
    private Context mCtx;
    AlertDialog builder;
    Dialog dialog;
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
        holder.txtruc.setText(proveedor.getRuc());
        holder.txtnombre.setText(proveedor.getNombre());
        holder.txtborrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                final View customLayout = view.inflate(view.getContext(), R.layout.proveedordetail, null);
                TextView txtnombre=customLayout.findViewById(R.id.proveedor_detail_nombre);
                txtnombre.setText(proveedor.getNombre());
                TextView txtruc=customLayout.findViewById(R.id.proveedor_detail_ruc);
                txtruc.setText(proveedor.getRuc());
                TextView txtdireccion=customLayout.findViewById(R.id.proveedor_detail_direccion);
                txtdireccion.setText(proveedor.getDireccion());
                TextView txtestado=customLayout.findViewById(R.id.proveedor_detail_estado);
                txtestado.setText(proveedor.getEstado());
                TextView txtcontacto=customLayout.findViewById(R.id.proveedor_detail_contact);
                txtcontacto.setText(proveedor.getContacto());
                builder.setView(customLayout);
                AlertDialog dialog= builder.create();
                dialog.show();
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
                        .filter(i->i.getNombre().toLowerCase().contains(buscador.toLowerCase()))
                        .collect(Collectors.toList());
                Listaproveedor.clear();
                Listaproveedor.addAll(collecion);
            }else
            {
                for(Proveedor pr:Listafiltrada)
                {
                    if(pr.getNombre().toLowerCase().contains(buscador.toLowerCase()))
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
            txtborrar=itemView.findViewById(R.id.deleteP);
        }
    }
}
