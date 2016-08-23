$(function() {
  if (isNewState()) {
    editLatestStateOnClick();
  }
});

function isNewState() {
  var queryStr = window.location.search;

  return queryStr.match(/state\=/);
}

function editLatestStateOnClick() {
    $('.stateInput').show();
    $('.stateData').hide();
    $('#latestStateEditButton').hide();
    $('div.button-panel > form.form-inline div.btn-group > a').addClass("disabled");
}

function cancelButtonOnClick() {
    if (isNewState()) {
        var appIdRegex = /applicationId\=[\d]+/;

        var applicationIdParam = appIdRegex.exec(window.location.search)[0];

        window.location.assign(window.location.href.replace(/\?[^]*/, "?" + applicationIdParam));
    }

    $('.stateInput').hide();
    $('.stateData').show();
    $('#latestStateEditButton').show();
    $('div.button-panel > form.form-inline div.btn-group > a').removeClass("disabled");
    $('#create-state-form').validator('destroy');
}

$.getJSON('/atsy/secure/positions', { get_param: 'value' }, function(data) {
    $.each(data, function(index, element) {
        $('#position').append($('<option value="'+element.name+'">'+element.name+'</option>'));
    });
});

$.getJSON('/atsy/secure/channels', { get_param: 'value' }, function(data) {
    $.each(data, function(index, element) {
        $('#channel').append($('<option value="'+element.name+'">'+element.name+'</option>'));
    });
});