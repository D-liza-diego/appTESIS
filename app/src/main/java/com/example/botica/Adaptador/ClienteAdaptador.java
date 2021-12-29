package com.example.botica.Adaptador;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

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

public class ClienteAdaptador extends RecyclerView.Adapter<ClienteAdaptador.ClienteViewHolder>  {

    private Context mCtx;
    private List<Cliente> Listacliente;
    private List<Cliente> Listafiltrada;
    String dic;
    public static final String DC="http://192.168.1.34/tesis/DeleteClientes.php";



    public ClienteAdaptador(Context mCtx, List<Cliente> listacliente) {
        this.mCtx = mCtx;
        Listacliente = listacliente;
        Listafiltrada= new ArrayList<>();
        Listafiltrada.addAll(Listacliente);
    }


    @Override
    public ClienteViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.cardcliente, null);
        return new ClienteAdaptador.ClienteViewHolder(view);
    }

    @Override
    public void onBindViewHolder( ClienteViewHolder holder,  int position) {
        Cliente cliente = Listacliente.get(position);
        holder.txtapellidos.setText(cliente.getApellidos());
        holder.txtnombre.setText(cliente.getNombre());
        holder.txtdni.setText(String.valueOf(cliente.getDni()));
        holder.txtmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(mCtx,holder.txtmenu);
                popupMenu.inflate(R.menu.menucrud);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        switch (menuItem.getItemId())
                        {
                            case R.id.Meditar:
                                Bundle bundle= new Bundle();
                                bundle.putSerializable("key",cliente);
                                Navigation.findNavController(view).navigate(R.id.clienteEditFragment,bundle);
                                break;
                            case R.id.Mborrar:
                                AlertDialog.Builder builder= new AlertDialog.Builder(view.getContext());
                                builder.setMessage("¿Desea eliminar al cliente?");
                                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Bundle bundle= new Bundle();
                                        bundle.putSerializable("key",cliente);
                                        dic=(String.valueOf(cliente.getIdc()));
                                        StringRequest stringRequest= new StringRequest(Request.Method.POST, DC,
                                                new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response)
                                                    {
                                                        Toast.makeText(view.getContext(), response, Toast.LENGTH_SHORT).show();
                                                        Listacliente.remove(position);
                                                        Listafiltrada.clear();
                                                        Listafiltrada.addAll(Listacliente);
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
                                                Map<String,String> parametros=new HashMap<>();
                                                parametros.put("idCliente",dic);
                                                return parametros;
                                            }
                                        };
                                        Volley.newRequestQueue(view.getContext()).add(stringRequest);

                                        //Navigation.findNavController(view).navigate(R.id.clienteFragment);
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

                                break;

                            default:
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }
    public void filtrado(String txtbuscar)
    {
        int longitud= txtbuscar.length();
        if(longitud==0)
        {
            Listacliente.clear();
            Listacliente.addAll(Listafiltrada);
        }else
        {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N)
            {
                List<Cliente> collecion = Listacliente.stream()
                        .filter(i->i.getNombre().toLowerCase().contains(txtbuscar.toLowerCase()))
                        .collect(Collectors.toList());
                Listacliente.clear();
                Listacliente.addAll(collecion);
            }else
            {
                for(Cliente cl:Listafiltrada)
                {
                    if(cl.getNombre().toLowerCase().contains(txtbuscar.toLowerCase()))
                    { Listacliente.add(cl); }
                }
            }
        }notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return Listacliente.size();
    }

    class ClienteViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtnombre,txtapellidos,txtdni,txtmenu;
        public ClienteViewHolder(View itemView) {
            super(itemView);
            txtnombre=itemView.findViewById(R.id.CVnombres);
            txtapellidos=itemView.findViewById(R.id.CVapellidos);
            txtdni=itemView.findViewById(R.id.CVdni);
            txtmenu=itemView.findViewById(R.id.menu);

        }
    }
}


