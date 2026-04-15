@Communities @regression
Feature: Comunidades

  Background:
    Given un usuario superadmin se encuentra loggeado en CF
    And el usuario superadmin se encuentra en el modulo comunidades

  @SearchCommunityByNameSuccess
  Scenario Outline: Buscar comunidad por nombre
    Given se leen y extraen los datos de pruebas desde la BD: "<SheetBD>", con status: "<StatusCommunity>"
    When Ingresa nombre de comunidad a buscar
    And Se pulsa boton filtrar
    Then se muestra resultados exitosos de busqueda de comunidad

    @SearchCommunityByNameCC
    Examples:
      | SheetBD       | StatusCommunity               |
      | ComunidadesCL | Registered pending properties |

    @SearchCommunityByNameSC
    Examples:
      | SheetBD       | StatusCommunity               |
      | ComunidadesMX | Registered pending properties |


  @GoToEditCommunitySuccess
  Scenario Outline: Acceder a editar Comunidad
    Given se leen y extraen los datos de pruebas desde la BD: "<SheetBD>", con status: "<StatusCommunity>"
    When Ingresa nombre de comunidad a buscar
    And Se pulsa boton filtrar
    And Se pulsar el boton de accion Editar Comunidad
    Then se muestra redireccion exitosa al modulo de edicion de comunidad

    @GoToEditCommunityCC
    Examples:
      | SheetBD       | StatusCommunity               |
      | ComunidadesCL | Registered pending properties |


    @GoToEditCommunitySC
    Examples:
      | SheetBD       | StatusCommunity               |
      | ComunidadesMX | Registered pending properties |


  @GoToViewCommunitySuccess
  Scenario Outline: Acceder a ver una Comunidad
    Given se leen y extraen los datos de pruebas desde la BD: "<SheetBD>", con status: "<StatusCommunity>"
    When Ingresa nombre de comunidad a buscar
    And Se pulsa boton filtrar
    And Se pulsar el boton de accion visualizar Comunidad
    Then se muestra redireccion exitosa al modulo de ver comunidad

    @GoToViewCommunityCC
    Examples:
      | SheetBD       | StatusCommunity               |
      | ComunidadesCL | Registered pending properties |

    @GoToViewCommunitySC
    Examples:
      | SheetBD       | StatusCommunity               |
      | ComunidadesMX | Registered pending properties |


  @ImpersonateAdminSuccess
  Scenario Outline: Impersonar Administrador en una comunidad exitosamente por primera vez
    Given se leen y extraen los datos de pruebas desde la BD: "<SheetBD>", con status: "<StatusCommunity>"
    When Ingresa nombre de comunidad a buscar
    And Se pulsa boton filtrar
    And Se pulsar el boton de accion impersonar administrador
    And se muestra redireccion hacia pantalla de terminos y condiciones
    Then Se redirecciona a la pantalla de mis comunidades

    @ImpersonateAdminIntoCommunityCC
    Examples:
      | SheetBD       | StatusCommunity|
      | ComunidadesCL | Admin Assigned |

    @ImpersonateAdminIntoCommunitySC
    Examples:
      | SheetBD       | StatusCommunity|
      | ComunidadesMX | Admin Assigned |
