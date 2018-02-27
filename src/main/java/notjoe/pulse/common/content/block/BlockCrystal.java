package notjoe.pulse.common.content.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import notjoe.pulse.common.content.block.base.AbstractModBlock;

public class BlockCrystal extends AbstractModBlock {
    public BlockCrystal() {
        super("crystal", Material.GLASS);
        setSoundType(SoundType.GLASS);
    }
}
