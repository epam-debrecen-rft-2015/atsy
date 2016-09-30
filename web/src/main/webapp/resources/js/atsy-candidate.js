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

    var additionalOptions = {
      customSort: function(sortName, sortOrder) {
        var defaultComparator;

        if (sortOrder === "asc") {
         defaultComparator = function(a, b, field) { return a[field] > b[field]; };
        } else {
         defaultComparator = function(a, b, field) { return a[field] < b[field]; };
        }

        this.data.sort(function(a, b) {
            return defaultComparator(a, b, a[sortName] !== b[sortName] ? sortName : "name");
        });
      }
    };

    table.bootstrapTable('refreshOptions', additionalOptions);

    locationHashChanged();
});

$('.table').bootstrapTable({
    queryParams: function (p) {
        var sortName = p.sortName;

        if (sortName == "email") {
          sortName = "candidate.email";
        } else if (sortName == "phone") {
          sortName = "candidate.phone";
        } else if (sortName == "positions") {
          sortName = "position.name";
        } else {
          sortName = "candidate.name";
        }
        p.sortName = sortName
        return  p
      },

      onClickRow: function (row, $element) {
        window.location.href="candidate/" + row.id;
      }
    }
});

function actionsFormatter(value, row, index) {
    return [
             '<a class="remove ml10 little-space" href="javascript:void(0)" title="Remove">',
                '<i class="glyphicon glyphicon-remove"></i>',
             '</a>',
            ].join('');
};

window.candidatesEvents = {
    'click .remove': function (e, value, row) {
         var container = $('#candidates_table');
         var options = getOptions('question.delete.candidate.js', 'selected.candidate.not.found.js', row, container, "candidates");
         e.stopImmediatePropagation();
         bootbox.dialog(options);
    }
};