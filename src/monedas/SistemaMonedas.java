package monedas;

import colores.ColoresANSI;

/**
 * Clase que representa a una entidad económica, con la cuál se pueden adquirir
 * mejoras
 * en el Simulador.
 * 
 * @author Christian
 */
public class SistemaMonedas {
    /** Las monedas del sistema **/
    private int monedas;

    /**
     * Constructor básico. Se agregan 100 monedas al inicio.
     */
    public SistemaMonedas() {
        this.monedas = 100;
    }

    /**
     * Método que devuelve el número de monedas disponibles.
     * 
     * @return el número de monedas disponibles.
     */
    public int getMonedas() {
        return this.monedas;
    }

    /**
     * Método que comprueba si existen monedas suficientes sin de restarlas.
     * 
     * @param monedas monedas a comprobar.
     * @return si se pueden restar las monedas pasadas por parámetro.
     */
    public boolean comprobarMonedas(int monedas) {
        if (monedas <= this.monedas) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Método que resta monedas.
     * 
     * @param monedas las monedas a restar.
     */
    public void restMonedas(int monedas) {
        System.out.println("\n" + ColoresANSI.setColor(ColoresANSI.RED, ("[-" + monedas + " monedas]")));
        this.monedas -= monedas;
    }

    /**
     * Método toString.
     * 
     * @return las monedas disponibles.
     */
    @Override
    public String toString() {
        return ColoresANSI.setColor(ColoresANSI.CYAN, (this.monedas + " monedas disponibles"));
    }

    /**
     * Método para agregar monedas.
     * 
     * @param monedas las monedas a agregar.
     */
    public void sumarMonedas(int monedas) {
        this.monedas += monedas;
    }
}