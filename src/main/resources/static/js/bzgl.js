//https://blog.csdn.net/qq493820798/article/details/81216090 (js文件之间相互调用)
//调用CreateTree.js,CreateDT.js部分代码

var DTtable;
//文档id，在调用后台服务传参时使用
var schemaCodeID;
//修改标准时，发生改动的标准数量
var changedNum=0;
//修改标准时，发生改变的标准组成的数组
var stdArray= new Array();

//对要编辑的数据进行清洗，去掉空白字符，去掉某些特殊字符。后续可能还会有某些特殊字符出现
function cleanStr(strData)
{
	//其中一种使用正则表达式的方法
	//var pattern = new RegExp("[\r\n\t]",'g');
	//如果有字段为null，就统一转成""。因为判断数据是否被修改的时候，有一次是从DT中拿数据，DT会自动将null转为""
	if (strData == null)
		{
//		console.log("是否为空",strData == null);
		return "";
		}
	//如果不为null。要看数据类型是否为String，因为有的数字类型或其他类型，这样的可以不做清洗，直接统一转换为String类型比较。如果是String类型的，需要做清洗
	else{
//	console.log("类型",Object.prototype.toString.call(strData));
//	console.log("是否相等",Object.prototype.toString.call(strData) === "[object String]");
	if(Object.prototype.toString.call(strData) === "[object String]")
		{
		//把空白都去掉，包括\f换页、\n、\t等。进而将字符统一编码encodeURIComponent，一个汉字占3个字节。从中找到不要的特殊字符，删之。编解码后的变量仍是String类型
		var encodeData = encodeURIComponent(strData.replace(/\s+/g,'_'));
		//encodeData = encodeData.replace("%13","");
		encodeData = encodeData.replace(/%13|%01|%14/g,"");
		decodeData = decodeURIComponent(encodeData);
//		console.log("编码后：",encodeData.replace("%13",""));
//		console.log("编码后：",encodeData.replace("%01",""));
//		console.log("编码后：",encodeData.replace("%14",""));
//		console.log("变量类型",Object.prototype.toString.call(encodeData));
//		console.log("解码后：",decodeURIComponent(encodeData));
		return decodeData;}
	//非String类型的不做清洗
	else
		{
		strData = strData.toString();
		return strData;
		}
	}
}

//点击ztree后的响应函数
function zTreeOnClick(event, treeId, treeNode) {
	//查看被点击的节点信息
	//console.log("treeNode");
	//console.log(treeNode);
	
	//如果节点是字段（模型），那么点击该节点时，在右侧DT上显示字段信息
	//treeNode.ismodel == 1,此时为叶子节点，需要展示字段信息
	if(treeNode.ismodel!=0)
		{
		//服务接口需要该modelID，获取该model下的字段。此时的modelID及为每个字段中的schemaCodeID。也可理解为父节点ID
		//console.log(treeNode.ismodel);
		var sentJsonData = {"id":treeNode.modelID};
		schemaCodeID=treeNode.modelID;
		//console.log(sentJsonData);	
		//调用后台服务post接口，获取叶子节点的字段信息。如果没有字段信息，即字段信息为null，就不做处理
		$.ajax({
			url: "../stdglprj/stdgl/doGetStdSchemaObjectDetail",
			type: 'post',
			dataType: 'text',
			data: JSON.stringify(sentJsonData),
			contentType: "application/json;charset=utf-8",
			success: function (data) {
				//console.log("success");
				//将text转成Json
				var jsonData = JSON.parse(data);
				//需要显示到DT上的信息
				//console.log(jsonData.data.fields);
				//console.log(jsonData.data)
				var bzglDT = "#dt_bzgl";
				if (jsonData.data != null)
				DTtable = creatTableByVar_Op(bzglDT, jsonData.data.fields);
			}
		});		
	}
}

//创建目录树的用法
//第一步：在html中引用CreateTree.js。注意：bzgl_bzck.js文件的位置要在本文件之前，否则其中的函数可能会无法引用
//第二步：给出目录树id名称（这里id名称不加#，是因为ztree插件给出的接口需要这样的id名称）
var zTreeName = "tree";

//第三步：给出服务端获取目录树数据的接口url
var ztreeUrl = "../stdglprj/stdgl/doGetAllCategory";

//第四步：从服务端获取目录树数据（可视情况重写）
var zNodes = getzTreeData(ztreeUrl);
//第五步：重写Setting函数
var bzglSetting = {
    view: {
        dblClickExpand: false,
        showLine: true,
        selectedMulti: false,
    },
    data: {
        key: {
            name: "name",
        },
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "pid",
            rootPId: ""
        }
    },
    callback: {
        //是否可以删掉
        beforeClick: function (treeId, treeNode) {
            //var zTree = $.fn.zTree.getZTreeObj("tree");
            //zTree.expandNode(treeNode);
            return true;
        },
        onClick: zTreeOnClick,
    }
};
//第六步：重写创建目录树函数
function createzTreeBzgl(ztreeSetting,zTreeName,zNodes){
//创建ztree目录。zTree 初始化方法，创建 zTree 必须使用此方法
$.fn.zTree.init($("#"+zTreeName), ztreeSetting, zNodes);
//更新目录树节点名称
zTreeOperation.updateNodeName(zTreeName);
}
//第七步：运行目录树创建函数
createzTreeBzgl(bzglSetting,zTreeName,zNodes);

//“单行编辑”操作，调用服务器特定接口
//问题1：input适应问题
$("#dt_bzgl").on("click", ".btn.bt_edit.btn-primary.btn-xs", function () {
	//重置发生改变的标准数量
	changedNum=0;
    //console.log("gyd...1234");
    //console.log($(this));
    //console.log("button");
    //获取被点击按钮所在行的所有元素
    var tds = $(this).parents("tr").children();
    //遍历行元素，如果行元素为数据项，将该元素变为input框，框内提示为原始数据项
    $.each(tds, function (i, val) {
        var jqob = $(val);
        //console.log("val_gyd");
        //console.log(val);
        if (i < 1 || jqob.has('button').length) { return true; }//跳过第1项 checkbox,按钮
        //在编辑前，将数据清洗一下，以供编辑
        var txt = cleanStr(jqob.text());
        //console.log("txt_gyd");
        //console.log(txt);
        //替换html的一种方法
        //var put = $("<input type='text'>");
        //put.val(txt);
        //jqob.html(put);
        
        //替换html的另一种方法
        jqob.html("<input type=\"text\"  value='" + txt+"'  />");
    });
    //点击编辑按钮后，编辑按钮变为“保存”按钮
    //改进1:toggle方法实现按钮class切换
    $(this).replaceWith("<button type=\"button\" style = \"margin:0px;display:inline\" class=\"btn bt_save btn-success btn-xs\"  id=\"dt_save\"><i class=\"fa fa-save\"></i> 保存</button>");
    //console.log(tds);
});

//“单行保存”按钮操作
$("#dt_bzgl").on("click", ".btn.bt_save.btn-success.btn-xs", function () {
	//获取行对象
    var row = DTtable.row($(this).parents("tr"));
    //console.log("row_gyd");
    //console.log(row);
    //获取表中原本显示的旧数据
    var oldData = row.data();
    //var oldCode = oldData.code;
    //var oldName = oldData.name;
    //var oldEnName = oldData.enName;
    //var oldCreateTime = oldData.createTime;
	 oldFieldCode=oldData.fieldCode;
	 oldFieldName=oldData.fieldName;
	 oldEnName=oldData.enName;
	 oldType=oldData.type;
	 oldRequired=oldData.required;
	 oldRange=oldData.range;
	 oldMaxsize=oldData.maxsize;
	 oldMaxContainCount=oldData.maxContainCount;
	 oldDefination=oldData.defination;
	 oldCreator=oldData.creator;
	 oldCreateTime=oldData.createTime;
	 oldComments=oldData.comments;
    //console.log("oldData");
    //console.log(oldData);
    //获取被点击按钮所在行的所有元素
    var tds = $(this).parents("tr").children();
    //遍历行元素，将修改后的数据展示在DT上
    $.each(tds, function (i, val) {
        var jqob = $(val);
        //把编辑后的input框内的值重新显示到DT上
        if (i >= 1 && !jqob.has('button').length) {
            //获取input框内的value
        	var txt = jqob.children("input").val();
            //console.log("input值：",txt);
            //改变html
        	jqob.html(txt);
            //console.log(jqob);
        	//修改DataTables对象的数据
            DTtable.cell(jqob).data(txt);
        }
    });

    var data = row.data();
    //console.log("rowData_gyd");
    //console.log(data);
    //比较新旧数据是否一致
    //console.log("compare");
    //console.log(JSON.stringify(oldData)==JSON.stringify(data));
    //console.log(oldData==data);
    //需引入CompareObject.js
    //console.log(CompareObj(oldData, data, true));
    //console.log(oldCode);
    //console.log(data.code);
    //console.log(oldCode==data.code);
    //console.log(!( 
	// oldFieldCode==data.fieldCode&&
	// oldFieldName==data.fieldName&&
	// oldEnName==data.enName&&
	// oldType==data.type&&
	// oldRequired==data.required&&
	// oldRange==data.range&&
	// oldMaxsize==data.maxsize&&
	// oldMaxContainCount==data.maxContainCount&&
	// oldDefination == data.defination&&
	// oldreator==data.creator&&
	// oldCreateTime==data.createTime&&
	// oldComments==data.comments
	//));
	//在批量保存的时候，记录下发生改变的标准数量
	//&dmash表反斜杠
	
	
	
//	var changeFlag = (
//	 oldFieldCode.trim().replace(pattern,'')==data.fieldCode.trim().replace(pattern,'')&&
//	 oldFieldName.trim().replace(pattern,'')==data.fieldName.trim().replace(pattern,'')&&
//	 oldEnName.trim().replace(pattern,'')==data.enName.trim().replace(pattern,'')&&
//	 oldType.trim().replace(pattern,'')==data.type.trim().replace(pattern,'')&&
//	 oldRequired.trim().replace(pattern,'')==data.required.trim().replace(pattern,'')&&
//	 oldRange.trim().replace(pattern,'')==data.range.trim().replace(pattern,'')&&
//	 oldMaxsize.trim().replace(pattern,'')==data.maxsize.trim().replace(pattern,'')&&
//	 oldMaxContainCount.trim().replace(pattern,'')==data.maxContainCount.trim().replace(pattern,'')&&
//	 oldDefination.trim().replace(pattern,'') == data.defination.trim().replace(pattern,'')&&
//	 //oldreator==data.creator&&
//	 //oldCreateTime==data.createTime&&
//	 oldComments.trim().replace(pattern,'')==data.comments.trim().replace(pattern,'')
//	 //equals(oldComments,data.comments)
//	 
//	);
//	
//	var changeFlag = (
//			cleanStr(pattern,oldFieldCode)==cleanStr(pattern,data.fieldCode)&&
//			cleanStr(pattern,oldFieldName)==cleanStr(pattern,data.fieldName)&&
//			cleanStr(pattern,oldEnName)==cleanStr(pattern,data.enName)&&
//			cleanStr(pattern,oldType)==cleanStr(pattern,data.type)&&
//			cleanStr(pattern,oldRequired)==cleanStr(pattern,data.required)&&
//			cleanStr(pattern,oldRange)==cleanStr(pattern,data.range)&&
//			cleanStr(pattern,oldMaxsize)==cleanStr(pattern,data.maxsize)&&
//			cleanStr(pattern,oldMaxContainCount)==cleanStr(pattern,data.maxContainCount)&&
//			cleanStr(pattern,oldDefination) == cleanStr(pattern,data.defination)//&&
//			//cleanStr(pattern,oldCreator)==cleanStr(pattern,data.creator)&&
//			//oldCreateTime==data.createTime&&
//			//cleanStr(pattern,oldComments)==cleanStr(pattern,data.comments)
//			 //equals(oldComments,data.comments)
//			 
//			);
    
	var changeFlag = (
	cleanStr(oldFieldCode)==cleanStr(data.fieldCode)&&
	cleanStr(oldFieldName)==cleanStr(data.fieldName)&&
	cleanStr(oldEnName)==cleanStr(data.enName)&&
	cleanStr(oldType)==cleanStr(data.type)&&
	cleanStr(oldRequired)==cleanStr(data.required)&&
	cleanStr(oldRange)==cleanStr(data.range)&&
	cleanStr(oldMaxsize)==cleanStr(data.maxsize)&&
	cleanStr(oldMaxContainCount)==cleanStr(data.maxContainCount)&&
	cleanStr(oldDefination) == cleanStr(data.defination)&&
	//cleanStr(pattern,oldCreator)==cleanStr(pattern,data.creator)&&
	//oldCreateTime==data.createTime&&
	cleanStr(oldComments)==cleanStr(data.comments)
	 //equals(oldComments,data.comments)
	 
	);
	
//	var changeFlag = (
//			cleanStr(pattern,oldFieldCode)==cleanStr(pattern,data.fieldCode)&&
//			cleanStr(pattern,oldFieldName)==cleanStr(pattern,data.fieldName)&&
//			cleanStr(pattern,oldEnName)==cleanStr(pattern,data.enName)&&
//			cleanStr(pattern,oldType)==cleanStr(pattern,data.type)&&
//			cleanStr(pattern,oldRequired)==cleanStr(pattern,data.required)&&
//			cleanStr(pattern,oldRange)==cleanStr(pattern,data.range)&&
//			cleanStr(pattern,oldMaxsize)==cleanStr(pattern,data.maxsize)&&
//			cleanStr(pattern,oldMaxContainCount)==cleanStr(pattern,data.maxContainCount)&&
//			cleanStr(pattern,oldDefination) == cleanStr(pattern,data.defination)//&&
//			//cleanStr(pattern,oldCreator)==cleanStr(pattern,data.creator)&&
//			//oldCreateTime==data.createTime&&
//			//cleanStr(pattern,oldComments)==cleanStr(pattern,data.comments)
//			 //equals(oldComments,data.comments)
//			 
//			);
	
	//如果发生改变
	if(!changeFlag)
	{
//		console.log("oldComments:",oldComments.trim().replace(pattern,''));
//		console.log("coments: ", data.comments.trim().replace(pattern,''));
//		
//		console.log("oldComments:",encodeURIComponent(oldComments.trim().replace(pattern,'')));
//		console.log("coments: ", encodeURIComponent(data.comments.trim().replace(pattern,'')));
//		
//		console.log("oldFieldCode:",oldFieldCode.trim().replace(pattern,''));
//		console.log("data.fieldCode: ", data.fieldCode.trim().replace(pattern,''));
//		
//		console.log("oldFieldCode:",encodeURIComponent(oldFieldCode.trim().replace(pattern,'')));
//		console.log("data.fieldCode: ", encodeURIComponent(data.fieldCode.trim().replace(pattern,'')));
//		
//		console.log("oldFieldName:",oldFieldName.trim().replace(pattern,''));
//		console.log("data.fieldName: ", data.fieldName.trim().replace(pattern,''));
//		
//		console.log("oldFieldName:",encodeURIComponent(oldFieldName.trim().replace(pattern,'')));
//		console.log("data.fieldName: ", encodeURIComponent(data.fieldName.trim().replace(pattern,'')));
		
//		console.log("oldCreator:",oldCreator);
//		console.log("data.creator: ", data.creator);
//		
//		console.log("oldCreator:",encodeURIComponent(oldCreator));
//		console.log("data.creator: ", encodeURIComponent(data.creator));

//		console.log("oldRange:",oldRange);
//		console.log("data.range: ", data.range);
//		
//		console.log("oldRange:",encodeURIComponent(oldRange));
//		console.log("data.range: ", encodeURIComponent(data.range));
		
//		console.log("flag: ",changeFlag);
		changedNum+=1;
//		console.log("name");
//		console.log(data.fieldName);
	}
	//如果已经发生改变的标准数量仅为1个，且发生改变是在本次修改，那么就向后台发送修改标准请求，否则将剩余的发生变化的标准组成一个Array，在点击批量保存的时候，向后台发送批量修改标准请求
	if(changedNum==1 && (!changeFlag))
	{
		//调用修改标准接口
		console.log("发送标准修改请求：",data);
		
		$.ajax({
			url: "../stdglprj/stdgl/doUpdateStdfieldObject",
			type: 'post',
			dataType: 'text',
			data: JSON.stringify(data),
			contentType: "application/json;charset=utf-8",
			success: function (data) {
				//console.log("success");
				//var jsonData = JSON.parse(data);
				//需要显示到DT上的信息
				//console.log(jsonData.data.fields);
				console.log(data);
			}
		});
		
	}
	//如果已经发生改变的标准数量大于1个，且发生改变是在本次修改
	if(changedNum>1 && (!changeFlag))
	{
		//将剩余的发生变化的标准组成一个Array
		//console.log("修改后的数据：",data);
		stdArray.push(data);
		
		//console.log("array");
		//console.log(stdArray);
		
		//console.log("Number");
		//console.log(changedNum);

	}
	

	
	//！！！！遍历比较与服务器发送可以在这里实现
//重新将“保存”按钮变成编辑按钮
    $(this).replaceWith("<button type=\"button\"  style = \"margin:0px;display:inline\" class=\"btn bt_edit btn-primary btn-xs\" id=\"dt_edit\"><i class=\"fa fa-pencil\"></i> 编辑 </button>");
    //$(this).toggleClass("edit-btn");
    //$(this).toggleClass("save-btn");
});

//“删除”操作，调用服务器特定接口
//该变量为后面“确定删除”按钮使用
var row4Del;

var row4DelList= new Array();
$("#dt_bzgl").on("click", ".btn.bt_unpass.btn-danger.btn-xs", function () {
    //弹出“确定删除”提示框
    //https://v4-alpha.getbootstrap.com/components/modal/#modalshow (手动显示Modal)
    $('#delWarning4delSingle').modal('show');

//获取将要被删除行的数据内容
    row4Del = DTtable.row($(this).parents().parents("tr"));
    //console.log("拟删除数据：",row4Del.data());
    //var code = row4Del.data().code;
    //console.log("codeSingle_gyd");
    //console.log(code);
});

//单条删除modal，在弹出时的提示信息及操作
$('#delWarning4delSingle').on('show.bs.modal',
    function () {
        //console.log("del_single");
        //？？ESC快捷键
        $("#ModalMesgSingle").text("确定删除该条数据？");
    });

    //单条删除modal确定按钮，点击后删除该行数据，并调用服务接口，删除该条数据
$("#confirmDelButtonSingle").on("click", function () {

    //console.log("id",row4Del.data().id);
    var sentJsonData = {"id":row4Del.data().id};
    console.log("data字段：", row4Del.data());
    console.log("请求参数",JSON.stringify(sentJsonData));
	$.ajax({
		url: "../stdglprj/stdgl/doDeleteStdfieldObject",
		type: 'delete',
		dataType: 'text',
		data: JSON.stringify(sentJsonData),
		contentType: "application/json;charset=utf-8",
		success: function (data) {
			//console.log("success");
			//var jsonData = JSON.parse(data);
			//需要显示到DT上的信息
			//console.log(jsonData.data.fields);
			console.log(data);
		}
	});
    //https://datatables.net/reference/api/rows().remove() (删除选中的多行)
    var row4Deled = row4Del
        .remove()
        .draw(false);
    console.log("被删除的数据：", row4Deled);
    //console.log("delconfirm_gyd");
    //table.rows('.selected').remove().draw( false );
});

//checkbox全选
// https://blog.csdn.net/homelam/article/details/79863731
// https://blog.csdn.net/DreamFJ/article/details/75365123 异步click事件
function checkAllFunc() {
    //console.log("checkAll_gyd");
    //console.log($("#checkAll").prop("checked"));
    if ($("#checkAll").prop("checked")) {
        $("input[name='checkList']").prop("checked", true);
    } else {
        $("input[name='checkList']").prop("checked", false);
    }
}

//创建DT
//var dwsp_url = "./txt/GMBCI3.txt";
//var dwspDT = "#dt_dwsp";
//console.log("gyd11");
//var DTtable = creatTable_Op(dwspDT, dwsp_url);

//var testBool;
//console.log("before");
//console.log(testBool);

/**
*批量删除
*/
//http://www.runoob.com/bootstrap/bootstrap-modal-plugin.html(在Modal显示的时候doSomething的API)
//http://www.w3school.com.cn/tiy/t.asp?f=jquery_manipulation_text_set(修改元素text的方法)
//“批量删除modal”显示时，显示要删除数据的条数以及要删除的数据
$('#delWarning4delBatch').on('show.bs.modal',
    function () {
        //console.log("del_batch");
        var str;
        //http://www.datatables.club/example/api/select_row.html (获取行数)
        //手动获取选中行数
        var checkedNum = 0;
        row4DelList= new Array();
        $("input[name='checkList']:checked").each(function (i, o) {
            str += $(this).val();
            str += ",";
            //获取选中行的数据,供调用服务删除接口时使用
            var DataId = DTtable.row($(this).parents("tr")).data().id;
            var JsonData = {"id":DataId};
            row4DelList.push(JsonData);
            //console.log("checkbox_code_gyd");
            //console.log(code);
            checkedNum++;
        });
        if (checkedNum == 0) {
            $("#ModalMesg").text("请至少选择一条数据！");
        }
        else {
            $("#ModalMesg").text("将有" + checkedNum.toString() + "条数据被删除！");
        }
    });

/**
*“确定批量删除”按钮操作
*/
//DT删除选中行，并调用服务删除接口
$("#confirmDelButton").on("click", function () {

    var sentJsonData = {"fields":row4DelList};
    console.log("请求参数",JSON.stringify(sentJsonData));
	$.ajax({
		url: "../stdglprj/stdgl/doplDeleteStdfieldObject",
		type: 'delete',
		dataType: 'text',
		data: JSON.stringify(sentJsonData),
		contentType: "application/json;charset=utf-8",
		success: function (data) {
			//console.log("success");
			//var jsonData = JSON.parse(data);
			//需要显示到DT上的信息
			//console.log(jsonData.data.fields);
			console.log(data);
		}
	});
	
    //https://datatables.net/reference/api/rows().remove() (删除选中的多行)
    var rows = DTtable
        .rows($("input[name='checkList']:checked").parents("tr"))
        .remove()
        .draw(false);
    //console.log("delconfirm_gyd");
    //table.rows('.selected').remove().draw( false );
});
   // var testBool;
//“添加数据Modal”
function addFunction() {

    $('#addData').modal('show');
    //console.log(testBool);
    //return testBool;
    
}
//console.log(testBool);
//“批量删除Modal”
function delFunction() {
    //data-toggle="modal" data-target="#delWarning4delBatch"
    $('#delWarning4delBatch').modal('show');
}

/**
*批量编辑
*/
//！！！！被选中行进行批量编辑
function Fedit_batch() {
    //console.log("gyd...123");
    //console.log($(this));
    $(".btn.bt_edit.btn-primary.btn-xs").click();
    //$(this).replaceWith("<button id=\"save_batch\" type=\"button\" class=\"btn bt_save_batch btn-success btn-xs\">批量保存</button>&nbsp")
}
/**
*批量保存
*/
//！！！！被选中行进行批量保存
function Fsave_batch() {
    $(".btn.bt_save.btn-success.btn-xs").click();
    if (stdArray.length >= 1){
    	var sentJsonData = {"id":schemaCodeID,"fields":stdArray};
        console.log("stringigy后的发送数据", JSON.stringify(sentJsonData));
        console.log("改动的数据array", stdArray);
    	$.ajax({
    		url: "../stdglprj/stdgl/doplUpdateStdfieldObject",
    		type: 'post',
    		dataType: 'text',
    		data: JSON.stringify(sentJsonData),
    		contentType: "application/json;charset=utf-8",
    		success: function (data) {
    			//console.log("success");
    			//var jsonData = JSON.parse(data);
    			//需要显示到DT上的信息
    			//console.log(jsonData.data.fields);
    			console.log(data);
    		}
    	});
    }
    stdArray= new Array();
    //$(this).replaceWith("<button id=\"edit_batch\" type=\"button\" class=\"btn bt_edit_batch btn-primary btn-xs\">批量编辑</button>&nbsp")
}

//添加数据DT
MDTable = $('#dt_addData').DataTable(
    {
        //设置排序
"ordering": false,
"columns": [

//控制DT列名与列数据字段
            {
                "title": "编码",
            },
            {
                "title": "中文名称",
            },
            {
                "title": "英文名称",
            },
            {
                "title": "类型",
            },
            {
                "title": "是否必须",
            },
            {
                "title": "取值范围",
            },
            {
                "title": "最大长度",
            },
            {
                "title": "最大出现次数",
            },
            {
                "title": "定义",
            },
            {
                "title": "创建者",
            },
            {
                "title": "创建时间",
            },
            {
                "title": "备注",
            },
        ],
        "searching": false, //去掉搜索框
        "bLengthChange": false,//去掉每页多少条框体 
        "language": {
            "url": "./js/DataTables.json"
        },
        "destroy": true, //Cannot reinitialise DataTable,解决重新加载表格内容问题 ，重新加载数据时，它就不会报错了
        //去掉排序功能
        "columnDefs": [
        { "orderable": false, 
        "targets": "_all",
        "createdCell": function (td, cellData, rowData, row, col) {

                    $(td).removeClass('sorting');
            }
            
        }
        ]
    }
);
//
///**
//*Modal添加数据
//*/
var rowId = 0;
$(".btn.bt_add_Modal.btn-default").on("click", function () {
//    //console.log("add_Modal_gyd");
//    //添加行
    MDTable.row.add([
//
		"<td><input type=\"text\" name='fieldCode" + rowId + "' placeholder=\"请输入编码\" autocomplete=\"on\"/></td>",
        "<td><input type=\"text\"  name='fieldName" + rowId + "' placeholder=\"请输入中文名称\" autocomplete=\"on\"/></td>",
        "<td><input type=\"text\"  name='enName" + rowId + "'  placeholder=\"请输入英文名称\" autocomplete=\"on\"/></td>",
        "<td><input type=\"text\" name='type" + rowId + "'  placeholder=\"请输入类型\" autocomplete=\"on\"/></td>",
        "<td><input type=\"text\" name='required" + rowId + "' placeholder=\"是否必须\" autocomplete=\"on\"/></td>",
        "<td><input type=\"text\"  name='range" + rowId + "' placeholder=\"请输入取值范围\" autocomplete=\"on\"/></td>",
        "<td><input type=\"text\"  name='maxsize" + rowId + "'  placeholder=\"请输入最大长度\" autocomplete=\"on\"/></td>",
        "<td><input type=\"text\" name='maxContainCount" + rowId + "'  placeholder=\"请输入最大出现次数\" autocomplete=\"on\"/></td>",
        "<td><input type=\"text\" name='defination" + rowId + "' placeholder=\"请输入定义\" autocomplete=\"on\"/></td>",
      "<td><input type=\"text\"  name='creator" + rowId + "' placeholder=\"请输入创建者\" autocomplete=\"on\"/></td>",
      "<td><input type=\"text\"  name='createTime" + rowId + "'  placeholder=\"请输入创建时间\" autocomplete=\"on\"/></td>",
        "<td><input type=\"text\" name='comments" + rowId + "'  placeholder=\"请输入备注\" autocomplete=\"on\"/></td>",
	]).draw();
    rowId++;
});

//“添加数据modal”关闭时，清除DT，重置行编号
$('#addData').on('hide.bs.modal',
    function () {
        MDTable.clear().draw();
        rowId = 0;
        //testBool = true;
        //console.log(testBool);
        //return testBool;
    });

 //   console.log("after");
 //   console.log(testBool);
/**
*“确定添加”按钮操作
*/

$("#confirmAddButton").on("click", function () {
  //获取行数据
    var nTrs = MDTable.rows().nodes();
    //遍历行对象 
    $.each(nTrs, function (i, val) {
        //console.log(i);
        //console.log("textInput");
        //console.log(typeof nTrs);
		//查看行dom
        //console.log(val);
        var sendFieldCode = $("input[name='fieldCode" + i + "']").val();
        var sendFieldName = $(" input[ name='fieldName" + i + "' ] ").val();
        var sendEnName = $(" input[ name='enName" + i + "' ] ").val();
        var sendType = $(" input[ name='type" + i + "' ] ").val();
        var sendRequired = $("input[name='required" + i + "']").val();
        var sendRange = $(" input[ name='range" + i + "' ] ").val();
        var sendMaxsize = $(" input[ name='maxsize" + i + "' ] ").val();
        var sendMaxContainCount = $(" input[ name='maxContainCount" + i + "' ] ").val();
        var sendDefination = $("input[name='defination" + i + "']").val();
        var sendCreator = $(" input[ name='creator" + i + "' ] ").val();
        var sendCreateTime = $(" input[ name='createTime" + i + "' ] ").val();
        var sendComments = $(" input[ name='comments" + i + "' ] ").val();
        //console.log(myCode);
        //console.log(myName);
        //console.log(myEnName);
        //console.log(myCT);
        
    
	//向服务端发送添加数据请求
	var sentJsonData = {
		"fieldCode": sendFieldCode,
		"fieldName": sendFieldName,
		"enName": sendEnName,
		"type": sendType,
		"required": sendRequired,
		"range": sendRange,
		"maxsize": sendMaxsize,
		"maxContainCount": sendMaxContainCount,
		"defination":sendDefination,
		"creator": sendCreator,
		"createTime": sendCreateTime,
		"comments": sendComments,
		"schemaCodeID":schemaCodeID,
		"pxh":"",
		"schemaCode":"",
		"schemaName":""
		}
	console.log("sendAddJsonData");
	console.log(sentJsonData);
	$.ajax({
				url: "../stdglprj/stdgl/doAddStdfield",
				type: 'post',
				dataType: 'text',
				data: JSON.stringify(sentJsonData),
				contentType: "application/json;charset=utf-8",
				success: function (data) {
						//依次添加行数据，到对应的列字段下
						DTtable.row.add(
							{
								"<input type=\"checkbox\" id='checkAll'>": "<input type='checkbox' name='checkList'>",
								"fieldCode": sendFieldCode,
								"fieldName": sendFieldName,
								"enName": sendEnName,
								"type": sendType,
								"required": sendRequired,
								"range": sendRange,
								"maxsize": sendMaxsize,
								"maxContainCount": sendMaxContainCount,
								"defination": sendDefination,
								"creator": sendCreator,
								"createTime": sendCreateTime,
								"comments": sendComments,
								"操作": "<button type=\"button\"  style = \"margin:0px;display:inline\" class=\"btn bt_edit btn-primary btn-xs\" ><i class=\"fa fa-pencil\"></i> 编辑 </button>" +
									" <button type=\"button\" style=\"display:inline\" class=\"btn bt_unpass btn-danger btn-xs\"><i class=\"fa fa-times\"></i> 删除 </button>"
							}
						).draw();
					//console.log("success");
				}
			});	
		});
	});

//下一步
//批量编辑的时候，数据量过大时，仅针对当前页面

//参考1：http://www.datatables.club/example/user_share/basic_curd.html（php基本增删改查）
