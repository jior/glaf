function Close()
{
	var ua = navigator.userAgent;
	var ie = navigator.appName == "Microsoft Internet Explorer" ? true:false;
	if(ie)
	{
		var IEversion = parseFloat(ua.substring(ua.indexOf("MSIE ")+5,ua.indexOf(";",ua.indexOf("MSIE "))));
	 	if(IEversion < 5.5)
		{
			var str  = '<object id=noTipClose classid="clsid:ADB880A6-D8FF-11CF-9377-00AA003B7A11">';
			str += '<param name="Command" value="Close"></object>';
			document.body.insertAdjacentHTML("beforeEnd", str);
			document.all.noTipClose.Click();
		}
		else
		{
			window.opener = null;
			window.close();
			//window.parent.close();
		}
	}
	else
	{
		window.close();
	}
}


