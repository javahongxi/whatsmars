/**
 * Created by chenguang on 2015/5/12 0012.
 */
$(document).ready(function() {
    $("#old_password").bind('focus', function() {
        $(this).siblings('.msg_box').css({
            'display' : "inline-block"
        });
    }).bind('blur', function() {
        _check_pwd('old_password');
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

    $("#update_password_but").click(function () {
            if(_check_pwd('old_password') && _check_pwd('password') && _check_con_pwd('password', 'confirm_password')) {
                $.ajax({
                    type:"POST",
                    url:"/lender/update_password_action.jhtml",
                    data:$('#update_password_form').serialize(),
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