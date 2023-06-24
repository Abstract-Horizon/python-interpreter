package org.ah.libgdx.pygame.modules.pygame;

public class PyGameSprite extends org.ah.python.interpreter.Module {

    public PyGameSprite() {
        super("pygame.sprite");
        __setattr__("Sprite", PyGameSurfaceSprite.PYGAME_SPRITE_CLASS);
    }
}

