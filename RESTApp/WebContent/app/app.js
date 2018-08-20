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
  .when('/evolve', {
    templateUrl: 'views/evolve.html',
    controller: 'EvolveController'
  })
  .when('/verification_successful/:userForVerification',{
    templateUrl:'views/verification_successful.html',
    controller: 'VerificationSuccessfulController'
  })
  .when('/waiting_for_validation',{
    templateUrl: 'views/waiting_for_validation.html'
  })
  .when('/test',{
    templateUrl: 'views/test.html',
    controller: 'UpgradeTestingController'
  })
  .otherwise({
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

  service.verifyUser = function(vUser){
    return $http.put(urlBase+'/verify',vUser);
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
    return '#!/test.html';
  }

  service.getWaitingForValidationRedirectPath = function(){
    return '#!/waiting_for_validation.html';
  }

  return service;
}]);

//IMAGE UPLOAD DIRECTIVE

webRestApp.directive('fileInput',['$parse',function($parse){
  return{
    restrict:'A',
    link:function(scope,elm,attrs){
      elm.bind('change',function(){
        $parse(attrs.fileInput).assign(scope,elm[0].files)
        scope.$apply()
      })
    }
  }
}])


/*
webRestApp.directive('fileModel',['$parse','$scope',function($parse,$scope){
  return{
    restrict: 'A',
    link: function(scope,element, attrs){
      var model = $parse(attrs.fileModel);
      var modelSetter = model.assign;

      element.bind('change', function(){
        scope.$apply(function(){
          modelSetter(scope,element[0].files[0]);
        });
      });
      $scope.picture.file;
      console.log("BIUBI: "+$scope.picture.file);
    }
  }
}])

webRestApp.service('multipartForm',['$http', function($http){
  this.post=function(uploadUrl, data){
    var fd = new FormData();
    for(var key in data){
      fd.append(key,data[key]); //Take every element of picture and put it into the form as a key and then take the value of every element of picture and put it in as a value for that key.
      $http.post(uploadUrl, fd, {
        transformRequest: angular.identity,
        headers: { 'Content-Type': undefined }
      })
    }
  }
}])

*/
//CONTROLLERS

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


webRestApp.controller('RegisterController',['$scope','$http', 'userConfig','$window', function($scope,$http, userConfig, $window){
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

      //REDIRECTION NEEDS FIXING
      $scope.notActivatedRedirect=userConfig.getWaitingForValidationRedirectPath();
      console.log('WAITING FOR VALIDATION REDIRECT PATH: '+$scope.notActivatedRedirect);
      $window.location.href = $scope.notActivatedRedirect;
      console.log('Waiting For Validation redirected successfully!');
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




webRestApp.controller('EvolveController',['$scope','$http','$rootScope','userConfig','$window', function($scope, $http, $rootScope, userConfig, $window){
  
  $scope.imageMessage="BLAA";
  $scope.pictureArray=[];
  $scope.counter=0;
  console.log("RAKOMIR ALMIGHTY");

  $scope.evolveUser = function(){
    console.log("ENETERED ATTACHING EVOLUTION");
    angular.forEach($scope.files, function(file){

      var breader = new FileReader();
      var reader = new FileReader();

      $scope.counter++;

      reader.addEventListener("load",function(){

        var image = new Image();
        image.src=reader.result;
        breader.onload = function(){
          var arrayBuffer = breader.result;
          $scope.array = new Uint8Array(arrayBuffer);
          $scope.len = $scope.array.byteLength;
          $scope.binary='';
          for(var i=0;i<$scope.len;i++){
            $scope.binary+=String.fromCharCode($scope.array[i]);
          }

          //$scope.binaryString = String.fromCharCode.apply(null,$scope.array);
          //console.log("BYTE ARRAY: " + $scope.binaryString);
          console.log("NEW BYTE ARRAY: "+$scope.binary);
          $scope.imageBA=window.btoa($scope.binary);

        }
        image.onload=function(){
          console.log("ENTERED ONLOAD OF IMAGE");
          console.log("AOA: " + file.name + " | " + image.height + " | " + image.width);
        }
      },false);
      if(file){
        reader.readAsDataURL(file);
        breader.readAsArrayBuffer(file);
      }
    })
  };
}]);





webRestApp.controller('VerificationSuccessfulController',['$scope','$http','$rootScope','userConfig','$routeParams', function($scope,$http,$rootscope,userConfig, $routeParams){
  $scope.verifiedUser={
    username:$routeParams.userForVerification,
    password:"temp",
    uType:"-1",
  };
  $scope.title="ERROR VERIFYING ACCOUNT";
  $scope.message="Random error occurred..."
  var formatedUser=angular.toJson($scope.verifiedUser);
  userConfig.verifyUser(formatedUser).then(function(usernameOfVerifiedUser, status){
    console.log("User "+$scope.verifiedUser.username + " verified successfully!")
    $scope.title="ACCOUNT SUCCESSFULLY VERIFIED";
    $scope.message="Thank you for verifying your account: "+$scope.verifiedUser.username+"! You are now eligible to use the website functions that have been prepared for people like you. Enjoy! ^_^";
  //console.log("YOYO "+ usernameOfVerifiedUser+" | "+$scope.verifiedUser.username);
  });


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

webRestApp.controller('UpgradeTestingController',['$scope','$http','$rootScope','userConfig','$window', function($scope, $http, $rootScope, userConfig, $window){
  console.log("LOVE IS LIFE");
}]);

webRestApp.controller('TestController',['$scope','$http','$rootScope','userConfig','$window', function($scope, $http, $rootScope, userConfig, $window){
  $scope.pictureArray=[];
  $scope.counter=0;
  console.log("RAKOMIR ALMIGHTY");

  $scope.upload = function(){
    console.log("ENETERED UPLOAD");
    var reader = new FileReader();
    angular.forEach($scope.files, function(file){
      if(file){
        reader.readAsDataURL(file);
      }
      reader.addEventListener("load",function(){
        var image = new Image();
        image.src=reader.result;
        console.log("NOTHING: " + image.src + " | " + image.height + " | " + image.width);
      },false);

    })
  };

/*
$scope.pictureArray=[];
$scope.openFile = function(file){
    $scope.input = file.target;
    var reader = new FileReader();
    console.log("ENETERED OPENFILE");
    reader.onload = function(){
      var dataURL=reader.result;
      console.log("DATAUR: "+dataURL);
      var image = new Image();
      image.src=dataURL;
      image.onload=function(){
        console.log("HEIGHT: "+image.height+" | WIDTH: "+image.width);
      }
    };
    reader.readAsDataURL(input.files[0]);
    console.log("READ AS DATA URL: " + reader.result);
}

$scope.upload = function(){

}

*/

  $scope.init = function(){
    console.log("TEST INIT");
    if(!$rootScope.Singleton.YouUser.activated){
      $scope.notActivatedRedirect=userConfig.getWaitingForValidationRedirectPath();
      console.log('WAITING FOR VALIDATION REDIRECT PATH: '+$scope.notActivatedRedirect);
      $window.location.href = $scope.notActivatedRedirect;
      console.log('Waiting For Validation redirected successfully!');
    }
  }

  $scope.init();
}]);
