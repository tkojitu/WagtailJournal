package com.github.tkojitu.wagtailjournal

class Container {
    private val factories = HashMap<String, ServiceFactory>()
    private val services = HashMap<String, Any?>()

    fun defserv(name: String, sf: ServiceFactory): Container {
        factories[name] = sf
        return this
    }

    fun geti(name: String): Any? {
        if (services.containsKey(name)) {
            return services[name]
        }
        val sf = factories[name]
        services[name] = sf!!.create(this)
        return services[name]
    }
}
