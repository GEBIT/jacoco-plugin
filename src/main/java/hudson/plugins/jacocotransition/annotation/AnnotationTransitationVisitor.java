package hudson.plugins.jacocotransition.annotation;

import java.util.Map;

import org.objectweb.asm.AnnotationVisitor;

public class AnnotationTransitationVisitor extends AnnotationVisitor {

	private Map<String,String> map;
	private String prefix;
	
	public AnnotationTransitationVisitor(int api, AnnotationVisitor av) {
		super(api, av);
	}
	
	public void visit(String name, Object value) {
		//System.err.println("!>>>>> anno "+name+" = "+ value+ "  "+map);
		super.visit(name, value);
		if(map != null){
			if(name.equals("statemachine")){
				
				int preprefixIndex = prefix.lastIndexOf("/");
				if(preprefixIndex>0){
					String preprefix = prefix.substring(0, preprefixIndex);
					int prepreprefixIndex = preprefix.lastIndexOf("/");
					if(prepreprefixIndex >0){
						String prepreprefix = preprefix.substring(0, prepreprefixIndex);
						
						
						map.put(preprefix,prepreprefix+"/"+value.toString());
						
					}
				
					
				}
				
				
			} else {
			
				map.put(prefix,value.toString());
			}
		}
	}

	
	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public Map<String, String> getMap() {
		return map;
	}
	
	
}
