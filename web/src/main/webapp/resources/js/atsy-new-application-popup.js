  function validateApplicationSourceAndPositionDropDowns() {
      var positionIsValid = positionValidator();
      var applicationSourceIsValid = applicationSourceValidator();
      return (positionIsValid && applicationSourceIsValid);
  }

  function positionValidator() {
      var ddl = $('#position');
      if (ddl.prop('selectedIndex') === 0) {
          $('#position_error').html(ddl.val());
          return false;
      }
      $('#position_error').html('');
      return true;
  }

  function applicationSourceValidator() {
      var ddl = $('#channel');
      if (ddl.prop('selectedIndex') === 0) {
          $('#application_source_error').html(ddl.val());
          return false;
      }
      $('#application_source_error').html('');
      return true;
  }
