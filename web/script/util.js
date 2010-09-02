/**
 * һЩ��������
 */
$import("rpc.js");

var util = {};

/** ����������ϸ��Ϣ, ���ڵ��� */
util.debug = function (object, depth)
{
	var s = dwr.util.toDescriptiveString(object, depth);
	alert(s);
}

/** ���ָ���ڵ������ */
util.clear = function (element)
{
	for (var i = element.childNodes.length - 1; i >= 0; i--)
	{
		var n = element.childNodes[i];
		element.removeChild(n);
	}
}

util.isIE = function()
{
	return (navigator.userAgent.indexOf('MSIE') >= 0);
}

util.trim = function (s)
{
	if (s == null) return null;
	
	var r = s.replace(/^[ \t\n\xA0]+/, "").replace(/[ \t\n\xA0]+$/, "");
	
	return r;
}

/**
* �÷��� 
* var args = util.getURLArgs(); // �� URL ���������� 
* var q = args.q || ""; // ���������ĳ��������ʹ����ֵ���������һ��Ĭ��ֵ 
* var n = args.n ? parseInt(args.n) : 10; 
*/
util.getURLArgs = function ()
{
	var args = new Object(); //����һ���ն��� 
	var query = window.location.search.substring(1); // ȡ��ѯ�ַ�������� http://www.snowpeak.org/testjs.htm?a1=v1&a2=&a3=v3#anchor �нس� a1=v1&a2=&a3=v3�� 
	var pairs = query.split("&"); // �� & ���ֿ������� 
	for(var i = 0; i < pairs.length; i++) 
	{ 
		var pos = pairs[i].indexOf('='); // ���� "name=value" �� 
		if (pos == -1) continue; // �����ɶԣ�������ѭ��������һ�� 
		var argname = pairs[i].substring(0,pos); // ȡ������ 
		var value = pairs[i].substring(pos+1); // ȡ����ֵ 
		value = decodeURI(value); // ����Ҫ������룬��ӦencodeURI�ı��룬����ͨ��URL����ʱӦ��ͨ��encodeURI("����")����
		args[argname] = value; // ��ɶ����һ������ 
	}
	return args; // ���ش˶��� 
}

/**
 * ��ָ����������ؼ��м���ָ�����͵������ֵ�
 * @param select ������ؼ�
 * @param typeName �����ֵ�����
 * @param operation �����ֵ������ɺ�Ĳ���
 */
util.loadDictionary = function(select, typeName, operation)
{
	var callback = function (o)
	{
		dwr.util.removeAllOptions(select);
		dwr.util.addOptions(select, [{id:-1, name:"��ѡ��..."}], "id", "name");
		dwr.util.addOptions(select, o.items, "id", "name");
		if(operation != null)
			operation;
	};	
	var operation = new Operation("ϵͳ.�����ֵ�.�����Ͳ��ֵ�");
	operation.typeName = typeName;
	operation.execute(callback);
};

/**
 * ����������
 * @param select ������ؼ�
 * @param items ����
 * @param firstOptionText ������ؼ��ĵ�һ��option��text
 */
util.loadSelectOption = function(select, items, firstOptionText)
{
	dwr.util.removeAllOptions(select);
	if (null != firstOptionText && "" != firstOptionText)
		dwr.util.addOptions(select, [{id:-1, name: firstOptionText}], "id", "name");
	dwr.util.addOptions(select, items, "id", "name");
};

/**
 * ͨ��operation����������
 * @param select ������ؼ�
 * @param operation ��������
 * @param firstOptionText ������ؼ��ĵ�һ��option��text
 * @param loadFinishFunction ������ɺ���Ҫִ�еĺ���
 */
util.loadSelect = function(select, operation, firstOptionText, loadFinishFunction)
{
	var callback = function (result)
	{
		var items = result.data; 
		util.loadSelectOption(select, items, firstOptionText);
		
		if (typeof(loadFinishFunction) == "function") loadFinishFunction();
	};	

	operation.execute(callback);
}

/**
 * ��ָ����������ؼ��м���ָ���������Ƶ������ֵ�
 * @param select ������ؼ�
 * @param typeName �����ֵ�����
 * @param firstOptionText ������ؼ��ĵ�һ��option��text
 * @param loadFinishFunction ������ɺ���Ҫִ�еĺ���
 */
util.loadDictionaryByTypeName = function(select, typeName, firstOptionText, loadFinishFunction)
{
	var operation = new Operation("ϵͳ.�����ֵ�.����������������ֵ�");
	operation.typeName = typeName;
	
	util.loadSelect(select, operation, firstOptionText, loadFinishFunction);
};

function delegate(that, method)
{
	return function () { return method.apply(that, arguments); };
}
