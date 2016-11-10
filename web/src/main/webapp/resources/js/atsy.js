window.messages = {};
$(document).ready(function () {
    $("#login-form").submit(function (event) {
        $("#userDiv").removeClass('has-error');
        $("#missingUsername").css('display', 'none');
        $("#passwordDiv").removeClass('has-error');
        $("#missingPassword").css('display', 'none');
        $("#globalMessage").css('display', 'none');
        if ($("#name").val() === "") {
            $("#userDiv").addClass('has-error');
            $("#missingUsername").css('display', 'block');
            event.preventDefault();
        }
        if ($("#password").val() === "") {
            $("#passwordDiv").addClass('has-error');
            $("#missingPassword").css('display', 'block');
            event.preventDefault();
        }
    });
});

$(document).ajaxError(function(event, xhr) {
    if ( (xhr.status === 200 || xhr.status === 405) && xhr.getResponseHeader("Content-Type").indexOf("json") === -1 ) {
        window.location.reload();
    }
});

function escapeHTMLFormatter(value, row, index) {
    return new Option(value).innerHTML;
}
