package org.testattoo.bundle.html5

import org.testattoo.core.CssIdentifier
import org.testattoo.core.component.Panel

import static org.testattoo.core.Testattoo.getConfig

@CssIdentifier('div')
class Div extends Panel {
    @Override
    String title() {
        config.evaluator.eval(id(), "it.find('h1').text()")
    }
}
