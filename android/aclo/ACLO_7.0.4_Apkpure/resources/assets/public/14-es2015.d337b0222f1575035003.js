(window.webpackJsonp=window.webpackJsonp||[]).push([[14],{eUK3:function(e,t,n){"use strict";n.r(t),n.d(t,"ForgotPasswordPageModule",(function(){return S}));var o=n("PCNd"),s=n("iInd"),i=n("s7LF"),a=n("IzEk"),r=n("sIb/"),c=n("C/rb"),b=n("E4px"),l=n("8Y7J"),d=n("tqRt"),m=n("sZkV"),u=n("SVse"),f=n("TSSN");function p(e,t){1&e&&(l.Pb(0,"ion-row"),l.Pb(1,"ion-col"),l.Pb(2,"div",5),l.Lb(3,"ion-spinner"),l.Ob(),l.Ob(),l.Ob())}function g(e,t){1&e&&(l.Pb(0,"ion-item",14),l.Pb(1,"ion-label",15),l.vc(2),l.ac(3,"translate"),l.ac(4,"translate"),l.Ob(),l.Ob()),2&e&&(l.zb(2),l.yc(" ",l.bc(3,2,"general.email")," ",l.bc(4,4,"general.isInvalid")," "))}function h(e,t){if(1&e){const e=l.Qb();l.Pb(0,"ion-row"),l.Pb(1,"ion-col",6),l.Pb(2,"ion-text",7),l.vc(3),l.ac(4,"translate"),l.Ob(),l.Ob(),l.Pb(5,"ion-col",8),l.Pb(6,"form",9),l.Xb("ngSubmit",(function(){return l.oc(e),l.Zb().onFormSubmit()})),l.Pb(7,"ion-list"),l.Pb(8,"ion-item"),l.Pb(9,"ion-label",10),l.vc(10),l.ac(11,"translate"),l.Ob(),l.Lb(12,"ion-input",11),l.Ob(),l.tc(13,g,5,6,"ion-item",12),l.Ob(),l.Pb(14,"ion-button",13),l.vc(15),l.ac(16,"translate"),l.Ob(),l.Ob(),l.Ob(),l.Ob()}if(2&e){const e=l.Zb();l.zb(3),l.xc(" ",l.bc(4,5,"general.enterEmailPasswordReset")," "),l.zb(3),l.hc("formGroup",e.form),l.zb(4),l.xc(" ",l.bc(11,7,"general.email")," "),l.zb(3),l.hc("ngIf",e.form.controls.email.dirty&&e.form.controls.email.invalid),l.zb(2),l.xc(" ",l.bc(16,9,"general.resetPassword")," ")}}function P(e,t){1&e&&(l.Pb(0,"ion-col"),l.Pb(1,"h4",16),l.vc(2),l.ac(3,"translate"),l.Ob(),l.Pb(4,"p",16),l.vc(5),l.ac(6,"translate"),l.Ob(),l.Ob()),2&e&&(l.zb(2),l.xc(" ",l.bc(3,2,"general.passwordResetSentSuccess")," "),l.zb(3),l.xc(" ",l.bc(6,4,"general.passwordResetSentSuccessSub")," "))}function w(e,t){1&e&&(l.Pb(0,"ion-col"),l.Pb(1,"h4",16),l.vc(2),l.ac(3,"translate"),l.Ob(),l.Pb(4,"p",16),l.vc(5),l.ac(6,"translate"),l.Ob(),l.Ob()),2&e&&(l.zb(2),l.xc(" ",l.bc(3,2,"general.passwordResetSentFailed")," "),l.zb(3),l.xc(" ",l.bc(6,4,"general.passwordResetSentFailedSub")," "))}function O(e,t){if(1&e&&(l.Pb(0,"ion-row"),l.tc(1,P,7,6,"ion-col",4),l.ac(2,"async"),l.tc(3,w,7,6,"ion-col",4),l.ac(4,"async"),l.Ob()),2&e){const e=l.Zb();l.zb(1),l.hc("ngIf",l.bc(2,2,e.pwResetResult$)),l.zb(2),l.hc("ngIf",!l.bc(4,4,e.pwResetResult$))}}const v=[{path:"",component:(()=>{class e{constructor(e,t){this.store=e,this.fb=t}ionViewDidEnter(){this.isLoading$=this.store.select(c.a.selectIsLoading),this.isPWResetSent$=this.store.select(c.a.selectSentPasswordResetEmail),this.pwResetResult$=this.store.select(c.a.selectPWResetResult),this.form=this.fb.group({email:["",{validators:[i.r.required,Object(b.a)()],updateOn:"blur"}]}),this.form.get("email").valueChanges.pipe(Object(a.a)(1)).subscribe(e=>{this.form.setControl("email",new i.d(e,{validators:[i.r.required,Object(b.a)()],updateOn:"change"}))}),this.store.dispatch(r.a.resetSentPWFlag())}onFormSubmit(){if(!this.form.valid)return this.form.controls.email.touched||this.form.setControl("email",new i.d(this.form.value.email,{validators:[i.r.required,Object(b.a)()],updateOn:"change"})),void this.form.controls.email.markAsDirty();const e=this.form.value;e.email&&this.store.dispatch(r.a.sendPasswordResetEmail({email:e.email.toLowerCase(),resetPath:"pages/reset"}))}}return e.\u0275fac=function(t){return new(t||e)(l.Kb(d.h),l.Kb(i.c))},e.\u0275cmp=l.Eb({type:e,selectors:[["forgot-password"]],decls:15,vars:17,consts:[[3,"translucent"],["slot","start"],["defaultHref","/landing",3,"text"],[3,"fullscreen"],[4,"ngIf"],[1,"ion-text-center"],["size","12",1,"ion-padding-horizontal","ion-padding-top"],["color","secondary"],["size","12"],[3,"formGroup","ngSubmit"],["position","floating","color","secondary"],["formControlName","email","type","email"],["class","ion-no-margin",4,"ngIf"],["expand","block","type","submit",1,"ion-margin-top","ion-margin-horizontal"],[1,"ion-no-margin"],["color","danger"],[1,"ion-padding-start"]],template:function(e,t){1&e&&(l.Pb(0,"ion-header",0),l.Pb(1,"ion-toolbar"),l.Pb(2,"ion-buttons",1),l.Lb(3,"ion-back-button",2),l.Ob(),l.Pb(4,"ion-title"),l.vc(5),l.ac(6,"translate"),l.Ob(),l.Ob(),l.Ob(),l.Pb(7,"ion-content",3),l.tc(8,p,4,0,"ion-row",4),l.ac(9,"async"),l.tc(10,h,17,11,"ion-row",4),l.ac(11,"async"),l.ac(12,"async"),l.tc(13,O,5,6,"ion-row",4),l.ac(14,"async"),l.Ob()),2&e&&(l.hc("translucent",!0),l.zb(3),l.hc("text",""),l.zb(2),l.xc(" ",l.bc(6,7,"general.forgotPassword")," "),l.zb(2),l.hc("fullscreen",!0),l.zb(1),l.hc("ngIf",!t.form||l.bc(9,9,t.isLoading$)),l.zb(2),l.hc("ngIf",t.form&&!l.bc(11,11,t.isPWResetSent$)&&!l.bc(12,13,t.isLoading$)),l.zb(3),l.hc("ngIf",l.bc(14,15,t.isPWResetSent$)))},directives:[m.x,m.gb,m.i,m.e,m.f,m.eb,m.r,u.o,m.P,m.q,m.X,m.bb,i.s,i.n,i.g,m.H,m.D,m.G,m.C,m.rb,i.m,i.e,m.h],pipes:[f.c,u.b],styles:[""]}),e})()}];let z=(()=>{class e{}return e.\u0275mod=l.Ib({type:e}),e.\u0275inj=l.Hb({factory:function(t){return new(t||e)},imports:[[s.m.forChild(v)],s.m]}),e})(),S=(()=>{class e{}return e.\u0275mod=l.Ib({type:e}),e.\u0275inj=l.Hb({factory:function(t){return new(t||e)},imports:[[o.a,z]]}),e})()}}]);