requirejs.config({
    shim: {
        'libs/backbone-min': {
            deps: ['libs/underscore-min', 'libs/jquery-min'],
            exports: 'Backbone'
        },
        'libs/backbone.subset${minifySuffix}': {
            deps: ['libs/backbone-min']
        },
        'libs/jquery-ui-min': {
            deps: ['libs/jquery-min', 'libs/require-css${minifySuffix}'],
            init: function () {
                requireCss("libs/jquery-ui-min");
            }
        },
        'libs/jcanvas-min': {
            deps: ['libs/jquery-min']
        },
        'tri-cz3ck-models${minifySuffix}': {
            deps: ['libs/backbone-min', 'libs/backbone.subset${minifySuffix}']
        },
        'tri-cz3ck-report-views${minifySuffix}': {
            deps: ['tri-cz3ck-models${minifySuffix}', 'libs/jquery-ui-min', 'libs/jcanvas-min', 'libs/require-css${minifySuffix}'],
            init: function() {
                requireCss("tri-cz3ck${minifySuffix}");
            }
        }
    }
});

define(['tri-cz3ck-report-views${minifySuffix}'], function () {
    var reportList = new WeekReportDataList;
    var employees = new Employees;
    $(function() {
        var report = new WeekReportListView({id: 'report', collection: employees, reportDetails: reportList });
        /*report.filterChange(function (year, week) {
            reportList.fetch({data: {year: year, week: week}, success: function() {
                employees.fetch();
            }});
        });*/

        reportList.fetch({success: function() {
            employees.fetch();
        }});
    });
});