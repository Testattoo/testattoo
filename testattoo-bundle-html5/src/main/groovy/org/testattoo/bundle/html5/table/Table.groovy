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

import org.testattoo.core.By
import org.testattoo.core.CssIdentifier
import org.testattoo.core.component.datagrid.DataGrid

/**
 * @author David Avenante (d.avenante@gmail.com)
 */
@CssIdentifier('table')
class Table extends DataGrid {
    @Override
    List<Th> columns() {
        find(By.css('thead tr:last th'), Th)
    }

    @Override
    List<Tr> rows() {
        find(By.css('tbody tr'), Tr)
    }

    @Override
    Tr row(String title) {
        rows().find { it.title() == title }
    }

    @Override
    Th column(String title) {
        columns().find { it.title() == title }
    }

    @Override
    boolean empty() {
        rows().empty
    }
}
