package natura;

import propiedades.CultivoDatos;

/**
 * Clase que representa a una planta de tipo Hoja.
 * 
 * @author Christian
 */
public class Hoja extends Plantae {

    protected Hoja(CultivoDatos data) {
        super(data);
    }

    /**
     * Al ser de tipo hoja, mueren directamente si no se riegan.
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
                super.estaViva = false;
            }
        }
    }
}
