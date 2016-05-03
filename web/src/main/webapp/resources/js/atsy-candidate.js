/**
 * Created by tothd on 2015. 11. 19..
 */
$(document).ready(function () {
    var form = $("#searchCandidate"),
        table = $("#candidates");
    window.onhashchange = locationHashChanged;


    form.submit(function (event) {
        var values = ["name", "email", "phone", "position"];
        var $this = $(this);
        values = values.map(function (selector) {
            return [selector, $this.find("[name=" + selector + "]").val()];
        }).filter(function (value) {
            return value[1].length != 0;
        }).map(function (value) {
            return value[0] + "/" + value[1];
        }).join("/");
        location.hash = values;

        event.preventDefault();
        return false;
    })

    function locationHashChanged() {
        var nth = 0;
        result = {};
        form[0].reset();
        var data = location.hash.replace(/["/"]/g, function (match) {
            nth++;
            return (nth % 2 === 1) ? ";" : match;
        }).replace("#", "").split("/").map(function (x) {
            x = x.split(";");
            form.find('#filter_' + x[0]).val(x[1]);
            result[x[0]] = x[1];
        });
        table.bootstrapTable('refresh', {
            url: form.attr('action') + "?filter=" + encodeURIComponent(JSON.stringify(result))
        });
    }

    locationHashChanged();
});

function actionFormatter(value, row, index) {
    return [
        '<a class="edit ml10" href="candidate/' + row.id + '" title="Edit">',
        '<i class="glyphicon glyphicon-edit"></i>',
        '</a>'
    ].join('');
}
