package chylex.serverproperties.props.supported;
import chylex.serverproperties.mixin.DedicatedServerMixin;
import chylex.serverproperties.mixin.DedicatedServerPropertiesMixin;
import chylex.serverproperties.props.PropertyChangeCallback;
import chylex.serverproperties.props.ServerProperty;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.dedicated.DedicatedServerProperties;
import net.minecraft.server.dedicated.DedicatedServerSettings;

public final class ResourcePackProperty extends ServerProperty<String> {
	public static final ResourcePackProperty INSTANCE = new ResourcePackProperty();

	private ResourcePackProperty() {}

	@Override
	public String get(final DedicatedServerProperties properties) {
		MinecraftServer.ServerResourcePackInfo o = properties.serverResourcePackInfo.orElse(null);
		return o.url();
	}

	@Override
	public void apply(final DedicatedServer server, final DedicatedServerPropertiesMixin target, final String value, final PropertyChangeCallback callback) {
		DedicatedServerSettings settings = ((DedicatedServerMixin)server).getSettings();
		DedicatedServerProperties ogProp = settings.getProperties();
		ogProp.serverResourcePackInfo.ifPresent(packInfo -> settings.update((prop) -> {
			String prompt = packInfo.prompt() == null ? null : net.minecraft.network.chat.Component.Serializer.toJson(packInfo.prompt());
			prop.serverResourcePackInfo = ((DedicatedServerPropertiesMixin) prop).getServerPackInfo(value, packInfo.hash(), packInfo.hash(), packInfo.isRequired(), prompt);
			return prop;
		}));
	}
}
