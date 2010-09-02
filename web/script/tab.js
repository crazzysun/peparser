

/**
 * 选项卡效果，非iframe方式
 * @param totle 选项卡总数
 * @param now	选中序号
 * @return
 */
function tab_show(totle,now){
	for(i=0;i<=totle;i++)
	{
		$('tab_bar_li_'+i).removeClass();
		$('tab_content_'+i).css('display','none');
	}
	$('tab_bar_li_'+now).addClass('on');
	$('tab_content_'+now).css('display','block');
}















function $_tab()
{
  var elements = new Array();

  for (var i = 0; i < arguments.length; i++)
  {
    var element = arguments[i];
    if (typeof element == 'string')
      element = document.getElementById(element);

    if (arguments.length == 1)
      return element;
      
    elements.push(element);
  }
  return elements;
}

function get_dom_name()
{
    return document.getElementsByName(arguments[0]);
}


var oldval;
//事件触发
function TabChange(val)
{
	var tabFirstLeft=$_tab("tabFirstLeft");
	var tabListLeft=get_dom_name("tabListLeft");
	var tabListRight=get_dom_name("tabListRight");	
	var tabLength=tabListLeft.length-1;
	RestoreTab();
	switch(val)
	{
		case 0:
			tabFirstLeft.className="tabLeftFirstOn";
			tabListLeft[val].className="tabStripeOn";
			tabListRight[val].className="tabMiddleOnOff";
			break;
		case tabLength:
			tabListRight[val-1].className="tabMiddleOffOn"
			tabListLeft[val].className="tabStripeOn";
			tabListRight[val].className="tabRightLastOn";
			break;
		default:
			tabListRight[val-1].className="tabMiddleOffOn"
			tabListLeft[val].className="tabStripeOn";
			tabListRight[val].className="tabMiddleOnOff";
			break;
	}
	
	if(oldval!=val)
	{
	//	GoUrl(val);//回调事件,写在调用TAB的页面中
	}
	oldval=val;
	
}

//选项卡初始化
function RestoreTab()
{
	var tabFirstLeft=$_tab("tabFirstLeft");
	var tabListLeft=get_dom_name("tabListLeft");
	var tabListRight=get_dom_name("tabListRight");	
	var tabLength=tabListLeft.length-1;
	
	for(var i=0;i<=tabLength;i++)
	{
		tabListLeft[i].className="tabStripeOff";
		tabListRight[i].className="tabMiddleOffOff";
	}
	tabFirstLeft.className="tabLeftFirstOff";
	tabListRight[tabLength].className="tabRightLastOff"	
}

function TabNavigetUrl(Url)
{
    window.top.location.href = Url;
}
