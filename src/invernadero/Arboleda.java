package invernadero;

import java.util.Random;

import natura.Arbol;
import natura.Autorregable;

/**
 * Clase que representa el terreno en el cual se plantan árboles.
 * 
 * @author Christian
 */
public class Arboleda extends Huerto {
    /** Una vez por arboleda. Cuesta 500 **/
    private boolean riegoPorGoteo;

    /**
     * Constructor parametrizado. Llama al de huerto.
     * 
     * @param nombreArboleda nombre de la arboleda.
     */
    public Arboleda(String nombreArboleda) {
        super(nombreArboleda, "Arboleda", "Árboles");
        this.riegoPorGoteo = false;
    }

    /**
     * Método que elimina todos los árboles muertos. Si un árbol creció por
     * completo, dará la mitad de su precio original.
     * 
     * @return el número de árboles eliminados.
     */
    @Override
    public int plow() {
        int plantasEliminadas = 0;
        for (int i = 0; i < plantas.length; i++) {
            if (super.plantas[i] != null && plantas[i].isDead()) {
                if (((Arbol) super.plantas[i]).getGrowed()) {
                    int precioMedio = super.plantas[i].getPrice() / 2;
                    Simulador.monedas.sumarMonedas(precioMedio);
                }
                plantasEliminadas++;
                plantas[i] = null;
            }
        }
        return plantasEliminadas;
    }

    /**
     * Método que elimina los árboles. Cuesta la mitad de su precio.
     */
    @Override
    public void unroot() {
        for (int i = 0; i < plantas.length; i++) {
            if (plantas[i] != null) {
                int precioMedio = super.plantas[i].getPrice() / 2;
                if (Simulador.monedas.comprobarMonedas(precioMedio)) {
                    Simulador.monedas.restMonedas(precioMedio);
                    plantas[i] = null;
                } else {
                    System.out.println("No se ha podido eliminar el árbol " + super.plantas[i].getName()
                            + ". No hay monedas suficientes.");
                }
            }
        }
    }

    /**
     * Método que devuelve si el sistema de riego por goteo está disponible.
     * 
     * @return si está el sistema de riego.
     */
    public boolean isRiegoPorGoteo() {
        return this.riegoPorGoteo;
    }

    /**
     * Método que habilita el sistema de riego por goteo en la arboleda.
     */
    public void setRiegoPorGoteo() {
        this.riegoPorGoteo = true;
    }

    /**
     * Método que riega los árboles si un árbol no está
     * previamente regado, y según la disponibilidad del agua
     * del tanque. Si se dispone del sistema de riego por goteo,
     * existe un 50% de probabilidad de no consumir agua.
     * 
     * @param tanqueAgua Tanque con cierto número de agua.
     * @return Devuelve el número de árboles regados.
     */
    @Override
    public int waterCrops(TanqueAgua tanqueAgua) {
        Random rand = new Random();
        int aguaDisponible = tanqueAgua.getWater();
        int arbolesRegados = 0;
        int aguaGastada = 0;

        for (int i = 0; i < super.plantas.length; i++) {
            if (super.plantas[i] != null && !super.plantas[i].isWatered() && aguaDisponible > 0) {
                if (super.plantas[i].water()) {
                    if (this.riegoPorGoteo) {
                        // si es true se gasta agua, si es false, se ahorra agua
                        if (rand.nextBoolean()) {
                            aguaDisponible--;
                            aguaGastada++;
                        }
                    } else if (!(super.plantas[i] instanceof Autorregable)) {
                        aguaDisponible--;
                        aguaGastada++;
                    }
                    arbolesRegados++;
                }
            }
        }
        tanqueAgua.restWater(aguaGastada);
        return arbolesRegados;
    }

    /**
     * Muestra el estado de la arboleda.
     */
    @Override
    public void showStatus() {
        System.out.println("=============== " + super.nombreEdif + " ===============");
        System.out.println("Nivel: " + super.nivel + " / 10");
        System.out.println("Ocupación: " + super.getNum() + " / " + super.plantas.length + " ("
                + ((super.getNum() * 100) / super.plantas.length) + "%)");
        System.out.println(super.natura + " vivos: " + super.getAlive() + " / " + super.getNum() + " ("
                + ((super.getNum() == 0) ? "0" : ((super.getAlive() * 100) / getNum())) + "%)");
        System.out.println(super.natura + " regados: " + super.getWatered() + " / " + super.getAlive() + " ("
                + ((super.getAlive() == 0) ? "0" : ((super.getWatered() * 100) / super.getAlive())) + "%)");
        System.out.println(super.natura + " maduros: " + super.getMature() + " / " + super.getAlive() + " ("
                + ((super.getAlive() == 0) ? "0" : ((super.getMature() * 100) / super.getAlive())) + "%)");
    }
}
