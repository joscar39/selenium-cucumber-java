package condominiosDemo.utils;

import java.util.Random;

/**
 * Clase utilitaria para la generación de RUTs chilenos válidos.
 * Incluye el cálculo del dígito verificador mediante el algoritmo Módulo 11.
 */
public class RutGenerator {

    /**
     * Genera un RUT aleatorio válido con su dígito verificador.
     * @param formatted Indica si se desea con puntos y guion (ej: 12.345.678-5)
     * o solo números (ej: 123456785).
     * @return String con el RUT generado.
     */
    public static String generateRandomRut(boolean formatted) {
        Random random = new Random();
        // Rango común de RUTs actuales (entre 10 y 25 millones)
        int numero = 10_000_000 + random.nextInt(15_000_000);

        String dv = calculateDV(numero);

        if (formatted) {
            return String.format("%,d-%s", numero, dv).replace(',', '.');
        } else {
            return String.valueOf(numero) + dv;
        }
    }

    /**
     * Calcula el dígito verificador de un número utilizando Módulo 11.
     * @param rut Número base del RUT.
     * @return Dígito verificador (0-9 o K).
     */
    private static String calculateDV(int rut) {
        int m = 0, s = 1;
        int t = rut;

        // Algoritmo Módulo 11
        for (; t != 0; t /= 10) {
            s = (s + t % 10 * (9 - m++ % 6)) % 11;
        }

        char dv = (char) (s != 0 ? s + 47 : 75);
        return String.valueOf(dv);
    }

    /**
     * Método de prueba rápida
     */
    public static void main(String[] args) {
        System.out.println("RUT Formateado: " + generateRandomRut(true));
        System.out.println("RUT Sin formato: " + generateRandomRut(false));
    }
}