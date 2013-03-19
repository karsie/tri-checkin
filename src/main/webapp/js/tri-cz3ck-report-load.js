requirejs.config({
    shim: {
        "libs/backbone": {
            deps: ["libs/underscore", "libs/jquery"],
            exports: "Backbone"
        },
        "libs/jquery-ui": {
            deps: ["libs/jquery", "libs/require-css"],
            init: function () {
                requireCss("libs/jquery-ui");
            }
        },
        "libs/jcanvas": {
            deps: ["libs/jquery"]
        },
        "tri-cz3ck-models": {
            deps: ["libs/backbone"]
        },
        "tri-cz3ck-report-views": {
            deps: ["tri-cz3ck-models", "libs/jquery-ui", "libs/jcanvas", "libs/require-css"],
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
            weekReport.filterChange(function (year, week, showInactive) {
                weekReportList.fetch({data: {year: year, week: week}, success: function() {
                    employees.fetch({success: function() {
                        if (!showInactive) {
                            employees.reset(employees.filterActive(true));
                        }
                    }});
                }});
            });
        }

        if ($("#report_month").length !== 0) {
            var monthReportList = new MonthReportList();
            var monthReport = new MonthReportListView({id: "report_month", collection: employees, reportDetails: monthReportList});
            monthReport.filterChange(function (year, month, showInactive) {
                monthReportList.fetch({data: {year: year, month: month}, success: function() {
                    employees.fetch({success: function() {
                        if (!showInactive) {
                            employees.reset(employees.filterActive(true));
                        }
                    }});
                }});
            });
        }
    });
});