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
    $('#latestStateEditButton').hide();
    $('div.button-panel > form.form-inline div.btn-group > a').addClass("disabled");
    enablePositionAndChannelDropDown();
}

function cancelButtonOnClick() {
    if (isNewState()) {
        var appIdRegex = /applicationId\=[\d]+/;

        var applicationIdParam = appIdRegex.exec(window.location.search)[0];

        window.location.assign(window.location.href.replace(/\?[^]*/, "?" + applicationIdParam));
    }

    $('.stateInput').toggleClass("hidden");
    $('.stateData').toggleClass("hidden");
    $('#latestStateEditButton').show();
    $('div.button-panel > form.form-inline div.btn-group > a').removeClass("disabled");
    $('#create-state-form').validator('destroy');
    disablePositionAndChannelDropDown();
}


function enablePositionAndChannelDropDown() {
    if ($('#stateId').val() == '1') {
        $('#positionSelector').prop('disabled', false)
        $('#channelSelector').prop('disabled', false);
    }
}

function disablePositionAndChannelDropDown() {
    if ($('#stateId').val() == '1') {
        $('#positionSelector').prop('disabled', true)
        $('#channelSelector').prop('disabled', true);
    }
}


$.getJSON('/atsy/secure/positions', { get_param: 'value' }, function(data) {
    $.each(data, function(index, element) {
        $('#positionSelector').append($('<option value="'+element.name+'"></option>').text(element.name));
    });
});

$.getJSON('/atsy/secure/channels', { get_param: 'value' }, function(data) {
    $.each(data, function(index, element) {
        $('#channelSelector').append($('<option value="'+element.name+'"></option>').text(element.name));
    });
});