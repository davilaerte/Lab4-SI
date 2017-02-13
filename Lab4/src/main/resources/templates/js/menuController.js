
/*
 * Controller pai da aplicacao.
 * 
 * Criado por Davi Laerte
 */
app.controller("menuControle", function($scope, $http ,$location) {
	$scope.totalDeTarefas = 0;
	$scope.$location = $location;
	$scope.protocolo = "https://";
	
	//carrega numero total de tarefas
	(function() {
		console.log(location.host);
		$http.get($scope.protocolo + location.host + "/ListaDeTarefa/TotalTarefas").success(function (data, status){
			$scope.totalDeTarefas = data;
		});
	})();
	
	$scope.verificaRota = function(caminhoDaRota) {
		if($scope.$location.path() === caminhoDaRota) {
			return "active";
		}
		return "";
	}
	
	
});



