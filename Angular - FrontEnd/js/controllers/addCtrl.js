angular.module('angularSpa')
    .controller('AddCtrl', function($scope, actorsService){			
        $scope.actors =[];
        $scope.create= function(){
            actorsService.addActors($scope.actor.id, $scope.actor.first, $scope.actor.last, $scope.actor.update)
            .success(function(data){
                $scope.actors = data;
            })
            .error(function(error){
                $scope.status = 'Error al consultar por actores';
            });
        }
    });