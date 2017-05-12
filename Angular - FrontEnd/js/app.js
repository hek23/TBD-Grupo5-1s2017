var app = angular.module('angularSpa', [
    'ngRoute'
    ]);
app.config(function($routeProvider){
    $routeProvider
    .when('/home', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl'
      })
    .when('/about', {
        templateUrl: 'views/about.html',
        controller: 'AboutCtrl'
      })
    .when('/actors', {
        templateUrl:'views/actors.html',
        controller:'ActorsCtrl'
        })
    .when('/add', {
        templateUrl:'views/add.html',
        controller:'AddCtrl'
        })
    .otherwise({
        redirectTo: '/home'
      });
});
