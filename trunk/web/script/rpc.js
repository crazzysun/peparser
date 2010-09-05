$import("dwr/interface/RPC.js");

dwr.engine.setErrorHandler
(
	function (message, ex)
	{
		alert(message);
		throw ex;
	}
);


function Operation($name$)
{
	this.$name$ = $name$;
}

Operation.prototype.execute = function (callback)
{

	var aaa = this.$name$;
	delete this.$name$;
	
	// ������Ļص�������������
	var callback1 = null;
	if (callback)
	{
		if (typeof callback == "function")
		{
			callback1 = callback;
		}
		else
		{
			callback1 = callback.callback;
		}
	}
	
	// �����µĻص�����
	var callback2 = function (result)
	{
		var message = result.message;
		if (message)
		{
			alert(message);
//			var currentHref = window.top.location.href;
//			var reg=new RegExp("/tcc/.*$","g");
//			currentHref = currentHref.replace(reg, "/tcc/index.html");
//			window.top.location.href  = currentHref;
			return;
		}
	
		// ����ԭ���Ļص�����
		if (callback1) callback1(result.data);
	}

	// �����µĻص�����
	var option = null;
	if (callback)
	{
		if (typeof callback == "function")
		{
			option = callback2;
		}
		else
		{
			option = callback;
			option.callback = callback2;
		}
	}
	
	RPC.call(aaa, {name: aaa, data: this}, option);
	
	this.$name$ = aaa;
}
