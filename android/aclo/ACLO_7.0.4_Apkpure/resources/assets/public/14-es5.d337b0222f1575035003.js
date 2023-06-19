!function(){function e(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}function t(e,t){for(var n=0;n<t.length;n++){var o=t[n];o.enumerable=o.enumerable||!1,o.configurable=!0,"value"in o&&(o.writable=!0),Object.defineProperty(e,o.key,o)}}function n(e,n,o){return n&&t(e.prototype,n),o&&t(e,o),Object.defineProperty(e,"prototype",{writable:!1}),e}(window.webpackJsonp=window.webpackJsonp||[]).push([[14],{eUK3:function(t,o,i){"use strict";i.r(o),i.d(o,"ForgotPasswordPageModule",(function(){return k}));var a=i("PCNd"),r=i("iInd"),s=i("s7LF"),c=i("IzEk"),b=i("sIb/"),l=i("C/rb"),u=i("E4px"),f=i("8Y7J"),d=i("tqRt"),m=i("sZkV"),p=i("SVse"),h=i("TSSN");function g(e,t){1&e&&(f.Pb(0,"ion-row"),f.Pb(1,"ion-col"),f.Pb(2,"div",5),f.Lb(3,"ion-spinner"),f.Ob(),f.Ob(),f.Ob())}function P(e,t){1&e&&(f.Pb(0,"ion-item",14),f.Pb(1,"ion-label",15),f.vc(2),f.ac(3,"translate"),f.ac(4,"translate"),f.Ob(),f.Ob()),2&e&&(f.zb(2),f.yc(" ",f.bc(3,2,"general.email")," ",f.bc(4,4,"general.isInvalid")," "))}function w(e,t){if(1&e){var n=f.Qb();f.Pb(0,"ion-row"),f.Pb(1,"ion-col",6),f.Pb(2,"ion-text",7),f.vc(3),f.ac(4,"translate"),f.Ob(),f.Ob(),f.Pb(5,"ion-col",8),f.Pb(6,"form",9),f.Xb("ngSubmit",(function(){return f.oc(n),f.Zb().onFormSubmit()})),f.Pb(7,"ion-list"),f.Pb(8,"ion-item"),f.Pb(9,"ion-label",10),f.vc(10),f.ac(11,"translate"),f.Ob(),f.Lb(12,"ion-input",11),f.Ob(),f.tc(13,P,5,6,"ion-item",12),f.Ob(),f.Pb(14,"ion-button",13),f.vc(15),f.ac(16,"translate"),f.Ob(),f.Ob(),f.Ob(),f.Ob()}if(2&e){var o=f.Zb();f.zb(3),f.xc(" ",f.bc(4,5,"general.enterEmailPasswordReset")," "),f.zb(3),f.hc("formGroup",o.form),f.zb(4),f.xc(" ",f.bc(11,7,"general.email")," "),f.zb(3),f.hc("ngIf",o.form.controls.email.dirty&&o.form.controls.email.invalid),f.zb(2),f.xc(" ",f.bc(16,9,"general.resetPassword")," ")}}function O(e,t){1&e&&(f.Pb(0,"ion-col"),f.Pb(1,"h4",16),f.vc(2),f.ac(3,"translate"),f.Ob(),f.Pb(4,"p",16),f.vc(5),f.ac(6,"translate"),f.Ob(),f.Ob()),2&e&&(f.zb(2),f.xc(" ",f.bc(3,2,"general.passwordResetSentSuccess")," "),f.zb(3),f.xc(" ",f.bc(6,4,"general.passwordResetSentSuccessSub")," "))}function v(e,t){1&e&&(f.Pb(0,"ion-col"),f.Pb(1,"h4",16),f.vc(2),f.ac(3,"translate"),f.Ob(),f.Pb(4,"p",16),f.vc(5),f.ac(6,"translate"),f.Ob(),f.Ob()),2&e&&(f.zb(2),f.xc(" ",f.bc(3,2,"general.passwordResetSentFailed")," "),f.zb(3),f.xc(" ",f.bc(6,4,"general.passwordResetSentFailedSub")," "))}function y(e,t){if(1&e&&(f.Pb(0,"ion-row"),f.tc(1,O,7,6,"ion-col",4),f.ac(2,"async"),f.tc(3,v,7,6,"ion-col",4),f.ac(4,"async"),f.Ob()),2&e){var n=f.Zb();f.zb(1),f.hc("ngIf",f.bc(2,2,n.pwResetResult$)),f.zb(2),f.hc("ngIf",!f.bc(4,4,n.pwResetResult$))}}var z,S,R,x=[{path:"",component:(z=function(){function t(n,o){e(this,t),this.store=n,this.fb=o}return n(t,[{key:"ionViewDidEnter",value:function(){var e=this;this.isLoading$=this.store.select(l.a.selectIsLoading),this.isPWResetSent$=this.store.select(l.a.selectSentPasswordResetEmail),this.pwResetResult$=this.store.select(l.a.selectPWResetResult),this.form=this.fb.group({email:["",{validators:[s.r.required,Object(u.a)()],updateOn:"blur"}]}),this.form.get("email").valueChanges.pipe(Object(c.a)(1)).subscribe((function(t){e.form.setControl("email",new s.d(t,{validators:[s.r.required,Object(u.a)()],updateOn:"change"}))})),this.store.dispatch(b.a.resetSentPWFlag())}},{key:"onFormSubmit",value:function(){if(!this.form.valid)return this.form.controls.email.touched||this.form.setControl("email",new s.d(this.form.value.email,{validators:[s.r.required,Object(u.a)()],updateOn:"change"})),void this.form.controls.email.markAsDirty();var e=this.form.value;e.email&&this.store.dispatch(b.a.sendPasswordResetEmail({email:e.email.toLowerCase(),resetPath:"pages/reset"}))}}]),t}(),z.\u0275fac=function(e){return new(e||z)(f.Kb(d.h),f.Kb(s.c))},z.\u0275cmp=f.Eb({type:z,selectors:[["forgot-password"]],decls:15,vars:17,consts:[[3,"translucent"],["slot","start"],["defaultHref","/landing",3,"text"],[3,"fullscreen"],[4,"ngIf"],[1,"ion-text-center"],["size","12",1,"ion-padding-horizontal","ion-padding-top"],["color","secondary"],["size","12"],[3,"formGroup","ngSubmit"],["position","floating","color","secondary"],["formControlName","email","type","email"],["class","ion-no-margin",4,"ngIf"],["expand","block","type","submit",1,"ion-margin-top","ion-margin-horizontal"],[1,"ion-no-margin"],["color","danger"],[1,"ion-padding-start"]],template:function(e,t){1&e&&(f.Pb(0,"ion-header",0),f.Pb(1,"ion-toolbar"),f.Pb(2,"ion-buttons",1),f.Lb(3,"ion-back-button",2),f.Ob(),f.Pb(4,"ion-title"),f.vc(5),f.ac(6,"translate"),f.Ob(),f.Ob(),f.Ob(),f.Pb(7,"ion-content",3),f.tc(8,g,4,0,"ion-row",4),f.ac(9,"async"),f.tc(10,w,17,11,"ion-row",4),f.ac(11,"async"),f.ac(12,"async"),f.tc(13,y,5,6,"ion-row",4),f.ac(14,"async"),f.Ob()),2&e&&(f.hc("translucent",!0),f.zb(3),f.hc("text",""),f.zb(2),f.xc(" ",f.bc(6,7,"general.forgotPassword")," "),f.zb(2),f.hc("fullscreen",!0),f.zb(1),f.hc("ngIf",!t.form||f.bc(9,9,t.isLoading$)),f.zb(2),f.hc("ngIf",t.form&&!f.bc(11,11,t.isPWResetSent$)&&!f.bc(12,13,t.isLoading$)),f.zb(3),f.hc("ngIf",f.bc(14,15,t.isPWResetSent$)))},directives:[m.x,m.gb,m.i,m.e,m.f,m.eb,m.r,p.o,m.P,m.q,m.X,m.bb,s.s,s.n,s.g,m.H,m.D,m.G,m.C,m.rb,s.m,s.e,m.h],pipes:[h.c,p.b],styles:[""]}),z)}],I=((R=n((function t(){e(this,t)}))).\u0275mod=f.Ib({type:R}),R.\u0275inj=f.Hb({factory:function(e){return new(e||R)},imports:[[r.m.forChild(x)],r.m]}),R),k=((S=n((function t(){e(this,t)}))).\u0275mod=f.Ib({type:S}),S.\u0275inj=f.Hb({factory:function(e){return new(e||S)},imports:[[a.a,I]]}),S)}}])}();