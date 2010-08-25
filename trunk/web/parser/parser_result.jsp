<%@ page contentType="text/html; charset=utf-8"%>
<%@ page language="java" import="java.util.*, com.entity.parser.*" %>
<jsp:useBean id="fileInfo" class="com.entity.parser.FileInfo" scope="session" />
<jsp:useBean id="headers" class="java.util.Vector" scope="session" />
<%
	Iterator it = headers.iterator();
	/** DOS头 */
	PEHeader dosHeader = (PEHeader)it.next();
	Map<String, String> dosValue = dosHeader.getValue();
	
	/** PE文件标志 */
	PEHeader NTHeader = (PEHeader)it.next();
	Map<String, String> NTValue = NTHeader.getValue();
	
	/** PE文件头 */
	PEHeader fileHeader = (PEHeader)it.next();
	Map<String, String> fileValue = fileHeader.getValue();
	
	/** 可选头部 */
	PEHeader optionalHeader = (PEHeader)it.next();
	Map<String, String> optionalValue = optionalHeader.getValue();
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title><%=fileInfo.getFileName()%>结构分析报告</title>
<link rel="stylesheet" href="../styles/pe_struct.css" type="text/css" />
<script type="text/javascript">

/** 显示当前日期 */
function showNowTime()
{
	var now = new Date();
	var fullYear = now.getFullYear(); 	//年份
	var month = now.getMonth() + 1;	//月份
	var date =  now.getDate();	//号数
	var day = now.getDay();	//星期几，数字形式
	var week = "";	//星期几，中文形式
	switch(day)
	{
		case 0:
			week = "星期日";
			break;
		case 1:
			week = "星期一";
			break;
		case 2:
			week = "星期二";
			break;
		case 3:
			week = "星期三";
			break;
		case 4:
			week = "星期四";
			break;
		case 5:
			week = "星期五";
			break;
		case 6:
			week = "星期六";
			break;
		default:
			break;
	}
	
	var dateString = fullYear + "年" + month + "月" + date + "日" + " " + week;
	document.getElementById("currentTime").innerHTML = dateString;
}
</script>
</head>
<body onload="showNowTime()">
    <div class="t">
        <span class="clr">文件名：</span><%=fileInfo.getFileName() %>
        <br />
        <span class="clr">大 小：</span><%=fileInfo.getFileSize() %>
        <br />
        <span class="clr">创建时间：</span><%=fileInfo.getCreateTime() %>
        <br />
        <span class="clr">修改时间：</span><%=fileInfo.getModifyTime() %>
        <br />
        <span class="clr">报告生成日期：</span><span id="currentTime"></span>
        <br />
        <div style="padding-top: 8px;">
            <span class="Quickguide"><a href="#dos">Dos头</a></span> <span class="Quickguide"><a
                href="#NT">PE头</a></span> <span class="Quickguide"><a href="#NT_FILE">PE文件头</a></span>
            <span class="Quickguide"><a href="#NT_OPTION">可选PE头</a></span> <span class="Quickguide">
                <a href="#NT_DATADIR">数据目录</a></span> <span class="Quickguide"><a href="#NT_SECTION">
                    区段信息</a></span> <span class="Quickguide"><a href="#IMP">导入表</a></span> <span class="Quickguide">
                        <a href="#EXP">导出表</a></span> <span class="Quickguide"><a href="#REL">重定位表</a></span>
            <span class="Quickguide"><a href="#RES">资源表</a></span>
        </div>
    </div>
    <!--=============================== MS-DOS文件头 ===============================-->
    <p class="clr">
        Dos头(IMAGE_DOS_HEADER)<a name="dos"></a></p>      
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="tb">
        <tr class="head">
            <td width="126">
                RVA</td>
            <td>
                Description</td>
            <td width="355">
                Value</td>
        </tr>
        <tr>
            <td>
                0x00000000</td>
            <td>
                <span class="special">e_magic</span><span class="info">(标识是否为一个有效的DOS程序)</span></td>
            <td>
                <span class="special"><%=dosValue.get("e_magic")%></span></td>
        </tr>
        <tr>
            <td>
                0x00000002</td>
            <td>
                e_cblp<span class="info">(文件最后页的字节数)</span></td>
            <td>
                <%=dosValue.get("e_cblp") %></td>
        </tr>
        <tr>
            <td>
                0x00000004</td>
            <td>
                e_cp<span class="info">(文件页数)</span></td>
            <td>
                <%=dosValue.get("e_cp") %></td>
        </tr>
        <tr>
            <td>
                0x00000006</td>
            <td>
                e_crlc<span class="info">(重定义元素个数)</span></td>
            <td>
                <%=dosValue.get("e_crlc")%></td>
        </tr>
        <tr>
            <td>
                0x00000008</td>
            <td>
                e_cparhdr<span class="info">(头部尺寸，以段落为单位)</span></td>
            <td>
                <%=dosValue.get("e_cparhdr") %></td>
        </tr>
        <tr>
            <td>
                0x0000000A</td>
            <td>
                e_minalloc<span class="info">(所需的最小附加段)</span></td>
            <td>
                <%=dosValue.get("e_minalloc") %></td>
        </tr>
        <tr>
            <td>
                0x0000000C</td>
            <td>
                e_maxalloc<span class="info">(所需的最大附加段)</span></td>
            <td>
                <%=dosValue.get("e_maxalloc") %></td>
        </tr>
        <tr>
            <td>
                0x0000000E</td>
            <td>
                e_ss<span class="info">(初始的SS值（相对偏移量）)</span></td>
            <td>
                <%=dosValue.get("e_ss") %></td>
        </tr>
        <tr>
            <td>
                0x00000010</td>
            <td>
                e_sp<span class="info">(初始的SP值)</span></td>
            <td>
                <%=dosValue.get("e_sp") %></td>
        </tr>
        <tr>
            <td>
                0x00000012</td>
            <td>
                e_csum<span class="info">(校验和)</span></td>
            <td>
                <%=dosValue.get("e_csum") %></td>
        </tr>
        <tr>
            <td>
                0x00000014</td>
            <td>
                e_ip<span class="info">(初始的IP值)</span></td>
            <td>
                <%=dosValue.get("e_ip") %></td>
        </tr>
        <tr>
            <td>
                0x00000016</td>
            <td>
                e_cs<span class="info">(初始的CS值（相对偏移量）)</span></td>
            <td>
                <%=dosValue.get("e_cs") %></td>
        </tr>
        <tr>
            <td>
                0x00000018</td>
            <td>
                e_lfarlc<span class="info">(重分配表文件地址)</span></td>
            <td>
                <%=dosValue.get("e_lfarlc") %></td>
        </tr>
        <tr>
            <td>
                0x0000001A</td>
            <td>
                e_ovno<span class="info">(覆盖号)</span></td>
            <td>
                <%=dosValue.get("e_ovno") %></td>
        </tr>
        <tr>
            <td>
                0x0000001C</td>
            <td>
                e_res<span class="info">(保留字)</span></td>
            <td>
                <%=dosValue.get("e_res") %></td>
        </tr>
        <tr>
            <td>
                0x00000024</td>
            <td>
                e_oemid<span class="info">(OEM标识符（相对e_oeminfo）)</span></td>
            <td>
                <%=dosValue.get("e_oemid") %></td>
        </tr>
        <tr>
            <td>
                0x00000026</td>
            <td>
                e_oeminfo<span class="info">(OEM信息)</span></td>
            <td>
                <%=dosValue.get("e_oeminfo") %></td>
        </tr>
        <tr>
            <td>
                0x00000028</td>
            <td>
                e_res2<span class="info">(10个字长度的保留字)</span></td>
            <td>
                <%=dosValue.get("e_res2") %></td>
        </tr>
        <tr>
            <td>
                0x0000003C</td>
            <td>
                <span class="special">e_lfanew<span class="info">(指向PE的IMAGE_NT_HEADES头，即指向&quot;PE&quot;字段)</span></span></td>
            <td>
                <span class="special"><%=dosValue.get("e_lfanew") %></span></td>
        </tr>
    </table>
    <!--=============================== PE文件标志 ===============================-->
    <p class="clr">
        PE文件标志<a name="NT"></a></p>
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="tb">
        <tr class="head">
            <td width="120">
                RVA</td>
            <td>
                Description</td>
            <td width="355">
                Value</td>
        </tr>
        <tr>
            <td>
                <%=dosValue.get("e_lfanew") %></td>
            <td>
                <span class="special">Signature</span><span class="info">(PE文件的标志)</span></td>
            <td>
                <span class="special"><%=NTValue.get("Signature") %></span></td>
        </tr>
    </table>
    <!--=============================== PE文件头 ===============================-->
    <p class="clr">
        PE文件头(IMAGE_FILE_HEADER)<a name="NT_FILE" id="NT_FILE"></a></p>
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="tb">
        <tr class="head">
            <td width="120">
                RVA</td>
            <td>
                Description</td>
            <td width="355">
                Value</td>
        </tr>
        <tr>
            <td>
                0xfc</td>
            <td>
                <span class="special">Machine</span><span class="info">(PE所运行的平台)</span></td>
            <td>
                <span class="special"><%=fileValue.get("Machine") %></span></td>
        </tr>
        <tr>
            <td>
                0xfe</td>
            <td>
                <span class="special">NumberOfSections</span><span class="info">(PE所包含的节数)</span></td>
            <td class="special">
                <%=fileValue.get("NumberOfSections") %></td>
        </tr>
        <tr>
            <td>
                0x100</td>
            <td>
                TimeDateStamp<span class="info">(生成日期)</span></td>
            <td>
                <%=fileValue.get("TimeDateStamp") %></td>
        </tr>
        <tr>
            <td>
                0x104</td>
            <td>
                PointerToSymbolTable<span class="info">(符号表)</span></td>
            <td>
                <%=fileValue.get("PointerToSymbolTable") %></td>
        </tr>
        <tr>
            <td>
                0x108</td>
            <td>
                NumberOfSymbols<span class="info">(符号数量)</span></td>
            <td>
                <%=fileValue.get("NumberOfSymbols") %></td>
        </tr>
        <tr>
            <td>
                0x10c</td>
            <td>
                SizeOfOptionalHeader<span class="info">(可选文件头大小)</span></td>
            <td>
                <%=fileValue.get("SizeOfOptionalHeader") %></td>
        </tr>
        <tr>
            <td>
                0x10e</td>
            <td>
                Characteristics<span class="info">(文件属性)</span></td>
            <td>
                <%=fileValue.get("Characteristics") %></td>
        </tr>
    </table>
    <!--=============================== 可选PE文件头 ===============================-->
    <p class="clr">
        可选PE文件头(IMAGE_OPTIONAL_HEADER)<a name="NT_OPTION" id="NT_OPTION"></a></p>
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="tb">
        <tr class="head">
            <td width="120">
                RVA</td>
            <td>
                Description</td>
            <td width="355">
                Value</td>
        </tr>
        <tr>
            <td>
                0x110</td>
            <td>
                <span class="special">Magic</span><span class="info">(说明文件类型是ROM映像还是可执行映像)</span></td>
            <td>
                <span class="special"><%=optionalValue.get("Magic") %></span></td>
        </tr>
        <tr>
            <td>
                0x112</td>
            <td>
                MajorLinkerVersion<span class="info">(链接器的主版本号)</span></td>
            <td>
                <%=optionalValue.get("MajorLinkerVersion") %></td>
        </tr>
        <tr>
            <td>
                0x113</td>
            <td>
                MinorLinkerVersion<span class="info">(链接器副版本号)</span></td>
            <td>
                <%=optionalValue.get("MinorLinkerVersion") %></td>
        </tr>
        <tr>
            <td>
                0x114</td>
            <td>
                SizeOfCode<span class="info">(Code Section的大小，是对齐后的大小)</span></td>
            <td>
                <%=optionalValue.get("SizeOfCode") %></td>
        </tr>
        <tr>
            <td>
                0x118</td>
            <td>
                SizeOfInitializedData<span class="info">(已初始化的数据大小)</span></td>
           <td>
                <%=optionalValue.get("SizeOfInitializedData") %></td>
        </tr>
        <tr>
            <td>
                0x11c</td>
            <td>
                SizeOfUninitializedData<span class="info">(未初始化的数据大小)</span></td>
            <td>
                <%=optionalValue.get("SizeOfUninitializedData") %></td>
        </tr>
        <tr>
            <td>
                0x120</td>
            <td>
                <span class="special">AddressOfEntryPoint</span><span class="info">(程序执行的相对入口地址)</span></td>
            <td>
                <%=optionalValue.get("AddressOfEntryPoint") %></td>
        </tr>
        <tr>
            <td>
                0x124</td>
            <td>
                BaseOfCode<span class="info">(代码段的起始的RVA)</span></td>
            <td>
                <%=optionalValue.get("BaseOfCode") %></td>
        </tr>
        <tr>
            <td>
                0x128</td>
            <td>
                BaseOfData<span class="info">(数据段的起始的RVA)</span></td>
            <td>
                <%=optionalValue.get("BaseOfData") %></td>
        </tr>
        <tr>
            <td>
                0x12c</td>
            <td>
                <span class="special">ImageBase</span><span class="info">(程序载入内存的基地址)</span></td>
            <td class="special">
                <%=optionalValue.get("ImageBase") %></td>
        </tr>
        <tr>
            <td>
                0x130</td>
            <td>
                SectionAlignment<span class="info">(内存对齐页大小，通常0x1000K)</span></td>
            <td>
                <%=optionalValue.get("SectionAlignment") %></td>
        </tr>
        <tr>
            <td>
                0x134</td>
            <td>
                FileAlignment<span class="info">(磁盘对齐页大小，通常0x200K)</span></td>
           	<td>
                <%=optionalValue.get("FileAlignment") %></td>
        </tr>
        <tr>
            <td>
                0x138</td>
            <td>
                MajorOperatingSystemVersion<span class="info">(所需最低要求操作系统的主版本号)</span></td>
            <td>
                <%=optionalValue.get("MajorOperatingSystemVersion") %></td>
        </tr>
        <tr>
            <td>
                0x13a</td>
            <td>
                MinorOperatingSystemVersion<span class="info">(所需最低要求操作系统的副版本号)</span></td>
            <td>
                <%=optionalValue.get("MinorOperatingSystemVersion") %></td>
        </tr>
        <tr>
            <td>
                0x13c</td>
            <td>
                MajorImageVersion<span class="info">(该程序的主版本号)</span></td>
            <td>
                <%=optionalValue.get("MajorImageVersion") %></td>
        </tr>
        <tr>
            <td>
                0x13e</td>
            <td>
                MinorImageVersion<span class="info">(该程序的副版本号)</span></td>
            <td>
                <%=optionalValue.get("MinorImageVersion") %></td>
        </tr>
        <tr>
            <td>
                0x140</td>
            <td>
                MajorSubsystemVersion<span class="info">(所需最低子系统版本的主版本号)</span></td>
            <td>
                <%=optionalValue.get("MajorSubsystemVersion") %></td>
        </tr>
        <tr>
            <td>
                0x142</td>
            <td>
                MinorSubsystemVersion<span class="info">(所需最低子系统版本的副版本号)</span></td>
            <td>
                <%=optionalValue.get("MinorSubsystemVersion") %></td>
        </tr>
        <tr>
            <td>
                0x144</td>
            <td>
                Win32VersionValue<span class="info">(保留值)</span></td>
            <td>
                <%=optionalValue.get("Win32VersionValue") %></td>
        </tr>
        <tr>
            <td>
                0x148</td>
            <td>
                SizeOfImage<span class="info">(装入内存后总大小)</span></td>
            <td>
                <%=optionalValue.get("SizeOfImage") %></td>
        </tr>
        <tr>
            <td>
                0x14c</td>
            <td>
                SizeOfHeaders<span class="info">(文件头的大小，包括DOS头、NT头和块表的大小)</span></td>
            <td>
                <%=optionalValue.get("SizeOfHeaders") %></td>
        </tr>
        <tr>
            <td>
                0x150</td>
            <td>
                CheckSum<span class="info">(CRC检验值)</span></td>
            <td>
                <%=optionalValue.get("CheckSum") %></td>
        </tr>
        <tr>
            <td>
                0x154</td>
            <td>
                Subsystem<span class="info">(图形接口子系统GUI)</span></td>
            <td>
                <%=optionalValue.get("Subsystem") %></td>
        </tr>
        <tr>
            <td>
                0x156</td>
            <td>
                DllCharacteristics<span class="info">(DllMain何时被调用，默认为0)</span></td>
            <td>
                <%=optionalValue.get("DllCharacteristics") %></td>
        </tr>
        <tr>
            <td>
                0x158</td>
            <td>
                SizeOfStackReserve<span class="info">(为线程保留的栈大小)</span></td>
            <td>
                <%=optionalValue.get("SizeOfStackReserve") %></td>
        </tr>
        <tr>
            <td>
                0x15c</td>
            <td>
                SizeOfStackCommit<span class="info">(线程初始化时提交的栈大小)</span></td>
            <td>
                <%=optionalValue.get("SizeOfStackCommit") %></td>
        </tr>
        <tr>
            <td>
                0x160</td>
            <td>
                SizeOfHeapReserve<span class="info">(为进程默认堆保留的内存大小)</span></td>
            <td>
                <%=optionalValue.get("SizeOfHeapReserve") %></td>
        </tr>
        <tr>
            <td>
                0x164</td>
            <td>
                SizeOfHeapCommit<span class="info">(进程初始化时提交的堆大小)</span></td>
            <td>
                <%=optionalValue.get("SizeOfHeapCommit") %></td>
        </tr>
        <tr>
            <td>
                0x168</td>
            <td>
                LoaderFlags<span class="info">(与调试相关)</span></td>
            <td>
                <%=optionalValue.get("LoaderFlags") %></td>
        </tr>
        <tr>
            <td>
                0x16c</td>
            <td>
                NumberOfRvaAndSizes<span class="info">(数据目录的总项数，通常为0x10)</span></td>
            <td>
                <%=optionalValue.get("NumberOfRvaAndSizes") %></td>
        </tr>
    </table>
    <!--=============================== PE区段信息 ===============================-->
    <p class="clr">
        PE区段信息(IMAGE_SECTION_HEADER)<a name="NT_SECTION" id="NT_SECTION"></a></p>
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="tb">
        <tr class="head">
            <td width="120">
                RVA</td>
            <td>
                Description</td>
            <td width="355">
                Value</td>
        </tr>
        <!--SECTION_HEADER_START-->
        <%
        while ()
        %>
        <tr>
            <td bgcolor="{SHS_BGCOLOR}">
                {Name1_add}</td>
            <td bgcolor="{SHS_BGCOLOR}" class="special">
                Name<span class="info">(区段名称)</span></td>
            <td bgcolor="{SHS_BGCOLOR}">
                <span class="special">{Name1}</span></td>
        </tr>
        <tr>
            <td bgcolor="{SHS_BGCOLOR}">
                {MisV_add}</td>
            <td bgcolor="{SHS_BGCOLOR}">
                Misc.VirtualSize<span class="info">(占内存大小)</span></td>
            <td bgcolor="{SHS_BGCOLOR}">
                {Misc.VirtualSize}</td>
        </tr>
        <tr>
            <td bgcolor="{SHS_BGCOLOR}">
                {VA_add}</td>
            <td bgcolor="{SHS_BGCOLOR}">
                VirtualAddress<span class="info">(虚拟地址)</span></td>
            <td bgcolor="{SHS_BGCOLOR}">
                {VirtualAddress}</td>
        </tr>
        <tr>
            <td bgcolor="{SHS_BGCOLOR}">
                {SORD_add}</td>
            <td bgcolor="{SHS_BGCOLOR}">
                SizeOfRawData<span class="info">(占磁盘大小)</span></td>
            <td bgcolor="{SHS_BGCOLOR}">
                {SizeOfRawData}</td>
        </tr>
        <tr>
            <td bgcolor="{SHS_BGCOLOR}">
                {PTRD_add}</td>
            <td bgcolor="{SHS_BGCOLOR}">
                PointerToRawData<span class="info">(文件偏移)</span></td>
            <td bgcolor="{SHS_BGCOLOR}">
                {PointerToRawData}</td>
        </tr>
        <tr>
            <td bgcolor="{SHS_BGCOLOR}">
                {PTR_add}</td>
            <td bgcolor="{SHS_BGCOLOR}">
                PointerToRelocations<span class="info">(重定位信息)</span></td>
            <td bgcolor="{SHS_BGCOLOR}">
                {PointerToRelocations}</td>
        </tr>
        <tr>
            <td bgcolor="{SHS_BGCOLOR}">
                {PTL_add}</td>
            <td bgcolor="{SHS_BGCOLOR}">
                PointerToLinenumbers<span class="info">(行号信息偏移)</span></td>
            <td bgcolor="{SHS_BGCOLOR}">
                {PointerToLinenumbers}</td>
        </tr>
        <tr>
            <td bgcolor="{SHS_BGCOLOR}">
                {NOR_add}</td>
            <td bgcolor="{SHS_BGCOLOR}">
                NumberOfRelocations<span class="info">(重定位数量)</span></td>
            <td bgcolor="{SHS_BGCOLOR}">
                {NumberOfRelocations}</td>
        </tr>
        <tr>
            <td bgcolor="{SHS_BGCOLOR}">
                {NOL_add}</td>
            <td bgcolor="{SHS_BGCOLOR}">
                NumberOfLinenumbers<span class="info">(行号数目)</span></td>
            <td bgcolor="{SHS_BGCOLOR}">
                {NumberOfLinenumbers}</td>
        </tr>
        <tr>
            <td bgcolor="{SHS_BGCOLOR}">
                {Char_add}</td>
            <td bgcolor="{SHS_BGCOLOR}">
                Characteristics<span class="info">(区段属性)</span></td>
            <td bgcolor="{SHS_BGCOLOR}">
                {Section_Characteristics}</td>
        </tr>
        <!--SECTION_HEADER_END-->
	</table>
</body>
</html>