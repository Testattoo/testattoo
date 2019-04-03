package org.testattoo.web.engine

import org.testattoo.core.MetaDataProvider
import org.testattoo.core.component.Component
import org.testattoo.core.internal.CachedMetaData

class Selector {
    /**
     * Create a component
     */
    static Component $(String expression) {
        new Component(new CachedMetaData(idProvider: new jQueryIdProvider(expression, true)))
    }

    /**
     * Creates a list of component
     */
    static List $$(String expression, Class clazz = Component) {
        Components components = new Components(clazz, new CachedMetaData(idProvider: new jQueryIdProvider(expression, false)))
        components.list()
    }

    static class Components<T extends Component> {
        private final MetaDataProvider meta
        private final Class<T> type
        private List<T> components

        Components(Class<T> type, MetaDataProvider meta) {
            this.meta = meta
            this.type = type
        }

        List<T> list() {
            if (components == null) {
                components = meta.metaInfos().collect {
                    new Component(new CachedMetaData(idProvider: new jQueryIdProvider("#${it.id}", false))).asType(type)
                } as List<T>
            }
            return Collections.unmodifiableList(components)
        }
    }

}
