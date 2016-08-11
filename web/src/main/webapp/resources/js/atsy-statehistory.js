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
}
