package org.keycloak.audit;

import org.jboss.logging.Logger;
import org.keycloak.Config;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class JSONLogEventListenerProviderFactory implements EventListenerProviderFactory {
    private static final String JSONLOG_PREFIX_ENV_VAR = "KEYCLOAK_JSONLOG_PREFIX";
    private static String PREFIX = "JSON_EVENT::";

    private Logger logger = Logger.getLogger(JSONLogEventListenerProvider.class.getPackage().getName());

    @Override
    public EventListenerProvider create(KeycloakSession session) {

        return new JSONLogEventListenerProvider(session, logger, PREFIX);
    }

    @Override
    public void init(Config.Scope scope) {
        String env_prefix = System.getenv(JSONLOG_PREFIX_ENV_VAR);
        if (env_prefix != null) {
            PREFIX = env_prefix;
        }
    }

    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {

    }

    @Override
    public void close() {

    }

    @Override
    public String getId() {
        return "jsonlog_event_listener";
    }
}
