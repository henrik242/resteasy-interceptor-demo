package no.synth.resteasyinterceptordemo;

import org.apache.log4j.Logger;
import org.jboss.resteasy.annotations.interception.Precedence;
import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.spi.interception.MessageBodyReaderContext;
import org.jboss.resteasy.spi.interception.MessageBodyReaderInterceptor;
import org.jboss.resteasy.spi.interception.MessageBodyWriterContext;
import org.jboss.resteasy.spi.interception.MessageBodyWriterInterceptor;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
@ServerInterceptor
@Precedence("SECURITY")
public class MyInterceptor implements MessageBodyReaderInterceptor, MessageBodyWriterInterceptor {
    private static final Logger log = Logger.getLogger(MessageBodyReaderInterceptor.class);

    public Object read(MessageBodyReaderContext context) throws IOException, WebApplicationException {
        log.info("MyInterceptor.read()");

        return context.proceed();
    }

    public void write(MessageBodyWriterContext context) throws IOException, WebApplicationException {
        log.info("MyInterceptor.write()");

        context.getHeaders().add("X-MyInterceptor", "Intercepted");
        context.proceed();
    }
}