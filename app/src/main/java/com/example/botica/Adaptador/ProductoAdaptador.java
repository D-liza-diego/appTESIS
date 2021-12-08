package com.example.botica.Adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.botica.R;

import java.util.List;

public class ProductoAdaptador extends RecyclerView.Adapter<ProductoAdaptador.ProductoViewHolder> {
    private Context mCtx;
    private List<Producto> Listaproducto;
    private List<Producto> Listafiltrada;

    public ProductoAdaptador(Context mCtx, List<Producto> listaproducto) {
        this.mCtx = mCtx;
        Listaproducto = listaproducto;
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
    }

    @Override
    public int getItemCount() {return Listaproducto.size();}

    public class ProductoViewHolder extends RecyclerView.ViewHolder {
        TextView txtpname, txtpstock,txtpprice,txtborrar,txtpcat;
        public ProductoViewHolder( View itemView) {
            super(itemView);
            txtpname=itemView.findViewById(R.id.product_name);
            txtpprice=itemView.findViewById(R.id.product_price);
            txtpstock=itemView.findViewById(R.id.product_stock);
            txtpcat=itemView.findViewById(R.id.product_cat);
            txtborrar=itemView.findViewById(R.id.deleteP);
        }

    }
}
