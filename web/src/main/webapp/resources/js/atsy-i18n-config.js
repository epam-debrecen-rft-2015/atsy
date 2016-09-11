$(document).ready(function() {
    $.i18n.properties({
        name: 'messages',
        path: '/atsy/resources/i18n/',
        language: 'hu',
        async: true,
        mode:'both',
        callback: function() {}
    });
});