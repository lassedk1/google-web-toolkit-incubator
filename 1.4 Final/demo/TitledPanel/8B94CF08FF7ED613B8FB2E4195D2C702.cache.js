(function(){var $wnd = window;var $doc = $wnd.document;var $moduleName, $moduleBase;var _,ks='com.google.gwt.core.client.',ls='com.google.gwt.demos.titledpanel.client.',ms='com.google.gwt.lang.',ns='com.google.gwt.user.client.',os='com.google.gwt.user.client.impl.',ps='com.google.gwt.user.client.ui.',qs='com.google.gwt.user.client.ui.impl.',rs='com.google.gwt.widgetideas.client.',ss='java.lang.',ts='java.util.';function js(){}
function ym(a){return this===a;}
function zm(){return gn(this);}
function wm(){}
_=wm.prototype={};_.eQ=ym;_.hC=zm;_.tI=1;var o=null;function r(a){return a==null?0:a.$H?a.$H:(a.$H=t());}
function s(a){return a==null?0:a.$H?a.$H:(a.$H=t());}
function t(){return ++u;}
var u=0;function x(b,a){if(!Db(a,2)){return false;}return B(b,Cb(a,2));}
function y(a){return r(a);}
function z(){return [];}
function A(){return {};}
function C(a){return x(this,a);}
function B(a,b){return a===b;}
function D(){return y(this);}
function v(){}
_=v.prototype=new wm();_.eQ=C;_.hC=D;_.tI=7;function mb(b){var a,c;b.a=Fl(new yl(),'Hello World');vk(b.a,'500px');nb(b,pi(new tg(),'This is a widget in the TitledPanel'));ef(oj(),b.a);a=kg(new ig(),3,3);ei(a,1);gi(a,3);ef(oj(),pi(new tg(),'<BR><BR>'));ef(oj(),a);c=ok(new gk());lk(c,b.a.b);ki(a,0,0,qf(new jf(),'Set Title',ab(new F(),b,c)));ki(a,0,1,c);ii(a,0,2,'Change the title dynamically');ki(a,1,0,qf(new jf(),'Clear Title',eb(new db(),b)));ii(a,1,1,'<BR>');ii(a,1,2,'Clear out the title of the box.  Note that this is different from setting the title to an empty string.  When you set the title to an empty string, you will see a small gap where the text would normally go.  Clearing the title effectively creates a bordered widget.');ii(a,2,1,'<BR>');ii(a,2,2,'The TitledPanel is a type of SimplePanel, so you can add any widget to it.');ki(a,2,0,qf(new jf(),'Set Widget',ib(new hb(),b)));}
function nb(b,a){Cj(b.a,a);uk(a,'titledPanelDemoContents');}
function E(){}
_=E.prototype=new wm();_.tI=0;_.a=null;function ab(b,a,c){b.a=a;b.b=c;return b;}
function cb(a){bm(this.a.a,kk(this.b));}
function F(){}
_=F.prototype=new wm();_.C=cb;_.tI=8;function eb(b,a){b.a=a;return b;}
function gb(a){bm(this.a.a,null);}
function db(){}
_=db.prototype=new wm();_.C=gb;_.tI=9;function ib(b,a){b.a=a;return b;}
function kb(c){var a,b;a=jq(new iq());b=pi(new tg(),'This is a new widget created on:<BR> '+nq(a));nb(this.a,b);}
function hb(){}
_=hb.prototype=new wm();_.C=kb;_.tI=10;function pb(c,a,d,b,e){c.a=a;c.b=b;e;c.tI=d;return c;}
function rb(a,b,c){return a[b]=c;}
function sb(b,a){return b[a];}
function ub(b,a){return b[a];}
function tb(a){return a.length;}
function wb(e,d,c,b,a){return vb(e,d,c,b,0,tb(b),a);}
function vb(j,i,g,c,e,a,b){var d,f,h;if((f=sb(c,e))<0){throw new um();}h=pb(new ob(),f,sb(i,e),sb(g,e),j);++e;if(e<a){j=Fm(j,1);for(d=0;d<f;++d){rb(h,d,vb(j,i,g,c,e,a,b));}}else{for(d=0;d<f;++d){rb(h,d,b);}}return h;}
function xb(f,e,c,g){var a,b,d;b=tb(g);d=pb(new ob(),b,e,c,f);for(a=0;a<b;++a){rb(d,a,ub(g,a));}return d;}
function yb(a,b,c){if(c!==null&&a.b!=0&& !Db(c,a.b)){throw new em();}return rb(a,b,c);}
function ob(){}
_=ob.prototype=new wm();_.tI=0;function Bb(b,a){return !(!(b&&bc[b][a]));}
function Cb(b,a){if(b!=null)Bb(b.tI,a)||ac();return b;}
function Db(b,a){return b!=null&&Bb(b.tI,a);}
function Eb(a){return ~(~a);}
function ac(){throw new hm();}
function Fb(a){if(a!==null){throw new hm();}return a;}
function cc(b,d){_=d.prototype;if(b&& !(b.tI>=_.tI)){var c=b.toString;for(var a in _){b[a]=_[a];}b.toString=c;}return b;}
var bc;function gc(){gc=js;Fc=tp(new rp());{Ac=new fe();je(Ac);}}
function hc(b,a){gc();ue(Ac,b,a);}
function ic(a,b){gc();return he(Ac,a,b);}
function jc(){gc();return we(Ac,'button');}
function kc(){gc();return we(Ac,'div');}
function lc(a){gc();return we(Ac,a);}
function mc(){gc();return xe(Ac,'text');}
function nc(){gc();return we(Ac,'tbody');}
function oc(){gc();return we(Ac,'td');}
function pc(){gc();return we(Ac,'table');}
function sc(b,a,d){gc();var c;c=o;{rc(b,a,d);}}
function rc(b,a,c){gc();var d;if(a===Ec){if(uc(b)==8192){Ec=null;}}d=qc;qc=b;try{c.B(b);}finally{qc=d;}}
function tc(b,a){gc();ye(Ac,b,a);}
function uc(a){gc();return ze(Ac,a);}
function vc(a){gc();oe(Ac,a);}
function wc(a,b){gc();return Ae(Ac,a,b);}
function xc(a){gc();return Be(Ac,a);}
function yc(a){gc();return pe(Ac,a);}
function zc(a){gc();return qe(Ac,a);}
function Bc(c,a,b){gc();se(Ac,c,a,b);}
function Cc(a){gc();var b,c;c=true;if(Fc.b>0){b=Fb(xp(Fc,Fc.b-1));if(!(c=null.gb())){tc(a,true);vc(a);}}return c;}
function Dc(b,a){gc();Ce(Ac,b,a);}
function bd(a,b,c){gc();Ee(Ac,a,b,c);}
function ad(a,b,c){gc();De(Ac,a,b,c);}
function cd(a,b){gc();Fe(Ac,a,b);}
function dd(a,b){gc();af(Ac,a,b);}
function ed(b,a,c){gc();bf(Ac,b,a,c);}
function fd(a,b){gc();le(Ac,a,b);}
var qc=null,Ac=null,Ec=null,Fc;function id(a){if(Db(a,4)){return ic(this,Cb(a,4));}return x(cc(this,gd),a);}
function jd(){return y(cc(this,gd));}
function gd(){}
_=gd.prototype=new v();_.eQ=id;_.hC=jd;_.tI=11;function nd(a){return x(cc(this,kd),a);}
function od(){return y(cc(this,kd));}
function kd(){}
_=kd.prototype=new v();_.eQ=nd;_.hC=od;_.tI=12;function ud(){ud=js;wd=tp(new rp());{vd();}}
function vd(){ud();Ad(new qd());}
var wd;function sd(){while((ud(),wd).b>0){Fb(xp((ud(),wd),0)).gb();}}
function td(){return null;}
function qd(){}
_=qd.prototype=new wm();_.F=sd;_.ab=td;_.tI=13;function zd(){zd=js;Bd=tp(new rp());de=tp(new rp());{Fd();}}
function Ad(a){zd();up(Bd,a);}
function Cd(){zd();var a,b;for(a=En(Bd);xn(a);){b=Cb(yn(a),5);b.F();}}
function Dd(){zd();var a,b,c,d;d=null;for(a=En(Bd);xn(a);){b=Cb(yn(a),5);c=b.ab();{d=c;}}return d;}
function Ed(){zd();var a,b;for(a=En(de);xn(a);){b=Fb(yn(a));null.gb();}}
function Fd(){zd();__gwt_initHandlers(function(){ce();},function(){return be();},function(){ae();$wnd.onresize=null;$wnd.onbeforeclose=null;$wnd.onclose=null;});}
function ae(){zd();var a;a=o;{Cd();}}
function be(){zd();var a;a=o;{return Dd();}}
function ce(){zd();var a;a=o;{Ed();}}
var Bd,de;function ue(c,b,a){b.appendChild(a);}
function we(b,a){return $doc.createElement(a);}
function xe(b,c){var a=$doc.createElement('INPUT');a.type=c;return a;}
function ye(c,b,a){b.cancelBubble=a;}
function ze(b,a){switch(a.type){case 'blur':return 4096;case 'change':return 1024;case 'click':return 1;case 'dblclick':return 2;case 'focus':return 2048;case 'keydown':return 128;case 'keypress':return 256;case 'keyup':return 512;case 'load':return 32768;case 'losecapture':return 8192;case 'mousedown':return 4;case 'mousemove':return 64;case 'mouseout':return 32;case 'mouseover':return 16;case 'mouseup':return 8;case 'scroll':return 16384;case 'error':return 65536;case 'mousewheel':return 131072;case 'DOMMouseScroll':return 131072;}}
function Ae(d,a,b){var c=a[b];return c==null?null:String(c);}
function Be(b,a){return a.__eventBits||0;}
function Ce(c,b,a){b.removeChild(a);}
function Ee(c,a,b,d){a[b]=d;}
function De(c,a,b,d){a[b]=d;}
function Fe(c,a,b){a.__listener=b;}
function af(c,a,b){if(!b){b='';}a.innerHTML=b;}
function bf(c,b,a,d){b.style[a]=d;}
function ee(){}
_=ee.prototype=new wm();_.tI=0;function oe(b,a){a.preventDefault();}
function pe(c,b){var a=b.firstChild;while(a&&a.nodeType!=1)a=a.nextSibling;return a||null;}
function qe(c,a){var b=a.parentNode;if(b==null){return null;}if(b.nodeType!=1)b=null;return b||null;}
function re(d){$wnd.__dispatchCapturedMouseEvent=function(b){if($wnd.__dispatchCapturedEvent(b)){var a=$wnd.__captureElem;if(a&&a.__listener){sc(b,a,a.__listener);b.stopPropagation();}}};$wnd.__dispatchCapturedEvent=function(a){if(!Cc(a)){a.stopPropagation();a.preventDefault();return false;}return true;};$wnd.addEventListener('click',$wnd.__dispatchCapturedMouseEvent,true);$wnd.addEventListener('dblclick',$wnd.__dispatchCapturedMouseEvent,true);$wnd.addEventListener('mousedown',$wnd.__dispatchCapturedMouseEvent,true);$wnd.addEventListener('mouseup',$wnd.__dispatchCapturedMouseEvent,true);$wnd.addEventListener('mousemove',$wnd.__dispatchCapturedMouseEvent,true);$wnd.addEventListener('mousewheel',$wnd.__dispatchCapturedMouseEvent,true);$wnd.addEventListener('keydown',$wnd.__dispatchCapturedEvent,true);$wnd.addEventListener('keyup',$wnd.__dispatchCapturedEvent,true);$wnd.addEventListener('keypress',$wnd.__dispatchCapturedEvent,true);$wnd.__dispatchEvent=function(b){var c,a=this;while(a&& !(c=a.__listener))a=a.parentNode;if(a&&a.nodeType!=1)a=null;if(c)sc(b,a,c);};$wnd.__captureElem=null;}
function se(f,e,g,d){var c=0,b=e.firstChild,a=null;while(b){if(b.nodeType==1){if(c==d){a=b;break;}++c;}b=b.nextSibling;}e.insertBefore(g,a);}
function te(c,b,a){b.__eventBits=a;b.onclick=a&1?$wnd.__dispatchEvent:null;b.ondblclick=a&2?$wnd.__dispatchEvent:null;b.onmousedown=a&4?$wnd.__dispatchEvent:null;b.onmouseup=a&8?$wnd.__dispatchEvent:null;b.onmouseover=a&16?$wnd.__dispatchEvent:null;b.onmouseout=a&32?$wnd.__dispatchEvent:null;b.onmousemove=a&64?$wnd.__dispatchEvent:null;b.onkeydown=a&128?$wnd.__dispatchEvent:null;b.onkeypress=a&256?$wnd.__dispatchEvent:null;b.onkeyup=a&512?$wnd.__dispatchEvent:null;b.onchange=a&1024?$wnd.__dispatchEvent:null;b.onfocus=a&2048?$wnd.__dispatchEvent:null;b.onblur=a&4096?$wnd.__dispatchEvent:null;b.onlosecapture=a&8192?$wnd.__dispatchEvent:null;b.onscroll=a&16384?$wnd.__dispatchEvent:null;b.onload=a&32768?$wnd.__dispatchEvent:null;b.onerror=a&65536?$wnd.__dispatchEvent:null;b.onmousewheel=a&131072?$wnd.__dispatchEvent:null;}
function me(){}
_=me.prototype=new ee();_.tI=0;function he(c,a,b){if(!a&& !b){return true;}else if(!a|| !b){return false;}return a.isSameNode(b);}
function je(a){re(a);ie(a);}
function ie(d){$wnd.addEventListener('mouseout',function(b){var a=$wnd.__captureElem;if(a&& !b.relatedTarget){if('html'==b.target.tagName.toLowerCase()){var c=$doc.createEvent('MouseEvents');c.initMouseEvent('mouseup',true,true,$wnd,0,b.screenX,b.screenY,b.clientX,b.clientY,b.ctrlKey,b.altKey,b.shiftKey,b.metaKey,b.button,null);a.dispatchEvent(c);}}},true);$wnd.addEventListener('DOMMouseScroll',$wnd.__dispatchCapturedMouseEvent,true);}
function le(c,b,a){te(c,b,a);ke(c,b,a);}
function ke(c,b,a){if(a&131072){b.addEventListener('DOMMouseScroll',$wnd.__dispatchEvent,false);}}
function fe(){}
_=fe.prototype=new me();_.tI=0;function sk(d,b,a){var c=b.parentNode;if(!c){return;}c.insertBefore(a,b);c.removeChild(b);}
function tk(b,a){if(b.k!==null){sk(b,b.k,a);}b.k=a;}
function uk(b,a){xk(b.k,a);}
function vk(a,b){ed(a.k,'width',b);}
function wk(b,a){fd(b.k,a|xc(b.k));}
function xk(a,b){bd(a,'className',b);}
function qk(){}
_=qk.prototype=new wm();_.tI=0;_.k=null;function il(a){if(a.i){throw pm(new om(),"Should only call onAttach when the widget is detached from the browser's document");}a.i=true;cd(a.k,a);a.q();a.D();}
function jl(a){if(!a.i){throw pm(new om(),"Should only call onDetach when the widget is attached to the browser's document");}try{a.E();}finally{a.r();cd(a.k,null);a.i=false;}}
function kl(a){if(a.j!==null){a.j.cb(a);}else if(a.j!==null){throw pm(new om(),"This widget's parent does not implement HasWidgets");}}
function ll(b,a){if(b.i){cd(b.k,null);}tk(b,a);if(b.i){cd(a,b);}}
function ml(c,b){var a;a=c.j;if(b===null){if(a!==null&&a.i){jl(c);}c.j=null;}else{if(a!==null){throw pm(new om(),'Cannot set a new parent without first clearing the old parent');}c.j=b;if(b.i){il(c);}}}
function nl(){}
function ol(){}
function pl(a){}
function ql(){}
function rl(){}
function sl(a){ll(this,a);}
function yk(){}
_=yk.prototype=new qk();_.q=nl;_.r=ol;_.B=pl;_.D=ql;_.E=rl;_.db=sl;_.tI=14;_.i=false;_.j=null;function Fi(b,a){ml(a,b);}
function bj(b,a){ml(a,null);}
function cj(){var a,b;for(b=this.y();b.x();){a=Cb(b.A(),7);il(a);}}
function dj(){var a,b;for(b=this.y();b.x();){a=Cb(b.A(),7);jl(a);}}
function ej(){}
function fj(){}
function Ei(){}
_=Ei.prototype=new yk();_.q=cj;_.r=dj;_.D=ej;_.E=fj;_.tI=15;function zf(a){a.a=Fk(new zk(),a);}
function Af(a){zf(a);return a;}
function Bf(c,a,b){kl(a);al(c.a,a);hc(b,a.k);Fi(c,a);}
function Df(b,c){var a;if(c.j!==b){return false;}bj(b,c);a=c.k;Dc(zc(a),a);gl(b.a,c);return true;}
function Ef(){return el(this.a);}
function Ff(a){return Df(this,a);}
function yf(){}
_=yf.prototype=new Ei();_.y=Ef;_.cb=Ff;_.tI=16;function df(a){Af(a);a.db(kc());ed(a.k,'position','relative');ed(a.k,'overflow','hidden');return a;}
function ef(a,b){Bf(a,b,a.k);}
function gf(a){ed(a,'left','');ed(a,'top','');ed(a,'position','');}
function hf(b){var a;a=Df(this,b);if(a){gf(b.k);}return a;}
function cf(){}
_=cf.prototype=new yf();_.cb=hf;_.tI=17;function cg(){cg=js;vl(),xl;}
function bg(b,a){vl(),xl;eg(b,a);return b;}
function dg(b,a){switch(uc(a)){case 1:if(b.b!==null){wf(b.b,b);}break;case 4096:case 2048:break;case 128:case 512:case 256:break;}}
function eg(b,a){ll(b,a);wk(b,7041);}
function fg(a){if(this.b===null){this.b=uf(new tf());}up(this.b,a);}
function gg(a){dg(this,a);}
function hg(a){eg(this,a);}
function ag(){}
_=ag.prototype=new yk();_.l=fg;_.B=gg;_.db=hg;_.tI=18;_.b=null;function mf(){mf=js;vl(),xl;}
function lf(b,a){vl(),xl;bg(b,a);return b;}
function nf(b,a){dd(b.k,a);}
function kf(){}
_=kf.prototype=new ag();_.tI=19;function rf(){rf=js;vl(),xl;}
function of(a){vl(),xl;lf(a,jc());sf(a.k);uk(a,'gwt-Button');return a;}
function pf(b,a){vl(),xl;of(b);nf(b,a);return b;}
function qf(c,a,b){vl(),xl;pf(c,a);c.l(b);return c;}
function sf(b){rf();if(b.type=='submit'){try{b.setAttribute('type','button');}catch(a){}}}
function jf(){}
_=jf.prototype=new kf();_.tI=20;function pn(d,a,b){var c;while(a.x()){c=a.A();if(b===null?c===null:b.eQ(c)){return a;}}return null;}
function rn(a){throw mn(new ln(),'add');}
function sn(b){var a;a=pn(this,this.y(),b);return a!==null;}
function on(){}
_=on.prototype=new wm();_.n=rn;_.p=sn;_.tI=0;function Dn(b,a){throw sm(new rm(),'Index: '+a+', Size: '+b.b);}
function En(a){return vn(new un(),a);}
function Fn(b,a){throw mn(new ln(),'add');}
function ao(a){this.m(this.eb(),a);return true;}
function bo(e){var a,b,c,d,f;if(e===this){return true;}if(!Db(e,12)){return false;}f=Cb(e,12);if(this.eb()!=f.eb()){return false;}c=En(this);d=f.y();while(xn(c)){a=yn(c);b=yn(d);if(!(a===null?b===null:a.eQ(b))){return false;}}return true;}
function co(){var a,b,c,d;c=1;a=31;b=En(this);while(xn(b)){d=yn(b);c=31*c+(d===null?0:d.hC());}return c;}
function eo(){return En(this);}
function fo(a){throw mn(new ln(),'remove');}
function tn(){}
_=tn.prototype=new on();_.m=Fn;_.n=ao;_.eQ=bo;_.hC=co;_.y=eo;_.bb=fo;_.tI=21;function sp(a){{vp(a);}}
function tp(a){sp(a);return a;}
function up(b,a){fq(b.a,b.b++,a);return true;}
function vp(a){a.a=z();a.b=0;}
function xp(b,a){if(a<0||a>=b.b){Dn(b,a);}return bq(b.a,a);}
function yp(b,a){return zp(b,a,0);}
function zp(c,b,a){if(a<0){Dn(c,a);}for(;a<c.b;++a){if(aq(b,bq(c.a,a))){return a;}}return (-1);}
function Ap(c,a){var b;b=xp(c,a);dq(c.a,a,1);--c.b;return b;}
function Bp(d,a,b){var c;c=xp(d,a);fq(d.a,a,b);return c;}
function Dp(a,b){if(a<0||a>this.b){Dn(this,a);}Cp(this.a,a,b);++this.b;}
function Ep(a){return up(this,a);}
function Cp(a,b,c){a.splice(b,0,c);}
function Fp(a){return yp(this,a)!=(-1);}
function aq(a,b){return a===b||a!==null&&a.eQ(b);}
function cq(a){return xp(this,a);}
function bq(a,b){return a[b];}
function eq(a){return Ap(this,a);}
function dq(a,c,b){a.splice(c,b);}
function fq(a,b,c){a[b]=c;}
function gq(){return this.b;}
function rp(){}
_=rp.prototype=new tn();_.m=Dp;_.n=Ep;_.p=Fp;_.v=cq;_.bb=eq;_.eb=gq;_.tI=22;_.a=null;_.b=0;function uf(a){tp(a);return a;}
function wf(d,c){var a,b;for(a=En(d);xn(a);){b=Cb(yn(a),6);b.C(c);}}
function tf(){}
_=tf.prototype=new rp();_.tI=23;function yh(a){a.h=oh(new jh());}
function zh(a){yh(a);a.g=pc();a.c=nc();hc(a.g,a.c);a.db(a.g);wk(a,1);return a;}
function Ah(d,c,b){var a;Bh(d,c);if(b<0){throw sm(new rm(),'Column '+b+' must be non-negative: '+b);}a=d.a;if(a<=b){throw sm(new rm(),'Column index: '+b+', Column size: '+d.a);}}
function Bh(c,a){var b;b=c.b;if(a>=b||a<0){throw sm(new rm(),'Row index: '+a+', Row size: '+b);}}
function Ch(e,c,b,a){var d;d=bh(e.d,c,b);ai(e,d,a);return d;}
function Eh(a){return oc();}
function Fh(d,b,a){var c,e;e=ih(d.f,d.c,b);c=mg(d);Bc(e,c,a);}
function ai(d,c,a){var b,e;b=yc(c);e=null;if(b!==null){e=qh(d.h,b);}if(e!==null){di(d,e);return true;}else{if(a){dd(c,'');}return false;}}
function di(b,c){var a;if(c.j!==b){return false;}bj(b,c);a=c.k;Dc(zc(a),a);th(b.h,a);return true;}
function bi(d,b,a){var c,e;Ah(d,b,a);c=Ch(d,b,a,false);e=ih(d.f,d.c,b);Dc(e,c);}
function ci(d,c){var a,b;b=d.a;for(a=0;a<b;++a){Ch(d,c,a,false);}Dc(d.c,ih(d.f,d.c,c));}
function ei(a,b){bd(a.g,'border',''+b);}
function fi(b,a){b.d=a;}
function gi(b,a){ad(b.g,'cellPadding',a);}
function hi(b,a){b.e=a;fh(b.e);}
function ii(e,c,a,b){var d;ng(e,c,a);d=Ch(e,c,a,b===null);if(b!==null){dd(d,b);}}
function ji(b,a){b.f=a;}
function ki(d,b,a,e){var c;ng(d,b,a);if(e!==null){kl(e);c=Ch(d,b,a,true);rh(d.h,e);hc(c,e.k);Fi(d,e);}}
function li(){return uh(this.h);}
function mi(a){switch(uc(a)){case 1:{break;}default:}}
function ni(a){return di(this,a);}
function ug(){}
_=ug.prototype=new Ei();_.y=li;_.B=mi;_.cb=ni;_.tI=24;_.c=null;_.d=null;_.e=null;_.f=null;_.g=null;function jg(a){zh(a);fi(a,Eg(new Dg(),a));ji(a,new gh());hi(a,dh(new ch(),a));return a;}
function kg(c,b,a){jg(c);rg(c,b,a);return c;}
function mg(b){var a;a=Eh(b);dd(a,'&nbsp;');return a;}
function ng(c,b,a){og(c,b);if(a<0){throw sm(new rm(),'Cannot access a column with a negative index: '+a);}if(a>=c.a){throw sm(new rm(),'Column index: '+a+', Column size: '+c.a);}}
function og(b,a){if(a<0){throw sm(new rm(),'Cannot access a row with a negative index: '+a);}if(a>=b.b){throw sm(new rm(),'Row index: '+a+', Row size: '+b.b);}}
function rg(c,b,a){pg(c,a);qg(c,b);}
function pg(d,a){var b,c;if(d.a==a){return;}if(a<0){throw sm(new rm(),'Cannot set number of columns to '+a);}if(d.a>a){for(b=0;b<d.b;b++){for(c=d.a-1;c>=a;c--){bi(d,b,c);}}}else{for(b=0;b<d.b;b++){for(c=d.a;c<a;c++){Fh(d,b,c);}}}d.a=a;}
function qg(b,a){if(b.b==a){return;}if(a<0){throw sm(new rm(),'Cannot set number of rows to '+a);}if(b.b<a){sg(b.c,a-b.b,b.a);b.b=a;}else{while(b.b>a){ci(b,--b.b);}}}
function sg(g,f,c){var h=$doc.createElement('td');h.innerHTML='&nbsp;';var d=$doc.createElement('tr');for(var b=0;b<c;b++){var a=h.cloneNode(true);d.appendChild(a);}g.appendChild(d);for(var e=1;e<f;e++){g.appendChild(d.cloneNode(true));}}
function ig(){}
_=ig.prototype=new ug();_.tI=25;_.a=0;_.b=0;function Bi(a){a.db(kc());wk(a,131197);uk(a,'gwt-Label');return a;}
function Di(a){switch(uc(a)){case 1:break;case 4:case 8:case 64:case 16:case 32:break;case 131072:break;}}
function Ai(){}
_=Ai.prototype=new yk();_.B=Di;_.tI=26;function oi(a){Bi(a);a.db(kc());wk(a,125);uk(a,'gwt-HTML');return a;}
function pi(b,a){oi(b);ri(b,a);return b;}
function ri(b,a){dd(b.k,a);}
function tg(){}
_=tg.prototype=new Ai();_.tI=27;function wg(a){{zg(a);}}
function xg(b,a){b.b=a;wg(b);return b;}
function zg(a){while(++a.a<a.b.b.b){if(xp(a.b.b,a.a)!==null){return;}}}
function Ag(a){return a.a<a.b.b.b;}
function Bg(){return Ag(this);}
function Cg(){var a;if(!Ag(this)){throw new fs();}a=xp(this.b.b,this.a);zg(this);return a;}
function vg(){}
_=vg.prototype=new wm();_.x=Bg;_.A=Cg;_.tI=0;_.a=(-1);function Eg(b,a){b.a=a;return b;}
function ah(e,d,c,a){var b=d.rows[c].cells[a];return b==null?null:b;}
function bh(c,b,a){return ah(c,c.a.c,b,a);}
function Dg(){}
_=Dg.prototype=new wm();_.tI=0;function dh(b,a){b.b=a;return b;}
function fh(a){if(a.a===null){a.a=lc('colgroup');Bc(a.b.g,a.a,0);hc(a.a,lc('col'));}}
function ch(){}
_=ch.prototype=new wm();_.tI=0;_.a=null;function ih(c,a,b){return a.rows[b];}
function gh(){}
_=gh.prototype=new wm();_.tI=0;function nh(a){a.b=tp(new rp());}
function oh(a){nh(a);return a;}
function qh(c,a){var b;b=wh(a);if(b<0){return null;}return Cb(xp(c.b,b),7);}
function rh(b,c){var a;if(b.a===null){a=b.b.b;up(b.b,c);}else{a=b.a.a;Bp(b.b,a,c);b.a=b.a.b;}xh(c.k,a);}
function sh(c,a,b){vh(a);Bp(c.b,b,null);c.a=lh(new kh(),b,c.a);}
function th(c,a){var b;b=wh(a);sh(c,a,b);}
function uh(a){return xg(new vg(),a);}
function vh(a){a['__widgetID']=null;}
function wh(a){var b=a['__widgetID'];return b==null?-1:b;}
function xh(a,b){a['__widgetID']=b;}
function jh(){}
_=jh.prototype=new wm();_.tI=0;_.a=null;function lh(c,a,b){c.a=a;c.b=b;return c;}
function kh(){}
_=kh.prototype=new wm();_.tI=0;_.a=0;_.b=null;function mj(){mj=js;rj=kr(new rq());}
function lj(b,a){mj();df(b);if(a===null){a=nj();}b.db(a);il(b);return b;}
function oj(){mj();return pj(null);}
function pj(c){mj();var a,b;b=Cb(qr(rj,c),8);if(b!==null){return b;}a=null;if(rj.c==0){qj();}rr(rj,c,b=lj(new gj(),a));return b;}
function nj(){mj();return $doc.body;}
function qj(){mj();Ad(new hj());}
function gj(){}
_=gj.prototype=new cf();_.tI=28;var rj;function jj(){var a,b;for(b=yo(gp((mj(),rj)));Fo(b);){a=Cb(ap(b),8);if(a.i){jl(a);}}}
function kj(){return null;}
function hj(){}
_=hj.prototype=new wm();_.F=jj;_.ab=kj;_.tI=29;function zj(b,a){b.db(a);return b;}
function Bj(a,b){if(a.c!==b){return false;}bj(a,b);Dc(a.k,b.k);a.c=null;return true;}
function Cj(a,b){if(b===a.c){return;}if(b!==null){kl(b);}if(a.c!==null){Bj(a,a.c);}a.c=b;if(b!==null){hc(a.k,a.c.k);Fi(a,b);}}
function Dj(){return vj(new tj(),this);}
function Ej(a){return Bj(this,a);}
function sj(){}
_=sj.prototype=new Ei();_.y=Dj;_.cb=Ej;_.tI=30;_.c=null;function uj(a){a.a=a.b.c!==null;}
function vj(b,a){b.b=a;uj(b);return b;}
function xj(){return this.a;}
function yj(){if(!this.a||this.b.c===null){throw new fs();}this.a=false;return this.b.c;}
function tj(){}
_=tj.prototype=new wm();_.x=xj;_.A=yj;_.tI=0;function jk(){jk=js;vl(),xl;}
function ik(b,a){vl(),xl;bg(b,a);wk(b,1024);return b;}
function kk(a){return wc(a.k,'value');}
function lk(b,a){bd(b.k,'value',a!==null?a:'');}
function mk(a){if(this.a===null){this.a=uf(new tf());}up(this.a,a);}
function nk(a){var b;dg(this,a);b=uc(a);if(b==1){if(this.a!==null){wf(this.a,this);}}else{}}
function hk(){}
_=hk.prototype=new ag();_.l=mk;_.B=nk;_.tI=31;_.a=null;function pk(){pk=js;vl(),xl;}
function ok(a){vl(),xl;ik(a,mc());uk(a,'gwt-TextBox');return a;}
function gk(){}
_=gk.prototype=new hk();_.tI=32;function Fk(b,a){b.a=wb('[Lcom.google.gwt.user.client.ui.Widget;',[0],[7],[4],null);return b;}
function al(a,b){dl(a,b,a.b);}
function cl(b,c){var a;for(a=0;a<b.b;++a){if(b.a[a]===c){return a;}}return (-1);}
function dl(d,e,a){var b,c;if(a<0||a>d.b){throw new rm();}if(d.b==d.a.a){c=wb('[Lcom.google.gwt.user.client.ui.Widget;',[0],[7],[d.a.a*2],null);for(b=0;b<d.a.a;++b){yb(c,b,d.a[b]);}d.a=c;}++d.b;for(b=d.b-1;b>a;--b){yb(d.a,b,d.a[b-1]);}yb(d.a,a,e);}
function el(a){return Bk(new Ak(),a);}
function fl(c,b){var a;if(b<0||b>=c.b){throw new rm();}--c.b;for(a=b;a<c.b;++a){yb(c.a,a,c.a[a+1]);}yb(c.a,c.b,null);}
function gl(b,c){var a;a=cl(b,c);if(a==(-1)){throw new fs();}fl(b,a);}
function zk(){}
_=zk.prototype=new wm();_.tI=0;_.a=null;_.b=0;function Bk(b,a){b.b=a;return b;}
function Dk(){return this.a<this.b.b-1;}
function Ek(){if(this.a>=this.b.b){throw new fs();}return this.b.a[++this.a];}
function Ak(){}
_=Ak.prototype=new wm();_.x=Dk;_.A=Ek;_.tI=0;_.a=(-1);function vl(){vl=js;wl=ul(new tl());xl=wl;}
function ul(a){vl();return a;}
function tl(){}
_=tl.prototype=new wm();_.tI=0;var wl,xl;function am(){am=js;cm=new Al();}
function Fl(a,b){am();zj(a,lc('fieldset'));a.a=lc('legend');hc(a.k,a.a);bm(a,b);return a;}
function bm(a,b){a.b=b;Cl(cm,a.k,a.a,b);}
function yl(){}
_=yl.prototype=new sj();_.tI=33;_.a=null;_.b=null;var cm;function El(c,a,b,d){if(d!==''&&d!==null){dd(b,d);Bc(a,b,0);}else if(zc(b)!==null){Dc(a,b);}}
function zl(){}
_=zl.prototype=new wm();_.tI=0;function Cl(c,a,b,d){ed(a,'display','none');El(c,a,b,d);ed(a,'display','');}
function Al(){}
_=Al.prototype=new zl();_.tI=0;function jn(b,a){a;return b;}
function hn(){}
_=hn.prototype=new wm();_.tI=3;function mm(b,a){jn(b,a);return b;}
function lm(){}
_=lm.prototype=new hn();_.tI=4;function Bm(b,a){mm(b,a);return b;}
function Am(){}
_=Am.prototype=new lm();_.tI=5;function em(){}
_=em.prototype=new Am();_.tI=34;function hm(){}
_=hm.prototype=new Am();_.tI=35;function pm(b,a){Bm(b,a);return b;}
function om(){}
_=om.prototype=new Am();_.tI=36;function sm(b,a){Bm(b,a);return b;}
function rm(){}
_=rm.prototype=new Am();_.tI=37;function um(){}
_=um.prototype=new Am();_.tI=38;function Fm(b,a){return b.substr(a,b.length-a);}
function an(a,b){return String(a)==b;}
function bn(a){if(!Db(a,1))return false;return an(this,a);}
function dn(){var a=cn;if(!a){a=cn={};}var e=':'+this;var b=a[e];if(b==null){b=0;var f=this.length;var d=f<64?1:f/32|0;for(var c=0;c<f;c+=d){b<<=1;b+=this.charCodeAt(c);}b|=0;a[e]=b;}return b;}
_=String.prototype;_.eQ=bn;_.hC=dn;_.tI=2;var cn=null;function gn(a){return s(a);}
function mn(b,a){Bm(b,a);return b;}
function ln(){}
_=ln.prototype=new Am();_.tI=39;function vn(b,a){b.c=a;return b;}
function xn(a){return a.a<a.c.eb();}
function yn(a){if(!xn(a)){throw new fs();}return a.c.v(a.b=a.a++);}
function zn(a){if(a.b<0){throw new om();}a.c.bb(a.b);a.a=a.b;a.b=(-1);}
function An(){return xn(this);}
function Bn(){return yn(this);}
function un(){}
_=un.prototype=new wm();_.x=An;_.A=Bn;_.tI=0;_.a=0;_.b=(-1);function ep(f,d,e){var a,b,c;for(b=fr(f.s());Eq(b);){a=Fq(b);c=a.t();if(d===null?c===null:d.eQ(c)){if(e){ar(b);}return a;}}return null;}
function fp(b){var a;a=b.s();return io(new ho(),b,a);}
function gp(b){var a;a=pr(b);return wo(new vo(),b,a);}
function hp(a){return ep(this,a,false)!==null;}
function ip(d){var a,b,c,e,f,g,h;if(d===this){return true;}if(!Db(d,13)){return false;}f=Cb(d,13);c=fp(this);e=f.z();if(!op(c,e)){return false;}for(a=ko(c);ro(a);){b=so(a);h=this.w(b);g=f.w(b);if(h===null?g!==null:!h.eQ(g)){return false;}}return true;}
function jp(b){var a;a=ep(this,b,false);return a===null?null:a.u();}
function kp(){var a,b,c;b=0;for(c=fr(this.s());Eq(c);){a=Fq(c);b+=a.hC();}return b;}
function lp(){return fp(this);}
function go(){}
_=go.prototype=new wm();_.o=hp;_.eQ=ip;_.w=jp;_.hC=kp;_.z=lp;_.tI=40;function op(e,b){var a,c,d;if(b===e){return true;}if(!Db(b,14)){return false;}c=Cb(b,14);if(c.eb()!=e.eb()){return false;}for(a=c.y();a.x();){d=a.A();if(!e.p(d)){return false;}}return true;}
function pp(a){return op(this,a);}
function qp(){var a,b,c;a=0;for(b=this.y();b.x();){c=b.A();if(c!==null){a+=c.hC();}}return a;}
function mp(){}
_=mp.prototype=new on();_.eQ=pp;_.hC=qp;_.tI=41;function io(b,a,c){b.a=a;b.b=c;return b;}
function ko(b){var a;a=fr(b.b);return po(new oo(),b,a);}
function lo(a){return this.a.o(a);}
function mo(){return ko(this);}
function no(){return this.b.a.c;}
function ho(){}
_=ho.prototype=new mp();_.p=lo;_.y=mo;_.eb=no;_.tI=42;function po(b,a,c){b.a=c;return b;}
function ro(a){return a.a.x();}
function so(b){var a;a=b.a.A();return a.t();}
function to(){return ro(this);}
function uo(){return so(this);}
function oo(){}
_=oo.prototype=new wm();_.x=to;_.A=uo;_.tI=0;function wo(b,a,c){b.a=a;b.b=c;return b;}
function yo(b){var a;a=fr(b.b);return Do(new Co(),b,a);}
function zo(a){return or(this.a,a);}
function Ao(){return yo(this);}
function Bo(){return this.b.a.c;}
function vo(){}
_=vo.prototype=new on();_.p=zo;_.y=Ao;_.eb=Bo;_.tI=0;function Do(b,a,c){b.a=c;return b;}
function Fo(a){return a.a.x();}
function ap(a){var b;b=a.a.A().u();return b;}
function bp(){return Fo(this);}
function cp(){return ap(this);}
function Co(){}
_=Co.prototype=new wm();_.x=bp;_.A=cp;_.tI=0;function kq(){kq=js;xb('[Ljava.lang.String;',0,1,['Sun','Mon','Tue','Wed','Thu','Fri','Sat']);xb('[Ljava.lang.String;',0,1,['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec']);}
function jq(a){kq();mq(a);return a;}
function lq(a){return a.jsdate.getTime();}
function mq(a){a.jsdate=new Date();}
function nq(a){return a.jsdate.toLocaleString();}
function oq(a){return Db(a,15)&&lq(this)==lq(Cb(a,15));}
function pq(){return Eb(lq(this)^lq(this)>>>32);}
function iq(){}
_=iq.prototype=new wm();_.eQ=oq;_.hC=pq;_.tI=43;function mr(){mr=js;tr=zr();}
function jr(a){{lr(a);}}
function kr(a){mr();jr(a);return a;}
function lr(a){a.a=z();a.d=A();a.b=cc(tr,v);a.c=0;}
function nr(b,a){if(Db(a,1)){return Dr(b.d,Cb(a,1))!==tr;}else if(a===null){return b.b!==tr;}else{return Cr(b.a,a,a.hC())!==tr;}}
function or(a,b){if(a.b!==tr&&Br(a.b,b)){return true;}else if(yr(a.d,b)){return true;}else if(wr(a.a,b)){return true;}return false;}
function pr(a){return dr(new Aq(),a);}
function qr(c,a){var b;if(Db(a,1)){b=Dr(c.d,Cb(a,1));}else if(a===null){b=c.b;}else{b=Cr(c.a,a,a.hC());}return b===tr?null:b;}
function rr(c,a,d){var b;{b=c.b;c.b=d;}if(b===tr){++c.c;return null;}else{return b;}}
function sr(c,a){var b;if(Db(a,1)){b=as(c.d,Cb(a,1));}else if(a===null){b=c.b;c.b=cc(tr,v);}else{b=Fr(c.a,a,a.hC());}if(b===tr){return null;}else{--c.c;return b;}}
function ur(e,c){mr();for(var d in e){if(d==parseInt(d)){var a=e[d];for(var f=0,b=a.length;f<b;++f){c.n(a[f]);}}}}
function vr(d,a){mr();for(var c in d){if(c.charCodeAt(0)==58){var e=d[c];var b=vq(c.substring(1),e);a.n(b);}}}
function wr(f,h){mr();for(var e in f){if(e==parseInt(e)){var a=f[e];for(var g=0,b=a.length;g<b;++g){var c=a[g];var d=c.u();if(Br(h,d)){return true;}}}}return false;}
function xr(a){return nr(this,a);}
function yr(c,d){mr();for(var b in c){if(b.charCodeAt(0)==58){var a=c[b];if(Br(d,a)){return true;}}}return false;}
function zr(){mr();}
function Ar(){return pr(this);}
function Br(a,b){mr();if(a===b){return true;}else if(a===null){return false;}else{return a.eQ(b);}}
function Er(a){return qr(this,a);}
function Cr(f,h,e){mr();var a=f[e];if(a){for(var g=0,b=a.length;g<b;++g){var c=a[g];var d=c.t();if(Br(h,d)){return c.u();}}}}
function Dr(b,a){mr();return b[':'+a];}
function Fr(f,h,e){mr();var a=f[e];if(a){for(var g=0,b=a.length;g<b;++g){var c=a[g];var d=c.t();if(Br(h,d)){if(a.length==1){delete f[e];}else{a.splice(g,1);}return c.u();}}}}
function as(c,a){mr();a=':'+a;var b=c[a];delete c[a];return b;}
function rq(){}
_=rq.prototype=new go();_.o=xr;_.s=Ar;_.w=Er;_.tI=44;_.a=null;_.b=null;_.c=0;_.d=null;var tr;function tq(b,a,c){b.a=a;b.b=c;return b;}
function vq(a,b){return tq(new sq(),a,b);}
function wq(b){var a;if(Db(b,16)){a=Cb(b,16);if(Br(this.a,a.t())&&Br(this.b,a.u())){return true;}}return false;}
function xq(){return this.a;}
function yq(){return this.b;}
function zq(){var a,b;a=0;b=0;if(this.a!==null){a=this.a.hC();}if(this.b!==null){b=this.b.hC();}return a^b;}
function sq(){}
_=sq.prototype=new wm();_.eQ=wq;_.t=xq;_.u=yq;_.hC=zq;_.tI=45;_.a=null;_.b=null;function dr(b,a){b.a=a;return b;}
function fr(a){return Cq(new Bq(),a.a);}
function gr(c){var a,b,d;if(Db(c,16)){a=Cb(c,16);b=a.t();if(nr(this.a,b)){d=qr(this.a,b);return Br(a.u(),d);}}return false;}
function hr(){return fr(this);}
function ir(){return this.a.c;}
function Aq(){}
_=Aq.prototype=new mp();_.p=gr;_.y=hr;_.eb=ir;_.tI=46;function Cq(c,b){var a;c.c=b;a=tp(new rp());if(c.c.b!==(mr(),tr)){up(a,tq(new sq(),null,c.c.b));}vr(c.c.d,a);ur(c.c.a,a);c.a=En(a);return c;}
function Eq(a){return xn(a.a);}
function Fq(a){return a.b=Cb(yn(a.a),16);}
function ar(a){if(a.b===null){throw pm(new om(),'Must call next() before remove().');}else{zn(a.a);sr(a.c,a.b.t());a.b=null;}}
function br(){return Eq(this);}
function cr(){return Fq(this);}
function Bq(){}
_=Bq.prototype=new wm();_.x=br;_.A=cr;_.tI=0;_.a=null;_.b=null;function fs(){}
_=fs.prototype=new Am();_.tI=47;function dm(){mb(new E());}
function gwtOnLoad(b,d,c){$moduleName=d;$moduleBase=c;if(b)try{dm();}catch(a){b(d);}else{dm();}}
var bc=[{},{},{1:1},{3:1},{3:1},{3:1},{3:1},{2:1},{6:1},{6:1},{6:1},{2:1,4:1},{2:1},{5:1},{7:1,9:1,10:1,11:1},{7:1,9:1,10:1,11:1},{7:1,9:1,10:1,11:1},{7:1,9:1,10:1,11:1},{7:1,9:1,10:1,11:1},{7:1,9:1,10:1,11:1},{7:1,9:1,10:1,11:1},{12:1},{12:1},{12:1},{7:1,9:1,10:1,11:1},{7:1,9:1,10:1,11:1},{7:1,9:1,10:1,11:1},{7:1,9:1,10:1,11:1},{7:1,8:1,9:1,10:1,11:1},{5:1},{7:1,9:1,10:1,11:1},{7:1,9:1,10:1,11:1},{7:1,9:1,10:1,11:1},{7:1,9:1,10:1,11:1},{3:1},{3:1},{3:1},{3:1},{3:1},{3:1},{13:1},{14:1},{14:1},{15:1},{13:1},{16:1},{14:1},{3:1}];if (com_google_gwt_demos_titledpanel_TitledPanelDemo) {  var __gwt_initHandlers = com_google_gwt_demos_titledpanel_TitledPanelDemo.__gwt_initHandlers;  com_google_gwt_demos_titledpanel_TitledPanelDemo.onScriptLoad(gwtOnLoad);}})();