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
    self.errorMessage = ko.observable();
    self.showError = ko.observable(false);

    self.ajaxCall = function() {
        var form = $("#candidate-create-form");
        $.ajax({
            url: form.attr('action'),
            method: form.attr('method'),
            contentType: 'application/json',
            data: JSON.stringify({
                id: self.id(),
                name: self.name(),
                referer: self.referer(),
                email: self.email(),
                languageSkill: self.languageSkill()[0],
                phone: self.phone(),
                description: self.description()
            })
        }).done(function (xhr) {
            window.location = form.attr('action')+ '/' + xhr;
        }).error(function (xhr) {
            self.errorMessage(xhr.responseText);
            self.showError(true);
        });
    }

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
    event.preventDefault();
    candidateModel.ajaxCall();
  }
});

ko.applyBindings(candidateModel);