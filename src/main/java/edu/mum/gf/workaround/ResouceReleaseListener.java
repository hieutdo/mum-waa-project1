package edu.mum.gf.workaround;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ResouceReleaseListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        // Nothing to do here
    }

    public void contextDestroyed(ServletContextEvent sce) {
        JpaUtil.releaseResources();
    }
}
