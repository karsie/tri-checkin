var WORKDAY_IN_MILLIS = 8 * 60 * 60 * 1000;
var WORKDAYS = ['ma', 'di', 'wo', 'do', 'vr'];
var PADDING_TOP = 9;
var PADDING_LEFT = 15;

var WeekReportListView = Backbone.View.extend({
    initialize: function() {
        this.setElement($('#' + this.id));
        this.collection.bind("reset", this.render, this);
    },

    render: function() {
        this.renderFilter();

        this.collection.forEach(this.renderOne, this);
    },

    renderOne: function(model) {
        var report = this.options.reportDetails.find(function(reportDetail) {return reportDetail.get('userId') == model.get('id');});
        var itemView = new WeekReportItemView({ model: model, report: report });
        itemView.render(this.$el);
    },

    renderFilter: function() {
        var filterView = new WeekReportFilterView;
        filterView.render(this.$el);
    }
});

var WeekReportFilterView = Backbone.View.extend({
    template: _.template('<div id="filter" class="report_header ui-widget-content ui-state-default ui-corner-all"><div id="filter_year" class="report_filter_item"><span>Selecteer jaar:</span><select id="select_year"></select></div><div id="filter_week" class="report_filter_item"><span>Selecteer week:</span><select id="select_week"></select></div></div>'),

    render: function(parent) {
        var elementId = '#filter';
        var $el = $(elementId);
        if ($el.length == 0) {
            parent.append(this.template());
            $el = $(elementId);
        }

        var filter_week = $('#filter_week');
        filter_week.hide();

        var select_week = $('#select_week');
        select_week.change(function (event) {

        });

        var select_year = $('#select_year');
        select_year.change(function (event) {
            var year = event.currentTarget.value;
            $.getJSON('/checkin/rest/report/list/weeks/' + year, function (data) {
                var options = select_week.prop('options');
                $('option', select_week).remove();

                filter_week.show();
                if (data.length == 0) {
                    options[options.length] = new Option('Nothing', -1);
                } else {
                    _.each(data, function(week) {
                        options[options.length] = new Option(week, week);
                    });
                    select_week.val(_.max(data));
                    select_week.trigger('change');
                }
            });
        });


        $.getJSON('/checkin/rest/report/list/years', function (data) {
            var options = select_year.prop('options');
            $('option', select_year).remove();

            if (data.length == 0) {
                options[options.length] = new Option('Nothing', -1);
            } else {
                _.each(data, function(year) {
                    options[options.length] = new Option(year, year);
                });
                select_year.val(Today.getFullYear());
                select_year.trigger('change');
            }
        });
    }
});

var WeekReportItemView = Backbone.View.extend({
    template: _.template('<div id="emp<%= id %>" class="report_item ui-widget-content ui-corner-all"><div class="left"><span><%= name %></span><br/><span id="emp<%= id %>EatingIn" class="smallprint"></span></div><canvas id="emp<%= id %>Canvas" class="right" width="458" height="70">canvas</canvas><div class="clear"></div></div>'),

    render: function(parent) {
        var elementId = '#emp' + this.model.get('id');
        var $el = $(elementId);
        if ($el.length == 0) {
            parent.append(this.template(this.model.toJSON()));
            $el = $(elementId);
        }

        this.setElement($el);
        var $canvas = $(elementId + 'Canvas');
        this.drawTooltip($canvas);

        this.updateEatingIn($(elementId + "EatingIn"), this.options.report.get('eatingIn'));

        var days = this.options.report.get('days');
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
        var line = {
            layer: true,
            name: 'line',
            strokeStyle: '#f00',
            strokeWidth: 2
        };
        _.each(points, function(point) {
            line['x' + point.id] = PADDING_LEFT + point.x;
            line['y' + point.id] = PADDING_TOP + point.y;
        });
        canvas.drawLine(line);
    },

    drawArcs: function(canvas, points) {
        _.each(points, function(point) {
            canvas.drawArc({
                layer: true,
                name: 'ph' + point.id,
                visible: false,
                fillStyle: '#fff',
                strokeStyle: '#333',
                strokeWidth: 2,
                x: PADDING_LEFT + point.x,
                y: PADDING_TOP + point.y,
                radius: 7,
                start: 0.1, end: 360
            });
            canvas.drawArc({
                layer: true,
                name: 'p' + point.id,
                pid: point.id,
                fillStyle: '#f00',
                strokeStyle: '#f00',
                strokeWidth: 2,
                x: PADDING_LEFT + point.x,
                y: PADDING_TOP + point.y,
                radius: 4,
                start: 0.1, end: 360,
                tooltip: point.time,
                mouseover: function(layer) {
                    canvas.setLayer('ph' + layer.pid, { visible: true });

                    var ttX = layer.eventX - 25;
                    if (ttX < PADDING_LEFT) {
                        ttX = PADDING_LEFT;
                    }
                    var ttY = layer.eventY - canvas.getLayer('tooltip').height - 15;
                    if (ttY < PADDING_TOP) { // move it below
                        ttY = layer.eventY + 20;
                    }

                    canvas.setLayer('tooltip', {visible: true, x: ttX, y: ttY});
                    canvas.setLayer('tooltipText', {visible: true, text: layer.tooltip, x: ttX + 5, y: ttY + 2});
                },
                mouseout: function(layer) {
                    canvas.setLayer('ph' + layer.pid, { visible: false });
                    canvas.setLayer('tooltip', {visible: false});
                    canvas.setLayer('tooltipText', {visible: false});
                }
            });
        });
    },

    drawTooltip: function(canvas) {
        canvas.drawRect({
            layer: true,
            name: 'tooltip',
            fillStyle: '#ff7',
            strokeStyle: '#000',
            visible: false,
            strokeWidth: 1,
            x: 10, y: 11,
            width: 50, height: 17,
            fromCenter: false,
            cornerRadius: 0
        });
        canvas.drawText({
            layer: true,
            name: 'tooltipText',
            visible: false,
            fillStyle: "#000",
            x: 10, y: 11,
            font: "10pt Arial, sans-serif",
            text: "Hello",
            fromCenter: false
        });
    },

    updateEatingIn: function (spanElement, days) {
        var daysEatingIn = [];
        _.each(days, function(eatingIn, index) {
            if (eatingIn) {
                daysEatingIn.push(WORKDAYS[index]);
            }
        });
        if (daysEatingIn.length > 0) {
            spanElement.html('Meegegeten op: ' + daysEatingIn.join('-'));
        } else {
            spanElement.html('');
        }
    },

    formatTime: function(millis) {
        var minutes = Math.ceil(millis / 1000 / 60);
        var hours = Math.floor(minutes / 60);

        if (hours > 0) {
            return hours + "u " + minutes + "m";
        } else if (minutes > 0) {
            return minutes + "m";
        } else {
            return "0";
        }
    }
});