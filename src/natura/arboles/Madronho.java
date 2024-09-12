package natura.arboles;

import natura.Autorregable;
import propiedades.AlmacenPropiedades;

/**
 * Clase que representa a un árbol de tipo Frutal.
 * 
 * @author Christian
 */
public class Madronho extends Autorregable {
    /**
     * Constructor sin parámetros. Llama al constructor padre y se pasan las
     * propiedades del cultivo.
     */
    public Madronho() {
        super(AlmacenPropiedades.MADRONHO);
    }
}