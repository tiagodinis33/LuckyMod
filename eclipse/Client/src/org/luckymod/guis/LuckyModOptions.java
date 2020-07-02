package org.luckymod.guis;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.world.BossInfo.Color;

public class LuckyModOptions extends GuiScreen {

	private GuiButton enablePanoramaButton;
	private GuiScreen lastGui;
	private GuiButton toggleSneakButton;
	private GameSettings gameSettings;
	private GuiButton toggleSprintButton;
	private GuiButton toggleBlackBGButton;

	public LuckyModOptions(GuiScreen lastGui, GameSettings settings){
		this.lastGui = lastGui;
		gameSettings = settings;
	}
	@Override
	public void onRender(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, "Configurações do LuckyMod", this.width / 2, 15, java.awt.Color.GREEN.getRGB());
		super.onRender(mouseX, mouseY, partialTicks);
	}
	@Override
	public void initGui() {
        enablePanoramaButton = new GuiButton(0, this.width / 3 - 100, this.height / 6 + 35, (gameSettings.getLuckyOptionB("enablePanorama")? "Desativar " : "Ativar ")+"Panorama");
        buttonList.add(enablePanoramaButton);
        buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height - 50, "Voltar"));
        toggleSneakButton = new GuiButton(2, this.width / 3 - 100, this.height / 6 + 60, (gameSettings.getLuckyOptionB("toggleSneak")? "Desativar " : "Ativar ")+"ToggleSneak");
        buttonList.add(toggleSneakButton);
        toggleSprintButton = new GuiButton(3, this.width / 3 - 100, this.height / 6 + 85, (gameSettings.getLuckyOptionB("toggleSprint")? "Desativar " : "Ativar ")+"ToggleSprint");
		buttonList.add(toggleSprintButton);
		toggleBlackBGButton = new GuiButton(4, this.width / 3 - 100, this.height / 6 + 110, (gameSettings.getLuckyOptionB("blackBG")? "Desativar " : "Ativar ")+"Fundo do inventario");
        buttonList.add(toggleBlackBGButton);
	}
	
	@Override
	public void updateScreen() {
	}
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		switch(button.id) {
			case 0:
				mc.gameSettings.setOptionValueLuck("enablePanorama", !gameSettings.getLuckyOptionB("enablePanorama"));
		        this.mc.gameSettings.saveOptions();
		        enablePanoramaButton.displayString = (gameSettings.getLuckyOptionB("enablePanorama")? "Desativar " : "Ativar ")+"Panorama";
				break;
			case 1:
				mc.displayGuiScreen(lastGui);
				break;
			case 2:
				gameSettings.setOptionValueLuck("toggleSneak", !gameSettings.getLuckyOptionB("toggleSneak"));
		        gameSettings.saveOptions();
		        toggleSneakButton.displayString = (gameSettings.getLuckyOptionB("toggleSneak")? "Desativar " : "Ativar ")+"ToggleSneak";
				break;
			case 3:
				gameSettings.setOptionValueLuck("toggleSprint", !gameSettings.getLuckyOptionB("toggleSprint"));
		        gameSettings.saveOptions();
		        toggleSprintButton.displayString = (gameSettings.getLuckyOptionB("toggleSprint")? "Desativar " : "Ativar ")+"ToggleSprint";
				break;
			case 4:
				gameSettings.setOptionValueLuck("blackBG", !gameSettings.getLuckyOptionB("blackBG"));
		        gameSettings.saveOptions();
		        toggleBlackBGButton.displayString = (gameSettings.getLuckyOptionB("blackBG")? "Desativar " : "Ativar ")+"Fundo do inventario";
				break;
			}
	}
	@Override
	public void onGuiClosed() {
		
	}

}
