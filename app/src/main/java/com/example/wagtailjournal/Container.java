package com.example.wagtailjournal;

import java.util.HashMap;

public class Container {
    private HashMap<String, ServiceFactory> factories = new HashMap();
    private HashMap<String, Object> services = new HashMap();

    public Container defserv(String name, ServiceFactory sf) {
        factories.put(name, sf);
        return this;
    }

    public Object geti(String name) {
        if (services.containsKey(name)) {
            return services.get(name);
        }
        ServiceFactory sf = factories.get(name);
        services.put(name, sf.create(this));
        return services.get(name);
    }
}
