package natura;

import java.util.Random;

import propiedades.CultivoDatos;

/**
 * Clase que representa a una planta Sedienta.
 * 
 * @author Christian
 */
public class Sedienta extends Plantae {

    /**
     * Constructor básico.
     * 
     * @param data los datos de la planta.
     */
    protected Sedienta(CultivoDatos data) {
        super(data);
    }

    /**
     * Las planta sedientas tienen una probabilidad de morir diferente
     * si no ha sido regada.
     */
    @Override
    public void grow() {
        if (super.estaViva) {
            if (super.estaRegada) {
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
            } else {
                /* 75% de probabilidad de morir si no es regada */
                Random rand = new Random();
                if (rand.nextInt(0, 4) > 0) {
                    super.estaViva = false;
                }
            }
        }
    }
}