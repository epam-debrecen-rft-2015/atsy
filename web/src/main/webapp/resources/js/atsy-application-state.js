$.urlParam = function(name){
	var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
	return results[1] || 0;
}

var jsonURL = 'http://localhost:8080/atsy/secure/applications_states/' + $.urlParam('applicationId');

var positionName = null;
$.getJSON(jsonURL, function(result)
{
    positionName = result[0].position.name;
    $('#positionName').text(positionName);
    $.each(result,function(i, value){
        $('#stateList').append(
        '<div class="page-header">' +
                '<h4>' + value.stateType + '</h4>' +
        '</div>' +

        '<form class="form-horizontal">' +
                '<div class="form-group">' +
                    '<label for="name" class="control-label col-sm-4">Dátum!!</label>' +
                    '<div class="col-sm-8">' +
                        '<p class="form-control-static">' + value.creationDate + '</p>' +
                    '</div>' +
                '</div>' +
                '<div class="form-group">' +
                    '<label for="name" class="control-label col-sm-4">Megjegyzés</label>' +
                    '<div class="col-sm-8">' +
                        '<p class="form-control-static">' + value.description + '</p>' +
                    '</div>' +
                '</div>' +
            '</form>'
        );
            //'<p>'+ value.id + '</p>');
    });
});