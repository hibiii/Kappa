package hibiii.kappa.mixin;

import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.util.Identifier;

import java.util.Map;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import hibiii.kappa.Provider;

@Mixin(PlayerListEntry.class)
public final class PlayerListEntryMixin {

	@Shadow 
	private final GameProfile profile = null;
	@Shadow
	private final Map<MinecraftProfileTexture.Type, Identifier> textures = null;

	@Inject(
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/MinecraftClient;getInstance()Lnet/minecraft/client/MinecraftClient;"),
		method = "loadTextures()V")
	protected void ltHInject(CallbackInfo info) {
		Provider.loadCape(profile, id -> {
			if(this.textures.get(MinecraftProfileTexture.Type.CAPE) == null) {
				this.textures.put(MinecraftProfileTexture.Type.CAPE, id);
				this.textures.put(MinecraftProfileTexture.Type.ELYTRA, id);
			}
		});
	}
}
