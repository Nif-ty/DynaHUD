package com.niftymods.plugin.systems;

import com.hypixel.hytale.builtin.npccombatactionevaluator.corecomponents.CombatTargetCollector;
import com.hypixel.hytale.builtin.npccombatactionevaluator.evaluator.CombatActionEvaluator;
import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.NPCPlugin;
import com.hypixel.hytale.server.npc.entities.NPCEntity;
import com.hypixel.hytale.server.npc.movement.controllers.MotionController;
import com.hypixel.hytale.server.npc.role.Role;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.Objects;

// UNUSED CUZ FUCK KNOWS HOW TO GET THIS WORKING
public class CombatSeekEventSystem extends EntityTickingSystem<EntityStore> {

    public static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();

    @Override
    public void tick(
            float dt,
            int index,
            @NonNullDecl ArchetypeChunk<EntityStore> archetypeChunk,
            @NonNullDecl Store<EntityStore> store,
            @NonNullDecl CommandBuffer<EntityStore> commandBuffer
    ) {
        CombatActionEvaluator combatActionEvaluator = archetypeChunk.getComponent(index, CombatActionEvaluator.getComponentType());
        if (combatActionEvaluator == null) return;
        Ref<EntityStore> targetRef = combatActionEvaluator.getPrimaryTarget();
        if (targetRef == null) return;
        if (!targetRef.isValid()) return;
        Player player = targetRef.getStore().getComponent(targetRef, Player.getComponentType());
        if (player == null)  return;
        player.sendMessage(Message.raw("FOR THE LOVE OF GOD PLEASE WORK!"));

        // Figure the fuck out how to:
        // Get if NPC is targeting player
        // and get the player that is being targetted
    }

    @NullableDecl
    @Override
    public Query<EntityStore> getQuery() {
        return NPCEntity.getComponentType();
    }
}
