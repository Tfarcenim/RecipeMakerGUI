package tfar.recipemakergui.menu;

import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

public class MakerProvider<T extends RecipeMakerMenu> implements MenuProvider {

    private final Component name;
    private final MenuType.MenuSupplier<T> supplier;

    public MakerProvider(Item item, MenuType.MenuSupplier<T> supplier) {
        this.name = item.getDefaultInstance().getHoverName();
        this.supplier = supplier;
    }

    @Override
    public Component getDisplayName() {
        return name;
    }

    @Nullable
    @Override
    public T createMenu(int i, Inventory inventory, Player player) {
        return supplier.create(i,inventory);
    }
}
