package hibiii.kappa.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import hibiii.kappa.Provider;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.screen.option.SkinOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.Text;
import net.minecraft.util.Util;

@Mixin(SkinOptionsScreen.class)
public abstract class SkinOptionsScreenMixin extends GameOptionsScreen {

	public SkinOptionsScreenMixin(Screen parent, GameOptions gameOptions, Text title) {
		super(parent, gameOptions, title);
	}

	// This is supposed to be a quick and simple mod for snapshots, FAPI may not be available,
	// and there should be little harm in using a hardcoded string in english.
	private final Text changeBtnText = Text.translatable("Open Cape Editor");
	
	@Inject(
		at = @At("TAIL"),
		method = "init()V")
	public void iTInject(CallbackInfo info) {
		this.addDrawableChild(ButtonWidget.method_46430(
			changeBtnText,
			(btn) -> {
				String url = Provider.getChangeUrl(this.client.getSession());
				if(url == null) {
					btn.active = false;
					return;
				}
				Util.getOperatingSystem().open(url);
			}
		).method_46434(
			this.width - 155, this.height - 25,
			             150,               20
		).method_46431());
	}
}
