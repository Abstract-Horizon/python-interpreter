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

    @Test public void testRectsMore() {
        executeLines(
            "import pygame",
            "",
            "screenRect = pygame.Rect(0, 0, 480, 768)",
            "x = screenRect[2] / 2",
            "print(x)",
            "#initialSpaceShipRect = pygame.Rect(screenRect[2] / 2 - 32, screenRect[3] - 84, 64, 48)",
            "#print(str(screenRect))",
            "#print(str(initialSpaceShipRect))"
        )
//        .assertResult("Rect[0, 0, 480, 768]\n");
        .assertResult("240.0\n");
    }



}
