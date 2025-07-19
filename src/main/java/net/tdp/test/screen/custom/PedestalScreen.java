package net.tdp.test.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.tdp.test.Test;

public class PedestalScreen extends HandledScreen<PedestalScreenHandler> {
    public static final Identifier GUI_TEXTURE =
            Identifier.of(Test.MOD_ID, "textures/gui/pedestal/pedestal_gui.png");

    public PedestalScreen(PedestalScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionProgram);
        RenderSystem.setShaderColor(1,1,1, 1);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);

            int x = (width - backgroundWidth) / 2;
            int y = (height - backgroundHeight) / 2;

            context.drawTexture(GUI_TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
