(function(){
	
	var share = function(title, desc, imgurl, linkurl, callback) {
		if (!callback || typeof(callback) != 'function') {
			callback = function() {};
		}
		$.get("wx_jssdk.jsp",
		{
			debug: false,
			url: window.location.href,
			api: 'onMenuShareAppMessage;onMenuShareTimeline;onMenuShareWeibo;onMenuShareQQ'
		},
		function(script){
			//
			wx.ready(function(){
				wx.error(function(res){
					callback("init", "faild");
				});
				wx.checkJsApi({
					jsApiList: [
						'onMenuShareAppMessage', 
						'onMenuShareTimeline',
						'onMenuShareWeibo',
						'onMenuShareQQ'],
					//需要检测的JS接口列表，所有JS接口列表见附录2,
					success: function(res){
						callback('check', 'success', res);
					}
				});
				wx.onMenuShareAppMessage({
					title: (typeof(title) == "function") ? title() : title,
					desc: (typeof(desc) == "function") ? desc() : desc,
					link: (typeof(linkurl) == "function") ? linkurl() : linkurl,
					imgUrl: (typeof(imgurl) == "function") ? imgurl() : imgurl,
					type: 'link',
					linkdataUrl: '',
					success: function(){
						callback("onMenuShareAppMessage", 'success');
					},
					cancel: function(){
						callback("onMenuShareAppMessage", 'cancel');
					}
				});
				wx.onMenuShareTimeline({
					title: (typeof(title) == "function") ? title() : title,
					link: (typeof(linkurl) == "function") ? linkurl() : linkurl,
					imgUrl: (typeof(imgurl) == "function") ? imgurl() : imgurl,
					success: function(){
						callback("onMenuShareTimeline", 'success');
					},
					cancel: function(){
						callback("onMenuShareTimeline", 'cancel');
					}
				});
				wx.onMenuShareQQ({
					title: (typeof(title) == "function") ? title() : title,
					desc: (typeof(desc) == "function") ? desc() : desc,
					link: (typeof(linkurl) == "function") ? linkurl() : linkurl,
					imgUrl: (typeof(imgurl) == "function") ? imgurl() : imgurl,
					success: function () { 
						callback("onMenuShareQQ", 'success');
					},
					cancel: function () { 
						callback("onMenuShareQQ", 'cancel');
					}
				});
				wx.onMenuShareWeibo({
					title: (typeof(title) == "function") ? title() : title,
					desc: (typeof(desc) == "function") ? desc() : desc,
					link: (typeof(linkurl) == "function") ? linkurl() : linkurl,
					imgUrl: (typeof(imgurl) == "function") ? imgurl() : imgurl,
					success: function () { 
						callback("onMenuShareWeibo", 'success');
					},
					cancel: function () { 
						callback("onMenuShareWeibo", 'cancel');
					}
				});
			});//jssdkready
		});//ajaxready
		
		////////////////////////////////////////////////
		// 微信分享定制
				var dataForWeixin={
						appId:"",
						MsgImg: imgurl,
						TLImg: imgurl,
						url: linkurl,
						title : title,
						desc: desc,
						fakeid : '',
						callback:function(){}
				};
				//
			   var onBridgeReady=function(){
			   WeixinJSBridge.on('menu:share:appmessage', function(argv){
				  WeixinJSBridge.invoke('sendAppMessage',{
					 "appid":dataForWeixin.appId,
					 "img_url":(typeof(dataForWeixin.MsgImg) == "function") ? dataForWeixin.MsgImg() : dataForWeixin.MsgImg,
					 "img_width":"120",
					 "img_height":"120",
					 "link":(typeof(dataForWeixin.url) == "function") ? dataForWeixin.url() : dataForWeixin.url,
					 "desc":(typeof(dataForWeixin.desc) == "function") ? dataForWeixin.desc() : dataForWeixin.desc,
					 "title":(typeof(dataForWeixin.title) == "function") ? dataForWeixin.title() : dataForWeixin.title
				  }, function(res){(dataForWeixin.callback)();});
			   });
			   WeixinJSBridge.on('menu:share:timeline', function(argv){
				  (dataForWeixin.callback)();
				  WeixinJSBridge.invoke('shareTimeline',{
					 "img_url":(typeof(dataForWeixin.MsgImg) == "function") ? dataForWeixin.MsgImg() : dataForWeixin.MsgImg,
					 "img_width":"120",
					 "img_height":"120",
					 "link":(typeof(dataForWeixin.url) == "function") ? dataForWeixin.url() : dataForWeixin.url,
					 "desc":(typeof(dataForWeixin.desc) == "function") ? dataForWeixin.desc() : dataForWeixin.desc,
					 "title":(typeof(dataForWeixin.title) == "function") ? dataForWeixin.title() : dataForWeixin.title
				  }, function(res){});
			   });
			   WeixinJSBridge.on('menu:share:weibo', function(argv){
				  WeixinJSBridge.invoke('shareWeibo',{
					 "content":(typeof(dataForWeixin.title) == "function") ? dataForWeixin.title() : dataForWeixin.title,
					 "url":(typeof(dataForWeixin.url) == "function") ? dataForWeixin.url() : dataForWeixin.url,
				  }, function(res){(dataForWeixin.callback)();});
			   });
			   WeixinJSBridge.on('menu:share:facebook', function(argv){
				  (dataForWeixin.callback)();
				  WeixinJSBridge.invoke('shareFB',{
					 "img_url":(typeof(dataForWeixin.MsgImg) == "function") ? dataForWeixin.MsgImg() : dataForWeixin.MsgImg,
					 "img_width":"120",
					 "img_height":"120",
					 "link":(typeof(dataForWeixin.url) == "function") ? dataForWeixin.url() : dataForWeixin.url,
					 "desc":(typeof(dataForWeixin.desc) == "function") ? dataForWeixin.desc() : dataForWeixin.desc,
					 "title":(typeof(dataForWeixin.title) == "function") ? dataForWeixin.title() : dataForWeixin.title
				  }, function(res){});
			   });
			};
			if(document.addEventListener){
			   document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
			}else if(document.attachEvent){
			   document.attachEvent('WeixinJSBridgeReady'   , onBridgeReady);
			   document.attachEvent('onWeixinJSBridgeReady' , onBridgeReady);
			}
	};
	
	window.support={
		share: share
	};
	
})();//pageready