package com.example.botica.Adaptador;

import java.io.Serializable;
import java.util.Comparator;

public class Cliente implements Serializable {

    private  String  Apellidos;
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
            return c1.getApellidos().compareTo(c2.getApellidos());
        }
    };
    public static Comparator<Cliente> ADESC = new Comparator<Cliente>() {
        @Override
        public int compare(Cliente c1, Cliente c2) {
            return c1.getApellidos().compareTo(c2.getApellidos());
        }
    };


    public Cliente(String apellidos, String nombre, int dni, int idc) {
        Apellidos = apellidos;
        Nombre = nombre;
        this.dni = dni;
        this.idc = idc;
    }

    public String getApellidos() {
        return Apellidos;
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
