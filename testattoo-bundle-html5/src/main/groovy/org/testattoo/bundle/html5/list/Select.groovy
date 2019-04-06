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
package org.testattoo.bundle.html5.list

import org.testattoo.core.By

import org.testattoo.bundle.html5.helper.LabelHelper
import org.testattoo.core.CssIdentifier
import org.testattoo.core.component.Dropdown

/**
 * @author David Avenante (d.avenante@gmail.com)
 */
@CssIdentifier("select:not([multiple])")
class Select extends Dropdown {
    @Override
    List<Option> items() {
        find(By.css('option'), Option)
    }

    @Override
    List<OptionGroup> groups() {
        find(By.css('optgroup'), OptionGroup)
    }

    @Override
    OptionGroup group(String value) {
        groups().find { it.value() == value }
    }

    @Override
    Option item(String value) {
        items().find { it.value() == value }
    }

    @Override
    Option selectedItem() {
        items().find { it.selected() }
    }

    @Override
    String label() {
        LabelHelper.label(this)
    }

    @Override
    boolean empty() {
        items().empty
    }
}
