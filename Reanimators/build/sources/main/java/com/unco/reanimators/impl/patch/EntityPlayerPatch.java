package com.unco.reanimators.impl.patch;

import com.unco.reanimators.Seppuku;
import com.unco.reanimators.api.event.player.EventApplyCollision;
import com.unco.reanimators.api.event.player.EventPushedByWater;
import com.unco.reanimators.api.patch.ClassPatch;
import com.unco.reanimators.api.patch.MethodPatch;
import com.unco.reanimators.impl.management.PatchManager;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import static org.objectweb.asm.Opcodes.*;

/**
 * Author Seth
 * 4/8/2019 @ 6:47 PM.
 */
public final class EntityPlayerPatch extends ClassPatch {

    public EntityPlayerPatch() {
        super("net.minecraft.entity.player.EntityPlayer", "aed");
    }

    /**
     * This is where minecraft handles
     * water pushing
     * Normally in creative mode you dont
     * get pushed by water
     * @param methodNode
     * @param env
     */
    @MethodPatch(
            mcpName = "isPushedByWater",
            notchName = "bo",
            mcpDesc = "()Z")
    public void isPushedByWater(MethodNode methodNode, PatchManager.Environment env) {
        //create a list of instructions
        final InsnList insnList = new InsnList();
        //call our hook function
        insnList.add(new MethodInsnNode(INVOKESTATIC, Type.getInternalName(this.getClass()), "isPushedByWaterHook", "()Z", false));
        //create a label to jump to
        final LabelNode jmp = new LabelNode();
        //add "if equals"
        insnList.add(new JumpInsnNode(IFEQ, jmp));
        //add 0 or false
        insnList.add(new InsnNode(ICONST_0));
        //return so the rest of the function doesnt get called
        insnList.add(new InsnNode(IRETURN));
        //add our label
        insnList.add(jmp);
        //insert the list of instructs at the top of the function
        methodNode.instructions.insert(insnList);
    }

    /**
     * Our isPushedByWater hook
     * @return
     */
    public static boolean isPushedByWaterHook() {
        //dispatch our event
        final EventPushedByWater event = new EventPushedByWater();
        Seppuku.INSTANCE.getEventManager().dispatchEvent(event);

        return event.isCanceled();
    }

    /**
     * This is where minecraft handles entities
     * colliding with each other
     * @param methodNode
     * @param env
     */
    @MethodPatch(
            mcpName = "applyEntityCollision",
            notchName = "i",
            mcpDesc = "(Lnet/minecraft/entity/Entity;)V",
            notchDesc = "(Lvg;)V")
    public void applyEntityCollision(MethodNode methodNode, PatchManager.Environment env) {
        //create a list of instructions
        final InsnList insnList = new InsnList();
        //call our hook function
        insnList.add(new MethodInsnNode(INVOKESTATIC, Type.getInternalName(this.getClass()), "applyEntityCollisionHook", "()Z", false));
        //create a label to jump to
        final LabelNode jmp = new LabelNode();
        //add "if equals"
        insnList.add(new JumpInsnNode(IFEQ, jmp));
        //return so the rest of the function doesnt get called
        insnList.add(new InsnNode(RETURN));
        //add our label
        insnList.add(jmp);
        //insert the list of instructs at the top of the function
        methodNode.instructions.insert(insnList);
    }

    /**
     * Our applyEntityCollision hook
     * Used to cancel entity collision
     * @return
     */
    public static boolean applyEntityCollisionHook() {
        //dispatch our event
        final EventApplyCollision event = new EventApplyCollision();
        Seppuku.INSTANCE.getEventManager().dispatchEvent(event);

        return event.isCanceled();
    }

}
