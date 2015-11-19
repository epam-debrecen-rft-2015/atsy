/**
 * Created by tothd on 2015. 11. 19..
 */
$(document).ready(function() {
    var form = $("#searchCandidate"),
        table = $("#candidates");


    if ("onhashchange" in window) {

        form.submit(function (event){
            console.log(event);
            var values = ["name","email","phone","position"];
            var $this=$(this);
            values = values.map(function (selector){
                return [selector,$this.find("[name="+selector+"]").val()];
            }).filter(function (value){
                return value[1].length != 0;
            }).map(function (value){
                return value[0]+"/"+value[1];
            }).join("/");
            if(values.length > 0){
                location.hash=values;
            }
            event.preventDefault();
            return false;
        })

        function locationHashChanged() {
            alert(location.hash);
        }

        window.onhashchange = locationHashChanged;

        form.refresh;
        table.bootstrapTable('refresh');
    }

});
