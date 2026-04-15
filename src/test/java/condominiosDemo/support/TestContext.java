package condominiosDemo.support;

import java.util.HashMap;
import java.util.Map;

/**
 * <h2>TestContext</h2>
 * Esta clase actúa como un contenedor de estado compartido para los escenarios de Cucumber.
 * <p>
 * Su propósito principal es permitir la transferencia de datos entre diferentes clases de
 * Step Definitions sin recurrir a variables estáticas, permitiendo la ejecución paralela.
 * </p>
 * * <b>Uso típico:</b>
 * <ul>
 * <li>Un step de búsqueda guarda los datos de la comunidad en {@code dataMap}.</li>
 * <li>Un step de edición recupera esos datos para llenar un formulario.</li>
 * </ul>
 */
public class TestContext {

    /** Diccionario principal que almacena los datos de la fila leída de la base de datos (Google Sheets). */
    private Map<String, String> dataMap = new HashMap<>();

    /** Almacena el nombre de la pestaña actual de Google Sheets para sincronización posterior. */
    private String sheetName;

    /**
     * Obtiene el mapa de datos actual del escenario.
     * @return Map con los pares clave-valor de la prueba.
     */
    public Map<String, String> getDataMap() {
        return dataMap;
    }

    /**
     * Define o actualiza el mapa de datos. Se utiliza usualmente después de leer la BD
     * o generar nuevos datos aleatorios.
     * @param dataMap El diccionario con la información de la comunidad.
     */
    public void setDataMap(Map<String, String> dataMap) {
        this.dataMap = dataMap;
    }

    /**
     * Retorna el nombre de la hoja de cálculo asociada al escenario actual.
     * @return String con el nombre de la hoja (ej: "ComunidadesCL").
     */
    public String getSheetName() {
        return sheetName;
    }

    /**
     * Guarda el nombre de la hoja de cálculo para que otros steps sepan dónde guardar resultados.
     * @param sheetName Nombre de la pestaña en Google Sheets.
     */
    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }
}