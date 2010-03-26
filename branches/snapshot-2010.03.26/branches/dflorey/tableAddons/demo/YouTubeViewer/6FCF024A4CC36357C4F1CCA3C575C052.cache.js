(function(){var $wnd = window;var $doc = $wnd.document;var $moduleName, $moduleBase;var _,xs='com.google.gwt.core.client.',ys='com.google.gwt.demos.youtubeviewer.client.',zs='com.google.gwt.lang.',As='com.google.gwt.user.client.',Bs='com.google.gwt.user.client.impl.',Cs='com.google.gwt.user.client.ui.',Ds='com.google.gwt.user.client.ui.impl.',Es='com.google.gwt.widgetideas.client.',Fs='java.lang.',at='java.util.';function ws(){}
function kn(a){return this===a;}
function ln(){return zn(this);}
function hn(){}
_=hn.prototype={};_.eQ=kn;_.hC=ln;_.tI=1;var o=null;function r(a){return a==null?0:a.$H?a.$H:(a.$H=t());}
function s(a){return a==null?0:a.$H?a.$H:(a.$H=t());}
function t(){return ++u;}
var u=0;function x(b,a){if(!ic(a,2)){return false;}return B(b,hc(a,2));}
function y(a){return r(a);}
function z(){return [];}
function A(){return {};}
function C(a){return x(this,a);}
function B(a,b){return a===b;}
function D(){return y(this);}
function v(){}
_=v.prototype=new hn();_.eQ=C;_.hC=D;_.tI=7;function xb(a){a.a=km(new jm(),'mc336q361N0');}
function yb(a){xb(a);return a;}
function Ab(d){var a,b,c,e,f;d.a=km(new jm(),'k56DbGDBsNk');nk(d.a,'YouTubeViewer');ok(d.a,'443px');mk(d.a,'369px');lf(tj(),d.a);lf(tj(),ui(new Ag(),'<BR><BR>'));a=rg(new pg(),5,3);e=gk(new Ej());dk(e,'iuywmPePYKQ');ni(a,0,0,'<B>Video ID:<\/B>');pi(a,0,1,e);pi(a,0,2,xf(new pf(),'Set',ab(new F(),d,e)));f=gk(new Ej());dk(f,'443px');ni(a,1,0,'<B>Width:<\/B>');pi(a,1,1,f);pi(a,1,2,xf(new pf(),'Set',eb(new db(),d,f)));b=gk(new Ej());dk(b,'369px');ni(a,2,0,'<B>Height:<\/B>');pi(a,2,1,b);pi(a,2,2,xf(new pf(),'Set',ib(new hb(),d,b)));c=gk(new Ej());dk(c,'http://www.google.com');ni(a,3,0,'<B>Plugins Page:<\/B>');pi(a,3,1,c);pi(a,3,2,xf(new pf(),'Set',mb(new lb(),d,c)));pi(a,4,0,xf(new pf(),'Enable AutoPlay',qb(new pb(),d)));pi(a,4,1,xf(new pf(),'Enable Related',ub(new tb(),d)));lf(tj(),a);}
function E(){}
_=E.prototype=new hn();_.tI=0;function ab(b,a,c){b.a=a;b.b=c;return b;}
function cb(a){pm(this.a.a,ck(this.b));}
function F(){}
_=F.prototype=new hn();_.C=cb;_.tI=8;function eb(b,a,c){b.a=a;b.b=c;return b;}
function gb(a){ok(this.a.a,ck(this.b));}
function db(){}
_=db.prototype=new hn();_.C=gb;_.tI=9;function ib(b,a,c){b.a=a;b.b=c;return b;}
function kb(a){mk(this.a.a,ck(this.b));}
function hb(){}
_=hb.prototype=new hn();_.C=kb;_.tI=10;function mb(b,a,c){b.a=a;b.b=c;return b;}
function ob(b){var a;a=this.a.a.d;bm(a,ck(this.b));hm(this.a.a,a);}
function lb(){}
_=lb.prototype=new hn();_.C=ob;_.tI=11;function qb(b,a){b.a=a;return b;}
function sb(a){if(this.a.a.a){om(this.a.a,false);uf(hc(a,3),'Enable AutoPlay');}else{om(this.a.a,true);uf(hc(a,3),'Disable AutoPlay');}}
function pb(){}
_=pb.prototype=new hn();_.C=sb;_.tI=12;function ub(b,a){b.a=a;return b;}
function wb(a){if(this.a.a.c){qm(this.a.a,false);uf(hc(a,3),'Enable Related');}else{qm(this.a.a,true);uf(hc(a,3),'Disable Related');}}
function tb(){}
_=tb.prototype=new hn();_.C=wb;_.tI=13;function Cb(c,a,d,b,e){c.a=a;c.b=b;e;c.tI=d;return c;}
function Eb(a,b,c){return a[b]=c;}
function Fb(b,a){return b[a];}
function ac(a){return a.length;}
function cc(e,d,c,b,a){return bc(e,d,c,b,0,ac(b),a);}
function bc(j,i,g,c,e,a,b){var d,f,h;if((f=Fb(c,e))<0){throw new fn();}h=Cb(new Bb(),f,Fb(i,e),Fb(g,e),j);++e;if(e<a){j=sn(j,1);for(d=0;d<f;++d){Eb(h,d,bc(j,i,g,c,e,a,b));}}else{for(d=0;d<f;++d){Eb(h,d,b);}}return h;}
function dc(a,b,c){if(c!==null&&a.b!=0&& !ic(c,a.b)){throw new sm();}return Eb(a,b,c);}
function Bb(){}
_=Bb.prototype=new hn();_.tI=0;function gc(b,a){return !(!(b&&lc[b][a]));}
function hc(b,a){if(b!=null)gc(b.tI,a)||kc();return b;}
function ic(b,a){return b!=null&&gc(b.tI,a);}
function kc(){throw new vm();}
function jc(a){if(a!==null){throw new vm();}return a;}
function mc(b,d){_=d.prototype;if(b&& !(b.tI>=_.tI)){var c=b.toString;for(var a in _){b[a]=_[a];}b.toString=c;}return b;}
var lc;function qc(){qc=ws;jd=fq(new dq());{ed=new pe();xe(ed);}}
function rc(b,a){qc();Ae(ed,b,a);}
function sc(a,b){qc();return te(ed,a,b);}
function tc(){qc();return Ce(ed,'button');}
function uc(){qc();return Ce(ed,'div');}
function vc(a){qc();return Ce(ed,a);}
function wc(){qc();return De(ed,'text');}
function xc(){qc();return Ce(ed,'tbody');}
function yc(){qc();return Ce(ed,'td');}
function zc(){qc();return Ce(ed,'table');}
function Cc(b,a,d){qc();var c;c=o;{Bc(b,a,d);}}
function Bc(b,a,c){qc();var d;if(a===id){if(Ec(b)==8192){id=null;}}d=Ac;Ac=b;try{c.B(b);}finally{Ac=d;}}
function Dc(b,a){qc();Ee(ed,b,a);}
function Ec(a){qc();return Fe(ed,a);}
function Fc(a){qc();ue(ed,a);}
function ad(a,b){qc();return af(ed,a,b);}
function bd(a){qc();return bf(ed,a);}
function cd(a){qc();return ve(ed,a);}
function dd(a){qc();return we(ed,a);}
function fd(c,a,b){qc();ye(ed,c,a,b);}
function gd(a){qc();var b,c;c=true;if(jd.b>0){b=jc(jq(jd,jd.b-1));if(!(c=null.hb())){Dc(a,true);Fc(a);}}return c;}
function hd(b,a){qc();cf(ed,b,a);}
function kd(a,b,c){qc();df(ed,a,b,c);}
function ld(a,b){qc();ef(ed,a,b);}
function md(a,b){qc();ff(ed,a,b);}
function nd(a,b){qc();gf(ed,a,b);}
function od(b,a,c){qc();hf(ed,b,a,c);}
function pd(a,b){qc();ze(ed,a,b);}
var Ac=null,ed=null,id=null,jd;function sd(a){if(ic(a,5)){return sc(this,hc(a,5));}return x(mc(this,qd),a);}
function td(){return y(mc(this,qd));}
function qd(){}
_=qd.prototype=new v();_.eQ=sd;_.hC=td;_.tI=14;function xd(a){return x(mc(this,ud),a);}
function yd(){return y(mc(this,ud));}
function ud(){}
_=ud.prototype=new v();_.eQ=xd;_.hC=yd;_.tI=15;function Ed(){Ed=ws;ae=fq(new dq());{Fd();}}
function Fd(){Ed();ee(new Ad());}
var ae;function Cd(){while((Ed(),ae).b>0){jc(jq((Ed(),ae),0)).hb();}}
function Dd(){return null;}
function Ad(){}
_=Ad.prototype=new hn();_.F=Cd;_.ab=Dd;_.tI=16;function de(){de=ws;fe=fq(new dq());ne=fq(new dq());{je();}}
function ee(a){de();gq(fe,a);}
function ge(){de();var a,b;for(a=ro(fe);ko(a);){b=hc(lo(a),6);b.F();}}
function he(){de();var a,b,c,d;d=null;for(a=ro(fe);ko(a);){b=hc(lo(a),6);c=b.ab();{d=c;}}return d;}
function ie(){de();var a,b;for(a=ro(ne);ko(a);){b=jc(lo(a));null.hb();}}
function je(){de();__gwt_initHandlers(function(){me();},function(){return le();},function(){ke();$wnd.onresize=null;$wnd.onbeforeclose=null;$wnd.onclose=null;});}
function ke(){de();var a;a=o;{ge();}}
function le(){de();var a;a=o;{return he();}}
function me(){de();var a;a=o;{ie();}}
var fe,ne;function Ae(c,b,a){b.appendChild(a);}
function Ce(b,a){return $doc.createElement(a);}
function De(b,c){var a=$doc.createElement('INPUT');a.type=c;return a;}
function Ee(c,b,a){b.cancelBubble=a;}
function Fe(b,a){switch(a.type){case 'blur':return 4096;case 'change':return 1024;case 'click':return 1;case 'dblclick':return 2;case 'focus':return 2048;case 'keydown':return 128;case 'keypress':return 256;case 'keyup':return 512;case 'load':return 32768;case 'losecapture':return 8192;case 'mousedown':return 4;case 'mousemove':return 64;case 'mouseout':return 32;case 'mouseover':return 16;case 'mouseup':return 8;case 'scroll':return 16384;case 'error':return 65536;case 'mousewheel':return 131072;case 'DOMMouseScroll':return 131072;}}
function af(d,a,b){var c=a[b];return c==null?null:String(c);}
function bf(b,a){return a.__eventBits||0;}
function cf(c,b,a){b.removeChild(a);}
function df(c,a,b,d){a[b]=d;}
function ef(c,a,b){a.__listener=b;}
function ff(c,a,b){if(!b){b='';}a.innerHTML=b;}
function gf(c,a,b){while(a.firstChild){a.removeChild(a.firstChild);}if(b!=null){a.appendChild($doc.createTextNode(b));}}
function hf(c,b,a,d){b.style[a]=d;}
function oe(){}
_=oe.prototype=new hn();_.tI=0;function te(c,a,b){return a==b;}
function ue(b,a){a.preventDefault();}
function ve(c,b){var a=b.firstChild;while(a&&a.nodeType!=1)a=a.nextSibling;return a||null;}
function we(c,a){var b=a.parentNode;if(b==null){return null;}if(b.nodeType!=1)b=null;return b||null;}
function xe(d){$wnd.__dispatchCapturedMouseEvent=function(b){if($wnd.__dispatchCapturedEvent(b)){var a=$wnd.__captureElem;if(a&&a.__listener){Cc(b,a,a.__listener);b.stopPropagation();}}};$wnd.__dispatchCapturedEvent=function(a){if(!gd(a)){a.stopPropagation();a.preventDefault();return false;}return true;};$wnd.addEventListener('click',$wnd.__dispatchCapturedMouseEvent,true);$wnd.addEventListener('dblclick',$wnd.__dispatchCapturedMouseEvent,true);$wnd.addEventListener('mousedown',$wnd.__dispatchCapturedMouseEvent,true);$wnd.addEventListener('mouseup',$wnd.__dispatchCapturedMouseEvent,true);$wnd.addEventListener('mousemove',$wnd.__dispatchCapturedMouseEvent,true);$wnd.addEventListener('mousewheel',$wnd.__dispatchCapturedMouseEvent,true);$wnd.addEventListener('keydown',$wnd.__dispatchCapturedEvent,true);$wnd.addEventListener('keyup',$wnd.__dispatchCapturedEvent,true);$wnd.addEventListener('keypress',$wnd.__dispatchCapturedEvent,true);$wnd.__dispatchEvent=function(b){var c,a=this;while(a&& !(c=a.__listener))a=a.parentNode;if(a&&a.nodeType!=1)a=null;if(c)Cc(b,a,c);};$wnd.__captureElem=null;}
function ye(f,e,g,d){var c=0,b=e.firstChild,a=null;while(b){if(b.nodeType==1){if(c==d){a=b;break;}++c;}b=b.nextSibling;}e.insertBefore(g,a);}
function ze(c,b,a){b.__eventBits=a;b.onclick=a&1?$wnd.__dispatchEvent:null;b.ondblclick=a&2?$wnd.__dispatchEvent:null;b.onmousedown=a&4?$wnd.__dispatchEvent:null;b.onmouseup=a&8?$wnd.__dispatchEvent:null;b.onmouseover=a&16?$wnd.__dispatchEvent:null;b.onmouseout=a&32?$wnd.__dispatchEvent:null;b.onmousemove=a&64?$wnd.__dispatchEvent:null;b.onkeydown=a&128?$wnd.__dispatchEvent:null;b.onkeypress=a&256?$wnd.__dispatchEvent:null;b.onkeyup=a&512?$wnd.__dispatchEvent:null;b.onchange=a&1024?$wnd.__dispatchEvent:null;b.onfocus=a&2048?$wnd.__dispatchEvent:null;b.onblur=a&4096?$wnd.__dispatchEvent:null;b.onlosecapture=a&8192?$wnd.__dispatchEvent:null;b.onscroll=a&16384?$wnd.__dispatchEvent:null;b.onload=a&32768?$wnd.__dispatchEvent:null;b.onerror=a&65536?$wnd.__dispatchEvent:null;b.onmousewheel=a&131072?$wnd.__dispatchEvent:null;}
function re(){}
_=re.prototype=new oe();_.tI=0;function pe(){}
_=pe.prototype=new re();_.tI=0;function kk(d,b,a){var c=b.parentNode;if(!c){return;}c.insertBefore(a,b);c.removeChild(b);}
function lk(b,a){if(b.k!==null){kk(b,b.k,a);}b.k=a;}
function mk(b,a){od(b.k,'height',a);}
function nk(b,a){qk(b.k,a);}
function ok(a,b){od(a.k,'width',b);}
function pk(b,a){pd(b.k,a|bd(b.k));}
function qk(a,b){kd(a,'className',b);}
function ik(){}
_=ik.prototype=new hn();_.tI=0;_.k=null;function bl(a){if(a.i){throw an(new Fm(),"Should only call onAttach when the widget is detached from the browser's document");}a.i=true;ld(a.k,a);a.q();a.D();}
function cl(a){if(!a.i){throw an(new Fm(),"Should only call onDetach when the widget is attached to the browser's document");}try{a.E();}finally{a.r();ld(a.k,null);a.i=false;}}
function dl(a){if(a.j!==null){a.j.cb(a);}else if(a.j!==null){throw an(new Fm(),"This widget's parent does not implement HasWidgets");}}
function el(b,a){if(b.i){ld(b.k,null);}lk(b,a);if(b.i){ld(a,b);}}
function fl(c,b){var a;a=c.j;if(b===null){if(a!==null&&a.i){cl(c);}c.j=null;}else{if(a!==null){throw an(new Fm(),'Cannot set a new parent without first clearing the old parent');}c.j=b;if(b.i){bl(c);}}}
function gl(){}
function hl(){}
function il(a){}
function jl(){}
function kl(){}
function ll(a){el(this,a);}
function rk(){}
_=rk.prototype=new ik();_.q=gl;_.r=hl;_.B=il;_.D=jl;_.E=kl;_.db=ll;_.tI=17;_.i=false;_.j=null;function ej(b,a){fl(a,b);}
function gj(b,a){fl(a,null);}
function hj(){var a,b;for(b=this.y();b.x();){a=hc(b.A(),8);bl(a);}}
function ij(){var a,b;for(b=this.y();b.x();){a=hc(b.A(),8);cl(a);}}
function jj(){}
function kj(){}
function dj(){}
_=dj.prototype=new rk();_.q=hj;_.r=ij;_.D=jj;_.E=kj;_.tI=18;function ag(a){a.a=yk(new sk(),a);}
function bg(a){ag(a);return a;}
function cg(c,a,b){dl(a);zk(c.a,a);rc(b,a.k);ej(c,a);}
function eg(b,c){var a;if(c.j!==b){return false;}gj(b,c);a=c.k;hd(dd(a),a);Fk(b.a,c);return true;}
function fg(){return Dk(this.a);}
function gg(a){return eg(this,a);}
function Ff(){}
_=Ff.prototype=new dj();_.y=fg;_.cb=gg;_.tI=19;function kf(a){bg(a);a.db(uc());od(a.k,'position','relative');od(a.k,'overflow','hidden');return a;}
function lf(a,b){cg(a,b,a.k);}
function nf(a){od(a,'left','');od(a,'top','');od(a,'position','');}
function of(b){var a;a=eg(this,b);if(a){nf(b.k);}return a;}
function jf(){}
_=jf.prototype=new Ff();_.cb=of;_.tI=20;function jg(){jg=ws;yl(),Al;}
function ig(b,a){yl(),Al;lg(b,a);return b;}
function kg(b,a){switch(Ec(a)){case 1:if(b.b!==null){Df(b.b,b);}break;case 4096:case 2048:break;case 128:case 512:case 256:break;}}
function lg(b,a){el(b,a);pk(b,7041);}
function mg(a){if(this.b===null){this.b=Bf(new Af());}gq(this.b,a);}
function ng(a){kg(this,a);}
function og(a){lg(this,a);}
function hg(){}
_=hg.prototype=new rk();_.l=mg;_.B=ng;_.db=og;_.tI=21;_.b=null;function sf(){sf=ws;yl(),Al;}
function rf(b,a){yl(),Al;ig(b,a);return b;}
function tf(b,a){md(b.k,a);}
function uf(b,a){nd(b.k,a);}
function qf(){}
_=qf.prototype=new hg();_.tI=22;function yf(){yf=ws;yl(),Al;}
function vf(a){yl(),Al;rf(a,tc());zf(a.k);nk(a,'gwt-Button');return a;}
function wf(b,a){yl(),Al;vf(b);tf(b,a);return b;}
function xf(c,a,b){yl(),Al;wf(c,a);c.l(b);return c;}
function zf(b){yf();if(b.type=='submit'){try{b.setAttribute('type','button');}catch(a){}}}
function pf(){}
_=pf.prototype=new qf();_.tI=23;function bo(d,a,b){var c;while(a.x()){c=a.A();if(b===null?c===null:b.eQ(c)){return a;}}return null;}
function eo(a){throw En(new Dn(),'add');}
function fo(b){var a;a=bo(this,this.y(),b);return a!==null;}
function ao(){}
_=ao.prototype=new hn();_.n=eo;_.p=fo;_.tI=0;function qo(b,a){throw dn(new cn(),'Index: '+a+', Size: '+b.b);}
function ro(a){return io(new ho(),a);}
function so(b,a){throw En(new Dn(),'add');}
function to(a){this.m(this.fb(),a);return true;}
function uo(e){var a,b,c,d,f;if(e===this){return true;}if(!ic(e,13)){return false;}f=hc(e,13);if(this.fb()!=f.fb()){return false;}c=ro(this);d=f.y();while(ko(c)){a=lo(c);b=lo(d);if(!(a===null?b===null:a.eQ(b))){return false;}}return true;}
function vo(){var a,b,c,d;c=1;a=31;b=ro(this);while(ko(b)){d=lo(b);c=31*c+(d===null?0:d.hC());}return c;}
function wo(){return ro(this);}
function xo(a){throw En(new Dn(),'remove');}
function go(){}
_=go.prototype=new ao();_.m=so;_.n=to;_.eQ=uo;_.hC=vo;_.y=wo;_.bb=xo;_.tI=24;function eq(a){{hq(a);}}
function fq(a){eq(a);return a;}
function gq(b,a){xq(b.a,b.b++,a);return true;}
function hq(a){a.a=z();a.b=0;}
function jq(b,a){if(a<0||a>=b.b){qo(b,a);}return tq(b.a,a);}
function kq(b,a){return lq(b,a,0);}
function lq(c,b,a){if(a<0){qo(c,a);}for(;a<c.b;++a){if(sq(b,tq(c.a,a))){return a;}}return (-1);}
function mq(c,a){var b;b=jq(c,a);vq(c.a,a,1);--c.b;return b;}
function nq(d,a,b){var c;c=jq(d,a);xq(d.a,a,b);return c;}
function pq(a,b){if(a<0||a>this.b){qo(this,a);}oq(this.a,a,b);++this.b;}
function qq(a){return gq(this,a);}
function oq(a,b,c){a.splice(b,0,c);}
function rq(a){return kq(this,a)!=(-1);}
function sq(a,b){return a===b||a!==null&&a.eQ(b);}
function uq(a){return jq(this,a);}
function tq(a,b){return a[b];}
function wq(a){return mq(this,a);}
function vq(a,c,b){a.splice(c,b);}
function xq(a,b,c){a[b]=c;}
function yq(){return this.b;}
function dq(){}
_=dq.prototype=new go();_.m=pq;_.n=qq;_.p=rq;_.v=uq;_.bb=wq;_.fb=yq;_.tI=25;_.a=null;_.b=0;function Bf(a){fq(a);return a;}
function Df(d,c){var a,b;for(a=ro(d);ko(a);){b=hc(lo(a),7);b.C(c);}}
function Af(){}
_=Af.prototype=new dq();_.tI=26;function Fh(a){a.h=vh(new qh());}
function ai(a){Fh(a);a.g=zc();a.c=xc();rc(a.g,a.c);a.db(a.g);pk(a,1);return a;}
function bi(d,c,b){var a;ci(d,c);if(b<0){throw dn(new cn(),'Column '+b+' must be non-negative: '+b);}a=d.a;if(a<=b){throw dn(new cn(),'Column index: '+b+', Column size: '+d.a);}}
function ci(c,a){var b;b=c.b;if(a>=b||a<0){throw dn(new cn(),'Row index: '+a+', Row size: '+b);}}
function di(e,c,b,a){var d;d=ih(e.d,c,b);hi(e,d,a);return d;}
function fi(a){return yc();}
function gi(d,b,a){var c,e;e=ph(d.f,d.c,b);c=tg(d);fd(e,c,a);}
function hi(d,c,a){var b,e;b=cd(c);e=null;if(b!==null){e=xh(d.h,b);}if(e!==null){ki(d,e);return true;}else{if(a){md(c,'');}return false;}}
function ki(b,c){var a;if(c.j!==b){return false;}gj(b,c);a=c.k;hd(dd(a),a);Ah(b.h,a);return true;}
function ii(d,b,a){var c,e;bi(d,b,a);c=di(d,b,a,false);e=ph(d.f,d.c,b);hd(e,c);}
function ji(d,c){var a,b;b=d.a;for(a=0;a<b;++a){di(d,c,a,false);}hd(d.c,ph(d.f,d.c,c));}
function li(b,a){b.d=a;}
function mi(b,a){b.e=a;mh(b.e);}
function ni(e,c,a,b){var d;ug(e,c,a);d=di(e,c,a,b===null);if(b!==null){md(d,b);}}
function oi(b,a){b.f=a;}
function pi(d,b,a,e){var c;ug(d,b,a);if(e!==null){dl(e);c=di(d,b,a,true);yh(d.h,e);rc(c,e.k);ej(d,e);}}
function qi(){return Bh(this.h);}
function ri(a){switch(Ec(a)){case 1:{break;}default:}}
function si(a){return ki(this,a);}
function Bg(){}
_=Bg.prototype=new dj();_.y=qi;_.B=ri;_.cb=si;_.tI=27;_.c=null;_.d=null;_.e=null;_.f=null;_.g=null;function qg(a){ai(a);li(a,fh(new eh(),a));oi(a,new nh());mi(a,kh(new jh(),a));return a;}
function rg(c,b,a){qg(c);yg(c,b,a);return c;}
function tg(b){var a;a=fi(b);md(a,'&nbsp;');return a;}
function ug(c,b,a){vg(c,b);if(a<0){throw dn(new cn(),'Cannot access a column with a negative index: '+a);}if(a>=c.a){throw dn(new cn(),'Column index: '+a+', Column size: '+c.a);}}
function vg(b,a){if(a<0){throw dn(new cn(),'Cannot access a row with a negative index: '+a);}if(a>=b.b){throw dn(new cn(),'Row index: '+a+', Row size: '+b.b);}}
function yg(c,b,a){wg(c,a);xg(c,b);}
function wg(d,a){var b,c;if(d.a==a){return;}if(a<0){throw dn(new cn(),'Cannot set number of columns to '+a);}if(d.a>a){for(b=0;b<d.b;b++){for(c=d.a-1;c>=a;c--){ii(d,b,c);}}}else{for(b=0;b<d.b;b++){for(c=d.a;c<a;c++){gi(d,b,c);}}}d.a=a;}
function xg(b,a){if(b.b==a){return;}if(a<0){throw dn(new cn(),'Cannot set number of rows to '+a);}if(b.b<a){zg(b.c,a-b.b,b.a);b.b=a;}else{while(b.b>a){ji(b,--b.b);}}}
function zg(g,f,c){var h=$doc.createElement('td');h.innerHTML='&nbsp;';var d=$doc.createElement('tr');for(var b=0;b<c;b++){var a=h.cloneNode(true);d.appendChild(a);}g.appendChild(d);for(var e=1;e<f;e++){g.appendChild(d.cloneNode(true));}}
function pg(){}
_=pg.prototype=new Bg();_.tI=28;_.a=0;_.b=0;function aj(a){a.db(uc());pk(a,131197);nk(a,'gwt-Label');return a;}
function cj(a){switch(Ec(a)){case 1:break;case 4:case 8:case 64:case 16:case 32:break;case 131072:break;}}
function Fi(){}
_=Fi.prototype=new rk();_.B=cj;_.tI=29;function ti(a){aj(a);a.db(uc());pk(a,125);nk(a,'gwt-HTML');return a;}
function ui(b,a){ti(b);wi(b,a);return b;}
function wi(b,a){md(b.k,a);}
function Ag(){}
_=Ag.prototype=new Fi();_.tI=30;function Dg(a){{ah(a);}}
function Eg(b,a){b.b=a;Dg(b);return b;}
function ah(a){while(++a.a<a.b.b.b){if(jq(a.b.b,a.a)!==null){return;}}}
function bh(a){return a.a<a.b.b.b;}
function ch(){return bh(this);}
function dh(){var a;if(!bh(this)){throw new ss();}a=jq(this.b.b,this.a);ah(this);return a;}
function Cg(){}
_=Cg.prototype=new hn();_.x=ch;_.A=dh;_.tI=0;_.a=(-1);function fh(b,a){b.a=a;return b;}
function hh(e,d,c,a){var b=d.rows[c].cells[a];return b==null?null:b;}
function ih(c,b,a){return hh(c,c.a.c,b,a);}
function eh(){}
_=eh.prototype=new hn();_.tI=0;function kh(b,a){b.b=a;return b;}
function mh(a){if(a.a===null){a.a=vc('colgroup');fd(a.b.g,a.a,0);rc(a.a,vc('col'));}}
function jh(){}
_=jh.prototype=new hn();_.tI=0;_.a=null;function ph(c,a,b){return a.rows[b];}
function nh(){}
_=nh.prototype=new hn();_.tI=0;function uh(a){a.b=fq(new dq());}
function vh(a){uh(a);return a;}
function xh(c,a){var b;b=Dh(a);if(b<0){return null;}return hc(jq(c.b,b),8);}
function yh(b,c){var a;if(b.a===null){a=b.b.b;gq(b.b,c);}else{a=b.a.a;nq(b.b,a,c);b.a=b.a.b;}Eh(c.k,a);}
function zh(c,a,b){Ch(a);nq(c.b,b,null);c.a=sh(new rh(),b,c.a);}
function Ah(c,a){var b;b=Dh(a);zh(c,a,b);}
function Bh(a){return Eg(new Cg(),a);}
function Ch(a){a['__widgetID']=null;}
function Dh(a){var b=a['__widgetID'];return b==null?-1:b;}
function Eh(a,b){a['__widgetID']=b;}
function qh(){}
_=qh.prototype=new hn();_.tI=0;_.a=null;function sh(c,a,b){c.a=a;c.b=b;return c;}
function rh(){}
_=rh.prototype=new hn();_.tI=0;_.a=0;_.b=null;function rj(){rj=ws;wj=vr(new Bq());}
function qj(b,a){rj();kf(b);if(a===null){a=sj();}b.db(a);bl(b);return b;}
function tj(){rj();return uj(null);}
function uj(c){rj();var a,b;b=hc(Br(wj,c),9);if(b!==null){return b;}a=null;if(wj.c==0){vj();}Cr(wj,c,b=qj(new lj(),a));return b;}
function sj(){rj();return $doc.body;}
function vj(){rj();ee(new mj());}
function lj(){}
_=lj.prototype=new jf();_.tI=31;var wj;function oj(){var a,b;for(b=kp(yp((rj(),wj)));rp(b);){a=hc(sp(b),9);if(a.i){cl(a);}}}
function pj(){return null;}
function mj(){}
_=mj.prototype=new hn();_.F=oj;_.ab=pj;_.tI=32;function bk(){bk=ws;yl(),Al;}
function ak(b,a){yl(),Al;ig(b,a);pk(b,1024);return b;}
function ck(a){return ad(a.k,'value');}
function dk(b,a){kd(b.k,'value',a!==null?a:'');}
function ek(a){if(this.a===null){this.a=Bf(new Af());}gq(this.a,a);}
function fk(a){var b;kg(this,a);b=Ec(a);if(b==1){if(this.a!==null){Df(this.a,this);}}else{}}
function Fj(){}
_=Fj.prototype=new hg();_.l=ek;_.B=fk;_.tI=33;_.a=null;function hk(){hk=ws;yl(),Al;}
function gk(a){yl(),Al;ak(a,wc());nk(a,'gwt-TextBox');return a;}
function Ej(){}
_=Ej.prototype=new Fj();_.tI=34;function yk(b,a){b.a=cc('[Lcom.google.gwt.user.client.ui.Widget;',[0],[8],[4],null);return b;}
function zk(a,b){Ck(a,b,a.b);}
function Bk(b,c){var a;for(a=0;a<b.b;++a){if(b.a[a]===c){return a;}}return (-1);}
function Ck(d,e,a){var b,c;if(a<0||a>d.b){throw new cn();}if(d.b==d.a.a){c=cc('[Lcom.google.gwt.user.client.ui.Widget;',[0],[8],[d.a.a*2],null);for(b=0;b<d.a.a;++b){dc(c,b,d.a[b]);}d.a=c;}++d.b;for(b=d.b-1;b>a;--b){dc(d.a,b,d.a[b-1]);}dc(d.a,a,e);}
function Dk(a){return uk(new tk(),a);}
function Ek(c,b){var a;if(b<0||b>=c.b){throw new cn();}--c.b;for(a=b;a<c.b;++a){dc(c.a,a,c.a[a+1]);}dc(c.a,c.b,null);}
function Fk(b,c){var a;a=Bk(b,c);if(a==(-1)){throw new ss();}Ek(b,a);}
function sk(){}
_=sk.prototype=new hn();_.tI=0;_.a=null;_.b=0;function uk(b,a){b.b=a;return b;}
function wk(){return this.a<this.b.b-1;}
function xk(){if(this.a>=this.b.b){throw new ss();}return this.b.a[++this.a];}
function tk(){}
_=tk.prototype=new hn();_.x=wk;_.A=xk;_.tI=0;_.a=(-1);function yl(){yl=ws;zl=ul(new tl());Al=zl!==null?xl(new ml()):zl;}
function xl(a){yl();return a;}
function ml(){}
_=ml.prototype=new hn();_.tI=0;var zl,Al;function ql(){ql=ws;yl();}
function ol(a){rl(a);sl(a);wl(a);}
function pl(a){ql();xl(a);ol(a);return a;}
function rl(b){return function(a){if(this.parentNode.onblur){this.parentNode.onblur(a);}};}
function sl(b){return function(a){if(this.parentNode.onfocus){this.parentNode.onfocus(a);}};}
function nl(){}
_=nl.prototype=new ml();_.tI=0;function vl(){vl=ws;ql();}
function ul(a){vl();pl(a);return a;}
function wl(b){return function(){var a=this.firstChild;$wnd.setTimeout(function(){a.focus();},0);};}
function tl(){}
_=tl.prototype=new nl();_.tI=0;function em(a){a.db(uc());return a;}
function gm(e){var a,b,c,d,f;if(e.d===null){return;}b='<object width="100%" height="100%"';b+='>';c=qr(Ar(e.d.d));while(jr(c)){a=kr(c);d=hc(a.t(),1);f=hc(a.u(),1);b+='<param name="'+d+'" value="'+f+'"/>';}b+='<embed height="100%" width="100%" type="'+e.d.c+'"'+'src="'+e.d.b+'"';if(e.d.a!==null){b+=' pluginspage="'+e.d.a+'"';}b+='><\/embed>';b+='<\/object>';md(e.k,b);}
function hm(b,a){im(b,a,true);}
function im(c,a,b){c.d=a;if(b){gm(c);}}
function Bl(){}
_=Bl.prototype=new rk();_.tI=35;_.d=null;function Dl(a){a.d=vr(new Bq());}
function El(c,d,a,b){Dl(c);c.b=a;dm(c,d);return c;}
function am(b,a,c){Cr(b.d,a,c);}
function bm(b,a){b.a=a;}
function cm(b,a){b.b=a;}
function dm(a,b){switch(b){case 0:a.c='application/x-shockwave-flash';break;default:throw Dm(new Cm(),'type invalid: Unrecognized type');}}
function Cl(){}
_=Cl.prototype=new hn();_.tI=0;_.a=null;_.b=null;_.c=null;function km(b,a){lm(b,a,false);return b;}
function lm(c,b,a){em(c);c.a=a;im(c,El(new Cl(),0,null,c),false);pm(c,b);return c;}
function nm(a){var b;b='http://www.youtube.com/v/'+a.b;if(a.a){b+='&autoplay=1';}if(a.c){b+='&rel=1';}else{b+='&rel=0';}return b;}
function om(b,a){b.a=a;}
function pm(c,b){var a,d;c.b=b;d=nm(c);a=c.d;cm(a,d);am(a,'movie',d);gm(c);}
function qm(b,a){b.c=a;}
function jm(){}
_=jm.prototype=new Bl();_.tI=36;_.a=false;_.b=null;_.c=false;function Bn(b,a){a;return b;}
function An(){}
_=An.prototype=new hn();_.tI=3;function Am(b,a){Bn(b,a);return b;}
function zm(){}
_=zm.prototype=new An();_.tI=4;function nn(b,a){Am(b,a);return b;}
function mn(){}
_=mn.prototype=new zm();_.tI=5;function sm(){}
_=sm.prototype=new mn();_.tI=37;function vm(){}
_=vm.prototype=new mn();_.tI=38;function Dm(b,a){nn(b,a);return b;}
function Cm(){}
_=Cm.prototype=new mn();_.tI=39;function an(b,a){nn(b,a);return b;}
function Fm(){}
_=Fm.prototype=new mn();_.tI=40;function dn(b,a){nn(b,a);return b;}
function cn(){}
_=cn.prototype=new mn();_.tI=41;function fn(){}
_=fn.prototype=new mn();_.tI=42;function rn(g){var a=vn;if(!a){a=vn={};}var e=':'+g;var b=a[e];if(b==null){b=0;var f=g.length;var d=f<64?1:f/32|0;for(var c=0;c<f;c+=d){b<<=1;b+=g.charCodeAt(c);}b|=0;a[e]=b;}return b;}
function sn(b,a){return b.substr(a,b.length-a);}
function tn(a,b){return String(a)==b;}
function un(a){if(!ic(a,1))return false;return tn(this,a);}
function wn(){return rn(this);}
_=String.prototype;_.eQ=un;_.hC=wn;_.tI=2;var vn=null;function zn(a){return s(a);}
function En(b,a){nn(b,a);return b;}
function Dn(){}
_=Dn.prototype=new mn();_.tI=43;function io(b,a){b.c=a;return b;}
function ko(a){return a.a<a.c.fb();}
function lo(a){if(!ko(a)){throw new ss();}return a.c.v(a.b=a.a++);}
function mo(a){if(a.b<0){throw new Fm();}a.c.bb(a.b);a.a=a.b;a.b=(-1);}
function no(){return ko(this);}
function oo(){return lo(this);}
function ho(){}
_=ho.prototype=new hn();_.x=no;_.A=oo;_.tI=0;_.a=0;_.b=(-1);function wp(f,d,e){var a,b,c;for(b=qr(f.s());jr(b);){a=kr(b);c=a.t();if(d===null?c===null:d.eQ(c)){if(e){lr(b);}return a;}}return null;}
function xp(b){var a;a=b.s();return Ao(new zo(),b,a);}
function yp(b){var a;a=Ar(b);return ip(new hp(),b,a);}
function zp(a){return wp(this,a,false)!==null;}
function Ap(d){var a,b,c,e,f,g,h;if(d===this){return true;}if(!ic(d,14)){return false;}f=hc(d,14);c=xp(this);e=f.z();if(!aq(c,e)){return false;}for(a=Co(c);dp(a);){b=ep(a);h=this.w(b);g=f.w(b);if(h===null?g!==null:!h.eQ(g)){return false;}}return true;}
function Bp(b){var a;a=wp(this,b,false);return a===null?null:a.u();}
function Cp(){var a,b,c;b=0;for(c=qr(this.s());jr(c);){a=kr(c);b+=a.hC();}return b;}
function Dp(){return xp(this);}
function yo(){}
_=yo.prototype=new hn();_.o=zp;_.eQ=Ap;_.w=Bp;_.hC=Cp;_.z=Dp;_.tI=44;function aq(e,b){var a,c,d;if(b===e){return true;}if(!ic(b,15)){return false;}c=hc(b,15);if(c.fb()!=e.fb()){return false;}for(a=c.y();a.x();){d=a.A();if(!e.p(d)){return false;}}return true;}
function bq(a){return aq(this,a);}
function cq(){var a,b,c;a=0;for(b=this.y();b.x();){c=b.A();if(c!==null){a+=c.hC();}}return a;}
function Ep(){}
_=Ep.prototype=new ao();_.eQ=bq;_.hC=cq;_.tI=45;function Ao(b,a,c){b.a=a;b.b=c;return b;}
function Co(b){var a;a=qr(b.b);return bp(new ap(),b,a);}
function Do(a){return this.a.o(a);}
function Eo(){return Co(this);}
function Fo(){return this.b.a.c;}
function zo(){}
_=zo.prototype=new Ep();_.p=Do;_.y=Eo;_.fb=Fo;_.tI=46;function bp(b,a,c){b.a=c;return b;}
function dp(a){return a.a.x();}
function ep(b){var a;a=b.a.A();return a.t();}
function fp(){return dp(this);}
function gp(){return ep(this);}
function ap(){}
_=ap.prototype=new hn();_.x=fp;_.A=gp;_.tI=0;function ip(b,a,c){b.a=a;b.b=c;return b;}
function kp(b){var a;a=qr(b.b);return pp(new op(),b,a);}
function lp(a){return zr(this.a,a);}
function mp(){return kp(this);}
function np(){return this.b.a.c;}
function hp(){}
_=hp.prototype=new ao();_.p=lp;_.y=mp;_.fb=np;_.tI=0;function pp(b,a,c){b.a=c;return b;}
function rp(a){return a.a.x();}
function sp(a){var b;b=a.a.A().u();return b;}
function tp(){return rp(this);}
function up(){return sp(this);}
function op(){}
_=op.prototype=new hn();_.x=tp;_.A=up;_.tI=0;function xr(){xr=ws;Er=es();}
function ur(a){{wr(a);}}
function vr(a){xr();ur(a);return a;}
function wr(a){a.a=z();a.d=A();a.b=mc(Er,v);a.c=0;}
function yr(b,a){if(ic(a,1)){return is(b.d,hc(a,1))!==Er;}else if(a===null){return b.b!==Er;}else{return hs(b.a,a,a.hC())!==Er;}}
function zr(a,b){if(a.b!==Er&&gs(a.b,b)){return true;}else if(ds(a.d,b)){return true;}else if(bs(a.a,b)){return true;}return false;}
function Ar(a){return or(new fr(),a);}
function Br(c,a){var b;if(ic(a,1)){b=is(c.d,hc(a,1));}else if(a===null){b=c.b;}else{b=hs(c.a,a,a.hC());}return b===Er?null:b;}
function Cr(c,a,d){var b;if(a!==null){b=ls(c.d,a,d);}else if(a===null){b=c.b;c.b=d;}else{b=ks(c.a,a,d,rn(a));}if(b===Er){++c.c;return null;}else{return b;}}
function Dr(c,a){var b;if(ic(a,1)){b=ns(c.d,hc(a,1));}else if(a===null){b=c.b;c.b=mc(Er,v);}else{b=ms(c.a,a,a.hC());}if(b===Er){return null;}else{--c.c;return b;}}
function Fr(e,c){xr();for(var d in e){if(d==parseInt(d)){var a=e[d];for(var f=0,b=a.length;f<b;++f){c.n(a[f]);}}}}
function as(d,a){xr();for(var c in d){if(c.charCodeAt(0)==58){var e=d[c];var b=Fq(c.substring(1),e);a.n(b);}}}
function bs(f,h){xr();for(var e in f){if(e==parseInt(e)){var a=f[e];for(var g=0,b=a.length;g<b;++g){var c=a[g];var d=c.u();if(gs(h,d)){return true;}}}}return false;}
function cs(a){return yr(this,a);}
function ds(c,d){xr();for(var b in c){if(b.charCodeAt(0)==58){var a=c[b];if(gs(d,a)){return true;}}}return false;}
function es(){xr();}
function fs(){return Ar(this);}
function gs(a,b){xr();if(a===b){return true;}else if(a===null){return false;}else{return a.eQ(b);}}
function js(a){return Br(this,a);}
function hs(f,h,e){xr();var a=f[e];if(a){for(var g=0,b=a.length;g<b;++g){var c=a[g];var d=c.t();if(gs(h,d)){return c.u();}}}}
function is(b,a){xr();return b[':'+a];}
function ks(f,h,j,e){xr();var a=f[e];if(a){for(var g=0,b=a.length;g<b;++g){var c=a[g];var d=c.t();if(gs(h,d)){var i=c.u();c.eb(j);return i;}}}else{a=f[e]=[];}var c=Fq(h,j);a.push(c);}
function ls(c,a,d){xr();a=':'+a;var b=c[a];c[a]=d;return b;}
function ms(f,h,e){xr();var a=f[e];if(a){for(var g=0,b=a.length;g<b;++g){var c=a[g];var d=c.t();if(gs(h,d)){if(a.length==1){delete f[e];}else{a.splice(g,1);}return c.u();}}}}
function ns(c,a){xr();a=':'+a;var b=c[a];delete c[a];return b;}
function Bq(){}
_=Bq.prototype=new yo();_.o=cs;_.s=fs;_.w=js;_.tI=47;_.a=null;_.b=null;_.c=0;_.d=null;var Er;function Dq(b,a,c){b.a=a;b.b=c;return b;}
function Fq(a,b){return Dq(new Cq(),a,b);}
function ar(b){var a;if(ic(b,16)){a=hc(b,16);if(gs(this.a,a.t())&&gs(this.b,a.u())){return true;}}return false;}
function br(){return this.a;}
function cr(){return this.b;}
function dr(){var a,b;a=0;b=0;if(this.a!==null){a=this.a.hC();}if(this.b!==null){b=this.b.hC();}return a^b;}
function er(a){var b;b=this.b;this.b=a;return b;}
function Cq(){}
_=Cq.prototype=new hn();_.eQ=ar;_.t=br;_.u=cr;_.hC=dr;_.eb=er;_.tI=48;_.a=null;_.b=null;function or(b,a){b.a=a;return b;}
function qr(a){return hr(new gr(),a.a);}
function rr(c){var a,b,d;if(ic(c,16)){a=hc(c,16);b=a.t();if(yr(this.a,b)){d=Br(this.a,b);return gs(a.u(),d);}}return false;}
function sr(){return qr(this);}
function tr(){return this.a.c;}
function fr(){}
_=fr.prototype=new Ep();_.p=rr;_.y=sr;_.fb=tr;_.tI=49;function hr(c,b){var a;c.c=b;a=fq(new dq());if(c.c.b!==(xr(),Er)){gq(a,Dq(new Cq(),null,c.c.b));}as(c.c.d,a);Fr(c.c.a,a);c.a=ro(a);return c;}
function jr(a){return ko(a.a);}
function kr(a){return a.b=hc(lo(a.a),16);}
function lr(a){if(a.b===null){throw an(new Fm(),'Must call next() before remove().');}else{mo(a.a);Dr(a.c,a.b.t());a.b=null;}}
function mr(){return jr(this);}
function nr(){return kr(this);}
function gr(){}
_=gr.prototype=new hn();_.x=mr;_.A=nr;_.tI=0;_.a=null;_.b=null;function ss(){}
_=ss.prototype=new mn();_.tI=50;function rm(){Ab(yb(new E()));}
function gwtOnLoad(b,d,c){$moduleName=d;$moduleBase=c;if(b)try{rm();}catch(a){b(d);}else{rm();}}
var lc=[{},{},{1:1},{4:1},{4:1},{4:1},{4:1},{2:1},{7:1},{7:1},{7:1},{7:1},{7:1},{7:1},{2:1,5:1},{2:1},{6:1},{8:1,10:1,11:1,12:1},{8:1,10:1,11:1,12:1},{8:1,10:1,11:1,12:1},{8:1,10:1,11:1,12:1},{8:1,10:1,11:1,12:1},{8:1,10:1,11:1,12:1},{3:1,8:1,10:1,11:1,12:1},{13:1},{13:1},{13:1},{8:1,10:1,11:1,12:1},{8:1,10:1,11:1,12:1},{8:1,10:1,11:1,12:1},{8:1,10:1,11:1,12:1},{8:1,9:1,10:1,11:1,12:1},{6:1},{8:1,10:1,11:1,12:1},{8:1,10:1,11:1,12:1},{8:1,10:1,11:1,12:1},{8:1,10:1,11:1,12:1},{4:1},{4:1},{4:1},{4:1},{4:1},{4:1},{4:1},{14:1},{15:1},{15:1},{14:1},{16:1},{15:1},{4:1}];if (com_google_gwt_demos_youtubeviewer_YouTubeViewerDemo) {  var __gwt_initHandlers = com_google_gwt_demos_youtubeviewer_YouTubeViewerDemo.__gwt_initHandlers;  com_google_gwt_demos_youtubeviewer_YouTubeViewerDemo.onScriptLoad(gwtOnLoad);}})();