function initializePopovers()
{
    $('[data-toggle="popover"]').popover();
    alert($('[data-toggle="popover"]').size());
}

function initializePopover($poe,$content,$container,$trigger)
{
    if($poe!=null)
    {
        var $html=true;
        if($content==null)
        {
            $content='';
            $html=false;
        }
        if($container==null)
            $container='body';
        if($trigger==null)
            $trigger='manual';
        $poe.popover({trigger:$trigger,content:$content.html(),html:$html,container:$container});
    }
}

function setOpenPopover($poe)
{
    $pPoe=$poe;
}

function getOpenPopover()
{
    return {popover:$pPoe};
}

function disposePopover($poe)
{
    if($pPoe.is($poe))
        $pPoe=$([]);
    $poe.popover('dispose');
}

function showClickedPopover($poe)
{
    var f;
    try
    {
        f=povBUV!=null;
    }
    catch(e)
    {
        povBUV=new Array();
    }
    finally
    {
       f=$pPoe.is($poe); 
    }
    if(f)
    {
        //$pPoe=$poe;
        $poe.popover('toggle');
    }
    else
    {
        if($poe==null)
        {
            $pPoe=$(target.event);
            $poe=$pPoe.closest('[data-toggle="popover"]');
            if($poe==null)
                $poe=$pPoe.find('[data-toggle="popover"]');
        }
        $pPoe.popover('hide');
        $pPoe=$poe;
        $poe.popover('show');
    }
}

function showPopover()
{
    $pPoe.popover('show');
}

function hidePopover()
{
    $pPoe.popover('hide');
}

function loadValuesIntoPopover(values)
{
    var n=values.length-1,$poe,$po=$(".popover");
    povBUV=new Array();
    $po.find(".form-control").each(function(index){
        var i=index+1,$this=$(this);
        if(i===n)
        {
            $poe=values[i];
            if($poe.charAt($poe.length-1)==="]")
            {
                $poe=$poe.substring(1,$poe.length-1);
                if($this[0].nodeName==="SELECT")
                    $this.children().filter(function(){return this.value==$poe})[0].selected=true;
                else
                    $this[0].value=$poe;
            }
            else
            {
                $poe=$poe.substring(1,$poe.length);
                if($this[0].nodeName==="SELECT")
                    $this.children().filter(function(){return this.value==$poe})[0].selected=true;
                else
                    $this[0].value=$poe;
            }
        }
        else if(n>i)
        {
            $poe=values[i];
            $poe=$poe.substring(1,$poe.length);
            if($this[0].nodeName==="SELECT")
                $this.children().filter(function(){return this.value==$poe})[0].selected=true;
            else
                $this[0].value=$poe;
        }
        povBUV.push(this.value);
        $this=$this.closest(".date");
        if($this.size()>0)
            loadDatePickers($this,$po);//Function in every view with calendars
    });
}

function checkForChanges()
{
    var f;
    try
    {
        f=povBUV!=null
    }
    catch(e)
    {
        povBUV=new Array();
        f=false;
    }
    if(f)
    {
        f=povBUV.length;
        var $cuv=$(".popover").find(".form-control,input[type=checkbox]");
        for(var i=0;i<f;i++)
        {
            var cu=povBUV[i],$tmp=$cuv.eq(i);
            if($tmp[0].type==="checkbox")
            {
                if(cu!=$tmp[0].checked)
                    break;
            }
            else
                if(cu!=$tmp.val())
                    break;
        }
        f=new Array(i<f);
    }
    else f=new Array(false);
    return f[0];
}

function onPopoverClose()
{
    var r=new Array();
    if($pPoe.size()>0)
    {
        if(checkForChanges())
            if(confirm("¿Desea cerrar sin guardar los cambios?"))
            {
                restorePopoverBackUpValues();
                delete povBUV;
                r[0]=true;
            }
            else r[0]=false;
        else
        {
            delete povBUV;
            r[0]=true;
        }
    }
    else r[0]=true;
    return r[0];
}

function restorePopoverBackUpValues($cuv)
{
    if($cuv==null)
        $cuv=$(".popover");
    var n=povBUV.length;
    $cuv.find(".form-control,input[type=checkbox]").each(function(index){
        if(n>index)
        {
            var val=povBUV[index];
            if(this.value!=val)
            {
                var $t=$(this);
                if(this.nodeName==="SELECT")
                    $t.filter(function(){return this.value==val;})[0].selected=true;
                else
                {
                    if(this.type!=='checkbox')
                        $t[0].value=val;
                    else
                        $t[0].checked=val;
                }
                $t.trigger("onchange");
            }
        }
    });
}

function restoreCurrentPopoverValue()
{
    var v=new Array(),n=0,$po=$(".popover").find(".form-control,input[type=checkbox]");
    $po.each(function(){
        v.push(this.value);
        n++;
    });
    showPopover();
    $po=$(".popover").find(".form-control,input[type=checkbox]");
    for(var i=0;n>i;i++)
    {
        var $tmp=$po.eq(i);
        if($tmp[0].type!=="checkbox")
            $tmp[0].value=v[i];
        else
            $tmp[0].checked=v[i];
    }
}

function setPortalPopoverValidations($scrollRestores,$scrollCancels)
{
    $eventTarget=$([]);
    $(document).on("portalPopoverValidations",function(){
        var $po=$pPoe;
        if(1>$eventTarget.closest(".day,.month,.year").size())
        {
            if($eventTarget.is("td"))
            {
                if(!$eventTarget.closest('table').hasClass('inPopover'))
                    if(onPopoverClose())
                    {
                        $eventTarget=$eventTarget.closest("tr");
                        if($eventTarget.is($po))
                        {
                            hidePopover();
                            setOpenPopover($([]));
                        }
                        else showClickedPopover($eventTarget);
                    }
            }
            else 
            {
                $po=$(".popover");
                if(1>$po.find($eventTarget).size())
                {
                    if(onPopoverClose())
                    {
                        hidePopover();
                        setOpenPopover($([]));
                    }
                }
            }
        }
    });
    if($scrollRestores!=null)
        $($scrollRestores).scroll(function(){
            $.doTimeout('scroll',250,function(){
                restoreCurrentPopoverValue();
             });
        });
    if($scrollCancels!=null)
        $scrollCancels.scroll(function(){
            if(onPopoverClose())
            {
                hidePopover();
                setOpenPopover($([]));
            }
        });
}

function getBackUpValues()
{
    return {buv:povBUV};
}

function setBackUpValues(buv)
{
    povBuv=buv;
}