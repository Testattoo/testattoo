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
package org.testattoo.bundle.html5.table

import org.testattoo.bundle.html5.CssIdentifier
import org.testattoo.core.component.datagrid.Row

import static org.testattoo.core.Testattoo.config

/**
 * @author David Avenante (d.avenante@gmail.com)
 */
@CssIdentifier('tr')
class Tr extends Row {
    @Override
    List<Td> cells() {
        config.evaluator.metaInfo("\$('[id=\"${id()}\"]').find('td')").collect { it as Td }
    }

    @Override
    Td cell(Object value) {
        cells().find { it.value() == value }
    }

    @Override
    String title() {
        config.evaluator.eval(id(), "it.find('th:first').text().trim()")
    }
}
