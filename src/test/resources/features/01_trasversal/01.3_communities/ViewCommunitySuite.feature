@ViewCommunity
Feature: Ver Comunidad


  @SetNewAdminSuccess
  Scenario Outline: Configurar nuevo administrador en una comunidad
    Given un usuario superadmin se encuentra loggeado en CF
    And el usuario superadmin se encuentra en el modulo comunidades
    And se leen y extraen los datos de pruebas desde la BD: "<SheetBD>", con status: "<StatusCommunity>"
    And el usuario superadmin accede al modulo ver comunidades
    When el usuario superadmin activa permisos temporales en la vista
    And llena datos del adminsitrador a asignar a la comunidad
    And Se pulsa el boton asignar administrador
    Then Se valida la asignacion correcta de administrador para la comunidad



    @SetNewAdminCC
    Examples:
      | SheetBD       | StatusCommunity               |
      | ComunidadesCL | Registered pending properties |


    @SetNewAdminSC
    Examples:
      | SheetBD       | StatusCommunity               |
      | ComunidadesMX | Registered pending properties |
