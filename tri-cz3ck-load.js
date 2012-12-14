var all = new Employees;
var outside = new EmployeesByStatus(undefined, { parent: all, status: 'out' });
var inside = new EmployeesByStatus(undefined, { parent: all, status: 'in' });

$(function() {
	var genericSortOptions = [{ attribute: 'first', text: 'Voornaam' }, { attribute: 'last', text: 'Achternaam' }];
	var leftView = new EmployeeListView({ id: 'ctleft', collection: inside, status: 'in', pageSize: 8, buttons: { sort: true, sortOptions: genericSortOptions, clear: true, clearOptions: { label: 'Iedereen uitchecken', text: false, icons: { primary: "ui-icon-arrowthickstop-1-e" } } } });
	leftView.clear(function() {
		rightView.suspendRender();
		leftView.suspendRender();
		inside.allOut();
		rightView.resumeRender();
		leftView.resumeRender();
	});
	var rightView = new EmployeeListView({ id: 'ctright', collection: outside, status: 'out', pageSize: 8, buttons: { sort: true, sortOptions: genericSortOptions } });
	
	leftView.options.targetView = rightView;
	rightView.options.targetView = leftView;
	
	all.fetch();
});