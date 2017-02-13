/*
 * filtros da aplicacao.
 * 
 * Criado por Davi Laerte.
 */

// filtra atividades, por categoria ou prioridade.
app.filter('meuFiltro', function(){
	return function(tarefas, propiedade, valor) {
		var lista = [];
		
		if(valor !== undefined && valor !== "" && propiedade === "categoria") {
			angular.forEach(tarefas, function(item) {
				if(valor === item.categoria) {
					lista.push(item);
				}
		    });
		
		} else if (valor !== undefined && valor !== "" && propiedade === "prioridade") {
			angular.forEach(tarefas, function(item) {
				if(valor === item.prioridade) {
					lista.push(item);
				}
		    });
		} else {
			angular.forEach(tarefas, function(item) {
				lista.push(item);
			});
		}
		
		return lista;
	}
});

// filtro para ordenacao, ordena por prioridade ou nome.
app.filter('minhaOrdenacao', function(){
	return function(tarefas, propiedade) {
		
		var listaOrdenada = [];
		
		angular.forEach(tarefas, function(item) {
			listaOrdenada.push(item);
	    });
		
		if(propiedade === "prioridade") {
			listaOrdenada.sort(function(a, b) {
				if(a.prioridade === "Alta"){
					if(b.prioridade === "Alta" ){
						return 0;
					} else {
						return -1;
					}
				} else if(a.prioridade === "Media") {
					if(b.prioridade === "Alta") {
						return 1;
					} else if(b.prioridade === "Media") {
						return 0;
					} else {
						return -1;
					}
				} else {
					if(b.prioridade === "Baixa") {
						return 0;
					} else {
						return 1;
					}
				}
			});
		
		} else if(propiedade === "nome") {
			listaOrdenada.sort(function(a, b) {
				 var x = a.nome.toLowerCase();
			     var y = b.nome.toLowerCase();
			     if (x < y) {return -1;}
			     if (x > y) {return 1;}
			     return 0;
			});
		} 
		
		return listaOrdenada;
	}
});
