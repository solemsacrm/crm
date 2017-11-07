$(document).ready(function(){
    var $headers=$(".header");
    $headers.each(function(){
        var $t=$(this),$c=$t.find(".search-input"),$i=$c.not("table").eq(0),$c=$c.not($i).eq(0).find("input"),$t=$t.siblings(":first").find("table").eq(0),n=$t.find("th").size()-1,
            $t=$t.children("tbody");
        var $tr=$t.children(),
            m=$tr.size();
        $i.after("<i class='pe-7s-search' style='cursor:pointer;height:"+$i.css("height")+";font-size:20px;border:1px solid #ddd;border-left:0;border-radius:0 25px 25px 0;position:absolute;'/>");
        $i.css("width",Number($i.css("width").replace("px",""))-Number($i.next().css("width").replace("px",""))+"px");
        $i.keyup(function(e){
            e.preventDefault();
            if(e.which===13)
                $(this).next().click();
            e.stopPropagation();
        });
        $i.next().click(function(e){
            e.preventDefault();
            var filter=$i.val().toUpperCase(),l=0,$ttr;
            if($c)
            {
                var $s=$c.filter(function(){return $(this).prop("checked")===true;});
                l=$s.size();
                if(l>0)
                    $ttr=$tr.filter(function(){var $t=$(this);if(Number($t.data("filtered"))<1)return $t;});
            }
            if(!$ttr)
                $ttr=$tr;
            for(var i=0;i<m;i++)
            {
                var $tt=$ttr.eq(i),$td=$tt.children(),dis="none";
                $tt.data("lookedup",0);
                for(var j=0;j<n;j++)
                {
                    var txt=$td.eq(j).text();
                    if(txt)
                        if(txt.toUpperCase().indexOf(filter)>-1)
                        {
                            dis="";
                            $tt.data("lookedup",1);
                            break;
                        }
                }
                $tt.css("display",dis);
            }
            e.stopPropagation();
            var $tf=$t,txt=$tf.children(":visible").size(),$tf=$t.parent().siblings().find(".foundSet");
            $tf.text(txt+"/"+$tf.text().split("/")[1]);
        });
        if($c)
        {
            $c.each(function(){
                $(this).change(function(e){
                    e.preventDefault();
                    var $s=$c.filter(function(){return $(this).prop("checked")===true;});
                    //filterByChecks($tr,$s,n,$(this).is(":checked"),$i.val());
                    filterByChecks($tr,$s,n,$i.val());
                    e.stopPropagation();
                    var $tf=$t,txt=$tf.children(":visible").size(),$tf=$t.parent().siblings().find(".foundSet");
                    $tf.text(txt+"/"+$tf.text().split("/")[1]);
                });
            });
        }
    });
});

function filterByChecks($tr,$s,n,iv)
{
    var l=$s.size();
    if(l>0)
    {
        var $ttr,m;
        if(iv!=="")
            $ttr=$tr.filter(function(){var $t=$(this);if(Number($t.data("lookedup"))>0)return $t;});
        else
            $ttr=$tr;
        $tr=$ttr.filter(function(){var $t=$(this);if(Number($t.data("filtered"))<2)return $t;});
        m=$tr.size();
        if(m>0)
            $ttr=$tr;
        else
            m=$ttr.size();
        //if(c)
        {
            /*if($ttr)
                $ttr=$ttr;
            else
                $ttr=$tr;*/
            
            for(var i=0;i<m;i++)
            {
                var $tt=$ttr.eq(i),dis=setNonVisibleByCheck($tt,n,$s,l);
                $tt.css("display",dis);
                if(dis==="")
                    $tt.data("filtered",0);
                else
                    $tt.data("filtered",1);
            }
        }
        /*else
        {
            if($ttr)
                $ttr=$ttr.not($ttr.filter(":visible"));
            else
                $ttr=$tr.not($tr.filter(":visible"));
            var m=$ttr.size();
            for(var i=0;i<m;i++)
            {
                var $tt=$ttr.eq(i),dis=setVisibleByCheck($tt,n,$s,l);
                $tt.css("display",dis);
                if(dis==="")
                    $tt.data("filtered",0);
                else
                    $tt.data("filtered",1);
            }
        }*/
    }
    else if(iv==="")$tr.each(function(){var $t=$(this);$t.data("lookedup",1);if(Number($t.data("filtered")<2)){$t.data("filtered",0);$t.css("display","");}});
    else $tr.each(function(){var $t=$(this);if(Number($t.data("lookedup"))!==0)$t.css("display","");});
}
function filterByStatus()
{
    var $t=$(event.target),d=Number($t.data("status"));
    switch(d)
    {
        case 0: d=1;$t.removeClass("fa-circle-o").addClass("fa-circle").css("color","yellow");break;
        case 1: d=2;$t.css("color","green");break;
        case 2: d=3;$t.css("color","red");break;
        case 3: d=0;$t.css("color","#9A9A9A");$t.attr("class",$t.attr("class")+"-o");break;
    }
    $t.attr("data-status",d);
    $t.data("status",d);
    displayBySatatus(d);
}
function displayBySatatus(d)
{
    var $ttr=$("#listTable").children(),$m=$(".search-input").eq(0);
    if($m.val()!=="")
        $ttr=$ttr.filter(function(){var $t=$(this);if(Number($t.data("lookedup"))>0)return $t;});
    $m=$m.parent().next().find(":checked").size();
    if($m>0)
        $ttr=$ttr.filter(function(){var $t=$(this);if(Number($t.data("filtered"))!==1)return $t;});
    if(d>0)
    {
        var n=$m;
        $m=$ttr.size();
        for(var i=0;$m>i;i++)
        {
            var $tt=$ttr.eq(i),dis=(d===Number($tt.children(":first").data("status"))?"":"none");
            $tt.css("display",dis);
            if(dis==="")
                $tt.data("filtered",0);
            else
                $tt.data("filtered",2);
        }
    }
    else
    {
        delete $m;
        $ttr.css("display","");
        $ttr.data("filtered",0);
    }
}
function setNonVisibleByCheck($tt,n,$s,l){
    var $tc=$tt.children().eq(n).find("form").find("td"),dis=new Array("");
    $tc.each(function(){
        var $ttc=$(this).children(),val=$ttc.first().val(),txt=Number($ttc.last().text());
        for(var j=0;j<l;j++)
        {
            if(val===$s.eq(j).val())
                if(txt!==1)
                    dis[0]="none";
        }
    });
    return dis[0];
}

function setVisibleByCheck($tt,n,$s,l){
    var $tc=$tt.children().eq(n).find("form").find("td"),dis=new Array("none");
    $tc.each(function(){
        var $ttc=$(this).children(),val=$ttc.first().val(),txt=Number($ttc.last().text());
        for(var j=0;j<l;j++)
        {
            if(val===$s.eq(j).val())
            {
                if(txt===1)
                    dis[0]="";
                else dis[0]="none";
            }
        }
    });
    return dis[0];
}