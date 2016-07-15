$(document).ready(function () {

    var pdsaCollapser = $("[data-pdsa-collapser-name]");

    for (var i = 0; i < pdsaCollapser.length; i++) {
        var name = pdsaCollapser[i].id;
        var open = $("#" + name).data("pdsa-collapser-open");   // use data-* attribute
        var close = $("#" + name).data("pdsa-collapser-close");

        // Add 'open' glyph icon to all panels
        $("#" + name + " .pdsa-panel-toggle").addClass(open);

        // Find any panel's that have the class '.in', remove the 'open' glyph &
        // Add the 'close' glyph icon , $(item).attr("id") 与 item.id 一样
        var list = $("#" + name + " .in");
        
        $.each(list, function (i, item) {
            $("a[href='#" + item.id + "']").next(".pdsa-panel-toggle").removeClass(open).addClass(close);
        })

        // Hook into 'hide' event, 这里的target为：eg. <div id="mytoprated" class="panel-collapse collapse in">
        $("#" + name).on('hide.bs.collapse', function (e) {
            var parent = $("#" + e.target.id).parents(".panel-group");

            $("#" + e.target.id).prev().find(".pdsa-panel-toggle")
                .removeClass($(parent).data("pdsa-collapser-close"))
                .addClass($(parent).data("pdsa-collapser-open"));
        });

        // Hook into 'show' event
        $("#" + name).on('show.bs.collapse', function (e) {
            var parent = $("#" + e.target.id).parents(".panel-group");

            $("#" + e.target.id).prev().find(".pdsa-panel-toggle")
                .removeClass($(parent).data("pdsa-collapser-open"))
                .addClass($(parent).data("pdsa-collapser-close"));
        });
    }
});
