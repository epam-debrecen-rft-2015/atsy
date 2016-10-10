$(document).ready(function() {
    $("#file").change(function() {
        $("#fileName").text(new Option($('input[type=file]')[0].files[0].name).innerHTML);
    });

    $('.table').bootstrapTable({
      onClickRow: function (row, $element) {
        window.location.href = "../application_state?applicationId=" + row.id;
      }
  });
});

function CandidateCreateModel(){
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

    ko.bindingHandlers.initDisplay = {
        init: function(element, valueAccessor, allBindingsAccessor, data) {
            self.modify(true);
        }
    };

    function sendCandidateWithAjax(url, method) {
        return $.ajax({
                   url: url,
                   method: method,
                   contentType: 'application/json',
                   dataType: "json",
                   data: JSON.stringify({
                       id: self.id(),
                       name: self.name(),
                       referer: self.referer(),
                       email: self.email(),
                       languageSkill: self.languageSkill(),
                       phone: self.phone(),
                       description: self.description(),
                       cvFilename: self.cvFilename()
                   })
               });
    }

    function sendFileWithAjax(url, formData) {
        return $.ajax({
                   url: url,
                   data: formData,
                   type: 'POST',
                   contentType: false,
                   processData: false,
               });
    }

    self.ajaxCall = function() {
        var candidateValidationUrl = '/atsy/secure/candidate/validate';
        var candidateUrl = '/atsy/secure/candidate';
        var fileValidationUrl = '/atsy/secure/fileUpload/validate';
        var fileUploadUrl = '/atsy/secure/fileUpload/candidate/';

        var form = $("#candidate-create-form");
        var formData = new FormData();

        if(($('input[type=file]'))[0] != undefined) {
            formData.append('file', $('input[type=file]')[0].files[0]);
        }

        // Send candidate to validate
        sendCandidateWithAjax(candidateValidationUrl, form.attr('method')).done(function (xhr) {
            // If candidate is valid, then hide the error msg
            self.showError(false);
            if (($('input[type=file]')[0] === undefined) || ($('input[type=file]').val() === '')) {
                // Send candidate to save
                sendCandidateWithAjax(candidateUrl, form.attr('method')).done(function (xhr) {
                    // If there is no error with candidate, then hide the error msg
                    self.showError(false);
                    window.location = form.attr('action')+ '/' + xhr.id;
                    // If there is error with candidate, then show the error msg
                }).error(function (xhr) {
                    self.candidateErrorResponse(xhr.responseJSON);
                    self.showError(true);
                });
             // If the file exists
            } else {
                // If file exists, then send file to validate
                sendFileWithAjax(fileValidationUrl, formData).done(function (xhr) {
                    // If file is valid, then hide the error msg
                    self.showFileError(false);
                    // Send candidate to save or update
                    sendCandidateWithAjax(candidateUrl, form.attr('method')).done(function (xhr) {
                        // If there is no error with candidate, then hide the error msg
                        self.showError(false);
                        // Send file to save
                        sendFileWithAjax(fileUploadUrl + xhr.id, formData).done(function (xhr) {
                            // If there is no error with file, then hide error msg
                            self.showFileError(false);
                            // If candidate and file are corrects
                            window.location = form.attr('action')+ '/' + xhr.id;
                        }).error(function (xhr) {
                            // If there is error with file, then show the error msg
                            self.fileErrorResponse(xhr.responseJSON);
                            self.showFileError(true);
                        });
                    // If there is error with candidate, then show the error msg
                    }).error(function (xhr) {
                        self.candidateErrorResponse(xhr.responseJSON);
                        self.showError(true);
                    });
                // If file is not valid, then show the error msg
                }).error(function (xhr) {
                    self.fileErrorResponse(xhr.responseJSON);
                    self.showFileError(true);
                });
            }
        // If candidate is not valid, then show error msg
        }).error(function (xhr) {
            self.candidateErrorResponse(xhr.responseJSON);
            self.showError(true);
            // If the file exists
            if (($('input[type=file]')[0] != undefined) && ($('input[type=file]').val() != '')) {
                // Send file to save
                sendFileWithAjax(fileValidationUrl, formData).done(function (xhr) {
                    // If there is no error with file, then hide the error msg
                    self.showFileError(false);
                // If file is not valid, then show the error msg
                }).error(function (xhr) {
                    self.fileErrorResponse(xhr.responseJSON);
                    self.showFileError(true);
                });
            }
        });
    }

    self.candidateErrorResponse = ko.observable(null);
    self.fileErrorResponse = ko.observable(null);
    self.showError = ko.observable(false);
    self.showFileError = ko.observable(false);

    self.errorMessage = ko.pureComputed(function() {
      if (self.candidateErrorResponse() === null) {
        return ""
      }

      return self.candidateErrorResponse().errorMessage;
    });

    self.fileErrorMessage = ko.pureComputed(function() {
        if (self.fileErrorResponse() === null) {
            return "";
        }

        return self.fileErrorResponse().errorMessage;
    });

    self.fieldMessages = ko.pureComputed(function() {
        if (self.candidateErrorResponse() === null) {
          return [];
        }

        return Object.keys(self.candidateErrorResponse().fields).map(function(key) {
            return self.candidateErrorResponse().fields[key];
        });
    });

    self.modify = ko.observable(false);

    self.modify_display_true = function() {
        self.modify(true);
        self.showError(false);
        self.showFileError(false);
        $("#candidate-create-form").validator('destroy');
        candidateModel.id(savedModel.id);
        candidateModel.name(savedModel.name);
        candidateModel.referer(savedModel.referer);
        candidateModel.email(savedModel.email);
        candidateModel.languageSkill(savedModel.languageSkill);
        candidateModel.phone(savedModel.phone);
        $("#fileName").text('');
    };

    self.modify_display_false = function() {
        self.modify(false);
        savedModel = ko.toJS(candidateModel);
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
}

var savedModel;

var candidateModel = new CandidateCreateModel();

$('#candidate-create-form').validator().on('submit', function (e) {
  if (e.isDefaultPrevented()) {
    // handle the invalid form...
  } else {
    candidateModel.ajaxCall();
    return false;
  }
});

ko.applyBindings(candidateModel);



function creationDateFormatter(value, row, index) {
    var dateTime = new Date(row.creationDate);
    return dateTimeFormatter(dateTime);
}

function modificationDateFormatter(value, row, index) {
    var dateTime = new Date(row.modificationDate);
    return dateTimeFormatter(dateTime);
}

function dateTimeFormatter(dateTime) {
    var options = {year: 'numeric', month: 'numeric', day: 'numeric', hour: 'numeric', minute: 'numeric', second: 'numeric'};
    return dateTime.toLocaleDateString('hu-HU', options);
}

function actionFormatter(value, row, index) {
    return [
        '<a class="remove ml10 little-space" href="javascript:void(0)" title="', $.i18n.prop('common.remove.js'), '">',
        '<i class="glyphicon glyphicon-remove"></i>',
        '</a>',
    ].join('');
}

window.channelsEvents = {
    'click .remove': function (e, value, row) {
         var container = $('#applications_table').parent();
         var options = getOptions('../applications', 'question.delete.application.js', 'selected.element.not.found', row, container);
         e.stopImmediatePropagation();
         bootbox.dialog(options);
    }
};
