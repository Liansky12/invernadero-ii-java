package natura.plantas;

import interfaces.ITriturable;
import natura.Plantae;
import propiedades.AlmacenPropiedades;

/**
 * Clase que reprenta una planta de tipo Cereal. Avena es triturable.
 * 
 * @author Christian
 */
public class Avena extends Plantae implements ITriturable {
    /**
     * Constructor sin parámetros. Llama al constructor padre y se pasan las
     * propiedades del cultivo.
     */
    public Avena() {
        super(AlmacenPropiedades.AVENA);
    }
}
