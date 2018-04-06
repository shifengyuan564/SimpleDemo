/**
* Description:
*
* Author: yuan@jd.com
* Date: 2016/9/3
*/

$(".navbar-collapse ul.navbar-scroll li a[href^='#'], a.navbar-brand[href^='#'], #back-to-top a[href^='#']").on('click', function(e){

    e.preventDefault();     // prevent default anchor click behavior

    var hash = this.hash;   // store hash

    $('html, body').animate({
        scrollTop: $(this.hash).offset().top - 50
    }, 500, function(){
       window.location.hash = hash; // when done, add hash to url (default click behavior)
    });
});

/* File: fixed.js
 * Fix shifting fixed navbar to the right
 */

$(document).ready(function(){
    $(window).load(function(){
        var oldSSB = $.fn.modal.Constructor.prototype.setScrollbar;
        $.fn.modal.Constructor.prototype.setScrollbar = function ()
        {
            oldSSB.apply(this);
            if(this.bodyIsOverflowing && this.scrollbarWidth)
            {
                $('.navbar-fixed-top, .navbar-fixed-bottom').css('padding-right', this.scrollbarWidth);
            }
        }

        var oldRSB = $.fn.modal.Constructor.prototype.resetScrollbar;
        $.fn.modal.Constructor.prototype.resetScrollbar = function ()
        {
            oldRSB.apply(this);
            $('.navbar-fixed-top, .navbar-fixed-bottom').css('padding-right', '');
        }
    });
});