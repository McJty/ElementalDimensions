package bitmovers.elementaldimensions.ncLayer;

import bitmovers.elementaldimensions.commands.CommandReloadConfig;
import bitmovers.elementaldimensions.commands.CommandReloadSchematics;
import bitmovers.elementaldimensions.setup.CommonSetup;
import bitmovers.elementaldimensions.util.EDResourceLocation;
import bitmovers.elementaldimensions.util.command.ElementalDimensionsCommand;
import bitmovers.elementaldimensions.world.*;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.relauncher.FMLInjectionData;

import java.io.File;

/**
 * Created by Elec332 on 4-8-2016.
 */
public class NCLayerMain {

    public static NCLayerMain instance;
    public static File mcDir;

    public void preInit(FMLPreInitializationEvent event){

    }

    public void init(FMLInitializationEvent event){
        mcDir = (File) FMLInjectionData.data()[6];

        for (int i = 1; i < 5; i++) {
            SchematicLoader.INSTANCE.registerSchematic(new EDResourceLocation("schematics/test"+i+".schematic"));
        }
        SchematicLoader.INSTANCE.registerSchematic(WorldGeneratorPortalDungeon.dungeonResource, true);
        SchematicLoader.INSTANCE.registerSchematic(WorldGeneratorEarthDungeon.dungeonResource, true);
        SchematicLoader.INSTANCE.registerSchematic(WorldGeneratorEarthDungeon.towerResource, true);
        SchematicLoader.INSTANCE.registerSchematic(WorldGeneratorWaterDungeon.dungeonResource, true);
        SchematicLoader.INSTANCE.registerSchematic(WorldGeneratorAirDungeon.dungeonResource, true);
        SchematicLoader.INSTANCE.registerSchematic(WorldGeneratorSpiritDungeon.dungeonResource, true);
    }

    public void postInit(FMLPostInitializationEvent event){
        CommonSetup.registerCommand(new CommandReloadSchematics());
        CommonSetup.registerCommand(new CommandReloadConfig());
        SchematicLoader.INSTANCE.reloadCache();
//        for (int i = 1; i < 5; i++) {
//            GameRegistry.registerWorldGenerator(new DefaultStructureCreator(new EDResourceLocation("schematics/test"+i+".schematic"), GenerationType.SURFACE), 100 + 1);
//        }
    }

    public void serverStarting(FMLServerStartingEvent event){
        event.registerServerCommand(new ElementalDimensionsCommand());
    }

    static {
        instance = new NCLayerMain();
    }

}
