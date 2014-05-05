$(function() {
    tabClose();
    tabCloseEven();

    $('#css3menu a').click(function() {
        $('#css3menu a').removeClass('active');
        $(this).addClass('active');

        var d = _menus[$(this).attr('name')];
        Clearnav();
        addNav(d);
        InitLeftMenu();
    });

    // 导航菜单绑定初始化
    $("#wnav").accordion({
        animate: false
    });

    var firstMenuName = $('#css3menu a:first').attr('name');
    addNav(_menus[firstMenuName]); //首次加载basic 左侧菜单
    InitLeftMenu();
});

function Clearnav() {
    var pp = $('#wnav').accordion('panels');

    $.each(pp, function(i, n) {
        if (n) {
            var t = n.panel('options').title;
            $('#wnav').accordion('remove', t);
        }
    });

    pp = $('#wnav').accordion('getSelected');
    if (pp) {
        var title = pp.panel('options').title;
        $('#wnav').accordion('remove', title);
    }
}

function GetMenuList(data, menulist) {
    if (data.children == null)
        return menulist;
    else {
        menulist += '<ul>';
        $.each(data.children, function(i, sm) {
            if (sm.url != null && sm.url.length  > 0) {
				var link = contextPath+sm.url;
                menulist += '<li><a ref="' + sm.id + '" href="#" rel="'
					+ sm.url + '" onclickxy="javascript:addTab(\''+sm.name+'\',\''+link+'\',\''+sm.icon+'\');" ><span class="icon '+sm.icon+'" >&nbsp;</span><span class="nav">' + sm.name
					+ '</span></a></li>'
            }
            else {
                menulist += '<li state="closed"><span class="nav">' + sm.name + '</span>'
            }
            menulist = GetMenuList(sm, menulist);
        })
        menulist += '</ul>';
    }
	//alert(menulist);
    return menulist;
}
//左侧导航加载
function addNav(data) {

    $.each(data, function(i, sm) {
        var menulist1 = "";
        //sm 常用菜单  
        menulist1 = GetMenuList(sm, menulist1);
        //menulist1 = "<ul id='tt1' class='easyui-tree' animate='true' dnd='false'>" + menulist1.substring(4); 
		//alert(menulist1);
        $('#wnav').accordion('add', {
            title: sm.name,
            content: menulist1,
            iconCls: 'icon ' + sm.icon
        });

    });

    var pp = $('#wnav').accordion('panels');
    var t = pp[0].panel('options').title;
    $('#wnav').accordion('select', t);

}

// 初始化左侧
function InitLeftMenu() {

    $("#wnav").accordion({animate:false,fit:true,border:false});

    hoverMenuItem();

    
    $('#wnav li a').live('click', function() {
        var tabTitle = $(this).children('.nav').text();

        var url = $(this).attr("rel");
        var id = $(this).attr("ref");
        var icon = getIcon(id, icon);

        addTab(tabTitle, url, icon);
        $('#wnav li div').removeClass("selected");
        $(this).parent().addClass("selected");
    });
	

}

/**
* 菜单项鼠标Hover
*/
function hoverMenuItem() {
    $(".easyui-accordion").find('a').hover(function() {
        $(this).parent().addClass("hover");
    }, function() {
        $(this).parent().removeClass("hover");
    });
}

// 获取左侧导航的图标Tab
function getIcon(id) {
    var icon = 'icon ';
    $.each(_menus, function(i, n) {
        $.each(n, function(j, o) {
            $.each(o.children, function(k, m) {
                if (m.id == id) {
                    icon += m.icon;
                    return false;
                }
            });
        });
    });
    return icon;
}

function addTab(subtitle, url, icon) {
    if (!$('#tabs').tabs('exists', subtitle)) {
        $('#tabs').tabs('add', {
            title: subtitle,
            content: createFrame(url),
            closable: true,
            icon: icon
        });
    } else {
        $('#tabs').tabs('select', subtitle);
        $('#mm-tabupdate').click();
    }
    tabClose();
}

function createFrame(url) {
	//alert(url);
	//var link = contextPath+ url;
	if(url.indexOf(contextPath)){
		url = contextPath+ url;
	}
    var s = '<iframe scrolling="auto" frameborder="0"  src="' + url + '" style="width:100%;height:100%;"></iframe>';
    return s;
}

function tabClose() {
    /* 双击关闭TAB选项卡 */
    $(".tabs-inner").dblclick(function() {
        var subtitle = $(this).children(".tabs-closable").text();
        $('#tabs').tabs('close', subtitle);
    });
    /* 为选项卡绑定右键 */
    $(".tabs-inner").bind('contextmenu', function(e) {
        $('#mm').menu('show', {
            left: e.pageX,
            top: e.pageY
        });

        var subtitle = $(this).children(".tabs-closable").text();

        $('#mm').data("currtab", subtitle);
        $('#tabs').tabs('select', subtitle);
        return false;
    });
}
// 绑定右键菜单事件
function tabCloseEven() {
    // 刷新
    $('#mm-tabupdate').click(function() {
        var currTab = $('#tabs').tabs('getSelected');
        var url = $(currTab.panel('options').content).attr('src');
        $('#tabs').tabs('update', {
            tab: currTab,
            options: {
                content: createFrame(url)
            }
        });
    });
    // 关闭当前
    $('#mm-tabclose').click(function() {
        var currtab_title = $('#mm').data("currtab");
        $('#tabs').tabs('close', currtab_title);
    });
    // 全部关闭
    $('#mm-tabcloseall').click(function() {
        $('.tabs-inner span').each(function(i, n) {
            var t = $(n).text();
            $('#tabs').tabs('close', t);
        });
    });
    // 关闭除当前之外的TAB
    $('#mm-tabcloseother').click(function() {
        $('#mm-tabcloseright').click();
        $('#mm-tabcloseleft').click();
    });
    // 关闭当前右侧的TAB
    $('#mm-tabcloseright').click(function() {
        var nextall = $('.tabs-selected').nextAll();
        if (nextall.length == 0) {
            // msgShow('系统提示','后边没有啦~~','error');
            alert('后边没有啦~~');
            return false;
        }
        nextall.each(function(i, n) {
            var t = $('a:eq(0) span', $(n)).text();
            $('#tabs').tabs('close', t);
        });
        return false;
    });
    // 关闭当前左侧的TAB
    $('#mm-tabcloseleft').click(function() {
        var prevall = $('.tabs-selected').prevAll();
        if (prevall.length == 0) {
            alert('到头了，前边没有啦~~');
            return false;
        }
        prevall.each(function(i, n) {
            var t = $('a:eq(0) span', $(n)).text();
            $('#tabs').tabs('close', t);
        });
        return false;
    });

    // 退出
    $("#mm-exit").click(function() {
        $('#mm').menu('hide');
    });
}

// 弹出信息窗口 title:标题 msgString:提示信息 msgType:信息类型 [error,info,question,warning]
function msgShow(title, msgString, msgType) {
    $.messager.alert(title, msgString, msgType);
}
 