package notjoe.pulse.common.content.container.base;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

// Adapted from MBE30: https://github.com/TheGreyGhost/MinecraftByExample
public abstract class AbstractModContainer extends Container {
    private static final int HOTBAR_SLOTS = 9;
    private static final int PLAYER_INVENTORY_ROWS = 3;
    private static final int PLAYER_INVENTORY_COLUMNS = 9;
    private static final int PLAYER_INVENTORY_SLOTS = PLAYER_INVENTORY_ROWS * PLAYER_INVENTORY_COLUMNS;
    private static final int PLAYER_INVENTORY_AND_HOTBAR_SLOTS = HOTBAR_SLOTS + PLAYER_INVENTORY_SLOTS;

    private static final int FIRST_PLAYER_SLOT = 0;

    private final IItemHandler itemHandler;
    private final EntityPlayer player;
    private final int slotWidth;
    private final int slotHeight;

    public AbstractModContainer(IItemHandler itemHandler, EntityPlayer player, int slotWidth, int slotHeight) {
        this.itemHandler = itemHandler;
        this.player = player;
        this.slotWidth = slotWidth;
        this.slotHeight = slotHeight;
    }

    public void addPlayerInventory(int startX, int startY) {
        for (int y = 0; y < PLAYER_INVENTORY_ROWS; y++) {
            for (int x = 0; x < PLAYER_INVENTORY_COLUMNS; x++) {
                int slot = HOTBAR_SLOTS + (y * PLAYER_INVENTORY_COLUMNS) + x;
                int slotX = startX + (x * slotWidth);
                int slotY = startY + (y * slotHeight);
                addSlotToContainer(new Slot(player.inventory, slot, slotX, slotY));
            }
        }
    }

    public void addPlayerHotbar(int startX, int startY) {
        for (int x = 0; x < HOTBAR_SLOTS; x++) {
            addSlotToContainer(new Slot(player.inventory, x, startX + (slotWidth * x), startY));
        }
    }

    public void addStackHandlerSlots(int startX, int startY, int rows, int columns, int startIndex) {
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                int slot = startIndex + x + (columns * y);
                int slotX = startX + (x * slotWidth);
                int slotY = startY + (y * slotHeight);
                addSlotToContainer(new SlotItemHandler(itemHandler, slot, slotX, slotY));
            }
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        Slot source = inventorySlots.get(index);

        if ((source == null) || !source.getHasStack()) {
            return ItemStack.EMPTY;
        }

        ItemStack sourceStack = source.getStack();
        ItemStack sourceStackCopy = sourceStack.copy();

        if ((index >= FIRST_PLAYER_SLOT) && (index < (FIRST_PLAYER_SLOT + PLAYER_INVENTORY_AND_HOTBAR_SLOTS))) {
            boolean mergedFullStack = !mergeItemStack(sourceStack, FIRST_PLAYER_SLOT + PLAYER_INVENTORY_AND_HOTBAR_SLOTS, FIRST_PLAYER_SLOT + PLAYER_INVENTORY_AND_HOTBAR_SLOTS + itemHandler.getSlots(), false);
            if (mergedFullStack) {
                return ItemStack.EMPTY;
            }
        } else if ((index >= (FIRST_PLAYER_SLOT + PLAYER_INVENTORY_AND_HOTBAR_SLOTS)) && (index < (FIRST_PLAYER_SLOT + PLAYER_INVENTORY_AND_HOTBAR_SLOTS + itemHandler.getSlots()))) {
            boolean mergedFullStack = !mergeItemStack(sourceStack, FIRST_PLAYER_SLOT, FIRST_PLAYER_SLOT + PLAYER_INVENTORY_AND_HOTBAR_SLOTS, false);
            if (mergedFullStack) {
                return ItemStack.EMPTY;
            }
        } else {
            return ItemStack.EMPTY;
        }

        if (sourceStack.isEmpty()) {
            source.putStack(ItemStack.EMPTY);
        } else {
            source.onSlotChanged();
        }

        source.onTake(player, sourceStack);
        return sourceStackCopy;
    }
}
