function generateBiddingList(data) {
    var _data = data.biddingList;
    var ageMap = data.ageMap;
    var basicMap = data.basicMap;
    var titleMap = data.titleMap;
    var salaryMap = data.salaryMap;

    var bidding_list = $("#bidding_list");
    bidding_list.empty();
    $("#no_record_msg").remove();

    if(_data.length == 0){
        bidding_list.append('<p class="norecord" id="no_record_msg">很抱歉，没有找到符合要求的记录</p>');
        $("#kkpager").hide();
        return;
    }


    for (var index in _data) {
      var li = '<li> <h3>Q_name</h3> <div class="t"><p class="c2"><span class="f40">Q_rate</span>%</p>年化利率</div>'+
        '<div class="t"><p class="c6"><span class="f40">Q_month</span>月</p>项目期限</div> <div class="borrower-info">'+
        '<p class="c2"><b class="progress-bar"><em class="progress" style="width:Q_process%; float:left"></em></b> Q_process%</p>'+
        '<p>可投：￥Q_money/总额：￥Q_total</p>  <p>截止日期：Q_invalidDate </p>   <p class="line1"></p>    <p class="c9">年龄：Q_age <i class="Q_sex"></i></p>'+
        '<p class="c9">预期岗位：Q_title</p> <p class="c9">预期月薪：Q_expected_salary元</p> </div> <a href="/bidding/detail.jhtml?bidding_id=Q_id " class="btn">Q_btn</a> </li>'

        var Q_btn ="马上投标";
        var Q_id =  _data[index].id;
        var student_id = _data[index].studentId;
        var Q_name = _data[index].studentName + '__' + _data[index].courseName ;
        var Q_rate = _data[index].rate;
        var Q_month = _data[index].monthLimit;
        var Q_process = _data[index].process;
        Q_process == 100 ? Q_btn ="去看看":Q_btn ="马上投资";

        var Q_money =_data[index].required - _data[index].obtained;
        var Q_total =  _data[index].required;
        var Q_age = ageMap[student_id];
        var Q_title = titleMap[student_id];
        var Q_expected_salary = salaryMap[student_id];
        var sex = basicMap[student_id];

        var Q_sex = "icon gender-m";sex==1?Q_sex = "icon gender-w":"icon gender-m";

        var time = new Date(_data[index].invalidDate.time);
        var month = time.getMonth()+1;month<10? month = "0"+month:month=month;
        var day = time.getDate();day<10 ? day = "0" + day:day = day;
        var hour = time.getHours();hour < 10 ?hour = "0"+hour:hour = hour;
        var minute = time.getMinutes();minute < 10?minute = "0"+minute:minute = minute;
        var second = time.getSeconds();second < 10 ? second = "0"+second:second = second;
        var Q_invalidDate = time.getFullYear()+"-"+month+"-"+day+" "+hour+":"+minute+":"+second;

        li = li.replace("Q_name",Q_name?Q_name:"");
        li = li.replace("Q_rate",Q_rate?Q_rate:"");
        li = li.replace("Q_month",Q_month?Q_month:"");
        li = li.replace(/Q_process/g,Q_process?Q_process:"0");
        li = li.replace("Q_money",Q_money?Q_money:"0.0");
        li = li.replace("Q_total",Q_total?Q_total:"");
        li = li.replace("Q_age",Q_age?Q_age:"");
        li = li.replace("Q_title",Q_title?Q_title:"");
        li = li.replace("Q_expected_salary",Q_expected_salary?Q_expected_salary:"");
        li = li.replace("Q_id",Q_id);
        li = li.replace("Q_sex",Q_sex?Q_sex:"");
        li = li.replace("Q_invalidDate",Q_invalidDate);
        li = li.replace("Q_btn",Q_btn);

        bidding_list.append(li);
        $("#kkpager").show();
    }
}

$(function(){
    $(".dd li").click(function(){
        $(this).siblings().removeClass('active');
        $(this).addClass('active')
        var item = $($(this).children()[0]);
        init_hidden_param(item.attr('name'),item.attr('value'))
    })


    $(".sort_filter_list li").click(function(){
        $(this).parent().find('li').removeClass("select");
        $(this).addClass('select');
        if ($(this).hasClass("up")){
            $(this).parent().find('li').removeClass("up down");
            $(this).removeClass('up');
            $(this).addClass('down');
        }else if ($(this).hasClass("down")){
            $(this).parent().find('li').removeClass("up down");
            $(this).removeClass('down');
            $(this).addClass('up');
        }else{
            $(this).parent().find('li').removeClass("up down");
            $(this).addClass('down');
        }

        var flag = $("#"+$(this).attr("xid")).val();
        $(".select_order").val(null)
        $("#"+$(this).attr("xid")).val(flag);
        init_select_order($(this).attr('data-order'),flag)
        requestBiddings(1);

    })



    function init_hidden_param(name,value){
        switch (name){
            case 'account_status':
                $('#status').val(value);
                break;
            case 'spread_month':
                value = value.split(',');
                $('#from_month_limit').val(value[0]);
                $('#to_month_limit').val(value[1]);
                break;
            case 'borrow_interestrate':
                value = value.split(',');
                $('#from_process').val(value[0]);
                $('#to_process').val(value[1]);
                break;
        }
        requestBiddings(1);
    }


    function init_select_order(name,flag){
        switch(name){
            case 'default':
                if(!$("#order").val() || flag == 'asc'){
                    $("#order").val('desc');
                }else{
                    $("#order").val('asc');
                }
                break;
            case 'account':
                if(!$("#amount_order").val()|| flag == 'asc'){
                    $("#amount_order").val('desc');
                }else{
                    $("#amount_order").val('asc');
                }

                break;
            case 'period':
                if(!$("#month_limit_order").val()|| flag == 'asc'){
                    $("#month_limit_order").val('desc');
                }else{
                    $("#month_limit_order").val('asc');
                }
                break;
            case 'apr':
                if(!$("#rate_order").val()|| flag == 'asc'){
                    $("#rate_order").val('desc');
                }else{
                    $("#rate_order").val('asc');
                }
                break;
            case 'scale':
                if(!$("#process_order").val()|| flag == 'asc'){
                    $("#process_order").val('desc');
                }else{
                    $("#process_order").val('asc');
                }
                break;
        }
        requestBiddings(1);
    }


})

function requestBiddings(page) {
    $("#current_page").prop("value",page);

    $.ajax({
        url: "/bidding/query.jhtml",
        type: "GET",
        data:$('#conditions').serialize(),
        async:false,
        dataType:"json",
        success: function (data) {
            if (data.code = "S00000") {
                var _data = data.data.biddingList;
                var page_info = data.data.queryResult;
                generateBiddingList(data.data);
                initPageInfo(page,page_info.totalPage,page_info.amount);
            }
        }
    });
}

var i=0;

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
            i++;
            //处理完后可以手动条用selectPage进行页码选中切换
            if(i%2 == 0){
                window.location.hash="#ST"
            } else{
                window.location.hash="#ST2"
            }


            this.selectPage(n);
            return false;
        },
        //getHref是在click模式下链接算法，一般不需要配置，默认代码如下
        getHref: function (n) {
            return '#';
        }

    },true);
}
$(function(){
    initPageInfo($("#current_page").val(),$("#total_page").val(),$("#total_records").val());
});
