function runJScode(ajaxR,DatePicker,numberFields){
    var $selects=$('select');
    if(ajaxR)
    {
        $selects.not('.form-control,.inPortalSS').each(function(){
            var $t=$(this),
            v=$t.find('option:selected').val(),
            t=$t.find('option:selected').text(),
            id=$t.attr("data-skipAjaxLoad");
            loadselect($t,0);
            if((v>0 || $t.next().next().children(".selected").attr("rel")>0)&&(id==null || id==false))
            {
                id=$t.next().attr("id");
                $t.siblings().remove();
                $t.off();
                loadselect($t,Number(id.replace("SS","")));
                $t.next().next().children().filter(function(){return $(this).attr("rel")===v;}).click();
            }
            else $t.next().text(t);
            $(document).click();
        });
    }
    else
    {
        $selects.not('.form-control').each(function () {
            loadselect($(this),0);
        });
        $(document).click(function(){
            var $list=$(".styledSelect.active");
            $list.each(function(){
                if(this.previousSibling.className.indexOf("inPortalSS")>-1)
                    this.parentNode.parentNode.style=tdStyle;
            });
            $list.removeClass('active');
            $list.next().hide();
            $(document).off('keydown');
        });
    }
    $selects.filter(".form-control").each(function(){
        $(this).children().filter(function(){return this.disabled===true;}).remove();
    });
    if(DatePicker)
        runDatePicker(window.jQuery);
    if(numberFields)
    {
        $(".number").each(function(){
            $(this).focus();
            $(this).blur();
        });
    }
}

function loadselect($vl,n)
{
    // Cache the number of options
    
    /*var $this=$vl,
        $defaultValue=$this.children('option:disabled'),
        N=$vl.children('option').length-$defaultValue.size(),
        f=true;*/
    
    var $this=$vl,
        $thisChildren=$vl.children('option'),
        $defaultValue=$thisChildren.filter(':disabled'),
        N=$thisChildren.size()-$defaultValue.size(),
        f=true;
    // Hides the select element
    //$this.addClass('s-hidden');
    // Wrap the select element in a div
    if(n===0)
    {
        $this.wrap('<div class="form-control select"></div>');
        n=$('div.styledSelect').size()+1;
        f=false;
    }
    $this.css("max-width","1px");
    // Insert a styled div to sit over the top of the hidden select element
    $this.after('<div id="SS'+n+'" class="form-control styledSelect"></div>');

    // Cache the styled div
    var $styledSelect = $this.next();//'div.styledSelect'
    //$styledSelect.width($styledSelect.parent().width());
    // Show the first select option in the styled div
    if($defaultValue && $defaultValue.size()>0)
    {
        $styledSelect.text($defaultValue.text());
        $defaultValue.remove();
        $thisChildren=$this.children('option');
    }
    /*else
        $styledSelect.text($thisChildren.eq(0).text());*/
    // Insert an unordered list after the styled div and also cache the list
    var $list = $('<ul />', {
        'id':"ulSS"+n,
        'class': 'options'//,
        //'height':(N>10?10:N/0.685)+'em'//0.685 = number of em's a <li> is
    }).insertAfter($styledSelect);
    // Insert a list item into the unordered list for each select option
    var liC;
    for(liC=0;liC<N;liC++)
    {
        var $ttc=$thisChildren.eq(liC),txt=$ttc.text(),cls='';
        if($styledSelect.text()===txt)
        {
            cls='selected';
            iLiSelected=liC;
        }
        var $li=$('<li />', {
            text: txt,
            rel: $ttc.val(),
            class:cls
        });
        if($ttc.data("other")!=null)//Attribute brought from portalActions.js
            $li.attr("data-other",$ttc.data("other"));
        $li.appendTo($list);
        if(cls!=='')
            liSelected=$li;
    }
    if($li)
        delete txt,cls,$li;
    if(f)
    {
        delete f;
        // Cache the list items
        var $listItems=$list.children('li');
        var liSelected,iLiSelected,next;
        liC-=6;
        
        // Show the unordered list when the styled div is clicked (also hides it if the div is clicked again)
        $styledSelect.click(function(e){
            var $t=$(this);
            if($t.prev().hasClass("unique"))
            {
                var $tp=$t.prev(),ds=$tp.data("status");
                if(ds==='0')
                {
                    $tp.data("status",'1');
                    $tp.attr("data-status",'1');
                }
                else if(ds==='1')
                {
                    $tp.data("status",'2');
                    $tp.attr("data-status",'2');
                }
                else
                {
                    var $tp=$t.prev();
                    $tp.children().remove();
                    $tp.data("status",'1');
                    $tp.attr("data-status",'1');
                    $tp.focus();
                    return false;
                }
            }
            if($this.hasClass("inPortalSS"))
            {
                tdStyle=$t.closest("td").attr("style");
                if(tdStyle!=null && tdStyle!="")
                {
                    if(tdStyle.indexOf("z-index:998;")<0)
                        $t.closest("td").attr("style","z-index:998;");
                }
                else delete tdStyle;
            }
            /*else
            {
                var $tp=$t.prev();
                $tp.children().remove();
                $tp.addClass("unique");
                $tp.focus();
            }*/
            $('div.styledSelect.active').each(function(){
                var $t=$(this);
                $t.removeClass('active');
                $t.next('ul.options').hide();
            });
            $t.toggleClass('active');
            $t.next('ul.options').toggle();
            $(document).keydown(function(e){
                e.preventDefault();
                var k=e.which;
                if(k===40)
                {
                    if(liSelected)
                    {
                        next = liSelected.next();
                        if(next.length>0)
                        {
                            iLiSelected+=1;
                            if(iLiSelected>6)
                                $list.animate({scrollTop:$list.scrollTop()+20},0);
                            liSelected.removeClass('selected');
                            liSelected=next.addClass('selected');
                        }
                    }
                    else
                    {
                        iLiSelected=1;
                        liSelected = $listItems.eq(0).addClass('selected');
                    }
                }
                else if(k===38)
                {
                    if(liSelected)
                    {
                        next = liSelected.prev();
                        if(next.length>0)
                        {
                            iLiSelected-=1;
                            if(iLiSelected<liC)
                                $list.animate({scrollTop:$list.scrollTop()-20},0);
                            liSelected.removeClass('selected');
                            liSelected = next.addClass('selected');
                        }
                    }
                    else
                    {
                        iLiSelected=liC+5;
                        $list.animate({scrollTop:liC*20});
                        liSelected=$listItems.last().addClass('selected');
                    }
                }
                else if(k===13)
                    liSelected.click();
                else if(k===9)
                {
                    $styledSelect.removeClass('active');
                    $list.hide();
                    var $x=$('.form-control');
                    var c=-1;
                    do var $next=$x.eq(++c);
                    while($next.attr('id')!==$styledSelect.attr('id'));
                    if(e.shiftKey)
                    {
                        do var $next=$x.eq(--c);
                        while($next[0].nodeName!=='INPUT');
                        $next.focus();
                    }
                    else
                    {
                        do var $next=$x.eq(++c);
                        while($next[0].nodeName!=='INPUT');
                        $next.focus();
                    }
                    //$this.css("display","inherit");
                    $(document).off('keydown');
                }
            });
            e.stopPropagation();
        });

        // Hides the unordered list when a list item is clicked and updates the styled div to show the selected list item
        // Updates the select element to have the value of the equivalent option
        $listItems.click(function (e){
            iLiSelected=$(this).index()+1;
            $thisChildren.filter(function(){
                return this.selected === true; 
            }).attr('selected',false);
            /*if(liSelected)
            {
                liSelected.removeClass('selected');
                liSelected.attr('selected',null);
            }*/
            //alert(1);
            liSelected=$(this);
            liSelected.siblings().removeClass('selected');
            if(!liSelected.hasClass('selected'))
                liSelected.addClass('selected');
            $styledSelect.text(liSelected.text()).removeClass('active');
            $this.val(liSelected.attr('rel'));
            $list.hide();
            //alert(2);
            $thisChildren.filter(function(){
                if(this.value!=null)
                    return this.value == liSelected.attr('rel');
                else
                    return $(this).text() === liSelected.text(); 
            }).attr('selected',true);
            //alert(3);
            if($this.hasClass("inPortalSS"))
            {
                try{
                    var $ctd=tdStyle!=null;
                }catch(err)
                {
                    $ctd=false;
                }
                if($ctd)
                {
                    var $ctd=liSelected.closest("td");
                    $ctd.attr("style",tdStyle);
                    delete tdStyle,$ctd;
                    fillInPortalFieldsWithOtherData(liSelected);
                }
            }
            $(document).off('keydown');
            //alert(4);
            $this.trigger("onchange");//triggers onchange ajax request
            //alert(5);
            e.stopPropagation();
            /*alert($this.val()); Uncomment this for demonstration!*/
        });
    }
    else
    {
        if(!$this.hasClass("inPortalSS"))
            $styledSelect.text($this.text());
        $styledSelect.click(function(e){
            $styledSelect.off('click');
            this.previousSibling.focus();
            e.stopPropagation();
        });
    }

    // Hides the unordered list when clicking outside of it
    /*$(document).click(function () {
        $styledSelect.removeClass('active');
        $list.hide();
        $(document).off('keydown');
    });*/
}

function afterAjaxSelect(idS,n)
{
    var idSS="SS"+n,
    $SS=$('#'+idSS);
    $SS.next().remove();
    $SS.remove();
    $SS=$('#'+idS);
    $SS.off();
    loadselect($SS,n);
    $('#'+idS).val(null);
    document.getElementById(idSS).click();
};

function fillInPortalFieldsWithOtherData($li)
{
    var data=$li.data("other");
    if(data!=null && data!="")
    {
        var data=data.split(",["),
            n=data.length;
        data[0]=data[0].slice(1,data[0].length);
        for(var i=0;i<n;i++)
            data[i]=data[i].split("]_");
        var $tr=$li.closest("tr");
        for(var i=0;i<n;i++)
        {
            var d=data[i],d1=d[1].indexOf("D");
            if(d1>-1)
            {
                d1=d[1].slice(0,d1);
                $tr.children().eq(Number(d1)).attr("data-other",d[0]);
            }
            else
            {
                d1=$tr.children().eq(Number(d[1])).find(".form-control").eq(0);
                d1.val(d[0]);
                if(d1.hasClass("number"))
                {
                    d1.focus();
                    d1.blur();
                }
                d=d1.attr("onchange");
                if(d!=null&&d!=="")
                    d1.change();
            }
        }
    }
}