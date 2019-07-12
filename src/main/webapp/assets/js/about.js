// $(function() {
//   $.scrollify({
//    section:".panel",
//     sectionName:false,
//     interstitialSection:".footer"
//   });
// });
var _w,_h;
var _cid=0;
var _ww;
$(window).on("resize",resizeHandle);
function resizeHandle() {
    _w = $(window).width();
    _h = $(window).height();
    _ww = Math.max(Math.min(_w,1920),1024);
    $(".about").css({
        'left':(_w-$('.about').width())/2
    })
    $(".ui-header").css({
        'left':(_w-$('.about').width())/2
    })
    $(".pages").css({
        "height":_h,
        "width":_ww
    });
    $(".page").css({
        "height":_h
    });
    console.log('resizeHandle===',-_cid*_h);
    var _st1 = _ww/1920;//_ww*0.84375/1230;//_ww*0.84375/1380;
    var _st2 = _ww/1920;//_ww*0.69375/1110;  1100/1600*_ww/1100

    $(".pic01,.picboxs").css({
        'height':601*_ww/1920
    });
    TweenMax.to(".pages",.1,{marginTop:-_cid*_h,ease:Cubic.easeOut});
}
(function () {
    resizeHandle();
    setTimeout(function () {
        $(".app").css({"opacity":1});
        PicShow.init();
    },300)
})()



var PicShow = (function () {
    var _total=$(".page").length,_lock= false,_isreset= true;

    var init = function () {
        _total = $(".page").length;
        $.each($(".cirs-list li"),function (index,elm) {
            elm.id = index;
            $(elm).on("click",cir_clicked);
        })


        $('.pages').on('mousewheel', mousewheelHandle);
        //update();
    }
    var cir_clicked = function (e) {

        if(_lock)return;
        _lock = true;
        _cid = parseInt(e.currentTarget.id);
        console.log(e.currentTarget.id);
        update();
        // if(isEdge){
        //     gotoMao();
        // }else{update();}
    }
    var mousewheelHandle = function (event) {
        // if(Math.abs(event.deltaY)<6) return;

        if(_lock) return;
        _lock = true;
        if(event.deltaY>0){
            _cid-=1;
        }else{
            _cid+=1;
        }
        _cid = parseInt(Math.max(Math.min(_cid,_total-1),0));
        update();

    }
    var update = function () {
        $(".page").removeClass("state-enter-from-below").removeClass("state-enter-from-above").removeClass("state-entering");

        if(_cid>=1){
            $($(".page")[_cid-1]).addClass("state-enter-from-below");
            //TweenMax.to($($(".page")[_cid-1]).find(".bgimg"),.7,{z:100,ease:Cubic.easeOut});
        }
        $($(".page")[_cid]).addClass("state-entering");
        if(_cid<4){
            $($(".page")[_cid+1]).addClass("state-enter-from-above");
            //TweenMax.to($($(".page")[_cid+1]).find(".bgimg"),.7,{z:100,ease:Cubic.easeOut});
        }
        //TweenMax.to($($(".page")[_cid]).find(".bgimg"),.7,{z:50,ease:Cubic.easeOut});


        TweenMax.to(".pages",.6,{marginTop:-_cid*_h,ease:Cubic.easeOut,onComplete:function () {
            setTimeout(function () {
                _lock = false;
            },400)
        }});
        $(".cirs-list li").removeClass("on");
        $($(".cirs-list li")[_cid]).addClass("on");
    }        
    return {
        init:init
    }
})();


