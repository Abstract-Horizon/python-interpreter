package org.ah.libgdx.pygame.modules.pygame;

import org.junit.Test;

public class TestPyGameRect extends BaseTestClass {
    @Test public void canReturnXYWH() {
        executeLines(
            "import pygame",
            "",
            "r = pygame.Rect(5, 6, 7, 8)",
            "print(r.x)",
            "print(r.y)",
            "print(r.w)",
            "print(r.h)"
        ).assertResult("5\n6\n7\n8\n");
    }
    @Test public void canReturnLeftRightWidthHeight() {
        executeLines(
            "import pygame",
            "",
            "r = pygame.Rect(5, 6, 7, 8)",
            "print(r.left)",
            "print(r.top)",
            "print(r.width)",
            "print(r.height)"
        ).assertResult("5\n6\n7\n8\n");
    }
    @Test public void canReturnRightBottomCenter() {
        executeLines(
            "import pygame",
            "",
            "r = pygame.Rect(5, 6, 7, 8)",
            "print(r.right)",
            "print(r.bottom)",
            "print(r.center)"
        ).assertResult("12\n14\n(8, 10)\n");
    }
}
