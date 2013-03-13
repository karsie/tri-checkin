requirejs.config({
    shim: {
        "libs/backbone-min": {
            deps: ["libs/underscore-min", "libs/jquery-min"],
            exports: "Backbone"
        },
        "libs/jquery-ui-min": {
            deps: ["libs/jquery-min", "libs/require-css"],
            init: function () {
                requireCss("libs/jquery-ui-min");
            }
        },
        "tri-cz3ck-models": {
            deps: ["libs/backbone-min"]
        },
        "tri-cz3ck-panel-views": {
            deps: ["tri-cz3ck-models", "libs/jquery-ui-min", "libs/require-css"],
            init: function() {
                requireCss("tri-cz3ck");
                requireCss("libs/themes/fff/css/fff.icon.core");
                requireCss("libs/themes/fff/css/fff.icon.icons.min");
            }
        }
    }
});

define(["tri-cz3ck-panel-views"], function () {
    var all = new Employees();
    var outside = new Employees();
    var inside = new Employees();

    $(function () {
        var genericSortOptions = [
            { attribute: "first", text: "Voornaam" },
            { attribute: "last", text: "Achternaam" }
        ];
        var clearOptions = { label: "Iedereen uitchecken", text: false, icons: { primary: "fff-icon-resultset-last" } };
        var leftView = new EmployeeListView({ id: "ctleft", title: "Aanwezig", collection: inside, status: "IN", pageSize: 8, buttons: { sort: true, sortOptions: genericSortOptions, info: true, clear: false, clearOptions: clearOptions } });
        leftView.clear(function () {
            rightView.suspendRender();
            leftView.suspendRender();
            inside.allOut();
            rightView.resumeRender();
            leftView.resumeRender();
        });
        var rightView = new EmployeeListView({ id: "ctright", title: "Buiten de deur", collection: outside, status: "OUT", pageSize: 8, buttons: { sort: true, sortOptions: genericSortOptions } });

        leftView.options.targetView = rightView;
        rightView.options.targetView = leftView;

        all.fetch({success: function() {
            unbindSubsets();
            filterSubsets();
            bindSubsets();
        }});

        setInterval(function() {
            rightView.disable();
            leftView.disable();
            all.fetch();
            rightView.enable();
            leftView.enable();
        }, 2 * 60 * 60 * 1000);
    });

    function filterSubsets() {
        outside.reset(all.filterActiveAndStatus(true, "OUT"));
        inside.reset(all.filterActiveAndStatus(true, "IN"));
    }

    function unbindSubsets() {
        outside.unbind("change", filterSubsets);
        inside.unbind("change", filterSubsets);
    }

    function bindSubsets() {
        outside.bind("change", filterSubsets);
        inside.bind("change", filterSubsets);
    }
});
