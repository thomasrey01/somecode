!function(){function t(t,o,r){return o in t?Object.defineProperty(t,o,{value:r,enumerable:!0,configurable:!0,writable:!0}):t[o]=r,t}function o(){"use strict";/*! regenerator-runtime -- Copyright (c) 2014-present, Facebook, Inc. -- license (MIT): https://github.com/facebook/regenerator/blob/main/LICENSE */o=function(){return t};var t={},r=Object.prototype,n=r.hasOwnProperty,i="function"==typeof Symbol?Symbol:{},e=i.iterator||"@@iterator",a=i.asyncIterator||"@@asyncIterator",s=i.toStringTag||"@@toStringTag";function c(t,o,r){return Object.defineProperty(t,o,{value:r,enumerable:!0,configurable:!0,writable:!0}),t[o]}try{c({},"")}catch(E){c=function(t,o,r){return t[o]=r}}function l(t,o,r,n){var i=o&&o.prototype instanceof b?o:b,e=Object.create(i.prototype),a=new z(n||[]);return e._invoke=function(t,o,r){var n="suspendedStart";return function(i,e){if("executing"===n)throw new Error("Generator is already running");if("completed"===n){if("throw"===i)throw e;return L()}for(r.method=i,r.arg=e;;){var a=r.delegate;if(a){var s=x(a,r);if(s){if(s===u)continue;return s}}if("next"===r.method)r.sent=r._sent=r.arg;else if("throw"===r.method){if("suspendedStart"===n)throw n="completed",r.arg;r.dispatchException(r.arg)}else"return"===r.method&&r.abrupt("return",r.arg);n="executing";var c=d(t,o,r);if("normal"===c.type){if(n=r.done?"completed":"suspendedYield",c.arg===u)continue;return{value:c.arg,done:r.done}}"throw"===c.type&&(n="completed",r.method="throw",r.arg=c.arg)}}}(t,r,a),e}function d(t,o,r){try{return{type:"normal",arg:t.call(o,r)}}catch(E){return{type:"throw",arg:E}}}t.wrap=l;var u={};function b(){}function f(){}function h(){}var p={};c(p,e,(function(){return this}));var g=Object.getPrototypeOf,v=g&&g(g(O([])));v&&v!==r&&n.call(v,e)&&(p=v);var m=h.prototype=b.prototype=Object.create(p);function y(t){["next","throw","return"].forEach((function(o){c(t,o,(function(t){return this._invoke(o,t)}))}))}function k(t,o){var r;this._invoke=function(i,e){function a(){return new o((function(r,a){!function r(i,e,a,s){var c=d(t[i],t,e);if("throw"!==c.type){var l=c.arg,u=l.value;return u&&"object"==typeof u&&n.call(u,"__await")?o.resolve(u.__await).then((function(t){r("next",t,a,s)}),(function(t){r("throw",t,a,s)})):o.resolve(u).then((function(t){l.value=t,a(l)}),(function(t){return r("throw",t,a,s)}))}s(c.arg)}(i,e,r,a)}))}return r=r?r.then(a,a):a()}}function x(t,o){var r=t.iterator[o.method];if(void 0===r){if(o.delegate=null,"throw"===o.method){if(t.iterator.return&&(o.method="return",o.arg=void 0,x(t,o),"throw"===o.method))return u;o.method="throw",o.arg=new TypeError("The iterator does not provide a 'throw' method")}return u}var n=d(r,t.iterator,o.arg);if("throw"===n.type)return o.method="throw",o.arg=n.arg,o.delegate=null,u;var i=n.arg;return i?i.done?(o[t.resultName]=i.value,o.next=t.nextLoc,"return"!==o.method&&(o.method="next",o.arg=void 0),o.delegate=null,u):i:(o.method="throw",o.arg=new TypeError("iterator result is not an object"),o.delegate=null,u)}function w(t){var o={tryLoc:t[0]};1 in t&&(o.catchLoc=t[1]),2 in t&&(o.finallyLoc=t[2],o.afterLoc=t[3]),this.tryEntries.push(o)}function j(t){var o=t.completion||{};o.type="normal",delete o.arg,t.completion=o}function z(t){this.tryEntries=[{tryLoc:"root"}],t.forEach(w,this),this.reset(!0)}function O(t){if(t){var o=t[e];if(o)return o.call(t);if("function"==typeof t.next)return t;if(!isNaN(t.length)){var r=-1,i=function o(){for(;++r<t.length;)if(n.call(t,r))return o.value=t[r],o.done=!1,o;return o.value=void 0,o.done=!0,o};return i.next=i}}return{next:L}}function L(){return{value:void 0,done:!0}}return f.prototype=h,c(m,"constructor",h),c(h,"constructor",f),f.displayName=c(h,s,"GeneratorFunction"),t.isGeneratorFunction=function(t){var o="function"==typeof t&&t.constructor;return!!o&&(o===f||"GeneratorFunction"===(o.displayName||o.name))},t.mark=function(t){return Object.setPrototypeOf?Object.setPrototypeOf(t,h):(t.__proto__=h,c(t,s,"GeneratorFunction")),t.prototype=Object.create(m),t},t.awrap=function(t){return{__await:t}},y(k.prototype),c(k.prototype,a,(function(){return this})),t.AsyncIterator=k,t.async=function(o,r,n,i,e){void 0===e&&(e=Promise);var a=new k(l(o,r,n,i),e);return t.isGeneratorFunction(r)?a:a.next().then((function(t){return t.done?t.value:a.next()}))},y(m),c(m,s,"Generator"),c(m,e,(function(){return this})),c(m,"toString",(function(){return"[object Generator]"})),t.keys=function(t){var o=[];for(var r in t)o.push(r);return o.reverse(),function r(){for(;o.length;){var n=o.pop();if(n in t)return r.value=n,r.done=!1,r}return r.done=!0,r}},t.values=O,z.prototype={constructor:z,reset:function(t){if(this.prev=0,this.next=0,this.sent=this._sent=void 0,this.done=!1,this.delegate=null,this.method="next",this.arg=void 0,this.tryEntries.forEach(j),!t)for(var o in this)"t"===o.charAt(0)&&n.call(this,o)&&!isNaN(+o.slice(1))&&(this[o]=void 0)},stop:function(){this.done=!0;var t=this.tryEntries[0].completion;if("throw"===t.type)throw t.arg;return this.rval},dispatchException:function(t){if(this.done)throw t;var o=this;function r(r,n){return a.type="throw",a.arg=t,o.next=r,n&&(o.method="next",o.arg=void 0),!!n}for(var i=this.tryEntries.length-1;i>=0;--i){var e=this.tryEntries[i],a=e.completion;if("root"===e.tryLoc)return r("end");if(e.tryLoc<=this.prev){var s=n.call(e,"catchLoc"),c=n.call(e,"finallyLoc");if(s&&c){if(this.prev<e.catchLoc)return r(e.catchLoc,!0);if(this.prev<e.finallyLoc)return r(e.finallyLoc)}else if(s){if(this.prev<e.catchLoc)return r(e.catchLoc,!0)}else{if(!c)throw new Error("try statement without catch or finally");if(this.prev<e.finallyLoc)return r(e.finallyLoc)}}}},abrupt:function(t,o){for(var r=this.tryEntries.length-1;r>=0;--r){var i=this.tryEntries[r];if(i.tryLoc<=this.prev&&n.call(i,"finallyLoc")&&this.prev<i.finallyLoc){var e=i;break}}e&&("break"===t||"continue"===t)&&e.tryLoc<=o&&o<=e.finallyLoc&&(e=null);var a=e?e.completion:{};return a.type=t,a.arg=o,e?(this.method="next",this.next=e.finallyLoc,u):this.complete(a)},complete:function(t,o){if("throw"===t.type)throw t.arg;return"break"===t.type||"continue"===t.type?this.next=t.arg:"return"===t.type?(this.rval=this.arg=t.arg,this.method="return",this.next="end"):"normal"===t.type&&o&&(this.next=o),u},finish:function(t){for(var o=this.tryEntries.length-1;o>=0;--o){var r=this.tryEntries[o];if(r.finallyLoc===t)return this.complete(r.completion,r.afterLoc),j(r),u}},catch:function(t){for(var o=this.tryEntries.length-1;o>=0;--o){var r=this.tryEntries[o];if(r.tryLoc===t){var n=r.completion;if("throw"===n.type){var i=n.arg;j(r)}return i}}throw new Error("illegal catch attempt")},delegateYield:function(t,o,r){return this.delegate={iterator:O(t),resultName:o,nextLoc:r},"next"===this.method&&(this.arg=void 0),u}},t}function r(t,o,r,n,i,e,a){try{var s=t[e](a),c=s.value}catch(l){return void r(l)}s.done?o(c):Promise.resolve(c).then(n,i)}function n(t,o){if(!(t instanceof o))throw new TypeError("Cannot call a class as a function")}function i(t,o){for(var r=0;r<o.length;r++){var n=o[r];n.enumerable=n.enumerable||!1,n.configurable=!0,"value"in n&&(n.writable=!0),Object.defineProperty(t,n.key,n)}}function e(t,o,r){return o&&i(t.prototype,o),r&&i(t,r),Object.defineProperty(t,"prototype",{writable:!1}),t}(window.webpackJsonp=window.webpackJsonp||[]).push([[38],{uQcF:function(i,a,s){"use strict";s.r(a),s.d(a,"ion_fab",(function(){return u})),s.d(a,"ion_fab_button",(function(){return b})),s.d(a,"ion_fab_list",(function(){return f}));var c=s("wEJo"),l=s("E/Mt"),d=s("74mu"),u=function(){function i(t){var o=this;n(this,i),Object(c.o)(this,t),this.edge=!1,this.activated=!1,this.onClick=function(){var t=!!o.el.querySelector("ion-fab-list"),r=o.getFab();t&&(!r||!r.disabled)&&(o.activated=!o.activated)}}var a,s;return e(i,[{key:"activatedChanged",value:function(){var t=this.activated,o=this.getFab();o&&(o.activated=t),Array.from(this.el.querySelectorAll("ion-fab-list")).forEach((function(o){o.activated=t}))}},{key:"componentDidLoad",value:function(){this.activated&&this.activatedChanged()}},{key:"close",value:(a=o().mark((function t(){return o().wrap((function(t){for(;;)switch(t.prev=t.next){case 0:this.activated=!1;case 1:case"end":return t.stop()}}),t,this)})),s=function(){var t=this,o=arguments;return new Promise((function(n,i){var e=a.apply(t,o);function s(t){r(e,n,i,s,c,"next",t)}function c(t){r(e,n,i,s,c,"throw",t)}s(void 0)}))},function(){return s.apply(this,arguments)})},{key:"getFab",value:function(){return this.el.querySelector("ion-fab-button")}},{key:"render",value:function(){var o,r=this.horizontal,n=this.vertical,i=this.edge,e=Object(l.b)(this);return Object(c.j)(c.c,{onClick:this.onClick,class:(o={},t(o,e,!0),t(o,"fab-horizontal-"+r,void 0!==r),t(o,"fab-vertical-"+n,void 0!==n),t(o,"fab-edge",i),o)},Object(c.j)("slot",null))}},{key:"el",get:function(){return Object(c.k)(this)}}],[{key:"watchers",get:function(){return{activated:["activatedChanged"]}}}]),i}();u.style=":host{position:absolute;z-index:999}:host(.fab-horizontal-center){left:50%;margin-left:-28px}:host-context([dir=rtl]):host(.fab-horizontal-center),:host-context([dir=rtl]).fab-horizontal-center{left:unset;right:unset;right:50%}@supports ((-webkit-margin-start: 0) or (margin-inline-start: 0)) or (-webkit-margin-start: 0){:host(.fab-horizontal-center){margin-left:unset;-webkit-margin-start:-28px;margin-inline-start:-28px}}:host(.fab-horizontal-start){left:calc(10px + var(--ion-safe-area-left, 0px))}:host-context([dir=rtl]):host(.fab-horizontal-start),:host-context([dir=rtl]).fab-horizontal-start{left:unset;right:unset;right:calc(10px + var(--ion-safe-area-left, 0px))}:host(.fab-horizontal-end){right:calc(10px + var(--ion-safe-area-right, 0px))}:host-context([dir=rtl]):host(.fab-horizontal-end),:host-context([dir=rtl]).fab-horizontal-end{left:unset;right:unset;left:calc(10px + var(--ion-safe-area-right, 0px))}:host(.fab-vertical-top){top:10px}:host(.fab-vertical-top.fab-edge){top:-28px}:host(.fab-vertical-bottom){bottom:10px}:host(.fab-vertical-bottom.fab-edge){bottom:-28px}:host(.fab-vertical-center){margin-top:-28px;top:50%}";var b=function(){function o(t){var r=this;n(this,o),Object(c.o)(this,t),this.ionFocus=Object(c.g)(this,"ionFocus",7),this.ionBlur=Object(c.g)(this,"ionBlur",7),this.activated=!1,this.disabled=!1,this.routerDirection="forward",this.show=!1,this.translucent=!1,this.type="button",this.closeIcon="close",this.onFocus=function(){r.ionFocus.emit()},this.onBlur=function(){r.ionBlur.emit()}}return e(o,[{key:"render",value:function(){var o,r=this,n=this.el,i=this.disabled,e=this.color,a=this.href,s=this.activated,u=this.show,b=this.translucent,f=this.size,h=Object(d.c)("ion-fab-list",n),p=Object(l.b)(this),g=void 0===a?"button":"a",v="button"===g?{type:this.type}:{download:this.download,href:a,rel:this.rel,target:this.target};return Object(c.j)(c.c,{"aria-disabled":i?"true":null,class:Object(d.a)(e,(o={},t(o,p,!0),t(o,"fab-button-in-list",h),t(o,"fab-button-translucent-in-list",h&&b),t(o,"fab-button-close-active",s),t(o,"fab-button-show",u),t(o,"fab-button-disabled",i),t(o,"fab-button-translucent",b),t(o,"ion-activatable",!0),t(o,"ion-focusable",!0),t(o,"fab-button-"+f,void 0!==f),o))},Object(c.j)(g,Object.assign({},v,{class:"button-native",part:"native",disabled:i,onFocus:this.onFocus,onBlur:this.onBlur,onClick:function(t){return Object(d.d)(a,t,r.routerDirection,r.routerAnimation)}}),Object(c.j)("ion-icon",{icon:this.closeIcon,part:"close-icon",class:"close-icon",lazy:!1}),Object(c.j)("span",{class:"button-inner"},Object(c.j)("slot",null)),"md"===p&&Object(c.j)("ion-ripple-effect",null)))}},{key:"el",get:function(){return Object(c.k)(this)}}]),o}();b.style={ios:':host{--color-activated:var(--color);--color-focused:var(--color);--color-hover:var(--color);--background-hover:var(--ion-color-primary-contrast, #fff);--background-hover-opacity:.08;--transition:background-color, opacity 100ms linear;--ripple-color:currentColor;--border-radius:50%;--border-width:0;--border-style:none;--border-color:initial;--padding-top:0;--padding-end:0;--padding-bottom:0;--padding-start:0;margin-left:0;margin-right:0;margin-top:0;margin-bottom:0;display:block;width:56px;height:56px;font-size:14px;text-align:center;text-overflow:ellipsis;text-transform:none;white-space:nowrap;-webkit-font-kerning:none;font-kerning:none}.button-native{border-radius:var(--border-radius);padding-left:var(--padding-start);padding-right:var(--padding-end);padding-top:var(--padding-top);padding-bottom:var(--padding-bottom);font-family:inherit;font-size:inherit;font-style:inherit;font-weight:inherit;letter-spacing:inherit;text-decoration:inherit;text-indent:inherit;text-overflow:inherit;text-transform:inherit;text-align:inherit;white-space:inherit;color:inherit;display:block;position:relative;width:100%;height:100%;-webkit-transform:var(--transform);transform:var(--transform);-webkit-transition:var(--transition);transition:var(--transition);border-width:var(--border-width);border-style:var(--border-style);border-color:var(--border-color);outline:none;background:var(--background);background-clip:padding-box;color:var(--color);-webkit-box-shadow:var(--box-shadow);box-shadow:var(--box-shadow);contain:strict;cursor:pointer;overflow:hidden;z-index:0;-webkit-appearance:none;-moz-appearance:none;appearance:none;-webkit-box-sizing:border-box;box-sizing:border-box}@supports ((-webkit-margin-start: 0) or (margin-inline-start: 0)) or (-webkit-margin-start: 0){.button-native{padding-left:unset;padding-right:unset;-webkit-padding-start:var(--padding-start);padding-inline-start:var(--padding-start);-webkit-padding-end:var(--padding-end);padding-inline-end:var(--padding-end)}}::slotted(ion-icon){line-height:1}.button-native::after{left:0;right:0;top:0;bottom:0;position:absolute;content:"";opacity:0}.button-inner{left:0;right:0;top:0;display:-ms-flexbox;display:flex;position:absolute;-ms-flex-flow:row nowrap;flex-flow:row nowrap;-ms-flex-negative:0;flex-shrink:0;-ms-flex-align:center;align-items:center;-ms-flex-pack:center;justify-content:center;height:100%;-webkit-transition:all ease-in-out 300ms;transition:all ease-in-out 300ms;-webkit-transition-property:opacity, -webkit-transform;transition-property:opacity, -webkit-transform;transition-property:transform, opacity;transition-property:transform, opacity, -webkit-transform;z-index:1}:host(.fab-button-disabled){cursor:default;opacity:0.5;pointer-events:none}@media (any-hover: hover){:host(:hover) .button-native{color:var(--color-hover)}:host(:hover) .button-native::after{background:var(--background-hover);opacity:var(--background-hover-opacity)}}:host(.ion-focused) .button-native{color:var(--color-focused)}:host(.ion-focused) .button-native::after{background:var(--background-focused);opacity:var(--background-focused-opacity)}:host(.ion-activated) .button-native{color:var(--color-activated)}:host(.ion-activated) .button-native::after{background:var(--background-activated);opacity:var(--background-activated-opacity)}::slotted(ion-icon){line-height:1}:host(.fab-button-small){margin-left:8px;margin-right:8px;margin-top:8px;margin-bottom:8px;width:40px;height:40px}@supports ((-webkit-margin-start: 0) or (margin-inline-start: 0)) or (-webkit-margin-start: 0){:host(.fab-button-small){margin-left:unset;margin-right:unset;-webkit-margin-start:8px;margin-inline-start:8px;-webkit-margin-end:8px;margin-inline-end:8px}}.close-icon{margin-left:auto;margin-right:auto;margin-top:0;margin-bottom:0;left:0;right:0;top:0;position:absolute;height:100%;-webkit-transform:scale(0.4) rotateZ(-45deg);transform:scale(0.4) rotateZ(-45deg);-webkit-transition:all ease-in-out 300ms;transition:all ease-in-out 300ms;-webkit-transition-property:opacity, -webkit-transform;transition-property:opacity, -webkit-transform;transition-property:transform, opacity;transition-property:transform, opacity, -webkit-transform;font-size:var(--close-icon-font-size);opacity:0;z-index:1}@supports ((-webkit-margin-start: 0) or (margin-inline-start: 0)) or (-webkit-margin-start: 0){.close-icon{margin-left:unset;margin-right:unset;-webkit-margin-start:auto;margin-inline-start:auto;-webkit-margin-end:auto;margin-inline-end:auto}}:host(.fab-button-close-active) .close-icon{-webkit-transform:scale(1) rotateZ(0deg);transform:scale(1) rotateZ(0deg);opacity:1}:host(.fab-button-close-active) .button-inner{-webkit-transform:scale(0.4) rotateZ(45deg);transform:scale(0.4) rotateZ(45deg);opacity:0}ion-ripple-effect{color:var(--ripple-color)}@supports ((-webkit-backdrop-filter: blur(0)) or (backdrop-filter: blur(0))){:host(.fab-button-translucent) .button-native{-webkit-backdrop-filter:var(--backdrop-filter);backdrop-filter:var(--backdrop-filter)}}:host(.ion-color) .button-native{background:var(--ion-color-base);color:var(--ion-color-contrast)}:host{--background:var(--ion-color-primary, #3880ff);--background-activated:var(--ion-color-primary-shade, #3171e0);--background-focused:var(--ion-color-primary-shade, #3171e0);--background-hover:var(--ion-color-primary-tint, #4c8dff);--background-activated-opacity:1;--background-focused-opacity:1;--background-hover-opacity:1;--color:var(--ion-color-primary-contrast, #fff);--box-shadow:0 4px 16px rgba(0, 0, 0, 0.12);--transition:0.2s transform cubic-bezier(0.25, 1.11, 0.78, 1.59);--close-icon-font-size:28px}:host(.ion-activated){--box-shadow:0 4px 16px rgba(0, 0, 0, 0.12);--transform:scale(1.1);--transition:0.2s transform ease-out}::slotted(ion-icon){font-size:28px}:host(.fab-button-in-list){--background:var(--ion-color-light, #f4f5f8);--background-activated:var(--ion-color-light-shade, #d7d8da);--background-focused:var(--background-activated);--background-hover:var(--ion-color-light-tint, #f5f6f9);--color:var(--ion-color-light-contrast, #000);--color-activated:var(--ion-color-light-contrast, #000);--color-focused:var(--color-activated);--transition:transform 200ms ease 10ms, opacity 200ms ease 10ms}:host(.fab-button-in-list) ::slotted(ion-icon){font-size:18px}:host(.ion-color.ion-focused) .button-native::after{background:var(--ion-color-shade)}:host(.ion-color.ion-focused) .button-native,:host(.ion-color.ion-activated) .button-native{color:var(--ion-color-contrast)}:host(.ion-color.ion-focused) .button-native::after,:host(.ion-color.ion-activated) .button-native::after{background:var(--ion-color-shade)}@media (any-hover: hover){:host(.ion-color:hover) .button-native{color:var(--ion-color-contrast)}:host(.ion-color:hover) .button-native::after{background:var(--ion-color-tint)}}@supports ((-webkit-backdrop-filter: blur(0)) or (backdrop-filter: blur(0))){:host(.fab-button-translucent){--background:rgba(var(--ion-color-primary-rgb, 56, 128, 255), 0.9);--background-hover:rgba(var(--ion-color-primary-rgb, 56, 128, 255), 0.8);--background-focused:rgba(var(--ion-color-primary-rgb, 56, 128, 255), 0.82);--backdrop-filter:saturate(180%) blur(20px)}:host(.fab-button-translucent-in-list){--background:rgba(var(--ion-color-light-rgb, 244, 245, 248), 0.9);--background-hover:rgba(var(--ion-color-light-rgb, 244, 245, 248), 0.8);--background-focused:rgba(var(--ion-color-light-rgb, 244, 245, 248), 0.82)}}@supports ((-webkit-backdrop-filter: blur(0)) or (backdrop-filter: blur(0))){@media (any-hover: hover){:host(.fab-button-translucent.ion-color:hover) .button-native{background:rgba(var(--ion-color-base-rgb), 0.8)}}:host(.ion-color.fab-button-translucent) .button-native{background:rgba(var(--ion-color-base-rgb), 0.9)}:host(.ion-color.ion-focused.fab-button-translucent) .button-native,:host(.ion-color.ion-activated.fab-button-translucent) .button-native{background:var(--ion-color-base)}}',md:':host{--color-activated:var(--color);--color-focused:var(--color);--color-hover:var(--color);--background-hover:var(--ion-color-primary-contrast, #fff);--background-hover-opacity:.08;--transition:background-color, opacity 100ms linear;--ripple-color:currentColor;--border-radius:50%;--border-width:0;--border-style:none;--border-color:initial;--padding-top:0;--padding-end:0;--padding-bottom:0;--padding-start:0;margin-left:0;margin-right:0;margin-top:0;margin-bottom:0;display:block;width:56px;height:56px;font-size:14px;text-align:center;text-overflow:ellipsis;text-transform:none;white-space:nowrap;-webkit-font-kerning:none;font-kerning:none}.button-native{border-radius:var(--border-radius);padding-left:var(--padding-start);padding-right:var(--padding-end);padding-top:var(--padding-top);padding-bottom:var(--padding-bottom);font-family:inherit;font-size:inherit;font-style:inherit;font-weight:inherit;letter-spacing:inherit;text-decoration:inherit;text-indent:inherit;text-overflow:inherit;text-transform:inherit;text-align:inherit;white-space:inherit;color:inherit;display:block;position:relative;width:100%;height:100%;-webkit-transform:var(--transform);transform:var(--transform);-webkit-transition:var(--transition);transition:var(--transition);border-width:var(--border-width);border-style:var(--border-style);border-color:var(--border-color);outline:none;background:var(--background);background-clip:padding-box;color:var(--color);-webkit-box-shadow:var(--box-shadow);box-shadow:var(--box-shadow);contain:strict;cursor:pointer;overflow:hidden;z-index:0;-webkit-appearance:none;-moz-appearance:none;appearance:none;-webkit-box-sizing:border-box;box-sizing:border-box}@supports ((-webkit-margin-start: 0) or (margin-inline-start: 0)) or (-webkit-margin-start: 0){.button-native{padding-left:unset;padding-right:unset;-webkit-padding-start:var(--padding-start);padding-inline-start:var(--padding-start);-webkit-padding-end:var(--padding-end);padding-inline-end:var(--padding-end)}}::slotted(ion-icon){line-height:1}.button-native::after{left:0;right:0;top:0;bottom:0;position:absolute;content:"";opacity:0}.button-inner{left:0;right:0;top:0;display:-ms-flexbox;display:flex;position:absolute;-ms-flex-flow:row nowrap;flex-flow:row nowrap;-ms-flex-negative:0;flex-shrink:0;-ms-flex-align:center;align-items:center;-ms-flex-pack:center;justify-content:center;height:100%;-webkit-transition:all ease-in-out 300ms;transition:all ease-in-out 300ms;-webkit-transition-property:opacity, -webkit-transform;transition-property:opacity, -webkit-transform;transition-property:transform, opacity;transition-property:transform, opacity, -webkit-transform;z-index:1}:host(.fab-button-disabled){cursor:default;opacity:0.5;pointer-events:none}@media (any-hover: hover){:host(:hover) .button-native{color:var(--color-hover)}:host(:hover) .button-native::after{background:var(--background-hover);opacity:var(--background-hover-opacity)}}:host(.ion-focused) .button-native{color:var(--color-focused)}:host(.ion-focused) .button-native::after{background:var(--background-focused);opacity:var(--background-focused-opacity)}:host(.ion-activated) .button-native{color:var(--color-activated)}:host(.ion-activated) .button-native::after{background:var(--background-activated);opacity:var(--background-activated-opacity)}::slotted(ion-icon){line-height:1}:host(.fab-button-small){margin-left:8px;margin-right:8px;margin-top:8px;margin-bottom:8px;width:40px;height:40px}@supports ((-webkit-margin-start: 0) or (margin-inline-start: 0)) or (-webkit-margin-start: 0){:host(.fab-button-small){margin-left:unset;margin-right:unset;-webkit-margin-start:8px;margin-inline-start:8px;-webkit-margin-end:8px;margin-inline-end:8px}}.close-icon{margin-left:auto;margin-right:auto;margin-top:0;margin-bottom:0;left:0;right:0;top:0;position:absolute;height:100%;-webkit-transform:scale(0.4) rotateZ(-45deg);transform:scale(0.4) rotateZ(-45deg);-webkit-transition:all ease-in-out 300ms;transition:all ease-in-out 300ms;-webkit-transition-property:opacity, -webkit-transform;transition-property:opacity, -webkit-transform;transition-property:transform, opacity;transition-property:transform, opacity, -webkit-transform;font-size:var(--close-icon-font-size);opacity:0;z-index:1}@supports ((-webkit-margin-start: 0) or (margin-inline-start: 0)) or (-webkit-margin-start: 0){.close-icon{margin-left:unset;margin-right:unset;-webkit-margin-start:auto;margin-inline-start:auto;-webkit-margin-end:auto;margin-inline-end:auto}}:host(.fab-button-close-active) .close-icon{-webkit-transform:scale(1) rotateZ(0deg);transform:scale(1) rotateZ(0deg);opacity:1}:host(.fab-button-close-active) .button-inner{-webkit-transform:scale(0.4) rotateZ(45deg);transform:scale(0.4) rotateZ(45deg);opacity:0}ion-ripple-effect{color:var(--ripple-color)}@supports ((-webkit-backdrop-filter: blur(0)) or (backdrop-filter: blur(0))){:host(.fab-button-translucent) .button-native{-webkit-backdrop-filter:var(--backdrop-filter);backdrop-filter:var(--backdrop-filter)}}:host(.ion-color) .button-native{background:var(--ion-color-base);color:var(--ion-color-contrast)}:host{--background:var(--ion-color-primary, #3880ff);--background-activated:transparent;--background-focused:currentColor;--background-hover:currentColor;--background-activated-opacity:0;--background-focused-opacity:.24;--background-hover-opacity:.08;--color:var(--ion-color-primary-contrast, #fff);--box-shadow:0 3px 5px -1px rgba(0, 0, 0, 0.2), 0 6px 10px 0 rgba(0, 0, 0, 0.14), 0 1px 18px 0 rgba(0, 0, 0, 0.12);--transition:box-shadow 280ms cubic-bezier(0.4, 0, 0.2, 1), background-color 280ms cubic-bezier(0.4, 0, 0.2, 1), color 280ms cubic-bezier(0.4, 0, 0.2, 1), opacity 15ms linear 30ms, transform 270ms cubic-bezier(0, 0, 0.2, 1) 0ms;--close-icon-font-size:24px}:host(.ion-activated){--box-shadow:0 7px 8px -4px rgba(0, 0, 0, 0.2), 0 12px 17px 2px rgba(0, 0, 0, 0.14), 0 5px 22px 4px rgba(0, 0, 0, 0.12)}::slotted(ion-icon){font-size:24px}:host(.fab-button-in-list){--color:rgba(var(--ion-text-color-rgb, 0, 0, 0), 0.54);--color-activated:rgba(var(--ion-text-color-rgb, 0, 0, 0), 0.54);--color-focused:var(--color-activated);--background:var(--ion-color-light, #f4f5f8);--background-activated:transparent;--background-focused:var(--ion-color-light-shade, #d7d8da);--background-hover:var(--ion-color-light-tint, #f5f6f9)}:host(.fab-button-in-list) ::slotted(ion-icon){font-size:18px}:host(.ion-color.ion-focused) .button-native{color:var(--ion-color-contrast)}:host(.ion-color.ion-focused) .button-native::after{background:var(--ion-color-contrast)}:host(.ion-color.ion-activated) .button-native{color:var(--ion-color-contrast)}:host(.ion-color.ion-activated) .button-native::after{background:transparent}@media (any-hover: hover){:host(.ion-color:hover) .button-native{color:var(--ion-color-contrast)}:host(.ion-color:hover) .button-native::after{background:var(--ion-color-contrast)}}'};var f=function(){function o(t){n(this,o),Object(c.o)(this,t),this.activated=!1,this.side="bottom"}return e(o,[{key:"activatedChanged",value:function(t){var o=Array.from(this.el.querySelectorAll("ion-fab-button")),r=t?30:0;o.forEach((function(o,n){setTimeout((function(){return o.show=t}),n*r)}))}},{key:"render",value:function(){var o,r=Object(l.b)(this);return Object(c.j)(c.c,{class:(o={},t(o,r,!0),t(o,"fab-list-active",this.activated),t(o,"fab-list-side-"+this.side,!0),o)},Object(c.j)("slot",null))}},{key:"el",get:function(){return Object(c.k)(this)}}],[{key:"watchers",get:function(){return{activated:["activatedChanged"]}}}]),o}();f.style=":host{margin-left:0;margin-right:0;margin-top:66px;margin-bottom:66px;display:none;position:absolute;top:0;-ms-flex-direction:column;flex-direction:column;-ms-flex-align:center;align-items:center;min-width:56px;min-height:56px}:host(.fab-list-active){display:-ms-flexbox;display:flex}::slotted(.fab-button-in-list){margin-left:0;margin-right:0;margin-top:8px;margin-bottom:8px;width:40px;height:40px;-webkit-transform:scale(0);transform:scale(0);opacity:0;visibility:hidden}:host(.fab-list-side-top) ::slotted(.fab-button-in-list),:host(.fab-list-side-bottom) ::slotted(.fab-button-in-list){margin-left:0;margin-right:0;margin-top:5px;margin-bottom:5px}:host(.fab-list-side-start) ::slotted(.fab-button-in-list),:host(.fab-list-side-end) ::slotted(.fab-button-in-list){margin-left:5px;margin-right:5px;margin-top:0;margin-bottom:0}@supports ((-webkit-margin-start: 0) or (margin-inline-start: 0)) or (-webkit-margin-start: 0){:host(.fab-list-side-start) ::slotted(.fab-button-in-list),:host(.fab-list-side-end) ::slotted(.fab-button-in-list){margin-left:unset;margin-right:unset;-webkit-margin-start:5px;margin-inline-start:5px;-webkit-margin-end:5px;margin-inline-end:5px}}::slotted(.fab-button-in-list.fab-button-show){-webkit-transform:scale(1);transform:scale(1);opacity:1;visibility:visible}:host(.fab-list-side-top){top:auto;bottom:0;-ms-flex-direction:column-reverse;flex-direction:column-reverse}:host(.fab-list-side-start){margin-left:66px;margin-right:66px;margin-top:0;margin-bottom:0;right:0;-ms-flex-direction:row-reverse;flex-direction:row-reverse}@supports ((-webkit-margin-start: 0) or (margin-inline-start: 0)) or (-webkit-margin-start: 0){:host(.fab-list-side-start){margin-left:unset;margin-right:unset;-webkit-margin-start:66px;margin-inline-start:66px;-webkit-margin-end:66px;margin-inline-end:66px}}:host-context([dir=rtl]):host(.fab-list-side-start),:host-context([dir=rtl]).fab-list-side-start{left:unset;right:unset;left:0}:host(.fab-list-side-end){margin-left:66px;margin-right:66px;margin-top:0;margin-bottom:0;left:0;-ms-flex-direction:row;flex-direction:row}@supports ((-webkit-margin-start: 0) or (margin-inline-start: 0)) or (-webkit-margin-start: 0){:host(.fab-list-side-end){margin-left:unset;margin-right:unset;-webkit-margin-start:66px;margin-inline-start:66px;-webkit-margin-end:66px;margin-inline-end:66px}}:host-context([dir=rtl]):host(.fab-list-side-end),:host-context([dir=rtl]).fab-list-side-end{left:unset;right:unset;right:0}"}}])}();