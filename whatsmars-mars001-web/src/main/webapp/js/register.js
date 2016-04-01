$(function(){
//check username
    $('#username').bind('focus',function(){

    }).bind('blur',function(){
        _check_username('username');
    })
//check phone
    $('#phone').bind('focus',function(){
    }).bind('blur',function(){
        _check_phone('phone');
    })
//check password strength		
    $('#password').bind('input propertychange',function(){
        _check_pwd_stg('password');
    }).bind('focus',function(){
    }).bind('blur',function(){
        _check_pwd('password');
    })
//check the confirm password
    $('#confirm_password').bind('focus',function(){
    }).bind('blur',function(){
        _check_con_pwd('password','confirm_password');
    })
//check the auth_code
    $('#auth_code').bind('focus',function(){
        $(this).siblings('.t').css({'display':'inline-block'});
    }).bind('blur',function(){
        _check_auth_code('auth_code');
    })

//check the o_pwd
    $('#o_pwd').bind('focus',function(){
        $(this).siblings('.t').css({'display':'inline-block'});
    }).bind('blur',function(){
        _check_o_pwd('o_pwd');
    });

    $("#agree").on('click',function(){
         if($('#agree').is(':checked')){
             $("#reg_but_div").show();
             $("#disable_reg_but_div").hide();
        }else{
             $("#reg_but_div").hide();
             $("#disable_reg_but_div").show();
         }

    });

    $("#reg_but").on('click', function () {
        var phone = _check_phone('phone');
        var username = _check_username('username');
        var pwd =  _check_pwd('password');
        var con_pwd = _check_con_pwd('password',"confirm_password");
        var phone_code = _check_auth_code('auth_code');

        var username_val = $("#name_flag").val();
        var phone_val = $("#phone_flag").val();

        if (username_val =='1' && phone_val=='1' && pwd  && con_pwd && phone_code ) {
            $("#reg_but_div").hide();
            $("#disable_reg_but_div").show();

            $.ajax({
                url: "register_action.jhtml",
                data: $("#reg_form").serialize(),
                dataType: "json",
                type: "POST",
                success: function (data) {
                    if(data.code == 'S00000'){
                        window.location.href = '/lender/register_success.jhtml?id='+data.data.userNew.id;
                    }else{
                        _check_error("phone_but",data.message);
                        $("#disable_reg_but_div").hide();
                        $("#reg_but_div").show();
                    }
                },
                error: function (e) {
                    console.log(e);
                }
            });
        }
    });


    $("#phone_but").bind("click",function(){
        var o = $(this);
        var phone =_check_phone("phone");
        if($("#phone_flag").val()=='1'){
            if($("#phone_flag").val()=="1"){
                var phone=$("#phone").val();
                $.ajax({
                    url:"/register/send_phone_code_action.jhtml?phone="+phone,
                    type:"post",
                    dataType:"json",
                    success:function(data){
                        if(data.code=="E00010"){
                            _check_error("phone_but",data.message);
                        }else if(data.code=="E00011"){
                            _check_error("phone_but",data.message);
                        }else if(data.code == "S00000"){
                            _check_right("phone_but","");
                            var phone =_check_phone("phone");
                            if($("#phone_flag").val()=='1'){
                                var wait=60;
                                var timeID=setInterval(function(){
                                    if(wait==-1){
                                        clearInterval(timeID);
                                        o.val("获取验证码");
                                        o.removeAttr("disabled");
                                    }
                                    else{
                                        o.val(wait+"秒后重新发送");wait--;
                                        o.attr("disabled",true);
                                    }
                                },1000);
                            }
                        }
                    }
                });
            }
        }
    });

});
// check the phone
function _check_phone(_phone) {
    var phone = $('#' + _phone)
    var phone_val = phone.val();
    var phone_reg = /^1[3|4|5|8|7][0-9]\d{8,8}$/;
    if (phone_val == '') {
        _check_error(_phone, '手机号码不能为空');
        return false;
    } else if (!phone_reg.test(phone_val)) {
        _check_error(_phone, '手机号码格式不正确');
        return false;
    }else{
        $.ajax({
            type: "GET",
            url: "/check_phone_action.jhtml?phone="+encodeURIComponent(phone_val),
            cache: false,
            async:false,
            dataType:"json",
            success: function(data) {
                if(data.code == 'E00004'){
                    _check_right(_phone,'');
                    $("#phone_flag").val('1');
                    return true;
                }else{
                    _check_error(_phone, '手机号码已被注册');
                    $("#phone_flag").val('0');
                    return false;
                }
            },
            error:function(data){
                alert('系统繁忙，请稍后再试');
            }
        });
    }
}

// check the username
function _check_username(_username) {
    var username = $('#' + _username);
    var username_val = username.val();
    var username_len = _check_len(username_val);
    var username_reg = /^[\w\u3E00-\u9FA5]+$/g;
    //alert(username_reg.test(username_val));
    if (username_val=="") {
        _check_error(_username, '用户名不能为空');
        return false;
    } else if (!username_reg.test(username_val)) {
        _check_error(_username, '请输入正确的用户名');
        return false;
    }else if (username_len < 4 || username_len > 20) {
        _check_error(_username, '请输入4-20个字符');
        return false;
    }else{
        $.ajax({
            type: "GET",
            url: "/check_name_action.jhtml?name="+encodeURIComponent(username_val),
            cache: false,
            async:false,
            dataType:"json",
            success: function(data) {
                if(data.code == 'E00004'){
                    $("#name_flag").val("1");
                    _check_right(_username,'');
                    return true;
                }else{
                    $("#name_flag").val("0");
                    _check_error(_username, '该用户名已被注册');
                    return false;
                }

            },
            error:function(data){
                alert('系统繁忙，请稍后再试');
            }
        })
    }
}

function _check_error(_name, _content) {
    var __name = $('#' + _name);
    __name.parent().removeClass('right').addClass('error');
    __name.parent().find('.t').html(_content);

    return false;
}

function _check_right(_name,_content) {
    var __name = $('#' + _name);
    __name.parent().removeClass('error').addClass('right');
    __name.parent().find('.t').html(_content);

    return true;
}
function _check_len(s) {
    var l = 0;
    var a = s.split("");
    for (var i = 0; i < a.length; i++) {
        if (a[i].charCodeAt(0) < 299) {
            l++;
        } else {
            l += 2;
        }
    }
    return l;
}
// check the password
function _check_pwd(_password) {
    var password = $('#' + _password);
    var pwd_val = password.val();
    var pwd_len = _check_len(pwd_val);
    var pwd_reg = /(^.*?[a-zA-Z]+.*?\d+.*?$)|(^.*?\d+.*?[a-zA-Z]+.*?$)|[^a-zA-Z0-9]/;
    if(pwd_val==""){
        _check_error(_password, '密码不能为空');
        return false;
    }else if(pwd_len<6 || pwd_len>20){
        _check_error(_password, '密码必须在6-20个字符之间');
        return false;
    }else if (!pwd_reg.test(pwd_val)) {
        _check_error(_password, '密码不能为纯数字或纯字母');
        return false;
    }else{
        _check_right(_password,'');
        return true;
    }
}

// check the confirm password
function _check_con_pwd(_password, _confirm_password) {
    var pwd = $('#' + _password);
    var con_pwd = $('#' + _confirm_password);
    var pwd_val = pwd.val();
    var con_pwd_val = con_pwd.val();
    if (pwd_val != con_pwd_val) {
        _check_error(_confirm_password, '两次密码不一致');
        return false;
    } else {
        if (pwd_val == '') {
            _check_error(_confirm_password, '请先输入您的登录密码');
            return false;
        } else {
            _check_right(_confirm_password,'');
            return true;
        }
    }
}
// check the strength of the password
function _check_pwd_stg(password) {
    // CharMode函数
    // 测试某个字符是属于哪一类
    function CharMode(iN) {
        if (iN >= 48 && iN <= 57) // 数字
            return 1;
        if (iN >= 65 && iN <= 90) // 大写字母
            return 2;
        if (iN >= 97 && iN <= 122) // 小写
            return 4;
        else
            return 8; // 特殊字符
    };
    // bitTotal函数
    // 计算出当前密码当中一共有多少种模式
    function bitTotal(num) {
        modes = 0;
        for (i = 0; i < 4; i++) {
            if (num & 1)
                modes++;
            num >>>= 1;
        }
        return modes;
    };
    // checkStrong函数
    // 返回密码的强度级别
    function checkStrong(sPW) {
        if (sPW.length <= 4)
            return 0; // 密码太短
        Modes = 0;
        for (i = 0; i < sPW.length; i++) {
            // 测试每一个字符的类别并统计一共有多少种模式
            Modes |= CharMode(sPW.charCodeAt(i));
        }
        return bitTotal(Modes);
    };

    var pwd = $('#' + password).val();
    var pw_bar = $('.pw_barr');
    if (pwd == null || pwd == '') {
        pw_bar.css({'width' : '0px'})
    } else {
        S_level = checkStrong(pwd);
        switch (S_level) {
            case 0 :
                pw_bar.css({
                    'width' : '0px',
                    'background' : '#FF0000'
                });
            case 1 :
                pw_bar.css({
                    'width' : '90px',
                    'background' : '#FF0000'
                });
                break;
            case 2 :
                pw_bar.css({
                    'width' : '180px',
                    'background' : '#FF9900'
                });
                break;
            case 3 :
                pw_bar.css({
                    'width' : '270px',
                    'background' : '#33CC00'
                });
                break;
            default :
                pw_bar.css({
                    'width' : '270px',
                    'background' : '#33CC00'
                });
        }
    }
    return;
}

//check auth_code;
function _check_auth_code(_auth_code){
    var auth_code = $('#' + _auth_code);
    var auth_val = auth_code.val();
    var auth_len = _check_len(auth_val);
    if (auth_val=="") {
        _check_error(_auth_code, '验证码必须填写');
        return false;
    } else {
        _check_right(_auth_code,'');
        return true;
    }
}

function _check_o_pwd(_password) {
    var password = $('#' + _password);
    var pwd_val = password.val();
    if(pwd_val==""){
        _check_error(_password, '密码不能为空');
        return false;
    }else{
        _check_right(_password,'');
        return true;
    }
}
//检查不能为汉字
function _check_qq(_qq){
    var qq = $('#' + _qq);
    var qq_val = qq.val();
    var qq_reg=/[^\u4E00-\u9FA5]/g;
    if(qq_val==""){
        _check_error(_qq, '此项不能为空');
        return false;
    }else if(!qq_reg.test(qq_val)){
        _check_error(_qq,"qq/微信不能包含汉字");
    }else{
        _check_right(_qq,'');
        return true;
    }
}
function _check_addr(_addr){
    var addr = $('#' + _addr);
    var addr_val = addr.val();
    if(addr_val==""){
        _check_error(_addr, '此项不能为空');
        return false;
    }else{
        _check_right(_addr,'');
        return true;
    }
}


