package natura.plantas;

import interfaces.IHidroponica;
import natura.Hoja;
import propiedades.AlmacenPropiedades;

/**
 * Clase que representa a una planta de tipo Hoja. Lechuga es hidropónica.
 * 
 * @author Christian
 */
public class Lechuga extends Hoja implements IHidroponica {
    /**
     * Constructor sin parámetros. Llama al constructor padre y se pasan las
     * propiedades del cultivo.
     */
    public Lechuga() {
        super(AlmacenPropiedades.LECHUGA);
    }
}
