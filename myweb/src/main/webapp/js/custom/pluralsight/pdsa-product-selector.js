/**
 * Description : PDSA Check Boxes in Buttons
 */

// * Options you can modify for check boxes in a bootstrap button
var checkOptions = {
    id: "",
    checkedGlyph: "glyphicon-ok-circle",
    uncheckedGlyph: "glyphicon-unchecked",
    checkedBtnClass: "btn-success",
    uncheckedBtnClass: "btn-primary",
    checkedText: "Selected",
    uncheckedText: "Not Selected"
};

// * Options you can modify for calculating a total
var totalOptions = {
    id: "#total",
    priceClass: ".price",
    priceContainerClass: ".panel"
};

// * Calculate total of checked check boxes
function calculateTotal(ctl) {

    var total = $(totalOptions.id).text();              // Get the total amount
    total = stripCurrency(total);                       // Strip currency symbols and thousands separator

    // to find the price within closest parent panel
    var price = $(ctl).closest(totalOptions.priceContainerClass)
                        .find(totalOptions.priceClass).text();
    price = stripCurrency(price);                       // Strip currency symbols and thousands separator

    if ($(ctl).prop("checked")) {
        total = parseFloat(total) + parseFloat(price);  // Add to total
    }
    else {
        total = parseFloat(total) - parseFloat(price);  // Subtract from total
    } 
    $(totalOptions.id).text(formatCurrency(total));     // Format the total and place into HTML
}

// * Set check box into 'checked' format
function setChecked(ctl) {
    $(ctl).prev()
        .removeClass(checkOptions.uncheckedGlyph)
        .addClass(checkOptions.checkedGlyph);
    $(ctl).parent()
        .removeClass(checkOptions.uncheckedBtnClass)
        .addClass(checkOptions.checkedBtnClass);
    $($(ctl).next()).text(checkOptions.checkedText);
}

// * Set check box into 'un-checked' format
function setUnchecked(ctl) {
    $(ctl).prev()
        .removeClass(checkOptions.checkedGlyph)
        .addClass(checkOptions.uncheckedGlyph);
    $(ctl).parent()
        .removeClass(checkOptions.checkedBtnClass)
        .addClass(checkOptions.uncheckedBtnClass);
    $($(ctl).next()).text(checkOptions.uncheckedText);
}

// * Connect to 'change' event and toggle any check boxes marked as 'checked'
$(document).ready(function () {

    // Detect checkboxes that are checked and toggle glyphs
    var checked = $(checkOptions.id + " .btn-group input:checked");

    // initialize the page : Add all 'checked' values to get total
    $.each(checked, function (i, item) {
        setChecked(item);
        calculateTotal(item);
    });
    /*
     for (var i = 0; i < checked.length; i++) {
     setChecked($(checked[i]));
     calculateTotal($(checked[i]));
     }
     */

    // Connect to 'change' event in order to toggle glyphs
    $(checkOptions.id + " .btn-group input[type='checkbox']").change(function () {
        if ($(this).prop("checked")) {
            setChecked($(this));
        }
        else {
            setUnchecked($(this));
        }
        calculateTotal($(this));
    });

});
