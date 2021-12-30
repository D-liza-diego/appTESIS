package com.example.botica.Adaptador;

import java.io.Serializable;
import java.util.Comparator;

public class Cliente implements Serializable {

    private String Nombre;
    private int dni;
    private int idc;

    //ordenar por
    public static Comparator<Cliente> DNIASC = new Comparator<Cliente>() {
        @Override
        public int compare(Cliente c1, Cliente c2) {
            return c1.getDni() - c2.getDni();
        }
    };
    public static Comparator<Cliente> DNIDES = new Comparator<Cliente>() {
        @Override
        public int compare(Cliente c1, Cliente c2) {
            return c2.getDni() - c1.getDni();
        }
    };
    public static Comparator<Cliente> AASC = new Comparator<Cliente>() {
        @Override
        public int compare(Cliente c1, Cliente c2) {
            return c1.getNombre().compareTo(c2.getNombre());
        }
    };
    public static Comparator<Cliente> ADESC = new Comparator<Cliente>() {
        @Override
        public int compare(Cliente c1, Cliente c2) {
            return c2.getNombre().compareTo(c1.getNombre());
        }
    };

    public Cliente(String nombre, int dni, int idc) {
        Nombre = nombre;
        this.dni = dni;
        this.idc = idc;
    }

    public String getNombre() {
        return Nombre;
    }

    public int getDni() {
        return dni;
    }

    public int getIdc() {
        return idc;
    }
}
