function initializeNumberFields(classes){
    var n=classes.length,cls="";
    for (var i=0;i<n;i++)
        cls+="."+classes[i];
    $(cls+".number").each(function(){
        $(this).focus();
        $(this).blur();
    });
}
function onNumberFieldFocus(id){
    var $field=document.getElementById(id);
    $field.value=keepNumericValue($field.value);
}

function keepNumericValue(val){
    var arr=val.split("."),n=arr.length;
    if(n>1)
    {
        var c=n-1;
        if(arr[n-1].replace(/0/g,'')!=='')
        {
            c--;
            val="";
            for (var i=0;i<n;i++)
                val+=arr[i].replace(/\D/g,'')+(i===c?".":"");
        }
        else
        {
            val="";
            for (var i=0;i<c;i++)
                val+=arr[i].replace(/\D/g,'');
        }
    }
    else
        val=val.replace(/\D/g,'');
    val=new Array(val);
    return val[0];
}

function addSymbolsToNumericValues(val,symbol,position,decimals)
{
    if(val!=='')
    {
        var di=val.indexOf(".");
        if(di>-1)
        {
            var arr=val.split("."),n=arr.length,c=n-2;
            val=(n>1?"":"0");
            for(var i=0;i<n;i++)
            {
                arr[i]=arr[i].replace(/\D/g,'')+(i===c?".":"");
                val+=arr[i];
            }
            n=arr[--i].length;
            if(n>decimals)
            {
                var last=Math.round(arr[i].substr(0,decimals)+"."+arr[i].substr(decimals,n)),
                    arr=val.split(".");
                val=arr[0]+"."+last;
                n=last.length;
            }
            if(n<decimals)
            {
                decimals-=n;
                for(var i=0;i<decimals;i++)
                    val+="0";
                if(val.indexOf(".")===val.length-1)
                    val=val.replace('.','');
            }
        }
        else
        {
            val=val.replace(/\D/g,'')+".";
            for(var i=0;i<decimals;i++)
                val+="0";
        }
        if(di===0)
            val="0"+val;
        if(val!=='')
        {
            var val=val.split("."),m=val.length;
            val[0]=Number(val[0])+"";
            m=val[0].length;
            if(m>3)
            {
                var temp="";
                for(var i=0;i<m;i++)
                {
                    var j=m-i;
                    temp+=(j%3===0&&i>0?',':'')+val[0].charAt(i);
                }
                val=temp+"."+val[1];
            }
            else
                val=val[0]+"."+val[1];
            if(position==='f')
                val=symbol+val;
            else if(position==='l')
                val+=symbol;
        }
    }
    val=new Array(val);
    return val[0];
}

function onNumberFieldBlur(id,symbol,position,decimals){
    var $field=document.getElementById(id);
    $field.value=addSymbolsToNumericValues($field.value,symbol,position,decimals);
}