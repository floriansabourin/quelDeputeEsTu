var app = angular.module('plunker', []);

app.controller('MainCtrl', function($scope) {
  
   $scope.affichageQuiz = true;
   $scope.affichageFin = true;
   
  $scope.connexion = function (){
    
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
      {id:"999", ordre:"10",vote: "",question:"How tall are you?"},
      
    ];
    
    $scope.compteur = 1;
    $scope.connecte = true;
    $scope.affichageQuiz = false;
  }
  
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
    $scope.affichageQuiz = true;
    $scope.affichageFin = false;
  }
  
});
