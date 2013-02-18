var TaskListView = Backbone.View.extend({
    initialize: function() {
        this.setElement($("#" + this.id));
        this.collection.bind("reset", this.render, this);
        this.renderTitle();
    },

    render: function() {
        this.collection.forEach(this.renderOne, this);
    },

    renderOne: function(model) {
        var itemView = new TaskItemView({model: model});
        itemView.render(this.$el);
    },

    renderTitle: function() {
        var titleView = new TaskTitleView();
        titleView.render(this);
    }
});

var TaskTitleView = Backbone.View.extend({
    template: _.template('<div id="filter" class="centered_header ui-widget-content ui-state-default ui-corner-all"><div class="report_filter_item"><span>Tasks</span></div></div>'),

    render: function(parentView) {
        var elementId = "#filter";
        if ($(elementId).length === 0) {
            parentView.$el.append(this.template());
        }
    }
});

var TaskItemView = Backbone.View.extend({
    template: _.template('<div id="task<%= taskClass %>" class="centered_item ui-widget-content ui-corner-all"><div class="left"><span><%= taskClass %></span><br/><span class="smallprint"><%= description %></span><br/><span class="smallprint"><%= schedule %></span></div><button class="right" id="task<%= taskClass %>Btn">Run</button><div class="clear"></div></div>'),

    render: function(parent) {
        var elementId = "#task" + this.model.get("taskClass");
        var $el = $(elementId);
        if ($el.length === 0) {
            parent.append(this.template(this.model.toJSON()));
        }

        $(elementId + "Btn").button({text: false, icons: {primary: "ui-icon-play"}}).click(this.model, function (event) {
            $.ajax({
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                type: "post",
                url: contextRoot + "/checkin/rest/task/run",
                data: JSON.stringify(event.data),
                traditional: true
            });
        });
    }
});