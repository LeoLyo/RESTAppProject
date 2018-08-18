var webRestApp = angular.module('webRestApp',['ngRoute','ngAnimate']);

webRestApp.run(function($rootScope){
  $rootScope.Singleton={
    YouUser:{
      username:"",
      password:"",
      uType:-1,
      activated:true
    }
  };
  console.log('run rootscope: '+'username: '+$rootScope.Singleton.YouUser.username+'|password: '+$rootScope.Singleton.YouUser.password+'|uType: '+$rootScope.Singleton.YouUser.uType);
});

webRestApp.config(['$routeProvider',function($routeProvider){

  $routeProvider
  .when('/home', {
    templateUrl: 'views/home.html',
    controller: 'HomeController'
  })
  .when('/login',{
    templateUrl: 'views/login.html',
    controller:'LoginController'
  })
  .when('/logout',{
    templateUrl: 'views/logout.html',
    controller: 'LogoutController'
  })
  .when('/register',{
    templateUrl: 'views/register.html',
    controller: 'RegisterController'
  })
  .when('/add_operator',{
    templateUrl: 'views/add_operator.html',
    controller: 'AddOperatorController'
  })
  .when('/verification_successful/:userForVerification',{
    templateUrl:'views/verification_successful.html',
    controller: 'VerificationSuccessfulController'
  })
  .when('/waiting_for_validation',{
    templateUrl: 'views/waiting_for_validation.html'
  }).when('/test',{
    templateUrl: 'views/test.html',
    controller: 'TestController'
  }).otherwise({
    redirectTo:'/home'
  });

}]);


webRestApp.factory('userConfig',['$http','$rootScope', function($http, $rootScope){
  var service = {};
  var urlBase = 'http://localhost:8080/RESTApp/rest';
  service.overrideCurrentUser = function(userDataObject){
    $rootScope.Singleton.YouUser=userDataObject;
    console.log('OVERRIDDEN: '+'username: '+$rootScope.Singleton.YouUser.username+'|password: '+$rootScope.Singleton.YouUser.password+'|uType: '+$rootScope.Singleton.YouUser.uType);
  };

  service.copyCurrentuser = function(){
    console.log('COPY CURRENT USER: '+'username: '+$rootScope.Singleton.YouUser.username+'|password: '+$rootScope.Singleton.YouUser.password+'|uType: '+$rootScope.Singleton.YouUser.uType);
    return $rootScope.Singleton.YouUser;
  };

  service.getCurrentUser = function(){
    return $http.get(urlBase+'/user');
  };

  service.verifyUser = function(){
    return $http.put(urlBase+'/verify');
  };

  service.register = function(rUser){
    return $http.post(urlBase+'/register',rUser);
  };

  service.login = function(lUser){
    return $http.post(urlBase+'/login',lUser);
  };
  service.logout = function(){
    return $http.get(urlBase+'/logout');
  }

/*Redirect paths*/

  service.getHomeRedirectPath = function(){
    return '#!/home.html';
  }

  service.getWaitingForValidationRedirectPath = function(){
    return '#!/waiting_for_validation.html';
  }

  return service;
}]);


webRestApp.run(function(){

});


webRestApp.controller('HeaderController',['$scope', '$http','$rootScope','userConfig',function($scope, $http, $rootScope, userConfig){
  $scope.currentuser={};

  $scope.$watch('$root.Singleton.YouUser',function(){
    $scope.currentuser=$rootScope.Singleton.YouUser;
    $scope.refreshHeader();
  });

 $scope.refreshHeader = function()
 {
   $scope.currentuser=userConfig.copyCurrentuser();
   console.log('HEADER REFRESH: '+'username: '+$scope.currentuser.username+'|password: '+$scope.currentuser.password+'|uType: '+$scope.currentuser.uType);

   /*
      if($scope.currentuser.username==""||$scope.currentuser.password==""||$scope.currentuser.uType==-1){
        userConfig.getCurrentUser().then(function(response){
          if()$scope.currentuser=response.data;
          userConfig.overrideCurrentUser($scope.currentuser);
        });
      }
      console.log("HEADER REINITIALIZED.");*/
  };

  var init = function(){
    $scope.currentuser=userConfig.copyCurrentuser();
    console.log('HEADER INIT: '+'username: '+$scope.currentuser.username+'|password: '+$scope.currentuser.password+'|uType: '+$scope.currentuser.uType);
  };
  init();
}]);


webRestApp.controller('HomeController',['$scope','$http','$rootScope','userConfig','$window', function($scope, $http, $rootScope, userConfig, $window){
  $scope.currentuser={};
  $scope.homeMessage="Please log in!";

  $scope.$watch('$root.Singleton.YouUser',function(){
    $scope.currentuser=$rootScope.Singleton.YouUser;
    $scope.refreshHomeMessage();
  });

  $scope.init = function(){
    if(!$rootScope.Singleton.YouUser.activated){

      $scope.notActivatedRedirect=userConfig.getWaitingForValidationRedirectPath();
      console.log('WAITING FOR VALIDATION REDIRECT PATH: '+$scope.notActivatedRedirect);
      $window.location.href = $scope.notActivatedRedirect;
      console.log('Waiting For Validation redirected successfully!');
    }
  }

  $scope.presentUser=function(){
    userConfig.getCurrentUser().then(function(response){
      $scope.currentuser=response.data;
      $scope.currentuser.username+=" ZZZ";
      console.log($scope.currentuser);
    });
  };

  $scope.testingMethod=function(){
    console.log($scope.currentuser);
  };



  $scope.refreshHomeMessage = function(){
    	$scope.currentuser=userConfig.copyCurrentuser();
      if($scope.currentuser.uType==-1){
        $scope.homeMessage="Please log in!";
      }else{
        $scope.homeMessage="Welcome, "+$scope.currentuser.username;
      }

      console.log("HOME REFRESH INIT: "+'username: '+$scope.currentuser.username+'|password: '+$scope.currentuser.password+'|uType: '+$scope.currentuser.uType);
  };

  $scope.init();

}]);


webRestApp.controller('LoginController',['$scope','$http','$rootScope','$window', 'userConfig',function($scope, $http, $rootScope, $window, userConfig){

  $scope.loginUser=function(){
    $scope.logintrialuser={
      username: $scope.probableuser.username,
      password: $scope.probableuser.password
    };
    $scope.probableuser.username="";
    $scope.probableuser.password="";

    var formatedUser=angular.toJson($scope.logintrialuser);
    console.log(formatedUser);
    userConfig.login(formatedUser).then(function(formatedUser,status){
      userConfig.getCurrentUser().then(function(response){
        userConfig.overrideCurrentUser(response.data);
        console.log('LOGGED IN USER RAW: '+angular.toJson(response.data));
        console.log('LOGGED IN USER: '+'username: '+$rootScope.Singleton.YouUser.username+'|password: '+$rootScope.Singleton.YouUser.password+'|uType: '+$rootScope.Singleton.YouUser.uType);
      });

      $scope.redirect=userConfig.getHomeRedirectPath();
      console.log('LOGIN REDIRECT PATH: '+$scope.redirect);
      $window.location.href = $scope.redirect;
      console.log('Login manifested successfully!');
    });
   };

   $scope.init = function(){
     if(!$rootScope.Singleton.YouUser.activated){

       $scope.notActivatedRedirect=userConfig.getWaitingForValidationRedirectPath();
       console.log('WAITING FOR VALIDATION REDIRECT PATH: '+$scope.notActivatedRedirect);
       $window.location.href = $scope.notActivatedRedirect;
       console.log('Waiting For Validation redirected successfully!');
     }
   }
   $scope.init();
}]);


webRestApp.controller('RegisterController',['$scope','$http', 'userConfig',function($scope,$http, userConfig){
  $scope.registerUser=function(){
    $scope.newuser={
      username: $scope.userbeingregistered.username,
      password: $scope.userbeingregistered.password,
      email: $scope.userbeingregistered.email,
      country: $scope.userbeingregistered.country
    };
    $scope.userbeingregistered.username="";
    $scope.userbeingregistered.password="";
    $scope.userbeingregistered.email="";
    $scope.userbeingregistered.country="";

    var formatedUser=angular.toJson($scope.newuser);
    userConfig.register(formatedUser).then(function(formatedUser,status){

      console.log('New user registered successfully!');
    });
  };
}]);

webRestApp.controller('LogoutController',['$scope','$http','$rootScope', 'userConfig',function($scope, $http, $rootScope, userConfig){
  $scope.userTemplate={
    username:"",
    password:"",
    uType:-1,
    activated:true
  };
  var init = function(){
    userConfig.logout().then(function(response){
      $rootScope.Singleton.YouUser=$scope.userTemplate;
      console.log('User successfully logged out!');
    });
  }
  init();
}]);

webRestApp.controller('AddOperatorController',['$scope',function($scope){
  $scope.username = "";
  $scope.password = "";
  $scope.addOperator=function(){

  };
}]);

webRestApp.controller('VerificationSuccessfulController',['$scope','$http','$rootScope','userConfig','$routeParams', function($scope,$http,$rootscope,userConfig, $routeParams){

  $scope.verifiedUser = $routeParams.userForVerification;
  console.log("VERIFIED USER: "+$scope.verifiedUser);


  /*
  $scope.verifiedUser = userConfig.copyCurrentuser();
  $scope.verifiedUser.activated=true;
  userConfig.overrideCurrentUser($scope.verifiedUser);
  userConfig.validateUser($scope.verifiedUser).then(function(verifiedUser,status){
    userconfig.getCurrentUser().then(function(response){
      userconfig.overrideCurrentUser(response.data);
      console.log('User activated successfully!');
    });
    console.log('Email activation successful!');
  });*/
}]);

webRestApp.controller('TestController',['$scope','$http','$rootScope','userConfig',function($scope, $http, $rootScope, userConfig){

  $scope.init = function(){
    if(!$rootScope.Singleton.YouUser.activated){

      $scope.notActivatedRedirect=userConfig.getWaitingForValidationRedirectPath();
      console.log('WAITING FOR VALIDATION REDIRECT PATH: '+$scope.notActivatedRedirect);
      $window.location.href = $scope.notActivatedRedirect;
      console.log('Waiting For Validation redirected successfully!');
    }
  }

  $scope.init();
}]);
