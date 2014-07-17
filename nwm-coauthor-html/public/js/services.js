var coAuthorServices = angular.module('coAuthorServices', [ 'ngResource' ]);

var host = getHost();

function getHost(){
    return window.location.protocol + '//' + window.location.host + '/nwm-coauthor-webapp';
}

coAuthorServices.factory('Story', [ '$resource', function($resource) {
    
    
	return $resource(host + '/stories:path', {}, {
		schemaNew : {
			method : 'GET',
			params: {path: '/schema/new'}
		}
	});
} ]);