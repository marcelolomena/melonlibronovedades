function init(){
    $('body').append(
            $('<div>').attr('id', 'loading').append(
                $('<img>').attr('src', '../images/ajax_loading.gif').attr('alt', 'Cargando...')
              ).css({
                  position: 'fixed',
                  display: 'none'
              })
    );	
}

function imprimir(id){
	$(id).printArea();
}

function resizeIframe() {

    var width = ((window.innerHeight) ? window.innerWidth : document.body.clientWidth);
    var height = ((window.innerHeight) ? window.innerHeight : document.body.clientHeight);
    height -= document.getElementById('externalSite').offsetTop;
    
    // not sure how to get this dynamically
    height -= 200; /* whatever you set your body bottom margin/padding to be */
    document.getElementById('externalSite').style.height = height +"px";

    document.getElementById('externalSite').style.width = width +"px";

            
}

function abre(url,titulo) {
    $("<iframe id=\"externalSite\" width=\"100%\" height=\"200px\" class=\"externalSite\" onload=\"resizeIframe()\" src='" + url + "' />").dialog({
        title: titulo,
        autoOpen: true,
        modal: true,
        resizable: true,
        position: [100,100],
        autoResize: true,
        overlay: {
            opacity: 0.5,
            background: "black"
        }, close: function (event, ui) {
        	var ifID = document.getElementById('externalSite');
        	ifID.parentNode.removeChild(ifID);
        }
    });
}

function abreURL(url,titulo,hdiv,vdiv,hpad,vpad) {
    var h = ((window.innerHeight) ? window.innerWidth : document.body.clientWidth);
    var v = ((window.innerHeight) ? window.innerHeight : document.body.clientHeight);
    var h1 = Math.ceil(h - (h * hdiv/100) );//2
    var v1 = Math.ceil(v - (v * vdiv/100) ); //4

    $('<iframe id="externalSite" class="externalSite" src="' + url + '" />').dialog({
        title: titulo,
        autoOpen: true,
        width: h1, 
        height: v1, 
        modal: true,
        resizable: true,
        autoResize: true,
        overlay: {
            opacity: 0.5,
            background: "black"
        }, close: function (event, ui) {
        	var ifID = document.getElementById('externalSite');
        	ifID.parentNode.removeChild(ifID);
        }
    }).width(h1 - hpad).height(v1 - vpad);
}

function ltrim(s) {
   return s.replace(/^\s+/, "");
}

function rtrim(s) {
   return s.replace(/\s+$/, "");
}

function trim(s) {
   return rtrim(ltrim(s));
}

function callAjax(obj){
    $.ajax({
        async: ((obj.async!=undefined)?obj.async:true),
        cache: false,
        type: "POST",
        url: obj.service,
        data: obj.params, 
		dataType : "json",
        timeout: 60000,
        success: function(data){
            try{
                obj.fx(data);
             }catch(ex2){
                Alerta("Fallo al ejecutar función de callback ["+obj.service+"] ["+ex2.message+"]");
             }
        },beforeSend:function(obj){
        	var nTop =eval(($(window).height()-32)/2);
        	var nLeft =eval(($(window).width()-32)/2);
        	$("#loading").css('top',nTop);
        	$("#loading").css('left',nLeft); 
        	$("#loading").show();
        },      
        error:function(data){
            if(data.responseText){
            	Alerta("Error [callWSjson]:"+data.responseText);
            }
            if(obj.errorfx){
                obj.errorfx(data);
            }else{
                if(!data.responseText){
                    Alerta("Error :"+data);
                 }
            }
        },
        complete:function(req,txtsts){
        	$("#loading").hide();
        }
    });
}
function Alerta(msg) {
	var $dialog = $('<div></div>')
	.html(msg)
	.dialog({
	    bgiframe: true,
	    resizable: false,
	    draggable: true,
	    autoOpen: false,
	    title:'Alerta',
	    height: 240,
	    modal: true,
	    overlay: {
	        backgroundColor: '#000',
	        opacity: 0.5
	    },
	    buttons: {
	        'Cerrar': function () {
	            $(this).dialog('close');
	        }
	    }
	});
    $dialog.dialog('open');
}

function SuperAlerta(msg,callback) {
	var $dialog = $('<div></div>')
	.html(msg)
	.dialog({
	    bgiframe: true,
	    resizable: false,
	    draggable: true,
	    autoOpen: false,
	    title:'Alerta',
	    height: 240,
	    modal: true,
	    overlay: {
	        backgroundColor: '#000',
	        opacity: 0.5
	    },
	    buttons: {
	        'Cerrar': function () {
	    		callback();
	            $(this).dialog('close');
	        }
	    }
	});
    $dialog.dialog('open');
}

function runEffect(capa) {

	var selectedEffect = 'clip';

	var options = {};

	if ( selectedEffect === "scale" ) {
		options = { percent: 0 };
	} else if ( selectedEffect === "size" ) {
		options = { to: { width: 200, height: 60 } };
	}

	$( capa ).toggle( selectedEffect, options, 500 );
};

function abreDialogo(msg,callback) {
	var $dialog = $('<div></div>')
	.html(msg)
	.dialog({
	    bgiframe: true,
	    resizable: false,
	    draggable: true,
	    autoOpen: false,
	    height: 140,
	    modal: true,
	    overlay: {
	        backgroundColor: '#000',
	        opacity: 0.5
	    },
	    buttons: {
	        'Si': function () {
	    		callback();
	            $(this).dialog('close');
	        },
	        'No': function () {
	            $(this).dialog('close');
	        }
	    }
	});
    $dialog.dialog('open');
}


function hoydia(){
	var date = new Date();
	var d  = date.getDate();
	var day = (d < 10) ? '0' + d : d;
	var m = date.getMonth() + 1;
	var month = (m < 10) ? '0' + m : m;
	var yy = date.getYear();
	var year = (yy < 1000) ? yy + 1900 : yy;
	return day + "/" + month + "/" + year;
}

function closeIframe()
{
    $('#externalSite').dialog('close');
    return false;
}

function fechaformat(fechita){
	var elem=fechita.split('T');
	var valores=elem[0].split('-');
	var fecha =new Date(valores[0],valores[1]-1,valores[2]);
	var yyyy = fecha.getFullYear();
	var mm = (fecha.getMonth()+1 < 10) ? "0" + (fecha.getMonth() + 1) : (fecha.getMonth() + 1);
	var dd = (fecha.getDate() < 10) ? "0" + fecha.getDate() : fecha.getDate();
	return dd + "/" + mm + "/" + yyyy;
}

function UTCtoDate(s){
	s = s.replace(/\D/g," ");
	var date_parts = s.split(" ");
	var yyyy = date_parts[0],
	mm = date_parts[1] - 1,
	dd = date_parts[2],
	hh = date_parts[3],
	mi = date_parts[4],
	ss = date_parts[5];
	var dt = new Date(yyyy, mm, dd, hh, mi, ss);
	return dt;
}
