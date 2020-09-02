package org.keycloak.audit;

import org.jboss.logging.Logger;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.models.KeycloakSession;

import java.util.Map;
import javax.json.*;

public class JSONLogEventListenerProvider implements EventListenerProvider {
    KeycloakSession session;
    Logger logger;
    String prefix;

    public JSONLogEventListenerProvider(KeycloakSession session, Logger logger, String prefix) {
        this.session = session;
        this.logger = logger;
        this.prefix = prefix;
    }

    @Override
    public void onEvent(Event event) {
        logger.log(Logger.Level.INFO, prefix + toString(event));
    }

    @Override
    public void onEvent(AdminEvent adminEvent, boolean b) {
        logger.log(Logger.Level.INFO, prefix + toString(adminEvent));
    }

    @Override
    public void close() {
    }

    private String toString(Event event) {
        JsonObjectBuilder obj = Json.createObjectBuilder();

        if (event.getType() != null) {
            obj.add("type", event.getType().toString());
        }
        if (event.getRealmId() != null) {
            obj.add("realmId", event.getRealmId());
        }
        if (event.getClientId() != null) {
            obj.add("clientId", event.getClientId());
        }
        if (event.getUserId() != null) {
            obj.add("userId", event.getUserId());
        }
        if (event.getIpAddress() != null) {
            obj.add("ipAddress", event.getIpAddress());
        }
        if (event.getError() != null) {
            obj.add("error", event.getError());
        }
        if (event.getDetails() != null) {
            for (Map.Entry<String, String> e : event.getDetails().entrySet()) {
                obj.add(e.getKey(), e.getValue());
            }
        }

        return obj.build().toString();
    }



    private String toString(AdminEvent adminEvent) {
        JsonObjectBuilder obj = Json.createObjectBuilder();

        obj.add("type", "ADMIN_EVENT");

        if (adminEvent.getOperationType() != null) {
            obj.add("operationType", adminEvent.getOperationType().toString());
        }

        if (adminEvent.getAuthDetails() != null) {
            if (adminEvent.getAuthDetails().getRealmId() != null) {
                obj.add("realmId", adminEvent.getAuthDetails().getRealmId());
            }
            if (adminEvent.getAuthDetails().getClientId() != null) {
                obj.add("clientId", adminEvent.getAuthDetails().getClientId());
            }
            if (adminEvent.getAuthDetails().getUserId() != null) {
                obj.add("userId", adminEvent.getAuthDetails().getUserId());
            }
            if (adminEvent.getAuthDetails().getIpAddress() != null) {
                obj.add("ipAddress", adminEvent.getAuthDetails().getIpAddress());
            }
        }

        if (adminEvent.getResourceType() != null) {
            obj.add("resourceType", adminEvent.getResourceType().toString());
        }
        if (adminEvent.getResourcePath() != null) {
            obj.add("resourcePath", adminEvent.getResourcePath());
        }
        if (adminEvent.getError() != null) {
            obj.add("error", adminEvent.getError());
        }

        return obj.build().toString();
    }

}
