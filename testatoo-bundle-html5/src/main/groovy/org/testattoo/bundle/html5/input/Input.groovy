/**
 * Copyright © 2018 Ovea (d.avenante@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.testattoo.bundle.html5.input

import org.testattoo.bundle.html5.helper.LabelHelper
import org.testattoo.core.ComponentException

import static org.testattoo.core.Testattoo.config
import static org.testattoo.core.Testattoo.type
import static org.testattoo.core.input.Key.BACK_SPACE
import static org.testattoo.core.input.Key.CTRL

/**
 * @author David Avenante (d.avenante@gmail.com)
 */
trait Input {
    String placeholder() {
        config.evaluator.eval(id(), "it.prop('placeholder')")
    }

    boolean empty() {
        config.evaluator.check(id(), "\$.trim(it.val()).length == 0")
    }

    boolean readOnly() {
        config.evaluator.check(id(), "it.prop('readonly')")
    }

    boolean required() {
        config.evaluator.check(id(), "it.prop('required')")
    }

    boolean focused() {
        config.evaluator.check(id(), "it.is(':focus')")
    }

    void value(Object value) {
        if (!this.enabled()) {
            throw new ComponentException("${this.class.simpleName} ${this} is disabled and cannot be filled")
        }
        this.click()
        config.evaluator.runScript("\$('[id=\"${id()}\"]').val('')")
        type([String.valueOf(value)])
    }

    String label() {
        LabelHelper.label(this)
    }

    void clear() {
        this.click()
        type(CTRL + 'a')
        type(BACK_SPACE)
    }

    Object value() {
        config.evaluator.eval(id(), "it.val()")
    }

    boolean valid() {
        config.evaluator.check(id(), "it[0].validity.valid")
    }

    Number length() {
        BigDecimal length = config.evaluator.eval(id(), "it.prop('maxlength')") as BigDecimal
        if (length.signum() == -1) {
            throw new ComponentException("Not length defined for component ${this.class.simpleName} ${this}")
        }
        length
    }
}
