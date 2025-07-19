package net.tdp.test.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;
import net.tdp.test.block.entity.custom.PedestalBlockEntity;
import org.jetbrains.annotations.Nullable;


public class PedestalBlock extends BlockWithEntity implements BlockEntityProvider {
    private static final VoxelShape SHAPE =
            Block.createCuboidShape(2, 0, 2, 14, 13, 14);

    public static final MapCodec<PedestalBlock> CODEC = PedestalBlock.createCodec(PedestalBlock::new);

    public PedestalBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PedestalBlockEntity(pos, state);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof PedestalBlockEntity) {
                ItemScatterer.spawn(world, pos, ((PedestalBlockEntity) blockEntity));
                world.updateComparators(pos, this);
            }

            super.onStateReplaced(state, world, pos, newState, moved);
        }

    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos,
                                             PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.getBlockEntity(pos) instanceof PedestalBlockEntity pedestalBlockEntity) {
            ItemStack heldStack = player.getStackInHand(hand);

            // Try placing item on pedestal
            if (pedestalBlockEntity.isEmpty() && !heldStack.isEmpty()) {
                ItemStack singleItem = heldStack.copyWithCount(1);
                pedestalBlockEntity.setStack(0, singleItem);
                heldStack.decrement(1); // Safely decrease only one from hand

                world.playSound(player, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1f, 2f);

                pedestalBlockEntity.markDirty();
                world.updateListeners(pos, state, state, 3);

                return ItemActionResult.SUCCESS;

                // Try taking item from pedestal
            } else if (heldStack.isEmpty() && !player.isSneaking()) {
                ItemStack itemOnPedestal = pedestalBlockEntity.removeStack(0);

                // Try inserting into player's hand or inventory
                if (!player.getInventory().insertStack(itemOnPedestal)) {
                    player.dropItem(itemOnPedestal, false); // Drop if can't insert
                }

                world.playSound(player, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1f, 1f);

                pedestalBlockEntity.markDirty();
                world.updateListeners(pos, state, state, 3);

                return ItemActionResult.SUCCESS;
            } else if (player.isSneaking() && !world.isClient) {
                player.openHandledScreen(pedestalBlockEntity);
            }
        }

        return ItemActionResult.SUCCESS;
    }
}

