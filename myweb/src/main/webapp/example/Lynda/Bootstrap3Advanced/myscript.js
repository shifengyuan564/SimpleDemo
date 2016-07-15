/* 这段脚本适合学习层级关系, 结合header.php*/

$(function () {
    // activate schedule tab by name
    var hash = window.location.hash;
    hash && $('ul.nav a[href="' + hash + '"]').tab("show");

    // controll opening of accordion
    $("#monday.accordion-body").addClass("in");
    $("#artists #tuesday.accordion-body").addClass("in");
    $("#artists #wednesday.accordion-body").addClass("in");
    $("#artists #thursday.accordion-body").addClass("in");
    $("#artists #friday.accordion-body").addClass("in");

    // add bootstrap class to photos
    $(".abouttheartists img").addClass("img-circle");
    $("aside.photosfromlastyear img").addClass("img-thumbnail");
    $(".artistinfo .photogrid img").addClass("img-circle");

    // highlight the curent nav
    $("#home a:contains('Home')").parent().addClass("active");
    $("#schedule a:contains('Schedule')").parent().addClass("active");
    $("#artists a:contains('Artists')").parent().addClass("active");
    $("#venuetravel a:contains('Venue/Travel')").parent().addClass("active");
    $("#register a:contains('Register')").parent().addClass("active");

    // make menu drop automatically
    $("ul.nav li.dropdown").hover(
        function () {
            $(".dropdown-menu", this).fadeIn()
        },
        function () {
            $(".dropdown-menu", this).fadeOut("fast")
        }
    );

    // data-toggle属性值是tooltip的选择器
    $("[data-toggle='tooltip']").tooltip({animation: true});

    // show modal
    $(".modalphotos img").on("click", function () {
        $("#modal").modal({
            show: true,
        });
        var imgUrl = this.src.substr(0, this.src.length - 7) + ".jpg";
        $("#modalimage").attr("src", imgUrl);
        $("#modalimage").on("click", function () {
            $("#modal").modal("hide")
        })
    });

});
