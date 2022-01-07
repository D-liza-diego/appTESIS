package com.example.botica.Adaptador;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.botica.R;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductoAdaptador extends RecyclerView.Adapter<ProductoAdaptador.ProductoViewHolder> {
    private Context mCtx;
    private List<Producto> Listaproducto;
    private List<Producto> Listafiltrada;

    public ProductoAdaptador(Context mCtx, List<Producto> listaproducto) {
        this.mCtx = mCtx;
        Listaproducto = listaproducto;
        Listafiltrada= new ArrayList<>();
        Listafiltrada.addAll(listaproducto);
    }

    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(mCtx);
        View view   = inflater.inflate(R.layout.card_product,null);
        return new ProductoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, final int position) {
        Producto producto = Listaproducto.get(position);
        holder.txtpname.setText(producto.getProduct_name());
        holder.txtpprice.setText("precio unit. "+producto.getProduct_price());
        holder.txtpstock.setText("stock: "+producto.getProduct_stock());
        holder.txtpcat.setText("CATEGORIA: "+producto.getProduct_cat());
        holder.txtdetalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                final View customLayout = view.inflate(view.getContext(), R.layout.product_detail, null);
                TextView txtpname=customLayout.findViewById(R.id.product_detail_nombre);
                txtpname.setText(producto.getProduct_name());

                TextView txtpcode=customLayout.findViewById(R.id.product_detail_barcode);
                txtpcode.setText(producto.getProduct_barcode());

                TextView txtdes=customLayout.findViewById(R.id.product_detail_des);
                txtdes.setText(producto.getProduct_des());

                TextView txtpprice=customLayout.findViewById(R.id.product_detail_precio);
                txtpprice.setText(producto.getProduct_price());

                TextView txtpstock=customLayout.findViewById(R.id.product_detail_stock);
                txtpstock.setText(producto.getProduct_stock());

                TextView txtpcat=customLayout.findViewById(R.id.product_detail_cat);
                txtpcat.setText(producto.getProduct_cat());

                ImageView imagen= customLayout.findViewById(R.id.product_detail_imagen);
                Glide.with(mCtx)
                        .load(producto.getProduct_imagen())
                        .into(imagen);
                builder.setView(customLayout);
                AlertDialog dialog= builder.create();
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {return Listaproducto.size();}

    public void filtrado(String txtbuscar)
    {
        int longitud= txtbuscar.length();
        if(longitud==0)
        {
            Listaproducto.clear();
            Listaproducto.addAll(Listafiltrada);
        }else
        {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N)
            {
                List<Producto> collecion = Listaproducto.stream()
                        .filter(i->i.getProduct_name().toLowerCase().contains(txtbuscar.toLowerCase()))
                        .collect(Collectors.toList());
                Listaproducto.clear();
                Listaproducto.addAll(collecion);
            }else
            {
                for(Producto pr:Listafiltrada)
                {
                    if(pr.getProduct_name().toLowerCase().contains(txtbuscar.toLowerCase()))
                    { Listaproducto.add(pr); }
                }
            }
        }notifyDataSetChanged();
    }

    public class ProductoViewHolder extends RecyclerView.ViewHolder {
        TextView txtpname, txtpstock,txtpprice,txtdetalle,txtpcat;
        public ProductoViewHolder( View itemView) {
            super(itemView);
            txtpname=itemView.findViewById(R.id.product_name);
            txtpprice=itemView.findViewById(R.id.product_price);
            txtpstock=itemView.findViewById(R.id.product_stock);
            txtpcat=itemView.findViewById(R.id.product_cat);
            txtdetalle=itemView.findViewById(R.id.view_product);
        }

    }
}
