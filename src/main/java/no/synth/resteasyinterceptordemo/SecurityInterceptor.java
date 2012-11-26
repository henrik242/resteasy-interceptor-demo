package no.synth.resteasyinterceptordemo;

import org.apache.log4j.Logger;
import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.ResourceMethod;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.spi.Failure;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.interception.AcceptedByMethod;
import org.jboss.resteasy.spi.interception.PreProcessInterceptor;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.Provider;
import java.lang.reflect.Method;

@Provider
@ServerInterceptor
public class SecurityInterceptor implements PreProcessInterceptor, AcceptedByMethod {
    private static final Logger log = Logger.getLogger(SecurityInterceptor.class);

    @SuppressWarnings("rawtypes")
    public boolean accept(Class c, Method m) {
        log.info("SecurityInterceptor.accept()");

        // run on all methods
        return true;
    }

    public ServerResponse preProcess(HttpRequest request, ResourceMethod method)
            throws Failure, WebApplicationException {
        log.info("SecurityInterceptor.preProcess()");

        ServerResponse response = null;

        String username = null;
        try {
            username = request.getFormParameters().get("username").get(0);
        } catch (NullPointerException e) {}

        if (username == null) {
            try {
                username = request.getUri().getQueryParameters().getFirst("username");
            } catch (NullPointerException e) {}
        }
        // very simple security validation
        if (username == null || username.isEmpty()) {
            log.error("SecurityInterceptor throws 401");

            throw new Failure("To access this method you need to inform an username",
                    401);
        } else if (!"john".equals(username)) {
            log.error("SecurityInterceptor throws 403");

            throw new Failure("User \"" + username
                    + "\" is not authorized to access this method.", 403);
        }
        return response;
    }
}

