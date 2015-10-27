$(document).ready(function(){
    $("form").submit(function(event){
        if ( $( "#name" ).val() === "" || $( "#password" ).val() === "") {
            $(".form-group").addClass('has-error');
            return;
        }
    });
});
