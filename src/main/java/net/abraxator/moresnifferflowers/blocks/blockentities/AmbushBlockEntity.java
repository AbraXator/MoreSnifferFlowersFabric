package net.abraxator.moresnifferflowers.blocks.blockentities;

import net.abraxator.moresnifferflowers.blocks.AmbushBlock;
import net.abraxator.moresnifferflowers.init.ModBlockEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class AmbushBlockEntity extends BlockEntity {
    public float growProgress;
    public boolean hasAmber;

    public AmbushBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.AMBUSH, pos, state);
    }

    public static void tick(World world, BlockPos blockPos, BlockState blockState, AmbushBlockEntity entity) {
        if(canGrow(blockState, entity.growProgress, entity.hasAmber)) {
            entity.growProgress += 0.001;
            world.updateListeners(blockPos, blockState, blockState, Block.NOTIFY_LISTENERS);
            if(entity.growProgress >= 1) {
                entity.onGrow(blockPos, blockState, world);
            }
        }
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        hasAmber = nbt.getBoolean("hasAmber");
        growProgress = nbt.getFloat("progress");
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putBoolean("hasAmber", hasAmber);
        nbt.putFloat("progress", growProgress);
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        NbtCompound nbt = super.createNbt();
        writeNbt(nbt);
        return nbt;
    }

    public void onGrow(BlockPos blockPos, BlockState state, World world) {
        hasAmber = true;
        this.world.updateListeners(blockPos, state, state, Block.NOTIFY_LISTENERS);
    }

    public void reset(BlockPos blockPos, BlockState state, World world) {
        growProgress = 0;
        hasAmber = false;
    }

    public static boolean canGrow(BlockState state, float growProgress, boolean hasAmber) {
        return state.get(AmbushBlock.AGE).equals(7) && !(growProgress >= 1) && !hasAmber;
    }
}
