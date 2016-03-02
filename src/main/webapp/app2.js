var app = angular.module('myApp', [ 'ngGrid' ])

.constant('nameColumnSize', 100);

app
		.controller(
				'MyCtrl',
				function($scope, $filter, $q, $http, $timeout) {

					$scope.show = false;
					
					$scope.homeRoster = [];
					$scope.awayRoster = [];

					

					
					// Gets the initial data for the form.
					$scope.getDropDownDataFromServer = function() {

						var deferred = $q.defer();
						var getTeams = $http({
							method : 'GET',
							url : 'rest/teams/0',
							cache : 'false'
						});

						// anything you want can go here and will safely be run
						// on the next digest.

						$q.all([ getTeams ]).then(function(results) {
							// deferred.resolve(console.log(results[0].data,
							// results[1].data));

							console.log(results[0].data);
							$scope.activeHomeTeam = results[0].data;
							$scope.activeAwayTeam = results[0].data;
							$scope.show = true;

						}, function(httperror) {
							deferred.resolve(console.log('some error'));
						});
					};

					$scope.loadRoster = function(isHome, teamNumber) {
						

						$http(
								{
									method : 'GET',
									url :  'rest/roster/' + teamNumber + '/' + isHome											+ '&isHome=' + isHome,
								}).success(
								function(data, status, headers, config) {

									if (isHome) {
										$scope.homeRoster = data;
									} else {
										$scope.awayRoster = data;
									}


								}).error(
								function(data, status, headers, config) {
									// called asynchronously if an error occurs
									// show
									// here
								});
					};

					$scope.loadAwayRoster = function() {
						
						$scope.loadRoster(false,$scope.selectedAwayTeam);

					};

					$scope.loadHomeRoster = function() {
						$scope.loadRoster(true,$scope.selectedHomeTeam);
					};

					// Open a new window and show the generated scoresheet.
					$scope.GenerateScoreSheet = function() {
						
						var url = 'rest/generatePlayoffScoreSheet/?homeTeamNumber='
							+ $scope.selectedHomeTeam + '&awayTeamNumber='
							+ $scope.selectedAwayTeam 
							+ '&date=' + $scope.date + '&table1='
							+ $scope.table1 + '&table2='
							+ $scope.table2 
							+ '&homeRosterId='+ $scope.homeRoster.id
							+ '&awayRosterId='+ $scope.awayRoster.id;
						

						window.open(url,'_blank');
					};
					
					

					

				
					
				
				});