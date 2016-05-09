/**
 * Created by mates on 2015. 11. 18..
 */


function showError(message) {
    $("#candidate-create-form").find('.globalMessage .error-message').text(message);
    $("#candidate-create-form").find('.globalMessage').show();
    $("#candidate-create-form").addClass('has-error');
}

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
                showError(xhr.responseText);
            });
    }

    self.modify = ko.observable(false);
    modify_display_true = function() {
        self.modify(true);
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
        console.log(savedModel);
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

ko.applyBindings(candidateModel);