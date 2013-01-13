/*
 jCanvas v13.01.10
 Copyright 2013 Caleb Evans
 Released under the MIT license
*/
(function(d,Y,da,Q,ea,u,v,g,w){function E(){}function H(c){c?B(fa,c):H.prefs=fa=E.prototype=B({},V);return this}function C(c){return c&&c.getContext?c.getContext("2d"):g}function K(c,a){c.fillStyle=a.fillStyle;c.strokeStyle=a.strokeStyle;c.lineWidth=a.strokeWidth;a.rounded?(c.lineCap="round",c.lineJoin="round"):(c.lineCap=a.strokeCap,c.lineJoin=a.strokeJoin,c.miterLimit=a.miterLimit);c.shadowOffsetX=a.shadowX;c.shadowOffsetY=a.shadowY;c.shadowBlur=a.shadowBlur;c.shadowColor=a.shadowColor;c.globalAlpha=
a.opacity;c.globalCompositeOperation=a.compositing}function N(c,a,b){b.closed&&a.closePath();a.fill();"transparent"!==b.fillStyle&&(a.shadowColor="transparent");0!==b.strokeWidth&&a.stroke();b.closed||a.closePath();b._transformed&&a.restore();b.mask&&(b.autosave&&(a.save(),c=L(c),c.transforms.mask=u,c.savedTransforms=B({},c.transforms)),a.clip())}function ga(c,a,b){a._toRad=a.inDegrees?F/180:1;c.translate(a.x,a.y);c.rotate(a.rotate*a._toRad);c.translate(-a.x,-a.y);b.rotate+=a.rotate*a._toRad}function ha(c,
a,b){1!==a.scale&&(a.scaleX=a.scaleY=a.scale);c.translate(a.x,a.y);c.scale(a.scaleX,a.scaleY);c.translate(-a.x,-a.y);b.scaleX*=a.scaleX;b.scaleY*=a.scaleY}function ia(c,a,b){a.translate&&(a.translateX=a.translateY=a.translate);c.translate(a.translateX,a.translateY);b.translateX+=a.translateX;b.translateY+=a.translateY}function J(c,a,b,e){a._toRad=a.inDegrees?F/180:1;a._transformed=u;c.save();e===w&&(e=b);!a.fromCenter&&!a._centered&&(a.x+=b/2,a.y+=e/2,a._centered=u);a.rotate&&ga(c,a,{});(1!==a.scale||
1!==a.scaleX||1!==a.scaleY)&&ha(c,a,{});(a.translate||a.translateX||a.translateY)&&ia(c,a,{})}function Z(c){c.draggable&&(c.translateX+=c.x,c.translateY+=c.y)}function L(c){var a;G.canvas===c?a=G.data:(a=d.data(c,"jCanvas"),a||(a={layers:[],intersects:[],drag:{},event:{},transforms:{rotate:0,scaleX:1,scaleY:1,translateX:0,translateY:0,mask:v},animating:v,animated:g},a.savedTransforms=B({},a.transforms),d.data(c,"jCanvas",a)),G.canvas=c,G.data=a);return a}function ja(c,a,b){b&&b.visible&&(b.method===
d.fn.draw?b.fn.call(c[0],a):b.method&&b.method.call(c,b))}function I(c,a,b,e){var f,j,D="function"===typeof b,s;b=b||{};a._args=b;if(a.layer&&!a._layer){f=d(c);a=f.getLayers();D&&(b={method:d.fn.draw,fn:b});b=B(new E,b);if(!D){b.method=d.fn[b.method]||e;s=L(c);for(j in H.events)H.events.hasOwnProperty(j)&&b[j]&&(j=$(j),H.events[j](f,s),b._event=u);if(b.draggable||b.cursor){b._event=u;c=["mousedown","mousemove","mouseup"];for(e=0;e<c.length;e+=1)j=$(c[e]),H.events[j](f,s);s.mouseout||(f.bind("mouseout.jCanvas",
function(){s.drag={};f.drawLayers()}),s.mouseout=u)}}b.layer=u;b._layer=u;b.index===w&&(b.index=a.length);a.splice(b.index,0,b)}return b}function ka(c){var a;for(a=0;a<P.length;a+=1)c[P[a]]=c["_"+P[a]]}function la(c,a){var b;for(b=0;b<P.length;b+=1)c["_"+P[b]]=c[P[b]],aa[P[b]]=1,a&&delete c[P[b]]}function ma(c){var a,b,e=[],f=1;c.match(/^#?\w+$/i)&&("transparent"===c&&(c="rgba(0,0,0,0)"),b=Y.head,a=b.style.color,b.style.color=c,c=d.css(b,"color"),b.style.color=a);c.match(/^rgb/i)&&(e=c.match(/\d+/gi),
c.match(/%/gi)&&(f=2.55),e[0]*=f,e[1]*=f,e[2]*=f,e[3]=e[3]!==w?ea(e[3]):1);return e}function ra(c){var a=3,b;"object"!==typeof c.start&&(c.start=ma(c.start),c.end=ma(c.end));c.now=[];if(1!==c.start[3]||1!==c.end[3])a=4;for(b=0;b<a;b+=1)c.now[b]=c.start[b]+(c.end[b]-c.start[b])*c.pos,3>b&&(c.now[b]=S(c.now[b]));1!==c.start[3]||1!==c.end[3]?c.now="rgba("+c.now.join(",")+")":(c.now.slice(0,3),c.now="rgb("+c.now.join(",")+")");c.elem.nodeName?c.elem.style[c.prop]=c.now:c.elem[c.prop]=c.now}function $(c){"ontouchstart"in
window&&ba[c]&&(c=ba[c]);return c}function O(c){H.events[c]=function(a,b){c=$(c);var e="mouseover"===c||"mouseout"===c?"mousemove":c,f=b.event;b[e]||(a.bind(e+".jCanvas",function(b){f.x=b.offsetX;f.y=b.offsetY;f.type=e;f.event=b;a.drawLayers();b.preventDefault()}),b[e]=u)}}function M(c,a,b){var e,f,j,d;b=b._args;b._event&&(c=L(c),e=c.event,a=a.isPointInPath(e.x,e.y),f=c.transforms,b.eventX=b.mouseX=e.x,b.eventY=b.mouseY=e.y,b.event=e.event,d=c.transforms.rotate,e=b.eventX,j=b.eventY,b._eventX=e*R(-d)-
j*T(-d),b._eventY=j*R(-d)+e*T(-d),b._eventX/=f.scaleX,b._eventY/=f.scaleY,!a&&(b._hovered&&!b._fired)&&(b._mousedout=u),a&&c.intersects.push(b))}function W(c,a,b,e,f){a.save();a.beginPath();a.rect(e[0],f[0],e[1]-e[0],f[1]-f[0]);a.clip();J(a,b,0);a.beginPath()}function X(c,a,b){b._event&&M(c,a,b);a.restore();N(c,a,b)}function na(c,a){a.font?c.font=a.font:(isNaN(Number(a.fontSize))||(a.fontSize+="px"),c.font=a.fontStyle+" "+a.fontSize+" "+a.fontFamily)}function oa(c,a,b,e,f){var j=/\b(\d*\.?\d*)\w\w\b/gi,
g;if(G.text===e.text&&G.font===e.font&&G.fontStyle===e.fontStyle&&G.fontSize===e.fontSize&&G.fontFamily===e.fontFamily&&G.maxWidth===e.maxWidth&&G.lineHeight===e.lineHeight)e.width=G.width,e.height=G.height;else if(!a){e.width=b.measureText(f[0]).width;for(a=1;a<f.length;a+=1)g=b.measureText(f[a]).width,g>e.width&&(e.width=g);b=c.style.fontSize;if(e.font){if(a=e.font.match(j))c.style.fontSize=e.font.match(j)[0]}else c.style.fontSize=e.fontSize;e.height=ea(d.css(c,"fontSize"))*f.length*e.lineHeight;
c.style.fontSize=b}}var V,fa,B=d.extend,S=Q.round,F=Q.PI,T=Q.sin,R=Q.cos,sa=d.event.fix,ba,ca,pa,G={},P,aa;d.fn.jCanvas=H;H.events={};V={align:"center",autosave:u,baseline:"middle",bringToFront:v,ccw:v,closed:v,compositing:"source-over",cornerRadius:0,cropFromCenter:u,disableDrag:v,disableEvents:v,domain:g,draggable:v,each:g,end:360,fillStyle:"transparent",font:"",fontStyle:"normal",fontSize:"12pt",fontFamily:"sans-serif",fromCenter:u,fn:g,graph:"y",height:g,inDegrees:u,lineHeight:1,load:g,mask:v,
maxWidth:g,method:g,miterLimit:10,opacity:1,projection:0,r1:g,r2:g,radius:0,range:g,repeat:"repeat",rotate:0,rounded:v,scale:1,scaleX:1,scaleY:1,shadowBlur:0,shadowColor:"transparent",shadowX:0,shadowY:0,sHeight:g,sides:3,source:"",start:0,strokeCap:"butt",strokeJoin:"miter",strokeStyle:"transparent",strokeWidth:1,sWidth:g,sx:g,sy:g,text:"",translate:0,translateX:0,translateY:0,type:g,visible:u,width:g,x:0,y:0};H();H.extend=function(c){H.defaults=B(V,c.props);H();c.name&&(d.fn[c.name]=function b(e){var f,
j,d,s=B(new E,e);for(j=0;j<this.length;j+=1)if(f=this[j],d=C(f))e=I(f,s,e,b),K(d,s),c.fn.call(f,d,s);return this});return d.fn[c.name]};d.fn.getLayers=function(){var c=this[0];return!c||!c.getContext?[]:L(c).layers};d.fn.getLayer=function(c){var a=this.getLayers(),b=d.type(c),e,f;if(c&&c.layer)e=c;else if("number"===b)0>c&&(c=a.length+c),e=a[c];else for(f=0;f<a.length;f+=1)if(a[f].index=f,a[f].name===c||"regexp"===b&&a[f].name.match(c)){e=a[f];break}return e};d.fn.setLayer=function(c,a){var b,e;for(b=
0;b<this.length;b+=1)(e=d(this[b]).getLayer(c))&&B(e,a);return this};d.fn.moveLayer=function(c,a){var b,e,f;for(e=0;e<this.length;e+=1)if(b=d(this[e]),f=b.getLayers(),b=b.getLayer(c))f.splice(b.index,1),f.splice(a,0,b),0>a&&(a=f.length+a),b.index=a;return this};d.fn.removeLayer=function(c){var a,b,e;for(b=0;b<this.length;b+=1)a=d(this[b]),e=a.getLayers(),(a=a.getLayer(c))&&e.splice(a.index,1);return this};d.fn.removeLayers=function(){var c,a;for(c=0;c<this.length;c+=1)a=d(this[c]).getLayers(),a.length=
0;return this};d.fn.getLayerGroup=function(c){var a=this.getLayers(),b=d.type(c),e=[],f;if("array"===b)return c;for(f=0;f<a.length;f+=1)a[f].index=f,(a[f].group===c||"regexp"===b&&a[f].group.match(c))&&e.push(a[f]);return e};d.fn.setLayerGroup=function(c,a){var b,e,f;for(e=0;e<this.length;e+=1){b=d(this[e]);b=b.getLayerGroup(c);for(f=0;f<b.length;f+=1)B(b[f],a)}return this};d.fn.removeLayerGroup=function(c){var a,b,e=d.type(c),f;if(c!==w)for(b=0;b<this.length;b+=1){a=d(this[b]);a=a.getLayers();for(f=
0;f<a.length;f+=1)if(a[f].index=f,a[f].group===c||"regexp"===e&&a[f].group.match(c))a.splice(f,1),f-=1}return this};d.fn.drawLayer=function(c){var a,b,e,f;for(a=0;a<this.length;a+=1)e=d(this[a]),b=C(this[a]),f=e.getLayer(c),ja(e,b,f);return this};d.fn.drawLayers=function(){var c,a,b,e,f,j,D,s,y;for(a=0;a<this.length;a+=1)if(c=d(this[a]),b=C(this[a])){L(this[a]);c.clearCanvas();G.canvas===this[a]?D=G.data:(D=L(this[a]),G.canvas=this[a],G.data=D);e=D.layers;for(j=0;j<e.length;j+=1)f=e[j],f.index=j,
f._fired=v,f._event=!f.disableEvents,ja(c,b,f),f._mousedout&&(f._mousedout=v,f._fired=u,f._hovered=v,f.mouseout&&f.mouseout.call(this[a],f),f.cursor&&f._cursor&&c.css({cursor:f._cursor}));f=D.intersects[D.intersects.length-1]||{};b=D.event;j=b.type;f[j]||ca[j]&&(j=ca[j]);y=f[j];s=D.drag;if(f._event){if((f.mouseover||f.mouseout||f.cursor)&&!f._hovered&&!f._fired)f._fired=u,f._hovered=u,f.mouseover&&f.mouseover.call(this[a],f),f.cursor&&(f._cursor=c.css("cursor"),c.css({cursor:f.cursor}));y&&!f._fired&&
(f._fired=u,y.call(this[a],f),b.type=g);if(f.draggable&&!f.disableDrag&&("mousedown"===j||"touchstart"===j))f.bringToFront&&(e.splice(f.index,1),f.index=e.push(f)),s.layer=f,s.dragging=u,s.startX=f.x,s.startY=f.y,s.endX=f._eventX,s.endY=f._eventY,f.dragstart&&f.dragstart.call(this[a],f)}if(s.layer){if(s.dragging&&("mouseup"===j||"touchend"===j))s.layer.dragstop&&s.layer.dragstop.call(this[a],s.layer),D.drag={};if(s.dragging&&("mousemove"===j||"touchmove"===j))s.layer.x=s.layer._eventX-(s.endX-s.startX),
s.layer.y=s.layer._eventY-(s.endY-s.startY),s.layer.drag&&s.layer.drag.call(this[a],s.layer)}D.intersects=[]}return this};d.fn.addLayer=function(c){var a,b,e={};c=c||{};for(a=0;a<this.length;a+=1)if(b=C(this[a]))c.layer=e.layer=u,c.type&&!c.method&&(c.method=d.fn[pa[c.type]]),c=I(this[a],e,c,c.method);return this};P=["width","height","opacity","lineHeight"];aa={};d.fn.animateLayer=function(){function c(a,b,c){return function(){ka(c);(!b.animating||b.animated===c)&&a.drawLayers();j[4]&&j[4].call(a[0],
c);c._animating=v;b.animating=v;b.animated=g}}function a(a,b,c){return function(e,f){ka(c);!c._animating&&!b.animating&&(c._animating=u,b.animating=u,b.animated=c);(!b.animating||b.animated===c)&&a.drawLayers();j[5]&&j[5].call(a[0],e,f,c)}}var b,e,f,j=[].slice.call(arguments,0),D;"object"===typeof j[0]&&!j[0].layer&&j.unshift(0);"object"===typeof j[2]?(j.splice(2,0,j[2].duration||g),j.splice(3,0,j[3].easing||g),j.splice(4,0,j[4].complete||g),j.splice(5,0,j[5].step||g)):(j[2]===w?(j.splice(2,0,g),
j.splice(3,0,g),j.splice(4,0,g)):"function"===typeof j[2]&&(j.splice(2,0,g),j.splice(3,0,g)),j[3]===w?(j[3]=g,j.splice(4,0,g)):"function"===typeof j[3]&&j.splice(3,0,g));j[1]=B({},j[1]);for(e=0;e<this.length;e+=1)if(b=d(this[e]),f=C(this[e]))if(f=L(this[e]),(D=b.getLayer(j[0]))&&D.method!==d.fn.draw)la(D),la(j[1],u),D.style=aa,d(D).animate(j[1],{duration:j[2],easing:d.easing[j[3]]?j[3]:g,complete:c(b,f,D),step:a(b,f,D)});return this};d.fn.animateLayerGroup=function(c){var a,b,e=[].slice.call(arguments,
0),f,j;for(b=0;b<this.length;b+=1){a=d(this[b]);f=a.getLayerGroup(c);for(j=0;j<f.length;j+=1)a.animateLayer.apply(a,[f[j]].concat(e.slice(1)))}};d.fn.delayLayer=function(c,a){var b,e;a=a||0;for(b=0;b<this.length;b+=1)e=d(this[b]).getLayer(c),d(e).delay(a);return this};d.fn.delayLayerGroup=function(c,a){var b,e,f,j;a=a||0;for(e=0;e<this.length;e+=1){b=d(this[e]);f=b.getLayerGroup(c);for(j=0;j<f.length;j+=1)b.delayLayer.call(b,f[j],a)}};d.fn.stopLayer=function(c,a){var b,e;for(b=0;b<this.length;b+=
1)e=d(this[b]).getLayer(c),d(e).stop(a);return this};d.fn.stopLayerGroup=function(c,a){var b,e,f,j;for(e=0;e<this.length;e+=1){b=d(this[e]);f=b.getLayerGroup(c);for(j=0;j<f.length;j+=1)b.stopLayer.call(b,f[j],a)}};var qa="color backgroundColor borderColor borderTopColor borderRightColor borderBottomColor borderLeftColor fillStyle outlineColor strokeStyle shadowColor".split(" "),U;for(U=0;U<qa.length;U+=1)d.fx.step[qa[U]]=ra;ba={mousedown:"touchstart",mouseup:"touchend",mousemove:"touchmove"};ca={touchstart:"mousedown",
touchend:"mouseup",touchmove:"mousemove"};O("click");O("dblclick");O("mousedown");O("mouseup");O("mousemove");O("mouseover");O("mouseout");O("touchstart");O("touchmove");O("touchend");d.event.fix=function(c){var a,b;c=sa.call(d.event,c);if(a=c.originalEvent)if(b=a.changedTouches,c.pageX!==w&&c.offsetX===w){if(a=d(c.target).offset())c.offsetX=c.pageX-a.left,c.offsetY=c.pageY-a.top}else if(b&&(a=d(a.target).offset()))c.offsetX=b[0].pageX-a.left,c.offsetY=b[0].pageY-a.top;return c};pa={arc:"drawArc",
bezier:"drawBezier",circle:"drawArc",ellipse:"drawEllipse","function":"draw",image:"drawImage",line:"drawLine",polygon:"drawPolygon",quadratic:"drawQuadratic",rectangle:"drawRect",text:"drawText",vector:"drawVector"};d.fn.draw=function a(b){var e,f;b=b||{};"function"===typeof b&&(b={fn:b});for(e=0;e<this.length;e+=1)if((f=C(this[e]))&&b.fn)b=I(this[e],{},b,a),b.fn.call(this[e],f);return this};d.fn.clearCanvas=function(a){var b,e=B(new E,a);for(a=0;a<this.length;a+=1)if(b=C(this[a]))J(b,e,e.width,
e.height),b.setTransform(1,0,0,1,0,0),!e.x||!e.y||!e.width||!e.height?b.clearRect(0,0,this[a].width,this[a].height):b.clearRect(e.x-e.width/2,e.y-e.height/2,e.width,e.height),b.restore();return this};d.fn.saveCanvas=function(){var a,b,e;for(a=0;a<this.length;a+=1)if(b=C(this[a]))e=L(this[a]),b.save(),e.savedTransforms=B({},e.transforms);return this};d.fn.restoreCanvas=function(){var a,b,e;for(a=0;a<this.length;a+=1)if(b=C(this[a]))e=L(this[a]),b.restore(),e.transforms=B({},e.savedTransforms);return this};
d.fn.restoreCanvasOnRedraw=function(a){var b={layer:u,fn:function(){d(this).restoreCanvas()}};B(b,a);return this.draw(b)};d.fn.translateCanvas=function(a){var b,e=B(new E,a),f;for(a=0;a<this.length;a+=1)if(b=C(this[a]))f=L(this[a]),e.autosave&&b.save(),ia(b,e,f.transforms);return this};d.fn.scaleCanvas=function(a){var b,e=B(new E,a),f;for(a=0;a<this.length;a+=1)if(b=C(this[a]))f=L(this[a]),e.autosave&&b.save(),ha(b,e,f.transforms);return this};d.fn.rotateCanvas=function(a){var b,e=B(new E,a),f;for(a=
0;a<this.length;a+=1)if(b=C(this[a]))f=L(this[a]),e.autosave&&b.save(),ga(b,e,f.transforms);return this};d.fn.drawRect=function b(e){var f,j,d=B(new E,e),s,g,x,z,t;for(f=0;f<this.length;f+=1)if(j=C(this[f]))e=I(this[f],d,e,b),K(j,d),J(j,d,d.width,d.height),j.beginPath(),s=d.x-d.width/2,g=d.y-d.height/2,(t=d.cornerRadius)?(d.closed=u,x=d.x+d.width/2,z=d.y+d.height/2,0>x-s-2*t&&(t=(x-s)/2),0>z-g-2*t&&(t=(z-g)/2),j.moveTo(s+t,g),j.lineTo(x-t,g),j.arc(x-t,g+t,t,3*F/2,2*F,v),j.lineTo(x,z-t),j.arc(x-t,
z-t,t,0,F/2,v),j.lineTo(s+t,z),j.arc(s+t,z-t,t,F/2,F,v),j.lineTo(s,g+t),j.arc(s+t,g+t,t,F,3*F/2,v)):j.rect(s,g,d.width,d.height),d._event&&M(this[f],j,d),N(this[f],j,d);return this};d.fn.drawArc=function e(f){var j,d,g=B(new E,f);f=f||{};!g.inDegrees&&360===g.end&&(f.end=g.end=2*F);for(j=0;j<this.length;j+=1)if(d=C(this[j]))f=I(this[j],g,f,e),K(d,g),J(d,g,2*g.radius),d.beginPath(),d.arc(g.x,g.y,g.radius,g.start*g._toRad-F/2,g.end*g._toRad-F/2,g.ccw),g._event&&M(this[j],d,g),N(this[j],d,g);return this};
d.fn.drawEllipse=function f(d){var g,s,y=B(new E,d),x=4*y.width/3,z=y.height;y.closed=u;for(g=0;g<this.length;g+=1)if(s=C(this[g]))d=I(this[g],y,d,f),K(s,y),J(s,y,y.width,y.height),s.beginPath(),s.moveTo(y.x,y.y-z/2),s.bezierCurveTo(y.x-x/2,y.y-z/2,y.x-x/2,y.y+z/2,y.x,y.y+z/2),s.bezierCurveTo(y.x+x/2,y.y+z/2,y.x+x/2,y.y-z/2,y.x,y.y-z/2),y._event&&M(this[g],s,y),N(this[g],s,y);return this};d.fn.drawPolygon=function j(d){var g,y,x=B(new E,d),z=2*F/x.sides,t=F/x.sides,r=t+F/2,n=x.radius*R(z/2),k,l,p;
d.closed=x.closed=u;for(g=0;g<this.length;g+=1)if(y=C(this[g])){d=I(this[g],x,d,j);K(y,x);J(y,x,2*x.radius);y.beginPath();for(p=0;p<x.sides;p+=1)k=x.x+S(x.radius*R(r)),l=x.y+S(x.radius*T(r)),y.lineTo(k,l),x.projection&&(k=x.x+S((n+n*x.projection)*R(r+t)),l=x.y+S((n+n*x.projection)*T(r+t)),y.lineTo(k,l)),r+=z;x._event&&M(this[g],y,x);N(this[g],y,x)}return this};d.fn.drawLine=function D(d){var g,x,z=B(new E,d),t,r,n;for(g=0;g<this.length;g+=1)if(x=C(this[g])){d=I(this[g],z,d,D);K(x,z);Z(z);J(x,z,0);
t=1;for(x.beginPath();u;)if(r=z["x"+t],n=z["y"+t],r!==w&&n!==w)x.lineTo(r,n),t+=1;else break;z._event&&M(this[g],x,z);N(this[g],x,z)}return this};d.fn.drawQuadratic=d.fn.drawQuad=function s(d){var g,z,t=B(new E,d),r,n,k,l,p;for(g=0;g<this.length;g+=1)if(z=C(this[g])){d=I(this[g],t,d,s);K(z,t);Z(t);J(z,t,0);r=2;z.beginPath();for(z.moveTo(t.x1,t.y1);u;)if(n=t["x"+r],k=t["y"+r],l=t["cx"+(r-1)],p=t["cy"+(r-1)],n!==w&&k!==w&&l!==w&&p!==w)z.quadraticCurveTo(l,p,n,k),r+=1;else break;t._event&&M(this[g],
z,t);N(this[g],z,t)}return this};d.fn.drawBezier=function y(g){var d,t,r=B(new E,g),n,k,l,p,q,m,h,A;for(d=0;d<this.length;d+=1)if(t=C(this[d])){g=I(this[d],r,g,y);K(t,r);Z(r);J(t,r,0);n=2;k=1;t.beginPath();for(t.moveTo(r.x1,r.y1);u;)if(l=r["x"+n],p=r["y"+n],q=r["cx"+k],m=r["cy"+k],h=r["cx"+(k+1)],A=r["cy"+(k+1)],l!==w&&p!==w&&q!==w&&m!==w&&h!==w&&A!==w)t.bezierCurveTo(q,m,h,A,l,p),n+=1,k+=2;else break;r._event&&M(this[d],t,r);N(this[d],t,r)}return this};d.fn.drawVector=function x(d){var g,r,n=B(new E,
d),k,l,p,q,m;for(g=0;g<this.length;g+=1)if(r=C(this[g])){d=I(this[g],n,d,x);K(r,n);J(r,n,0);k=1;r.beginPath();q=n.x;m=n.y;for(r.moveTo(n.x,n.y);u;)if(l=n["a"+k],p=n["l"+k],l!==w&&p!==w)l=l*n._toRad-F/2,q+=p*Q.cos(l),m+=p*Q.sin(l),r.lineTo(q,m),k+=1;else break;n._event&&M(this[g],r,n);N(this[g],r,n)}return this};d.fn.drawGraph=function z(d){var r,n,k=B(new E,d),l,p,q,m,h;for(r=0;r<this.length;r+=1)if((n=C(this[r]))&&k.fn){d=I(this[r],k,d,z);K(n,k);l=k.graph;p=k.domain;q=k.range;m=this[r].width;h=this[r].height;
p===g&&(p=[g,g]);p[0]===g&&(p[0]=0);p[1]===g&&(p[1]=m);q===g&&(q=[g,g]);q[0]===g&&(q[0]=0);q[1]===g&&(q[1]=h);W(r,n,k,p,q);if("y"===l)for(l=p[0]-k.x;l<=p[1]-k.x;l+=1)h=k.fn(l),h===g?(X(this[r],n,k),W(r,n,k,p,q)):n.lineTo(l+k.x,h+k.y);else if("x"===l)for(h=q[0]-k.y;h<=q[1]-k.y;h+=1)l=k.fn(h),l===g?(X(this[r],n,k),W(r,n,k,p,q)):n.lineTo(l+k.x,h+k.y);else if("r"===l)for(m=0;m<2*F;m+=F/180)h=k.fn(m),l=h*R(m),h*=T(m),l===g||h===g?(X(this[r],n,k),W(n,k,p,q)):n.lineTo(l+k.x,h+k.y);X(this[r],n,k)}};d.fn.drawText=
function t(r){var n,k,l=B(new E,r),p,q,m,h;for(n=0;n<this.length;n+=1)if(d(this[n]),k=C(this[n])){r=I(this[n],l,r,t);K(k,l);k.textBaseline=l.baseline;k.textAlign=l.align;na(k,l);if(!n&&l.maxWidth!==g){p=k;q=l.text;h=l.maxWidth;var A=q.split(" "),u=void 0,w=[],v="";if(p.measureText(q).width<h||1===A.length)w=[q];else{for(u=0;u<A.length;u+=1)p.measureText(v+A[u]).width>h&&(""!==v&&w.push(v),v=""),v+=A[u],u!==A.length-1&&(v+=" ");w.push(v)}p=w;p=p.join("\n").replace(/( (\n))|( $)/gi,"$2").split("\n")}else n||
(p=String(l.text).split("\n"));oa(this[n],n,k,l,p);J(k,l,l.width,l.height);n||(m=l.x,"left"===l.align?m-=l.width/2:"right"===l.align&&(m+=l.width/2));for(q=0;q<p.length;q+=1)k.shadowColor=l.shadowColor,h=l.y+q*l.height/p.length-(p.length-1)*l.height/p.length/2,k.fillText(p[q],m,h),"transparent"!==l.fillStyle&&(k.shadowColor="transparent"),k.strokeText(p[q],m,h);l._event?(k.beginPath(),k.rect(l.x-l.width/2,l.y-l.height/2,l.width,l.height),k.restore(),M(this[n],k,l),k.closePath()):k.restore()}G=l;return this};
d.fn.measureText=function(d){var g;g=d!==w&&("object"!==typeof d||d.layer)?this.getLayer(d):B(new E,d);if((d=C(this[0]))&&g.text!==w)na(d,g),oa(this[0],0,d,g,g.text.split("\n"));return g};d.fn.drawImage=function r(d){function k(r,k,m){return function(){0===k&&(w=A.width/A.height,h.width===g&&h.sWidth===g&&(d.width=h.width=h.sWidth=A.width),h.height===g&&h.sHeight===g&&(d.height=h.height=h.sHeight=A.height),h.width===g&&h.sWidth!==g&&(h.width=h.sWidth),h.height===g&&h.sHeight!==g&&(h.height=h.sHeight),
h.sWidth===g&&h.width!==g&&(d.sWidth=h.sWidth=A.width),h.sHeight===g&&h.height!==g&&(d.sHeight=h.sHeight=A.height),h.sx===g&&(h.sx=h.cropFromCenter?A.width/2:0),h.sy===g&&(h.sy=h.cropFromCenter?A.height/2:0),h.cropFromCenter||(h.sx+=h.sWidth/2,h.sy+=h.sHeight/2),h.sx+h.sWidth/2>A.width&&(h.sx=A.width-h.sWidth/2),0>h.sx-h.sWidth/2&&(h.sx=h.sWidth/2),0>h.sy-h.sHeight/2&&(h.sy=h.sHeight/2),h.sy+h.sHeight/2>A.height&&(h.sy=A.height-h.sHeight/2),h.width!==g&&h.height===g?d.height=h.height=h.width/w:h.width===
g&&h.height!==g?d.width=h.width=h.height*w:h.width===g&&h.height===g&&(d.width=h.width=A.width,d.height=h.height=A.height));J(m,h,h.width,h.height);m.drawImage(A,h.sx-h.sWidth/2,h.sy-h.sHeight/2,h.sWidth,h.sHeight,h.x-h.width/2,h.y-h.height/2,h.width,h.height);m.fillStyle="transparent";m.beginPath();m.rect(h.x-h.width/2,h.y-h.height/2,h.width,h.height);h._event&&M(l[k],m,h);N(l[k],m,h);h.load&&h.load.call(r,d)}}var l=this,p,q,m,h=B(new E,d),A,u,w;p=h.source;u=p.getContext;p.src||u?A=p:p&&(A=new da,
A.src=p);for(q=0;q<l.length;q+=1)if(p=l[q],m=C(l[q]))d=I(l[q],h,d,r),K(m,h),A&&(A.complete||u?k(p,q,m)():(A.onload=k(p,q,m),A.src=A.src));return l};d.fn.createPattern=d.fn.pattern=function(d){function n(){m=l.createPattern(q,p.repeat);p.load&&p.load.call(k[0],m)}var k=this,l,p=B(new E,d),q,m,h;(l=C(k[0]))?(h=p.source,"function"===typeof h?(q=Y.createElement("canvas"),q.width=p.width,q.height=p.height,d=C(q),h.call(q,d),n()):(d=h.getContext,h.src||d?q=h:(q=new da,q.src=h),q.complete||d?n():(q.onload=
n,q.src=q.src))):m=g;return m};d.fn.createGradient=d.fn.gradient=function(d){var n;d=B(new E,d);var k=[],l,p,q,m,h,u,v;if(n=C(this[0])){d.x1=d.x1||0;d.y1=d.y1||0;d.x2=d.x2||0;d.y2=d.y2||0;n=d.r1!==g||d.r2!==g?n.createRadialGradient(d.x1,d.y1,d.r1,d.x2,d.y2,d.r2):n.createLinearGradient(d.x1,d.y1,d.x2,d.y2);for(m=1;d["c"+m]!==w;m+=1)d["s"+m]!==w?k.push(d["s"+m]):k.push(g);l=k.length;k[0]===g&&(k[0]=0);k[l-1]===g&&(k[l-1]=1);for(m=0;m<l;m+=1){if(k[m]!==g){u=1;v=0;p=k[m];for(h=m+1;h<l;h+=1)if(k[h]!==
g){q=k[h];break}else u+=1;p>q&&(k[h]=k[m])}else k[m]===g&&(v+=1,k[m]=p+v*((q-p)/u));n.addColorStop(k[m],d["c"+(m+1)])}}else n=g;return n};d.fn.setPixels=function n(d){var g,p,q,m=B(new E,d),h={},u,w,v,F;for(p=0;p<this.length;p+=1)if(g=this[p],q=C(g)){d=I(g,m,d,n);J(q,m,m.width,m.height);if(!m.x||!m.y||!m.width||!m.height)m.width=g.width,m.height=g.height,m.x=m.width/2,m.y=m.height/2;u=q.getImageData(m.x-m.width/2,m.y-m.height/2,m.width,m.height);w=u.data;F=w.length;h=[];if(m.each)for(v=0;v<F;v+=4)h.r=
w[v],h.g=w[v+1],h.b=w[v+2],h.a=w[v+3],m.each.call(g,h),w[v]=h.r,w[v+1]=h.g,w[v+2]=h.b,w[v+3]=h.a;q.putImageData(u,m.x-m.width/2,m.y-m.height/2);q.restore()}return this};d.fn.getCanvasImage=function(d,k){var l=this[0];return l&&l.toDataURL?l.toDataURL("image/"+d,k):g};d.support.canvas=Y.createElement("canvas").getContext!==w;H.defaults=V;H.detectEvents=M;H.closePath=N;d.jCanvas=H})(jQuery,document,Image,Math,parseFloat,!0,!1,null);