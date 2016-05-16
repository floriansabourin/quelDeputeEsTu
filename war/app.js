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

app.controller('MainCtrl', ['$scope', '$window', function ($scope, $window) {
    
	$scope.UserName = "Visiteur";
	$scope.connecte = false;
	$scope.accueil = true;
	$scope.affichageVote = true;
	$scope.affichageFin = true;
	$scope.partieVote = true;
	
	$scope.labels = ["Download Sales", "In-Store Sales", "Mail-Order Sales"];
	$scope.data = [300, 500, 100];

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
	
	$window.init = function (){
		var rootApi = 'https://1-dot-nideputesnisoumises.appspot.com/_ah/api/';
		gapi.client.load('loiendpoint', 'v1', function() {
			console.log("Init : loaded");
			/*
			gapi.client.loiendpoint.insertLoi({id:"1515151515",name:"Loi sur le Code du travail", 
				description:"Révision de la loi sur le code du travail",nb_votes:"100",
				votes_p:["Cricri","Monceaux","Pascalou"],votes_c:["Tranber","Bechet","Manu"],votes_a:["Florian","Romain","Denis"]}).execute(
					function(resp) {
						console.log(resp);
					});		
			
			gapi.client.loiendpoint.listLoi().execute(
					function(resp) {
						console.log(resp);
					});	
			 */
			console.log("--------|LOIS|--------");
			$scope.lois = function() 
		    {
		      $http({
		        method: 'POST',
		        url: 'http://localhost:8888/ndns',
		        headers: {'Content-Type': 'application/json'},
		        data:  $scope.lois
		      }).success(function (data) 
		        {
		          $scope.status=data;
		        });
		    };
			
		    console.log($scope.lois);
		    console.log("---------------------");
		}, rootApi);
	}

	$scope.fctDeco= function(){
		console.log("Methode : fctDeco");
		var divToken = document.getElementById('varToken');
		varaccess_token = divToken.innerHTML;
		//alert(varaccess_token);
		$scope.disconnectUser(varaccess_token);
	};

	$scope.disconnectUser = function (access_token) {
		console.log("Methode : disconnectUser");
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

	$scope.majConnexion = function (name){	 
		console.log("Methode : majConnexion");
		$scope.connecte = !$scope.connecte;
		if($scope.connecte){
			$scope.accueil = false;
		}
		else {
			$scope.accueil = true;
			$scope.affichageVote = true;
			$scope.affichageFin = true;
			$scope.partieVote = true;			
		}
		$scope.UserName = name;
	}

	$scope.home = function (){	 
		console.log("Methode : accueil");
		if($scope.connecte){
			$scope.accueil = false;
			$scope.affichageVote = true;
			$scope.affichageFin = true;
			$scope.partieVote = true;
		}
	}

	$scope.voter = function (){	 
		console.log("Methode : voter");
		if($scope.connecte){
			$scope.accueil = true;
			$scope.partieVote = false;
			$scope.affichageVote = false;
		}
	}

	$scope.oui = function (){
		console.log("Methode : oui");
		$scope.questions[$scope.compteur].vote = "ok";
		$scope.compteur++;
		if($scope.compteur == 10){
			$scope.fin();
		}
	}

	$scope.non = function (){
		console.log("Methode : non");
		$scope.questions[$scope.compteur].vote = "ko";
		$scope.compteur++;
		if($scope.compteur == 10){
			$scope.fin();
		}
	}

	$scope.nspp = function (){
		console.log("Methode : nspp");
		$scope.questions[$scope.compteur].vote = "nspp";
		$scope.compteur++;
		if($scope.compteur == 10){
			$scope.fin();
		}
	}

	$scope.fin = function(){
		console.log("Methode : fin");
		$scope.compteur = 0;
		$scope.affichageVote = true;
		$scope.affichageFin = false;
	}

	$scope.tabTest = [];

	$scope.test= function(resp){
		console.log("Methode : test");
		$scope.tabTest = resp.items;
		console.log($scope.tabTest);
	};

	$scope.testAjout = function(resp){
		console.log("Methode : testAjout");
		$scope.tabTest.push(resp);
		console.log($scope.tabTest);
	};

}]);



