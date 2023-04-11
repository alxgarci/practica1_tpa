package com.agg;

public class Pruebas {

    public static void main(String[] args) {
	// write your code here
        Hash<String> tablaHash = new Hash<String>(11);
        tablaHash.setAlfa(0.3f);
        tablaHash.insertar(12, "RAFA");
        tablaHash.insertar(11, "JOEL");
        tablaHash.insertar(3, "LOLA");
        tablaHash.insertar(1, "PEPE");
        tablaHash.insertar(7, "FERNANDO");
        tablaHash.insertar(2, "JOSE");
        System.out.println(tablaHash.esVacia());
        tablaHash.borrar(7);
        System.out.println("tablaHash.get(11) = " + String.valueOf(tablaHash.get(11)));
        System.out.println(tablaHash);
        //TODO falta comprobar si se supera el umbral introduciendo una celda
        // antes de introducirlo
    }
}
