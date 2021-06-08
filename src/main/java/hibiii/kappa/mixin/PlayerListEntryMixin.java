package hibiii.kappa.mixin;

import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.util.Identifier;

import java.util.Map;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import hibiii.kappa.Provider;

@Mixin(PlayerListEntry.class)
public final class PlayerListEntryMixin {

	@Shadow @Final
	private GameProfile profile;
	@Shadow @Final
	private Map<MinecraftProfileTexture.Type, Identifier> textures;
	@Shadow
	private boolean texturesLoaded;

	// Note that loadTextures()V might be called like a foxton,
	// so rejecting to run it has to be really fast
	@Inject(
		at = @At("HEAD"),
		method = "loadTextures()V")
	protected void ltHInject(CallbackInfo info) {
		if(texturesLoaded) return;
		Provider.loadCape(this.profile, id -> {
			if(this.textures.get(MinecraftProfileTexture.Type.CAPE) == null) {
				this.textures.put(MinecraftProfileTexture.Type.CAPE, id);
			}
		});
	}
}
