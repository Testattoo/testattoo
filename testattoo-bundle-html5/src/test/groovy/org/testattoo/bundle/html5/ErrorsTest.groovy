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
package org.testattoo.bundle.html5

import org.junit.jupiter.api.Test
import org.testattoo.bundle.html5.input.InputTypeCheckBox
import org.testattoo.bundle.html5.input.InputTypeNumber
import org.testattoo.bundle.html5.input.InputTypePassword
import org.testattoo.core.ComponentException
import org.testattoo.core.component.field.NumberField
import org.testattoo.core.component.field.PasswordField
import static org.testattoo.core.input.MouseModifiers.*
import static org.testattoo.core.input.Key.*

import static org.junit.jupiter.api.Assertions.fail
import static org.testattoo.core.Testattoo.*

/**
 * @author David Avenante (d.avenante@gmail.com)
 */
class ErrorsTest {
//    @BeforeClass
//    static void before() {
//        visit BASE_URL + 'error.html'
//    }

    @Test
    void should_not_check_already_checked_element() {
        InputTypeCheckBox checkbox = $('#checkbox_1') as InputTypeCheckBox
        checkbox.should { be checked }

        try {
            check checkbox
        } catch (ComponentException e) {
            assert e.message == 'InputTypeCheckBox InputTypeCheckBox:checkbox_1 is already checked and cannot be checked'
        }
    }

    @Test
    void should_not_be_able_to_submit_form_if_no_submit_button_available() {
        Form form = $('#form') as Form
        try {
            submit form
            fail()
        } catch (ComponentException e) {
            assert e.message == 'Cannot submit form without submit button'
        }
    }

    @Test
    void should_not_be_able_to_reset_form_if_no_reset_button_available() {
        Form form = $('#form') as Form
        try {
            reset form
            fail()
        } catch (ComponentException e) {
            assert e.message == 'Cannot reset form without reset button'
        }
    }

    @Test
    void should_throw_an_error_on_invalid_click_sequence() {
        Form form = $('#form') as Form
        try {
            [CTRL, 'test', ALT].click form
            fail()
        } catch (IllegalArgumentException e) {
            assert e.message == 'Cannot type a modifier after some text'
        }

        try {
            config.evaluator.click('form', [RIGHT, DOUBLE], [])
            fail()
        } catch (IllegalArgumentException e) {
            assert e.message == 'Invalid click sequence'
        }
    }

    @Test
    void should_throw_an_error_when_asking_length_on_input_whiteout_length() {
        PasswordField password = $('#password') as InputTypePassword
        try {
            password.length()
        } catch (ComponentException e) {
            assert e.message == 'Not length defined for component InputTypePassword InputTypePassword:password'
        }
    }

    @Test
    void should_throw_an_error_when_asking_value_on_number_field_whiteout_value() {
        NumberField number = $('#number') as InputTypeNumber
        try {
            number.value()
        } catch (ComponentException e) {
            assert e.message == 'InputTypeNumber InputTypeNumber:number is empty and has no value'
        }
    }
}
