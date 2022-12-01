package net.trainsley69.isuck.mixin;

import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DisconnectedScreen.class)
public class DisconnectedScreenMixin extends Screen {
    protected DisconnectedScreenMixin(Text title) {
        super(title);
    }

    @Shadow @Final private Screen parent;

    @Inject(at = @At("HEAD"), method = "init")
    private void initWidgets(CallbackInfo ci) {
        this.addDrawableChild(new ButtonWidget(
                10,
                this.height - 30,
                100,
                20,
                Text.literal("Rejoin"),
                btn -> {
                    ((MultiplayerScreen)parent).connect();
                }
        ));
    }
}
