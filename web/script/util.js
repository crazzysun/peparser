/**
 * 一些基本工具
 */
$import("rpc.js");

var util = {};

/** 输出对象的详细信息, 用于调试 */
util.debug = function (object, depth)
{
	var s = dwr.util.toDescriptiveString(object, depth);
	alert(s);
}

/** 清除指定节点的内容 */
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
* 用法： 
* var args = util.getURLArgs(); // 从 URL 解析出参数 
* var q = args.q || ""; // 如果定义了某参数，则使用其值，否则给它一个默认值 
* var n = args.n ? parseInt(args.n) : 10; 
*/
util.getURLArgs = function ()
{
	var args = new Object(); //声明一个空对象 
	var query = window.location.search.substring(1); // 取查询字符串，如从 http://www.snowpeak.org/testjs.htm?a1=v1&a2=&a3=v3#anchor 中截出 a1=v1&a2=&a3=v3。 
	var pairs = query.split("&"); // 以 & 符分开成数组 
	for(var i = 0; i < pairs.length; i++) 
	{ 
		var pos = pairs[i].indexOf('='); // 查找 "name=value" 对 
		if (pos == -1) continue; // 若不成对，则跳出循环继续下一对 
		var argname = pairs[i].substring(0,pos); // 取参数名 
		var value = pairs[i].substring(pos+1); // 取参数值 
		value = decodeURI(value); // 若需要，则解码，对应encodeURI的编码，中文通过URL传递时应先通过encodeURI("中文")编码
		args[argname] = value; // 存成对象的一个属性 
	}
	return args; // 返回此对象 
}

/**
 * 向指定的下拉框控件中加载指定类型的数据字典
 * @param select 下拉框控件
 * @param typeName 数据字典类型
 * @param operation 数据字典加载完成后的操作
 */
util.loadDictionary = function(select, typeName, operation)
{
	var callback = function (o)
	{
		dwr.util.removeAllOptions(select);
		dwr.util.addOptions(select, [{id:-1, name:"请选择..."}], "id", "name");
		dwr.util.addOptions(select, o.items, "id", "name");
		if(operation != null)
			operation;
	};	
	var operation = new Operation("系统.数据字典.按类型查字典");
	operation.typeName = typeName;
	operation.execute(callback);
};

/**
 * 加载下拉框
 * @param select 下拉框控件
 * @param items 数据
 * @param firstOptionText 下拉框控件的第一个option的text
 */
util.loadSelectOption = function(select, items, firstOptionText)
{
	dwr.util.removeAllOptions(select);
	if (null != firstOptionText && "" != firstOptionText)
		dwr.util.addOptions(select, [{id:-1, name: firstOptionText}], "id", "name");
	dwr.util.addOptions(select, items, "id", "name");
};

/**
 * 通过operation加载下拉框
 * @param select 下拉框控件
 * @param operation 操作名称
 * @param firstOptionText 下拉框控件的第一个option的text
 * @param loadFinishFunction 加载完成后需要执行的函数
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
 * 向指定的下拉框控件中加载指定类型名称的数据字典
 * @param select 下拉框控件
 * @param typeName 数据字典类型
 * @param firstOptionText 下拉框控件的第一个option的text
 * @param loadFinishFunction 加载完成后需要执行的函数
 */
util.loadDictionaryByTypeName = function(select, typeName, firstOptionText, loadFinishFunction)
{
	var operation = new Operation("系统.数据字典.按类别名称列数据字典");
	operation.typeName = typeName;
	
	util.loadSelect(select, operation, firstOptionText, loadFinishFunction);
};

function delegate(that, method)
{
	return function () { return method.apply(that, arguments); };
}
