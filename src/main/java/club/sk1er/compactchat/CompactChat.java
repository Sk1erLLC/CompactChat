package club.sk1er.compactchat;

import club.sk1er.compactchat.utils.Sk1erMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static club.sk1er.compactchat.CompactChat.*;

@Mod(modid = MODID, name = NAME, version = VERSION)
public class CompactChat {

    static final String MODID = "compactchat", NAME = "Compact Chat", VERSION = "1.2";

    private String lastMessage = "";
    private int line, amount;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        new Sk1erMod(MODID, VERSION, NAME).checkStatus();
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void chat(ClientChatReceivedEvent event) {
        if (!event.isCanceled() && event.getType() == 0) {
            GuiNewChat guiNewChat = Minecraft.getMinecraft().ingameGUI.getChatGUI();
            if (lastMessage.equals(event.getMessage().getUnformattedText())) {
                guiNewChat.deleteChatLine(line);
                amount++;
                lastMessage = event.getMessage().getUnformattedText();
                event.getMessage().appendText(TextFormatting.GRAY + " (" + amount + ")");
            } else {
                amount = 1;
                lastMessage = event.getMessage().getUnformattedText();
            }

            line++;
            if (!event.isCanceled()) {
                guiNewChat.printChatMessageWithOptionalDeletion(event.getMessage(), line);
            }

            if (line > 256) {
                line = 0;
            }

            event.setCanceled(true);
        }
    }
}
