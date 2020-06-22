//定义时间格式转换器，将数字格式转换为日期格式
var myFormatConversion;

myFormatConversion = {
    //将Date格式数据，格式化为**年**月**日 **:**:**
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
     * 毫秒转时间格式,输入毫秒格式数据，输出日期格式数据
     */
    longMsTimeConvertToDateTime: function (time) {
        var myDate = new Date(time);
        return this.formatterDateTime(myDate);
    },
    //输入毫秒格式数据，输出日期格式数据
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
