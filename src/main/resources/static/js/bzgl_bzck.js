//点击ztree节点后，加载该节点对应的数据，通过DT展示
function zTreeOnClick(event, treeId, treeNode) {
	//查看被点击的节点信息
	//console.log("treeNode");
	//console.log(treeNode);
	
	//如果节点是字段（模型），那么点击该节点时，在右侧DT上显示字段信息
	if(treeNode.ismodel!=0)

		{
		//服务接口需要该信息
		//console.log(treeNode.modelID);
		var sentJsonData = {"id":treeNode.modelID};
		//console.log(sentJsonData);	
		//调用后台服务post接口
		$.ajax({
			url: "../stdglprj/stdgl/doGetStdSchemaObjectDetail",
			type: 'post',
			dataType: 'text',
			data: JSON.stringify(sentJsonData),
			contentType: "application/json;charset=utf-8",
			success: function (data) {
				//console.log("success");
				var jsonData = JSON.parse(data);
				//需要显示到DT上的信息
				//console.log(jsonData.data.fields);
				var bzckDT = "#bzckDT";
				var DTtable = creatTableByVar(bzckDT, jsonData.data.fields);
			}
		});		
	}
}

//第一步：在html中引用CreateTree.js。注意：bzgl_bzck.js文件的位置要在本文件之前，否则其中的函数可能会无法引用
//第二步：给出目录树id名称（这里id名称不加#，是因为ztree插件给出的接口需要这样的id名称）
var zTreeName = "tree";

//第三步：给出服务端获取目录树数据的接口url
var ztreeUrl = "../stdglprj/stdgl/doGetAllCategory";

//第四步：从服务端获取目录树数据（可视情况重写）
var zNodes = getzTreeData(ztreeUrl);

//第五步：重写Setting函数
//ztree的设置选项.http://www.treejs.cn/v3/api.php
var setting = {
    view: {
        //双击节点时，是否自动展开父节点的标识
        dblClickExpand: false,
        //设置 zTree 是否显示节点之间的连线
        showLine: true,
        //设置是否允许同时选中多个节点
        selectedMulti: false,
    },
    //使用简单 Array 格式的数据
    data: {
        key: {
            name: "name",
        },
        simpleData: {
            enable: true,
            //节点数据中保存唯一标识的属性名称
            idKey: "id",
            pIdKey: "pid",
            //用于修正根节点父节点数据，即 pIdKey 指定的属性值。[setting.data.simpleData.enable = true 时生效]
//默认值：null
            rootPId: ""
        }
    },
    callback: {
        //用于捕获单击节点之前的事件回调函数，并且根据返回值确定是否允许单击操作。默认值：null
        beforeClick: function (treeId, treeNode) {
            //var zTree = $.fn.zTree.getZTreeObj("tree");
            //展开 / 折叠 指定的节点。实现的功能为：点击节点即展开显示其子节点
            //zTree.expandNode(treeNode);
            return true;
        },
        //点击节点所对应的响应函数
        onClick: zTreeOnClick
    }
};

//第六步：重写创建目录树函数
function createzTreeBzck(ztreeSetting,zTreeName,zNodes){
//创建ztree目录。zTree 初始化方法，创建 zTree 必须使用此方法
$.fn.zTree.init($("#"+zTreeName), ztreeSetting, zNodes);
//更新目录树节点名称
zTreeOperation.updateNodeName(zTreeName);
}

//第七步：运行目录树创建函数
createzTreeBzck(setting,zTreeName,zNodes);



