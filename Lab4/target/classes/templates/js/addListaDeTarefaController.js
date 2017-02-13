
/*
 * Controller do template addListaDeTarefa.
 * 
 * Criado por Davi Laerte
 */
app.controller("addListaDeTarefaController", function($scope, $http, $location) {
	
	$scope.numeroMaximoDeCaracteresEntrada = 15;
	$scope.alerta = {texto:"", tipoAlerta:"" , estaAtivo:false, classe:""};
	
	$scope.addListaDeTarefa = function() {
		var listaDeTarefa = {nome:$scope.novaListaDeTarefa, listaDeTarefas:[]};
		
		$http.post($scope.$parent.protocolo + location.host + "/ListaDeTarefa", listaDeTarefa).success(function (data, status) {
			
			$scope.mudarAlerta("Lista Adicionada","Sucesso",true, "alert alert-success");
			$scope.novaListaDeTarefa = '';
			
			console.log(status);
		
		}).error(function (data, status){
			
			//verifica status e modifica alerta de acordo com status.
			if(status == 406) {
				$scope.mudarAlerta("Erro Ao Tentar Adicionar Lista, Lista invalida.", "Status: "+status, true, "alert alert-danger");
			
			} else {
				$scope.mudarAlerta("Erro Ao Tentar Adicionar Lista, Erro Do Servidor.", "Status: "+status, true, "alert alert-danger");
			}
			console.log(status);
		});
	}
	
	$scope.mudarAlerta = function(texto,tipoAlerta, estaAtivo, classe) {
		$scope.alerta.texto = texto;
		$scope.alerta.tipoAlerta = tipoAlerta;
		$scope.alerta.estaAtivo = estaAtivo;
		$scope.alerta.classe = classe;
	}
	
});
