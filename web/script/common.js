var common = {};
common.scriptBase = "";
common.scripts = {};

//******************************************************
// �����ļ� 
// �÷�: $import("dwr/interface/RPC.js");
// ע��: ������js�ļ���·����Ҫ�������common.js�����·��ָʾ
//******************************************************
function $import(path)
{
	// ȷ��ÿ���ļ�ֻ������һ��
	var s = common.scripts[path];
	if (s == "yes") return;

	common.scripts[path] = "yes";
	
	// �����ű�����
	// ������������ʱ�����⣬���ܲ�����������
//	var script = document.createElement("script");
//	script.type = "text/javascript";
//	script.language = "JavaScript";
//	script.src = common.scriptBase + path;
//	document.getElementsByTagName("head")[0].appendChild(script);		

	// ����������û�з�������
	document.write("<script src='" + common.scriptBase + path + "'></sc" + "ript>");
}

//===================================
// ���ýű�����·��
common.setupScriptBase = function ()
{
	var src = "common.js";
	var scripts = document.getElementsByTagName("script");

	for (var i = 0; i < scripts.length; i++) 
	{
		if (scripts[i].src.match(src)) 
		{
			common.scriptBase = scripts[i].src.replace(src, "");
			break;
		}
	}
}

common.setupScriptBase();

function testCommon()
{
	alert("testCommon");
}

function fucCheckLength(strTemp) {
	var i,sum;
	sum=0;
	for(i=0;i<strTemp.length;i++) {
		if ((strTemp.charCodeAt(i)>=0) && (strTemp.charCodeAt(i)<=255)) {
			sum=sum+1;
		} else {
			sum=sum+2;
		}
	}
	return sum;
}
//==============================================================

$import("dwr/engine.js");
$import("dwr/util.js");
$import("util.js");
$import("fix_dwr.js");