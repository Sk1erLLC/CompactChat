package club.sk1er.compactchat;

import club.sk1er.compactchat.utils.Sk1erMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static club.sk1er.compactchat.CompactChat.*;

@Mod(modid = MODID, name = NAME, version = VERSION)
public class CompactChat {

    static final String MODID = "compactchat", NAME = "Compact Chat", VERSION = "1.0";

    private String lastMessage = "";
    private int line, amount;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        new Sk1erMod(MODID, VERSION, NAME).checkStatus();
    }

    @SubscribeEvent
    public void chat(ClientChatReceivedEvent event) {
        if (!event.isCanceled() && event.type == 0) {
            GuiNewChat guiNewChat = Minecraft.getMinecraft().ingameGUI.getChatGUI();
            if (lastMessage.equals(event.message.getUnformattedText())) {
                guiNewChat.deleteChatLine(line);
                amount++;
                lastMessage = event.message.getUnformattedText();
                event.message.appendText(EnumChatFormatting.GRAY + " (" + amount + ")");
            } else {
                amount = 1;
                lastMessage = event.message.getUnformattedText();
            }

            line++;
            if (!event.isCanceled()) {
                guiNewChat.printChatMessageWithOptionalDeletion(event.message, line);
            }

            if (line > 256) {
                line = 0;
            }

            event.setCanceled(true);
        }
    }
}
