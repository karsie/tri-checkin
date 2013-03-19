var WORKDAY_IN_MILLIS = 8 * 60 * 60 * 1000;
var WORKDAYS = ["ma", "di", "wo", "do", "vr"];
var MONTHNAMES = ["Januari", "Februari", "Maart", "April", "Mei", "Juni", "Juli", "Augustus", "September", "October", "November", "December"];
var PADDING_TOP = 9;
var PADDING_LEFT = 15;

var WeekReportListView = Backbone.View.extend({
    initialize: function() {
        this.setElement($("#" + this.id));
        this.collection.bind("reset", this.render, this);
        this.renderFilter();
    },

    render: function() {
        this.$(".centered_item").hide();
        this.collection.forEach(this.renderOne, this);
    },

    renderOne: function(model) {
        var report = this.options.reportDetails.find(function(reportDetail) {return reportDetail.get("userId") == model.get("id");});
        var itemView = new WeekReportItemView({ model: model, report: report });
        itemView.render(this.$el);
    },

    renderFilter: function() {
        var filterView = new WeekReportFilterView();
        filterView.render(this);
    },

    filterChange: function(delegate) {
        this.filterChangeHandler = delegate;
    },

    triggerFilterChange: function(year, week, showInactive) {
        if (typeof(this.filterChangeHandler) !== "undefined" && this.filterChangeHandler !== null) {
            this.filterChangeHandler(year, week, showInactive);
        }
    }
});

var WeekReportFilterView = Backbone.View.extend({
    template: _.template('<div id="filter" class="centered_header ui-widget-content ui-state-default ui-corner-all"><div id="filter_year" class="report_filter_item"><span>Selecteer jaar:</span><select id="select_year"></select></div><div id="filter_week" class="report_filter_item"><span>Selecteer week:</span><select id="select_week"></select></div><div id="filter_inactive" class="report_filter_item"><span>Toon inactieve medewerkers:</span><input type="checkbox" id="check_inactive"></select></div></div>'),

    render: function(parentView) {
        var elementId = "#filter";
        if ($(elementId).length === 0) {
            parentView.$el.append(this.template());
        } else {
            $(elementId).show();
        }

        var $filter_week = $("#filter_week");
        $filter_week.hide();

        var $select_week = $("#select_week");
        var $select_year = $("#select_year");
        var $showInactive = $("#check_inactive");

        $select_week.change(function (event) {
            parentView.triggerFilterChange($select_year.val(), event.currentTarget.value, $showInactive.is(":checked"));
        });

        $showInactive.change(function () {
            parentView.triggerFilterChange($select_year.val(), $select_week.val(), $showInactive.is(":checked"));
        });

        $select_year.change(function (event) {
            var year = event.currentTarget.value;
            $.getJSON(contextRoot + "/checkin/rest/report/list/week/weeks/" + year, function (data) {
                var options = $select_week.prop("options");
                $("option", $select_week).remove();

                $filter_week.show();
                if (data.length === 0) {
                    options[options.length] = new Option("Nothing", -1);
                } else {
                    _.each(data, function(week) {
                        options[options.length] = new Option(week, week);
                    });
                    $select_week.val(_.max(data));
                    $select_week.trigger("change");
                }
            });
        });

        $.getJSON(contextRoot + "/checkin/rest/report/list/week/years", function (data) {
            var options = $select_year.prop("options");
            $("option", $select_year).remove();

            if (data.length === 0) {
                options[options.length] = new Option("Nothing", -1);
            } else {
                _.each(data, function(year) {
                    options[options.length] = new Option(year, year);
                });
                $select_year.val(Today.getFullYear());
                $select_year.trigger("change");
            }
        });
    }
});

var WeekReportItemView = Backbone.View.extend({
    template: _.template('<div id="emp<%= id %>" class="centered_item ui-widget-content ui-corner-all"><div class="left"><span><%= name %></span><br/><span id="emp<%= id %>EatingIn" class="smallprint"></span><br/><span id="emp<%= id %>Active" class="smallprint"></span></div><canvas id="emp<%= id %>Canvas" class="right" width="458" height="70">canvas</canvas><div class="clear"></div></div>'),

    render: function(parent) {
        var elementId = "#emp" + this.model.get("id");
        var $el = $(elementId);
        if ($el.length === 0) {
            parent.append(this.template(this.model.toJSON()));
            $el = $(elementId);
        }
        $el.show();

        this.setElement($el);
        var $canvas = $(elementId + "Canvas");
        this.drawTooltip($canvas);

        this.updateEatingIn($(elementId + "EatingIn"), this.options.report.get("eatingIn"));
        $(elementId + "Active").html(this.model.get("active") === true ? "" : "(Inactief)");

        var days = this.options.report.get("days");
        var maxMillis = _.max(days);
        if (maxMillis < WORKDAY_IN_MILLIS) {
            maxMillis = WORKDAY_IN_MILLIS;
        }

        var points = [];
        _.each(days, function(millis, index) {
            var percentOfMax = (100 * millis / maxMillis);

            points.push({
                id: (index + 1),
                x: index * 100,
                y: (100 - percentOfMax) / 2,
                time: this.formatTime(millis),
                percent: percentOfMax
            });
        }, this);

        this.drawLine($canvas, points);
        this.drawArcs($canvas, points);

        $canvas.drawLayers();
    },

    drawLine: function(canvas, points) {
        if (typeof(canvas.getLayer("line")) === "undefined") {
            canvas.drawLine({
                layer: true,
                name: "line",
                strokeStyle: "#f00",
                strokeWidth: 2
            });
        }
        var coords = {};
        _.each(points, function(point) {
            coords["x" + point.id] = PADDING_LEFT + point.x;
            coords["y" + point.id] = PADDING_TOP + point.y;
        });
        canvas.setLayer("line", coords);
    },

    drawArcs: function(canvas, points) {
        if (typeof(canvas.getLayer("p1")) === "undefined") {
            _.each(points, function(point) {
                canvas.drawArc({
                    layer: true,
                    name: "ph" + point.id,
                    fillStyle: "#fff",
                    strokeStyle: "#333",
                    strokeWidth: 2,
                    radius: 7,
                    start: 0.1, end: 360
                });
                canvas.drawArc({
                    layer: true,
                    name: "p" + point.id,
                    pid: point.id,
                    fillStyle: "#f00",
                    strokeStyle: "#f00",
                    strokeWidth: 2,
                    radius: 4,
                    start: 0.1, end: 360,
                    mouseover: function(layer) {
                        canvas.setLayer("ph" + layer.pid, { visible: true });

                        var ttX = layer.eventX - 25;
                        if (ttX < PADDING_LEFT) {
                            ttX = PADDING_LEFT;
                        }
                        var ttY = layer.eventY - canvas.getLayer("tooltip").height - 15;
                        if (ttY < PADDING_TOP) { // move it below
                            ttY = layer.eventY + 20;
                        }

                        canvas.setLayer("tooltip", {visible: true, x: ttX, y: ttY});
                        canvas.setLayer("tooltipText", {visible: true, text: layer.tooltip, x: ttX + 5, y: ttY + 2});
                    },
                    mouseout: function(layer) {
                        canvas.setLayer("ph" + layer.pid, { visible: false });
                        canvas.setLayer("tooltip", {visible: false});
                        canvas.setLayer("tooltipText", {visible: false});
                    }
                });
            });
        }
        _.each(points, function(point) {
            canvas.setLayer("ph" + point.id, {
                visible: false,
                x: PADDING_LEFT + point.x,
                y: PADDING_TOP + point.y
            });
            canvas.setLayer("p" + point.id, {
                x: PADDING_LEFT + point.x,
                y: PADDING_TOP + point.y,
                tooltip: point.time
            });
        });
    },

    drawTooltip: function(canvas) {
        if (typeof(canvas.getLayer("tooltip")) === "undefined") {
            canvas.drawRect({
                layer: true,
                name: "tooltip",
                fillStyle: "#ff7",
                strokeStyle: "#000",
                strokeWidth: 1,
                x: 10, y: 11,
                width: 50, height: 17,
                fromCenter: false,
                cornerRadius: 0
            });
            canvas.drawText({
                layer: true,
                name: "tooltipText",
                fillStyle: "#000",
                x: 10, y: 11,
                font: "10pt Arial, sans-serif",
                text: "",
                fromCenter: false
            });
        }
        canvas.setLayer("tooltip", {visible:false});
        canvas.setLayer("tooltipText", {visible:false});
    },

    updateEatingIn: function (spanElement, days) {
        var daysEatingIn = [];
        _.each(days, function(eatingIn, index) {
            if (eatingIn) {
                daysEatingIn.push(WORKDAYS[index]);
            }
        });
        if (daysEatingIn.length > 0) {
            spanElement.html("Meegegeten op: " + daysEatingIn.join("-"));
        } else {
            spanElement.html("");
        }
    },

    formatTime: function(millis) {
        var minutes = Math.ceil(millis / 1000 / 60);
        var hours = Math.floor(minutes / 60);

        if (hours > 0) {
            minutes = minutes - (hours * 60);
            return hours + "u " + minutes + "m";
        } else if (minutes > 0) {
            return minutes + "m";
        } else {
            return "0";
        }
    }
});

var MonthReportListView = Backbone.View.extend({
    initialize: function() {
        this.setElement($("#" + this.id));
        this.collection.bind("reset", this.render, this);
        this.renderFilter();
    },

    render: function() {
        this.$(".centered_item").hide();
        this.collection.forEach(this.renderOne, this);
    },

    renderOne: function(model) {
        var report = this.options.reportDetails.find(function(reportDetail) {return reportDetail.get("userId") == model.get("id");});
        var itemView = new MonthReportItemView({ model: model, report: report });
        itemView.render(this.$el);
    },

    renderFilter: function() {
        var filterView = new MonthReportFilterView();
        filterView.render(this);
    },

    filterChange: function(delegate) {
        this.filterChangeHandler = delegate;
    },

    triggerFilterChange: function(year, week, showInactive) {
        if (typeof(this.filterChangeHandler) !== "undefined" && this.filterChangeHandler !== null) {
            this.filterChangeHandler(year, week, showInactive);
        }
    }
});

var MonthReportFilterView = Backbone.View.extend({
    template: _.template('<div id="filter" class="centered_header ui-widget-content ui-state-default ui-corner-all"><div id="filter_year" class="report_filter_item"><span>Selecteer jaar:</span><select id="select_year"></select></div><div id="filter_month" class="report_filter_item"><span>Selecteer maand:</span><select id="select_month"></select></div><div id="filter_inactive" class="report_filter_item"><span>Toon inactieve medewerkers:</span><input type="checkbox" id="check_inactive"></select></div></div>'),

    render: function(parentView) {
        var elementId = "#filter";
        if ($(elementId).length === 0) {
            parentView.$el.append(this.template());
        } else {
            $(elementId).show();
        }

        var $filter_month = $("#filter_month");
        $filter_month.hide();

        var $select_month = $("#select_month");
        var $select_year = $("#select_year");
        var $showInactive = $("#check_inactive");

        $select_month.change(function (event) {
            parentView.triggerFilterChange($select_year.val(), event.currentTarget.value, $showInactive.is(":checked"));
        });

        $showInactive.change(function () {
            parentView.triggerFilterChange($select_year.val(), $select_month.val(), $showInactive.is(":checked"));
        });

        $select_year.change(function (event) {
            var year = event.currentTarget.value;
            $.getJSON(contextRoot + "/checkin/rest/report/list/month/months/" + year, function (data) {
                var options = $select_month.prop("options");
                $("option", $select_month).remove();

                $filter_month.show();
                if (data.length === 0) {
                    options[options.length] = new Option("Nothing", -1);
                } else {
                    _.each(data, function(month) {
                        options[options.length] = new Option(MONTHNAMES[month - 1], month);
                    });
                    $select_month.val(_.max(data));
                    $select_month.trigger("change");
                }
            });
        });

        $.getJSON(contextRoot + "/checkin/rest/report/list/week/years", function (data) {
            var options = $select_year.prop("options");
            $("option", $select_year).remove();

            if (data.length === 0) {
                options[options.length] = new Option("Nothing", -1);
            } else {
                _.each(data, function(year) {
                    options[options.length] = new Option(year, year);
                });
                $select_year.val(Today.getFullYear());
                $select_year.trigger("change");
            }
        });
    }
});

var MonthReportItemView = Backbone.View.extend({
    template: _.template('<div id="emp<%= id %>" class="centered_item ui-widget-content ui-corner-all"><div class="left"><span><%= name %></span></div><div class="right" id="emp<%= id %>EatingIn"></div><div class="clear"></div></div>'),

    render: function(parent) {
        var elementId = "#emp" + this.model.get("id");
        var $el = $(elementId);
        if ($el.length === 0) {
            parent.append(this.template(this.model.toJSON()));
        }
        $el.show();

        var $eatingIn = $(elementId + "EatingIn");
        $eatingIn.html('');

        var year = this.options.report.get("year");
        var month = this.options.report.get("month") - 1;
        var days = this.options.report.get("eatingIn");
        var date = new Date();
        date.setFullYear(year, month, 1);
        for (var i = 0; i < 31; i++) {
            date.setDate(i + 1);

            if (date.getMonth() == month) {
                var active = (date.getDay() > 0 && date.getDay() < 6);
                var icon = days[i] ? "tri-icon-eating-in-small" : "tri-icon-eating-out-small";
                $eatingIn.append(this.newDisplayButton(icon, date.toDateString(), active));
            }
        }
    },

    newDisplayButton: function(icon, title, active) {
        var $button = $(document.createElement("div"))
            .addClass("ui-button")
            .addClass("small-button")
            .addClass("ui-widget")
            .addClass("ui-corner-all")
            .addClass("ui-button-icon-only")
            .addClass("fff-icon");
        if (active) {
            $button.addClass("ui-state-active");
        } else {
            $button.addClass("ui-state-default");
        }
        var $spanIcon = $(document.createElement("span"))
            .addClass("ui-button-icon-primary")
            .addClass("ui-icon")
            .addClass("small-icon")
            .addClass(icon);
        var $spanText = $(document.createElement("span"))
            .addClass("ui-button-text")
            .html(title);
        $button.append($spanIcon).append($spanText);
        return $button;
    }
});