package org.testattoo.core

import static org.testattoo.core.Testattoo.config

/**
 * @author David Avenante (d.avenante@gmail.com)
 */
class Browser {
    static void navigateTo(String url) { config.evaluator.to(url) }

    static void back() { config.evaluator.back() }

    static void forward() { config.evaluator.forward() }

    static void refresh() { config.evaluator.refresh() }

    static String getTitle() { config.evaluator.title }

    static String getPageSource() { config.evaluator.pageSource }

    static String getUrl() { config.evaluator.url }

    static void open(String url) { config.evaluator.open(url) }

    static List<Window> getWindows() {
        config.evaluator.windowIds.each { String id ->
            if(!Window.windows.any { it.id == id }) {
                Window.windows.add(new Window(id))
            }
        }
        return Window.windows
    }

    static void switchTo(Window window) { window.switchTo(window) }
}
