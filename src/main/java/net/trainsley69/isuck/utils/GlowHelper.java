package net.trainsley69.isuck.utils;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;

public class GlowHelper {
    public enum EntityType {
        HOSTILE,
        PASSIVE,
        PLAYER,
        INVALID
    }

    public static EntityType getEntityType(Entity entity) {
        if (entity instanceof PlayerEntity && !isPlayer(entity)) return EntityType.PLAYER;
        if (entity instanceof HostileEntity) return EntityType.HOSTILE;
        if (entity instanceof PassiveEntity) return EntityType.PASSIVE;
        return EntityType.INVALID;
    }

    private static boolean isPlayer(Entity entity) {
        return entity instanceof ClientPlayerEntity;
    }


    public static final int GOLD_COLOR = 0xFFAA00;
    public static final int DARK_RED_COLOR = 0xAA0000;
    public static final int GREEN_COLOR = 0x55FF55;
    public static final int NO_COLOR = -1;

    public static int getGlowColor(EntityType entity) {
        return switch (entity) {
            case PLAYER -> GOLD_COLOR;
            case HOSTILE -> DARK_RED_COLOR;
            case PASSIVE -> GREEN_COLOR;
            default -> NO_COLOR;
        };
    }
}
