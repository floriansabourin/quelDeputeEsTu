var app = angular.module('nideputenisoumises', []);


app.directive('headTemplate', function(){
    return{
        restrict: 'C',
        templateUrl: 'head.html'
    };
});

app.directive('navTemplate', function(){
    return {
      
        restrict: 'C',
        templateUrl: 'nav.html'
    };
    
});

app.directive('footerTemplate', function(){
    return {
        restrict: 'C',
        templateUrl: 'footer.html'
    };
});

app.controller('MainCtrl', ['$scope', function ($scope) {
  
  $scope.affichageFin = true;
  $scope.compteur = 0;
  $scope.questions = [
    {id:"505", ordre:"1",vote: "",question:"Whats your (full) name?"},
    {id:"402", ordre:"2",vote: "",question:"How old are you?"},
    {id:"302", ordre:"3",vote: "",question:"Whats your Birthday?"},
    {id:"111", ordre:"4",vote: "",question:"What starsign does that make it?"},
    {id:"222", ordre:"5",vote: "",question:"Whats your favourite colour?"},
    {id:"333", ordre:"6",vote: "",question:"Whats your lucky number?"},
    {id:"444", ordre:"7",vote: "",question:"Do you have any pets?"},
    {id:"555", ordre:"8",vote: "",question:"Where are you from?"},
    {id:"666", ordre:"9",vote: "",question:"What starsign does that make it?"},
    {id:"999", ordre:"10",vote: "",question:"How tall are you?"}
  ];
  
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
  
  $scope.oui = function (){
   $scope.questions[$scope.compteur].vote = "ok";
   $scope.compteur++;
   if($scope.compteur == 10){
     $scope.fin();
   }
  }
   
  $scope.non = function (){
    $scope.questions[$scope.compteur].vote = "ko";
    $scope.compteur++;
    if($scope.compteur == 10){
      $scope.fin();
    }
  }
  
  $scope.nspp = function (){
    $scope.questions[$scope.compteur].vote = "nspp";
    $scope.compteur++;
    if($scope.compteur == 10){
      $scope.fin();
    }
  }
   
  $scope.fin = function(){
    console.log($scope.questions);
    $scope.affichageVote = true;
    $scope.affichageFin = false;
  }
  
  
}]);