$(document).ready(function() {

    positionRef = $('#position');
    positionErrorRef = $('#position_error')
    applicationSourceRef = $('#channel');
    applicationSourceErrorRef = $('#application_source_error');


    $("#application-create-form").submit(function() {
        return validateApplicationSourceAndPositionDropDowns();
    });

    $("#save_new_apply_button").click(function(){
        validateApplicationSourceAndPositionDropDowns();
    });

    $('#position').change(function(){
        positionValidator();
     });

     $('#channel').change(function() {
        applicationSourceValidator();
     });


    function validateApplicationSourceAndPositionDropDowns() {
        positionIsValid = positionValidator();
        applicationSourceIsValid = applicationSourceValidator();
        return (positionIsValid && applicationSourceIsValid);
    }

    function positionValidator() {
        if (positionRef.prop('selectedIndex') === 0) {
            $('#positionDiv').addClass('has-error');
            positionErrorRef.text(positionRef.val());
            return false;
        }
        $('#positionDiv').removeClass('has-error');
        positionErrorRef.text('');
        return true;
       }

    function applicationSourceValidator() {
        if (applicationSourceRef.prop('selectedIndex') === 0) {
            $('#sourceDiv').addClass('has-error');
            applicationSourceErrorRef.text(applicationSourceRef.val());
            return false;
        }
        $('#sourceDiv').removeClass('has-error');
        applicationSourceErrorRef.text('');
        return true;
       }
});




