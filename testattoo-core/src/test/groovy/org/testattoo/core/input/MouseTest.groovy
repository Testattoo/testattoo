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
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.testattoo.Server
import org.testattoo.bundle.html5.Button
import org.testattoo.bundle.html5.Div
import org.testattoo.bundle.html5.Span
import org.testattoo.core.Browser
import org.testattoo.core.component.Panel
import org.testattoo.core.evaluator.WebDriverEvaluator

import static org.junit.jupiter.api.Assertions.fail
import static org.testattoo.core.Testattoo.*
import static org.testattoo.core.input.Key.*
import static org.testattoo.core.input.MouseModifiers.DOUBLE
import static org.testattoo.core.input.MouseModifiers.RIGHT

/**
 * @author David Avenante (d.avenante@gmail.com)
 */
@DisplayName("Mouse behaviours")
class MouseTest {
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

        visit BASE_URL + 'mouse.html'
    }

    @AfterAll
    static void tearDown() {
        config.evaluator.close()
        server.stop()
    }

    @BeforeEach
    void setup() {
        Browser.refresh()
    }

    @Test
    @DisplayName("Should Click")
    void should_click() {
        Button button = $('#button_1') as Button
        assert button.text() == 'Button'
        clickOn button
        assert button.text() == 'Button Clicked!'

        Browser.refresh()

        button = $('#button_1') as Button
        assert button.text() == 'Button'
        button.click()
        assert button.text() == 'Button Clicked!'
    }

    @Test
    @DisplayName("Should double click")
    void should_double_click() {
        Button button = $('#button_2') as Button

        assert button.text() == 'Button'
        doubleClickOn button
        assert button.text() == 'Button Double Clicked!'

        Browser.refresh()

        button = $('#button_2') as Button

        assert button.text() == 'Button'
        button.doubleClick()
        assert button.text() == 'Button Double Clicked!'
    }

    @Test
    @DisplayName("Should right click")
    void should_right_click() {
        Button button = $('#button_5') as Button

        assert button.text() == 'Button'
        rightClickOn button
        assert button.text() == 'Button Right Clicked!'

        Browser.refresh()

        button = $('#button_5') as Button

        assert button.text() == 'Button'
        button.rightClick()
        assert button.text() == 'Button Right Clicked!'
    }

    @Test
    @DisplayName("Should mouse over")
    void should_mouse_over() {
        Button button = $('#button_3') as Button
        assert button.text() == 'Button'
        hoveringMouseOn button
        assert button.text() == 'Button Mouse Over!'
    }

    @Test
    @DisplayName("Should mouse out")
    void should_mouse_out() {
        Button button = $('#button_4') as Button
        assert button.text() == 'Button'

        // To simulate mouse out
        // 1 - mouse over the component
        hoveringMouseOn button
        // 2 - mouse over an another component
        hoveringMouseOn $('#button_5') as Button
        // The mouse out is triggered
        assert button.text() == 'Button Mouse Out!'
    }

    @Test
    @DisplayName("Should drag and drop")
    void should_drag_and_drop() {
        Panel dropPanel = $('#droppable') as Div
        assert dropPanel.title() == 'Drop here'

        Panel dragPanel = $('#draggable') as Div
        drag dragPanel on dropPanel
        assert dropPanel.title() == 'Dropped!'

        Browser.refresh()

        dropPanel = $('#droppable') as Div
        assert dropPanel.title() == 'Drop here'

        dragPanel = $('#draggable') as Div
        dragPanel.drag().on(dropPanel)
        assert dropPanel.title() == 'Dropped!'
    }

    @Test
    @DisplayName("Should use with key modifier")
    void should_use_mouse_with_key_modifier() {
        Span span_Ctrl_mouseleft = $('#span_Ctrl_mouseleft') as Span
        Span span_Shift_mouseleft = $('#span_Shift_mouseleft') as Span

        assert !span_Ctrl_mouseleft.available()
        assert !span_Shift_mouseleft.available()

        CTRL.click $('#_Ctrl_mouseleft') as Div
        SHIFT.click $('#_Shift_mouseleft') as Div

        assert span_Ctrl_mouseleft.available()
        assert span_Shift_mouseleft.available()

        Span span_Alt_Shift_mouseleft = $('#span_Alt_Shift_mouseleft') as Span
        assert !span_Alt_Shift_mouseleft.available()
        (ALT + SHIFT).click $('#_Alt_Shift_mouseleft') as Div
        assert span_Alt_Shift_mouseleft.available()

        Span span_Crtl_Shift_mouseleft = $('#span_Crtl_Shift_mouseleft') as Span
        assert !span_Crtl_Shift_mouseleft.available()
        [CTRL, SHIFT].click $('#_Ctrl_Shift_mouseleft') as Div
        assert !span_Crtl_Shift_mouseleft.available()

        [SPACE].click $('#_Ctrl_Shift_mouseleft') as Div
        ['data'].click $('#_Ctrl_Shift_mouseleft') as Div
    }

    @Test
    @DisplayName("Should throw an error on invalid click sequence")
    void should_throw_an_error_on_invalid_click_sequence() {
        Button button = $('#button_1') as Button
        try {
            [CTRL, 'test', ALT].click button
            fail()
        } catch (IllegalArgumentException e) {
            assert e.message == 'Cannot type a modifier after some text'
        }

        try {
            config.evaluator.click('button_1', [RIGHT, DOUBLE], [])
            fail()
        } catch (IllegalArgumentException e) {
            assert e.message == 'Invalid click sequence'
        }
    }
}
