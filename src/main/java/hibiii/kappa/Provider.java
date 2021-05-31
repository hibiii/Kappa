package hibiii.kappa;

import java.net.URL;

import com.mojang.authlib.GameProfile;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

public final class Provider {
	
	public static void loadCape(GameProfile player, CapeTextureAvailableCallback callback) {
		Runnable runnable = () -> {
			try {
				URL url = new URL("http://s.optifine.net/capes/" + player.getName() + ".png");
				NativeImage tex = uncrop(NativeImage.read(url.openStream()));
				NativeImageBackedTexture nIBT = new NativeImageBackedTexture(tex);
				Identifier id = MinecraftClient.getInstance().getTextureManager().registerDynamicTexture("kappa" + player.getName().toLowerCase(), nIBT);
				Capes.store.put(player.getName(), id);
				callback.onTexAvail(id);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		};
		Util.getMainWorkerExecutor().execute(runnable);
	}

	public static interface CapeTextureAvailableCallback {
		public void onTexAvail(Identifier id);
	}

	// This is a provider specific implementation.
	// Images are usually 46x22 or 92x44, and these work as expected (32, 64).
	// There are edge cages with sizes 184x88, 1024x512 and 2048x1024,
	// but these should work alright.
	private static NativeImage uncrop(NativeImage in) {
		int zoom = (int) Math.ceil(in.getHeight() / 32f);
		NativeImage out = new NativeImage(64 * zoom, 32 * zoom, true);
		out.copyFrom(in);
		return out;
	}

	private Provider() { }
}
