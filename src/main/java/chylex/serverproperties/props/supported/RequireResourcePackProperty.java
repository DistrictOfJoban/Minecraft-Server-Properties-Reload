package chylex.serverproperties.props.supported;
import chylex.serverproperties.mixin.DedicatedServerMixin;
import chylex.serverproperties.mixin.DedicatedServerPropertiesMixin;
import chylex.serverproperties.props.BoolServerProperty;
import chylex.serverproperties.props.PropertyChangeCallback;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.dedicated.DedicatedServerProperties;
import net.minecraft.server.dedicated.DedicatedServerSettings;

public final class RequireResourcePackProperty extends BoolServerProperty {
	public static final RequireResourcePackProperty INSTANCE = new RequireResourcePackProperty();
	
	private RequireResourcePackProperty() {}
	
	@Override
	protected boolean getBool(final DedicatedServerProperties properties) {
		MinecraftServer.ServerResourcePackInfo o = properties.serverResourcePackInfo.orElse(null);
		if(o == null) return false;
		return o.isRequired();
	}
	
	@Override
	protected void applyBool(final DedicatedServer server, final DedicatedServerPropertiesMixin target, final boolean value, final PropertyChangeCallback callback) {
		DedicatedServerSettings settings = ((DedicatedServerMixin)server).getSettings();
		DedicatedServerProperties ogProp = settings.getProperties();
		ogProp.serverResourcePackInfo.ifPresent(packInfo -> settings.update((prop) -> {
			String prompt = packInfo.prompt() == null ? null : net.minecraft.network.chat.Component.Serializer.toJson(packInfo.prompt());
			prop.serverResourcePackInfo = ((DedicatedServerPropertiesMixin) prop).getServerPackInfo(packInfo.url(), packInfo.hash(), packInfo.hash(), value, prompt);
			return prop;
		}));
	}
}
