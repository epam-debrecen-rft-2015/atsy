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
        var candidateStatusCode, fileStatusCode;
        var form = $("#candidate-create-form");

        sendCandidateWithAjax("./candidate/validate", form.attr('method')).done(function (xhr) {
            candidateStatusCode = xhr.statusCode;
            var formData = new FormData();
            formData.append('file', $('input[type=file]')[0].files[0]);

            sendFileWithAjax('./candidate/fileUpload/validate', formData).done(function (xhr) {
                fileStatusCode = xhr.statusCode;

                if (candidateStatusCode === 200 && fileStatusCode === 200) {
                   sendCandidateWithAjax(form.attr('action'), form.attr('method')).done(function (xhr) {
                       var formData = new FormData();
                       formData.append('file', $('input[type=file]')[0].files[0]);
                       sendFileWithAjax('./candidate/fileUpload/' + xhr.id, formData).done(function (xhr) {
                           window.location = form.attr('action')+ '/' + xhr.id;
                       }).error(function (xhr) {
                           self.errorResponse(xhr.responseJSON);
                           self.showError(true);
                       });
                   });
                }
            }).error(function (xhr) {
                fileStatusCode = xhr.statusCode;
                self.errorResponse(xhr.responseJSON);
                self.showError(true);
            });
        }).error(function (xhr) {

            var formData = new FormData();
            formData.append('file', $('input[type=file]')[0].files[0]);
            sendFileWithAjax('./candidate/fileUpload/validate', formData).done(function (xhr) {
                fileStatusCode = 200;
            }).error(function (xhr) {
                fileStatusCode = 400;
            });

            sendCandidateWithAjax(form.attr('action'), form.attr('method')).error(function (xhr) {
                self.errorResponse(xhr.responseJSON);
                self.showError(true);

                if (fileStatusCode === 400) {
                    var formData = new FormData();
                    formData.append('file', $('input[type=file]')[0].files[0]);
                    sendFileWithAjax('./candidate/fileUpload/' + 1, formData).error(function (xhr) {
                        self.fileErrorResponse(xhr.responseJSON);
                        self.showFileError(true);
                    });

                }
            });
        });
    }

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
        candidateModel.referer(savedModel.referer);
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