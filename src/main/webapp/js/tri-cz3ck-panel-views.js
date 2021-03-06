var EmployeeListView = Backbone.View.extend({
	template: _.template('<div id="<%= id %>"></div>'),
	renderState: true,
	
	initialize: function () {
		this.setElement($("#" + this.id));
		this.pageViews = [];
		this.clearListeners = [];
		
		if (typeof(this.options.buttons) !== "undefined") {
			this.renderButtons(this.options.buttons);
		}
        this.renderTitle();

        this.collection.bind("reset", this.render, this);
        this.collection.bind("sort", this.render, this);
    },

	clear: function(delegate) {
		if (arguments.length > 0) {
			this.clearListeners.push(delegate);
		} else {
			_.each(this.clearListeners, function(delegate) {
				delegate();
			}, this);
		}
	},
	
	render: function() {
		if (this.renderState) {
			this.renderAccordion();
			this.renderPages();

			this.collection.forEach(this.renderOne, this);
			this.refreshAccordion();
		}
	},

    disable: function() {
        this.$accordion.accordion("disable");
    },

    enable: function() {
        this.$accordion.accordion("enable");
    },
	
	suspendRender: function() {
		this.renderState = false;
	},
	
	resumeRender: function() {
		this.renderState = true;
		this.render();
	},
	
	renderOne: function(model, index){
		var page = Math.floor(index / this.options.pageSize);
		
		var employeeView = new EmployeeView({ model: model });
		this.pageViews[page].$pageSelectable.append(employeeView.render().el.firstChild);
	},
	
	renderButtons: function(buttons) {
		var buttonView = new EmployeeButtonView({ id: this.id + "Buttons", sourceView: this, collection: this.collection, buttons: buttons });
        var $buttons = $(buttonView.render().el);
        $buttons.addClass("buttons");
		this.$el.append($buttons);
	},

    renderTitle: function() {
        var titleId = this.id + "Title";
        this.$el.append(this.template({ id: titleId }));
        var $title = $("#" + titleId);
        $title.addClass("ui-widget-content").addClass("ui-corner-all").addClass("title");
        $title.html("<h4>" + this.options.title + "</h4>");
    },
	
	renderAccordion: function() {
		var accordionId = this.id + "Accordion";
		this.$accordion = $("#" + accordionId);
		if (this.$accordion.length === 0) {
			this.$el.append(this.template({ id: accordionId }));
			this.$accordion = $("#" + accordionId);
			this.$accordion.accordion();
		}
	},
	
	refreshAccordion: function(desiredPage) {
		var active = this.$accordion.accordion("option", "active");
		if (typeof desiredPage !== "undefined") {
			active = desiredPage;
		}
		
		if (active >= this.pageViews.length) {
			active = this.pageViews.length - 1;
		}
		
		this.$accordion.accordion("destroy");
		this.$accordion.accordion({ active: active });
	},
	
	renderPages: function() {
        var pageView;
		var numPages = Math.ceil(this.collection.size() / this.options.pageSize);
		
		_.each(this.pageViews, function(pageView) {
			pageView.reset();
		});
		
		if (numPages < this.pageViews.length) { // we lost a page
			while (numPages < this.pageViews.length) {
				pageView = this.pageViews.pop();
				pageView.destroy();
			}
		} else {
			// validate all pages
			for (var pageNum = 0; pageNum < numPages; pageNum++) {
				var start = (pageNum) * this.options.pageSize;
				var end = start + this.options.pageSize - 1;
				if (end >= this.collection.size()) end = this.collection.size() - 1;
				
				if (pageNum < this.pageViews.length) {
					pageView = this.pageViews[pageNum];
					pageView.render(this, start, end);
				} else {
					var pageId = this.id + "Page" + pageNum;
					
					pageView = new EmployeePageView({ id: pageId, collection: this.collection });
					this.pageViews.push(pageView);
					pageView.render(this, start, end);
				}
			}
		}
	}
});

var EmployeePageView = Backbone.View.extend({
	template: _.template('<h3 id="<%= id %>Header"></h3><div id="<%= id %>"><ol class="selectable" id="<%= id %>Selectable"></ol></div>'),
	
	render: function(sourceView, startIndex, endIndex) {
		this.$page = $("#" + this.id);
		if (this.$page.length === 0) {
			sourceView.$accordion.append(this.template({ id: this.id }));
			this.$page = $("#" + this.id);
			
			this.$pageSelectable = $("#" + this.id + "Selectable");
			this.$pageSelectable.selectable({ selecting: this.selecting(sourceView, sourceView.options.targetView) });
		} else {
			this.$pageSelectable = $("#" + this.id + "Selectable");
		}
		
		// page header
		var headerText = "&nbsp;";
		if (this.collection.size() > sourceView.options.pageSize) {
			var startValue = this.collection.at(startIndex).getShortNotation(this.collection.getSortAttribute());
			var endValue = this.collection.at(endIndex).getShortNotation(this.collection.getSortAttribute());
			
			headerText = startValue;
			if (startValue != endValue) {
				headerText += "-" + endValue;
			}
		}
		this.$pageHeader = $("#" + this.id + "Header");
		this.$pageHeader.html(headerText);
		return this;
	},
	
	reset: function() {
		this.$pageSelectable.empty();
	},
	
	destroy: function() {
		this.$page.detach();
		this.$pageHeader.detach();
	},
	
	selecting: function(sourceView, targetView) {
		return function (event, ui) {
			var options = { className: "ui-effects-transfer" };
			options.to = "#" + targetView.id;
			var selected = $(ui.selecting);
			selected.addClass("ui-highlited");
			selected.effect("transfer", options, 500, selectingCallback(selected, sourceView, targetView));
			$(".selectable").selectable("disable");
		};
	}
});

var EmployeeView = Backbone.View.extend({
	template: _.template('<li id="emp<%= id %>" class="ui-widget-content ui-corner-all employee">' +
        '<span id="emp<%= id %>Name" class="name"><%= name %></span>' +
        "</li>"),

	render: function() {
		this.$el.html(this.template(this.model.toJSON()));
        var $emp = this.$("#emp" + this.model.get("id"));

        if (this.model.get("status") == "IN") {
            $emp.append(this.newEatingButton());
        } else {
            if (this.model.get("eatingIn")) {
                $emp.append(this.newDisplayButton("tri-icon-eating-in", "Ik eet mee"));
            } else {
                $emp.append(this.newDisplayButton("tri-icon-eating-out", "Ik eet niet mee"));
            }
        }

        if (this.model.isBirthday()) {
            $emp.append(this.newDisplayButton("fff-icon-cake", "Jarig"));
        }
        if (this.model.isNewEmployee()) {
            $emp.append(this.newDisplayButton("fff-icon-new", "Nieuw"));
        }
		return this;
	},

    newDisplayButton: function(icon, title) {
        var $button = $(document.createElement("div"))
            .addClass("ui-button")
            .addClass("ui-widget")
            .addClass("ui-state-active")
            .addClass("ui-corner-all")
            .addClass("ui-button-icon-only")
            .addClass("fff-icon");
        var $spanIcon = $(document.createElement("span"))
            .addClass("ui-button-icon-primary")
            .addClass("ui-icon")
            .addClass(icon);
        var $spanText = $(document.createElement("span"))
            .addClass("ui-button-text")
            .html(title);
        $button.append($spanIcon).append($spanText);
        return $button;
    },

    newEatingButton: function() {
        var enabledState = {primary: "tri-icon-eating-in"};
        var disabledState = {primary: "tri-icon-eating-out"};

        var $button = $(document.createElement("button")).attr("id", "emp" + this.model.get("id") + "EatingInBtn").html("Ik eet mee");
        $button.button({text: false, icons: (this.model.get("eatingIn") ? enabledState : disabledState)}).click(this.model, function (event) {
                if (event.data.get("eatingIn")) {
                    event.data.set("eatingIn", false);
                    event.data.save();
                    $(event.currentTarget).button({ icons: disabledState });
                } else {
                    event.data.set("eatingIn", true);
                    event.data.save();
                    $(event.currentTarget).button({ icons: enabledState });
                }
            }).addClass("fff-icon");
        return $button;
    }
});

var EmployeeButtonView = Backbone.View.extend({
	sortTemplate: _.template('<div id="<%= id %>" class="left"></div>'),
	sortOptionTemplate: _.template('<input type="radio" id="<%= id %><%= attribute %>" value="<%= attribute %>" name="<%= id %>"><label for="<%= id %><%= attribute %>"><%= attributeText %></label>'),
	buttonTemplate: _.template('<button id="<%= id %>" class="right"><%= text %></button>'),

	render: function() {
		var buttons = this.options.buttons;
		if (buttons.sort === true && _.isArray(buttons.sortOptions)) {
			var sortId = this.id + "Sort";
			this.$el.append(this.sortTemplate({ id: sortId }));
			
			var $sortEl = this.$("#" + sortId);
			_.each(buttons.sortOptions, function(attribute) {
				if (_.isString(attribute)) {
					$sortEl.append(this.sortOptionTemplate({ id: sortId, attribute: attribute, attributeText: attribute }));
				} else {
					$sortEl.append(this.sortOptionTemplate({ id: sortId, attribute: attribute.attribute, attributeText: attribute.text }));
				}
			}, this);
			
			var collection = this.collection;
			var view = this;
			$sortEl.buttonset({ create: function() {
				view.$("#" + sortId + " input:radio[value=" + collection.getSortAttribute() + "]").attr("checked", true);
			} }).change(function (event) {
				collection.sortByAttribute(event.target.value);
			});
		}
		
		if (buttons.clear === true) {
			this.$el.append(this.buttonTemplate({ id: this.id + "Clear", text: "All out" }));
			
			this.$("#" + this.id + "Clear").button(buttons.clearOptions).click(this.options.sourceView, function(event) {
				event.data.clear();
			}).addClass("fff-icon");
		}
        if (buttons.info === true) {
            var $dialog = $("#" + this.id + "Dialog");
            var infoId = this.id + "Info";
            if ($dialog.length === 0) {
                $dialog = $(document.createElement("div")).attr("id", this.id + "Dialog").attr("title", "Information");
                $dialog.dialog({
                    autoOpen: false,
                    modal: true,
                    draggable: false,
                    resizable: false,
                    buttons: {
                        OK: function() {
                            $(this).dialog("close");
                        }
                    }
                });
            }
            this.$el.append(this.buttonTemplate({ id: infoId, text: "Information" }));
            this.$("#" + infoId).button({text: false, icons: { primary: "fff-icon-information" }}).click(this, function(event) {
                var count = 0;
                event.data.collection.forEach(function(employee) {
                    if (employee.get("eatingIn")) {
                        count++;
                    }
                });

                $dialog.html("Aantal aangemeld voor lunch: " + count);

                $dialog.dialog("open");
                return false;
            }).addClass("fff-icon");
        }
		this.$el.append('<div class="clear"></div>');
		return this;
	}
});

function selectingCallback(selected, sourceView, targetView) {
	return function() {
		selected.fadeOut();
		selected.detach();
		
		var id = selected.attr("id").substring(3);
		var model = sourceView.collection.get(id);
		model.set("status", targetView.options.status);
        model.save();

		$("#emp" + model.get("id")).addClass("ui-highlited");
		var targetPage = (Math.floor(targetView.collection.indexOf(model) / targetView.options.pageSize));
		targetView.refreshAccordion(targetPage);
		
		$(".selectable").selectable("enable");
		setTimeout("removeHighlited('" + model.get("id") + "');", 1000);
	};
}

function removeHighlited(id) {
	$("#emp" + id).removeClass("ui-highlited");
}
