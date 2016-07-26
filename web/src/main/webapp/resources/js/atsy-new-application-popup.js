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
            positionErrorRef.html(positionRef.val());
            return false;
        }
        positionErrorRef.html('');
        return true;
       }

    function applicationSourceValidator() {
        if (applicationSourceRef.prop('selectedIndex') === 0) {
            applicationSourceErrorRef.html(applicationSourceRef.val());
            return false;
        }
        applicationSourceErrorRef.html('');
        return true;
       }
});




