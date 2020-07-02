package optfine;

import java.util.Arrays;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;

public class ChunkCacheOF implements IBlockAccess
{
    private IBlockAccess blockAccess;
    private int posX;
    private int posY;
    private int posZ;
    private int[] combinedLights;
    private IBlockState[] blockStates;
    private static ArrayCache cacheCombinedLights = new ArrayCache(Integer.TYPE, 16);
    private static ArrayCache cacheBlockStates = new ArrayCache(IBlockState.class, 16);
    private static final int ARRAY_SIZE = 8000;

    public ChunkCacheOF(IBlockAccess p_i19_1_, BlockPos p_i19_2_, int p_i19_3_)
    {
        this.blockAccess = p_i19_1_;
        this.posX = p_i19_2_.getX() - p_i19_3_;
        this.posY = p_i19_2_.getY() - p_i19_3_;
        this.posZ = p_i19_2_.getZ() - p_i19_3_;
    }

    public int getCombinedLight(BlockPos pos, int lightValue)
    {
        if (this.combinedLights == null)
        {
            int k = this.blockAccess.getCombinedLight(pos, lightValue);

            if (Config.isDynamicLights() && !this.getBlockState(pos).isOpaqueCube())
            {
                k = DynamicLights.getCombinedLight(pos, k);
            }

            return k;
        }
        else
        {
            int i = this.getPositionIndex(pos);

            if (i >= 0 && i < this.combinedLights.length)
            {
                int j = this.combinedLights[i];

                if (j == -1)
                {
                    j = this.blockAccess.getCombinedLight(pos, lightValue);

                    if (Config.isDynamicLights() && !this.getBlockState(pos).isOpaqueCube())
                    {
                        j = DynamicLights.getCombinedLight(pos, j);
                    }

                    this.combinedLights[i] = j;
                }

                return j;
            }
            else
            {
                return this.blockAccess.getCombinedLight(pos, lightValue);
            }
        }
    }

    public IBlockState getBlockState(BlockPos pos)
    {
        if (this.blockStates == null)
        {
            return this.blockAccess.getBlockState(pos);
        }
        else
        {
            int i = this.getPositionIndex(pos);

            if (i >= 0 && i < this.blockStates.length)
            {
                IBlockState iblockstate = this.blockStates[i];

                if (iblockstate == null)
                {
                    iblockstate = this.blockAccess.getBlockState(pos);
                    this.blockStates[i] = iblockstate;
                }

                return iblockstate;
            }
            else
            {
                return this.blockAccess.getBlockState(pos);
            }
        }
    }

    private int getPositionIndex(BlockPos p_getPositionIndex_1_)
    {
        int i = p_getPositionIndex_1_.getX() - this.posX;
        int j = p_getPositionIndex_1_.getY() - this.posY;
        int k = p_getPositionIndex_1_.getZ() - this.posZ;
        return i * 400 + k * 20 + j;
    }

    public void renderStart()
    {
        if (this.combinedLights == null)
        {
            this.combinedLights = (int[])((int[])cacheCombinedLights.allocate(8000));
        }

        Arrays.fill((int[])this.combinedLights, (int) - 1);

        if (this.blockStates == null)
        {
            this.blockStates = (IBlockState[])((IBlockState[])cacheBlockStates.allocate(8000));
        }

        Arrays.fill(this.blockStates, (Object)null);
    }

    public void renderFinish()
    {
        cacheCombinedLights.free(this.combinedLights);
        this.combinedLights = null;
        cacheBlockStates.free(this.blockStates);
        this.blockStates = null;
    }

    /**
     * set by !chunk.getAreLevelsEmpty
     */
    public boolean extendedLevelsInChunkCache()
    {
        return this.blockAccess.extendedLevelsInChunkCache();
    }

    public Biome getBiomeGenForCoords(BlockPos pos)
    {
        return this.blockAccess.getBiomeGenForCoords(pos);
    }

    public int getStrongPower(BlockPos pos, EnumFacing direction)
    {
        return this.blockAccess.getStrongPower(pos, direction);
    }

    public TileEntity getTileEntity(BlockPos pos)
    {
        return this.blockAccess.getTileEntity(pos);
    }

    public WorldType getWorldType()
    {
        return this.blockAccess.getWorldType();
    }

    /**
     * Checks to see if an air block exists at the provided location. Note that this only checks to see if the blocks
     * material is set to air, meaning it is possible for non-vanilla blocks to still pass this check.
     */
    public boolean isAirBlock(BlockPos pos)
    {
        return this.blockAccess.isAirBlock(pos);
    }
}
