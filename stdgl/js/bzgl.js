//https://blog.csdn.net/qq493820798/article/details/81216090 (js文件之间相互调用)
//调用bzgl_bzck.js部分代码

//点击ztree后的响应函数
function zTreeOnClick(event, treeId, treeNode) {
    //调用后台服务接口，
    var urlStr = "./txt/GMBCI3.txt";
    var bzckDT = "#dt_dwsp";
    var DTtable = creatTable_Op(bzckDT, urlStr);
}



//创建目录树的用法
//第一步：在html中引用CreateTree.js。注意：bzgl_bzck.js文件的位置要在本文件之前，否则其中的函数可能会无法引用
//第二步：给出目录树id名称（这里id名称不加#，是因为ztree插件给出的接口需要这样的id名称）
var zTreeName = "tree";

//第三步：给出服务端获取目录树数据的接口url
var ztreeUrl = "./txt/catalog.txt";

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
            pIdKey: "parentId",
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
        onClick: zTreeOnClick
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


//创建有操作按钮的DT
function creatTable_Op(DTname, url) {
    //将创建的表格存放到tables变量中
    var DTtable = $(DTname).DataTable({
        //数据传入
        "ajax": {
            "url": url,
            "dataSrc": "data", //如果是数组，那么dataSrc为""。（控制要展示的数据为源数据中的哪个字段）
        },
        //每页显示数据条数控制
        "lengthMenu": [15, 25, 50, 75, 100],
        //允许重建DT
        "destroy": true,
        //如果列数太多，允许使用滚动条
         "scrollX": true,
        //设置列数据
        "columns": [
        //该列为选择框
            {
                "data": null,
                //列名
                "title": "<input type=\"checkbox\" id='checkAll' onclick=checkAllFunc()>",
                //列数据，这里的cellData为[Object Object]
                "createdCell": function (td, cellData, rowData, row, col) {
                    //console.log(row);
                    //console.log(col);
                    //console.log(rowData);
                    //console.log(cellData);
                    $(td).html("<input type='checkbox' name='checkList' value='" + cellData + "'>");
                }
            },
//控制DT列名与列数据字段
            {
                "data": "code",
                "title": "编码",
            },
            {
                "data": "name",
                "title": "中文名称",
            },
            {
                "data": "enName",
                "title": "英文名称",
            },
            {
                "data": "createTime",
                "title": "创建时间",
            },
            {
                "data": null,
                "title": "操作",
                // "title": "操作",
                //控制列内容为操作按钮，
                "render": function (data, type, row, meta) {
                    return data = '<button type="button"  style = "margin:0px;display:inline" class="btn bt_edit btn-primary btn-xs" ><i class="fa fa-pencil"></i> 编辑 </button>' +
                        ' <button type="button" style="display:inline" class="btn bt_unpass btn-danger btn-xs"><i class="fa fa-times"></i> 删除 </button>';
                }
            },
        ],
        //设置排序
        "order": [[1, 'asc']],
        //设置语言
        "language": {
            "url": "./js/DataTables.json"
        },
//定义DT域内的操作按钮
        "dom": "<'row'<'col-xs-4'l><'#mytool.col-xs-4'><'col-xs-4'f>r>" +
            "t" +
            "<'row'<'col-xs-6'i><'col-xs-6'p>>",
            //DT初始化，显示自定义内容，及自定义按钮要绑定的click事件
        "initComplete": function () {
            $("#mytool").append('<button id="edit_batch" type="button" class="btn bt_edit_batch btn-primary btn-xs">批量编辑</button>&nbsp');
            $("#mytool").append('<button id="save_batch" type="button" class="btn bt_save_batch btn-success btn-xs">批量保存</button>&nbsp');
            $("#mytool").append('<button id="del_batch" type="button" class="btn bt_del_batch btn-danger btn-xs">批量删除</button>&nbsp');
            $("#mytool").append('<button id="add_bt" type="button" class="btn bt_add btn-default btn-xs" >添加</button>');
            //$("#mytool").append('<button type="button" class="btn btn-default btn-sm" data-toggle="modal" data-target="#myModal">添加</button>');
            //data-toggle="modal" data-target="#addData"
            $("#add_bt").on("click", addFunction);
            $("#del_batch").on("click", delFunction);
            $("#edit_batch").on("click", Fedit_batch);
            $("#save_batch").on("click", Fsave_batch);

            //DIY关闭列排序图标
            //$("th").each(function(){
            //if($(this).has('input').length)
            //{
            //$(this).replaceWith("<th style=\"width:15px\"><input type=\"checkbox\" id='checkAll'></th>");

            //}
            //console.log("th_gyd");
            //console.log($(this).text());
            //});
        },
        //控制第一列关闭排序图标
        "columnDefs": [{
            "targets": 0,
            "orderable": false,
            "createdCell": function (td, cellData, rowData, row, col) {
                //console.log("cell_gyd");
                //console.log(row);
                //console.log(rowData);
                //关闭列名上的排序图标
                if (row == -1) {
                    $(td).removeClass('sorting')
                }
            }
        }]
    });//表格tables创建完毕
    //返回DT变量
    return DTtable;
}


//每行的“编辑”操作，调用服务器特定接口
//？？？？input如何自适应
$("#dt_dwsp").on("click", ".btn.bt_edit.btn-primary.btn-xs", function () {
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
        var txt = jqob.text();
        //console.log("txt_gyd");
        //console.log(txt);
        //要替换的html
        var put = $("<input type='text'>");
        put.val(txt);
        jqob.html(put);
    });
    //！！！！保存的时候注意：如果与原来的内容相同（且去掉空格回车），不需要发送接口请求
    //点击编辑按钮后，编辑按钮变为“保存”按钮
    //！！！！后可尝试toggle方法实现
    $(this).replaceWith("<button type=\"button\" style = \"margin:0px;display:inline\" class=\"btn bt_save btn-success btn-xs\"  id=\"dt_save\"><i class=\"fa fa-save\"></i> 保存</button>");
    //console.log(tds);
});



//“保存”按钮操作
$("#dt_dwsp").on("click", ".btn.bt_save.btn-success.btn-xs", function () {
//获取行对象
    var row = DTtable.row($(this).parents("tr"));
    //console.log("row_gyd");
    //console.log(row);
    //获取表中旧数据
    var oldData = row.data();
    var oldCode = oldData.code;
    var oldName = oldData.name;
    var oldEnName = oldData.enName;
    var oldCreateTime = oldData.createTime;
    //console.log("oldData");
    //console.log(oldData);
    //获取获取被点击按钮所在行的所有元素
    var tds = $(this).parents("tr").children();
    //遍历行元素，将修改后的数据展示在DT上
    $.each(tds, function (i, val) {
        var jqob = $(val);
        //把input的值重新显示到DT上
        if (i >= 1 && !jqob.has('button').length) {
            var txt = jqob.children("input").val();
            jqob.html(txt);
            //console.log(jqob);
            DTtable.cell(jqob).data(txt);//修改DataTables对象的数据
        }
    });

    var data = row.data();
    //console.log("rowData_gyd");
    //console.log(data);
    //比较新旧数据是否一致
    console.log("compare");
    //console.log(JSON.stringify(oldData)==JSON.stringify(data));
    //console.log(oldData==data);
    //需引入CompareObject.js
    //console.log(CompareObj(oldData, data, true));
    //console.log(oldCode);
    //console.log(data.code);
    //console.log(oldCode==data.code);
    console.log((oldCode==data.code && oldName == data.name && oldEnName == data.enName && oldCreateTime==data.createTime));
    //！！！！遍历比较与服务器发送可以在这里实现
//重新将“保存”按钮变成编辑按钮
    $(this).replaceWith("<button type=\"button\"  style = \"margin:0px;display:inline\" class=\"btn bt_edit btn-primary btn-xs\" id=\"dt_edit\"><i class=\"fa fa-pencil\"></i> 编辑 </button>");
    //$(this).toggleClass("edit-btn");
    //$(this).toggleClass("save-btn");
});

//“删除”操作，调用服务器特定接口
//该变量为后面“确定删除”按钮使用
var row4Del;
$("#dt_dwsp").on("click", ".btn.bt_unpass.btn-danger.btn-xs", function () {
    //弹出“确定删除”提示框
    //https://v4-alpha.getbootstrap.com/components/modal/#modalshow (手动显示Modal)
    $('#delWarning4delSingle').modal('show');
    //调用服务接口代码
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

//获取将要被删除行的数据内容
    row4Del = DTtable.row($(this).parents().parents("tr"));
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
    //https://datatables.net/reference/api/rows().remove() (删除选中的多行)
    var row4Deled = row4Del
        .remove()
        .draw(false);
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
var dwsp_url = "./txt/GMBCI3.txt";
var dwspDT = "#dt_dwsp";
//console.log("gyd11");
var DTtable = creatTable_Op(dwspDT, dwsp_url);

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
        $("input[name='checkList']:checked").each(function (i, o) {
            str += $(this).val();
            str += ",";
            //获取选中行的数据,供调用服务删除接口时使用
            var code = DTtable.row($(this).parents("tr")).data().code;
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
*“确定删除”按钮操作
*/
//DT删除选中行，并调用服务删除接口
$("#confirmDelButton").on("click", function () {
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
                "title": "创建时间",
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

                    $(td).removeClass('sorting')
                
            }
            
        }
        ]
    }
);

/**
*Modal添加数据
*/
var rowId = 0;
$(".btn.bt_add_Modal.btn-default").on("click", function () {
    //console.log("add_Modal_gyd");
    //添加行
    MDTable.row.add([
        "<td><input type=\"text\" name='code" + rowId + "' placeholder=\"请输入编码\" autocomplete=\"on\"></td>",
        "<td><input type=\"text\"  name='name" + rowId + "' placeholder=\"请输入中文名称\" autocomplete=\"on\"></td>",
        "<td><input type=\"text\"  name='enName" + rowId + "'  placeholder=\"请输入英文名称\" autocomplete=\"on\"></td>",
        "<td><input type=\"text\" name='createTime" + rowId + "'  placeholder=\"请输入创建时间\" autocomplete=\"on\"></td>"
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
        //console.log(nTrs)
        myCode = $("input[name='code" + i + "']").val();
        myName = $(" input[ name='name" + i + "' ] ").val();
        myEnName = $(" input[ name='enName" + i + "' ] ").val();
        myCT = $(" input[ name='createTime" + i + "' ] ").val();
        //console.log(myCode);
        //console.log(myName);
        //console.log(myEnName);
        //console.log(myCT);
        //依次添加行数据，到对应的列字段下
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
});

//下一步
//批量编辑的时候，数据量过大时，仅针对当前页面

//参考1：http://www.datatables.club/example/user_share/basic_curd.html（php基本增删改查）
