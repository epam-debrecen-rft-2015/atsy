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
                candidateId: candidateId,
                name: name,
                referer:referer,
                email:email,
                languageSkill:languageSkill,
                phone:phone,
                description:description
            })
        });
    });

var jsonloc = "http://localhost:8080/atsy/secure/candidate?ID=1",
candidateId,
name,
email,
phone,
referer,
languageSkill,
description;

$.getJSON(jsonloc, function (json) {

    candidateId = json.candidateId;
    name = json.name;
    email = json.email;
    phone = json.phone;
    referer = json.referer;
    languageSkill = json.languageSkill;
    description = json.description;

    $(document).ready(function () {
        if (candidateId) {
            $("#enableModify").show();
            $('.input').attr("disabled",true);
            $('#cancelButton').hide();
            $('#saveButton').hide();

            $('#candidateId').val(candidateId);
            $('#name').val(name);
            $('#email').val(email);
            $('#phone').val(phone);
            $('#referer').val(referer);
            $('#languageSkill').val(languageSkill);
            $('#description').val(description);
        }
    });

});

if (!candidateId) {
    $("#enableModify").hide();
    $('.input').attr("disabled",false);
    $('#cancelButton').show();
    $('#saveButton').show();

    $('#candidateId').val("");
    $('#name').val("");
    $('#email').val("");
    $('#phone').val("");
    $('#referer').val("");
    $('#languageSkill').val(0);
    $('#description').val("");

    $("#saveButton").button().click(function(){
        window.location.href = 'welcome';
    });
    $("#cancelButton").button().click(function(){
        window.location.href = 'welcome';
    });
}

$("#enableModify").button().click(function(){
    var candidateIdOld = $('#candidateId').val(),
        nameOld = $('#name').val(),
        refererOld = $('#referer').val(),
        emailOld = $('#email').val(),
        languageSkillOld = $('#languageSkill option:selected').attr("value").toString(),
        phoneOld = $('#phone').val(),
        descriptionOld = $('#description').val();

    $('.input').attr("disabled",false);
    $('#enableModify').hide();
    $('#cancelButton').show();
    $('#saveButton').show();

    $("#cancelButton").button().click(function(){
        $("#enableModify").show();
        $('.input').attr("disabled",true);
        $('#cancelButton').hide();
        $('#saveButton').hide();

        $('#candidateId').val(candidateIdOld);
        $('#name').val(nameOld);
        $('#email').val(emailOld);
        $('#phone').val(phoneOld);
        $('#referer').val(refererOld);
        $('#languageSkill').val(languageSkillOld);
        $('#description').val(descriptionOld);
    });

    $("#saveButton").button().click(function(){
        $("#enableModify").show();
        $('.input').attr("disabled",true);
        $('#cancelButton').hide();
        $('#saveButton').hide();
    });
});