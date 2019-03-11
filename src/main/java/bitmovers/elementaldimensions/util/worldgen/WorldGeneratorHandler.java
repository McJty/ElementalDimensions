package bitmovers.elementaldimensions.util.worldgen;

import bitmovers.elementaldimensions.ElementalDimensions;
import elec332.core.api.discovery.ASMDataProcessor;
import elec332.core.api.discovery.IASMDataHelper;
import elec332.core.api.discovery.IASMDataProcessor;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.LoaderState;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by Elec332 on 4-8-2016.
 */
@ASMDataProcessor(LoaderState.POSTINITIALIZATION)
public class WorldGeneratorHandler implements IASMDataProcessor {

    @Override
    public void processASMData(IASMDataHelper iasmDataHelper, LoaderState loaderState) {
        for (ASMDataTable.ASMData asmData : iasmDataHelper.getAnnotationList(RegisteredWorldGenerator.class)){
            try {
                Class<?> clazz = Class.forName(asmData.getClassName());
                Object o = clazz.newInstance();
                if (o instanceof IWorldGenerator){
                    GameRegistry.registerWorldGenerator((IWorldGenerator) o, clazz.getAnnotation(RegisteredWorldGenerator.class).weight());
                }
            } catch (Exception e){
                ElementalDimensions.setup.getLogger().error("Error registering WorldGenerator: "+asmData.getClassName());
            }
        }
    }

}
