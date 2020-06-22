var table1 = $("#table1").DataTable({
	// "ajax": {
	// "url": ""
	// "contentType": "application/json",
	// "type": "POST",
	// "data": function (d) {
	// return JSON.stringify(d);
	// }
	// },
	"data" : [],
	// "ajax": "test1.json",
	// "columns": [{ "data": "number", "orderable": false, "width": "100px",
	// "searchable": false }],
	"columns" : [ {
		"data" : "fieldcode",
		"title" : "字段名"
	}, {
		"data" : "enname",
		"title" : "短名"
	}, {
		"data" : "fieldname",
		"title" : "字段中文名"
	}, {
		"data" : "defination",
		"title" : "字段定义"
	}, {
		"data" : "type",
		"title" : "字段类型"
	}, {
		"data" : "maxsize",
		"title" : "最大长度"
	}, {
		"data" : "fieldrange",
		"title" : "范围"
	}, {
		"data" : "creator",
		"title" : "创建人"
	}, {
		"data" : "createtime",
		"title" : "创建时间"
	} ],
	"language" : {
		"url" : "DataTables.json"
	},
	"scrollX" : true,
	"autoWidth" : false,
	"order" : [ [ 0, 'asc' ] ]
});

// 浏览器窗口大小调整后，重绘表格
window.onresize = function() {
	table1.draw("page");
};

$("#btn-search").click(function() {
	// table1.ajax.url("../stdglprj/stdgl/fieldSearch" + data.id).load();
	var input = $("#input-search").val();
	console.log(input);
	$.ajax({
		type : 'POST',
		//url : "../stdglprj/stdgl/fieldSearch",
		url : "../stdglprj/stdgl/esSearch",
		data : JSON.stringify({
			searchcontent : input.toString()
		}),
		// async: false,
		contentType : "application/json;charset=utf-8",
		success : function(post_data, status) {
			// table1.data = post_data;
			json = JSON.parse(post_data);
//			console.log(json.data);
			// table1.ajax.reload();
			// table1.ajax.url(post_data).load();
			table1.clear();
			table1.rows.add(json.data).draw();
		},
		dataType : "text"
	});
});

// $.ajax({
// type : 'GET',
// url : "../label_api/treelist",
// // data: data,
// // async: false,
// success : function(post_data, status) {
// $("#tree").treeview({
// data : buildTree(post_data),
// // data: test,
// onNodeSelected : function(event, data) {
// console.log(data.id);
// table1.ajax.url("../label_api/resources/" + data.id).load();
// },
// // levels: 5,
// multiSelect : false,
// showTags : true
// });
// },
// dataType : "text"
// });

