(window.webpackJsonp=window.webpackJsonp||[]).push([[17],{RLmF:function(e,t,i){"use strict";i.r(t),i.d(t,"SchedulePageModule",(function(){return Qe}));var o=i("PCNd"),n=i("ElmV"),s=i("gxPF"),c=i("iInd"),a=i("EcI+"),l=i("x0ql"),r=i("y32E"),d=i("+cd3"),b=i("mrSG"),u=i("sjEL"),g=i("2Vo4"),h=i("itXk"),p=i("LRne"),f=i("SxV6"),v=i("pLZG"),O=i("lJxs"),I=i("eIep"),y=i("zp1y"),P=i("/uUt"),m=i("vkgz"),k=i("JIr8"),S=i("tS1D"),z=i("IzEk"),E=i("C/rb"),w=i("EMFo"),B=i("s0Cq"),T=i("gG+F"),D=i("AytR"),$=i("p6Ya"),M=i("3bzS"),C=i("7bNT"),x=i("XU5k"),j=i("E8GO"),L=i("pZS7"),F=i("j7EN"),_=i("Gd0g"),A=i("/Mrg"),N=i("6c8H"),R=i("POb6"),Z=i("bIeb"),V=i("2bFT"),K=i("byic"),Y=i("4Kl7"),J=i("X56j"),H=i("3kdJ"),U=i("yg+Z"),X=i("iWn4"),q=i("8Y7J"),G=i("tqRt"),Q=i("UhO6"),W=i("w/Lx"),ee=i("bwYc"),te=i("OWih"),ie=i("AMBg"),oe=i("sZkV"),ne=i("SVse"),se=i("BFul"),ce=function(e){return e[e.VISIBLE=0]="VISIBLE",e[e.NO_RESERVATION=1]="NO_RESERVATION",e[e.HIDDEN=2]="HIDDEN",e}({}),ae=i("uxKI"),le=i("B1zd"),re=i("9uVP"),de=i("cOSJ"),be=i("qIvP"),ue=i("q6GM"),ge=i("TSSN");let he=(()=>{class e{constructor(e,t){this.store=e,this.courseService=t}transform(e,t){return Object(h.a)([this.store.select(E.a.selectMember),this.store.select(Z.a.selectEntities),this.store.select(X.a.selectAll)]).pipe(Object(O.a)(([i,o,n])=>this.courseService.getPriceForMember(e,o[e.productId],i,t)))}}return e.\u0275fac=function(t){return new(t||e)(q.Kb(G.h),q.Kb(W.a))},e.\u0275pipe=q.Jb({name:"coursePriceForMember",type:e,pure:!0}),e.\u0275prov=q.Gb({token:e,factory:e.\u0275fac,providedIn:"root"}),e})();var pe=i("DFrK"),fe=i("DVhg");function ve(e,t){1&e&&q.Lb(0,"ion-icon",25)}function Oe(e,t){if(1&e&&(q.Pb(0,"div"),q.tc(1,ve,1,0,"ion-icon",24),q.ac(2,"async"),q.ac(3,"coursePriceForMember"),q.Ob()),2&e){const e=q.Zb().$implicit,t=q.Zb(2);q.zb(1),q.hc("ngIf",0!==q.bc(2,1,q.bc(3,3,t.courseEntities[e.courseId])))}}function Ie(e,t){1&e&&q.Lb(0,"ion-icon",25)}function ye(e,t){if(1&e&&(q.Pb(0,"div"),q.tc(1,Ie,1,0,"ion-icon",24),q.ac(2,"async"),q.ac(3,"productPriceForMember"),q.Ob()),2&e){const e=q.Zb().$implicit,t=q.Zb(2);q.zb(1),q.hc("ngIf",0!==q.bc(2,1,q.cc(3,3,t.getTargetProduct(e),e)))}}function Pe(e,t){1&e&&q.Lb(0,"ion-icon",25)}function me(e,t){if(1&e&&(q.Pb(0,"div"),q.tc(1,Pe,1,0,"ion-icon",24),q.ac(2,"async"),q.ac(3,"productPriceForMember"),q.Ob()),2&e){const e=q.Zb().$implicit,t=q.Zb(2);q.zb(1),q.hc("ngIf",0!==q.bc(2,1,q.cc(3,3,t.getTargetProduct(e),e)))}}function ke(e,t){if(1&e&&(q.Pb(0,"span"),q.vc(1),q.ac(2,"async"),q.ac(3,"getTranslation"),q.Ob()),2&e){const e=q.Zb().$implicit;q.zb(1),q.wc(q.bc(2,1,q.dc(3,3,e.courseId,"course","description")))}}function Se(e,t){if(1&e&&(q.Pb(0,"span"),q.vc(1),q.ac(2,"async"),q.ac(3,"getTranslation"),q.Ob()),2&e){const e=q.Zb().$implicit;q.zb(1),q.xc(" ",q.bc(2,1,q.dc(3,3,e.linkedProductId,"product","description"))," ")}}function ze(e,t){if(1&e&&(q.Pb(0,"span"),q.vc(1),q.ac(2,"async"),q.ac(3,"getTranslation"),q.Ob()),2&e){const e=q.Zb().$implicit;q.zb(1),q.xc(" ",q.bc(2,1,q.dc(3,3,e.bookableProductId,"product","description"))," ")}}function Ee(e,t){1&e&&(q.Pb(0,"ion-col",26),q.Pb(1,"ion-chip",27),q.vc(2),q.ac(3,"translate"),q.Ob(),q.Ob()),2&e&&(q.zb(2),q.xc(" ",q.bc(3,1,"bookingModule.going")," "))}function we(e,t){if(1&e&&(q.Pb(0,"ion-text",19),q.vc(1),q.ac(2,"async"),q.ac(3,"getTranslation"),q.Ob()),2&e){const e=q.Zb().$implicit;q.zb(1),q.xc(" ",q.bc(2,1,q.dc(3,3,null==e?null:e.bookableProductId,"product","description"))," ")}}function Be(e,t){if(1&e&&(q.Pb(0,"ion-row",2),q.Pb(1,"ion-col",18),q.Pb(2,"ion-text",19),q.vc(3),q.Ob(),q.Ob(),q.Ob()),2&e){const e=q.Zb().$implicit,t=q.Zb(2);q.zb(3),q.xc(" ",null==t.bookingEntities[e.bookingId]?null:t.bookingEntities[e.bookingId].description," ")}}function Te(e,t){1&e&&(q.Pb(0,"ion-row",2),q.Pb(1,"ion-col",28),q.Pb(2,"ion-badge",29),q.vc(3),q.ac(4,"translate"),q.ac(5,"lowercase"),q.ac(6,"translate"),q.Ob(),q.Ob(),q.Ob()),2&e&&(q.zb(3),q.yc(" ",q.bc(4,2,"general.enrollment")," ",q.bc(5,4,q.bc(6,6,"general.hasNotStartedYet"))," "))}function De(e,t){1&e&&(q.Pb(0,"ion-col",30),q.Pb(1,"ion-button"),q.Lb(2,"ion-icon",31),q.Ob(),q.Ob())}function $e(e,t){1&e&&(q.Pb(0,"div",32),q.Pb(1,"ion-text",33),q.vc(2),q.ac(3,"translate"),q.Ob(),q.Ob()),2&e&&(q.zb(2),q.xc(" ",q.bc(3,1,"courseModule.course")," "))}function Me(e,t){1&e&&(q.Pb(0,"div",34),q.Pb(1,"ion-badge",35),q.vc(2),q.ac(3,"translate"),q.Ob(),q.Ob()),2&e&&(q.zb(2),q.xc(" ",q.bc(3,1,"bookingModule.full")," "))}function Ce(e,t){if(1&e){const e=q.Qb();q.Pb(0,"ion-item",4),q.Xb("click",(function(){q.oc(e);const i=t.$implicit;return q.Zb(2).onSlotClick(i)})),q.Pb(1,"ion-grid",5),q.Pb(2,"ion-row",5),q.Pb(3,"ion-col",6),q.Pb(4,"div",7),q.Pb(5,"div"),q.Pb(6,"ion-text",8),q.vc(7),q.Ob(),q.Lb(8,"br"),q.Pb(9,"ion-text",9),q.vc(10),q.ac(11,"lowercase"),q.ac(12,"translate"),q.Ob(),q.Ob(),q.Ob(),q.Ob(),q.Pb(13,"ion-col",10),q.tc(14,Oe,4,5,"div",11),q.tc(15,ye,4,6,"div",11),q.tc(16,me,4,6,"div",11),q.Ob(),q.Pb(17,"ion-col",12),q.Pb(18,"ion-row",2),q.Pb(19,"ion-col",13),q.ac(20,"async"),q.Pb(21,"ion-label",14),q.Pb(22,"h2",8),q.tc(23,ke,4,7,"span",11),q.tc(24,Se,4,7,"span",11),q.tc(25,ze,4,7,"span",11),q.Ob(),q.Ob(),q.Ob(),q.tc(26,Ee,4,3,"ion-col",15),q.ac(27,"async"),q.Ob(),q.Pb(28,"ion-row",2),q.Pb(29,"ion-col",16),q.ac(30,"async"),q.tc(31,we,4,7,"ion-text",17),q.Ob(),q.Pb(32,"ion-col",18),q.Pb(33,"ion-text",19),q.vc(34),q.ac(35,"async"),q.Ob(),q.Ob(),q.Ob(),q.tc(36,Be,4,1,"ion-row",20),q.tc(37,Te,7,8,"ion-row",20),q.Ob(),q.tc(38,De,3,0,"ion-col",21),q.Ob(),q.Ob(),q.tc(39,$e,4,3,"div",22),q.tc(40,Me,4,3,"div",23),q.ac(41,"async"),q.Ob()}if(2&e){const e=t.$implicit,o=q.Zb(2);var i;q.zb(7),q.wc(o.getStartTime(e)),q.zb(3),q.yc(" ",o.bookingService.getDuration(e)," ",q.bc(11,21,q.bc(12,23,"general.min"))," "),q.zb(4),q.hc("ngIf",e.courseId),q.zb(1),q.hc("ngIf",!e.courseId&&e.linkedProductId),q.zb(1),q.hc("ngIf",!e.courseId&&!e.linkedProductId),q.zb(1),q.hc("size",o.isFreeToBook(e)?"7":"9"),q.zb(2),q.hc("size",q.bc(20,25,o.getIsBookedByUser(e))?"7":"9"),q.zb(4),q.hc("ngIf",e.courseId),q.zb(1),q.hc("ngIf",!e.courseId&&e.linkedProductId),q.zb(1),q.hc("ngIf",!e.courseId&&!e.linkedProductId),q.zb(1),q.hc("ngIf",q.bc(27,27,o.getIsBookedByUser(e))),q.zb(3),q.Bb(q.bc(30,29,o.getIsBookedByUser(e))?"move-top":""),q.zb(2),q.hc("ngIf",!(null!=e.availableOnParentBookableProductIds&&e.availableOnParentBookableProductIds.length)||(null==e.availableOnParentBookableProductIds?null:e.availableOnParentBookableProductIds.length)<=1),q.zb(3),q.xc(" ",q.bc(35,31,o.getNameOfSupervisor(e))," "),q.zb(2),q.hc("ngIf",null==o.bookingEntities[e.bookingId]?null:o.bookingEntities[e.bookingId].description),q.zb(1),q.hc("ngIf",o.courseEnrollmentHasStarted(e)),q.zb(1),q.hc("ngIf",o.isFreeToBook(e)),q.zb(1),q.hc("ngIf",e.courseId),q.zb(1),q.hc("ngIf",0==(null==(i=q.bc(41,33,o.participationService.getParticipantCounts$(e)))?null:i.spotsLeft))}}function xe(e,t){if(1&e&&(q.Pb(0,"div",1),q.Pb(1,"ion-card-content",2),q.Pb(2,"ion-list",2),q.tc(3,Ce,42,35,"ion-item",3),q.ac(4,"async"),q.Ob(),q.Ob(),q.Ob()),2&e){const e=q.Zb();q.zb(3),q.hc("ngForOf",q.bc(4,1,e.bookableSlots$))}}let je=(()=>{class e{constructor(e,t,i,o,n){this.store=e,this.productService=t,this.icalendarService=i,this.bookingService=o,this.participationService=n,this.onClickBookableSlot=new q.n,this.BookingStatus=ce,this.subscriptions=[]}ngOnInit(){this.store.select(Z.a.selectEntities).subscribe(e=>this.productEntities=e),this.store.select(U.a.selectEntities).subscribe(e=>this.bookingEntities=e),this.store.select(ae.a.selectAll).subscribe(e=>this.participations=e),this.store.select(F.a.selectEntities).subscribe(e=>this.locationEntities=e),this.store.select(j.a.selectEntities).subscribe(e=>this.courseEntities=e),this.teacherSupervisorDisplayNameOption$=this.store.select(x.b.selectByKey,"supervisor_display_name_option").pipe(Object(O.a)(e=>(console.log(e),e?JSON.parse(e.value):null)))}getTargetProduct(e){return e.linkedProductId?this.productEntities[e.linkedProductId]:this.productEntities[e.bookableProductId]}getStartTime(e){return e.startDate?u(e.startDate).format("HH:mm"):""}getIsBookedByUser(e){return Object(h.a)([this.store.select(E.a.selectMember),this.store.select(ae.a.selectAll)]).pipe(Object(O.a)(([t,i])=>Boolean(i.find(i=>i.bookingId==e.bookingId&&(i.memberId==(null==t?void 0:t.id)||i.invitedMemberId==(null==t?void 0:t.id))))))}onSlotClick(e){if(this.isFreeToBook(e))return this.addToCalendar(e);Object(h.a)([this.participationService.getParticipantCounts$(e),this.getIsBookedByUser(e)]).pipe(Object(z.a)(1)).subscribe(([t,i])=>{var o;!(e.isAvailable&&t.spotsLeft>0)||i||this.isFreeToBook(e)||[ce.HIDDEN,ce.NO_RESERVATION].includes(null===(o=this.bookingEntities[e.bookingId])||void 0===o?void 0:o.status)?this.isFreeToBook(e)&&this.addToCalendar(e):this.onClickBookableSlot.emit(e)})}getNameOfSupervisor(e){var t;if(!(null==e?void 0:e.bookingId))return Object(p.a)("");const i=this.bookingEntities[e.bookingId];return(null===(t=null==i?void 0:i.supervisors)||void 0===t?void 0:t.length)?Object(h.a)([this.store.select(le.a.selectEntities),this.teacherSupervisorDisplayNameOption$]).pipe(Object(O.a)(([e,t])=>i.supervisors.map(i=>(null==t?void 0:t.show_only_first_name)?""+e[i].firstName:Object(re.c)(e[i])).join(", "))):Object(p.a)("")}isFreeToBook(e){var t;return(null==e?void 0:e.bookingId)&&null!=this.bookingEntities[e.bookingId].status?this.bookingEntities[e.bookingId].status==ce.NO_RESERVATION:null===(t=this.getTargetProduct(e))||void 0===t?void 0:t.noReservationAllowed}courseEnrollmentHasStarted(e){var t,i;return!(!e||!(null==e?void 0:e.courseId))&&(null===(i=u(null===(t=this.courseEntities[e.courseId])||void 0===t?void 0:t.startDateEnrollment))||void 0===i?void 0:i.isAfter(u()))}addToCalendar(e){this.icalendarService.addProductToCalendar(this.productEntities[e.linkedProductId],this.productEntities[e.bookableProductId],u(e.startDate),u(e.endDate))}}return e.\u0275fac=function(t){return new(t||e)(q.Kb(G.h),q.Kb(ee.a),q.Kb(de.a),q.Kb(be.a),q.Kb(ue.a))},e.\u0275cmp=q.Eb({type:e,selectors:[["bookable-slot-list"]],inputs:{bookableSlots$:"bookableSlots$"},outputs:{onClickBookableSlot:"onClickBookableSlot"},decls:2,vars:3,consts:[["class","ion-margin-top",4,"ngIf"],[1,"ion-margin-top"],[1,"ion-no-padding"],["lines","full","class","ion-no-padding",3,"click",4,"ngFor","ngForOf"],["lines","full",1,"ion-no-padding",3,"click"],[1,"no-padding-x"],["size","2",1,"no-padding-x","white-space-no-wrap"],[1,"slot-time-container"],["color","dark"],["color","secondary smaller"],["size","1",1,"padding-top-half"],[4,"ngIf"],[3,"size"],[1,"ion-no-padding",3,"size"],[1,"description-wrapper"],["size","6","class","ion-no-padding ion-text-right",4,"ngIf"],["size","12",1,"ion-no-padding"],["color","secondary","class","smaller",4,"ngIf"],["size","12",1,"ion-no-padding","white-space-no-wrap",2,"text-overflow","ellipsis","overflow","hidden"],["color","secondary",1,"smaller"],["class","ion-no-padding",4,"ngIf"],["size","2","class","ion-no-padding add-to-calendar-button-container",4,"ngIf"],["class","course-strip strip-secondary",4,"ngIf"],["class","status-badge",4,"ngIf"],["name","wallet","color","primary",4,"ngIf"],["name","wallet","color","primary"],["size","6",1,"ion-no-padding","ion-text-right"],["color","success"],["size","12",1,"ion-no-padding","white-space-no-wrap"],["color","warning"],["size","2",1,"ion-no-padding","add-to-calendar-button-container"],["name","calendar-outline","slot","icon-only",1,"add-to-calendar-button"],[1,"course-strip","strip-secondary"],[1,"course-text"],[1,"status-badge"],["color","medium"]],template:function(e,t){var i;1&e&&(q.tc(0,xe,5,3,"div",0),q.ac(1,"async")),2&e&&q.hc("ngIf",(null==(i=q.bc(1,1,t.bookableSlots$))?null:i.length)>0)},directives:[ne.o,oe.k,oe.H,ne.n,oe.D,oe.w,oe.P,oe.q,oe.bb,oe.G,oe.y,oe.p,oe.g,oe.h],pipes:[ne.b,ne.l,ge.c,he,pe.a,fe.a],styles:["ion-item[_ngcontent-%COMP%]{position:relative}ion-item[_ngcontent-%COMP%]   .status-badge[_ngcontent-%COMP%]{position:absolute;top:10px;right:10px}.ios[_ngcontent-%COMP%]   ion-card-title[_ngcontent-%COMP%]{font-size:20px;font-weight:500}.description-wrapper[_ngcontent-%COMP%]{display:flex}.description-wrapper[_ngcontent-%COMP%]   h2[_ngcontent-%COMP%]{font-size:1.1rem;min-height:1.1rem;font-weight:600}.description-wrapper[_ngcontent-%COMP%]   ion-icon[_ngcontent-%COMP%]{margin-left:3px}.move-top[_ngcontent-%COMP%]{margin-top:-12px}.add-to-calendar-button-container[_ngcontent-%COMP%]{display:flex;align-items:center;justify-content:center}.add-to-calendar-button-container[_ngcontent-%COMP%]   ion-button[_ngcontent-%COMP%]{min-height:2.5rem}.add-to-calendar-button-container[_ngcontent-%COMP%]   .add-to-calendar-button[_ngcontent-%COMP%]{font-size:1.5rem}"]}),e})();var Le=i("eMFS"),Fe=B.d.HIDE_ITEM_PRICE;const _e=["tabslides"],Ae=function(e){return{selected:e}};function Ne(e,t){if(1&e){const e=q.Qb();q.Pb(0,"ion-slide",3),q.Pb(1,"span",4),q.Xb("click",(function(){q.oc(e);const i=t.index;return q.Zb().selectDay(i)})),q.vc(2),q.ac(3,"date"),q.ac(4,"async"),q.Ob(),q.Ob()}if(2&e){const e=t.$implicit,i=t.index,o=q.Zb();q.hc("ngClass",q.lc(9,Ae,o.selectedDayIndex==i)),q.zb(2),q.xc(" ",q.ec(3,2,e,"EEEEEE dd",void 0,q.bc(4,7,o.selectedLanguage$))," ")}}function Re(e,t){if(1&e&&(q.Pb(0,"div",13),q.Lb(1,"selected-tag-list",14),q.Ob()),2&e){const e=q.Zb();q.zb(1),q.hc("selectedTagIds$",e.selectedTagIds$)}}function Ze(e,t){if(1&e){const e=q.Qb();q.Pb(0,"ion-refresher",15),q.Xb("ionRefresh",(function(t){return q.oc(e),q.Zb().doRefresh(t)})),q.Lb(1,"ion-refresher-content"),q.Ob()}}function Ve(e,t){1&e&&(q.Pb(0,"ion-row"),q.Pb(1,"ion-col"),q.Pb(2,"div",16),q.Lb(3,"ion-spinner"),q.Ob(),q.Ob(),q.Ob())}function Ke(e,t){if(1&e){const e=q.Qb();q.Pb(0,"ion-chip",26),q.Xb("click",(function(){q.oc(e);const i=t.$implicit;return q.Zb(3).selectTagById(i)})),q.vc(1),q.ac(2,"async"),q.ac(3,"async"),q.Ob()}if(2&e){const e=t.$implicit,i=q.Zb(3);q.zb(1),q.xc(" ",q.bc(2,1,i.tagService.getTagTranslationById(e,q.bc(3,3,i.selectedLanguage$)))," ")}}function Ye(e,t){if(1&e&&(q.Pb(0,"div"),q.Pb(1,"ion-row"),q.Pb(2,"ion-col",19),q.Pb(3,"ion-text",20),q.vc(4),q.ac(5,"translate"),q.Ob(),q.Ob(),q.Ob(),q.Pb(6,"ion-row"),q.Pb(7,"ion-col",21),q.Pb(8,"ion-text",22),q.vc(9),q.ac(10,"translate"),q.Ob(),q.Lb(11,"br"),q.tc(12,Ke,4,5,"ion-chip",23),q.ac(13,"async"),q.Ob(),q.Ob(),q.Pb(14,"ion-row"),q.Pb(15,"ion-col",24),q.Lb(16,"ion-img",25),q.Ob(),q.Ob(),q.Ob()),2&e){const e=q.Zb(2);q.zb(4),q.xc(" ",q.bc(5,3,"productModule.nothingFound"),""),q.zb(5),q.wc(q.bc(10,5,"general.try")),q.zb(3),q.hc("ngForOf",q.bc(13,7,e.filteredTryTagIds$))}}function Je(e,t){1&e&&(q.Pb(0,"ion-row",27),q.Pb(1,"ion-col",24),q.Lb(2,"ion-img",28),q.Ob(),q.Pb(3,"ion-col",29),q.vc(4),q.ac(5,"translate"),q.Ob(),q.Ob()),2&e&&(q.zb(4),q.xc(" ",q.bc(5,1,"productModule.noSlotsAvailable")," "))}function He(e,t){if(1&e&&(q.Pb(0,"div",17),q.tc(1,Ye,17,9,"div",10),q.ac(2,"async"),q.tc(3,Je,6,3,"ion-row",18),q.ac(4,"async"),q.ac(5,"async"),q.Ob()),2&e){const e=q.Zb();var i,o=null;q.zb(1),q.hc("ngIf",!(null!=(i=q.bc(2,2,e.selectedTagIds$))&&i.length)),q.zb(2),q.hc("ngIf",(null==(o=q.bc(4,4,e.bookableSlots$))?null:o.length)<1&&(null==(o=q.bc(5,6,e.selectedTagIds$))?null:o.length))}}const Ue=function(){return{slidesPerView:"auto",zoom:!1,grabCursor:!0,freeMode:!0}},Xe=function(e){return{"slide-disabled":e}},qe=[{path:"",canActivate:[r.a],component:(()=>{let e=class{constructor(e,t,i,o,n,s,c,a){this.store=e,this.translationService=t,this.courseService=i,this.productService=o,this.router=n,this.bookableSlotService=s,this.tagService=c,this.actions$=a,this.selectedProductIds=[],this.daysToShow=[],this.selectedDayIndex=-1,this.bookingModule=D.a.moduleConfigs.bookingModule,this.fetchedBookingIds=[],this.doRefresh$=new g.a(null),this.isRefreshing=!1,this.selectedDay$=new g.a(u().startOf("day")),this.showAvailableBookingsOnly$=new g.a(!1),this.secondaryFilterConfigs=[]}ngOnInit(){this.slides.update();const e=u().startOf("day");for(let t=1;t<=this.bookingModule.numberOfDaysAhead;t++)this.daysToShow.push(e.clone().add(t,"days").toDate());this.store.dispatch(L.a.fetchMany({})),this.store.select(F.a.selectAll).pipe(Object(M.b)(this),Object(f.a)(e=>e.length>0)).subscribe(e=>{if(!e.length)return;const t=e.map(e=>e.id);console.info("Initializing persistant locations, setting all locations selected ..."),this.store.dispatch(_.a.setSelectedLocations({locationIds:t}))})}ionViewDidEnter(){this.selectedLanguage$=this.store.select(w.a.selectById,"language"),this.isTagLoading$=this.store.select(J.a.selectIsLoading),this.tryTagIds$=this.store.select(x.b.selectByKey,"tagfilter_example_ids"+T.a.SCHEDULE).pipe(Object(v.a)(e=>Boolean(e)),Object(O.a)(e=>JSON.parse(e.value))),this.dateSelectorValue=this.getTodayDate(),this.dateSelectorValue=this.getTodayDate(),this.maxNumberOfDays=D.a.moduleConfigs.bookingModule.numberOfDaysAhead,this.store.dispatch(L.a.fetchMany({})),this.store.dispatch(Y.a.fetchManyPublic({params:$.RequestQueryBuilder.create({join:[{field:"parentTags"},{field:"childTags"}]})})),this.productEntities$=this.store.select(Z.a.selectEntities),this.tagEntities$=this.store.select(J.a.selectEntities),this.tagEntities$.pipe(Object(M.b)(this)).subscribe(e=>{this.tagEntities=e}),this.filteredTryTagIds$=Object(h.a)([this.tryTagIds$,this.tagEntities$]).pipe(Object(O.a)(([e,t])=>Object.keys(t).length?e.filter(e=>{var i;return null===(i=t[e])||void 0===i?void 0:i.activeState}):[])),this.isLoading$=Object(h.a)([this.store.select(Z.a.selectIsLoading),this.store.select(R.a.selectIsLoading)]).pipe(Object(O.a)(([e,t])=>Boolean(e||t))),this.selectedTagIds$=this.store.select(K.a.selectSelectedTagIds),this.selectedLocationIds$=this.store.select(A.a.selectSelectedLocationIds),this.store.select(x.b.selectById,"primary_tag_filter_config").pipe(Object(v.a)(e=>Boolean(e)),Object(O.a)(e=>JSON.parse(e.value)),Object(M.b)(this)).subscribe(e=>{this.filterConfigs=e}),this.store.select(x.b.selectById,"secondary_tag_filter_config").pipe(Object(v.a)(e=>Boolean(null==e?void 0:e.value)),Object(O.a)(e=>JSON.parse(e.value)),Object(M.b)(this)).subscribe(e=>{this.secondaryFilterConfigs=e}),Object(h.a)([this.selectedTagIds$,this.selectedDay$,this.doRefresh$]).pipe(Object(v.a)(([e,t,i])=>Boolean(null==e?void 0:e.length)||Boolean(this.selectedProductIds.length)),Object(M.b)(this)).subscribe(([e,t,i])=>{let o=[...e];if(Object.keys(this.tagEntities).length&&(o=e.filter(e=>{var t;return null===(t=this.tagEntities[e])||void 0===t?void 0:t.activeState}),o.length!=e.length&&this.store.dispatch(V.a.setSelectedTags({tagIds:o})),!o.length))return[];this.store.dispatch(N.a.resetState());const n=u().valueOf()>t.valueOf()?u().toISOString():t.toISOString(),s={startDate:n,endDate:t.clone().add(24,"hours").toISOString(),tagIds:{[$.CondOperator.IN]:o},productIds:this.selectedProductIds.length>0?{[$.CondOperator.IN]:this.selectedProductIds}:void 0,availableFromDate:{[$.CondOperator.GREATER_THAN]:u().toISOString()},availableTillDate:{[$.CondOperator.GREATER_THAN_EQUALS]:n}};this.store.dispatch(N.a.fetchMany({params:$.RequestQueryBuilder.create({search:s,join:{field:"course"}})}))}),this.bookableSlots$=Object(h.a)([this.store.select(R.a.selectAll),this.productEntities$,this.selectedTagIds$,this.selectedDay$,this.showAvailableBookingsOnly$,this.store.select(w.a.selectById,"language"),this.selectedLocationIds$,this.store.select(E.a.selectMember),this.store.select(j.a.selectEntities),this.tagEntities$,this.store.select(U.a.selectEntities),this.store.select(F.a.selectAll),this.store.select(X.a.selectAll)]).pipe(Object(O.a)(([e,t,i,o,n,s,c,a,l,r,d,b,g])=>0===(null==i?void 0:i.length)&&0===this.selectedProductIds.length?[]:e.filter(e=>{var n,s,h,p,f,v,O;const I=u(e.startDate),y=u(e.endDate);if(this.selectedProductIds.includes(e.linkedProductId))return!0;if(I.valueOf()<o.valueOf()||y.valueOf()>=o.clone().endOf("day").add(1,"second").valueOf())return!1;if(!e.isAvailable&&!e.bookingId)return!1;const P=t[e.bookableProductId],m=t[e.linkedProductId];if(null===(n=this.selectedProductIds)||void 0===n?void 0:n.includes(e.linkedProductId)){if(!m)return!0;if(u(m.availableFromDate).isAfter(I))return!1;if(u(m.availableTillDate).isSameOrBefore(I))return!1;const e=this.productService.getProductPriceForMember(t[null==m?void 0:m.id],a);return!e||e!=Fe}if(null==e?void 0:e.courseId){const i=l[e.courseId],o=this.courseService.getPriceForMember(i,t[null==i?void 0:i.productId],a,g);if(o&&o==Fe)return!1}const k=null===(s=d[null==e?void 0:e.bookingId])||void 0===s?void 0:s.allowedLinkedProducts,S=null!==(O=null===(v=null!==(f=null===(p=null===(h=d[null==e?void 0:e.bookingId])||void 0===h?void 0:h.allowedLinkedProducts)||void 0===p?void 0:p.map(e=>{var i,o;return null!==(o=null===(i=t[e])||void 0===i?void 0:i.tags)&&void 0!==o?o:[]}))&&void 0!==f?f:[])||void 0===v?void 0:v.flat())&&void 0!==O?O:[],z=(null==P?void 0:P.tags)?P.tags:[],E=(null==m?void 0:m.tags)?m.tags:[],w=(null==P?void 0:P.locationId)?P.locationId:null;if(m){if(u(m.availableFromDate).isAfter(I))return!1;if(u(m.availableTillDate).isSameOrBefore(I))return!1;const e=this.productService.getProductPriceForMember(t[null==m?void 0:m.id],a);if(e&&e==Fe)return!1}else if(null==k?void 0:k.length){let e=!1;k.forEach(i=>{var o,n;if(e||u(null===(o=t[i])||void 0===o?void 0:o.availableFromDate).isBefore(I)&&u(null===(n=t[i])||void 0===n?void 0:n.availableTillDate).isAfter(y)&&(e=!0),!e)return!1})}else if(P){if(u(P.availableFromDate).isAfter(I))return!1;if(u(P.availableTillDate).isSameOrBefore(y))return!1;const e=this.productService.getProductPriceForMember(t[null==P?void 0:P.id],a);if(e&&e==Fe)return!1}return this.filterBookingByTags(i,r,c,z,[...E,...S],w,b)})),Object(I.a)(e=>this.bookableSlotService.enrichMap(this.searchResources(e))),Object(y.a)(this.productEntities$,this.selectedTagIds$),Object(O.a)(([e,t,i])=>0==i.length?e:e.filter(e=>{if(!e.linkedProductId)return!0;const o=t[e.linkedProductId];return!o||Boolean(null==i?void 0:i.find(e=>{var t;return null===(t=o.tags)||void 0===t?void 0:t.includes(e)}))})),Object(P.a)((e,t)=>JSON.stringify(e)===JSON.stringify(t)),Object(m.a)(e=>{this.fetchBookingParticipations(e)}),Object(k.a)(e=>(console.error(e),Object(p.a)([]))))}selectDay(e){this.selectedDayIndex=e,this.selectedDay$.next(-1!==e?u(this.daysToShow[e]).startOf("day"):u().startOf("day"))}onSelectedDayChange(e){this.selectedDay$.next(u(e.detail.value).startOf("day"))}onChangeSelectedDay(e=1){const t=u(this.dateSelectorValue).startOf("day");this.dateSelectorValue=t.add(e,"day").format("YYYY-MM-DD")}canChangeSelectedDateByAmount(e){const t=u(this.dateSelectorValue).startOf("day").add(e,"day");return!(e<0&&t.isBefore(u(this.getTodayDate()))||e>0&&t.isAfter(u(this.getMaxDate())))}onOnlyAvailableBookingChanged(e){this.showAvailableBookingsOnly$.next(e)}filterBookingByTags(e,t,i,o,n,s,c){const a=e.map(e=>null==t?void 0:t[e]).filter(e=>{var t;return null===(t=this.secondaryFilterConfigs.map(e=>e.tagLevel))||void 0===t?void 0:t.includes(e.level)}).map(e=>e.id),l=e.map(e=>null==t?void 0:t[e]).filter(e=>{var t;return!(null===(t=this.secondaryFilterConfigs.map(e=>e.tagLevel))||void 0===t?void 0:t.includes(e.level))}).map(e=>e.id),r=[...o,...n];let d,b;return(null==o?void 0:o.length)&&(null==n?void 0:n.length)?(d=l.some(e=>n.find(t=>t==e&&!(i.length&&(null==c?void 0:c.length)>1&&!(null==i?void 0:i.includes(s))))),b=a.some(e=>n.find(t=>t==e&&!(i.length&&(null==c?void 0:c.length)>1&&!(null==i?void 0:i.includes(s)))))):(d=l.some(e=>r.find(t=>t==e&&!(i.length&&(null==c?void 0:c.length)>1&&!(null==i?void 0:i.includes(s))))),b=a.some(e=>r.find(t=>t==e&&!(i.length&&(null==c?void 0:c.length)>1&&!(null==i?void 0:i.includes(s)))))),l.length>0&&a.length>0?d&&b:d||b}getTodayDate(){return u().format("YYYY-MM-DD")}getMaxDate(){return u().add(this.maxNumberOfDays,"days").format("YYYY-MM-DD")}onClickBookableSlot(e){if(console.log(e),!e.isAvailable)return!1;this.store.dispatch(N.a.setSelected({entity:e})),this.router.navigateByUrl(e.courseId?"/home/shop/courses/"+e.courseId:"/home/schedule/products")}selectTagById(e){this.store.dispatch(V.a.setSelectedTags({tagIds:[e]}))}doRefresh(e){this.isRefreshing=!0,this.doRefresh$.next(!0),this.actions$.pipe(Object(C.d)(N.a.upsertMany),Object(S.a)(5e3),Object(k.a)(t=>(e.target.complete(),this.isRefreshing=!1,Object(p.a)(null)))).subscribe(t=>{this.isRefreshing=!1,e.target.complete()})}fetchBookingParticipations(e){const t=e.filter(e=>e.bookingId).map(e=>null==e?void 0:e.bookingId);if(!(null==t?void 0:t.length))return;const i=t.filter(e=>!this.fetchedBookingIds.includes(e));(null==i?void 0:i.length)&&(console.log("Will now fetch participants for booking ids "+i),this.fetchedBookingIds.push(...i),this.store.dispatch(H.a.fetchMany({params:$.RequestQueryBuilder.create({search:{bookingId:{[$.CondOperator.IN]:t}}})})))}searchResources(e){let t,i=JSON.parse(JSON.stringify(e));return this.store.select(U.a.selectEntities).pipe(Object(z.a)(1)).subscribe(e=>t=e),Object.keys(t).length>0&&i.map(e=>{e.bookingId&&!e.linkedProductId&&t[e.bookingId]&&(e.startDate=t[e.bookingId].startDate||e.startDate,e.endDate=t[e.bookingId].endDate||e.endDate)}),i}};return e.\u0275fac=function(t){return new(t||e)(q.Kb(G.h),q.Kb(Q.b),q.Kb(W.a),q.Kb(ee.a),q.Kb(c.j),q.Kb(te.a),q.Kb(ie.a),q.Kb(C.a))},e.\u0275cmp=q.Eb({type:e,selectors:[["app-schedule"]],viewQuery:function(e,t){var i;1&e&&q.sc(_e,!0),2&e&&q.mc(i=q.Yb())&&(t.slides=i.first)},decls:27,vars:37,consts:[[1,"padding-left-half","padding-right-half"],[3,"options","ngClass"],["tabslides",""],[1,"day-selector-date",3,"ngClass"],[3,"click"],["class","day-selector-date",3,"ngClass",4,"ngFor","ngForOf"],[3,"showLocations"],[1,"ion-padding-horizontal","ion-padding-bottom",3,"fullscreen"],["class","selected-tag-badges",4,"ngIf"],["slot","fixed",3,"ionRefresh",4,"ngIf"],[4,"ngIf"],["class","no-slots-message",4,"ngIf"],[3,"hidden","bookableSlots$","onClickBookableSlot"],[1,"selected-tag-badges"],[3,"selectedTagIds$"],["slot","fixed",3,"ionRefresh"],[1,"ion-text-center"],[1,"no-slots-message"],["class","ion-text-center ion-margin-top",4,"ngIf"],["size","12",1,"ion-text-center","ion-margin-top"],["color","secondary",2,"white-space","pre-wrap"],["size","12",1,"ion-text-center"],[1,"strong"],["color","primary",3,"click",4,"ngFor","ngForOf"],["size","6","offset","3",1,"ion-padding-vertical"],["width","100%","src","assets/svg/illustrations/rope-jump.svg"],["color","primary",3,"click"],[1,"ion-text-center","ion-margin-top"],["width","100%","src","assets/svg/illustrations/sad.svg"],["size","12",1,"alert","secondary-alert"]],template:function(e,t){var i,o;1&e&&(q.Pb(0,"ion-header"),q.Pb(1,"ion-toolbar"),q.Pb(2,"ion-title"),q.vc(3),q.ac(4,"translate"),q.Ob(),q.Ob(),q.Pb(5,"ion-toolbar",0),q.Pb(6,"ion-slides",1,2),q.ac(8,"async"),q.Pb(9,"ion-slide",3),q.Pb(10,"span",4),q.Xb("click",(function(){return t.selectDay(-1)})),q.vc(11),q.ac(12,"translate"),q.Ob(),q.Ob(),q.tc(13,Ne,5,11,"ion-slide",5),q.Ob(),q.Ob(),q.Lb(14,"tag-filter-slider",6),q.Ob(),q.Pb(15,"ion-content",7),q.tc(16,Re,2,1,"div",8),q.ac(17,"async"),q.tc(18,Ze,2,0,"ion-refresher",9),q.ac(19,"async"),q.tc(20,Ve,4,0,"ion-row",10),q.ac(21,"async"),q.tc(22,He,6,8,"div",11),q.ac(23,"async"),q.ac(24,"async"),q.Pb(25,"bookable-slot-list",12),q.Xb("onClickBookableSlot",(function(e){return t.onClickBookableSlot(e)})),q.ac(26,"async"),q.Ob(),q.Ob()),2&e&&(q.zb(3),q.xc("",q.bc(4,14,"bookingModule.schedule")," "),q.zb(3),q.hc("options",q.kc(32,Ue))("ngClass",q.lc(33,Xe,q.bc(8,16,t.isLoading$))),q.zb(3),q.hc("ngClass",q.lc(35,Ae,-1==t.selectedDayIndex)),q.zb(2),q.wc(q.bc(12,18,"general.today")),q.zb(2),q.hc("ngForOf",t.daysToShow),q.zb(1),q.hc("showLocations",!0),q.zb(1),q.hc("fullscreen",!0),q.zb(1),q.hc("ngIf",!q.bc(17,20,t.isTagLoading$)),q.zb(2),q.hc("ngIf",null==(i=q.bc(19,22,t.selectedTagIds$))?null:i.length),q.zb(2),q.hc("ngIf",q.bc(21,24,t.isLoading$)&&!t.isRefreshing),q.zb(2),q.hc("ngIf",!(null!=(o=q.bc(23,26,t.bookableSlots$))&&o.length||q.bc(24,28,t.isLoading$))),q.zb(3),q.hc("hidden",q.bc(26,30,t.isLoading$))("bookableSlots$",t.bookableSlots$))},directives:[oe.x,oe.gb,oe.eb,oe.W,ne.m,oe.V,ne.n,se.a,oe.r,ne.o,je,Le.a,oe.M,oe.N,oe.P,oe.q,oe.X,oe.bb,oe.z,oe.p],pipes:[ge.c,ne.b,ne.f],styles:[".slide-disabled[_ngcontent-%COMP%]{pointer-events:none;opacity:.5}.day-selector-date[_ngcontent-%COMP%]{width:auto;word-spacing:-1.5px;font-size:1rem;margin-right:15px}.day-selector-date.selected[_ngcontent-%COMP%]{color:var(--ion-color-primary);font-weight:500}"]}),e=Object(b.b)([Object(M.a)()],e),e})()},{path:"filter",canActivate:[r.a],component:d.a},{path:"products",canActivate:[r.a],component:l.a},{path:"cart",canActivate:[a.a,r.a],loadChildren:()=>Promise.resolve().then(i.bind(null,"RZQh")).then(e=>e.CartModule)},{path:"sales",canActivate:[a.a],loadChildren:()=>Promise.resolve().then(i.bind(null,"X6VU")).then(e=>e.SaleModule)}];let Ge=(()=>{class e{}return e.\u0275mod=q.Ib({type:e}),e.\u0275inj=q.Hb({factory:function(t){return new(t||e)},imports:[[c.m.forChild(qe)],c.m]}),e})(),Qe=(()=>{class e{}return e.\u0275mod=q.Ib({type:e}),e.\u0275inj=q.Hb({factory:function(t){return new(t||e)},providers:[],imports:[[Ge,o.a,n.ProductModule,s.TagModule]]}),e})()}}]);