var misDatosTorta = angular.module('angularSpa').controller('linealController',function($scope,$http){
        $scope.importarLineal = function(){
            $http.get('http://159.203.84.81:8080/WW3App/auxiliarJsonFullLinear').success(function(datoLineal){
                $scope.dataLineal = datoLineal;

                var lineal = document.getElementById("myLineChart");
                var gLineal = lineal.getContext('2d');

                Chart.defaults.global.defaultFontColor = 'black';
                Chart.defaults.global.defaultFontSize = 16;
                var a=0;
                var x=0;
                var y=0;
                var i=0;
                var fechasLineal = [];
                var conceptos=[];
                var numFechas=datoLineal.cantidadFechas;
                var numConceptos = datoLineal.info.length;
                var auxiliar=[];
                var menciones=[];

                for(i=0; i<datoLineal.cantidadFechas;i++)
                {
                    fechasLineal.push(datoLineal.info[0].puntos[i].identificador);
                }
                
                for (x=0;x<numConceptos;x++)
                {
                    for(y=0;y<numFechas;y++)
                    {
                        auxiliar[y]=datoLineal.info[x].puntos[y].tweets;
                    }
                    menciones[x]= auxiliar;
                    auxiliar =[];
                }

                for (a=0;a<numConceptos;a++)
                {
                    conceptos[a]=datoLineal.info[a].concepto;
                }

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

                function crearDataSet() 
                {
                    var dataSetP = [];
                    var largoConceptos = conceptos.length;
                    for (var i = 0; i < largoConceptos; i++ ) 
                    {
                        dataSetP[i]=
                            {
                                label: conceptos[i],
                                data: menciones[i],

                                fill: false,
                                borderColor: obtenerColorRandom()
                            };
                    }
                return dataSetP;
                }
             
                var dataLineal = {
                    labels: fechasLineal.sort(),
                    datasets: crearDataSet() 
                }

                var options = {
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
                                labelString: 'Cantidad de Menciones vs Fecha',
                                fontSize: 20 
                            }
                        }]            
                    }  
                };

                var myLineChart = new Chart(gLineal, {
                  type: 'line',
                  data: dataLineal,
                  options: options
                });
            });
        }
        $scope.importarLineal();
    }
);