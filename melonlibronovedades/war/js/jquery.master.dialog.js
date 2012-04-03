/*
 * jQuery Master Dialog
 *
 * This plugins extends jQuery UI Dialog widget, adding minimize, restore and maximize buttons
 * Developed by Carlos Carvalhar - http://carvalhar.com when working at PixFly -> http://pixfly.com.br
 *
 * This code was based in two resources:
 * http://www.profissionaisti.com.br/2008/11/jquery-dialog-melhorias-interessantes  by Jackson Caset
 * http://old.nabble.com/How-to-%22properly%22-extend-the-jQuery-UI-Dialog-widget-----td24182676s27240.html
 *  
 *  
 * Depends:
 *	jQuery  (http://jquery.com)
 *  jQuery UI (http://jqueryui.com/demos/dialog)
 *
 */


(function($) {
		  
   // $.widget("ui.mywidget", {_init: function() {}});
   var _init = $.ui.dialog.prototype._init;
   $.ui.dialog.prototype._init = function() {	
		
        var self = this;
        _init.apply(this, arguments);	
		
		uiDialogTitlebar = this.uiDialogTitlebar;
		this.originalSize();

		/* Adding maximize button */
		if (self.options.maximized) {	 			
			uiDialogTitlebar.append('<a href="#" id="dialog-maximize" class="ui-dialog-titlebar-max"><span>Max</span></a>');
			this.uiDialogTitlebarMax = $('#dialog-maximize', uiDialogTitlebar).hover(function(){
				$(this).addClass('ui-dialog-titlebar-max-hover');
			}, function(){
				$(this).removeClass('ui-dialog-titlebar-max-hover');
			}).mousedown(function(ev){
				ev.stopPropagation();
			}).click(function(){
				self.maximize();
				return false;
			});
			
			/* Allow titlebar doubleclick to maximize/restore the dialog. */
			uiDialogTitlebar.bind("dblclick", function() {
				if(self.maximized) {
					self.restore();
				} else {
					self.maximize();	
				}
			});	
		}
		
		/* Adding minimize button */
			
		if (self.options.minimized) {
			uiDialogTitlebar.append('<a href="#" id="dialog-minimize" class="ui-dialog-titlebar-min"><span>Min</span></a>');
			this.uiDialogTitlebarMax = $('#dialog-minimize', uiDialogTitlebar).hover(function(){
				$(this).addClass('ui-dialog-titlebar-min-hover');
			}, function(){
				$(this).removeClass('ui-dialog-titlebar-min-hover');
			}).mousedown(function(ev){
				ev.stopPropagation();
			}).click(function(){
				self.minimize();
				return false;
			});
			
			
		}
		
		/* Adding restore button */
		
		if (self.options.minimized || self.options.maximized ) {
		
			uiDialogTitlebar.append('<a href="#" id="dialog-restore" class="ui-dialog-titlebar-rest"><span>Restore</span></a>');
				this.uiDialogTitlebarMin = $('#dialog-restore', uiDialogTitlebar).hover(function(){
					$(this).addClass('ui-dialog-titlebar-rest-hover');
				}, function(){
					$(this).removeClass('ui-dialog-titlebar-rest-hover');
				}).mousedown(function(ev){
					ev.stopPropagation();
				}).click(function(){
					self.restore();
					return false;
				}).hide();
		}
				
    };
    $.ui.dialog.defaults.maximized = true;
	$.ui.dialog.defaults.minimized = true;
	


	$.extend($.ui.dialog.prototype, {
			 
			 
	/* override resize */
/*	resize: function() {
		alert("oi " + self.minimized);
		if (!self.minimized) {
			$.widget.prototype.destroy.apply(this, arguments);
		}
	},	*/
		
	/* Allow restore the dialog */
	restore: function() {
		this.maximized=false; /* reset both states (restored) */
		this.minimized=false;
		$('.ui-dialog-content').show();
		this.uiDialog.css({width: this.options.width, height:this.options.height});
		this.size();
		$('#dialog-restore').css('right','1.5em');
		this.adjustScrollContent();		
		this.position(this.options.position);
		
		$('#dialog-maximize').show();
		$('#dialog-restore').hide();
		$('#dialog-minimize').show();
		this.uiDialog.css('position','absolute'); 
		/* this.uiDialog.data("resizable", true);
		this.uiDialog.data("draggable", true);
		this.uiDialog.addClass('ui-draggable ui-resizable ui-resizable-resizing');*/
	},	
	
	/* Minimize to a custom position */
	minimize: function() {		
		this.minimized=true; /* save the current state: minimized */
		this.maximized=false;
		//this.originalSize();
		$('.ui-dialog-content').hide();
		$('#dialog-restore').css('right','2.8em');
		this.uiDialog.css('position','fixed'); /* sticky the dialog at the page to avoid scrolling */		
		this.uiDialog.animate({left:"10px", width: 250, height: 100, bottom:"10px"});
		this.uiDialog.css('top','auto'); /* needed because top has a default value and this breaks bottom value */
		this.size();
		this.adjustScrollContent();
		$('#dialog-restore').show();
		$('#dialog-maximize').show();
		$('#dialog-minimize').hide();
		
		this.uiDialog.dialog("close");
		
		/*this.uiDialog.data("resizable", false);
		this.uiDialog.data("draggable", false);
		this.uiDialog.removeClass('ui-draggable ui-resizable ui-resizable-resizing');
		this.options.resizable  = false;
		alert(this.options.resizable);*/
	},
	
	/* Maximize to the whole visible size of the window */
	maximize: function() {		
		this.maximized=true; /* save the current state: maximized */
		this.minimized=false;
		//this.originalSize();
		$('.ui-dialog-content').show();
		$('#dialog-restore').css('right','1.5em');
		this.uiDialog.animate({left:0, top:0, width: $(window.body).width()-15, height:$(window).height()-15});
		this.size();
		this.adjustScrollContent();
		
		$('#dialog-restore').show();
		$('#dialog-maximize').hide();
		$('#dialog-minimize').show();
		this.uiDialog.css('position','absolute'); 
		this.uiDialog.data("resizable", true);
		this.uiDialog.data("draggable", true);
	},
	
	/* Store the size of dialog, before it gets minimized or maximized */
	originalSize: function() {		
		this.options.height = this.uiDialog.height();
		this.options.width = this.uiDialog.width();
	},
	
	/* Saves all css related to the dialog position before maximize or minimize */
	position: function(pos) {
		var wnd = $(window), doc = $(document),
			pTop = doc.scrollTop(), pLeft = doc.scrollLeft(),
			minTop = pTop;
		
		if ($.inArray(pos, ['center','top','right','bottom','left']) >= 0) {
			pos = [
				pos == 'right' || pos == 'left' ? pos : 'center',
				pos == 'top' || pos == 'bottom' ? pos : 'middle'
			];
		}
		if (pos.constructor != Array) {
			pos = ['center', 'middle'];
		}
		if (pos[0].constructor == Number) {
			pLeft += pos[0];
		} else {
			switch (pos[0]) {
				case 'left':
					pLeft += 0;
					break;
				case 'right':
					pLeft += wnd.width() - this.uiDialog.width();
					break;
				default:
				case 'center':
					pLeft += (wnd.width() - this.uiDialog.width()) / 2;
			}
		}
		if (pos[1].constructor == Number) {
			pTop += pos[1];
		} else {
			switch (pos[1]) {
				case 'top':
					pTop += 0;
					break;
				case 'bottom':
					pTop += wnd.height() - this.uiDialog.height();
					break;
				default:
				case 'middle':
					pTop += (wnd.height() - this.uiDialog.height()) / 2;
			}
		}
		
		// prevent the dialog from being too high (make sure the titlebar is accessible)
		pTop = Math.max(pTop, minTop);
		this.uiDialog.css({top: pTop, left: pLeft});
	},

	size: function() {
		var container = this.uiDialogTitlebar.parent(),
			titlebar = this.uiDialogTitlebar,
			content = this.element,
			tbMargin = parseInt(content.css('margin-top'),10) + parseInt(content.css('margin-bottom'),10),
			lrMargin = parseInt(content.css('margin-left'),10) + parseInt(content.css('margin-right'),10);

		content.height(container.height() - titlebar.outerHeight() - tbMargin /* More precision on scroll content */ - 8);
		content.width(container.width() - lrMargin);
		
		
	},
	/* Adjuste the content inside the dialog on maximize/restore */
	adjustScrollContent: function () {
		$('.ui-dialog-content').css('width', this.uiDialog.width()-16, 'height', this.uiDialog.height()-16);
	}
		
	});
	
	
})(jQuery); 