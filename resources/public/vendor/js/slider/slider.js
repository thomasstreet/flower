(function() {

  angular.module('slider', [])
    .directive('slider', function() {
      return {
        restrict: 'E',
        require: '?ngModel',
        templateUrl: '/vendor/js/slider/sliderTemplate.html',
        link: function($scope, element, attrs, ngModel) {
          sliderConfig = {
              min: Number(attrs.min),
              max: Number(attrs.max),
              orientation: "horizontal",
              range: "min",
              value: ngModel.$modelValue || 101,
              slide: (function slideHandler(event, ui) { 
                ngModel.$setViewValue(ui.value);
                $scope.$apply()
              })
          }
          $(element).find('.ui-slider').slider(sliderConfig)
          $scope.$watch(attrs.ngModel, function(newVal) {
            if (! _.isUndefined(newVal)) {
              $(element).find('.ui-slider').slider('value', newVal);
            }
          });
        }
      }
    })
}).call(this)
