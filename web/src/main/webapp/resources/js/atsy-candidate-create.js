$('.table').bootstrapTable({
    onClickRow: function (row, $element) {
      window.location.href = "../application_state?applicationId=" + row.applicationId;
    }
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
        var fileValidationUrl = '/atsy/secure/candidate/fileUpload/validate';
        var fileUploadUrl = '/atsy/secure/candidate/fileUpload/';

        var form = $("#candidate-create-form");
        var formData = new FormData();
        formData.append('file', $('input[type=file]')[0].files[0]);

        // Send candidate to validate
        sendCandidateWithAjax(candidateValidationUrl, form.attr('method')).done(function (xhr) {
            // If candidate is valid, then hide the error msg
            self.showError(false);
            // If candidate is valid, then send file to validate
            sendFileWithAjax(fileValidationUrl, formData).done(function (xhr) {
                // If candidate and file are valid, then hide error msg
                self.showFileError(false);
                // Send candidate to save or update
                sendCandidateWithAjax(candidateUrl, form.attr('method')).done(function (xhr) {
                    // If there is no error, then hide error msg
                    self.showError(false);
                    // Send file to save
                    sendFileWithAjax(fileUploadUrl + xhr.id, formData).done(function (xhr) {
                        // If there is no error with file, then hide error msg
                        self.showFileError(false);
                        // If candidate and file are correct
                        window.location = form.attr('action')+ '/' + xhr.id;
                    // If there is error with file, then show error msg
                    }).error(function (xhr) {
                        self.fileErrorResponse(xhr.responseJSON);
                        self.showFileError(true);
                    });
                // If there is error with candidate, then show error msg
                }).error(function (xhr) {
                    self.candidateErrorResponse(xhr.responseJSON);
                    self.showError(true);
                });
            // If file is not valid, then show error msg
            }).error(function (xhr) {
                self.fileErrorResponse(xhr.responseJSON);
                self.showFileError(true);
            });
        // If candidate is not valid, then show error msg
        }).error(function (xhr) {
            self.candidateErrorResponse(xhr.responseJSON);
            self.showError(true);

            // Candidate is not valid but we send file to validate
            sendFileWithAjax(fileValidationUrl, formData).done(function (xhr) {
                // If file is valid, then hide error msg
                 self.showFileError(false);
            // If file is not valid, then show error msg
            }).error(function (xhr) {
                 self.fileErrorResponse(xhr.responseJSON);
                 self.showFileError(true);
            });
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
    };

    self.modify_display_false = function() {
        self.modify(false);
        savedModel = ko.toJS(candidateModel);
    };
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