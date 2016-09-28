function getOptions(prefixUrl, dialogMessageKey, errorMessageKey, row, container) {
    var options = {
        size: 'small',
        message: $.i18n.prop(dialogMessageKey) + " (" + new Option(row.name).innerHTML + ")",
        animate: true,
        onEscape: function() {},

        buttons: {

            danger: {
                className: "btn-danger",
                label: $.i18n.prop('common.no.js'),
                callback: function() {}
            },

            success: {
                className: "btn-success",
                label: $.i18n.prop('common.yes.js'),
                callback: function() {
                    $.ajax({
                        type: 'DELETE',
                        url: prefixUrl + "/delete?" + 'id=' + row.id,
                        cache: false,
                    }).done(function() {
                        var table = container.find('table');
                        table.bootstrapTable('refresh');
                        hideError(container);
                    }).error(function (xhr) {
                        var response = xhr.responseJSON;
                        showError(container, response.errorMessage + " (" + row.name + ")");
                    });
                }
             },
        },
    }
    return options;
}

function showError(container, message) {
    container.find('#errorMessageForDeleting').text(message);
    container.find('#errorMessageForDeleting').show();
    container.addClass('has-error');
}

function hideError(container) {
    container.find('#errorMessageForDeleting').hide();
    container.removeClass('has-error');
}
