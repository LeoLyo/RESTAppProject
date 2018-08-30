var webRestApp = angular.module('webRestApp',['ngRoute','ngAnimate']);

webRestApp.run(function($rootScope){
  $rootScope.Singleton={
    YouUser:{
      username:"",
      password:"",
      uType:-1,
      activated:true
    },
    Testing:{
      usersWaitingForGrading:[],
      usersFailedAtEvolving:[],
      usersWaitingForEvolving:[],
      usersSucceededAtEvolving:[]
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
  .when('/upload_successful',{
    templateUrl: 'views/upload_successful.html'
  })
  .when('/change_password',{
    templateUrl: 'views/change_password.html',
    controller: 'ChangePasswordController'
  })
  .when('/test',{
    templateUrl: 'views/test.html',
    controller: 'UpgradeTestingController'
  })
  .when('/password_changed_successfully',{
    templateUrl: 'views/password_changed_successfully.html'
  })
  .when('/operator_created_successfully',{
    templateUrl: 'views/operator_created_successfully.html'
  })
  .when('/operator_mode',{
    templateUrl: 'views/operator_mode.html',
    controller: 'OperatorModeController'
  })
  .when('/who_to_test',{
    templateUrl: 'views/who_to_test.html',
    controller: 'WhoToTestController'
  })
  .when('/grade_test/:peasant_grading_in_progress',{
    templateUrl: 'views/grade_test.html',
    controller: 'GradeTestController'
  })
  .when('/whose_pics_to_approve',{
    templateUrl: 'views/whose_pics_to_approve.html',
    controller: 'WhosePicsToApproveController'
  })
  .when('/grading_completed',{
    templateUrl: 'views/grading_completed.html'
  })
  .when('/failed_to_evolve',{
    templateUrl: 'views/failed_to_evolve.html'
  })
  .when('/waiting_to_evolve',{
    templateUrl: 'views/waiting_to_evolve.html',
    controller: 'WaitingToEvolveController'
  })
  .when('/succeeded_at_evolving',{
    templateUrl: 'views/succeeded_at_evolving.html'
  })
  .when('/merchant_corner',{
    templateUrl: 'views/merchant_corner.html',
    controller: 'MerchantCornerController'
  })
  .when('/place_item_on_auction',{
    templateUrl: 'views/place_item_on_auction.html',
    controller: 'PlaceItemOnAuctionController'
  })
  .when('/your_auction_page',{
    templateUrl: 'views/your_auction_page.html',
    controller: 'YourAuctionPageController'
  })
  .when('/insert_credit_card',{
    templateUrl: 'views/insert_credit_card.html',
    controller: 'InsertCreditCardController'
  })
  .when('/block_users',{
    templateUrl: 'views/block_users.html',
    controller: 'BlockUsersController'
  })
  .when('/blocked',{
    templateUrl: 'views/blocked.html'
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

  service.checkUsername = function(){
    return $http.get(urlBase+'/check-username');
  }

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
  };

    service.addTestPicture = function(atpUser){
    return $http.put(urlBase+'/add-test-picture', atpUser);
  };

  service.registerOperator = function(roUser){
    return $http.post(urlBase+'/add-operator', roUser);
  };

  service.changePassword = function(cpUser){
    return $http.put(urlBase+'/change-password', cpUser);
  };

  service.operatorFirstTimeChange = function(oftcUser){
    return $http.put(urlBase+'/operator-first-time-change', oftcUser);
  };

  service.findUser = function(fUser){
    return $http.put(urlBase+'/find-user', fUser);
  };

  service.evolveUser = function(eUser){
    return $http.put(urlBase+'/evolve', eUser);
  };

  service.addCard = function(acUser){
    return $http.post(urlBase+'/add-card', acUser);
  };

  service.getAllUsers = function(){
    return $http.get(urlBase+'/users');
  };
  service.getAllOUsers = function(){
    return $http.get(urlBase+'/ousers');
  };
  service.blockUser = function(bUser){
    return $http.put(urlBase+'/block',bUser);
  };
  service.unblockUser = function(uUser){
    return $http.put(urlBase+'/unblock',uUser);
  };
    return service;
}]);

//IMAGE UPLOAD DIRECTIVE

webRestApp.directive('fileInput',['$parse',function($parse){
  return{
    restrict:'A',
    link:function(scope,elm,attrs){
      console.log("Directive link function");
      var model = $parse(attrs.fileInput);
      var modelSetter = model.assign;

      elm.bind('change',function(){
        console.log("Directive element bind");
        modelSetter(scope,elm[0].files);
        //$parse(attrs.fileInput).assign(scope,elm[0].files)
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
  $scope.userT="";
  $scope.$watch('$root.Singleton.YouUser',function(){
    $scope.currentuser=$rootScope.Singleton.YouUser;
    $scope.refreshHeader();
  });

 $scope.refreshHeader = function(){
   $scope.currentuser=userConfig.copyCurrentuser();
   if($scope.currentuser.uType == 0){
     $scope.userT = " | " + ".Basic User.";
   }
   else if($scope.currentuser.uType == 1){
     $scope.userT = " | " + "!Seller!";
   }
   else if($scope.currentuser.uType == 2){
     $scope.userT = " | " + ":O Operator O:"
   }
   else if($scope.currentuser.uType == 3){
     $scope.userT = " | " + "_/¯( ? )_/¯ Admin _/¯( ? )_/¯";
   }
   else if($scope.currentuser.uType == 4){
     $scope.userT = " | " + "<<< Firm >>>"
   }
   else{
     $scope.userT = "";
   }
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


webRestApp.controller('HomeController',['$scope','$http','$rootScope','userConfig','$location', function($scope, $http, $rootScope, userConfig, $location){
  $scope.currentuser={};
  $scope.homeMessage="Please log in!";

  $scope.$watch('$root.Singleton.YouUser',function(){
    $scope.currentuser=$rootScope.Singleton.YouUser;
    $scope.refreshHomeMessage();
  });

  $scope.init = function(){
    if($rootScope.Singleton.YouUser.activated==false){
        $location.path('/waiting_for_validation');
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

    console.log("TESTING METHOD: " + $rootScope.Singleton.YouUser.isBlocked);
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


webRestApp.controller('LoginController',['$scope','$http','$rootScope','$location', 'userConfig',function($scope, $http, $rootScope, $location, userConfig){

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
        if($rootScope.Singleton.YouUser.uType==2 && $rootScope.Singleton.YouUser.firstTime==true){
          $location.path('/change_password');
          console.log('REDIRECT TO CHANGE PASSWORD SUCCEEDED!');
        } else if($rootScope.Singleton.YouUser.uType==0 && $rootScope.Singleton.YouUser.activated==false){
          $location.path('/waiting_for_validation');
          console.log('REDIRECT TO WAITING SUCCEEDED!');
        }
        else if(($rootScope.Singleton.YouUser.uType==0 || $rootScope.Singleton.YouUser.uType==1) && $rootScope.Singleton.YouUser.blocked==true){
          $location.path('/blocked');
          console.log('REDIRECT TO BLOCKED SUCCEDED!');
        }
        else{
          $location.path('/home');
        console.log('REDIRECT TO LOGIN SUCCEDED!');
        }
      });


    });
   };

   $scope.init = function(){
     if($rootScope.Singleton.YouUser.activated==false){
         $location.path('/waiting_for_validation');
       console.log('Waiting For Validation redirected successfully!');
     }
   }

   $scope.init();
}]);


webRestApp.controller('RegisterController',['$scope','$http', 'userConfig','$location', function($scope,$http, userConfig, $location){
  $scope.usernameExists=false;
  $scope.errorMessage="";
  $scope.registerUser=function(){
    $scope.usernameExists=false;
    $scope.errorMessage="";
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
/*
  userConfig.checkUsername(formatedUser).then(function(formatedUser,status){
    console.log("ASDSAD: " + status.data);
    console.log("GFDGDFS: " + status);
  });*/

    var formatedUser=angular.toJson($scope.newuser);
    userConfig.register(formatedUser).then(function(formatedUser,status){
      console.log('New user registered successfully!');
      $location.path('/waiting_for_validation');
      console.log('Waiting For Validation redirected successfully!');
    }, function(response){
      $scope.errorMessage=response.data;
      $scope.usernameExists=true;
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

webRestApp.controller('AddOperatorController',['$scope','$http','$rootScope','userConfig','$location', function($scope, $http, $rootScope, userConfig, $location){
  $scope.opusername = "";
  $scope.oppassword = "";
  $scope.addOperator=function(){
    $scope.newOperator = {
      username: $scope.opusername,
      password: $scope.oppassword
    };
    var formatedUser=angular.toJson($scope.newOperator);
    console.log("Operator check: " + formatedUser);
    userConfig.registerOperator(formatedUser).then(function(formatedUser,status){
      console.log('New operator registered successfully!');
      $location.path('/operator_created_successfully');
      console.log('Login redirected successfully!');
    });
  };
}]);

webRestApp.controller('ChangePasswordController',['$scope','$http','$rootScope','userConfig','$location', function($scope, $http, $rootScope, userConfig, $location){
  $scope.cpfirst = "";
  $scope.cpsecond = "";
  $scope.changeMessage = "Passwords must match.";
  $scope.showChangeMessage = false;
  $scope.changePassword = function(){
    if($scope.cpfirst!=$scope.cpsecond){
      $scope.showChangeMessage = true;
    }else{
      $scope.showChangeMessage = false;
      $scope.changedOperator = {
        username: $rootScope.Singleton.YouUser.username,
        password: $scope.cpfirst
      };
      var formatedUser = angular.toJson($scope.changedOperator);
      userConfig.changePassword(formatedUser).then(function(fr, status){
        console.log("Password changed successfully.");
        if($rootScope.Singleton.YouUser.uType==2 && $rootScope.Singleton.YouUser.firstTime==true){
          userConfig.operatorFirstTimeChange(formatedUser).then(function(newfr,newstatus){
            console.log("Operator's first time changed successfully.");
            $rootScope.Singleton.YouUser.firstTime=false;
          });
        };
        $location.path('/password_changed_successfully');
        console.log("Password changed successfully redirected, wellp, successfully. _/¯( ? )_/¯");
      });
    };
  }
}]);



webRestApp.controller('EvolveController',['$scope','$http','$rootScope','userConfig','$location', function($scope, $http, $rootScope, userConfig, $location){
  $scope.rakometer=false;
  //$scope.Test=[];
  //$scope.count=0;
  $scope.imageMessage="Number of current images for grading: " + $rootScope.Singleton.YouUser.test.length;

  console.log("RAKOMIR ALMIGHTY");

  $scope.evolveUser = function(){
    console.log("ENETERED ATTACHING EVOLUTION");
    angular.forEach($scope.files, function(file){


      var breader = new FileReader();
      var reader = new FileReader();



      reader.addEventListener("load",function(){

        /*breader.onload = function(){
          var arrayBuffer = breader.result;
          $scope.array = new Uint8Array(arrayBuffer);
          $scope.len = $scope.array.byteLength;
          $scope.binary='';
          for(var i=0;i<$scope.len;i++){
            $scope.binary+=String.fromCharCode($scope.array[i]);
          }

          //$scope.binaryString = String.fromCharCode.apply(null,$scope.array);
          //console.log("BYTE ARRAY: " + $scope.binaryString);
          //console.log("NEW BYTE ARRAY: "+$scope.binary);
          $scope.tempImage.byteArray=$scope.binary;
          console.log("INSIDE BYTE ARRAY ONLOAD");
        }*/
        var image = new Image();
        image.src=reader.result;
        image.onload=function(){

          /*$scope.count++;*/

          $scope.tempImage={
            name: "",
            byteArray: "",
            resolution: ""
          };
          //console.log("NEW RAK: " + image.src);
          console.log("ENTERED ONLOAD OF IMAGE");
          //console.log("AOA: " + file.name + " | " + image.width + " | " + image.height);
          $scope.tempImage.name = file.name;
          $scope.tempImage.resolution = image.width+"x"+image.height;
          $scope.tempImage.byteArray = image.src;


        $scope.formatTest = angular.toJson($rootScope.Singleton.YouUser.test);
        //console.log("RAKSON: " + $scope.formatTest);
        //console.log("RAK IS ALWAYS WITH US: " +   $scope.Test[$scope.count-1]);
        //console.log("QUAAA: "+$scope.Test[$scope.count-1].name+" | "+$scope.Test[$scope.count-1].resolution+" | "+"imagine a byte array");

        $rootScope.Singleton.YouUser.test.push({
        name: $scope.tempImage.name,
        resolution: $scope.tempImage.resolution,
        byteArray: $scope.tempImage.byteArray,
        grade: 0
      });
      console.log("NOW TEST COUNT: "+$rootScope.Singleton.YouUser.test.length);


      //console.log("TESTING ROOTTEST: " + $rootScope.Singleton.Test.length);

        }

        /*$scope.imageBA=window.btoa($scope.pictureArray[$scope.count-1].byteArray);
          $scope.Test.push({
          name: $scope.tempImage.name,
          resolution: $scope.tempImage.resolution,
          byteArray: $scope.tempImage.byteArray
        });
        $scope.formatTest = angular.toJson($scope.Test);
        console.log("RAKSON: " + $scope.formatTest);
        console.log("RAK IS ALWAYS WITH US: " +   $scope.Test[$scope.count-1]);
        console.log("QUAAA: "+$scope.Test[$scope.count-1].name+" | "+$scope.Test[$scope.count-1].resolution+" | "+"imagine a byte array");*/

      },false);

      if(file){
        reader.readAsDataURL(file);
        breader.readAsArrayBuffer(file);
      }

    })

    //$state.reload();
    //$scope.$apply();
    //console.log(">>>>>>>>>" + $scope.count + " >>>>>>>>> " + $rootScope.Singleton.Test.length);
    $scope.rakometer=true;
  };


  $scope.removeImage = function(image){
    var indexRemovedImage = $rootScope.Singleton.YouUser.test.indexOf(image);
    $rootScope.Singleton.YouUser.test.splice(indexRemovedImage,1);
    $scope.imageMessage="Number of current images for grading: " + $rootScope.Singleton.YouUser.test.length;
  }
  $scope.clearList = function(){
    $rootScope.Singleton.YouUser.test=[];
    $scope.imageMessage="Number of current images for grading: " + $rootScope.Singleton.YouUser.test.length;

  }
  $scope.refreshRak = function(){
    if($rootScope.Singleton.YouUser.test.length>10){
      $rootScope.Singleton.YouUser.test.length=10;
    }
    console.log("TESTING ROOT ON REFRESH: " + $rootScope.Singleton.YouUser.test.length);
    $scope.imageMessage="Number of current images for grading: " + $rootScope.Singleton.YouUser.test.length;
    $scope.rakometer=false;
  }
  $scope.uploadTest = function(){

    for(var i=0;i<10;i++){

      $scope.tempPicUser={
        username: $rootScope.Singleton.YouUser.username,
        test: []
      };
      console.log("No. "+i+" and "+$scope.tempPicUser.test.length);

      $scope.tempPicUser.test.push({
        name:  $rootScope.Singleton.YouUser.test[i].name,
        resolution: $rootScope.Singleton.YouUser.test[i].resolution,
        byteArray: $rootScope.Singleton.YouUser.test[i].byteArray,
        grade: 0
      });
      var formatedUser = angular.toJson($scope.tempPicUser);
      console.log("Evolve fu no. " + i + ": " + formatedUser);
      userConfig.addTestPicture(formatedUser).then(function(response, status){
        console.log("Picture no. " + i + " uploaded.");
      })

    }
    $rootScope.Singleton.Testing.usersWaitingForGrading.push({
      username: $rootScope.Singleton.YouUser.username
    });
    console.log("Users waiting for grading: " + $rootScope.Singleton.Testing.usersWaitingForGrading.length + "QUQU: " + $rootScope.Singleton.Testing.usersWaitingForGrading[0].username + " | " + $rootScope.Singleton.Testing.usersWaitingForGrading.length);
    $location.path('/upload_successful');
    console.log('Upload successful redirected successfully!');

    /*
    var formatedUser=angular.toJson($rootScope.Singleton.YouUser);
    console.log("Evolve fu: " + formatedUser);
    userConfig.startTest(formatedUser).then(function(response, status){
      console.log("Evolution commencing...");
      $rootScope.Singleton.Testing.usersWaitingForGrading.push({
        name: $rootScope.Singleton.YouUser.username
      });
      console.log("Users waiting for grading: " + $rootScope.Singleton.Testing.usersWaitingForGrading.length + "QUQU: " + $rootScope.Singleton.Testing.usersWaitingForGrading[0]);
      $location.path('/upload_successful');
      console.log('Upload successful redirected successfully!')
    })*/
  }
  $scope.init = function(){

    if($rootScope.Singleton.YouUser.activated==false){
        $location.path('/waiting_for_validation');
      console.log('Waiting For Validation redirected successfully!');
    }
    $scope.person = $rootScope.Singleton.YouUser.username;

    //Waiting for grading
    $scope.exists = false;
    for(var i=0;i<$rootScope.Singleton.Testing.usersWaitingForGrading.length;i++){
      if($scope.person==$rootScope.Singleton.Testing.usersWaitingForGrading[i].username){
        $scope.exists = true;
        break;
      }
    }
    if($scope.exists==true){
      $location.path('/upload_successful');
      console.log('Upload successful redirected successfully!');
    }

    //Failed at Evolving
    $scope.fExists = false;
    for(var i=0;i<$rootScope.Singleton.Testing.usersFailedAtEvolving.length;i++){
      if($scope.person==$rootScope.Singleton.Testing.usersFailedAtEvolving[i].username){
        $scope.fExists = true;
        break;
      }
    }
    if($scope.fExists==true){
      $location.path('/failed_to_evolve');
      console.log('Failed at evolving (failed to evolve) redirected successfully!');
    }

    //Waiting to EVOLVE
    $scope.wExists = false;
    for(var i=0;i<$rootScope.Singleton.Testing.usersWaitingForEvolving.length;i++){
      if($scope.person==$rootScope.Singleton.Testing.usersWaitingForEvolving[i].username){
        $scope.wExists = true;
        break;
      }
    }
    if($scope.wExists==true){
      $location.path('/waiting_to_evolve');
      console.log('Waiting to evolve redirected successfully!');
    }

    //Succeeded at Evolving
    $scope.sExists = false;
    for(var i=0;i<$rootScope.Singleton.Testing.usersSucceededAtEvolving.length;i++){
      if($scope.person==$rootScope.Singleton.Testing.usersSucceededAtEvolving[i].username){
        $scope.sExists = true;
        break;
      }
    }
    if($scope.sExists==true){
      $location.path('/succeeded_at_evolving');
      console.log('Succeeded at evolving redirected successfully!');
    }

  }

  $scope.init();

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


/*THE NEW AGE HAS COME*/

webRestApp.controller('OperatorModeController', ['$scope','$location', function($scope, $location){

  $scope.whoToTest = function(){
    $location.path('/who_to_test');
  }

  $scope.whosePicsToApprove = function(){
    $location.path('/whose_pics_to_approve');
  }

  $scope.blockUsers = function(){
    $location.path('/block_users');
  }
}]);

webRestApp.controller('WhoToTestController', ['$scope','$rootScope','$location', function($scope, $rootScope, $location){

  $scope.gradeSelectedPeasant = function(peasant){
    var path = "/grade_test/"+peasant.username;
    $location.path(path);
  }
}]);

webRestApp.controller('WhosePicsToApproveController', ['$scope','$http','$rootScope','userConfig','$location', function($scope, $http, $rootScope, userConfig, $location){

}]);

webRestApp.controller('GradeTestController', ['$scope','$http','$rootScope','userConfig','$location', '$routeParams', function($scope, $http, $rootScope, userConfig, $location, $routeParams){
  $scope.tempPeasant = {
    username: $routeParams.peasant_grading_in_progress,
    password: "temp",
    uType: "0"
  };
  console.log("Peasant: " + $scope.tempPeasant.username);
  $scope.peasantUser="";
  $scope.result = 0;
  var formatedUser = angular.toJson($scope.tempPeasant);
  console.log("JSON PEASANT: " + formatedUser);
  userConfig.findUser(formatedUser).then(function(response, satus){
    console.log("Passed.");
    $scope.peasantUser = response.data;
    console.log("Please Lord: " + $scope.peasantUser.username + " | " + $scope.peasantUser.test.length);
  });
  Array.sum = function(key){
    var total = 0;
    for(var i=0;i<this.length;i++){
      total += this[i][key]
    }
    return total
  }
  $scope.refreshTotalGrade = function(){
    $scope.result = 0;
    for(var i=0;i<$scope.peasantUser.test.length;i++){
      $scope.result += parseFloat($scope.peasantUser.test[i].grade);
    }
    $scope.result = $scope.result / $scope.peasantUser.test.length;
    console.log("Grade Totale: " + $scope.result);
  }

  $scope.finalizeGrading = function(image){
    $scope.exchangingUser = $rootScope.Singleton.Testing.usersWaitingForGrading.find(x => x.username==$routeParams.peasant_grading_in_progress);
    console.log("EXCHANGING USER: " + $scope.exchangingUser.username+ " | " + $scope.exchangingUser);
    $rootScope.Singleton.Testing.usersWaitingForGrading.splice($scope.exchangingUser ,1);
    console.log("EXCHANGING USER LATER: " + $scope.exchangingUser.username+ " | " + $scope.exchangingUser);
    if($scope.result>4){
      $rootScope.Singleton.Testing.usersWaitingForEvolving.push({
        username: $scope.exchangingUser.username
      });
      console.log("NEW EXCHANGED: " + $rootScope.Singleton.Testing.usersWaitingForEvolving.length + " AND NOW OBJECT " + $rootScope.Singleton.Testing.usersWaitingForEvolving[$rootScope.Singleton.Testing.usersWaitingForEvolving.length-1] + " AND NOW ELEMENT " + $rootScope.Singleton.Testing.usersWaitingForEvolving[$rootScope.Singleton.Testing.usersWaitingForEvolving.length-1].username);
      console.log("SIZE OLD: " +  $rootScope.Singleton.Testing.usersWaitingForGrading.length + "SIZE EVOLVED: " + $rootScope.Singleton.Testing.usersWaitingForEvolving.length);

    }
    else{
      $rootScope.Singleton.Testing.usersFailedAtEvolving.push({
        username: $scope.exchangingUser.username
      });
      console.log("SIZE OLD: " +  $rootScope.Singleton.Testing.usersWaitingForGrading.length + "SIZE FAILED: " + $rootScope.Singleton.Testing.usersFailedAtEvolving.length);

    }
    $location.path('/grading_completed');
  }
}]);

webRestApp.controller('WaitingToEvolveController', ['$scope','$http','$rootScope','userConfig','$location', '$routeParams', function($scope, $http, $rootScope, userConfig, $location, $routeParams){
  $scope.evolveFromPeasantToMerchant = function(){
    $scope.evolvingUser = $rootScope.Singleton.Testing.usersWaitingForEvolving.find(x => x.username==$rootScope.Singleton.YouUser.username);
    console.log("USERNAME: " + $scope.evolvingUser.username);
    $rootScope.Singleton.Testing.usersWaitingForEvolving.splice($scope.evolvingUser,1);
    $rootScope.Singleton.Testing.usersSucceededAtEvolving.push({
      username: $scope.evolvingUser.username
    });
    $scope.unformatedUser={
      username:$scope.evolvingUser.username,
      password:"temp",
      uType:"-1",
    };
    var formatedUser = angular.toJson($scope.unformatedUser);
    console.log("WTEC JSON USER: " + formatedUser);
    userConfig.evolveUser(formatedUser).then(function(response, status){
      userConfig.overrideCurrentUser(response.data);
      var formatedYou = angular.toJson($rootScope.Singleton.YouUser);
      console.log("!: " + formatedYou);
      $location.path('/home');
    });
  }
}]);

webRestApp.controller('MerchantCornerController',['$scope','$location', function($scope, $location){
  $scope.placeItemOnAuction = function(){
    $location.path('/place_item_on_auction');
  }
  $scope.yourAuctionPage = function(){
    $location.path('/your_auction_page');
  }
}]);

webRestApp.controller('PlaceItemOnAuctionController',['$scope','$http','$rootScope','userConfig','$location', '$routeParams', function($scope, $http, $rootScope, userConfig, $location, $routeParams){
  if($rootScope.Singleton.YouUser.cards === undefined || $rootScope.Singleton.YouUser.cards == 0){
    $location.path('/insert_credit_card');
  }else{

  }
}]);

webRestApp.controller('YourAuctionPageController',['$scope','$http','$rootScope','userConfig','$location', '$routeParams', function($scope, $http, $rootScope, userConfig, $location, $routeParams){

}]);

webRestApp.controller('InsertCreditCardController',['$scope','$http','$rootScope','userConfig','$location', '$routeParams', function($scope, $http, $rootScope, userConfig, $location, $routeParams){
  $scope.creditCardNumber="";
  $scope.creditCardPin="";
  $scope.addCard = function(){
    $scope.tempUser = {
      username: $rootScope.Singleton.YouUser.username,
      password: $rootScope.Singleton.YouUser.password,
      uType: $rootScope.Singleton.YouUser.uType,
      cards: []
    };
    $scope.tempUser.cards.push({
      number: $scope.creditCardNumber,
      pin: $scope.creditCardPin
    });
    var formatedUser = angular.toJson($scope.tempUser);
    userConfig.addCard(formatedUser).then(function(response, status){
      userConfig.overrideCurrentUser(response.data);
      var formatedCards = angular.toJson($rootScope.Singleton.YouUser.cards);
      console.log("Deck check: " + formatedCards);
      if($rootScope.Singleton.YouUser.cards.length==1){
        $location.path('/place_item_on_auction');
      }else{
        $location.path('/merchant_corner');
      }
    });
  }
}]);

webRestApp.controller('BlockUsersController',['$scope','$http','$rootScope','userConfig','$location', '$routeParams', function($scope, $http, $rootScope, userConfig, $location, $routeParams){

  $scope.users = "";

  $scope.init = function(){
    console.log("UPDATED QUACKS");
    userConfig.getAllOUsers().then(function(response){
      $scope.users = response.data;
      console.log("QUAXCK: " + $scope.users);
      console.log("NEXT: " + $scope.users[0].username);
    });
  }


  $scope.type = function(user){
    if(user.uType == 0){
      return "Peasant";
    }
    else if(user.uType == 1){
      return "Merchant";
    }
    else{
      return "Void";
    }
  };

  $scope.whatToDo = function(user){
    if(user.blocked==true){
      return "block";
    }
    else{
      return "unblock";
    }
  };

  $scope.blockUser = function(user){
    $scope.tempUser = {
      username: user.username,
      password: "temp",
      uType: user.uType
    };
    var formatedUser = angular.toJson($scope.tempUser);
    userConfig.blockUser(formatedUser).then(function(respone,status){
      user.blocked = true;
      console.log("[B] Current user block status: " + user.username + ", " + user.blocked);
    });
  }
  $scope.unblockUser = function(user){
    $scope.tempUser = {
      username: user.username,
      password: "temp",
      uType: user.uType
    };
    var formatedUser = angular.toJson($scope.tempUser);
    userConfig.unblockUser(formatedUser).then(function(respone,status){
      user.blocked = false;
      console.log("[UB] Current user block status: " + user.username + ", " + user.blocked);
    });
  }

$scope.init();

}]);

webRestApp.controller('TemplateController',['$scope','$http','$rootScope','userConfig','$location', '$routeParams', function($scope, $http, $rootScope, userConfig, $location, $routeParams){
	$scope.template=1;
}]);
