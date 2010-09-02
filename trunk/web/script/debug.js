$import("dwr/util.js");

if (!debug) var debug = {};

/** 对象的详细信息 */
debug.detail = function (object, depth)
{
	return dwr.util.toDescriptiveString(object, depth);
}

/** 用对话框显示对象的详细信息, 用于调试 */
debug.inspect = function (object, depth)
{
	var s = dwr.util.toDescriptiveString(object, depth);
	alert(s);
}

/** 在DEBUG窗口输出跟踪信息 */
debug.trace = function (s)
{
	if (debug.stop) return;

	if (!debug.window || !debug.window.document)
	{
		debug.window = window.open("about:blank", "_blank");
		
		debug.window.document.write("<div style='white-space: pre;'>DEBUG</div>");
	}

	var doc = debug.window.document;
	var div = doc.firstChild.firstChild.nextSibling.firstChild;

//	s = dwr.util.escapeHtml(s);
	var text = doc.createElement("pre");
	text.innerHTML = s;

	div.appendChild(text);
}
