(window.webpackJsonp=window.webpackJsonp||[]).push([[21],{M4Zn:function(n,e,t){"use strict";t.r(e),t.d(e,"WelcomeModule",(function(){return O}));var i=t("SVse"),o=t("PCNd"),c=t("iInd"),s=t("AytR"),l=t("8Y7J"),r=t("tqRt"),a=t("sZkV"),b=t("TSSN");const d=["slides"],g=function(n){return{active:n}};function u(n,e){if(1&n){const n=l.Qb();l.Pb(0,"li",15),l.Xb("click",(function(){l.oc(n);const t=e.$implicit;return l.Zb(),l.nc(2).slideTo(t)})),l.Ob()}if(2&n){const n=e.$implicit,t=l.Zb();l.hc("ngClass",l.lc(1,g,n==t.activeIndex))}}function p(n,e){1&n&&(l.Nb(0),l.vc(1),l.ac(2,"translate"),l.Lb(3,"ion-icon",16),l.Mb()),2&n&&(l.zb(1),l.xc(" ",l.bc(2,1,"general.continue")," "))}function m(n,e){1&n&&(l.Nb(0),l.vc(1),l.ac(2,"translate"),l.Mb()),2&n&&(l.zb(1),l.xc(" ",l.bc(2,1,"general.skip")," "))}const h=[{path:"",component:(()=>{class n{constructor(n,e){this.router=n,this.store=e,this.activeIndex=0,this.numOfSlides=4,this.showContinue=!1,this.slideIndexArray=[]}ngOnInit(){}ngAfterViewInit(){this.slides.length().then(n=>{this.numOfSlides=n,this.slideIndexArray=Array(this.numOfSlides).fill(0).map((n,e)=>e)})}onSlideChange(n){this.slides.getActiveIndex().then(n=>{this.activeIndex=n,this.showContinue=this.showContinue||this.activeIndex==this.numOfSlides-1})}onSlideButtonClick(){this.router.navigateByUrl(s.a.moduleConfigs.authModule.afterLoginSuccessRedirect)}}return n.\u0275fac=function(e){return new(e||n)(l.Kb(c.j),l.Kb(r.h))},n.\u0275cmp=l.Eb({type:n,selectors:[["welcome"]],viewQuery:function(n,e){var t;1&n&&l.sc(d,!0),2&n&&l.mc(t=l.Yb())&&(e.slides=t.first)},decls:42,vars:27,consts:[["fullscreen","","scroll-y","false",1,"ion-padding"],[3,"ionSlideDidChange"],["slides",""],[1,"slide"],["src","./../../../../assets/img/welcome-1.png"],[1,"text-center"],[1,"text-center","text-muted"],["src","./../../../../assets/img/welcome-2.png"],["src","./../../../../assets/img/welcome-3.png"],["src","./../../../../assets/img/welcome-4.png"],[1,"slide-footer"],[1,"carousel-indicators","position-relative"],[3,"ngClass","click",4,"ngFor","ngForOf"],["fill","clear",3,"click"],[4,"ngIf"],[3,"ngClass","click"],["slot","end","name","arrow-forward"]],template:function(n,e){1&n&&(l.Pb(0,"ion-content",0),l.Pb(1,"ion-slides",1,2),l.Xb("ionSlideDidChange",(function(n){return e.onSlideChange(n)})),l.Pb(3,"ion-slide"),l.Pb(4,"div",3),l.Lb(5,"img",4),l.Pb(6,"h2",5),l.vc(7),l.ac(8,"translate"),l.Ob(),l.Pb(9,"p",6),l.vc(10),l.ac(11,"translate"),l.Ob(),l.Ob(),l.Ob(),l.Pb(12,"ion-slide"),l.Lb(13,"img",7),l.Pb(14,"h2",5),l.vc(15),l.ac(16,"translate"),l.Ob(),l.Pb(17,"p",6),l.vc(18),l.ac(19,"translate"),l.Ob(),l.Ob(),l.Pb(20,"ion-slide"),l.Lb(21,"img",8),l.Pb(22,"h2",5),l.vc(23),l.ac(24,"translate"),l.Ob(),l.Pb(25,"p",6),l.vc(26),l.ac(27,"translate"),l.Ob(),l.Ob(),l.Pb(28,"ion-slide"),l.Lb(29,"img",9),l.Pb(30,"h2",5),l.vc(31),l.ac(32,"translate"),l.Ob(),l.Pb(33,"p",6),l.vc(34),l.ac(35,"translate"),l.Ob(),l.Ob(),l.Ob(),l.Pb(36,"div",10),l.Pb(37,"ol",11),l.tc(38,u,1,3,"li",12),l.Ob(),l.Pb(39,"ion-button",13),l.Xb("click",(function(){return e.onSlideButtonClick()})),l.tc(40,p,4,3,"ng-container",14),l.tc(41,m,3,3,"ng-container",14),l.Ob(),l.Ob(),l.Ob()),2&n&&(l.zb(7),l.xc(" ",l.bc(8,11,"welcomeModule.activitiesTitle")," "),l.zb(3),l.xc(" ",l.bc(11,13,"welcomeModule.activitiesDescription")," "),l.zb(5),l.wc(l.bc(16,15,"welcomeModule.bookTitle")),l.zb(3),l.xc(" ",l.bc(19,17,"welcomeModule.bookDescription")," "),l.zb(5),l.wc(l.bc(24,19,"welcomeModule.buyTitle")),l.zb(3),l.xc(" ",l.bc(27,21,"welcomeModule.buyDescription")," "),l.zb(5),l.xc(" ",l.bc(32,23,"welcomeModule.upcomingTitle")," "),l.zb(3),l.xc(" ",l.bc(35,25,"welcomeModule.upcomingDescription")," "),l.zb(4),l.hc("ngForOf",e.slideIndexArray),l.zb(2),l.hc("ngIf",e.showContinue),l.zb(1),l.hc("ngIf",!e.showContinue))},directives:[a.r,a.W,a.V,i.n,a.h,i.o,i.m,a.y],pipes:[b.c],styles:["ion-slides[_ngcontent-%COMP%]{height:100%}.swiper-slide[_ngcontent-%COMP%]{display:block}.swiper-slide[_ngcontent-%COMP%]   h2[_ngcontent-%COMP%]{margin-top:2.8rem}.swiper-slide[_ngcontent-%COMP%]   img[_ngcontent-%COMP%]{max-height:50%;height:35%;max-width:80%;margin:60px 0 40px;pointer-events:none}b[_ngcontent-%COMP%]{font-weight:500}p[_ngcontent-%COMP%]{padding:0 40px;font-size:14px;line-height:1.5;color:var(--ion-color-step-600,#60646b)}p[_ngcontent-%COMP%]   b[_ngcontent-%COMP%]{color:var(--ion-text-color,#000)}.slide-footer[_ngcontent-%COMP%]{position:absolute;display:flex;flex-direction:column;align-items:center;bottom:5%;left:50%;transform:translateX(-50%);z-index:10}.carousel-indicators[_ngcontent-%COMP%]{list-style:none;padding:0}.carousel-indicators[_ngcontent-%COMP%]   li[_ngcontent-%COMP%]{background-color:var(--ion-color-step-800,#ccc);display:inline-block;width:20px;height:10px;border-radius:4px;border-top:0;border-bottom:0;margin-right:6px;margin-left:6px}.carousel-indicators[_ngcontent-%COMP%]   li.active[_ngcontent-%COMP%]{background-color:var(--ion-color-primary,#007aff)}"]}),n})()}];let f=(()=>{class n{}return n.\u0275mod=l.Ib({type:n}),n.\u0275inj=l.Hb({factory:function(e){return new(e||n)},imports:[[c.m.forChild(h)],c.m]}),n})(),O=(()=>{class n{}return n.\u0275mod=l.Ib({type:n}),n.\u0275inj=l.Hb({factory:function(e){return new(e||n)},imports:[[i.c,o.a,f]]}),n})()}}]);