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

import org.testattoo.bundle.html5.CssIdentifier
import org.testattoo.bundle.html5.helper.RangeHelper
import org.testattoo.core.ComponentException
import org.testattoo.core.component.field.NumberField

import static org.testattoo.core.Testattoo.config

/**
 * @author David Avenante (d.avenante@gmail.com)
 */
@CssIdentifier('input[type=number]')
class InputTypeNumber extends NumberField implements Input {
    Number value() {
        Object value = config.evaluator.eval(id(), "it.val()")
        if (value)
            value as BigDecimal
        else
            throw new ComponentException("${this.class.simpleName} ${this} is empty and has no value")
    }

    @Override
    Number minimum() {
        RangeHelper.minimun(this) as BigDecimal
    }

    @Override
    Number maximum() {
        RangeHelper.maximum(this) as BigDecimal
    }

    @Override
    Number step() {
        RangeHelper.step(this)
    }

    @Override
    boolean inRange() {
        RangeHelper.inRange(this)
    }
}
