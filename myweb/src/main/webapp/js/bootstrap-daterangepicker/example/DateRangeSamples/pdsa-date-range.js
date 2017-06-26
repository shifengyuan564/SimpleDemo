$(function () {
    // Hook up to click event of List Group Items
    $("#pdsadatelist .list-group-item").click(function () {
        // Get data- value
        var func = $(this).data("pdsa-date-func");
        var d = new Date();

        // Unhighlight the last selected item
        $("#pdsadatelist").children().removeClass("list-group-item-selected");
        // Highlight the current item
        $(this).addClass("list-group-item-selected");

        switch (func.toLowerCase()) {
            case "today":
                $("#pdsaDateFrom").val(formatdatetime(d.toLocaleDateString()));
                $("#pdsaDateTo").val(formatdatetime(d.toLocaleDateString()));
                break;

            case "yesterday":
                d.setDate(d.getDate() - 1);
                $("#pdsaDateFrom").val(formatdatetime(d.toLocaleDateString()));
                $("#pdsaDateTo").val(formatdatetime(d.toLocaleDateString()));
                break;

            case "last30days":
                var de = new Date(d.getFullYear(), d.getMonth(), d.getDay() - 30);
                $("#pdsaDateFrom").val(formatdatetime(de.toLocaleDateString()));
                $("#pdsaDateTo").val(formatdatetime(d.toLocaleDateString()));
                break;

            case "thismonth":
                var dm = new Date(d.getFullYear(), d.getMonth(), 1);
                var de = new Date(d.getFullYear(), d.getMonth() + 1, 1);
                de.setDate(de.getDate() - 1);
                $("#pdsaDateFrom").val(formatdatetime(dm.toLocaleDateString()));
                $("#pdsaDateTo").val(formatdatetime(de.toLocaleDateString()));
                break;

            case "lastmonth":
                var dm = new Date(d.getFullYear(), d.getMonth() - 1, 1);
                var de = new Date(d.getFullYear(), d.getMonth(), 1);
                de.setDate(de.getDate() - 1);
                $("#pdsaDateFrom").val(formatdatetime(dm.toLocaleDateString()));
                $("#pdsaDateTo").val(formatdatetime(de.toLocaleDateString()));
                break;

            case "nextmonth":
                var dm = new Date(d.getFullYear(), d.getMonth() + 1, 1);
                var de = new Date(d.getFullYear(), d.getMonth() + 2, 1);
                de.setDate(de.getDate() - 1);
                $("#pdsaDateFrom").val(formatdatetime(dm.toLocaleDateString()));
                $("#pdsaDateTo").val(formatdatetime(de.toLocaleDateString()));
                break;

            case "thisyear":
                var dm = new Date(d.getFullYear(), 0, 1);
                var de = new Date(d.getFullYear(), 11, 31);
                $("#pdsaDateFrom").val(formatdatetime(dm.toLocaleDateString()));
                $("#pdsaDateTo").val(formatdatetime(de.toLocaleDateString()));
                break;

            default:
                break;
        }
    });
});

function formatdatetime(val) {
    var datetime = "";
    if (val != "" && val != null) {
        var d = new Date(val);
        var year = d.getFullYear();

        var month = (d.getMonth() + 1);  // 获取月份。
        if (month < 10) {
            month = "0" + month;
        }

        var day = d.getDate();                   // 获取日。
        if (day < 10) {
            day = "0" + day;
        }
        datetime = year + "-" + month + "-" + day;
    }
    return datetime;
}