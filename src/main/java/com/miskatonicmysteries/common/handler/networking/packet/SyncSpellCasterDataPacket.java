package com.miskatonicmysteries.common.handler.networking.packet;

import com.miskatonicmysteries.common.feature.interfaces.SpellCaster;
import com.miskatonicmysteries.common.handler.networking.PacketHandler;
import com.miskatonicmysteries.common.lib.Constants;
import com.miskatonicmysteries.common.lib.util.CapabilityUtil;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class SyncSpellCasterDataPacket {
    public static final Identifier ID = new Identifier(Constants.MOD_ID, "sync_spell");

    public static void send(boolean client, PlayerEntity user, SpellCaster caster) {
        PacketByteBuf data = new PacketByteBuf(Unpooled.buffer());
        CompoundTag spellCompound = CapabilityUtil.writeSpellData(caster, new CompoundTag());
        data.writeCompoundTag(spellCompound);
        if (client) {
            ClientPlayNetworking.send(ID, data);
        } else {
            PacketHandler.sendToPlayer(user, data, ID);
        }
    }

    public static void handleFromClient(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf packetByteBuf, PacketSender sender) {
        CompoundTag tag = packetByteBuf.readCompoundTag();
        server.execute(() -> SpellCaster.of(player).ifPresent(caster -> CapabilityUtil.readSpellData(caster, tag)));
    }

    public static void handleFromServer(MinecraftClient client, ClientPlayNetworkHandler networkHandler, PacketByteBuf packetByteBuf, PacketSender sender) {
        CompoundTag tag = packetByteBuf.readCompoundTag();
        client.execute(() -> SpellCaster.of(client.player).ifPresent(caster -> CapabilityUtil.readSpellData(caster, tag)));
    }
}
