
/*
 * Modulo geral da aplicacao e configuracao das routes.
 * 
 * Criado por Davi Laerte
 */
var app = angular.module("menuPrincipal", ["ngRoute","angularUtils.directives.dirPagination"]);

app.config(function($routeProvider) {

	$routeProvider
	.when("/addListaTarefa", {
		templateUrl : "/templates/addListaDeTarefa.html",
		controller: "addListaDeTarefaController"
	})
	.when("/verListaTarefas", {
		templateUrl : "/templates/verListaDeTarefas.html",
		controller: "verListaDeTarefasController"	
	})
	.when("/sobre", {
		templateUrl : "/templates/sobre.html"
	})
	.when("/listaDeTarefa/:idLista", {
		templateUrl : "/templates/listaDeTarefa.html",
		controller : "listaDeTarefasController"
	})
	.otherwise({
		redirectTo: '/addListaTarefa'
	});
	
});