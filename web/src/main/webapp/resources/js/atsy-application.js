$('#modal').modal({
    show: true
});
$('#modal').on('hide.bs.modal', function (e){
    e.preventDefault();
    return false;
});

$.getJSON('/atsy/secure/positions', { get_param: 'value' }, function(data) {
    $.each(data, function(index, element) {
        $('#position').append($('<option value="'+element.id+'">'+element.name+'</option>'));
    });
});

$.getJSON('/atsy/secure/channels', { get_param: 'value' }, function(data) {
    $.each(data, function(index, element) {
        $('#source').append($('<option value="'+element.id+'">'+element.name+'</option>'));
    });
});