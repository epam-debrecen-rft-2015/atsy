/**
 * Created by mates on 2015. 11. 18..
 */

$('#candidate-create-form').validator().on('submit', function (e) {
    if (e.isDefaultPrevented()) {
        alert('form is not valid');
    } else {
        var $this = $(this);
        e.preventDefault();
        var candidateId = $('#candidateId').val(),
            name = $('#name').val(),
            referer = $('#referer').val(),
            email = $('#email').val(),
            languageSkill = $('#languageSkill option:selected').attr("value").toString(),
            phone = $('#phone').val(),
            description = $('#description').val();
        $.ajax({
            url: $this.attr('action'),
            method: $this.attr('method'),
            contentType: 'application/json',
            data: JSON.stringify({
                candidateId: candidateId,
                name: name,
                referer: referer,
                email: email,
                languageSkill: languageSkill,
                phone: phone,
                description: description
            })
        }).done(function (xhr) {
            window.location = $this.attr('action')+ '/' + xhr;
        }).error(function (xhr) {
            showError(xhr.responseText);
        });
    }
});
function showError(message) {
    $("#candidate-create-form").find('.globalMessage .error-message').text(message);
    $("#candidate-create-form").find('.globalMessage').show();
    $("#candidate-create-form").addClass('has-error');
}

$("#enableModify").button().click(function () {
    $("#candidate_creation").removeClass("display");
    return false;
});
$("#cancelButtonModify").click(function () {
    $("#candidate_creation").addClass("display");
})