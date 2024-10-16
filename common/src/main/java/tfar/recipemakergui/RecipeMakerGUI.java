package tfar.recipemakergui;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.RepositorySource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tfar.recipemakergui.init.ModMenuTypes;
import tfar.recipemakergui.menu.CraftingRecipeMakerMenu;
import tfar.recipemakergui.platform.Services;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// This class is part of the common project meaning it is shared between all supported loaders. Code written here can only
// import and access the vanilla codebase, libraries used by vanilla, and optionally third party libraries that provide
// common compatible binaries. This means common code can not directly use loader specific concepts such as Forge events
// however it will be compatible with all supported mod loaders.
public class RecipeMakerGUI {

    public static final String MOD_ID = "recipemakergui";
    public static final String MOD_NAME = "RecipeMakerGUI";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    // The loader specific projects are able to import and use any code from the common project. This allows you to
    // write the majority of your code here and load it from your loader specific projects. This example has some
    // code that gets invoked by the entry point of the loader specific projects.
    public static void init() {

        Services.PLATFORM.registerAll(ModMenuTypes.class, BuiltInRegistries.MENU, (Class<MenuType<?>>) (Object)MenuType.class);


        // It is common for all supported loaders to provide a similar feature that can not be used directly in the
        // common code. A popular way to get around this is using Java's built-in service loader feature to create
        // your own abstraction layer. You can learn more about this in our provided services class. In this example
        // we have an interface in the common code and use a loader specific implementation to delegate our call to
        // the platform specific approach.
    }

    public static void commands(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal(MOD_ID)
                .requires(commandSourceStack -> commandSourceStack.hasPermission(Commands.LEVEL_GAMEMASTERS))
                .executes(RecipeMakerGUI::openGui)
        );
    }

    static int openGui(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        CommandSourceStack source = ctx.getSource();
        ServerPlayer player = source.getPlayerOrException();
        player.openMenu(new MenuProvider() {
            @Override
            public Component getDisplayName() {
                return Component.literal("Crafting Table");
            }

            @Override
            public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
                return new CraftingRecipeMakerMenu(i,inventory);
            }
        });
        return 1;
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID,path);
    }



    public static Path GAME_DIR;

    static{
        GAME_DIR = Path.of(".");

        String launchArgument = System.getProperty("sun.java.command");

//		System.out.println(launchArgument);

        if(launchArgument == null){
            LOG.warn("Unable to find launch arguments, the mod might not function as expected.");
        }else if(launchArgument.contains("gameDir")){
            Pattern pattern = Pattern.compile("gameDir\\s(.+?)(?:\\s--|$)");
            Matcher matcher = pattern.matcher(launchArgument);
            if(!matcher.find()){
                LOG.error("Unable to find gameDir in launch arguments '{}' even though it was specified", "--reducted--");
            }else{
                String gameDirParam = matcher.group(1);
                GAME_DIR = Path.of(gameDirParam);
            }
        }

        setup();
    }

    public static final String LOCATION = "recipemakergui/";
    public static final String RECIPE_PATH = "/recipemakergui/data/recipemakergui/recipes";

    public static void setup() {
        if(Files.notExists(RecipeMakerGUI.getGameDir().resolve("recipemakergui").resolve("data")
                .resolve(RecipeMakerGUI.MOD_ID).resolve("recipes").resolve("pack.mcmeta"))){
            try {
                //create folders
                Files.createDirectories(new File(RecipeMakerGUI.getGameDir().toFile(), RECIPE_PATH).toPath());
                //create pack.mcmeta
                URL url = PackConfig.class.getClassLoader().getResource("pack.mcmeta");
                Files.copy(url.openStream(),RecipeMakerGUI.getGameDir().resolve("recipemakergui").resolve("pack.mcmeta"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Path getGameDir() {
        return GAME_DIR;
    }

    public static File[] getCustomRecipes() {
        File recipeDirectory = RecipeMakerGUI.getGameDir().resolve(RECIPE_PATH).toFile();
        return recipeDirectory.listFiles();
    }

    public static boolean doesNameExist(String name) {
        File file = new File("recipemakergui/data/recipemakergui/recipes/"+name+".json");
        return file.exists();
    }

    public static RepositorySource getRepositorySource(PackType type) {
        List<Path> files = new ArrayList<>();

     /*   List<String> packFolders = switch (type){
            case SERVER_DATA -> PackConfig.getRequiredDatapacks();
            default -> null;
        };*/

        files.add(GAME_DIR.resolve(Path.of(LOCATION)));

     /*   for (String packFolder : packFolders) {
            Path str = Path.of(packFolder);
            Path resolve = GAME_DIR.resolve(str);
            files.add(resolve);
        }*/

        return new GlobalPackFinder(type,  files);
    }

}