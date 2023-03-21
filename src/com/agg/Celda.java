package com.agg;

import java.util.Objects;

public class Celda<T> {
    private int clave;
    private T valor;
    private int estado;

    public Celda() {

    }

    public Celda(int clave) {
        Celda.this.clave = clave;
    }

    public void setEstado(int estado) {
        this.estado = estado;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Celda<?> celda = (Celda<?>) o;
        return clave == celda.clave && estado == celda.estado && Objects.equals(valor, celda.valor);
    }
}
