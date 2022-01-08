package com.example.botica.Adaptador;

public class GraficoVentas {
    private String name;
    private String total;

    public GraficoVentas(String name, String total) {
        this.name = name;
        this.total = total;
    }

    public String getName() {
        return name;
    }

    public String getTotal() {
        return total;
    }
}
