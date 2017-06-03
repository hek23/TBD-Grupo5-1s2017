angular.module('angularSpa', [
    'ngRoute'    ])
    
    .config(function($routeProvider){
    $routeProvider
    .when('/home', {
        templateUrl: 'views/main.html',
    })
    .when('/torta', {
      templateUrl: 'views/torta.html',
      controller: 'tortaController'
    })
    .when('/lineal', {
      templateUrl: 'views/lineal.html',
      controller: 'linealController'
    })
    .when('/seleccion', {
      templateUrl: 'views/seleccion.html',
      controller: 'seleccionController'
    })
    .otherwise({
      redirectTo: '/home'
    });
});