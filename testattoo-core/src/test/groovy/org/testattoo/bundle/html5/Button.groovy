package org.testattoo.bundle.html5

import org.testattoo.core.CssIdentifier

import static org.testattoo.core.Testattoo.getConfig

@CssIdentifier('button,input[type=button]')
class Button extends org.testattoo.core.component.Button {
    @Override
    String text() {
        config.evaluator.eval(this.id(), "it.is('input') ? it.val() : it.text().trim()")
    }
}
