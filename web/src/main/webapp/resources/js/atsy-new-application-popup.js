  function validateApplicationSourceAndPositionDropDowns() {
      var positionIsValid = positionValidator();
      var applicationSourceIsValid = applicationSourceValidator();
      return (positionIsValid && applicationSourceIsValid);
  }

  function positionValidator() {
      var ddl = document.getElementById('position');
      if (ddl.selectedIndex === 0) {
          document.getElementById('position_error').innerHTML = ddl.options[ddl.selectedIndex].value;
          return false;
      }

      document.getElementById('position_error').innerHTML = '';
      return true;
  }

  function applicationSourceValidator() {
      var ddl = document.getElementById('channel');

      if (ddl.selectedIndex === 0) {
          document.getElementById('application_source_error').innerHTML = ddl.options[ddl.selectedIndex].value;
          return false;
      }

      document.getElementById('application_source_error').innerHTML = '';
      return true;
  }
