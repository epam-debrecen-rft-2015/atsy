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
