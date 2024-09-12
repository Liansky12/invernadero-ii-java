package colores;

/**
 * ANSI colores de escape para Java.
 * Funciona concatenando un color y un reset a una cadena
 * de texto.
 * ADVERTENCIA: No funciona con todos los
 * IDEs/Terminales.
 * 
 * @author Christian
 * @version 2.0
 */
public enum ColoresANSI {
    /** Devuelve el texto al color original de la consola **/
    RESET("\u001B[0m"),
    /** Texto en cursiva **/
    ITALIC("\u001B[3m"),
    /** Texto en color rojo **/
    RED("\u001B[31m"),
    /** Texto en color verde **/
    GREEN("\u001B[32m"),
    /** Texto en color amarillo **/
    YELLOW("\u001B[33m"),
    /** Texto en color lavanda **/
    PURPLE("\u001B[35m"),
    /** Texto en color azul **/
    CYAN("\u001B[36m");

    /** Variable que almacena los carácteres de escape. **/
    private String val;
    /** Aprueba el método si es true **/
    private static boolean beta;

    /**
     * Constructor que asigna el color.
     * 
     * @param val
     */
    private ColoresANSI(String val) {
        this.val = val;
    }

    /**
     * Método que devuelve el valor de la variable.
     * 
     * @return el valor de <code>val</code>
     */
    public String getVal() {
        return this.val;
    }

    /**
     * Método que asigna si el método <code>ColoresANSI.setColor()</code>
     * devuelve la cadena coloreada.
     * @param setBeta si se activa el modo beta.
     */
    public static void setBeta(boolean setBeta) {
        beta = setBeta;
    }

    /**
     * Método que devuelve una cadena con el color solicitado sólo si se ha
     * activado el modo beta en Simulador, sino, devuelve la misma cadena sin
     * cambios.
     * 
     * @param c     Color a asignar.
     * @param texto cadena a colorear.
     * @return la cadena coloreada.
     */
    public static String setColor(ColoresANSI c, String texto) {
        return ((beta) ? (c.getVal() + texto + RESET.getVal()) : texto);
    }
}