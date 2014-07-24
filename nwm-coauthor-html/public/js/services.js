var coAuthorServices = angular.module('coAuthorServices', [
    'ngResource'
]);

var host = getHost();

function getHost() {
    return window.location.protocol + '//' + window.location.host + '/nwm-coauthor-webapp';
}

coAuthorServices.factory('Schemas', [
        '$resource', function($resource) {
            return $resource(host + '/schemas/:type', {}, {
                getSchemaForCreate : {
                    method : 'GET',
                    params : {
                        type : 'new-story'
                    }
                }
            });
        }
]);

coAuthorServices.factory('Story', [
        '$resource', function($resource) {
            return $resource(host + '/story/:type', {}, {
                create : {
                    method : 'POST'
                },
                getTopViewStories : {
                    method : 'GET',
                    params : {
                        type : 'top-view-stories'
                    },
                    isArray : true
                }
            });
        }
]);