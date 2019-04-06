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

import org.junit.jupiter.api.Test
import org.testattoo.core.component.Dropdown
import org.testattoo.core.component.Group

/**
 * @author David Avenante (d.avenante@gmail.com)
 */
class SelectTest {
//    @BeforeClass
//    static void before() {
//        visit BASE_URL + 'components.html'
//    }

    // http://en.wikipedia.org/wiki/Drop-down_list
    @Test
    void select_should_have_expected_behaviours() {
        assert Select in Dropdown

        Select empty_select = $('#empty_select') as Select
        empty_select.should { be empty }

        Select elements = $('#elements') as Select

        assert elements.label() == 'Elements list'
        assert elements.items().size() == 5

        Dropdown os = $('#os') as Select
        assert os.items().size() == 8

        assert os.items()[1].value() == 'Ubuntu'
        assert os.item('Ubuntu').value() == 'Ubuntu'

        assert os.groups().size() == 3

        assert os.groups()[0].value() == 'linux'
        assert os.group('linux').value() == 'linux'
        assert os.group('win32').value() == 'win32'
        assert os.group('BSD').value() == 'BSD'

        assert os.selectedItem().value() == 'None'

        os.select('Fedora')
        assert os.selectedItem().value() == 'Fedora'
        os.select(os.items()[3])
        assert os.selectedItem().value() == 'Gentoo'

        Dropdown countries = $('#countries') as Select
        assert !countries.enabled()
        // Items are disabled too
        countries.items().forEach {
            assert !it.enabled()
        }
    }

    @Test
    void groupItem_should_have_expected_behaviours() {
        assert OptionGroup in Group

        Select os = $('#os') as Select
        Group linux = os.group('linux')

        assert linux.value() == 'linux'
        assert linux.items().size() == 3
        assert linux.item('Gentoo').value() == 'Gentoo'
    }

    @Test
    void implement_toString_and_equal() {
        Select os = $('#os') as Select

        assert os.items()[1].toString() == 'Ubuntu'
        assert os.items()[1] == os.items()[1]
    }
}
