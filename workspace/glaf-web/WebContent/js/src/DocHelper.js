/**
 * @author suey
 */
DocHelper = {
	XML_NAMESPACE : "<?xml version=\"1.0\"?>"
			   + "<xml xmlns:s=\"uuid:BDC6E3F0-6DA3-11d1-A2A3-00AA00C14882\""
			   + " xmlns:dt=\"uuid:C2F41010-65B3-11d1-A29F-00AA00C14882\""
			   + " xmlns:rs=\"urn:schemas-microsoft-com:rowset\""
			   + " xmlns:z=\"#RowsetSchema\"/>",
	newDoc : function()
	{
		var doc;
		if(window.ActiveXObject) {
			doc = new ActiveXObject("MSXML2.DOMDocument");
		} else if(document.implementation && document.implementation.createDocument){
			doc = document.implementation.createDocument("","",null);
		} else {
			alert("你的浏览器不支持运行Script!");
			return null;
		}
		doc.async = "false";
		return doc;
	},
	
	newXML : function()
	{
		var xRs = DocHelper.newDoc();
		xRs.loadXML(DocHelper.XML_NAMESPACE);
		
		return xRs;
	},
	
	newXMLBy : function(name)
	{
		if(!name) name = "rs:data";
		var xRs = DocHelper.newXML();
		var xmlns = "";
		if(name == "rs:data"){
			xmlns = "urn:schemas-microsoft-com:rowset";
		} else if(name == "z:row"){
			xmlns= "#RowsetSchema";
		}
		
		xRs.documentElement.appendChild(xRs.createNode(1,name,xmlns));
		
		return rs;
	}
};
