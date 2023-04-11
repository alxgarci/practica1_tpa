package com.agg;

import java.util.Objects;

public class Celda<T> {
    private int clave;
    private T valor;
    private int estado;

    //La celda
    public Celda() {
        //Celda creada por defecto esta vacia
        Celda.this.estado = 0;
        Celda.this.valor = null;
        Celda.this.clave = -1;
    }

    public Celda(int clave, T valor) {
        Celda.this.clave = clave;
        Celda.this.valor = valor;
        //Establecemos estado como "ocupado"
        Celda.this.estado = 1;
    }

    public boolean setEstado(int estado) {
        if (estado == 1 || estado == -1 || estado == 0) {
            this.estado = estado;
            return true;
        } else {
            return false;
        }
    }

    public int getEstado() {
        return estado;
    }

    public void setClave(int clave) {
        this.clave = clave;
    }

    public int getClave() {
        return clave;
    }

    public void setValor(T valor) {
        this.valor = valor;
    }

    public T getValor() {
        return valor;
    }

    public boolean equals(Celda<T> celda) {
        return clave == celda.clave && estado == celda.estado && Objects.equals(valor, celda.valor);
    }
}
