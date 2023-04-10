package com.agg;

import java.util.Arrays;

public class Hash<T> {
    private Celda<T>[] contenedor;
    private int numElementos;
    private float alfaMaximo;

    //TODO IMPORTANTE numElementos es el tamaño de la tabla (N)
    // o los elementos que hay insertados?
    public Hash() {

    }

    public Hash(int capacidad) {
        Celda<T> celdaVacia = new Celda<>();
        Celda<T>[] contAux = new Celda[capacidad];
        Arrays.fill(contAux, celdaVacia);
//        this.contenedor = new Celda[capacidad];
        this.contenedor = contAux;

        //TODO DUDAAAA
        this.numElementos = capacidad;
    }

    public Hash(int capacidad, float alfaMax) {
        Celda<T> celdaVacia = new Celda<>();
        Celda<T>[] contAux = new Celda[capacidad];
        Arrays.fill(contAux, celdaVacia);
        this.contenedor = contAux;
        this.alfaMaximo = alfaMax;
        this.numElementos = capacidad;
    }

    public void insertar(int clave, T v) {
        //TODO Comprobar porcentaje carga de la tabla
        if (factorCarga() > alfaMaximo) {
            //El factor de carga es mayor que alfa, no se inserta elemento
            // y se amplia tabla
            this.redimensionar();
            this.insertar(clave, v);
        } else {
            boolean insertado = false;
            int colisiones = 0;
            if (this.get(clave) == null) {
                //Comprobamos si la clave ya esta en la tabla y no lo insertamos entonces
                while (!insertado) {
                    int posicion = funcionHash(clave, colisiones);
                    if (this.hayColision(posicion)) {
                        //La cenlda esta ocupada, colision
                        colisiones++;
                    } else {
                        //La celda esta vacia o borrada, insertamos
                        Celda<T> celda = new Celda<T>(clave, v);
                        contenedor[posicion] = celda;
                        insertado = true;
                    }
                }
            }
        }

    }
    public boolean borrar(int clave) {
        boolean cont = true;
        boolean borrado = false;
        int colisiones = 0;
        while (cont) {
            int posicion = funcionHash(clave, colisiones);
            if (contenedor[posicion].getClave() == clave) {
                contenedor[posicion] = new Celda<>();
                contenedor[posicion].setEstado(-1);

                cont = false;
                borrado = true;
            } else {
                colisiones++;
                if (colisiones > numElementos*2) {
                    cont = false;
//                    borrado = false;
                    // Ya esta asignado a false, no hace falta
                }
            }
        }
        return borrado;
    }
    public T get(int clave) {
        boolean cont = true;
        T valor = null;
        int colisiones = 0;
        while (cont) {
            int posicion = funcionHash(clave, colisiones);
            if (hayColision(posicion)) {
                if (contenedor[posicion].getClave() == clave) {
                    valor = contenedor[posicion].getValor();
                    cont = false;
                } else {
                    colisiones++;
                }
            } else if (contenedor[posicion].getEstado() == -1) {
                colisiones++;
            } else {
                //El estado de la celda sera 0, esta vacia, no se puede continuar
                cont = false;
            }
        }
        return valor;
    }
    public boolean esVacia() {
        //TODO es comprobar si tabla hash esta vacia?
        boolean esVacia = true;
        for (Celda<T> c: contenedor) {
            if (c.getEstado() != 1) {
                esVacia = false;
            }
        }
        return esVacia;
    }

    public float getAlfa() {
        return alfaMaximo;
    }

    public void setAlfa(float alfaMax) {
        if (alfaMax > 1.0 || alfaMax < 0.0 || alfaMax < alfaMaximo) {
            System.out.println("Alfa max no puede ser < alfaMaximo, y tiene que estar entre 0.0 y 1.0");
        } else {
            this.alfaMaximo = alfaMax;
        }
    }

    public int getNumElementos() {
        return numElementos;
    }

    public float factorCarga() {
        int elementos = 0;
        for (Celda<T> celda: contenedor) {
            if (celda.getEstado() == 1) {
                elementos++;
            }
        }
        return (float)elementos/numElementos;
    }

    private boolean hayColision(int index) {
        //TODO bien?
        //Al insertar, si la celda tiene estado 1, esta ocupada y hay colision
        return contenedor[index].getEstado() == 1;
    }

    private int funcionHash(int clave, int colisiones) {
        int n = hash1(clave) + hash2(clave, colisiones);
        if (n > numElementos) {
            return Math.floorMod(n, numElementos);
        } else {
            return n;
        }
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
        //TODO bien?
        int nt = numElementos*2;
        int nuevoTamanio = siguientePrimo(nt);
        Hash<T> hashAux = new Hash<>(nuevoTamanio);
        hashAux.setAlfa(alfaMaximo);
        for (Celda<T> celdaAnteriorTabla : contenedor) {
            if (celdaAnteriorTabla.getEstado() == 1) {
                hashAux.insertar(celdaAnteriorTabla.getClave(), celdaAnteriorTabla.getValor());
            }
        }
        this.numElementos = nuevoTamanio;
        this.contenedor = hashAux.contenedor;
        this.alfaMaximo = hashAux.alfaMaximo;
    }

    //Calculamos si el numero pasado es primo, comprobando que su resto
    //con los numeros que hay hasta la mitad de su valor es igual a 0.
    //Devolvemos true si es primo, false si no lo es.
    private boolean esPrimo(int numero) {
        boolean numEsPrimo = true;
        int c = 2;
        while (numEsPrimo && (c < numero)) {
            numEsPrimo = !(numero % c == 0);
            c++;
        }
        return numEsPrimo;
    }

    //Buscamos el siguiente primo a un numero dado (nt), comprobamos si
    //ese numero es primo con el anterior metodo, mientras no sea primo el numero,
    //ejecutamos el bucle y comprobamos el siguiente numero hasta encontrar el siguiente
    //primo y lo devolvemos.
    private int siguientePrimo(int nt) {
        int nt_res = nt;
        while (!esPrimo(nt_res)) {
            nt_res++;
        }
        return nt_res;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(String.format("TABLA HASH RESULTANTE (N = %d) (\u03B1 = %.3f)\n", numElementos, factorCarga()));
        res.append("|| INDEX || ESTADO | CLAVE |   VALOR   ||\n");
        int pos = 0;
        for (Celda<T> c: contenedor) {
            //Simplemente para dejarlo mas bonito,
            // %1$Nd escribe un string a partir de un double (d)
            // con "N" espacios a su izquierda, que si no me da toc la tabla.
            res.append(
                    String.format("||%s ||%s |%s |%s ||\n",
                    String.format("%1$"+6+"d", pos),
                    String.format("%1$"+7+"d", c.getEstado()),
                    String.format("%1$"+6+"d", c.getClave()),
                    String.format("%1$"+10+"s", (c.getValor() == null)?" ":String.valueOf(c.getValor())
                    )));
            pos++;
        }
        return res.toString();
    }
}
