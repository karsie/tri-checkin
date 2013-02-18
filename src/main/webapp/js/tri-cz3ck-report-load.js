requirejs.config({
    shim: {
        "libs/backbone-min": {
            deps: ["libs/underscore-min", "libs/jquery-min"],
            exports: "Backbone"
        },
        "libs/backbone.subset": {
            deps: ["libs/backbone-min"]
        },
        "libs/jquery-ui-min": {
            deps: ["libs/jquery-min", "libs/require-css"],
            init: function () {
                requireCss("libs/jquery-ui-min");
            }
        },
        "libs/jcanvas-min": {
            deps: ["libs/jquery-min"]
        },
        "tri-cz3ck-models": {
            deps: ["libs/backbone-min", "libs/backbone.subset"]
        },
        "tri-cz3ck-report-views": {
            deps: ["tri-cz3ck-models", "libs/jquery-ui-min", "libs/jcanvas-min", "libs/require-css"],
            init: function() {
                requireCss("tri-cz3ck");
            }
        }
    }
});

define(["tri-cz3ck-report-views"], function () {
    var employees = new Employees(null, {sortAttribute: "first"});
    $(function() {
        if ($("#report_week").length !== 0) {
            var weekReportList = new WeekReportList();
            var weekReport = new WeekReportListView({id: "report_week", collection: employees, reportDetails: weekReportList});
            weekReport.filterChange(function (year, week) {
                weekReportList.fetch({data: {year: year, week: week}, success: function() {
                    employees.fetch();
                }});
            });
        }

        if ($("#report_month").length !== 0) {
            var monthReportList = new MonthReportList();
            var monthReport = new MonthReportListView({id: "report_month", collection: employees, reportDetails: monthReportList});
            monthReport.filterChange(function (year, month) {
                monthReportList.fetch({data: {year: year, month: month}, success: function() {
                    employees.fetch();
                }});
            });
        }
    });
});