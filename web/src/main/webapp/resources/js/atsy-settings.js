/**
 * Created by Ikantik on 2015.11.04..
 */

function SettingsForm() {
    this.init = function (container) {
        if (typeof container === 'string') {
            container = $(container);
        }

        var form = container.find('form'),
            table = container.find('table'),
            addAction = container.find('.add');

        form.find('button[type="reset"]').add(addAction).on('click', function (event) {
            event.preventDefault();
            form.find('input').val('');
            form.find('#globalMessage').hide();
        });

        form.on('submit', function (event) {
            var $this = $(this);
            event.preventDefault();
            data = {};
            $this.serializeArray().map(function (x) {
                data[x.name] = x.value;
            });
            $this.find('#globalMessage').hide();
            $.ajax({
                url: $this.attr('action'),
                method: $this.attr('method'),
                contentType: 'application/json',
                data: JSON.stringify(data)
            }).done(function () {
                if (table instanceof $) {
                    table.bootstrapTable('refresh');
                }
                $this.find('input').val('');
            }).error(function (xhr) {
                $this.find('#globalMessage #error-message').text(xhr.responseText);
                $this.find('#globalMessage').show();
            });
        });
    }
};

$(function () {
    new SettingsForm().init('#positions_section');
});

function positionActionFormatter(value, row, index) {
    return [
        '<a class="edit ml10" href="javascript:void(0)" title="Edit">',
        '<i class="glyphicon glyphicon-edit"></i>',
        '</a>'
    ].join('');
}

window.positionsEvents = {
    'click .edit': function (e, value, row) {
        $('#position-form #name').val(row.name);
        $('#position-form #positionId').val(row.positionId);
    }
};