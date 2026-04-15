@AdminPanel
Feature: Panel de Administrador


  @AdminConfigCommunityNormalCCSuccess
  Scenario Outline: Configuracion inicial de comunidad Normal
    Given se leen los datos de la BD para acceder como admin: "<SheetBD>" con status: "<StatusCommunity>"
    And un administrador se encuentra loggeado en la vista de panel de CF
    When el usuario accede a lista de tareas pendientes
    And selecciona opcion Crear unidades
    And Se carga el template para unidades: "<FileName>"
    Then Se confirma que la plantilla se cargo correctamente
    And se muestra unidades registradas en modulo residentes

    Examples:
      | SheetBD       | StatusCommunity               |                 FileName                |
      | ComunidadesCL | ReadyForConfiguration | Plantilla Propiedades Comunidad CC.xlsx |