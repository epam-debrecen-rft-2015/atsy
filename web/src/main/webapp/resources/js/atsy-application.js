$('#modal').modal({
    show: true
});
$('#modal').on('hide.bs.modal', function (e){
    e.preventDefault();
    return false;
});

$.getJSON('/atsy/secure/positions', { get_param: 'value' }, function(data) {
    $.each(data, function(index, element) {
       $('#position').append($('<option value="'+element.id+'"></option>').text(element.name));
    });
});

$.getJSON('/atsy/secure/channels', { get_param: 'value' }, function(data) {
    $.each(data, function(index, element) {
        $('#channel').append($('<option value="'+element.id+'"></option>').text(element.name));
    });
});