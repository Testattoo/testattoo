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
package org.testattoo.core.evaluator

import io.github.bonigarcia.wdm.WebDriverManager
import io.undertow.Undertow
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.testattoo.Server
import org.testattoo.bundle.html5.Div
import org.testattoo.bundle.html5.Input
import org.testattoo.core.internal.Log

import static org.testattoo.core.Testattoo.*

/**
 * @author David Avenante (d.avenante@gmail.com)
 */
class WebDriverEvaluatorTest {
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
    }

    @AfterAll
    static void tearDown() {
        config.debug = false
        config.evaluator.close()
        server.stop()
    }

    @Test
    void should_be_able_to_obtain_the_underline_implementation() {
        assert config.evaluator.driver instanceof WebDriver
    }

    @Test
    void should_be_able_to_register_a_script() {
        try {
            // Page with jquery missing
            visit BASE_URL + 'popup.html'

            Input field = $('[id="first.name"]') as Input
            Div error = $('#firstname_blur') as Div

            assert field.empty()
            assert !error.visible()

            // Register scripts who
            // 1 - show the first name_blur message
            // 2 - set an email in email field
            config.evaluator.registerScripts("function A_test() { \$('#firstname_blur').show()  }; A_test()")
            config.evaluator.registerScripts("function B_test() { \$('[id=\"first.name\"]').val('Joe') }; B_test()")

            visit BASE_URL + 'popup.html'

            field = $('[id="first.name"]') as Input
            error = $('#firstname_blur') as Div

            assert !field.empty()
            assert error.visible()

            // Page with jquery already available
            visit BASE_URL + 'index.html'

            Div created = $('#created') as Div
            created.should { be missing }

            // Register scripts who
            // Create the missing TAG
            config.evaluator.registerScripts("function create() { var element = document.createElement('div'); " +
                "element.id = 'created'; document.body.appendChild(element);}; create()")

           visit BASE_URL + 'index.html'

            created = $('#created') as Div
            created.should { be available }

        } finally {
            config.evaluator.close()
        }
    }

    @Test
    void should_add_jquery_if_missing() {
        visit BASE_URL + 'popup.html'

        assert 'Joe' == config.evaluator.eval(null, "\$('#last_name').val('Joe').val()")
    }

    @Test
    void should_be_able_to_activate_logging() {
        assert !Log.debug
        config.debug = true
        assert Log.debug
    }
}
