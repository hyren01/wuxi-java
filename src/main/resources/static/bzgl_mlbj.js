//https://blog.csdn.net/qq493820798/article/details/81216090 (js文件之间相互调用)
//调用bzgl_bzck.js部分代码
//function now_node(){
//	var zTree = GetzTree(zTreeName);
//	now_ztreeNode= zTree.getSelectedNodes();
//	return now_ztreeNode;
//}

//在编辑当前目录时不可更换树节点
treeclick_status = true

//向后台服务器发送请求
//修改目录
function updata_mulu(r_data){	
	$.ajax({
	    url: "../stdglprj/stdgl/doUpdateCategory",
	    type: 'post',
	    dataType: 'text',
	    contentType:'application/json',	    
	    data: JSON.stringify(r_data),	   
	    success: function (data) {
//	        console.log(data);
	    }
	});
}


//新增目录
function add_mulu(r_data){	
	$.ajax({
	    url: "../stdglprj/stdgl/doAddCategory",
	    type: 'post',
	    dataType: 'text',
	    contentType:'application/json',	    
	    data: JSON.stringify(r_data),	   
	    success: function (data) {
//	        console.log(data);
	    }
	});
}

//删除目录及其包含的子目录
function delete_mulu(r_data){
	del = {
		"id":r_data.id,
		"ismodel" : r_data.ismodel,
	}
	$.ajax({
	    url: "../stdglprj/stdgl/doDeleteCategory",
	    type: 'delete',
	    dataType: 'text',
	    contentType:'application/json',	    
	    data: JSON.stringify(del),	   
	    success: function (data) {
//	        console.log(data);
	    }
	});
}


//正在编辑节点时不能点击别的节点
function zTreeBeforeClick(treeId, treeNode) {	
      if(treeclick_status){      
          return true;
      }
      else{            
          if(confirm("是否保存当前目录？")){
        	  save_change(treeNode);
        	  return true;
        	  
          }
          else       	 
        	  return false;
      }
        	          	  
  }


//点击ztree后的响应函数
function zTreeOnClick(event, treeId, treeNode) {
    //调用后台服务接口，
    var urlStr = "http://localhost:8080/stdglprj/stdgl/doGetAllCategory";
//删除名称选项括号内的内容
    name = treeNode.name.replace(/\([^\)]*\)/g,"");
    now_ztreeNode = treeNode;
    $("#input1").val(name);
    $("#input2").val(treeNode.creator);
    $("#input3").val(treeNode.creator_time);
    $("#input4").val(treeNode.pxh);
    $("#input5").val(treeNode.modelID);
    change_status = 1;
    edite(treeNode);
    $("#delete").off().on("click",function(){
    	 if(confirm("提示：将删除当前目录及其子目录。确认删除当前目录？")){
    		 delete_mulu(treeNode); 
    		 var zTree = GetzTree(zTreeName); 
    		 zTree.removeNode(treeNode);  
    		 p_node = treeNode.getParentNode();
    		 p_node.name =  p_node.name.replace(/ \(.*?\)/g,"") ;
    		 RenameNode(zTree,p_node);
  		 
         }           	
   })
   
//    new_tjmulu(treeNode);
    new_zimulu(treeNode);
    $("#cancel_mlbj").off().on("click",function(){
    	 if(confirm("确认放弃当前编辑？")){
    		 cancel_change();       	  
         }           	
    })
}


//更换名称
function new_name(old_n,new_n){
//	$('#input1').bind("input propertychange",function(event){
//		console.log($('#input1').val());
//		name_part = $('#input1').val();
//	})
	 length = old_n.length;
	 kuohao_index = old_n.indexOf("(");
	 name_part = old_n.substring(0,kuohao_index);
	 kuohao = old_n.substring(kuohao_index,length);
	 name = new_n.concat(kuohao);
//	 console.log(name);
	 return name;
}
//change_statues用于记录节点修改的类型，0为未修改，1为编辑，2为新增文件
change_status = 0;

function isEmptyObject(obj) {
　　for (var key in obj){
　　　　return false;//返回false，不为空对象
　　}　　
　　return true;//返回true，为空对象
}



function edite(treeNode){
//	//编辑当前选项
	$("#edite_btn").off().on("click",function()	{
//		alert("在左边选择一个要修改的项目！！")
    	$("#edite_info").text("编辑当前选项");
        $("#inputN").removeAttr("disabled");
        $("#sava_mlbj").removeAttr("disabled");
        $("#cancel_mlbj").removeAttr("disabled");
        $("#new_lower").attr("disabled",true);
        $("#delete").attr("disabled",true);
     
        $("#input1").removeAttr("disabled");
        $("#input2").removeAttr("disabled");        
        $("#input4").removeAttr("disabled");        
        $("#input5").removeAttr("disabled");
        $("#input6").removeAttr("disabled");
        
        treeclick_status = false;
        change_status = 1;
        $("#sava_mlbj").off().on("click",function(){
        	save_change(treeNode);
        }) 
	})
}


//新增同级目录
function new_tjmulu(treeNode){
	$("#new_visavis").off().on("click",function(){
	    if(!isEmptyObject(now_ztreeNode))
	    {
	    	$("#edite_info").text("新增同级目录");
	    	$("#input1").val("目录名称");
	    	$('#input1').bind("input propertychange",function(event){
			name_part = $('#input1').val();
			name = new_name()
		})
	    	
	        $("#input1").removeAttr("disabled");
	        $("#input2").removeAttr("disabled");
	        $("#input4").removeAttr("disabled");
	        $("#input5").removeAttr("disabled");
	        $("#input6").removeAttr("disabled");
	        $("#sava_mlbj").removeAttr("disabled");
	        $("#cancel_mlbj").removeAttr("disabled");
	        
	      //新增
	        var zTree = GetzTree(zTreeName);     
	        nodes = zTree.getSelectedNodes();
	        p_node = nodes[0].getParentNode();
	        	        
	        treeclick_status = false;
	        change_status = 2;
	        
	        $("#sava_mlbj").off().on("click",function(){
	        	save_change(p_node);
	        }) 
	        
	       
	    }
	    else{
	        alert("在左边选择一个项目！！")
	    }
	    
	})
}



//新增子目录
function new_zimulu(treeNode){
	$("#new_lower").off().on("click",function(){
    if(!isEmptyObject(now_ztreeNode))
    {
    	
    	if(treeNode.ismodel)
    		alert("请勿在模型节点下新增子目录！")
    	
    	else{
    		$("#edite_info").text("新增子目录");
	        $("#input1").val("目录名称");
	        $("#input1").removeAttr("disabled");
	        $("#input6").removeAttr("disabled");
	        $("#sava_mlbj").removeAttr("disabled");
	        $("#cancel_mlbj").removeAttr("disabled");  
	        $("#edite_btn").attr("disabled",true);
	        $("#delete").attr("disabled",true);
	      
	        treeclick_status = false;
	        change_status = 2;
	        $("#sava_mlbj").off().on("click",function(){
	        	
	        	save_change(treeNode);
	        }) 
	        $("#cancel_mlbj").off().on("click",function(){
	        	cancel_change(treeNode);
	        }) 
       
    		
    	}
        
    }
    else{
        alert("在左边选择一个项目！！")
    }
    change_status = 2;
	})
}


//保存当前编辑
function save_change(node){
	name = $("#input1").val();
	creator= $("#input2").val();
	creator_time = $("#input2").val();
	pxh = $("#input4").val();
	modelId = $("#input5").val();
	type = $("#input6 ").val();
	if(type == '模型')
		ismodel = 1;
	else
		ismodel = 0;
//	console.log(node.getParentNode().name);
	
//判断是修改还是新增
//change_status==1为修改编辑
	if(change_status == 1){
		var ajax_node ={
				"id" : node.id,
				"name" : name,
				"creator" : creator,
				"pxh":pxh,				
				"ismodel" : ismodel,
				"pid" : node.getParentNode().id,			
			};
		var zTree = GetzTree(zTreeName);
		node.name = name;
		zTree.updateNode(node);
		RenameNode(zTree,node);
		updata_mulu(ajax_node);
		alert("修改成功！！");	
		
		
	}
	else{
		//新增
        var zTree = GetzTree(zTreeName);     
        nodes = zTree.getSelectedNodes();
//        zTree.cancelSelectedNode(nodes[0]);
        p_node = node;
//        console.log(node);
        
        
		var ajax_node ={						
						"name" : name,						
						"ismodel" : ismodel,
						"pid" : p_node.id,			
		};
		tree_name = name +'(0)';
		tree_node = {
			'name':tree_name,
			"creator" : creator,
			"creator_time":creator_time,
			"pxh":pxh,
			"modelId":modelId,
		}
//		new_node = zTree.transformTozTreeNodes(ajax_node);
		zTree.addNodes(p_node,tree_node);
		add_mulu(ajax_node);
//		父目录因为多了一个子节点，数量发生啦变化
		p_node.name =  p_node.name.replace(/ \(.*?\)/g,"") ;
		RenameNode(zTree,p_node);

		
	}
		
        $('#input1').attr("disabled",true);
        $('#input2').attr("disabled",true);
        $('#input3').attr("disabled",true);
        $('#input4').attr("disabled",true);
        $('#input5').attr("disabled",true);
        $('#input6').attr("disabled",true);
        
        $('#edite_btn').removeAttr("disabled");
        $('#new_lower').removeAttr("disabled");
        $('#delete').removeAttr("disabled");
        $('#sava_mlbj').attr("disabled",true);
        $('#cancel_mlbj').attr("disabled",true);
        treeclick_status = true;   
        var zTree = GetzTree(zTreeName);
//        RenameNode(zTree,node);	
        
}

//取消当前编辑
function cancel_change(){
	
        $('#input1').attr("disabled",true);
        $('#input2').attr("disabled",true);
        $('#input3').attr("disabled",true);
        $('#input4').attr("disabled",true);
        $('#input5').attr("disabled",true);
        $('#input6').attr("disabled",true);
        
        $('#edite_btn').removeAttr("disabled");
        $('#new_lower').removeAttr("disabled");
        $('#delete').removeAttr("disabled");
        $('#sava_mlbj').attr("disabled",true);
        $('#cancel_mlbj').attr("disabled",true);
        treeclick_status = true;

}



//ztree的设置函数
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
        beforeClick: zTreeBeforeClick,        	
        onClick: zTreeOnClick
    }
};


//目录树对象名称
var zTreeName = "tree";

//获取ztree数据的url
var ztreeUrl = "http://localhost:8080/stdglprj/stdgl/doGetAllCategory";

//构建目录树所需的数据
function getzTreeData(ztreeUrl)
{
//从服务中获取创建ztree目录所需的数据
var zNodes = new Object();
$.ajax({
    //async. 默认是true，即为异步方式，$.Ajax执行后，会继续执行ajax后面的脚本，直到服务器端返回数据后，触发$.Ajax里的success方法，这时候执行的是两个线程。
    //若要将其设置为false，则所有的请求均为同步请求，在没有返回值之前，同步请求将锁住浏览器，用户其它操作必须等待请求完成才可以执行。
	async: false,
    type: "GET",
    dataType: "text",
    url: ztreeUrl,
    timeout: 3000,
    success: function (data, status) {
        var JSONdata = JSON.parse(data);//由JSON字符串转换为JSON对象
        zNodes = JSONdata.data;
    }
});
return zNodes;
}
var zNodes = getzTreeData(ztreeUrl);
//创建目录树


//获取目录树
function GetzTree(zTreeName) {
    var catalogTree = $.fn.zTree.getZTreeObj(zTreeName);
    return catalogTree;
}

//操作zTree。该对象可理解为：类似于zTreeOperation是一个类，updateNodeName是类中的一个方法。不需要实例化，可以直接调用
var zTreeOperation = {
    updateNodeName: function (zTreeName) {
        //获取目录树对象，获取整个目录树节点数据
        var zTree = GetzTree(zTreeName),
            nodes = zTree.getNodes();

        //查看整个目录树节点数据
        //console.log("nodes");
        //console.log(nodes);
        for (var j = 0; j < nodes.length; j++) {

            //console.log("整个目录树节点长度(目前仅为1，因为根目录节点仅有一个，即仅有一棵树)");
            //console.log(nodes.length);
            //console.log(j);

            traverseTree(zTree,nodes[j]);
        }
    },
};


//递归遍历目录树
function traverseTree(zTree,node) {
    //遇到叶子节点返回
    if (!node) {
        return;
    }

    //查看遍历过程
    //console.log("正向遍历跟踪修改前的名称");
    //console.log(node);

    //将Node名字后面加上下一级子Node的个数
    RenameNode(zTree,node);

    //如果有子节点，进入下面的迭代，在迭代的过程中修改每个节点的名称，直到遇到各个叶子节点停止
    if (node.children && node.children.length > 0) {
        var i = 0;
        
        for (i = 0; i < node.children.length; i++) {
            //更细致的查看迭代过程
            //console.log("正向遍历中的嵌套循环，输出上一个父节点，此时已经被修改过名称");
            //console.log(node);
            //console.log("正向遍历中的嵌套循环，输出本次子节点");
            //console.log(node.children[i]);

            this.traverseTree(zTree,node.children[i]);
        }
    }
}

//更改目录节点名字
function RenameNode(zTree,node) {

    //获取一级子节点数目
    var num = node.children ? node.children.length : 0;

    //查看原节点名
    //console.log("origin name");
    //console.log(node.name);

    //https://blog.csdn.net/qq_16399991/article/details/55259560 replace方法
    //https://blog.csdn.net/gao_yu_long/article/details/80383625 正则表达式.*表示任意字符
    //http://jquery.cuishifeng.cn/regexp.html 正则速查表
    //http://www.php.cn/js-tutorial-384691.html 去掉头尾空白符 .replace(/(^\s*)|(\s*$)/g,"")
    //https://www.cnblogs.com/stayreal/p/3938927.html /gi 贪婪且忽略大小写

    //下面这句话的意思是，匹配到“空格(任何字符串)”这种的，替换为空。否则，保留原来字符串
    //node.name = node.name.replace(/ \(.*\)/gi, "") + " (" + num + ")";

    //更改结点名为“节点名（一级子节点数）”
    node.name = node.name + " (" + num + ")";

    //查看更改后的节点名
    //console.log("rex name");
    //console.log(node.name);

    //获取目录树对象
    var catalogTree = zTree;
    //更新节点对象
    catalogTree.updateNode(node);

    //查看更新后的节点名
    //console.log("html node111");
    //console.log(node.name);
}



function createzTree(ztreeSetting,zTreeName,zNodes){
//创建ztree目录。zTree 初始化方法，创建 zTree 必须使用此方法
$.fn.zTree.init($("#"+zTreeName), ztreeSetting, zNodes);
//fuzzySearch(zTreeName,"#search",null,true);

//更新目录树节点名称
zTreeOperation.updateNodeName(zTreeName);
}

createzTree(bzglSetting,zTreeName,zNodes);

//格式转换
var myFormatConversion;

myFormatConversion = {
    formatterDateTime: function (date) {
        var datetime = date.getFullYear()
            + "-"// "年"
            + ((date.getMonth() + 1) > 10 ? (date.getMonth() + 1) : "0"
                + (date.getMonth() + 1))
            + "-"// "月"
            + (date.getDate() < 10 ? "0" + date.getDate() : date
                .getDate())
            + " "
            + (date.getHours() < 10 ? "0" + date.getHours() : date
                .getHours())
            + ":"
            + (date.getMinutes() < 10 ? "0" + date.getMinutes() : date
                .getMinutes())
            + ":"
            + (date.getSeconds() < 10 ? "0" + date.getSeconds() : date
                .getSeconds());
        return datetime;
    },
    /**
     * 毫秒转时间格式
     */
    longMsTimeConvertToDateTime: function (time) {
        var myDate = new Date(time);
        return this.formatterDateTime(myDate);
    },
    //时间格式转换函数
    timeconv: function (data) {
        var dataConv = this.longMsTimeConvertToDateTime(data);
        // for (var myIndex= 0; myIndex < data.length; myIndex++) {
        //     // console.log(myIndex);
        //     if (data[myIndex].hasOwnProperty("state")) data[myIndex].state=this.stateConversion(data[myIndex].state);
        //     if (data[myIndex].hasOwnProperty("createtime")) data[myIndex].createtime=this.longMsTimeConvertToDateTime(data[myIndex].createtime);
        //     if (data[myIndex].hasOwnProperty("createTime")) data[myIndex].createTime=this.longMsTimeConvertToDateTime(data[myIndex].createTime);
        // }
        // console.log(data);
        return dataConv;
    },
};

//“编辑”操作，调用服务器特定接口
//？？？？input如何自适应
//$("#edite_info").on("click", ".btn.bt_edit.btn-primary.btn-xs", function () {
//    console.log("gyd...1234");
//    console.log($(this));
//    console.log("button");
//    var tds = $(this).parents("tr").children();
//});



//“保存”按钮操作
$("#dt_dwsp").on("click", ".btn.bt_save.btn-success.btn-xs", function () {

    var row = DTtable.row($(this).parents("tr"));
    console.log("row_gyd");
    console.log(row);
    var tds = $(this).parents("tr").children();
    $.each(tds, function (i, val) {
        var jqob = $(val);
        //把input变为字符串
        if (i >= 1 && !jqob.has('button').length) {
            var txt = jqob.children("input").val();
            jqob.html(txt);
            DTtable.cell(jqob).data(txt);//修改DataTables对象的数据
        }
    });

    var data = row.data();
    console.log("rowData_gyd");
    console.log(data);
    //！！！！遍历比较可以在这里实现

    $(this).replaceWith("<button type=\"button\"  style = \"margin:0px;display:inline\" class=\"btn bt_edit btn-primary btn-xs\" id=\"dt_edit\"><i class=\"fa fa-pencil\"></i> 编辑 </button>");
    //$(this).toggleClass("edit-btn");
    //$(this).toggleClass("save-btn");
});

//“删除”操作，调用服务器特定接口




var row4Del;
$("#dt_dwsp").on("click", ".btn.bt_unpass.btn-danger.btn-xs", function () {
    //https://v4-alpha.getbootstrap.com/components/modal/#modalshow (手动显示Modal)
    $('#delWarning4delSingle').modal('show');


    //var id = $('#dt_dwsp').DataTable().row($(this).parents().parents("tr")).data().id;
    // console.log(id);
    //    $.ajax({
    //        async: false,
    //        type: "GET",
    //        dataType: "json",
    //        url: "http://localhost:8080/ywsjglprj/process/unpass/" + id,
    //        timeout: 3000,
    //        success: function (data, status) {
    //            //请求执行成功后，刷新表格
    //            // UpdateDT();
    //            $('#dt_dwsp').DataTable().ajax.reload();
    //        }
    //    });

    /**
*“确定删除”按钮操作
*/
    row4Del = DTtable.row($(this).parents().parents("tr"));
    var code = row4Del.data().code;
    console.log("codeSingle_gyd");
    console.log(code);
});

$('#delWarning4delSingle').on('show.bs.modal',
    function () {
        console.log("del_single");
        //？？ESC快捷键
        $("#ModalMesgSingle").text("确定删除该条数据？");

    });

$("#confirmDelButtonSingle").on("click", function () {
    //https://datatables.net/reference/api/rows().remove() (删除选中的多行)
    var row4Deled = row4Del
        .remove()
        .draw(false);
    console.log("delconfirm_gyd");
    //table.rows('.selected').remove().draw( false );
});

//checkbox全选 https://blog.csdn.net/homelam/article/details/79863731


// https://blog.csdn.net/DreamFJ/article/details/75365123 异步click事件
function checkAllFunc() {
    console.log("checkAll_gyd");
    console.log($("#checkAll").prop("checked"));
    if ($("#checkAll").prop("checked")) {
        $("input[name='checkList']").prop("checked", true);
    } else {
        $("input[name='checkList']").prop("checked", false);
    }

}

$(document).ready(function () {

    // $("#checkAll").on("click", function () {
    //     console.log("checkAll_gyd");
    //     console.log($(this).prop("checked"));
    //    if ($(this).prop("checked")) {
    //        $("input[name='checkList']").prop("checked", true);
    //    } else {
    //        $("input[name='checkList']").prop("checked", false);
    //    }
    //});
});

//初始化网页
//$(document).ready(function () {

var dwsp_url = "./txt/GMBCI3.txt";
var dwspDT = "#dt_dwsp";

//var DTtable = creatTable_Op(dwspDT, dwsp_url);

//});

/**
*批量删除
*/
// row dt_dwsp_wrapper
//$("#dt_dwsp").on("click",".btn.bt_save_batch.btn-success.btn-xs",function(){
//console.log("gyd...");
//});
//http://www.runoob.com/bootstrap/bootstrap-modal-plugin.html(在Modal显示的时候doSomething的API)
//http://www.w3school.com.cn/tiy/t.asp?f=jquery_manipulation_text_set(修改元素text的方法)

$('#delWarning4delBatch').on('show.bs.modal',
    function () {
        console.log("del_batch");
        var str;

        //http://www.datatables.club/example/api/select_row.html (获取行数)
        var checkedNum = 0;
        $("input[name='checkList']:checked").each(function (i, o) {

            str += $(this).val();
            str += ",";
            var code = DTtable.row($(this).parents("tr")).data().code;
            console.log("checkbox_code_gyd");
            console.log(code);
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
*“确定删除”按钮操作
*/
$("#confirmDelButton").on("click", function () {
    //https://datatables.net/reference/api/rows().remove() (删除选中的多行)
    var rows = DTtable
        .rows($("input[name='checkList']:checked").parents("tr"))
        .remove()
        .draw(false);
    console.log("delconfirm_gyd");
    //table.rows('.selected').remove().draw( false );
});



//添加数据Modal展示
function addFunction() {
    $('#addData').modal('show');
}

function delFunction() {
    //data-toggle="modal" data-target="#delWarning4delBatch"
    $('#delWarning4delBatch').modal('show');
}

/**
*批量编辑
*/
function Fedit_batch() {
    console.log("gyd...123");
    console.log($(this));
    $(".btn.bt_edit.btn-primary.btn-xs").click();
    //$(this).replaceWith("<button id=\"save_batch\" type=\"button\" class=\"btn bt_save_batch btn-success btn-xs\">批量保存</button>&nbsp")
}
/**
*批量保存
*/
function Fsave_batch() {
    $(".btn.bt_save.btn-success.btn-xs").click();
    //$(this).replaceWith("<button id=\"edit_batch\" type=\"button\" class=\"btn bt_edit_batch btn-primary btn-xs\">批量编辑</button>&nbsp")
}
MDTable = $('#dt_addData').DataTable(
    {

        "searching": false, //去掉搜索框
        "bLengthChange": false,//去掉每页多少条框体 
        "language": {
            "url": "./js/DataTables.json"
        },
        "destroy": true, //Cannot reinitialise DataTable,解决重新加载表格内容问题 ，重新加载数据时，它就不会报错了
        "columnDefs": [
            { "orderable": false, "targets": "_all" }]

    }

);
/**
*Modal添加数据
*/
var rowId = 0;
$(".btn.bt_add_Modal.btn-default").on("click", function () {
    //https://datatables.net/reference/api/rows().remove() (删除选中的多行)
    //    var rows = DTtable
    //    .rows( $("input[name='checkList']:checked").parents("tr") )
    //    .remove()
    //    .draw(false);
    //    console.log("delconfirm_gyd");
    //table.rows('.selected').remove().draw( false );
    console.log("add_Modal_gyd");
    MDTable.row.add([
        "<td><input type=\"text\" name='code" + rowId + "' value=\"请输入编码\"/></td>",
        "<td><input type=\"text\"  name='name" + rowId + "' value=\"请输入中文名称\"/></td>",
        "<td><input type=\"text\"  name='enName" + rowId + "'  value=\"请输入英文名称\"/></td>",
        "<td><input type=\"text\" name='createTime" + rowId + "'  value=\"请输入创建时间\"/></td>"


    ]).draw();

    rowId++;
});

$('#addData').on('hide.bs.modal',
    function () {
        MDTable.clear().draw();
        rowId = 0;
    });

/**
*“确定添加”按钮操作
*/

$("#confirmAddButton").on("click", function () {
    //https://datatables.net/reference/api/rows().remove() (删除选中的多行)
    //var data = MDTable
    //    .rows().data()
    //    ;
    //    
    var nTrs = MDTable.rows().nodes();

    //    $("tr").each(function(i,val)
    //    {
    //        if($(this).has('input').length){
    //        console.log("tr_gyd");
    //        console.log(i);
    //        myCode = $("input[name='code']").val();
    //        myName = $(" input[ name='name' ] ").val();
    //        myEnName = $(" input[ name='enName' ] ").val();
    //        myCT = $(" input[ name='createTime' ] ").val();
    //        console.log(myCode);
    //        console.log(myName);
    //        console.log(myEnName);
    //        console.log(myCT);
    //        }
    //    }
    //    );


    //遍历行对象 
    $.each(nTrs, function (i, val) {

        console.log(i);
        console.log("textInput");
        console.log(typeof nTrs);
        console.log(nTrs)
        myCode = $("input[name='code" + i + "']").val();
        myName = $(" input[ name='name" + i + "' ] ").val();
        myEnName = $(" input[ name='enName" + i + "' ] ").val();
        myCT = $(" input[ name='createTime" + i + "' ] ").val();
        console.log(myCode);
        console.log(myName);
        console.log(myEnName);
        console.log(myCT);
        DTtable.row.add(

            {
                "<input type=\"checkbox\" id='checkAll'>": "<input type='checkbox' name='checkList'>",
                "code": myCode,
                "name": myName,
                "enName": myEnName,
                "createTime": myCT,
                "操作": "<button type=\"button\"  style = \"margin:0px;display:inline\" class=\"btn bt_edit btn-primary btn-xs\" ><i class=\"fa fa-pencil\"></i> 编辑 </button>" +
                    " <button type=\"button\" style=\"display:inline\" class=\"btn bt_unpass btn-danger btn-xs\"><i class=\"fa fa-times\"></i> 删除 </button>"

            }
        ).draw();


    });

    //   console.log("addconfirm_gyd");
    //   console.log(data);
    //table.rows('.selected').remove().draw( false );

    //   $.each(data, function(i,rowsData){
    //       console.log(i);
    //    $.each(rowsData, function(j,itemData){
    //    console.log("rowData_gyd");
    //    console.log(j);
    //    console.log(itemData);
    //    
    //    });
    //});


});








//参考1：http://www.datatables.club/example/user_share/basic_curd.html（php基本增删改查）


