
$.extend($.fn.tabs.methods, {
    /**
     * tabs组件每个tab panel对应的小工具条绑定的事件没有传递事件参数
     * 本函数修正这个问题
     * @param {[type]} jq [description]
     */
    addEventParam: function(jq) {
        return jq.each(function() {
            var that = this;
            var headers = $(this).find('>div.tabs-header>div.tabs-wrap>ul.tabs>li');
            headers.each(function(i) {
                var tools = $(that).tabs('getTab', i).panel('options').tools;
                if (typeof tools != "string") {
                    $(this).find('>span.tabs-p-tool a').each(function(j) {
                        $(this).unbind('click').bind("click", {
                            handler: tools[j].handler
                        }, function(e) {
                            if ($(this).parents("li").hasClass("tabs-disabled")) {
                                return;
                            }
                            e.data.handler.call(this, e);
                        });
                    });
                }
            })
        });
    },
    /**
     * 增加iframe模式的标签页
     * @param {[type]} jq     [description]
     * @param {[type]} params [description]
     */
    addIframeTab:function(jq,params){
        return jq.each(function(){
            if(params.tab.href){
                delete params.tab.href;
            }
            $(this).tabs('add',params.tab);
            var opts = $(this).tabs('options');
            var $tabBody = $(this).tabs('getTab',params.tab.title).panel('body');
            $tabBody.css({'overflow':'hidden','position':'relative'});
            var $mask = $('<div style="position:absolute;z-index:2;width:100%;height:100%;background:#ccc;z-index:1000;opacity:0.3;filter:alpha(opacity=30);"><div>').appendTo($tabBody);
            var $maskMessage = $('<div class="mask-message" style="z-index:3;width:184px;height:16px;line-height:16px;position:absolute;top:50%;left:50%;margin-top:-20px;margin-left:-92px;border:2px solid #d4d4d4;padding: 12px 5px 10px 30px;background: #ffffff url(\'../../themes/default/images/loading.gif\') no-repeat scroll 5px center;">Processing, please wait ...</div>').appendTo($tabBody);
            var $containterMask = $('<div style="position:absolute;width:100%;height:100%;z-index:1;background:#fff;"></div>').appendTo($tabBody);
            var $containter = $('<div style="position:absolute;width:100%;height:100%;z-index:0;"></div>').appendTo($tabBody);

            var iframe = document.createElement("iframe");
            iframe.src = params.iframe.src;
            iframe.frameBorder = params.iframe.frameBorder || 0;
            iframe.height = params.iframe.height || '100%';
            iframe.width = params.iframe.width || '100%';
            if (iframe.attachEvent){
                iframe.attachEvent("onload", function(){
                    $([$mask[0],$maskMessage[0]]).fadeOut(params.iframe.delay || 'slow',function(){
                        $(this).remove();
                        if($(this).hasClass('mask-message')){
                            $containterMask.fadeOut(params.iframe.delay || 'slow',function(){
                                $(this).remove();
                            });
                        }
                    });
                });
            } else {
                iframe.onload = function(){
                    $([$mask[0],$maskMessage[0]]).fadeOut(params.iframe.delay || 'slow',function(){
                        $(this).remove();
                        if($(this).hasClass('mask-message')){
                            $containterMask.fadeOut(params.iframe.delay || 'slow',function(){
                                $(this).remove();
                            });
                        }
                    });
                };
            }
            $containter[0].appendChild(iframe);
        });
    }
});