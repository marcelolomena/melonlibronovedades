package cl.loso.google;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ValidaCredencialesGoogle 
{
	private static final String AUTH_URL = "https://www.google.com/accounts/ClientLogin";
	private String _token;
	
	public ValidaCredencialesGoogle() {
		this._token = null;
	}
	
	public boolean valida(String usuario, String password) throws IOException 
	{
		try
		{
			boolean credencialCorrecta = false;
			
			String postContent = "accountType=HOSTED_OR_GOOGLE&Email=" + urlEncode(usuario) + "&Passwd=" + urlEncode(password);
			HttpURLConnection  connection = (HttpURLConnection) new URL(AUTH_URL).openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Length", Integer.toString(postContent.getBytes().length));
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(true);
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			out.writeBytes(postContent);
			out.flush();
			out.close();
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line = reader.readLine();
			
			while (line != null) 
			{
				if (line.startsWith("SID=")) 
				{
					reader.close();
					this._token = line.substring("SID=".length());
					credencialCorrecta = true;
					break;
				} 
				else 
				{
					line = reader.readLine();
				}
			}
			
			reader.close();
			
			return credencialCorrecta;
		}
		catch(Exception ex)
		{
			return false;
		}
	}

	private String urlEncode(String s) 
		throws UnsupportedEncodingException {
		
		return URLEncoder.encode(s, "UTF-8");
	}

	public String getToken() {
		return _token;
	}
}
