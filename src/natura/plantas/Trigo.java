package natura.plantas;

import interfaces.ITriturable;
import natura.Plantae;
import propiedades.AlmacenPropiedades;

/**
 * Clase que representa a una planta de tipo Cereal. Trigo es triturable.
 * 
 * @author Christian
 */
public class Trigo extends Plantae implements ITriturable {
    /**
     * Constructor sin parámetros. Llama al constructor padre y se pasan las
     * propiedades del cultivo.
     */
    public Trigo() {
        super(AlmacenPropiedades.TRIGO);
    }
}