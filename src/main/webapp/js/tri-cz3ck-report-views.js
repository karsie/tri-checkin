var ReportListView = Backbone.View.extend({

    initialize: function() {
        this.setElement($('#' + this.id));
        this.collection.bind("reset", this.render, this);
    },

    render: function() {
        this.$el.html('');
        this.collection.forEach(this.renderOne, this);
    },

    renderOne: function(model, index){
        var report = this.options.reportDetails.find(function(reportDetail) {return reportDetail.get('userId') == model.get('id');});
        var itemView = new ReportItemView({ model: model, report: report });
        itemView.render(this.$el);
    }
});

var ReportItemView = Backbone.View.extend({
    template: _.template('<div id="emp<%= id %>" class="report_item ui-widget-content ui-corner-all"><div class="left"><span><%= name %></span></div><canvas id="emp<%= id %>Canvas" class="right" width="458" height="70"></canvas><div class="clear"></div></div>'),

    render: function(parent) {
        var $el = $('#emp' + this.model.get('id'));
        if ($el.length == 0) {
            parent.append(this.template(this.model.toJSON()));
            $el = $('#emp' + this.model.get('id'));
        }

        this.setElement($el);
//        if (typeof this.options.report == 'undefined') return;

        var $canvas = $('#emp' + this.model.get('id') + 'Canvas');
        var days = this.options.report.get('days');

        var max = 8 * 60 * 60 * 1000;
        var pts = [];

        for (var i = 0; i < days.length; i++) {
            pts.push([i * 100, 0, days[i], 0]);

            if (days[i] > max) {
                max = days[i];
            }
        }

        if (max == 0) {
            return;
        }

        for (var p = 0; p < pts.length; p++) {
            var percentOfMax = (100 * pts[p][2] / max);
            pts[p][1] = (100 - percentOfMax) / 2;
            pts[p][3] = percentOfMax;
        }

        var paddingTop = 9;
        var paddingLeft = 15;

        var line = {
            layer: true,
            name: 'line',
            strokeStyle: '#f00',
            strokeWidth: 2
        };
        for (p = 0; p < pts.length; p++) {
            var pid = (p + 1);
            line['x' + pid] = paddingLeft + pts[p][0];
            line['y' + pid] = paddingTop + pts[p][1];
        }
        $canvas.drawLine(line);

        $canvas.drawRect({
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
        $canvas.drawText({
            layer: true,
            name: 'tooltipText',
            visible: false,
            fillStyle: "#000",
            x: 10, y: 11,
            font: "10pt Arial, sans-serif",
            text: "Hello",
            fromCenter: false
        });

        for (p = 0; p < pts.length; p++) {
            pid = (p + 1);
            $canvas.drawArc({
                layer: true,
                name: 'ph' + pid,
                visible: false,
                fillStyle: '#fff',
                strokeStyle: '#333',
                strokeWidth: 2,
                x: paddingLeft + pts[p][0], y: paddingTop + pts[p][1],
                radius: 7,
                start: 0.1, end: 360
            });
            $canvas.drawArc({
                layer: true,
                name: 'p' + pid,
                fillStyle: '#f00',
                strokeStyle: '#f00',
                strokeWidth: 2,
                pid: pid,
                x: paddingLeft + pts[p][0], y: paddingTop + pts[p][1],
                start: 0.1, end: 360,
                tooltip: pts[p][2],
                radius: 4,
                mouseover: function(layer) {
                    $canvas.setLayer('ph' + layer.pid, { visible: true });

                    var $tt = $canvas.getLayer('tooltip');
                    var ttX = layer.eventX - 25;
                    if (ttX < paddingLeft) {
                        ttX = paddingLeft;
                    }
                    var ttY = layer.eventY - $tt.height - 15;
                    if (ttY < paddingTop) { // move it below
                        ttY = layer.eventY + 20;
                    }

                    $canvas.setLayer('tooltip', {visible: true, x: ttX, y: ttY});
                    $canvas.setLayer('tooltipText', {visible: true, text: layer.tooltip, x: ttX + 5, y: ttY + 2})
                },
                mouseout: function(layer) {
                    $canvas.setLayer('ph' + layer.pid, { visible: false });
                    $canvas.setLayer('tooltip', {visible: false});
                    $canvas.setLayer('tooltipText', {visible: false});
                }
            });
        }
        $canvas.drawLayers();
    }
});