
//字符串乱码问题：文件本身编码与整个系统编码不一致，导致系统显示中文乱码。
//解决：将字符集不一致的文件内容复制到一个没有问题的文件内即可
//通过调用后台服务，创建仅供查看的DT
function creatTable(DTname, url) {
    //将创建的DT存放到DTtable变量（对象）中
    var DTtable = $(DTname).DataTable({
        //数据传入
        "ajax": {
            "url": url,
            "dataSrc": "data", //控制传入字段。如果是数组，那么dataSrc为""
        },
        //控制每页显示数据条数
        "lengthMenu": [15, 25, 50, 75, 100],
        //允许重建
        "destroy": true,
        //当列过多时，允许启用水平滚动条
        "scrollX": true,
        //设置列数据
        "columns": [
            {
                "data": "fieldCode",
                "title": "编码",
            },
            {
                "data": "fieldName",
                "title": "中文名称",
            },
            {
                "data": "enName",
                "title": "英文名称",
            },
			{
                "data": "type",
                "title": "类型",
            },
            {
                "data": "required",
                "title": "是否必须",
            },
            {
                "data": "range",
                "title": "取值范围",
            },
            {
                "data": "maxsize",
                "title": "最大长度",
            },
            {
                "data": "maxContainCount",
                "title": "最大出现次数",
            },
            {
                "data": "defination",
                "title": "定义",
            },
            {
                "data": "creator",
                "title": "创建者",
            },
            {
                "data": "createTime",
                "title": "创建时间",
            },
            {
                "data": "comments",
                "title": "备注",
            },
        ],

        //设置语言
        "language": {
            "url": "./js/DataTables.json"
        },

    });//表格tables创建完毕
    
    //返回DT对象
    return DTtable;
}

//通过变量数据，创建仅供查看的DT
function creatTableByVar(DTname, dataset) {
    //将创建的DT存放到DTtable变量（对象）中
    var DTtable = $(DTname).DataTable({
        //数据传入
        "data": dataset,
        //控制每页显示数据条数
        "lengthMenu": [15, 25, 50, 75, 100],
        //允许重建
        "destroy": true,
        //当列过多时，允许启用水平滚动条
        "scrollX": true,
        //设置列数据
        "columns": [
            {
                "data": "fieldCode",
                "title": "编码",
            },
            {
                "data": "fieldName",
                "title": "中文名称",
            },
            {
                "data": "enName",
                "title": "英文名称",
            },
			{
                "data": "type",
                "title": "类型",
            },
            {
                "data": "required",
                "title": "是否必须",
            },
            {
                "data": "range",
                "title": "取值范围",
            },
            {
                "data": "maxsize",
                "title": "最大长度",
            },
            {
                "data": "maxContainCount",
                "title": "最大出现次数",
            },
            {
                "data": "defination",
                "title": "定义",
            },
            {
                "data": "creator",
                "title": "创建者",
            },
            {
                "data": "createTime",
                "title": "创建时间",
            },
            {
                "data": "comments",
                "title": "备注",
            },
        ],

        //设置语言
        "language": {
            "url": "./js/DataTables.json"
        },

    });//表格tables创建完毕
    
    //返回DT对象
    return DTtable;
}

//通过调用后台服务，创建有操作按钮的DT
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
                "data": "fieldCode",
                "title": "编码",
            },
            {
                "data": "fieldName",
                "title": "中文名称",
            },
            {
                "data": "enName",
                "title": "英文名称",
            },
			{
                "data": "type",
                "title": "类型",
            },
            {
                "data": "required",
                "title": "是否必须",
            },
            {
                "data": "range",
                "title": "取值范围",
            },
            {
                "data": "maxsize",
                "title": "最大长度",
            },
            {
                "data": "maxContainCount",
                "title": "最大出现次数",
            },
            {
                "data": "defination",
                "title": "定义",
            },
            {
                "data": "creator",
                "title": "创建者",
            },
            {
                "data": "createTime",
                "title": "创建时间",
            },
            {
                "data": "comments",
                "title": "备注",
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

//通过变量，创建有操作按钮的DT
function creatTableByVar_Op(DTname, dataset) {
    //将创建的表格存放到tables变量中
    var DTtable = $(DTname).DataTable({
        //数据传入
        "data": dataset,
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
                "data": "fieldCode",
                "title": "编码",
            },
            {
                "data": "fieldName",
                "title": "中文名称",
            },
            {
                "data": "enName",
                "title": "英文名称",
            },
			{
                "data": "type",
                "title": "类型",
            },
            {
                "data": "required",
                "title": "是否必须",
            },
            {
                "data": "range",
                "title": "取值范围",
            },
            {
                "data": "maxsize",
                "title": "最大长度",
            },
            {
                "data": "maxContainCount",
                "title": "最大出现次数",
            },
            {
                "data": "defination",
                "title": "定义",
            },
            {
                "data": "creator",
                "title": "创建者",
            },
            {
                "data": "createTime",
                "title": "创建时间",
            },
            {
                "data": "comments",
                "title": "备注",
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
