package chylex.serverproperties.props.unsupported;
import chylex.serverproperties.mixin.DedicatedServerPropertiesMixin;
import chylex.serverproperties.props.PropertyChangeCallback;
import chylex.serverproperties.props.ServerProperty;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.dedicated.DedicatedServerProperties;

import java.util.Optional;

public final class ResourcePackHashProperty extends ServerProperty<String> {
	public static final ResourcePackHashProperty INSTANCE = new ResourcePackHashProperty();
	
	private ResourcePackHashProperty() {}
	
	@Override
	public String get(final DedicatedServerProperties properties) {
		MinecraftServer.ServerResourcePackInfo o = properties.serverResourcePackInfo.orElse(null);
		if(o == null) return null;
		return properties.serverResourcePackInfo.get().hash();
	}
	
	@Override
	public void apply(final DedicatedServer server, final DedicatedServerPropertiesMixin target, final String value, final PropertyChangeCallback callback) {
		throw new UnsupportedOperationException();
	}
}
