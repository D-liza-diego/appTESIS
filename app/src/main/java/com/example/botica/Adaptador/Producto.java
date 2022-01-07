package com.example.botica.Adaptador;

import java.util.Comparator;

public class Producto {
    private int id_product;
    private String product_name;
    private String product_price;
    private String product_barcode;
    private String product_stock;
    private String product_cat;
    private String product_des;
    private String product_imagen;;
    //ordenar por
    public static Comparator<Producto> NASC = new Comparator<Producto>() {
        @Override
        public int compare(Producto p1, Producto p2) {
            return p1.getProduct_name().compareTo(p2.getProduct_name());
        }
    };
    public static Comparator<Producto> NDESC = new Comparator<Producto>() {
        @Override
        public int compare(Producto p1, Producto p2) {
            return p2.getProduct_name().compareTo(p1.getProduct_name());
        }
    };
    public static Comparator<Producto> SASC = new Comparator<Producto>() {
        @Override
        public int compare(Producto p1, Producto p2) {
            return p1.getProduct_stock().compareTo(p2.getProduct_stock());
        }
    };
    public static Comparator<Producto> SDESC = new Comparator<Producto>() {
        @Override
        public int compare(Producto p1, Producto p2) {
            return p2.getProduct_stock().compareTo(p1.getProduct_stock());
        }
    };

    public Producto(int id_product, String product_name, String product_price, String product_barcode, String product_stock, String product_cat, String product_des, String product_imagen) {
        this.id_product = id_product;
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_barcode = product_barcode;
        this.product_stock = product_stock;
        this.product_cat = product_cat;
        this.product_des = product_des;
        this.product_imagen = product_imagen;
    }

    public int getId_product() {
        return id_product;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getProduct_price() {
        return product_price;
    }

    public String getProduct_barcode() {
        return product_barcode;
    }

    public String getProduct_stock() {
        return product_stock;
    }

    public String getProduct_cat() {
        return product_cat;
    }

    public String getProduct_des() {
        return product_des;
    }

    public String getProduct_imagen() {
        return product_imagen;
    }
}
