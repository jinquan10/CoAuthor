var coAuthorControllers = angular.module('coAuthorControllers', []);

coAuthorControllers.controller('mainController', [
        '$scope', '$routeParams', '$http', function($scope, $routeParams, $http) {
            $scope.hi = 'hi';
        }
]);