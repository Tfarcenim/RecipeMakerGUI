package tfar.recipemakergui.mixin;

import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.RepositorySource;
import net.minecraft.server.packs.repository.ServerPacksSource;
import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import tfar.recipemakergui.RecipeMakerGUI;

@Mixin(ServerPacksSource.class)
public class ServerPackSourceMixin {

    @ModifyArg(
            method = "createPackRepository",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/packs/repository/PackRepository;<init>([Lnet/minecraft/server/packs/repository/RepositorySource;)V")
    )
    private static RepositorySource[] customCreatePackRepository(RepositorySource[] arg) {
        return ArrayUtils.addAll(arg, RecipeMakerGUI.getRepositorySource(PackType.SERVER_DATA));
    }
}
