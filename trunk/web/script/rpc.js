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
	
	// 将传入的回调函数保存起来
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
	
	// 定义新的回调函数
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
	
		// 调用原来的回调函数
		if (callback1) callback1(result.data);
	}

	// 定义新的回调参数
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
