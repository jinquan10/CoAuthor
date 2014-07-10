var coAuthorApp = angular.module('coAuthorApp', [
        'ngRoute', 'coAuthorControllers'
]);

coAuthorApp.config([
        '$routeProvider', function($routeProvider) {
            $routeProvider.when('/main', {
                templateUrl : 'partials/main.html',
                controller : 'mainController'
            }).otherwise({
                redirectTo : '/main'
            });
        }
]);