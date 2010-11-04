$import("rpc.js");

function TableList(operation, titles, columns, limit)
{
	this.reset();
	
	this.operation = operation;				// 操作对象
	this.titles = titles;					// 标题栏
	this.columns = columns;					// 每列的单元内容函数
	
	this.order = null;						// 排序字段
	this.desc = false;						// 逆序
	this.orderSpan = null;
	
	this.tbody = null;						// 用于显示数据的表格中的tbody标签
	this.onchange = null;					// 当页面发生变化后执行
	
	this.limit = limit == null ? 10 : limit;
}

TableList.prototype.reset = function ()
{
	this.total = 0;
	this.offset = 0;
}

TableList.prototype.sort = function (title, span)
{
	if (this.order == null || this.order != title)
	{
		if (this.order) this.orderSpan.innerHTML = this.order.text;
		this.desc = false;
	}
	else
	{
		this.desc = !this.desc;
	}
	
	this.order = title;
	this.orderSpan = span;
	
	if (this.desc)
		this.orderSpan.innerHTML = this.order.text + "↓";
	else
		this.orderSpan.innerHTML = this.order.text + "↑";
	
	this.refresh();
}

TableList.prototype.firstPage = function ()
{
	this.offset = 0;
	
	this.refresh();
}

TableList.prototype.gotoPage = function ()
{
	var page = this.pageInput.value;
	
	if (null == page || isNaN(page) || page < 1)
	{
		alert("请输入正确的页码！");
		this.refresh();
		return;
	}
	
	/** 判断输入的页数是否超过最大页数值 */
	if (this.total % this.limit == 0 && this.total > 0)
	{
		if (page > this.total / this.limit)
		{
			alert("请输入正确的页码！");
			this.refresh();
			return;
		}
	}
	else
	{
		if (page > this.total / this.limit + 1)
		{
			alert("请输入正确的页码！");
			this.refresh();
			return;
		}
	}

	this.offset = (page - 1) * this.limit;
	
	this.refresh();
}

TableList.prototype.lastPage = function ()
{
	this.offset = Math.floor(this.total / this.limit) * this.limit;
	if (this.total % this.limit == 0 && this.total > 0 ) this.offset -= this.limit;
	
	this.refresh();
}

TableList.prototype.pageUp = function ()
{
	this.offset -= this.limit;
	if (this.offset < 0) this.offset = 0;
	
	this.refresh();
}

TableList.prototype.pageDown = function ()
{
	this.offset += this.limit;
	if (this.offset >= this.total) this.offset -= this.limit;
	
	this.refresh();
}

TableList.prototype.refresh = function ()
{
	var self = this;
	var callback = function(result)
	{
		self.showData(result);
		
		if (self.onchange) self.onchange(result);
	}

	this.operation.offset = this.offset;	
	this.operation.limit = this.limit;
	if (this.order)
	{
		this.operation.order = this.order.order;
		this.operation.desc = this.desc;
	}
	
	this.showLoading();
		
	this.operation.execute(callback);
}

TableList.prototype.rowCreator = function (options)
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

TableList.prototype.cellCreator = function (options)
{
	var td = document.createElement("td");
	return td;
};

TableList.prototype.showLoading = function()
{
	dwr.util.removeAllRows(this.tbody);
	var loadingColumns = [ function(data){return "加载中...";} ];
	
	dwr.util.addRows(this.tbody, [{}], loadingColumns, {rowCreator: this.rowCreator});
	
	this.pageInput.value = "";
	this.totalSpan.innerHTML = "";
	this.recordsSpan.innerHTML = "";
}

TableList.prototype.showData = function (data)
{
	var self = this;

	dwr.util.removeAllRows(this.tbody);
	
	this.offset = data.offset;
	this.total = data.total;
	dwr.util.addRows(this.tbody, data.data, this.columns, {rowCreator: this.rowCreator});
	
	this.pageInput.value = "" + Math.floor(data.offset / data.limit + 1);
	this.totalSpan.innerHTML = "" + Math.floor((data.total - 1) / data.limit + 1);
	this.recordsSpan.innerHTML = "共" + this.total + "条记录";
}

TableList.prototype.createControlLine = function (div)
{

	div.innerHTML = 
		"<span class='action'><a href='#'>首页</a></span>" +
		"<span class='action'><a href='#'>上一页</a></span>" +
		"<span class='action'><a href='#'>下一页</a></span>" +
		"<span class='action'><a href='#'>尾页</a></span>" +
		"<span>页数:<span><input type='text' style='text-align:center'/></span> /<span>总页</span></span>" +
		"<span class='action'><a href='#'>转到</a></span>" +
		"<span class='action'><a href='#'>刷新</a></span>" +
		"<span>共 条记录</span>";
	div.className = "pagecontrol";
	
	var self = this;

	var span = div.firstChild;
	span.onclick = function () { self.firstPage(); }
	
	span = span.nextSibling;
	span.onclick = function () { self.pageUp(); }

	span = span.nextSibling;
	span.onclick = function () { self.pageDown(); }

	span = span.nextSibling;
	span.onclick = function () { self.lastPage(); }
	
	span = span.nextSibling;
	this.pageInput = span.firstChild.nextSibling.firstChild;
	this.totalSpan = span.firstChild.nextSibling.nextSibling.nextSibling;
	
	span = span.nextSibling;
	span.onclick = function () { self.gotoPage(); }

	span = span.nextSibling;
	span.onclick = function () { self.refresh(); }
	
	var span = span.nextSibling;
	this.recordsSpan = span;

	return;
}

TableList.prototype.create = function (container)
{
	container.innerHTML = 
		"" +		// 控制栏
		"<div>" + 
		"<table class='table_list'>" +
		"<thead><tr></tr></thead>" +					// 标题 
		"<tbody></tbody>" + 							// 数据
		"</table>" + 
		"</div><div class='table_list_control'></div>";
		
	var tableDiv = container.firstChild;				// 控制栏
	//alert(controlDiv.tagName);
	this.createControlLine(tableDiv.nextSibling);
	
	
	var table = tableDiv.firstChild;		// 表格部分
	var thead = table.firstChild;
	var tr = thead.firstChild;							// 标题
	
	var self = this;

	for (var i = 0 ; i < this.titles.length; i++)
	{
		var title = this.titles[i];

		var th = document.createElement("th");
		tr.appendChild(th);
		
		th.width = title.width;
//		alert(th.style.textAlign);
		if(title.align){
			th.style.textAlign = title.align;
		}
		if (title.order)
		{
			var span = document.createElement("span");
			th.appendChild(span);
			if (title.node)
				span.appendChild(title.node);
			else
				span.innerHTML = title.text;
			span.t = title;
			span.style.cursor = "pointer";
			span.onclick = function () { self.sort(this.t, this); }
		}
		else
		{
			if (title.node)
				th.appendChild(title.node);
			else
				th.innerHTML = title.text;
		}
	}

	var tbody = thead.nextSibling;						// 数据显示部分
	this.tbody = tbody;

	return;
}


/** 将文本显示为链接 */
TableList.link = function(text, action, object)
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
TableList.links = function(operations, object)
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
