function initPageInfo(currentPage,totalPage,totalRecords) {
    $("#kkpager").empty();
    kkpager.generPageHtml({
        pno: currentPage,
        mode: 'click', //设置为click模式
        //总页码
        total: totalPage,
        //总数据条数
        totalRecords: totalRecords,
        //点击页码、页码输入框跳转、以及首页、下一页等按钮都会调用click
        //适用于不刷新页面，比如ajax
        click: function (n) {
            requestBiddings(n);
            //处理完后可以手动条用selectPage进行页码选中切换
            this.selectPage(n);
            return false;
        },
        //getHref是在click模式下链接算法，一般不需要配置，默认代码如下
        getHref: function (n) {
            return '#';
        }
    },true);
}


function generateFlowList(_data,flowMap) {
    var flow_list = $("#flow_list");
    $("#message").empty();
    flow_list.empty();
    if(_data.length == 0){
        $("#message").append('<li style="border:0">很抱歉，没有符合筛选要求的记录</li>');
    }

    for (var index in _data) {
        var tr = $("<tr></tr>");
        var time = new Date(_data[index].created.time);

        var month = time.getMonth()+1;month<10? month = "0"+month:month=month;
        var day = time.getDate();day<10 ? day = "0" + day:day = day;
        var hour = time.getHours();hour < 10 ?hour = "0"+hour:hour = hour;
        var minute = time.getMinutes();minute < 10?minute = "0"+minute:minute = minute;
        var second = time.getSeconds();second < 10 ? second = "0"+second:second = second;
        var date = time.getFullYear()+"-"+month+"-"+day+" "+hour+":"+minute+":"+second;

        var td1 = $("<td></td>").text(date);
        var td2 = $('<td tyle="text-align: center"></td>').text(flowMap[_data[index].type]);
        var balance = _data[index].balanceAfter -  _data[index].balanceBefore;

        if(balance > 0){
            var td3 = $('<td></td>').text('+' + Math.round(balance*100)/100);
        }else{
            var td3 = $('<td></td>').text( Math.round(balance*100)/100);
        }

        //var td4 = $('<td></td>').text(Math.round(_data[index].frozen*100)/100);
        //var td5 = $('<td></td>').text(Math.round(_data[index].balanceAfter*100)/100);

        var statusMap = {
            "0":"失败",
            "1":"成功",
            "2":"处理中"
        };

        var td6 = $('<td></td>').text(statusMap[_data[index]["status"]]);

        var td7 = $('<td class="txt_lf"></td>').text(_data[index].description);

        tr.append(td1).append(td2).append(td3).append(td6).append(td7);
        flow_list.append(tr);
    }
}



function requestBiddings(page) {
    $("#page").val(page);
    $.ajax({
        url: "/lender/flow_list_query.jhtml",
        type: "GET",
        data: $('#search_form').serialize(),
        async: false,
        dataType: "json",
        success: function (data) {
            if (data.code = "S00000") {
                var _data = data.data.flows;
                var flowMap = data.data.flowMap;
                var page_info = data.data.queryResult;
                generateFlowList(_data,flowMap);
                initPageInfo(page, page_info.totalPage, page_info.amount);
            }
        }
    });
}


$(function(){
    initPageInfo($("#page").val(),$("#total_page").val(),$("#total_records").val());
    $("#search_btn,#charge,#withdraw").on("click",function(){
        requestBiddings(1);
    });
    $("#charge").on("click",function(){
        $("#type").val(0);
        requestBiddings(1);
    });

    $("#withdraw").on("click",function(){
        $("#type").val(1);
        requestBiddings(1);
    });

});