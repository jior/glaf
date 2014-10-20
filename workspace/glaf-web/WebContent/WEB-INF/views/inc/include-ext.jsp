<%@ page contentType="text/javascript;charset=UTF-8" language="java"%>
(function() {
    function getQueryParam(name) {
        var regex = RegExp('[?&]' + name + '=([^&]*)');

        var match = regex.exec(location.search) || regex.exec(path);
        return match && decodeURIComponent(match[1]);
    }

    function hasOption(opt, queryString) {
        var s = queryString || location.search;
        var re = new RegExp('(?:^|[&?])' + opt + '(?:[=]([^&]*))?(?:$|[&])', 'i');
        var m = re.exec(s);

        return m ? (m[1] === undefined || m[1] === '' ? true : m[1]) : false;
    }

    function getCookieValue(name){
        var cookies = document.cookie.split('; '),
            i = cookies.length,
            cookie, value;

        while(i--) {
           cookie = cookies[i].split('=');
           if (cookie[0] === name) {
               value = cookie[1];
           }
        }

        return value;
    }

    var scriptEls = document.getElementsByTagName('script'),
        path = scriptEls[scriptEls.length - 1].src,
        rtl = getQueryParam('rtl'),
        theme = getQueryParam('theme') || 'crisp',
        includeCSS = !hasOption('nocss', path),
        hasOverrides = !hasOption('nooverrides', path) && !!{
            // TODO: remove neptune
            neptune: 1,
            classic: 1,
            gray: 1,
            'neptune-touch': 1,
            crisp: 1,
            'crisp-touch': 1
        }[theme],
        repoDevMode = getCookieValue('ExtRepoDevMode'),
        packagePath,
        themePath,
        i = 3,
        overridePath;

    rtl = rtl && rtl.toString() === 'true';

    while (i--) {
        path = path.substring(0, path.lastIndexOf('/'));
    }
    // path == root of ext  
    theme = 'ext-theme-' + theme;
    packagePath ='<%=request.getContextPath()%>/static/scripts/extjs/themes/' + theme + '/build/';
	//alert("path:"+path);
	//alert("packagePath:"+packagePath);
    themePath = packagePath + 'resources/' + theme + (rtl ? '-all-rtl' : '-all');
    //alert("themePath:"+themePath);
    if (includeCSS) {
        document.write('<link rel="stylesheet" type="text/css" href="' +
                            themePath + '.css"/>');
    }

    document.write('<script type="text/javascript" src="<%=request.getContextPath()%>/static/scripts/extjs/ext-all' +
                            (rtl ? '-rtl' : '') + '.js"></script>');

    if (hasOverrides) {
        // since document.write('<script>') does not block execution in IE, we need to 
        // makes sure we prevent ext-theme-neptune.js from executing before ext-all.js
        // normally this can be done using the defer attribute on the script tag, however
        // this method does not work in IE when in repoDevMode.  It seems the reason for
        // this is because in repoDevMode ext-all.js is simply a script that loads other
        // scripts and so Ext is still undefined when the neptune overrides are executed.
        // To work around this we use the _beforereadyhandler hook to load the neptune
        // overrides dynamically after Ext has been defined.
        overridePath = packagePath + theme + (repoDevMode ? '' : '') + '.js';

        if (repoDevMode &&  window.ActiveXObject) {
            Ext = {
                _beforereadyhandler: function() {
                    Ext.Loader.loadScript({ url: overridePath });
                }
            };
        } else {
            document.write('<script type="text/javascript" src="' +
                            overridePath + '" defer></script>');
        }
    }

})();
