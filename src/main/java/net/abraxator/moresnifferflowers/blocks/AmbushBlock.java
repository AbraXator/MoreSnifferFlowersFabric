package net.abraxator.moresnifferflowers.blocks;

import net.abraxator.moresnifferflowers.blocks.blockentities.AmbushBlockEntity;
import net.abraxator.moresnifferflowers.init.ModBlockEntities;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.abraxator.moresnifferflowers.init.ModItems;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.*;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class AmbushBlock extends TallPlantBlock implements Fertilizable, ModCropBlock, BlockEntityProvider {
    public static final IntProperty AGE = IntProperty.of("age", 0, 8);
    public static final int MAX_AGE = 7;
    public static final int AGE_TO_GROW_UP = 4;

    public AmbushBlock(Settings settings) {
        super(settings);
        setDefaultState(this.getDefaultState().with(HALF, DoubleBlockHalf.LOWER));
    }

    private boolean isMaxAge(BlockState state) {
        return state.get(AGE) >= MAX_AGE;
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return !isMaxAge(state);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState();
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if(!state.canPlaceAt(world, pos) && world.getBlockEntity(pos) instanceof AmbushBlockEntity entity && isHalf(state, DoubleBlockHalf.UPPER) && entity.growProgress >= 1) {
            dropStack((World) world, pos, new ItemStack(ModBlocks.AMBER));
        }
        return !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : state;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        if(this.isHalf(state, DoubleBlockHalf.UPPER)) {
            BlockState blockState = world.getBlockState(pos.down());
            boolean b = blockState.isOf(this);
            return b && isHalf(blockState, DoubleBlockHalf.LOWER);
        } else {
            return this.canPlantOnTop(world.getBlockState(pos.down()), world, pos.down()) && sufficientLight(world, pos) && state.get(AGE) < AGE_TO_GROW_UP || isHalf(world.getBlockState(pos.up()), DoubleBlockHalf.UPPER);
        }
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isOf(Blocks.FARMLAND);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
        super.appendProperties(builder);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if(entity instanceof RavagerEntity && world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
            world.breakBlock(pos, true, entity);
        }

        super.onEntityCollision(state, world, pos, entity);
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        return false;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        float f = getAvailableMoisture(this, world, pos);
        if(random.nextInt((int) ((25.0F / f) + 1)) == 0) {
            this.grow(world, state, pos, 1);
        }
    }

    private void grow(ServerWorld pLevel, BlockState pState, BlockPos pPos, int i) {
        int k = Math.min(pState.get(AGE) + i, MAX_AGE);
        if(this.canGrow(pLevel, pPos, pState, k)) {
            pLevel.setBlockState(pPos, pState.with(AGE, k), 2);
            if(k >= AGE_TO_GROW_UP) {
                pLevel.setBlockState(pPos.up(), this.getDefaultState().with(AGE, k).with(HALF, DoubleBlockHalf.UPPER), 3);
            }

            if(pLevel.getBlockEntity(pPos) instanceof AmbushBlockEntity entity) {
                entity.growProgress = 0;
            }
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack usedStack = player.getStackInHand(hand);
        if(usedStack.isOf(Items.BONE_MEAL)) {
            return ActionResult.PASS;
        } else if(world.getBlockEntity(pos) instanceof AmbushBlockEntity entity && entity.hasAmber && isHalf(state, DoubleBlockHalf.UPPER)) {
            dropStack(world, pos, new ItemStack(ModBlocks.AMBER));

            BlockPos lowerPos = isHalf(state, DoubleBlockHalf.LOWER) ? pos : pos.down();
            BlockPos upperPos = isHalf(state, DoubleBlockHalf.UPPER) ? pos : pos.up();
            BlockState lowerState = world.getBlockState(lowerPos).with(AGE, 7);
            BlockState upperState = world.getBlockState(upperPos).with(AGE, 7);
            world.setBlockState(lowerPos, lowerState, 3);
            world.setBlockState(upperPos, upperState, 3);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, lowerPos, GameEvent.Emitter.of(player, lowerState));
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, upperPos, GameEvent.Emitter.of(player, upperState));

            entity.reset(pos, state, world);
            return ActionResult.success(world.isClient());
        } else {
            return super.onUse(state, world, pos, player, hand, hit);
        }
    }

    private boolean canGrowInto(BlockState state) {
        return state.isAir() || state.isOf(ModBlocks.AMBUSH);
    }

    private boolean sufficientLight(WorldView pLevel, BlockPos pPos) {
        return pLevel.getBaseLightLevel(pPos, 0) >= 8 || pLevel.isSkyVisible(pPos);
    }

    private boolean isHalf(BlockState state, DoubleBlockHalf half) {
        return state.isOf(ModBlocks.AMBUSH) && state.get(HALF) == half;
    }

    private boolean canGrow(WorldView pLevel, BlockPos pPos, BlockState pState, int k) {
        return !this.isMaxAge(pState) && sufficientLight(pLevel, pPos) && (k < AGE_TO_GROW_UP || canGrowInto(pLevel.getBlockState(pPos.up())));
    }

    private PosAndState getLowerHalf(WorldView level, BlockPos blockPos, BlockState state) {
        if(isHalf(state, DoubleBlockHalf.LOWER)) {
            return new PosAndState(blockPos, state);
        } else {
            BlockPos posBelow = blockPos.down();
            BlockState stateBelow = level.getBlockState(posBelow);
            return isHalf(stateBelow, DoubleBlockHalf.LOWER) ? new PosAndState(posBelow, stateBelow) : null;
        }
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {}

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return ModItems.AMBUSH_SEEDS.getDefaultStack();
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient) {
        PosAndState posAndState = this.getLowerHalf(world, pos, state);
        return posAndState != null && this.canGrow(world, posAndState.blockPos(), posAndState.state(), posAndState.state().get(AGE) + 1);
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        PosAndState posAndState = this.getLowerHalf(world, pos, state);
        if(posAndState != null) {
            this.grow(world, posAndState.state(), posAndState.blockPos(), 1);
        }
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new AmbushBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? null : checkType(type, ModBlockEntities.AMBUSH, AmbushBlockEntity::tick);
    }

    @Nullable
    protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> checkType(BlockEntityType<A> givenType, BlockEntityType<E> expectedType, BlockEntityTicker<? super E> ticker) {
        return expectedType == givenType ? (BlockEntityTicker<A>) ticker : null;
    }
}
