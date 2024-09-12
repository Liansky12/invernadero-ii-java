package invernadero;

/**
 * Clase que representa a un tanque de agua, con una
 * capacidad máxima de 20 unidades, con la posibilidad
 * de ser aumentado.
 * 
 * @author Christian
 */
public class TanqueAgua {
    /** Representa a la cantida de agua que hay en el tanque. **/
    private int aguaDisponible;
    /** Es el número máximo de agua que se puede almacenar. **/
    private int maxCapacidad;

    /**
     * Constructor sin parámetros.
     * 
     * Inicializa el agua a 10 y la capacidad máxima
     * a 20 unidades.
     */
    public TanqueAgua() {
        this.aguaDisponible = 10;
        this.maxCapacidad = 20;
    }

    /**
     * Método que obtiene el agua disponible en
     * el tanque.
     * 
     * @return Devuelve el agua disponible.
     */
    public int getWater() {
        return this.aguaDisponible;
    }

    /**
     * Método que obtiene la capacidad maxima de
     * almacenaje de agua.
     * 
     * @return Devuelve un valor que representa
     *         la máxima capacidad del tanque.
     */
    public int getMax() {
        return this.maxCapacidad;
    }

    /**
     * Método que muestra la capacidad del tanque.
     * 
     * El porcentaje es calculado multiplicando el agua
     * disponible por 100 y luego es dividido entre la
     * capacidad máxima.
     */
    public void showStatus() {
        System.out.println(this.toString());
    }

    /**
     * Método toString.
     * 
     * @return la información básica del tanque de agua.
     */
    public String toString() {
        return "Tanque de agua al " + ((aguaDisponible * 100) / maxCapacidad) + "% de su capacidad. [" + aguaDisponible
                + "/" + maxCapacidad + "]";
    }

    /**
     * Método que añade agua al tanque.
     * 
     * No permitirá aumentar si la cantidad de agua
     * introducida más el agua disponible,
     * es superior a la máxima capacidad.
     * 
     * @param cantidadAgua Cantidad de agua que se quiere
     *                     introducir al tanque.
     * 
     * @return Devuelve si se ha podido añadir agua al tanque o no.
     */
    public boolean addWater(int cantidadAgua) {
        if ((cantidadAgua + aguaDisponible) > maxCapacidad) {
            System.out.println("No se ha podido añadir agua al tanque, ha llegado a su máxima capacidad");
            return false;
        } else {
            aguaDisponible += cantidadAgua;
            return true;
        }
    }

    /**
     * Método que aumenta en 5 unidades la capacidad
     * máxima del tanque.
     */
    public void upgrade() {
        maxCapacidad += 5;
    }

    /**
     * Método que resta ciertas unidades de agua del tanque.
     * 
     * @param restar Cantidad de agua a restar del tanque.
     */
    public void restWater(int restar) {
        this.aguaDisponible = this.aguaDisponible - restar;
    }
}