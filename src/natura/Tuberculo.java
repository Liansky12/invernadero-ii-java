package natura;

import java.util.Random;

import propiedades.CultivoDatos;

/**
 * Clase que representa a una planta Tubércula.
 * 
 * @author Christian
 */
public class Tuberculo extends Plantae {

    /**
     * Constructor básico.
     * 
     * @param data datos de la planta.
     */
    protected Tuberculo(CultivoDatos data) {
        super(data);
    }

    /**
     * Las plantas tubérculas tienen un 50% de probabilidad de replantarse al
     * recolectarse.
     */
    @Override
    public int harvest() {
        if (this.estaMadura) {
            Random rand = new Random();
            if (this.data.getCiclo() == -1) {
                this.estaViva = false;
            }
            this.estaMadura = false;
            /* 50% de probabilidad de replantarse */
            if (rand.nextBoolean()) {
                super.replant();
            }
            return rand.nextInt(this.data.getProdMin(), this.data.getProdMax() + 1);
        } else {
            return -1;
        }
    }
}