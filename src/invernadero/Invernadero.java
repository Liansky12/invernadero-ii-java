package invernadero;

/**
 * Clase que representa a un invernadero, con capacidad para
 * plantas (Clase Planta) y obtener información acerca de esa clase.
 * 
 * @author Christian
 */
public class Invernadero extends Huerto {
    /**
     * Constructor parametrizado.
     * 
     * Crear el Array de plantas con 10 espacios iniciales.
     * 
     * @param nombre El nombre del invernadero.
     */
    public Invernadero(String nombreInv) {
        super(nombreInv, "Invernadero", "Plantas");
    }

    /**
     * Método que elimina las plantas muertas.
     * 
     * @return el número de plantas que se han eliminado.
     */
    @Override
    public int plow() {
        int plantasEliminadas = 0;
        for (int i = 0; i < plantas.length; i++) {
            if (plantas[i] != null && plantas[i].isDead()) {
                plantasEliminadas++;
                plantas[i] = null;
            }
        }
        return plantasEliminadas;
    }

    /**
     * Método que elimina todas las plantas.
     */
    @Override
    public void unroot() {
        for (int i = 0; i < plantas.length; i++) {
            if (plantas[i] != null) {
                plantas[i] = null;
            }
        }
    }

    /**
     * Método que muestra la información completa del invernadero.
     */
    @Override
    public void showStatus() {
        System.out.println("=============== " + super.nombreEdif + " ===============");
        System.out.println("Nivel: " + super.nivel + " / 10");
        System.out.println("Ocupación: " + super.getNum() + " / " + super.plantas.length + " ("
                + ((super.getNum() * 100) / super.plantas.length) + "%)");
        System.out.println(super.natura + " vivas: " + super.getAlive() + " / " + super.getNum() + " ("
                + ((super.getNum() == 0) ? "0" : ((super.getAlive() * 100) / getNum())) + "%)");
        System.out.println(super.natura + " regadas: " + super.getWatered() + " / " + super.getAlive() + " ("
                + ((super.getAlive() == 0) ? "0" : ((super.getWatered() * 100) / super.getAlive())) + "%)");
        System.out.println(super.natura + " maduras: " + super.getMature() + " / " + super.getAlive() + " ("
                + ((super.getAlive() == 0) ? "0" : ((super.getMature() * 100) / super.getAlive())) + "%)");
    }
}