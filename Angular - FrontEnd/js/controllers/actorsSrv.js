angular.module('angularSpa')
    .service('actorsService', function($http){
        var urlBase = 'http://localhost:8080/sakila-backend-master/actors';
        this.getActors = function(){
            return $http.get(urlBase);
        };
        this.addActors = function(id, first, last, update){
            var actor = {
                actorId: id,
                firstName: first,
                lastName: last,
                lastUpdate: update

            }
            return $http.post(urlBase, actor);
        };
    });
 