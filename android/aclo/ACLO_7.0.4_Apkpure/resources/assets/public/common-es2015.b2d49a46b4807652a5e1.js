(window.webpackJsonp=window.webpackJsonp||[]).push([[0],{"74mu":function(t,e,n){"use strict";n.d(e,"a",(function(){return r})),n.d(e,"b",(function(){return i})),n.d(e,"c",(function(){return s})),n.d(e,"d",(function(){return a}));const s=(t,e)=>null!==e.closest(t),r=(t,e)=>"string"==typeof t&&t.length>0?Object.assign({"ion-color":!0,["ion-color-"+t]:!0},e):e,i=t=>{const e={};return(t=>void 0!==t?(Array.isArray(t)?t:t.split(" ")).filter(t=>null!=t).map(t=>t.trim()).filter(t=>""!==t):[])(t).forEach(t=>e[t]=!0),e},c=/^[a-z][a-z0-9+\-.]*:/,a=async(t,e,n,s)=>{if(null!=t&&"#"!==t[0]&&!c.test(t)){const r=document.querySelector("ion-router");if(r)return null!=e&&e.preventDefault(),r.push(t,n,s)}return!1}},Zcj0:function(t,e,n){"use strict";n.d(e,"a",(function(){return c}));var s=n("wEJo"),r=n("qULd"),i=n("KF81");const c=(t,e)=>{let n,c;const a=(t,s,r)=>{if("undefined"==typeof document)return;const i=document.elementFromPoint(t,s);i&&e(i)?i!==n&&(u(),o(i,r)):u()},o=(t,e)=>{n=t,c||(c=n);const r=n;Object(s.f)(()=>r.classList.add("ion-activated")),e()},u=(t=!1)=>{if(!n)return;const e=n;Object(s.f)(()=>e.classList.remove("ion-activated")),t&&c!==n&&n.click(),n=void 0};return Object(i.createGesture)({el:t,gestureName:"buttonActiveDrag",threshold:0,onStart:t=>a(t.currentX,t.currentY,r.a),onMove:t=>a(t.currentX,t.currentY,r.b),onEnd:()=>{u(!0),Object(r.e)(),c=void 0}})}},h3R7:function(t,e,n){"use strict";n.d(e,"a",(function(){return s}));const s={bubbles:{dur:1e3,circles:9,fn:(t,e,n)=>{const s=t*e/n-t+"ms",r=2*Math.PI*e/n;return{r:5,style:{top:9*Math.sin(r)+"px",left:9*Math.cos(r)+"px","animation-delay":s}}}},circles:{dur:1e3,circles:8,fn:(t,e,n)=>{const s=e/n,r=t*s-t+"ms",i=2*Math.PI*s;return{r:5,style:{top:9*Math.sin(i)+"px",left:9*Math.cos(i)+"px","animation-delay":r}}}},circular:{dur:1400,elmDuration:!0,circles:1,fn:()=>({r:20,cx:48,cy:48,fill:"none",viewBox:"24 24 48 48",transform:"translate(0,0)",style:{}})},crescent:{dur:750,circles:1,fn:()=>({r:26,style:{}})},dots:{dur:750,circles:3,fn:(t,e)=>({r:6,style:{left:9-9*e+"px","animation-delay":-110*e+"ms"}})},lines:{dur:1e3,lines:12,fn:(t,e,n)=>({y1:17,y2:29,style:{transform:`rotate(${30*e+(e<6?180:-180)}deg)`,"animation-delay":t*e/n-t+"ms"}})},"lines-small":{dur:1e3,lines:12,fn:(t,e,n)=>({y1:12,y2:20,style:{transform:`rotate(${30*e+(e<6?180:-180)}deg)`,"animation-delay":t*e/n-t+"ms"}})}}},hLNb:function(t,e,n){"use strict";n.d(e,"a",(function(){return d}));var s=n("8Y7J"),r=n("SVse"),i=n("sZkV"),c=n("yFzK"),a=n("TSSN");function o(t,e){1&t&&s.Lb(0,"ion-icon",5)}function u(t,e){1&t&&s.Lb(0,"ion-icon",6)}function l(t,e){if(1&t&&(s.Pb(0,"div",1),s.tc(1,o,1,0,"ion-icon",2),s.tc(2,u,1,0,"ion-icon",3),s.Pb(3,"ion-text",4),s.vc(4),s.ac(5,"replaceVarInString"),s.ac(6,"translate"),s.Ob(),s.Ob()),2&t){const t=e.$implicit,n=s.Zb();s.zb(1),s.hc("ngIf",t.status),s.zb(1),s.hc("ngIf",!t.status),s.zb(2),s.xc("",s.dc(5,3,s.bc(6,7,t.label),"$MIN_LENGTH$",n.minLength.toString())," ")}}let d=(()=>{class t{constructor(){this.minLength=8,this.containsNumber=!0,this.containsSpecialCharacter=!0,this.containsLowercaseLetter=!0,this.containsUppercaseLetter=!0,this.requirementsUpdated=new s.n,this.requirements=[]}ngOnInit(){this.updateRequirements()}ngOnChanges(t){t.password&&(this.updateRequirements(),this.requirementsUpdated.emit(Boolean(!this.requirements.find(t=>0==t.status))))}updateRequirements(){this.requirements=[],this.requirements.push({label:"general.passwordLenght",status:this.password&&this.password.length>=this.minLength}),this.containsNumber&&this.requirements.push({label:"general.containsNumber",status:/[0-9]/.test(this.password)}),this.containsSpecialCharacter&&this.requirements.push({label:"general.containsSpecialCharacter",status:/[!@#$%*]/.test(this.password)}),this.containsLowercaseLetter&&this.requirements.push({label:"general.containsLowercaseLetter",status:/[a-z]/.test(this.password)}),this.containsUppercaseLetter&&this.requirements.push({label:"general.containsUppercaseLetter",status:/[A-Z]/.test(this.password)})}}return t.\u0275fac=function(e){return new(e||t)},t.\u0275cmp=s.Eb({type:t,selectors:[["show-password-requirements"]],inputs:{password:"password",minLength:"minLength",containsNumber:"containsNumber",containsSpecialCharacter:"containsSpecialCharacter",containsLowercaseLetter:"containsLowercaseLetter",containsUppercaseLetter:"containsUppercaseLetter"},outputs:{requirementsUpdated:"requirementsUpdated"},features:[s.xb],decls:1,vars:1,consts:[["class","smaller",4,"ngFor","ngForOf"],[1,"smaller"],["color","success","name","checkmark-circle",4,"ngIf"],["color","danger","name","close-circle",4,"ngIf"],["color","secondary"],["color","success","name","checkmark-circle"],["color","danger","name","close-circle"]],template:function(t,e){1&t&&s.tc(0,l,7,9,"div",0),2&t&&s.hc("ngForOf",e.requirements)},directives:[r.n,r.o,i.bb,i.y],pipes:[c.a,a.c],styles:["ion-icon[_ngcontent-%COMP%]{margin-right:8px}"]}),t})()},qULd:function(t,e,n){"use strict";n.d(e,"a",(function(){return i})),n.d(e,"b",(function(){return c})),n.d(e,"c",(function(){return r})),n.d(e,"d",(function(){return o})),n.d(e,"e",(function(){return a}));const s={getEngine(){const t=window;return t.TapticEngine||t.Capacitor&&t.Capacitor.isPluginAvailable("Haptics")&&t.Capacitor.Plugins.Haptics},available(){return!!this.getEngine()},isCordova:()=>!!window.TapticEngine,isCapacitor:()=>!!window.Capacitor,impact(t){const e=this.getEngine();if(!e)return;const n=this.isCapacitor()?t.style.toUpperCase():t.style;e.impact({style:n})},notification(t){const e=this.getEngine();if(!e)return;const n=this.isCapacitor()?t.style.toUpperCase():t.style;e.notification({style:n})},selection(){this.impact({style:"light"})},selectionStart(){const t=this.getEngine();t&&(this.isCapacitor()?t.selectionStart():t.gestureSelectionStart())},selectionChanged(){const t=this.getEngine();t&&(this.isCapacitor()?t.selectionChanged():t.gestureSelectionChanged())},selectionEnd(){const t=this.getEngine();t&&(this.isCapacitor()?t.selectionEnd():t.gestureSelectionEnd())}},r=()=>{s.selection()},i=()=>{s.selectionStart()},c=()=>{s.selectionChanged()},a=()=>{s.selectionEnd()},o=t=>{s.impact(t)}},spDm:function(t,e,n){"use strict";n.d(e,"a",(function(){return r})),n.d(e,"b",(function(){return i}));var s=n("W6o/");const r=async(t,e,n,r,i)=>{if(t)return t.attachViewToDom(e,n,i,r);if("string"!=typeof n&&!(n instanceof HTMLElement))throw new Error("framework delegate is missing");const c="string"==typeof n?e.ownerDocument&&e.ownerDocument.createElement(n):n;return r&&r.forEach(t=>c.classList.add(t)),i&&Object.assign(c,i),e.appendChild(c),await new Promise(t=>Object(s.c)(c,t)),c},i=(t,e)=>{if(e){if(t)return t.removeViewFromDom(e.parentElement,e);e.remove()}return Promise.resolve()}}}]);