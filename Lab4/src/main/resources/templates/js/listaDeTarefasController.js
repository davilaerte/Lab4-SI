
/*
 * Controller do template listaDeTarefa.
 * 
 * Criado por Davi Laerte
 */
app.controller("listaDeTarefasController", function($scope, $http, $location, $routeParams, $filter) {

	$scope.tarefas = [];
	$scope.optionsPrioridade = ["Baixa","Media","Alta"];
	$scope.alerta = {texto:"", tipoAlerta:"" , estaAtivo:false, classe:""};
	
	$scope.nomeDaLista = "";
	$scope.mostrarSubtarefas = false;
	$scope.efiltro = false;
	$scope.numeroMaximoDeCaracteresEntrada = 40;
	$scope.numeroMaximoDeCaracteresTexto = 200;
	$scope.updateTarefa;
	$scope.TarefaParaAddSubTarefa;
	
	//Carrega a lista de tarefas.
	(function () {
		$http.get($scope.$parent.protocolo + location.host + "/ListaDeTarefa/"+ $routeParams.idLista).success(function (data, status){
			$scope.tarefas =  data.listaDeTarefas;
			$scope.nomeDaLista = data.nome;
			
			console.log(status);
		}).error(function (data, status){
			
			//verifica status e modifica alerta de acordo com status.
			if(status == 404) {
				$scope.mudarAlerta("Erro Ao Tentar Carregar Lista De Tarefa, Lista Nao Encontrada", "Status: "+status, true, "alert alert-danger");
			
			} else {
				$scope.mudarAlerta("Erro Ao Tentar Carregar Lista De Tarefa, Erro Do Servidor.", "Status: "+status, true, "alert alert-danger");
			}
			
		})
	})();
	
		
	$scope.iniciaFormCriarAtividade = function() {
		$scope.novaTarefa = '';
		$scope.novaCategoria = '';
		$scope.novoComentario = '';
		$scope.novaPrioridade = $scope.optionsPrioridade[0];
	}
	
		
	$scope.iniciaFormEditarAtividade = function(tarefa) {
		$scope.updateTarefa = tarefa;
		
		$scope.editarTarefa = tarefa.nome;
		$scope.editarCategoria = tarefa.categoria;
		$scope.editarComentarios = tarefa.comentarios;
		$scope.editarPrioridade = tarefa.prioridade;
	}
	
	$scope.iniciaFormAddSubtarefa = function(tarefa) {
		$scope.TarefaParaAddSubTarefa = tarefa;
		$scope.novaSubtarefa = '';
	}
	
	$scope.editarTarefaAtual = function() {
				
		$scope.updateTarefa.nome = $scope.editarTarefa;
		$scope.updateTarefa.categoria = $scope.editarCategoria;
		$scope.updateTarefa.comentarios = $scope.editarComentarios;
		$scope.updateTarefa.prioridade = $scope.editarPrioridade;
		
		//Jquery, necessario para fechar modal.
		$("#modalEditarTarefa").modal("hide");
		
		$http.put($scope.$parent.protocolo + location.host + "/ListaDeTarefa/"+ $routeParams.idLista + "/tarefa/"+ $scope.updateTarefa.id, $scope.updateTarefa).success(function (data, status) {
			$scope.mudarAlerta("Tarefa Modificada. ", "Sucesso!", true, "alert alert-info");
			console.log(status);
		}
		).error(function (data, status) {
			$scope.msgError("Editar Tarefa","tarefa invalida.","Lista ou Tarefa Nao Encontrada.", "erro no servidor.", status);
		})
	}
	
	$scope.addTarefa = function() {
		
		var novaTarefa = { nome:$scope.novaTarefa, categoria:$scope.novaCategoria, comentarios:$scope.novoComentario,
				prioridade:$scope.novaPrioridade, estaConcluida:false, subtarefas:[]};
		
		//Jquery, necessario para fechar modal.
		$("#modalCriarTarefa").modal("hide");
		
		$http.put($scope.$parent.protocolo + location.host + "/ListaDeTarefa/"+ $routeParams.idLista + "/tarefa", novaTarefa).success(function (data, status) {
			$scope.tarefas.push(data);
			
			$scope.$parent.totalDeTarefas += 1;
						
			$scope.mudarAlerta("Tarefa Adicionada a Lista.", "Sucesso!", true, "alert alert-success");
						
			console.log(status);
		}).error(function (data, status) {
			$scope.msgError("Adicionar Tarefa","tarefa invalida.","Lista Nao Encontrada.", "erro no servidor.", status);
		})
	}
	
	$scope.tarefaEstaConcluida = function(tarefa) {
		tarefa.estaConcluida = !tarefa.estaConcluida;
		
		$http.put($scope.$parent.protocolo + location.host + "/ListaDeTarefa/"+ $routeParams.idLista + "/tarefa/"+ tarefa.id, tarefa).success(function (data, status) {
			
			console.log(status);
		
		}).error(function (data, status) {
			$scope.msgError("Modificar Tarefa","tarefa invalida.","Lista ou Tarefa Nao Encontrada.", "erro no servidor.", status);
		})
	}
	
	$scope.addSubtarefaEmTarefa = function() {
		
		var subtarefa = {nome:$scope.novaSubtarefa, estaConcluida:false};
		
		//Jquery, necessario para fechar modal.
		$("#modalAdicionarSubtarefa").modal("hide");
		
		$http.put($scope.$parent.protocolo + location.host + "/ListaDeTarefa/"+ $routeParams.idLista + "/tarefa/"+ $scope.TarefaParaAddSubTarefa.id + "/subtarefa"
				, subtarefa).success(function (data, status) {
	
					$scope.TarefaParaAddSubTarefa.subtarefas.push(data);
					
					
					
					console.log(status);
		
		}).error(function (data, status) {
			$scope.msgError("Adicionar SubTarefa em Tarefa","SubTarefa invalida.","Lista ou Tarefa Nao Encontrada.", "erro no servidor.", status);
		})
	}
	
	$scope.subtarefaEstaConcluida = function(tarefa, subtarefa) {
		
		subtarefa.estaConcluida = !subtarefa.estaConcluida;
		
		$http.put($scope.$parent.protocolo + location.host + "/ListaDeTarefa/"+ $routeParams.idLista + "/tarefa/"+ tarefa.id + "/subtarefa/" + subtarefa.id
				, subtarefa).success(function (data, status) {
					
					console.log(status);
					
		}).error(function (data, status) {
			$scope.msgError("Editar SubTarefa","SubTarefa invalida.","Lista,Tarefa ou SubTarefa Nao Encontrada.", "erro no servidor.", status);
		})
	}
	
	$scope.removerTarefaDaLista = function(tarefa) {
		
		$http.delete($scope.$parent.protocolo + location.host + "/ListaDeTarefa/" + $routeParams.idLista + "/tarefa/" + tarefa.id 
				).success(function (data, status){
			
			var indice = obterIndexPorId($scope.tarefas, tarefa.id);
					
			$scope.tarefas.splice(indice,1);
			$scope.$parent.totalDeTarefas -= 1;
			console.log(status);
		}).error(function (data, status) {
			$scope.mudarAlerta("Erro Ao Tentar Remover Tarefa, Erro Do Servidor.", "Status: "+status, true, "alert alert-danger");
		})
	}
	
	$scope.removerSubTarefaDeTarefa = function(tarefa, subtarefa) {
		
		$http.delete($scope.$parent.protocolo + location.host + "/ListaDeTarefa/" + $routeParams.idLista + "/tarefa/" + tarefa.id
				+ "/subtarefa/"+ subtarefa.id).success(function (data, status){
			
			var indiceSubTarefa = obterIndexPorId(tarefa.subtarefas, subtarefa.id);
					
			tarefa.subtarefas.splice(indiceSubTarefa,1);		
			console.log(status);
		}).error(function (data, status) {
			$scope.mudarAlerta("Erro Ao Tentar Remover SubTarefa, Erro Do Servidor.", "Status: "+status, true, "alert alert-danger");
		})
	}
	
	$scope.removerTodasAsTarefas = function() {
		
		$http.delete($scope.$parent.protocolo + location.host + "/ListaDeTarefa/" + $routeParams.idLista + "/tarefa/all" 
		).success(function (data, status){
			
			$scope.$parent.totalDeTarefas -= $scope.tarefas.length;
			$scope.tarefas = [];
		}).error(function (data, status) {
			$scope.mudarAlerta("Erro Ao Tentar Remover Tarefas, Erro Do Servidor.", "Status: "+status, true, "alert alert-danger");
		})
				
	}
	
	//verifica status e modifica alerta de acordo os textos de Error.
	$scope.msgError = function(tipoError, textoError406,textoError404, textoError, status) {
		
		if(status == 406) {
			$scope.mudarAlerta("Erro Ao Tentar "+ tipoError +", "+textoError406, "Status: "+status, true, "alert alert-danger");
		
		} else if(status == 404) {
			$scope.mudarAlerta("Erro Ao Tentar "+ tipoError +", "+textoError404, "Status: "+status, true, "alert alert-danger");

		} else {
			$scope.mudarAlerta("Erro Ao Tentar "+ tipoError +", "+textoError, "Status: "+status, true, "alert alert-danger");
		}
	}
	
	$scope.mudarAlerta = function(texto,tipoAlerta, estaAtivo, classe) {
		$scope.alerta.texto = texto;
		$scope.alerta.tipoAlerta = tipoAlerta;
		$scope.alerta.estaAtivo = estaAtivo;
		$scope.alerta.classe = classe;
	}
	
	//retorna o filtroAtual de acordo com a entrada do usuario.
	$scope.filtroAtual = function() {
		if($scope.filtroTarefas === "prioridade") {
			return $scope.filtroTarefasPrioridade;
		} else if($scope.filtroTarefas === "categoria") {
			return $scope.filtroTarefasCategoria;
		} else {
			return "";
		}
	}
	
	//desliga filtro se ele estiver inativo.
	$scope.mudarFiltro = function() {
		if($scope.efiltro) {
			$scope.filtroTarefas = '';
		}
		$scope.efiltro = !$scope.efiltro;
	}
	
	$scope.obterTarefasNoFiltro = function() {
		return $filter('meuFiltro')($scope.tarefas, $scope.filtroTarefas, $scope.filtroAtual());
	}
	
	$scope.numeroDeTarefasNoFiltro = function() {
		return $scope.obterTarefasNoFiltro().length;
	}
	
	$scope.numeroDeTarefasConcluidasNoFiltro = function() {
		return obterNumeroDeAtividadeFeitas($scope.obterTarefasNoFiltro());
	}
		
	$scope.mudaClassePrioridade = function(prioridade) {
		
		if(prioridade === "Alta") {
			return "label label-danger";
		} else if(prioridade === "Media") {
			return "label label-warning";
		} else {
			return "label label-info";
		}
	}
	
	$scope.progresso = function() {
		if($scope.efiltro) {
			console.log($scope.numeroDeTarefasNoFiltro());
			return $scope.porcetagem($scope.numeroDeTarefasNoFiltro(), $scope.numeroDeTarefasConcluidasNoFiltro());
		} else {
			return $scope.porcetagem($scope.tarefas.length,obterNumeroDeAtividadeFeitas($scope.tarefas));
		}
	}
	
	$scope.porcetagem = function(numeroTotal, numeroFeitas) {
		return (numeroTotal > 0 ? numeroFeitas*100/numeroTotal:0).toFixed(1);
	}
	
	$scope.barraProgresso = function() {
		
		if($scope.progresso() >= 0 && $scope.progresso() <= 25){
			return "progress-bar progress-bar-danger progress-bar-striped active";
		} else if ($scope.progresso() > 25 && $scope.progresso() <= 50) {
			return "progress-bar progress-bar-warning progress-bar-striped active";
		} else if ($scope.progresso() > 50 && $scope.progresso() <= 75) {
			return "progress-bar progress-bar-info progress-bar-striped active";
		} else {
			return "progress-bar progress-bar-success progress-bar-striped active";
		}
	}
	
});
