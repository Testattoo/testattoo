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
package org.testattoo.core.input

import io.github.bonigarcia.wdm.WebDriverManager
import io.undertow.Undertow
import org.junit.jupiter.api.*
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.testattoo.Server
import org.testattoo.core.Browser
import org.testattoo.core.CssIdentifier
import org.testattoo.core.component.Component
import org.testattoo.core.evaluator.WebDriverEvaluator
import org.testattoo.core.support.property.ValueSupport
import org.testattoo.core.support.state.EmptySupport

import static org.testattoo.core.Testattoo.*
import static org.testattoo.core.input.Key.*

/**
 * @author David Avenante (d.avenante@gmail.com)
 */
@DisplayName("Keyboard behaviours")
class KeyboardTest {
    private static Undertow server
    private static final int PORT = 9090
    private static String BASE_URL = "http://localhost:${PORT}/"
    private static WebDriver driver

    @BeforeAll
    static void before() {
        server = Server.undertow(9090)
        server.start()

        WebDriverManager.chromedriver().setup()
        driver = new ChromeDriver()
        config.evaluator = new WebDriverEvaluator(driver)

        visit BASE_URL + 'keyboard.html'
    }

    @AfterAll
    static void tearDown() {
        driver.close()
        server.stop()
    }

    @BeforeEach
    void setup() {
        Browser.refresh()
    }

    @Test
    @DisplayName("Should type Letters")
    void should_type_letters() {
        (0..25).each {
            char letter = (char) (('a' as char) + it)
            Component current_span = $("#span_$letter") as Component

            assert !current_span.available()
            type "$letter"
            assert current_span.available()
        }
    }

    @Test
    @DisplayName("Should type Numbers")
    void should_type_number() {
        (0..9).each {
            Component current_span = $("#span_$it") as Component
            assert !current_span.available()
            type "$it"
            assert current_span.available()
        }
    }

    /**
     * Most of the browsers of the market don't ever propagate all keyboard events for some keys.
     * So in this tests we just test keys that can be really handle by a web page
     * So we excluded :
     *  F1 to F12
     *  ESCAPE, INSERT, DELETE, PAGE_UP, PAGE_DOWN, HOME, END, BACK_SPACE, TAB, LEFT, UP, RIGHT, DOWN
     */
    @Test
    @DisplayName("Should type special Keys")
    void should_type_special_key() {
        [
            '#span_divide'   : DIVIDE,
            '#span_multiply' : MULTIPLY,
            '#span_substract': SUBTRACT,
            '#span_add'      : ADD,
            '#span_equals'   : EQUALS,
            '#span_return'   : RETURN,
            '#span_space'    : SPACE
        ].each { k, v ->
            Component current_span = $(k) as Component
            assert !current_span.available()
            type v
            assert current_span.available()
        }

        Component span = $('#span_Ctrl_Alt_Shift_x') as Component
        assert !span.available()
        type(CTRL + ALT + SHIFT + 'x')
        assert span.available()
    }

    @Test
    @DisplayName("Should use key modifier")
    void should_use_key_modifier() {
        Input textField = $('#textfield') as Input

        assert textField.value() == ''
        clickOn textField
        type(SHIFT + 'testattoo')
        textField.should { have value('TESTATTOO') }

        textField.clear()
        textField.should { have value('') }
        type('~!@#$%^&*()_+')
        textField.should { have value('~!@#$%^&*()_+') }

        textField.clear()
        textField.should { have value('') }
        type(SHIFT + '`1234567890-=')
        textField.should { have value('~!@#$%^&*()_+') }
    }

    @CssIdentifier('input[type=text]')
    class Input extends Component implements ValueSupport, EmptySupport {
        Object value() {
            config.evaluator.eval(id(), "it.val()")
        }

        void clear() {
            this.click()
            config.evaluator.runScript("\$('[id=\"${id()}\"]').val('')")
        }

        boolean empty() {
            config.evaluator.check(id(), "\$.trim(it.val()).length == 0")
        }
    }
}
