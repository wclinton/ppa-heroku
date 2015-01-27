var app = angular.module('myApp', [ 'ngGrid' ])

.constant('nameColumnSize', 100);

app.controller('MyCtrl', function($scope, $filter, $q, $http, $timeout) {

	$scope.show = true;

	$scope.position = "home";

	$scope.init = function() {
		var sparePlayersUrl = '/players';

		var deferred = $q.defer();

		var getPlayers = $http({
			method : 'GET',
			url : sparePlayersUrl,
			cache : 'false'
		});

		// anything you want can go here and will safely be run
		// on the next digest.

		$q.all([ getPlayers ]).then(function(results) {
			// deferred.resolve(console.log(results[0].data,
			// results[1].data));

			$scope.Players = results[0].data;
			
			
			$scope.gridOptions.sortBy('totalPoints');
			
			$scope.show = true;
			

		}, function(httperror) {
			deferred.resolve(console.log('some error'));
		});

		// configure the player roster grid.
		$scope.gridOptions = {
			data : 'Players.players',
	//			rowHeight : 22,
	//			enableRowReordering : true,
	//			enableRowSelection : false,
	//			multiSelect : false,
	//			enableCellEditOnFocus : false,
	//
	//			enableCellSelection : true,
			sortInfo : {
				fields : [ 'totalPoints' ],
				directions : [ 'desc' ]
			},
			columnDefs : [ {
				cellTemplate : '<div>{{row.rowIndex + 1}}</div>',
				width : 20,
			},

			

			{
				field : 'firstName',
				displayName : 'First Name',
				width : 100,
			}, {
				field : 'lastName',
				displayName : 'Last Name',
				width : 100,
			},{
				field : 'teamNumber',
				displayName : 'Team'
				
				
			},
			
			

			{
				field : 'actualAverage',
				displayName : 'Average',
				width : 80,
				format : "c2",
				cellFilter : 'number: 4',
				enableCellEdit : true
			}, {
				field : 'totalPoints',
				displayName : 'Points',
				width : 80
			}, {
				field : 'gamesPlayed',
				displayName : 'Games',
				width : 80
			}, 
			{
				field : 'perfectNights',
				displayName: 'Perfect Nights'
			}
			]
		};

	};

});
