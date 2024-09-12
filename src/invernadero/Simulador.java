package invernadero;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

import colores.ColoresANSI;
import estadisticas.Estadisticas;
import monedas.SistemaMonedas;
import natura.Arbol;
import natura.Plantae;
import natura.arboles.*;
import natura.plantas.*;
import propiedades.AlmacenPropiedades;

/**
 * Clase que contiene la lógica general del proyecto.
 * 
 * @author Christian
 * @version 2.0
 */
public class Simulador {
    /** Contador para los días que han pasado. **/
    private int numDias;
    /** Array de los invernaderos. Empieza con 5 espacios. **/
    private Invernadero[] invernaderos;
    /** Array de arboledas. **/
    private Arboleda[] arboledas;
    /** Representa al tanque de agua **/
    private TanqueAgua tanqueAgua;
    /** Nombre de la entidad/empresa/partida. **/
    private String nombre;
    /** Las interacciones por teclado del usuario. **/
    private Scanner sc;
    /** Monedas disponibles **/
    public static SistemaMonedas monedas;
    /** Si se dispone de un molino. Compra única **/
    private boolean molino;
    /** Si está comprado el aspersor **/
    private boolean aspersor;
    /** Clase que gestiona las estadísticas de las plantas del sistema. **/
    public static Estadisticas stats;
    /** Array de plantas/árboles del Simulador **/
    private final Plantae[] plantas = { new Avena(), new Garbanzos(), new Lechuga(), new Pimiento(), new RemolachaAz(), new Trigo(), new Kiwi(), new Madronho(), new Manzano(), new Melocotonero(), new Vid() };

    /**
     * Método que inicializa todo el programa solicitando el
     * nombre de la sesión y el nombre del primer invernadero al
     * usuario.
     */
    public void init() {
        this.numDias = 0;
        this.invernaderos = new Invernadero[5];
        this.arboledas = new Arboleda[5];
        this.tanqueAgua = new TanqueAgua();
        this.sc = new Scanner(System.in);
        monedas = new SistemaMonedas();
        this.molino = false;
        this.aspersor = false;
        stats = new Estadisticas(new String[] { AlmacenPropiedades.AVENA.getNombre(), AlmacenPropiedades.GARBANZOS.getNombre(), AlmacenPropiedades.LECHUGA.getNombre(), AlmacenPropiedades.PIMIENTO.getNombre(), AlmacenPropiedades.REMOLACHA_AZ.getNombre(), AlmacenPropiedades.TRIGO.getNombre(), AlmacenPropiedades.KIWI.getNombre(), AlmacenPropiedades.MADRONHO.getNombre(), AlmacenPropiedades.MANZANO.getNombre(), AlmacenPropiedades.MELOCOTONERO.getNombre(), AlmacenPropiedades.VID.getNombre() });
        System.out.println();
        this.beta();
        System.out.print("Introduce el nombre de la entidad/empresa/partida: ");
        this.nombre = sc.nextLine();
        System.out.print("Introduce el nombre del primer invernadero: ");
        Invernadero inv1 = new Invernadero(sc.nextLine());
        this.invernaderos[0] = inv1;
        System.out.println("\nBienvenido, " + ColoresANSI.setColor(ColoresANSI.YELLOW, this.nombre) + ".");
    }

    /**
     * Método que activa el modo beta en el Simulador.
     */
    private void beta() {
        char select = ' ';
        do {
            System.out.print("¿Habilitar opciones experimentales? [y/n] : ");
            select = sc.nextLine().charAt(0);
        } while (select != 'y' && select != 'n');
        ColoresANSI.setBeta((select == 'y'));
    }

    /**
     * Este método representa un menú de opciones, del cual
     * el usuario podrá elegir.
     */
    public void menu() {
        System.out.println("========= Menú principal =========");
        System.out.println("1.- Estado general");
        System.out.println("2.- Estado cultivos");
        System.out.println("3.- Informes");
        System.out.println("4.- Naturpedia");
        System.out.println("5.- Pasar día");
        System.out.println("6.- Recolectar agua");
        System.out.println("7.- Regar");
        System.out.println("8.- Plantar");
        System.out.println("9.- Cosechar");
        System.out.println("10.- Desbrozar");
        System.out.println("11.- Arrancar");
        System.out.println("12.- Mejorar");
        System.out.println("13.- Pasar varios días");
        System.out.println("14.- Salir");
        System.out.print("\n· Seleccione una opción: ");
    }

    /**
     * Este método lista la información general de los
     * invernaderos.
     * 
     * @return Devuelve el número de invernaderos que no son nulos.
     */
    public int menuInv() {
        int contador = 0;
        System.out.println("\n--------------------------- Invernaderos ---------------------------");
        System.out.println("[Plantas vivas / Plantas plantadas / Terreno total]");
        System.out.println("0.- Cancelar");
        for (Invernadero invernadero : this.invernaderos) {
            if (invernadero != null) {
                System.out.println((++contador) + ".- " + invernadero.getName() + " [" + invernadero.getAlive() + "/" + invernadero.getNum() + "/" + invernadero.getTiles() + "]");
            }
        }
        return contador;
    }

    /**
     * Este método muestra el menú de invernaderos
     * y devuelve un número entero que representa la
     * selección del usuario.
     * 
     * @return Devuelve un número introducido por teclado.
     */
    public int selectInv() {
        return this.select(this.menuInv());
    }

    /**
     * Método que lista los árboles.
     * 
     * @return el número de arboledas que no son nulas.
     */
    public int menuArb() {
        int contador = 0;
        System.out.println("\n--------------------------- Arboledas ---------------------------");
        System.out.println("[Árboles vivos / Árboles plantados / Árboles totales]");
        System.out.println("0.- Cancelar");
        for (Arboleda arboleda : this.arboledas) {
            if (arboleda != null) {
                System.out.println((++contador) + ".- " + arboleda.getName() + " [" + arboleda.getAlive() + "/" + arboleda.getNum() + "/" + arboleda.getTiles() + "]");
            }
        }
        return contador;
    }

    /**
     * Método que permite seleccionar una arboleda.
     * 
     * @return el número introducido por teclado.
     */
    public int selectArb() {
        return this.select(this.menuArb());
    }

    /**
     * Método que muestra el estado de todos los invernaderos y arboledas, el tanque
     * de agua el día actual, si se dispone de molino y las monedas actuales.
     */
    public void showGeneralStatus() {
        System.out.println("\n========== Invernaderos ==========");
        for (Invernadero invernadero : this.invernaderos) {
            if (invernadero != null) {
                invernadero.showStatus();
            }
        }
        System.out.println("---------------------------------");
        System.out.println("\n========== Arboledas ==========");
        if (this.invArbExisten(2) > 0) {
            for (Arboleda arboleda : this.arboledas) {
                if (arboleda != null) {
                    arboleda.showStatus();
                }
            }
        } else {
            System.out.println(ColoresANSI.setColor(ColoresANSI.ITALIC, "Aún no se ha adquirido ninguna arboleda") + ".");
        }

        System.out.println("---------------------------------");
        this.tanqueAgua.showStatus();
        System.out.println("Día actual: " + ColoresANSI.setColor(ColoresANSI.PURPLE, this.numDias + ""));
        System.out.println("Molino: "
                + ((this.molino)
                        ? ColoresANSI.setColor(ColoresANSI.CYAN,
                                "Activado para el conjunto de plantas triturables. Incremento del 20% de ganancias.")
                        : "No comprado"));
        System.out.println(
                "Aspersor: " + ((this.aspersor) ? ColoresANSI.setColor(ColoresANSI.CYAN, "Adquirido") : "No comprado"));
        System.out.println("Monedas: " + monedas.toString());
    }

    /**
     * Método que muestra el estado de todas las plantas/árboles de un
     * invernadero/arboleda seleccionado.
     */
    public void showSpecificStatus() {
        int contInv = 0;
        int contArb = 0;
        int select;
        System.out.println("\n====== Estado plantas/árboles ======");
        System.out.println("0.- Cancelar");
        for (Invernadero inv : this.invernaderos) {
            if (inv != null) {
                System.out.println((++contInv) + ".- [I] " + inv.getName());
            }
        }
        if (this.invArbExisten(2) > 0) {
            contArb = contInv;
            for (Arboleda arb : this.arboledas) {
                if (arb != null) {
                    System.out.println((++contArb) + ".- [A] " + arb.getName());
                }
            }
        } else {
            System.out.println(ColoresANSI.setColor(ColoresANSI.ITALIC, "No hay arboledas disponibles"));
        }

        if (contArb == 0) {
            select = this.select(contInv);
        } else {
            select = this.select(contArb);
        }

        if (select != 0) {
            if (select <= contInv) {
                this.invernaderos[select - 1].showTileStatus();
            } else {
                this.arboledas[(select - 1) - contInv].showTileStatus();
            }
        }
    }

    /**
     * Método que muestra un desglose de las plantas y árboles del sistema
     * con información general de los productos cosechados y la harina generada.
     */
    public void showStats() {
        System.out.println("\n----- Informes -----");
        stats.mostrar();
    }

    /**
     * Método que permite consultar la información de cada planta y árbol.
     * Primero, muestra un menú para consultar árboles o plantas. Luego, una lista
     * con las plantas o árboles disponibles. Por último, muestra la información de
     * la planta o árbol.
     */
    public void showNatura() {
        int contador = 0;

        System.out.println("\n --------- NaturPedia ---------");
        System.out.println("0.- Cancelar");
        System.out.println("1.- Consultar plantas");
        System.out.println("2.- Consultar árboles");
        int select = this.select(2);

        switch (select) {
            case 1:
                System.out.println("\n --------- Plantas disponibles ---------");
                System.out.println("0.- Cancelar");
                for (Plantae plant : this.plantas) {
                    if (!(plant instanceof Arbol)) {
                        System.out.println((++contador) + ".- " + plant.getName() + ".");
                    }
                }
                select = this.select(contador);
                if (select != 0) {
                    this.plantas[select - 1].showInfoNatura();
                }
                break;
            case 2:
                System.out.println("\n --------- Árboles disponibles ---------");
                System.out.println("0.- Cancelar");
                for (Plantae plant : this.plantas) {
                    if (plant instanceof Arbol) {
                        System.out.println((++contador) + ".- " + plant.getName() + ".");
                    }
                }
                select = this.select(contador);
                if (select != 0) {
                    this.plantas[(this.contadorPlantas()) + (select - 1)].showInfoNatura();
                }
                break;
            default:
                break;
        }
    }

    /**
     * Método privado que permite seleccionar un tipo de edifio del simulador.
     * 
     * @return Devuelve la opción seleccionada.
     */
    private int selectInvArb() {
        System.out.println("0.- Cancelar");
        System.out.println("1.- Seleccionar un invernadero");
        if (this.invArbExisten(2) > 0) {
            System.out.println("2.- Seleccionar una arboleda");
            return this.select(2);
        } else {
            return this.select(1);
        }
    }

    /**
     * Método que por defecto asigna el rango mínimo en 0.
     * 
     * @param rangoMax el rango máximo a evaluar.
     * @return retorna la selección correcta del usuario.
     */
    private int select(int rangoMax) {
        return this.select(0, rangoMax);
    }

    /**
     * Método privado que facilita la obtención de un número válido introducido por
     * el usuario. Por defecto se comprueba que sea un valor positivo.
     * 
     * @param rangoMin número mínimo que puede tener el valor introducido por
     *                 teclado.
     * @param rangoMax número máximo que puede tener el valor introducido por
     *                 teclado.
     * @return devuelve el número introducido.
     */
    private int select(int rangoMin, int rangoMax) {
        boolean flag = false;
        int select = -1;
        do {
            try {
                System.out.print("\n· Seleccione una opción: ");
                select = sc.nextInt();
                if (select >= rangoMin && select <= rangoMax) {
                    if (select == 0) {
                        System.out.println(
                                "\n" + ColoresANSI.setColor(ColoresANSI.ITALIC, "Se ha cancelado la operación") + ".");
                    }
                    flag = !flag;
                } else {
                    System.out.println("Debe elegir una opción válida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Debe elegir una opción válida.");
                sc.next();
            }
        } while (!flag);
        return select;
    }

    /**
     * Aumenta en un día a todas las plantas vivas de los invernaderos
     * al igual que el día del Simulador.
     */
    public void nextDay() {
        if (this.aspersor) {
            this.waterInv();
            this.waterArb();
        }
        for (Invernadero invernadero : this.invernaderos) {
            if (invernadero != null) {
                invernadero.growCrops();
            }
        }
        for (Arboleda arboleda : this.arboledas) {
            if (arboleda != null) {
                arboleda.growCrops();
            }
        }
        
        this.numDias++;
        System.out.println("\n" + ColoresANSI.setColor(ColoresANSI.ITALIC, "¡Se ha aumentado un día!"));
    }

    /**
     * Este método añade cierta cantidad de agua al tanque según sea
     * elegido por el usuario; luego muestra el estado del tanque de agua.
     */
    public void addWater() {
        int select = -1;
        int agua = -1;
        System.out.println("\n" + Simulador.monedas.toString());
        System.out.println("------------- Recolectar agua -------------");
        System.out.println("0.- Cancelar");
        System.out.println("1.- Añadir 1 unidad de agua");
        System.out.println("2.- Añadir 5 unidades de agua");
        System.out.println("3.- Añadir 10 unidades de agua");
        System.out.println("4.- Llenar el tanque de agua");

        select = this.select(4);
        if (select != 0) {
            switch (select) {
                case 1:
                    if (Simulador.monedas.comprobarMonedas(1) && this.tanqueAgua.addWater(1)) {
                        Simulador.monedas.restMonedas(agua = 1);
                    }
                    break;
                case 2:
                    if (Simulador.monedas.comprobarMonedas(5) && this.tanqueAgua.addWater(5)) {
                        Simulador.monedas.restMonedas(agua = 5);
                    }
                    break;
                case 3:
                    if (Simulador.monedas.comprobarMonedas(10) && this.tanqueAgua.addWater(10)) {
                        Simulador.monedas.restMonedas(agua = 10);
                    }
                    break;
                case 4:
                    int dif = this.tanqueAgua.getMax() - this.tanqueAgua.getWater();
                    int descuento = dif / 10;
                    if (Simulador.monedas.comprobarMonedas(dif - descuento)) {
                        Simulador.monedas.restMonedas(dif - descuento);
                        this.tanqueAgua.addWater(dif);
                        agua = dif;
                    }
                    break;
            }
            if (agua != -1) {
                System.out.println("\nAñadidos " + agua + " de agua");
            }
            this.tanqueAgua.showStatus();
        }
    }

    /**
     * Método riega las plantas o árboles de un huerto según los recursos del
     * tanque.
     * 
     * Muestra por pantalla las plantas o árboles que han sido regadas.
     */
    public void waterCrops() {
        int aguaDisponible = this.tanqueAgua.getWater();
        if (aguaDisponible > 0) {
            int select;
            System.out.println("\n------------- Regar natura -------------");
            System.out.println("0.- Cancelar");
            System.out.println("1.- Regar invernaderos");
            if (this.invArbExisten(2) > 0) {
                System.out.println("2.- Regar arboledas");
                select = select(2);
            } else {
                select = this.select(1);
            }

            switch (select) {
                case 1:
                    this.waterInv();
                    break;
                case 2:
                    this.waterArb();
                    break;
                default:
                    break;
            }
        } else {
            System.out.println("\nNo es posible regar ninguna planta/árbol, el tanque de agua está vacío");
        }
    }

    /**
     * Método para regar plantas de los invernaderos.
     */
    private void waterInv() {
        if (this.naturaTotal(1) > 0) {
            int plantasRegadas = 0;
            for (Invernadero inv : this.invernaderos) {
                if (inv != null && tanqueAgua.getWater() > 0) {
                    plantasRegadas += inv.waterCrops(this.tanqueAgua);
                }
            }
            System.out.println("\nSe han regado [" + plantasRegadas + "/" + this.naturaViva(1)
                    + "] plantas de los invernaderos");
        } else {
            System.out.println("\n" + ColoresANSI.setColor(ColoresANSI.ITALIC, "No hay ninguna planta para regar."));
        }
    }

    /**
     * Método que riega los árboles de las arboledas.
     */
    private void waterArb() {
        if (this.naturaTotal(2) > 0) {
            int arbolesRegados = 0;
            for (Arboleda arb : this.arboledas) {
                if (arb != null && tanqueAgua.getWater() > 0) {
                    arbolesRegados += arb.waterCrops(this.tanqueAgua);
                }
            }
            System.out.println("\nSe han regado [" + arbolesRegados + "/" + this.naturaViva(2)
                    + "] árboles de las arboledas");
        } else {
            System.out.println("\n" + ColoresANSI.setColor(ColoresANSI.ITALIC, "No hay ningún árbol para regar."));
        }
    }

    /**
     * Método que permite seleccionar si agregar una planta o un árbol a uno de los
     * edificios de plantación.
     * La opción de árboles sólo es mostrada si existe, al menos, una arboleda en el
     * sistema.
     * Agrega una planta o árbol, listando los disponibles, a un
     * invernadero/arboleda específico sólo si hay
     * espacio en dicho invernadero/arboleda. Luego es mostrado un mensaje con el
     * estado del
     * invernadero seleccionado.
     */
    public void plant() {
        int select = -1;
        int selectHuerto = -1;
        int contador = 0;
        /** Vefifica si hay al menos una arboleda disponible. **/
        boolean isArb = this.invArbExisten(2) != 0;
        System.out.println("\n" + Simulador.monedas.toString());
        System.out.println("========= Nueva plantación =========");
        System.out.println("0.- Cancelar");
        System.out.println("1.- Seleccionar una planta");
        if (isArb) {
            System.out.println("2.- Seleccionar un árbol");
        }

        if (isArb) {
            select = this.select(2);
        } else {
            select = this.select(1);
        }

        if (select != 0) {
            switch (select) {
                case 1:
                    System.out.println("\n------------- Nueva planta -------------");
                    System.out.println("0.- Cancelar");
                    for (Plantae plant : this.plantas) {
                        if (!(plant instanceof Arbol)) {
                            System.out.println(
                                    (++contador) + ".- " + plant.getName() + ColoresANSI.setColor(ColoresANSI.PURPLE,
                                            (" [" + plant.getPrice() + " monedas]")));
                        }
                    }
                    select = this.select(contador);
                    if (select != 0) {
                        if (Simulador.monedas.comprobarMonedas(this.plantas[select - 1].getPrice())) {
                            selectHuerto = this.selectInv();
                            if (selectHuerto != 0) {
                                // Objeto provisional.
                                final Invernadero inv = this.invernaderos[selectHuerto - 1];

                                // Se comprueba que existen espacios libres en este invernadero para agregar una
                                // planta nueva.
                                if ((inv.getTiles() - inv.getNum()) > 0) {
                                    Simulador.monedas.restMonedas(this.plantas[select - 1].getPrice());
                                    String plant = this.plantas[select - 1].getName();

                                    this.plantObjet(selectHuerto, plant);

                                    System.out.println(
                                            "\nSe ha agregado una planta [" + this.plantas[select - 1].getName()
                                                    + "] a un invernadero [" + inv.getName() + "].");
                                    inv.showCapacity();
                                } else {
                                    System.out.println(
                                            "\nNo hay espacio para añadir una nueva planta a " + inv.getName());
                                }
                            }
                        } else {
                            System.out.println("No hay monedas suficientes para comprar la planta "
                                    + this.plantas[select - 1].getName() + ".");
                        }
                    }
                    break;
                case 2:
                    System.out.println("\n------------- Nuevo árbol -------------");
                    System.out.println("0.- Cancelar");
                    for (Plantae plant : this.plantas) {
                        if (plant instanceof Arbol) {
                            System.out.println(
                                    (++contador) + ".- " + plant.getName() + ColoresANSI.setColor(ColoresANSI.PURPLE,
                                            (" [" + plant.getPrice() + " monedas]")));
                        }
                    }
                    select = this.select(contador);
                    if (select != 0) {
                        if (Simulador.monedas
                                .comprobarMonedas(this.plantas[this.contadorPlantas() + (select - 1)].getPrice())) {
                            selectHuerto = this.selectArb();
                            if (selectHuerto != 0) {
                                // Objeto provisional.
                                final Arboleda arb = this.arboledas[selectHuerto - 1];

                                // Se comprueba que existen espacios libres en esta arboleda para agregar un
                                // árbol nuevo.
                                if ((arb.getTiles() - arb.getNum()) > 0) {
                                    Simulador.monedas.restMonedas(
                                            this.plantas[this.contadorPlantas() + (select - 1)].getPrice());
                                    String arbol = this.plantas[this.contadorPlantas() + (select - 1)].getName();

                                    this.plantObjet(selectHuerto, arbol);

                                    System.out.println("\nSe ha agregado un árbol ["
                                            + this.plantas[this.contadorPlantas() + (select - 1)].getName()
                                            + "] a una arboleda [" + arb.getName() + "].");
                                    this.arboledas[selectHuerto - 1].showCapacity();
                                } else {
                                    System.out
                                            .println("\nNo hay espacio para añadir un nuevo árbol a " + arb.getName());
                                }
                            }
                        } else {
                            System.out.println("No hay monedas suficientes para comprar el árbol "
                                    + this.plantas[this.contadorPlantas() + (select - 1)].getName() + ".");
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Método que agrega una planta u árbol según el String pasado por parámetros.
     * 
     * @param selectHuerto la posición del huerto.
     * @param natura       el String con el nombre de la planta o árbol
     */
    private void plantObjet(int selectHuerto, String natura) {
        switch (natura) {
            case "Avena":
                this.invernaderos[selectHuerto - 1].addPlanta(new Avena());
                break;
            case "Garbanzos":
                this.invernaderos[selectHuerto - 1].addPlanta(new Garbanzos());
                break;
            case "Lechuga":
                this.invernaderos[selectHuerto - 1].addPlanta(new Lechuga());
                break;
            case "Pimiento":
                this.invernaderos[selectHuerto - 1].addPlanta(new Pimiento());
                break;
            case "Remolacha az.":
                this.invernaderos[selectHuerto - 1].addPlanta(new RemolachaAz());
                break;
            case "Trigo":
                this.invernaderos[selectHuerto - 1].addPlanta(new Trigo());
                break;
            case "Kiwi":
                this.arboledas[selectHuerto - 1].addPlanta(new Kiwi());
                break;
            case "Madroño":
                this.arboledas[selectHuerto - 1].addPlanta(new Madronho());
                break;
            case "Manzano":
                this.arboledas[selectHuerto - 1].addPlanta(new Manzano());
                break;
            case "Melocotonero":
                this.arboledas[selectHuerto - 1].addPlanta(new Melocotonero());
                break;
            case "Vid":
                this.arboledas[selectHuerto - 1].addPlanta(new Vid());
                break;
        }
    }

    /**
     * Método para contar las plantas (no árboles) que dispone el Simulador.
     * 
     * @return el número de plantas que hay.
     */
    private int contadorPlantas() {
        int contador = 0;
        for (Plantae plant : this.plantas) {
            if (!(plant instanceof Arbol)) {
                contador++;
            }
        }
        return contador;
    }

    /**
     * Método que permite recolectar todas las plantas de los invernaderos o árboles
     * de las arboledas que
     * estén maduros y obtiene los productos de cada uno, luego es mostrado
     * un mensaje de las plantas/árboles afectados y los productos obtenidos.
     */
    public void harvest() {
        int select;
        System.out.println("\n-------- Recolectar productos --------");
        System.out.println("0.- Cancelar");
        System.out.println("1.- Cosechar plantas");
        if (this.invArbExisten(2) > 0) {
            System.out.println("2.- Cosechar árboles");
            select = this.select(2);
        } else {
            select = this.select(1);
        }

        if (select != 0) {
            final int monedasActuales = Simulador.monedas.getMonedas();
            int productosRecolectados = 0;
            int naturaRecolectada = 0;
            int naturaMuerta = 0;
            int monedasNaturaTriturables = 0;
            int[] harvest;

            switch (select) {
                case 1:
                    if (naturaMadura(1) > 0) {
                        for (Invernadero invernadero : invernaderos) {
                            if (invernadero != null) {
                                harvest = invernadero.harvest();
                                productosRecolectados += harvest[0];
                                naturaRecolectada += harvest[1];
                                monedasNaturaTriturables += harvest[2];
                                naturaMuerta += harvest[3];
                            }
                        }
                        if (this.molino && monedasNaturaTriturables > 0) {
                            Simulador.stats.registrarHarina((int) (monedasNaturaTriturables * 1.20));
                            Simulador.monedas.sumarMonedas((int) (monedasNaturaTriturables * 1.20));
                        }
                        System.out.println("\n------------- Cosecha de productos -------------");
                        System.out.println(
                                naturaRecolectada + " plantas han producido " + productosRecolectados + " productos.");
                        System.out.println("Han muerto " + naturaMuerta + " plantas en el proceso.");
                        System.out
                                .println("Se han ganado "
                                        + ColoresANSI.setColor(ColoresANSI.GREEN,
                                                ((Simulador.monedas.getMonedas() - monedasActuales + " monedas")))
                                        + ".");
                    } else {
                        System.out.println("\n" + ColoresANSI.setColor(ColoresANSI.ITALIC, "No hay plantas maduras para recolectar."));
                    }
                    break;
                case 2:
                    if (naturaMadura(2) > 0) {
                        /* int monedasArbTriturables = 0; */
                        for (Arboleda arb : this.arboledas) {
                            if (arb != null) {
                                harvest = arb.harvest();
                                productosRecolectados += harvest[0];
                                naturaRecolectada += harvest[1];
                                /* monedasArbTriturables += productos_plantas[2]; */
                                naturaMuerta += harvest[3];

                            }
                        }
                        /**
                         * if (this.molino && monedasArbTriturables > 0) {
                         * Simulador.stats.registrarHarina(monedasArbTriturables);
                         * Simulador.monedas.restMonedas(monedasArbTriturables);
                         * Simulador.monedas.sumarMonedas((int) (monedasArbTriturables * 1.20));
                         * }
                         */
                        System.out.println("\n------------- Cosecha de productos -------------");
                        System.out.println(
                                naturaRecolectada + " árboles han producido " + productosRecolectados + " productos.");
                        System.out.println("Han muerto " + naturaMuerta + " árboles en el proceso.");
                        System.out.println(
                                "Se han ganado "
                                        + ColoresANSI.setColor(ColoresANSI.GREEN,
                                                ((Simulador.monedas.getMonedas() - monedasActuales + " monedas")))
                                        + ".");
                    } else {
                        System.out.println("\n" + ColoresANSI.setColor(ColoresANSI.ITALIC, "No hay árboles maduros para recolectar."));
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Método que elimina las plantas/árboles muertas de un invernadero/arboleda en
     * concreto.
     */
    public void plow() {
        int select;
        int naturaEliminada = 0;
        System.out.println("\n-------- Eliminar plantas/árboles muertos --------");
        System.out.println("0.- Cancelar");
        System.out.println("1.- Desbrozar plantas");
        if (this.invArbExisten(2) > 0) {
            System.out.println("2.- Desbrozar árboles");
            select = this.select(2);
        } else {
            select = this.select(1);
        }

        System.out.println("\n------------- Eliminación de plantas muertas -------------");
        switch (select) {
            case 1:
                if (this.naturaViva(1) < this.naturaTotal(1)) {
                    for (Invernadero inv : this.invernaderos) {
                        if (inv != null) {
                            naturaEliminada += inv.plow();
                        }
                    }
                    System.out.println("Se han eliminado un total de " + naturaEliminada
                            + " plantas muertas de los invernaderos.");
                } else {
                    System.out.println("\n" + ColoresANSI.setColor(ColoresANSI.ITALIC, "No hay ninguna planta muerta para eliminar."));
                }
                break;
            case 2:
                if (this.naturaViva(2) < this.naturaTotal(2)) {
                    for (Arboleda arb : this.arboledas) {
                        if (arb != null) {
                            naturaEliminada += arb.plow();
                        }
                    }
                    System.out.println(
                            "Se han eliminado un total de " + naturaEliminada + " árboles muertos de las arboledas.");
                } else {
                    System.out.println("\n" + ColoresANSI.setColor(ColoresANSI.ITALIC, "No hay ningún árbol muerto para eliminar."));
                }
                break;
            default:
                break;
        }
    }

    /**
     * Método que elimina las plantas/árboles de un edificio en concreto.
     * En el caso de los árboles, tiene un coste de la mitad de su precio.
     */
    public void unroot() {
        int selectInvArb;
        int select;
        System.out.println("\n-------- Arrancar plantas/árboles --------");
        System.out.println("0.- Cancelar");
        System.out.println("1.- Arrancar plantas");
        if (this.invArbExisten(2) > 0) {
            System.out.println("2.- Arrancar árboles");
            selectInvArb = this.select(2);
        } else {
            selectInvArb = this.select(1);
        }

        switch (selectInvArb) {
            case 1:
                select = selectInv();
                if (select > 0) {
                    this.invernaderos[selectInvArb - 1].unroot();
                    System.out.println("\n------------- Eliminación de plantas -------------");
                    System.out.println(
                            "\nSe han eliminado todas las plantas de " + invernaderos[selectInvArb - 1].getName());
                }
                break;
            case 2:
                System.out.println("\n" + Simulador.monedas.toString());
                select = selectArb();
                if (select > 0) {
                    this.arboledas[selectInvArb - 1].unroot();
                    System.out.println("\n------------- Eliminación de árboles -------------");
                    System.out.println(
                            "\nSe han eliminado los árboles posibles de " + this.arboledas[selectInvArb - 1].getName());
                }
                break;
            default:
                break;
        }
    }

    /**
     * Este método permite ampliar las condiciones del Simulador. Algunas
     * opciones no estarán disponibles si antes no se ha adquirido el objeto
     * indispensable para su mejora. Por ejemplo, no se podrá mejorar una arboleda
     * sin antes haber adquirido una previamente.
     */
    public void upgrade() {
        int seleccion;
        int select;
        int contadorHuerto;
        int costoInv;
        int costoArb;
        int contador = 0;

        System.out.println("\n" + Simulador.monedas.toString());
        System.out.println("------------- Mejoras -------------");
        System.out.println("1.- Comprar edificio");
        System.out.println("2.- Mejorar edificio");
        System.out.println("3.- Mejorar el tanque de agua");
        System.out.println("4.- Cancelar");

        seleccion = this.select(1, 4);

        switch (seleccion) {
            // Comprar edificio.
            case 1:
                System.out.println("\n--- Comprar edificio ---");
                System.out.println("0.- Cancelar");

                contadorHuerto = this.invArbExisten(1);
                costoInv = ((contadorHuerto == 0) ? 1 : contadorHuerto) * 500;
                System.out.println(
                        "1.- Invernadero " + ColoresANSI.setColor(ColoresANSI.PURPLE, ("[" + costoInv + " monedas]")));

                contadorHuerto = this.invArbExisten(2);
                costoArb = ((contadorHuerto == 0) ? 1 : contadorHuerto) * 500;
                System.out.println(
                        "2.- Arboleda " + ColoresANSI.setColor(ColoresANSI.PURPLE, ("[" + costoArb + " monedas]")));

                // Se comprueb que no se tenga el molino para mostrar la opción de comprarlo
                if (!this.molino) {
                    System.out.println("3.- Molino " + ColoresANSI.setColor(ColoresANSI.PURPLE, ("[3000 monedas]")));
                    select = select(3);
                } else {
                    select = select(2);
                }

                switch (select) {
                    // Comprar invernaderos.
                    case 1:
                        if (Simulador.monedas.comprobarMonedas(costoInv)) {
                            System.out.print("\nIntroduce el nombre del nuevo invernadero: ");
                            sc.nextLine();
                            Invernadero nuevoInv = new Invernadero(sc.nextLine());

                            if (this.invArbNulos(1) == 0) {
                                this.upgradeInvArb(1);
                            }

                            for (int i = 0; i < this.invernaderos.length; i++) {
                                if (this.invernaderos[i] == null) {
                                    this.invernaderos[i] = nuevoInv;
                                    break;
                                }
                            }
                            System.out.println("\n" + nuevoInv.getName() + " ha sido añadido correctamente.");
                            Simulador.monedas.restMonedas(costoInv);
                        } else {
                            System.out.println("\nNo hay monedas suficientes para comprar un invernadero. Faltan "
                                    + ColoresANSI.setColor(ColoresANSI.PURPLE,
                                            ((costoInv - Simulador.monedas.getMonedas()) + " monedas"))
                                    + ".");
                        }
                        break;
                    // Comprar arboleda.
                    case 2:
                        if (Simulador.monedas.comprobarMonedas(costoArb)) {

                            System.out.print("\nIntroduce el nombre de la nueva arboleda: ");
                            sc.nextLine();
                            Arboleda nuevaArb = new Arboleda(sc.nextLine());

                            if (this.invArbNulos(2) == 0) {
                                this.upgradeInvArb(2);
                            }

                            for (int i = 0; i < this.arboledas.length; i++) {
                                if (this.arboledas[i] == null) {
                                    this.arboledas[i] = nuevaArb;
                                    break;
                                }
                            }
                            System.out.println("\n" + nuevaArb.getName() + " ha sido añadido correctamente.");
                            Simulador.monedas.restMonedas(costoArb);
                        } else {
                            System.out.println("\nNo hay monedas suficientes para comprar una arboleda. Faltan "
                                    + ColoresANSI.setColor(ColoresANSI.PURPLE,
                                            ((costoInv - Simulador.monedas.getMonedas()) + " monedas"))
                                    + ".");
                        }
                        break;
                    // Comprar molino.
                    case 3:
                        // se comprueba que hay monedas suficientes para comprar el molino.
                        if (Simulador.monedas.comprobarMonedas(3000)) {
                            this.molino = true;
                            System.out.println("\n¡Se ha comprado el molino!");
                            Simulador.monedas.restMonedas(3000);
                        } else {
                            System.out.println("\nNo hay monedas suficientes para comprar el molino. Faltan "
                                    + ColoresANSI.setColor(ColoresANSI.PURPLE,
                                            ((3000 - Simulador.monedas.getMonedas()) + " monedas"))
                                    + ".");
                        }
                        break;
                    default:
                        break;
                }
                break;
            // Mejorar edificio.
            case 2:
                System.out.println("\n--- Mejorar edificio ---");
                System.out.println("0.- Cancelar");
                System.out.println("1.- Invernadero");
                if (this.invArbExisten(2) > 0) {
                    System.out.println("2.- Arboleda");
                    select = this.select(2);
                } else {
                    select = this.select(1);
                }

                switch (select) {
                    // Mejorar invernadero.
                    case 1:
                        System.out.println("\n--- Mejorar invernadero ---");
                        System.out.println("0.- Cancelar");
                        System.out.println("1.- Aumentar tamaño");
                        select = this.select(1);

                        if (select != 0) {
                            System.out.println("\n------------ Invernaderos ------------");
                            System.out.println("0.- Cancelar");
                            for (Invernadero inv : this.invernaderos) {
                                if (inv != null) {
                                    System.out.println(
                                            (++contador) + ".- " + inv.getName() + " [" + inv.getLevel() + "/10]"
                                                    + ColoresANSI.setColor(ColoresANSI.PURPLE, (" ["
                                                            + ((inv.getLevel() != 10
                                                                    ? ((inv.getLevel() * 150) + " monedas]")
                                                                    : "Nv. máx.]")))));
                                }
                            }
                            seleccion = this.select(contador);
                            if (seleccion != 0) {
                                if (this.invernaderos[seleccion - 1].getLevel() < 10) {
                                    costoInv = this.invernaderos[seleccion - 1].getLevel() * 150;
                                    if (Simulador.monedas.comprobarMonedas(costoInv)) {
                                        this.invernaderos[seleccion - 1].upgrade();
                                        Simulador.monedas.restMonedas(costoInv);
                                    } else {
                                        System.out.println("\nNo hay monedas suficientes para mejorar el invernadero "
                                                + this.invernaderos[seleccion - 1].getName() + ". Faltan "
                                                + ColoresANSI.setColor(ColoresANSI.PURPLE,
                                                        ((costoInv - Simulador.monedas.getMonedas()) + " monedas"))
                                                + ".");
                                    }
                                } else {
                                    System.out.println("\nEste invernadero ha llegado a su nivel máximo.");
                                }
                            }
                        }
                        break;
                    // Mejorar arboleda.
                    case 2:
                        System.out.println("\n--- Mejorar arboleda ---");
                        System.out.println("0.- Cancelar");
                        System.out.println("1.- Aumentar tamaño "
                                + ColoresANSI.setColor(ColoresANSI.PURPLE, ("[150 monedas/Nv.]")));
                        System.out.println("2.- Añadir sistema de riego por goteo "
                                + ColoresANSI.setColor(ColoresANSI.PURPLE, ("[500 monedas]")));
                        select = this.select(2);

                        switch (select) {
                            // Aumentar tamaño de una arboleda.
                            case 1:
                                System.out.println("\n------------ Arboledas ------------");
                                System.out.println("0.- Cancelar");
                                for (Arboleda arb : this.arboledas) {
                                    if (arb != null) {
                                        System.out.println(
                                                (++contador) + ".- " + arb.getName() + " [" + arb.getLevel() + "/10]"
                                                        + ColoresANSI.setColor(ColoresANSI.PURPLE, (" ["
                                                                + ((arb.getLevel() != 10
                                                                        ? ((arb.getLevel() * 150) + " monedas]")
                                                                        : "Nv. máx.]")))));
                                    }
                                }
                                seleccion = select(contador);
                                if (seleccion != 0) {
                                    if (this.arboledas[seleccion - 1].getLevel() < 10) {
                                        costoArb = this.arboledas[seleccion - 1].getLevel() * 150;
                                        if (Simulador.monedas.comprobarMonedas(costoArb)) {
                                            this.arboledas[seleccion - 1].upgrade();
                                            Simulador.monedas.restMonedas(costoArb);
                                        } else {
                                            System.out.println("\nNo hay monedas suficientes para mejorar la arboleda "
                                                    + this.arboledas[seleccion - 1].getName() + ". Faltan "
                                                    + ColoresANSI.setColor(ColoresANSI.PURPLE,
                                                            ((costoArb - Simulador.monedas.getMonedas()) + " monedas"))
                                                    + ".");
                                        }
                                    } else {
                                        System.out.println("\nEsta arboleda ha llegado a su nivel máximo.");
                                    }
                                }
                                break;
                            // Comprar el sistema de riego por goteo.
                            case 2:
                                seleccion = this.selectArb();
                                if (seleccion != 0) {
                                    costoArb = 500;
                                    if (!this.arboledas[seleccion - 1].isRiegoPorGoteo()) {
                                        if (Simulador.monedas.comprobarMonedas(costoArb)) {
                                            this.arboledas[seleccion - 1].setRiegoPorGoteo();
                                            Simulador.monedas.restMonedas(costoArb);
                                        } else {
                                            System.out.println("\nNo hay monedas suficientes para comprar esta mejora. "
                                                    + ColoresANSI.setColor(ColoresANSI.PURPLE,
                                                            ((costoArb - Simulador.monedas.getMonedas())
                                                                    + " monedas"))
                                                    + " faltantes.");
                                        }
                                    } else {
                                        System.out.println("\nYa se ha agregado esta mejora en esta arboleda.");
                                    }
                                }
                                break;
                            default:
                                break;
                        }
                        break;
                    default:
                        break;
                }
                break;
            case 3:
                System.out.println("\n--- Mejorar el tanque de agua ---");
                System.out.println("0.- Cancelar");
                System.out.println("1.- Aumentar tamaño " + ColoresANSI.setColor(ColoresANSI.PURPLE, "[100 monedas]"));
                if (!this.aspersor) {
                    System.out.println(
                            "2.- Añadir aspersores " + ColoresANSI.setColor(ColoresANSI.PURPLE, "[1000 monedas]"));
                    select = this.select(2);
                } else {
                    select = this.select(1);
                }

                int costo;

                switch (select) {
                    case 1:
                        costo = 100;
                        if (Simulador.monedas.comprobarMonedas(costo)) {
                            this.tanqueAgua.upgrade();
                            System.out.println("\n¡Tanque de agua mejorado!");
                            Simulador.monedas.restMonedas(costo);
                        } else {
                            System.out.println(
                                    "\n" + ColoresANSI.setColor(ColoresANSI.ITALIC, "No hay monedas suficientes para aumentar el tamaño del tanque de agua."));
                        }
                        break;
                    case 2:
                        costo = 1000;
                        if (Simulador.monedas.comprobarMonedas(costo)) {
                            this.aspersor = true;
                            System.out.println("\n¡Aspersor comprado!");
                            Simulador.monedas.restMonedas(costo);
                        } else {
                            System.out.println("\n" + ColoresANSI.setColor(ColoresANSI.ITALIC, "No hay monedas suficientes para comprar un aspersor."));
                        }
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }

    /**
     * Método que permite aumentar días en el Simulador entre 1 y 5 días.
     */
    public void forwardDays() {
        int select;
        System.out.println("\nIntroduzca cuántos días avanzar en el Simulador [Entre 1 y 5 días; 0 para cancelar]");
        select = this.select(5);
        if (select != 0) {
            for (int i = 1; i <= select; i++) {
                this.nextDay();
            }
            System.out.println("\n"
                    + ColoresANSI.setColor(ColoresANSI.ITALIC, "Han pasado un total de " + select + " días") + ".");
            this.showGeneralStatus();
        }
    }

    /**
     * Método que devuelve el número de espacios vacíos en los Arrays
     * de invernaderos o arboledas.
     * 
     * @param select 1 para devolver número de espacios en los invernaderos,
     *               2 para las arboledas.
     * @return el número de espacios vacíos (nulos) en el edificio de plantación.
     */
    private int invArbNulos(int select) {
        int contador = 0;
        switch (select) {
            case 1:
                for (Invernadero inv : this.invernaderos) {
                    if (inv == null) {
                        contador++;
                    }
                }
                break;
            case 2:
                for (Arboleda arb : this.arboledas) {
                    if (arb == null) {
                        contador++;
                    }
                }
                break;
            default:
                break;
        }
        return contador;
    }

    /**
     * Método que devuelve el número de invernaderos (select 1) o
     * arboledas (select 2) que hay en el Simulador.
     * 
     * @param select 1 para los invernaderos, 2 para las arboledas.
     * @return el número de edificios
     */
    private int invArbExisten(int select) {
        int contador = 0;
        switch (select) {
            case 1:
                for (Invernadero inv : this.invernaderos) {
                    if (inv != null) {
                        contador++;
                    }
                }
                break;
            case 2:
                for (Arboleda arb : this.arboledas) {
                    if (arb != null) {
                        contador++;
                    }
                }
                break;
            default:
                break;
        }
        return contador;
    }

    /**
     * Método privado que aumenta el tamaño del Array de invernaderos
     * o arboledas en 5 posiciones más.
     * 
     * @param select 1 para el Array de invernaderos, 2 para el de arboledas.
     */
    private void upgradeInvArb(int select) {
        switch (select) {
            case 1:
                Invernadero[] invs = Arrays.copyOf(this.invernaderos, this.invernaderos.length + 5);
                this.invernaderos = invs;
                break;
            case 2:
                Arboleda[] arbs = Arrays.copyOf(this.arboledas, this.arboledas.length + 5);
                this.arboledas = arbs;
                break;
            default:
                break;
        }
    }

    /**
     * Método que devuelve el número de plantas vivas o el número
     * de árboles vivos de todos los invernaderos/árboledas.
     * 
     * @param select 1 para plantas, 2 para árboles
     * @return devuelve la natura viva.
     */
    private int naturaViva(int select) {
        int naturaViva = 0;
        switch (select) {
            case 1:
                for (Invernadero invernadero : this.invernaderos) {
                    if (invernadero != null) {
                        naturaViva += invernadero.getAlive();
                    }
                }
                break;
            case 2:
                for (Arboleda arb : this.arboledas) {
                    if (arb != null) {
                        naturaViva += arb.getAlive();
                    }
                }
                break;
            default:
                break;
        }
        return naturaViva;
    }

    /**
     * Método que obtiene el número de plantas o árboles totales de
     * los invernaderos o arboledas.
     * 
     * @param select 1 para plantas, 2 para árboles
     * @return
     */
    private int naturaTotal(int select) {
        int naturaTotal = 0;
        switch (select) {
            case 1:
                for (Invernadero invernadero : invernaderos) {
                    if (invernadero != null) {
                        naturaTotal += invernadero.getNum();
                    }
                }
                break;
            case 2:
                for (Arboleda arb : this.arboledas) {
                    if (arb != null) {
                        naturaTotal += arb.getNum();
                    }
                }
                break;
            default:
                break;
        }
        return naturaTotal;
    }

    /**
     * Método que devuelve el número de plantas o árboles de los
     * invernaderos o arboledas que están maduros.
     * 
     * @param select 1 para las plantas, 2 para los árboles.
     * @return el número de ellos que están maduros.
     */
    private int naturaMadura(int select) {
        int naturaMadura = 0;
        switch (select) {
            case 1:
                for (Invernadero inv : this.invernaderos) {
                    if (inv != null) {
                        naturaMadura += inv.getMature();
                    }
                }
                break;
            case 2:
                for (Arboleda arb : this.arboledas) {
                    if (arb != null) {
                        naturaMadura += arb.getMature();
                    }
                }
                break;
            default:
                break;
        }
        return naturaMadura;
    }

    /**
     * Método que añade 4 plantas o 5 árboles aleatorios, dependiendo si se escoge
     * un invernadero o una arboleda. Ese método no resta monedas.
     */
    public void addNatura() {
        int select;
        int contador;
        int planta;
        select = this.selectInvArb();

        switch (select) {
            case 1:
                select = this.selectInv();
                if (select != 0) {
                    contador = 4;
                    Random rand = new Random();
                    // Objeto provisional.
                    final Invernadero inv = this.invernaderos[select - 1];
                    while (contador >= 1) {
                        planta = rand.nextInt(0, (this.contadorPlantas()));
                        // Se comprueba que existen espacios libres en este invernadero para agregar una
                        // planta nueva.
                        if ((inv.getTiles() - inv.getNum()) > 0) {
                            this.plantObjet(select, this.plantas[planta].getName());
                        }
                        contador--;
                    }
                    System.out.println();
                    this.invernaderos[select - 1].showCapacity();
                }
                break;
            case 2:
                select = this.selectArb();
                if (select != 0) {
                    contador = 5;
                    Random rand = new Random();
                    // Objeto provisional.
                    final Arboleda arb = this.arboledas[select - 1];
                    while (contador >= 1) {
                        planta = rand.nextInt((this.contadorPlantas()), this.plantas.length);
                        // Se comprueba que existen espacios libres en esta arboleda para agregar un
                        // árbol nuevo.
                        if ((arb.getTiles() - arb.getNum()) > 0) {
                            this.plantObjet(select, this.plantas[planta].getName());
                        }
                        contador--;
                    }
                    System.out.println();
                    this.arboledas[select - 1].showCapacity();
                }
                break;
            default:
                break;
        }
    }

    /**
     * Este método simula un menú interactivo de 14 opciones disponibles
     * con gestión de errores en caso de valores no correctos introducidos por
     * teclado.
     * 
     * Mientras no se seleccione la opción indicada para finalizar, el programa
     * seguirá ejecutándose.
     * 
     * @param args Argumentos.
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int select = -1;
        Simulador simulador = new Simulador();

        simulador.init();
        simulador.showGeneralStatus();

        do {
            System.out.println();
            simulador.menu();
            try {
                select = sc.nextInt();
                switch (select) {
                    case 1:
                        simulador.showGeneralStatus();
                        break;
                    case 2:
                        simulador.showSpecificStatus();
                        break;
                    case 3:
                        simulador.showStats();
                        break;
                    case 4:
                        simulador.showNatura();
                        break;
                    case 5:
                        simulador.nextDay();
                        simulador.showGeneralStatus();
                        break;
                    case 6:
                        simulador.addWater();
                        break;
                    case 7:
                        simulador.waterCrops();
                        break;
                    case 8:
                        simulador.plant();
                        break;
                    case 9:
                        simulador.harvest();
                        break;
                    case 10:
                        simulador.plow();
                        break;
                    case 11:
                        simulador.unroot();
                        break;
                    case 12:
                        simulador.upgrade();
                        break;
                    case 13:
                        simulador.forwardDays();
                        break;
                    case 14:
                        System.out.println(ColoresANSI.setColor(ColoresANSI.YELLOW, "\n¡Hasta luego!\n"));
                        sc.close();
                        break;
                    case 98:
                        System.out.println("\nOpción oculta: Añadir natura aleatoria.\n");
                        simulador.addNatura();
                        break;
                    case 99:
                        System.out.println("\nOpción oculta: Se han agregado "
                                + ColoresANSI.setColor(ColoresANSI.GREEN, "1000 monedas") + " al Simulador.");
                        Simulador.monedas.sumarMonedas(1000);
                        break;
                    default:
                        System.out.println("Debe introducir un número en el rango de 1 a 14");
                        break;
                }
            } catch (InputMismatchException e) {
                System.err.println("Debe introducir un número en el rango de 1 a 14");
                sc.next();
            }
        } while (select != 14);
    }
}