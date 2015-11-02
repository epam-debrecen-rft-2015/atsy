/**
 * Created by Ikantik on 2015.11.04..
 */
$(document).ready(function () {
        $("form button[type=reset], div#add").click(function (event) {
            event.preventDefault();
            $("#position-form").find("input").val('');
            $("#position-form #globalMessage").hide();
        });

        $("#position-form").submit(function (event) {
            event.preventDefault();
            data = {};
            $(this).serializeArray().map(function (x) {
                data[x.name] = x.value;
            });
            $("#position-form #globalMessage").hide();
            $.ajax({
                    url: this.action,
                    method: this.method,
                    contentType: "application/json",
                    data: JSON.stringify(data)
                }
            ).success(function () {
                    $('table#positions').bootstrapTable('refresh');
                    $("#position-form").find("input").val('');
                }).error(function (xhr, status, error) {
                    $("#position-form #globalMessage #error-message").text(xhr.responseText);
                    $("#position-form #globalMessage").show();
                });
        });


    }
);
function positionActionFormatter(value, row, index) {
    return [
        '<a class="edit ml10" href="javascript:void(0)" title="Edit">',
        '<i class="glyphicon glyphicon-edit"></i>',
        '</a>'
    ].join('');
}
window.positionsEvents = {
    'click .edit': function (e, value, row, index) {
        $('#position-form #name').val(row.name);
        $('#position-form #positionId').val(row.positionId);
    }
};