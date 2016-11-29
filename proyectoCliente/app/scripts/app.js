'use strict';

/**
 * @ngdoc overview
 * @name proyectoClienteApp
 * @description
 * # proyectoClienteApp
 *
 * Main module of the application.
 */

var proyectoClienteApp = angular.module('proyectoClienteApp', []);

proyectoClienteApp.controller('proyectoClienteAppController', function($scope, $http) {
    
    $scope.acces_token = "";
    $scope.listaComentarios = [];
    
    $http.post('http://localhost:8080/api/login', {
        username: "user",
        password: "pass"
    }).then(function (response) {
        $scope.access_token=response.data.access_token;
        var req = {
          method: 'GET',
          url: 'http://localhost:8080/comentario/consultar',
          headers: {
            'Authorization': "Bearer "+$scope.access_token
          }
        }
        $http(req).then(function(res){
            var data=res.data;
            $scope.listaComentarios=data;
        });
    });
});