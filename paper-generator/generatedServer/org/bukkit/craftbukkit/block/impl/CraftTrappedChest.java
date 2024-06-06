package org.bukkit.craftbukkit.block.impl;

import com.google.common.base.Preconditions;
import io.papermc.paper.generated.GeneratedFrom;
import java.util.Set;
import net.minecraft.world.level.block.TrappedChestBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Chest;
import org.bukkit.craftbukkit.block.data.CraftBlockData;

@GeneratedFrom("1.20.6")
public class CraftTrappedChest extends CraftBlockData implements Chest {
    private static final DirectionProperty FACING = TrappedChestBlock.FACING;

    private static final EnumProperty<ChestType> TYPE = TrappedChestBlock.TYPE;

    private static final BooleanProperty WATERLOGGED = TrappedChestBlock.WATERLOGGED;

    public CraftTrappedChest(BlockState state) {
        super(state);
    }

    @Override
    public BlockFace getFacing() {
        return this.get(FACING, BlockFace.class);
    }

    @Override
    public void setFacing(final BlockFace blockFace) {
        Preconditions.checkArgument(blockFace != null, "blockFace cannot be null!");
        Preconditions.checkArgument(blockFace.isCartesian() && blockFace.getModY() == 0, "Invalid face, only cartesian horizontal face are allowed for this property!");
        this.set(FACING, blockFace);
    }

    @Override
    public Set<BlockFace> getFaces() {
        return this.getValues(FACING, BlockFace.class);
    }

    @Override
    public Chest.Type getType() {
        return this.get(TYPE, Chest.Type.class);
    }

    @Override
    public void setType(final Chest.Type type) {
        Preconditions.checkArgument(type != null, "type cannot be null!");
        this.set(TYPE, type);
    }

    @Override
    public boolean isWaterlogged() {
        return this.get(WATERLOGGED);
    }

    @Override
    public void setWaterlogged(final boolean waterlogged) {
        this.set(WATERLOGGED, waterlogged);
    }
}