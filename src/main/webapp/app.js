var app = angular.module('myApp', [ 'ngGrid' ])

.constant('nameColumnSize', 100);

app.controller('MyCtrl',function($scope,$q,$http, $timeout) {

					$scope.show = false;

					$scope.position = "home";

					$scope.populateGridData = function() {
						var myactivevalue = angular.element(
								document.querySelector('#activeTeam')).val();
						$http({
							method : 'GET',
							url : 'players?teamNumber=' + myactivevalue
						}).success(
								function(dataBasket, status, headers, config) {
									$scope.myData = dataBasket;
									$scope.AddIndex($scope.myData.players);
								}).error(
								function(data, status, headers, config) {
									// called asynchronously if an error occurs
									// show here
								});
					};
					$scope.filterOptions = {
						filterText : ''
					};

					$scope.editableInPopup = '<button id="editBtn" type="button" class="btn btn-primary" ng-click="removeRow(row)" >Remove</button>';

					$scope.moveRowUpButton = '<button id="upBtn" type="button" class="btn btn-primary" ng-click="moveRowUp(row)" >Up</button>';

					$scope.moveRowDownButton = '<button id="downBtn" type="button" class="btn btn-primary" ng-click="moveRowDown(row)" >Down</button>';

					$scope.removeRow = function(row) {
						var index = $scope.myData.players.indexOf(row.entity);
						$scope.myData.players.splice(index, 1);
					};

					$scope.moveRowUp = function(row) {

						var index = $scope.myData.players.indexOf(row.entity);

						var newIndex = index - 1;

						if (index != 0) {
							$scope.moveRow(index, newIndex);
						}

					};

					$scope.moveRow = function(fromIndex, toIndex) {
						var element = $scope.myData.players[fromIndex];

						$scope.myData.players.splice(fromIndex, 1);
						$scope.myData.players.splice(toIndex, 0, element);

						// In order to show the correct sequence we need to
						// update to idx values to match the array indexs and
						// re-sort.
						// Since we are sorting ascending, we must do the sortby
						// twice.
						$scope.AddIndex($scope.myData.players);
						$scope.gridOptions.sortBy('idx');
						$scope.gridOptions.sortBy('idx');
					};

					$scope.moveRowDown = function(row) {
						var index = $scope.myData.players.indexOf(row.entity);

						if (index != $scope.myData.players.length - 1) {
							$scope.moveRow(index, index + 1);
						}
					};

					$scope.AddIndex = function(players) {
						var i = 1;

						for (i = 0; i < players.length; i++) {
							players[i].idx = i;

						}

					};
					$scope.gridOptions = {
						data : 'myData.players',
						rowHeight : 22,
						enableRowReordering : true,
						enableRowSelection : false,
						multiSelect : false,
						sortInfo : {
							fields : [ 'idx' ],
							directions : [ 'asc' ]
						},
						columnDefs : [ {
							cellTemplate : '<div>{{row.rowIndex + 1}}</div>',
							width : 20,
						},

						{
							field : 'idx',
						},

						{
							field : 'firstName',
							displayName : 'First Name',
							width : 100
						}, {
							field : 'lastName',
							displayName : 'Last Name',
							width : 100
						},

						{
							field : 'displayActualAverage',
							displayName : 'Average',
							width : 80,
							format : "c2",
							cellFilter : 'number: 1'
						}, {
							field : 'totalPoints',
							displayName : 'Points',
							width : 80
						}, {
							field : 'gamesPlayed',
							displayName : 'Games',
							width : 80
						}, {
							cellTemplate : $scope.editableInPopup,
							width : 80
						}, {
							cellTemplate : $scope.moveRowUpButton,
							width : 80
						}, {
							cellTemplate : $scope.moveRowDownButton,
							width : 80
						}

						]
					};
					
					$scope.getDropDownDataFromServer = function() { 
						
						 var deferred = $q.defer();
						  var getTeams = $http({method: 'GET', url: '/teams', cache: 'false'});
						  var getSpares = $http({method: 'GET', url: '/players?spares=true', cache: 'false'});
						 
							  // anything you want can go here and will safely be run on the next digest.
							  
							  $q.all([getTeams, getSpares])
							  .then(function(results) {
								//  deferred.resolve(console.log(results[0].data, results[1].data));
								 
								  
								  console.log(results[0].data);
								  $scope.activeTeam = results[0].data;
								  $scope.sparePlayers = results[1].data;
								  $scope.show = true;
								  
							  },
							  function(httperror){
								  deferred.resolve(console.log('some error'));
							  });
							  
						 
					};

// $scope.getDropDownDataFromServer = function() {
// $http({
// method : 'GET',
// url : 'teams'
// }).success(function(data, status, headers, config) {
//
// $scope.activeTeam = data;
// $scope.show = true;
//
// }).error(function(data, status, headers, config) {
// // called asynchronously if an error occurs show
// // here
// });
//
// };
					
					$scope.getSparePlayers = function()
					{
						$http({
							method : 'GET',
							url : 'players?team=11'
						}).success(function(data, status, headers, config) {

							$scope.sparePlayers = data;

						}).error(function(data, status, headers, config) {
							// called asynchronously if an error occurs show
							// here
						});
					}

					$scope.GenerateScoreSheet = function() {
						var myTeam = angular.element(
								document.querySelector('#activeTeam')).val();

						var opponent = angular.element(
								document.querySelector('#opponentTeam')).val();

						h = true;

						if ($scope.position == "away") {
							h = false;
						}

						var roster = [];

						for (i = 0; i < $scope.myData.players.length; i++)

						{
							var obj = {};
							obj['name'] = $scope.myData.players[i].firstName
									+ ' ' + $scope.myData.players[i].lastName;
							obj['average'] = $scope.myData.players[i].displayActualAverage;
							roster.push(obj);
						}

						window
								.open('GenerateScoreSheet?myTeam=' + myTeam
										+ '&opponentTeam=' + opponent
										+ '&ishome=' + h + '&roster='
										+ JSON.stringify(roster), '_blank');
					};

				});