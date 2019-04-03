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
import org.testattoo.bundle.html5.heading.*
import org.testattoo.bundle.html5.input.InputTypeCheckBox
import org.testattoo.bundle.html5.input.InputTypeEmail
import org.testattoo.bundle.html5.input.InputTypePassword
import org.testattoo.bundle.html5.input.InputTypeRadio
import org.testattoo.core.ComponentException
import org.testattoo.core.component.*
import org.testattoo.core.support.property.TextSupport

import static org.junit.jupiter.api.Assertions.fail
import static org.testattoo.core.Testattoo.*

/**
 * @author David Avenante (d.avenante@gmail.com)
 */
class ComponentsTest {
//    @BeforeClass
//    static void before() {
//        visit BASE_URL + 'components.html'
//    }

    @Test
    void component_should_have_expected_common_behaviours() {
        assert Button in org.testattoo.core.component.Button

        Button button = $('#button') as Button

        assert button.enabled()
        assert button.available()
        assert button.visible()

        button = $('#submit') as Button
        assert !button.enabled()

        Div panel = $('#hidden_panel') as Div
        assert !panel.visible()

        panel = $('#non_existing_id') as Div
        assert !panel.available()
    }

    @Test
    void article_should_have_expected_behaviours() {
        assert Article in Component

        Article article = $('#article') as Article

        assert article.paragraphs.size() == 2
    }

    @Test
    void aside_should_have_expected_behaviours() {
        assert Aside in Component

        Aside aside = $('#aside') as Aside

        assert aside.visible()
    }

    @Test
    void button_should_have_expected_behaviours() {
        assert Button in org.testattoo.core.component.Button

        // fields type=button
        Button button = $('#button') as Button

        assert Button in TextSupport
        // But override text
        assert button.text() == 'Button'

        // fields type=submit
        button = $('#submit') as Button
        assert button.text() == 'Submit'

        // fields type=reset
        button = $('#reset') as Button
        assert button.text() == 'Reset'

        // button element
        button = $('#btn') as Button
        assert button.text() == 'My Button Text'
    }

    @Test
    void checkbox_should_have_expected_behaviours() {
        assert InputTypeCheckBox in CheckBox

        CheckBox checkBox = $('#checkbox') as InputTypeCheckBox
        assert checkBox.label() == 'Check me out'
        assert !checkBox.checked()
        checkBox.click()
        assert checkBox.checked()
        checkBox.click()
        assert !checkBox.checked()
        checkBox.click()
        assert checkBox.checked()

        try {
            check checkBox
            fail()
        } catch (ComponentException e) {
            assert e.message == 'InputTypeCheckBox InputTypeCheckBox:checkbox is already checked and cannot be checked'
        }

        clickOn checkBox

        try {
            uncheck checkBox
            fail()
        } catch (ComponentException e) {
            assert e.message == 'InputTypeCheckBox InputTypeCheckBox:checkbox is already unchecked and cannot be unchecked'
        }

        checkBox = $('#disabled_checkbox') as InputTypeCheckBox
        try {
            check checkBox
            fail()
        } catch (ComponentException e) {
            assert e.message == 'InputTypeCheckBox InputTypeCheckBox:disabled_checkbox is disabled and cannot be checked'
        }

        try {
            uncheck checkBox
            fail()
        } catch (ComponentException e) {
            assert e.message == 'InputTypeCheckBox InputTypeCheckBox:disabled_checkbox is disabled and cannot be unchecked'
        }
    }

    @Test
    void footer_should_have_expected_behaviours() {
        assert Footer in Component

        Footer footer = $('#footer') as Footer

        assert footer.visible()
    }

    @Test
    void form_should_have_expected_behaviours() {
        assert Form in org.testattoo.core.component.Form

        Form form = $('#form') as Form
        InputTypeEmail email = $('#form [type=email]') as InputTypeEmail
        InputTypePassword password = $('#form [type=password]') as InputTypePassword
        Message message = $('#form .alert') as Message

        assert form.visible()
        // Cause password mandatory
        assert !form.valid()

        email.value('joe.blow@email.org')
        password.value('password666')
        assert email.value() == 'joe.blow@email.org'
        assert password.value() == 'password666'

        form.reset()

        assert email.value() == ''
        assert password.value() == ''

        assert message.title() == 'The form was submitted 0 time(s)'
        // Set the required password field before submitting
        password.value('password666')
        form.submit()
        assert message.title() == 'The form was submitted 1 time(s)'
    }

    @Test
    void header_should_have_expected_behaviours() {
        assert Header in Component

        Header header = $('#header') as Header

        assert header.visible()
    }

    @Test
    void heading_should_have_expected_behaviours() {
        assert H1 in Heading
        assert H2 in Heading
        assert H3 in Heading
        assert H4 in Heading
        assert H5 in Heading
        assert H6 in Heading

        H1 h1 = $('#h1') as H1
        assert h1.text() == 'heading 1'

        H2 h2 = $('#h2') as H2
        assert h2.text() == 'heading 2'

        H3 h3 = $('#h3') as H3
        assert h3.text() == 'heading 3'

        H4 h4 = $('#h4') as H4
        assert h4.text() == 'heading 4'

        H5 h5 = $('#h5') as H5
        assert h5.text() == 'heading 5'

        H6 h6 = $('#h6') as H6
        assert h6.text() == 'heading 6'
    }

    @Test
    void image_should_have_expected_behaviours() {
        assert Img in Image

        Img image = $('#image') as Img

        assert image.reference().endsWith('img/Montpellier.jpg')
    }

    @Test
    void link_should_have_expected_behaviours() {
        assert A in Link

        A link = $('#link') as A

        assert link.text() == 'Link external page'
        assert link.reference().contains('https://www.google.ca')
    }

    @Test
    void panel_should_have_expected_behaviours() {
        assert Div in Panel

        Div panel = $('#panel') as Div

        assert panel.title() == ''
    }

    @Test
    void paragraph_should_have_expected_behaviours() {
        assert P in Component
        assert P in TextSupport

        P paragraph = $('#p_1') as P

        assert paragraph.text() == 'Paragraph 1'
    }

    @Test
    void radio_should_have_expected_behaviours() {
        assert InputTypeRadio in Radio

        Radio radio = $('#radio_1') as InputTypeRadio
        assert radio.label() == 'Radio checked'
        assert radio.checked()

        radio = $('#radio_2') as InputTypeRadio
        assert radio.label() == 'Radio unchecked'
        assert !radio.checked()
        radio.click()
        assert radio.checked()

        try {
            check radio
            fail()
        } catch (ComponentException e) {
            assert e.message == 'InputTypeRadio InputTypeRadio:radio_2 is already checked and cannot be checked'
        }

        radio = $('#disabled_radio') as InputTypeRadio
        try {
            check radio
            fail()
        } catch (ComponentException e) {
            assert e.message == 'InputTypeRadio InputTypeRadio:disabled_radio is disabled and cannot be checked'
        }
    }

    @Test
    void section_should_have_expected_behaviours() {
        assert Section in Component

        Section section = $('#section') as Section

        assert section.paragraphs().size() == 1
        assert section.articles().size() == 1
    }

    @Test
    void span_should_have_expected_behaviours() {
        assert Span in Component

        Span span = $('#span') as Span

        assert span.text() == 'A span'
    }

    @Test
    void label_should_have_expected_behaviours() {
        assert Label in org.testattoo.core.component.Label

        Label label = $('[for=password_field]') as Label

        assert label.text() == 'Password'
    }

    @Test
    void should_find_child_elements_by_css() {
        // TODO David
    }

    @Test
    void should_find_child_elements_by_js() {
        // TODO David
    }

    @CssIdentifier('div')
    class Message extends Panel {
        @Override
        String title() {
            config.evaluator.eval(id(), "it.text()")
        }
    }
}
