package bitmovers.elementaldimensions.dimensions;

import bitmovers.elementaldimensions.mobs.EntityDirtZombieBoss;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Random;

public class DimensionTicker {


    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent evt) {
        if (evt.phase == TickEvent.Phase.START) {
            return;
        }
        Dimensions dimension = Dimensions.findDimension(evt.world.provider.getDimension());
        if (dimension == null) {
            return;
        }
        switch (dimension) {
            case EARTH:
                handleEarth(evt);
                break;
            case WATER:
                break;
            case AIR:
                break;
            case SPIRIT:
                break;
            case FIRE:
                break;
            case OVERWORLD:
                break;
        }
    }

    private void handleEarth(TickEvent.WorldTickEvent evt) {
        long worldTime = evt.world.getWorldTime();
        if (Math.abs(worldTime-18000) < 500) {
            // It is around midnight now
            int count = evt.world.countEntities(EntityDirtZombieBoss.class);
            if (count == 0) {
                BlockPos pos = findAveragePlayerPos(evt.world);
                if (pos != null) {
                    EntityDirtZombieBoss boss = new EntityDirtZombieBoss(evt.world);
                    boss.setPosition(pos.getX(), pos.getY(), pos.getZ());
                    evt.world.spawnEntityInWorld(boss);
                }
            }
        }
    }

    private static Random random = new Random();

    private static BlockPos findAveragePlayerPos(World world) {
        AxisAlignedBB b = null;
        for (EntityPlayer entity : world.playerEntities) {
            BlockPos position = entity.getPosition();
            if (b == null) {
                b = new AxisAlignedBB(position);
            } else {
                b = b.union(new AxisAlignedBB(position));
            }
        }
        if (b == null) {
            return null;
        }
        BlockPos center = new BlockPos((b.maxX+b.minX) / 2 + random.nextInt(50) - 25, (b.maxY+b.minY) / 2, (b.maxZ+b.minZ) / 2 + random.nextInt(50) - 25);
        return world.getTopSolidOrLiquidBlock(center);
    }

}