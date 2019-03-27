package org.testattoo.core

import org.testattoo.core.component.Component

interface Selector {
    String expression(Component component)
}
