package com.agg;

public class Pruebas {

    public static void main(String[] args) {
	// write your code here
        Hash<String> tablaHash = new Hash<String>(11);
        tablaHash.setAlfa(0.4f);
        tablaHash.insertar(12, "RAFA");
        tablaHash.insertar(11, "JOEL");
        tablaHash.insertar(3, "LOLA");
        tablaHash.insertar(1, "PEPE");
        tablaHash.insertar(7, "FERNANDO");
        tablaHash.insertar(2, "JOSE");
        tablaHash.borrar(7);
        System.out.println(tablaHash);
        //TODO FUNCIONA TODO MENOS AMPLIAR LA TABLA CUANDO SUPERA UMBRAL
    }
}
