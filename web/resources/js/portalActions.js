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
        settings=$t.parent().closest(".card").children(":first").find("i").data("msg");
        if(settings==null)
            settings=$t.parent().closest("table").parent().siblings().find("i").data("msg");
        if(prepend[2]==null)
            prepend[2]=$t.prev().find('th').eq(fieldsNumber).attr("style");
        prepend=prepend[0]+"<td style='"+prepend[2]+"'><center style='padding:0'><i class='inPortal pe-7s-check' onclick='commitChanges(\""+tbodyID+"\","+prepend[1]+((settings!=null)?(",\""+settings+"\""):"")+")'/><i class='inPortal pe-7s-close-circle' onclick='cancelChanges()'/></center></td></tr>";
        $t.prepend(prepend);
        prepend=$t.children(":first");
        assingSettings(prepend);
        addColumnEvents($t,prepend);
        prepend=prepend.find(".date");
        if(prepend.size()>0)
            loadDatePickers(prepend,prepend.closest(".card"));
    }
}

function getRowContents(tbodyID,fieldsNumber,settings,newRow){
    var $t=$("#"+tbodyID),sL=settings.length,$tP=$t.parent(),tPn,rC;
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
    var outer="<tr data-row='"+tPn+"' class='editing "+rC+"'";
    $t=$t.children().not(".newPR,.modPR").first();
    if($t.size()>0)
    {
        fieldsNumber=$t.attr("style");
        if(fieldsNumber!=null)
        {
            fieldsNumber=fieldsNumber.split(";;");
            if(fieldsNumber.length>1)
                outer+=" data-extraStyle='"+fieldsNumber[1]+"'";
            fieldsNumber=fieldsNumber[0];
            outer+=" style=\""+fieldsNumber+"\"";
        }
        newRow=$t.children(":last").attr("style");
        $t=$t.children();
        fieldsNumber=$t.size()-1;
    }
    outer+=">";
    var fields=new Array(fieldsNumber);
    for (var i=0;i<sL;i++)
    {
        var type=settings[i],conf=settings[++i].split(",");
        for(var j=0;j<conf.length;j++)
        {
            var tmp=(conf[j]+"").split("_"),
                current=fields[Number(tmp[0]-1)];
            if(current==null)
                current="";
            else
                current+="_";
            current+=type;
            for(var k=1;k<tmp.length;k++)
                current+="_"+tmp[k];
            fields[Number(tmp[0])-1]=current;
        }
    }
    current=$("#"+tbodyID).prev().find('th');
    tbodyID="\""+tbodyID+"\"";
    sL=0;
    for(var i=0;i<fieldsNumber;i++)
    {
        outer+="<td";
        var tmp=$t.eq(i).attr("style");
        if(tmp!=null)
        {
            /*tmp=tmp.split("margin");
            for(var j=1;j<tmp.length;j++)
                tmp[0]+="padding"+tmp[j];
            outer+=" style=\""+tmp[0]+"\"";*/
            outer+=" style=\""+tmp+"\"";
        }
        else
        {
            tmp=current.eq(i).attr("style");
            if(tmp!=null)
                outer+=" style=\""+tmp+"\"";
        }
        outer+=">";
        tmp=fields[i];
        if(tmp!=null && tmp!="")
        {
            if(tmp.indexOf("select")>-1)
            {
                outer+="<select class='inPortalSS";
                if(tmp.indexOf("unique")>-1)
                    outer+=" unique' data-status='"+(rC==="newPR"?"0":"1");
                outer+="' style=\"max-width:0;\" onfocus='loadValueList("+tbodyID+","+tPn+","+sL+")'";
                if(tmp.indexOf("disabled")>-1)
                    outer+=" disabled='disabled'";
                outer+="></select>";
                sL++;
            }
            else if(tmp.indexOf("textarea")>-1)
            {
                outer+="<textarea class='form-control'";
                tmp=tmp.split("_");
                var meta=$t.eq(i).attr("data-inFieldStyle");
                if(meta!=null)
                    outer+=" style=\""+meta+"\"";
                for(var j=0;j<tmp.length;j++)
                {
                    meta=tmp[j];
                    if(meta==="disabled")
                        outer+=" disabled='disabled'";
                    else if(meta!=="textarea")
                        outer+=" rows=\""+meta+"\"";
                }
                delete meta;
                outer+="></textarea>";
            }
            else
            {
                outer+="<input type='text'";
                if(tmp.indexOf("disabled")>-1)
                    outer+=" disabled='disabled'";
                outer+=" class=\"form-control inPortal";
                if(tmp.indexOf("calendar")>-1)
                    outer+=" date\"";
                else
                {
                    tmp=tmp.split("number_");
                    if(tmp.length>1)
                    {
                        outer+=" number\"";
                        tmp=tmp[1].split("_");
                        if(tmp[tmp.length-1].toString().toLowerCase()==="r")
                                outer+=" style=\"text-align:right\"";
                        if(tmp.length>1)
                        {
                            var idNF="inPortalNF_"+tPn+"_"+i,sym=(tmp[0]!=null?tmp[0]:""),posi=(tmp[1]!=null?tmp[1]:""),decs=(tmp[2]!=null?tmp[2]:"");
                            outer+=" id="+idNF+" onfocus='onNumberFieldFocus(\""+idNF+"\")'";
                            outer+=" onblur='onNumberFieldBlur(\""+idNF+"\",\""+sym+"\",\""+posi+"\","+decs+")'";
                        }
                    }
                    else outer+="\"";
                }
                tmp=$t.eq(i).attr("data-inFieldStyle");
                if(tmp!=null)
                    outer+=" style=\""+tmp+"\"";
                outer+="/>";
            }
        }
        else
        {
            outer+="<input type='text' class='form-control inPortal'";
            tmp=$t.eq(i).attr("data-inFieldStyle");
            if(tmp!=null)
                outer+=" style=\""+tmp+"\"";
            outer+="/>";
        }
        outer+="</td>";
    }
    var a=[outer,tPn,newRow];
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
    var $so=$tb.parent();//looks for the portal ($tb is the tbody)
    //looks for source selects
    if($so.siblings('form').prop("nodeName")!=null)
        $so=$so.siblings('form').children("select");
    else
        $so=$so.siblings("select");
    var n=$so.size()-1;
    if(n<position)
        $so=$so.eq(0);
    else
        $so=$so.eq(position);
    var a=[$so];
    return a[0];
}

function cancelChanges()
{
    var $tr=$(event.target).closest('tr'),$tb=$tr.children(":first").data("id");
    if($tr.prop("nodeName")!=null && $tb!=null)
    {
        var $tb=$tr.parent(),$cmb=$tb.parent().siblings("form").children("input[type=submit]").first();
        if($cmb.prop("nodeName")==null)
            $cmb=$tb.parent().siblings("input[type=submit]").first();
        assingTemporalIdToRow($tr,$tr.data("row"),$tb.attr("id"),$cmb);
        $tr.attr("data-cancel",true);
        replaceRowType(false,$tr.attr("id"),$cmb.attr("id"));
    }
    else if(confirm("¿Desea cancelar la creación de esta fila?"))
        $tr.remove();
}

function deleteFromPortal(id)
{
    if(confirm(id==null?'¿Desea eliminar esta fila?':id))
    {
        var $tr=$(event.target).closest('tr');
            id=$tr.children(":first").data("id");
        if(id!=null && id!=='')
        {
            id=Number(id)>0?id:id+"_"+$tr.data("row");
            var $cmb=$tr.parent().parent().siblings("form").eq(0);
            if($cmb.prop("nodeName")!=null)
            {
                var bid=($cmb.attr("id").replace(/:/g,"\\:"));
                $cmb.children("#"+bid+"\\:commit").val(id);
                $cmb.children("#"+bid+"\\:btnD").click();
            }
            else
            {
                $cmb=$tr.parent().parent().siblings("input");
                $cmb.filter(function(){
                    var arr=this.id.split(":");
                    if(arr[arr.length-1]==="commit")
                        return this;
                }).val(id);
                $cmb.filter(function(){
                    var arr=this.id.split(":");
                    if(arr[arr.length-1]==="btnD")
                        return this;
                }).click();
            }
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
    var $vals=$tr.find("input, select, textarea"),
        $tb=getValueListSource($tb,0),c=$tb.attr("id").split(":"),tbodyID="#";
        for (var i=0;i<c.length-1;i++)
            tbodyID+=c[i]+"\\:";
    var $cm=$tb.siblings(tbodyID+"commit").eq(0),
        trn=$tr.data("row");
        c=$tr.children().first().data("id");
    var cmV="["+trn+"],["+((c!=null&&c!=='')?c:"")+"],";
        c=0;
    var flag=true;
    $vals.each(function(){
        var $t=$(this),v;
        if(flag && $t.is(":visible"))
        {
            if($t.prop("nodeName")==='SELECT')
            {
                v=$t.children(':selected').val();
                if(v==='' || v==null)
                {
                    v=$t.closest("td").data("value");
                    if(v==='' || v==null)
                    {
                        var tv=$t.next().text();
                        if((tv==='' || tv==null)&&msg!=null)
                        {
                            alert("Por favor seleccione "+msg+".");
                            flag=false;
                        }
                        else
                            v=tv;
                    }
                }
                else
                    $t.val(v);
            }
            /*else if($vals.eq(c+1).prop("nodeName")==='SELECT')
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
            }*/
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
        var cmvL=cmV.length,$cmb=$cm.siblings(tbodyID+"btn");
        if(cmV.substr(cmvL-1)===",")
            cmV=cmV.substr(0,(cmvL-1));
        $cm.val(cmV);
        assingTemporalIdToRow($tr,trn,tbodyID.substring(1,tbodyID.length-2).replace(/[\\]/g,""),$cmb);
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
function backupRowValues($fields,rowID,$tbody){
    var buv=new Array();
    $fields.filter(function(){return $(this).is(":visible");}).each(function(){
        var $t=$(this),v=$t.val();
        if($t.hasClass("number"))
            v=keepNumericValue(v); //NumberFields.js file function
        /*else if(v==null)
            if($t.is("select") && !$t.hasClass("unique"))
                v=$t.next().text();*/
        else if($t.is("select"))
        {
            if(v!=null)
                v={value:v,text:$t.next().text()};
            else
                v={value:$t.closest('td').data('value'),text:$t.next().text()};
            
        }
        buv.push(v);
    });
    if(!buvL)
        buvL=new Array({portal:$tbody,rows:new Array({row:rowID,buv:buv})});
    else
        setPortalFromBackUp($tbody,{row:rowID,buv:buv});
}

function getPortalFromBackUp($tbody)
{
    var tb,n=buvL.length;
    for(var i=0;i<n;i++)
    {
        tb=buvL[i];
        if(tb.portal===$tbody)
            break;
    }
    return new Array(i,tb);
}

function setPortalFromBackUp($tbody,arr)
{
    var n=buvL.length,i,t;
    for(i=0;i<n;i++)
    {
        t=buvL[i];
        if(t.portal===$tbody)
        {
            t.rows.push(arr);
            break;
        }
    }
    if(i>=n)
        buvL.push({portal:$tbody,rows:new Array(arr)});
}

function deleteBackupValues(rowID,$tbody)
{
    var n=buvL.length,i,t,j,tt;
    t=getPortalFromBackUp($tbody);
    i=t[0];
    t=t[1].rows;
    n=t.length;
    for(j=0;j<n;j++)
    {
        tt=t[j];
        if(tt.row===rowID)
            break;
    }
    if(tt && tt!=='')
        buvL[i].rows.splice(j,1);
}

function compareValues($fields,rowID,$tbody)
{
    var buv=getPortalFromBackUp($tbody)[1].rows,n=buv.length;
    for(var i=0;i<n;i++){
        var t=buv[i];
        if(t.row===rowID)
        {
            buv=t;
            break;
        }
    }
    buv=buv.buv;
    n=buv.length;
    $fields=$fields.filter(function(){return $(this).is(":visible");});
    var r=new Array(true);
    for(var i=0;i<n;i++)
    {
        var $t=$fields.eq(i),v=$t.val();
        if(v==null)
        {
            if($t.is("select") && !$t.hasClass("unique"))
            {
                v=$t.closest('td').data('value');
                v=(v!=null)?v:$t.next().text();
            }
            else
                v='';
        }
        if($t.hasClass("number"))
            v=keepNumericValue(v); //NumberFields.js file function
        var tmp=buv[i];
        if($.isPlainObject(tmp))
            r[0]=(v===tmp.value);
        else
            r[0]=(v===tmp);
        if(!r[0])
            break;
    }
    return r[0];
}

function restoreBackUpValues($fields,rowID,$tbody)
{
    var buv=getPortalFromBackUp($tbody),j=buv[0],buv=buv[1].rows,n=buv.length;
    for(var i=0;i<n;i++)
    {
        var t=buv[i];
        if(t.row===rowID)
        {
            buv=t;
            break;
        }
    }
    //n=buv.buv.length;
    n=$fields.size();
    var tr=new Array("");
    //$fields=$fields.filter(function(){return $(this).is(":visible");});
    for(var i=0;i<n;i++)
    {
        var $t=$fields.eq(i),txt="",v;
        v=buv.buv[i];
        v=(v==null)?"":v;
        if($.isPlainObject(v))
        {
            if(v.value==null)
                v.value="";
            $t.val(v.value);
        }
        else $t.val(v);
        if($t.prop("nodeName")==="SELECT")
        {
            txt=v.text;
            v=v.value;
            if(txt==='' || !txt)
                txt=$t.val();
        }
        else
        {
            txt=v;
            v="";
        }
        var c;
        if($t.hasClass("number"))
        {
            c="class=\"number ";
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
        else if($t.hasClass("date"))
            c="class=\"calendar ";
        else
            c="class=\"";
        var stl=$t.closest("td").attr("style");
        //alert($t.prop("nodeName")+": "+stl);
        tr[0]+="<td "+c+($t.hasClass("unique")?"unique":"")+"\""+((v!=="")?(" data-value=\""+v+"\""):"")+(" style=\"text-align:"+$t.css("text-align")+";"+(stl!=null?stl:"")+($t.is(":visible")?"":" display:none;")+"\"")+">"+txt+"</td>";
        //tr[0]+="<td"+($t.is(":visible")?"":" style=\"display:none;\"")+">"+txt+"</td>";
    }
    return tr[0];
}

function editRow($tr)
{
    var $tr=$tr==null?$(event.target).eq(0).closest('tr'):$tr;
    replaceRowType(true,$tr);
}

function replaceRowType(editableRow,target,submit)
{
    var $tr,tr="";
    if(editableRow)
    {
        $tr=target;
        var $trP=$tr.parent(),dataID=$tr.children(":first").data("id"),msg;
        if($trP.prev().attr("data-trPopover")!=null)
        {
            hidePopover();
            setOpenPopover($([]));
        }
        if($trP.children(":last").attr("data-row")==="-1")
            tr=$trP.closest(".content,.table-responsive,.table-full-width").siblings().first().find("i");
        else
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
        var tr2=getRowContents(tr[0][0],tr[0][1],eval("new Array"+tr[1]),$tr.data("row"),false),
            uniqueValues=tr2[2],$trc;
        tr=tr2[0]+"<td style='"+((uniqueValues!=null && uniqueValues!=="")?uniqueValues:"padding:0")+"'><center style='padding:0'><i class='inPortal pe-7s-check' onclick=\"commitChanges('"+tr[0][0]+"',"+tr2[1]+(msg!=null?(",'"+msg+"'"):"")+")\"/><i class='inPortal pe-7s-close-circle' onclick='cancelChanges()'/></center></td></tr>";
        uniqueValues=new Array();
        msg=new Array();
        $tr.children().each(function(index){
            $trc=$(this);
            if($trc.hasClass("unique"))
                uniqueValues.push(index);
            $trc=$trc.data('value');
            if($trc>0)
            {
                msg.push(index);
                msg.push($trc);
            }
        });
        $tr.replaceWith(tr);
        $trc=$tr.children();
        tr=$trc.size();
        var trcv=new Array();
        for(var i=0;i<tr;i++)
            trcv.push($trc.eq(i).text());
        $trc=0;
        $tr=findTR($trP,tr2[1]);
        var $trF=$tr.find('input[type=text], select, textarea'),
            nUV=uniqueValues.length;
        tr2=msg.length;
        tr=$tr.find(".date");
        if(tr.size()>0)
            loadDatePickers(tr,$trP.closest(".card"));
        $trF.each(function(){
            var i,$tp;
            tr=$(this);
            if(tr.prop("nodeName")==='SELECT')
            {
                $tp=new Array(trcv[$trc]);
                for(i=0;i<tr2;i++)
                {
                    if(msg[i]==$trc)
                    {
                        $tp.push(msg[i+1]);
                        break;
                    }
                }
                if($tp[1]!=null)
                {
                    tr.closest('td').attr('data-value',$tp[1]);
                    tr.append("<option selected='selected' disabled='disabled' value='"+$tp[1]+"'>"+$tp[0]+"</option>");
                }
                else
                    tr.append("<option selected='selected' disabled='disabled'>"+$tp[0]+"</option>");
                tr.next().text($tp[0]);
            }
            else
            {
                tr.val(trcv[$trc]);
            }
            if($trc<1)
            {
                $tp=$(tr).closest('td');
                $tp.data("id",dataID);
                $tp.attr("data-id",dataID);
            }
            for(i=0;i<nUV;i++)
                if(uniqueValues[i]===$trc)
                {
                    $tp=uniqueValues[++i];
                    tr.attr("data-value",$tp);
                    tr.data("value",$tp);
                    tr.attr("data-status",'1');
                    tr.data("status",'1');
                }
            $trc++;
        });
        delete tr,tr2;
        intializeValueLists($tr);
        addColumnEvents($trP,$tr);
        initializeNumberFields(new Array("inPortal")); //NumberFields.js file function
        if(Number(dataID)>0)
            backupRowValues($trF,dataID,$trP.attr("id"));
    }
    else
    {
        $tr=$("#"+target.replace(/['"]/g,"").replace(/[:]/g,"\\:"));
        var dataID=$tr.children(":first").data("id"),f=true,$trF=$tr.find('input[type=text], select, textarea'),i=0,$trP=$tr.closest('tbody');
        dataID=dataID!=null?Number(dataID):dataID;
        if(dataID>0&&$tr.data("cancel"))
        {
            f=compareValues($trF,dataID,$trP.attr('id'));
            if(!f)
                if(confirm("¿Quiere deshacer los cambios realizados?"))
                {
                    $tr.data("cancel",null);
                    $tr.removeAttr("data-cancel");
                }
                else
                    return;
        }
        var newPR=$tr.hasClass("newPR")||dataID<1;
        if(f)
            $trF.each(function(){
                var $t=$(this),txt="",val="";
                if($t.prop("nodeName")==='SELECT')
                {
                    //get the text in the styledSelect DIV
                    txt=$t.next().text();
                    val=$t.children(":selected").val();
                    if(val==null || val==="")
                        val=$t.closest('td').data("value");
                }
                /*else if($trF.eq(i+1).prop("nodeName")==='SELECT')
                {
                    txt=$trF.eq(i+1).children(":selected").val();
                    if(txt==='' || txt==null)
                        txt=$t.val();
                    val=txt;
                }*/
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
                else if($t.hasClass("date"))
                    f="class=\"calendar ";
                else
                    f="class=\"";
                var stl=$t.closest("td").attr("style");
                //alert($t.prop("nodeName")+": "+stl);
                tr+="<td "+f+($t.hasClass("unique")?"unique":"")+"\""+((val!=="")?(" data-value=\""+val+"\""):"")+(" style=\"text-align:"+$t.css("text-align")+";"+(stl!=null?stl:"")+"\"")+">"+txt+"</td>";
                i++;
            });
        else if(!newPR)
            tr+=restoreBackUpValues($trF,dataID,$trP.attr('id'));
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
            deleteBackupValues(dataID,$trP.attr('id'));
        $trF=$tr.children(":last").attr("style");
        tr+="<td style='"+(($trF!=null && $trF!="")?$trF:"padding:0")+"'><center style='padding:0'><i class='inPortal pe-7s-note' onclick='editRow();return false;'/><i class='inPortal pe-7s-trash' onclick='deleteFromPortal();return false;'/></center></td>";
        $trF=$tr.attr("data-extraStyle");
        if($trF!=null)
        {
            $trF=$trF.split(";");
            newPR="";
            for(i=0;i<$trF.length;i++)
            {
                if($trF[i].indexOf("background-color")<0)
                    newPR+=$trF[i]+";";
            }
            $trF=false;
            i=$tr.attr("style");
            $tr.attr("style",i+((i.charAt(i.length-1)===";")?";":";;")+newPR);
            $tr.removeAttr("id class data-extraStyle");
        }
        else
        {
            $trF=true;
            $tr.removeAttr("id class");
        }
        $tr.children().remove();
        $tr.prepend(tr);
        newPR=$tr.children(":first");
        newPR.attr("data-id",dataID);
        newPR.data("id",dataID);
        $tr.data("cancel",null);
        $tr.removeAttr("data-cancel");
        var $tb=$tr.parent();
        if($trF)
            limitTdWidth($tb,$tr);
        addRowEvents($tb,$tr);
        delete $tr,newPR;
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
        $id=$tr.find('select');
        $tb=$id.eq(pos);//Gets the in-portal select
    if($tb.children().size()>0)
        $id=$tb.children(":selected").val();
    else
        $id=$tb.closest('td').data("value");
    if($id==null)
        $id=$tb.next().text();
    pos=$so.clone(true,true);
    $so=pos.children();//source select set
    if(pos.hasClass("unique"))
    {
        $so.each(function(){
            this.disabled=false;
        });
        $tr=$tr.parent().find(".unique").not($tr.find(".unique"));//filters already selected values in other TR's
        var vals=getUniqueValues($tr,$so);
        $so.each(function(){
            var n=vals.length,$t=$(this);
            for(var i=0;i<n;i++)
                if(Number(vals[i])===Number($t.val()))
                    $t.attr('disabled',true);
        });
    }
    else
    {
        pos=$so.filter(function(){
            return this.value==$id;
        }).clone();
        pos.attr("disabled",true);
        $so=$.merge(pos,$so);
    }
    $tb.children().remove();
    $tb.append($so);
    delete pos,$so;
    var $sel=$tb.children().filter(function(){
                 return $(this).val() === $id; 
             });
    $sel.attr('selected',true);
    var $tbn=$tb.next(),tbSS=$tbn.attr('id'),tbID=tbSS+'src';
    $tb.attr('id',tbID);
    afterAjaxSelect(tbID,tbSS.replace('SS',''));//Function from the valuelistcontrols.js file
    delete tbID;
    $tb.val($sel.val());
    $tb.blur();
    $tb=$tb.next();
    if($tb.text()==null || $tb.text()=="")
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
    if(n>2 && soIndex<sortOrder.lenght)
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
            var kA=a.dataset.row,kB=b.dataset.row;
            if(kA!=="-1"&&kB!=="-1")
            {
                kA = $('td',a).eq(pos);
                kB = $('td',b).eq(pos);
                if(kA.hasClass("number"))
                    kA=Number(keepNumericValue(kA.text()));
                else if(kA.hasClass("calendar"))
                    kA=kA.text().split("/");
                else
                    kA=kA.text();
                if(kB.hasClass("number"))
                    kB=Number(keepNumericValue(kB.text()));
                else if(kB.hasClass("calendar"))
                {
                    kB=kB.text().split("/");
                    for(var i=2;i>-1;i--)
                    {
                        if(kA[i]!==kB[i]||i<1)
                        {
                            kA=Number(kA[i]);
                            kB=Number(kB[i]);
                            break;
                        }
                    }
                }
                else
                    kB=kB.text();
                if(type==='D')//DESC
                    return (kA < kB)?1:0;
                else//ASC
                    return (kA > kB)?1:0;
            }
            else return (Number(kA) < Number(kB))?1:0;
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
            var oc=$th.eq(c).attr("data-events");
            if(oc!=null)
            {
                oc=oc.split(",");
                var $t=$(this),n=oc.length;
                for (var i=0;i<n;i++) 
                {
                    var toc=oc[i].split("_");
                    $t.find(toc.length>2?toc[2]:".form-control").attr("on"+toc[0],toc[1]);
                }
            }
            else
            {
                oc=$th.eq(c).attr("onchange");
                if(oc!=null)
                    $t.children(".form-control").attr("onchange",oc);
            }
            c++;
        }
        else c=0;
    });
}

function addRowEvents($tbody,$tr)
{
    var $th=$tbody.prev(),tre=$th.attr("data-trEvent");
    if(tre!=null)
    {
        tre=tre.split("_");
        if(tre.length>1)
            $tr.attr("on"+tre[0],tre[1]);
    }
    tre=$th.attr("data-trPopover");
    if(tre!=null)
    {
        tre=tre.split(",");
        $tr.attr("data-toggle","popover");
        for(var i=1;i<tre.length;i++)
        {
            $th=tre[i].split("=");
            $tr.attr($th[0],$th[1].replace(/'/g,""));
        }
    }
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
            var $t=$(this);
            //t=$t.attr("style"),t=(t!=null)?t:"";
            if(c!=l)
            {
                var t=$t.text();
                $t.text("");
                var w=$t.css("width");
                $t.css("max-width",w);
                $t.css("min-width",w);
                $t.css("overflow","hidden");
                $t.text(t);
            }
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