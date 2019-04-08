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
package org.testattoo.core

import static org.testattoo.core.Testattoo.config

/**
 * @author David Avenante (d.avenante@gmail.com)
 */
class Window {
    final String id
    private String parent
    protected static List<Window> windows = []

    Window(String id) {
        this.id = id
    }

    void switchTo(Window window) {
        parent = config.evaluator.currentWindow()
        config.evaluator.switchToWindow(window.id)
    }

    void close() {
        config.evaluator.closeWindow(id)
        windows.removeAll { it.id == id }
        if(parent) config.evaluator.switchToWindow(parent)
    }

    @Override
    String toString() { this.id }

    boolean equals(o) {
        if (this.is(o)) return true
        if (this.class != o.class) return false

        Window window = (Window) o
        id == window.id
    }

    int hashCode() { id.hashCode() }
}
