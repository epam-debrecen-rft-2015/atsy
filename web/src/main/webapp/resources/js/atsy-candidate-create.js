/**
 * Created by mates on 2015. 11. 18..
 */

    $("#candidate-create-form").submit(function (event) {
        var $this = $(this);
        event.preventDefault();
        var candidateId = $('#candidateId').val(),
        name = $('#name').val(),
        referer = $('#referer').val(),
        email = $('#email').val(),
        languageSkill = $('#languageSkill option:selected').attr("value").toString(),
        phone = $('#phone').val(),
        description = $('#description').val();
        // make a POST ajax call
        $.ajax({
            url: $this.attr('action'),
            method: $this.attr('method'),
            contentType: 'application/json',
            data: JSON.stringify({
                name: name,
                referer:referer,
                email:email,
                languageSkill:languageSkill,
                phone:phone,
                description:description
            })
        });
    });

var jsonloc = "http://localhost:8080/atsy/secure/candidate?ID=1";

$.getJSON(jsonloc, function (json) {

    var candidateId = json.candidateId;
    var name = json.name;
    var email = json.email;
    var phone = json.phone;
    var referer = json.referer;
    var languageSkill = json.languageSkill;
    var description = json.description;

    $(document).ready(function () {
        $('#id').val(candidateId);
        $('#name').val(name);
        $('#email').val(email);
        $('#phone').val(phone);
        $('#referer').val(referer);
        $('#languageSkill').val(languageSkill);
        $('#description').val(description);
    });

});
