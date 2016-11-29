'use strict';

var markers = [];

angular.module('proyectoClienteApp')
  .controller('mapa', function($scope, $http) {
    
    $scope.acces_token = "";
    
    $http.post('http://localhost:8080/api/login', {
        username: "user",
        password: "pass"
    }).then(function (response) {
        $scope.access_token=response.data.access_token;
        var req = {
          method: 'GET',
          url: 'http://localhost:8080/parquimetro/consultar',
          headers: {
            'Authorization': "Bearer "+$scope.access_token
          }
        }
        $http(req).then(function(res){
            var data=res.data;
            markers = data;
            
            var map = new google.maps.Map(document.getElementById("map"), {
                zoom: 11,
                center: new google.maps.LatLng(19.4007, -99.1573),
                mapTypeId: google.maps.MapTypeId.ROADMAP
            });

            google.maps.Circle.prototype.contains = function(latLng) {
                return this.getBounds().contains(latLng) && google.maps.geometry.spherical.computeDistanceBetween(this.getCenter(), latLng) <= this.getRadius();
            }

            for(var i = 0; i < markers.length; i++){
                var marker = new google.maps.Marker({
                map: map,
                position: new google.maps.LatLng(markers[i].latitud, markers[i].longitud),
                title: markers[i].nombre
                });
                var circle = new google.maps.Circle({
                    map: map,
                    radius: 300,
                    fillColor: '#AA0000',
                    strokeColor: '#FF0000',
                    strokeOpacity:'0.1'
                });
                circle.bindTo('center', marker, 'position');
            }

            var marker1 = new google.maps.Marker({
                map: map,
                position: new google.maps.LatLng(19.393395, -99.175825),
                title: 'Uno'
            });

            function calculateRoute(from, to, mapObject) {

            var directionsService = new google.maps.DirectionsService();
            var directionsRequest = {
              origin: from,
              destination: to,
              travelMode: google.maps.DirectionsTravelMode.WALKING,
              unitSystem: google.maps.UnitSystem.METRIC
            };
            directionsService.route(
              directionsRequest,
              function(response, status)
              {
                if (status == google.maps.DirectionsStatus.OK)
                {
                  new google.maps.DirectionsRenderer({
                    map: mapObject,
                    directions: response
                  });
                }
                else
                  $("#error").append("Unable to retrieve your route<br />");
              }
            );
          }

          $(document).ready(function() {

              $("#from-link").click(function(event) {
                event.preventDefault();

              navigator.geolocation.getCurrentPosition(function(position) {
                var geocoder = new google.maps.Geocoder();
                geocoder.geocode({
                  "location": new google.maps.LatLng(position.coords.latitude, position.coords.longitude)
                },
                function(results, status) {
                  if (status == google.maps.GeocoderStatus.OK)
                      $("#from").val(results[0].formatted_address);
                  else
                    $("#error").append("Unable to retrieve your address<br />");
                });
              },
              function(positionError){
                $("#error").append("Error: " + positionError.message + "<br />");
              },
              {
                enableHighAccuracy: true,
                timeout: 10 * 1000
              });
            });

            $("#calculate-route").submit(function(event) {
              event.preventDefault();
              calculateRoute($("#from").val(), $("#to").val(), map);
            });

              $("#coche").click(function(event){
                  event.preventDefault();

                  navigator.geolocation.getCurrentPosition(function(position) {
                    var geocoder = new google.maps.Geocoder();
                    geocoder.geocode({
                      "location": new google.maps.LatLng(position.coords.latitude, position.coords.longitude)
                    },
                    function(results, status) {
                      if (status == google.maps.GeocoderStatus.OK){
                          $("#to").val(results[0].formatted_address);
                            evaluar(position.coords.latitude, position.coords.longitude);
                      }else
                        $("#error").append("Unable to retrieve your address<br />");
                    });
                  },
                  function(positionError){
                    $("#error").append("Error: " + positionError.message + "<br />");
                  },
                  {
                    enableHighAccuracy: true,
                    timeout: 10 * 1000
                  });

              });

              function evaluar(lati, long){
                  LatLng = new google.maps.LatLng({lat: lati, lng: long});
                  if(circle.contains(LatLng)){
                    alert("ATENCIÓN! Probablemente te encuentre en una zona de parquímetros");   
                  } 
              }
          });
        });
    });
});