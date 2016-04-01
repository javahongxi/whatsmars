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

    $("#card_id").bind('focus', function() {
        $(this).siblings('.msg_box').css({
            'display' : "inline-block"
        });
    }).bind('blur', function() {
        _check_card_id('card_id');
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
    }).bind('input propertychange',function(){
        _check_pwd_stg('password');
    });

    $("#confirm_password").bind('focus', function() {
        $(this).siblings('.msg_box').css({
            'display' : "inline-block"
        });
    }).bind('blur', function() {
        _check_con_pwd('password', 'confirm_password');
    });

    $("#old_password").bind('focus', function() {
        $(this).siblings('.msg_box').css({
            'display' : "inline-block"
        });
    }).bind('blur', function() {
        _check_pwd('old_password');
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

    function _check_card_id(_id) {
        var card_id = $('#' + _id);
        var card_id_val = card_id.val();
        var card_id_len = _check_len(card_id_val);
        var reg = /(^\d{18}$)|(^\d{17}(\d|X|x)$)/
        if(card_id_val==""){
            _check_error(_id, '身份证号不能为空');
            return false;
        }else if (!(reg.test(card_id_val))) {
            _check_error(_id, '身份证号不正确');
            return false;
        }else{
            _check_right(_id,'');
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

    //检验交易密码是否正确
    $("#find_password_submit").click(function () {
        if(_check_phone('phone') && _check_validate_code('validate_code')) {
            $.ajax({
                type:"POST",
                url:"/find_deal_password/check_phone_code_action.jhtml",
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

    //获取交易密码验证码
    $("#get_verty_btn").click(function() {
        var phone = $("#phone").val();
        if(phone == '') {
            _check_error('phone', '手机号不能为空');
            return false;
        }

        var cardId = $("#card_id").val();
        if(cardId == '') {
            _check_error('card_id', '身份证号不能为空');
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
            url:"/find_deal_password/send_phone_code_action.jhtml?phone=" + phone + "&card_id=" + cardId,
            type:"post",
            dateType:"json",
            success:function(data) {
                var data = eval('(' + data + ')');
                if (data.code == 'S00000') {
                    $("#message_span").html('');
                } else {
                    $("#message_span").html(data.message);
                }
            },
            error:function(data) {
                alert("connect error!");
            }
        });
    });

    //重置交易密码
    $("#reset_password_btn").click(function () {
        if(_check_pwd('password') && _check_con_pwd('password', 'comfirm_password')) {
            $.ajax({
                type:"POST",
                url:"/reset_deal_password_action.jhtml",
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

    //添加交易密码
    $("#deal_password_btn").click(function () {
        $.ajax({
            type:"POST",
            url:"/lender/add_deal_password_action.jhtml",
            data:$('#deal_password_form').serialize(),
            async:false,
            dataType:"json",
            success: function (data) {
                if(data.code == "S00000") {
                    location.href = data.data._redirect_url;
                } else {
                    if(data.data._redirect_url != null) {
                        _alert(data.message, url);
                    }
                    alert(data.message);
                }
            },
            error: function (e) {
                alert("connect error!");
            }
        });
    });

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
            pw_bar.css({
                'width' : '0px'
            })
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

    //修改交易密码
    $("#update_deal_password_btn").click(function () {
        $.ajax({
            type:"POST",
            url:"/lender/update_deal_password_action.jhtml",
            data:$('#deal_password_form').serialize(),
            async:false,
            dataType:"json",
            success: function (data) {
                if(data.code == "S00000") {
                    location.href = data.data._redirect_url;
                } else {
                    $("#message_span").html(data.message);
                }
            },
            error: function (e) {
                alert("connect error!");
            }
        });
    });
})