var positionDefaultValueInEng = 'Please choose a position';
var applicationSourceDefaultValueInEng = 'Please choose an application source';
var positionDefaultValueInHun = 'Kérem válasszon egy pozíciót';
var applicationSourceDefaultValueInHun = 'Kérem válasszon egy jelentkezési forrást';

function validateApplicationSourceAndPositionDropDowns() {
    var positionIsValid = positionValidator();
    var applicationSourceIsValid = applicationSourceValidator();
    return (positionIsValid && applicationSourceIsValid);
}

function positionValidator() {
    var ddl = document.getElementById('position');
    var selectedValue = ddl.options[ddl.selectedIndex].value;

    if (selectedValue == positionDefaultValueInEng) {
        document.getElementById('position_error').innerHTML = positionDefaultValueInEng;
        return false;
    }

    if (selectedValue == positionDefaultValueInHun) {
        document.getElementById('position_error').innerHTML = positionDefaultValueInHun;
        return false;
    }

    document.getElementById('position_error').innerHTML = '';
    return true;
}

function applicationSourceValidator() {
    var ddl = document.getElementById('channel');
    var selectedValue = ddl.options[ddl.selectedIndex].value;

    if (selectedValue == applicationSourceDefaultValueInEng) {
        document.getElementById('application_source_error').innerHTML = applicationSourceDefaultValueInEng;
         return false;
    }

    if (selectedValue == applicationSourceDefaultValueInHun) {
        document.getElementById('application_source_error').innerHTML = applicationSourceDefaultValueInHun;
         return false;
    }

    document.getElementById('application_source_error').innerHTML = '';
    return true;
}