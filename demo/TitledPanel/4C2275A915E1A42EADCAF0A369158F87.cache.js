(function(){var $wnd = window;var $doc = $wnd.document;var $moduleName, $moduleBase;var _,xs='com.google.client.',ys='com.google.gwt.core.client.',zs='com.google.gwt.lang.',As='com.google.gwt.user.client.',Bs='com.google.gwt.user.client.impl.',Cs='com.google.gwt.user.client.ui.',Ds='com.google.gwt.user.client.ui.impl.',Es='com.google.gwt.widgetideas.client.',Fs='java.lang.',at='java.util.';function ws(){}
function Fm(a){return this===a;}
function an(){return un(this);}
function Dm(){}
_=Dm.prototype={};_.eQ=Fm;_.hC=an;_.tI=1;function z(a){A(a);}
function A(d){var a,b,c,e;a=pi(new tg(),'This is a widget in the TitledPanel');vk(a,'titledPanelDemoContents');c=dm(new Bl(),'Hello World',a);wk(c,'500px');ef(oj(),c);b=kg(new ig(),3,3);ei(b,1);gi(b,3);e=ok(new gk());lk(e,c.b);ki(b,0,1,e);ii(b,0,2,'Change the title dynamically');ki(b,0,0,qf(new jf(),'Set Title',n(new m(),d,c,e)));ii(b,1,1,'<BR>');ii(b,1,2,'Clear out the title of the box.  Note that this is different from setting the title to an empty string.  When you set the title to an empty string, you will see a small gap where the text would normally go.  Clearing the title effectively creates a bordered widget.');ki(b,1,0,qf(new jf(),'Clear Title',r(new q(),d,c)));ii(b,2,1,'<BR>');ii(b,2,2,'The TitledPanel is a type of SimplePanel, so you can add any widget to it.');ki(b,2,0,qf(new jf(),'Set Widget',v(new u(),d,c)));ef(oj(),pi(new tg(),'<BR><BR>'));ef(oj(),b);}
function l(){}
_=l.prototype=new Dm();_.tI=0;function n(b,a,c,d){b.a=c;b.b=d;return b;}
function p(a){fm(this.a,kk(this.b));}
function m(){}
_=m.prototype=new Dm();_.C=p;_.tI=3;function r(b,a,c){b.a=c;return b;}
function t(a){fm(this.a,null);}
function q(){}
_=q.prototype=new Dm();_.C=t;_.tI=4;function v(b,a,c){b.a=c;return b;}
function x(c){var a,b;a=wq(new vq());b=pi(new tg(),'This is a new widget created on:<BR> '+Aq(a));rk(b,'titledPanelDemoContents');Cj(this.a,b);}
function u(){}
_=u.prototype=new Dm();_.C=x;_.tI=5;var E=null;function bb(a){return a==null?0:a.$H?a.$H:(a.$H=db());}
function cb(a){return a==null?0:a.$H?a.$H:(a.$H=db());}
function db(){return ++eb;}
var eb=0;function hb(b,a){if(!Db(a,2)){return false;}return lb(b,Cb(a,2));}
function ib(a){return bb(a);}
function jb(){return [];}
function kb(){return {};}
function mb(a){return hb(this,a);}
function lb(a,b){return a===b;}
function nb(){return ib(this);}
function fb(){}
_=fb.prototype=new Dm();_.eQ=mb;_.hC=nb;_.tI=10;function pb(c,a,d,b,e){c.a=a;c.b=b;e;c.tI=d;return c;}
function rb(a,b,c){return a[b]=c;}
function sb(b,a){return b[a];}
function ub(b,a){return b[a];}
function tb(a){return a.length;}
function wb(e,d,c,b,a){return vb(e,d,c,b,0,tb(b),a);}
function vb(j,i,g,c,e,a,b){var d,f,h;if((f=sb(c,e))<0){throw new Bm();}h=pb(new ob(),f,sb(i,e),sb(g,e),j);++e;if(e<a){j=ln(j,1);for(d=0;d<f;++d){rb(h,d,vb(j,i,g,c,e,a,b));}}else{for(d=0;d<f;++d){rb(h,d,b);}}return h;}
function xb(f,e,c,g){var a,b,d;b=tb(g);d=pb(new ob(),b,e,c,f);for(a=0;a<b;++a){rb(d,a,ub(g,a));}return d;}
function yb(a,b,c){if(c!==null&&a.b!=0&& !Db(c,a.b)){throw new im();}return rb(a,b,c);}
function ob(){}
_=ob.prototype=new Dm();_.tI=0;function Bb(b,a){return !(!(b&&bc[b][a]));}
function Cb(b,a){if(b!=null)Bb(b.tI,a)||ac();return b;}
function Db(b,a){return b!=null&&Bb(b.tI,a);}
function Eb(a){return ~(~a);}
function ac(){throw new lm();}
function Fb(a){if(a!==null){throw new lm();}return a;}
function cc(b,d){_=d.prototype;if(b&& !(b.tI>=_.tI)){var c=b.toString;for(var a in _){b[a]=_[a];}b.toString=c;}return b;}
var bc;function gc(){gc=ws;Fc=aq(new Ep());{Ac=new fe();je(Ac);}}
function hc(b,a){gc();ue(Ac,b,a);}
function ic(a,b){gc();return he(Ac,a,b);}
function jc(){gc();return we(Ac,'button');}
function kc(){gc();return we(Ac,'div');}
function lc(a){gc();return we(Ac,a);}
function mc(){gc();return xe(Ac,'text');}
function nc(){gc();return we(Ac,'tbody');}
function oc(){gc();return we(Ac,'td');}
function pc(){gc();return we(Ac,'table');}
function sc(b,a,d){gc();var c;c=E;{rc(b,a,d);}}
function rc(b,a,c){gc();var d;if(a===Ec){if(uc(b)==8192){Ec=null;}}d=qc;qc=b;try{c.B(b);}finally{qc=d;}}
function tc(b,a){gc();ye(Ac,b,a);}
function uc(a){gc();return ze(Ac,a);}
function vc(a){gc();oe(Ac,a);}
function wc(a,b){gc();return Ae(Ac,a,b);}
function xc(a){gc();return Be(Ac,a);}
function yc(a){gc();return pe(Ac,a);}
function zc(a){gc();return qe(Ac,a);}
function Bc(c,a,b){gc();se(Ac,c,a,b);}
function Cc(a){gc();var b,c;c=true;if(Fc.b>0){b=Fb(eq(Fc,Fc.b-1));if(!(c=null.gb())){tc(a,true);vc(a);}}return c;}
function Dc(b,a){gc();Ce(Ac,b,a);}
function bd(a,b,c){gc();Ee(Ac,a,b,c);}
function ad(a,b,c){gc();De(Ac,a,b,c);}
function cd(a,b){gc();Fe(Ac,a,b);}
function dd(a,b){gc();af(Ac,a,b);}
function ed(b,a,c){gc();bf(Ac,b,a,c);}
function fd(a,b){gc();le(Ac,a,b);}
var qc=null,Ac=null,Ec=null,Fc;function id(a){if(Db(a,4)){return ic(this,Cb(a,4));}return hb(cc(this,gd),a);}
function jd(){return ib(cc(this,gd));}
function gd(){}
_=gd.prototype=new fb();_.eQ=id;_.hC=jd;_.tI=11;function nd(a){return hb(cc(this,kd),a);}
function od(){return ib(cc(this,kd));}
function kd(){}
_=kd.prototype=new fb();_.eQ=nd;_.hC=od;_.tI=12;function ud(){ud=ws;wd=aq(new Ep());{vd();}}
function vd(){ud();Ad(new qd());}
var wd;function sd(){while((ud(),wd).b>0){Fb(eq((ud(),wd),0)).gb();}}
function td(){return null;}
function qd(){}
_=qd.prototype=new Dm();_.F=sd;_.ab=td;_.tI=13;function zd(){zd=ws;Bd=aq(new Ep());de=aq(new Ep());{Fd();}}
function Ad(a){zd();bq(Bd,a);}
function Cd(){zd();var a,b;for(a=mo(Bd);fo(a);){b=Cb(go(a),5);b.F();}}
function Dd(){zd();var a,b,c,d;d=null;for(a=mo(Bd);fo(a);){b=Cb(go(a),5);c=b.ab();{d=c;}}return d;}
function Ed(){zd();var a,b;for(a=mo(de);fo(a);){b=Fb(go(a));null.gb();}}
function Fd(){zd();__gwt_initHandlers(function(){ce();},function(){return be();},function(){ae();$wnd.onresize=null;$wnd.onbeforeclose=null;$wnd.onclose=null;});}
function ae(){zd();var a;a=E;{Cd();}}
function be(){zd();var a;a=E;{return Dd();}}
function ce(){zd();var a;a=E;{Ed();}}
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
_=ee.prototype=new Dm();_.tI=0;function oe(b,a){a.preventDefault();}
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
_=fe.prototype=new me();_.tI=0;function rk(b,a){Ak(b.k,a,true);}
function tk(d,b,a){var c=b.parentNode;if(!c){return;}c.insertBefore(a,b);c.removeChild(b);}
function uk(b,a){if(b.k!==null){tk(b,b.k,a);}b.k=a;}
function vk(b,a){zk(b.k,a);}
function wk(a,b){ed(a.k,'width',b);}
function xk(b,a){fd(b.k,a|xc(b.k));}
function yk(a){return wc(a,'className');}
function zk(a,b){bd(a,'className',b);}
function Ak(c,j,a){var b,d,e,f,g,h,i;if(c===null){throw cn(new bn(),'Null widget handle. If you are creating a composite, ensure that initWidget() has been called.');}j=nn(j);if(kn(j)==0){throw tm(new sm(),'Style names cannot be empty');}i=yk(c);e=hn(i,j);while(e!=(-1)){if(e==0||fn(i,e-1)==32){f=e+kn(j);g=kn(i);if(f==g||f<g&&fn(i,f)==32){break;}}e=jn(i,j,e+1);}if(a){if(e==(-1)){if(kn(i)>0){i+=' ';}bd(c,'className',i+j);}}else{if(e!=(-1)){b=nn(mn(i,0,e));d=nn(ln(i,e+kn(j)));if(kn(b)==0){h=d;}else if(kn(d)==0){h=b;}else{h=b+' '+d;}bd(c,'className',h);}}}
function qk(){}
_=qk.prototype=new Dm();_.tI=0;_.k=null;function ll(a){if(a.i){throw wm(new vm(),"Should only call onAttach when the widget is detached from the browser's document");}a.i=true;cd(a.k,a);a.q();a.D();}
function ml(a){if(!a.i){throw wm(new vm(),"Should only call onDetach when the widget is attached to the browser's document");}try{a.E();}finally{a.r();cd(a.k,null);a.i=false;}}
function nl(a){if(a.j!==null){a.j.cb(a);}else if(a.j!==null){throw wm(new vm(),"This widget's parent does not implement HasWidgets");}}
function ol(b,a){if(b.i){cd(b.k,null);}uk(b,a);if(b.i){cd(a,b);}}
function pl(c,b){var a;a=c.j;if(b===null){if(a!==null&&a.i){ml(c);}c.j=null;}else{if(a!==null){throw wm(new vm(),'Cannot set a new parent without first clearing the old parent');}c.j=b;if(b.i){ll(c);}}}
function ql(){}
function rl(){}
function sl(a){}
function tl(){}
function ul(){}
function vl(a){ol(this,a);}
function Bk(){}
_=Bk.prototype=new qk();_.q=ql;_.r=rl;_.B=sl;_.D=tl;_.E=ul;_.db=vl;_.tI=14;_.i=false;_.j=null;function Fi(b,a){pl(a,b);}
function bj(b,a){pl(a,null);}
function cj(){var a,b;for(b=this.y();b.x();){a=Cb(b.A(),7);ll(a);}}
function dj(){var a,b;for(b=this.y();b.x();){a=Cb(b.A(),7);ml(a);}}
function ej(){}
function fj(){}
function Ei(){}
_=Ei.prototype=new Bk();_.q=cj;_.r=dj;_.D=ej;_.E=fj;_.tI=15;function zf(a){a.a=cl(new Ck(),a);}
function Af(a){zf(a);return a;}
function Bf(c,a,b){nl(a);dl(c.a,a);hc(b,a.k);Fi(c,a);}
function Df(b,c){var a;if(c.j!==b){return false;}bj(b,c);a=c.k;Dc(zc(a),a);jl(b.a,c);return true;}
function Ef(){return hl(this.a);}
function Ff(a){return Df(this,a);}
function yf(){}
_=yf.prototype=new Ei();_.y=Ef;_.cb=Ff;_.tI=16;function df(a){Af(a);a.db(kc());ed(a.k,'position','relative');ed(a.k,'overflow','hidden');return a;}
function ef(a,b){Bf(a,b,a.k);}
function gf(a){ed(a,'left','');ed(a,'top','');ed(a,'position','');}
function hf(b){var a;a=Df(this,b);if(a){gf(b.k);}return a;}
function cf(){}
_=cf.prototype=new yf();_.cb=hf;_.tI=17;function cg(){cg=ws;yl(),Al;}
function bg(b,a){yl(),Al;eg(b,a);return b;}
function dg(b,a){switch(uc(a)){case 1:if(b.b!==null){wf(b.b,b);}break;case 4096:case 2048:break;case 128:case 512:case 256:break;}}
function eg(b,a){ol(b,a);xk(b,7041);}
function fg(a){if(this.b===null){this.b=uf(new tf());}bq(this.b,a);}
function gg(a){dg(this,a);}
function hg(a){eg(this,a);}
function ag(){}
_=ag.prototype=new Bk();_.l=fg;_.B=gg;_.db=hg;_.tI=18;_.b=null;function mf(){mf=ws;yl(),Al;}
function lf(b,a){yl(),Al;bg(b,a);return b;}
function nf(b,a){dd(b.k,a);}
function kf(){}
_=kf.prototype=new ag();_.tI=19;function rf(){rf=ws;yl(),Al;}
function of(a){yl(),Al;lf(a,jc());sf(a.k);vk(a,'gwt-Button');return a;}
function pf(b,a){yl(),Al;of(b);nf(b,a);return b;}
function qf(c,a,b){yl(),Al;pf(c,a);c.l(b);return c;}
function sf(b){rf();if(b.type=='submit'){try{b.setAttribute('type','button');}catch(a){}}}
function jf(){}
_=jf.prototype=new kf();_.tI=20;function Cn(d,a,b){var c;while(a.x()){c=a.A();if(b===null?c===null:b.eQ(c)){return a;}}return null;}
function En(a){throw zn(new yn(),'add');}
function Fn(b){var a;a=Cn(this,this.y(),b);return a!==null;}
function Bn(){}
_=Bn.prototype=new Dm();_.n=En;_.p=Fn;_.tI=0;function lo(b,a){throw zm(new ym(),'Index: '+a+', Size: '+b.b);}
function mo(a){return co(new bo(),a);}
function no(b,a){throw zn(new yn(),'add');}
function oo(a){this.m(this.eb(),a);return true;}
function po(e){var a,b,c,d,f;if(e===this){return true;}if(!Db(e,12)){return false;}f=Cb(e,12);if(this.eb()!=f.eb()){return false;}c=mo(this);d=f.y();while(fo(c)){a=go(c);b=go(d);if(!(a===null?b===null:a.eQ(b))){return false;}}return true;}
function qo(){var a,b,c,d;c=1;a=31;b=mo(this);while(fo(b)){d=go(b);c=31*c+(d===null?0:d.hC());}return c;}
function ro(){return mo(this);}
function so(a){throw zn(new yn(),'remove');}
function ao(){}
_=ao.prototype=new Bn();_.m=no;_.n=oo;_.eQ=po;_.hC=qo;_.y=ro;_.bb=so;_.tI=21;function Fp(a){{cq(a);}}
function aq(a){Fp(a);return a;}
function bq(b,a){sq(b.a,b.b++,a);return true;}
function cq(a){a.a=jb();a.b=0;}
function eq(b,a){if(a<0||a>=b.b){lo(b,a);}return oq(b.a,a);}
function fq(b,a){return gq(b,a,0);}
function gq(c,b,a){if(a<0){lo(c,a);}for(;a<c.b;++a){if(nq(b,oq(c.a,a))){return a;}}return (-1);}
function hq(c,a){var b;b=eq(c,a);qq(c.a,a,1);--c.b;return b;}
function iq(d,a,b){var c;c=eq(d,a);sq(d.a,a,b);return c;}
function kq(a,b){if(a<0||a>this.b){lo(this,a);}jq(this.a,a,b);++this.b;}
function lq(a){return bq(this,a);}
function jq(a,b,c){a.splice(b,0,c);}
function mq(a){return fq(this,a)!=(-1);}
function nq(a,b){return a===b||a!==null&&a.eQ(b);}
function pq(a){return eq(this,a);}
function oq(a,b){return a[b];}
function rq(a){return hq(this,a);}
function qq(a,c,b){a.splice(c,b);}
function sq(a,b,c){a[b]=c;}
function tq(){return this.b;}
function Ep(){}
_=Ep.prototype=new ao();_.m=kq;_.n=lq;_.p=mq;_.v=pq;_.bb=rq;_.eb=tq;_.tI=22;_.a=null;_.b=0;function uf(a){aq(a);return a;}
function wf(d,c){var a,b;for(a=mo(d);fo(a);){b=Cb(go(a),6);b.C(c);}}
function tf(){}
_=tf.prototype=new Ep();_.tI=23;function yh(a){a.h=oh(new jh());}
function zh(a){yh(a);a.g=pc();a.c=nc();hc(a.g,a.c);a.db(a.g);xk(a,1);return a;}
function Ah(d,c,b){var a;Bh(d,c);if(b<0){throw zm(new ym(),'Column '+b+' must be non-negative: '+b);}a=d.a;if(a<=b){throw zm(new ym(),'Column index: '+b+', Column size: '+d.a);}}
function Bh(c,a){var b;b=c.b;if(a>=b||a<0){throw zm(new ym(),'Row index: '+a+', Row size: '+b);}}
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
function ki(d,b,a,e){var c;ng(d,b,a);if(e!==null){nl(e);c=Ch(d,b,a,true);rh(d.h,e);hc(c,e.k);Fi(d,e);}}
function li(){return uh(this.h);}
function mi(a){switch(uc(a)){case 1:{break;}default:}}
function ni(a){return di(this,a);}
function ug(){}
_=ug.prototype=new Ei();_.y=li;_.B=mi;_.cb=ni;_.tI=24;_.c=null;_.d=null;_.e=null;_.f=null;_.g=null;function jg(a){zh(a);fi(a,Eg(new Dg(),a));ji(a,new gh());hi(a,dh(new ch(),a));return a;}
function kg(c,b,a){jg(c);rg(c,b,a);return c;}
function mg(b){var a;a=Eh(b);dd(a,'&nbsp;');return a;}
function ng(c,b,a){og(c,b);if(a<0){throw zm(new ym(),'Cannot access a column with a negative index: '+a);}if(a>=c.a){throw zm(new ym(),'Column index: '+a+', Column size: '+c.a);}}
function og(b,a){if(a<0){throw zm(new ym(),'Cannot access a row with a negative index: '+a);}if(a>=b.b){throw zm(new ym(),'Row index: '+a+', Row size: '+b.b);}}
function rg(c,b,a){pg(c,a);qg(c,b);}
function pg(d,a){var b,c;if(d.a==a){return;}if(a<0){throw zm(new ym(),'Cannot set number of columns to '+a);}if(d.a>a){for(b=0;b<d.b;b++){for(c=d.a-1;c>=a;c--){bi(d,b,c);}}}else{for(b=0;b<d.b;b++){for(c=d.a;c<a;c++){Fh(d,b,c);}}}d.a=a;}
function qg(b,a){if(b.b==a){return;}if(a<0){throw zm(new ym(),'Cannot set number of rows to '+a);}if(b.b<a){sg(b.c,a-b.b,b.a);b.b=a;}else{while(b.b>a){ci(b,--b.b);}}}
function sg(g,f,c){var h=$doc.createElement('td');h.innerHTML='&nbsp;';var d=$doc.createElement('tr');for(var b=0;b<c;b++){var a=h.cloneNode(true);d.appendChild(a);}g.appendChild(d);for(var e=1;e<f;e++){g.appendChild(d.cloneNode(true));}}
function ig(){}
_=ig.prototype=new ug();_.tI=25;_.a=0;_.b=0;function Bi(a){a.db(kc());xk(a,131197);vk(a,'gwt-Label');return a;}
function Di(a){switch(uc(a)){case 1:break;case 4:case 8:case 64:case 16:case 32:break;case 131072:break;}}
function Ai(){}
_=Ai.prototype=new Bk();_.B=Di;_.tI=26;function oi(a){Bi(a);a.db(kc());xk(a,125);vk(a,'gwt-HTML');return a;}
function pi(b,a){oi(b);ri(b,a);return b;}
function ri(b,a){dd(b.k,a);}
function tg(){}
_=tg.prototype=new Ai();_.tI=27;function wg(a){{zg(a);}}
function xg(b,a){b.b=a;wg(b);return b;}
function zg(a){while(++a.a<a.b.b.b){if(eq(a.b.b,a.a)!==null){return;}}}
function Ag(a){return a.a<a.b.b.b;}
function Bg(){return Ag(this);}
function Cg(){var a;if(!Ag(this)){throw new ss();}a=eq(this.b.b,this.a);zg(this);return a;}
function vg(){}
_=vg.prototype=new Dm();_.x=Bg;_.A=Cg;_.tI=0;_.a=(-1);function Eg(b,a){b.a=a;return b;}
function ah(e,d,c,a){var b=d.rows[c].cells[a];return b==null?null:b;}
function bh(c,b,a){return ah(c,c.a.c,b,a);}
function Dg(){}
_=Dg.prototype=new Dm();_.tI=0;function dh(b,a){b.b=a;return b;}
function fh(a){if(a.a===null){a.a=lc('colgroup');Bc(a.b.g,a.a,0);hc(a.a,lc('col'));}}
function ch(){}
_=ch.prototype=new Dm();_.tI=0;_.a=null;function ih(c,a,b){return a.rows[b];}
function gh(){}
_=gh.prototype=new Dm();_.tI=0;function nh(a){a.b=aq(new Ep());}
function oh(a){nh(a);return a;}
function qh(c,a){var b;b=wh(a);if(b<0){return null;}return Cb(eq(c.b,b),7);}
function rh(b,c){var a;if(b.a===null){a=b.b.b;bq(b.b,c);}else{a=b.a.a;iq(b.b,a,c);b.a=b.a.b;}xh(c.k,a);}
function sh(c,a,b){vh(a);iq(c.b,b,null);c.a=lh(new kh(),b,c.a);}
function th(c,a){var b;b=wh(a);sh(c,a,b);}
function uh(a){return xg(new vg(),a);}
function vh(a){a['__widgetID']=null;}
function wh(a){var b=a['__widgetID'];return b==null?-1:b;}
function xh(a,b){a['__widgetID']=b;}
function jh(){}
_=jh.prototype=new Dm();_.tI=0;_.a=null;function lh(c,a,b){c.a=a;c.b=b;return c;}
function kh(){}
_=kh.prototype=new Dm();_.tI=0;_.a=0;_.b=null;function mj(){mj=ws;rj=xr(new Eq());}
function lj(b,a){mj();df(b);if(a===null){a=nj();}b.db(a);ll(b);return b;}
function oj(){mj();return pj(null);}
function pj(c){mj();var a,b;b=Cb(Dr(rj,c),8);if(b!==null){return b;}a=null;if(rj.c==0){qj();}Er(rj,c,b=lj(new gj(),a));return b;}
function nj(){mj();return $doc.body;}
function qj(){mj();Ad(new hj());}
function gj(){}
_=gj.prototype=new cf();_.tI=28;var rj;function jj(){var a,b;for(b=fp(tp((mj(),rj)));mp(b);){a=Cb(np(b),8);if(a.i){ml(a);}}}
function kj(){return null;}
function hj(){}
_=hj.prototype=new Dm();_.F=jj;_.ab=kj;_.tI=29;function zj(b,a){b.db(a);return b;}
function Bj(a,b){if(a.c!==b){return false;}bj(a,b);Dc(a.k,b.k);a.c=null;return true;}
function Cj(a,b){if(b===a.c){return;}if(b!==null){nl(b);}if(a.c!==null){Bj(a,a.c);}a.c=b;if(b!==null){hc(a.k,a.c.k);Fi(a,b);}}
function Dj(){return vj(new tj(),this);}
function Ej(a){return Bj(this,a);}
function sj(){}
_=sj.prototype=new Ei();_.y=Dj;_.cb=Ej;_.tI=30;_.c=null;function uj(a){a.a=a.b.c!==null;}
function vj(b,a){b.b=a;uj(b);return b;}
function xj(){return this.a;}
function yj(){if(!this.a||this.b.c===null){throw new ss();}this.a=false;return this.b.c;}
function tj(){}
_=tj.prototype=new Dm();_.x=xj;_.A=yj;_.tI=0;function jk(){jk=ws;yl(),Al;}
function ik(b,a){yl(),Al;bg(b,a);xk(b,1024);return b;}
function kk(a){return wc(a.k,'value');}
function lk(b,a){bd(b.k,'value',a!==null?a:'');}
function mk(a){if(this.a===null){this.a=uf(new tf());}bq(this.a,a);}
function nk(a){var b;dg(this,a);b=uc(a);if(b==1){if(this.a!==null){wf(this.a,this);}}else{}}
function hk(){}
_=hk.prototype=new ag();_.l=mk;_.B=nk;_.tI=31;_.a=null;function pk(){pk=ws;yl(),Al;}
function ok(a){yl(),Al;ik(a,mc());vk(a,'gwt-TextBox');return a;}
function gk(){}
_=gk.prototype=new hk();_.tI=32;function cl(b,a){b.a=wb('[Lcom.google.gwt.user.client.ui.Widget;',[0],[7],[4],null);return b;}
function dl(a,b){gl(a,b,a.b);}
function fl(b,c){var a;for(a=0;a<b.b;++a){if(b.a[a]===c){return a;}}return (-1);}
function gl(d,e,a){var b,c;if(a<0||a>d.b){throw new ym();}if(d.b==d.a.a){c=wb('[Lcom.google.gwt.user.client.ui.Widget;',[0],[7],[d.a.a*2],null);for(b=0;b<d.a.a;++b){yb(c,b,d.a[b]);}d.a=c;}++d.b;for(b=d.b-1;b>a;--b){yb(d.a,b,d.a[b-1]);}yb(d.a,a,e);}
function hl(a){return Ek(new Dk(),a);}
function il(c,b){var a;if(b<0||b>=c.b){throw new ym();}--c.b;for(a=b;a<c.b;++a){yb(c.a,a,c.a[a+1]);}yb(c.a,c.b,null);}
function jl(b,c){var a;a=fl(b,c);if(a==(-1)){throw new ss();}il(b,a);}
function Ck(){}
_=Ck.prototype=new Dm();_.tI=0;_.a=null;_.b=0;function Ek(b,a){b.b=a;return b;}
function al(){return this.a<this.b.b-1;}
function bl(){if(this.a>=this.b.b){throw new ss();}return this.b.a[++this.a];}
function Dk(){}
_=Dk.prototype=new Dm();_.x=al;_.A=bl;_.tI=0;_.a=(-1);function yl(){yl=ws;zl=xl(new wl());Al=zl;}
function xl(a){yl();return a;}
function wl(){}
_=wl.prototype=new Dm();_.tI=0;var zl,Al;function em(){em=ws;gm=new Dl();}
function cm(a,b){em();zj(a,lc('fieldset'));a.a=lc('legend');hc(a.k,a.a);fm(a,b);return a;}
function dm(a,b,c){em();cm(a,b);Cj(a,c);return a;}
function fm(a,b){a.b=b;Fl(gm,a.k,a.a,b);}
function Bl(){}
_=Bl.prototype=new sj();_.tI=33;_.a=null;_.b=null;var gm;function bm(c,a,b,d){if(d!==''&&d!==null){dd(b,d);Bc(a,b,0);}else if(zc(b)!==null){Dc(a,b);}}
function Cl(){}
_=Cl.prototype=new Dm();_.tI=0;function Fl(c,a,b,d){ed(a,'display','none');bm(c,a,b,d);ed(a,'display','');}
function Dl(){}
_=Dl.prototype=new Cl();_.tI=0;function wn(b,a){a;return b;}
function vn(){}
_=vn.prototype=new Dm();_.tI=6;function qm(b,a){wn(b,a);return b;}
function pm(){}
_=pm.prototype=new vn();_.tI=7;function cn(b,a){qm(b,a);return b;}
function bn(){}
_=bn.prototype=new pm();_.tI=8;function im(){}
_=im.prototype=new bn();_.tI=34;function lm(){}
_=lm.prototype=new bn();_.tI=35;function tm(b,a){cn(b,a);return b;}
function sm(){}
_=sm.prototype=new bn();_.tI=36;function wm(b,a){cn(b,a);return b;}
function vm(){}
_=vm.prototype=new bn();_.tI=37;function zm(b,a){cn(b,a);return b;}
function ym(){}
_=ym.prototype=new bn();_.tI=38;function Bm(){}
_=Bm.prototype=new bn();_.tI=39;function fn(b,a){return b.charCodeAt(a);}
function hn(b,a){return b.indexOf(a);}
function jn(c,b,a){return c.indexOf(b,a);}
function kn(a){return a.length;}
function ln(b,a){return b.substr(a,b.length-a);}
function mn(c,a,b){return c.substr(a,b-a);}
function nn(c){var a=c.replace(/^(\s*)/,'');var b=a.replace(/\s*$/,'');return b;}
function on(a,b){return String(a)==b;}
function pn(a){if(!Db(a,1))return false;return on(this,a);}
function rn(){var a=qn;if(!a){a=qn={};}var e=':'+this;var b=a[e];if(b==null){b=0;var f=this.length;var d=f<64?1:f/32|0;for(var c=0;c<f;c+=d){b<<=1;b+=this.charCodeAt(c);}b|=0;a[e]=b;}return b;}
_=String.prototype;_.eQ=pn;_.hC=rn;_.tI=2;var qn=null;function un(a){return cb(a);}
function zn(b,a){cn(b,a);return b;}
function yn(){}
_=yn.prototype=new bn();_.tI=40;function co(b,a){b.c=a;return b;}
function fo(a){return a.a<a.c.eb();}
function go(a){if(!fo(a)){throw new ss();}return a.c.v(a.b=a.a++);}
function ho(a){if(a.b<0){throw new vm();}a.c.bb(a.b);a.a=a.b;a.b=(-1);}
function io(){return fo(this);}
function jo(){return go(this);}
function bo(){}
_=bo.prototype=new Dm();_.x=io;_.A=jo;_.tI=0;_.a=0;_.b=(-1);function rp(f,d,e){var a,b,c;for(b=sr(f.s());lr(b);){a=mr(b);c=a.t();if(d===null?c===null:d.eQ(c)){if(e){nr(b);}return a;}}return null;}
function sp(b){var a;a=b.s();return vo(new uo(),b,a);}
function tp(b){var a;a=Cr(b);return dp(new cp(),b,a);}
function up(a){return rp(this,a,false)!==null;}
function vp(d){var a,b,c,e,f,g,h;if(d===this){return true;}if(!Db(d,13)){return false;}f=Cb(d,13);c=sp(this);e=f.z();if(!Bp(c,e)){return false;}for(a=xo(c);Eo(a);){b=Fo(a);h=this.w(b);g=f.w(b);if(h===null?g!==null:!h.eQ(g)){return false;}}return true;}
function wp(b){var a;a=rp(this,b,false);return a===null?null:a.u();}
function xp(){var a,b,c;b=0;for(c=sr(this.s());lr(c);){a=mr(c);b+=a.hC();}return b;}
function yp(){return sp(this);}
function to(){}
_=to.prototype=new Dm();_.o=up;_.eQ=vp;_.w=wp;_.hC=xp;_.z=yp;_.tI=41;function Bp(e,b){var a,c,d;if(b===e){return true;}if(!Db(b,14)){return false;}c=Cb(b,14);if(c.eb()!=e.eb()){return false;}for(a=c.y();a.x();){d=a.A();if(!e.p(d)){return false;}}return true;}
function Cp(a){return Bp(this,a);}
function Dp(){var a,b,c;a=0;for(b=this.y();b.x();){c=b.A();if(c!==null){a+=c.hC();}}return a;}
function zp(){}
_=zp.prototype=new Bn();_.eQ=Cp;_.hC=Dp;_.tI=42;function vo(b,a,c){b.a=a;b.b=c;return b;}
function xo(b){var a;a=sr(b.b);return Co(new Bo(),b,a);}
function yo(a){return this.a.o(a);}
function zo(){return xo(this);}
function Ao(){return this.b.a.c;}
function uo(){}
_=uo.prototype=new zp();_.p=yo;_.y=zo;_.eb=Ao;_.tI=43;function Co(b,a,c){b.a=c;return b;}
function Eo(a){return a.a.x();}
function Fo(b){var a;a=b.a.A();return a.t();}
function ap(){return Eo(this);}
function bp(){return Fo(this);}
function Bo(){}
_=Bo.prototype=new Dm();_.x=ap;_.A=bp;_.tI=0;function dp(b,a,c){b.a=a;b.b=c;return b;}
function fp(b){var a;a=sr(b.b);return kp(new jp(),b,a);}
function gp(a){return Br(this.a,a);}
function hp(){return fp(this);}
function ip(){return this.b.a.c;}
function cp(){}
_=cp.prototype=new Bn();_.p=gp;_.y=hp;_.eb=ip;_.tI=0;function kp(b,a,c){b.a=c;return b;}
function mp(a){return a.a.x();}
function np(a){var b;b=a.a.A().u();return b;}
function op(){return mp(this);}
function pp(){return np(this);}
function jp(){}
_=jp.prototype=new Dm();_.x=op;_.A=pp;_.tI=0;function xq(){xq=ws;xb('[Ljava.lang.String;',0,1,['Sun','Mon','Tue','Wed','Thu','Fri','Sat']);xb('[Ljava.lang.String;',0,1,['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec']);}
function wq(a){xq();zq(a);return a;}
function yq(a){return a.jsdate.getTime();}
function zq(a){a.jsdate=new Date();}
function Aq(a){return a.jsdate.toLocaleString();}
function Bq(a){return Db(a,15)&&yq(this)==yq(Cb(a,15));}
function Cq(){return Eb(yq(this)^yq(this)>>>32);}
function vq(){}
_=vq.prototype=new Dm();_.eQ=Bq;_.hC=Cq;_.tI=44;function zr(){zr=ws;as=gs();}
function wr(a){{yr(a);}}
function xr(a){zr();wr(a);return a;}
function yr(a){a.a=jb();a.d=kb();a.b=cc(as,fb);a.c=0;}
function Ar(b,a){if(Db(a,1)){return ks(b.d,Cb(a,1))!==as;}else if(a===null){return b.b!==as;}else{return js(b.a,a,a.hC())!==as;}}
function Br(a,b){if(a.b!==as&&is(a.b,b)){return true;}else if(fs(a.d,b)){return true;}else if(ds(a.a,b)){return true;}return false;}
function Cr(a){return qr(new hr(),a);}
function Dr(c,a){var b;if(Db(a,1)){b=ks(c.d,Cb(a,1));}else if(a===null){b=c.b;}else{b=js(c.a,a,a.hC());}return b===as?null:b;}
function Er(c,a,d){var b;{b=c.b;c.b=d;}if(b===as){++c.c;return null;}else{return b;}}
function Fr(c,a){var b;if(Db(a,1)){b=ns(c.d,Cb(a,1));}else if(a===null){b=c.b;c.b=cc(as,fb);}else{b=ms(c.a,a,a.hC());}if(b===as){return null;}else{--c.c;return b;}}
function bs(e,c){zr();for(var d in e){if(d==parseInt(d)){var a=e[d];for(var f=0,b=a.length;f<b;++f){c.n(a[f]);}}}}
function cs(d,a){zr();for(var c in d){if(c.charCodeAt(0)==58){var e=d[c];var b=cr(c.substring(1),e);a.n(b);}}}
function ds(f,h){zr();for(var e in f){if(e==parseInt(e)){var a=f[e];for(var g=0,b=a.length;g<b;++g){var c=a[g];var d=c.u();if(is(h,d)){return true;}}}}return false;}
function es(a){return Ar(this,a);}
function fs(c,d){zr();for(var b in c){if(b.charCodeAt(0)==58){var a=c[b];if(is(d,a)){return true;}}}return false;}
function gs(){zr();}
function hs(){return Cr(this);}
function is(a,b){zr();if(a===b){return true;}else if(a===null){return false;}else{return a.eQ(b);}}
function ls(a){return Dr(this,a);}
function js(f,h,e){zr();var a=f[e];if(a){for(var g=0,b=a.length;g<b;++g){var c=a[g];var d=c.t();if(is(h,d)){return c.u();}}}}
function ks(b,a){zr();return b[':'+a];}
function ms(f,h,e){zr();var a=f[e];if(a){for(var g=0,b=a.length;g<b;++g){var c=a[g];var d=c.t();if(is(h,d)){if(a.length==1){delete f[e];}else{a.splice(g,1);}return c.u();}}}}
function ns(c,a){zr();a=':'+a;var b=c[a];delete c[a];return b;}
function Eq(){}
_=Eq.prototype=new to();_.o=es;_.s=hs;_.w=ls;_.tI=45;_.a=null;_.b=null;_.c=0;_.d=null;var as;function ar(b,a,c){b.a=a;b.b=c;return b;}
function cr(a,b){return ar(new Fq(),a,b);}
function dr(b){var a;if(Db(b,16)){a=Cb(b,16);if(is(this.a,a.t())&&is(this.b,a.u())){return true;}}return false;}
function er(){return this.a;}
function fr(){return this.b;}
function gr(){var a,b;a=0;b=0;if(this.a!==null){a=this.a.hC();}if(this.b!==null){b=this.b.hC();}return a^b;}
function Fq(){}
_=Fq.prototype=new Dm();_.eQ=dr;_.t=er;_.u=fr;_.hC=gr;_.tI=46;_.a=null;_.b=null;function qr(b,a){b.a=a;return b;}
function sr(a){return jr(new ir(),a.a);}
function tr(c){var a,b,d;if(Db(c,16)){a=Cb(c,16);b=a.t();if(Ar(this.a,b)){d=Dr(this.a,b);return is(a.u(),d);}}return false;}
function ur(){return sr(this);}
function vr(){return this.a.c;}
function hr(){}
_=hr.prototype=new zp();_.p=tr;_.y=ur;_.eb=vr;_.tI=47;function jr(c,b){var a;c.c=b;a=aq(new Ep());if(c.c.b!==(zr(),as)){bq(a,ar(new Fq(),null,c.c.b));}cs(c.c.d,a);bs(c.c.a,a);c.a=mo(a);return c;}
function lr(a){return fo(a.a);}
function mr(a){return a.b=Cb(go(a.a),16);}
function nr(a){if(a.b===null){throw wm(new vm(),'Must call next() before remove().');}else{ho(a.a);Fr(a.c,a.b.t());a.b=null;}}
function or(){return lr(this);}
function pr(){return mr(this);}
function ir(){}
_=ir.prototype=new Dm();_.x=or;_.A=pr;_.tI=0;_.a=null;_.b=null;function ss(){}
_=ss.prototype=new bn();_.tI=48;function hm(){z(new l());}
function gwtOnLoad(b,d,c){$moduleName=d;$moduleBase=c;if(b)try{hm();}catch(a){b(d);}else{hm();}}
var bc=[{},{},{1:1},{6:1},{6:1},{6:1},{3:1},{3:1},{3:1},{3:1},{2:1},{2:1,4:1},{2:1},{5:1},{7:1,9:1,10:1,11:1},{7:1,9:1,10:1,11:1},{7:1,9:1,10:1,11:1},{7:1,9:1,10:1,11:1},{7:1,9:1,10:1,11:1},{7:1,9:1,10:1,11:1},{7:1,9:1,10:1,11:1},{12:1},{12:1},{12:1},{7:1,9:1,10:1,11:1},{7:1,9:1,10:1,11:1},{7:1,9:1,10:1,11:1},{7:1,9:1,10:1,11:1},{7:1,8:1,9:1,10:1,11:1},{5:1},{7:1,9:1,10:1,11:1},{7:1,9:1,10:1,11:1},{7:1,9:1,10:1,11:1},{7:1,9:1,10:1,11:1},{3:1},{3:1},{3:1},{3:1},{3:1},{3:1},{3:1},{13:1},{14:1},{14:1},{15:1},{13:1},{16:1},{14:1},{3:1}];if (com_google_MyDemo) {  var __gwt_initHandlers = com_google_MyDemo.__gwt_initHandlers;  com_google_MyDemo.onScriptLoad(gwtOnLoad);}})();