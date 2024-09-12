package natura;

import java.util.Random;

import colores.ColoresANSI;
import propiedades.CultivoDatos;

/**
 * Clase que representa a una entidad planta
 * con atributos sobre su estado.
 * 
 * @author Christian
 */
public abstract class Plantae {
    /** Contador para que la planta vuelva a estar madura. **/
    protected int cicloDias;
    /** Número de días de la planta (Representa su edad). **/
    protected int numDias;
    /** Si está regada. **/
    protected boolean estaRegada;
    /** Si está viva. **/
    protected boolean estaViva;
    /** Si está madura. **/
    protected boolean estaMadura;
    /** Datos de la planta. **/
    protected final CultivoDatos data;

    /**
     * Constructor para árboles con propiedades.
     * Se le asigna el siguiente ciclo de maduración.
     * 
     * @param data los datos de la planta.
     */
    protected Plantae(CultivoDatos data) {
        this.data = data;
        this.numDias = 0;
        this.estaRegada = false;
        this.estaViva = true;
        this.estaMadura = false;
        /*
         * se suma los días de maduración más el siguiente ciclo para calcular cuando
         * será su próxima fecha de maduración.
         */
        this.cicloDias = this.data.getMadura() + this.data.getCiclo();
    }

    /**
     * Método que devuelve el nombre de la planta.
     * 
     * @return el nombre común de la planta.
     */
    public String getName() {
        return this.data.getNombre();
    }

    /**
     * Método que devuelve el nombre científico de la planta.
     * 
     * @return el nombre científico de la planta.
     */
    public String getScientificName() {
        return this.data.getCientifico();
    }

    /**
     * Obtiene si está madura.
     * 
     * @return Devuelve <code>true</code> si está madura.
     */
    public boolean isMature() {
        return this.estaMadura;
    }

    /**
     * Este método indica si la planta está
     * muerta.
     * 
     * @return Devuelve <code>true</code> si no está viva.
     */
    public boolean isDead() {
        return !this.estaViva;
    }

    /**
     * Obtiene si la planta está regada o no.
     * 
     * @return Devuelve true si está regada.
     */
    public boolean isWatered() {
        return this.estaRegada;
    }

    /**
     * Método que devuelve el precio de la planta
     * 
     * @return el precio de la planta.
     */
    public int getPrice() {
        return this.data.getCoste();
    }

    /**
     * Método que devuelve la cantidad de monedas que genera la planta por producto
     * obtenido.
     * 
     * @return el número de monedas por producto.
     */
    public int getGain() {
        return this.data.getMonedas();
    }

    /**
     * Método que devuelve el nombre del producto de la planta.
     * 
     * @return el nombre del producto que genera la planta.
     */
    protected String getProduct() {
        return this.data.getProducto();
    }

    /**
     * Método que muestra por pantalla el estado
     * de la planta.
     */
    public void showStatus() {
        System.out.println(this.data.getNombre() + " ---------------");
        System.out.println("Edad: " + this.numDias + " días");
        System.out.println("Viva: " + ((this.estaViva) ? "Si" : "No"));
        System.out.println("Regada: " + ((this.estaRegada) ? "Si" : "No"));
        System.out.println("Madura: " + ((this.estaMadura) ? "Si" : "No"));
    }

    /**
     * Método que riega la planta si está viva.
     * 
     * @return Devuelve si se ha regado o no.
     */
    public boolean water() {
        if (this.estaViva) {
            return this.estaRegada = true;
        } else {
            return false;
        }
    }

    /**
     * Aumenta de día a la planta/árbol. Estará madura cuando llegue
     * a su edad de maduración o llegue al día del ciclo de maduración.
     * Tras pasar de día, vuelve a no estar regada.
     */
    public void grow() {
        if (this.estaViva) {
            if (this.estaRegada) {
                this.numDias++;
                if (this.numDias == this.data.getMadura()) {
                    this.estaMadura = true;
                }
                if (this.numDias >= this.data.getMadura()) {
                    if (this.data.getCiclo() != -1) {
                        if (this.numDias == this.cicloDias) {
                            this.estaMadura = true;
                            this.cicloDias += this.data.getCiclo();
                        }
                    }
                    if (this.numDias == this.data.getMuerte()) {
                        this.estaViva = false;
                        this.estaMadura = false;
                    }
                }
                this.estaRegada = false;
            } else {
                Random rand = new Random();
                this.estaViva = rand.nextBoolean();
            }
        }
    }

    /**
     * Método que devuelve los productos de la planta/árbol
     * en un valor aleatorio entre la producción mínima y la producción máxima
     * cuando esta está madura.
     * 
     * @return Devuelve un valor aleatorio entre la producción mínima y la
     *         producción máxima de la planta/árbol si está madura sino, devuelve
     *         -1.
     */
    public int harvest() {
        if (this.estaMadura) {
            Random rand = new Random();
            if (this.data.getCiclo() == -1) {
                this.estaViva = false;
            }
            this.estaMadura = false;
            this.estaRegada = false;
            return rand.nextInt(this.data.getProdMin(), this.data.getProdMax() + 1);
        } else {
            return -1;
        }
    }

    /**
     * Reinicia los atributos de la planta a su
     * estado incial.
     */
    protected void replant() {
        this.numDias = 0;
        this.estaRegada = false;
        this.estaViva = true;
        this.estaMadura = false;
        System.out.println("\n" + this.data.getNombre() + " se ha replantado.");
    }

    /**
     * Método que muestra la información de la planta/árbol.
     */
    public void showInfoNatura() {
        System.out.println("\n==========" + this.data.getNombre() + "==========");
        System.out.println("Precio: " + ColoresANSI.setColor(ColoresANSI.PURPLE, this.data.getCoste() + ""));
        System.out.println("Producto: " + this.getProduct());
        System.out.println("Número de productos: " + this.data.getProdMin() + "-" + this.data.getProdMax());
        System.out.println("Monedas por producto: " + this.data.getMonedas() + "/producto");
        System.out.println("Maduración: " + this.data.getMadura() + " días");
        System.out.println("Tiempo de vida: " + this.data.getMuerte() + " días");
        if (this.data.getCiclo() != -1) {
            System.out.println("Ciclo: " + this.data.getCiclo() + " días");
        }
    }

    /**
     * Método toString.
     * @return información básica de la planta.
     */
    public String toString() {
        return this.data.getNombre() + " - Viva: " + ((this.estaViva) ? "Si" : "No") +
                " | Madura: " + ((this.estaMadura) ? "Si" : "No") +
                " | Regada: " + ((this.estaRegada) ? "Si " : "No");
    }

    /**
     * Método que devuelve el número de días para que vuelva a ser madura.
     * 
     * @return el número de días, o -1 si muere al recolectarse.
     */
    public int getCiclo() {
        return this.data.getCiclo();
    }
}