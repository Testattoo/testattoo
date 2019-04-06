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
import org.testattoo.core.component.ListView
import org.testattoo.core.ComponentException

/**
 * @author David Avenante (d.avenante@gmail.com)
 */
class ListTest {
//    @BeforeClass
//    static void before() {
//        visit BASE_URL + 'components.html'
//    }

    @Test
    void should_have_expected_behaviours() {
        assert Ul in ListView

        Ul ul = $('#empty_unordered_list') as Ul
        assert ul.empty()

        ul = $('#unordered_list') as Ul

        assert ul.items().size() == 5
        assert ul.items()[0].value() == 'Item 1'
        assert ul.item('Item 4').value() == 'Item 4'
        assert ul.items()[3].equals(ul.items()[4])
        assert ul.items()[3].toString() == 'Item 4'

        assert Ol in ListView

        Ol ol = $('#empty_ordered_list') as Ol
        assert ol.empty()

        ol = $('#ordered_list') as Ol

        assert ol.items().size() == 5
        assert ol.items()[0].value() == 'Item 11'
        assert ol.item('Item 44').value() == 'Item 44'

        // Html5 list items can't be selected
        try {
            ol.items()[0].selected()
            fail()
        } catch (ComponentException e) {
            assert e.message == 'Unsupported Operation'
        }

        try {
            !ol.items()[0].selected()
            fail()
        } catch (ComponentException e) {
            assert e.message == 'Unsupported Operation'
        }

        try {
            ol.items()[0].select()
            fail()
        } catch (ComponentException e) {
            assert e.message == 'Li Item 11 cannot be selected (Unsupported Operation)'
        }

        try {
            ol.items()[0].unselect()
            fail()
        } catch (ComponentException e) {
            assert e.message == 'Li Item 11 cannot be unselected (Unsupported Operation)'
        }
    }
}
