package natura.plantas;

import natura.Tuberculo;
import propiedades.AlmacenPropiedades;

/**
 * Clase que representa a una planta de tipo Tubéculo.
 * 
 * @author Christian
 */
public class RemolachaAz extends Tuberculo {
    /**
     * Constructor sin parámetros. Llama al constructor padre y se pasan las
     * propiedades del cultivo.
     */
    public RemolachaAz() {
        super(AlmacenPropiedades.REMOLACHA_AZ);
    }
}