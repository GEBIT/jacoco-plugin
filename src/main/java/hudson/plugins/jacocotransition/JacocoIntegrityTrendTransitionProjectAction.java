package hudson.plugins.jacocotransition;

import hudson.model.Action;
import hudson.model.Job;
import hudson.model.Result;
import hudson.model.Run;

import java.io.IOException;

import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

/**
 * Project view extension by JaCoCo plugin.
 * 
 * @author Kohsuke Kawaguchi
 */
public final class JacocoIntegrityTrendTransitionProjectAction implements Action {
    public final Job<?,?> project;

    public JacocoIntegrityTrendTransitionProjectAction(Job<?,?> project) {
        this.project = project;
    }

    public String getIconFileName() {
        return "graph.gif";
    }

    public String getDisplayName() {
        return Messages.ProjectAction_DisplayName();
    }

    public String getUrlName() {
        return "jacocotransition";
    }

    /**
     * Gets the most recent {@link JacocoIntegrityTrendTransitionBuildAction} object.
     * @return the most recent jacoco coverage report
     */
    public JacocoIntegrityTrendTransitionBuildAction getLastResult() {
        for (Run<?, ?> b = project.getLastBuild(); b != null; b = b.getPreviousBuild()) {
            if (b.isBuilding() || b.getResult() == Result.FAILURE || b.getResult() == Result.ABORTED)
                continue;
            JacocoIntegrityTrendTransitionBuildAction r = b.getAction(JacocoIntegrityTrendTransitionBuildAction.class);
            if (r != null)
                return r;
        }
        return null;
    }

    public void doGraphTransition(StaplerRequest req, StaplerResponse rsp) throws IOException {
       if (getLastResult() != null) {
    	   getLastResult().doGraphTransition(req,rsp);
       }
    }
    
    public void doGraphActivity(StaplerRequest req, StaplerResponse rsp) throws IOException {
        if (getLastResult() != null) {
     	   getLastResult().doGraphActivity(req,rsp);
        }
     }
    
    public void doGraphWorkflow(StaplerRequest req, StaplerResponse rsp) throws IOException {
        if (getLastResult() != null) {
     	   getLastResult().doGraphWorkflow(req,rsp);
        }
     }

    //private static final Logger logger = Logger.getLogger(JacocoBuildAction.class.getName());
}
