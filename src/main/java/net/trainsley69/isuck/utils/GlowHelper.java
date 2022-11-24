package net.trainsley69.isuck.utils;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;

public class GlowHelper {
    public enum EntityType {
            Hostile,
            Passive,
            Player,
            Invalid
    }

    public static EntityType getEntityType(Entity entity) {
        if (entity instanceof PlayerEntity && !isPlayer(entity)) return EntityType.Player;
        if (entity instanceof HostileEntity) return EntityType.Hostile;
        if (entity instanceof PassiveEntity) return EntityType.Passive;
        return EntityType.Invalid;
    }

    private static boolean isPlayer(Entity entity) {
        return entity instanceof ClientPlayerEntity;
    }

    public static int getGlowColor(EntityType entity) {
        switch (entity) {
            case Player -> { return 16777215; }
            case Hostile -> { return 11141120; }
            case Passive -> { return 5635925; }
            default -> { return -1; }
        }
    }
}
