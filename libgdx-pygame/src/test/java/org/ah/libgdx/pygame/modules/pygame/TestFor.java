package org.ah.libgdx.pygame.modules.pygame;

import org.junit.Test;

public class TestFor extends BaseTestClass {
    @Test public void testFor() {
        executeLines(
            "import pygame, sys",
            "i = 1",
            "while i < 4:",
            "    now = pygame.time.get_ticks()",
            "    key = pygame.key.get_pressed()",
            "    i = i + 1",
            "    for event in  pygame.event.get():",
            "        if event.type == pygame.QUIT:",
            "            pygame.quit()",
            "            sys.exit()"
        ).assertResult("");
        contextIsEmpty();
    }
}
