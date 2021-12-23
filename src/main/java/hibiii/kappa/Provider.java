package hibiii.kappa;

import java.math.BigInteger;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.mojang.authlib.GameProfile;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.util.Session;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

public final class Provider {
	
	// This is where capes will be stored
	public static Map<String, Identifier> capes = new HashMap<String, Identifier>();

	// This loads the cape for one player, doesn't matter if it's the player or not.
	// Requires a callback, that receives the id for the cape
	public static void loadCape(GameProfile player, CapeTextureAvailableCallback callback) {
		Runnable runnable = () -> {
			try {
				URL url = new URL("https://optifine.net/capes/" + player.getName() + ".png");
				NativeImage tex = uncrop(NativeImage.read(url.openStream()));
				NativeImageBackedTexture nIBT = new NativeImageBackedTexture(tex);
				Identifier id = MinecraftClient.getInstance().getTextureManager().registerDynamicTexture("kappa" + player.getName().toLowerCase(), nIBT);
				capes.put(player.getName(), id);
				callback.onTexAvail(id);
			}
			catch (Exception e) {
			}
		};
		Util.getMainWorkerExecutor().execute(runnable);
	}

	// Gets the URL to change your cape
	public static String getChangeUrl(Session session) {
		BigInteger intA = new BigInteger(128, new Random());
		BigInteger intB = new BigInteger(128, new Random(System.identityHashCode(new Object())));
		String fakeId = intA.xor(intB).toString(16);
		try {
			MinecraftClient.getInstance().getSessionService().joinServer(session.getProfile(), session.getAccessToken(),
					fakeId);
		} catch (Exception e) {
			return null;
		}
		return "https://optifine.net/capeChange?n=" + session.getUsername() + "&u=" + session.getUuid() + "&s=" + fakeId;
	}

	public static interface CapeTextureAvailableCallback {
		public void onTexAvail(Identifier id);
	}

	// This is a provider specific implementation.
	// Images are usually 46x22 or 92x44, and these work as expected (32, 64).
	// There are edge cages with sizes 184x88, 1024x512 and 2048x1024,
	// but these should work alright.
	private static NativeImage uncrop(NativeImage in) {
		int srcHeight = in.getHeight(), srcWidth = in.getWidth();
		int zoom = (int) Math.ceil(in.getHeight() / 32f);
		NativeImage out = new NativeImage(64 * zoom, 32 * zoom, true);
		// NativeImage.copyFrom doesn't work! :(
		for (int x = 0; x < srcWidth; x++) {
			for (int y = 0; y < srcHeight; y++) {
				out.setColor(x, y, in.getColor(x, y));
			}
        }
		return out;
	}

	private Provider() { }
}
