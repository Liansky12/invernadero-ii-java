package natura.arboles;

import natura.Arbol;
import propiedades.AlmacenPropiedades;

/**
 * Clase que representa a un árbol de tipo Frutal.
 * 
 * @author Christian
 */
public class Manzano extends Arbol {
    /**
     * Constructor sin parámetros. Llama al constructor padre y se pasan las
     * propiedades del cultivo.
     */
    public Manzano() {
        super(AlmacenPropiedades.MANZANO);
    }
}