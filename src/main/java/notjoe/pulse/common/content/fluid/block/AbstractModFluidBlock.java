package notjoe.pulse.common.content.fluid.block;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class AbstractModFluidBlock extends BlockFluidClassic {
    public AbstractModFluidBlock(Fluid fluid) {
        super(fluid, Material.WATER);
    }

    @Override
    public String getUnlocalizedName() {
        return getFluid().getUnlocalizedName();
    }

    @SideOnly(Side.CLIENT)
    public IStateMapper getCustomStateMapper() {
        return new StateMap.Builder().ignore(LEVEL).build();
    }
}
