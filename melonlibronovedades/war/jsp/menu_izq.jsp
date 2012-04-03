            <div class="sidebarmenu">      
                 <script>
                    if(perfil=='A'){           
                    	document.writeln('<a class=\"menuitem submenuheader\" href=\"\">Administrar</a>');
                    	document.writeln('<div class=\"submenu\">');
                    	document.writeln('<ul>');
                        document.writeln('<li><a id=\"menu11\" href=\"#\">Turnos</a></li>');
                        document.writeln('<li><a id=\"menu12\" href=\"#\">Usuarios</a></li>');
                        document.writeln('<li><a id=\"menu13\" href=\"#\">Negocios</a></li>');
                        document.writeln('<li><a id=\"menu32\" href=\"#\">Ingresar Novedad</a></li>'); 
                        document.writeln('</ul>');
                   		document.writeln('</div>');
                    	document.writeln('<a class=\"menuitem submenuheader\" href=\"\">Reportes</a>');
                    	document.writeln('<div class=\"submenu\">');
                    	document.writeln('<ul>');                        
                        document.writeln('<li><a id=\"menu14\" href=\"#\">Novedades y FC</a></li>');            
                        document.writeln('<li><a id=\"menu15\" href=\"#\">Resumen Día</a></li>');
                        document.writeln('</ul>');
                   		document.writeln('</div>');
                    }else if(perfil=='U'){
                    	document.writeln('<a class=\"menuitem submenuheader\" href=\"\">Opciones</a>');
                    	document.writeln('<div class=\"submenu\">');
                    	document.writeln('<ul>');
                        document.writeln('<li><a id=\"menu21\" href=\"#\">Fuera de Servicio</a></li>');
                        document.writeln('<li><a id=\"menu32\" href=\"#\">Ingresar Novedad</a></li>');
                        document.writeln('</ul>');
                   		document.writeln('</div>');
                    	document.writeln('<a class=\"menuitem submenuheader\" href=\"\">Reportes</a>');
                    	document.writeln('<div class=\"submenu\">');
                    	document.writeln('<ul>');                         
                        document.writeln('<li><a id=\"menu23\" href=\"#\">Novedades y FC</a></li>');                        
                        document.writeln('<li><a id=\"menu15\" href=\"#\">Resumen Día</a></li>');                                              
                        document.writeln('</ul>');
                   		document.writeln('</div>');                       
                    }else if(perfil=='X'){
                    	document.writeln('<a class=\"menuitem submenuheader\" href=\"\">Opciones</a>');
                    	document.writeln('<div class=\"submenu\">');
                    	document.writeln('<ul>');
                        document.writeln('<li><a id=\"menu32\" href=\"#\">Ingresar Novedad</a></li>');
                        document.writeln('</ul>');
                   		document.writeln('</div>');
                    	document.writeln('<a class=\"menuitem submenuheader\" href=\"\">Reportes</a>');
                    	document.writeln('<div class=\"submenu\">');
                    	document.writeln('<ul>');                         
                        document.writeln('<li><a id=\"menu23\" href=\"#\">Novedades y FC</a></li>');                          
                        document.writeln('<li><a id=\"menu15\" href=\"#\">Resumen Día</a></li>');
                        document.writeln('</ul>');
                   		document.writeln('</div>');                       
                    }
                </script>            

            </div>