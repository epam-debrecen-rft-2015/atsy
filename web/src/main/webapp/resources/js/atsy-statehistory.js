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

    $('.stateInput').toggleClass("hidden");
    $('.stateData').toggleClass("hidden");
    $('.stateDataPostfix').toggleClass("hidden");
    $('#latestStateEditButton').hide();
    $('div.button-panel > form.form-inline div.btn-group > a').addClass("disabled");
}

function cancelButtonOnClick() {
    if (isNewState()) {
        var appIdRegex = /applicationId\=[\d]+/;

        var applicationIdParam = appIdRegex.exec(window.location.search)[0];

        window.location.assign(window.location.href.replace(/\?[^]*/, "?" + applicationIdParam));
    }

    $('.stateInput').toggleClass("hidden");
    $('.stateData').toggleClass("hidden");
    $('.stateDataPostfix').toggleClass("hidden");
    $('#latestStateEditButton').show();
    $('div.button-panel > form.form-inline div.btn-group > a').removeClass("disabled");
    $('#create-state-form').validator('destroy');
}

$.getJSON('/atsy/secure/positions', { get_param: 'value' }, function(data) {
    var currentPositionId = parseInt($('#positionSelector').attr("data-currentPositionId"));
    $.each(data, function(index, element) {
        if (element.id !== currentPositionId) {
            $('#positionSelector').append($('<option value="'+element.id+'"></option>').text(element.name));
        }
    });
});

$.getJSON('/atsy/secure/channels', { get_param: 'value' }, function(data) {
    var currentChannelName = $('#channelSelector').attr("data-currentChannelName");
    $.each(data, function(index, element) {
        if (element.name !== currentChannelName) {
            $('#channelSelector').append($('<option value="'+element.name+'"></option>').text(element.name));
        }
    });
});