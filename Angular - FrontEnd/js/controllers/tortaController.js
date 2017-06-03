var misDatosTorta = angular.module('angularSpa').controller('tortaController',function($scope,$http){
        $scope.importarTorta = function(){
            $http.get('http://localhost:8080/WW3App/auxiliarJsonFullCake').success(function(datos2){
                $scope.dataTorta = datos2;

                var torta = document.getElementById("myPieChart");
                
                //Aquí comencé a modificar
                //Creo arreglos auxiliares para guardar la data
                ejex = [];
                ejey = [];
                
                //Creo una variable auxiliar 0
                var i = 0;
                var ctdtweets = 0;
                
                //Ciclo for each. Para cada objeto de datos2.info, iterando sobre identificador y tweets
                angular.forEach(datos2.info, function(identificador, tweets){
                    
                    //Se agregan los datos a las listas particulares
                    ejex.push(datos2.info[i].identificador);
                    ejey.push(datos2.info[i].tweets)
                    ctdtweets = datos2.info[i].tweets + ctdtweets;
                    
                    //Se avanza en el contrador auxiliar
                    i = i +1;
                });
                
                $scope.ctdtweets = ctdtweets;
                
                var dataTorta = {
                    labels:
                    //Aquí aplique la lista de eje x que son las labels
                        ejex,
                        datasets: 
                        [
                            {
                                //Aquí aplique la lista de eje y que son los tweets
                                data: ejey,
                                backgroundColor: 
                                ["#DD6FF9","#F0AA81","#AE256C","#ACE6BB","#EBDEAB","#E88477","#D78811","#9C0C26","#4FDB82","#97B32C","#B0A41D","#235E66","#D9A069","#AA8750","#9F753A","#2FAD3C","#A6FF55","#62F125","#ADF345","#3E5B38","#55702B","#CD2CD7","#13905C","#6F952E","#B6C020","#876079","#C46E03","#62703D","#C9C6FF","#5A61B8","#140748","#00C7FD","#B50353","#02DD8C","#DD73F2","#C06C8B","#49C10B","#EB1FF6","#2F80D1","#CAF4EF","#6A0D68","#8B25C2","#0BE1AD","#8DD1DA","#3A43E2","#25D223","#BBFD0B","#016F31","#DCEADC","#516944","#C6999B","#D57C37","#849F3C","#008FB6","#B071AB","#572F0C","#BE12A5","#A3932C","#02D918","#FD97E4","#34B53C","#2EA415","#9B82FA","#FFDD9F","#69CF0B","#43FF93","#BC1603","#C9E5CE","#FCEDA7","#C00901","#44433E","#6FA86A","#C24B71","#97E748","#F19FA9","#1FE532","#492F28","#AEAE92","#039FAE","#89F19A","#BAA90D","#C57E49","#6E81D1","#3E5CDF","#A69A73","#53EFDF","#C94378","#DE65F4","#722DFF","#CA664D","#AA18AE","#778BB0","#48FBEE","#F61130","#10B770","#51A794","#61CFD7","#F20EDF","#9C383F"],
                                
                                hoverBackgroundColor: 
                                ["#DD6FF9","#F0AA81","#AE256C","#ACE6BB","#EBDEAB","#E88477","#D78811","#9C0C26","#4FDB82","#97B32C","#B0A41D","#235E66","#D9A069","#AA8750","#9F753A","#2FAD3C","#A6FF55","#62F125","#ADF345","#3E5B38","#55702B","#CD2CD7","#13905C","#6F952E","#B6C020","#876079","#C46E03","#62703D","#C9C6FF","#5A61B8","#140748","#00C7FD","#B50353","#02DD8C","#DD73F2","#C06C8B","#49C10B","#EB1FF6","#2F80D1","#CAF4EF","#6A0D68","#8B25C2","#0BE1AD","#8DD1DA","#3A43E2","#25D223","#BBFD0B","#016F31","#DCEADC","#516944","#C6999B","#D57C37","#849F3C","#008FB6","#B071AB","#572F0C","#BE12A5","#A3932C","#02D918","#FD97E4","#34B53C","#2EA415","#9B82FA","#FFDD9F","#69CF0B","#43FF93","#BC1603","#C9E5CE","#FCEDA7","#C00901","#44433E","#6FA86A","#C24B71","#97E748","#F19FA9","#1FE532","#492F28","#AEAE92","#039FAE","#89F19A","#BAA90D","#C57E49","#6E81D1","#3E5CDF","#A69A73","#53EFDF","#C94378","#DE65F4","#722DFF","#CA664D","#AA18AE","#778BB0","#48FBEE","#F61130","#10B770","#51A794","#61CFD7","#F20EDF","#9C383F"],
                            }
                        ]
                };
                
                var optionsTorta = 
                {
                    animation: 
                    {
                        animateScale: true
                    }
                };
                    
                // Chart declaration:
                var myPieChart = new Chart(torta, 
                {
                    type: 'pie',
                    data: dataTorta,
                    options: optionsTorta
                });
            });
        }
        $scope.importarTorta();
    }
);

