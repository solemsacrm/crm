$(document).ready(function(){
    
    function setActiveButtonGroup($t,$btgs)
    {
        var $shows;
        $btgs.not($t).each(function( ){
            $shows=this,c=(' '+$shows.className+' ').split(" active "),n=n=c.length;
            //if(n>1)
            {
                var nc="";
                for(var i=0;i<n;i++)
                    nc+=c[i];
                $shows.className=nc;
                $shows=$shows.dataset.shows;
                $shows=$("#"+$shows.replace(":","\\:"));
                $shows.css("display","none");
            }
        });
        $t.addClass("active");
        $shows=$("#"+$t.data("shows").replace(":","\\:"));
        if($shows!=null)
        {
            $shows.css("display","block");
        }
    }

    function setEventsOnTabControls()
    {
        $(".btn-group-justified").each(function(){
            var $btgs=$(this).find(".btn-group");
            $btgs.each(function(){
                var $t=$(this),$btn=$t.children("button");
                $btn.click(function(event){
                    event.preventDefault();
                    $btn.next().click();
                    event.stopPropagation();
                });
                $t.on("triggerTabControlSelectionEvent",function(event){
                    setActiveButtonGroup($t,$btgs);
                    event.stopPropagation();
                });
            });
        });
    }
        
    setEventsOnTabControls();
        
});

function triggerTabControlSelectionEvent($btn){
    $btn.triggerHandler("tabControlSelection");
}

function afterTabControlChange(data,t)
{
    if(data.status==='success')
        $(t).parent().trigger("triggerTabControlSelectionEvent");
}