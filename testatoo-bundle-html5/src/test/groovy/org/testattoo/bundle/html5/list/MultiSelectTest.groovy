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
import org.testattoo.core.ComponentException
import org.testattoo.core.component.ListBox
import org.testattoo.core.component.Item

import static org.junit.jupiter.api.Assertions.fail

/**
 * @author David Avenante (d.avenante@gmail.com)
 */
class MultiSelectTest {
//    @BeforeClass
//    static void before() {
//        visit BASE_URL + 'components.html'
//    }

    @Test
    void should_have_expected_behaviours() {
        assert MultiSelect in ListBox

        ListBox empty_multi_select = $('#empty_multi_select') as MultiSelect
        empty_multi_select.should { be empty }

        ListBox cities = $('#cities') as MultiSelect

        assert cities.label() == 'Cities list'
        assert cities.items().size() == 6
        assert cities.visibleItems().size() == 3

        assert cities

        Item montreal = cities.item('Montreal')
        Item quebec = cities.item('Quebec')
        Item montpellier = cities.item('Montpellier')
        Item newYork = cities.item('New York')
        Item casablanca = cities.item('Casablanca')
        Item munich = cities.item('Munich')

        assert montreal.selected()
        assert montpellier.enabled()
        assert cities.item('Montreal').selected()

        assert !quebec.selected()
        assert !quebec.enabled()
        assert !cities.item('Quebec').selected()

        assert !montpellier.selected()
        assert !cities.item('Montpellier').selected()

        assert !newYork.selected()
        assert !cities.item('New York').selected()

        assert !casablanca.selected()
        assert !cities.item('Casablanca').selected()

        assert !munich.selected()
        assert !cities.item('Munich').selected()

        assert cities.selectedItems().containsAll(montreal)

        cities.unselect(montreal)
        cities.select(newYork, munich)

        assert cities.selectedItems().containsAll(newYork, munich)

        cities.select('Montpellier', 'Montreal')
        assert cities.item('Montpellier').selected()
        assert cities.item('Montreal').selected()
        assert cities.selectedItems().containsAll(newYork, munich, montpellier, montreal)

        cities.unselect(montreal)
        cities.unselect(montpellier)

        assert !cities.item('Montreal').selected()
        assert !cities.item('Montpellier').selected()
        assert cities.item('New York').selected()
        assert cities.item('Munich').selected()

        cities.select(montreal, montpellier)
        assert cities.item('Montreal').selected()
        assert cities.item('Montpellier').selected()
        assert cities.item('New York').selected()
        assert cities.item('Munich').selected()

        montpellier.click() // Now just Montpellier is selected
        assert montpellier.selected()
        assert !montreal.selected()
        assert !newYork.selected()
        assert !munich.selected()

        try {
            cities.select(quebec)
            fail()
        } catch (ComponentException e) {
            assert e.message == 'Option Quebec is disabled and cannot be selected'
        }

        try {
            cities.unselect(quebec)
            fail()
        } catch (ComponentException e) {
            assert e.message == 'Option Quebec is disabled and cannot be deselected'
        }

        try {
            cities.unselect(newYork)
            fail()
        } catch (ComponentException e) {
            assert e.message == 'Option New York is already unselected and cannot be deselected'
        }

        try {
            cities.select(montpellier)
            fail()
        } catch (ComponentException e) {
            assert e.message == 'Option Montpellier is already selected and cannot be selected'
        }

        MultiSelect planets = $('#planets') as MultiSelect
        assert planets.visibleItems().size() == 5
        assert planets.groups().size() == 2
        assert planets.groups()[0].value() == 'Cat-1'
        assert planets.group('Cat-1').value() == 'Cat-1'

        Item venus = planets.item('Venus')
        Item saturn = planets.item('Saturn')

        assert planets.selectedItems().size() == 0
        planets.select('Venus', 'Saturn')

        assert planets.selectedItems().size() == 2
        assert planets.selectedItems().containsAll(venus, saturn)

        planets.unselect('Venus', 'Saturn')
        assert planets.selectedItems().size() == 0
    }
}
