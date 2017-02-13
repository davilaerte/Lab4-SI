
/*
 * Controller do template verListaDeTarefas.
 * 
 * Criado por Davi Laerte
 */
app.controller("verListaDeTarefasController", function($scope, $http, $location) {
	$scope.listasDeTarefas = [];
	$scope.alerta = {texto:"", tipoAlerta:"" , estaAtivo:false, classe:""};
	$scope.listaDeTarefaDownloadPDF;
	
	//carrega listas de tarefas
	$scope.carregarListasDeTarefas = function () {
		$http.get($scope.$parent.protocolo + location.host + "/ListaDeTarefa/ListaDeTarefaReduzida").success(function (data, status){
			$scope.listasDeTarefas =  data;
			console.log(status);
		
		}).error(function (data, status){
			$scope.mudarAlerta("Erro Ao Tentar Carregar Listas De Tarefas, Erro Do Servidor.", "Status: "+status, true, "alert alert-danger");
		})
	}
	
	$scope.carregarListasDeTarefas();
	
	$scope.deletarListaDeTarefa = function(listaDeTarefa) {
		
		$http.delete($scope.$parent.protocolo + location.host + "/ListaDeTarefa/" + listaDeTarefa.id).success(function (data, status){
			
			var indice = obterIndexPorId($scope.listasDeTarefas, listaDeTarefa.id);
			$scope.$parent.totalDeTarefas -= listaDeTarefa.tamanho;
			$scope.listasDeTarefas.splice(indice,1);
			console.log(status);
		
		}).error(function (data, status){
			$scope.mudarAlerta("Erro Ao Tentar remover Lista De Tarefa, Erro Do Servidor.", "Status: "+status, true, "alert alert-danger");
		})
			
	}
	
	$scope.removerTodasAsTarefasDasListas = function() {
		$http.delete($scope.$parent.protocolo + location.host + "/ListaDeTarefa/allTarefa").success(function (data, status) {
	
			$scope.carregarListasDeTarefas();
			$scope.$parent.totalDeTarefas = 0;
			console.log(status);
		
		}).error(function (data, status){
			$scope.mudarAlerta("Erro Ao Tentar remover Listas De Tarefas, Erro Do Servidor.", "Status: "+status, true, "alert alert-danger");
		})
	}
	
	$scope.iniciaDownloadListaDeTarefa = function(listaDeTarefa) {
		$scope.listaDeTarefaDownloadPDF = listaDeTarefa;
	}
	
	$scope.baixarPDFListaDeTarefa = function() {
		
		//Jquery, necessario para fechar modal.
		$("#modalDownloadPDF").modal("hide");
		
		var idLista = $scope.listaDeTarefaDownloadPDF.id;
						
		$http.get($scope.$parent.protocolo + location.host + "/ListaDeTarefa/"+ idLista).success(function (data, status){
			
			var textoPDF = criarTextoPDF(data);
			var docDefinition = textoPDF;
			
			pdfMake.createPdf(docDefinition).download(data.nome+'.pdf');
			
		}).error(function (data, status){
			
			//verifica status e modifica alerta de acordo com status.
			if(status == 404) {
				$scope.mudarAlerta("Erro Ao Tentar Baixar PDF, Lista Nao Encontrada", "Status: "+status, true, "alert alert-danger");
			
			} else {
				$scope.mudarAlerta("Erro Ao Tentar Baixar PDF, Erro Do Servidor.", "Status: "+status, true, "alert alert-danger");
			}
			
		})
		
	}
	
	$scope.mudarAlerta = function(texto,tipoAlerta, estaAtivo, classe) {
		$scope.alerta.texto = texto;
		$scope.alerta.tipoAlerta = tipoAlerta;
		$scope.alerta.estaAtivo = estaAtivo;
		$scope.alerta.classe = classe;
	}
	
});
