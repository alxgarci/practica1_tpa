package com.agg;

public class Hash<T> {
    private Celda<T>[] contenedor;
    private int numElementos;
    private float alfaMaximo;

    public Hash() {

    }
    public Hash(int capacidad) {
        //TODO
    }
    public Hash(int capacidad, float alfaMax) {
        //TODO
    }
    public void insertar(int clave, T v) {
        //TODO
    }
    public void insertar(int clave) {
        //TODO
    }
    public T get(int clave) {
        //TODO
        return null;
    }
    public boolean esVacia() {
        //TODO
        return false;
    }

    public float getAlfa() {
        return alfaMaximo;
    }

    public void setAlfa(float alfaMax) {
        this.alfaMaximo = alfaMax;
    }

    public int getNumElementos() {
        return numElementos;
    }

    public float factorCarga() {
        //TODO
        return 0;
    }

    private boolean hayColision(int clave) {
        //TODO
        return false;
    }

    private int funcionHash(int clave, int colisiones) {
        //TODO
        return hash1(clave) + hash2(clave, colisiones);
    }

    private int hash1(int clave) {
        //H1 (clave) = clave mod N
        return Math.floorMod(clave, numElementos);
    }

    private int hash2(int clave, int colisiones) {
        //H2 (clave, colisiones) = colisiones * (7 – (clave mod 7))
        return colisiones * (7 - (Math.floorMod(clave, 7)));
    }

    private void redimensionar() {
        //TODO
        int nt = numElementos*2;
        int nuevoTamanio = siguientePrimo(nt);
    }

    private boolean esPrimo(int numero) {
        boolean esPrimo = true;
        int c = 2;
        while (esPrimo && (c < numero)) {
            esPrimo = numero % c != 0;
            c++;
        }
        return esPrimo;
    }

    private int siguientePrimo(int nt) {
        boolean noPrimo = true;
        while (noPrimo) {
            noPrimo = esPrimo(++nt);
        }
        return nt;
    }
}
