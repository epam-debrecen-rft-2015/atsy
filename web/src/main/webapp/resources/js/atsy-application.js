$('#modal').modal({
    show: true
});
$('#modal').on('hide.bs.modal', function (e){
    e.preventDefault();
    return false;
});

$.getJSON('/atsy/secure/positions', { get_param: 'value' }, function(data) {
    $.each(data, function(index, element) {
        $('#position').append($('<option>', {text: element.name}));
    });
});