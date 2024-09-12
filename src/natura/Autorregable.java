package natura;

import java.util.Random;

import propiedades.CultivoDatos;

/**
 * Clase que representa a un árbol autorregable.
 * 
 * @author Christian
 */
public class Autorregable extends Arbol {

    /**
     * Constructor básico.
     * 
     * @param data datos de la planta/árbol.
     */
    protected Autorregable(CultivoDatos data) {
        super(data);
    }

    /**
     * No necesitan riego.
     */
    @Override
    public void grow() {
        if (super.estaViva) {
            super.numDias++;
            if (super.numDias == super.data.getMadura()) {
                super.estaMadura = true;
            }
            if (super.numDias >= super.data.getMadura()) {
                if (super.data.getCiclo() != -1) {
                    if (super.numDias == super.cicloDias) {
                        super.estaMadura = true;
                        super.cicloDias += super.data.getCiclo();
                    }

                }
                if (super.numDias == super.data.getMuerte()) {
                    super.estaViva = false;
                    super.estaMadura = false;
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
