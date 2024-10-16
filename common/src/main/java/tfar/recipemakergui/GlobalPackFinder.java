package tfar.recipemakergui;

import com.google.common.collect.ImmutableList;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.*;
import net.minecraft.server.packs.linkfs.LinkFileSystem;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.RepositorySource;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class GlobalPackFinder implements RepositorySource {

    private static final Predicate<Path> IS_VALID_DATA_PACK = (pack) -> {
        boolean flag = true;
        if (Files.isRegularFile(pack) && pack.toString().endsWith(".zip")) {
            try (FileSystem fs = FileSystems.newFileSystem(pack)) {
                flag &= Files.isDirectory(fs.getPath("data/"));
                flag &= Files.isRegularFile(fs.getPath("pack.mcmeta"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            flag &= Files.isDirectory(pack.resolve("data"));
            flag &= Files.isRegularFile(pack.resolve("pack.mcmeta"));
        }
        return flag;
    };

    private static final PackSource GLOBAL = PackSource.create(name -> name.copy().append(" (Global)").withStyle(ChatFormatting.AQUA), true);

    private final PackType packType;
    private final ImmutableList<Path> packLocations;


    public GlobalPackFinder(PackType packType, List<Path> packLocations) {
        this.packType = packType;
        this.packLocations = ImmutableList.<Path>builder().addAll(packLocations).build();
    }

    @Override
    public void loadPacks(Consumer<Pack> packRegistrar) {
        this.discoverResourcePacks((path) -> {
            Pack.ResourcesSupplier resourceSupplier;
            if (Files.isRegularFile(path) && path.toString().endsWith(".zip")) {
                resourceSupplier = this.createFilePack(path);
            } else {
                resourceSupplier = this.createFolderPack(path);
            }

            if (resourceSupplier != null) {
                Pack pack = Pack.readMetaAndCreate(path.getFileName().toString(), Component.literal(path.getFileName().toString()), true, resourceSupplier, this.packType, Pack.Position.TOP, GLOBAL);
                if (pack != null) {
                    packRegistrar.accept(pack);
                }
            }
        });
    }

    private Pack.ResourcesSupplier createFilePack(Path path) {
        FileSystem fs = path.getFileSystem();
        if (this.packType == PackType.SERVER_DATA && !IS_VALID_DATA_PACK.test(path)) {
            return null;
        } else {
            return fs != FileSystems.getDefault() && !(fs instanceof LinkFileSystem) ? null : (needle) -> {
                return new FilePackResources(needle, path.toFile(), false);
            };
        }
    }

    private Pack.ResourcesSupplier createFolderPack(Path path) {
        return this.packType == PackType.SERVER_DATA && !IS_VALID_DATA_PACK.test(path) ? null : (needle) -> {
            return new PathPackResources(needle, path, false);
        };
    }

    private void discoverResourcePacks(Consumer<Path> packCallback) {
        for (Path path : this.packLocations) {
            if (Files.isDirectory(path)) {
                if (Files.isRegularFile(path.resolve("pack.mcmeta"))) {
                    packCallback.accept(path);
                    continue;
                }

                try {
                    if (Files.notExists(path)) Files.createDirectories(path);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try (Stream<Path> fileStream = Files.list(path)) {
                    fileStream.forEach(filePath -> {
                        if (Files.isRegularFile(filePath) || (Files.isDirectory(filePath) && Files.isRegularFile(filePath.resolve("pack.mcmeta"))))
                            packCallback.accept(filePath);
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                packCallback.accept(path);
            }
        }
    }
}
