$import("rpc.js");

function AppendTableList(operation, titles, columns, total)
{
	this.operation = operation;				// ��������
	this.titles = titles;					// ������
	this.columns = columns;					// ÿ�еĵ�Ԫ���ݺ���
	this.tbody = null;						// ������ʾ���ݵı���е�tbody��ǩ
	
	this.total = total;						//����ʱ��֪�����ж�������¼
	this.current = 0;						//��ǰ������������
}

/**
 * ѭ��Ƕ�׵��ô˺�����ÿ�δӶ�����ȡ��һ��filePathִ��rpc
 * ֻ����һ��������غ�ŵ�����һ��rpc
 * @param {Object} fileList
 */
AppendTableList.prototype.append = function (fileList)
{
	this.current++;								//ѭ��ȡ����ͷ
	if (this.current > this.total) return;	
	if (!(this.file = fileList.shift())) alert("TableList Gets File Error!");
	
	var self = this;
	this.operation.filePath = this.file;		//��operation����
	$("statusBar").innerHTML = this.file;		//ҳ���е�״̬������
	var callback = function(result)
	{
		dwr.util.addRows(self.tbody, result.data, self.columns, {rowCreator: self.rowCreator});
		
		self.recordsSpan.innerHTML = "��" + self.total + "����¼���ѷ���" + self.current + "��";
		
		if (self.current == self.total)			//ȫ��������ɺ�
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
		"" +		// ������
		"<div>" + 
		"<table class='table_list'>" +
		"<thead><tr></tr></thead>" +					// ���� 
		"<tbody></tbody>" + 							// ����
		"</table>" + 
		"</div><div class='table_list_control'></div>";
		
	var tableDiv = container.firstChild;	
	
	var controlDiv = tableDiv.nextSibling;
	controlDiv.innerHTML = "<span>��" + this.total + "����¼���ѷ���" + this.current + "��</span>";
	controlDiv.className = "pagecontrol";
	this.recordsSpan = controlDiv.firstChild;			// ������
	
	
	var table = tableDiv.firstChild;					// ��񲿷�
	var thead = table.firstChild;
	var tr = thead.firstChild;							// ����
	
	for (var i = 0 ; i < this.titles.length; i++)
	{
		var title = this.titles[i];

		var th = document.createElement("th");
		
		th.width = title.width;
		th.innerHTML = title.text;
		tr.appendChild(th);
	}

	var tbody = thead.nextSibling;						// ������ʾ����
	this.tbody = tbody;

	return;
}


/** ���ı���ʾΪ���� */
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

/** ������ʾ������ */
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
