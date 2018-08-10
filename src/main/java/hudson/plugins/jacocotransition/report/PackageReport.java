package hudson.plugins.jacocotransition.report;


/**
 * @author Kohsuke Kawaguchi
 * @author David Carver
 */
public final class PackageReport extends AggregatedReport<CoverageReport,PackageReport,ClassReport> {

	private String workflowType;
	
    /**
     * Give the default no-name package a non-empty name.
     */
    @Override
    public String getName() {
        String n = super.getName();
        return n.length() == 0 ? "(default)" : n;
    }

    @Override
    public void setName(String name) {
        super.setName(name.replaceAll("/", "."));
    }
    
    @Override
    public void add(ClassReport child) {
    	String newChildName = child.getName().replaceAll(this.getName() + ".", ""); 
    	//System.err.println("XXXCLASS report"+child.getName()+"==>"+newChildName);
    	child.setName(newChildName);
    	//System.err.println("XXXCLASS report"+child.getName());
        this.getChildren().put(child.getName(), child);
        //logger.log(Level.INFO, "PackageReport");
    }

	public String getWorkflowType() {
		return workflowType;
	}

	public void setWorkflowType(String workflowType) {
		this.workflowType = workflowType;
	}

	public String shortWorkflowName() {
		String name = getName();
		int index = name.lastIndexOf(".");
		if(index > 0 && index < name.length()) {
			name = name.substring(index+1);
		} 
		
		return name;
	}
    
    //private static final Logger logger = Logger.getLogger(CoverageObject.class.getName());
    
    
}
