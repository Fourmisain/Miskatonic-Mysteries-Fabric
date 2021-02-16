package com.miskatonicmysteries.mixin;

import com.miskatonicmysteries.api.interfaces.Hallucination;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityMixin {
    @Shadow
    public World world;

    @Inject(method = "playSound", at = @At("HEAD"), cancellable = true)
    private void playSound(SoundEvent sound, float volume, float pitch, CallbackInfo ci) {
        Hallucination.of(this).ifPresent(hallucination -> {
            if (hallucination.getHallucinationTarget().isPresent()) {
                if (!world.isClient) {
                    ci.cancel();
                } else if (!hallucination.getHallucinationTarget().get().equals(MinecraftClient.getInstance().player.getUuid())) {
                    ci.cancel();
                }
            }
        });
    }
}