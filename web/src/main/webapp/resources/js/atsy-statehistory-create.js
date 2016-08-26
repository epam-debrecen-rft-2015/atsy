$(document).ready(function() {
  $("#create-state-form").validator().on('submit', (function(event) {
      if (event.isDefaultPrevented()){
        //nothing
      } else {
        stateHistoryModel.ajaxCall();

        return false;
      }
  }));
});


function StateHistoryModel() {
  var self = this;

  ko.bindingHandlers.valueWithInit = {
          init: function(element, valueAccessor, allBindingsAccessor, data) {
              var property = valueAccessor(),
                  value = element.value;

              //create the observable, if it doesn't exist
              if (!ko.isWriteableObservable(data[property])) {
                  data[property] = ko.observable();
              }

              data[property](value);

              ko.applyBindingsToNode(element, { value: data[property] });
          }
      };

 self.errorResponse = ko.observable(null);
    self.showError = ko.observable(false);

    self.errorMessage = ko.pureComputed(function() {
      if (self.errorResponse() === null) {
        return ""
      }

      return self.errorResponse().errorMessage;
    });

    self.fieldMessages = ko.pureComputed(function() {
        if (self.errorResponse() === null) {
          return [];
        }

        return Object.keys(self.errorResponse().fields).map(function(key) {
            return self.errorResponse().fields[key];
        });
    });

   self.ajaxCall = function() {
    var form = $("#create-state-form");

    console.log(ko.toJSON(self));

     $.ajax({
       url: form.attr('action'),
       method: form.attr('method'),
             contentType: 'application/json',
             dataType: 'json',
             data: ko.toJSON(self),
             success: function(data) {
              self.redirectWithoutState();
             },
             error: function(xhr) {
               self.errorResponse(xhr.responseJSON);
               self.showError(true);
             }
     });
   }

   self.isRecommendationValid = ko.pureComputed(function() {
    var recomm = typeof self.recommendation !== 'undefined' ? self.recommendation() : "0";

    return ((recomm == "0") || (recomm == "1"));
   });

   self.isRecommendationPositionLevelValid = ko.pureComputed(function() {
    var pos = typeof self.recommendedPositionLevel !== 'undefined' ? self.recommendedPositionLevel() : "0";

    return (pos >= "0" && pos <= "5");
   });

   self.canSave = ko.pureComputed(function() {
    return self.isRecommendationValid() && self.isRecommendationPositionLevelValid();
   });
}

StateHistoryModel.prototype.redirectWithoutState = function() {
  var appIdRegex = /applicationId\=[\d]+/;
  var applicationIdParam = appIdRegex.exec(window.location.search)[0];
  window.location.assign(window.location.href.replace(/\?[^]*/, "?" + applicationIdParam));
}

var stateHistoryModel = new StateHistoryModel();

ko.applyBindings(stateHistoryModel);
