if (typeof Wildfire == 'undefined') {
	Wildfire= new Object();
	Wildfire.LinkedLoading = true;
	Wildfire._pixeIframeCreated = false;
	Wildfire._NextZIndex=1000;
	Wildfire._runningID=0;	
} 

Wildfire.Flash={

isIE  : (navigator.appVersion.indexOf("MSIE") != -1) ? true : false,
isWin : (navigator.appVersion.toLowerCase().indexOf("win") != -1) ? true : false,
isOpera : (navigator.userAgent.indexOf("Opera") != -1) ? true : false,

getFlashVersion:function() {
    var version = -1;
    if (navigator.plugins != null && navigator.plugins.length > 0) {
	    if (navigator.plugins["Shockwave Flash"]) {
		    var flashDescription = navigator.plugins["Shockwave Flash"].description;
		    if (flashDescription!=null) {
		        version = flashDescription.split(" ")[2].split(".")[0];
		    }
	    }
    }
    else if ( this.isIE && this.isWin && !this.isOpera ) {
	    try {
		    var axo = new ActiveXObject("ShockwaveFlash.ShockwaveFlash.7");
		    var flashDescription = axo.GetVariable("$version");
	    } catch (e) {}
	    if (flashDescription!=null) {
	        version = flashDescription.split(" ")[1].split(",")[0];
	    }
    }
    return version;
},

AC_Generateobj:function(objAttrs, params, embedAttrs) { 
    var str = '';
    if (this.isIE && this.isWin && !this.isOpera)	{
	    str += '<object ';
	    for (var i in objAttrs) {str += i + '="' + objAttrs[i] + '" ';}
	    str += '>';
	    for (var i in params) {str += '<param name="' + i + '" value="' + params[i] + '" /> ';}
	    str += '</object>';
    }
    else {
	    str += '<embed ';
	    for (var i in embedAttrs) {str += i + '="' + embedAttrs[i] + '" ';}
	    str += '> </embed>';
    }
    return str;
},

AC_FL_GetContent:function(){
	var ret = this.AC_GetArgs(arguments);
	return this.AC_Generateobj(ret.objAttrs, ret.params, ret.embedAttrs);
},

AC_GetArgs:function(args, classid, mimeType){
	var ret = {};
	ret.embedAttrs = {};
	ret.params = {};
	ret.objAttrs = {};
	for (var i=0; i < args.length; i=i+2){
		var currArg = args[i].toLowerCase();    
		switch (currArg){	
			case "movie":	
				ret.embedAttrs["src"] = args[i+1];
				ret.params["movie"] = args[i+1];
			break;
			case "id":  
			case "width":
			case "height":
			case "align":
			case "name":
				ret.embedAttrs[args[i]] = ret.objAttrs[args[i]] = args[i+1];
			break;
			default:
				ret.embedAttrs[args[i]] = ret.params[args[i]] = args[i+1];
		}
  }
  ret.objAttrs['codebase']='http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,0,0';
  ret.objAttrs["classid"] = "clsid:d27cdb6e-ae6d-11cf-96b8-444553540000";
  ret.embedAttrs["type"] ="application/x-shockwave-flash";
  ret.embedAttrs['pluginspage']='http://www.macromedia.com/go/getflashplayer';
  
  return ret;
}

} // Wildfire.Flash

// Event handlers
Wildfire.onClose = Wildfire.onPostProfile = Wildfire.onPostComment = Wildfire.onSend = Wildfire.onEmail = function(){};

Wildfire.modules = new Object();
Wildfire.modulesArray = new Array();


/*** PUBLIC METHODS ***/
	
Wildfire.initShare = function(partner, targetId, width, height, config)	{   
    config.showEmail=true;
    config.showPost=false;
    config.showMobile=false;
    if (config.defaultTemplate) config.emailBody=config.defaultTemplate;
    if (config.emailTemplate) config.emailBody=config.emailTemplate;
    config.emailBody=config.emailBody.replace('@@userMsg@@','$userMsg$');
    return Wildfire._createFlashModule("post",''+partner,targetId,width,height,config);
}

Wildfire.initPost = function(partner, targetId, width, height, config) {	
	return Wildfire._createFlashModule("post",''+partner,targetId,width,height,config);
}

//DEPRECATED, replaced by initShare */
Wildfire.init = Wildfire.initShare;

//DEPRECATED, replaced by module.applyConfig*/
Wildfire.applyConfig= function(config) {
	if (isnotnull(Wildfire.share)) Wildfire.share.applyConfig(config);
}

/*** Flash Interface ***/
//this is Called from the SWF
Wildfire._GetFlashModuleXMLConfig=function (targetId){
    document.getElementById(targetId).style.background = '';
	//hide the js progress indicator -- this is a mac issue fix.;
	var pdiv = document.getElementById(targetId+"_progress");
	if (pdiv!=null) {
		pdiv.innerHTML='&nbsp;';
		pdiv.style.display = "none";
		pdiv.style.visibility = "hidden";
	}
	
	var xs= ['config',[], [
			'display',['width','height','showCodeBox','rememberMeVisible','emailImportProviders','networksToShow','networksToHide','bookmarksToShow','bookmarksToHide','bulletinChecked','showEmail','showPost','showBookmark','showDesktop', 'showCloseButton'],
			'body',['font=fontType','size=fontSize'],[
				'background',['frame-color=frameColor','background-color=internalColor','corner-roundness=cornerRoundness'],
				'controls',[], [
					'textboxes',[],	[
						'inputs',['color=textInputColor','background-color=textInputBackgroundColor','frame-color=textInputBorderColor']
						],
					'snbuttons',['color=tabTextColor|snButtonsTextColor','background-color=snButtonsBackgroundColor','frame-color=snButtonsFrameColor','over-color=tabTextColor|snButtonsOverTextColor','over-background-color=snButtonsOverBackgroundColor','over-frame-color=snButtonsOverFrameColor'],
					'buttons',['font=fontType|buttonFontType','color=buttonTextColor']
					],
				'texts background-color="transparent" ',['color=textColor'],[
					'messages',['color=messageTextColor'],
					'links',['font=linkFontType','color=linkTextColor'],
					'privacy',['color=privacyTextColor']
					]
				]
			]
		];

	var oConfig=Wildfire._GetFlashModuleConfig(targetId);
	var s=Wildfire._BuildXMLConfigFromJSON(oConfig,xs);
	
	return s;
}

Wildfire._GetFlashModuleConfigAttribute=function (targetId,configAttribute,canBeTextareaID,network){
    document.getElementById(targetId).style.background = '';
	//hide the js progress indicator -- this is a mac issue fix.;
	var pdiv = document.getElementById(targetId+"_progress");
	if (pdiv!=null) {
		pdiv.innerHTML='&nbsp;';
		pdiv.style.display = "none";
		pdiv.style.visibility = "hidden";
	}

	var module=Wildfire.modules[targetId];
	if (module!=null) {
		var AttribValue=module.config[configAttribute];
		if (typeof AttribValue=='undefined') return null;
		if (typeof AttribValue=='function') return AttribValue(configAttribute,network);
		if (canBeTextareaID==true) {
			if ( isnotnull(AttribValue) ) {
				try {
					var element=document.getElementById(AttribValue);
					
					if ( isnotnull(element) ) {
						return element.value;
					}
					else {
						return AttribValue;
					}
					
				} catch (e) {
					//GIGYAONLY:alert('Unable to get template for module:' + targetId + ', configAttribute :'+ configAttribute + '\n' + ex.description);
					return AttribValue;	
				}
			}
		}
		else {
			return AttribValue;
		}
	}
	else {
		return {error:'Modlue not found',MID:targetId};
	}
}

Wildfire._GetFlashModuleConfig=function (targetId){
	if (Wildfire.modules == null) {
		alert('Wildfire has no modules yet');
	}
	var module=Wildfire.modules[targetId];
	if (module!=null) {
	
		var res=module.config;
		return res;
	}
	else {
		return null;
	}
}
	

/*** PRIVATE METHODS ***/

Wildfire._BuildXMLConfigFromJSON=function (oConfig,xs) {

	var s=new Array();
	try{
	for(var i=0;i<xs.length;i+=2){

		s[s.length]='<'+xs[i]+' ';
		var atts=xs[i+1];

		for (var ia=0;ia<atts.length;ia++) {
		
			var attrAndKeys=atts[ia].split('=');
			var key=attrAndKeys[0];
			var valkeys;
			if (attrAndKeys.length>1) {
				valkeys=attrAndKeys[1]
			}
			else {
				valkeys=attrAndKeys[0];
			}
			
			var arrKeys=valkeys.split('|')
			for (var ikey=0;ikey<arrKeys.length;ikey++) {
				if (typeof oConfig[arrKeys[ikey]] != 'undefined') {
					s[s.length]=key+'="';
					s[s.length]=(''+oConfig[arrKeys[ikey]]).replace('&','&amp;').replace('"','&quot;').replace('<','&lt;').replace('>','&gt;') ;
					s[s.length]='" ';
					break;
				}
			}
		}
		if (i+2==xs.length) { // there are no more nodes
			s[s.length]='/>';						
		}
		else {
			if (typeof xs[i+2]!='string') {
			  s[s.length]='>';
			  s[s.length]=Wildfire._BuildXMLConfigFromJSON(oConfig,xs[i+2]);
			  s[s.length]='</'+xs[i].split(' ')[0]+'>';
			  i++; // skip the array node.
			}
			else {
				s[s.length]='/>';
			}
		}
		
	}
	}
	catch(e){
//			return ('***');
	}
	return s.join('');
}

Wildfire._origOnLoad = null;
Wildfire._onLoad = function(evt)
{
	
	Wildfire.onLoad=Wildfire._origOnLoad; //restore
	
	if (Wildfire.LinkedLoading==false) {
		if (Wildfire._origOnLoad!=null) {
			Wildfire._origOnLoad(evt);
		}
		return;
	}
	Wildfire.LinkedLoading=false;
	// this can not be called before setting Wildfire.LinkedLoading to false
	// or it might trigger additional loads while in LinkedLoading 
	if (Wildfire._origOnLoad!=null) {
		Wildfire._origOnLoad(evt);
	}
	
	if ((evt.ModuleID == Wildfire.modulesArray[0].id))
	{
		for (var i=1;i<Wildfire.modulesArray.length; i++)
		{
			if (!Wildfire.modulesArray[i].ready) 
			{
				try {Wildfire.modulesArray[i].init(true);} catch(e) {}
			}
				
		}
	}
	
};

Wildfire._createTextareaModule = function(width, height, config, targetId) {
    var str = '';
    var defaultContent = config.defaultContent;
    if (document.getElementById(defaultContent) != null) {
        defaultContent = document.getElementById(defaultContent).value;
    }
    
    if (!(config.UIConfig && (config.UIConfig.indexOf('showCodeBox="false"')!=-1 || config.UIConfig.indexOf("showCodeBox='false'")!=-1))) {    
        str += '<textarea style="width: ' + width + 'px; height: ' + height + 'px">';
        str += defaultContent;
        str += '</textarea>';
	}
    var container = document.getElementById(targetId);
    container.style.width  = width + "px";
    container.style.height = height + "px";
    container.innerHTML = str;    
}

Wildfire._createFlashModule = function (moduleType, partner, targetId, width, height, config/*, getParams*/)	
{	
/*
    if (moduleType == 'post' && Wildfire.Flash.getFlashVersion()<8) {
        Wildfire._createTextareaModule(width, height, config, targetId);
        return;
    }*/

	// set bookmarkURL if not set
	
	try {
		if (typeof config['bookmarkURL'] == 'undefined')
			config['bookmarkURL'] = document.location.href;
	} catch (e) {}


	// hook onLoad 
	if (Wildfire.LinkedLoading) 
	{
		if (typeof Wildfire.onLoad != 'undefined' && Wildfire.onLoad!=Wildfire._onLoad) 
			Wildfire._origOnLoad = Wildfire.onLoad;
	
		Wildfire.onLoad = Wildfire._onLoad;
	}

	var blnRecreate=false;
	var idxInArray;
	if (this.modules[targetId] != null) {
		blnRecreate=true;
		document.getElementById(targetId).innerHTML='&nbsp;';
		idxInArray=this.modules[targetId].idxInArray;
	}
	else {
		idxInArray=this.modulesArray.length;
	}
	try {
		config.location = document.location.href;
	} catch(err) {}
	
	config.partner = partner;
	config.width = width;
	config.height = height;

	// validate input params
	if ( undef(moduleType) || undef(partner) || undef(targetId) || undef(width) || undef(height)|| undef(config)) return;
	
	
	var module = new Wildfire._FlashModule();
	this.modulesArray[idxInArray] = this[targetId] = this.modules[targetId] = module;
	
	module.idxInArray=idxInArray;
	module.copyConfig(config);
	module.queued = false;
	module.ready = false;
	module.type = moduleType;
	module.id = targetId;
	module.partner = partner;
	module.width = width;
	module.height = height;
	module.container = document.getElementById(targetId);		  
	module.container.style.width  = width + "px";
	module.container.style.height = height + "px";
	
	/*
	module.qsParams = new Array();
	var getParamArray = getParams.split(',');
	for (var i=0; i<getParamArray.length ; i++)
		Wildfire._addQSParam(module,getParamArray[i]);
	*/
	if (!Wildfire.LinkedLoading || this.modulesArray.length==1 || this.modulesArray[0].ready)
	{
		module.init(true); // true means check ping for safe mode
	} else {
		module.queued = true;
	}
	return module;
};

Wildfire._eventObjects = {};

Wildfire._onCallback=Wildfire._raiseEvent = function(WFEvent) {		
    var handlerId='h_' + (++Wildfire._runningID);
    Wildfire._eventObjects[handlerId] = WFEvent;
	var handlerName=Wildfire._eventHandlersMap[WFEvent.type];
    window.setTimeout('Wildfire.raiseEvent2(\'' + handlerId + '\')' , 1);
}

Wildfire.raiseEvent2 = function(handlerId) {
    try {
        var eventObject = Wildfire._eventObjects[handlerId];
        var handlerName = Wildfire._eventHandlersMap[eventObject.type];
	    if (isnotnull(Wildfire[handlerName]))
		    Wildfire[handlerName](eventObject);
    	
	    if (isnotnull(Wildfire.modules[eventObject.ModuleID].config[handlerName]))
		    Wildfire.modules[eventObject.ModuleID].config[handlerName](eventObject);
    } 
    catch(e) {
        //alert(e);
    }
    delete Wildfire._eventObjects[handlerId];
}

Wildfire._eventHandlersMap={ 
	send:'onSend',
	postComment:'onPostComment',
	postProfile:'onPostProfile',
	close:'onClose',
	renderDone:'onRenderDone',
	networkButtonClicked:'onNetworkButtonClicked',
	load:'onLoad',
	copy:'onCopy',
	inspect:'onInspect',
	error:'onError',
	message:'onMessage',
	email:'onEmail'
};

Wildfire._Module=function() {
	// for common functionality of Flash and JS modules
}

Wildfire._FlashModule=function(){}
Wildfire._FlashModule.prototype=new Wildfire._Module();

Wildfire._FlashModule.prototype.init = function(checkPing){
	var html='';
		if ((''+this.config.isApply)!='true' && this.config.hideProgress != true) {
		    document.getElementById(this.id).style.background = 'url(' + this.config.progressImageSrc + ') no-repeat center center';
			//html += '<div style="position:relative;top:50%;text-align:center;font-size:12px;z-index:50;" id="'+this.id+'_progress"><center><img  src="'+this.config.progressImageSrc+'"></center></div>';
		}

		var CDNSubDomain=(Math.random()<0)?'cdn2':'cdn';
		
		var swf='http://'+CDNSubDomain+'.gigya.com/WildFire/swf/wildfire_en.swf';
		if (this.config.lang != null) {
			swf='http://'+CDNSubDomain+'.gigya.com/WildFire/swf/wildfire_' + this.config.lang + '.swf';
		}		

		var wmode = (this.config.nowmode?'':'transparent');
		if (this.config.wmodeType) wmode = this.config.wmodeType;
		html += Wildfire.Flash.AC_FL_GetContent(
		'id', 'wfmodule_'+this.id,
		'name', 'wfmodule_'+this.id,
		'width', this.config.width,
		'height', this.config.height,
		'movie', swf,
		'quality', 'high',  
		'align', 'middle',
		'play', 'true',
		'loop', 'true',
		'scale', 'showall',
		'wmode', wmode, 
		'devicefont', 'false',
		'bgcolor', ((this.config.nowmode && this.config.outsideColor)?this.config.outsideColor:'#ffffff'),
		'menu', 'true',
		'allowFullScreen', 'false',
		'allowScriptAccess','always',
		'salign', '',
		'flashvars','ModuleID='+ this.id+'&now='+(new Date()).getTime(),
		'swLiveConnect','true'
		)
	
	window['wfmodule_'+this.id] = null;
	//alert('html =' + html);
	if (!Wildfire._pixeIframeCreated) {
		html += "<iframe src='http://cdn.gigya.com/wildfire/do_not_delete.htm' style='width:0;height:0;visibility:hidden' />";
		Wildfire._pixeIframeCreated = true;
	}
		
	this._injectWFCode(html);
	//alert('Html Code  Injected');
	// ExternalInterface bug workaround - 
	window['wfmodule_'+this.id] = document.getElementById('wfmodule_'+this.id);
	//alert('window attribute set, invoking go()');
	//because the flash needs to do externalInterface calls as soon as it starts
	//we can not have it "autoExecute" or the line above this comment would not
	//be executed by the time it tries to call back.
	//window['wfmodule_'+this.id].SetVariable('_root.ready','1');
}

Wildfire._IsModuleReady=function(targetId) {
	return (window['wfmodule_'+targetId] != null)
}

Wildfire._FlashModule.prototype.copyConfig =  function(config) 
{
	// clone config obj to module
	if (config!=null) {
		this.config = {};
		for(var key in config) this.config[key] = config[key];
	}

	// apply default values
	if ( undef(this.config.progressImageSrc) ) 	this.config.progressImageSrc = "http://cdn.gigya.com/WildFire/i/progress_ani.gif";
	//if ( undef(this.config.cornerRoundness) ) 	this.config.cornerRoundness=1;
	if ( undef(this.config.simple) ) this.config.simple = navigator.userAgent.toLowerCase().indexOf('safari')!=-1;
}

Wildfire._FlashModule.prototype._injectWFCode= function(html){
	this.container.innerHTML = html;
};

Wildfire._FlashModule.prototype.applyConfig = function(conf){
	conf.isApply="true";
	return Wildfire._createFlashModule(this.type, this.partner, this.id, this.width, this.height, conf);
};

Wildfire._CopyAtts=function(t,s,atts){ for(k in atts.split(',')){ t[atts[k]]=s[atts[k]]; }}
Wildfire._CopyAllAtts=function(t,s){for(k in s) {t[k]=s[k];}}

function undef(o) { return (typeof(o)=='undefined');}
function def(o) { return (typeof(o)!='undefined');}
function isnotnull(o) { return (def(o) && (o!=null));}

////////////////////////////
//  Wildfire Post Button
///////////////////////////
Wildfire._GetElementPos=function(obj) {
	var curleft = curtop = 0;
	if (obj.offsetParent) {
		do {
			curleft += obj.offsetLeft;
			curtop += obj.offsetTop;
		} while (obj = obj.offsetParent);
	};
	return {left:curleft,top:curtop};
}	

Wildfire._HandleEmbedAndObjectsBelow=function(container,w,h){
	//if (!Gigya.Flash.isFF)  return;
	var blnHide=true;
	
	var cpos=Wildfire._GetElementPos(container);
	var ctop=cpos.top;
	var cleft=cpos.left;
	var cright=cleft+w;
	var cbottom=ctop+h;
	var tags;
	if (Wildfire.Flash.isIE)  {
		tags=['iframe'];
	}
	else {
		tags=['embed','iframe']; //object seems to be unrequired.
	}
	for (var itag=0;itag<tags.length;itag++) {
		tagname=tags[itag];
		elements=document.getElementsByTagName(tagname);
		//alert ('There are ' + elements.length + ' of tag ' + tagname);
		for (var i=0;i<elements.length;i++){
			var el=elements[i];
			if (el.style.visibility!='hidden' && container.childNodes[0]!=el) {
				var epos=Wildfire._GetElementPos(el);
				var etop=epos.top;
				var eleft=epos.left;
				
				var elcs=(document.defaultView)?document.defaultView.getComputedStyle(el, ""):el.currentStyle;
				var eright=eleft+parseInt(elcs.getPropertyValue?elcs.getPropertyValue('width'):elcs.width);
				var ebottom=etop+parseInt(elcs.getPropertyValue?elcs.getPropertyValue('height'):elcs.height);
				
				if (!((etop>cbottom) || (ebottom<ctop) || (eleft >cright) || (eright<cleft))) {
					var isNonGigyaIframe=(tagname=='iframe') && ((el.id+'          ').substr(0,10)!='gigya_ifr_');
					if (
						  ( (tagname=='embed' ) && 
							( (el.getAttribute('wmode')==null) || 
							  (el.getAttribute('wmode')=='') || 
							  (el.getAttribute('wmode')=='window') 
							)
						   ) 
						|| isNonGigyaIframe)  {
						if (blnHide && (container.id != 'coreDiv')) {								
								el.style.visibility='hidden';
								if (container.elementsToShowOnClose == null) container.elementsToShowOnClose=[];
								container.elementsToShowOnClose.push(el);

						}
					}
				}
			}
		}
	}
}

Wildfire._CreateContainer=function(id, noIframe) {
	var ifrel;
	if (Wildfire.Flash.isIE && Wildfire.Flash.isWin && !noIframe) {
		ifrel = document.createElement('IFRAME');
		ifrel.id='gigya_ifr_'+id;
		ifrel.frameBorder="0";
		ifrel.style.border='0px';
		ifrel.style.position='absolute';
		ifrel.style.width='1px';
		ifrel.style.height='1px';
		if (ifrel.style.zIndex!=null) {
			ifrel.style.zIndex=Wildfire._NextZIndex++;
		}
	}

	var el = document.createElement('div');
	el.style.position='absolute';
	if (el.style.zIndex!=null) {
		el.style.zIndex=Wildfire._NextZIndex++;
	}
	var html='';
	el.innerHTML = html;
	el.id=id;
	el.swfLoaded = false;
	if (document.body) {
		if(document.body.insertBefore) {
			if (document.body.firstChild) {
				if (ifrel!=null) document.body.insertBefore(ifrel, document.body.firstChild);
				document.body.insertBefore(el, document.body.firstChild);
			}
		}
	}
	return el;
}

Wildfire._hideWildfirePopup = function(o) {
    var wildfireDiv = document.getElementById(o.ModuleID)
    var ifr=document.getElementById('gigya_ifr_'+o.ModuleID)
	if (ifr!=null) ifr.style.visibility='hidden';
	var elementsToShowOnClose=wildfireDiv.elementsToShowOnClose;
	if (elementsToShowOnClose!=null) {
		for (var i=0;i<elementsToShowOnClose.length;i++) {
			elementsToShowOnClose[i].style.visibility='';
		}
	}
	
	//wildfireDiv.innerHTML='&nbsp;';
	wildfireDiv.style.visibility='hidden';
	//wildfireDiv.parentNode.removeChild(wildfireDiv);
    Wildfire.disposeWildfireButton(o.ModuleID);
}

if (typeof Wildfire._popupConfigs == 'undefined') {
    Wildfire._popupConfigs = [];
}
Wildfire.drawWildfireButton = function(params) {
    if (typeof params.w=='undefined') params.w = 400;
    if (typeof params.h=='undefined') params.h = 300;
    if (params.b==null) {
        params.b = 'click';
    }
    /*if (params.conf['emailBody']==null) {
        params.conf['emailBody'] = '$userMsg$';
    } */     
    if (params.conf['module']=='post' && params.conf['defaultContent']==null) {
        var url=document.location.href;
        params.conf['defaultContent'] = 'Take a look at this: <br /><a href="' + url + '">' + url + '</a>';
    }       
    var altText = '';
    if (typeof(params.btnurl)=='undefined') {
        switch(params.conf['module']) {
            case 'bookmarks': params.btnurl='http://cdn.gigya.com/wildfire/i/bookmark_button.gif'; break;
            case 'email': params['btnurl']='http://cdn.gigya.com/wildfire/i/share-button.gif'; break;
            case 'post': 
            default: params.btnurl='http://cdn.gigya.com/wildfire/i/post-to-button.gif';
        }
    }
    switch(params.conf['module']) {
        case 'bookmarks': 
            altText = 'Add to bookmarks';
            params.conf["showBookmark"] = "true";
            params.conf["showEmail"] = "false";
            params.conf["showPost"] = "false";
            break;
        case 'email':
            params.conf['showEmail'] = 'true';
            params.conf['showPost'] = 'false';
            params.conf['showBookmark'] = 'false';
            break;            
        case 'post': 
            params.conf["showEmail"] = "true";
            params.conf["showBookmark"] = "true";
        default:
            altText = 'Post to my social network or blog';
    }
    params.conf.showCloseButton = true;
    //params.conf.nowmode = true;
    if (params.conf.nowmode!='true' && params.conf.nowmode!=true) {
        params.conf.wmodeType = 'opaque';
    }
    params.conf.cornerRoundness = 0;

    params.conf.onClose = Wildfire._hideWildfirePopup;
    params.conf.onRenderDone = function(o) {
        var popDiv = document.getElementById(o.ModuleID);
        popDiv.style.visibility = "";
        Wildfire._GetContainer(o.ModuleID + 'Progress').style.visibility="hidden";
        //var ifrel = document.getElementById('gigya_ifr_'+divID);
	    //if (ifrel!=null) ifrel.style.visibility = "";
        popDiv.style.zIndex=Wildfire._NextZIndex++;
        if (popDiv.fnDocumentClicked==null) {
            popDiv.fnDocumentClicked = function(evt) {
                var target;
                if (evt['target']) target = evt['target']
                if (evt['srcElement']) target = evt['srcElement'];      
                if (target!=null) Wildfire._documentClicked(target, popDiv);
            }
            if(window.addEventListener){ // Mozilla, Netscape, Firefox
	            document.addEventListener('click', popDiv.fnDocumentClicked, false);
            } else { // IE
	            document.attachEvent('onclick', popDiv.fnDocumentClicked);
            }        
        }
    }
    Wildfire._popupConfigs.push(params.conf);
    
    var configID = Wildfire._popupConfigs.length - 1;
    var html = '<img id="Wildfire_Button' + configID + '" src="' + params.btnurl + '" style="cursor: pointer" border=0 alt="' + altText + '" title="' + altText + '" />';
    if (params.button_divID) {
        document.getElementById(params.button_divID).innerHTML = html
    } else {
        document.write(html);
    }
    var btn = document.getElementById('Wildfire_Button' + configID);
    btn.openWildfirePopup = function() {
        btn.mouseIsOut=true;
        Wildfire._showWildfirePopup(params.partner, params.w, params.h, configID);
    }
    btn.onmouseout = function() {
        btn.mouseIsOut = true;
    }
    if (typeof Wildfire.buttonsData == 'undefined') Wildfire.buttonsData = {};
    switch(params.b.toLowerCase()) {
        case 'mouseover': 
            btn.configID = configID;
            Wildfire.buttonsData[configID] = function() {
                if (!btn.mouseIsOut) btn.openWildfirePopup();
            }
            btn.onmouseover = function() {
                btn.mouseIsOut=false;
                setTimeout('Wildfire.buttonsData[' + btn.configID + ']()', 500);
            }
            break;
        case 'click': 
        default: btn.onclick = btn.openWildfirePopup;
    }   
    return 'wildfire_postDiv_' + configID;
}
Wildfire.disposeWildfireButton = function(id) {
    var wfDiv = document.getElementById(id);
    var progressDiv = document.getElementById(id + 'Progress');
    var ifrel = document.getElementById('gigya_ifr_'+id);
    if (wfDiv) {
        if(window.removeEventListener){ // Mozilla, Netscape, Firefox
	        document.removeEventListener('click', wfDiv.fnDocumentClicked, false);
        } else { // IE
	        document.detachEvent('onclick', wfDiv.fnDocumentClicked);
        }      
        var elementsToShowOnClose=wfDiv.elementsToShowOnClose;
	    if (elementsToShowOnClose!=null) {
		    for (var i=0;i<elementsToShowOnClose.length;i++) {
			    elementsToShowOnClose[i].style.visibility='';
		    }
	    }
	}
    if (wfDiv) document.body.removeChild(wfDiv);
    if (progressDiv) document.body.removeChild(progressDiv);
    if (ifrel) document.body.removeChild(ifrel);
}

Wildfire.renderPostButton = function(partner, width, height, config, btnurl, eventType, divID) {
    var params = {
        partner: partner,
        w: width,
        h: height,
        conf: config,
        btnurl: btnurl,
        b: eventType,
        button_divID: divID
    }
    return Wildfire.drawWildfireButton(params);
}

Wildfire._lastID = 0;

Wildfire._getScrollXY=function() {
  var scrOfX = 0, scrOfY = 0;
  if( typeof( window.pageYOffset ) == 'number' ) {
    //Netscape compliant
    scrOfY = window.pageYOffset;
    scrOfX = window.pageXOffset;
  } else if( document.body && ( document.body.scrollLeft || document.body.scrollTop ) ) {
    //DOM compliant
    scrOfY = document.body.scrollTop;
    scrOfX = document.body.scrollLeft;
  } else if( document.documentElement && ( document.documentElement.scrollLeft || document.documentElement.scrollTop ) ) {
    //IE6 standards compliant mode
    scrOfY = document.documentElement.scrollTop;
    scrOfX = document.documentElement.scrollLeft;
  }
  return [ scrOfX, scrOfY ];
}

Wildfire._GetContainer=function(id){
	if (id=='') return null;
	var el=document.getElementById(id);
	if (typeof(el)=='Array') {
		return el[el.length];
	}
	return el;
}

Wildfire._preparePopup = function(w, h, id, btnDiv, progressImageSrc) {
	var wfDiv = Wildfire._CreateContainer(id);
	var progressDiv = Wildfire._CreateContainer(id + 'Progress', true);
	
	var dst;
    
    dst=wfDiv.style;
    
	var vph;
	var vpw;
	var de=document.documentElement;
	
	vph=de.clientHeight;
	vpw=de.clientWidth;
	if (typeof vph=='undefined' || vph==0) {
		vph=document.body.clientHeight;
		vpw=document.body.clientWidth;
	}
	if (typeof vph=='undefined' || vph==0) {
		vph=window.innerHeight;
		vpw=window.innerWidth;
	}
	
	scrl=Wildfire._getScrollXY();
	
	var vpt=scrl[1];
	var vpl=scrl[0];
	var middlePointTop = vpt + Math.floor(vph/2);
	var middlePointLeft = vpl + Math.floor(vpw/2);
	
	//dst.top=''+(vpt+Math.floor((vph-h)/2))+'px';
	//dst.left=''+(vpl+Math.floor((vpw-w)/2))+'px';
	var btnPos = Wildfire._GetElementPos(btnDiv);
	if (btnPos.top>middlePointTop && btnPos.top>h) {
	    dst.top = '' + (btnPos.top - h) + 'px';
	} else {
	    dst.top = '' + (btnPos.top + btnDiv.height) + 'px';
	}
	if (btnPos.left>middlePointLeft) {
	    dst.left = '' + (btnPos.left + btnDiv.width - w) + 'px';
	} else {
	    dst.left = '' + btnPos.left + 'px';
	}
	
	dst.width=''+w+'px';
	dst.height=''+h+'px';
	progressDiv.style.position = 'absolute';
	progressDiv.style.background = 'url(' + progressImageSrc + ') no-repeat center center';
	progressDiv.style.width = dst.width;
	progressDiv.style.height = dst.height;
	progressDiv.style.top = dst.top;
	progressDiv.style.left = dst.left;
	
	var ifrel = document.getElementById('gigya_ifr_'+id);
	if (ifrel!=null) {
	    ifrel.style.top=dst.top;
	    ifrel.style.left=dst.left;
	    ifrel.style.width=dst.width;
	    ifrel.style.height=dst.height;	
	}
	Wildfire._HandleEmbedAndObjectsBelow(wfDiv,w,h);


	
	/*wfDiv.onmouseout = function() {
	    wfDiv.timeoutHandle = window.setTimeout('if (document.getElementById("' + id + '")) Wildfire._hideWildfirePopup({ModuleID: "' + id + '"})',2000);
	}
	wfDiv.onmouseover = function() {
	    window.clearTimeout(wfDiv.timeoutHandle);
	}*/
}


Wildfire._documentClicked = function(target, wfDiv){
    // "target" for Mozilla, Netscape, Firefox et al. ; "srcElement" for IE
    if (target==null) return;
    if (target.id.indexOf('wfmodule_wildfire')==0) return;
    
    if (wfDiv.style.display=='') Wildfire.disposeWildfireButton(wfDiv.id);
}

Wildfire._showWildfirePopup = function(partner, width, height, configID) {
    var divID = 'wildfire_postDiv_' + configID;
    var btnDiv = document.getElementById('Wildfire_Button' + configID);
    var popDiv = Wildfire._GetContainer(divID);
    if (popDiv==null || popDiv.style.visibility=='hidden') {
        Wildfire._preparePopup(width, height, divID, btnDiv, 'http://cdn.gigya.com/WildFire/i/progress_ani.gif');
        Wildfire.initPost(partner, divID, width, height, Wildfire._popupConfigs[configID]);      
    }
}

Wildfire.revealDiv = function(divID, maxHeight) {
    var div = document.getElementById(divID);
    var nextHeight = parseInt(div.style.height.replace('px','')) + 20;
    if (nextHeight<maxHeight) {
	    var ifrel = document.getElementById('gigya_ifr_'+divID);
        div.style.height = nextHeight + 'px';
	    if (ifrel!=null) {
	        ifrel.style.height = nextHeight + 'px';
	    }        
        window.setTimeout('Wildfire.revealDiv("' + divID + '", ' + maxHeight + ')',10);
    } else {
        div.style.height=maxHeight + 'px';
    }
}

//inject CIMP
Wildfire._injectCIMP = function() {
    if (document.getElementById('wildfire_cimp') == null && document.body != null) {
        var cimp = document.createElement('div');
        cimp.id = 'wildfire_cimp';
        cimp.width = 1;
        cimp.height = 1;
        cimp.style.display = 'none';
        cimp.style.visibility = 'hidden';
        cimp.style.position = 'absolute';
        cimp.innerHTML = '<img src="http://cdn.gigya.com/wildfire/i/CIMP.gif?CXNID=2000002.0NXC" />';
        document.body.insertBefore(cimp,document.body.firstChild);
    }
}

if (typeof WildfireBtn!='undefined' && typeof WildfireBtn.pendingButtons!='undefined') {
    for (var i = 0; i < WildfireBtn.pendingButtons.length; i++) {
        Wildfire.drawWildfireButton(WildfireBtn.pendingButtons[i]);
    }
}

if (typeof GigyaToolbar !='undefined'&& typeof GigyaToolbar.onWFLoaded !='undefined') {
    GigyaToolbar.onWFLoaded();
}

window.setTimeout("Wildfire._injectCIMP()",5000);