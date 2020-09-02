package org.keycloak.audit;

import org.jboss.logging.Logger;
import org.keycloak.Config;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class JSONLogEventListenerProviderFactory implements EventListenerProviderFactory {
    private Logger logger = Logger.getLogger(JSONLogEventListenerProvider.class.getPackage().getName());

    private static final String jsonlog_prefix_env_var = "KEYCLOAK_JSONLOG_PREFIX";

    String prefix = "JSON_EVENT::";

    @Override
    public EventListenerProvider create(KeycloakSession session) {

        return new JSONLogEventListenerProvider(session, logger, prefix);
    }

    @Override
    public void init(Config.Scope scope) {
        String env_prefix = System.getenv(jsonlog_prefix_env_var);
        if (env_prefix != null) {
            prefix = env_prefix;
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
