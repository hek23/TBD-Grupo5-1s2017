angular.module('angularSpa').controller('seleccionController', function($scope, $http){
        $http.get('http://localhost:8080/WW3App/country/listcountrynames').success(function (datos) 
        {
         $scope.testAccounts = datos;
         $scope.selectedTestAccount = $scope.testAccounts[0];
        });


        var glineal = document.getElementById("myExChart");
        var grLineal = glineal.getContext('2d');

        Chart.defaults.global.defaultFontColor = 'black';
        Chart.defaults.global.defaultFontSize = 16;

        $scope.selectedTestAccount;
        $scope.show = false;
        $scope.datos = {};
        
        function obtenerColorRandom() 
        {
            var letters = '0123456789ABCDEF';
            var color = '#';
            for (var i = 0; i < 6; i++ ) 
            {
                color += letters[Math.floor(Math.random() * 16)];
            }
        return color;
        } 

        var x = 1;
        var y = null; // To keep under proper scope

        function sleep(milliseconds) {
            var start = new Date().getTime();
            for (var i = 0; i < 1e7; i++) 
            {
                if ((new Date().getTime() - start) > milliseconds)
                {
                    break;
                }
            }
        }

        $scope.generar = function() {
            $http.get('http://localhost:8080/WW3App/jsongenerationgraph/'+$scope.selectedTestAccount.nombrePais).success(function (prueba) 
            {
                $scope.pruebas = prueba;
                var pruebaFechas = [];
                var menciones = [];
                var i=0;
                angular.forEach(prueba, function(fecha,cantidad){
                    pruebaFechas.push(prueba[i].fecha);
                    menciones.push(prueba[i].cantidad)
                    i = i +1;
                });
            $scope.datos = 
            {
                labels: pruebaFechas,
                datasets:[
                {
                    label: $scope.selectedTestAccount.nombrePais,
                    data: menciones,
                    fill: false,
                    borderColor: obtenerColorRandom()
                }]
            }
            $scope.options = {
                        scales: {
                            yAxes: 
                            [{
                                ticks: 
                                {
                                    beginAtZero:true
                                },
                                
                                scaleLabel: 
                                {
                                    display: true,
                                    labelString: 'Cantidad de Menciones vs Fecha del país '+$scope.selectedTestAccount.nombrePais,
                                    fontSize: 20 
                                }
                            }]            
                        }  
                    };

            var myExChart = new Chart(grLineal, {
              type: 'line',
              data: $scope.datos,
              options: $scope.options
            });
            });

            
        }
});