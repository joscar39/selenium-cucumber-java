@Login
Feature: Login

  Background:
    Given la aplicación está inicializada y en la pantalla de login

  @LoginSuperAdminSuccess
  Scenario: Login como superadmin exitosamente
    #Escenario: Login como superadmin exitosamente
    When Se ingresas las credenciales como superadmin
    Then Se redireccionar al home como superadmin exitosamente

#  @LoginAdminFirstTimeSuccess
#  Scenario Outline: Login inicial como administrador por primera vez exitosamente
#    #Escenario: Login inicial como superadmin exitosamente
#    Given se leen los datos de la BD para acceder como admin: "<SheetBD>"
#    When Se ingresas las credenciales de usuario admin de una comunidad
#    Then Al loggearse por primera vez el usuario debe aceptar terminos y condiciones
#    And Se redireccionar a la pantalla de mis comunidades como admin de comunidad exitosamente
#
#    @LoginAdminFirstTimeCC
#    Examples:
#      | SheetBD       |
#      | ComunidadesCL |
#    @LoginAdminFirstTimeSC
#    Examples:
#      | SheetBD       |
#      | ComunidadesMX |
#
  @LoginAdminRecurrentSuccess
  Scenario Outline: Login como administrador recurrente exitosamente
    #Escenario: Login como administrador recurrente exitosamente
    Given se leen los datos de la BD para acceder como admin: "<SheetBD>" con status: "<StatusCommunity>"
    When Se ingresas las credenciales de usuario admin de una comunidad
    And se muestra redireccion hacia pantalla de terminos y condiciones
    And Se debe configurar contraseña nueva como admin
    Then Se redirecciona a la pantalla de mis comunidades

    @LoginAdminRecurrentCC
    Examples:
      | SheetBD       |       StatusCommunity    |
      | ComunidadesCL | ReadyForImpersonation  |
    @LoginAdminRecurrentSC
    Examples:
      | SheetBD       |       StatusCommunity    |
      | ComunidadesMX |ReadyForImpersonation   |

#  @LoginResidentFirstTimeSuccess
#  Scenario Outline: Login inicial como Residente por primera vez exitosamente
#    #Escenario: Login inicial como superadmin exitosamente
#    Given se leen los datos de la BD para acceder como admin: "<SheetBD>"
#    When Se ingresas las credenciales de usuario admin de una comunidad
#    Then Al loggearse por primera vez el usuario debe aceptar terminos y condiciones
#    And Se redireccionar a la pantalla home residente exitosamente
#
#    @LoginResidentFirstTimeCC
#    Examples:
#      | SheetBD       |
#      | ComunidadesCL |
#    @LoginResidentFirstTimeSC
#    Examples:
#      | SheetBD       |
#      | ComunidadesMX |
#
#  @LoginResidentRecurrentSuccess
#  Scenario Outline: Login como Residente recurrente exitosamente
#    #Escenario: Login como administrador recurrente exitosamente
#    Given se leen los datos de la BD para acceder como admin: "<SheetBD>"
#    When Se ingresas las credenciales de usuario residente de una comunidad
#    Then Se redireccionar a la pantalla home residente exitosamente
#
#    @LoginResidentRecurrentCC
#    Examples:
#      | SheetBD       |
#      | ComunidadesCL |
#    @LoginResidentRecurrentSC
#    Examples:
#      | SheetBD       |
#      | ComunidadesMX |
#
#
#  @LoginResidentRecurrentSuccess
#  Scenario Outline: Login con credenciales invalidas
#    #Escenario: Login con credenciales invalidas
#    When Se ingresas las credenciales de usuario invalidas: "<Email>", "<Password>"
#    Then Se mostrar alerta de credenciales invalidas
#
#    Examples:
#      | Email           | Password  |
#      | pedro@perea.com | 321456789 |
