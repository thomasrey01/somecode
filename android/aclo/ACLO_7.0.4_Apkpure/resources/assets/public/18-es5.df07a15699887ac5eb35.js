!function(){function n(n,o){return function(n){if(Array.isArray(n))return n}(n)||function(n,t){var o=null==n?null:"undefined"!=typeof Symbol&&n[Symbol.iterator]||n["@@iterator"];if(null==o)return;var i,e,r=[],c=!0,a=!1;try{for(o=o.call(n);!(c=(i=o.next()).done)&&(r.push(i.value),!t||r.length!==t);c=!0);}catch(s){a=!0,e=s}finally{try{c||null==o.return||o.return()}finally{if(a)throw e}}return r}(n,o)||function(n,o){if(!n)return;if("string"==typeof n)return t(n,o);var i=Object.prototype.toString.call(n).slice(8,-1);"Object"===i&&n.constructor&&(i=n.constructor.name);if("Map"===i||"Set"===i)return Array.from(n);if("Arguments"===i||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(i))return t(n,o)}(n,o)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function t(n,t){(null==t||t>n.length)&&(t=n.length);for(var o=0,i=new Array(t);o<t;o++)i[o]=n[o];return i}function o(n,t){if(!(n instanceof t))throw new TypeError("Cannot call a class as a function")}function i(n,t){for(var o=0;o<t.length;o++){var i=t[o];i.enumerable=i.enumerable||!1,i.configurable=!0,"value"in i&&(i.writable=!0),Object.defineProperty(n,i.key,i)}}function e(n,t,o){return t&&i(n.prototype,t),o&&i(n,o),Object.defineProperty(n,"prototype",{writable:!1}),n}(window.webpackJsonp=window.webpackJsonp||[]).push([[18],{"6hRX":function(t,i,r){"use strict";r.r(i),r.d(i,"LandingPageModule",(function(){return R}));var c=r("PCNd"),a=r("iInd"),s=r("mrSG"),l=r("itXk"),b=r("LRne"),u=r("IzEk"),g=r("pLZG"),d=r("lJxs"),f=r("tS1D"),p=r("JIr8"),h=r("sIb/"),m=r("C/rb"),v=r("777Z"),O=r("EMFo"),y=r("s7LF"),P=r("3bzS"),w=r("7bNT"),k=r("AytR"),x=r("XU5k"),z=r("E4px"),L=r("zEhZ"),C=r("8Y7J"),I=r("tqRt"),S=r("sZkV"),j=r("SVse"),A=r("TSSN");function $(n,t){1&n&&(C.Pb(0,"div",7),C.Lb(1,"ion-spinner",8),C.Ob())}function _(n,t){1&n&&(C.Pb(0,"ion-row"),C.Pb(1,"ion-col"),C.Pb(2,"div",3),C.Lb(3,"ion-spinner"),C.Ob(),C.Ob(),C.Ob())}function T(n,t){if(1&n){var o=C.Qb();C.Pb(0,"ion-button",15),C.Xb("click",(function(){C.oc(o);var n=t.$implicit;return C.Zb(2).onClickLoginWithOidcOption(n)})),C.vc(1),C.ac(2,"async"),C.ac(3,"async"),C.Ob()}if(2&n){var i=t.$implicit,e=C.Zb(2);C.zb(1),C.xc(" ","Surfconext"==i.name&&C.bc(2,1,e.surfconextLoginButtonText$)?C.bc(3,3,e.surfconextLoginButtonText$):i.name," ")}}function B(n,t){1&n&&(C.Pb(0,"ion-col",16),C.Pb(1,"h4"),C.vc(2),C.ac(3,"uppercase"),C.ac(4,"translate"),C.Ob(),C.Ob()),2&n&&(C.zb(2),C.wc(C.bc(3,1,C.bc(4,3,"general.or"))))}function M(n,t){if(1&n&&(C.Pb(0,"span"),C.vc(1),C.ac(2,"async"),C.Ob()),2&n){var o=C.Zb(3);C.zb(1),C.xc(" ",C.bc(2,1,o.emailLoginButtonText$)," ")}}function Z(n,t){1&n&&(C.Pb(0,"span"),C.vc(1),C.ac(2,"translate"),C.Ob()),2&n&&(C.zb(1),C.xc(" ",C.bc(2,1,"general.loginWithEmail")," "))}function E(n,t){if(1&n){var o=C.Qb();C.Pb(0,"ion-button",15),C.Xb("click",(function(){return C.oc(o),C.Zb(2).onClickEmailAuth()})),C.tc(1,M,3,3,"span",6),C.ac(2,"async"),C.tc(3,Z,3,3,"span",6),C.ac(4,"async"),C.Ob()}if(2&n){var i=C.Zb(2);C.zb(1),C.hc("ngIf",C.bc(2,2,i.emailLoginButtonText$)),C.zb(2),C.hc("ngIf",!C.bc(4,4,i.emailLoginButtonText$))}}function F(n,t){if(1&n){var o=C.Qb();C.Pb(0,"ion-row"),C.Pb(1,"ion-col"),C.Pb(2,"form",17),C.Xb("ngSubmit",(function(){return C.oc(o),C.Zb(2).onSubmitForm()})),C.Pb(3,"ion-list",18),C.Pb(4,"ion-item"),C.Pb(5,"ion-label",19),C.vc(6),C.ac(7,"translate"),C.Ob(),C.Lb(8,"ion-input",20),C.Ob(),C.Pb(9,"ion-item"),C.Pb(10,"ion-label",19),C.vc(11),C.ac(12,"translate"),C.Ob(),C.Lb(13,"ion-input",21),C.Ob(),C.Ob(),C.Pb(14,"h6",22),C.Pb(15,"span",23),C.vc(16),C.ac(17,"translate"),C.Ob(),C.Ob(),C.Pb(18,"ion-button",24),C.vc(19),C.ac(20,"translate"),C.Ob(),C.Ob(),C.Ob(),C.Ob()}if(2&n){var i=C.Zb(2);C.zb(2),C.hc("formGroup",i.form),C.zb(4),C.xc(" ",C.bc(7,6,"general.email")," "),C.zb(2),C.hc("debounce",300),C.zb(3),C.xc(" ",C.bc(12,8,"general.password")," "),C.zb(5),C.xc(" ",C.bc(17,10,"general.forgotPassword")," "),C.zb(3),C.xc(" ",C.bc(20,12,"general.login")," ")}}function K(n,t){1&n&&(C.Pb(0,"ion-row",25),C.Pb(1,"ion-col",9),C.Pb(2,"h6"),C.vc(3),C.ac(4,"translate"),C.Pb(5,"span",26),C.vc(6),C.ac(7,"translate"),C.Ob(),C.Ob(),C.Ob(),C.Ob()),2&n&&(C.zb(3),C.xc(" ",C.bc(4,2,"general.notAMemberYet")," "),C.zb(3),C.xc(" ",C.bc(7,4,"general.register")," "))}function X(n,t){if(1&n&&(C.Pb(0,"div"),C.Pb(1,"ion-row"),C.Pb(2,"ion-col",9),C.tc(3,T,4,5,"ion-button",10),C.Ob(),C.tc(4,B,5,5,"ion-col",11),C.Pb(5,"ion-col",12),C.tc(6,E,5,6,"ion-button",13),C.Ob(),C.Ob(),C.tc(7,F,21,14,"ion-row",6),C.tc(8,K,8,6,"ion-row",14),C.Ob()),2&n){var o=C.Zb();C.zb(3),C.hc("ngForOf",o.oidcProviderSettings),C.zb(1),C.hc("ngIf",null==o.oidcProviderSettings?null:o.oidcProviderSettings.length),C.zb(2),C.hc("ngIf",!o.emailAuthVisible),C.zb(1),C.hc("ngIf",o.emailAuthVisible),C.zb(1),C.hc("ngIf",o.registrationModuleConfig&&o.registrationModuleConfig.isActive)}}function J(n,t){if(1&n){var o=C.Qb();C.Pb(0,"ion-row"),C.Pb(1,"ion-col"),C.Pb(2,"ion-button",27),C.Xb("click",(function(){return C.oc(o),C.Zb().onClickCancel()})),C.vc(3),C.ac(4,"translate"),C.Ob(),C.Ob(),C.Ob()}2&n&&(C.zb(3),C.xc(" ",C.bc(4,1,"general.cancel")," "))}var N,V,q,G=[{path:"",component:(N=function(){function t(n,i,e,r,c){o(this,t),this.formBuilder=n,this.store=i,this.auth=e,this.actions$=r,this.router=c,this.emailAuthVisible=!1}return e(t,[{key:"ngOnInit",value:function(){var t,o,i=this;this.registrationModuleConfig=k.a.moduleConfigs.registrationModule,this.oidcProviderSettings=k.a.oidcProviderSettings,(null===(t=this.oidcProviderSettings)||void 0===t?void 0:t.length)||(this.emailAuthVisible=!0),(null===(o=this.oidcProviderSettings)||void 0===o?void 0:o.length)>0&&(console.log("AppAuth initializing"),this.auth.init(),this.auth.events$.subscribe((function(n){return i.onSignInSuccess(n)}))),this.isConfigLoading$=this.store.select(x.b.selectIsLoading),this.selectedLanguage$=this.store.select(O.a.selectById,"language"),this.logoUrl="assets/targets/".concat(k.a.targetAssetFolderKey,"/logo@256.png"),this.form=this.formBuilder.group({email:["",{validators:[Object(z.a)()],updateOn:"blur"}],password:["",y.r.required]}),this.form.get("email").valueChanges.pipe(Object(u.a)(1)).subscribe((function(n){i.form.setControl("email",new y.d(n,{validators:[y.r.required,Object(z.a)()],updateOn:"change"}))})),this.isLoading$=this.store.select(m.a.selectIsLoading),this.emailLoginButtonText$=Object(l.a)([this.store.select(x.b.selectByKey,"login_buttons_text"),this.selectedLanguage$]).pipe(Object(g.a)((function(n){return Boolean(n)})),Object(d.a)((function(t){var o=n(t,2),i=o[0],e=o[1];return(null==i?void 0:i.value)?JSON.parse(i.value)["email_login_button_text_"+e]:null}))),this.surfconextLoginButtonText$=Object(l.a)([this.store.select(x.b.selectByKey,"login_buttons_text"),this.selectedLanguage$]).pipe(Object(g.a)((function(n){return Boolean(n)})),Object(d.a)((function(t){var o=n(t,2),i=o[0],e=o[1];return(null==i?void 0:i.value)?JSON.parse(i.value)["surfconext_button_text_"+e]:null})))}},{key:"onSubmitForm",value:function(){var n=this.form.value;if(!this.form.valid)return this.form.controls.email.touched||this.form.setControl("email",new y.d(n.email,{validators:[y.r.required,Object(z.a)()],updateOn:"change"})),this.form.controls.email.markAsTouched(),this.form.controls.password.markAsTouched(),!1;this.store.dispatch(h.a.login({email:n.email,password:n.password}))}},{key:"onClickLoginWithOidcOption",value:function(n){var t=this;if(console.log("Oidc config login selected",n),this.store.dispatch(v.a.loadProviderSettings({providerSettings:n})),n.name!==k.a.defaultOidcProvider)return console.log("Loading non default oidc config before redirection"),void this.actions$.pipe(Object(w.d)(v.a.loadProviderConfig),Object(u.a)(1),Object(f.a)(5e3),Object(p.a)((function(n){return Object(b.a)(null)}))).subscribe((function(n){console.log("Loading non default oidc config completed, will now redirect",n),setTimeout((function(){return t.store.dispatch(v.a.startAuth())}),10)}));console.log("Default oidc config was selected, starting auth process"),setTimeout((function(){return t.store.dispatch(v.a.startAuth())}),10)}},{key:"onSignInSuccess",value:function(n){n.action===L.a.Init&&console.log("AppAuth service has been initiated")}},{key:"onClickCancel",value:function(){this.store.dispatch(h.a.logout())}},{key:"onClickEmailAuth",value:function(){this.emailAuthVisible=!0}}]),t}(),N.\u0275fac=function(n){return new(n||N)(C.Kb(y.c),C.Kb(I.h),C.Kb(L.b),C.Kb(w.a),C.Kb(a.j))},N.\u0275cmp=C.Eb({type:N,selectors:[["app-landing"]],decls:16,vars:17,consts:[[3,"fullscreen"],["class","ion-padding",4,"ngIf"],[3,"hidden"],[1,"ion-text-center"],[1,"ion-padding","ion-margin-bottom","ion-margin-top"],["alt","logo",2,"width","30%","border-radius","10px",3,"src"],[4,"ngIf"],[1,"ion-padding"],[1,"ion-margin-horizontal"],[1,"ion-margin-top"],["expand","block",3,"click",4,"ngFor","ngForOf"],["size","12","class","ion-text-center",4,"ngIf"],["size","12"],["expand","block",3,"click",4,"ngIf"],["class","register-link-wrapper",4,"ngIf"],["expand","block",3,"click"],["size","12",1,"ion-text-center"],[3,"formGroup","ngSubmit"],["lines","full",1,"ion-no-margin","ion-no-padding"],["position","floating","color","secondary"],["formControlName","email","type","email","placeholder","john@example.com",3,"debounce"],["formControlName","password","type","password"],[1,"forgot-password-link-wrapper"],["routerLink","/forgot-password",1,"forgot-password-link"],["expand","block","type","submit",1,"ion-margin-top"],[1,"register-link-wrapper"],["routerLink","/registration",1,"register-link"],["expand","block","type","submit","color","tertiary",1,"ion-margin-top",3,"click"]],template:function(n,t){1&n&&(C.Pb(0,"ion-content",0),C.tc(1,$,2,0,"div",1),C.ac(2,"async"),C.Pb(3,"ion-grid",2),C.ac(4,"async"),C.Pb(5,"ion-row"),C.Pb(6,"ion-col",3),C.Pb(7,"div",4),C.Pb(8,"div"),C.Lb(9,"img",5),C.Ob(),C.Ob(),C.Ob(),C.Ob(),C.tc(10,_,4,0,"ion-row",6),C.ac(11,"async"),C.tc(12,X,9,5,"div",6),C.ac(13,"async"),C.tc(14,J,5,3,"ion-row",6),C.ac(15,"async"),C.Ob(),C.Ob()),2&n&&(C.hc("fullscreen",!0),C.zb(1),C.hc("ngIf",C.bc(2,7,t.isConfigLoading$)),C.zb(2),C.hc("hidden",C.bc(4,9,t.isConfigLoading$)),C.zb(6),C.hc("src",t.logoUrl,C.qc),C.zb(1),C.hc("ngIf",C.bc(11,11,t.isLoading$)),C.zb(2),C.hc("ngIf",!C.bc(13,13,t.isLoading$)),C.zb(2),C.hc("ngIf",C.bc(15,15,t.isLoading$)))},directives:[S.r,j.o,S.w,S.P,S.q,S.X,j.n,S.h,y.s,y.n,y.g,S.H,S.D,S.G,S.C,S.rb,y.m,y.e,S.pb,a.k],pipes:[j.b,j.r,A.c],styles:[".register-link-wrapper[_ngcontent-%COMP%]{margin-left:8px}.register-link-wrapper[_ngcontent-%COMP%]   .register-link[_ngcontent-%COMP%]{color:var(--ion-color-primary);font-weight:700;border-bottom:2px solid var(--ion-color-primary)}.forgot-password-link-wrapper[_ngcontent-%COMP%]{margin-left:8px}.forgot-password-link-wrapper[_ngcontent-%COMP%]   .forgot-password-link[_ngcontent-%COMP%]{font-size:.875rem;color:var(--ion-color-medium);border-bottom:2px dotted var(--ion-color-medium)}"]}),N=Object(s.b)([Object(P.a)()],N))}],Q=((q=e((function n(){o(this,n)}))).\u0275mod=C.Ib({type:q}),q.\u0275inj=C.Hb({factory:function(n){return new(n||q)},imports:[[a.m.forChild(G)],a.m]}),q),R=((V=e((function n(){o(this,n)}))).\u0275mod=C.Ib({type:V}),V.\u0275inj=C.Hb({factory:function(n){return new(n||V)},imports:[[c.a,Q]]}),V)}}])}();