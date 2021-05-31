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

	@Inject(
		at = @At("HEAD"),
		method = "loadTextures()V")
	protected void ltHInject(CallbackInfo info) {
		if(!texturesLoaded) {
			Provider.loadCape(this.profile, id -> {
				if(this.textures.get(MinecraftProfileTexture.Type.CAPE) == null) {
					this.textures.put(MinecraftProfileTexture.Type.CAPE, id);
					this.textures.put(MinecraftProfileTexture.Type.ELYTRA, id);
				}
			});
		}
	}
}
