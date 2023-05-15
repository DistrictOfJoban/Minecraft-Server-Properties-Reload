package chylex.serverproperties.props.supported;
import chylex.serverproperties.mixin.DedicatedServerMixin;
import chylex.serverproperties.mixin.DedicatedServerPropertiesMixin;
import chylex.serverproperties.props.PropertyChangeCallback;
import chylex.serverproperties.props.ServerProperty;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.dedicated.DedicatedServerProperties;
import net.minecraft.server.dedicated.DedicatedServerSettings;

public final class ResourcePackSha1Property extends ServerProperty<String> {
	public static final ResourcePackSha1Property INSTANCE = new ResourcePackSha1Property();

	private ResourcePackSha1Property() {}

	@Override
	public String get(final DedicatedServerProperties properties) {
		MinecraftServer.ServerResourcePackInfo o = properties.serverResourcePackInfo.orElse(null);
		return o.hash();
	}

	@Override
	public void apply(final DedicatedServer server, final DedicatedServerPropertiesMixin target, final String value, final PropertyChangeCallback callback) {
		DedicatedServerSettings settings = ((DedicatedServerMixin)server).getSettings();
		DedicatedServerProperties prop = settings.getProperties();
		MinecraftServer.ServerResourcePackInfo packInfo = prop.serverResourcePackInfo.orElse(null);
		if(packInfo == null) return;

		String prompt = packInfo.prompt() == null ? null : net.minecraft.network.chat.Component.Serializer.toJson(packInfo.prompt());
		((DedicatedServerMixin)server).getSettings().getProperties().serverResourcePackInfo = ((DedicatedServerPropertiesMixin) prop).getServerPackInfo(packInfo.url(), value, null, packInfo.isRequired(), prompt);
	}
}
