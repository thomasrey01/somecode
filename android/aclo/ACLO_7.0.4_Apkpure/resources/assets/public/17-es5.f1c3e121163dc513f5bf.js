!function(){function e(e,t,n){return t in e?Object.defineProperty(e,t,{value:n,enumerable:!0,configurable:!0,writable:!0}):e[t]=n,e}function t(e){return function(e){if(Array.isArray(e))return o(e)}(e)||function(e){if("undefined"!=typeof Symbol&&null!=e[Symbol.iterator]||null!=e["@@iterator"])return Array.from(e)}(e)||i(e)||function(){throw new TypeError("Invalid attempt to spread non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function n(e,t){return function(e){if(Array.isArray(e))return e}(e)||function(e,t){var n=null==e?null:"undefined"!=typeof Symbol&&e[Symbol.iterator]||e["@@iterator"];if(null==n)return;var i,o,r=[],a=!0,c=!1;try{for(n=n.call(e);!(a=(i=n.next()).done)&&(r.push(i.value),!t||r.length!==t);a=!0);}catch(s){c=!0,o=s}finally{try{a||null==n.return||n.return()}finally{if(c)throw o}}return r}(e,t)||i(e,t)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function i(e,t){if(e){if("string"==typeof e)return o(e,t);var n=Object.prototype.toString.call(e).slice(8,-1);return"Object"===n&&e.constructor&&(n=e.constructor.name),"Map"===n||"Set"===n?Array.from(e):"Arguments"===n||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(n)?o(e,t):void 0}}function o(e,t){(null==t||t>e.length)&&(t=e.length);for(var n=0,i=new Array(t);n<t;n++)i[n]=e[n];return i}function r(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}function a(e,t){for(var n=0;n<t.length;n++){var i=t[n];i.enumerable=i.enumerable||!1,i.configurable=!0,"value"in i&&(i.writable=!0),Object.defineProperty(e,i.key,i)}}function c(e,t,n){return t&&a(e.prototype,t),n&&a(e,n),Object.defineProperty(e,"prototype",{writable:!1}),e}(window.webpackJsonp=window.webpackJsonp||[]).push([[17],{RLmF:function(i,o,a){"use strict";a.r(o),a.d(o,"SchedulePageModule",(function(){return st}));var s,l=a("PCNd"),d=a("ElmV"),u=a("gxPF"),b=a("iInd"),f=a("EcI+"),g=a("x0ql"),h=a("y32E"),v=a("+cd3"),p=a("mrSG"),y=a("sjEL"),O=a("2Vo4"),I=a("itXk"),m=a("LRne"),k=a("SxV6"),P=a("pLZG"),S=a("lJxs"),w=a("eIep"),E=a("zp1y"),z=a("/uUt"),T=a("vkgz"),B=a("JIr8"),$=a("tS1D"),D=a("IzEk"),M=a("C/rb"),C=a("EMFo"),j=a("s0Cq"),x=a("gG+F"),A=a("AytR"),L=a("p6Ya"),F=a("3bzS"),_=a("7bNT"),N=a("XU5k"),R=a("E8GO"),Z=a("pZS7"),V=a("j7EN"),K=a("Gd0g"),Y=a("/Mrg"),J=a("6c8H"),H=a("POb6"),U=a("bIeb"),X=a("2bFT"),q=a("byic"),G=a("4Kl7"),Q=a("X56j"),W=a("3kdJ"),ee=a("yg+Z"),te=a("iWn4"),ne=a("8Y7J"),ie=a("tqRt"),oe=a("UhO6"),re=a("w/Lx"),ae=a("bwYc"),ce=a("OWih"),se=a("AMBg"),le=a("sZkV"),de=a("SVse"),ue=a("BFul"),be=function(e){return e[e.VISIBLE=0]="VISIBLE",e[e.NO_RESERVATION=1]="NO_RESERVATION",e[e.HIDDEN=2]="HIDDEN",e}({}),fe=a("uxKI"),ge=a("B1zd"),he=a("9uVP"),ve=a("cOSJ"),pe=a("qIvP"),ye=a("q6GM"),Oe=a("TSSN"),Ie=((s=function(){function e(t,n){r(this,e),this.store=t,this.courseService=n}return c(e,[{key:"transform",value:function(e,t){var i=this;return Object(I.a)([this.store.select(M.a.selectMember),this.store.select(U.a.selectEntities),this.store.select(te.a.selectAll)]).pipe(Object(S.a)((function(o){var r=n(o,3),a=r[0],c=r[1];return r[2],i.courseService.getPriceForMember(e,c[e.productId],a,t)})))}}]),e}()).\u0275fac=function(e){return new(e||s)(ne.Kb(ie.h),ne.Kb(re.a))},s.\u0275pipe=ne.Jb({name:"coursePriceForMember",type:s,pure:!0}),s.\u0275prov=ne.Gb({token:s,factory:s.\u0275fac,providedIn:"root"}),s),me=a("DFrK"),ke=a("DVhg");function Pe(e,t){1&e&&ne.Lb(0,"ion-icon",25)}function Se(e,t){if(1&e&&(ne.Pb(0,"div"),ne.tc(1,Pe,1,0,"ion-icon",24),ne.ac(2,"async"),ne.ac(3,"coursePriceForMember"),ne.Ob()),2&e){var n=ne.Zb().$implicit,i=ne.Zb(2);ne.zb(1),ne.hc("ngIf",0!==ne.bc(2,1,ne.bc(3,3,i.courseEntities[n.courseId])))}}function we(e,t){1&e&&ne.Lb(0,"ion-icon",25)}function Ee(e,t){if(1&e&&(ne.Pb(0,"div"),ne.tc(1,we,1,0,"ion-icon",24),ne.ac(2,"async"),ne.ac(3,"productPriceForMember"),ne.Ob()),2&e){var n=ne.Zb().$implicit,i=ne.Zb(2);ne.zb(1),ne.hc("ngIf",0!==ne.bc(2,1,ne.cc(3,3,i.getTargetProduct(n),n)))}}function ze(e,t){1&e&&ne.Lb(0,"ion-icon",25)}function Te(e,t){if(1&e&&(ne.Pb(0,"div"),ne.tc(1,ze,1,0,"ion-icon",24),ne.ac(2,"async"),ne.ac(3,"productPriceForMember"),ne.Ob()),2&e){var n=ne.Zb().$implicit,i=ne.Zb(2);ne.zb(1),ne.hc("ngIf",0!==ne.bc(2,1,ne.cc(3,3,i.getTargetProduct(n),n)))}}function Be(e,t){if(1&e&&(ne.Pb(0,"span"),ne.vc(1),ne.ac(2,"async"),ne.ac(3,"getTranslation"),ne.Ob()),2&e){var n=ne.Zb().$implicit;ne.zb(1),ne.wc(ne.bc(2,1,ne.dc(3,3,n.courseId,"course","description")))}}function $e(e,t){if(1&e&&(ne.Pb(0,"span"),ne.vc(1),ne.ac(2,"async"),ne.ac(3,"getTranslation"),ne.Ob()),2&e){var n=ne.Zb().$implicit;ne.zb(1),ne.xc(" ",ne.bc(2,1,ne.dc(3,3,n.linkedProductId,"product","description"))," ")}}function De(e,t){if(1&e&&(ne.Pb(0,"span"),ne.vc(1),ne.ac(2,"async"),ne.ac(3,"getTranslation"),ne.Ob()),2&e){var n=ne.Zb().$implicit;ne.zb(1),ne.xc(" ",ne.bc(2,1,ne.dc(3,3,n.bookableProductId,"product","description"))," ")}}function Me(e,t){1&e&&(ne.Pb(0,"ion-col",26),ne.Pb(1,"ion-chip",27),ne.vc(2),ne.ac(3,"translate"),ne.Ob(),ne.Ob()),2&e&&(ne.zb(2),ne.xc(" ",ne.bc(3,1,"bookingModule.going")," "))}function Ce(e,t){if(1&e&&(ne.Pb(0,"ion-text",19),ne.vc(1),ne.ac(2,"async"),ne.ac(3,"getTranslation"),ne.Ob()),2&e){var n=ne.Zb().$implicit;ne.zb(1),ne.xc(" ",ne.bc(2,1,ne.dc(3,3,null==n?null:n.bookableProductId,"product","description"))," ")}}function je(e,t){if(1&e&&(ne.Pb(0,"ion-row",2),ne.Pb(1,"ion-col",18),ne.Pb(2,"ion-text",19),ne.vc(3),ne.Ob(),ne.Ob(),ne.Ob()),2&e){var n=ne.Zb().$implicit,i=ne.Zb(2);ne.zb(3),ne.xc(" ",null==i.bookingEntities[n.bookingId]?null:i.bookingEntities[n.bookingId].description," ")}}function xe(e,t){1&e&&(ne.Pb(0,"ion-row",2),ne.Pb(1,"ion-col",28),ne.Pb(2,"ion-badge",29),ne.vc(3),ne.ac(4,"translate"),ne.ac(5,"lowercase"),ne.ac(6,"translate"),ne.Ob(),ne.Ob(),ne.Ob()),2&e&&(ne.zb(3),ne.yc(" ",ne.bc(4,2,"general.enrollment")," ",ne.bc(5,4,ne.bc(6,6,"general.hasNotStartedYet"))," "))}function Ae(e,t){1&e&&(ne.Pb(0,"ion-col",30),ne.Pb(1,"ion-button"),ne.Lb(2,"ion-icon",31),ne.Ob(),ne.Ob())}function Le(e,t){1&e&&(ne.Pb(0,"div",32),ne.Pb(1,"ion-text",33),ne.vc(2),ne.ac(3,"translate"),ne.Ob(),ne.Ob()),2&e&&(ne.zb(2),ne.xc(" ",ne.bc(3,1,"courseModule.course")," "))}function Fe(e,t){1&e&&(ne.Pb(0,"div",34),ne.Pb(1,"ion-badge",35),ne.vc(2),ne.ac(3,"translate"),ne.Ob(),ne.Ob()),2&e&&(ne.zb(2),ne.xc(" ",ne.bc(3,1,"bookingModule.full")," "))}function _e(e,t){if(1&e){var n=ne.Qb();ne.Pb(0,"ion-item",4),ne.Xb("click",(function(){ne.oc(n);var e=t.$implicit;return ne.Zb(2).onSlotClick(e)})),ne.Pb(1,"ion-grid",5),ne.Pb(2,"ion-row",5),ne.Pb(3,"ion-col",6),ne.Pb(4,"div",7),ne.Pb(5,"div"),ne.Pb(6,"ion-text",8),ne.vc(7),ne.Ob(),ne.Lb(8,"br"),ne.Pb(9,"ion-text",9),ne.vc(10),ne.ac(11,"lowercase"),ne.ac(12,"translate"),ne.Ob(),ne.Ob(),ne.Ob(),ne.Ob(),ne.Pb(13,"ion-col",10),ne.tc(14,Se,4,5,"div",11),ne.tc(15,Ee,4,6,"div",11),ne.tc(16,Te,4,6,"div",11),ne.Ob(),ne.Pb(17,"ion-col",12),ne.Pb(18,"ion-row",2),ne.Pb(19,"ion-col",13),ne.ac(20,"async"),ne.Pb(21,"ion-label",14),ne.Pb(22,"h2",8),ne.tc(23,Be,4,7,"span",11),ne.tc(24,$e,4,7,"span",11),ne.tc(25,De,4,7,"span",11),ne.Ob(),ne.Ob(),ne.Ob(),ne.tc(26,Me,4,3,"ion-col",15),ne.ac(27,"async"),ne.Ob(),ne.Pb(28,"ion-row",2),ne.Pb(29,"ion-col",16),ne.ac(30,"async"),ne.tc(31,Ce,4,7,"ion-text",17),ne.Ob(),ne.Pb(32,"ion-col",18),ne.Pb(33,"ion-text",19),ne.vc(34),ne.ac(35,"async"),ne.Ob(),ne.Ob(),ne.Ob(),ne.tc(36,je,4,1,"ion-row",20),ne.tc(37,xe,7,8,"ion-row",20),ne.Ob(),ne.tc(38,Ae,3,0,"ion-col",21),ne.Ob(),ne.Ob(),ne.tc(39,Le,4,3,"div",22),ne.tc(40,Fe,4,3,"div",23),ne.ac(41,"async"),ne.Ob()}if(2&e){var i,o=t.$implicit,r=ne.Zb(2);ne.zb(7),ne.wc(r.getStartTime(o)),ne.zb(3),ne.yc(" ",r.bookingService.getDuration(o)," ",ne.bc(11,21,ne.bc(12,23,"general.min"))," "),ne.zb(4),ne.hc("ngIf",o.courseId),ne.zb(1),ne.hc("ngIf",!o.courseId&&o.linkedProductId),ne.zb(1),ne.hc("ngIf",!o.courseId&&!o.linkedProductId),ne.zb(1),ne.hc("size",r.isFreeToBook(o)?"7":"9"),ne.zb(2),ne.hc("size",ne.bc(20,25,r.getIsBookedByUser(o))?"7":"9"),ne.zb(4),ne.hc("ngIf",o.courseId),ne.zb(1),ne.hc("ngIf",!o.courseId&&o.linkedProductId),ne.zb(1),ne.hc("ngIf",!o.courseId&&!o.linkedProductId),ne.zb(1),ne.hc("ngIf",ne.bc(27,27,r.getIsBookedByUser(o))),ne.zb(3),ne.Bb(ne.bc(30,29,r.getIsBookedByUser(o))?"move-top":""),ne.zb(2),ne.hc("ngIf",!(null!=o.availableOnParentBookableProductIds&&o.availableOnParentBookableProductIds.length)||(null==o.availableOnParentBookableProductIds?null:o.availableOnParentBookableProductIds.length)<=1),ne.zb(3),ne.xc(" ",ne.bc(35,31,r.getNameOfSupervisor(o))," "),ne.zb(2),ne.hc("ngIf",null==r.bookingEntities[o.bookingId]?null:r.bookingEntities[o.bookingId].description),ne.zb(1),ne.hc("ngIf",r.courseEnrollmentHasStarted(o)),ne.zb(1),ne.hc("ngIf",r.isFreeToBook(o)),ne.zb(1),ne.hc("ngIf",o.courseId),ne.zb(1),ne.hc("ngIf",0==(null==(i=ne.bc(41,33,r.participationService.getParticipantCounts$(o)))?null:i.spotsLeft))}}function Ne(e,t){if(1&e&&(ne.Pb(0,"div",1),ne.Pb(1,"ion-card-content",2),ne.Pb(2,"ion-list",2),ne.tc(3,_e,42,35,"ion-item",3),ne.ac(4,"async"),ne.Ob(),ne.Ob(),ne.Ob()),2&e){var n=ne.Zb();ne.zb(3),ne.hc("ngForOf",ne.bc(4,1,n.bookableSlots$))}}var Re,Ze=((Re=function(){function e(t,n,i,o,a){r(this,e),this.store=t,this.productService=n,this.icalendarService=i,this.bookingService=o,this.participationService=a,this.onClickBookableSlot=new ne.n,this.BookingStatus=be,this.subscriptions=[]}return c(e,[{key:"ngOnInit",value:function(){var e=this;this.store.select(U.a.selectEntities).subscribe((function(t){return e.productEntities=t})),this.store.select(ee.a.selectEntities).subscribe((function(t){return e.bookingEntities=t})),this.store.select(fe.a.selectAll).subscribe((function(t){return e.participations=t})),this.store.select(V.a.selectEntities).subscribe((function(t){return e.locationEntities=t})),this.store.select(R.a.selectEntities).subscribe((function(t){return e.courseEntities=t})),this.teacherSupervisorDisplayNameOption$=this.store.select(N.b.selectByKey,"supervisor_display_name_option").pipe(Object(S.a)((function(e){return console.log(e),e?JSON.parse(e.value):null})))}},{key:"getTargetProduct",value:function(e){return e.linkedProductId?this.productEntities[e.linkedProductId]:this.productEntities[e.bookableProductId]}},{key:"getStartTime",value:function(e){return e.startDate?y(e.startDate).format("HH:mm"):""}},{key:"getIsBookedByUser",value:function(e){return Object(I.a)([this.store.select(M.a.selectMember),this.store.select(fe.a.selectAll)]).pipe(Object(S.a)((function(t){var i=n(t,2),o=i[0],r=i[1];return Boolean(r.find((function(t){return t.bookingId==e.bookingId&&(t.memberId==(null==o?void 0:o.id)||t.invitedMemberId==(null==o?void 0:o.id))})))})))}},{key:"onSlotClick",value:function(e){var t=this;if(this.isFreeToBook(e))return this.addToCalendar(e);Object(I.a)([this.participationService.getParticipantCounts$(e),this.getIsBookedByUser(e)]).pipe(Object(D.a)(1)).subscribe((function(i){var o,r=n(i,2),a=r[0],c=r[1];!(e.isAvailable&&a.spotsLeft>0)||c||t.isFreeToBook(e)||[be.HIDDEN,be.NO_RESERVATION].includes(null===(o=t.bookingEntities[e.bookingId])||void 0===o?void 0:o.status)?t.isFreeToBook(e)&&t.addToCalendar(e):t.onClickBookableSlot.emit(e)}))}},{key:"getNameOfSupervisor",value:function(e){var t;if(!(null==e?void 0:e.bookingId))return Object(m.a)("");var i=this.bookingEntities[e.bookingId];return(null===(t=null==i?void 0:i.supervisors)||void 0===t?void 0:t.length)?Object(I.a)([this.store.select(ge.a.selectEntities),this.teacherSupervisorDisplayNameOption$]).pipe(Object(S.a)((function(e){var t=n(e,2),o=t[0],r=t[1];return i.supervisors.map((function(e){return(null==r?void 0:r.show_only_first_name)?""+o[e].firstName:Object(he.c)(o[e])})).join(", ")}))):Object(m.a)("")}},{key:"isFreeToBook",value:function(e){var t;return(null==e?void 0:e.bookingId)&&null!=this.bookingEntities[e.bookingId].status?this.bookingEntities[e.bookingId].status==be.NO_RESERVATION:null===(t=this.getTargetProduct(e))||void 0===t?void 0:t.noReservationAllowed}},{key:"courseEnrollmentHasStarted",value:function(e){var t,n;return!(!e||!(null==e?void 0:e.courseId))&&(null===(n=y(null===(t=this.courseEntities[e.courseId])||void 0===t?void 0:t.startDateEnrollment))||void 0===n?void 0:n.isAfter(y()))}},{key:"addToCalendar",value:function(e){this.icalendarService.addProductToCalendar(this.productEntities[e.linkedProductId],this.productEntities[e.bookableProductId],y(e.startDate),y(e.endDate))}}]),e}()).\u0275fac=function(e){return new(e||Re)(ne.Kb(ie.h),ne.Kb(ae.a),ne.Kb(ve.a),ne.Kb(pe.a),ne.Kb(ye.a))},Re.\u0275cmp=ne.Eb({type:Re,selectors:[["bookable-slot-list"]],inputs:{bookableSlots$:"bookableSlots$"},outputs:{onClickBookableSlot:"onClickBookableSlot"},decls:2,vars:3,consts:[["class","ion-margin-top",4,"ngIf"],[1,"ion-margin-top"],[1,"ion-no-padding"],["lines","full","class","ion-no-padding",3,"click",4,"ngFor","ngForOf"],["lines","full",1,"ion-no-padding",3,"click"],[1,"no-padding-x"],["size","2",1,"no-padding-x","white-space-no-wrap"],[1,"slot-time-container"],["color","dark"],["color","secondary smaller"],["size","1",1,"padding-top-half"],[4,"ngIf"],[3,"size"],[1,"ion-no-padding",3,"size"],[1,"description-wrapper"],["size","6","class","ion-no-padding ion-text-right",4,"ngIf"],["size","12",1,"ion-no-padding"],["color","secondary","class","smaller",4,"ngIf"],["size","12",1,"ion-no-padding","white-space-no-wrap",2,"text-overflow","ellipsis","overflow","hidden"],["color","secondary",1,"smaller"],["class","ion-no-padding",4,"ngIf"],["size","2","class","ion-no-padding add-to-calendar-button-container",4,"ngIf"],["class","course-strip strip-secondary",4,"ngIf"],["class","status-badge",4,"ngIf"],["name","wallet","color","primary",4,"ngIf"],["name","wallet","color","primary"],["size","6",1,"ion-no-padding","ion-text-right"],["color","success"],["size","12",1,"ion-no-padding","white-space-no-wrap"],["color","warning"],["size","2",1,"ion-no-padding","add-to-calendar-button-container"],["name","calendar-outline","slot","icon-only",1,"add-to-calendar-button"],[1,"course-strip","strip-secondary"],[1,"course-text"],[1,"status-badge"],["color","medium"]],template:function(e,t){var n;1&e&&(ne.tc(0,Ne,5,3,"div",0),ne.ac(1,"async")),2&e&&ne.hc("ngIf",(null==(n=ne.bc(1,1,t.bookableSlots$))?null:n.length)>0)},directives:[de.o,le.k,le.H,de.n,le.D,le.w,le.P,le.q,le.bb,le.G,le.y,le.p,le.g,le.h],pipes:[de.b,de.l,Oe.c,Ie,me.a,ke.a],styles:["ion-item[_ngcontent-%COMP%]{position:relative}ion-item[_ngcontent-%COMP%]   .status-badge[_ngcontent-%COMP%]{position:absolute;top:10px;right:10px}.ios[_ngcontent-%COMP%]   ion-card-title[_ngcontent-%COMP%]{font-size:20px;font-weight:500}.description-wrapper[_ngcontent-%COMP%]{display:flex}.description-wrapper[_ngcontent-%COMP%]   h2[_ngcontent-%COMP%]{font-size:1.1rem;min-height:1.1rem;font-weight:600}.description-wrapper[_ngcontent-%COMP%]   ion-icon[_ngcontent-%COMP%]{margin-left:3px}.move-top[_ngcontent-%COMP%]{margin-top:-12px}.add-to-calendar-button-container[_ngcontent-%COMP%]{display:flex;align-items:center;justify-content:center}.add-to-calendar-button-container[_ngcontent-%COMP%]   ion-button[_ngcontent-%COMP%]{min-height:2.5rem}.add-to-calendar-button-container[_ngcontent-%COMP%]   .add-to-calendar-button[_ngcontent-%COMP%]{font-size:1.5rem}"]}),Re),Ve=a("eMFS"),Ke=j.d.HIDE_ITEM_PRICE,Ye=["tabslides"],Je=function(e){return{selected:e}};function He(e,t){if(1&e){var n=ne.Qb();ne.Pb(0,"ion-slide",3),ne.Pb(1,"span",4),ne.Xb("click",(function(){ne.oc(n);var e=t.index;return ne.Zb().selectDay(e)})),ne.vc(2),ne.ac(3,"date"),ne.ac(4,"async"),ne.Ob(),ne.Ob()}if(2&e){var i=t.$implicit,o=t.index,r=ne.Zb();ne.hc("ngClass",ne.lc(9,Je,r.selectedDayIndex==o)),ne.zb(2),ne.xc(" ",ne.ec(3,2,i,"EEEEEE dd",void 0,ne.bc(4,7,r.selectedLanguage$))," ")}}function Ue(e,t){if(1&e&&(ne.Pb(0,"div",13),ne.Lb(1,"selected-tag-list",14),ne.Ob()),2&e){var n=ne.Zb();ne.zb(1),ne.hc("selectedTagIds$",n.selectedTagIds$)}}function Xe(e,t){if(1&e){var n=ne.Qb();ne.Pb(0,"ion-refresher",15),ne.Xb("ionRefresh",(function(e){return ne.oc(n),ne.Zb().doRefresh(e)})),ne.Lb(1,"ion-refresher-content"),ne.Ob()}}function qe(e,t){1&e&&(ne.Pb(0,"ion-row"),ne.Pb(1,"ion-col"),ne.Pb(2,"div",16),ne.Lb(3,"ion-spinner"),ne.Ob(),ne.Ob(),ne.Ob())}function Ge(e,t){if(1&e){var n=ne.Qb();ne.Pb(0,"ion-chip",26),ne.Xb("click",(function(){ne.oc(n);var e=t.$implicit;return ne.Zb(3).selectTagById(e)})),ne.vc(1),ne.ac(2,"async"),ne.ac(3,"async"),ne.Ob()}if(2&e){var i=t.$implicit,o=ne.Zb(3);ne.zb(1),ne.xc(" ",ne.bc(2,1,o.tagService.getTagTranslationById(i,ne.bc(3,3,o.selectedLanguage$)))," ")}}function Qe(e,t){if(1&e&&(ne.Pb(0,"div"),ne.Pb(1,"ion-row"),ne.Pb(2,"ion-col",19),ne.Pb(3,"ion-text",20),ne.vc(4),ne.ac(5,"translate"),ne.Ob(),ne.Ob(),ne.Ob(),ne.Pb(6,"ion-row"),ne.Pb(7,"ion-col",21),ne.Pb(8,"ion-text",22),ne.vc(9),ne.ac(10,"translate"),ne.Ob(),ne.Lb(11,"br"),ne.tc(12,Ge,4,5,"ion-chip",23),ne.ac(13,"async"),ne.Ob(),ne.Ob(),ne.Pb(14,"ion-row"),ne.Pb(15,"ion-col",24),ne.Lb(16,"ion-img",25),ne.Ob(),ne.Ob(),ne.Ob()),2&e){var n=ne.Zb(2);ne.zb(4),ne.xc(" ",ne.bc(5,3,"productModule.nothingFound"),""),ne.zb(5),ne.wc(ne.bc(10,5,"general.try")),ne.zb(3),ne.hc("ngForOf",ne.bc(13,7,n.filteredTryTagIds$))}}function We(e,t){1&e&&(ne.Pb(0,"ion-row",27),ne.Pb(1,"ion-col",24),ne.Lb(2,"ion-img",28),ne.Ob(),ne.Pb(3,"ion-col",29),ne.vc(4),ne.ac(5,"translate"),ne.Ob(),ne.Ob()),2&e&&(ne.zb(4),ne.xc(" ",ne.bc(5,1,"productModule.noSlotsAvailable")," "))}function et(e,t){if(1&e&&(ne.Pb(0,"div",17),ne.tc(1,Qe,17,9,"div",10),ne.ac(2,"async"),ne.tc(3,We,6,3,"ion-row",18),ne.ac(4,"async"),ne.ac(5,"async"),ne.Ob()),2&e){var n,i=ne.Zb(),o=null;ne.zb(1),ne.hc("ngIf",!(null!=(n=ne.bc(2,2,i.selectedTagIds$))&&n.length)),ne.zb(2),ne.hc("ngIf",(null==(o=ne.bc(4,4,i.bookableSlots$))?null:o.length)<1&&(null==(o=ne.bc(5,6,i.selectedTagIds$))?null:o.length))}}var tt,nt,it,ot=function(){return{slidesPerView:"auto",zoom:!1,grabCursor:!0,freeMode:!0}},rt=function(e){return{"slide-disabled":e}},at=[{path:"",canActivate:[h.a],component:(tt=function(){function i(e,t,n,o,a,c,s,l){r(this,i),this.store=e,this.translationService=t,this.courseService=n,this.productService=o,this.router=a,this.bookableSlotService=c,this.tagService=s,this.actions$=l,this.selectedProductIds=[],this.daysToShow=[],this.selectedDayIndex=-1,this.bookingModule=A.a.moduleConfigs.bookingModule,this.fetchedBookingIds=[],this.doRefresh$=new O.a(null),this.isRefreshing=!1,this.selectedDay$=new O.a(y().startOf("day")),this.showAvailableBookingsOnly$=new O.a(!1),this.secondaryFilterConfigs=[]}return c(i,[{key:"ngOnInit",value:function(){var e=this;this.slides.update();for(var t=y().startOf("day"),n=1;n<=this.bookingModule.numberOfDaysAhead;n++)this.daysToShow.push(t.clone().add(n,"days").toDate());this.store.dispatch(Z.a.fetchMany({})),this.store.select(V.a.selectAll).pipe(Object(F.b)(this),Object(k.a)((function(e){return e.length>0}))).subscribe((function(t){if(t.length){var n=t.map((function(e){return e.id}));console.info("Initializing persistant locations, setting all locations selected ..."),e.store.dispatch(K.a.setSelectedLocations({locationIds:n}))}}))}},{key:"ionViewDidEnter",value:function(){var i=this;this.selectedLanguage$=this.store.select(C.a.selectById,"language"),this.isTagLoading$=this.store.select(Q.a.selectIsLoading),this.tryTagIds$=this.store.select(N.b.selectByKey,"tagfilter_example_ids"+x.a.SCHEDULE).pipe(Object(P.a)((function(e){return Boolean(e)})),Object(S.a)((function(e){return JSON.parse(e.value)}))),this.dateSelectorValue=this.getTodayDate(),this.dateSelectorValue=this.getTodayDate(),this.maxNumberOfDays=A.a.moduleConfigs.bookingModule.numberOfDaysAhead,this.store.dispatch(Z.a.fetchMany({})),this.store.dispatch(G.a.fetchManyPublic({params:L.RequestQueryBuilder.create({join:[{field:"parentTags"},{field:"childTags"}]})})),this.productEntities$=this.store.select(U.a.selectEntities),this.tagEntities$=this.store.select(Q.a.selectEntities),this.tagEntities$.pipe(Object(F.b)(this)).subscribe((function(e){i.tagEntities=e})),this.filteredTryTagIds$=Object(I.a)([this.tryTagIds$,this.tagEntities$]).pipe(Object(S.a)((function(e){var t=n(e,2),i=t[0],o=t[1];return Object.keys(o).length?i.filter((function(e){var t;return null===(t=o[e])||void 0===t?void 0:t.activeState})):[]}))),this.isLoading$=Object(I.a)([this.store.select(U.a.selectIsLoading),this.store.select(H.a.selectIsLoading)]).pipe(Object(S.a)((function(e){var t=n(e,2),i=t[0],o=t[1];return Boolean(i||o)}))),this.selectedTagIds$=this.store.select(q.a.selectSelectedTagIds),this.selectedLocationIds$=this.store.select(Y.a.selectSelectedLocationIds),this.store.select(N.b.selectById,"primary_tag_filter_config").pipe(Object(P.a)((function(e){return Boolean(e)})),Object(S.a)((function(e){return JSON.parse(e.value)})),Object(F.b)(this)).subscribe((function(e){i.filterConfigs=e})),this.store.select(N.b.selectById,"secondary_tag_filter_config").pipe(Object(P.a)((function(e){return Boolean(null==e?void 0:e.value)})),Object(S.a)((function(e){return JSON.parse(e.value)})),Object(F.b)(this)).subscribe((function(e){i.secondaryFilterConfigs=e})),Object(I.a)([this.selectedTagIds$,this.selectedDay$,this.doRefresh$]).pipe(Object(P.a)((function(e){var t=n(e,3),o=t[0];return t[1],t[2],Boolean(null==o?void 0:o.length)||Boolean(i.selectedProductIds.length)})),Object(F.b)(this)).subscribe((function(o){var r=n(o,3),a=r[0],c=r[1],s=(r[2],t(a));if(Object.keys(i.tagEntities).length&&((s=a.filter((function(e){var t;return null===(t=i.tagEntities[e])||void 0===t?void 0:t.activeState}))).length!=a.length&&i.store.dispatch(X.a.setSelectedTags({tagIds:s})),!s.length))return[];i.store.dispatch(J.a.resetState());var l=y().valueOf()>c.valueOf()?y().toISOString():c.toISOString(),d={startDate:l,endDate:c.clone().add(24,"hours").toISOString(),tagIds:e({},L.CondOperator.IN,s),productIds:i.selectedProductIds.length>0?e({},L.CondOperator.IN,i.selectedProductIds):void 0,availableFromDate:e({},L.CondOperator.GREATER_THAN,y().toISOString()),availableTillDate:e({},L.CondOperator.GREATER_THAN_EQUALS,l)};i.store.dispatch(J.a.fetchMany({params:L.RequestQueryBuilder.create({search:d,join:{field:"course"}})}))})),this.bookableSlots$=Object(I.a)([this.store.select(H.a.selectAll),this.productEntities$,this.selectedTagIds$,this.selectedDay$,this.showAvailableBookingsOnly$,this.store.select(C.a.selectById,"language"),this.selectedLocationIds$,this.store.select(M.a.selectMember),this.store.select(R.a.selectEntities),this.tagEntities$,this.store.select(ee.a.selectEntities),this.store.select(V.a.selectAll),this.store.select(te.a.selectAll)]).pipe(Object(S.a)((function(e){var o=n(e,13),r=o[0],a=o[1],c=o[2],s=o[3],l=(o[4],o[5],o[6]),d=o[7],u=o[8],b=o[9],f=o[10],g=o[11],h=o[12];return 0===(null==c?void 0:c.length)&&0===i.selectedProductIds.length?[]:r.filter((function(e){var n,o,r,v,p,O,I,m=y(e.startDate),k=y(e.endDate);if(i.selectedProductIds.includes(e.linkedProductId))return!0;if(m.valueOf()<s.valueOf()||k.valueOf()>=s.clone().endOf("day").add(1,"second").valueOf())return!1;if(!e.isAvailable&&!e.bookingId)return!1;var P=a[e.bookableProductId],S=a[e.linkedProductId];if(null===(n=i.selectedProductIds)||void 0===n?void 0:n.includes(e.linkedProductId)){if(!S)return!0;if(y(S.availableFromDate).isAfter(m))return!1;if(y(S.availableTillDate).isSameOrBefore(m))return!1;var w=i.productService.getProductPriceForMember(a[null==S?void 0:S.id],d);return!w||w!=Ke}if(null==e?void 0:e.courseId){var E=u[e.courseId],z=i.courseService.getPriceForMember(E,a[null==E?void 0:E.productId],d,h);if(z&&z==Ke)return!1}var T=null===(o=f[null==e?void 0:e.bookingId])||void 0===o?void 0:o.allowedLinkedProducts,B=null!==(I=null===(O=null!==(p=null===(v=null===(r=f[null==e?void 0:e.bookingId])||void 0===r?void 0:r.allowedLinkedProducts)||void 0===v?void 0:v.map((function(e){var t,n;return null!==(n=null===(t=a[e])||void 0===t?void 0:t.tags)&&void 0!==n?n:[]})))&&void 0!==p?p:[])||void 0===O?void 0:O.flat())&&void 0!==I?I:[],$=(null==P?void 0:P.tags)?P.tags:[],D=(null==S?void 0:S.tags)?S.tags:[],M=(null==P?void 0:P.locationId)?P.locationId:null;if(S){if(y(S.availableFromDate).isAfter(m))return!1;if(y(S.availableTillDate).isSameOrBefore(m))return!1;var C=i.productService.getProductPriceForMember(a[null==S?void 0:S.id],d);if(C&&C==Ke)return!1}else if(null==T?void 0:T.length){var j=!1;T.forEach((function(e){var t,n;if(j||y(null===(t=a[e])||void 0===t?void 0:t.availableFromDate).isBefore(m)&&y(null===(n=a[e])||void 0===n?void 0:n.availableTillDate).isAfter(k)&&(j=!0),!j)return!1}))}else if(P){if(y(P.availableFromDate).isAfter(m))return!1;if(y(P.availableTillDate).isSameOrBefore(k))return!1;var x=i.productService.getProductPriceForMember(a[null==P?void 0:P.id],d);if(x&&x==Ke)return!1}return i.filterBookingByTags(c,b,l,$,[].concat(t(D),t(B)),M,g)}))})),Object(w.a)((function(e){return i.bookableSlotService.enrichMap(i.searchResources(e))})),Object(E.a)(this.productEntities$,this.selectedTagIds$),Object(S.a)((function(e){var t=n(e,3),i=t[0],o=t[1],r=t[2];return 0==r.length?i:i.filter((function(e){if(!e.linkedProductId)return!0;var t=o[e.linkedProductId];return!t||Boolean(null==r?void 0:r.find((function(e){var n;return null===(n=t.tags)||void 0===n?void 0:n.includes(e)})))}))})),Object(z.a)((function(e,t){return JSON.stringify(e)===JSON.stringify(t)})),Object(T.a)((function(e){i.fetchBookingParticipations(e)})),Object(B.a)((function(e){return console.error(e),Object(m.a)([])})))}},{key:"selectDay",value:function(e){this.selectedDayIndex=e,this.selectedDay$.next(-1!==e?y(this.daysToShow[e]).startOf("day"):y().startOf("day"))}},{key:"onSelectedDayChange",value:function(e){this.selectedDay$.next(y(e.detail.value).startOf("day"))}},{key:"onChangeSelectedDay",value:function(){var e=arguments.length>0&&void 0!==arguments[0]?arguments[0]:1,t=y(this.dateSelectorValue).startOf("day");this.dateSelectorValue=t.add(e,"day").format("YYYY-MM-DD")}},{key:"canChangeSelectedDateByAmount",value:function(e){var t=y(this.dateSelectorValue).startOf("day").add(e,"day");return!(e<0&&t.isBefore(y(this.getTodayDate()))||e>0&&t.isAfter(y(this.getMaxDate())))}},{key:"onOnlyAvailableBookingChanged",value:function(e){this.showAvailableBookingsOnly$.next(e)}},{key:"filterBookingByTags",value:function(e,n,i,o,r,a,c){var s,l,d=this,u=e.map((function(e){return null==n?void 0:n[e]})).filter((function(e){var t;return null===(t=d.secondaryFilterConfigs.map((function(e){return e.tagLevel})))||void 0===t?void 0:t.includes(e.level)})).map((function(e){return e.id})),b=e.map((function(e){return null==n?void 0:n[e]})).filter((function(e){var t;return!(null===(t=d.secondaryFilterConfigs.map((function(e){return e.tagLevel})))||void 0===t?void 0:t.includes(e.level))})).map((function(e){return e.id})),f=[].concat(t(o),t(r));return(null==o?void 0:o.length)&&(null==r?void 0:r.length)?(s=b.some((function(e){return r.find((function(t){return t==e&&!(i.length&&(null==c?void 0:c.length)>1&&!(null==i?void 0:i.includes(a)))}))})),l=u.some((function(e){return r.find((function(t){return t==e&&!(i.length&&(null==c?void 0:c.length)>1&&!(null==i?void 0:i.includes(a)))}))}))):(s=b.some((function(e){return f.find((function(t){return t==e&&!(i.length&&(null==c?void 0:c.length)>1&&!(null==i?void 0:i.includes(a)))}))})),l=u.some((function(e){return f.find((function(t){return t==e&&!(i.length&&(null==c?void 0:c.length)>1&&!(null==i?void 0:i.includes(a)))}))}))),b.length>0&&u.length>0?s&&l:s||l}},{key:"getTodayDate",value:function(){return y().format("YYYY-MM-DD")}},{key:"getMaxDate",value:function(){return y().add(this.maxNumberOfDays,"days").format("YYYY-MM-DD")}},{key:"onClickBookableSlot",value:function(e){if(console.log(e),!e.isAvailable)return!1;this.store.dispatch(J.a.setSelected({entity:e})),this.router.navigateByUrl(e.courseId?"/home/shop/courses/"+e.courseId:"/home/schedule/products")}},{key:"selectTagById",value:function(e){this.store.dispatch(X.a.setSelectedTags({tagIds:[e]}))}},{key:"doRefresh",value:function(e){var t=this;this.isRefreshing=!0,this.doRefresh$.next(!0),this.actions$.pipe(Object(_.d)(J.a.upsertMany),Object($.a)(5e3),Object(B.a)((function(n){return e.target.complete(),t.isRefreshing=!1,Object(m.a)(null)}))).subscribe((function(n){t.isRefreshing=!1,e.target.complete()}))}},{key:"fetchBookingParticipations",value:function(n){var i,o=this,r=n.filter((function(e){return e.bookingId})).map((function(e){return null==e?void 0:e.bookingId}));if(null==r?void 0:r.length){var a=r.filter((function(e){return!o.fetchedBookingIds.includes(e)}));(null==a?void 0:a.length)&&(console.log("Will now fetch participants for booking ids "+a),(i=this.fetchedBookingIds).push.apply(i,t(a)),this.store.dispatch(W.a.fetchMany({params:L.RequestQueryBuilder.create({search:{bookingId:e({},L.CondOperator.IN,r)}})})))}}},{key:"searchResources",value:function(e){var t,n=JSON.parse(JSON.stringify(e));return this.store.select(ee.a.selectEntities).pipe(Object(D.a)(1)).subscribe((function(e){return t=e})),Object.keys(t).length>0&&n.map((function(e){e.bookingId&&!e.linkedProductId&&t[e.bookingId]&&(e.startDate=t[e.bookingId].startDate||e.startDate,e.endDate=t[e.bookingId].endDate||e.endDate)})),n}}]),i}(),tt.\u0275fac=function(e){return new(e||tt)(ne.Kb(ie.h),ne.Kb(oe.b),ne.Kb(re.a),ne.Kb(ae.a),ne.Kb(b.j),ne.Kb(ce.a),ne.Kb(se.a),ne.Kb(_.a))},tt.\u0275cmp=ne.Eb({type:tt,selectors:[["app-schedule"]],viewQuery:function(e,t){var n;1&e&&ne.sc(Ye,!0),2&e&&ne.mc(n=ne.Yb())&&(t.slides=n.first)},decls:27,vars:37,consts:[[1,"padding-left-half","padding-right-half"],[3,"options","ngClass"],["tabslides",""],[1,"day-selector-date",3,"ngClass"],[3,"click"],["class","day-selector-date",3,"ngClass",4,"ngFor","ngForOf"],[3,"showLocations"],[1,"ion-padding-horizontal","ion-padding-bottom",3,"fullscreen"],["class","selected-tag-badges",4,"ngIf"],["slot","fixed",3,"ionRefresh",4,"ngIf"],[4,"ngIf"],["class","no-slots-message",4,"ngIf"],[3,"hidden","bookableSlots$","onClickBookableSlot"],[1,"selected-tag-badges"],[3,"selectedTagIds$"],["slot","fixed",3,"ionRefresh"],[1,"ion-text-center"],[1,"no-slots-message"],["class","ion-text-center ion-margin-top",4,"ngIf"],["size","12",1,"ion-text-center","ion-margin-top"],["color","secondary",2,"white-space","pre-wrap"],["size","12",1,"ion-text-center"],[1,"strong"],["color","primary",3,"click",4,"ngFor","ngForOf"],["size","6","offset","3",1,"ion-padding-vertical"],["width","100%","src","assets/svg/illustrations/rope-jump.svg"],["color","primary",3,"click"],[1,"ion-text-center","ion-margin-top"],["width","100%","src","assets/svg/illustrations/sad.svg"],["size","12",1,"alert","secondary-alert"]],template:function(e,t){var n,i;1&e&&(ne.Pb(0,"ion-header"),ne.Pb(1,"ion-toolbar"),ne.Pb(2,"ion-title"),ne.vc(3),ne.ac(4,"translate"),ne.Ob(),ne.Ob(),ne.Pb(5,"ion-toolbar",0),ne.Pb(6,"ion-slides",1,2),ne.ac(8,"async"),ne.Pb(9,"ion-slide",3),ne.Pb(10,"span",4),ne.Xb("click",(function(){return t.selectDay(-1)})),ne.vc(11),ne.ac(12,"translate"),ne.Ob(),ne.Ob(),ne.tc(13,He,5,11,"ion-slide",5),ne.Ob(),ne.Ob(),ne.Lb(14,"tag-filter-slider",6),ne.Ob(),ne.Pb(15,"ion-content",7),ne.tc(16,Ue,2,1,"div",8),ne.ac(17,"async"),ne.tc(18,Xe,2,0,"ion-refresher",9),ne.ac(19,"async"),ne.tc(20,qe,4,0,"ion-row",10),ne.ac(21,"async"),ne.tc(22,et,6,8,"div",11),ne.ac(23,"async"),ne.ac(24,"async"),ne.Pb(25,"bookable-slot-list",12),ne.Xb("onClickBookableSlot",(function(e){return t.onClickBookableSlot(e)})),ne.ac(26,"async"),ne.Ob(),ne.Ob()),2&e&&(ne.zb(3),ne.xc("",ne.bc(4,14,"bookingModule.schedule")," "),ne.zb(3),ne.hc("options",ne.kc(32,ot))("ngClass",ne.lc(33,rt,ne.bc(8,16,t.isLoading$))),ne.zb(3),ne.hc("ngClass",ne.lc(35,Je,-1==t.selectedDayIndex)),ne.zb(2),ne.wc(ne.bc(12,18,"general.today")),ne.zb(2),ne.hc("ngForOf",t.daysToShow),ne.zb(1),ne.hc("showLocations",!0),ne.zb(1),ne.hc("fullscreen",!0),ne.zb(1),ne.hc("ngIf",!ne.bc(17,20,t.isTagLoading$)),ne.zb(2),ne.hc("ngIf",null==(n=ne.bc(19,22,t.selectedTagIds$))?null:n.length),ne.zb(2),ne.hc("ngIf",ne.bc(21,24,t.isLoading$)&&!t.isRefreshing),ne.zb(2),ne.hc("ngIf",!(null!=(i=ne.bc(23,26,t.bookableSlots$))&&i.length||ne.bc(24,28,t.isLoading$))),ne.zb(3),ne.hc("hidden",ne.bc(26,30,t.isLoading$))("bookableSlots$",t.bookableSlots$))},directives:[le.x,le.gb,le.eb,le.W,de.m,le.V,de.n,ue.a,le.r,de.o,Ze,Ve.a,le.M,le.N,le.P,le.q,le.X,le.bb,le.z,le.p],pipes:[Oe.c,de.b,de.f],styles:[".slide-disabled[_ngcontent-%COMP%]{pointer-events:none;opacity:.5}.day-selector-date[_ngcontent-%COMP%]{width:auto;word-spacing:-1.5px;font-size:1rem;margin-right:15px}.day-selector-date.selected[_ngcontent-%COMP%]{color:var(--ion-color-primary);font-weight:500}"]}),tt=Object(p.b)([Object(F.a)()],tt))},{path:"filter",canActivate:[h.a],component:v.a},{path:"products",canActivate:[h.a],component:g.a},{path:"cart",canActivate:[f.a,h.a],loadChildren:function(){return Promise.resolve().then(a.bind(null,"RZQh")).then((function(e){return e.CartModule}))}},{path:"sales",canActivate:[f.a],loadChildren:function(){return Promise.resolve().then(a.bind(null,"X6VU")).then((function(e){return e.SaleModule}))}}],ct=((it=c((function e(){r(this,e)}))).\u0275mod=ne.Ib({type:it}),it.\u0275inj=ne.Hb({factory:function(e){return new(e||it)},imports:[[b.m.forChild(at)],b.m]}),it),st=((nt=c((function e(){r(this,e)}))).\u0275mod=ne.Ib({type:nt}),nt.\u0275inj=ne.Hb({factory:function(e){return new(e||nt)},providers:[],imports:[[ct,l.a,d.ProductModule,u.TagModule]]}),nt)}}])}();