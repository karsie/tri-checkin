// The number of milliseconds in one day
var ONE_DAY = 1000 * 60 * 60 * 24;
var Today = new Date();

var Employee = Backbone.Model.extend({
    urlRoot: contextRoot + "/checkin/rest/person",
	getShortNotation: function(attribute) {
		return this.get(attribute);
	},

    isBirthday: function() {
        var birthDate = this.get("birthDate");
        if (birthDate !== null) {
            return Today.toDateString() == new Date(birthDate).toDateString();
        }
        return false;
    },

    isNewEmployee: function() {
        var startDate = this.get("startDate");
        if (startDate !== null) {
            return Math.round(Math.abs(startDate - Today.getTime()) / ONE_DAY) < 60;
        }
        return false;
    }
});

var Employees = Backbone.Collection.extend({
    model: Employee,
    url: contextRoot + "/checkin/rest/person/list",
	sortAttribute: "first",

    initialize: function(attributes, options) {
        var l_options = options || {};
        if (typeof(l_options.sortAttribute) !== "undefined") {
            this.sortAttribute = l_options.sortAttribute;
        }
    },
	
	comparator: function(model) {
		return model.get(this.sortAttribute);
	},

    sortByAttribute: function(attribute) {
        this.sortAttribute = attribute;
        this.sort();
    },

    getSortAttribute: function() {
        return this.sortAttribute;
    },

    filterActiveAndStatus: function(active, status) {
        return this.filter(function (e) {
            return e.get("active") === active && e.get("status") === status;
        });
    }
});

var WeekReport = Backbone.Model.extend({
    idAttribute: "userId"
});

var WeekReportList = Backbone.Collection.extend({
    model: WeekReport,
    url: contextRoot + "/checkin/rest/report/list/week"
});

var MonthReport = Backbone.Model.extend({
    idAttribute: "userId"
});

var MonthReportList = Backbone.Collection.extend({
    model: MonthReport,
    url: contextRoot + "/checkin/rest/report/list/month"
});

var Task = Backbone.Model.extend({
    idAttribute: "taskClass"
});

var TaskList = Backbone.Collection.extend({
    model: Task,
    url: contextRoot + "/checkin/rest/task/list"
});