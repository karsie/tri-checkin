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
        'tri-cz3ck-models${minifySuffix}': {
            deps: ['libs/backbone-min', 'libs/backbone.subset${minifySuffix}']
        },
        'tri-cz3ck-panel-views${minifySuffix}': {
            deps: ['tri-cz3ck-models${minifySuffix}', 'libs/jquery-ui-min', 'libs/require-css${minifySuffix}'],
            init: function() {
                requireCss("tri-cz3ck${minifySuffix}");
            }
        }
    }
});

define(['tri-cz3ck-panel-views${minifySuffix}'], function () {
    var all = new Employees;
    var outside = new EmployeesByStatus(undefined, { parent: all, status: 'OUT' });
    var inside = new EmployeesByStatus(undefined, { parent: all, status: 'IN' });

    $(function () {
        var genericSortOptions = [
            { attribute: 'first', text: 'Voornaam' },
            { attribute: 'last', text: 'Achternaam' }
        ];
        var leftView = new EmployeeListView({ id: 'ctleft', collection: inside, status: 'IN', pageSize: 8, buttons: { sort: true, sortOptions: genericSortOptions, clear: true, clearOptions: { label: 'Iedereen uitchecken', text: false, icons: { primary: "ui-icon-arrowthickstop-1-e" } } } });
        leftView.clear(function () {
            rightView.suspendRender();
            leftView.suspendRender();
            inside.allOut();
            rightView.resumeRender();
            leftView.resumeRender();
        });
        var rightView = new EmployeeListView({ id: 'ctright', collection: outside, status: 'OUT', pageSize: 8, buttons: { sort: true, sortOptions: genericSortOptions } });

        leftView.options.targetView = rightView;
        rightView.options.targetView = leftView;

        all.fetch();
    });
});
