package natura.plantas;

import natura.Sedienta;
import propiedades.AlmacenPropiedades;

/**
 * Clase que representa a una planta de tipo Hortaliza.
 * 
 * @author Christian
 */
public class Pimiento extends Sedienta {
    /**
     * Constructor sin parámetros. Llama al constructor padre y se pasan las
     * propiedades del cultivo.
     */
    public Pimiento() {
        super(AlmacenPropiedades.PIMIENTO);
    }
}