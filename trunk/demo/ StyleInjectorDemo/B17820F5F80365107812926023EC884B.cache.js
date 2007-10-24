(function(){var $wnd = window;var $doc = $wnd.document;var $moduleName, $moduleBase;var _,wn='com.google.gwt.core.client.',xn='com.google.gwt.demos.styleinjector.client.',yn='com.google.gwt.lang.',zn='com.google.gwt.libideas.client.',An='com.google.gwt.user.client.',Bn='com.google.gwt.user.client.impl.',Cn='com.google.gwt.user.client.ui.',Dn='java.lang.',En='java.util.';function vn(){}
function ji(a){return this===a;}
function ki(){return Ci(this);}
function hi(){}
_=hi.prototype={};_.eQ=ji;_.hC=ki;_.tI=1;function o(){return u();}
var p=null;function s(a){return a==null?0:a.$H?a.$H:(a.$H=v());}
function t(a){return a==null?0:a.$H?a.$H:(a.$H=v());}
function u(){return $moduleBase;}
function v(){return ++w;}
var w=0;function z(b,a){if(!ac(a,2)){return false;}return D(b,Fb(a,2));}
function A(a){return s(a);}
function B(){return [];}
function C(){return {};}
function E(a){return z(this,a);}
function D(a,b){return a===b;}
function F(){return A(this);}
function x(){}
_=x.prototype=new hi();_.eQ=E;_.hC=F;_.tI=7;function eb(a){pc(((bb(),cb).b,"body {\r\n  background: url('%background%') repeat-x;\r\n}\r\nh1{\r\n color:white;\r\n}"),(bb(),cb));Fe(kg(),nf(new lf(),'<h1>This, a picture of my mom and daughter, it should take some time to load the first time. It should load like lighting the second time to show the value of immutable resources.<\/h1>'));}
function ab(){}
_=ab.prototype=new hi();_.tI=0;function bb(){bb=vn;cb=ob(new fb());}
var cb;function nb(a){a.b=new gb();a.a=new jb();}
function ob(a){nb(a);return a;}
function qb(a){return Ab('[Lcom.google.gwt.libideas.resources.client.ResourcePrototype;',0,0,[a.b,a.a]);}
function fb(){}
_=fb.prototype=new hi();_.tI=0;function ib(){return 'css';}
function gb(){}
_=gb.prototype=new hi();_.m=ib;_.tI=0;function lb(){return 'background';}
function mb(){return o()+'9FA5064EEE42A503EFFA18A00A470EF1.cache.jpg';}
function jb(){}
_=jb.prototype=new hi();_.m=lb;_.n=mb;_.tI=8;function sb(c,a,d,b,e){c.a=a;c.b=b;e;c.tI=d;return c;}
function ub(a,b,c){return a[b]=c;}
function vb(b,a){return b[a];}
function xb(b,a){return b[a];}
function wb(a){return a.length;}
function zb(e,d,c,b,a){return yb(e,d,c,b,0,wb(b),a);}
function yb(j,i,g,c,e,a,b){var d,f,h;if((f=vb(c,e))<0){throw new fi();}h=sb(new rb(),f,vb(i,e),vb(g,e),j);++e;if(e<a){j=ti(j,1);for(d=0;d<f;++d){ub(h,d,yb(j,i,g,c,e,a,b));}}else{for(d=0;d<f;++d){ub(h,d,b);}}return h;}
function Ab(f,e,c,g){var a,b,d;b=wb(g);d=sb(new rb(),b,e,c,f);for(a=0;a<b;++a){ub(d,a,xb(g,a));}return d;}
function Bb(a,b,c){if(c!==null&&a.b!=0&& !ac(c,a.b)){throw new vh();}return ub(a,b,c);}
function rb(){}
_=rb.prototype=new hi();_.tI=0;function Eb(b,a){return !(!(b&&dc[b][a]));}
function Fb(b,a){if(b!=null)Eb(b.tI,a)||cc();return b;}
function ac(b,a){return b!=null&&Eb(b.tI,a);}
function cc(){throw new yh();}
function bc(a){if(a!==null){throw new yh();}return a;}
function ec(b,d){_=d.prototype;if(b&& !(b.tI>=_.tI)){var c=b.toString;for(var a in _){b[a]=_[a];}b.toString=c;}return b;}
var dc;function nc(){nc=vn;oc=new jc();}
function pc(a,e){nc();var b,c,d;if(e!==null){d=qb(e);for(b=0;b<d.a;b++){c=d[b];if(ac(c,4)){a=si(a,'%'+c.m()+'%',Fb(c,4).n());}}}lc(oc,a);}
var oc;function ic(){}
_=ic.prototype=new hi();_.tI=0;function lc(b,a){$doc.createStyleSheet().cssText=a;}
function jc(){}
_=jc.prototype=new ic();_.tI=0;function vc(){vc=vn;fd=hl(new fl());{bd=new ke();pe(bd);}}
function wc(b,a){vc();se(bd,b,a);}
function xc(a,b){vc();return me(bd,a,b);}
function yc(){vc();return ue(bd,'div');}
function Bc(b,a,d){vc();var c;c=p;{Ac(b,a,d);}}
function Ac(b,a,c){vc();var d;if(a===ed){if(Dc(b)==8192){ed=null;}}d=zc;zc=b;try{c.v(b);}finally{zc=d;}}
function Cc(b,a){vc();ve(bd,b,a);}
function Dc(a){vc();return we(bd,a);}
function Ec(a){vc();ne(bd,a);}
function Fc(a){vc();return xe(bd,a);}
function ad(a){vc();return oe(bd,a);}
function cd(a){vc();var b,c;c=true;if(fd.b>0){b=bc(ll(fd,fd.b-1));if(!(c=null.D())){Cc(a,true);Ec(a);}}return c;}
function dd(b,a){vc();ye(bd,b,a);}
function gd(a,b,c){vc();ze(bd,a,b,c);}
function hd(a,b){vc();Ae(bd,a,b);}
function id(a,b){vc();Be(bd,a,b);}
function jd(b,a,c){vc();Ce(bd,b,a,c);}
function kd(a,b){vc();qe(bd,a,b);}
var zc=null,bd=null,ed=null,fd;function nd(a){if(ac(a,5)){return xc(this,Fb(a,5));}return z(ec(this,ld),a);}
function od(){return A(ec(this,ld));}
function ld(){}
_=ld.prototype=new x();_.eQ=nd;_.hC=od;_.tI=9;function sd(a){return z(ec(this,pd),a);}
function td(){return A(ec(this,pd));}
function pd(){}
_=pd.prototype=new x();_.eQ=sd;_.hC=td;_.tI=10;function zd(){zd=vn;Bd=hl(new fl());{Ad();}}
function Ad(){zd();Fd(new vd());}
var Bd;function xd(){while((zd(),Bd).b>0){bc(ll((zd(),Bd),0)).D();}}
function yd(){return null;}
function vd(){}
_=vd.prototype=new hi();_.y=xd;_.z=yd;_.tI=11;function Ed(){Ed=vn;ae=hl(new fl());ie=hl(new fl());{ee();}}
function Fd(a){Ed();il(ae,a);}
function be(){Ed();var a,b;for(a=tj(ae);mj(a);){b=Fb(nj(a),6);b.y();}}
function ce(){Ed();var a,b,c,d;d=null;for(a=tj(ae);mj(a);){b=Fb(nj(a),6);c=b.z();{d=c;}}return d;}
function de(){Ed();var a,b;for(a=tj(ie);mj(a);){b=bc(nj(a));null.D();}}
function ee(){Ed();__gwt_initHandlers(function(){he();},function(){return ge();},function(){fe();$wnd.onresize=null;$wnd.onbeforeclose=null;$wnd.onclose=null;});}
function fe(){Ed();var a;a=p;{be();}}
function ge(){Ed();var a;a=p;{return ce();}}
function he(){Ed();var a;a=p;{de();}}
var ae,ie;function se(c,b,a){b.appendChild(a);}
function ue(b,a){return $doc.createElement(a);}
function ve(c,b,a){b.cancelBubble=a;}
function we(b,a){switch(a.type){case 'blur':return 4096;case 'change':return 1024;case 'click':return 1;case 'dblclick':return 2;case 'focus':return 2048;case 'keydown':return 128;case 'keypress':return 256;case 'keyup':return 512;case 'load':return 32768;case 'losecapture':return 8192;case 'mousedown':return 4;case 'mousemove':return 64;case 'mouseout':return 32;case 'mouseover':return 16;case 'mouseup':return 8;case 'scroll':return 16384;case 'error':return 65536;case 'mousewheel':return 131072;case 'DOMMouseScroll':return 131072;}}
function xe(b,a){return a.__eventBits||0;}
function ye(c,b,a){b.removeChild(a);}
function ze(c,a,b,d){a[b]=d;}
function Ae(c,a,b){a.__listener=b;}
function Be(c,a,b){if(!b){b='';}a.innerHTML=b;}
function Ce(c,b,a,d){b.style[a]=d;}
function je(){}
_=je.prototype=new hi();_.tI=0;function me(c,a,b){if(!a&& !b)return true;else if(!a|| !b)return false;return a.uniqueID==b.uniqueID;}
function ne(b,a){a.returnValue=false;}
function oe(c,a){var b=a.parentElement;return b||null;}
function pe(d){try{$doc.execCommand('BackgroundImageCache',false,true);}catch(a){}$wnd.__dispatchEvent=function(){var c=re;re=this;if($wnd.event.returnValue==null){$wnd.event.returnValue=true;if(!cd($wnd.event)){re=c;return;}}var b,a=this;while(a&& !(b=a.__listener))a=a.parentElement;if(b)Bc($wnd.event,a,b);re=c;};$wnd.__dispatchDblClickEvent=function(){var a=$doc.createEventObject();this.fireEvent('onclick',a);if(this.__eventBits&2)$wnd.__dispatchEvent.call(this);};$doc.body.onclick=$doc.body.onmousedown=$doc.body.onmouseup=$doc.body.onmousemove=$doc.body.onmousewheel=$doc.body.onkeydown=$doc.body.onkeypress=$doc.body.onkeyup=$doc.body.onfocus=$doc.body.onblur=$doc.body.ondblclick=$wnd.__dispatchEvent;}
function qe(c,b,a){b.__eventBits=a;b.onclick=a&1?$wnd.__dispatchEvent:null;b.ondblclick=a&(1|2)?$wnd.__dispatchDblClickEvent:null;b.onmousedown=a&4?$wnd.__dispatchEvent:null;b.onmouseup=a&8?$wnd.__dispatchEvent:null;b.onmouseover=a&16?$wnd.__dispatchEvent:null;b.onmouseout=a&32?$wnd.__dispatchEvent:null;b.onmousemove=a&64?$wnd.__dispatchEvent:null;b.onkeydown=a&128?$wnd.__dispatchEvent:null;b.onkeypress=a&256?$wnd.__dispatchEvent:null;b.onkeyup=a&512?$wnd.__dispatchEvent:null;b.onchange=a&1024?$wnd.__dispatchEvent:null;b.onfocus=a&2048?$wnd.__dispatchEvent:null;b.onblur=a&4096?$wnd.__dispatchEvent:null;b.onlosecapture=a&8192?$wnd.__dispatchEvent:null;b.onscroll=a&16384?$wnd.__dispatchEvent:null;b.onload=a&32768?$wnd.__dispatchEvent:null;b.onerror=a&65536?$wnd.__dispatchEvent:null;b.onmousewheel=a&131072?$wnd.__dispatchEvent:null;}
function ke(){}
_=ke.prototype=new je();_.tI=0;var re=null;function tg(d,b,a){var c=b.parentNode;if(!c){return;}c.insertBefore(a,b);c.removeChild(b);}
function ug(b,a){if(b.d!==null){tg(b,b.d,a);}b.d=a;}
function vg(b,a){xg(b.d,a);}
function wg(b,a){kd(b.d,a|Fc(b.d));}
function xg(a,b){gd(a,'className',b);}
function rg(){}
_=rg.prototype=new hi();_.tI=0;_.d=null;function kh(a){if(a.b){throw ai(new Fh(),"Should only call onAttach when the widget is detached from the browser's document");}a.b=true;hd(a.d,a);a.i();a.w();}
function lh(a){if(!a.b){throw ai(new Fh(),"Should only call onDetach when the widget is attached to the browser's document");}try{a.x();}finally{a.j();hd(a.d,null);a.b=false;}}
function mh(a){if(a.c!==null){bf(a.c,a);}else if(a.c!==null){throw ai(new Fh(),"This widget's parent does not implement HasWidgets");}}
function nh(b,a){if(b.b){hd(b.d,null);}ug(b,a);if(b.b){hd(a,b);}}
function oh(c,b){var a;a=c.c;if(b===null){if(a!==null&&a.b){lh(c);}c.c=null;}else{if(a!==null){throw ai(new Fh(),'Cannot set a new parent without first clearing the old parent');}c.c=b;if(b.b){kh(c);}}}
function ph(){}
function qh(){}
function rh(a){}
function sh(){}
function th(){}
function yg(){}
_=yg.prototype=new rg();_.i=ph;_.j=qh;_.v=rh;_.w=sh;_.x=th;_.tI=12;_.b=false;_.c=null;function Bf(b,a){oh(a,b);}
function Df(b,a){oh(a,null);}
function Ef(){var a,b;for(b=this.s();Dg(b);){a=Eg(b);kh(a);}}
function Ff(){var a,b;for(b=this.s();Dg(b);){a=Eg(b);lh(a);}}
function ag(){}
function bg(){}
function Af(){}
_=Af.prototype=new yg();_.i=Ef;_.j=Ff;_.w=ag;_.x=bg;_.tI=13;function ef(a){a.a=bh(new zg(),a);}
function ff(a){ef(a);return a;}
function gf(c,a,b){mh(a);ch(c.a,a);wc(b,a.d);Bf(c,a);}
function jf(b,c){var a;if(c.c!==b){return false;}Df(b,c);a=c.d;dd(ad(a),a);ih(b.a,c);return true;}
function kf(){return gh(this.a);}
function df(){}
_=df.prototype=new Af();_.s=kf;_.tI=14;function Ee(a){ff(a);nh(a,yc());jd(a.d,'position','relative');jd(a.d,'overflow','hidden');return a;}
function Fe(a,b){gf(a,b,a.d);}
function bf(b,c){var a;a=jf(b,c);if(a){cf(c.d);}return a;}
function cf(a){jd(a,'left','');jd(a,'top','');jd(a,'position','');}
function De(){}
_=De.prototype=new df();_.tI=15;function xf(a){nh(a,yc());wg(a,131197);vg(a,'gwt-Label');return a;}
function zf(a){switch(Dc(a)){case 1:break;case 4:case 8:case 64:case 16:case 32:break;case 131072:break;}}
function wf(){}
_=wf.prototype=new yg();_.v=zf;_.tI=16;function mf(a){xf(a);nh(a,yc());wg(a,125);vg(a,'gwt-HTML');return a;}
function nf(b,a){mf(b);pf(b,a);return b;}
function pf(b,a){id(b.d,a);}
function lf(){}
_=lf.prototype=new wf();_.tI=17;function ig(){ig=vn;ng=vm(new Cl());}
function hg(b,a){ig();Ee(b);if(a===null){a=jg();}nh(b,a);kh(b);return b;}
function kg(){ig();return lg(null);}
function lg(c){ig();var a,b;b=Fb(Bm(ng,c),7);if(b!==null){return b;}a=null;if(ng.c==0){mg();}Cm(ng,c,b=hg(new cg(),a));return b;}
function jg(){ig();return $doc.body;}
function mg(){ig();Fd(new dg());}
function cg(){}
_=cg.prototype=new De();_.tI=18;var ng;function fg(){var a,b;for(b=mk(Ak((ig(),ng)));tk(b);){a=Fb(uk(b),7);if(a.b){lh(a);}}}
function gg(){return null;}
function dg(){}
_=dg.prototype=new hi();_.y=fg;_.z=gg;_.tI=19;function bh(b,a){b.a=zb('[Lcom.google.gwt.user.client.ui.Widget;',[0],[9],[4],null);return b;}
function ch(a,b){fh(a,b,a.b);}
function eh(b,c){var a;for(a=0;a<b.b;++a){if(b.a[a]===c){return a;}}return (-1);}
function fh(d,e,a){var b,c;if(a<0||a>d.b){throw new ci();}if(d.b==d.a.a){c=zb('[Lcom.google.gwt.user.client.ui.Widget;',[0],[9],[d.a.a*2],null);for(b=0;b<d.a.a;++b){Bb(c,b,d.a[b]);}d.a=c;}++d.b;for(b=d.b-1;b>a;--b){Bb(d.a,b,d.a[b-1]);}Bb(d.a,a,e);}
function gh(a){return Bg(new Ag(),a);}
function hh(c,b){var a;if(b<0||b>=c.b){throw new ci();}--c.b;for(a=b;a<c.b;++a){Bb(c.a,a,c.a[a+1]);}Bb(c.a,c.b,null);}
function ih(b,c){var a;a=eh(b,c);if(a==(-1)){throw new rn();}hh(b,a);}
function zg(){}
_=zg.prototype=new hi();_.tI=0;_.a=null;_.b=0;function Bg(b,a){b.b=a;return b;}
function Dg(a){return a.a<a.b.b-1;}
function Eg(a){if(a.a>=a.b.b){throw new rn();}return a.b.a[++a.a];}
function Fg(){return Dg(this);}
function ah(){return Eg(this);}
function Ag(){}
_=Ag.prototype=new hi();_.r=Fg;_.u=ah;_.tI=0;_.a=(-1);function Ei(b,a){a;return b;}
function Di(){}
_=Di.prototype=new hi();_.tI=3;function Dh(b,a){Ei(b,a);return b;}
function Ch(){}
_=Ch.prototype=new Di();_.tI=4;function mi(b,a){Dh(b,a);return b;}
function li(){}
_=li.prototype=new Ch();_.tI=5;function vh(){}
_=vh.prototype=new li();_.tI=20;function yh(){}
_=yh.prototype=new li();_.tI=21;function ai(b,a){mi(b,a);return b;}
function Fh(){}
_=Fh.prototype=new li();_.tI=22;function di(b,a){mi(b,a);return b;}
function ci(){}
_=ci.prototype=new li();_.tI=23;function fi(){}
_=fi.prototype=new li();_.tI=24;function pi(b,a){return b.charCodeAt(a);}
function ri(c,b,a){return c.indexOf(b,a);}
function si(c,a,b){b=wi(b);return c.replace(RegExp(a,'g'),b);}
function ti(b,a){return b.substr(a,b.length-a);}
function ui(c,a,b){return c.substr(a,b-a);}
function vi(a,b){return String(a)==b;}
function wi(b){var a;a=0;while(0<=(a=ri(b,'\\',a))){if(pi(b,a+1)==36){b=ui(b,0,a)+'$'+ti(b,++a);}else{b=ui(b,0,a)+ti(b,++a);}}return b;}
function xi(a){if(!ac(a,1))return false;return vi(this,a);}
function zi(){var a=yi;if(!a){a=yi={};}var e=':'+this;var b=a[e];if(b==null){b=0;var f=this.length;var d=f<64?1:f/32|0;for(var c=0;c<f;c+=d){b<<=1;b+=this.charCodeAt(c);}b|=0;a[e]=b;}return b;}
_=String.prototype;_.eQ=xi;_.hC=zi;_.tI=2;var yi=null;function Ci(a){return t(a);}
function bj(b,a){mi(b,a);return b;}
function aj(){}
_=aj.prototype=new li();_.tI=25;function ej(d,a,b){var c;while(a.r()){c=a.u();if(b===null?c===null:b.eQ(c)){return a;}}return null;}
function gj(a){throw bj(new aj(),'add');}
function hj(b){var a;a=ej(this,this.s(),b);return a!==null;}
function dj(){}
_=dj.prototype=new hi();_.f=gj;_.h=hj;_.tI=0;function sj(b,a){throw di(new ci(),'Index: '+a+', Size: '+b.b);}
function tj(a){return kj(new jj(),a);}
function uj(b,a){throw bj(new aj(),'add');}
function vj(a){this.e(this.B(),a);return true;}
function wj(e){var a,b,c,d,f;if(e===this){return true;}if(!ac(e,12)){return false;}f=Fb(e,12);if(this.B()!=f.B()){return false;}c=tj(this);d=f.s();while(mj(c)){a=nj(c);b=nj(d);if(!(a===null?b===null:a.eQ(b))){return false;}}return true;}
function xj(){var a,b,c,d;c=1;a=31;b=tj(this);while(mj(b)){d=nj(b);c=31*c+(d===null?0:d.hC());}return c;}
function yj(){return tj(this);}
function zj(a){throw bj(new aj(),'remove');}
function ij(){}
_=ij.prototype=new dj();_.e=uj;_.f=vj;_.eQ=wj;_.hC=xj;_.s=yj;_.A=zj;_.tI=26;function kj(b,a){b.c=a;return b;}
function mj(a){return a.a<a.c.B();}
function nj(a){if(!mj(a)){throw new rn();}return a.c.p(a.b=a.a++);}
function oj(a){if(a.b<0){throw new Fh();}a.c.A(a.b);a.a=a.b;a.b=(-1);}
function pj(){return mj(this);}
function qj(){return nj(this);}
function jj(){}
_=jj.prototype=new hi();_.r=pj;_.u=qj;_.tI=0;_.a=0;_.b=(-1);function yk(f,d,e){var a,b,c;for(b=qm(f.k());jm(b);){a=km(b);c=a.l();if(d===null?c===null:d.eQ(c)){if(e){lm(b);}return a;}}return null;}
function zk(b){var a;a=b.k();return Cj(new Bj(),b,a);}
function Ak(b){var a;a=Am(b);return kk(new jk(),b,a);}
function Bk(a){return yk(this,a,false)!==null;}
function Ck(d){var a,b,c,e,f,g,h;if(d===this){return true;}if(!ac(d,13)){return false;}f=Fb(d,13);c=zk(this);e=f.t();if(!cl(c,e)){return false;}for(a=Ej(c);fk(a);){b=gk(a);h=this.q(b);g=f.q(b);if(h===null?g!==null:!h.eQ(g)){return false;}}return true;}
function Dk(b){var a;a=yk(this,b,false);return a===null?null:a.o();}
function Ek(){var a,b,c;b=0;for(c=qm(this.k());jm(c);){a=km(c);b+=a.hC();}return b;}
function Fk(){return zk(this);}
function Aj(){}
_=Aj.prototype=new hi();_.g=Bk;_.eQ=Ck;_.q=Dk;_.hC=Ek;_.t=Fk;_.tI=27;function cl(e,b){var a,c,d;if(b===e){return true;}if(!ac(b,14)){return false;}c=Fb(b,14);if(c.B()!=e.B()){return false;}for(a=c.s();a.r();){d=a.u();if(!e.h(d)){return false;}}return true;}
function dl(a){return cl(this,a);}
function el(){var a,b,c;a=0;for(b=this.s();b.r();){c=b.u();if(c!==null){a+=c.hC();}}return a;}
function al(){}
_=al.prototype=new dj();_.eQ=dl;_.hC=el;_.tI=28;function Cj(b,a,c){b.a=a;b.b=c;return b;}
function Ej(b){var a;a=qm(b.b);return dk(new ck(),b,a);}
function Fj(a){return this.a.g(a);}
function ak(){return Ej(this);}
function bk(){return this.b.a.c;}
function Bj(){}
_=Bj.prototype=new al();_.h=Fj;_.s=ak;_.B=bk;_.tI=29;function dk(b,a,c){b.a=c;return b;}
function fk(a){return a.a.r();}
function gk(b){var a;a=b.a.u();return a.l();}
function hk(){return fk(this);}
function ik(){return gk(this);}
function ck(){}
_=ck.prototype=new hi();_.r=hk;_.u=ik;_.tI=0;function kk(b,a,c){b.a=a;b.b=c;return b;}
function mk(b){var a;a=qm(b.b);return rk(new qk(),b,a);}
function nk(a){return zm(this.a,a);}
function ok(){return mk(this);}
function pk(){return this.b.a.c;}
function jk(){}
_=jk.prototype=new dj();_.h=nk;_.s=ok;_.B=pk;_.tI=0;function rk(b,a,c){b.a=c;return b;}
function tk(a){return a.a.r();}
function uk(a){var b;b=a.a.u().o();return b;}
function vk(){return tk(this);}
function wk(){return uk(this);}
function qk(){}
_=qk.prototype=new hi();_.r=vk;_.u=wk;_.tI=0;function gl(a){{jl(a);}}
function hl(a){gl(a);return a;}
function il(b,a){yl(b.a,b.b++,a);return true;}
function jl(a){a.a=B();a.b=0;}
function ll(b,a){if(a<0||a>=b.b){sj(b,a);}return ul(b.a,a);}
function ml(b,a){return nl(b,a,0);}
function nl(c,b,a){if(a<0){sj(c,a);}for(;a<c.b;++a){if(tl(b,ul(c.a,a))){return a;}}return (-1);}
function ol(c,a){var b;b=ll(c,a);wl(c.a,a,1);--c.b;return b;}
function ql(a,b){if(a<0||a>this.b){sj(this,a);}pl(this.a,a,b);++this.b;}
function rl(a){return il(this,a);}
function pl(a,b,c){a.splice(b,0,c);}
function sl(a){return ml(this,a)!=(-1);}
function tl(a,b){return a===b||a!==null&&a.eQ(b);}
function vl(a){return ll(this,a);}
function ul(a,b){return a[b];}
function xl(a){return ol(this,a);}
function wl(a,c,b){a.splice(c,b);}
function yl(a,b,c){a[b]=c;}
function zl(){return this.b;}
function fl(){}
_=fl.prototype=new ij();_.e=ql;_.f=rl;_.h=sl;_.p=vl;_.A=xl;_.B=zl;_.tI=30;_.a=null;_.b=0;function xm(){xm=vn;Em=en();}
function um(a){{wm(a);}}
function vm(a){xm();um(a);return a;}
function wm(a){a.a=B();a.d=C();a.b=ec(Em,x);a.c=0;}
function ym(b,a){if(ac(a,1)){return jn(b.d,Fb(a,1))!==Em;}else if(a===null){return b.b!==Em;}else{return hn(b.a,a,a.hC())!==Em;}}
function zm(a,b){if(a.b!==Em&&gn(a.b,b)){return true;}else if(dn(a.d,b)){return true;}else if(bn(a.a,b)){return true;}return false;}
function Am(a){return om(new fm(),a);}
function Bm(c,a){var b;if(ac(a,1)){b=jn(c.d,Fb(a,1));}else if(a===null){b=c.b;}else{b=hn(c.a,a,a.hC());}return b===Em?null:b;}
function Cm(c,a,d){var b;{b=c.b;c.b=d;}if(b===Em){++c.c;return null;}else{return b;}}
function Dm(c,a){var b;if(ac(a,1)){b=mn(c.d,Fb(a,1));}else if(a===null){b=c.b;c.b=ec(Em,x);}else{b=ln(c.a,a,a.hC());}if(b===Em){return null;}else{--c.c;return b;}}
function Fm(e,c){xm();for(var d in e){if(d==parseInt(d)){var a=e[d];for(var f=0,b=a.length;f<b;++f){c.f(a[f]);}}}}
function an(d,a){xm();for(var c in d){if(c.charCodeAt(0)==58){var e=d[c];var b=am(c.substring(1),e);a.f(b);}}}
function bn(f,h){xm();for(var e in f){if(e==parseInt(e)){var a=f[e];for(var g=0,b=a.length;g<b;++g){var c=a[g];var d=c.o();if(gn(h,d)){return true;}}}}return false;}
function cn(a){return ym(this,a);}
function dn(c,d){xm();for(var b in c){if(b.charCodeAt(0)==58){var a=c[b];if(gn(d,a)){return true;}}}return false;}
function en(){xm();}
function fn(){return Am(this);}
function gn(a,b){xm();if(a===b){return true;}else if(a===null){return false;}else{return a.eQ(b);}}
function kn(a){return Bm(this,a);}
function hn(f,h,e){xm();var a=f[e];if(a){for(var g=0,b=a.length;g<b;++g){var c=a[g];var d=c.l();if(gn(h,d)){return c.o();}}}}
function jn(b,a){xm();return b[':'+a];}
function ln(f,h,e){xm();var a=f[e];if(a){for(var g=0,b=a.length;g<b;++g){var c=a[g];var d=c.l();if(gn(h,d)){if(a.length==1){delete f[e];}else{a.splice(g,1);}return c.o();}}}}
function mn(c,a){xm();a=':'+a;var b=c[a];delete c[a];return b;}
function Cl(){}
_=Cl.prototype=new Aj();_.g=cn;_.k=fn;_.q=kn;_.tI=31;_.a=null;_.b=null;_.c=0;_.d=null;var Em;function El(b,a,c){b.a=a;b.b=c;return b;}
function am(a,b){return El(new Dl(),a,b);}
function bm(b){var a;if(ac(b,15)){a=Fb(b,15);if(gn(this.a,a.l())&&gn(this.b,a.o())){return true;}}return false;}
function cm(){return this.a;}
function dm(){return this.b;}
function em(){var a,b;a=0;b=0;if(this.a!==null){a=this.a.hC();}if(this.b!==null){b=this.b.hC();}return a^b;}
function Dl(){}
_=Dl.prototype=new hi();_.eQ=bm;_.l=cm;_.o=dm;_.hC=em;_.tI=32;_.a=null;_.b=null;function om(b,a){b.a=a;return b;}
function qm(a){return hm(new gm(),a.a);}
function rm(c){var a,b,d;if(ac(c,15)){a=Fb(c,15);b=a.l();if(ym(this.a,b)){d=Bm(this.a,b);return gn(a.o(),d);}}return false;}
function sm(){return qm(this);}
function tm(){return this.a.c;}
function fm(){}
_=fm.prototype=new al();_.h=rm;_.s=sm;_.B=tm;_.tI=33;function hm(c,b){var a;c.c=b;a=hl(new fl());if(c.c.b!==(xm(),Em)){il(a,El(new Dl(),null,c.c.b));}an(c.c.d,a);Fm(c.c.a,a);c.a=tj(a);return c;}
function jm(a){return mj(a.a);}
function km(a){return a.b=Fb(nj(a.a),15);}
function lm(a){if(a.b===null){throw ai(new Fh(),'Must call next() before remove().');}else{oj(a.a);Dm(a.c,a.b.l());a.b=null;}}
function mm(){return jm(this);}
function nm(){return km(this);}
function gm(){}
_=gm.prototype=new hi();_.r=mm;_.u=nm;_.tI=0;_.a=null;_.b=null;function rn(){}
_=rn.prototype=new li();_.tI=34;function uh(){eb(new ab());}
function gwtOnLoad(b,d,c){$moduleName=d;$moduleBase=c;if(b)try{uh();}catch(a){b(d);}else{uh();}}
var dc=[{},{},{1:1},{3:1},{3:1},{3:1},{3:1},{2:1},{4:1},{2:1,5:1},{2:1},{6:1},{8:1,9:1,10:1,11:1},{8:1,9:1,10:1,11:1},{8:1,9:1,10:1,11:1},{8:1,9:1,10:1,11:1},{8:1,9:1,10:1,11:1},{8:1,9:1,10:1,11:1},{7:1,8:1,9:1,10:1,11:1},{6:1},{3:1},{3:1},{3:1},{3:1},{3:1},{3:1},{12:1},{13:1},{14:1},{14:1},{12:1},{13:1},{15:1},{14:1},{3:1}];if (com_google_gwt_demos_styleinjector_StyleInjectorDemo) {  var __gwt_initHandlers = com_google_gwt_demos_styleinjector_StyleInjectorDemo.__gwt_initHandlers;  com_google_gwt_demos_styleinjector_StyleInjectorDemo.onScriptLoad(gwtOnLoad);}})();