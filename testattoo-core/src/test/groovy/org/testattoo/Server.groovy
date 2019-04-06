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
package org.testattoo

import io.undertow.Undertow
import io.undertow.server.handlers.resource.PathResourceManager
import io.undertow.server.handlers.resource.ResourceHandler

import java.nio.file.Paths

import static io.undertow.Handlers.resource

class Server {
    static Undertow undertow(int port) {
        String helper = new File(".").absolutePath

        ResourceHandler handler = resource(new PathResourceManager(Paths.get(helper + "/src/test/webapp")))
            .addWelcomeFiles("index.html")
            .setDirectoryListingEnabled(true)

        return Undertow.builder()
            .addHttpListener(port, "localhost")
            .setHandler(handler)
            .build()
    }
}
