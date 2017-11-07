var nav=true;
function getRow(id){
    if($('i.rTb:visible').size()>0)
        nav=false;
    if(nav)
    {
        document.getElementById("formu:idHidden").value=id;
        document.getElementById("formu:rowAction").click();
    }
    else nav=true;
    return nav;
}
function deleteMultiple()
{
    var $rTb=$('.rTb');
    if($rTb.size()>0)
    {
        var $a=$rTb.filter('a').filter(function(){return !$(this).hasClass("rTbH");});
        if($a.size()>0)
        {
            if($a.filter(':visible').size()>0)
            {
                $a.each(function(){
                    $(this).parent().append('<label class="rTb checkbox"><span class="icons"><span class="first-icon fa fa-square-o"></span><span class="second-icon fa fa-check-square-o"></span></span><input type="checkbox" value="" data-toggle="checkbox"></label>');
                    $(this).css("display","none");
                });
                $rTb.not($a).css("display","inline");
            }
            else
            {
                $rTb.filter('label').remove();
                $a.css("display","inherit");
                $rTb.not($a).css('display','none');
            }
        }
    }
    else
        $('#deleteRecord\\:Btn').click();
}
function getMultipleIDs()
{
    if($('i.rTb:visible').size()>0)
    {
        var id="";
        $('label.rTb').filter('.checked').each(function(){
            var raw=$(this).siblings('a').attr('onclick'),
            raw=raw.split('deleteRow(');
            if(raw.length>1)
                raw=raw[1].split(",")[0];
            else
            {
                raw=raw[0].split("deleteRowVentas(");
                raw=raw.length>1?raw[1].split(",")[0]:null;
            }
            if(raw!==null)
            {
                raw=raw.split(')');
                raw=raw[0].substring(-1);
                id+=raw+",";
            }
        });
        deleteRow(id);
    }
}
function deleteRow(id)
{
    nav=false;
    var msg;
    if((id+"").indexOf(",")>-1)
        msg="¿Desea eleminar los registros seleccionados?";
    else
    {
        msg="¿Desea eliminar el registro "+id+"?";
        id+=",";
    }
    if(confirm(msg))
    {
        document.getElementById("formu:idHidden").value=id;
        document.getElementById("formu:refreshTb").click();
    }
}