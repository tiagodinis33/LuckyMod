package optfine;

import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.settings.GameSettings;

public class GuiOptionButtonOF extends GuiOptionButton implements IOptionControl
{
    private GameSettings.Options option = null;

    public GuiOptionButtonOF(int p_i34_1_, int p_i34_2_, int p_i34_3_, GameSettings.Options p_i34_4_, String p_i34_5_)
    {
        super(p_i34_1_, p_i34_2_, p_i34_3_, p_i34_4_, p_i34_5_);
        this.option = p_i34_4_;
    }

    public GameSettings.Options getOption()
    {
        return this.option;
    }
}
