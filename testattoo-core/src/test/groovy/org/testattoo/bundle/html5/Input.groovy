package org.testattoo.bundle.html5

import org.testattoo.core.CssIdentifier
import org.testattoo.core.component.Component
import org.testattoo.core.support.property.ValueSupport
import org.testattoo.core.support.state.EmptySupport

import static org.testattoo.core.Testattoo.getConfig

@CssIdentifier('input[type=text]')
class Input extends Component implements ValueSupport, EmptySupport {
    Object value() {
        config.evaluator.eval(id(), "it.val()")
    }

    void clear() {
        this.click()
        config.evaluator.runScript("\$('[id=\"${id()}\"]').val('')")
    }

    boolean empty() {
        config.evaluator.check(id(), "\$.trim(it.val()).length == 0")
    }
}
