$(document).ready(function() {
    $('#login-form').formValidation({
        framework: 'bootstrap',
        fields: {
            username: {
                message: 'The username is not valid',
                validators: {
                    remote: {
                        url: '/path/to/backend/',
                        // Send { username: 'its value', email: 'its value' } to the back-end
                        data: function(validator, $field, value) {
                            return {
                                password: validator.getFieldElements('password').val()
                            };
                        },
                        message: 'The username is not available',
                        type: 'POST'
                    }
                }
            },
            password: {
                validators: {
                    remote: {
                        url: '/path/to/backend/',
                        // Send { email: 'its value', username: 'its value' } to the back-end
                        data: function(validator, $field, value) {
                            return {
                                username: validator.getFieldElements('username').val()
                            };
                        },
                        message: 'asdf',
                        type: 'POST'
                    }
                }
            }
        }
    });
});