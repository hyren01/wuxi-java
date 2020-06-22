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
                "data": "code",
                "title": "序号",
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
                "title": "短名",
            },
            {
                "data": "createTime",
                "title": "定义",
            },
            {
                "data": "createTime",
                "title": "数据类型",
            },
            {
                "data": "createTime",
                "title": "域",
            },
            {
                "data": "createTime",
                "title": "是否必传",
            },
            {
                "data": "createTime",
                "title": "最大出现次数",
            },
            {
                "data": "createTime",
                "title": "备注",
            },
            {
                "data": "createTime",
                "title": "长度",
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
                "data": "comments",
                "title": "序号",
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
                "data": "maxsize",
                "title": "短名",
            },
            {
                "data": "maxsize",
                "title": "定义",
            },
            {
                "data": "maxsize",
                "title": "数据类型",
            },
            {
                "data": "maxsize",
                "title": "域",
            },
            {
                "data": "maxsize",
                "title": "是否必传",
            },
            {
                "data": "maxsize",
                "title": "最大出现次数",
            },
            {
                "data": "maxsize",
                "title": "备注beizhu",
            },
            {
                "data": "maxsize",
                "title": "长度",
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