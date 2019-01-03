package bitmovers.elementaldimensions.blocks.altar;

import bitmovers.elementaldimensions.ElementalDimensions;
import bitmovers.elementaldimensions.blocks.GenericBlock;
import bitmovers.elementaldimensions.init.ItemRegister;
import bitmovers.elementaldimensions.items.ItemElementalWand;
import bitmovers.elementaldimensions.util.Config;
import bitmovers.elementaldimensions.util.InventoryHelper;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class AltarCenterBlock extends GenericBlock implements ITileEntityProvider {

    public static final PropertyBool WORKING = PropertyBool.create("working");

    public AltarCenterBlock() {
        super("altarcenter", Material.ROCK);
        setHardness(3.0f);
        setResistance(5.0f);
        setSoundType(SoundType.STONE);
        GameRegistry.registerTileEntity(AltarCenterTileEntity.class, ElementalDimensions.MODID + ":altarcenter");
    }

    @Override
    public void initClient() {
        super.initClient();
        AltarCenterRenderer.register();
    }


    @Override
    @Nonnull
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new AltarCenterTileEntity();
    }

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
        super.addProbeInfo(mode, probeInfo, player, world, blockState, data);
        TileEntity te = world.getTileEntity(data.getPos());
        if (te instanceof AltarCenterTileEntity) {
            AltarCenterTileEntity altar = (AltarCenterTileEntity) te;
            ItemStack dustStack = altar.getDust();
            int dust = !dustStack.isEmpty() ? dustStack.getCount() : 0;
            probeInfo.text(TextFormatting.GREEN + "Dust: " + TextFormatting.WHITE + dust);
            ItemStack chargingItem = altar.getChargingItem();
            if (!chargingItem.isEmpty()) {
                if (chargingItem.getItem() == ItemRegister.elementalWand) {
                    probeInfo.progress(ItemElementalWand.getDustLevel(chargingItem), Config.Wand.maxDust);
//                    probeInfo.text(TextFormatting.GREEN + "Wand charge: " + TextFormatting.YELLOW + ItemElementalWand.getDustLevel(chargingItem));
                }
            }
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TextFormatting.GREEN + "This altar can charge the elemental wand");
        tooltip.add(TextFormatting.GREEN + "Right click with dust and give a redstone");
        tooltip.add(TextFormatting.GREEN + "signal");
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
        checkRedstone(world, pos);
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntity tileentity = worldIn.getTileEntity(pos);

        if (tileentity instanceof AltarCenterTileEntity) {
            AltarCenterTileEntity altar = (AltarCenterTileEntity) tileentity;
            ItemStack dust = altar.getDust();
            if (!dust.isEmpty()) {
                InventoryHelper.spawnItemStack(worldIn, pos, dust);
                altar.setDust(ItemStack.EMPTY);
            }
            ItemStack chargedItem = altar.getChargingItem();
            if (!chargedItem.isEmpty()) {
                InventoryHelper.spawnItemStack(worldIn, pos, chargedItem);
                altar.setChargingItem(ItemStack.EMPTY);
            }
        }

        super.breakBlock(worldIn, pos, state);
    }

    protected void checkRedstone(World world, BlockPos pos) {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof AltarCenterTileEntity) {
            int powered = world.isBlockIndirectlyGettingPowered(pos); //TODO: check
            AltarCenterTileEntity altar = (AltarCenterTileEntity) te;
            altar.setWorking(powered);
        }
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        TileEntity te = world instanceof ChunkCache ? ((ChunkCache) world).getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK) : world.getTileEntity(pos);
        boolean working = false;
        if (te instanceof AltarCenterTileEntity) {
            working = ((AltarCenterTileEntity) te).isWorking();
        }
        return state.withProperty(WORKING, working);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, WORKING);
    }

    private AltarCenterTileEntity getTE(World world, BlockPos pos) {
        return (AltarCenterTileEntity) world.getTileEntity(pos);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return false;
    }


//    @Override
//    public boolean isBlockSolid(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
//        return false;
//    }

    @Override
    public boolean isBlockNormalCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }


    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            ItemStack heldItem = player.getHeldItem(hand);
            AltarCenterTileEntity altar = getTE(world, pos);

            if (!heldItem.isEmpty() && heldItem.getItem() == ItemRegister.elementalDustItem) {
                IItemHandler handler = altar.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
                ItemStack remainder = handler.insertItem(0, heldItem, false);
                player.setHeldItem(EnumHand.MAIN_HAND, remainder);
            } else if (altar.getChargingItem().isEmpty()) {
                if (!heldItem.isEmpty()) {
                    // There is no item in the pedestal and the player is holding an item. We move that item
                    // to the pedestal
                    altar.setChargingItem(heldItem);
                    player.inventory.setInventorySlotContents(player.inventory.currentItem, ItemStack.EMPTY);
                    // Make sure the client knows about the changes in the player inventory
                    player.openContainer.detectAndSendChanges();
                }
            } else {
                // There is a stack in the pedestal. In this case we remove it and try to put it in the
                // players inventory if there is room
                ItemStack stack = altar.getChargingItem();
                altar.setChargingItem(ItemStack.EMPTY);
                if (!player.inventory.addItemStackToInventory(stack)) {
                    // Not possible. Throw item in the world
                    EntityItem entityItem = new EntityItem(world, pos.getX(), pos.getY() + 1, pos.getZ(), stack);
                    world.spawnEntity(entityItem);
                } else {
                    player.openContainer.detectAndSendChanges();
                }
            }
        }

        // Return true also on the client to make sure that MC knows we handled this and will not try to place
        // a block on the client
        return true;
    }
}
