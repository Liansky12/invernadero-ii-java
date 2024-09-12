package natura.arboles;

import interfaces.IIndustrial;
import natura.Arbol;
import propiedades.AlmacenPropiedades;

/**
 * Clase que representa a un árbol de tipo Frutal.
 * 
 * @author Christian
 */
public class Vid extends Arbol implements IIndustrial {
    /**
     * Constructor sin parámetros. Llama al constructor padre y se pasan las
     * propiedades del cultivo.
     */
    public Vid() {
        super(AlmacenPropiedades.VID);
    }
}
