function listarItemLVS(pariente){
	callAjax({
		async:true,
        service: "jsp/obtenerItem.action",
        params: "",
        fx: function(result){
        try{
			var fila="";
			$.each(result.itemList,function(index, value){
	 			fila+="<tr>";
	 			fila+="<td></td>";
	 			fila+="<td>"+value.id+"</td>";
	 			fila+="<td>"+value.descripcion+"</td>";
	 			fila+="<td>" + value.fecha+ "</td>";
	 			fila+="<td><a href='#' onclick=\"editarItemLVS(" + value.id + "); return false;\"><img src='../images/user_edit.png' alt='' title='' border='0' /></a></td>";
	 			fila+="<td><a href='#' onclick=\"eliminarItemLVS(" + value.id + "); return false;\"><img src='../images/trash.png' alt='' title='' border='0'/></a></td>";
	 			fila+="</tr>";  		 		   
			});

				if(pariente){
					parent.$('table[id=rounded-corner] tbody', parent.document).append(fila);
					parent.$('#externalSite', parent.document).dialog('close');
				}else{
					$("#rounded-corner > tbody").append(fila);	
				}

            }catch(e){alert(e.message);}
        },
        errorfx: function(msg){
            alert("Error "+msg);
        }            
    }); 
}

function guardarItemLVS(formInput){
	callAjax({
		async:true,
	    service: "jsp/guardarItem.action",
	    params: formInput,
	    fx: function(result){
	    try{
	    	parent.$('table[id=rounded-corner] tbody tr', parent.document).addClass("borrar");
	    	parent.$('table[id=rounded-corner] tbody tr.borrar', parent.document).remove();
	    	listarItemLVS(true);
	        }catch(ee){alert(ee.message);}
	    },
	    errorfx: function(msg){
	        alert("Error "+msg);
	    }            
	}); 
}

function actualizarItemLVS(formInput){
	callAjax({
		async:true,
	    service: "jsp/actualizarItem.action",
	    params: formInput,
	    fx: function(result){
	    try{
	    	parent.$('table[id=rounded-corner] tbody tr', parent.document).addClass("borrar");
	    	parent.$('table[id=rounded-corner] tbody tr.borrar', parent.document).remove();
	    	listarItemLVS(true);
	        }catch(ee){alert(ee.message);}
	    },
	    errorfx: function(msg){
	        alert("Error "+msg);
	    }            
	}); 	
}

function editarItemLVS2(id){
	var formInput='id=' + id;
	callAjax({
		async:true,
	    service: "jsp/editarItem.action",
	    params: formInput,
	    fx: function(result){
	    try{
	    	$("#item").attr('value', result.itemLVS.descripcion);
	    	$("#id").attr('value', result.itemLVS.id);
	        }catch(ee){alert(ee.message);}
	    },
	    errorfx: function(msg){
	        alert("Error "+msg);
	    }            
	}); 	
}
function eliminarItemLVS(id){
	abreDialogo('quiere borrar este tema?', function() { borrarItemLVS(id);});
}

function borrarItemLVS(id){
	var formInput='id=' + id;	
	callAjax({
		async:true,
	    service: "jsp/borrarItem.action",
	    params: formInput,
	    fx: function(result){
	    try{
	    	$('table[id=rounded-corner] tbody tr').addClass("borrar");
	    	$('table[id=rounded-corner] tbody tr.borrar').remove();
	    	listarItemLVS(false);
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
		 			fila+="<td>"+value.id+"</td>";
		 			fila+="<td>"+value.nombres+"</td>";
		 			fila+="<td>" + value.apepa+ "</td>";
		 			fila+="<td>" + value.cargo+ "</td>";
		 			fila+="<td><a href='#' onclick=\"editarUsuarioLVS(" + value.id + "); return false;\"><img src='../images/user_edit.png' alt='' title='' border='0' /></a></td>";
		 			fila+="<td><a href='#' onclick=\"eliminarUsuarioLVS(" + value.id + "); return false;\"><img src='../images/trash.png' alt='' title='' border='0'/></a></td>";
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

function guardarUsuarioLVS(formInput){
	callAjax({
		async:true,
	    service: "jsp/guardarUsuario.action",
	    params: formInput,
	    fx: function(result){
	    try{
	    	parent.$('table[id=rounded-corner] tbody tr', parent.document).addClass("borrar");
	    	parent.$('table[id=rounded-corner] tbody tr.borrar', parent.document).remove();
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
	callAjax({
		async:true,
	    service: "jsp/editarUsuario.action",
	    params: formInput,
	    fx: function(result){
	    try{
	    	$("#id").attr('value', result.usuarioLVS.id);
	    	$("#nombres").attr('value', result.usuarioLVS.nombres);
	    	$("#apepa").attr('value', result.usuarioLVS.apepa);
	    	$("#apema").attr('value', result.usuarioLVS.apema);
	    	$("#email").attr('value', result.usuarioLVS.email);
	    	$("#cargo").attr('value', result.usuarioLVS.cargo);
	    	if(result.usuarioLVS.perfil=='A'){
	    		$("#perfil").attr('checked', true);	
	    	}

	        }catch(ee){alert(ee.message);}
	    },
	    errorfx: function(msg){
	        alert("Error "+msg);
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
	    	parent.$('table[id=rounded-corner] tbody tr', parent.document).addClass("borrar");
	    	parent.$('table[id=rounded-corner] tbody tr.borrar', parent.document).remove();
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
	    	$('table[id=rounded-corner] tbody tr').addClass("borrar");
	    	$('table[id=rounded-corner] tbody tr.borrar').remove();
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
		 			fila+="<td>"+value.id+"</td>";
		 			fila+="<td>"+value.nombre+"</td>";
		 			fila+="<td>"+value.fecha+"</td>";
		 			fila+="<td><a href='#' onclick=\"editarNegocioLVS(" + value.id + "); return false;\"><img src='../images/user_edit.png' alt='' title='' border='0' /></a></td>";
		 			fila+="<td><a href='#' onclick=\"eliminarNegocioLVS(" + value.id + "); return false;\"><img src='../images/trash.png' alt='' title='' border='0'/></a></td>";
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
	abreDialogo('quiere borrar este negocio', function() { borrarNegocioLVS(idNegocio); });
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
	    	$("#id").attr('value', result.negocioLVS.id);
	    	$("#nombre").attr('value', result.negocioLVS.nombre);
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
        service: "jsp/obtenerSitio.action",
        params: formInput,
        fx: function(result){
        try{
			var fila="";
			if(!$.isEmptyObject(result)){
				$.each(result.sitioList,function(index, value){
		 			fila+="<tr>";
		 			if(index==0){
		 				fila+="<td><input type='radio' checked name='idSitio' value='" + value.key.id +"'/></td>";
		 			}else{
		 				fila+="<td><input type='radio' name='idSitio' value='" + value.key.id +"'/></td>";
		 			}
		 			fila+="<td>"+value.key.id+"</td>";
		 			fila+="<td>"+value.nombre+"</td>";
		 			fila+="<td>"+value.fecha+"</td>";
		 			fila+="<td><a href='#' onclick=\"editarSitioLVS(" + value.key.id + "); return false;\"><img src='../images/user_edit.png' alt='' title='' border='0' /></a></td>";
		 			fila+="<td><a href='#' onclick=\"eliminarSitioLVS(" + id + "," + value.key.id + "); return false;\"><img src='../images/trash.png' alt='' title='' border='0'/></a></td>";
		 			fila+="</tr>";  		 		   
				});
			}else{
				Alerta('no hay datos');
			}
				$('#titulo').html("Lista Sitios : " + result.negocioLVS.nombre);
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
	abreDialogo('quiere borrar este sitio?', function() { borrarSitioLVS(idNegocio,id); });
}

function borrarSitioLVS(idNegocio,idSitio){
	var formInput='idNegocio=' + idNegocio + '&idSitio=' + idSitio;
	callAjax({
		async:true,
	    service: "jsp/borrarSitio.action",
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
	    service: "jsp/guardarSitio.action",
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
	var formInput='idNegocio=' + idNegocio + '&id=' + id;
	callAjax({
		async:true,
	    service: "jsp/editarSitio.action",
	    params: formInput,
	    fx: function(result){
	    try{
	    	$("#nombre").attr('value', result.sitioLVS.nombre);
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
	    service: "jsp/actualizarSitio.action",
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

function listarAreaLVS(pariente,idNegocio,idSitio){
	var formInput='idSitio=' + idSitio + '&idNegocio=' + idNegocio;
	callAjax({
		async:true,
        service: "jsp/obtenerArea.action",
        params: formInput,
        fx: function(result){
        try{
			var fila="";
			if(!$.isEmptyObject(result)){
				$.each(result.areaList,function(index, value){
		 			fila+="<tr>";
		 			if(index==0){
		 				fila+="<td><input type='radio' checked name='idArea' value='" + value.key.id +"'/></td>";
		 			}else{
		 				fila+="<td><input type='radio' name='idArea' value='" + value.key.id +"'/></td>";
		 			}
		 			fila+="<td>"+value.key.id+"</td>";
		 			fila+="<td>"+value.nombre+"</td>";
		 			fila+="<td>"+value.fecha+"</td>";
		 			fila+="<td><a href='#' onclick=\"editarAreaLVS(" + value.key.id + "); return false;\"><img src='../images/user_edit.png' alt='' title='' border='0' /></a></td>";
		 			fila+="<td><a href='#' class='ask' onclick=\"eliminarAreaLVS("+idNegocio+","+idSitio+","+value.key.id+"); return false;\"><img src='../images/trash.png' alt='' title='' border='0'/></a></td>";
		 			fila+="</tr>";  		 		   
				});
			}else{
				Alerta('no hay datos');
			}
			$('#titulo').html("Lista Sitios : " + result.sitioLVS.nombre);
				if(pariente){
					parent.$('table[id=tabla-areas] tbody', parent.document).append(fila);
					parent.$('#externalSite', parent.document).dialog('close');
				}else{
			    	$('table[id=tabla-areas] tbody tr').addClass("borrar");
			    	$('table[id=tabla-areas] tbody tr.borrar').remove();						
					$("#tabla-areas > tbody").append(fila);	
				}
				
            }catch(e){Alerta(e.message);}
        },
        errorfx: function(msg){
        	Alerta("Error "+msg);
        }            
    }); 
}

function eliminarAreaLVS(idNegocio,idSitio,id){
	abreDialogo('quiere borrar esta area', function() { borrarAreaLVS(idNegocio,idSitio,id); });
}

function guardarAreaLVS(formInput,idNegocio,idSitio){
	callAjax({
		async:true,
	    service: "jsp/guardarArea.action",
	    params: formInput,
	    fx: function(result){
	    try{
	    	parent.$('table[id=tabla-areas] tbody tr', parent.document).addClass("borrar");
	    	parent.$('table[id=tabla-areas] tbody tr.borrar', parent.document).remove();
	    	listarAreaLVS(true,idNegocio,idSitio);
	        }catch(ee){alert(ee.message);}
	    },
	    errorfx: function(msg){
	        alert("Error "+msg);
	    }            
	}); 	
}

function editarAreaLVS2(idNegocio,idSitio,idArea){
	var formInput='idNegocio=' + idNegocio + '&idSitio=' + idSitio + '&idArea=' + idArea;
	callAjax({
		async:true,
	    service: "jsp/editarArea.action",
	    params: formInput,
	    fx: function(result){
	    try{
	    	$("#nombre").attr('value', result.areaLVS.nombre);
	        }catch(ee){alert(ee.message);}
	    },
	    errorfx: function(msg){
	        alert("Error "+msg);
	    }            
	}); 		
}

function actualizarAreaLVS(formInput,idNegocio,idSitio){
	callAjax({
		async:true,
	    service: "jsp/actualizarArea.action",
	    params: formInput,
	    fx: function(result){
	    try{
	    	parent.$('table[id=tabla-areas] tbody tr', parent.document).addClass("borrar");
	    	parent.$('table[id=tabla-areas] tbody tr.borrar', parent.document).remove();
	    	listarAreaLVS(true,idNegocio,idSitio);
	        }catch(ee){alert(ee.message);}
	    },
	    errorfx: function(msg){
	        Alerta(msg);
	    }            
	}); 	
}

function borrarAreaLVS(idNegocio,idSitio,idArea){
	var formInput='idNegocio=' + idNegocio + '&idSitio=' + idSitio + '&idArea=' + idArea;
	callAjax({
		async:true,
	    service: "jsp/borrarArea.action",
	    params: formInput,
	    fx: function(result){
	    try{
	    	$('table[id=tabla-areas] tbody tr').addClass("borrar");
	    	$('table[id=tabla-areas] tbody tr.borrar').remove();
	    	listarAreaLVS(true,idNegocio,idSitio);
	        }catch(ee){alert(ee.message);}
	    },
	    errorfx: function(msg){
	        alert("Error "+msg);
	    }            
	}); 	
}

function listarMetasLVS(pariente,idUsuario){
	var formInput='idUsuario=' + idUsuario;
	callAjax({
		async:true,
        service: "jsp/obtenerMeta.action",
        params: formInput,
        fx: function(result){
        try{
			var fila="";
			if(!$.isEmptyObject(result)){
				$.each(result.metaList,function(index, value){
		 			fila+="<tr>";
		 			fila+="<td></td>";
		 			fila+="<td>"+value.key.id+"</td>";
		 			fila+="<td>"+value.nombre+"</td>";
		 			fila+="<td>"+value.meta+"</td>";
		 			fila+="<td><a href='#' onclick=\"editarMetaLVS("+idUsuario+","+value.key.id+"); return false;\"><img src='../images/user_edit.png' alt='' title='' border='0' /></a></td>";
		 			fila+="<td><a href='#' class='ask' onclick=\"borrarMetaLVS("+idUsuario+","+value.key.id+"); return false;\"><img src='../images/trash.png' alt='' title='' border='0'/></a></td>";
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
					$("#tabla-metas > tbody").append(fila);	
				}
				
            }catch(e){Alerta(e.message);}
        },
        errorfx: function(msg){
        	Alerta("Error "+msg);
        }            
    }); 
}

function listarVisitasLVS(pariente,idUsuario){
	var formInput='idUsuario=' + idUsuario;
	callAjax({
		async:true,
        service: "jsp/obtenerVisita.action",
        params: formInput,
        fx: function(result){
        try{
			var fila="";
			if(!$.isEmptyObject(result)){
				$.each(result.visitaList,function(index, value){
		 			fila+="<tr>";
		 			fila+="<td></td>";
		 			fila+="<td>"+value.key.id+"</td>";
		 			fila+="<td>"+value.comentario+"</td>";
		 			fila+="<td>algo</td>";
		 			fila+="<td><a href='#' onclick=\"editarVisitaLVS(" + idUsuario + "," + value.key.id + "); return false;\"><img src='../images/user_edit.png' alt='' title='' border='0' /></a></td>";
		 			fila+="<td><a href='#' class='ask' onclick=\"borrarVisitaLVS("+value.key.id+"); return false;\"><img src='../images/trash.png' alt='' title='' border='0'/></a></td>";
		 			fila+="</tr>";  		 		   
				});
			}else{
				Alerta('no hay datos');
			}

				if(pariente){
					parent.$('table[id=tabla-visitas] tbody', parent.document).append(fila);
					parent.$('#externalSite', parent.document).dialog('close');
				}else{
			    	$('table[id=tabla-visitas] tbody tr').addClass("borrar");
			    	$('table[id=tabla-visitas] tbody tr.borrar').remove();						
					$("#tabla-visitas > tbody").append(fila);	
				}
				
            }catch(e){Alerta(e.message);}
        },
        errorfx: function(msg){
        	Alerta("Error "+msg);
        }            
    }); 
}

function guardarVisitaLVS(formInput,idUsuario){
	
	callAjax({
		async:true,
	    service: "jsp/guardarVisita.action",
	    params: formInput,
	    fx: function(result){
	    try{
	    	parent.$('table[id=tabla-visitas] tbody tr', parent.document).addClass("borrar");
	    	parent.$('table[id=tabla-visitas] tbody tr.borrar', parent.document).remove();
	    	listarVisitasLVS(true,idUsuario);
	        }catch(ee){alert(ee.message);}
	    },
	    errorfx: function(msg){
	        alert("Error "+msg);
	    }            
	}); 	
}

function editarVisitaLVS2(idUsuario,idVisita){
	var formInput='idUsuario=' + idUsuario + '&idVisita=' + idVisita;
	callAjax({
		async:true,
		timeout: 3600000,
	    service: "jsp/editarVisita.action",
	    params: formInput,
	    fx: function(result){
	    try{
			var filaNegocio="";
			var filaSitio="";
			var filaArea="";
			var filaItem="";
			var filaResponsable="";

			if(!$.isEmptyObject(result)){
				$.each(result.negocioList,function(index, value){
					filaNegocio += "<option value=\"" + value.id + "\">" + value.nombre + "</option>";
				});
				$.each(result.sitioList,function(index, value){
					filaSitio += "<option value=\"" + value.key.id + "\">" + value.nombre + "</option>";
				});
				$.each(result.areaList,function(index, value){
					filaArea += "<option value=\"" + value.key.id + "\">" + value.nombre + "</option>";
				});	
				$.each(result.usuarioList,function(index, value){
					filaResponsable += "<option value=\"" + value.id + "\">" + value.nombres + " " + value.apepa+ "</option>";
				});
				
				$.each(result.visitaitemList,function(indice, value){
					filaItem += "<tr>";
					filaItem += "<td>" + value.descripcion+ "</td>";
					if(value.seguro){
						filaItem += "<td align='center'><input type='checkbox' name='seguro' checked value=" + value.id + " id=s" + indice + " onclick='kseguro(this)' /></td>";
					}else{
						filaItem += "<td align='center'><input type='checkbox' name='seguro' value=" + value.id + " id=s" + indice + " onclick='kseguro(this)' /></td>";						
					}
					if(value.inseguro){
						filaItem += "<td align='center'><input type='checkbox' name='inseguro' checked value=" + value.id + " id=i" + indice + " onclick='kinseguro(this)' /></td>";
					}else{
						filaItem += "<td align='center'><input type='checkbox' name='inseguro' value=" + value.id + " id=i" + indice + " onclick='kinseguro(this)' /></td>";						
					}
					filaItem += "</tr>";
				});	
				
			}else{
				Alerta('no hay datos');
			}
			$("#fecha").attr('value', result.visitaLVS.fecha);
			$("#personas").attr('value', result.visitaLVS.personas);
			$("#horini").attr('value', result.visitaLVS.horaInico);
			$("#horter").attr('value', result.visitaLVS.horaTermino);
			$("#comentario").attr('value', result.visitaLVS.comentario);
			
			$("#idResponsable").html(filaResponsable);
			$("#idNegocio").html(filaNegocio);	
			$("#idSitio").html(filaSitio);
			$("#idArea").html(filaArea);
			$("#idResponsable option[value='" + result.visitaLVS.responsable + "']").attr('selected', true);
			$("#idNegocio option[value='" + result.visitaLVS.negocio + "']").attr('selected', true);
			$("#idSitio option[value='" + result.visitaLVS.sitio + "']").attr('selected', true);
			$("#idArea option[value='" + result.visitaLVS.area + "']").attr('selected', true);			

            $('table[id=tableItem] tbody').append(filaItem);
            $('table[id=tableItem]').trigger("update");
            $('table[id=tableItem]').tablesorter({ sortList: [[0, 0]], locale: 'en', widgets: ['zebra'], useUI: true });
    	
	        }catch(ee){alert(ee.message);}
	    },
	    errorfx: function(msg){
	        alert("Error "+msg);
	    }            
	}); 		
}

function actualizarVisitaLVS(formInput,idUsuario){
	callAjax({
		async:true,
	    service: "jsp/actualizarVisita.action",
	    params: formInput,
	    fx: function(result){
	    try{
	    	parent.$('table[id=tabla-visitas] tbody tr', parent.document).addClass("borrar");
	    	parent.$('table[id=tabla-visitas] tbody tr.borrar', parent.document).remove();
	    	listarVisitasLVS(true,idUsuario)
	        }catch(ee){alert(ee.message);}
	    },
	    errorfx: function(msg){
	        Alerta(msg);
	    }            
	}); 	
}

function CargarSitio(idNegocio){
	var formInput='idNegocio=' + idNegocio;
	callAjax({
		async:true,
        service: "jsp/obtenerSitio.action",
        params: formInput,
        fx: function(result){
        try{
			var filaSitio="";
			if(!$.isEmptyObject(result)){
				$.each(result.sitioList,function(index, value){
					filaSitio += "<option value=\"" + value.key.id + "\">" + value.nombre + "</option>";
				});
			}else{
				Alerta('no hay datos');
			}
			$("#idSitio").html(filaSitio);
            }catch(e){Alerta(e.message);}
        },
        errorfx: function(msg){
        	Alerta("Error "+msg);
        }            
    }); 
}

function CargarArea(idNegocio,idSitio){
	var formInput='idSitio=' + idSitio + '&idNegocio=' + idNegocio;
	callAjax({
		async:true,
        service: "jsp/obtenerArea.action",
        params: formInput,
        fx: function(result){
        try{
			var filaArea="";
			if(!$.isEmptyObject(result)){
				$.each(result.areaList,function(index, value){
					filaArea += "<option value=\"" + value.key.id + "\">" + value.nombre + "</option>";
				});
			}else{
				Alerta('no hay datos');
			}
			$("#idArea").html(filaArea);
				
            }catch(e){Alerta(e.message);}
        },
        errorfx: function(msg){
        	Alerta("Error "+msg);
        }            
    }); 
}
function guardarMetaLVS(formInput,idUsuario){
	callAjax({
		async:true,
	    service: "jsp/guardarMeta.action",
	    params: formInput,
	    fx: function(result){
	    try{
	    	parent.$('table[id=tabla-metas] tbody tr', parent.document).addClass("borrar");
	    	parent.$('table[id=tabla-metas] tbody tr.borrar', parent.document).remove();
	    	listarMetasLVS(true,idUsuario);
	        }catch(ee){alert(ee.message);}
	    },
	    errorfx: function(msg){
	        alert("Error "+msg);
	    }            
	}); 	
}

function editarMetaLVS2(idUsuario,idMeta){
	var formInput='idUsuario=' + idUsuario + '&idMeta=' + idMeta;
	callAjax({
		async:true,
	    service: "jsp/editarMeta.action",
	    params: formInput,
	    fx: function(result){
	    try{
			var filaNegocio="";
			var filaSitio="";
			var filaArea="";
				$.each(result.negocioList,function(index, value){
					filaNegocio += "<option value=\"" + value.id + "\">" + value.nombre + "</option>";
				});
				$.each(result.sitioList,function(index, value){
					filaSitio += "<option value=\"" + value.key.id + "\">" + value.nombre + "</option>";
				});
				$.each(result.areaList,function(index, value){
					filaArea += "<option value=\"" + value.key.id + "\">" + value.nombre + "</option>";
				});	
		    	$("#nombre").attr('value', result.metaLVS.nombre);
		    	$("#horas").attr('value', result.metaLVS.meta);
		    	
			$("#idNegocio").html(filaNegocio);	
			$("#idSitio").html(filaSitio);
			$("#idArea").html(filaArea);	    	
	    	
			$("#idNegocio option[value='" + result.metaLVS.idNegocio + "']").attr('selected', true);
			$("#idSitio option[value='" + result.metaLVS.idSitio + "']").attr('selected', true);
			$("#idArea option[value='" + result.metaLVS.idArea + "']").attr('selected', true);	    	
 
	        }catch(ee){alert(ee.message);}
	    },
	    errorfx: function(msg){
	        alert("Error "+msg);
	    }            
	}); 		
}

function actualizarMetaLVS(formInput,idUsuario){
	callAjax({
		async:true,
	    service: "jsp/actualizarMeta.action",
	    params: formInput,
	    fx: function(result){
	    try{
	    	parent.$('table[id=tabla-metas] tbody tr', parent.document).addClass("borrar");
	    	parent.$('table[id=tabla-metas] tbody tr.borrar', parent.document).remove();

	    	listarMetasLVS(true,idUsuario)
	        }catch(ee){alert(ee.message);}
	    },
	    errorfx: function(msg){
	        Alerta(msg);
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
			var filaArea="";
			if(!$.isEmptyObject(result)){
				$.each(result.negocioList,function(index, value){
					filaNegocio += "<option value=\"" + value.id + "\">" + value.nombre + "</option>";
				});
				$.each(result.sitioList,function(index, value){
					filaSitio += "<option value=\"" + value.key.id + "\">" + value.nombre + "</option>";
				});
				$.each(result.areaList,function(index, value){
					filaArea += "<option value=\"" + value.key.id + "\">" + value.nombre + "</option>";
				});	
			}else{
				Alerta('no hay datos');
			}
			
			$("#idNegocio").html(filaNegocio);	
			$("#idSitio").html(filaSitio);
			$("#idArea").html(filaArea);
  
        }catch(e){Alerta( e.message);}
        },
        errorfx: function(msg){
        	Alerta("Error "+msg);
        }            
    }); 
}

function CargarItemLocalidades(){
	callAjax({
		async:true,
        service: "jsp/obtenerItemLocalidades.action",
        params: "",
        fx: function(result){
        try{
			var filaNegocio="";
			var filaSitio="";
			var filaArea="";
			var filaItem="";
			var filaResponsable="";

			if(!$.isEmptyObject(result)){
				$.each(result.negocioList,function(index, value){
					filaNegocio += "<option value=\"" + value.id + "\">" + value.nombre + "</option>";
				});
				$.each(result.sitioList,function(index, value){
					filaSitio += "<option value=\"" + value.key.id + "\">" + value.nombre + "</option>";
				});
				$.each(result.areaList,function(index, value){
					filaArea += "<option value=\"" + value.key.id + "\">" + value.nombre + "</option>";
				});	
			
				$.each(result.itemList,function(indice, value){
					filaItem += "<tr>";
					filaItem += "<td>" + value.descripcion + "</td>";
					filaItem += "<td align='center'><input type='checkbox' name='seguro' value=" + value.id + " id=s" + indice + " onclick='kseguro(this)' /></td>";
					filaItem += "<td align='center'><input type='checkbox' name='inseguro' value=" + value.id + " id=i" + indice + " onclick='kinseguro(this)' /></td>";
					filaItem += "</tr>";
				});	
				$.each(result.usuarioList,function(index, value){
					filaResponsable += "<option value=\"" + value.id + "\">" + value.nombres + " " + value.apepa+ "</option>";
				});
				
			}else{
				Alerta('no hay datos');
			}
			
			$("#idNegocio").html(filaNegocio);	
			$("#idSitio").html(filaSitio);
			$("#idArea").html(filaArea);
			$("#idResponsable").html(filaResponsable);
            $('table[id=tableItem] tbody').append(filaItem);
            $('table[id=tableItem]').trigger("update");
            $('table[id=tableItem]').tablesorter({ sortList: [[0, 0]], locale: 'en', widgets: ['zebra'], useUI: true });
			
        }catch(e){Alerta(e.message);}
        },
        errorfx: function(msg){
        	Alerta("Error "+msg);
        }            
    }); 
}