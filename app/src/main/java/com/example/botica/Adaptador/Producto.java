package com.example.botica.Adaptador;

public class Producto {
    private int id_product;
    private String product_name;
    private String product_price;
    private String product_stock;
    private String product_cat;

    public Producto(int id_product, String product_name, String product_price, String product_stock, String product_cat) {
        this.id_product = id_product;
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_stock = product_stock;
        this.product_cat = product_cat;
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

    public String getProduct_stock() {
        return product_stock;
    }

    public String getProduct_cat() {
        return product_cat;
    }
}
