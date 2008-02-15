(function(){var $wnd = window;var $doc = $wnd.document;var $moduleName, $moduleBase;var _,ts='com.google.gwt.core.client.',us='com.google.gwt.demos.titledpanel.client.',vs='com.google.gwt.lang.',ws='com.google.gwt.user.client.',xs='com.google.gwt.user.client.impl.',ys='com.google.gwt.user.client.ui.',zs='com.google.gwt.user.client.ui.impl.',As='com.google.gwt.widgetideas.client.',Bs='java.lang.',Cs='java.util.';function ss(){}
function bn(a){return this===a;}
function cn(){return qn(this);}
function Fm(){}
_=Fm.prototype={};_.eQ=bn;_.hC=cn;_.tI=1;var o=null;function r(a){return a==null?0:a.$H?a.$H:(a.$H=t());}
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
_=v.prototype=new Fm();_.eQ=C;_.hC=D;_.tI=7;function mb(b){var a,c;b.a=im(new bm(),'Hello World');xk(b.a,'500px');nb(b,ri(new vg(),'This is a widget in the TitledPanel'));gf(qj(),b.a);a=mg(new kg(),3,3);gi(a,1);ii(a,3);gf(qj(),ri(new vg(),'<BR><BR>'));gf(qj(),a);c=qk(new ik());nk(c,b.a.b);mi(a,0,0,sf(new lf(),'Set Title',ab(new F(),b,c)));mi(a,0,1,c);ki(a,0,2,'Change the title dynamically');mi(a,1,0,sf(new lf(),'Clear Title',eb(new db(),b)));ki(a,1,1,'<BR>');ki(a,1,2,'Clear out the title of the box.  Note that this is different from setting the title to an empty string.  When you set the title to an empty string, you will see a small gap where the text would normally go.  Clearing the title effectively creates a bordered widget.');ki(a,2,1,'<BR>');ki(a,2,2,'The TitledPanel is a type of SimplePanel, so you can add any widget to it.');mi(a,2,0,sf(new lf(),'Set Widget',ib(new hb(),b)));}
function nb(b,a){Ej(b.a,a);wk(a,'titledPanelDemoContents');}
function E(){}
_=E.prototype=new Fm();_.tI=0;_.a=null;function ab(b,a,c){b.a=a;b.b=c;return b;}
function cb(a){km(this.a.a,mk(this.b));}
function F(){}
_=F.prototype=new Fm();_.C=cb;_.tI=8;function eb(b,a){b.a=a;return b;}
function gb(a){km(this.a.a,null);}
function db(){}
_=db.prototype=new Fm();_.C=gb;_.tI=9;function ib(b,a){b.a=a;return b;}
function kb(c){var a,b;a=sq(new rq());b=ri(new vg(),'This is a new widget created on:<BR> '+wq(a));nb(this.a,b);}
function hb(){}
_=hb.prototype=new Fm();_.C=kb;_.tI=10;function pb(c,a,d,b,e){c.a=a;c.b=b;e;c.tI=d;return c;}
function rb(a,b,c){return a[b]=c;}
function sb(b,a){return b[a];}
function ub(b,a){return b[a];}
function tb(a){return a.length;}
function wb(e,d,c,b,a){return vb(e,d,c,b,0,tb(b),a);}
function vb(j,i,g,c,e,a,b){var d,f,h;if((f=sb(c,e))<0){throw new Dm();}h=pb(new ob(),f,sb(i,e),sb(g,e),j);++e;if(e<a){j=jn(j,1);for(d=0;d<f;++d){rb(h,d,vb(j,i,g,c,e,a,b));}}else{for(d=0;d<f;++d){rb(h,d,b);}}return h;}
function xb(f,e,c,g){var a,b,d;b=tb(g);d=pb(new ob(),b,e,c,f);for(a=0;a<b;++a){rb(d,a,ub(g,a));}return d;}
function yb(a,b,c){if(c!==null&&a.b!=0&& !Db(c,a.b)){throw new nm();}return rb(a,b,c);}
function ob(){}
_=ob.prototype=new Fm();_.tI=0;function Bb(b,a){return !(!(b&&bc[b][a]));}
function Cb(b,a){if(b!=null)Bb(b.tI,a)||ac();return b;}
function Db(b,a){return b!=null&&Bb(b.tI,a);}
function Eb(a){return ~(~a);}
function ac(){throw new qm();}
function Fb(a){if(a!==null){throw new qm();}return a;}
function cc(b,d){_=d.prototype;if(b&& !(b.tI>=_.tI)){var c=b.toString;for(var a in _){b[a]=_[a];}b.toString=c;}return b;}
var bc;function gc(){gc=ss;Fc=Cp(new Ap());{Ac=new ge();le(Ac);}}
function hc(b,a){gc();we(Ac,b,a);}
function ic(a,b){gc();return je(Ac,a,b);}
function jc(){gc();return ye(Ac,'button');}
function kc(){gc();return ye(Ac,'div');}
function lc(a){gc();return ye(Ac,a);}
function mc(){gc();return ze(Ac,'text');}
function nc(){gc();return ye(Ac,'tbody');}
function oc(){gc();return ye(Ac,'td');}
function pc(){gc();return ye(Ac,'table');}
function sc(b,a,d){gc();var c;c=o;{rc(b,a,d);}}
function rc(b,a,c){gc();var d;if(a===Ec){if(uc(b)==8192){Ec=null;}}d=qc;qc=b;try{c.B(b);}finally{qc=d;}}
function tc(b,a){gc();Ae(Ac,b,a);}
function uc(a){gc();return Be(Ac,a);}
function vc(a){gc();qe(Ac,a);}
function wc(a,b){gc();return Ce(Ac,a,b);}
function xc(a){gc();return De(Ac,a);}
function yc(a){gc();return re(Ac,a);}
function zc(a){gc();return se(Ac,a);}
function Bc(c,a,b){gc();ue(Ac,c,a,b);}
function Cc(a){gc();var b,c;c=true;if(Fc.b>0){b=Fb(aq(Fc,Fc.b-1));if(!(c=null.gb())){tc(a,true);vc(a);}}return c;}
function Dc(b,a){gc();Ee(Ac,b,a);}
function bd(a,b,c){gc();af(Ac,a,b,c);}
function ad(a,b,c){gc();Fe(Ac,a,b,c);}
function cd(a,b){gc();bf(Ac,a,b);}
function dd(a,b){gc();cf(Ac,a,b);}
function ed(b,a,c){gc();df(Ac,b,a,c);}
function fd(a,b){gc();ne(Ac,a,b);}
var qc=null,Ac=null,Ec=null,Fc;function id(a){if(Db(a,4)){return ic(this,Cb(a,4));}return x(cc(this,gd),a);}
function jd(){return y(cc(this,gd));}
function gd(){}
_=gd.prototype=new v();_.eQ=id;_.hC=jd;_.tI=11;function nd(a){return x(cc(this,kd),a);}
function od(){return y(cc(this,kd));}
function kd(){}
_=kd.prototype=new v();_.eQ=nd;_.hC=od;_.tI=12;function ud(){ud=ss;wd=Cp(new Ap());{vd();}}
function vd(){ud();Ad(new qd());}
var wd;function sd(){while((ud(),wd).b>0){Fb(aq((ud(),wd),0)).gb();}}
function td(){return null;}
function qd(){}
_=qd.prototype=new Fm();_.F=sd;_.ab=td;_.tI=13;function zd(){zd=ss;Bd=Cp(new Ap());de=Cp(new Ap());{Fd();}}
function Ad(a){zd();Dp(Bd,a);}
function Cd(){zd();var a,b;for(a=io(Bd);ao(a);){b=Cb(bo(a),5);b.F();}}
function Dd(){zd();var a,b,c,d;d=null;for(a=io(Bd);ao(a);){b=Cb(bo(a),5);c=b.ab();{d=c;}}return d;}
function Ed(){zd();var a,b;for(a=io(de);ao(a);){b=Fb(bo(a));null.gb();}}
function Fd(){zd();__gwt_initHandlers(function(){ce();},function(){return be();},function(){ae();$wnd.onresize=null;$wnd.onbeforeclose=null;$wnd.onclose=null;});}
function ae(){zd();var a;a=o;{Cd();}}
function be(){zd();var a;a=o;{return Dd();}}
function ce(){zd();var a;a=o;{Ed();}}
var Bd,de;function we(c,b,a){b.appendChild(a);}
function ye(b,a){return $doc.createElement(a);}
function ze(b,c){var a=$doc.createElement('INPUT');a.type=c;return a;}
function Ae(c,b,a){b.cancelBubble=a;}
function Be(b,a){switch(a.type){case 'blur':return 4096;case 'change':return 1024;case 'click':return 1;case 'dblclick':return 2;case 'focus':return 2048;case 'keydown':return 128;case 'keypress':return 256;case 'keyup':return 512;case 'load':return 32768;case 'losecapture':return 8192;case 'mousedown':return 4;case 'mousemove':return 64;case 'mouseout':return 32;case 'mouseover':return 16;case 'mouseup':return 8;case 'scroll':return 16384;case 'error':return 65536;case 'mousewheel':return 131072;case 'DOMMouseScroll':return 131072;}}
function Ce(d,a,b){var c=a[b];return c==null?null:String(c);}
function De(b,a){return a.__eventBits||0;}
function Ee(c,b,a){b.removeChild(a);}
function af(c,a,b,d){a[b]=d;}
function Fe(c,a,b,d){a[b]=d;}
function bf(c,a,b){a.__listener=b;}
function cf(c,a,b){if(!b){b='';}a.innerHTML=b;}
function df(c,b,a,d){b.style[a]=d;}
function ee(){}
_=ee.prototype=new Fm();_.tI=0;function qe(b,a){a.preventDefault();}
function re(c,b){var a=b.firstChild;while(a&&a.nodeType!=1)a=a.nextSibling;return a||null;}
function se(c,a){var b=a.parentNode;if(b==null){return null;}if(b.nodeType!=1)b=null;return b||null;}
function te(d){$wnd.__dispatchCapturedMouseEvent=function(b){if($wnd.__dispatchCapturedEvent(b)){var a=$wnd.__captureElem;if(a&&a.__listener){sc(b,a,a.__listener);b.stopPropagation();}}};$wnd.__dispatchCapturedEvent=function(a){if(!Cc(a)){a.stopPropagation();a.preventDefault();return false;}return true;};$wnd.addEventListener('click',$wnd.__dispatchCapturedMouseEvent,true);$wnd.addEventListener('dblclick',$wnd.__dispatchCapturedMouseEvent,true);$wnd.addEventListener('mousedown',$wnd.__dispatchCapturedMouseEvent,true);$wnd.addEventListener('mouseup',$wnd.__dispatchCapturedMouseEvent,true);$wnd.addEventListener('mousemove',$wnd.__dispatchCapturedMouseEvent,true);$wnd.addEventListener('mousewheel',$wnd.__dispatchCapturedMouseEvent,true);$wnd.addEventListener('keydown',$wnd.__dispatchCapturedEvent,true);$wnd.addEventListener('keyup',$wnd.__dispatchCapturedEvent,true);$wnd.addEventListener('keypress',$wnd.__dispatchCapturedEvent,true);$wnd.__dispatchEvent=function(b){var c,a=this;while(a&& !(c=a.__listener))a=a.parentNode;if(a&&a.nodeType!=1)a=null;if(c)sc(b,a,c);};$wnd.__captureElem=null;}
function ue(f,e,g,d){var c=0,b=e.firstChild,a=null;while(b){if(b.nodeType==1){if(c==d){a=b;break;}++c;}b=b.nextSibling;}e.insertBefore(g,a);}
function ve(c,b,a){b.__eventBits=a;b.onclick=a&1?$wnd.__dispatchEvent:null;b.ondblclick=a&2?$wnd.__dispatchEvent:null;b.onmousedown=a&4?$wnd.__dispatchEvent:null;b.onmouseup=a&8?$wnd.__dispatchEvent:null;b.onmouseover=a&16?$wnd.__dispatchEvent:null;b.onmouseout=a&32?$wnd.__dispatchEvent:null;b.onmousemove=a&64?$wnd.__dispatchEvent:null;b.onkeydown=a&128?$wnd.__dispatchEvent:null;b.onkeypress=a&256?$wnd.__dispatchEvent:null;b.onkeyup=a&512?$wnd.__dispatchEvent:null;b.onchange=a&1024?$wnd.__dispatchEvent:null;b.onfocus=a&2048?$wnd.__dispatchEvent:null;b.onblur=a&4096?$wnd.__dispatchEvent:null;b.onlosecapture=a&8192?$wnd.__dispatchEvent:null;b.onscroll=a&16384?$wnd.__dispatchEvent:null;b.onload=a&32768?$wnd.__dispatchEvent:null;b.onerror=a&65536?$wnd.__dispatchEvent:null;b.onmousewheel=a&131072?$wnd.__dispatchEvent:null;}
function oe(){}
_=oe.prototype=new ee();_.tI=0;function je(c,a,b){if(!a&& !b){return true;}else if(!a|| !b){return false;}return a.isSameNode(b);}
function le(a){te(a);ke(a);}
function ke(d){$wnd.addEventListener('mouseout',function(b){var a=$wnd.__captureElem;if(a&& !b.relatedTarget){if('html'==b.target.tagName.toLowerCase()){var c=$doc.createEvent('MouseEvents');c.initMouseEvent('mouseup',true,true,$wnd,0,b.screenX,b.screenY,b.clientX,b.clientY,b.ctrlKey,b.altKey,b.shiftKey,b.metaKey,b.button,null);a.dispatchEvent(c);}}},true);$wnd.addEventListener('DOMMouseScroll',$wnd.__dispatchCapturedMouseEvent,true);}
function ne(c,b,a){ve(c,b,a);me(c,b,a);}
function me(c,b,a){if(a&131072){b.addEventListener('DOMMouseScroll',$wnd.__dispatchEvent,false);}}
function fe(){}
_=fe.prototype=new oe();_.tI=0;function ge(){}
_=ge.prototype=new fe();_.tI=0;function uk(d,b,a){var c=b.parentNode;if(!c){return;}c.insertBefore(a,b);c.removeChild(b);}
function vk(b,a){if(b.k!==null){uk(b,b.k,a);}b.k=a;}
function wk(b,a){zk(b.k,a);}
function xk(a,b){ed(a.k,'width',b);}
function yk(b,a){fd(b.k,a|xc(b.k));}
function zk(a,b){bd(a,'className',b);}
function sk(){}
_=sk.prototype=new Fm();_.tI=0;_.k=null;function kl(a){if(a.i){throw ym(new xm(),"Should only call onAttach when the widget is detached from the browser's document");}a.i=true;cd(a.k,a);a.q();a.D();}
function ll(a){if(!a.i){throw ym(new xm(),"Should only call onDetach when the widget is attached to the browser's document");}try{a.E();}finally{a.r();cd(a.k,null);a.i=false;}}
function ml(a){if(a.j!==null){a.j.cb(a);}else if(a.j!==null){throw ym(new xm(),"This widget's parent does not implement HasWidgets");}}
function nl(b,a){if(b.i){cd(b.k,null);}vk(b,a);if(b.i){cd(a,b);}}
function ol(c,b){var a;a=c.j;if(b===null){if(a!==null&&a.i){ll(c);}c.j=null;}else{if(a!==null){throw ym(new xm(),'Cannot set a new parent without first clearing the old parent');}c.j=b;if(b.i){kl(c);}}}
function pl(){}
function ql(){}
function rl(a){}
function sl(){}
function tl(){}
function ul(a){nl(this,a);}
function Ak(){}
_=Ak.prototype=new sk();_.q=pl;_.r=ql;_.B=rl;_.D=sl;_.E=tl;_.db=ul;_.tI=14;_.i=false;_.j=null;function bj(b,a){ol(a,b);}
function dj(b,a){ol(a,null);}
function ej(){var a,b;for(b=this.y();b.x();){a=Cb(b.A(),7);kl(a);}}
function fj(){var a,b;for(b=this.y();b.x();){a=Cb(b.A(),7);ll(a);}}
function gj(){}
function hj(){}
function aj(){}
_=aj.prototype=new Ak();_.q=ej;_.r=fj;_.D=gj;_.E=hj;_.tI=15;function Bf(a){a.a=bl(new Bk(),a);}
function Cf(a){Bf(a);return a;}
function Df(c,a,b){ml(a);cl(c.a,a);hc(b,a.k);bj(c,a);}
function Ff(b,c){var a;if(c.j!==b){return false;}dj(b,c);a=c.k;Dc(zc(a),a);il(b.a,c);return true;}
function ag(){return gl(this.a);}
function bg(a){return Ff(this,a);}
function Af(){}
_=Af.prototype=new aj();_.y=ag;_.cb=bg;_.tI=16;function ff(a){Cf(a);a.db(kc());ed(a.k,'position','relative');ed(a.k,'overflow','hidden');return a;}
function gf(a,b){Df(a,b,a.k);}
function jf(a){ed(a,'left','');ed(a,'top','');ed(a,'position','');}
function kf(b){var a;a=Ff(this,b);if(a){jf(b.k);}return a;}
function ef(){}
_=ef.prototype=new Af();_.cb=kf;_.tI=17;function eg(){eg=ss;El(),am;}
function dg(b,a){El(),am;gg(b,a);return b;}
function fg(b,a){switch(uc(a)){case 1:if(b.b!==null){yf(b.b,b);}break;case 4096:case 2048:break;case 128:case 512:case 256:break;}}
function gg(b,a){nl(b,a);yk(b,7041);}
function hg(a){if(this.b===null){this.b=wf(new vf());}Dp(this.b,a);}
function ig(a){fg(this,a);}
function jg(a){gg(this,a);}
function cg(){}
_=cg.prototype=new Ak();_.l=hg;_.B=ig;_.db=jg;_.tI=18;_.b=null;function of(){of=ss;El(),am;}
function nf(b,a){El(),am;dg(b,a);return b;}
function pf(b,a){dd(b.k,a);}
function mf(){}
_=mf.prototype=new cg();_.tI=19;function tf(){tf=ss;El(),am;}
function qf(a){El(),am;nf(a,jc());uf(a.k);wk(a,'gwt-Button');return a;}
function rf(b,a){El(),am;qf(b);pf(b,a);return b;}
function sf(c,a,b){El(),am;rf(c,a);c.l(b);return c;}
function uf(b){tf();if(b.type=='submit'){try{b.setAttribute('type','button');}catch(a){}}}
function lf(){}
_=lf.prototype=new mf();_.tI=20;function yn(d,a,b){var c;while(a.x()){c=a.A();if(b===null?c===null:b.eQ(c)){return a;}}return null;}
function An(a){throw vn(new un(),'add');}
function Bn(b){var a;a=yn(this,this.y(),b);return a!==null;}
function xn(){}
_=xn.prototype=new Fm();_.n=An;_.p=Bn;_.tI=0;function ho(b,a){throw Bm(new Am(),'Index: '+a+', Size: '+b.b);}
function io(a){return En(new Dn(),a);}
function jo(b,a){throw vn(new un(),'add');}
function ko(a){this.m(this.eb(),a);return true;}
function lo(e){var a,b,c,d,f;if(e===this){return true;}if(!Db(e,12)){return false;}f=Cb(e,12);if(this.eb()!=f.eb()){return false;}c=io(this);d=f.y();while(ao(c)){a=bo(c);b=bo(d);if(!(a===null?b===null:a.eQ(b))){return false;}}return true;}
function mo(){var a,b,c,d;c=1;a=31;b=io(this);while(ao(b)){d=bo(b);c=31*c+(d===null?0:d.hC());}return c;}
function no(){return io(this);}
function oo(a){throw vn(new un(),'remove');}
function Cn(){}
_=Cn.prototype=new xn();_.m=jo;_.n=ko;_.eQ=lo;_.hC=mo;_.y=no;_.bb=oo;_.tI=21;function Bp(a){{Ep(a);}}
function Cp(a){Bp(a);return a;}
function Dp(b,a){oq(b.a,b.b++,a);return true;}
function Ep(a){a.a=z();a.b=0;}
function aq(b,a){if(a<0||a>=b.b){ho(b,a);}return kq(b.a,a);}
function bq(b,a){return cq(b,a,0);}
function cq(c,b,a){if(a<0){ho(c,a);}for(;a<c.b;++a){if(jq(b,kq(c.a,a))){return a;}}return (-1);}
function dq(c,a){var b;b=aq(c,a);mq(c.a,a,1);--c.b;return b;}
function eq(d,a,b){var c;c=aq(d,a);oq(d.a,a,b);return c;}
function gq(a,b){if(a<0||a>this.b){ho(this,a);}fq(this.a,a,b);++this.b;}
function hq(a){return Dp(this,a);}
function fq(a,b,c){a.splice(b,0,c);}
function iq(a){return bq(this,a)!=(-1);}
function jq(a,b){return a===b||a!==null&&a.eQ(b);}
function lq(a){return aq(this,a);}
function kq(a,b){return a[b];}
function nq(a){return dq(this,a);}
function mq(a,c,b){a.splice(c,b);}
function oq(a,b,c){a[b]=c;}
function pq(){return this.b;}
function Ap(){}
_=Ap.prototype=new Cn();_.m=gq;_.n=hq;_.p=iq;_.v=lq;_.bb=nq;_.eb=pq;_.tI=22;_.a=null;_.b=0;function wf(a){Cp(a);return a;}
function yf(d,c){var a,b;for(a=io(d);ao(a);){b=Cb(bo(a),6);b.C(c);}}
function vf(){}
_=vf.prototype=new Ap();_.tI=23;function Ah(a){a.h=qh(new lh());}
function Bh(a){Ah(a);a.g=pc();a.c=nc();hc(a.g,a.c);a.db(a.g);yk(a,1);return a;}
function Ch(d,c,b){var a;Dh(d,c);if(b<0){throw Bm(new Am(),'Column '+b+' must be non-negative: '+b);}a=d.a;if(a<=b){throw Bm(new Am(),'Column index: '+b+', Column size: '+d.a);}}
function Dh(c,a){var b;b=c.b;if(a>=b||a<0){throw Bm(new Am(),'Row index: '+a+', Row size: '+b);}}
function Eh(e,c,b,a){var d;d=dh(e.d,c,b);ci(e,d,a);return d;}
function ai(a){return oc();}
function bi(d,b,a){var c,e;e=kh(d.f,d.c,b);c=og(d);Bc(e,c,a);}
function ci(d,c,a){var b,e;b=yc(c);e=null;if(b!==null){e=sh(d.h,b);}if(e!==null){fi(d,e);return true;}else{if(a){dd(c,'');}return false;}}
function fi(b,c){var a;if(c.j!==b){return false;}dj(b,c);a=c.k;Dc(zc(a),a);vh(b.h,a);return true;}
function di(d,b,a){var c,e;Ch(d,b,a);c=Eh(d,b,a,false);e=kh(d.f,d.c,b);Dc(e,c);}
function ei(d,c){var a,b;b=d.a;for(a=0;a<b;++a){Eh(d,c,a,false);}Dc(d.c,kh(d.f,d.c,c));}
function gi(a,b){bd(a.g,'border',''+b);}
function hi(b,a){b.d=a;}
function ii(b,a){ad(b.g,'cellPadding',a);}
function ji(b,a){b.e=a;hh(b.e);}
function ki(e,c,a,b){var d;pg(e,c,a);d=Eh(e,c,a,b===null);if(b!==null){dd(d,b);}}
function li(b,a){b.f=a;}
function mi(d,b,a,e){var c;pg(d,b,a);if(e!==null){ml(e);c=Eh(d,b,a,true);th(d.h,e);hc(c,e.k);bj(d,e);}}
function ni(){return wh(this.h);}
function oi(a){switch(uc(a)){case 1:{break;}default:}}
function pi(a){return fi(this,a);}
function wg(){}
_=wg.prototype=new aj();_.y=ni;_.B=oi;_.cb=pi;_.tI=24;_.c=null;_.d=null;_.e=null;_.f=null;_.g=null;function lg(a){Bh(a);hi(a,ah(new Fg(),a));li(a,new ih());ji(a,fh(new eh(),a));return a;}
function mg(c,b,a){lg(c);tg(c,b,a);return c;}
function og(b){var a;a=ai(b);dd(a,'&nbsp;');return a;}
function pg(c,b,a){qg(c,b);if(a<0){throw Bm(new Am(),'Cannot access a column with a negative index: '+a);}if(a>=c.a){throw Bm(new Am(),'Column index: '+a+', Column size: '+c.a);}}
function qg(b,a){if(a<0){throw Bm(new Am(),'Cannot access a row with a negative index: '+a);}if(a>=b.b){throw Bm(new Am(),'Row index: '+a+', Row size: '+b.b);}}
function tg(c,b,a){rg(c,a);sg(c,b);}
function rg(d,a){var b,c;if(d.a==a){return;}if(a<0){throw Bm(new Am(),'Cannot set number of columns to '+a);}if(d.a>a){for(b=0;b<d.b;b++){for(c=d.a-1;c>=a;c--){di(d,b,c);}}}else{for(b=0;b<d.b;b++){for(c=d.a;c<a;c++){bi(d,b,c);}}}d.a=a;}
function sg(b,a){if(b.b==a){return;}if(a<0){throw Bm(new Am(),'Cannot set number of rows to '+a);}if(b.b<a){ug(b.c,a-b.b,b.a);b.b=a;}else{while(b.b>a){ei(b,--b.b);}}}
function ug(g,f,c){var h=$doc.createElement('td');h.innerHTML='&nbsp;';var d=$doc.createElement('tr');for(var b=0;b<c;b++){var a=h.cloneNode(true);d.appendChild(a);}g.appendChild(d);for(var e=1;e<f;e++){g.appendChild(d.cloneNode(true));}}
function kg(){}
_=kg.prototype=new wg();_.tI=25;_.a=0;_.b=0;function Di(a){a.db(kc());yk(a,131197);wk(a,'gwt-Label');return a;}
function Fi(a){switch(uc(a)){case 1:break;case 4:case 8:case 64:case 16:case 32:break;case 131072:break;}}
function Ci(){}
_=Ci.prototype=new Ak();_.B=Fi;_.tI=26;function qi(a){Di(a);a.db(kc());yk(a,125);wk(a,'gwt-HTML');return a;}
function ri(b,a){qi(b);ti(b,a);return b;}
function ti(b,a){dd(b.k,a);}
function vg(){}
_=vg.prototype=new Ci();_.tI=27;function yg(a){{Bg(a);}}
function zg(b,a){b.b=a;yg(b);return b;}
function Bg(a){while(++a.a<a.b.b.b){if(aq(a.b.b,a.a)!==null){return;}}}
function Cg(a){return a.a<a.b.b.b;}
function Dg(){return Cg(this);}
function Eg(){var a;if(!Cg(this)){throw new os();}a=aq(this.b.b,this.a);Bg(this);return a;}
function xg(){}
_=xg.prototype=new Fm();_.x=Dg;_.A=Eg;_.tI=0;_.a=(-1);function ah(b,a){b.a=a;return b;}
function ch(e,d,c,a){var b=d.rows[c].cells[a];return b==null?null:b;}
function dh(c,b,a){return ch(c,c.a.c,b,a);}
function Fg(){}
_=Fg.prototype=new Fm();_.tI=0;function fh(b,a){b.b=a;return b;}
function hh(a){if(a.a===null){a.a=lc('colgroup');Bc(a.b.g,a.a,0);hc(a.a,lc('col'));}}
function eh(){}
_=eh.prototype=new Fm();_.tI=0;_.a=null;function kh(c,a,b){return a.rows[b];}
function ih(){}
_=ih.prototype=new Fm();_.tI=0;function ph(a){a.b=Cp(new Ap());}
function qh(a){ph(a);return a;}
function sh(c,a){var b;b=yh(a);if(b<0){return null;}return Cb(aq(c.b,b),7);}
function th(b,c){var a;if(b.a===null){a=b.b.b;Dp(b.b,c);}else{a=b.a.a;eq(b.b,a,c);b.a=b.a.b;}zh(c.k,a);}
function uh(c,a,b){xh(a);eq(c.b,b,null);c.a=nh(new mh(),b,c.a);}
function vh(c,a){var b;b=yh(a);uh(c,a,b);}
function wh(a){return zg(new xg(),a);}
function xh(a){a['__widgetID']=null;}
function yh(a){var b=a['__widgetID'];return b==null?-1:b;}
function zh(a,b){a['__widgetID']=b;}
function lh(){}
_=lh.prototype=new Fm();_.tI=0;_.a=null;function nh(c,a,b){c.a=a;c.b=b;return c;}
function mh(){}
_=mh.prototype=new Fm();_.tI=0;_.a=0;_.b=null;function oj(){oj=ss;tj=tr(new Aq());}
function nj(b,a){oj();ff(b);if(a===null){a=pj();}b.db(a);kl(b);return b;}
function qj(){oj();return rj(null);}
function rj(c){oj();var a,b;b=Cb(zr(tj,c),8);if(b!==null){return b;}a=null;if(tj.c==0){sj();}Ar(tj,c,b=nj(new ij(),a));return b;}
function pj(){oj();return $doc.body;}
function sj(){oj();Ad(new jj());}
function ij(){}
_=ij.prototype=new ef();_.tI=28;var tj;function lj(){var a,b;for(b=bp(pp((oj(),tj)));ip(b);){a=Cb(jp(b),8);if(a.i){ll(a);}}}
function mj(){return null;}
function jj(){}
_=jj.prototype=new Fm();_.F=lj;_.ab=mj;_.tI=29;function Bj(b,a){b.db(a);return b;}
function Dj(a,b){if(a.c!==b){return false;}dj(a,b);Dc(a.k,b.k);a.c=null;return true;}
function Ej(a,b){if(b===a.c){return;}if(b!==null){ml(b);}if(a.c!==null){Dj(a,a.c);}a.c=b;if(b!==null){hc(a.k,a.c.k);bj(a,b);}}
function Fj(){return xj(new vj(),this);}
function ak(a){return Dj(this,a);}
function uj(){}
_=uj.prototype=new aj();_.y=Fj;_.cb=ak;_.tI=30;_.c=null;function wj(a){a.a=a.b.c!==null;}
function xj(b,a){b.b=a;wj(b);return b;}
function zj(){return this.a;}
function Aj(){if(!this.a||this.b.c===null){throw new os();}this.a=false;return this.b.c;}
function vj(){}
_=vj.prototype=new Fm();_.x=zj;_.A=Aj;_.tI=0;function lk(){lk=ss;El(),am;}
function kk(b,a){El(),am;dg(b,a);yk(b,1024);return b;}
function mk(a){return wc(a.k,'value');}
function nk(b,a){bd(b.k,'value',a!==null?a:'');}
function ok(a){if(this.a===null){this.a=wf(new vf());}Dp(this.a,a);}
function pk(a){var b;fg(this,a);b=uc(a);if(b==1){if(this.a!==null){yf(this.a,this);}}else{}}
function jk(){}
_=jk.prototype=new cg();_.l=ok;_.B=pk;_.tI=31;_.a=null;function rk(){rk=ss;El(),am;}
function qk(a){El(),am;kk(a,mc());wk(a,'gwt-TextBox');return a;}
function ik(){}
_=ik.prototype=new jk();_.tI=32;function bl(b,a){b.a=wb('[Lcom.google.gwt.user.client.ui.Widget;',[0],[7],[4],null);return b;}
function cl(a,b){fl(a,b,a.b);}
function el(b,c){var a;for(a=0;a<b.b;++a){if(b.a[a]===c){return a;}}return (-1);}
function fl(d,e,a){var b,c;if(a<0||a>d.b){throw new Am();}if(d.b==d.a.a){c=wb('[Lcom.google.gwt.user.client.ui.Widget;',[0],[7],[d.a.a*2],null);for(b=0;b<d.a.a;++b){yb(c,b,d.a[b]);}d.a=c;}++d.b;for(b=d.b-1;b>a;--b){yb(d.a,b,d.a[b-1]);}yb(d.a,a,e);}
function gl(a){return Dk(new Ck(),a);}
function hl(c,b){var a;if(b<0||b>=c.b){throw new Am();}--c.b;for(a=b;a<c.b;++a){yb(c.a,a,c.a[a+1]);}yb(c.a,c.b,null);}
function il(b,c){var a;a=el(b,c);if(a==(-1)){throw new os();}hl(b,a);}
function Bk(){}
_=Bk.prototype=new Fm();_.tI=0;_.a=null;_.b=0;function Dk(b,a){b.b=a;return b;}
function Fk(){return this.a<this.b.b-1;}
function al(){if(this.a>=this.b.b){throw new os();}return this.b.a[++this.a];}
function Ck(){}
_=Ck.prototype=new Fm();_.x=Fk;_.A=al;_.tI=0;_.a=(-1);function El(){El=ss;Fl=yl(new wl());am=Fl!==null?Dl(new vl()):Fl;}
function Dl(a){El();return a;}
function vl(){}
_=vl.prototype=new Fm();_.tI=0;var Fl,am;function zl(){zl=ss;El();}
function xl(a){Al(a);Bl(a);Cl(a);}
function yl(a){zl();Dl(a);xl(a);return a;}
function Al(b){return function(a){if(this.parentNode.onblur){this.parentNode.onblur(a);}};}
function Bl(b){return function(a){if(this.parentNode.onfocus){this.parentNode.onfocus(a);}};}
function Cl(a){return function(){this.firstChild.focus();};}
function wl(){}
_=wl.prototype=new vl();_.tI=0;function jm(){jm=ss;lm=new dm();}
function im(a,b){jm();Bj(a,lc('fieldset'));a.a=lc('legend');hc(a.k,a.a);km(a,b);return a;}
function km(a,b){a.b=b;fm(lm,a.k,a.a,b);}
function bm(){}
_=bm.prototype=new uj();_.tI=33;_.a=null;_.b=null;var lm;function hm(c,a,b,d){if(d!==''&&d!==null){dd(b,d);Bc(a,b,0);}else if(zc(b)!==null){Dc(a,b);}}
function cm(){}
_=cm.prototype=new Fm();_.tI=0;function fm(c,a,b,d){ed(a,'display','none');hm(c,a,b,d);ed(a,'display','');}
function dm(){}
_=dm.prototype=new cm();_.tI=0;function sn(b,a){a;return b;}
function rn(){}
_=rn.prototype=new Fm();_.tI=3;function vm(b,a){sn(b,a);return b;}
function um(){}
_=um.prototype=new rn();_.tI=4;function en(b,a){vm(b,a);return b;}
function dn(){}
_=dn.prototype=new um();_.tI=5;function nm(){}
_=nm.prototype=new dn();_.tI=34;function qm(){}
_=qm.prototype=new dn();_.tI=35;function ym(b,a){en(b,a);return b;}
function xm(){}
_=xm.prototype=new dn();_.tI=36;function Bm(b,a){en(b,a);return b;}
function Am(){}
_=Am.prototype=new dn();_.tI=37;function Dm(){}
_=Dm.prototype=new dn();_.tI=38;function jn(b,a){return b.substr(a,b.length-a);}
function kn(a,b){return String(a)==b;}
function ln(a){if(!Db(a,1))return false;return kn(this,a);}
function nn(){var a=mn;if(!a){a=mn={};}var e=':'+this;var b=a[e];if(b==null){b=0;var f=this.length;var d=f<64?1:f/32|0;for(var c=0;c<f;c+=d){b<<=1;b+=this.charCodeAt(c);}b|=0;a[e]=b;}return b;}
_=String.prototype;_.eQ=ln;_.hC=nn;_.tI=2;var mn=null;function qn(a){return s(a);}
function vn(b,a){en(b,a);return b;}
function un(){}
_=un.prototype=new dn();_.tI=39;function En(b,a){b.c=a;return b;}
function ao(a){return a.a<a.c.eb();}
function bo(a){if(!ao(a)){throw new os();}return a.c.v(a.b=a.a++);}
function co(a){if(a.b<0){throw new xm();}a.c.bb(a.b);a.a=a.b;a.b=(-1);}
function eo(){return ao(this);}
function fo(){return bo(this);}
function Dn(){}
_=Dn.prototype=new Fm();_.x=eo;_.A=fo;_.tI=0;_.a=0;_.b=(-1);function np(f,d,e){var a,b,c;for(b=or(f.s());hr(b);){a=ir(b);c=a.t();if(d===null?c===null:d.eQ(c)){if(e){jr(b);}return a;}}return null;}
function op(b){var a;a=b.s();return ro(new qo(),b,a);}
function pp(b){var a;a=yr(b);return Fo(new Eo(),b,a);}
function qp(a){return np(this,a,false)!==null;}
function rp(d){var a,b,c,e,f,g,h;if(d===this){return true;}if(!Db(d,13)){return false;}f=Cb(d,13);c=op(this);e=f.z();if(!xp(c,e)){return false;}for(a=to(c);Ao(a);){b=Bo(a);h=this.w(b);g=f.w(b);if(h===null?g!==null:!h.eQ(g)){return false;}}return true;}
function sp(b){var a;a=np(this,b,false);return a===null?null:a.u();}
function tp(){var a,b,c;b=0;for(c=or(this.s());hr(c);){a=ir(c);b+=a.hC();}return b;}
function up(){return op(this);}
function po(){}
_=po.prototype=new Fm();_.o=qp;_.eQ=rp;_.w=sp;_.hC=tp;_.z=up;_.tI=40;function xp(e,b){var a,c,d;if(b===e){return true;}if(!Db(b,14)){return false;}c=Cb(b,14);if(c.eb()!=e.eb()){return false;}for(a=c.y();a.x();){d=a.A();if(!e.p(d)){return false;}}return true;}
function yp(a){return xp(this,a);}
function zp(){var a,b,c;a=0;for(b=this.y();b.x();){c=b.A();if(c!==null){a+=c.hC();}}return a;}
function vp(){}
_=vp.prototype=new xn();_.eQ=yp;_.hC=zp;_.tI=41;function ro(b,a,c){b.a=a;b.b=c;return b;}
function to(b){var a;a=or(b.b);return yo(new xo(),b,a);}
function uo(a){return this.a.o(a);}
function vo(){return to(this);}
function wo(){return this.b.a.c;}
function qo(){}
_=qo.prototype=new vp();_.p=uo;_.y=vo;_.eb=wo;_.tI=42;function yo(b,a,c){b.a=c;return b;}
function Ao(a){return a.a.x();}
function Bo(b){var a;a=b.a.A();return a.t();}
function Co(){return Ao(this);}
function Do(){return Bo(this);}
function xo(){}
_=xo.prototype=new Fm();_.x=Co;_.A=Do;_.tI=0;function Fo(b,a,c){b.a=a;b.b=c;return b;}
function bp(b){var a;a=or(b.b);return gp(new fp(),b,a);}
function cp(a){return xr(this.a,a);}
function dp(){return bp(this);}
function ep(){return this.b.a.c;}
function Eo(){}
_=Eo.prototype=new xn();_.p=cp;_.y=dp;_.eb=ep;_.tI=0;function gp(b,a,c){b.a=c;return b;}
function ip(a){return a.a.x();}
function jp(a){var b;b=a.a.A().u();return b;}
function kp(){return ip(this);}
function lp(){return jp(this);}
function fp(){}
_=fp.prototype=new Fm();_.x=kp;_.A=lp;_.tI=0;function tq(){tq=ss;xb('[Ljava.lang.String;',0,1,['Sun','Mon','Tue','Wed','Thu','Fri','Sat']);xb('[Ljava.lang.String;',0,1,['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec']);}
function sq(a){tq();vq(a);return a;}
function uq(a){return a.jsdate.getTime();}
function vq(a){a.jsdate=new Date();}
function wq(a){return a.jsdate.toLocaleString();}
function xq(a){return Db(a,15)&&uq(this)==uq(Cb(a,15));}
function yq(){return Eb(uq(this)^uq(this)>>>32);}
function rq(){}
_=rq.prototype=new Fm();_.eQ=xq;_.hC=yq;_.tI=43;function vr(){vr=ss;Cr=cs();}
function sr(a){{ur(a);}}
function tr(a){vr();sr(a);return a;}
function ur(a){a.a=z();a.d=A();a.b=cc(Cr,v);a.c=0;}
function wr(b,a){if(Db(a,1)){return gs(b.d,Cb(a,1))!==Cr;}else if(a===null){return b.b!==Cr;}else{return fs(b.a,a,a.hC())!==Cr;}}
function xr(a,b){if(a.b!==Cr&&es(a.b,b)){return true;}else if(bs(a.d,b)){return true;}else if(Fr(a.a,b)){return true;}return false;}
function yr(a){return mr(new dr(),a);}
function zr(c,a){var b;if(Db(a,1)){b=gs(c.d,Cb(a,1));}else if(a===null){b=c.b;}else{b=fs(c.a,a,a.hC());}return b===Cr?null:b;}
function Ar(c,a,d){var b;{b=c.b;c.b=d;}if(b===Cr){++c.c;return null;}else{return b;}}
function Br(c,a){var b;if(Db(a,1)){b=js(c.d,Cb(a,1));}else if(a===null){b=c.b;c.b=cc(Cr,v);}else{b=is(c.a,a,a.hC());}if(b===Cr){return null;}else{--c.c;return b;}}
function Dr(e,c){vr();for(var d in e){if(d==parseInt(d)){var a=e[d];for(var f=0,b=a.length;f<b;++f){c.n(a[f]);}}}}
function Er(d,a){vr();for(var c in d){if(c.charCodeAt(0)==58){var e=d[c];var b=Eq(c.substring(1),e);a.n(b);}}}
function Fr(f,h){vr();for(var e in f){if(e==parseInt(e)){var a=f[e];for(var g=0,b=a.length;g<b;++g){var c=a[g];var d=c.u();if(es(h,d)){return true;}}}}return false;}
function as(a){return wr(this,a);}
function bs(c,d){vr();for(var b in c){if(b.charCodeAt(0)==58){var a=c[b];if(es(d,a)){return true;}}}return false;}
function cs(){vr();}
function ds(){return yr(this);}
function es(a,b){vr();if(a===b){return true;}else if(a===null){return false;}else{return a.eQ(b);}}
function hs(a){return zr(this,a);}
function fs(f,h,e){vr();var a=f[e];if(a){for(var g=0,b=a.length;g<b;++g){var c=a[g];var d=c.t();if(es(h,d)){return c.u();}}}}
function gs(b,a){vr();return b[':'+a];}
function is(f,h,e){vr();var a=f[e];if(a){for(var g=0,b=a.length;g<b;++g){var c=a[g];var d=c.t();if(es(h,d)){if(a.length==1){delete f[e];}else{a.splice(g,1);}return c.u();}}}}
function js(c,a){vr();a=':'+a;var b=c[a];delete c[a];return b;}
function Aq(){}
_=Aq.prototype=new po();_.o=as;_.s=ds;_.w=hs;_.tI=44;_.a=null;_.b=null;_.c=0;_.d=null;var Cr;function Cq(b,a,c){b.a=a;b.b=c;return b;}
function Eq(a,b){return Cq(new Bq(),a,b);}
function Fq(b){var a;if(Db(b,16)){a=Cb(b,16);if(es(this.a,a.t())&&es(this.b,a.u())){return true;}}return false;}
function ar(){return this.a;}
function br(){return this.b;}
function cr(){var a,b;a=0;b=0;if(this.a!==null){a=this.a.hC();}if(this.b!==null){b=this.b.hC();}return a^b;}
function Bq(){}
_=Bq.prototype=new Fm();_.eQ=Fq;_.t=ar;_.u=br;_.hC=cr;_.tI=45;_.a=null;_.b=null;function mr(b,a){b.a=a;return b;}
function or(a){return fr(new er(),a.a);}
function pr(c){var a,b,d;if(Db(c,16)){a=Cb(c,16);b=a.t();if(wr(this.a,b)){d=zr(this.a,b);return es(a.u(),d);}}return false;}
function qr(){return or(this);}
function rr(){return this.a.c;}
function dr(){}
_=dr.prototype=new vp();_.p=pr;_.y=qr;_.eb=rr;_.tI=46;function fr(c,b){var a;c.c=b;a=Cp(new Ap());if(c.c.b!==(vr(),Cr)){Dp(a,Cq(new Bq(),null,c.c.b));}Er(c.c.d,a);Dr(c.c.a,a);c.a=io(a);return c;}
function hr(a){return ao(a.a);}
function ir(a){return a.b=Cb(bo(a.a),16);}
function jr(a){if(a.b===null){throw ym(new xm(),'Must call next() before remove().');}else{co(a.a);Br(a.c,a.b.t());a.b=null;}}
function kr(){return hr(this);}
function lr(){return ir(this);}
function er(){}
_=er.prototype=new Fm();_.x=kr;_.A=lr;_.tI=0;_.a=null;_.b=null;function os(){}
_=os.prototype=new dn();_.tI=47;function mm(){mb(new E());}
function gwtOnLoad(b,d,c){$moduleName=d;$moduleBase=c;if(b)try{mm();}catch(a){b(d);}else{mm();}}
var bc=[{},{},{1:1},{3:1},{3:1},{3:1},{3:1},{2:1},{6:1},{6:1},{6:1},{2:1,4:1},{2:1},{5:1},{7:1,9:1,10:1,11:1},{7:1,9:1,10:1,11:1},{7:1,9:1,10:1,11:1},{7:1,9:1,10:1,11:1},{7:1,9:1,10:1,11:1},{7:1,9:1,10:1,11:1},{7:1,9:1,10:1,11:1},{12:1},{12:1},{12:1},{7:1,9:1,10:1,11:1},{7:1,9:1,10:1,11:1},{7:1,9:1,10:1,11:1},{7:1,9:1,10:1,11:1},{7:1,8:1,9:1,10:1,11:1},{5:1},{7:1,9:1,10:1,11:1},{7:1,9:1,10:1,11:1},{7:1,9:1,10:1,11:1},{7:1,9:1,10:1,11:1},{3:1},{3:1},{3:1},{3:1},{3:1},{3:1},{13:1},{14:1},{14:1},{15:1},{13:1},{16:1},{14:1},{3:1}];if (com_google_gwt_demos_titledpanel_TitledPanelDemo) {  var __gwt_initHandlers = com_google_gwt_demos_titledpanel_TitledPanelDemo.__gwt_initHandlers;  com_google_gwt_demos_titledpanel_TitledPanelDemo.onScriptLoad(gwtOnLoad);}})();