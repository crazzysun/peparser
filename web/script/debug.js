$import("dwr/util.js");

if (!debug) var debug = {};

/** �������ϸ��Ϣ */
debug.detail = function (object, depth)
{
	return dwr.util.toDescriptiveString(object, depth);
}

/** �öԻ�����ʾ�������ϸ��Ϣ, ���ڵ��� */
debug.inspect = function (object, depth)
{
	var s = dwr.util.toDescriptiveString(object, depth);
	alert(s);
}

/** ��DEBUG�������������Ϣ */
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
