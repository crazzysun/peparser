var common = {};
common.scriptBase = "";
common.scripts = {};

//******************************************************
// 包含文件 
// 用法: $import("dwr/interface/RPC.js");
// 注意: 被包含js文件的路径需要用相对于common.js的相对路径指示
//******************************************************
function $import(path)
{
	// 确保每个文件只被引入一次
	var s = common.scripts[path];
	if (s == "yes") return;

	common.scripts[path] = "yes";
	
	// 包含脚本进来
	// 这种做法由于时序问题，可能不能正常工作
//	var script = document.createElement("script");
//	script.type = "text/javascript";
//	script.language = "JavaScript";
//	script.src = common.scriptBase + path;
//	document.getElementsByTagName("head")[0].appendChild(script);		

	// 这种做法还没有发现问题
	document.write("<script src='" + common.scriptBase + path + "'></sc" + "ript>");
}

//===================================
// 设置脚本基础路径
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