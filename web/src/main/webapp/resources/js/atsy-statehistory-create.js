$(document).ready(function() {
  $("#create-state-form").on('submit', (function(event) {
      if (event.isDefaultPrevented()){
        //nothing
      } else {
        console.log("calling AJAX..")

        doAjax();

        return false;
      }
  }));
});

function doAjax() {

      var form = $("#create-state-form");
      var json = {};

      $.ajax({
        url: form.attr('action'),
        method: form.attr('method'),
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify(json),
        success: function(data) {
          console.log("SUCCESS");
        },
        error: function(e) {
          console.log("ERROR");
        },
        done: function(e) {
          console.log("DONE");
        }
      });
}