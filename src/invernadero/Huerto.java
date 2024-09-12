package invernadero;

import java.util.Arrays;
import java.util.Random;

import colores.ColoresANSI;
import interfaces.ITriturable;
import natura.Plantae;
import natura.Secano;
import natura.Sedienta;

/**
 * Clase que representa un edificio en el que se pueden plantar cultivos.
 * 
 * @author Christian
 */
public abstract class Huerto {
    /** Nombre del edificio. **/
    protected final String nombreEdif;
    /** Array de clase Plantae, almacena plantas o árboles. **/
    protected Plantae[] plantas;
    /** Nivel del edificio **/
    protected int nivel;
    /** Tipo de edificio **/
    private final String tipo;
    /** Tipo de natura **/
    protected final String natura;

    /**
     * Constructor de Huerto.
     * 
     * @param nombreEdif nombre el edificio.
     * @param tipo       tipo de huerto (invernadero o arboleda).
     * @param natura     el tipo de natura que se va a almacenar.
     */
    protected Huerto(String nombreEdif, String tipo, String natura) {
        this.nombreEdif = nombreEdif;
        this.natura = natura;
        this.plantas = new Plantae[10];
        this.nivel = 1;
        this.tipo = tipo;
    }

    /**
     * Método para obtener el nombre del edificio.
     * 
     * @return Devuelve el nombre del edificio.
     */
    public String getName() {
        return ColoresANSI.setColor(ColoresANSI.CYAN, this.nombreEdif);
    }

    /**
     * Verifica que las posiciones del Array no sean nulas antes de
     * aumentar el contador de plantas plantadas.
     * 
     * @return Devuelve el número de plantas plantadas, sino, 0.
     */
    public int getNum() {
        int naturaPlantada = 0;
        for (Plantae planta : plantas) {
            if (planta != null) {
                naturaPlantada++;
            }
        }
        return naturaPlantada;
    }

    /**
     * Método que obtiene el número de espacios totales de plantas.
     * 
     * @return Devuelve el tamaño del Array de plantas.
     */
    public int getTiles() {
        return this.plantas.length;
    }

    /**
     * Método que devuelve el número de plantas vivas.
     * Si una planta está viva, aumenta el contador en +1.
     * 
     * @return Devuelve el número de plantas vivas, o 0 si no hay
     *         ninguna viva.
     */
    public int getAlive() {
        int naturaViva = 0;
        for (Plantae planta : plantas) {
            if (planta != null && !planta.isDead()) {
                naturaViva++;
            }
        }
        return naturaViva;
    }

    /**
     * Obtiene el número de plantas maduras.
     * 
     * @return Devuelve el número de plantas que están maduras, sino,
     *         retorna 0.
     */
    public int getMature() {
        int naturaMad = 0;
        for (Plantae planta : plantas) {
            if (planta != null && planta.isMature()) {
                naturaMad++;
            }
        }
        return naturaMad;
    }

    /**
     * Este método devuelve el número de plantas regadas.
     * 
     * @return Devuelve el número de plantas regadas, si no hay,
     *         devuelve 0.
     */
    public int getWatered() {
        int naturaRegada = 0;
        for (Plantae planta : plantas) {
            if (planta != null && planta.isWatered()) {
                naturaRegada++;
            }
        }
        return naturaRegada;
    }

    /**
     * Método que devuelve el nivel de mejora del edificio.
     * 
     * @return el número del nivel.
     */
    public int getLevel() {
        return this.nivel;
    }

    /**
     * Método que muestra por pantalla toda la información del edificio.
     */
    public abstract void showStatus();

    /**
     * Método que muestra el estado de cada una de las plantas.
     * Mediante un bucle se recorre el Array de plantas, y con
     * una condicionante de que no sea null.
     * 
     * Se imprime el estado de cada una de las plantas llamando al método
     * showStatus de la clase Planta. Además se ha implementado un contador
     * para cada planta, que aumenta cada vez que una planta no sea
     * nula.
     */
    public void showTileStatus() {
        int contador = 0;

        System.out.println("\n============ " + this.getName() + " ===========");
        for (Plantae planta : plantas) {
            if (planta != null) {
                System.out.print("--------------- |" + (++contador) + "| ");
                planta.showStatus();
            }
        }
        if (contador == 0) {
            System.out.println("\nNo hay " + this.natura + " en " + this.getName());
        }
    }

    /**
     * Muestra la ocupación del edificio. El porcentaje es
     * calculado obteniendo el número de plantas que hay multiplicado
     * por 100 y luego esto es dividido entre el tamaño de Array; que es
     * el número de espacios disponibles. Se concatena un "%".
     */
    public void showCapacity() {
        System.out.println(this.tipo + " " + this.getName() + " al " + ((getNum() * 100) / plantas.length)
                + "% de capacidad. [" + getNum() + "/" + this.plantas.length + "]");
    }

    /**
     * Este método riega todas las plantas y obtiene el número de plantas regadas.
     * 
     * @return Se devuelve el número de plantas que se han podido.
     *         regar
     */
    public int waterCrops() {
        int contador = 0;
        for (Plantae planta : plantas) {
            if (planta != null && planta.water()) {
                contador++;
            }
        }
        return contador;
    }

    /**
     * Método que riega las plantas/árboles si una planta/árbol no está
     * previamente regada, y según la disponibilidad del agua
     * de un tanque.
     * 
     * @param tanqueAgua Tanque con cierto número de agua.
     * @return Devuelve el número de plantas/árboles regadas.
     */
    public int waterCrops(TanqueAgua tanqueAgua) {
        int aguaDisponible = tanqueAgua.getWater();
        int plantasRegadas = 0;
        int aguaGastada = 0;

        for (Plantae planta : plantas) {
            if (planta != null && !planta.isWatered()) {
                if (aguaDisponible > 0) {
                    if (planta instanceof Sedienta && aguaDisponible > 1 && planta.water()) {
                        aguaDisponible -= 2;
                        aguaGastada += 2;
                        plantasRegadas++;
                    } else if (aguaDisponible > 0 && planta.water()) {
                        if (planta instanceof Secano) {
                            Random rand = new Random();
                            if (rand.nextInt(0, 4) > 0) {
                                aguaDisponible--;
                                aguaGastada++;
                            }
                        } else {
                            aguaDisponible--;
                            aguaGastada++;
                        }
                        plantasRegadas++;
                    }
                }
            }
        }
        tanqueAgua.restWater(aguaGastada);
        return plantasRegadas;
    }

    /**
     * Este método hace crecer a cada una de las plantas/árboles, invocando
     * al método grow de la clase Plantae. La planta/árbol aumenta de día sólo
     * si no está muerta.
     */
    public void growCrops() {
        for (Plantae planta : plantas) {
            if (planta != null) {
                planta.grow();
            }
        }
    }

    /**
     * Se mejora el edifcio, aumentando en 10 la capacidad del Array
     * de plantas. Se ha utilizado un método de la clase Arrays, CopyOf.
     * 
     * El nuevo Array es igualado con el anterior, además de añadir diez
     * posiciones, y esto sin afectar al contenido incial del Array.
     * Posteriormente, se muestra un mensaje por pantalla de la mejora
     */
    public void upgrade() {
        if (this.nivel <= 10) {
            Plantae[] newArray = Arrays.copyOf(this.plantas, this.plantas.length + 10);
            this.plantas = newArray;
            System.out.println("\n" + this.tipo + " " + this.getName()
                    + " mejorado. Su capacidad ha aumentado en 10 hasta un total de " + this.plantas.length + ".");
            this.nivel++;
        } else {
            System.out.println("\nNo es posible mejorar este/esta " + this.natura + ". Ha llegado a su nivel máximo.");
        }
    }

    /**
     * Este método añade una planta dada al Array de plantas.
     * 
     * @param planta Objeto de la clase Planta.
     */
    public void addPlanta(Plantae planta) {
        for (int i = 0; i < this.plantas.length; i++) {
            if (plantas[i] == null) {
                plantas[i] = planta;
                break;
            }
        }
    }

    /**
     * Método que recolecta los productos de las plantas/árboles que
     * estén maduros y las planta/árboles que han sido recolectados en
     * el proceso.
     * 
     * @return Devuelve un Array con los productos cosechados en la posición 0,
     *         las plantas/árboles recolectados en la posición 1, las
     *         plantas/árboles
     *         triturables en la posición 2, y la natura muerta en la posición 3.
     */
    public int[] harvest() {
        int contadorProductos = 0;
        int productosRecolectados = 0;
        int naturaRecolectada = 0;
        int naturaMuerta = 0;
        int monedasNaturaTriturables = 0;
        for (Plantae planta : this.plantas) {
            if (planta != null && !planta.isDead() && planta.isMature()) {
                /* Se obtienen los productos de la planta/árbol. */
                contadorProductos = planta.harvest();
                /* Se registra la cosecha. */
                Simulador.stats.registrarCosecha(planta.getName(), contadorProductos);
                /* Se añaden las monedas de ganancia por productos cosechados. */
                Simulador.monedas.sumarMonedas(contadorProductos * planta.getGain());
                /* Se verifica que la planta/árbol sea triturable. */
                if (planta instanceof ITriturable) {
                    /* Si es triturable, se almacenan en una variable. */
                    monedasNaturaTriturables += planta.getGain();
                }
                /* Se guardan en el conjunto de productos cosechados */
                productosRecolectados += contadorProductos;
                /* Si la planta muere al recolectarse */
                if (planta.getCiclo() == -1) {
                    naturaMuerta++;
                }
                /* Las plantas/árboles que se han cosechado. */
                naturaRecolectada++;
            }
        }
        return new int[] { productosRecolectados, naturaRecolectada, monedasNaturaTriturables, naturaMuerta };
    }

    /**
     * Este método elimina todas las plantas muertas del Array
     * de plantas. En el caso de los árboles, se obtienen la mitad de ganancias
     * de su precio.
     * 
     * @return Devuelve el número de plantas o árboles que han sido eliminadas.
     */
    public abstract int plow();

    /**
     * Método que elimina (<code>null</code>) todas las plantas del Array de
     * plantas. Para quitar un árbol cuesta la mitad de su precio.
     */
    public abstract void unroot();

    /**
     * Método toString.
     * 
     * @return la información básica del huerto.
     */
    @Override
    public String toString() {
        return this.tipo + " Nv. " + this.nivel + " - " + this.nombreEdif + "[" + this.getAlive() + "/" + this.getNum()
                + "/" + this.plantas.length + "]";
    }
}