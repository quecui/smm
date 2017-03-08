angular.module('services').factory('httpRequestInterceptor', function ($rootScope, $q, $timeout, $cookies, $location) {
    return {
        request: function (requestConfig) {
            $rootScope.showToaster = true;
            if ($rootScope.authToken) {
                requestConfig.headers['Authorization'] = $rootScope.authToken;
                requestConfig.headers['PageToken'] = $rootScope.pageAccessToken;
                requestConfig.headers['Cache-Control'] = 'private, no-cache, no-store, must-revalidate';
                requestConfig.headers['Expires'] = '-1';
                requestConfig.headers['Pragma'] = 'no-cache';
            }
            return requestConfig;
        },
        response: function (response) {
            $rootScope.isShowOverlay = false;
            return response;
        },
        responseError: function (response) {
            $rootScope.isShowOverlay = false;
            var BreakException = {};
            try {
                if (response.status == 401 && response.statusText == 'Unauthorized') {
                    $rootScope.logOut();
                }
            } catch (e) {
                if (e !== BreakException) throw e;
            }
            return $q.reject(response);
        }
    };
});