<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <title>tri-cz3ck reporting</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <style type="text/css">
        #canvas {
            border: solid red 1px;
        }
    </style>
    <script type="text/javascript" src="/js/libs/jquery-min.js"></script>
    <script type="text/javascript" src="/js/libs/jcanvas.min.js"></script>
    <script type="text/javascript">
        $(function() {
            var pts = [
                [0, 20, 80],
                [100, 50, 35],
                [200, 10, 90],
                [300, 0, 100],
                [400, 17.5, 82.5]
            ];

            var padding = 9;

            var $canvas = $('#canvas');

            var line = {
//					layer: true,
//					name: 'line',
                strokeStyle: '#f00',
                strokeWidth: 2
            };
            for (var p = 0; p < pts.length; p++) {
                var pid = (p + 1);
                line['x' + pid] = padding + pts[p][0];
                line['y' + pid] = padding + pts[p][1];
            }
//				$canvas.drawLine(line);

            /*$canvas.drawRect({
             layer: true,
             name: 'tooltip',
             fillStyle: '#ff7',
             strokeStyle: '#000',
             strokeWidth: 1,
             x: 10, y: 11,
             width: 50, height: 10,
             fromCenter: false,
             cornerRadius: 0
             });*/

            for (var p = 0; p < pts.length; p++) {
//                var pid = (p + 1);
                /*$canvas.drawArc({
                 layer: true,
                 name: 'ph' + pid,
                 visible: false,
                 fillStyle: '#fff',
                 strokeStyle: '#333',
                 strokeWidth: 2,
                 x: padding + pts[p][0], y: padding + pts[p][1],
                 radius: 7
                 });*/
                $canvas.drawArc({
                 						layer: true,
//                 name: 'p' + pid,
//                 fillStyle: '#36b',
                    strokeStyle: '#333',
                 strokeWidth: 2,
                 
                 //						pid: pid,
                 x: padding + pts[p][0], y: padding + pts[p][1],
                    start: 0, end: 360,
                 //						tooltip: pts[p][2],
                 radius: 4//,
                 //						mouseover: function(layer) {
                 //							$canvas.setLayer('ph' + layer.pid, { visible: true });
                 //						},
                 //						mouseout: function(layer) {
                 //							$canvas.setLayer('ph' + layer.pid, { visible: false });
                 //						}
                 });
                /*drawArc(document.getElementById('canvas'), {
                    x: padding + pts[p][0], y: padding + pts[p][1],
                    radius: 50
                });*/
            }
//				$canvas.drawLayers();

        });

function drawArc(canvas, arc) {
var ctx = canvas.getContext('2d');
ctx.beginPath();
ctx.arc(arc.x,arc.y,arc.radius,0,Math.PI+(Math.PI*2)/2, false);

ctx.stroke();
}

    </script>
</head>
<body>
<canvas id="canvas" width="458" height="568"></canvas>
</body>
</html>