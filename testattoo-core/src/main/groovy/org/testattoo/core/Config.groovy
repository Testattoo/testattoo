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

import io.github.classgraph.ClassGraph
import io.github.classgraph.ClassInfo
import io.github.classgraph.ClassInfoList
import org.testattoo.core.component.Component
import org.testattoo.core.internal.Identifiers
import org.testattoo.core.internal.Log

import java.time.Duration

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
class Config {
    /**
     * Change default time to wait for wait for assertions to complete (waitUntil)
     */
    Duration waitUntil = 2.seconds

    final Collection<Class<Component>> componentTypes = new HashSet<>()

    /**
     * Activate debug mode
     */
    static void setDebug(boolean debug) {
        Log.debug = debug
    }

    /**
     * Scan for packages containing custom component
     */
    void scan(String... packageNames) {
        componentTypes.addAll(packageNames
            .collect {new ClassGraph().whitelistPackages(it).scan().getAllStandardClasses()
            .filter(new ClassInfoList.ClassInfoFilter() {
                @Override
                boolean accept(ClassInfo info) {
                    return info.className.indexOf('$') == -1
                }
            })}
            .flatten()
            .collect {
                it.loadClass()
            }
            .findAll { Component.isAssignableFrom(it) && Identifiers.hasIdentifier(it) })
    }

    /**
     * Sets the default evaluator to use
     */
    Evaluator evaluator



}
