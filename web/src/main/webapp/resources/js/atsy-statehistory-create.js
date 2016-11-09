$(document).ready(function() {
  $("#create-state-form").validator().on('submit', (function(event) {
      if (event.isDefaultPrevented()){
        //nothing
      } else {
        stateHistoryModel.ajaxCall();
        return false;
      }
  }));
  $('#dateOfEnterInput').datepicker({
      format: 'yyyy-mm-dd',
      autoclose: true
  });
  $('#feedbackDateInput').datetimepicker({
    format: 'YYYY-MM-DD HH:mm',
  });
  $('#dayOfStartInput').datetimepicker({
    format: 'YYYY-MM-DD'
  });

  $("#file").change(function() {
      $("#fileName").text(new Option($('input[type=file]')[0].files[0].name).innerHTML);
  });

  $("#latestStateEditButton").on('click', function () {
      if (localStorage['isShowFileUploadIcon'] == 'false') {
          $('#fileUploadLabel').show();
          localStorage['isShowFileUploadIcon'] = 'true';
      }
  });

  $("#newStateButton").on('click', function () {
      if (localStorage['isShowFileUploadIcon'] == 'false') {
          $('#fileUploadLabel').show();
          localStorage['isShowFileUploadIcon'] = 'true';
      }
  });

  if (!localStorage['isShowFileUploadIcon']) {
      localStorage['isShowFileUploadIcon'] = 'false';
  } else if (localStorage['isShowFileUploadIcon'] == 'true') {
      $('#fileUploadLabel').show();
  } else if (localStorage['isShowFileUploadIcon'] == 'false') {
      $('#fileUploadLabel').hide();
  }
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

  ko.bindingHandlers.datetimepickerBinding = {
          init: function(element, valueAccessor, allBindingsAccessor, data) {
              var property = valueAccessor()
              if (!ko.isWriteableObservable(data[property])) {
                 data[property] = ko.observable();
              }
                  $(element).parent().on("dp.change", function(event){
                    var timeZonedDate = event.date.subtract(event.date.utcOffset(), 'm').format('YYYY-MM-DD HH:mm')
                    data[property](timeZonedDate);
                  });
          }
      };

    self.errorResponse = ko.observable(null);
    self.fileErrorResponse = ko.observable(null);
    self.showError = ko.observable(false);
    self.showFileError = ko.observable(false);

    self.errorMessage = ko.pureComputed(function() {
      if (self.errorResponse() === null) {
        return ""
      }

      return self.errorResponse().errorMessage;
    });

    self.fileErrorMessage = ko.pureComputed(function() {
        if (self.fileErrorResponse() === null) {
            return "";
        }

        return self.fileErrorResponse().errorMessage;
    });

    self.fieldMessages = ko.pureComputed(function() {
        if (self.errorResponse() === null) {
          return [];
        }

        return Object.keys(self.errorResponse().fields).map(function(key) {
            return self.errorResponse().fields[key];
        });
    });

    function sendFileWithAjax(url, formData) {
        return $.ajax({
                   url: url,
                   data: formData,
                   type: 'POST',
                   contentType: false,
                   processData: false,
               });
    }

    function sendStateHistoryViewRepresentationWithAjax(url, method) {
        return $.ajax({
                   url: url,
                   method: method,
                   contentType: 'application/json',
                   dataType: 'json',
                   data: ko.toJSON(self)
               });
    }

   self.ajaxCall = function() {

    var stateHistoryViewRepresentationValidationUrl = '/atsy/secure/application_state/validate';
    var fileValidationUrl = '/atsy/secure/fileUpload/validate';
    var fileUploadUrl = '/atsy/secure/fileUpload/stateHistory/';

    var form = $("#create-state-form");
    var formData = new FormData();

    if(($('input[type=file]'))[0] != undefined) {
        formData.append('file', $('input[type=file]')[0].files[0]);
    }

    console.log(ko.toJSON(self));

    // Send stateHistoryViewRepresentation to validate
    sendStateHistoryViewRepresentationWithAjax(stateHistoryViewRepresentationValidationUrl, form.attr('method')).done(function (xhr) {
        // If stateHistoryViewRepresentation is valid, then hide the error msg
        self.showError(false);
        // If the file not exists
        if (($('input[type=file]')[0] === undefined) || ($('input[type=file]').val() === '')) {
            // Send stateHistoryViewRepresentation to save
            sendStateHistoryViewRepresentationWithAjax(form.attr('action'), form.attr('method')).done(function (xhr) {
                // If there is no error with stateHistoryViewRepresentation, then hide the error msg
                self.showError(false);
                self.redirectWithoutState();
                // If there is error with stateHistoryViewRepresentation, then show the error msg
            }).error(function (xhr) {
                self.errorResponse(xhr.responseJSON);
                self.showError(true);
            });
        // If the file exists
        } else {
            // If file exists, then send file to validate
            sendFileWithAjax(fileValidationUrl, formData).done(function (xhr) {
                // If file is valid, then hide the error msg
                self.showFileError(false);
                $('#fileUploadLabel').show();
                localStorage['isShowFileUploadIcon'] = 'true';
                // Send stateHistoryViewRepresentation to save or update
                sendStateHistoryViewRepresentationWithAjax(form.attr('action'), form.attr('method')).done(function (xhr) {
                    // If there is no error with stateHistoryViewRepresentation, then hide the error msg
                    self.showError(false);
                    // Send file to save
                    sendFileWithAjax(fileUploadUrl + xhr.applicationId, formData).done(function (xhr) {
                        // If there is no error with file, then hide error msg
                        self.showFileError(false);
                        $('#fileUploadLabel').show();
                        localStorage['isShowFileUploadIcon'] = 'true';
                        // If stateHistoryViewRepresentation and file are corrects
                        self.redirectWithoutState();
                    }).error(function (xhr) {
                        // If there is error with file, then show the error msg
                        self.fileErrorResponse(xhr.responseJSON);
                        self.showFileError(true);
                        $('#fileUploadLabel').show();
                        localStorage['isShowFileUploadIcon'] = 'true';
                    });
                // If there is error with stateHistoryViewRepresentation, then show the error msg
                }).error(function (xhr) {
                        self.errorResponse(xhr.responseJSON);
                        self.showError(true);
                });
             // If file is not valid, then show the error msg
            }).error(function (xhr) {
                self.fileErrorResponse(xhr.responseJSON);
                self.showFileError(true);
                $('#fileUploadLabel').show();
                localStorage['isShowFileUploadIcon'] = 'true';
            });
        }
    // If stateHistoryViewRepresentation is not valid, then show error msg
    }).error(function (xhr) {
        if (typeof xhr.responseJSON !== "undefined") {
            self.errorResponse(xhr.responseJSON);
            self.showError(true);
            // If the file exists
            if (($('input[type=file]')[0] != undefined) && ($('input[type=file]').val() != '')) {
                // Send file to save
                sendFileWithAjax(fileValidationUrl, formData).done(function (xhr) {
                    // If there is no error with file, then hide the error msg
                    self.showFileError(false);
                    $('#fileUploadLabel').show();
                    localStorage['isShowFileUploadIcon'] = 'true';
                // If file is not valid, then show the error msg
                }).error(function (xhr) {
                    self.fileErrorResponse(xhr.responseJSON);
                    self.showFileError(true);
                    $('#fileUploadLabel').show();
                    localStorage['isShowFileUploadIcon'] = 'true';
                });
            }
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

   self.isDayOfStartValid = ko.pureComputed(function () {
    var date = typeof self.dayOfStart !== 'undefined' ? self.dayOfStart() : "0000-00-00";

    return date.match(/^\d{4}\-\d{2}\-\d{2}$/) !== null;
   });

   self.isDateOfEnterValid = ko.pureComputed(function () {
    var date = typeof self.dateOfEnter !== 'undefined' ? self.dateOfEnter() : "0000-00-00";

    return date.match(/^\d{4}\-\d{2}\-\d{2}$/) !== null;
   });

   self.canSave = ko.pureComputed(function() {
     return self.isRecommendationValid()
            && self.isRecommendationPositionLevelValid()
            && self.isDayOfStartValid()
            && self.isDateOfEnterValid();
   });

   self.modify_display_true = function() {
       $("#fileName").text('');
   };

   $("#downloadLink").on('click', function (event) {
       var url = $(this).data('file');

       event.preventDefault();
       event.stopPropagation();

       $.ajax({
           url: url,
           type: 'GET'
       }).done(function (xhr) {
           var downloadUrl = "/atsy/secure/fileDownload/" + xhr.id;
           window.location = downloadUrl;
       }).error(function (xhr) {
           self.fileErrorResponse(xhr.responseJSON);
           self.showFileError(true);
       });
    });

    $("#saveButton").on('click', function () {
          if (localStorage['isShowFileUploadIcon'] == 'true') {
              $('#fileUploadLabel').hide();
              localStorage['isShowFileUploadIcon'] = 'false';
          }
      });

      $("#cancelButton").on('click', function () {
          if (localStorage['isShowFileUploadIcon'] == 'true') {
              $('#fileUploadLabel').hide();
              localStorage['isShowFileUploadIcon'] = 'false';
          }
          self.showError(false);
          self.showFileError(false);
      });
}

StateHistoryModel.prototype.redirectWithoutState = function() {
  var appIdRegex = /applicationId\=[\d]+/;
  var applicationIdParam = appIdRegex.exec(window.location.search)[0];
  window.location.assign(window.location.href.replace(/\?[^]*/, "?" + applicationIdParam));
}

var stateHistoryModel = new StateHistoryModel();

ko.applyBindings(stateHistoryModel);

