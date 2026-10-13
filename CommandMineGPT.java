package com.minegpt.command;

import com.minegpt.entity.EntityMineGPT;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class CommandMineGPT extends CommandBase {
    @Override public String getName() { return "minegpt"; }
    @Override public String getUsage(ICommandSender sender) { return "/minegpt <spawn|status>"; }
    @Override public int getRequiredPermissionLevel() { return 0; }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 0 || "help".equalsIgnoreCase(args[0])) {
            sender.sendMessage(new TextComponentString("[MineGPT] Commands: /minegpt spawn, /minegpt status"));
            return;
        }
        if ("spawn".equalsIgnoreCase(args[0])) {
            World world = sender.getEntityWorld();
            BlockPos p = sender.getPosition();
            EntityMineGPT bot = new EntityMineGPT(world);
            bot.setPosition(p.getX() + 1.5D, p.getY() + 1.0D, p.getZ() + 0.5D);
            world.spawnEntity(bot);
            sender.sendMessage(new TextComponentString("[MineGPT] Prototype spawned. I can follow and wander, but advanced learning is not implemented yet."));
            return;
        }
        if ("status".equalsIgnoreCase(args[0])) {
            sender.sendMessage(new TextComponentString("[MineGPT] Version 0.1 prototype: follow player, wander, basic hostile-mob combat. No chat, wood gathering, persistent learning, or cheat approval system yet."));
            return;
        }
        sender.sendMessage(new TextComponentString(getUsage(sender)));
    }
}
