package net.trainsley69.isuck.mixin;

import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;
import net.trainsley69.isuck.ISuck;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameOptions.class)
public class GammaMixin {
    @Shadow
    private SimpleOption<Double> gamma;
    @Inject(method="getGamma", at = @At("HEAD"), cancellable = true)
    public void getGamma(CallbackInfoReturnable<SimpleOption<Double>> cir) {
        SimpleOption<Double> fullbrightGamma = new SimpleOption<Double>("optiong.fullbright",
                SimpleOption.emptyTooltip(),
                (x, y) -> Text.literal("No more dark place"),
                SimpleOption.DoubleSliderCallbacks.INSTANCE,
                999.0,
                x -> {}
        );

        cir.setReturnValue(ISuck.config.Fullbright ? fullbrightGamma : gamma);
    }
}
