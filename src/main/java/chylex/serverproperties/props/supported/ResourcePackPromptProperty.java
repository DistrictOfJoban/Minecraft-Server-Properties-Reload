package chylex.serverproperties.props.supported;
import chylex.serverproperties.mixin.DedicatedServerMixin;
import chylex.serverproperties.mixin.DedicatedServerPropertiesMixin;
import chylex.serverproperties.props.PropertyChangeCallback;
import chylex.serverproperties.props.ServerProperty;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.dedicated.DedicatedServerProperties;
import net.minecraft.server.dedicated.DedicatedServerSettings;

public final class ResourcePackPromptProperty extends ServerProperty<String> {
	public static final ResourcePackPromptProperty INSTANCE = new ResourcePackPromptProperty();

	private ResourcePackPromptProperty() {}

	@Override
	public String get(final DedicatedServerProperties properties) {
		MinecraftServer.ServerResourcePackInfo o = properties.serverResourcePackInfo.orElse(null);
		if(o == null || o.prompt() == null) return null;
		return o.prompt().getString();
	}

	@Override
	public void apply(final DedicatedServer server, final DedicatedServerPropertiesMixin target, final String value, final PropertyChangeCallback callback) {
		DedicatedServerSettings settings = ((DedicatedServerMixin)server).getSettings();
		DedicatedServerProperties prop = settings.getProperties();
		MinecraftServer.ServerResourcePackInfo packInfo = prop.serverResourcePackInfo.orElse(null);
		if(packInfo == null) return;

		prop.serverResourcePackInfo = ((DedicatedServerPropertiesMixin) prop).getServerPackInfo(packInfo.url(), packInfo.hash(), packInfo.hash(), packInfo.isRequired(), value);
	}
}
