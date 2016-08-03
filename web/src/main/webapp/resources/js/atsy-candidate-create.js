var savedModel;

function actionFormatter(value, row, index) {
    return [
        '<a class="edit ml10" href="../application_state?applicationId=' + row.applicationId + '" title="Edit">',
        '<i class="glyphicon glyphicon-edit"></i>',
        '</a>'
    ].join('');
}

function CandidateCreateModel(){
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


    var self = this;
    self.errorResponse = ko.observable(null);
    self.showError = ko.observable(false);

    self.ajaxCall = function() {
        var form = $("#candidate-create-form");
        $.ajax({
            url: form.attr('action'),
            method: form.attr('method'),
            contentType: 'application/json',
            dataType: "json",
            data: JSON.stringify({
                id: self.id(),
                name: self.name(),
                referer: self.referer(),
                email: self.email(),
                languageSkill: self.languageSkill(),
                phone: self.phone(),
                description: self.description()
            })
        }).done(function (xhr) {
            window.location = form.attr('action')+ '/' + xhr.id;
        }).error(function (xhr) {
            self.errorResponse(xhr.responseJSON);

            self.showError(true);
        });
    }

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

    self.modify = ko.observable(false);
    modify_display_true = function() {
        self.modify(true);
        self.showError(false);
        $("#candidate-create-form").validator('destroy');
        candidateModel.id(savedModel.id);
        candidateModel.name(savedModel.name);
        candidateModel.referer(savedModel.referer);
        candidateModel.email(savedModel.email);
        candidateModel.languageSkill(savedModel.languageSkill);
        candidateModel.phone(savedModel.phone);
        candidateModel.referer(savedModel.referer);
    };
    modify_display_false = function() {
        self.modify(false);
        savedModel = ko.toJS(candidateModel);
    };
    save_button = function() {
        self.ajaxCall();
    };

    self.cssclass = ko.computed(function() {
        if (self.modify == true) {
            return "display";
        } else {
            return "";
        }
    });
}

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
    var year = dateTime.getFullYear();
    var month = dateTime.getMonth() + 1;
    month = month.toString().length == 2 ? month : ('0' + month);

    var day = dateTime.getDate();
    day = day.toString().length == 2 ? day : ('0' + day);

    var hour = dateTime.getHours();
    hour = hour.toString().length == 2 ? hour : ('0' + hour);

    var minute = dateTime.getMinutes();
    minute = minute.toString().length == 2 ? minute : ('0' + minute);

    var second = dateTime.getSeconds();
    second = second.toString().length == 2 ? second : ('0' + second);

    return year + '-' + month + '-' + day + " " + hour + ":" + minute + ':' + second;
}
