package chylex.serverproperties.mixin;

import chylex.serverproperties.command.PropertiesCommand;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.Commands;
import net.minecraft.commands.Commands.CommandSelection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Commands.class)
public class CommandMixin {
	@Inject(method = "<init>", at = @At("RETURN"))
	private void init(final CommandSelection commandSelection, final CommandBuildContext context, final CallbackInfo ci) {
		@SuppressWarnings("ConstantConditions")
		final Commands commands = (Commands)(Object)this;
		PropertiesCommand.register(commands.getDispatcher());
	}
}
