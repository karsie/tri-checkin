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
        "tri-cz3ck-models": {
            deps: ["libs/backbone"]
        },
        "tri-cz3ck-tasks-views": {
            deps: ["tri-cz3ck-models", "libs/jquery-ui", "libs/require-css"],
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