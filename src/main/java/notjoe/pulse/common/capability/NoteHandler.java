package notjoe.pulse.common.capability;

import net.minecraft.util.EnumFacing;
import notjoe.pulse.common.content.entity.EntityNote;

public interface NoteHandler {
    void receiveNote(EntityNote note, EnumFacing facing);
}
