!function(){function n(n,o){if(!(n instanceof o))throw new TypeError("Cannot call a class as a function")}function o(n,o){for(var e=0;e<o.length;e++){var r=o[e];r.enumerable=r.enumerable||!1,r.configurable=!0,"value"in r&&(r.writable=!0),Object.defineProperty(n,r.key,r)}}function e(n,e,r){return e&&o(n.prototype,e),r&&o(n,r),Object.defineProperty(n,"prototype",{writable:!1}),n}(window.webpackJsonp=window.webpackJsonp||[]).push([[20],{"+0Ue":function(o,r,s){"use strict";s.r(r),s.d(r,"ResetPasswordPageModule",(function(){return V}));var t=s("PCNd"),i=s("iInd"),a=s("s7LF"),c=s("Kj3r"),b=s("IzEk"),l=s("sIb/"),u=s("C/rb"),d=s("7+vQ"),f=s("iswq"),m=s("8Y7J"),p=s("tqRt"),w=s("sZkV"),h=s("SVse"),P=s("hLNb"),g=s("TSSN");function v(n,o){1&n&&(m.Pb(0,"ion-row"),m.Pb(1,"ion-col"),m.Pb(2,"div",3),m.Lb(3,"ion-spinner"),m.Ob(),m.Ob(),m.Ob())}function y(n,o){1&n&&(m.Pb(0,"ion-item",7),m.Pb(1,"ion-label",12),m.vc(2),m.ac(3,"translate"),m.ac(4,"translate"),m.Ob(),m.Ob()),2&n&&(m.hc("lines","none"),m.zb(2),m.yc(" ",m.bc(3,3,"general.password")," ",m.bc(4,5,"general.mustMatch")," "))}function O(n,o){if(1&n){var e=m.Qb();m.Pb(0,"ion-row"),m.Pb(1,"ion-col"),m.Pb(2,"form",4),m.Xb("ngSubmit",(function(){return m.oc(e),m.Zb().onFormSubmit()})),m.Pb(3,"ion-list"),m.Pb(4,"ion-item"),m.Pb(5,"ion-label",5),m.vc(6),m.ac(7,"translate"),m.Ob(),m.Lb(8,"ion-input",6),m.Ob(),m.Pb(9,"ion-item",7),m.Pb(10,"show-password-requirements",8),m.Xb("requirementsUpdated",(function(n){return m.oc(e),m.Zb().onPasswordRequirementsChange(n)})),m.Ob(),m.Ob(),m.Pb(11,"ion-item"),m.Pb(12,"ion-label",5),m.vc(13),m.ac(14,"translate"),m.ac(15,"translate"),m.Ob(),m.Lb(16,"ion-input",9),m.Ob(),m.tc(17,y,5,7,"ion-item",10),m.Ob(),m.Pb(18,"ion-button",11),m.vc(19),m.ac(20,"translate"),m.Ob(),m.Ob(),m.Ob(),m.Ob()}if(2&n){var r=m.Zb();m.zb(2),m.hc("formGroup",r.form),m.zb(4),m.xc(" ",m.bc(7,10,"general.password")," "),m.zb(3),m.hc("lines","none"),m.zb(1),m.hc("password",r.form.controls.password.value)("containsSpecialCharacter",!1),m.zb(3),m.yc(" ",m.bc(14,12,"general.confirm")," ",m.bc(15,14,"general.password")," "),m.zb(4),m.hc("ngIf",r.form.controls.confirmPassword.dirty&&r.form.controls.confirmPassword.invalid),m.zb(1),m.hc("disabled",!r.form.valid),m.zb(1),m.xc(" ",m.bc(20,16,"general.resetPassword")," ")}}var k,z,C,I=[{path:"",component:(k=function(){function o(e,r){n(this,o),this.store=e,this.fb=r,this.confirmPasswordValidationMessage="",this.isPasswordValid=!1}return e(o,[{key:"ionViewWillEnter",value:function(){var n=this;this.form=this.fb.group({password:["",[a.r.required]],confirmPassword:["",[a.r.required]]},{validator:Object(f.a)("password","confirmPassword")}),this.isLoading$=this.store.select(u.a.selectIsLoading);var o=this.form.get("confirmPassword");o.valueChanges.pipe(Object(c.a)(500)).subscribe((function(e){return n.setValidationMessage(o)}))}},{key:"setValidationMessage",value:function(n){this.confirmPasswordValidationMessage="",n.errors&&n.errors.mustMatch&&(this.confirmPasswordValidationMessage="general.passwordDoNotMatch")}},{key:"onPasswordRequirementsChange",value:function(n){this.isPasswordValid=n,this.form.controls.password.setErrors(this.isPasswordValid?null:{missingRequirements:!0})}},{key:"onFormSubmit",value:function(){var n=this;if(this.form.valid){var o=this.form.value;o.password&&o.confirmPassword&&this.store.select(d.a.selectQueryParam("token")).pipe(Object(b.a)(1)).subscribe((function(e){n.store.dispatch(l.a.resetPassword({password:o.password,token:{accessToken:e,refreshToken:null,idToken:null,expiresIn:null,exp:null,scope:null,tokenType:null,issuedAt:null}}))}))}}}]),o}(),k.\u0275fac=function(n){return new(n||k)(m.Kb(p.h),m.Kb(a.c))},k.\u0275cmp=m.Eb({type:k,selectors:[["reset-password"]],decls:10,vars:11,consts:[[3,"translucent"],[3,"fullscreen"],[4,"ngIf"],[1,"ion-text-center"],[3,"formGroup","ngSubmit"],["position","floating","color","secondary"],["formControlName","password","type","password"],[1,"ion-no-margin",3,"lines"],[1,"ion-margin-top",3,"password","containsSpecialCharacter","requirementsUpdated"],["formControlName","confirmPassword","type","password"],["class","ion-no-margin",3,"lines",4,"ngIf"],["expand","block","type","submit",1,"ion-margin-top","ion-margin-horizontal",3,"disabled"],["color","danger"]],template:function(n,o){1&n&&(m.Pb(0,"ion-header",0),m.Pb(1,"ion-toolbar"),m.Pb(2,"ion-title"),m.vc(3),m.ac(4,"translate"),m.Ob(),m.Ob(),m.Ob(),m.Pb(5,"ion-content",1),m.tc(6,v,4,0,"ion-row",2),m.ac(7,"async"),m.tc(8,O,21,18,"ion-row",2),m.ac(9,"async"),m.Ob()),2&n&&(m.hc("translucent",!0),m.zb(3),m.xc(" ",m.bc(4,5,"general.resetPassword")," "),m.zb(2),m.hc("fullscreen",!0),m.zb(1),m.hc("ngIf",!o.form||m.bc(7,7,o.isLoading$)),m.zb(2),m.hc("ngIf",o.form&&!m.bc(9,9,o.isLoading$)))},directives:[w.x,w.gb,w.eb,w.r,h.o,w.P,w.q,w.X,a.s,a.n,a.g,w.H,w.D,w.G,w.C,w.rb,a.m,a.e,P.a,w.h],pipes:[g.c,h.b],styles:[""]}),k)}],q=((C=e((function o(){n(this,o)}))).\u0275mod=m.Ib({type:C}),C.\u0275inj=m.Hb({factory:function(n){return new(n||C)},imports:[[i.m.forChild(I)],i.m]}),C),V=((z=e((function o(){n(this,o)}))).\u0275mod=m.Ib({type:z}),z.\u0275inj=m.Hb({factory:function(n){return new(n||z)},imports:[[t.a,q]]}),z)}}])}();