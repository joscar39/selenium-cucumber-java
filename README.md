# Automatización de pruebas con Selenium y Java

# Comunidad Feliz - Framework Refactorizado

![Logo de Comunidad Feliz](https://downloads.intercomcdn.com/i/o/230191/660c1d68a27d825c9b7b4b1a/logo-cf.png)

Este proyecto se enfoca en la automatización de casos de prueba web utilizando una potente combinación de herramientas: **Selenium WebDriver** para la interacción con el navegador, el lenguaje de programación **Java (JDK 21)**, el framework **Cucumber** para el Desarrollo Guiado por Comportamiento (BDD) bajo el motor de **JUnit 5**, y la generación de reportes detallados con **Allure Report**.

El objetivo principal es ampliar la cobertura de pruebas y agilizar la ejecución de regresiones complejas, liberando al equipo de QA de tareas repetitivas y permitiendo un análisis profundo de los procesos críticos del negocio.

La arquitectura del proyecto se basa en una combinación estratégica de:

* **Page Object Model (POM):** Un patrón de diseño que organiza los elementos de la interfaz en clases separadas, facilitando el mantenimiento y la reutilización del código.
* **Cucumber y Gherkin (BDD):** Un enfoque metodológico que permite definir las pruebas en lenguaje natural, facilitando la colaboración entre perfiles técnicos y no técnicos.
* **Java (JDK 21):** El lenguaje de programación elegido por su robustez, tipado fuerte y amplia comunidad en el sector de la automatización empresarial.
* **Maven:** Utilizado como gestor de dependencias y para la orquestación del ciclo de vida de las pruebas (Clean, Compile, Test).
* **Selenium WebDriver:** Para la automatización de pruebas en navegadores web (Chrome, Firefox, Edge), abarcando diferentes entornos de ejecución.
* **Integración con Google Cloud:** Uso de servicios como Google Sheets para gestionar datos dinámicos durante las ejecuciones.
* NOTA: La cuenta utilizada para google cloud es qa.automation.cf@gmail.com / Aa123456.

Finalmente, se utiliza **Allure Report** para generar informes visuales de las ejecuciones, incluyendo información sobre pasos, resultados y adjuntos como capturas de pantalla.

---

# Instalación de herramientas y proyecto

- [X] ☕ **Java Development Kit (JDK 21):** Descargar e instalar desde la web oficial. Asegurarse de configurar las variables de entorno `JAVA_HOME` y `PATH`.
- [X] 🏗️ **Maven:** Descargar e instalar Apache Maven. Validar la instalación con el comando: `mvn -version`.
- [X] 🚀 **Allure CLI:** Teniendo Node.js y npm instalados, ejecutar:
  `npm install -g allure-commandline`
- [X] 🤖 **IDE (IntelliJ IDEA):** Se recomienda descargar la versión Community o Ultimate para el desarrollo en Java.

### Descarga del proyecto y dependencias
- [X] ⬇️ **Clonar el repositorio con llave SSH:**
  `git clone ssh del proyecto`
- [X] 🛠️ **Instalar dependencias:** Abrir el proyecto en el IDE y esperar a que Maven descargue las librerías definidas en el `pom.xml`, o ejecutar `mvn install` desde la terminal.

---

# Estructura del proyecto

El framework está diseñado bajo una arquitectura modular y escalable:

1.  **`src/test/java/condominiosDemo/actions`**: Contiene `BaseActions`, con los métodos genéricos de Selenium (clicks, waits, scrolls).
2.  **`src/test/java/condominiosDemo/pages`**: Modelado POM de las páginas de Comunidad Feliz.
3.  **`src/test/java/condominiosDemo/config`**:
    * `WebDriverFactory`: Implementación Singleton para gestionar la sesión del navegador.
    * `Hooks`: Gestión de ciclos de vida de Cucumber (@Before, @After).
4.  **`src/test/java/condominiosDemo/runner`**: Clases @Suite de JUnit 5 para ejecutar los diferentes módulos (CC, SC, Invoices, etc.).
5.  **`src/test/java/condominiosDemo/steps`**: Clases Step Definitions que conectan Gherkin con la lógica de negocio.
6.  **`src/test/java/condominiosDemo/utils`**: Clases de apoyo como el gestor de credenciales y conexión con Google Sheets.
7.  **`src/test/resources/features`**: Archivos .feature con los escenarios de prueba organizados por carpetas de módulos.
8.  **`src/test/resources`**: Archivos de configuración de entorno, propiedades y logs.
---

# 🏃 Ejecución y Construcción de reportes

## Ejecución de pruebas

El framework utiliza variables personalizadas a través de comandos CLI (`-D`) para flexibilidad total.

### ⚙️ Jerarquía de Parámetros:
1.  **Comandos CLI (-D):** Prioridad máxima.
2.  **Archivos .properties:** Valores por defecto definidos en el código.

### Comandos útiles:

**Ejecutar un Runner específico:**
`mvn clean test -Dtest=RunCucumberTestCommunityCreator -Denv=test5 -Dbrowser=CHROME -Dheadless=false`

**Ejecutar todos los Runners (Suite Global):**
`mvn clean test -Dtest=RunCucumberTest* -Denv=test5`

**Ejecutar lista de Runners específicos:**
`mvn clean test -Dtest=RunCucumberTestsCC,RunCucumberTestsSC -Denv=test5`

**Forzar modo Headless (Sin interfaz):**
`mvn clean test -Dtest=RunCucumberTest* -Dheadless=true`

**Ejecutar bajo una etiqueta o decorador:**
`mvn clean test "-Dtest=RunCucumberTestLogin" "-Denv=test5" "-Dbrowser=CHROME" "-Dheadless=false" "-Dcucumber.filter.tags=@LoginResidentRecurrentSC"`

---

## 📊 Generación de reporte Allure

Para visualizar los resultados de las pruebas en este proyecto Java/Maven:

### 1. Visualización Local
Si deseas ver el reporte inmediatamente después de ejecutar tus pruebas:

```powershell
allure serve target/allure-results
   ```


2.  **Generar reporte estático para GitHub Pages:**
    `allure generate allure-results --clean -o docs`

3.  **Despliegue de reporte:**
    ```powershell
    git add -f docs
    git commit -m "Actualizando reporte Allure para GitHub Pages"
    git push origin <tu-rama>
    ```

---

