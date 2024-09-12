package natura;

import java.util.Random;

import propiedades.CultivoDatos;

/**
 * Clase que repreenta a un árbol
 * 
 * @author Christian
 */
public class Arbol extends Plantae {
    /** Si ha llegado a la madurez por primera vez. **/
    private boolean growed;

    /**
     * Constructor heredado
     * 
     * @param arbol los datos del árbol.
     */
    public Arbol(CultivoDatos arbol) {
        super(arbol);
        this.growed = false;
    }

    /**
     * Método que devuelve si el árbol ha llegado por
     * primera vez a la madurez
     * 
     * @return si ha madurado por primera vez.
     */
    public boolean getGrowed() {
        return growed;
    }

    /**
     * Método que devuelve el nombre del producto del árbol.
     * 
     * @return el nombre del fruto.
     */
    public String getProduct() {
        return super.data.getProducto();
    }

    /**
     * Método que aumenta de día al árbol. Estos no mueren si no se riegan,
     * pero tampoco avanzarán de día. Cuando lleguen a su edad de muerte no
     * mueren directamente, tiene un 5% de probablidad de morir cada día.
     */
    @Override
    public void grow() {
        if (super.estaViva) {
            if (super.estaRegada) {
                super.numDias++;
                if (this.numDias == super.data.getMadura()) {
                    this.growed = true;
                    super.estaMadura = true;
                }
                if (this.growed) {
                    if (super.data.getCiclo() != -1) {
                        if (super.numDias == super.cicloDias) {
                            super.estaMadura = true;
                            super.cicloDias += super.data.getCiclo();
                        }
                    }
                }
                super.estaRegada = false;
            }

            if (super.numDias >= super.data.getMuerte()) {
                Random rand = new Random();
                if (rand.nextInt(100) + 1 <= 5) {
                    super.estaViva = false;
                    super.estaMadura = false;
                }
            }
        }
    }

    /**
     * Método que replanta el árbol.
     */
    @Override
    public void replant() {
        super.replant();
        this.growed = false;
    }
}