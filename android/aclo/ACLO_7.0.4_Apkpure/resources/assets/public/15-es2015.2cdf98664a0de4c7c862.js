(window.webpackJsonp=window.webpackJsonp||[]).push([[15],{Zuv8:function(n,e,t){"use strict";t.r(e),t.d(e,"HomePageModule",(function(){return U}));var o=t("PCNd"),i=t("gxPF"),l=t("iInd"),c=t("gbsI"),a=t("H5dK"),s=t("y32E"),b=t("mrSG"),u=t("3bzS"),r=t("itXk"),d=t("lJxs"),h=t("IzEk"),f=t("C/rb"),g=t("AytR"),m=t("gVQ9"),p=t("XU5k"),M=t("0+2f"),v=t("41Qi"),C=t("Nu82"),P=t("wr8B"),I=t("r0RS"),y=t("8Y7J"),O=t("tqRt"),k=t("sZkV"),z=t("SVse"),S=t("TSSN");function A(n,e){1&n&&(y.Pb(0,"ion-tab-button",10),y.Lb(1,"ion-icon",11),y.Pb(2,"ion-label"),y.vc(3),y.ac(4,"translate"),y.Ob(),y.Ob()),2&n&&(y.zb(3),y.xc(" ",y.bc(4,1,"bookingModule.upcoming"),""))}function N(n,e){1&n&&(y.Pb(0,"ion-tab-button",12),y.Lb(1,"ion-icon",13),y.Pb(2,"ion-label"),y.vc(3),y.ac(4,"translate"),y.Ob(),y.Ob()),2&n&&(y.zb(3),y.xc(" ",y.bc(4,1,"bookingModule.schedule"),""))}function w(n,e){1&n&&(y.Pb(0,"ion-tab-button",14),y.Lb(1,"ion-icon",15),y.Pb(2,"ion-label"),y.vc(3),y.ac(4,"translate"),y.Ob(),y.Ob()),2&n&&(y.zb(3),y.xc(" ",y.bc(4,1,"scanModule.scan"),""))}function L(n,e){1&n&&(y.Pb(0,"ion-tab-button",16),y.Lb(1,"ion-icon",17),y.Pb(2,"ion-label"),y.vc(3),y.ac(4,"translate"),y.Ob(),y.Ob()),2&n&&(y.zb(3),y.xc(" ",y.bc(4,1,"nfcModule.nfc"),""))}function x(n,e){1&n&&(y.Pb(0,"ion-tab-button",18),y.Lb(1,"ion-icon",19),y.Pb(2,"ion-label"),y.vc(3),y.ac(4,"translate"),y.Ob(),y.Ob()),2&n&&(y.zb(3),y.xc(" ",y.bc(4,1,"general.shop"),""))}function E(n,e){1&n&&(y.Pb(0,"ion-fab",22),y.Pb(1,"ion-fab-button",23),y.Lb(2,"ion-icon",24),y.Ob(),y.Ob())}function R(n,e){if(1&n){const n=y.Qb();y.Pb(0,"ion-fab",25),y.Xb("click",(function(){return y.oc(n),y.Zb(2).onPendingSaleClick()})),y.Pb(1,"ion-fab-button",23),y.Lb(2,"ion-icon",26),y.Ob(),y.Ob()}}function j(n,e){if(1&n&&(y.Pb(0,"div"),y.tc(1,E,3,0,"ion-fab",20),y.ac(2,"async"),y.ac(3,"async"),y.tc(4,R,3,0,"ion-fab",21),y.ac(5,"async"),y.Ob()),2&n){const n=y.Zb();y.zb(1),y.hc("ngIf",y.bc(2,2,n.cartItemCount$)>0&&!y.bc(3,4,n.pendingSale$)),y.zb(3),y.hc("ngIf",y.bc(5,6,n.pendingSale$))}}const $=[{path:"",component:(()=>{let n=class{constructor(n,e,t){this.store=n,this.router=e,this.platform=t,this.isPendingSale=!1,this.showNfcInMenu=!1}ionViewWillEnter(){this.cartItemCount$=this.store.select(m.a.selectCount),this.isLoading$=this.store.select(v.a.selectIsLoading),this.pendingSale$=Object(r.a)([this.store.select(f.a.selectMember),this.store.select(I.a.selectAll),this.store.select(p.b.selectByKey,"disabled_payment_methods"),this.store.select(C.a.selectEntities)]).pipe(Object(d.a)(([n,e,t,o])=>{const i=e.filter(e=>e.customerPersonId==n.id);if(!i||!(null==i?void 0:i.length))return null;let l=[];return t&&(l=JSON.parse(null==t?void 0:t.value)),i.find(n=>{var e;return!(![P.b.PENDING,P.b.PENDING_REQUIRED].includes(n.statusCode)||(null===(e=null==n?void 0:n.payments)||void 0===e?void 0:e.length)&&!n.payments.find(n=>!(null==l?void 0:l.includes(n.payMethod))))})}))}ngOnInit(){this.upcomingModuleConfig=g.a.moduleConfigs.upcomingModule,this.scheduleModuleConfig=g.a.moduleConfigs.bookingModule,this.scanAccessModuleConfig=g.a.moduleConfigs.scanAccessModule,this.shopModuleConfig=g.a.moduleConfigs.shopModule,this.paymentModuleConfig=g.a.moduleConfigs.paymentModule,this.nfcModuleConfig=g.a.moduleConfigs.nfcModule,this.store.select(v.a.selectUserHasRoles,[M.a.ADMIN,M.a.SUPERVISOR]).pipe(Object(u.b)(this)).subscribe(n=>{this.showNfcInMenu=n&&!this.platform.is("ios")})}onPendingSaleClick(){this.pendingSale$.pipe(Object(h.a)(1)).subscribe(n=>{n&&this.router.navigateByUrl(`/sales/${n.id}/checkout`)})}};return n.\u0275fac=function(e){return new(e||n)(y.Kb(O.h),y.Kb(l.j),y.Kb(k.nb))},n.\u0275cmp=y.Eb({type:n,selectors:[["app-home"]],decls:14,vars:12,consts:[[3,"hidden"],["slot","bottom"],["tab","myhome",4,"ngIf"],["tab","schedule",4,"ngIf"],["tab","scan",4,"ngIf"],["tab","nfc",4,"ngIf"],["tab","shop",4,"ngIf"],["tab","profile"],["name","person-circle-outline"],[4,"ngIf"],["tab","myhome"],["name","home-outline"],["tab","schedule"],["name","calendar-outline"],["tab","scan"],["name","qr-code-outline"],["tab","nfc"],["name","card-outline"],["tab","shop"],["name","basket-outline"],["vertical","bottom","horizontal","end","slot","fixed","routerLink","/cart","style","margin-bottom: 4rem",4,"ngIf"],["vertical","bottom","horizontal","end","slot","fixed","style","margin-bottom: 4rem",3,"click",4,"ngIf"],["vertical","bottom","horizontal","end","slot","fixed","routerLink","/cart",2,"margin-bottom","4rem"],["color","primary"],["name","cart-outline"],["vertical","bottom","horizontal","end","slot","fixed",2,"margin-bottom","4rem",3,"click"],["name","logo-euro"]],template:function(n,e){1&n&&(y.Pb(0,"ion-tabs",0),y.ac(1,"async"),y.Pb(2,"ion-tab-bar",1),y.tc(3,A,5,3,"ion-tab-button",2),y.tc(4,N,5,3,"ion-tab-button",3),y.tc(5,w,5,3,"ion-tab-button",4),y.tc(6,L,5,3,"ion-tab-button",5),y.tc(7,x,5,3,"ion-tab-button",6),y.Pb(8,"ion-tab-button",7),y.Lb(9,"ion-icon",8),y.Pb(10,"ion-label"),y.vc(11),y.ac(12,"translate"),y.Ob(),y.Ob(),y.Ob(),y.Ob(),y.tc(13,j,6,8,"div",9)),2&n&&(y.hc("hidden",y.bc(1,8,e.isLoading$)),y.zb(3),y.hc("ngIf",e.upcomingModuleConfig&&e.upcomingModuleConfig.isActive),y.zb(1),y.hc("ngIf",e.scheduleModuleConfig&&e.scheduleModuleConfig.isActive),y.zb(1),y.hc("ngIf",null==e.scanAccessModuleConfig?null:e.scanAccessModuleConfig.isActive),y.zb(1),y.hc("ngIf",(null==e.nfcModuleConfig?null:e.nfcModuleConfig.isActive)&&e.showNfcInMenu),y.zb(1),y.hc("ngIf",null==e.shopModuleConfig?null:e.shopModuleConfig.isActive),y.zb(4),y.xc(" ",y.bc(12,10,"general.profile"),""),y.zb(2),y.hc("ngIf",null==e.paymentModuleConfig?null:e.paymentModuleConfig.isActive))},directives:[k.ab,k.Y,z.o,k.Z,k.y,k.G,k.t,k.pb,l.k,k.u],pipes:[z.b,S.c],styles:[""]}),n=Object(b.b)([Object(u.a)()],n),n})(),children:[{path:"scan",canActivate:[c.a],loadChildren:()=>t.e(16).then(t.bind(null,"C1Hr")).then(n=>n.ScanPageModule)},{path:"myhome",canActivate:[c.a,a.a],loadChildren:()=>Promise.resolve().then(t.bind(null,"Td53")).then(n=>n.BookingModule)},{path:"profile",loadChildren:()=>Promise.resolve().then(t.bind(null,"El0N")).then(n=>n.MemberModule)},{path:"schedule",canActivate:[c.a,a.a],loadChildren:()=>t.e(17).then(t.bind(null,"RLmF")).then(n=>n.SchedulePageModule)},{path:"shop",canActivate:[c.a,s.a],loadChildren:()=>Promise.resolve().then(t.bind(null,"QbNU")).then(n=>n.ShopModule)},{path:"nfc",canActivate:[c.a,a.a],loadChildren:()=>Promise.resolve().then(t.bind(null,"Ruak")).then(n=>n.NfcModule)},{path:"",redirectTo:"/home/profile",pathMatch:"full"}]}];let H=(()=>{class n{}return n.\u0275mod=y.Ib({type:n}),n.\u0275inj=y.Hb({factory:function(e){return new(e||n)},imports:[[l.m.forChild($)],l.m]}),n})(),U=(()=>{class n{}return n.\u0275mod=y.Ib({type:n}),n.\u0275inj=y.Hb({factory:function(e){return new(e||n)},imports:[[o.a,H,i.TagModule]]}),n})()}}]);