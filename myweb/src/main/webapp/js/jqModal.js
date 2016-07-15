/*
 * jqModal - Minimalist Modaling with jQuery
 *
 * Copyright (c) 2007-2015 Brice Burgess @IceburgBrice
 * Dual licensed under the MIT and GPL licenses:
 *   http://www.opensource.org/licenses/mit-license.php
 *   http://www.gnu.org/licenses/gpl.html
 *
 * $Version: 1.4.1 (2015.09.07 +r26)
 * Requires: jQuery 1.2.3+
 */

(function (factory) {
  if (typeof module === 'object' && typeof module.exports === 'object') {
    // Node/CommonJS
    module.exports = factory(require('jquery'));
  } else {
    // Browser globals
    factory(jQuery);
  }
}(function ($) {

	/**
	 * Initialize elements as "modals". Modals typically are popup dialogs,
	 * notices, modal windows, &c.
	 *
	 * @name jqm
	 * @param options user defined options, augments defaults.
	 * @type jQuery
	 * @cat Plugins/jqModal
	 */

	$.fn.jqm=function(options){
		return this.each(function(){
			var jqm = $(this).data('jqm') || $.extend({ID: I++}, $.jqm.params),
		      o = $.extend(jqm,options);

			// add/extend options to modal and mark as initialized
			$(this).data('jqm',o).addClass('jqm-init')[0]._jqmID = o.ID;

			// ... Attach events to trigger showing of this modal
			$(this).jqmAddTrigger(o.trigger);
		});
	};

	/**
	 * Matching modals will have their jqmShow() method fired by attaching a
	 *   onClick event to elements matching `trigger`.
	 *
	 * @name jqmAddTrigger
	 * @param trigger a a string selector, jQuery collection, or DOM element.
	 */
	$.fn.jqmAddTrigger=function(trigger){
	  if(trigger){
	    return this.each(function(){
			  if (!addTrigger($(this), 'jqmShow', trigger)) {
			    err("jqmAddTrigger must be called on initialized modals");
        }
		  });
	  }
	};

	/**
	 * Matching modals will have their jqmHide() method fired by attaching an
	 *   onClick event to elements matching `trigger`.
	 *
	 * @name jqmAddClose
	 * @param trigger a string selector, jQuery collection, or DOM element.
	 */
	$.fn.jqmAddClose=function(trigger){
	  if(trigger){
  	  return this.each(function(){
  			if(!addTrigger($(this), 'jqmHide', trigger)) {
  			  err ("jqmAddClose must be called on initialized modals");
        }
  		});
	  }
	};

	/**
	 * Open matching modals (if not shown)
	 */
	$.fn.jqmShow=function(trigger){
		return this.each(function(){
      if(!this._jqmShown) { show($(this), trigger); }
    });
	};

	/**
	 * Close matching modals
	 */
	$.fn.jqmHide=function(trigger){
		return this.each(function(){
      if(this._jqmShown) { hide($(this), trigger); }
    });
	};

	// utility functions

	var
		err = function(msg){
			if(window.console && window.console.error) { window.console.error(msg); }

	}, show = function(m, t){

		/**
		 * m = modal element (as jQuery object)
		 * t = triggering element
		 *
		 * o = options
		 * z = z-index of modal
		 * v = overlay element (as jQuery object)
		 * h = hash (for jqModal <= r15 compatibility)
		 */

	  t = t || window.event;

		var o = m.data('jqm'),
			z = (parseInt(m.css('z-index'),10)) || 3000,
			v = $('<div></div>').addClass(o.overlayClass).css({
			  height:'100%',
			  width:'100%',
			  position:'fixed',
			  left:0,
			  top:0,
			  'z-index':z-1,
			  opacity:o.overlay/100
			}),

			// maintain legacy "hash" construct
			h = {w: m, c: o, o: v, t: t};

		m.css('z-index',z);

		if(o.ajax){
			var target = o.target || m,
				url = o.ajax;

			target = (typeof target === 'string') ? $(target,m) : $(target);
			if(url.substr(0,1) === '@') {
        url = $(t).attr(url.substring(1));
      }

			// load remote contents
			target.load(url,function(){
				if(o.onLoad) {
           o.onLoad.call(this,h);
         }
        parseModalContents.call(m,o,target);
			});

			// show modal
			if(o.ajaxText) {
        target.html(o.ajaxText);
      }
      open(h);
		}
		else { open(h); }

	}, hide = function(m, t){
		/**
		 * m = modal element (as jQuery object)
		 * t = triggering element
		 *
		 * o = options
		 * h = hash (for jqModal <= r15 compatibility)
		 */

	  t = t || window.event;
		var o = m.data('jqm'),
		    // maintain legacy "hash" construct
		    h = {w: m, c: o, o: m.data('jqmv'), t: t};

		close(h);

	}, onShow = function(hash){
		// onShow callback. Responsible for showing a modal and overlay.
		//  return false to stop opening modal.

		// hash object;
		//  w: (jQuery object) The modal element
		//  c: (object) The modal's options object
		//  o: (jQuery object) The overlay element
		//  t: (DOM object) The triggering element

		// if overlay not disabled, prepend to body
		if(hash.c.overlay > 0) {
      hash.o.prependTo('body');
    }

		// make modal visible
		hash.w.show();

		// call focusFunc (attempts to focus on first input in modal)
		$.jqm.focusFunc(hash.w,true);

		return true;

	}, onHide = function(hash){
		// onHide callback. Responsible for hiding a modal and overlay.
		//  return false to stop closing modal.

		// hash object;
		//  w: (jQuery object) The modal element
		//  c: (object) The modal's options object
		//  o: (jQuery object) The overlay element
		//  t: (DOM object) The triggering element

		// hide modal and if overlay, remove overlay.
		if(hash.w.hide() && hash.o) {
      hash.o.remove();
    }

		return true;

	},  addTrigger = function(m, key, trigger){
		// addTrigger: Adds a jqmShow/jqmHide (key) event click on modal (m)
		//  to all elements that match trigger string (trigger)

		var jqm = m.data('jqm');
		if(jqm) {
      return $(trigger).each(function(){
  			this[key] = this[key] || [];
  			// register this modal with this trigger only once
  			if($.inArray(jqm.ID,this[key]) < 0) {
  				this[key].push(jqm.ID);

  				// register trigger click event for this modal
  				//  allows cancellation of show/hide event from
  				$(this).click(function(e){
  					if(!e.isDefaultPrevented()) { m[key](this); }
  					return false;
  				});
  			}
  		});
    }

	}, open = function(h){
		// open: executes the onOpen callback + performs common tasks if successful

		// transform legacy hash into new var shortcuts
		var m = h.w,
			v = h.o,
			o = h.c;

		// execute onShow callback
		if(o.onShow(h) !== false){
			// mark modal as shown
			m[0]._jqmShown = true;

			// if modal:true  dialog
			//   Bind the Keep Focus Function [F] if no other Modals are active
			// else,
			//   trigger closing of dialog when overlay is clicked
			if(o.modal){
			  if(!ActiveModals[0]){ F('bind'); }
			  ActiveModals.push(m[0]);
			}
			else {
        m.jqmAddClose(v);
      }

      // parse modal contents and add behavior
      parseModalContents.call(m,o);

			// if toTop is true and overlay exists;
			//  remember modal DOM position with <span> placeholder element, and move
			//  the modal to a direct child of the body tag (after overlyay)
			if(o.toTop && v) {
			  m.before('<span id="jqmP'+o.ID+'"></span>').insertAfter(v);
      }

			// remember overlay (for closing function)
			m.data('jqmv',v);

			// close modal if the esc key is pressed and closeOnEsc is set to true
			m.unbind("keydown",$.jqm.closeOnEscFunc);
			if(o.closeOnEsc) {
				m.attr("tabindex", 0).bind("keydown",$.jqm.closeOnEscFunc).focus();
			}
		}

	}, close = function(h){
		// close: executes the onHide callback + performs common tasks if successful

		// transform legacy hash into new var shortcuts
		 var m = h.w,
			v = h.o,
			o = h.c;

		// execute onHide callback
		if(o.onHide(h) !== false){
			// mark modal as !shown
			m[0]._jqmShown = false;

			 // If modal, remove from modal stack.
			 // If no modals in modal stack, unbind the Keep Focus Function
			 if(o.modal){
			   ActiveModals.pop();
			   if(!ActiveModals[0]) {
           F('unbind');
         }
			 }

			 // IF toTop was passed and an overlay exists;
			 //  Move modal back to its "remembered" position.
			 if(o.toTop && v) {
         $('#jqmP'+o.ID).after(m).remove();
       }
		}

	},  F = function(t){
		// F: The Keep Focus Function (for modal: true dialos)
		// Binds or Unbinds (t) the Focus Examination Function (X)

		$(document)[t]("keypress keydown mousedown",X);

	}, X = function(e){
		// X: The Focus Examination Function (for modal: true dialogs)

		var targetModal = $(e.target).data('jqm') ||
		                  $(e.target).parents('.jqm-init:first').data('jqm');
		var activeModal = ActiveModals[ActiveModals.length-1];

		// allow bubbling if event target is within active modal dialog
		return (targetModal && targetModal.ID === activeModal._jqmID) ?
		         true : $.jqm.focusFunc(activeModal,e);

	}, parseModalContents = function(o, context){
    context = context || this;

    //  Attach events to elements inside the modal matching closingClass
    if(o.closeClass) {
      this.jqmAddClose($('.' + o.closeClass,context));
    }

  },
	I = 0,   // modal ID increment (for nested modals)
	ActiveModals = [];  // array of active modals

	// $.jqm, overridable defaults
	$.jqm = {
		/**
		 *  default options
		 *
		 * (Integer)   overlay      - [0-100] Translucency percentage (opacity) of the body covering overlay. Set to 0 for NO overlay, and up to 100 for a 100% opaque overlay.
		 * (String)    overlayClass - Applied to the body covering overlay. Useful for controlling overlay look (tint, background-image, &c) with CSS.
		 * (String)    closeClass   - Children of the modal element matching `closeClass` will fire the onHide event (to close the modal).
		 * (Mixed)     trigger      - Matching elements will fire the onShow event (to display the modal). Trigger can be a selector String, a jQuery collection of elements, a DOM element, or a False boolean.
		 * (String)    ajax         - URL to load content from via an AJAX request. False to disable ajax. If ajax begins with a "@", the URL is extracted from the attribute of the triggering element (e.g. use '@data-url' for; <a href="#" class="jqModal" data-url="modal.html">...)
		 * (Mixed)     target       - Children of the modal element to load the ajax response into. If false, modal content will be overwritten by ajax response. Useful for retaining modal design.
		 *                            Target may be a selector string, jQuery collection of elements, or a DOM element -- and MUST exist as a child of the modal element.
		 * (String)    ajaxText     - Text shown while waiting for ajax return. Replaces HTML content of `target` element.
		 * (Boolean)   modal        - If true, user interactivity will be locked to the modal window until closed.
		 * (Boolean)   toTop        - If true, modal will be posistioned as a first child of the BODY element when opened, and its DOM posistion restored when closed. Useful for overcoming z-Index container issues.
		 * (Function)  onShow       - User defined callback function fired when modal opened.
		 * (Function)  onHide       - User defined callback function fired when modal closed.
		 * (Function)  onLoad       - User defined callback function fired when ajax content loads.
		 */
		params: {
			overlay: 50,
			overlayClass: 'jqmOverlay',
			closeClass: 'jqmClose',
			closeOnEsc: false,
			trigger: '.jqModal',
			ajax: false,
			target: false,
			ajaxText: '',
			modal: false,
			toTop: false,
			onShow: onShow,
			onHide: onHide,
			onLoad: false
		},

		// focusFunc is fired:
		//   a) when a modal:true dialog is shown,
		//   b) when an event occurs outside an active modal:true dialog
		// It is passed the active modal:true dialog as well as event
		focusFunc: function(activeModal, e) {

		  // if the event occurs outside the activeModal, focus on first element
		  if(e) { $(':input:visible:first',activeModal).focus(); }

		  // lock interactions to the activeModal
		  return false;
		},

		// closeOnEscFunc is attached to modals where closeOnEsc param true.
		closeOnEscFunc: function(e){
			if (e.keyCode === 27) {
				$(this).jqmHide();
				return false;
			}
		}
	};

    /* ---------------------------------------- custom method ------------------------------------*/
    $.jqm.confirm = function(c) {
        var d = {
            w : 370,
            self : null,
            title : "确认",
            content : "内容",
            type : "alert",
            onConfirm : null,
            overlay : 20,
            overlayClass : "whiteOverlay",
            retrieveTop : undefined,
            closeClass : "jqmClose"
        };
        d.w = c.w ? c.w : d.w;
        d.self = c.self ? c.self : d.self;
        d.title = c.title ? c.title : d.title;
        d.content = c.content ? c.content : d.content;
        d.type = c.type ? c.type : d.type;
        d.onConfirm = c.onConfirm ? c.onConfirm : d.onConfirm;
        d.overlay = c.overlay ? c.overlay : d.overlay;
        d.overlayClass = c.overlayClass ? c.overlayClass : d.overlayClass;
        d.jqmClose = c.jqmClose ? c.closeClass : d.closeClass;
        d.retrieveTop = c.retrieveTop ? c.retrieveTop : d.retrieveTop;
        var b = {
            overlay : d.overlay,
            overlayClass : d.overlayClass,
            retrieveTop : d.retrieveTop,
            closeClass : d.closeClass
        };
        if ($("#jqmConfirm").length) {
            $("#jqmConfirm").remove()
        }
        var a = '<div id="jqmConfirm" style="width:'
            + d.w
            + 'px;"class="modal"><h1 class="tl"><span class="tr"><span class="tit">'
            + d.title
            + '</span><span class="modalClose jqmClose">&#20851;&#38381;</span></span></h1><div class="moadalCon"><div class="clearfix fakeMsg '
            + d.type
            + '"><i class="ico"></i><div class="conText">'
            + d.content
            + '</div></div></div><div class="modalFooter pb20">' +
            '<a href="javascript:void(0);"id="jqmConfirmBtn"class="btn mr25"><s><b><span>&#30830;&emsp;&#23450;</span></b></s></a><a href="javascript:void(0);"id="jqmConfirmBtn"class="btn jqmClose"><s><b><span>&#21462;&nbsp;&#28040;</span></b></s></a></div><div class="bl"><div class="br"></div></div></div>';
        $("body").append(a);
        $("#jqmConfirm").jqm(b);
        $("#jqmConfirm").jqmShow();
        $("#jqmConfirmBtn").click(function() {
            if (d.onConfirm) {
                $("#jqmConfirm").jqmHide().remove();
                d.onConfirm.call(d.self)
            } else {
                return
            }
        })
    };
    $.jqm.alert = function(c) {
        var d = {
            w : 200,
            self : null,
            title : "确认",
            content : "内容",
            type : "success",
            onConfirm : null,
            overlay : 20,
            overlayClass : "whiteOverlay",
            closeClass : "jqmClose",
            retrieveTop : undefined,
            onShow : undefined,
            onHide : function() {
                return true
            }
        };
        d.w = c.w ? c.w : d.w;
        d.self = c.self ? c.self : d.self;
        d.title = c.title ? c.title : d.title;
        d.content = c.content ? c.content : d.content;
        d.type = c.type ? c.type : d.type;
        d.onConfirm = c.onConfirm ? c.onConfirm : d.onConfirm;
        d.overlay = c.overlay ? c.overlay : d.overlay;
        d.overlayClass = c.overlayClass ? c.overlayClass : d.overlayClass;
        d.jqmClose = c.jqmClose ? c.closeClass : d.closeClass;
        d.onHide = c.onHide ? c.onHide : d.onHide;
        d.retrieveTop = c.retrieveTop ? c.retrieveTop : d.retrieveTop;
        var b = {
            overlay : d.overlay,
            overlayClass : d.overlayClass,
            retrieveTop : d.retrieveTop,
            closeClass : d.closeClass
        };
        if ($("#jqmConfirm").length) {
            $("#jqmConfirm").remove()
        }
        var a = '<div id="jqmAlert" style="width:'
            + d.w
            + 'px;"class="modal"><h1 class="tl"><span class="tr"><span class="tit">'
            + d.title
            + '</span><span class="modalClose '
            + b.closeClass
            + '">&#20851;&#38381;</span></span></h1><div class="moadalCon"><div class="clearfix fakeMsg '
            + d.type
            + '"><i class="ico"></i><div class="conText">'
            + d.content
            + '</div></div></div><div class="modalFooter pb20"><a href="javascript:void(0);"id="jqmAlertBtn" class="btn '
            + b.closeClass
            + '"><s><b><span>&#20851;&emsp;&#38381;</span></b></s></a></div><div class="bl"><div class="br"></div></div></div>';
        $("body").append(a);
        $("#jqmAlert").jqm(b);
        $("#jqmAlert").jqmShow();
        $("#jqmAlert ." + b.closeClass + "").click(function() {
            d.onHide();
            $("#jqmAlert").jqmHide().remove()
        })
    };
    $.jqm.msg = function(b) {
        var d = {
            w : 200,
            self : null,
            title : "确认",
            content : "内容",
            type : "success",
            onConfirm : null,
            overlay : 20,
            overlayClass : "whiteOverlay",
            closeClass : "jqmClose",
            retrieveTop : undefined,
            onShow : undefined,
            onHide : function() {
                return true
            }
        };
        d.w = b.w ? b.w : d.w;
        d.self = b.self ? b.self : d.self;
        d.title = b.title ? b.title : d.title;
        d.content = b.content ? b.content : d.content;
        d.type = b.type ? b.type : d.type;
        d.onConfirm = b.onConfirm ? b.onConfirm : d.onConfirm;
        d.overlay = b.overlay ? b.overlay : d.overlay;
        d.overlayClass = b.overlayClass ? b.overlayClass : d.overlayClass;
        d.jqmClose = b.jqmClose ? b.closeClass : d.closeClass;
        d.onHide = b.onHide ? b.onHide : d.onHide;
        d.retrieveTop = b.retrieveTop ? b.retrieveTop : d.retrieveTop;
        var a = {
            overlay : d.overlay,
            overlayClass : d.overlayClass,
            retrieveTop : d.retrieveTop,
            closeClass : d.closeClass
        };
        if ($("#jqmMsg").length) {
            $("#jqmMsg").remove()
        }
        var c = '<div id="jqmMsg" class="modal jqMsg '
            + d.type
            + '"style="width:'
            + d.w
            + 'px;"><h3 class="jqTit"><a href=""class="modalClose '
            + a.closeClass
            + '">&#20851;&#38381;</a></h3><div class="jqMsgCon clearfix"><i></i><div class="msgHtml">'
            + d.content + "</div></div></div>";
        $("body").append(c);
        $("#jqmMsg").jqm(a);
        $("#jqmMsg").jqmShow();
        $("#jqmMsg ." + a.closeClass + "").click(function() {
            d.onHide();
            $("#jqmMsg").jqmHide().remove()
        })
    };
    $.jqm.loading = function(c) {
        var d = {
            content : "",
            size : 32,
            overlay : 10,
            overlayClass : "whiteOverlay",
            w : 200
        };
        d.w = c.w ? c.w : d.w;
        d.content = c.content ? c.content : d.content;
        d.size = c.size ? c.size : d.size;
        d.overlay = c.overlay ? c.overlay : d.overlay;
        d.overlayClass = c.overlayClass ? c.overlayClass : d.overlayClass;
        var a = {
            overlay : d.overlay,
            overlayClass : d.overlayClass,
            closeClass : d.closeClass
        };
        //2012-10-24 战勇（zhanyong@360buy.com）添加组件ID定义部分，解决同一页面加载多个组件隐藏失败问题
        var loadinPluginId='jqmLoading';
        if(typeof(c.jqmId)!="undefined"){
            loadinPluginId=c.jqmId;
        }
        if ($("#"+loadinPluginId).length) {
            $("#"+loadinPluginId).remove()
        }
        var b = '<div id="'+loadinPluginId+'" style="width:' + d.w
            + 'px;"class="jqmLoading hide"><span class="loading loading_'
            + d.size + '"></span><span style="line-height:' + d.size
            + 'px;" class="ml10 font1">' + d.content + "</span></div>";
        $("body").append(b);
        $("#"+loadinPluginId).jqm(a).jqDrag(".jqDrag");
        $("#"+loadinPluginId).jqmShow()
    };
    $.jqm.oneConfirm = function(c) {
        var d = {
            w : 200,
            self : null,
            title : "确认",
            content : "内容",
            type : "success",
            onConfirm : null,
            overlay : 20,
            overlayClass : "whiteOverlay",
            closeClass : "jqmClose",
            retrieveTop : undefined,
            onShow : undefined,
            onHide : function() {
                return true
            }
        };
        d.w = c.w ? c.w : d.w;
        d.self = c.self ? c.self : d.self;
        d.title = c.title ? c.title : d.title;
        d.content = c.content ? c.content : d.content;
        d.type = c.type ? c.type : d.type;
        d.onConfirm = c.onConfirm ? c.onConfirm : d.onConfirm;
        d.overlay = c.overlay ? c.overlay : d.overlay;
        d.overlayClass = c.overlayClass ? c.overlayClass : d.overlayClass;
        d.jqmClose = c.jqmClose ? c.closeClass : d.closeClass;
        d.onHide = c.onHide ? c.onHide : d.onHide;
        d.retrieveTop = c.retrieveTop ? c.retrieveTop : d.retrieveTop;
        var b = {
            overlay : d.overlay,
            overlayClass : d.overlayClass,
            retrieveTop : d.retrieveTop,
            closeClass : d.closeClass
        };
        if ($("#jqmConfirm").length) {
            $("#jqmConfirm").remove()
        }
        var a = '<div id="jqmAlert" style="width:'
            + d.w
            + 'px;"class="modal"><h1 class="tl"><span class="tr"><span class="tit">'
            + d.title
            + '</span><span class="modalClose '
            + b.closeClass
            + '">&#20851;&#38381;</span></span></h1><div class="moadalCon"><div class="clearfix fakeMsg '
            + d.type
            + '"><i class="ico"></i><div class="conText">'
            + d.content
            + '</div></div></div><div class="modalFooter pb20"><a href="javascript:void(0);"id="jqmAlertBtn" class="btn '
            + b.closeClass
            + '"><s><b><span>&#20851;&emsp;&#38381;</span></b></s></a></div><div class="bl"><div class="br"></div></div></div>';
        $("body").append(a);
        $("#jqmAlert").jqm(b);
        $("#jqmAlert").jqmShow();
        $("#jqmAlert").find(".modalFooter").find("span").html("确&nbsp;认");
        $("#jqmAlert ." + b.closeClass + "").click(function() {
            d.onHide();
            $("#jqmAlert").jqmHide().remove()
        })
    };
    return $.jqm;

}));

