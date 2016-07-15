
function errorShow(error, element) {

	$(element).next('.errorCon').find('.w1000').append(error);
	error.css('width','auto');
	var _width= error.outerWidth()+10;
	error.css('width',0);
	
	error.cluetip({
		cluetipClass: 'jtip',
		titleAttribute:'errorText',
		width:_width, 
		arrows: true, 
		showTitle:false,
		dropShadow: false,
		hoverIntent: false,
		cluezIndex: 999999,
		splitTitle: '|'
   });
}

var formValidator = {

	errorCon:'errorCon',
	success:function(label){
		$(label).css('margin-left','20px').remove();
	},
	unsuccess: function(error, element){
		errorShow(error, element);
	},
	errorPlacement: function(error, element){
		errorShow(error, element)
	}
};