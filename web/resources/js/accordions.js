function initializeAccordion(asc,onclick)
{
    $(".accordion").each(function(){
        var $acc=$(this).find(".accordionElement"),ri=0;
        if(asc)
        {
            var n=$acc.size();
            for(var i=0;i<n;i++)
            {
                var $t=$acc.eq(i);
                if($t.is("i"))
                    setIElemnts($t);
                else
                    setAccordionBehavor($t,++ri,onclick);
            }
        }
        else
        {
            for(var i=$acc.size()-1;i>=0;i--)
            {
                var $t=$acc.eq(i);
                if($t.is("i"))
                    setIElemnts($t);
                else
                    setAccordionBehavor($t,i-(i-ri++)+1,onclick);
            }
        }
    });
}

function setIElemnts($t)
{
    $t.mouseover(function() {
        $(this).css("color","#1DC7EA");
    });
    $t.mouseout(function() {
        $(this).css("color","#333");
    });
}

function setAccordionBehavor($t,ri,onclick)
{
    $t.find(".accText").each(function(){
        var $tt=$(this),txt=$tt.text().split("_ "),m=txt.length,txt2="";
        for(var j=(m>1?1:0);j<m;j++)
        {
            txt2+=txt[j];
        }
        $tt.text(ri+") "+txt2);
    });
    if(onclick)
    {
        $t.click(function(){
            this.classList.toggle("active");
            var panel=this;
            do
                panel=panel.nextSibling;
            while(panel.nodeName!=="LI")
            if (panel.style.display === "block") {
                panel.style.display = "none";
            } else {
                panel.style.display = "block";
            }
        });
    }
    else
    {
        var panel=$t.attr("onclick");
        if(panel==null || panel=="")
            $t.attr("onclick","var $t=$(this);$t.find('.accPanelLoader').keyup();$t.trigger('accordionLoad')");
        $t.on("accordionLoad",function(){
            panel=$t.attr("onclick");
            if(panel!=null && panel!="" && panel!=="$(this).trigger('accordionLoad')")
                $t.attr("onclick","$(this).trigger(\'accordionLoad\')");
            this.classList.toggle("active");
            panel=this;
            do
                panel=panel.nextSibling;
            while(panel.nodeName!=="LI")
            if (panel.style.display === "block") {
                panel.style.display = "none";
            } else {
                panel.style.display = "block";
            }
        });
    }
}