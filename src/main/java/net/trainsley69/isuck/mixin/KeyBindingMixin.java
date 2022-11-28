package net.trainsley69.isuck.mixin;

import net.minecraft.client.option.KeyBinding;
import net.trainsley69.isuck.input.KeyBindings;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(KeyBinding.class)
public class KeyBindingMixin {
    @Shadow @Final private static Map<String, Integer> CATEGORY_ORDER_MAP;

    @Inject(at = @At("RETURN"), method = "<clinit>")
    private static void initKeyBindings(CallbackInfo ci) {
        CATEGORY_ORDER_MAP.put(KeyBindings.MAIN, CATEGORY_ORDER_MAP.size() + 1);
    }
}
