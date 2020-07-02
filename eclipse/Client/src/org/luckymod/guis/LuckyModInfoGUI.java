package org.luckymod.guis;

import java.awt.Color;
import java.io.IOException;

import org.luckymod.Constants;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

public class LuckyModInfoGUI extends GuiScreen {
	private int lines;
	private GuiScreen lastGui;
	public LuckyModInfoGUI(GuiScreen lastGui) {
		super();
		this.lastGui = lastGui;
	}
	@Override
	public void initGui() {
		this.buttonList.add(new GuiButton(1, width/3, height - (height/3),"Voltar"));
	}
	@Override
	public void updateScreen() {
	}
	@Override
	public void onGuiClosed() {
		
	}
	TextureManager engine;
	@Override
	public void setWorldAndResolution(Minecraft mc, int width, int height) {
		super.setWorldAndResolution(mc, width, height);
		engine = mc.getTextureManager();
	}
	private void drawStringAutoLine(String line, Color color) {
		drawCenteredString(mc.fontRendererObj, line, width/2.0f, (((float)height)/5)+(lines*mc.fontRendererObj.FONT_HEIGHT)+90, color.getRGB());
		lines++;
	}
	 
	
	@Override
	public void onRender(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		GlStateManager.color(1f,1f,1f,1f);
		GlStateManager.enableBlend();
		engine.bindTexture(new ResourceLocation("textures/misc/logo.png"));
		drawTexturedModalRect(((float)width)/3.5f, ((float)height)/5, 0, 90, 260,69);
		GlStateManager.disableBlend();
		
		drawStringAutoLine("Versão: " + Constants.VERSION, Color.WHITE);
		drawStringAutoLine("Site: luckymod.pt", Color.white);
		super.onRender(mouseX, mouseY, partialTicks);
		lines = 0;
	}
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		switch(button.id) {
			case 1:
				mc.displayGuiScreen(lastGui);
		}
	}
	
}
