package org.testattoo.bundle.html5

import org.testattoo.core.CssIdentifier
import org.testattoo.core.component.Component
import org.testattoo.core.support.property.TextSupport

import static org.testattoo.core.Testattoo.getConfig

@CssIdentifier('span')
class Span extends Component implements TextSupport {
    @Override
    String text() {
        config.evaluator.eval(id(), "it.text()")
    }
}
