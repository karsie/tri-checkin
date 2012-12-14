var Employee = Backbone.Model.extend({
	getShortNotation: function(attribute) {
		return this.get(attribute);
	}
});

var Employees = Backbone.Collection.extend({
    model: Employee,
	url: 'Data.xml',
	sortAttribute: 'last',
	
	comparator: function(model) {
		return model.get(this.sortAttribute);
	},
	
    parse: function(data) {
        var parsed = [];
		var xml = $.parseXML(data);
		$(xml).find('employee').each(function () {
            var fullName = $(this).attr('name');
			var nameParts = fullName.split(' ');
			var lastName = _.last(nameParts);
			var firstName = _.first(nameParts);

            parsed.push(new Employee({
				id: $(this).attr('id')
				, name: fullName
				, first: firstName
				, last: lastName
				, status: $(this).attr('status')
				, added: false
			}));
        });

        return parsed;
    },
	
    fetch: function(options) {
        options || (options = {});
        options.dataType = "text";
		return Backbone.Collection.prototype.fetch.call(this, options);
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
		this.forEach(function(model) { model.set({ status: 'out', silent: true }); });
	},
	
	sortByAttribute: function(attribute) {
		this.sortAttribute = attribute;
		this.sort();
	},
	
	getSortAttribute: function() {
		return this.sortAttribute;
	}
});
