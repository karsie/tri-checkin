// The number of milliseconds in one day
var ONE_DAY = 1000 * 60 * 60 * 24;
var Today = new Date();

var Employee = Backbone.Model.extend({
	getShortNotation: function(attribute) {
		return this.get(attribute);
	},

    isBirthday: function() {
        var birthDate = this.get('birthDate');
        if (birthDate != null) {
            return Today.toDateString() == new Date(birthDate).toDateString();
        }
        return false;
    },

    isNewEmployee: function() {
        var startDate = this.get('startDate');
        if (startDate != null) {
            return Math.round(Math.abs(startDate - Today.getTime()) / ONE_DAY) < 60;
        }
        return false;
    }
});

var Employees = Backbone.Collection.extend({
    model: Employee,
    url: '/checkin/person',
	sortAttribute: 'last',
	
	comparator: function(model) {
		return model.get(this.sortAttribute);
	},

    sortByAttribute: function(attribute) {
        this.sortAttribute = attribute;
        this.sort();
    },

    getSortAttribute: function() {
        return this.sortAttribute;
    }
});

var EmployeesByStatus = Backbone.Subset.extend({
	liveupdate_keys: ['status'],
	sortAttribute: 'last',
	
	initialize: function(models, options) {
		this.status = options.status;
	},
	
	sieve: function(model) {
		return model.get('status') == this.status;
	},
	
	allOut: function() {
		this.forEach(function(model) { model.set({ status: 'OUT' }); model.save(); });
	},
	
	sortByAttribute: function(attribute) {
		this.sortAttribute = attribute;
		this.sort();
	},
	
	getSortAttribute: function() {
		return this.sortAttribute;
	}
});

var ReportData = Backbone.Model.extend({
    idAttribute: 'userId'
});

var ReportDataList = Backbone.Collection.extend({
    model: ReportData,
    url: '/checkin/report/list'
});