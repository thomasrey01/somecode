!function(){function e(){"use strict";/*! regenerator-runtime -- Copyright (c) 2014-present, Facebook, Inc. -- license (MIT): https://github.com/facebook/regenerator/blob/main/LICENSE */e=function(){return t};var t={},n=Object.prototype,r=n.hasOwnProperty,i="function"==typeof Symbol?Symbol:{},o=i.iterator||"@@iterator",a=i.asyncIterator||"@@asyncIterator",s=i.toStringTag||"@@toStringTag";function c(e,t,n){return Object.defineProperty(e,t,{value:n,enumerable:!0,configurable:!0,writable:!0}),e[t]}try{c({},"")}catch(F){c=function(e,t,n){return e[t]=n}}function l(e,t,n,r){var i=t&&t.prototype instanceof f?t:f,o=Object.create(i.prototype),a=new C(r||[]);return o._invoke=function(e,t,n){var r="suspendedStart";return function(i,o){if("executing"===r)throw new Error("Generator is already running");if("completed"===r){if("throw"===i)throw o;return O()}for(n.method=i,n.arg=o;;){var a=n.delegate;if(a){var s=b(a,n);if(s){if(s===u)continue;return s}}if("next"===n.method)n.sent=n._sent=n.arg;else if("throw"===n.method){if("suspendedStart"===r)throw r="completed",n.arg;n.dispatchException(n.arg)}else"return"===n.method&&n.abrupt("return",n.arg);r="executing";var c=h(e,t,n);if("normal"===c.type){if(r=n.done?"completed":"suspendedYield",c.arg===u)continue;return{value:c.arg,done:n.done}}"throw"===c.type&&(r="completed",n.method="throw",n.arg=c.arg)}}}(e,n,a),o}function h(e,t,n){try{return{type:"normal",arg:e.call(t,n)}}catch(F){return{type:"throw",arg:F}}}t.wrap=l;var u={};function f(){}function d(){}function v(){}var p={};c(p,o,(function(){return this}));var g=Object.getPrototypeOf,m=g&&g(g(L([])));m&&m!==n&&r.call(m,o)&&(p=m);var y=v.prototype=f.prototype=Object.create(p);function w(e){["next","throw","return"].forEach((function(t){c(e,t,(function(e){return this._invoke(t,e)}))}))}function x(e,t){var n;this._invoke=function(i,o){function a(){return new t((function(n,a){!function n(i,o,a,s){var c=h(e[i],e,o);if("throw"!==c.type){var l=c.arg,u=l.value;return u&&"object"==typeof u&&r.call(u,"__await")?t.resolve(u.__await).then((function(e){n("next",e,a,s)}),(function(e){n("throw",e,a,s)})):t.resolve(u).then((function(e){l.value=e,a(l)}),(function(e){return n("throw",e,a,s)}))}s(c.arg)}(i,o,n,a)}))}return n=n?n.then(a,a):a()}}function b(e,t){var n=e.iterator[t.method];if(void 0===n){if(t.delegate=null,"throw"===t.method){if(e.iterator.return&&(t.method="return",t.arg=void 0,b(e,t),"throw"===t.method))return u;t.method="throw",t.arg=new TypeError("The iterator does not provide a 'throw' method")}return u}var r=h(n,e.iterator,t.arg);if("throw"===r.type)return t.method="throw",t.arg=r.arg,t.delegate=null,u;var i=r.arg;return i?i.done?(t[e.resultName]=i.value,t.next=e.nextLoc,"return"!==t.method&&(t.method="next",t.arg=void 0),t.delegate=null,u):i:(t.method="throw",t.arg=new TypeError("iterator result is not an object"),t.delegate=null,u)}function k(e){var t={tryLoc:e[0]};1 in e&&(t.catchLoc=e[1]),2 in e&&(t.finallyLoc=e[2],t.afterLoc=e[3]),this.tryEntries.push(t)}function E(e){var t=e.completion||{};t.type="normal",delete t.arg,e.completion=t}function C(e){this.tryEntries=[{tryLoc:"root"}],e.forEach(k,this),this.reset(!0)}function L(e){if(e){var t=e[o];if(t)return t.call(e);if("function"==typeof e.next)return e;if(!isNaN(e.length)){var n=-1,i=function t(){for(;++n<e.length;)if(r.call(e,n))return t.value=e[n],t.done=!1,t;return t.value=void 0,t.done=!0,t};return i.next=i}}return{next:O}}function O(){return{value:void 0,done:!0}}return d.prototype=v,c(y,"constructor",v),c(v,"constructor",d),d.displayName=c(v,s,"GeneratorFunction"),t.isGeneratorFunction=function(e){var t="function"==typeof e&&e.constructor;return!!t&&(t===d||"GeneratorFunction"===(t.displayName||t.name))},t.mark=function(e){return Object.setPrototypeOf?Object.setPrototypeOf(e,v):(e.__proto__=v,c(e,s,"GeneratorFunction")),e.prototype=Object.create(y),e},t.awrap=function(e){return{__await:e}},w(x.prototype),c(x.prototype,a,(function(){return this})),t.AsyncIterator=x,t.async=function(e,n,r,i,o){void 0===o&&(o=Promise);var a=new x(l(e,n,r,i),o);return t.isGeneratorFunction(n)?a:a.next().then((function(e){return e.done?e.value:a.next()}))},w(y),c(y,s,"Generator"),c(y,o,(function(){return this})),c(y,"toString",(function(){return"[object Generator]"})),t.keys=function(e){var t=[];for(var n in e)t.push(n);return t.reverse(),function n(){for(;t.length;){var r=t.pop();if(r in e)return n.value=r,n.done=!1,n}return n.done=!0,n}},t.values=L,C.prototype={constructor:C,reset:function(e){if(this.prev=0,this.next=0,this.sent=this._sent=void 0,this.done=!1,this.delegate=null,this.method="next",this.arg=void 0,this.tryEntries.forEach(E),!e)for(var t in this)"t"===t.charAt(0)&&r.call(this,t)&&!isNaN(+t.slice(1))&&(this[t]=void 0)},stop:function(){this.done=!0;var e=this.tryEntries[0].completion;if("throw"===e.type)throw e.arg;return this.rval},dispatchException:function(e){if(this.done)throw e;var t=this;function n(n,r){return a.type="throw",a.arg=e,t.next=n,r&&(t.method="next",t.arg=void 0),!!r}for(var i=this.tryEntries.length-1;i>=0;--i){var o=this.tryEntries[i],a=o.completion;if("root"===o.tryLoc)return n("end");if(o.tryLoc<=this.prev){var s=r.call(o,"catchLoc"),c=r.call(o,"finallyLoc");if(s&&c){if(this.prev<o.catchLoc)return n(o.catchLoc,!0);if(this.prev<o.finallyLoc)return n(o.finallyLoc)}else if(s){if(this.prev<o.catchLoc)return n(o.catchLoc,!0)}else{if(!c)throw new Error("try statement without catch or finally");if(this.prev<o.finallyLoc)return n(o.finallyLoc)}}}},abrupt:function(e,t){for(var n=this.tryEntries.length-1;n>=0;--n){var i=this.tryEntries[n];if(i.tryLoc<=this.prev&&r.call(i,"finallyLoc")&&this.prev<i.finallyLoc){var o=i;break}}o&&("break"===e||"continue"===e)&&o.tryLoc<=t&&t<=o.finallyLoc&&(o=null);var a=o?o.completion:{};return a.type=e,a.arg=t,o?(this.method="next",this.next=o.finallyLoc,u):this.complete(a)},complete:function(e,t){if("throw"===e.type)throw e.arg;return"break"===e.type||"continue"===e.type?this.next=e.arg:"return"===e.type?(this.rval=this.arg=e.arg,this.method="return",this.next="end"):"normal"===e.type&&t&&(this.next=t),u},finish:function(e){for(var t=this.tryEntries.length-1;t>=0;--t){var n=this.tryEntries[t];if(n.finallyLoc===e)return this.complete(n.completion,n.afterLoc),E(n),u}},catch:function(e){for(var t=this.tryEntries.length-1;t>=0;--t){var n=this.tryEntries[t];if(n.tryLoc===e){var r=n.completion;if("throw"===r.type){var i=r.arg;E(n)}return i}}throw new Error("illegal catch attempt")},delegateYield:function(e,t,n){return this.delegate={iterator:L(e),resultName:t,nextLoc:n},"next"===this.method&&(this.arg=void 0),u}},t}function t(e,t,n,r,i,o,a){try{var s=e[o](a),c=s.value}catch(l){return void n(l)}s.done?t(c):Promise.resolve(c).then(r,i)}function n(e){return function(){var n=this,r=arguments;return new Promise((function(i,o){var a=e.apply(n,r);function s(e){t(a,i,o,s,c,"next",e)}function c(e){t(a,i,o,s,c,"throw",e)}s(void 0)}))}}function r(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}function i(e,t){for(var n=0;n<t.length;n++){var r=t[n];r.enumerable=r.enumerable||!1,r.configurable=!0,"value"in r&&(r.writable=!0),Object.defineProperty(e,r.key,r)}}function o(e,t,n){return t&&i(e.prototype,t),n&&i(e,n),Object.defineProperty(e,"prototype",{writable:!1}),e}(window.webpackJsonp=window.webpackJsonp||[]).push([[79],{eGHz:function(t,i,a){"use strict";a.r(i),a.d(i,"pwa_camera",(function(){return l}));var s=a("JHRn"),c=window.ImageCapture;void 0===c&&(c=function(){function e(t){var n=this;if(r(this,e),"video"!==t.kind)throw new DOMException("NotSupportedError");this._videoStreamTrack=t,"readyState"in this._videoStreamTrack||(this._videoStreamTrack.readyState="live"),this._previewStream=new MediaStream([t]),this.videoElement=document.createElement("video"),this.videoElementPlaying=new Promise((function(e){n.videoElement.addEventListener("playing",e)})),HTMLMediaElement?this.videoElement.srcObject=this._previewStream:this.videoElement.src=URL.createObjectURL(this._previewStream),this.videoElement.muted=!0,this.videoElement.setAttribute("playsinline",""),this.videoElement.play(),this.canvasElement=document.createElement("canvas"),this.canvas2dContext=this.canvasElement.getContext("2d")}return o(e,[{key:"videoStreamTrack",get:function(){return this._videoStreamTrack}},{key:"getPhotoCapabilities",value:function(){return new Promise((function(e,t){var n={current:0,min:0,max:0};e({exposureCompensation:n,exposureMode:"none",fillLightMode:["none"],focusMode:"none",imageHeight:n,imageWidth:n,iso:n,redEyeReduction:!1,whiteBalanceMode:"none",zoom:n}),t(new DOMException("OperationError"))}))}},{key:"setOptions",value:function(){return new Promise((function(e,t){}))}},{key:"takePhoto",value:function(){var e=this;return new Promise((function(t,n){if("live"!==e._videoStreamTrack.readyState)return n(new DOMException("InvalidStateError"));e.videoElementPlaying.then((function(){try{e.canvasElement.width=e.videoElement.videoWidth,e.canvasElement.height=e.videoElement.videoHeight,e.canvas2dContext.drawImage(e.videoElement,0,0),e.canvasElement.toBlob(t)}catch(r){n(new DOMException("UnknownError"))}}))}))}},{key:"grabFrame",value:function(){var e=this;return new Promise((function(t,n){if("live"!==e._videoStreamTrack.readyState)return n(new DOMException("InvalidStateError"));e.videoElementPlaying.then((function(){try{e.canvasElement.width=e.videoElement.videoWidth,e.canvasElement.height=e.videoElement.videoHeight,e.canvas2dContext.drawImage(e.videoElement,0,0),t(window.createImageBitmap(e.canvasElement))}catch(r){n(new DOMException("UnknownError"))}}))}))}}]),e}()),window.ImageCapture=c;var l=function(){function t(e){r(this,t),Object(s.h)(this,e),this.facingMode="user",this.showShutterOverlay=!1,this.flashIndex=0,this.hasMultipleCameras=!1,this.hasFlash=!1,this.flashModes=[],this.flashMode="off",this.isServer=Object(s.e)(this,"isServer")}var i,a,c,l,h,u,f,d;return o(t,[{key:"componentDidLoad",value:(d=n(e().mark((function t(){return e().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:if(e.t0=this.isServer,e.t0){e.next=7;break}return this.defaultConstraints={video:{facingMode:this.facingMode}},e.next=5,this.queryDevices();case 5:return e.next=7,this.initCamera();case 7:case"end":return e.stop()}}),t,this)}))),function(){return d.apply(this,arguments)})},{key:"componentDidUnload",value:function(){this.stopStream(),this.photoSrc&&URL.revokeObjectURL(this.photoSrc)}},{key:"hasImageCapture",value:function(){return"ImageCapture"in window}},{key:"queryDevices",value:(f=n(e().mark((function t(){var n;return e().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.prev=0,e.next=3,navigator.mediaDevices.enumerateDevices();case 3:n=e.sent,this.hasMultipleCameras=n.filter((function(e){return"videoinput"==e.kind})).length>1,e.next=10;break;case 7:e.prev=7,e.t0=e.catch(0),this.onPhoto(e.t0);case 10:case"end":return e.stop()}}),t,this,[[0,7]])}))),function(){return f.apply(this,arguments)})},{key:"initCamera",value:(u=n(e().mark((function t(n){var r;return e().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return n||(n=this.defaultConstraints),e.prev=1,e.next=4,navigator.mediaDevices.getUserMedia(Object.assign({video:!0,audio:!1},n));case 4:r=e.sent,this.initStream(r),e.next=11;break;case 8:e.prev=8,e.t0=e.catch(1),this.onPhoto(e.t0);case 11:case"end":return e.stop()}}),t,this,[[1,8]])}))),function(e){return u.apply(this,arguments)})},{key:"initStream",value:(h=n(e().mark((function t(n){return e().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:if(this.stream=n,this.videoElement.srcObject=n,console.log(n.getVideoTracks()[0]),e.t0=this.hasImageCapture(),!e.t0){e.next=8;break}return this.imageCapture=new window.ImageCapture(n.getVideoTracks()[0]),e.next=8,this.initPhotoCapabilities(this.imageCapture);case 8:this.el.forceUpdate();case 9:case"end":return e.stop()}}),t,this)}))),function(e){return h.apply(this,arguments)})},{key:"initPhotoCapabilities",value:(l=n(e().mark((function t(n){var r;return e().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,n.getPhotoCapabilities();case 2:(r=e.sent).fillLightMode.length>1&&(this.flashModes=r.fillLightMode.map((function(e){return e})),this.flashMode?(this.flashMode=this.flashModes[this.flashModes.indexOf(this.flashMode)]||"off",this.flashIndex=this.flashModes.indexOf(this.flashMode)||0):this.flashIndex=0);case 4:case"end":return e.stop()}}),t,this)}))),function(e){return l.apply(this,arguments)})},{key:"stopStream",value:function(){this.stream&&this.stream.getTracks().forEach((function(e){return e.stop()}))}},{key:"capture",value:(c=n(e().mark((function t(){var n;return e().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:if(!this.hasImageCapture()){e.next=13;break}return e.prev=1,e.next=4,this.imageCapture.takePhoto({fillLightMode:this.flashModes.length>1?this.flashMode:void 0});case 4:return n=e.sent,e.next=7,this.flashScreen();case 7:this.promptAccept(n),e.next=13;break;case 10:e.prev=10,e.t0=e.catch(1),console.error("Unable to take photo!",e.t0);case 13:this.stopStream();case 14:case"end":return e.stop()}}),t,this,[[1,10]])}))),function(){return c.apply(this,arguments)})},{key:"promptAccept",value:(a=n(e().mark((function t(n){return e().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:this.photo=n,this.photoSrc=URL.createObjectURL(n);case 1:case"end":return e.stop()}}),t,this)}))),function(e){return a.apply(this,arguments)})},{key:"rotate",value:function(){this.stopStream();var e=this.stream&&this.stream.getTracks()[0];if(e){var t=e.getConstraints().facingMode;t||(t=e.getCapabilities().facingMode[0]),this.initCamera("environment"===t?{video:{facingMode:"user"}}:{video:{facingMode:"environment"}})}}},{key:"setFlashMode",value:function(e){console.log("New flash mode: ",e),this.flashMode=e}},{key:"cycleFlash",value:function(){this.flashModes.length>0&&(this.flashIndex=(this.flashIndex+1)%this.flashModes.length,this.setFlashMode(this.flashModes[this.flashIndex]))}},{key:"flashScreen",value:(i=n(e().mark((function t(){var n=this;return e().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.abrupt("return",new Promise((function(e,t){n.showShutterOverlay=!0,setTimeout((function(){n.showShutterOverlay=!1,e()}),100)})));case 1:case"end":return e.stop()}}),t)}))),function(){return i.apply(this,arguments)})},{key:"handleShutterClick",value:function(e){console.log(),this.capture()}},{key:"handleRotateClick",value:function(e){this.rotate()}},{key:"handleClose",value:function(e){this.onPhoto&&this.onPhoto(null)}},{key:"handleFlashClick",value:function(e){this.cycleFlash()}},{key:"handleCancelPhoto",value:function(e){this.photo=null,this.initCamera()}},{key:"handleAcceptPhoto",value:function(e){this.onPhoto&&this.onPhoto(this.photo)}},{key:"iconExit",value:function(){return"data:image/svg+xml,%3Csvg version='1.1' id='Layer_1' xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink' x='0px' y='0px' viewBox='0 0 512 512' enable-background='new 0 0 512 512' xml:space='preserve'%3E%3Cg id='Icon_5_'%3E%3Cg%3E%3Cpath fill='%23FFFFFF' d='M402.2,134L378,109.8c-1.6-1.6-4.1-1.6-5.7,0L258.8,223.4c-1.6,1.6-4.1,1.6-5.7,0L139.6,109.8 c-1.6-1.6-4.1-1.6-5.7,0L109.8,134c-1.6,1.6-1.6,4.1,0,5.7l113.5,113.5c1.6,1.6,1.6,4.1,0,5.7L109.8,372.4c-1.6,1.6-1.6,4.1,0,5.7 l24.1,24.1c1.6,1.6,4.1,1.6,5.7,0l113.5-113.5c1.6-1.6,4.1-1.6,5.7,0l113.5,113.5c1.6,1.6,4.1,1.6,5.7,0l24.1-24.1 c1.6-1.6,1.6-4.1,0-5.7L288.6,258.8c-1.6-1.6-1.6-4.1,0-5.7l113.5-113.5C403.7,138.1,403.7,135.5,402.2,134z'/%3E%3C/g%3E%3C/g%3E%3C/svg%3E"}},{key:"iconConfirm",value:function(){return"data:image/svg+xml,%3Csvg version='1.1' id='Layer_1' xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink' x='0px' y='0px' viewBox='0 0 512 512' enable-background='new 0 0 512 512' xml:space='preserve'%3E%3Ccircle fill='%232CD865' cx='256' cy='256' r='256'/%3E%3Cg id='Icon_1_'%3E%3Cg%3E%3Cg%3E%3Cpath fill='%23FFFFFF' d='M208,301.4l-55.4-55.5c-1.5-1.5-4-1.6-5.6-0.1l-23.4,22.3c-1.6,1.6-1.7,4.1-0.1,5.7l81.6,81.4 c3.1,3.1,8.2,3.1,11.3,0l171.8-171.7c1.6-1.6,1.6-4.2-0.1-5.7l-23.4-22.3c-1.6-1.5-4.1-1.5-5.6,0.1L213.7,301.4 C212.1,303,209.6,303,208,301.4z'/%3E%3C/g%3E%3C/g%3E%3C/g%3E%3C/svg%3E"}},{key:"iconReverseCamera",value:function(){return"data:image/svg+xml,%3Csvg version='1.1' id='Layer_1' xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink' x='0px' y='0px' viewBox='0 0 512 512' enable-background='new 0 0 512 512' xml:space='preserve'%3E%3Cg%3E%3Cpath fill='%23FFFFFF' d='M352,0H160C72,0,0,72,0,160v192c0,88,72,160,160,160h192c88,0,160-72,160-160V160C512,72,440,0,352,0z M356.7,365.8l-3.7,3.3c-27,23.2-61.4,35.9-96.8,35.9c-72.4,0-135.8-54.7-147-125.6c-0.3-1.9-2-3.3-3.9-3.3H64 c-3.3,0-5.2-3.8-3.2-6.4l61.1-81.4c1.6-2.1,4.7-2.1,6.4-0.1l63.3,81.4c2,2.6,0.2,6.5-3.2,6.5h-40.6c-2.5,0-4.5,2.4-3.9,4.8 c11.5,51.5,59.2,90.6,112.4,90.6c26.4,0,51.8-9.7,73.7-27.9l3.1-2.5c1.6-1.3,3.9-1.1,5.3,0.3l18.5,18.6 C358.5,361.6,358.4,364.3,356.7,365.8z M451.4,245.6l-61,83.5c-1.6,2.2-4.8,2.2-6.4,0.1l-63.3-83.3c-2-2.6-0.1-6.4,3.2-6.4h40.8 c2.5,0,4.4-2.3,3.9-4.8c-5.1-24.2-17.8-46.5-36.5-63.7c-21.2-19.4-48.2-30.1-76-30.1c-26.5,0-52.6,9.7-73.7,27.3l-3.1,2.5 c-1.6,1.3-3.9,1.2-5.4-0.3l-18.5-18.5c-1.6-1.6-1.5-4.3,0.2-5.9l3.5-3.1c27-23.2,61.4-35.9,96.8-35.9c38,0,73.9,13.7,101.2,38.7 c23.2,21.1,40.3,55.2,45.7,90.1c0.3,1.9,1.9,3.4,3.9,3.4h41.3C451.4,239.2,453.3,243,451.4,245.6z'/%3E%3C/g%3E%3C/svg%3E"}},{key:"iconRetake",value:function(){return"data:image/svg+xml,%3Csvg version='1.1' id='Layer_1' xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink' x='0px' y='0px' viewBox='0 0 512 512' enable-background='new 0 0 512 512' xml:space='preserve'%3E%3Ccircle fill='%23727A87' cx='256' cy='256' r='256'/%3E%3Cg id='Icon_5_'%3E%3Cg%3E%3Cpath fill='%23FFFFFF' d='M394.2,142L370,117.8c-1.6-1.6-4.1-1.6-5.7,0L258.8,223.4c-1.6,1.6-4.1,1.6-5.7,0L147.6,117.8 c-1.6-1.6-4.1-1.6-5.7,0L117.8,142c-1.6,1.6-1.6,4.1,0,5.7l105.5,105.5c1.6,1.6,1.6,4.1,0,5.7L117.8,364.4c-1.6,1.6-1.6,4.1,0,5.7 l24.1,24.1c1.6,1.6,4.1,1.6,5.7,0l105.5-105.5c1.6-1.6,4.1-1.6,5.7,0l105.5,105.5c1.6,1.6,4.1,1.6,5.7,0l24.1-24.1 c1.6-1.6,1.6-4.1,0-5.7L288.6,258.8c-1.6-1.6-1.6-4.1,0-5.7l105.5-105.5C395.7,146.1,395.7,143.5,394.2,142z'/%3E%3C/g%3E%3C/g%3E%3C/svg%3E"}},{key:"iconFlashOff",value:function(){return"data:image/svg+xml,%3Csvg version='1.1' id='Layer_1' xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink' x='0px' y='0px' viewBox='0 0 512 512' style='enable-background:new 0 0 512 512;' xml:space='preserve'%3E%3Cstyle type='text/css'%3E .st0%7Bfill:%23FFFFFF;%7D%0A%3C/style%3E%3Cg%3E%3Cpath class='st0' d='M498,483.7L42.3,28L14,56.4l149.8,149.8L91,293.8c-2.5,3-0.1,7.2,3.9,7.2h143.9c1.6,0,2.7,1.3,2.4,2.7 L197.6,507c-1,4.4,5.8,6.9,8.9,3.2l118.6-142.8L469.6,512L498,483.7z'/%3E%3Cpath class='st0' d='M449,218.2c2.5-3,0.1-7.2-3.9-7.2H301.2c-1.6,0-2.7-1.3-2.4-2.7L342.4,5c1-4.4-5.8-6.9-8.9-3.2L214.9,144.6 l161.3,161.3L449,218.2z'/%3E%3C/g%3E%3C/svg%3E"}},{key:"iconFlashOn",value:function(){return"data:image/svg+xml,%3Csvg version='1.1' id='Layer_1' xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink' x='0px' y='0px' viewBox='0 0 512 512' style='enable-background:new 0 0 512 512;' xml:space='preserve'%3E%3Cstyle type='text/css'%3E .st0%7Bfill:%23FFFFFF;%7D%0A%3C/style%3E%3Cpath class='st0' d='M287.2,211c-1.6,0-2.7-1.3-2.4-2.7L328.4,5c1-4.4-5.8-6.9-8.9-3.2L77,293.8c-2.5,3-0.1,7.2,3.9,7.2h143.9 c1.6,0,2.7,1.3,2.4,2.7L183.6,507c-1,4.4,5.8,6.9,8.9,3.2l242.5-292c2.5-3,0.1-7.2-3.9-7.2L287.2,211L287.2,211z'/%3E%3C/svg%3E"}},{key:"iconFlashAuto",value:function(){return"data:image/svg+xml,%3Csvg version='1.1' id='Layer_1' xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink' x='0px' y='0px' viewBox='0 0 512 512' style='enable-background:new 0 0 512 512;' xml:space='preserve'%3E%3Cstyle type='text/css'%3E .st0%7Bfill:%23FFFFFF;%7D%0A%3C/style%3E%3Cpath class='st0' d='M287.2,211c-1.6,0-2.7-1.3-2.4-2.7L328.4,5c1-4.4-5.8-6.9-8.9-3.2L77,293.8c-2.5,3-0.1,7.2,3.9,7.2h143.9 c1.6,0,2.7,1.3,2.4,2.7L183.6,507c-1,4.4,5.8,6.9,8.9,3.2l242.5-292c2.5-3,0.1-7.2-3.9-7.2L287.2,211L287.2,211z'/%3E%3Cg%3E%3Cpath class='st0' d='M321.3,186l74-186H438l74,186h-43.5l-11.9-32.5h-80.9l-12,32.5H321.3z M415.8,47.9l-27.2,70.7h54.9l-27.2-70.7 H415.8z'/%3E%3C/g%3E%3C/svg%3E"}},{key:"render",value:function(){var e=this,t="user"==this.facingMode?{transform:"scaleX(-1)"}:{};return Object(s.g)("div",{class:"camera-wrapper"},Object(s.g)("div",{class:"camera-header"},Object(s.g)("section",{class:"items"},Object(s.g)("div",{class:"item close",onClick:function(t){return e.handleClose(t)}},Object(s.g)("img",{src:this.iconExit()})),Object(s.g)("div",{class:"item flash",onClick:function(t){return e.handleFlashClick(t)}},this.flashModes.length>0&&Object(s.g)("div",null,"off"==this.flashMode?Object(s.g)("img",{src:this.iconFlashOff()}):"","auto"==this.flashMode?Object(s.g)("img",{src:this.iconFlashAuto()}):"","flash"==this.flashMode?Object(s.g)("img",{src:this.iconFlashOn()}):"")))),this.photo?Object(s.g)("div",{class:"accept"},Object(s.g)("div",{class:"accept-image",style:{backgroundImage:"url(".concat(this.photoSrc,")")}})):Object(s.g)("div",{class:"camera-video"},this.showShutterOverlay&&Object(s.g)("div",{class:"shutter-overlay"}),this.hasImageCapture()?Object(s.g)("video",{style:t,ref:function(t){return e.videoElement=t},autoplay:!0,playsinline:!0}):Object(s.g)("canvas",{ref:function(t){return e.canvasElement=t},width:"100%",height:"100%"}),Object(s.g)("canvas",{class:"offscreen-image-render",ref:function(t){return e.offscreenCanvas=t},width:"100%",height:"100%"})),Object(s.g)("div",{class:"camera-footer"},this.photo?Object(s.g)("section",{class:"items"},Object(s.g)("div",{class:"item accept-cancel",onClick:function(t){return e.handleCancelPhoto(t)}},Object(s.g)("img",{src:this.iconRetake()})),Object(s.g)("div",{class:"item accept-use",onClick:function(t){return e.handleAcceptPhoto(t)}},Object(s.g)("img",{src:this.iconConfirm()}))):[Object(s.g)("div",{class:"shutter",onClick:function(t){return e.handleShutterClick(t)}},Object(s.g)("div",{class:"shutter-button"})),Object(s.g)("div",{class:"rotate",onClick:function(t){return e.handleRotateClick(t)}},Object(s.g)("img",{src:this.iconReverseCamera()}))]))}},{key:"el",get:function(){return Object(s.f)(this)}}],[{key:"assetsDirs",get:function(){return["icons"]}},{key:"style",get:function(){return":host{--header-height:4em;--footer-height:9em;--shutter-size:6em;--icon-size-header:1.5em;--icon-size-footer:2.5em;--margin-size-header:1.5em;--margin-size-footer:2.0em;font-family:-apple-system,BlinkMacSystemFont,\u201cSegoe UI\u201d,\u201cRoboto\u201d,\u201cDroid Sans\u201d,\u201cHelvetica Neue\u201d,sans-serif;display:block}.items,:host{width:100%;height:100%}.items{-webkit-box-sizing:border-box;box-sizing:border-box;display:-ms-flexbox;display:flex;-ms-flex-align:center;align-items:center;-ms-flex-pack:center;justify-content:center}.items .item{-ms-flex:1;flex:1;text-align:center}.items .item:first-child{text-align:left}.items .item:last-child{text-align:right}.camera-wrapper{position:relative;display:-ms-flexbox;display:flex;-ms-flex-direction:column;flex-direction:column;width:100%;height:100%}.camera-header{color:#fff;background-color:#000;height:var(--header-height)}.camera-header .items{padding:var(--margin-size-header)}.camera-footer{position:relative;color:#fff;background-color:#000;height:var(--footer-height)}.camera-footer .items{padding:var(--margin-size-footer)}.camera-video{position:relative;-ms-flex:1;flex:1;overflow:hidden}.camera-video,video{background-color:#000}video{width:100%;height:100%;max-height:100%;min-height:100%;-o-object-fit:cover;object-fit:cover}.shutter{position:absolute;left:50%;top:50%;width:var(--shutter-size);height:var(--shutter-size);margin-top:calc(var(--shutter-size) / -2);margin-left:calc(var(--shutter-size) / -2);border-radius:100%;background-color:#c6cdd8;padding:12px;-webkit-box-sizing:border-box;box-sizing:border-box}.shutter:active .shutter-button{background-color:#9da9bb}.shutter-button{background-color:#fff;border-radius:100%;width:100%;height:100%}.rotate{display:-ms-flexbox;display:flex;-ms-flex-align:center;align-items:center;position:absolute;right:var(--margin-size-footer);top:0;height:100%;color:#fff}.rotate,.rotate img{width:var(--icon-size-footer)}.rotate img{height:var(--icon-size-footer)}.shutter-overlay{z-index:5;position:absolute;width:100%;height:100%;background-color:#000}.error{width:100%;height:100%;color:#fff;display:-ms-flexbox;display:flex;-ms-flex-pack:center;justify-content:center;-ms-flex-align:center;align-items:center}.accept{background-color:#000;-ms-flex:1;flex:1}.accept .accept-image{width:100%;height:100%;background-position:50%;background-size:cover;background-repeat:no-repeat}.close img,.flash img{width:var(--icon-size-header);height:var(--icon-size-header)}.accept-cancel img,.accept-use img{width:var(--icon-size-footer);height:var(--icon-size-footer)}.offscreen-image-render{top:0;left:0;visibility:hidden;pointer-events:none;width:100%;height:100%}"}}]),t}()}}])}();