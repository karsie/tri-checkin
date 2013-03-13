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
        "tri-cz3ck-tasks-views": {
            deps: ["tri-cz3ck-models", "libs/jquery-ui-min", "libs/require-css"],
            init: function() {
                requireCss("tri-cz3ck");
            }
        }
    }
});

define(["tri-cz3ck-tasks-views"], function () {
    var tasks = new TaskList();
    $(function() {
        var tasksList = new TaskListView({id: "tasks", collection: tasks});
        tasks.fetch();
    });
});