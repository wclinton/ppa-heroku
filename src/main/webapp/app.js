var app = angular.module('myApp', [ 'ngGrid' ])

.constant('nameColumnSize', 100);

app.controller('MyCtrl', function($scope, $filter, $q, $http, $timeout) {

  $scope.show = false;

  $scope.position = "home";

  $scope.populateGridAndMatchData = function() {
    var playersUrl = 'rest/teamPlayers/' + $scope.selectedTeam;

    var matchUrl = 'rest/matches/' + $scope.selectedWeek + '/' + +$scope.selectedTeam;

    var deferred = $q.defer();

    var getPlayers = $http({ method : 'GET',
    url : playersUrl,
    cache : 'false'
    });
    var getWeeks = $http({ method : 'GET',
    url : matchUrl,
    cache : 'false'
    });

    // anything you want can go here and will safely be run
    // on the next digest.

    $q.all([ getPlayers, getWeeks ]).then(function(results) {
      // deferred.resolve(console.log(results[0].data,
      // results[1].data));

      $scope.myData = results[0].data;
      $scope.match = results[1].data;
      $scope.AddIndex($scope.myData.players);
      $scope.setOpponentTeam($scope.match);
      $scope.setHome($scope.match);

    }, function(httperror) {
      deferred.resolve(console.log('some error'));
    });
    $scope.setHome = function(match) {

      if (match.homeTeam == $scope.selectedTeam) {
        $scope.position = "home";
      } else {
        $scope.position = "away";
      }
    };

    $scope.setOpponentTeam = function(match) {
      if (match.homeTeam == $scope.selectedTeam) {
        $scope.selectedOpponentTeam = match.awayTeam;
      } else {
        $scope.selectedOpponentTeam = match.homeTeam;
      }
    }

  };
  $scope.filterOptions = { filterText : ''
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

  // configure the player roster grid.
  $scope.gridOptions = { data : 'myData.players',
  rowHeight : 22,
  enableRowReordering : true,
  enableRowSelection : false,
  multiSelect : false,
  enableCellEditOnFocus : false,

  enableCellSelection : true,
  sortInfo : { fields : [ 'idx' ],
  directions : [ 'asc' ]
  },
  columnDefs : [ { cellTemplate : '<div>{{row.rowIndex + 1}}</div>',
  width : 20,
  },

  { field : 'idx',
  visible : false,
  },

  { field : 'firstName',
  displayName : 'First Name',
  width : 100,
  enableCellEdit : true,
  }, { field : 'lastName',
  displayName : 'Last Name',
  width : 100,
  enableCellEdit : true,
  },

  { field : 'displayActualAverage',
  displayName : 'Average',
  width : 80,
  format : "c2",
  cellFilter : 'number: 1',
  enableCellEdit : true
  }, { field : 'totalPoints',
  displayName : 'Points',
  width : 80
  }, { field : 'gamesPlayed',
  displayName : 'Games',
  width : 80
  }, { cellTemplate : $scope.editableInPopup,
  width : 80
  }, { cellTemplate : $scope.moveRowUpButton,
  width : 80
  }, { cellTemplate : $scope.moveRowDownButton,
  width : 80
  }

  ]
  };

  // Gets the initial data for the form.
  $scope.getDropDownDataFromServer = function() {

    var deferred = $q.defer();
    var getTeams = $http({ method : 'GET',
    url : 'rest/teams',
    cache : 'false'
    });
    var getSpares = $http({ method : 'GET',
    url : 'rest/sparePlayers',
    cache : 'false'
    });
    var getWeeks = $http({ method : 'GET',
    url : 'rest/weeks',
    cache : 'false'
    });

    // anything you want can go here and will safely be run
    // on the next digest.

    $q.all([ getTeams, getSpares, getWeeks ]).then(function(results) {
      // deferred.resolve(console.log(results[0].data,
      // results[1].data));

      console.log(results[0].data);
      $scope.activeTeam = results[0].data;
      $scope.sparePlayers = results[1].data;
      $scope.weeks = results[2].data.weeks;
      $scope.show = true;

    }, function(httperror) {
      deferred.resolve(console.log('some error'));
    });
  };

  // Gets a list of all the current spare players.
  $scope.getSparePlayers = function() {
    $http({ method : 'GET',
    url : 'rest/sparePlayers'
    }).success(function(data, status, headers, config) {

      $scope.sparePlayers = data;

    }).error(function(data, status, headers, config) {
      // called asynchronously if an error occurs show
      // here
    });
  }

  // Open a new window and show the generated scoresheet.
  $scope.GenerateScoreSheet = function() {

    h = "true";

    if ($scope.position == "away") {
      h = "false";
    }

    var roster = $scope.getRoster();

    var date = $filter('date')($scope.weeks[$scope.selectedWeek - 1].date, 'MMMM d yyyy', 'UTC');

    window.open('rest/generateScoreSheet?myTeam=' + $scope.selectedTeam + '&opponentTeam=' + $scope.selectedOpponentTeam + '&week='
        + $scope.selectedWeek + '&ishome=' + h + '&date=' + date + '&table1=' + $scope.match.table1 + '&table2=' + $scope.match.table2 + '&roster='
        + JSON.stringify(roster), '_blank');
  };

  $scope.SaveRoster = function() {
    var rosterPlayers = $scope.getRoster();

    var home = $scope.position != "away";

    var rosterViewModel = { teamNumber : $scope.selectedTeam,
    players : rosterPlayers,
    isHome : home
    };

    $http.post('/roster', rosterViewModel);
    success(function(data, status, headers, config) {
      // this callback will be called asynchronously
      // when the response is available
    }).error(function(data, status, headers, config) {
      // called asynchronously if an error occurs
      // or server returns response with an error status.
    });
  };

  $scope.LoadRoster = function() {
    var home = $scope.position != "away";

    $http({ method : 'GET',
    url : 'roster?teamNumber=' + $scope.selectedTeam + '&isHome=' + home,
    }).success(function(data, status, headers, config) {

      $scope.setRoster(data);

    }).error(function(data, status, headers, config) {
      // called asynchronously if an error occurs show
      // here
    });
  };

  $scope.getRoster = function() {

    var roster = [];
    for (i = 0; i < $scope.myData.players.length; i++) {
      roster.push($scope.myData.players[i]);
    }
    return roster;
  };

  $scope.setRoster = function(roster) {
    $scope.myData.players.length = 0;

    $scope.myData.players = roster.players;

    // In order to show the correct sequence we need to
    // update to idx values to match the array indexs and
    // re-sort.
    // Since we are sorting ascending, we must do the sortby
    // twice.
    $scope.AddIndex($scope.myData.players);
    $scope.gridOptions.sortBy('idx');
    $scope.gridOptions.sortBy('idx');
  };

  // Add the spare player to the list of players on the
  // roster.
  $scope.addSparePlayer = function() {

    if ($scope.selectedSparePlayer == "new") {

      var len = $scope.myData.players.length

      $scope.myData.players.push({ fullName : "",
      idx : len
      })
    } else {
      var p = JSON.parse($scope.selectedSparePlayer);
      $scope.myData.players.push(p);
    }
    $scope.selectedSparePlayer = "";
  };

  $scope.weekSelected = function() {

  };
});
