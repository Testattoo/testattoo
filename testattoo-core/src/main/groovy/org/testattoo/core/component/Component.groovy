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
package org.testattoo.core.component

import org.hamcrest.Matcher
import org.testattoo.core.ComponentException
import org.testattoo.core.MetaDataProvider
import org.testattoo.core.Selector
import org.testattoo.core.input.DragBuilder
import org.testattoo.core.support.Clickable
import org.testattoo.core.support.Draggable

import static org.testattoo.core.Testattoo.config
import static org.testattoo.core.input.MouseModifiers.*

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
class Component implements Clickable, Draggable {
    private final Queue<Matcher> BLOCKS = new LinkedList<>()

    MetaDataProvider meta

    protected Component() {}

    Component(MetaDataProvider meta) {
        this()
        this.meta = meta
    }

    String id() {
        meta.metaInfo(this).id
    }

    boolean enabled() {
        !config.evaluator.check(id(), "it.is(':disabled') || !!it.attr('disabled')")
    }

    boolean available() {
        try {
            meta.metaInfo(this)
            return true
        } catch (ComponentException ignored) {
            return false
        }
    }

    boolean visible() {
        !config.evaluator.check(id(), "it.is(':hidden')")
    }

    protected <T extends Component> List<T> find(Selector selector, Class<T> type = Component) {
        config.evaluator.metaInfo(selector.expression(this)).collect { it.asType(type) } as List<T>
    }

    void clearBlocks() {
        BLOCKS.clear()
    }

    boolean addBlock(Matcher matcher) {
        BLOCKS.add(matcher)
    }

    Collection<Matcher> getBlocks() {
        Collections.unmodifiableCollection(BLOCKS)
    }

    @Override
    void click() {
        config.evaluator.click(id(), [LEFT, SINGLE], [])
    }

    @Override
    void rightClick() {
        config.evaluator.click(id(), [RIGHT, SINGLE], [])
    }

    @Override
    void doubleClick() {
        config.evaluator.click(id(), [LEFT, DOUBLE], [])
    }

    @Override
    DragBuilder drag() {
        new DragBuilder(this)
    }

    @Override
    boolean equals(o) {
        if (this.is(o)) return true
        if (this.class != o.class) return false
        Component component = (Component) o
        id() == component.id()
    }

    @Override
    int hashCode() { id().hashCode() }

    @Override
    String toString() { this.class.simpleName + ":${this.id()}" }

    Object asType(Class clazz) {
        if (Component.isAssignableFrom(clazz)) {
            Component c = (Component) clazz.newInstance()
            c.meta = this.meta
            return c
        }
        return super.asType(clazz)
    }
}
