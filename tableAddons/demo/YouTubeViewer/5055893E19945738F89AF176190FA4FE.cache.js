(function(){var $wnd = window;var $doc = $wnd.document;var $moduleName, $moduleBase;var _,ps='com.google.gwt.core.client.',qs='com.google.gwt.demos.youtubeviewer.client.',rs='com.google.gwt.lang.',ss='com.google.gwt.user.client.',ts='com.google.gwt.user.client.impl.',us='com.google.gwt.user.client.ui.',vs='com.google.gwt.user.client.ui.impl.',ws='com.google.gwt.widgetideas.client.',xs='java.lang.',ys='java.util.';function os(){}
function bn(a){return this===a;}
function cn(){return rn(this);}
function Fm(){}
_=Fm.prototype={};_.eQ=bn;_.hC=cn;_.tI=1;var o=null;function r(a){return a==null?0:a.$H?a.$H:(a.$H=t());}
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
_=v.prototype=new Fm();_.eQ=C;_.hC=D;_.tI=7;function xb(a){a.a=cm(new bm(),'mc336q361N0');}
function yb(a){xb(a);return a;}
function Ab(d){var a,b,c,e,f;d.a=cm(new bm(),'k56DbGDBsNk');mk(d.a,'YouTubeViewer');nk(d.a,'443px');lk(d.a,'369px');kf(sj(),d.a);kf(sj(),ti(new zg(),'<BR><BR>'));a=qg(new og(),5,3);e=fk(new Dj());ck(e,'iuywmPePYKQ');mi(a,0,0,'<B>Video ID:<\/B>');oi(a,0,1,e);oi(a,0,2,wf(new of(),'Set',ab(new F(),d,e)));f=fk(new Dj());ck(f,'443px');mi(a,1,0,'<B>Width:<\/B>');oi(a,1,1,f);oi(a,1,2,wf(new of(),'Set',eb(new db(),d,f)));b=fk(new Dj());ck(b,'369px');mi(a,2,0,'<B>Height:<\/B>');oi(a,2,1,b);oi(a,2,2,wf(new of(),'Set',ib(new hb(),d,b)));c=fk(new Dj());ck(c,'http://www.google.com');mi(a,3,0,'<B>Plugins Page:<\/B>');oi(a,3,1,c);oi(a,3,2,wf(new of(),'Set',mb(new lb(),d,c)));oi(a,4,0,wf(new of(),'Enable AutoPlay',qb(new pb(),d)));oi(a,4,1,wf(new of(),'Enable Related',ub(new tb(),d)));kf(sj(),a);}
function E(){}
_=E.prototype=new Fm();_.tI=0;function ab(b,a,c){b.a=a;b.b=c;return b;}
function cb(a){hm(this.a.a,bk(this.b));}
function F(){}
_=F.prototype=new Fm();_.C=cb;_.tI=8;function eb(b,a,c){b.a=a;b.b=c;return b;}
function gb(a){nk(this.a.a,bk(this.b));}
function db(){}
_=db.prototype=new Fm();_.C=gb;_.tI=9;function ib(b,a,c){b.a=a;b.b=c;return b;}
function kb(a){lk(this.a.a,bk(this.b));}
function hb(){}
_=hb.prototype=new Fm();_.C=kb;_.tI=10;function mb(b,a,c){b.a=a;b.b=c;return b;}
function ob(b){var a;a=this.a.a.d;zl(a,bk(this.b));Fl(this.a.a,a);}
function lb(){}
_=lb.prototype=new Fm();_.C=ob;_.tI=11;function qb(b,a){b.a=a;return b;}
function sb(a){if(this.a.a.a){gm(this.a.a,false);tf(hc(a,3),'Enable AutoPlay');}else{gm(this.a.a,true);tf(hc(a,3),'Disable AutoPlay');}}
function pb(){}
_=pb.prototype=new Fm();_.C=sb;_.tI=12;function ub(b,a){b.a=a;return b;}
function wb(a){if(this.a.a.c){im(this.a.a,false);tf(hc(a,3),'Enable Related');}else{im(this.a.a,true);tf(hc(a,3),'Disable Related');}}
function tb(){}
_=tb.prototype=new Fm();_.C=wb;_.tI=13;function Cb(c,a,d,b,e){c.a=a;c.b=b;e;c.tI=d;return c;}
function Eb(a,b,c){return a[b]=c;}
function Fb(b,a){return b[a];}
function ac(a){return a.length;}
function cc(e,d,c,b,a){return bc(e,d,c,b,0,ac(b),a);}
function bc(j,i,g,c,e,a,b){var d,f,h;if((f=Fb(c,e))<0){throw new Dm();}h=Cb(new Bb(),f,Fb(i,e),Fb(g,e),j);++e;if(e<a){j=kn(j,1);for(d=0;d<f;++d){Eb(h,d,bc(j,i,g,c,e,a,b));}}else{for(d=0;d<f;++d){Eb(h,d,b);}}return h;}
function dc(a,b,c){if(c!==null&&a.b!=0&& !ic(c,a.b)){throw new km();}return Eb(a,b,c);}
function Bb(){}
_=Bb.prototype=new Fm();_.tI=0;function gc(b,a){return !(!(b&&lc[b][a]));}
function hc(b,a){if(b!=null)gc(b.tI,a)||kc();return b;}
function ic(b,a){return b!=null&&gc(b.tI,a);}
function kc(){throw new nm();}
function jc(a){if(a!==null){throw new nm();}return a;}
function mc(b,d){_=d.prototype;if(b&& !(b.tI>=_.tI)){var c=b.toString;for(var a in _){b[a]=_[a];}b.toString=c;}return b;}
var lc;function qc(){qc=os;jd=Dp(new Bp());{ed=new pe();ve(ed);}}
function rc(b,a){qc();Ae(ed,b,a);}
function sc(a,b){qc();return re(ed,a,b);}
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
function Fc(a){qc();se(ed,a);}
function ad(a,b){qc();return af(ed,a,b);}
function bd(a){qc();return bf(ed,a);}
function cd(a){qc();return te(ed,a);}
function dd(a){qc();return ue(ed,a);}
function fd(c,a,b){qc();we(ed,c,a,b);}
function gd(a){qc();var b,c;c=true;if(jd.b>0){b=jc(bq(jd,jd.b-1));if(!(c=null.hb())){Dc(a,true);Fc(a);}}return c;}
function hd(b,a){qc();cf(ed,b,a);}
function kd(a,b,c){qc();df(ed,a,b,c);}
function ld(a,b){qc();ef(ed,a,b);}
function md(a,b){qc();ff(ed,a,b);}
function nd(a,b){qc();xe(ed,a,b);}
function od(b,a,c){qc();gf(ed,b,a,c);}
function pd(a,b){qc();ye(ed,a,b);}
var Ac=null,ed=null,id=null,jd;function sd(a){if(ic(a,5)){return sc(this,hc(a,5));}return x(mc(this,qd),a);}
function td(){return y(mc(this,qd));}
function qd(){}
_=qd.prototype=new v();_.eQ=sd;_.hC=td;_.tI=14;function xd(a){return x(mc(this,ud),a);}
function yd(){return y(mc(this,ud));}
function ud(){}
_=ud.prototype=new v();_.eQ=xd;_.hC=yd;_.tI=15;function Ed(){Ed=os;ae=Dp(new Bp());{Fd();}}
function Fd(){Ed();ee(new Ad());}
var ae;function Cd(){while((Ed(),ae).b>0){jc(bq((Ed(),ae),0)).hb();}}
function Dd(){return null;}
function Ad(){}
_=Ad.prototype=new Fm();_.F=Cd;_.ab=Dd;_.tI=16;function de(){de=os;fe=Dp(new Bp());ne=Dp(new Bp());{je();}}
function ee(a){de();Ep(fe,a);}
function ge(){de();var a,b;for(a=jo(fe);bo(a);){b=hc(co(a),6);b.F();}}
function he(){de();var a,b,c,d;d=null;for(a=jo(fe);bo(a);){b=hc(co(a),6);c=b.ab();{d=c;}}return d;}
function ie(){de();var a,b;for(a=jo(ne);bo(a);){b=jc(co(a));null.hb();}}
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
function gf(c,b,a,d){b.style[a]=d;}
function oe(){}
_=oe.prototype=new Fm();_.tI=0;function re(c,a,b){if(!a&& !b)return true;else if(!a|| !b)return false;return a.uniqueID==b.uniqueID;}
function se(b,a){a.returnValue=false;}
function te(c,b){var a=b.firstChild;return a||null;}
function ue(c,a){var b=a.parentElement;return b||null;}
function ve(d){try{$doc.execCommand('BackgroundImageCache',false,true);}catch(a){}$wnd.__dispatchEvent=function(){var c=ze;ze=this;if($wnd.event.returnValue==null){$wnd.event.returnValue=true;if(!gd($wnd.event)){ze=c;return;}}var b,a=this;while(a&& !(b=a.__listener))a=a.parentElement;if(b)Cc($wnd.event,a,b);ze=c;};$wnd.__dispatchDblClickEvent=function(){var a=$doc.createEventObject();this.fireEvent('onclick',a);if(this.__eventBits&2)$wnd.__dispatchEvent.call(this);};$doc.body.onclick=$doc.body.onmousedown=$doc.body.onmouseup=$doc.body.onmousemove=$doc.body.onmousewheel=$doc.body.onkeydown=$doc.body.onkeypress=$doc.body.onkeyup=$doc.body.onfocus=$doc.body.onblur=$doc.body.ondblclick=$wnd.__dispatchEvent;}
function we(d,c,a,b){if(b>=c.children.length)c.appendChild(a);else c.insertBefore(a,c.children[b]);}
function xe(c,a,b){if(!b)b='';a.innerText=b;}
function ye(c,b,a){b.__eventBits=a;b.onclick=a&1?$wnd.__dispatchEvent:null;b.ondblclick=a&(1|2)?$wnd.__dispatchDblClickEvent:null;b.onmousedown=a&4?$wnd.__dispatchEvent:null;b.onmouseup=a&8?$wnd.__dispatchEvent:null;b.onmouseover=a&16?$wnd.__dispatchEvent:null;b.onmouseout=a&32?$wnd.__dispatchEvent:null;b.onmousemove=a&64?$wnd.__dispatchEvent:null;b.onkeydown=a&128?$wnd.__dispatchEvent:null;b.onkeypress=a&256?$wnd.__dispatchEvent:null;b.onkeyup=a&512?$wnd.__dispatchEvent:null;b.onchange=a&1024?$wnd.__dispatchEvent:null;b.onfocus=a&2048?$wnd.__dispatchEvent:null;b.onblur=a&4096?$wnd.__dispatchEvent:null;b.onlosecapture=a&8192?$wnd.__dispatchEvent:null;b.onscroll=a&16384?$wnd.__dispatchEvent:null;b.onload=a&32768?$wnd.__dispatchEvent:null;b.onerror=a&65536?$wnd.__dispatchEvent:null;b.onmousewheel=a&131072?$wnd.__dispatchEvent:null;}
function pe(){}
_=pe.prototype=new oe();_.tI=0;var ze=null;function jk(d,b,a){var c=b.parentNode;if(!c){return;}c.insertBefore(a,b);c.removeChild(b);}
function kk(b,a){if(b.k!==null){jk(b,b.k,a);}b.k=a;}
function lk(b,a){od(b.k,'height',a);}
function mk(b,a){pk(b.k,a);}
function nk(a,b){od(a.k,'width',b);}
function ok(b,a){pd(b.k,a|bd(b.k));}
function pk(a,b){kd(a,'className',b);}
function hk(){}
_=hk.prototype=new Fm();_.tI=0;_.k=null;function al(a){if(a.i){throw ym(new xm(),"Should only call onAttach when the widget is detached from the browser's document");}a.i=true;ld(a.k,a);a.q();a.D();}
function bl(a){if(!a.i){throw ym(new xm(),"Should only call onDetach when the widget is attached to the browser's document");}try{a.E();}finally{a.r();ld(a.k,null);a.i=false;}}
function cl(a){if(a.j!==null){a.j.cb(a);}else if(a.j!==null){throw ym(new xm(),"This widget's parent does not implement HasWidgets");}}
function dl(b,a){if(b.i){ld(b.k,null);}kk(b,a);if(b.i){ld(a,b);}}
function el(c,b){var a;a=c.j;if(b===null){if(a!==null&&a.i){bl(c);}c.j=null;}else{if(a!==null){throw ym(new xm(),'Cannot set a new parent without first clearing the old parent');}c.j=b;if(b.i){al(c);}}}
function fl(){}
function gl(){}
function hl(a){}
function il(){}
function jl(){}
function kl(a){dl(this,a);}
function qk(){}
_=qk.prototype=new hk();_.q=fl;_.r=gl;_.B=hl;_.D=il;_.E=jl;_.db=kl;_.tI=17;_.i=false;_.j=null;function dj(b,a){el(a,b);}
function fj(b,a){el(a,null);}
function gj(){var a,b;for(b=this.y();b.x();){a=hc(b.A(),8);al(a);}}
function hj(){var a,b;for(b=this.y();b.x();){a=hc(b.A(),8);bl(a);}}
function ij(){}
function jj(){}
function cj(){}
_=cj.prototype=new qk();_.q=gj;_.r=hj;_.D=ij;_.E=jj;_.tI=18;function Ff(a){a.a=xk(new rk(),a);}
function ag(a){Ff(a);return a;}
function bg(c,a,b){cl(a);yk(c.a,a);rc(b,a.k);dj(c,a);}
function dg(b,c){var a;if(c.j!==b){return false;}fj(b,c);a=c.k;hd(dd(a),a);Ek(b.a,c);return true;}
function eg(){return Ck(this.a);}
function fg(a){return dg(this,a);}
function Ef(){}
_=Ef.prototype=new cj();_.y=eg;_.cb=fg;_.tI=19;function jf(a){ag(a);a.db(uc());od(a.k,'position','relative');od(a.k,'overflow','hidden');return a;}
function kf(a,b){bg(a,b,a.k);}
function mf(a){od(a,'left','');od(a,'top','');od(a,'position','');}
function nf(b){var a;a=dg(this,b);if(a){mf(b.k);}return a;}
function hf(){}
_=hf.prototype=new Ef();_.cb=nf;_.tI=20;function ig(){ig=os;ql(),sl;}
function hg(b,a){ql(),sl;kg(b,a);return b;}
function jg(b,a){switch(Ec(a)){case 1:if(b.b!==null){Cf(b.b,b);}break;case 4096:case 2048:break;case 128:case 512:case 256:break;}}
function kg(b,a){dl(b,a);ok(b,7041);}
function lg(a){if(this.b===null){this.b=Af(new zf());}Ep(this.b,a);}
function mg(a){jg(this,a);}
function ng(a){kg(this,a);}
function gg(){}
_=gg.prototype=new qk();_.l=lg;_.B=mg;_.db=ng;_.tI=21;_.b=null;function rf(){rf=os;ql(),sl;}
function qf(b,a){ql(),sl;hg(b,a);return b;}
function sf(b,a){md(b.k,a);}
function tf(b,a){nd(b.k,a);}
function pf(){}
_=pf.prototype=new gg();_.tI=22;function xf(){xf=os;ql(),sl;}
function uf(a){ql(),sl;qf(a,tc());yf(a.k);mk(a,'gwt-Button');return a;}
function vf(b,a){ql(),sl;uf(b);sf(b,a);return b;}
function wf(c,a,b){ql(),sl;vf(c,a);c.l(b);return c;}
function yf(b){xf();if(b.type=='submit'){try{b.setAttribute('type','button');}catch(a){}}}
function of(){}
_=of.prototype=new pf();_.tI=23;function zn(d,a,b){var c;while(a.x()){c=a.A();if(b===null?c===null:b.eQ(c)){return a;}}return null;}
function Bn(a){throw wn(new vn(),'add');}
function Cn(b){var a;a=zn(this,this.y(),b);return a!==null;}
function yn(){}
_=yn.prototype=new Fm();_.n=Bn;_.p=Cn;_.tI=0;function io(b,a){throw Bm(new Am(),'Index: '+a+', Size: '+b.b);}
function jo(a){return Fn(new En(),a);}
function ko(b,a){throw wn(new vn(),'add');}
function lo(a){this.m(this.fb(),a);return true;}
function mo(e){var a,b,c,d,f;if(e===this){return true;}if(!ic(e,13)){return false;}f=hc(e,13);if(this.fb()!=f.fb()){return false;}c=jo(this);d=f.y();while(bo(c)){a=co(c);b=co(d);if(!(a===null?b===null:a.eQ(b))){return false;}}return true;}
function no(){var a,b,c,d;c=1;a=31;b=jo(this);while(bo(b)){d=co(b);c=31*c+(d===null?0:d.hC());}return c;}
function oo(){return jo(this);}
function po(a){throw wn(new vn(),'remove');}
function Dn(){}
_=Dn.prototype=new yn();_.m=ko;_.n=lo;_.eQ=mo;_.hC=no;_.y=oo;_.bb=po;_.tI=24;function Cp(a){{Fp(a);}}
function Dp(a){Cp(a);return a;}
function Ep(b,a){pq(b.a,b.b++,a);return true;}
function Fp(a){a.a=z();a.b=0;}
function bq(b,a){if(a<0||a>=b.b){io(b,a);}return lq(b.a,a);}
function cq(b,a){return dq(b,a,0);}
function dq(c,b,a){if(a<0){io(c,a);}for(;a<c.b;++a){if(kq(b,lq(c.a,a))){return a;}}return (-1);}
function eq(c,a){var b;b=bq(c,a);nq(c.a,a,1);--c.b;return b;}
function fq(d,a,b){var c;c=bq(d,a);pq(d.a,a,b);return c;}
function hq(a,b){if(a<0||a>this.b){io(this,a);}gq(this.a,a,b);++this.b;}
function iq(a){return Ep(this,a);}
function gq(a,b,c){a.splice(b,0,c);}
function jq(a){return cq(this,a)!=(-1);}
function kq(a,b){return a===b||a!==null&&a.eQ(b);}
function mq(a){return bq(this,a);}
function lq(a,b){return a[b];}
function oq(a){return eq(this,a);}
function nq(a,c,b){a.splice(c,b);}
function pq(a,b,c){a[b]=c;}
function qq(){return this.b;}
function Bp(){}
_=Bp.prototype=new Dn();_.m=hq;_.n=iq;_.p=jq;_.v=mq;_.bb=oq;_.fb=qq;_.tI=25;_.a=null;_.b=0;function Af(a){Dp(a);return a;}
function Cf(d,c){var a,b;for(a=jo(d);bo(a);){b=hc(co(a),7);b.C(c);}}
function zf(){}
_=zf.prototype=new Bp();_.tI=26;function Eh(a){a.h=uh(new ph());}
function Fh(a){Eh(a);a.g=zc();a.c=xc();rc(a.g,a.c);a.db(a.g);ok(a,1);return a;}
function ai(d,c,b){var a;bi(d,c);if(b<0){throw Bm(new Am(),'Column '+b+' must be non-negative: '+b);}a=d.a;if(a<=b){throw Bm(new Am(),'Column index: '+b+', Column size: '+d.a);}}
function bi(c,a){var b;b=c.b;if(a>=b||a<0){throw Bm(new Am(),'Row index: '+a+', Row size: '+b);}}
function ci(e,c,b,a){var d;d=hh(e.d,c,b);gi(e,d,a);return d;}
function ei(a){return yc();}
function fi(d,b,a){var c,e;e=oh(d.f,d.c,b);c=sg(d);fd(e,c,a);}
function gi(d,c,a){var b,e;b=cd(c);e=null;if(b!==null){e=wh(d.h,b);}if(e!==null){ji(d,e);return true;}else{if(a){md(c,'');}return false;}}
function ji(b,c){var a;if(c.j!==b){return false;}fj(b,c);a=c.k;hd(dd(a),a);zh(b.h,a);return true;}
function hi(d,b,a){var c,e;ai(d,b,a);c=ci(d,b,a,false);e=oh(d.f,d.c,b);hd(e,c);}
function ii(d,c){var a,b;b=d.a;for(a=0;a<b;++a){ci(d,c,a,false);}hd(d.c,oh(d.f,d.c,c));}
function ki(b,a){b.d=a;}
function li(b,a){b.e=a;lh(b.e);}
function mi(e,c,a,b){var d;tg(e,c,a);d=ci(e,c,a,b===null);if(b!==null){md(d,b);}}
function ni(b,a){b.f=a;}
function oi(d,b,a,e){var c;tg(d,b,a);if(e!==null){cl(e);c=ci(d,b,a,true);xh(d.h,e);rc(c,e.k);dj(d,e);}}
function pi(){return Ah(this.h);}
function qi(a){switch(Ec(a)){case 1:{break;}default:}}
function ri(a){return ji(this,a);}
function Ag(){}
_=Ag.prototype=new cj();_.y=pi;_.B=qi;_.cb=ri;_.tI=27;_.c=null;_.d=null;_.e=null;_.f=null;_.g=null;function pg(a){Fh(a);ki(a,eh(new dh(),a));ni(a,new mh());li(a,jh(new ih(),a));return a;}
function qg(c,b,a){pg(c);xg(c,b,a);return c;}
function sg(b){var a;a=ei(b);md(a,'&nbsp;');return a;}
function tg(c,b,a){ug(c,b);if(a<0){throw Bm(new Am(),'Cannot access a column with a negative index: '+a);}if(a>=c.a){throw Bm(new Am(),'Column index: '+a+', Column size: '+c.a);}}
function ug(b,a){if(a<0){throw Bm(new Am(),'Cannot access a row with a negative index: '+a);}if(a>=b.b){throw Bm(new Am(),'Row index: '+a+', Row size: '+b.b);}}
function xg(c,b,a){vg(c,a);wg(c,b);}
function vg(d,a){var b,c;if(d.a==a){return;}if(a<0){throw Bm(new Am(),'Cannot set number of columns to '+a);}if(d.a>a){for(b=0;b<d.b;b++){for(c=d.a-1;c>=a;c--){hi(d,b,c);}}}else{for(b=0;b<d.b;b++){for(c=d.a;c<a;c++){fi(d,b,c);}}}d.a=a;}
function wg(b,a){if(b.b==a){return;}if(a<0){throw Bm(new Am(),'Cannot set number of rows to '+a);}if(b.b<a){yg(b.c,a-b.b,b.a);b.b=a;}else{while(b.b>a){ii(b,--b.b);}}}
function yg(g,f,c){var h=$doc.createElement('td');h.innerHTML='&nbsp;';var d=$doc.createElement('tr');for(var b=0;b<c;b++){var a=h.cloneNode(true);d.appendChild(a);}g.appendChild(d);for(var e=1;e<f;e++){g.appendChild(d.cloneNode(true));}}
function og(){}
_=og.prototype=new Ag();_.tI=28;_.a=0;_.b=0;function Fi(a){a.db(uc());ok(a,131197);mk(a,'gwt-Label');return a;}
function bj(a){switch(Ec(a)){case 1:break;case 4:case 8:case 64:case 16:case 32:break;case 131072:break;}}
function Ei(){}
_=Ei.prototype=new qk();_.B=bj;_.tI=29;function si(a){Fi(a);a.db(uc());ok(a,125);mk(a,'gwt-HTML');return a;}
function ti(b,a){si(b);vi(b,a);return b;}
function vi(b,a){md(b.k,a);}
function zg(){}
_=zg.prototype=new Ei();_.tI=30;function Cg(a){{Fg(a);}}
function Dg(b,a){b.b=a;Cg(b);return b;}
function Fg(a){while(++a.a<a.b.b.b){if(bq(a.b.b,a.a)!==null){return;}}}
function ah(a){return a.a<a.b.b.b;}
function bh(){return ah(this);}
function ch(){var a;if(!ah(this)){throw new ks();}a=bq(this.b.b,this.a);Fg(this);return a;}
function Bg(){}
_=Bg.prototype=new Fm();_.x=bh;_.A=ch;_.tI=0;_.a=(-1);function eh(b,a){b.a=a;return b;}
function gh(e,d,c,a){var b=d.rows[c].cells[a];return b==null?null:b;}
function hh(c,b,a){return gh(c,c.a.c,b,a);}
function dh(){}
_=dh.prototype=new Fm();_.tI=0;function jh(b,a){b.b=a;return b;}
function lh(a){if(a.a===null){a.a=vc('colgroup');fd(a.b.g,a.a,0);rc(a.a,vc('col'));}}
function ih(){}
_=ih.prototype=new Fm();_.tI=0;_.a=null;function oh(c,a,b){return a.rows[b];}
function mh(){}
_=mh.prototype=new Fm();_.tI=0;function th(a){a.b=Dp(new Bp());}
function uh(a){th(a);return a;}
function wh(c,a){var b;b=Ch(a);if(b<0){return null;}return hc(bq(c.b,b),8);}
function xh(b,c){var a;if(b.a===null){a=b.b.b;Ep(b.b,c);}else{a=b.a.a;fq(b.b,a,c);b.a=b.a.b;}Dh(c.k,a);}
function yh(c,a,b){Bh(a);fq(c.b,b,null);c.a=rh(new qh(),b,c.a);}
function zh(c,a){var b;b=Ch(a);yh(c,a,b);}
function Ah(a){return Dg(new Bg(),a);}
function Bh(a){a['__widgetID']=null;}
function Ch(a){var b=a['__widgetID'];return b==null?-1:b;}
function Dh(a,b){a['__widgetID']=b;}
function ph(){}
_=ph.prototype=new Fm();_.tI=0;_.a=null;function rh(c,a,b){c.a=a;c.b=b;return c;}
function qh(){}
_=qh.prototype=new Fm();_.tI=0;_.a=0;_.b=null;function qj(){qj=os;vj=nr(new tq());}
function pj(b,a){qj();jf(b);if(a===null){a=rj();}b.db(a);al(b);return b;}
function sj(){qj();return tj(null);}
function tj(c){qj();var a,b;b=hc(tr(vj,c),9);if(b!==null){return b;}a=null;if(vj.c==0){uj();}ur(vj,c,b=pj(new kj(),a));return b;}
function rj(){qj();return $doc.body;}
function uj(){qj();ee(new lj());}
function kj(){}
_=kj.prototype=new hf();_.tI=31;var vj;function nj(){var a,b;for(b=cp(qp((qj(),vj)));jp(b);){a=hc(kp(b),9);if(a.i){bl(a);}}}
function oj(){return null;}
function lj(){}
_=lj.prototype=new Fm();_.F=nj;_.ab=oj;_.tI=32;function ak(){ak=os;ql(),sl;}
function Fj(b,a){ql(),sl;hg(b,a);ok(b,1024);return b;}
function bk(a){return ad(a.k,'value');}
function ck(b,a){kd(b.k,'value',a!==null?a:'');}
function dk(a){if(this.a===null){this.a=Af(new zf());}Ep(this.a,a);}
function ek(a){var b;jg(this,a);b=Ec(a);if(b==1){if(this.a!==null){Cf(this.a,this);}}else{}}
function Ej(){}
_=Ej.prototype=new gg();_.l=dk;_.B=ek;_.tI=33;_.a=null;function gk(){gk=os;ql(),sl;}
function fk(a){ql(),sl;Fj(a,wc());mk(a,'gwt-TextBox');return a;}
function Dj(){}
_=Dj.prototype=new Ej();_.tI=34;function xk(b,a){b.a=cc('[Lcom.google.gwt.user.client.ui.Widget;',[0],[8],[4],null);return b;}
function yk(a,b){Bk(a,b,a.b);}
function Ak(b,c){var a;for(a=0;a<b.b;++a){if(b.a[a]===c){return a;}}return (-1);}
function Bk(d,e,a){var b,c;if(a<0||a>d.b){throw new Am();}if(d.b==d.a.a){c=cc('[Lcom.google.gwt.user.client.ui.Widget;',[0],[8],[d.a.a*2],null);for(b=0;b<d.a.a;++b){dc(c,b,d.a[b]);}d.a=c;}++d.b;for(b=d.b-1;b>a;--b){dc(d.a,b,d.a[b-1]);}dc(d.a,a,e);}
function Ck(a){return tk(new sk(),a);}
function Dk(c,b){var a;if(b<0||b>=c.b){throw new Am();}--c.b;for(a=b;a<c.b;++a){dc(c.a,a,c.a[a+1]);}dc(c.a,c.b,null);}
function Ek(b,c){var a;a=Ak(b,c);if(a==(-1)){throw new ks();}Dk(b,a);}
function rk(){}
_=rk.prototype=new Fm();_.tI=0;_.a=null;_.b=0;function tk(b,a){b.b=a;return b;}
function vk(){return this.a<this.b.b-1;}
function wk(){if(this.a>=this.b.b){throw new ks();}return this.b.a[++this.a];}
function sk(){}
_=sk.prototype=new Fm();_.x=vk;_.A=wk;_.tI=0;_.a=(-1);function ql(){ql=os;rl=nl(new ml());sl=rl;}
function pl(a){ql();return a;}
function ll(){}
_=ll.prototype=new Fm();_.tI=0;var rl,sl;function ol(){ol=os;ql();}
function nl(a){ol();pl(a);return a;}
function ml(){}
_=ml.prototype=new ll();_.tI=0;function Cl(a){a.db(uc());return a;}
function El(e){var a,b,c,d,f;if(e.d===null){return;}b='<object width="100%" height="100%"';b+='>';c=ir(sr(e.d.d));while(br(c)){a=cr(c);d=hc(a.t(),1);f=hc(a.u(),1);b+='<param name="'+d+'" value="'+f+'"/>';}b+='<embed height="100%" width="100%" type="'+e.d.c+'"'+'src="'+e.d.b+'"';if(e.d.a!==null){b+=' pluginspage="'+e.d.a+'"';}b+='><\/embed>';b+='<\/object>';md(e.k,b);}
function Fl(b,a){am(b,a,true);}
function am(c,a,b){c.d=a;if(b){El(c);}}
function tl(){}
_=tl.prototype=new qk();_.tI=35;_.d=null;function vl(a){a.d=nr(new tq());}
function wl(c,d,a,b){vl(c);c.b=a;Bl(c,d);return c;}
function yl(b,a,c){ur(b.d,a,c);}
function zl(b,a){b.a=a;}
function Al(b,a){b.b=a;}
function Bl(a,b){switch(b){case 0:a.c='application/x-shockwave-flash';break;default:throw vm(new um(),'type invalid: Unrecognized type');}}
function ul(){}
_=ul.prototype=new Fm();_.tI=0;_.a=null;_.b=null;_.c=null;function cm(b,a){dm(b,a,false);return b;}
function dm(c,b,a){Cl(c);c.a=a;am(c,wl(new ul(),0,null,c),false);hm(c,b);return c;}
function fm(a){var b;b='http://www.youtube.com/v/'+a.b;if(a.a){b+='&autoplay=1';}if(a.c){b+='&rel=1';}else{b+='&rel=0';}return b;}
function gm(b,a){b.a=a;}
function hm(c,b){var a,d;c.b=b;d=fm(c);a=c.d;Al(a,d);yl(a,'movie',d);El(c);}
function im(b,a){b.c=a;}
function bm(){}
_=bm.prototype=new tl();_.tI=36;_.a=false;_.b=null;_.c=false;function tn(b,a){a;return b;}
function sn(){}
_=sn.prototype=new Fm();_.tI=3;function sm(b,a){tn(b,a);return b;}
function rm(){}
_=rm.prototype=new sn();_.tI=4;function en(b,a){sm(b,a);return b;}
function dn(){}
_=dn.prototype=new rm();_.tI=5;function km(){}
_=km.prototype=new dn();_.tI=37;function nm(){}
_=nm.prototype=new dn();_.tI=38;function vm(b,a){en(b,a);return b;}
function um(){}
_=um.prototype=new dn();_.tI=39;function ym(b,a){en(b,a);return b;}
function xm(){}
_=xm.prototype=new dn();_.tI=40;function Bm(b,a){en(b,a);return b;}
function Am(){}
_=Am.prototype=new dn();_.tI=41;function Dm(){}
_=Dm.prototype=new dn();_.tI=42;function jn(g){var a=nn;if(!a){a=nn={};}var e=':'+g;var b=a[e];if(b==null){b=0;var f=g.length;var d=f<64?1:f/32|0;for(var c=0;c<f;c+=d){b<<=1;b+=g.charCodeAt(c);}b|=0;a[e]=b;}return b;}
function kn(b,a){return b.substr(a,b.length-a);}
function ln(a,b){return String(a)==b;}
function mn(a){if(!ic(a,1))return false;return ln(this,a);}
function on(){return jn(this);}
_=String.prototype;_.eQ=mn;_.hC=on;_.tI=2;var nn=null;function rn(a){return s(a);}
function wn(b,a){en(b,a);return b;}
function vn(){}
_=vn.prototype=new dn();_.tI=43;function Fn(b,a){b.c=a;return b;}
function bo(a){return a.a<a.c.fb();}
function co(a){if(!bo(a)){throw new ks();}return a.c.v(a.b=a.a++);}
function eo(a){if(a.b<0){throw new xm();}a.c.bb(a.b);a.a=a.b;a.b=(-1);}
function fo(){return bo(this);}
function go(){return co(this);}
function En(){}
_=En.prototype=new Fm();_.x=fo;_.A=go;_.tI=0;_.a=0;_.b=(-1);function op(f,d,e){var a,b,c;for(b=ir(f.s());br(b);){a=cr(b);c=a.t();if(d===null?c===null:d.eQ(c)){if(e){dr(b);}return a;}}return null;}
function pp(b){var a;a=b.s();return so(new ro(),b,a);}
function qp(b){var a;a=sr(b);return ap(new Fo(),b,a);}
function rp(a){return op(this,a,false)!==null;}
function sp(d){var a,b,c,e,f,g,h;if(d===this){return true;}if(!ic(d,14)){return false;}f=hc(d,14);c=pp(this);e=f.z();if(!yp(c,e)){return false;}for(a=uo(c);Bo(a);){b=Co(a);h=this.w(b);g=f.w(b);if(h===null?g!==null:!h.eQ(g)){return false;}}return true;}
function tp(b){var a;a=op(this,b,false);return a===null?null:a.u();}
function up(){var a,b,c;b=0;for(c=ir(this.s());br(c);){a=cr(c);b+=a.hC();}return b;}
function vp(){return pp(this);}
function qo(){}
_=qo.prototype=new Fm();_.o=rp;_.eQ=sp;_.w=tp;_.hC=up;_.z=vp;_.tI=44;function yp(e,b){var a,c,d;if(b===e){return true;}if(!ic(b,15)){return false;}c=hc(b,15);if(c.fb()!=e.fb()){return false;}for(a=c.y();a.x();){d=a.A();if(!e.p(d)){return false;}}return true;}
function zp(a){return yp(this,a);}
function Ap(){var a,b,c;a=0;for(b=this.y();b.x();){c=b.A();if(c!==null){a+=c.hC();}}return a;}
function wp(){}
_=wp.prototype=new yn();_.eQ=zp;_.hC=Ap;_.tI=45;function so(b,a,c){b.a=a;b.b=c;return b;}
function uo(b){var a;a=ir(b.b);return zo(new yo(),b,a);}
function vo(a){return this.a.o(a);}
function wo(){return uo(this);}
function xo(){return this.b.a.c;}
function ro(){}
_=ro.prototype=new wp();_.p=vo;_.y=wo;_.fb=xo;_.tI=46;function zo(b,a,c){b.a=c;return b;}
function Bo(a){return a.a.x();}
function Co(b){var a;a=b.a.A();return a.t();}
function Do(){return Bo(this);}
function Eo(){return Co(this);}
function yo(){}
_=yo.prototype=new Fm();_.x=Do;_.A=Eo;_.tI=0;function ap(b,a,c){b.a=a;b.b=c;return b;}
function cp(b){var a;a=ir(b.b);return hp(new gp(),b,a);}
function dp(a){return rr(this.a,a);}
function ep(){return cp(this);}
function fp(){return this.b.a.c;}
function Fo(){}
_=Fo.prototype=new yn();_.p=dp;_.y=ep;_.fb=fp;_.tI=0;function hp(b,a,c){b.a=c;return b;}
function jp(a){return a.a.x();}
function kp(a){var b;b=a.a.A().u();return b;}
function lp(){return jp(this);}
function mp(){return kp(this);}
function gp(){}
_=gp.prototype=new Fm();_.x=lp;_.A=mp;_.tI=0;function pr(){pr=os;wr=Cr();}
function mr(a){{or(a);}}
function nr(a){pr();mr(a);return a;}
function or(a){a.a=z();a.d=A();a.b=mc(wr,v);a.c=0;}
function qr(b,a){if(ic(a,1)){return as(b.d,hc(a,1))!==wr;}else if(a===null){return b.b!==wr;}else{return Fr(b.a,a,a.hC())!==wr;}}
function rr(a,b){if(a.b!==wr&&Er(a.b,b)){return true;}else if(Br(a.d,b)){return true;}else if(zr(a.a,b)){return true;}return false;}
function sr(a){return gr(new Dq(),a);}
function tr(c,a){var b;if(ic(a,1)){b=as(c.d,hc(a,1));}else if(a===null){b=c.b;}else{b=Fr(c.a,a,a.hC());}return b===wr?null:b;}
function ur(c,a,d){var b;if(a!==null){b=ds(c.d,a,d);}else if(a===null){b=c.b;c.b=d;}else{b=cs(c.a,a,d,jn(a));}if(b===wr){++c.c;return null;}else{return b;}}
function vr(c,a){var b;if(ic(a,1)){b=fs(c.d,hc(a,1));}else if(a===null){b=c.b;c.b=mc(wr,v);}else{b=es(c.a,a,a.hC());}if(b===wr){return null;}else{--c.c;return b;}}
function xr(e,c){pr();for(var d in e){if(d==parseInt(d)){var a=e[d];for(var f=0,b=a.length;f<b;++f){c.n(a[f]);}}}}
function yr(d,a){pr();for(var c in d){if(c.charCodeAt(0)==58){var e=d[c];var b=xq(c.substring(1),e);a.n(b);}}}
function zr(f,h){pr();for(var e in f){if(e==parseInt(e)){var a=f[e];for(var g=0,b=a.length;g<b;++g){var c=a[g];var d=c.u();if(Er(h,d)){return true;}}}}return false;}
function Ar(a){return qr(this,a);}
function Br(c,d){pr();for(var b in c){if(b.charCodeAt(0)==58){var a=c[b];if(Er(d,a)){return true;}}}return false;}
function Cr(){pr();}
function Dr(){return sr(this);}
function Er(a,b){pr();if(a===b){return true;}else if(a===null){return false;}else{return a.eQ(b);}}
function bs(a){return tr(this,a);}
function Fr(f,h,e){pr();var a=f[e];if(a){for(var g=0,b=a.length;g<b;++g){var c=a[g];var d=c.t();if(Er(h,d)){return c.u();}}}}
function as(b,a){pr();return b[':'+a];}
function cs(f,h,j,e){pr();var a=f[e];if(a){for(var g=0,b=a.length;g<b;++g){var c=a[g];var d=c.t();if(Er(h,d)){var i=c.u();c.eb(j);return i;}}}else{a=f[e]=[];}var c=xq(h,j);a.push(c);}
function ds(c,a,d){pr();a=':'+a;var b=c[a];c[a]=d;return b;}
function es(f,h,e){pr();var a=f[e];if(a){for(var g=0,b=a.length;g<b;++g){var c=a[g];var d=c.t();if(Er(h,d)){if(a.length==1){delete f[e];}else{a.splice(g,1);}return c.u();}}}}
function fs(c,a){pr();a=':'+a;var b=c[a];delete c[a];return b;}
function tq(){}
_=tq.prototype=new qo();_.o=Ar;_.s=Dr;_.w=bs;_.tI=47;_.a=null;_.b=null;_.c=0;_.d=null;var wr;function vq(b,a,c){b.a=a;b.b=c;return b;}
function xq(a,b){return vq(new uq(),a,b);}
function yq(b){var a;if(ic(b,16)){a=hc(b,16);if(Er(this.a,a.t())&&Er(this.b,a.u())){return true;}}return false;}
function zq(){return this.a;}
function Aq(){return this.b;}
function Bq(){var a,b;a=0;b=0;if(this.a!==null){a=this.a.hC();}if(this.b!==null){b=this.b.hC();}return a^b;}
function Cq(a){var b;b=this.b;this.b=a;return b;}
function uq(){}
_=uq.prototype=new Fm();_.eQ=yq;_.t=zq;_.u=Aq;_.hC=Bq;_.eb=Cq;_.tI=48;_.a=null;_.b=null;function gr(b,a){b.a=a;return b;}
function ir(a){return Fq(new Eq(),a.a);}
function jr(c){var a,b,d;if(ic(c,16)){a=hc(c,16);b=a.t();if(qr(this.a,b)){d=tr(this.a,b);return Er(a.u(),d);}}return false;}
function kr(){return ir(this);}
function lr(){return this.a.c;}
function Dq(){}
_=Dq.prototype=new wp();_.p=jr;_.y=kr;_.fb=lr;_.tI=49;function Fq(c,b){var a;c.c=b;a=Dp(new Bp());if(c.c.b!==(pr(),wr)){Ep(a,vq(new uq(),null,c.c.b));}yr(c.c.d,a);xr(c.c.a,a);c.a=jo(a);return c;}
function br(a){return bo(a.a);}
function cr(a){return a.b=hc(co(a.a),16);}
function dr(a){if(a.b===null){throw ym(new xm(),'Must call next() before remove().');}else{eo(a.a);vr(a.c,a.b.t());a.b=null;}}
function er(){return br(this);}
function fr(){return cr(this);}
function Eq(){}
_=Eq.prototype=new Fm();_.x=er;_.A=fr;_.tI=0;_.a=null;_.b=null;function ks(){}
_=ks.prototype=new dn();_.tI=50;function jm(){Ab(yb(new E()));}
function gwtOnLoad(b,d,c){$moduleName=d;$moduleBase=c;if(b)try{jm();}catch(a){b(d);}else{jm();}}
var lc=[{},{},{1:1},{4:1},{4:1},{4:1},{4:1},{2:1},{7:1},{7:1},{7:1},{7:1},{7:1},{7:1},{2:1,5:1},{2:1},{6:1},{8:1,10:1,11:1,12:1},{8:1,10:1,11:1,12:1},{8:1,10:1,11:1,12:1},{8:1,10:1,11:1,12:1},{8:1,10:1,11:1,12:1},{8:1,10:1,11:1,12:1},{3:1,8:1,10:1,11:1,12:1},{13:1},{13:1},{13:1},{8:1,10:1,11:1,12:1},{8:1,10:1,11:1,12:1},{8:1,10:1,11:1,12:1},{8:1,10:1,11:1,12:1},{8:1,9:1,10:1,11:1,12:1},{6:1},{8:1,10:1,11:1,12:1},{8:1,10:1,11:1,12:1},{8:1,10:1,11:1,12:1},{8:1,10:1,11:1,12:1},{4:1},{4:1},{4:1},{4:1},{4:1},{4:1},{4:1},{14:1},{15:1},{15:1},{14:1},{16:1},{15:1},{4:1}];if (com_google_gwt_demos_youtubeviewer_YouTubeViewerDemo) {  var __gwt_initHandlers = com_google_gwt_demos_youtubeviewer_YouTubeViewerDemo.__gwt_initHandlers;  com_google_gwt_demos_youtubeviewer_YouTubeViewerDemo.onScriptLoad(gwtOnLoad);}})();