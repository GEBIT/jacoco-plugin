package hudson.plugins.jacocotransition.annotation;

import java.util.Map;

public class Mapper {
	protected Map<String,String> map;
	
	
	public Mapper(Map<String,String> map){
		this.map=map;
	}
	
	public String get(String i) {
		return map.get(i);
	}
	
	
	
	public Map<String, String> getMap() {
		return map;
	}

	public String get(String info,String i) {
		
		String mapped = map.get(i);
		
		//System.err.println("**LOOKUP** "+info+" "+i+" ==> "+ mapped);
		
		if(mapped== null) {
			mapped = i;
			//System.err.println("**LOOKUP** "+info+" "+i+" ==> "+ mapped);
		} else {
			//System.err.println("**LOOKUP** "+info+" "+i+" ==> "+ mapped);
		}
		
	
		return mapped;
	}
}
