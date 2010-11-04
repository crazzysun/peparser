$import("rpc.js");

function AppendTableList(operation, titles, columns, total)
{
	this.operation = operation;				// 操作对象
	this.titles = titles;					// 标题栏
	this.columns = columns;					// 每列的单元内容函数
	this.tbody = null;						// 用于显示数据的表格中的tbody标签
	
	this.total = total;						//创建时已知道共有多少条记录
	this.current = 0;						//当前分析到的条数
}

/**
 * 循环嵌套调用此函数，每次从队列中取出一个filePath执行rpc
 * 只有上一条结果返回后才调用下一条rpc
 * @param {Object} fileList
 */
AppendTableList.prototype.append = function (fileList)
{
	this.current++;								//循环取队列头
	if (this.current > this.total) return;	
	if (!(this.file = fileList.shift())) alert("TableList Gets File Error!");
	
	var self = this;
	this.operation.filePath = this.file;		//给operation传参
	$("statusBar").innerHTML = this.file;		//页面中的状态进度条
	var callback = function(result)
	{
		dwr.util.addRows(self.tbody, result.data, self.columns, {rowCreator: self.rowCreator});
		
		self.recordsSpan.innerHTML = "共" + self.total + "条记录，已分析" + self.current + "条";
		
		if (self.current == self.total)			//全部分析完成后
		{
			$("progress").style.display = "none";
			$("status").style.display = "none";
			$("finish").style.display = "";
		}
		self.append(fileList);
	}
	
	this.operation.execute(callback);
}

AppendTableList.prototype.rowCreator = function (options)
{
	var tr = document.createElement("tr");
	if (options.rowNum % 2 == 0)
	{
		tr.className = "even";
	}
	else
	{
		tr.className = "odd";
	}
	return tr;
}

AppendTableList.prototype.create = function (container)
{
	container.innerHTML = 
		"" +		// 控制栏
		"<div>" + 
		"<table class='table_list'>" +
		"<thead><tr></tr></thead>" +					// 标题 
		"<tbody></tbody>" + 							// 数据
		"</table>" + 
		"</div><div class='table_list_control'></div>";
		
	var tableDiv = container.firstChild;	
	
	var controlDiv = tableDiv.nextSibling;
	controlDiv.innerHTML = "<span>共" + this.total + "条记录，已分析" + this.current + "条</span>";
	controlDiv.className = "pagecontrol";
	this.recordsSpan = controlDiv.firstChild;			// 控制栏
	
	
	var table = tableDiv.firstChild;					// 表格部分
	var thead = table.firstChild;
	var tr = thead.firstChild;							// 标题
	
	for (var i = 0 ; i < this.titles.length; i++)
	{
		var title = this.titles[i];

		var th = document.createElement("th");
		
		th.width = title.width;
		th.innerHTML = title.text;
		tr.appendChild(th);
	}

	var tbody = thead.nextSibling;						// 数据显示部分
	this.tbody = tbody;

	return;
}


/** 将文本显示为链接 */
AppendTableList.link = function(text, action, object)
{
	var span = document.createElement("span");
	span.style.textDecoration = 'underline';
	span.style.cursor = 'pointer';
	span.onmouseover = function(){this.style.color="#ff0000";}
	span.onmouseout = function(){this.style.color="#000000";}
	span.appendChild(document.createTextNode(text));
	span.object = object;
	span.onclick = function () { action(this.object); }
	
	return span;
}

/** 帮助显示操作列 */
AppendTableList.links = function(operations, object)
{
	var sspan = document.createElement("span");

	for (var i = 0; i < operations.length; i++)
	{
		var o = operations[i];

		if (o.enable && !o.enable(object)) continue;

		var span = document.createElement("span");
		sspan.appendChild(span);
		span.className = "action";
		
		var text = document.createElement("span");
		text.style.margin = "5px";
		span.appendChild(text);
		if(o.icon == ''){
			text.appendChild(document.createTextNode("[" + o.text + "]"));
		}else{
			text.innerHTML = '<img src="' + o.icon+ '" title="'+ o.text +'"/>';
		}
		
		text.onmouseover = function () { this.style.color = "#FF0000"; }
		text.onmouseout = function () { this.style.color = "#000000"; }
		text.onmousemove = function () { }

		span.object = object;
		span.action = o.onclick;
		span.onclick = function () { this.action(this.object); };
	}
	
	return sspan;
}
