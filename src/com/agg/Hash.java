package com.agg;

import java.util.Arrays;

public class Hash<T> {
    private Celda<T>[] contenedor;
    private int numElementos;
    private float alfaMaximo;

    //Creamos la tabla, donde por defecto al no especificar nada
    // en el enunciado nos dicen que tenemos que poner un tamanio
    // de 7 y un alfaMax de 0.8 (80%)
    public Hash() {
        //Por defecto N=7 y alfa=80%
        Celda<T> celdaVacia = new Celda<>();
        Celda<T>[] contAux = new Celda[7];
        Arrays.fill(contAux, celdaVacia);
        this.alfaMaximo = 0.8f;
        this.contenedor = contAux;
        this.numElementos = 0;
    }
    //Creamos una tabla Hash con celdas vacias por defecto
    // rellenando su contenedo con estas.
    // El numero de elementos en la tabla sera 0
    // ya que la tabla estara vacia al crearla.
    // alfaMax lo establecemos al 80% (0.8) al no especificarlo,
    // como nos pone en el enunciado
    public Hash(int capacidad) {
        Celda<T> celdaVacia = new Celda<>();
        Celda<T>[] contAux = new Celda[capacidad];
        Arrays.fill(contAux, celdaVacia);
        this.alfaMaximo = 0.8f;
        this.contenedor = contAux;
        this.numElementos = 0;
    }

    //Creamos una tabla Hash con celdas vacias por defecto
    // rellenando su contenedo con estas y estableciendo
    // el alfaMax. El numero de elementos en la tabla sera 0
    // ya que la tabla estara vacia al crearla.
    public Hash(int capacidad, float alfaMax) {
        Celda<T> celdaVacia = new Celda<>();
        Celda<T>[] contAux = new Celda[capacidad];
        Arrays.fill(contAux, celdaVacia);
        this.contenedor = contAux;
        this.alfaMaximo = alfaMax;
        this.numElementos = 0;
    }

    //Aumentamos el numero de elementos en la tabla para comprobar
    // el factor de carga, si se supera el alfaMaximo, redimensionamos
    // primero la tabla y luego insertamos el dato.
    //Si no se supera el alfaMaximo se comprueba si la clave ya existe en
    // la tabla y si no existe se resuelven colisiones hasta que se pueda insertar
    // en celda con estado -1 o 0 (borrada o vacia).
    // Si esta ok, se aumenta el numero de elementos en tabla.
    public void insertar(int clave, T v) {
        this.numElementos = numElementos+1;
        if (factorCarga() > alfaMaximo) {
            //El factor de carga es mayor que alfa, no se inserta elemento
            // y se amplia tabla
            this.redimensionar();
            this.insertar(clave, v);
        } else {
            this.numElementos = numElementos-1;
            boolean cont = true;
            int colisiones = 0;
            if (get(clave) == null) {

                //Comprobamos si la clave ya esta en la tabla y no lo insertamos entonces
                while (cont) {
                    int posicion = funcionHash(clave, colisiones);
                    if (hayColision(posicion)) {
                        //La cenlda esta ocupada, colision
                        colisiones++;
                    } else {
                        //La celda esta vacia o borrada, insertamos
                        Celda<T> celda = new Celda<T>(clave, v);
                        contenedor[posicion] = celda;

                        cont = false;
                    }
                }
                this.numElementos = numElementos+1;
            }
        }

    }

    //Buscamos la clave de la misma forma que insertamos, si hay colision
    // la resolvemos comprobando si la clave es la misma o si no aumentando
    // el contador de colisiones. Si la celda esta borrada tambien aumentamos
    // el contador de colisiones. Si no se cumple ninguna de estas dos condiciones,
    // El estado de la celda sera 0 (esta vacia), por lo tanto no se puede continuar
    // y no se ha encontrado la celda buscada

    public boolean borrar(int clave) {
        boolean cont = true;
        boolean borrado = false;
        int colisiones = 0;
        while (cont) {
            int posicion = funcionHash(clave, colisiones);
            if (hayColision(posicion)) {
                if (contenedor[posicion].getClave() == clave) {
                    contenedor[posicion].setEstado(-1);
                    cont = false;
                    borrado = true;
                } else {
                    colisiones++;
                }
            }  else if (contenedor[posicion].getEstado() == -1) {
                colisiones++;
            } else {

                cont = false;
            }
        }
        //Si se ha borrado disminuimos el numero de elementos en la tabla, si no no.
        this.numElementos = (borrado)? this.numElementos-1:this.numElementos;
        return borrado;
    }

    //Obtenemos un dato con X clave, haciendo la funcion hash y
    // resolviendo colisiones, de la misma forma que lo hemos insertado
    // hasta llegar a la celda en ceustion y devolvemos el valor.
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
                    if (contenedor[posicion].getEstado() == 0) {
                       cont = false;
                    } else {
                        colisiones++;
                    }
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

    //Comprobamos si la tabla esta vacia comprobando el estado
    // de cada celda (si es 1 esta ocupada y si es -1 esta borrada),
    // solo sera tabla hash vacia si todas las celdas tienen de estado
    // 0 (vacio)
    public boolean esVacia() {
        boolean esVacia = true;
        for (Celda<T> c: contenedor) {
            if (c.getEstado() == 1 || c.getEstado() == -1) {
                esVacia = false;
            }
        }
        return esVacia;
    }

    public float getAlfa() {
        return alfaMaximo;
    }

    //Establecemos el alfaMaximo, solo se establece alfaMax si
    // el valor introducido es mayor que el actual y si se encuentra
    // entre 1.0 y 0.0
    public void setAlfa(float alfaMax) {
        if (alfaMax > 1.0f || alfaMax < 0.0f || alfaMax < alfaMaximo) {
            System.out.println("Alfa max no puede ser < alfaMaximo, y tiene que estar entre 0.0 y 1.0");
        } else {
            this.alfaMaximo = alfaMax;
        }
    }

    public int getNumElementos() {
        return numElementos;
    }

    //Calculamos el factor de carga:
    //numero de elementos en tabla/tamanio tabla
    //Hacemos cast de numElementos a float para que no haya
    // errores de conversion al devolver un float
    public float factorCarga() {
        return (float)numElementos/contenedor.length;
    }

    private boolean hayColision(int index) {
        //Al insertar, si la celda tiene estado 1, esta ocupada
        // y por lo tanto hay colision
        return contenedor[index].getEstado() == 1;
    }

    //Funcion hash donde si superamos el numero N de la tabla
    // hacemos el modulo del numero resultante para que vuelva
    // a entrar en el rango de la tabla (N)
    private int funcionHash(int clave, int colisiones) {
        int n = hash1(clave) + hash2(clave, colisiones);
        if (n > contenedor.length) {
            return Math.floorMod(n, contenedor.length);
        } else {
            return n;
        }
    }

    //Funcion hash 1 especificada en el ejercicio:
    //H1 (clave) = clave mod N
    private int hash1(int clave) {
        return Math.floorMod(clave, contenedor.length);
    }

    //Funcion hash 2 especificada en el ejercicio:
    //H2 (clave, colisiones) = colisiones * (7 – (clave mod 7))
    private int hash2(int clave, int colisiones) {
        return colisiones * (7 - (Math.floorMod(clave, 7)));
    }

    //NT = 2*N, para el nuevo tamanio sacamos el siguiente primo a NT
    //Creamos una clase Hash auxiliar, introducimos los datos en
    // la nueva tabla y reasignamos esa tabla a nuestra funcion hash actual
    private void redimensionar() {
        int nt = contenedor.length*2;
        int nuevoTamanio = siguientePrimo(nt);
        Hash<T> hashAux = new Hash<>(nuevoTamanio, alfaMaximo);
        for (Celda<T> celdaAnteriorTabla : contenedor) {
            if (celdaAnteriorTabla.getEstado() == 1) {
                hashAux.insertar(celdaAnteriorTabla.getClave(), celdaAnteriorTabla.getValor());
            }
        }
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

    //toString que se especifica en el ejercicio para mostrar
    //la tabla hash resultante. Se usa StringBuilder y
    //String.format() para mostrar los datos y formatearlos
    //para que ocupen X espacios y sea mejor legible
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(String.format("TABLA HASH RESULTANTE (N = %d) (TAM = %d) (\u03B1 = %f) (\u03B1max = %f)\n", contenedor.length, numElementos, factorCarga(), alfaMaximo));
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
                    String.format("%1$"+10+"s", String.valueOf(c.getValor())
                    )));
            pos++;
        }
        return res.toString();
    }
}
