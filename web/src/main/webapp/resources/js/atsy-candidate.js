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
    });

    function fillInFormFromLocationHash() {
        var nth = 0;
        form[0].reset();
        var data = location.hash.replace(/["/"]/g, function (match) {
            nth++;
            return (nth % 2 === 1) ? ";" : match;
        }).replace("#", "").split("/").map(function (x) {
            x = x.split(";");
            form.find('#filter_' + x[0]).val(x[1]);
        });
    }

    function locationHashChanged() {
        fillInFormFromLocationHash();
        table.bootstrapTable('refresh');
    }

    var fieldToSortAttributeMap = {
      "email": "candidate.email",
      "phone": "candidate.phone",
      "positions": "position.name",
      "name": "candidate.name"
    };

    function translateSortName(fieldName) {
        var sortName = fieldToSortAttributeMap[fieldName];
        return sortName ? sortName : params.sortName;
    }

    function extractFilterFromForm() {
        var filterFields = {};
        form.find("input").each(function(index, element){
          var $this = $(this);
          if($this.val()) {
            filterFields[$this.attr("name")] = $this.val();
          }
        });
        return JSON.stringify(filterFields);
    }

    fillInFormFromLocationHash();
    $('.table').bootstrapTable({
        queryParams: function (params) {
          params.sortName = translateSortName(params.sortName);
          params.filter = extractFilterFromForm();

          return params;
        },
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
        },
        onClickRow: function (row, $element) {
          window.location.href = "candidate/details/" + row.id;
        }
    });

    window.actionsFormatter = function(value, row, index) {
        return [
            '<a class="remove ml10 little-space" href="javascript:void(0)" title="', $.i18n.prop('common.remove.js'), '">',
            '<i class="glyphicon glyphicon-remove"></i>',
            '</a>',
        ].join('');
    };

    window.candidatesEvents = {
        'click .remove': function (e, value, row) {
             var container = $('#candidates_table');
             var options = getOptions('question.delete.candidate.js', 'selected.candidate.not.found.js', row, container, "candidate");
             e.stopImmediatePropagation();
             bootbox.dialog(options);
        }
    };
});
