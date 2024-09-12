package natura.plantas;

import natura.Secano;
import propiedades.AlmacenPropiedades;

/**
 * Clase que representa a una planta de tipo Legumbre.
 * 
 * @author Christian
 */
public class Garbanzos extends Secano {
    /**
     * Constructor sin parámetros. Llama al constructor padre y se pasan las
     * propiedades del cultivo.
     */
    public Garbanzos() {
        super(AlmacenPropiedades.GARBANZOS);
    }
}
