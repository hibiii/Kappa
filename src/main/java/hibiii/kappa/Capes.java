package hibiii.kappa;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.Identifier;

public final class Capes {

	public static Map<String,Identifier> store = new HashMap<String,Identifier>();

	public static void clear() {
		TextureManager texMgr = MinecraftClient.getInstance().getTextureManager();
		for(Identifier id : Capes.store.values()) {
			texMgr.destroyTexture(id);
		}
		Capes.store = new HashMap<String,Identifier>();
	}

	private Capes() {}
}
