<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*, java.lang.*" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1' />
<title>LIBRO DE NOVEDADES</title>
<link rel="stylesheet" type="text/css" href="../css/style.css" />
<link rel="shortcut icon" href="../images/favicon.ico">
<link rel="icon" type="image/gif" href="../images/animated_favicon1.gif" >
<link rel="stylesheet" type="text/css" href="../css/start/jquery-ui-1.8.13.custom.css" />
<link rel="stylesheet" type="text/css" href="../css/jquery.treeTable.css" />
<script type="text/javascript" src="../js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../js/jquery-ui-1.8.13.custom.min.js"></script>
<script type="text/javascript" src="../js/jquery.treeTable.js"></script>
<script type="text/javascript" src="../js/jquery.printarea.js"></script>
<script type="text/javascript" src="../js/ddaccordion.js"></script>
<script type="text/javascript" src="../js/utils.js"></script>
<script type="text/javascript" src="../js/ajax.js"></script>
<script type="text/javascript" src="../js/init.js"></script>
<script type="text/javascript" src="../js/home.js"></script>
<script type="text/javascript">
<%
		String uid=(String)request.getAttribute("uid");
		String ch=(String)request.getAttribute("perfil");
		//System.out.println("var uid='" + uid + "'");
		//System.out.println("var perfil='" + ch + "';");
		
		if(uid!=null){
			out.print("var uid='" + uid + "';\n");
		}else{
			out.print("var uid='';\n");
		}
		
		if(ch!=null){
			out.print("var perfil='" + ch + "';\n");
		}else{
			out.print("var perfil='';\n");
		}
		
					
%>
</script>
</head>
<body>
<div id="logout" style="display:none;"> 
<h3><a href="login">Melón Libro de novedades</a></h3>
</div>
<div id="main_container">

	<div class="header">
		<jsp:include page="encabezado.jsp" />
    </div>
    
    <div class="main_content">
    	<jsp:include page="menu_superior.jsp" />
                    
    <div class="center_content">  
    
    <div class="left_content">
    
<jsp:include page="menu_izq.jsp" />

    </div>  
    
    <div id="der_contenido" class="right_content">            

     </div><!-- end of right content-->
        
  </div>   <!--end of center content -->               

    <div class="clear"></div>
    </div> <!--end of main content-->

    <div class="footer">
    
    	<div class="left_footer">PANEL ADMINISTRACIÓN <!--<a href="http://www.loso.cl">LOSO</a>--></div>
    	<!--<div class="right_footer"><a href="http://www.loso.cl"><img src="../images/loso_logo.gif" alt="" title="" border="0" /></a></div>-->
    
    </div>

</div>	

</body>
</html>