import java.util.HashMap;
import java.util.Map;

class Document {
	
	HashMap<String, String> attributes;
	
	public void setAttr(String p_Key, String p_Value) {
		attributes.put(p_Key,p_Value);
	}
	
	public Document () {
		attributes = new HashMap<>();
	}
}