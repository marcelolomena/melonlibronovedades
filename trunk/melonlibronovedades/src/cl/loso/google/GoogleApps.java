package cl.loso.google;

import java.util.logging.Logger;

public class GoogleApps {

	private static final Logger log = Logger.getLogger(GoogleApps.class.getName());

	public boolean UsuarioSel(String _User, String _Dominio, String _UsuClave)
	{
		try
		{
			ValidaCredencialesGoogle vCred = new ValidaCredencialesGoogle(); 

          	return vCred.valida(_User + "@" + _Dominio, _UsuClave);
		}
    	catch (Exception ex)
    	{
			log.warning(ex.getMessage());
    		System.out.println(ex.getMessage());
          	
          	return false;
    	}
	}
}
