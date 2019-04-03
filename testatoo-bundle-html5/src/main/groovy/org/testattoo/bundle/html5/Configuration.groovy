package org.testattoo.bundle.html5

import org.testattoo.core.internal.Identifiers

class Configuration {
    static {
        Identifiers.factories.CssIdentifier = { CssIdentifier annotation -> return "it.is('${annotation.value()}')" }
    }
}
