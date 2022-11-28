package net.trainsley69.isuck.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import net.trainsley69.isuck.input.KeyBindings;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;

@Mixin(GameOptions.class)
public class GameOptionsMixin {

    @Shadow @Final @Mutable private KeyBinding[] allKeys;
    @Shadow private MinecraftClient client;

    @Inject(
            method = "<init>",
            at = @At(
                    value = "INVOKE",
                    shift = At.Shift.BEFORE,
                    target = "Lnet/minecraft/client/option/GameOptions;load()V"
            )
    )
    private void initOptions(CallbackInfo ci) {
        Collection<KeyBinding> isuckKeys = KeyBindings.getKeyBindings();

        KeyBinding[] mcKeys = allKeys;
        allKeys = new KeyBinding[mcKeys.length + isuckKeys.size()];

        int index = 0;
        for (int i = 0; i < mcKeys.length; i++) {
            allKeys[index++] = mcKeys[i];
        }
        for (KeyBinding key : isuckKeys) {
            allKeys[index++] = key;
        }
    }
}
