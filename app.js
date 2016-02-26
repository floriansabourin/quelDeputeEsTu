var app = angular.module('plunker', []);

app.controller('MainCtrl', function($scope) {
  
   $scope.affichageQuiz = true;
   $scope.affichageFin = true;
   $scope.fctDeco= function(){
		var divToken = document.getElementById('varToken');
		varaccess_token = divToken.innerHTML;
		//alert(varaccess_token);
		$scope.disconnectUser(varaccess_token);
	};
   
	$scope.disconnectUser = function (access_token) {
		//alert(varaccess_token);
		  var revokeUrl = 'https://accounts.google.com/o/oauth2/revoke?token=' + access_token;

		  // Exécuter une requête GET asynchrone.
		  $.ajax({
			type: 'GET',
			url: revokeUrl,
			async: false,
			contentType: "application/json",
			dataType: 'jsonp',
			success: function(nullResponse) {
			  // Effectuer une action maintenant que l'utilisateur est dissocié
			  // La réponse est toujours non définie.
			},
			error: function(e) {
			  // Gérer l'erreur
			  // console.log(e);
			  // Orienter éventuellement les utilisateurs vers une dissociation manuelle en cas d'échec
			  // https://plus.google.com/apps
			}
		  });
		};
   
   
  
  

});
