/**
 * Created by chenguang on 2015/5/12 0012.
 */
$(document).ready(function() {
    $("#phone").bind('focus', function() {
        $(this).siblings('.msg_box').css({
            'display' : "inline-block"
        });
    }).bind('blur', function() {
        _check_phone('phone');
    });

    $("#validate_code").bind('focus', function() {
        $(this).siblings('.msg_box').css({
            'display' : "inline-block"
        });
    }).bind('blur', function() {
        _check_validate_code('validate_code');
    });

    $("#password").bind('focus', function() {
        $(this).siblings('.msg_box').css({
            'display' : "inline-block"
        });
    }).bind('blur', function() {
        _check_pwd('password');
    });

    $("#comfirm_password").bind('focus', function() {
        $(this).siblings('.msg_box').css({
            'display' : "inline-block"
        });
    }).bind('blur', function() {
        _check_con_pwd('password', 'comfirm_password');
    });

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

    function _check_validate_code(_code) {
        var code = $('#' + _code)
        var code_val = code.val();
        if(code_val == '') {
            _check_error(_code, "验证码不能为空");
            return false;
        }else {
            _check_right(_code, '');
            return true;
        }
    }

    function _check_phone(_phone) {
        var phone = $('#' + _phone)
        var phone_val = phone.val();
        var phone_reg = /^1[3|4|5|8|7][0-9]\d{8,8}$/;
        if (phone_val == '') {
            _check_error(_phone, '手机号码不能为空');
            return false;
        } else if (!phone_reg.test(phone_val)) {
            _check_error(_phone, '请输入正确的手机号');
            return false;
        }else {
            _check_right(_phone, '');
            return true;
        }
    }

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
                _check_error(_confirm_password, '请先输入要重设的密码');
                return false;
            } else {
                _check_right(_confirm_password,'');
                return true;
            }
        }
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

    function _check_right(_name,_content) {
        var __name = $('#' + _name);
        __name.parent().removeClass('error').addClass('right');
        __name.parent().find('.t').html(_content);

        return true;
    }

    function _check_error(_name, _content) {
        var __name = $('#' + _name);
        __name.parent().removeClass('right').addClass('error');
        __name.parent().find('.t').html(_content);

        return false;
    }

    $("#find_password_submit").click(function () {
        if(_check_phone('phone') && _check_validate_code('validate_code')) {
            $.ajax({
                type:"POST",
                url:"/find_password/check_phone_code_action.jhtml",
                data:$('#find_password_form').serialize(),
                async:false,
                dataType:"json",
                success:function(data) {
                    if (data.code == 'S00000') {
                        window.location.href=data.data._redirect_url;
                    } else {
                        $("#message_span").html(data.message);
                    }
                },
                error:function(data) {
                    alert("connect error!");
                }
            });
        }
    });

    $("#get_verty_btn").click(function() {
        var phone = $("#phone").val();
        if(phone == '') {
            _check_error('phone', '手机号不能为空');
            return false;
        }

        var o = $(this);
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
        $.ajax({
            url:"/find_password/send_phone_code_action.jhtml?phone=" + phone,
            type:"post",
            dateType:"json",
            success:function(data) {
                var data = eval( '(' + data + ')' );
                if (data.code == 'S00000') {
                } else {
                    $("#message_span").html(data.message);
                }
            },
            error:function(data) {
                alert("connect error!");
            }
        });
    });

    $("#reset_password_btn").click(function () {
        if(_check_pwd('password') && _check_con_pwd('password', 'comfirm_password')) {
            $.ajax({
                type:"POST",
                url:"/reset_password_action.jhtml",
                data:$('#reset_password_form').serialize(),
                async:false,
                dataType:"json",
                success:function(data) {
                    if (data.code == 'S00000') {
                        window.location.href=data.data._redirect_url;
                    } else {
                        $("#message_span").html(data.message);
                    }
                },
                error:function(data) {
                    alert("connect error!");
                }
            });
        }
    });
})