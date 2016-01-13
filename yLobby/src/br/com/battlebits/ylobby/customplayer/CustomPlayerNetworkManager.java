package br.com.battlebits.ylobby.customplayer;

import java.lang.reflect.Field;
import java.net.SocketAddress;

import net.minecraft.server.v1_7_R4.NetworkManager;
import net.minecraft.util.io.netty.util.AttributeKey;

public class CustomPlayerNetworkManager extends NetworkManager {

	@SuppressWarnings("unchecked")
	public CustomPlayerNetworkManager() {
		super(false);

		try {
			Field channel = NetworkManager.class.getDeclaredField("m");
			Field address = NetworkManager.class.getDeclaredField("n");

			channel.setAccessible(true);
			address.setAccessible(true);

			CustomPlayerChannel parent = new CustomPlayerChannel();
			try {
				Field protocolVersion = NetworkManager.class.getDeclaredField("protocolVersion");
				parent.attr(((AttributeKey<Integer>) protocolVersion.get(null))).set(5);
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
			channel.set(this, parent);
			address.set(this, new SocketAddress() {
				private static final long serialVersionUID = 6994835504305404545L;
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}