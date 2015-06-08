package pe.pucp.inf320.toxicity.conector;

import com.google.gson.annotations.SerializedName;

public class SincronizacionRequest {
	
	@SerializedName("u")
	public SincronizacionData data = new SincronizacionData();
	
	public class SincronizacionData{
		@SerializedName("@i")
		public String id;
		@SerializedName("@f")
		public String fecha;
	}
	
}
