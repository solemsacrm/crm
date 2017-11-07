$(document).ready(function(){
    runCheckListsControls();
});

function runCheckListsControls()
{
    var $ct=$(".checkTarget");
    if($ct && $ct!=='')
    {
        $(".checkSource").each(function(index){
            var $t=$(this);
            $ct=$ct.eq(index);
            $t.find("input[type=checkbox]").each(function(){
                $(this).change(function(){
                    var $tf=$t.find(":checked"),n=$tf.size(),val="";
                    for(var i=0;i<n;i++)
                    {
                        var $temp=$tf.eq(i);
                            val+=$temp.val()+",";
                    }
                    $ct.val(val.replace(/,$/,""));
                    $ct.trigger("onchange");
                });
            });
        });
    }
}