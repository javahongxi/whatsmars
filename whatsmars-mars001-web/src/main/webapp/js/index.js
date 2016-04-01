$(function(){
	//轮播
	$.fn.loopBannerSlider = function(options) {
    var defaults = {            
            slides: 'dl',
            autoPlay: 1, 
            autoPlayTime : 5000,
            slidespeed: 500,
            creatSmallBtn : true,
            creatBigBtn : true
    };
    this.each(function() {
        var obj = this; //操作的div
        var o = $.extend(defaults, options);
        var m = 0;  //图片父元素的left值
        var t = 1;  //当前在第几张
        var lists = $(o.slides,obj);
        var list = lists.children();
        var s = list.length;  //图片个数
        var w = $(document).outerWidth(true) + 17;  //图片宽度
        $(".banner .banner_list dl dd").css("width",w);
        //$(".banner .banner_list dl dd img").css("width",w);
        var u = false;
        var sliderIntervalID = null;
        lists.css({width:(s*w)}); //初始化幻灯片div的宽度
        list.each(function(index){ //初始化每个图片的位置
            $(this).css({position:'absolute',left:index*w,display:'block',background:'url(images/banner_0'+index+'.png) no-repeat center 0'});
        });
        $(obj).on('click','.next',function(){
            if(u===false) {
                animate('next',true);
                if(o.autoPlay){clearInterval(sliderIntervalID);}
            } 
        }).on('click','.prev',function(){
            if(u===false) {
                animate('prev',true);
                if(o.autoPlay){clearInterval(sliderIntervalID);}
            } 
        }).on('click' , 'ul li' , function(){
            var index = $('ul li' , obj).index(this)+1;
            u = true;
            if(index > t){
                t = t+index-t;
                m = (-(t*w-w));
                current(t);
                if(t==s){
                    //最后一张left为-的一张图片的宽度
                    list.eq(s-1).css({left:s*w-w}); 
                    lists.animate({left:m},o.slidespeed,function(){
                        list.eq(0).css({left:s*w});
                        u = false; 
                    });
                }else{
                    lists.animate({left:m}, o.slidespeed,function(){
                        u = false;
                    }); 
                }
            }
            if(index < t){
                t = t-(t-index);
                m = (-(t*w-w));
                current(t);
                if(t==1){
                    list.eq(0).css({left:0});
                    lists.animate({left: 0},o.slidespeed,function(){
                        list.eq(s-1).css({left:(-w)});
                    });
                    u = false;
                }else{
                    lists.animate({left: m}, o.slidespeed,function(){
                        u = false;
                    });
                }
            }
        }).hover(function(){
            $('.lrbtn',obj).stop(1,1).animate({opacity : 0.7},o.slidespeed);
            $('.prev',obj).stop(1,1).animate({left : 0 },o.slidespeed);
            $('.next',obj).stop(1,1).animate({right : 0 },o.slidespeed);
            if(o.autoPlay){clearInterval(sliderIntervalID);}
        },function(){
            $('.lrbtn',obj).animate({opacity : 0.15},o.slidespeed);
            $('.prev',obj).animate({left : -$('.lrbtn',obj).outerWidth()},o.slidespeed);
            $('.next',obj).animate({right : -$('.lrbtn',obj).outerWidth()},o.slidespeed);
            if (o.autoPlay) {
                sliderIntervalID = setInterval(function(){
                    if(u===false) {animate('next',true);}
                }, o.autoPlayTime);
            }
        }).trigger('mouseleave');
        if(o.creatSmallBtn){ //创建小按钮
            var Btns = '<ul>';
            for (var i = 0; i < s; i++) {
                Btns += '<li></li>';
            };
            Btns += '</ul>';
            $(obj).append(Btns);
            if(o.creatBigBtn){
                var Btns = '<a href="javascript:void(0);" class="lrbtn prev">&lt;</a><a href="javascript:void(0)" class="lrbtn next">&gt;</a>';
                $(obj).append(Btns);
                $('.lrbtn',obj).css({
                    opacity : 0.15,
                    top : ($(obj).outerHeight() - $('.lrbtn',obj).outerHeight())/2
                });
            }
            $('ul li' , obj).removeClass('active').first().addClass('active');
        }
        function current(t) { //高亮小按钮
            if(t==s+1){t=1;}
            if(t==0){t=s;}
            if(o.creatSmallBtn){
                $('ul li' , obj).removeClass('active').eq(t-1).addClass('active');
            }
        };  
        function animate(dir,clicked){  
            u = true;   
            switch(dir){
                case 'next':
                    t = t+1;
                    m = (-(t*w-w));
                    current(t);
                    lists.animate({left:m}, o.slidespeed,function(){
                        if (t===s+1) { //大于最后一张的时候
                            t = 1;  //跳到第一张
                            lists.css({left:0},function(){lists.animate({left:m})});
                            //第一张left为0     
                            list.eq(0).css({left: 0}); 
                            //最后一张left为-的一张图片的宽度
                            list.eq(s-1).css({left:-w});    
                            
                        }
                        //最后一张的时候
                        if (t==s) list.eq(0).css({left:(s*w)}); 
                        //倒数第二张的时候
                        if (t==s-1) list.eq(t).css({left:s*w-w});
                        u = false;
                    });                 
                    break; 
                case 'prev':
                    t = t-1;
                    m = (-(t*w-w));
                    current(t);
                    lists.animate({left: m}, o.slidespeed,function(){
                        if (t==0) {
                            t = s;
                            list.eq(s-1).css({left:(s*w-w)});
                            lists.css({left: -(s*w-w)});
                            list.eq(0).css({left:(s*w)});
                        }
                        if (t==2) list.eq(0).css({left:0});
                        if (t==1) list.eq(s-1).css({left:-w});
                        u = false;
                    });
                    break;
                default:
                    break;
                }                   
            };
        });
    };
	//
	$.fn.infiniteCarousel = function () {
    return this.each(function () {
        var $wrapper = $('> div', this).css('overflow', 'hidden'),
            $slider = $wrapper.find('> ul'),
            $items = $slider.find('> li'),
            $single = $items.filter(':first'),
            singleWidth = $single.outerWidth()+125, 
            visible = Math.ceil($wrapper.innerWidth() / singleWidth),
            currentPage = 1,
            pages = Math.ceil($items.length / visible);            
        if (($items.length % visible) != 0) {
            $items = $slider.find('> li');
        }

        $items.filter(':first').before($items.slice(- visible).clone().addClass('cloned'));
        $items.filter(':last').after($items.slice(0, visible).clone().addClass('cloned'));
        $items = $slider.find('> li');
        //$wrapper.scrollLeft(singleWidth * visible);
        
        function gotoPage(page) {
            var dir = page < currentPage ? -1 : 1,
                n = Math.abs(currentPage - page),
                left = singleWidth * dir * visible * n;
            
            $wrapper.filter(':not(:animated)').animate({
                scrollLeft : '+=' + left
            }, 500, function () {
                if (page == 0) {
                    $wrapper.scrollLeft(singleWidth * visible * pages);
                    page = pages;
                } else if (page > pages) {
                    $wrapper.scrollLeft(singleWidth * visible);
                    page = 1;
                } 
                currentPage = page;
            });                
            return false;
        }
        $wrapper.after('<a class="list-arrow back">&lt;</a><a class="list-arrow forward">&gt;</a>');
        
        $('a.back', this).click(function () {
            return gotoPage(currentPage - 1);                
        });
        
        $('a.forward', this).click(function () {
            return gotoPage(currentPage + 1);
        });
        
        $(this).bind('goto', function (event, page) {
            gotoPage(page);
        });
    });  
};

   //菜单
   $.fn.lavaLamp = function(o) {
		o = $.extend({
			fx: "",
			speed: 500,
			click: function() {}
		}, o || {});
		return this.each(function() {
			var b = $(this),
				noop = function() {},
				$back = $('<li class="back"><div class="left"></div></li>').appendTo(b),
				$li = $("li", this),
				curr = $("li.cur", this)[0] || $($li[0]).addClass("cur")[0];
			$li.not(".back").hover(function() {
				move(this)
			}, noop);
			$(this).hover(noop, function() {
				move(curr)
			});
			$li.click(function(e) {
				setCurr(this);
				return o.click.apply(this, [e, this])
			});
			setCurr(curr);

			function setCurr(a) {
				$back.css({
					"left": a.offsetLeft + "px",
					"width": a.offsetWidth + "px"
				});
				curr = a;
				$(a).addClass("cur").siblings().removeClass('cur')
			};

			function move(a) {
				$back.each(function() {
					$.dequeue(this, "fx")
				}).css({
					width: a.offsetWidth,
					left: a.offsetLeft
				}, o.speed, o.fx)
			}
		})
	}
   
   //导航
	$("#menu").lavaLamp({
        fx: "", 
        speed: 700,
        click: function(event, menuItem) {
            
        }
    });
    $('#loopBannerSlider').loopBannerSlider();
    $('.borrower-wraper').infiniteCarousel();
    $('.top-l a').mouseover(function (event) {
        var target = $('.' + this.id + '-pop');
        var cur = $(event.currentTarget);
        target.show().css({
            top: cur.offset().top+40,
            left: cur.offset().left - target.outerWidth() / 2 + cur.outerWidth() / 2
        });
    }).mouseout(function (event) {
        var target = $('.' + this.id + '-pop');
        target.hide();
    });
    $('.borrower-list li').live({
        mouseenter:function(){
            $(this).addClass('active').siblings().addClass('rel');
        },
        mouseleave:function(){
            $(this).removeClass('active').siblings().removeClass('rel');
        }
    });
    $('.help').on('click','dt',function(){
        if (!$(this).parent().hasClass("cur")){
            $(this).next().show().parent().addClass('cur');
        }else{
            $(this).next().hide().parent().removeClass('cur');
        }
    });
    $('#password').bind('input propertychange',function(){
        unit._check_pwd_stg('password');
    });
    $("#getcode").bind("click",function(){
        unit._getcode(60,$(this));
    });

	 $(".hint").mouseover(function(){
                $(".prompt").show();
            });
        $(".hint").mouseout(function(){
                $(".prompt").hide();
            });
            $(".hint1").mouseover(function(){
                $(".prompt1").show();
            });
        $(".hint1").mouseout(function(){
                $(".prompt1").hide();
            });
            
        $(".table_w1000 tr:odd td").css("background-color","#f2f2f2");
        
        $("#example2-1,#example2-2,#example2-3,#example2-4").fancybox();
        
        $("#money").click(function(){
			$(this).attr("value","");
			$(this).css("text-align","left");
		})
        
        $(".mm a").click(function(){
			$(this).addClass("cur").siblings().removeClass("cur");
		})
});
var unit = {
    _check_pwd_stg: function (password) {
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
        var pw_bar = $('.pw_bar');
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
                        'width' : '61px',
                        'background' : '#ffcf10'
                    });
                    break;
                case 2 :
                    pw_bar.css({
                        'width' : '124px',
                        'background' : '#f88e63'
                    });
                    break;
                case 3 :
                    pw_bar.css({
                        'width' : '189px',
                        'background' : '#dc4820'
                    });
                    break;
                default :
                    pw_bar.css({
                        'width' : '189px',
                        'background' : '#33CC00'
                    });
            }
        }
        return;
    },
    _getcode: function (num,obj){
        var wait= num;
        var timeID=setInterval(function(){
            if(wait==-1){
                clearInterval(timeID);
                obj.val("获取验证码");
                obj.removeAttr("disabled");
            }else{
                obj.val(wait+"秒后重新发送");wait--;
                obj.attr("disabled",true);
            }
        },1000);
    }
}
