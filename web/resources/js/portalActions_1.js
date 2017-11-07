$(function(){
    $(".portal").each(function(){
        limitTdWidth($(this).children(":last"));
    });
});

function assingSettings($tr)
{
    intializeValueLists($tr);
    initializeNumberFields(new Array("inProtal")); //NumberFields.js file function
}

function intializeValueLists($tr)
{
    $tr.find(".inPortalSS").each(function(){
        var $t=$(this);
        loadselect($t,0);//library="js" name="valuelists.js"
        //$t.removeClass("inPortalSS");
    });
}

function addPortalRow(tbodyID,fieldsNumber,settings)
{
    if(fieldsNumber>0)
    {
        var $t=$("#"+tbodyID),
            prepend=getRowContents(tbodyID,fieldsNumber,settings,true);
            prepend=prepend[0]+"<td style='padding:0'><center style='padding:0'><i class='inPortal pe-7s-check' onclick='commitChanges(\""+tbodyID+"\","+prepend[1]+",\""+$t.parent().closest(".card").children(":first").find("i").data("msg")+"\")'/><i class='inPortal pe-7s-close-circle' onclick='cancelChanges()'/></center></td></tr>";
        $t.prepend(prepend);
        prepend=$t.children(":first");
        addColumnEvents($t,prepend);
        assingSettings(prepend);
    }
}

function getRowContents(tbodyID,fieldsNumber,settings,newRow){
    var $t=$("#"+tbodyID),select=new Array(),disabled= new Array(),numbered=new Array(),textarea=new Array(),sL=settings.length,uN=0;
    for(var i=0;i<sL;i++)
    {
        var si=settings[i].toLowerCase();
        if(++i>=sL) break;
        var temp=settings[i].split(","),
            tempL=temp.length;
        if(si==='select')
            for(var j=0;j<tempL;j++)
            {
                var tt=temp[j].split("_");
                select.push(Number(tt[0]));
                if(tt.length>1)
                {
                    select.push(tt[1]);
                    uN++;
                }
                delete tt;
            }
        else if(si==='disabled')
            for(var j=0;j<tempL;j++)
                disabled.push(temp[j]);
        else if(si==='number')
            for(var j=0;j<tempL;j++)
                numbered.push(temp[j]);
        else if(si==='textarea')
            for (var j=0;j<tempL;j++)
                textarea.push(temp[j]);
    }
    var $tP=$t.parent(),tPn,rC;
    if(newRow===true)
    {
        tPn=Number($tP.data("rows"))+1;
        rC="newPR";
        $tP.data("rows",tPn);
        $tP.attr("data-rows",tPn);
    }
    else
    {
        tPn=newRow;
        rC="modPR";
    }
    var outer="<tr data-row='"+tPn+"' class='editing "+rC+"'>",sL=select.length,dL=disabled.length,nL=numbered.length,tL=textarea.length;
    $t=$t.children().not(".newPR,.modPR").first().children();
    for(var i=0;i<fieldsNumber;i++)
    {
        outer+="<td style=\""+$t.eq(i).attr("style")+"\">";
        var j=0;
        while(j<sL-uN)
        {
            if(select[j]-1===i)
            {
                tbodyID="\""+tbodyID+"\"";
                outer+="<select class='inPortalSS";
                if(i<sL)
                {
                    if(select[j++]==='unique')
                        outer+=" unique' data-status='"+(rC==="newPR"?"0":"1");
                }
                outer+="' onfocus='loadValueList("+tbodyID+","+tPn+","+i+")'";
                for(var l=0;l<dL;l++)
                    if(disabled[l]-1===i)
                        outer+=" disabled='disabled'";
                outer+="></select>";
            }
            else if(textarea[j]-1===i)
            {
                outer+="<textarea class='form-control'";
                for(var l=0;l<dL;l++)
                    if(disabled[l]-1===i)
                        outer+=" disabled='disabled'";
                outer+="></textarea>";
            }
            else if(j<sL)
            {
                outer+="<input type='text' class='form-control inPortal";
                var k=0,tmpNum=null;
                for(k=0;k<nL;k++)
                {
                    tmpNum=numbered[k].split("_");
                    if(Number(tmpNum[0])-1===i)
                    {
                        outer+=" number";break;
                    }
                    else tmpNum=null;
                }
                outer+="'";
                if(tmpNum!==null)
                {
                    tmpNum[0]=tmpNum.length;
                    if(tmpNum[(Number(tmpNum[0])-1)].toString().toLowerCase()==="r")
                            outer+=" style=\"text-align:right\"";
                    if(tmpNum[0]>2)
                    {
                        var idNF="inPortalNF_"+tPn+"_"+i,sym=(tmpNum[1]!=null?tmpNum[1]:""),posi=(tmpNum[2]!=null?tmpNum[2]:""),decs=(tmpNum[3]!=null?tmpNum[3]:"");
                        outer+=" id="+idNF+" onfocus='onNumberFieldFocus(\""+idNF+"\")'";
                        outer+=" onblur='onNumberFieldBlur(\""+idNF+"\",\""+sym+"\",\""+posi+"\","+decs+")'";
                    }
                }
                for(var l=0;l<dL;l++)
                    if(disabled[l]-1===i)
                        outer+=" disabled='disabled'";
                outer+="/>";
            }
            j++;
        }
        if(j<1)
        {
            outer+="<input type='text' class='form-control";
            var k=0,tmpNum=null;
                for(k=0;k<nL;k++)
                {
                    tmpNum=numbered[k].split("_");
                    if(Number(tmpNum[0])-1===i)
                    {
                        outer+=" number";break;
                    }
                    else tmpNum=null;
                }
                outer+="'";
                if(tmpNum!==null)
                {
                    tmpNum[0]=tmpNum.length;
                    if(tmpNum[(Number(tmpNum[0])-1)].toString().toLowerCase()==="r")
                            outer+=" style=\"text-align:right\"";
                    if(tmpNum[0]>2)
                    {
                        var idNF="inPortalNF_"+tPn+"_"+i,sym=(tmpNum[1]!=null?tmpNum[1]:""),posi=(tmpNum[2]!=null?tmpNum[2]:""),decs=(tmpNum[3]!=null?tmpNum[3]:"");
                        outer+=" id="+idNF+" onfocus='onNumberFieldFocus(\""+idNF+"\")'";
                        outer+=" onblur='onNumberFieldBlur(\""+idNF+"\",\""+sym+"\",\""+posi+"\","+decs+")'";
                    }
                }
            for(var l=0;l<dL;l++)
                if(disabled[l]-1===i)
                    outer+=" disabled='disabled'";
            outer+="/>";
        }
        outer+="</td>";
    }
    var a=[outer,tPn];
    return a;
}

function loadValueList(tbodyID,tr,position)
{
    
    var $tb=$("#"+tbodyID),
        $so=getValueListSource($tb,position);
    if($so.children().size()>0)
        displayValueList($tb,tr,position,$so);
    else
    {
        var onf=$so.attr('onfocus');
        $so.attr('onfocus',onf.replace('trNumber',tr+','+position));
        $so.focus();
    }
    event.stopPropagation();
}

function getValueListSource($tb,position)
{
    var $so=$tb.parent(),//looks for the portal ($tb is the tbody)
        $so=$so.siblings('form').children('select'),//looks for source selects
        n=$so.size()-1;
    if(n<position)
        $so=$so.eq(0);
    else
        $so=$so.eq(position);
    var a=[$so];
    return a[0];
}

function cancelChanges()
{
    var $tr=$(event.target).eq(0).closest('tr'),$tb=$tr.children(":first").data("id");
    if($tb && $tb!=='')
    {
        var $tb=$tr.parent(),$cmb=$tb.parent().siblings("form").children("input[type=submit]").first();
        assingTemporalIdToRow($tr,$tr.data("row"),$tb.attr("id"),$cmb);
        $tr.attr("data-cancel",true);
        replaceRowType(false,$tr.attr("id"),$cmb.attr("id"));
    }
    else if(confirm("¿Desea cancelar la creación de esta fila?"))
        $tr.remove();
}

function deleteFromPortal()
{
    if(confirm('¿Desea eliminar esta fila?'))
    {
        var $tr=$(event.target).closest('tr'),
            id=$tr.children(":first").data("id");
        if(id!=null && id!=='')
        {
            var $cmb=$tr.parent().parent().siblings("form").eq(0);
            $cmb.children("#"+$cmb.attr("id")+"\\:commit").val(id);
            $cmb.children("input[type=submit]:last").click();
            $tr.remove();
            logCardActions();
        }
    }
}

function logCardActions()
{
    var $lc=$("#logCard");
    if($lc.size()>0)
    {
        $lc=$lc.children().filter(function(){return $(this).attr("id")==="logCard:btnIT";});
        $lc.val("@@cameTroughPortal@@");
        $lc.next().click();
    }
}

function commitChanges(tbodyID,tr,msg)
{
    var $tb=$("#"+tbodyID),
        $tr=findTR($tb,tr);
        $tr.data("cancel",null);
        $tr.removeAttr("data-cancel");
    var $vals=$tr.find("input, select"),
        $cm=getValueListSource($tb,0).siblings("#"+tbodyID+"F\\:commit").eq(0),
        trn=$tr.data("row"),
        c=$tr.children().first().data("id"),
        cmV="["+trn+"],["+((c&&c!=='')?c:"")+"],";
        c=0;
    var flag=true;
    $vals.each(function(){
        if(flag)
        {
            var $t=$(this),v;
            if($t.prop("nodeName")==='SELECT')
                v=$t.next().text();
            else if($vals.eq(c+1).prop("nodeName")==='SELECT')
            {
                var $nt=$($vals.eq(c+1)),tv=$t.val();
                v=$nt.children(':selected').val();
                if(v==='' || !v)
                {
                    if(tv==='' || !tv)
                    {
                        alert("Por favor seleccione "+msg+".");
                        flag=false;
                    }
                    else
                        v=tv;
                }
                else
                    $t.val(v);
            }
            else
                v=$t.val();
            c++;
            if($t.hasClass("number"))
                v=keepNumericValue(v);//Function Fom NumberFields.js
            cmV+="["+v+"],";
        }
    });
    delete c;
    if(flag)
    {
        var cmvL=cmV.length,$cmb=$cm.siblings("#"+tbodyID+"F\\:btn");
        if(cmV.substr(cmvL-1)===",")
            cmV=cmV.substr(0,(cmvL-1));
        $cm.val(cmV);
        assingTemporalIdToRow($tr,trn,tbodyID,$cmb);
        if($cmb.attr("onmousedown")!=null)
            $cmb.mousedown();
        else
            $cmb.click();
    }
}

function assingTemporalIdToRow($tr,trn,tbodyID,$cmb)
{
    var trID=tbodyID+"_Row_"+trn;
    $tr.attr("id",trID);
    $cmb.attr("onclick",$cmb.attr("onclick").replace("'@tableRowNumber'","'"+trID+"','"+$cmb.attr("id")+"'"));
}

var buvL;
function backupRowValues($fields,rowID){
    var buv=new Array();
    $fields.each(function(){
        var $t=$(this),v=$t.val();
        if($t.hasClass("number"))
            v=keepNumericValue(v); //NumberFields.js file function
        buv.push(v);
    });
    if(!buvL)
        buvL=new Array();
    buvL.push({row:rowID,buv:buv});
}

function deleteBackupValues(rowID)
{
    var n=buvL.length,i,t;
    for(i=0;i<n;i++)
    {
        t=buvL[i].row;
        if(t===rowID)
            break;
    }
    if(t && t!=='')
        buvL.splice(i,1);
}

function compareValues($fields,rowID)
{
    var buv,n=buvL.length;
    for(var i=0;i<n;i++){
        var t=buvL[i];
        if(t.row===rowID)
        {
            buv=t;
            break;
        }
    }
    n=buv.buv.length;
    var r=new Array(true);
    for(var i=0;i<n;i++)
    {
        var $t=$fields.eq(i),v=$t.val();
        if(v===null)
            v='';
        if($t.hasClass("number"))
            v=keepNumericValue(v); //NumberFields.js file function
        r[0]=(v===buv.buv[i]);
    }
    return r[0];
}

function restoreBackUpValues($fields,rowID)
{
    var buv,n=buvL.length;
    for(var i=0;i<n;i++){
        var t=buvL[i];
        if(t.row===rowID)
        {
            buv=t;
            break;
        }
    }
    n=buv.buv.length;
    var tr=new Array("");
    for(var i=0;i<n;i++)
    {
        var $t=$fields.eq(i),v=buv.buv[i],txt="";
        if(v===null)
            v='';
        $t.val(v);
        if($t.hasClass("number"))
        {
            $t.focus();
            $t.blur();
        }
        if($t.prop("nodeName")==="SELECT")
        {
            txt=$($t).next("div.form-control").text();
            if(txt==='' || !txt)
                txt=$t.val();
        }
        else
            txt=$t.val();
        tr[0]+="<td>"+txt+"</td>";
    }
    return tr[0];
}

function editRow()
{
    var $tr=$(event.target).eq(0).closest('tr');
    replaceRowType(true,$tr);
}

function replaceRowType(editableRow,target,submit)
{
    var $tr,tr="";
    if(editableRow)
    {
        $tr=target;
        var $trP=$tr.parent(),dataID=$tr.children(":first").data("id"),msg;
        tr=$trP.closest(".card").children().first().find("i");
        msg=tr.data("msg");
        if(tr.attr("onfocus")!=null)
            tr=tr.attr("onfocus");
        else
            tr=tr.attr("onclick");
        tr=tr.split("(");
        tr=tr[1]+"("+tr[2];
        tr=tr.substr(0,tr.length-2);
        tr=tr.split("new Array");
        tr[0]=tr[0].split(",");
        tr[0][0]=tr[0][0].replace(/['"]/g,"");
        var tr2=getRowContents(tr[0][0],tr[0][1],eval("new Array"+tr[1]),$tr.data("row"),false);
        tr=tr2[0]+"<td style='padding:0'><center style='padding:0'><i class='inPortal pe-7s-check' onclick=\"commitChanges('"+tr[0][0]+"',"+tr2[1]+",'"+msg+"')\"/><i class='inPortal pe-7s-close-circle' onclick='cancelChanges()'/></center></td></tr>";
        var uniqueValues=new Array();
        $tr.children().each(function(index){
            if($(this).hasClass("unique"))
            {
                uniqueValues.push(index);
                uniqueValues.push($(this).data("value"));
            }
        });
        $tr.replaceWith(tr);
        var $trc=$tr.children(),tr=$trc.size(),trcv=new Array();
        for (var i=0;i<tr;i++)
        {
            $tr=$trc.eq(i);
            trcv.push($tr.text());
            $tr=$tr.data("value");
            if($tr>0)
                trcv.push($tr);
        }
        $trc=0;
        var $tr=findTR($trP,tr2[1]),$trF=$tr.find('input[type=text], select'),nUV=uniqueValues.length;
        delete tr,tr2;
        intializeValueLists($tr);
        $trF.each(function(){
            var $t=$(this);
            if($t.prop("nodeName")==='SELECT')
            {
                $t.val(trcv[$trc-1]);
                $t.next().text(trcv[$trc]);
            }
            else
                $t.val(trcv[$trc]);
            if($trc<1)
            {
                var $tp=$($t).closest('td');
                $tp.data("id",dataID);
                $tp.attr("data-id",dataID);
            }
            for(var iuv=0;iuv<nUV;iuv++)
                if(uniqueValues[iuv]===$trc)
                {
                    var tempUV=uniqueValues[++iuv];
                    $t.attr("data-value",tempUV);
                    $t.data("value",tempUV);
                    $t.attr("data-status",'1');
                    $t.data("status",'1');
                    delete tempUV;
                }
            $trc++;
        });
        addColumnEvents($trP,$tr);
        initializeNumberFields(new Array("inPortal")); //NumberFields.js file function
        backupRowValues($trF,dataID);
    }
    else
    {
        $tr=$("#"+target.replace(/['"]/g,"")).eq(0);
        var dataID=$tr.children(":first").data("id"),f=true;
        var $trF=$tr.find('input[type=text], select'),i=0;
        if($tr.data("cancel"))
        {
            f=compareValues($trF,dataID);
            if(!f)
                if(confirm("¿Quiere deshacer los cambios realizados?"))
                {
                    $tr.data("cancel",null);
                    $tr.removeAttr("data-cancel");
                }
                else
                    return;
        }
        var newPR=$tr.hasClass("newPR");
        if(f)
            $trF.each(function(){
                var $t=$(this),txt="",val="";
                if($t.prop("nodeName")==='SELECT')
                {
                    //get the text in the styledSelect DIV
                    txt=$t.next().text();
                    val=$t.children(":selected").val();
                    if(val==null || val==="")
                        val=$t.data("value");
                }
                else if($trF.eq(i+1).prop("nodeName")==='SELECT')
                {
                    txt=$trF.eq(i+1).children(":selected").val();
                    if(txt==='' || txt==null)
                        txt=$t.val();
                    val=txt;
                }
                else
                    txt=$t.val();
                if($t.hasClass("number"))
                {
                    f="class=\"number ";
                    if(txt===""||txt==null)
                    {
                        $t.val(0);
                        $t.focus();
                        $t.blur();
                        txt=$t.val();
                        if(txt===""||txt==null)
                            txt=$t.text();
                    }
                }
                else
                    f="class=\"";
                tr+="<td "+f+($t.hasClass("unique")?"unique":"")+"\""+((val!=="")?(" data-value=\""+val+"\""):"")+(" style=\"text-align:"+$t.css("text-align")+"\"")+">"+txt+"</td>";
                i++;
            });
        else if(!newPR)
            tr+=restoreBackUpValues($trF,dataID);
        if(newPR)
        {
            dataID=submit.split(":");
            i=dataID.length-1;
            newPR="";
            for(var j=0;j<i;j++)
                newPR+=dataID[j]+"\\:";
            dataID=$("#"+(newPR+"commit").replace(/['"]/g,"")).val();
        }
        else
            deleteBackupValues(dataID);
        tr+="<td style='padding:0'><center style='padding:0'><i class='inPortal pe-7s-note' onclick='editRow()'/><i class='inPortal pe-7s-trash' onclick='deleteFromPortal()'/></center></td>";
        $tr.removeAttr("id class");
        $tr.children().remove();
        $tr.append(tr);
        $tr.data("cancel",null);
        $tr.removeAttr("data-cancel");
        var $tb=$tr.parent();
        limitTdWidth($tb,$tr);
        $tr=$tr.children(":first");
        $tr.attr("data-id",dataID);
        $tr.data("id",dataID);
        delete $tr;
        removeTemporalRowId(submit,target);
        sortPortal($tb,$tb.parent().data("sort"));
        logCardActions();
    }
    
}

function removeTemporalRowId(submit,target)
{
    if(submit && submit!=='')
    {
        if(target.indexOf("'")<0)
            target="'"+target+"'";
        var $submit=$("#"+(submit.replace(/[:]/g,"\\:")).replace(/['"]/g,"")).eq(0);
        if(submit.indexOf("'")<0)
            submit="'"+submit+"'";
        $submit.attr("onclick",$submit.attr("onclick").replace(target+","+submit,"'@tableRowNumber'"));
    }
}

function loadSourceSelect(d,tr,pos,src)
{
    if(d.status==='success')
    {
        var $so=$("#"+src),
            $tb=$so.parent().siblings('table').children("tbody").eq(0);
            //Will check if label contains other columns data from the DataBase
            $so.children().each(function(){
                var $t=$(this),arr=$t.text().split(",["),n=arr.length;
                $t.text(arr[0].replace("]",""));
                if(n>1)
                {
                    var txt="";
                    for(var j=1;j<n; j++)
                        txt+="["+arr[j]+",";
                    $t.attr("data-other",txt.slice(0,-1));
                }
            });
        displayValueList($tb,tr,pos,$so);
    }
    event.stopPropagation();
}

function findTR($tb,tr)
{
    var $tr=$tb.children().filter(function(){
        return $(this).data("row")===tr;
    });
    var a=[$tr];
    return a[0];
}

function displayValueList($tb,tr,pos,$so)
{
    var $tr=findTR($tb,tr),
        $id=$tr.find('.form-control');
        $tb=$id.eq(pos).children('select').eq(0);//Gets the in-portal select
    $tr=$tr.parent().find(".unique").not($tr.find(".unique"));//filters already selected values in other TR's
    $id=$id.eq(pos-1).val();
    if($so.hasClass("standard"))
        $tb.children().remove();
    $so=$so.children().clone();//source select set
    var vals=getUniqueValues($tr,$so);
    $so.each(function(){
        $(this).attr('disabled',false);
    });
    $so.each(function(){
            var n=vals.length,$t=$(this);
            for(var i=0;i<n;i++)
                if(Number(vals[i])===Number($t.val()))
                    $t.attr('disabled',true);
        });
    $tb.append($so);
    var $sel=$tb.children().filter(function(){
                 return $(this).val() === $id; 
             });
    $sel.attr('selected',true);
    var $tbn=$tb.next(),tbSS=$tbn.attr('id'),tbID=tbSS+'src';
    $tb.attr('id',tbID);
    afterAjaxSelect(tbID,tbSS.replace('SS',''));
    delete tbID;
    $tb.val($sel.val());
    $tb.blur();
    $tb=$tb.next();
    $tb.text($sel.text());
    $tb.next().children().filter(function(){
        return $(this).attr('rel') === $sel.val(); 
    }).addClass('selected');
    event.stopPropagation();
}

function getUniqueValues($set,$source){
    var vals=new Array();
    $set.each(function(){
        var $t=$(this),val;
        if($t.prop("nodeName")==='TD')
            val=$t.data("value");
        else val=$t.children(":selected").val();
        if(val==='' || !val)
            val=$t.data("value");
        $source.each(function(){
            if(Number($(this).val())===Number(val))
                vals.push(val);
        });
    });
    return vals;
}

function sortPortal($tb,sortOrder)
{
    $apnd=$([]);
    var $rs=$tb.children().not(".newPR").not(".modPR");
    sortOrder=sortOrder.split(",");
    sortLevels($rs,sortOrder,0);
    $rs.remove();
    $tb.append($apnd);
    delete $apnd;
}

function sortLevels($rows,sortOrder,soIndex)
{
    var n=$rows.size(),pos=sortOrder[soIndex].split('_'),indexes=new Array();
    soIndex++;
    $rows=sortRows($rows,pos[0],pos[1]);
    if(n>2)
    {
        for(var i=1;i<n;i++)
            if($rows.eq(i-1).children().eq(pos[0]).text()!==$rows.eq(i).children().eq(pos[0]).text())
                indexes.push(i);
        var m=indexes.length;
        if(m>0 && m+1!==n)
        {
            var arr=new Array(),i=0,c=0,n2=0,$r;
            while(i<m)
            {
                $r=$rows.clone().slice(c,indexes[i]);
                arr.push($r);
                n2+=$r.size();
                c=indexes[i++];
            }
            if(n2!==n)
            {
                delete indexes,n2,i;
                $r=$rows.clone().slice(c,n);
                arr.push($r);
                delete $r,n,c;
                m=arr.length;
            }
            else
            {
                delete $r,indexes,n,n2,c,i;
                m=arr.length;
            }
            for(var i=0;i<m;i++)
                sortLevels(arr[i],sortOrder,soIndex);
        }
        else
            $.merge($apnd,$rows);
    }
    else
        $.merge($apnd,$rows);
}

function addNewRowsToSet($src,$tgt)
{
    var n=$src.size();
    for (var i=0;i<n;i++)
        $tgt.push($src.eq(i));
}

function sortRows($rows,pos,type)
{
    type=type.toUpperCase();
    if(pos<0)
        $rows.sort(function(a,b)
        {
            var kA = a.dataset.row;
            var kB = b.dataset.row;
            if(type==='D')//DESC
                return (kA < kB)?1:0;
            else//ASC
                return (kA > kB)?1:0;
        });
    else
        $rows.sort(function(a,b)
        {
            var kA = $('td',a).eq(pos);
            var kB = $('td',b).eq(pos);
            if(kA.hasClass("number"))
                kA=Number(keepNumericValue(kA.text()));
            else
                kA=kA.text();
            if(kB.hasClass("number"))
                kB=Number(keepNumericValue(kB.text()));
            else
                kB=kB.text();
            if(type==='D')//DESC
                return (kA < kB)?1:0;
            else//ASC
                return (kA > kB)?1:0;
        });
    return $rows;
}

function addColumnEvents($tbody,$tr)
{
    var $th=$tbody.prev().find('th'),l=$th.size()-1,c=0;
    //l is the number of th minus the one for the 'tick' and 'x' icons;
    if($tr==null)
        $tr=$tbody.children();
    $tr.children().each(function(){
        if(c<l)
        {
            var $t=$(this),oc=$th.eq(c).attr("onchange");
            if(oc!=null)
                $t.children(".form-control").attr("onchange",oc);
            c++;
        }
        else c=0;
    });
}

function limitTdWidth($tbody,$tr)
{
    var l=$tbody.prev().find('th').size()-1,c=0;
    //l is the number of th minus the one for the 'tick' and 'x' icons;
    if($tr==null)
        $tr=$tbody.children();
    $tr.children().each(function(){
        if(c<l)
        {
            var $t=$(this),t=$t.text();
            $t.text("");
            $t.css("max-width",$t.width());
            $t.css("overflow","hidden");
            $t.text(t);
            c++;
        }
        else c=0;
    });
}

function goToDetailFromPortal()
{
    var et=event.target,idv=document.getElementById("formu:idHidden"),id;
    if(et.nodeName==="TD")
    {
        et=et.parentElement;
        id=et.children[0].dataset.id;
    }
    else if(et.nodeName==="I")
        id=null;
    idv.value=id;
    $(idv.nextSibling).click();
}