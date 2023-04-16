public class Pruebas {

    public static void main(String[] args) {
//        Hash<String> tablaHash = new Hash<String>(11);
//        tablaHash.setAlfa(0.8f);
        Hash<String> tablaHash = new Hash<String>(11, 0.5f);
//        System.out.println(tablaHash);
        tablaHash.insertar(12, "RAFA");
        tablaHash.insertar(11, "JOEL");
        tablaHash.insertar(3, "LOLA");
        tablaHash.insertar(1, "PEPE");
        tablaHash.insertar(7, "FERNANDO");
        tablaHash.insertar(2, "JOSE");

        System.out.printf("prueba1: %b\n", prueba1(tablaHash));

//        System.out.println(tablaHash);
        tablaHash.insertar(14, "JUAN");
        System.out.printf("prueba2: %b\n", prueba2(tablaHash));
        tablaHash.borrar(7);
        System.out.printf("prueba3: %s\n", prueba3(tablaHash));
//        System.out.println(tablaHash);
        System.out.println("Borrando que no existe");
        tablaHash.borrar(5);
        System.out.println(tablaHash);

    }

    public static boolean prueba1(Hash<String> tabla) {
        return tabla.get(12).equals("RAFA") && tabla.get(11).equals("JOEL") && tabla.get(3).equals("LOLA")
        && tabla.get(1).equals("PEPE") && tabla.get(7).equals("FERNANDO") && tabla.get(2).equals("JOSE");
    }

    public static boolean prueba2(Hash<String> tabla) {
        return tabla.get(14).equals("JUAN");
    }

    public static String prueba3(Hash<String> tabla) {
        return tabla.get(7);
    }
}
