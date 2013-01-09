var Employee = Backbone.Model.extend({
	getShortNotation: function(attribute) {
		return this.get(attribute);
	}
});

var Employees = Backbone.Collection.extend({
    model: Employee,
    url: '/checkin/person',
	sortAttribute: 'last',
	
	comparator: function(model) {
		return model.get(this.sortAttribute);
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