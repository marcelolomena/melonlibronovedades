function loginLN(formInput){
	callAjax({
		async:true,
        service: "jsp/ingreso.action",
        params: formInput,
        fx: function(result){
        try{

            }catch(e){alert(e.message);}
        },
        errorfx: function(msg){
            alert("Error "+msg);
        }            
    }); 
}
function listarTipoLN(pariente){
	callAjax({
		async:true,
        service: "jsp/obtenerTipo.action",
        params: "",
        fx: function(result){
        try{
			var fila="";
			$.each(result.tipoList,function(index, value){
	 			fila+="<tr>";
	 			fila+="<td></td>";
	 			fila+="<td>"+value.descripcion+"</td>";
	 			fila+="<td align=\"center\"><a href='#' onclick=\"editarTipoLN(" + value.id + "); return false;\"><img src='../images/user_edit.png' alt='' title='' border='0' /></a></td>";
	 			fila+="<td align=\"center\"><a href='#' onclick=\"eliminarTipoLN(" + value.id + "); return false;\"><img src='../images/trash.png' alt='' title='' border='0'/></a></td>";
	 			fila+="</tr>";  		 		   
			});
				if(pariente){
					parent.$('table[id=tabla-item] tbody', parent.document).append(fila);
					parent.$('#externalSite', parent.document).dialog('close');
				}else{
					$("#tabla-item > tbody").append(fila);	
				}

            }catch(e){alert(e.message);}
        },
        errorfx: function(msg){
            alert("Error "+msg);
        }            
    }); 
}

function guardarTipoLN(formInput){
	callAjax({
		async:true,
	    service: "jsp/guardarTipo.action",
	    params: formInput,
	    fx: function(result){
	    try{
	    	parent.$('table[id=tabla-item] tbody tr', parent.document).addClass("borrar");
	    	parent.$('table[id=tabla-item] tbody tr.borrar', parent.document).remove();
	    	listarTipoLN(true);
	        }catch(ee){alert(ee.message);}
	    },
	    errorfx: function(msg){
	        alert("Error "+msg);
	    }            
	}); 
}

function actualizarTipoLN(formInput){
	callAjax({
		async:true,
	    service: "jsp/actualizarTipo.action",
	    params: formInput,
	    fx: function(result){
	    try{
	    	parent.$('table[id=tabla-item] tbody tr', parent.document).addClass("borrar");
	    	parent.$('table[id=tabla-item] tbody tr.borrar', parent.document).remove();
	    	listarTipoLN(true);
	        }catch(ee){alert(ee.message);}
	    },
	    errorfx: function(msg){
	        alert("Error "+msg);
	    }            
	}); 	
}

function editarTipoLN2(id){
	var formInput='id=' + id;
	callAjax({
		async:true,
	    service: "jsp/editarTipo.action",
	    params: formInput,
	    fx: function(result){
	    try{
			var horini =UTCtoDate(result.tipoLN.horainicio);

			var HH1 = (horini.getHours() < 10) ? "0" + horini.getHours() : horini.getHours();
			var mm1 = (horini.getMinutes() < 10) ? "0" + horini.getMinutes() : horini.getMinutes();
			
			var horter =UTCtoDate(result.tipoLN.horatermino);
			var HH2 = (horter.getHours() < 10) ? "0" + horter.getHours() : horter.getHours();
			var mm2 = (horter.getMinutes() < 10) ? "0" + horter.getMinutes() : horter.getMinutes();			
			
	    	$("#tipo").attr('value', result.tipoLN.descripcion);
	    	$("#id").attr('value', result.tipoLN.id);
	    	$("#orden").attr('value', result.tipoLN.orden);
			$("#horini").attr('value', HH1+':'+mm1);
			$("#horter").attr('value', HH2+':'+mm2);	    	
	        }catch(ee){alert(ee.message);}
	    },
	    errorfx: function(msg){
	        alert("Error "+msg);
	    }            
	}); 	
}
function eliminarTipoLN(id){
	abreDialogo('quiere borrar este turno?', function() { borrarTipoLN(id);});
}

function borrarTipoLN(id){
	var formInput='id=' + id;	
	callAjax({
		async:true,
	    service: "jsp/borrarTipo.action",
	    params: formInput,
	    fx: function(result){
	    try{
	    	$('table[id=tabla-item] tbody tr').addClass("borrar");
	    	$('table[id=tabla-item] tbody tr.borrar').remove();
	    	listarTipoLN(false);
	        }catch(ee){alert(ee.message);}
	    },
	    errorfx: function(msg){
	        alert("Error "+msg);
	    }            
	}); 	
}

function listarUsuarioLVS(pariente){
	callAjax({
		async:true,
        service: "jsp/obtenerUsuario.action",
        params: "",
        fx: function(result){
        try{
			var fila="";
			if(!$.isEmptyObject(result)){
				$.each(result.usuarioList,function(index, value){
		 			fila+="<tr>";
		 			if(index==0){
		 				fila+="<td><input type='radio' checked name='idUsuario' value='" + value.id +"'/></td>";
		 			}else{
		 				fila+="<td><input type='radio' name='idUsuario' value='" + value.id +"'/></td>";
		 			}
		 			fila+="<td>"+value.nombres+"</td>";
		 			fila+="<td>" + value.apepa+ "</td>";
		 			fila+="<td>" + value.cargo+ "</td>";
		 			fila+="<td align=\"center\"><a href='#' onclick=\"editarUsuarioLVS(" + value.id + "); return false;\"><img src='../images/user_edit.png' alt='' title='' border='0' /></a></td>";
		 			fila+="<td align=\"center\"><a href='#' onclick=\"eliminarUsuarioLVS(" + value.id + "); return false;\"><img src='../images/trash.png' alt='' title='' border='0'/></a></td>";
		 			fila+="</tr>";  		 		   
				});
			}else{
				Alerta('no hay datos');
			}
				if(pariente){
					parent.$('table[id=tabla-usuarios] tbody', parent.document).append(fila);
					parent.$('#externalSite', parent.document).dialog('close');
				}else{
			    	$('table[id=tabla-usuarios] tbody tr').addClass("borrar");
			    	$('table[id=tabla-usuarios] tbody tr.borrar').remove();	
					$("#tabla-usuarios > tbody").append(fila);	
				}
            }catch(e){Alerta(e.message);}
        },
        errorfx: function(msg){
        	Alerta("Error "+msg);
        }            
    }); 
}

function guardarUsuarioLVS(formInput){
	callAjax({
		async:true,
	    service: "jsp/guardarUsuario.action",
	    params: formInput,
	    fx: function(result){
	    try{
	    	parent.$('table[id=tabla-usuarios] tbody tr', parent.document).addClass("borrar");
	    	parent.$('table[id=tabla-usuarios] tbody tr.borrar', parent.document).remove();
	    	listarUsuarioLVS(true);
	        }catch(ee){alert(ee.message);}
	    },
	    errorfx: function(msg){
	        alert("Error "+msg);
	    }            
	}); 	
}

function editarUsuarioLVS2(id){
	var formInput='id=' + id;
	var filaNegocio="";
	callAjax({
		async:true,
	    service: "jsp/editarUsuario.action",
	    params: formInput,
	    fx: function(result){

	    try{
	    	$("#id").attr('value', result.usuarioLN.id);
	    	$("#nombres").attr('value', result.usuarioLN.nombres);
	    	$("#apepa").attr('value', result.usuarioLN.apepa);
	    	$("#apema").attr('value', result.usuarioLN.apema);
	    	$("#email").attr('value', result.usuarioLN.email);
	    	$("#cargo").attr('value', result.usuarioLN.cargo);
	    	
	    	$("#perfil option[value='" + result.usuarioLN.perfil + "']").attr('selected', true);	
	    	
			$.each(result.negocioList,function(index, value){
				filaNegocio += "<option value=\"" + value.id + "\">" + value.nombre + "</option>";
			});
			
			$("#idNegocio").html(filaNegocio);

	    	if(result.usuarioLN.idNegocio!=null){
	    		$("#idNegocio option[value='" + result.usuarioLN.idNegocio + "']").attr('selected', true);
	    	}	    	

	    	if(result.usuarioLN.correo!=null){
	    		if(result.usuarioLN.correo==true)
	    			$('input[name=correo]').attr('checked', true);
	    	}
	    	
	        }catch(ee){Alerta(ee.message);}
	    },
	    errorfx: function(msg){
	        Alerta("Error : "+msg);
	    }            
	}); 		
}

function nuevoUsuarioLN(){
	var filaNegocio="";
	callAjax({
		async:true,
	    service: "jsp/nuevoUsuario.action",
	    params: "",
	    fx: function(result){
	    try{
			$.each(result.negocioList,function(index, value){
				filaNegocio += "<option value=\"" + value.id + "\">" + value.nombre + "</option>";
			});	  
			$("#idNegocio").html(filaNegocio); 	

	        }catch(ee){Alerta(ee.message);}
	    },
	    errorfx: function(msg){
	        Alerta("Error : "+msg);
	    }            
	}); 		
}

function actualizarUsuarioLVS(formInput){
	callAjax({
		async:true,
	    service: "jsp/actualizarUsuario.action",
	    params: formInput,
	    fx: function(result){
	    try{
	    	parent.$('table[id=tabla-usuarios] tbody tr', parent.document).addClass("borrar");
	    	parent.$('table[id=tabla-usuarios] tbody tr.borrar', parent.document).remove();
	    	listarUsuarioLVS(true);
	        }catch(ee){alert(ee.message);}
	    },
	    errorfx: function(msg){
	        alert("Error "+msg);
	    }            
	}); 	
}

function eliminarUsuarioLVS(id){
	abreDialogo('quiere borrar este usuario', function() { borrarUsuarioLVS(id); });
}

function borrarUsuarioLVS(id){
	var formInput='id=' + id;	
	callAjax({
		async:true,
	    service: "jsp/borrarUsuario.action",
	    params: formInput,
	    fx: function(result){
	    try{
	    	$('table[id=tabla-usuarios] tbody tr').addClass("borrar");
	    	$('table[id=tabla-usuarios] tbody tr.borrar').remove();
	    	listarUsuarioLVS(false);
	        }catch(ee){alert(ee.message);}
	    },
	    errorfx: function(msg){
	        alert("Error "+msg);
	    }            
	}); 	
}

function listarNegocioLVS(pariente){
	callAjax({
		async:true,
        service: "jsp/obtenerNegocio.action",
        params: "",
        fx: function(result){
        try{
			var fila="";
			if(!$.isEmptyObject(result)){
				$.each(result.negocioList,function(index, value){
					
		 			fila+="<tr>";
		 			if(index==0){
		 				fila+="<td><input type='radio' checked name='idNegocio' value='" + value.id +"'/></td>";
		 			}else{
		 				fila+="<td><input type='radio' name='idNegocio' value='" + value.id +"'/></td>";
		 			}
		 			fila+="<td>"+value.nombre+"</td>";
		 			fila+="<td align=\"center\"><a href='#' onclick=\"editarNegocioLVS(" + value.id + "); return false;\"><img src='../images/user_edit.png' alt='' title='' border='0' /></a></td>";
		 			fila+="<td align=\"center\"><a href='#' onclick=\"eliminarNegocioLVS(" + value.id + "); return false;\"><img src='../images/trash.png' alt='' title='' border='0'/></a></td>";
		 			fila+="</tr>";  		 		   
				});
			}else{
				Alerta('no hay datos');
			}
				if(pariente){
					parent.$('table[id=rounded-corner] tbody', parent.document).append(fila);
					parent.$('#externalSite', parent.document).dialog('close');
				}else{
			    	$('table[id=rounded-corner] tbody tr').addClass("borrar");
			    	$('table[id=rounded-corner] tbody tr.borrar').remove();					
					$("#rounded-corner > tbody").append(fila);	
				}
            }catch(e){Alerta(e.message);}
        },
        errorfx: function(msg){
        	Alerta("Error "+msg);
        }            
    }); 
}

function eliminarNegocioLVS(idNegocio){
	abreDialogo('al borrar el negocio se borra también la informacion relacionada', function() { borrarNegocioLVS(idNegocio); });
}

function borrarNegocioLVS(idNegocio){
	var formInput='id=' + idNegocio;
	callAjax({
		async:true,
	    service: "jsp/borrarNegocio.action",
	    params: formInput,
	    fx: function(result){
	    try{
	    	$('table[id=rounded-corner] tbody tr').addClass("borrar");
	    	$('table[id=rounded-corner] tbody tr.borrar').remove();
	    	listarNegocioLVS(true);
	        }catch(ee){alert(ee.message);}
	    },
	    errorfx: function(msg){
	        alert("Error "+msg);
	    }            
	}); 	
}

function guardarNegocioLVS(formInput){
	callAjax({
		async:true,
	    service: "jsp/guardarNegocio.action",
	    params: formInput,
	    fx: function(result){
	    try{
	    	parent.$('table[id=rounded-corner] tbody tr', parent.document).addClass("borrar");
	    	parent.$('table[id=rounded-corner] tbody tr.borrar', parent.document).remove();
	    	listarNegocioLVS(true);
	        }catch(ee){alert(ee.message);}
	    },
	    errorfx: function(msg){
	        alert("Error "+msg);
	    }            
	}); 	
}

function editarNegocioLVS2(id){
	var formInput='id=' + id;
	callAjax({
		async:true,
	    service: "jsp/editarNegocio.action",
	    params: formInput,
	    fx: function(result){
	    try{
	    	$("#id").attr('value', result.negocioLN.id);
	    	$("#nombre").attr('value', result.negocioLN.nombre);
	        }catch(ee){alert(ee.message);}
	    },
	    errorfx: function(msg){
	        alert("Error "+msg);
	    }            
	}); 		
}

function actualizarNegocioLVS(formInput){
	callAjax({
		async:true,
	    service: "jsp/actualizarNegocio.action",
	    params: formInput,
	    fx: function(result){
	    try{
	    	parent.$('table[id=rounded-corner] tbody tr', parent.document).addClass("borrar");
	    	parent.$('table[id=rounded-corner] tbody tr.borrar', parent.document).remove();
	    	listarNegocioLVS(true);
	        }catch(ee){alert(ee.message);}
	    },
	    errorfx: function(msg){
	        Alerta(msg);
	    }            
	}); 	
}

function listarSitioLVS(pariente,id){
	var formInput='idNegocio=' + id;
	callAjax({
		async:true,
        service: "jsp/obtenerEquipo.action",
        params: formInput,
        fx: function(result){
        try{
			var fila="";
			if(!$.isEmptyObject(result)){
				$.each(result.equipoList,function(index, value){
		 			fila+="<tr>";
		 			fila+="<td></td>";
		 			fila+="<td>"+value.nombre+"</td>";
		 			fila+="<td align=\"center\"><a href='#' onclick=\"editarSitioLVS(" + value.key.id + "); return false;\"><img src='../images/user_edit.png' alt='' title='' border='0' /></a></td>";
		 			fila+="<td align=\"center\"><a href='#' onclick=\"eliminarSitioLVS(" + id + "," + value.key.id + "); return false;\"><img src='../images/trash.png' alt='' title='' border='0'/></a></td>";
		 			fila+="</tr>";  		 		   
				});
			}else{
				Alerta('no hay datos');
			}
				$('#titulo').html("Lista Equipos : " + result.negocioLN.nombre);
				if(pariente){
					parent.$('table[id=tabla-sitios] tbody', parent.document).append(fila);
					parent.$('#externalSite', parent.document).dialog('close');
				}else{
			    	$('table[id=tabla-sitios] tbody tr').addClass("borrar");
			    	$('table[id=tabla-sitios] tbody tr.borrar').remove();						
					$("#tabla-sitios > tbody").append(fila);	
				}
            }catch(e){Alerta(e.message);}
        },
        errorfx: function(msg){
        	Alerta("Error "+msg);
        }            
    }); 
}
function eliminarSitioLVS(idNegocio,id){
	abreDialogo('quiere borrar este equipo?', function() { borrarSitioLVS(idNegocio,id); });
}

function borrarSitioLVS(idNegocio,idEquipo){
	var formInput='idNegocio=' + idNegocio + '&idEquipo=' + idEquipo;
	callAjax({
		async:true,
	    service: "jsp/borrarEquipo.action",
	    params: formInput,
	    fx: function(result){
	    try{
	    	$('table[id=tabla-sitios] tbody tr').addClass("borrar");
	    	$('table[id=tabla-sitios] tbody tr.borrar').remove();
	    	listarSitioLVS(true,idNegocio);
	        }catch(ee){alert(ee.message);}
	    },
	    errorfx: function(msg){
	        alert("Error "+msg);
	    }            
	}); 	
}

function guardarSitioLVS(formInput,idNegocio){
	callAjax({
		async:true,
	    service: "jsp/guardarEquipo.action",
	    params: formInput,
	    fx: function(result){
	    try{
	    	parent.$('table[id=tabla-sitios] tbody tr', parent.document).addClass("borrar");
	    	parent.$('table[id=tabla-sitios] tbody tr.borrar', parent.document).remove();
	    	listarSitioLVS(true,idNegocio);
	        }catch(ee){alert(ee.message);}
	    },
	    errorfx: function(msg){
	        alert("Error "+msg);
	    }            
	}); 	
}

function editarSitioLVS2(idNegocio,id){
	var formInput='idNegocio=' + idNegocio + '&idEquipo=' + id;
	callAjax({
		async:true,
	    service: "jsp/editarEquipo.action",
	    params: formInput,
	    fx: function(result){
	    try{
	    	$("#nombre").attr('value', result.equipoLN.nombre);
	        }catch(ee){alert(ee.message);}
	    },
	    errorfx: function(msg){
	        alert("Error "+msg);
	    }            
	}); 		
}

function actualizarSitioLVS(formInput,idNegocio){
	callAjax({
		async:true,
	    service: "jsp/actualizarEquipo.action",
	    params: formInput,
	    fx: function(result){
	    try{
	    	parent.$('table[id=tabla-sitios] tbody tr', parent.document).addClass("borrar");
	    	parent.$('table[id=tabla-sitios] tbody tr.borrar', parent.document).remove();
	    	listarSitioLVS(true,idNegocio);
	        }catch(ee){alert(ee.message);}
	    },
	    errorfx: function(msg){
	        Alerta(msg);
	    }            
	}); 	
}

function verDoc(blobkey){
	abreURL("/serve?blob-key=" + blobkey,"Documento", 35, 40, 30, 30);
}

function listarVisitasLVS(pariente,idUsuario){
	var formInput='idUsuario=' + idUsuario;
	callAjax({
		async:true,
        service: "jsp/obtenerNovedad.action",
        params: formInput,
        fx: function(result){
        try{
			var fila="";
			if(!$.isEmptyObject(result.novedadList)){
				$.each(result.novedadList,function(index, value){
					fila+="<tr>";
					fila+="<td></td>";
		 			fila+="<td align=\"left\">" + fechaformat(value.fecha) + "</td>";
		 			fila+="<td align=\"left\">" + value.turnoNombre + "</td>";
		 			fila+="<td align=\"center\"><a href='#' onclick=\"editarVisitaLVS(" + idUsuario + "," + value.key.id + "); return false;\"><img src='../images/user_edit.png' alt='' title='' border='0' /></a></td>";
		 			fila+="<td align=\"center\"><a href='#' onclick=\"eliminarVisitaLVS("+ idUsuario + "," + value.key.id+"); return false;\"><img src='../images/trash.png' alt='' title='' border='0'/></a></td>";
		 			fila+="</tr>";  		 		   
				});
				
			}else{
				Alerta('no hay datos');
			}

				if(pariente){
					parent.$('table[id=tabla-visitas] tbody tr').addClass("borrar");
					parent.$('table[id=tabla-visitas] tbody tr.borrar').remove();		
					$('#titulo').html("novedades de : " + result.usuarioLN.nombres + " " + result.usuarioLN.apepa)
					parent.$('table[id=tabla-visitas] tbody', parent.document).append(fila);
					parent.$('#externalSite', parent.document).dialog('close');
				}else{
			    	$('table[id=tabla-visitas] tbody tr').addClass("borrar");
			    	$('table[id=tabla-visitas] tbody tr.borrar').remove();
					$('#titulo').html("novedades de : " + result.usuarioLN.nombres + " " + result.usuarioLN.apepa)
					$("#tabla-visitas > tbody").append(fila);	
				}
				
            }catch(e){Alerta(e.message);}
        },
        errorfx: function(msg){
        	Alerta("Error "+msg);
        }            
    }); 
}

function getJSONValue(id,result){
	var nombre="";
	$.each(result.equipoList,function(index, value){
		if(id==value.key.id){
			nombre=value.nombre;
			return  false;
		}
	});	
	return nombre;
}

function editarVisitaLVS2(idUsuario,idVisita){
	var formInput='idUsuario=' + idUsuario + '&idVisita=' + idVisita;
	callAjax({
		async:true,
	    service: "jsp/editarNovedad.action",
	    params: formInput,
	    fx: function(result){
	    try{
			var filaEquipo="";
			var filaTipo="";
			var filaFotos="";
			var tieneFoto=0;

			if(!$.isEmptyObject(result.bitacoraList)){
				filaEquipo +="<ul>";
				$.each(result.bitacoraList,function(index, value){
					tieneFoto++;
					filaEquipo +="<li>";
					filaEquipo +="<a href=\"#tabs-" + value.equipo + "\">" + getJSONValue(value.equipo,result) + "</a>";
					filaEquipo +="</li>";					
				});			
				filaEquipo +="</ul>";			
			}
			
				if(!$.isEmptyObject(result.fotitos)){
					filaFotos +="<ul>";
					$.each(result.fotitos,function(index, value){
						var url="'/filedownload?id=" + value.key.id +"'";					
						filaFotos +="<li>";
						filaFotos +="<a href=\"javascript:abreURL(" + url + ",'Foto', 50, 50, 30, 30)" + "\">foto</a>";
						filaFotos +="</li>";					
					});
					filaFotos +="</ul>";
				}				
				
				if(!$.isEmptyObject(result.bitacoraList)){
					$.each(result.bitacoraList,function(index, value){			
						filaEquipo +="<div id=\"tabs-" + value.equipo + "\">";
						filaEquipo +="<input type=\"hidden\" id=\"tab" + value.equipo + "Selected\"/>";
						//filaEquipo +="<div style=\"float:left;\"><textarea rows=\"10\" cols=\"50\" name=\"comentario_" + value.equipo + "\" id=\"comentario" + value.equipo + "\">" + value.comentario+ "</textarea></div>";
						filaEquipo +="<p><textarea rows=\"10\" cols=\"50\" name=\"comentario_" + value.equipo + "\" id=\"comentario" + value.equipo + "\">" + value.comentario+ "</textarea>";
						filaEquipo +="<div id=\"file-uploader-" + value.equipo + "\"></div></p>"				
						filaEquipo +="</div>";	
					});			
				}
				$.each(result.tipoList,function(index, value){
					filaTipo += "<option value=\"" + value.id + "\">" + value.descripcion + "</option>";
				});					

			var fecha =UTCtoDate(result.novedadLN.fecha);
				
			var yyyy = fecha.getFullYear();
			var mm = (fecha.getMonth() < 10) ? "0" + (fecha.getMonth() + 1) : (fecha.getMonth() + 1);
			var dd = (fecha.getDate() < 10) ? "0" + fecha.getDate() : fecha.getDate();

			if(tieneFoto>0){			
				$("#fotos").html(filaFotos);
			}
			
			$("#fecha").attr('value', dd + "/" + mm + "/" + yyyy);
			$("#tabs").html(filaEquipo);
			$("#tipo").html(filaTipo);
	    	if(result.novedadLN.turno!=null){
	    		$("#tipo option[value='" + result.novedadLN.turno + "']").attr('selected', true);
	    	}
	    	$("#empleado").attr('value', result.novedadLN.empleado);
	    	
	        $("#tabs").tabs({ selected: 0,event: "mouseover" }).addClass('ui-tabs-vertical ui-helper-clearfix');
	        $("#tabs li").removeClass('ui-corner-top').addClass('ui-corner-left');
	        //$('#tabs').bind('tabsselect', function (event, ui) {
	        //});
	        
		      $.each(result.bitacoraList,function(index, value){
			      new qq.FileUploader({
			    	    element: document.getElementById('file-uploader-' + value.equipo),
			    	    action: '/fileupload',
			    	    allowedExtensions: ['jpg', 'jpeg', 'gif'],
			    	    sizeLimit: 512*1024,
			    	    params: {
			    	  		idEquipo: value.equipo,
			    	        idUsuario: idUsuario,
			    	        idVisita: idVisita
			    	    },
			            onComplete: function(id, fileName, responseJSON){
			    	    	if(!responseJSON.success){alert(responseJSON.errorMessage);}
			    	    }			    	    
			      }); 
		      });	        
	        
	        }catch(ee){alert(ee.message);}
	    },
	    errorfx: function(msg){
	        alert("Error "+msg);
	    }            
	}); 		
}
function guardarNovedad(formInput,idUsuario){
	callAjax({
		async:true,
	    service: "/novedad",
	    params: formInput,
	    fx: function(result){
	    try{
	    	if(!result.success){
	    		SuperAlerta("<p><span class='ui-icon ui-icon-alert' style='float:left; margin:0 7px 20px 0;'></span>" + result.error + "</p>", function() { listarVisitasLVS(true,idUsuario); });
	    	}else{
	    		listarVisitasLVS(true,idUsuario);
	    	}
	        }catch(ee){alert(ee.message);}
	    },
	    errorfx: function(msg){
	        alert(msg);
	    }            
	}); 
}

function ingresoNovedad(idUsuario){
	var formInput='idUsuario=' + idUsuario;
	callAjax({
		async:true,
	    service: "jsp/ingresoNovedad.action",
	    params: formInput,
	    fx: function(result){
	    try{
			var filaEquipo="";
			var filaTipo="";

				filaEquipo +="<ul>";
				$.each(result.equipoList,function(index, value){			
					filaEquipo +="<li>";
					filaEquipo +="<a href=\"#tabs-" + value.key.id + "\">" + value.nombre + "</a>";
					filaEquipo +="</li>";					
				});
				filaEquipo +="</ul>";
				$.each(result.equipoList,function(index, value){			
					filaEquipo +="<div id=\"tabs-" + value.key.id + "\">";
					filaEquipo +="<input type=\"hidden\" id=\"tab" + value.key.id + "Selected\"/>";
					filaEquipo +="<p><textarea maxlength=\"500\" rows=\"10\" cols=\"50\" name=\"comentario_" + value.key.id + "\" id=\"comentario" + value.key.id + "\"></textarea></p>";
					filaEquipo +="<div style=\"float:left;\" id=\"file-uploader-" + value.key.id + "\"></div>"				
					filaEquipo +="</div>";	
				});				
				
				$.each(result.tipoList,function(index, value){
					filaTipo += "<option value=\"" + value.id + "\">" + value.descripcion + "</option>";
				});	
		    	
			$("#empleado").attr('value', result.usuarioLN.email);
			$("#tabs").html(filaEquipo);
			$("#tipo").html(filaTipo);
	        $("#tabs").tabs({ selected: 0,event: "mouseover" }).addClass('ui-tabs-vertical ui-helper-clearfix');
	        $("#tabs li").removeClass('ui-corner-top').addClass('ui-corner-left');
	        //$('#tabs').bind('tabsselect', function (event, ui) {});
	        $("#tipo option[value='" + result.turno + "']").attr('selected', true);
	        
		      $.each(result.equipoList,function(index, value){
			      new qq.FileUploader({
			    	    element: document.getElementById('file-uploader-' + value.key.id),
			    	    action: '/fileupload',
			    	    allowedExtensions: ['jpg', 'jpeg', 'pdf', 'txt','doc','htm','html','ppt','png', 'gif'],
			    	    sizeLimit: 512*1024,
			    	    params: {
			    	  		idEquipo: value.key.id,
			    	        idUsuario: idUsuario
			    	    },
			            onComplete: function(id, fileName, responseJSON){
			    	    	if(!responseJSON.success){alert(responseJSON.errorMessage);}
			    	    }			    	    
			      }); 
		      });
	        
	        }catch(ee){alert(ee.message);}
	    },
	    errorfx: function(msg){
	        alert("Error "+msg);
	    }            
	}); 		
}

function actualizarNovedad(formInput,idUsuario){
	callAjax({
		async:true,
	    service: "/actualizanovedad",
	    params: formInput,
	    fx: function(result){
	    try{
	    	listarVisitasLVS(true,idUsuario)
	        }catch(ee){alert(ee.message);}
	    },
	    errorfx: function(msg){
	        Alerta(msg);
	    }            
	}); 	
}

function eliminarVisitaLVS(idUsuario,idVisita){
	abreDialogo('Quiere borrar esta novedad?', function() { borrarNovedad(idUsuario,idVisita); });
}

function borrarNovedad(idUsuario,idVisita){
	var formInput='idUsuario=' + idUsuario + '&idVisita=' + idVisita;	
	callAjax({
		async:true,
	    service: "jsp/borrarNovedad.action",
	    params: formInput,
	    fx: function(result){
	    try{
	    	listarVisitasLVS(false,idUsuario);
	        }catch(ee){alert(ee.message);}
	    },
	    errorfx: function(msg){
	        alert("Error "+msg);
	    }            
	}); 	
}

function CargarSitio(idNegocio){
	var formInput='idNegocio=' + idNegocio;
	callAjax({
		async:true,
        service: "jsp/obtenerEquipo.action",
        params: formInput,
        fx: function(result){
        try{
			var filaSitio="";
			var count=0;
			if(!$.isEmptyObject(result)){
				$.each(result.equipoList,function(index, value){
					count++;
					filaSitio += "<option value=\"" + value.key.id + "\">" + value.nombre + "</option>";
				});
				if(count==0){
					filaSitio += "<option value=\"0\">no hay sitios</option>";
				}
				
			}else{
				Alerta('no hay datos');
			}
			$("#idSitio").html(filaSitio);
            }catch(e){Alerta(e.message);}
        },
        errorfx: function(msg){
        	Alerta("Error: consulte al administador");
        }            
    }); 
}

function listarMetasLVS(pariente,idUsuario,idNovedad){
	var formInput='idUsuario=' + idUsuario + '&idNovedad=' + idNovedad;
	callAjax({
		async:true,
        service: "jsp/obtenerBitacora.action",
        params: formInput,
        fx: function(result){
        try{
			var fila="";
			if(!$.isEmptyObject(result)){
				$.each(result.bitacoraList,function(index, value){
		 			fila+="<tr>";
		 			fila+="<td></td>";
		 			fila+="<td>"+value.fecha+"</td>";
		 			fila+="<td>"+value.comentario+"</td>";
		 			fila+="</tr>"; 
				});
			}else{
				Alerta('no hay datos');
			}

				if(pariente){
					parent.$('table[id=tabla-metas] tbody', parent.document).append(fila);
					parent.$('#externalSite', parent.document).dialog('close');
				}else{
			    	$('table[id=tabla-metas] tbody tr').addClass("borrar");
			    	$('table[id=tabla-metas] tbody tr.borrar').remove();						
					$('#tabla-metas > tbody').append(fila);	
				}

            }catch(e){Alerta(e.message);}
        },
        errorfx: function(msg){
        	Alerta("Error: consulte al administador");
        }            
    }); 
}

function guardarMetaLVS(formInput,idUsuario,idNovedad){
	callAjax({
		async:true,
	    service: "jsp/guardarBitacora.action",
	    params: formInput,
	    fx: function(result){
	    try{
	    	parent.$('table[id=tabla-metas] tbody tr', parent.document).addClass("borrar");
	    	parent.$('table[id=tabla-metas] tbody tr.borrar', parent.document).remove();
	    	listarMetasLVS(true,idUsuario,idNovedad);
	        }catch(ee){alert(ee.message);}
	    },
	    errorfx: function(msg){
	        alert("Error: consulte al administador");
	    }            
	}); 	
}

function CargarLocalidades(){
	callAjax({
		async:true,
        service: "jsp/obtenerLocalidades.action",
        params: "",
        fx: function(result){
        try{
			var filaNegocio="";
			var filaSitio="";
			var filaResponsable="";
			var filaTipo="";
			if(!$.isEmptyObject(result)){
	
				$.each(result.negocioList,function(index, value){
					filaNegocio += "<option value=\"" + value.id + "\">" + value.nombre + "</option>";
				});

				$.each(result.equipoList,function(index, value){
					filaSitio += "<option value=\"" + value.key.id + "\">" + value.nombre + "</option>";
				});
				
				$.each(result.usuarioList,function(index, value){
					filaResponsable += "<option value=\"" + value.id + "\">" + value.nombres + " " + value.apepa+ "</option>";
				});
				
				$.each(result.tipoList,function(index, value){
					filaTipo += "<option value=\"" + value.id + "\">" + value.descripcion + "</option>";
				});				
			}else{
				Alerta('no hay datos');
			}


			$("#tipo").html(filaTipo);
        }catch(e){Alerta( e.message);}
        },
        errorfx: function(msg){
        	Alerta("Error "+msg);
        }            
    }); 
}


function buscarUsuario(request, response){
	var formInput='nombres=' + request.term;
	callAjax({
		async:true,
	    service: "jsp/obtenerUsuarioPorNombre.action",
	    params: formInput,
	    fx: function(result){
	    try{
                response($.map(result.usuarioList, function (item) {
                    return {
                        id: item.id,
                        value: item.nombres+' '+item.apepa+' '+item.apema
                    }
                }));
	        }catch(ee){alert(ee.message);}
	    },
	    errorfx: function(msg){
	        alert("Error "+msg);
	    }            
	}); 		
}

function ReporteHome(){
	var cabecita="<table class=\"novedades\">";
	cabecita+="<thead>";
	cabecita+="<tr>";
	cabecita+="<th scope=\"col\" class=\"rounded-company\"></th>";    	
	cabecita+="<th scope=\"col\" class=\"rounded\">Equipo</th>";
	cabecita+="<th scope=\"col\" class=\"rounded\">Turno</th>";   
	cabecita+="<th scope=\"col\" class=\"rounded\">Descripcion</th>";    
	cabecita+="<th scope=\"col\" class=\"rounded-q4\"></th>";                             
	cabecita+="</tr>";
	cabecita+="</thead>";
	cabecita+="<tbody>";
	var patitas="</tbody>";    
	patitas+="</table>";
	var cuerpecito="";
	
	callAjax({
		async:true,
        service: "jsp/reporteHome.action",
        params: '',
        fx: function(result){
        try{
        	
			if(!$.isEmptyObject(result)){
				var fechita;
				var count=0;
				$.each(result.novedades,function(indice, value){
					
					if(indice > 0 && value.fecha!=fechita){
						$("#der_contenido").append("<h2>Novedades para : " +fechaformat(fechita) + " </h2>");
						$("#der_contenido").append(cabecita+cuerpecito+patitas);
						$("#der_contenido").append("<br>");
						cuerpecito="";
					}
					cuerpecito+="<tr><td></td>";
					cuerpecito+="<td>"+value.equipoNombre+"</td>";
					cuerpecito+="<td>"+value.turnoNombre+"</td>";
					cuerpecito+="<td>"+value.comentario+"</td><td></td>";
					cuerpecito+="</tr>"; 	
					fechita=value.fecha;
					count++;
				});	
				
				if(count>0){
					$("#der_contenido").append("<h2>Novedades para : " +fechaformat(fechita) + " </h2>");	
					$("#der_contenido").append(cabecita+cuerpecito+patitas);
				}
				
			}
        }catch(e){Alerta(e.message);}
        },
        errorfx: function(msg){
        	Alerta(msg);
        }            
    }); 
}
function listarFueraLN(pariente,idUsuario){
	var formInput='idUsuario=' + idUsuario;
	callAjax({
		async:true,
        service: "jsp/obtenerFueraServicio.action",
        params: formInput,
        fx: function(result){
        try{
			var fila="";
			$.each(result.eventoList,function(index, value){
	 			fila+="<tr>";
	 			fila+="<td></td>";
	 			fila+="<td>" + fechaformat(value.fecha) + "</td>";
	 			if(value.estado=='P'){
	 				fila+="<td>Pendiente</td>";
	 			}else{
	 				fila+="<td>Resuelto</td>";
	 			}
	 			fila+="<td align=\"center\"><a href='#' onclick=\"editarFueraServicio(" + idUsuario + "," + value.key.id + "," + value.idpadre+"); return false;\"><img src='../images/user_edit.png' alt='' title='' border='0' /></a></td>";
	 			fila+="</tr>";  		 		   
			});
				if(pariente){
					$('#titulo').append(" de : " + result.usuarioln.nombres + " " + result.usuarioln.apepa)
					parent.$('table[id=tabla-metas] tbody', parent.document).append(fila);
					parent.$('#externalSite', parent.document).dialog('close');
				}else{
			    	$('table[id=tabla-metas] tbody tr').addClass("borrar");
			    	$('table[id=tabla-metas] tbody tr.borrar').remove();
					$('#titulo').append(" de : " + result.usuarioln.nombres + " " + result.usuarioln.apepa)
					$('#tabla-metas > tbody').append(fila);	
				}				

            }catch(e){alert(e.message);}
        },
        errorfx: function(msg){
            alert("Error "+msg);
        }            
    }); 
}

function CargarFueraServicio(idUsuario){
	var formInput='idUsuario=' + idUsuario;
	callAjax({
		async:true,
        service: "jsp/nuevoFueraServicio.action",
        params: formInput,
        fx: function(result){
        try{
			var filaEquipo="";
			var filaUsuario="";
			if(!$.isEmptyObject(result)){
				$.each(result.equipoList,function(index, value){
						filaEquipo += "<option value=\"" + value.key.id + "\">" + value.nombre + "</option>";
				});
				$.each(result.usuarioList,function(indice, value){
					filaUsuario += "<option value=\"" + value.id + "\">" + value.nombres + "</option>";
				});	
			}else{
				Alerta('no hay datos');
			}
			
			$("#idResponsable").html(filaUsuario);	
			$("#idEquipo").html(filaEquipo);
		
        }catch(e){Alerta(e.message);}
        },
        errorfx: function(msg){
        	Alerta("Error: consulte al administador");
        }            
    }); 
}

function guardarFueraServicio(formInput,idUsuario){
	callAjax({
		async:true,
	    service: "jsp/guardarFueraServicio.action",
	    params: formInput,
	    fx: function(result){
	    try{
	    	parent.$('table[id=tabla-metas] tbody tr', parent.document).addClass("borrar");
	    	parent.$('table[id=tabla-metas] tbody tr.borrar', parent.document).remove();
	    	listarFueraLN(true,idUsuario);
	        }catch(ee){alert(ee.message);}
	    },
	    errorfx: function(msg){
	        alert("Error "+msg);
	    }            
	}); 	
}

function editarFueraServicio(idUsuario,idListaEvento){
	var formInput='idUsuario=' + idUsuario + '&idListaEvento=' + idListaEvento;
	callAjax({
		async:true,
	    service: "jsp/editarFueraServicio.action",
	    params: formInput,
	    fx: function(result){
	    try{
			var filaEquipo="";
			var filaUsuario="";
			$.each(result.equipoList,function(index, value){
					filaEquipo += "<option value=\"" + value.key.id + "\">" + value.nombre + "</option>";
			});
			$.each(result.usuarioList,function(indice, value){
				filaUsuario += "<option value=\"" + value.id + "\">" + value.nombres + "</option>";
			});	
			$("#idResponsable option[value='" + result.fueraServicio.responsable + "']").attr('selected', true);
			$("#idEquipo option[value='" + result.fueraServicio.equipo + "']").attr('selected', true);
			$("#problema").attr('value', result.fueraServicio.falla);
			$("#fecha").attr('value', result.fueraServicio.fecha);
			$("#idResponsable").html(filaUsuario);	
			$("#idEquipo").html(filaEquipo);			
	        }catch(ee){alert(ee.message);}
	    },
	    errorfx: function(msg){
	        alert("Error "+msg);
	    }            
	}); 		
}

function editarFueraServicioUsuario(idUsuario,idListaEvento){
	var formInput='idUsuario=' + idUsuario + '&idListaEvento=' + idListaEvento;
	callAjax({
		async:true,
	    service: "jsp/editarFueraServicioUsuario.action",
	    params: formInput,
	    fx: function(result){
	    try{
			var filaEquipo="";
			var filaUsuario="";
			$.each(result.equipoList,function(index, value){
					filaEquipo += "<option value=\"" + value.key.id + "\">" + value.nombre + "</option>";
			});
			$.each(result.usuarioList,function(indice, value){
				filaUsuario += "<option value=\"" + value.id + "\">" + value.nombres + "</option>";
			});	
			$("#idResponsable option[value='" + result.fueraServicioUsuario.responsable + "']").attr('selected', true);
			$("#idEquipo option[value='" + result.fueraServicioUsuario.equipo + "']").attr('selected', true);
			$("#problema").attr('value', result.fueraServicioUsuario.falla);
			$("#fecha").attr('value', result.fueraServicioUsuario.fecha);
			$("#idResponsable").html(filaUsuario);	
			$("#idEquipo").html(filaEquipo);	
			

			$("#fechacompromiso").attr('value', result.fueraServicioUsuario.fechacompromiso);
			$("#fechasolucion").attr('value', result.fueraServicioUsuario.fechasolucion);
			$("#comentario").attr('value', result.fueraServicioUsuario.comentario);
			
			if(result.fueraServicioUsuario.estado=="T"){
				$('#fechacompromiso').attr("disabled", true); 
				$('#fechasolucion').attr("disabled", true); 
				$('#comentario').attr("disabled", true); 	
				$('#botonera').hide(); 			
			}			
			
			$('#problema').attr("disabled", true); 
			$('#fecha').attr("disabled", true); 
			$('#idResponsable').attr("disabled", true); 
			$('#idEquipo').attr("disabled", true); 
			
	        }catch(ee){alert(ee.message);}
	    },
	    errorfx: function(msg){
	        alert("Error "+msg);
	    }            
	}); 		
}

function guardarFueraServicioUsuario(formInput,idUsuario){
	callAjax({
		async:true,
	    service: "jsp/guardarFueraServicioUsuario.action",
	    params: formInput,
	    fx: function(result){
	    try{
	    	parent.$('table[id=tabla-metas] tbody tr', parent.document).addClass("borrar");
	    	parent.$('table[id=tabla-metas] tbody tr.borrar', parent.document).remove();
	    	listarFueraLN(true,idUsuario);
	        }catch(ee){alert(ee.message);}
	    },
	    errorfx: function(msg){
	        alert("Error "+msg);
	    }            
	}); 	
}

function cerrarFueraServicioUsuario(formInput,idUsuario){
	abreDialogo('¿Desea cerrar esta falla?', function() { cerrarFueraServicioUsuario2(formInput,idUsuario);});
}
function cerrarFueraServicioUsuario2(formInput,idUsuario){
	
	callAjax({
		async:true,
	    service: "jsp/cerrarFueraServicioUsuario.action",
	    params: formInput,
	    fx: function(result){
	    try{
	    	parent.$('table[id=tabla-metas] tbody tr', parent.document).addClass("borrar");
	    	parent.$('table[id=tabla-metas] tbody tr.borrar', parent.document).remove();
	    	listarFueraLN(true,idUsuario);
	        }catch(ee){alert(ee.message);}
	    },
	    errorfx: function(msg){
	        alert("Error "+msg);
	    }            
	}); 	
}

function ReporteFallas(formInput){
	callAjax({
		async:false,
        service: "jsp/reporteFallas.action",
        params: formInput,
        fx: function(result){
        try{
			var filaItem="";
			var thead="";
			var filas=false;
			if(!$.isEmptyObject(result)){
						if(!$.isEmptyObject(result.fallaList)){
							filas=true;
							$.each(result.fallaList,function(indice, value){
								if(indice==0){
									thead+="<tr>";
									thead+="<th align='center'><label>Fecha</label></th>";	
								    thead+="<th align='center'><label>Equipo</label></th>";									
								    thead+="<th align='center'><label>Falla</label></th>";
								    thead+="</tr>";
								}
								filaItem += "<tr>";
								filaItem += "<td align='left'>" + fechaformat(value.fecha)+ "</td>";
								filaItem += "<td align='left'>" + value.equipoNombre+ "</td>";								
								filaItem += "<td align='left'>" + value.problema+ "</td>";
								filaItem += "</tr>";
							});	
						}else{
							Alerta("No hay datos para esta consulta");
						}
				
				$('button[id=btn_exportar]').button({ disabled: false });
			}else{
				Alerta('no hay datos');
			}
			if(filas==true){
				$('table[id=tblReporteMeta] thead tr').addClass("delete");
				$('table[id=tblReporteMeta] thead tr.delete').remove(); 
				$('table[id=tblReporteMeta] tbody tr').addClass("delete");
				$('table[id=tblReporteMeta] tbody tr.delete').remove();  
				           
				$('table[id=tblReporteMeta] thead').append(thead);
				$('table[id=tblReporteMeta]').trigger("update");           
				$('table[id=tblReporteMeta] tbody').append(filaItem);
				$('table[id=tblReporteMeta]').trigger("update");
				           
				$('table[id=tblReporteMeta]').tablesorter({ locale: 'en', widgets: ['zebra'], useUI: true });
			}else{
				$('table[id=tblReporteMeta] thead tr').addClass("delete");
				$('table[id=tblReporteMeta] thead tr.delete').remove(); 
				$('table[id=tblReporteMeta] tbody tr').addClass("delete");
				$('table[id=tblReporteMeta] tbody tr.delete').remove();  
				$("#divTableReporteMeta").hide();
			}
        }catch(e){Alerta(e.message);}
        },
        errorfx: function(msg){
        	Alerta(msg);
        }            
    }); 
}

function ReporteNovedad(formInput){
	callAjax({
		async:false,
        service: "jsp/reporteNovedades.action",
        params: formInput,
        fx: function(result){
        try{
			var filaItem="";
			var thead="";
			var filas=false;
			if(!$.isEmptyObject(result)){
						if(!$.isEmptyObject(result.novedadList)){
							filas=true;
							$.each(result.novedadList,function(indice, value){
								if(indice==0){
									thead+="<tr>";
									thead+="<th align='center'><label>Fecha</label></th>";
									thead+="<th align='center'><label>Usuario</label></th>";
									thead+="<th align='center'><label>Turno</label></th>";
								    thead+="<th align='center'><label>Equipo</label></th>";
								    thead+="<th align='center'><label>Novedad</label></th>";
								    thead+="</tr>";
								}
								filaItem += "<tr>";
								filaItem += "<td align='center'>" + fechaformat(value.fecha)+ "</td>";
								if(value.usuarioNombre==null){
									filaItem += "<td align='left'></td>";
								}else{
									filaItem += "<td align='left' nowrap>" + value.usuarioNombre+ "</td>";
								}							
								filaItem += "<td align='left' nowrap>" + value.turnoNombre+ "</td>";
								filaItem += "<td align='left' nowrap>" + value.equipoNombre+ "</td>";								
								filaItem += "<td align='left'>" + value.comentario+ "</td>";
								filaItem += "</tr>";
							});	
						}else{
							Alerta("No hay datos para esta consulta");
						}
				
				$('button[id=btn_exportar]').button({ disabled: false });
			}else{
				Alerta('no hay datos');
			}
			if(filas==true){
				$('table[id=tblReporteMeta] thead tr').addClass("delete");
				$('table[id=tblReporteMeta] thead tr.delete').remove(); 
				$('table[id=tblReporteMeta] tbody tr').addClass("delete");
				$('table[id=tblReporteMeta] tbody tr.delete').remove();  
				           
				$('table[id=tblReporteMeta] thead').append(thead);
				$('table[id=tblReporteMeta]').trigger("update");           
				$('table[id=tblReporteMeta] tbody').append(filaItem);
				$('table[id=tblReporteMeta]').trigger("update");
				           
				$('table[id=tblReporteMeta]').tablesorter({ locale: 'en', widgets: ['zebra'], useUI: true });
			}else{
				$('table[id=tblReporteMeta] thead tr').addClass("delete");
				$('table[id=tblReporteMeta] thead tr.delete').remove(); 
				$('table[id=tblReporteMeta] tbody tr').addClass("delete");
				$('table[id=tblReporteMeta] tbody tr.delete').remove();  
				$("#divTableReporteMeta").hide();
			}
        }catch(e){Alerta(e.message);}
        },
        errorfx: function(msg){
        	Alerta(msg);
        }            
    }); 
}

function mostrarComboEquipo(idUsuario){
	var formInput='idUsuario=' + idUsuario;
	callAjax({
		async:true,
        service: "jsp/obtenerEquipos.action",
        params: formInput,
        fx: function(result){
        try{
			var filaEquipo = "<option value=\"0\" selected>Todos</option>";
			if(!$.isEmptyObject(result.equipoList)){
				$.each(result.equipoList,function(index, value){
					filaEquipo += "<option value=\"" + value.key.id + "\">" + value.nombre + "</option>";
				});
			}else{
				Alerta('no hay datos');
			}
			
			$("#equipos").html(filaEquipo);	
			
        }catch(e){Alerta(e.message);}
        },
        errorfx: function(msg){
        	Alerta("Error: consulte al administador");
        }            
    }); 
}

$.fn.expandAll = function() {
    $(this).find("tr").removeClass("collapsed").addClass("expanded").each(function(){
        $(this).expand();
    });
};

var repoHome=function(direccion,div){
	var icons = {
			header: "ui-icon-circle-arrow-e",
			headerSelected: "ui-icon-circle-arrow-s"
		};
	 $.ajax({
         	  url: direccion,
         	  type: "GET",
         	  data: "",
         	  success: function(resp){
		 		$('#der_contenido').empty();
				$('#der_contenido').html(resp);
				$( div ).accordion({
					autoHeight: false,
					navigation: true
				});
				$('.ui-accordion-content').css('padding-top','1px');
				$('.ui-accordion-content').css('padding-bottom','1px');
				$('.ui-accordion-content').css('padding-left','5px');
				$('.ui-accordion-content').css('padding-right','5px');
			    $(".table-home").treeTable().expandAll();;
			    $("table#dnd-example tbody tr").mousedown(function() {
			      $("tr.selected").removeClass("selected"); 
			      $(this).addClass("selected");
			    });

			    $("table#dnd-example tbody tr span").mousedown(function() {
			      $($(this).parents("tr")[0]).trigger("mousedown");
			    });
				
        	  },beforeSend:function(obj){
             	var nTop =eval(($(window).height()-32)/2);
             	var nLeft =eval(($(window).width()-32)/2);
             	$("#loading").css('top',nTop);
             	$("#loading").css('left',nLeft); 
             	if(obj.zindex!=undefined){
             		$("#loading").css('z-index',obj.zindex);
             	}else{
             		$("#loading").css('z-index',1000);
             	}
             	$("#loading").show();
             },error: function(resp){
         		  alert("error : "+resp);
         	  },complete:function(req,txtsts){
             	$("#loading").hide();
             }
       });
}