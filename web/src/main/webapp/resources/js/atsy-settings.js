var pageContainer;

$(document).ready(function() {
    $.i18n.properties({
        name: 'messages',
        path: '/atsy/resources/i18n/',
        language: 'hu',
        async: true,
        mode:'both',
        callback: function(){  }
    });
});

function SettingsForm() {
    this.init = function (container, validationMessageKey) {
        if (typeof container === 'string') {
            container = $(container);
            pageContainer = container;
        }

        var form = container.find('form'),
            table = container.find('table'),
            addAction = container.find('.add');

        form.find('button[type="reset"]').add(addAction).on('click', function (event) {
            event.preventDefault();
            form.find('input').val('');
            hideError(container);
        });

        form.on('submit', function (event) {
            var $this = $(this);
            event.preventDefault();
            if ($this.find(".name").val() === "") {
                showError($this, window.messages[validationMessageKey]);
            } else {
                data = {};
                $this.serializeArray().map(function (x) {
                    data[x.name] = x.value;
                });
                hideError($this);
                $.ajax({
                    url: $this.attr('action'),
                    method: $this.attr('method'),
                    contentType: 'application/json',
                    data: JSON.stringify(data),
                    dataType: "json"
                }).done(function () {
                    if (table instanceof $) {
                        table.bootstrapTable('refresh');
                    }
                    $this.find('input').val('');
                }).error(function (xhr) {
                    var response = xhr.responseJSON;
                    showError($this, response.errorMessage);
                });
            }
        });

        function showError(container, message) {
            container.find('#errorMessageForCreating').text(message);
            container.find('#errorMessageForCreating').show();
            container.addClass('has-error');
        }

        function hideError(container) {
            container.find('#errorMessageForCreating').hide();
            container.removeClass('has-error');
        }
    }
};

$(function () {
    new SettingsForm().init('#positions_section','settings.positions.error.empty');
    new SettingsForm().init('#channels_section','settings.channels.error.empty');
});


function actionFormatter(value, row, index) {
    return [
        '<a class="edit ml10 little-space" href="javascript:void(0)" title="Edit">',
        '<i class="glyphicon glyphicon-edit"></i>',
        '</a>',

        '<a class="remove ml10 little-space" href="javascript:void(0)" title="Remove">',
        '<i class="glyphicon glyphicon-remove"></i>',
         '</a>',
    ].join('');
}

window.positionsEvents = {
    'click .edit': function (e, value, row) {
        $('#position-form #position_name').val(row.name);
        $('#position-form #positionId').val(row.id);
    }
};

window.channelsEvents = {
    'click .edit': function (e, value, row) {
        $('#channel-form #channel_name').val(row.name);
        $('#channel-form #channelId').val(row.id);
    },
    'click .remove': function (e, value, row) {

         var options = {
            size: 'small',
            message: $.i18n.prop('do.you.want.to.delete') + " (" + row.name + ")",
            animate: true,
            onEscape: function() {},
            buttons: {

                danger: {
                    label: $.i18n.prop('cancel.button'),
                    className: "btn-danger",
                    callback: function() {}
                },

                success: {
                      label: $.i18n.prop('delete.button'),
                      className: "btn-success",
                      callback: function() {
                          $.ajax({
                              type: 'DELETE',
                              url: "./delete?" + 'channelId=' + row.id,
                              cache: false,
                          }).done(function (container) {
                              var table = pageContainer.find('table');
                              if (table instanceof $) {
                                  table.bootstrapTable('refresh');
                              }
                              hideError(pageContainer);
                          }).error(function (xhr) {
                              var response = xhr.responseJSON;
                              showError(pageContainer, $.i18n.prop('selected.channel.not.found'));
                          });
                      }
                 },
            },
         }
        bootbox.dialog(options);

        function showError(container, message) {
            container.find('#errorMessageForDeleting').text(message);
            container.find('#errorMessageForDeleting').show();
            container.addClass('has-error');
        }

        function hideError(container) {
            container.find('#errorMessageForDeleting').hide();
            container.removeClass('has-error');
        }
    }
};