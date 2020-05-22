package com.unco.reanimators.impl.patch;

import com.unco.reanimators.Seppuku;
import com.unco.reanimators.api.event.world.EventSetOpaqueCube;
import com.unco.reanimators.api.patch.ClassPatch;
import com.unco.reanimators.api.patch.MethodPatch;
import com.unco.reanimators.impl.management.PatchManager;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import static org.objectweb.asm.Opcodes.*;

/**
 * Author Seth
 * 4/7/2019 @ 5:02 PM.
 */
public final class VisGraphPatch extends ClassPatch {

    public VisGraphPatch() {
        super("net.minecraft.client.renderer.chunk.VisGraph", "bxu");
    }

    /**
     * This is where minecraft handles culling
     * @param methodNode
     * @param env
     */
    @MethodPatch(
            mcpName = "setOpaqueCube",
            notchName = "a",
            mcpDesc = "(Lnet/minecraft/util/math/BlockPos;)V",
            notchDesc = "(Let;)V")
    public void setOpaqueCube(MethodNode methodNode, PatchManager.Environment env) {
        //create a list of instructions and add the needed instructions to call our hook function
        final InsnList insnList = new InsnList();
        //call our hook function
        insnList.add(new MethodInsnNode(INVOKESTATIC, Type.getInternalName(this.getClass()), "setOpaqueCubeHook", "()Z", false));
        //add a label to jump to
        final LabelNode jmp = new LabelNode();
        //add if equals and pass the label
        insnList.add(new JumpInsnNode(IFEQ, jmp));
        //add return so the rest of the function doesn't get called
        insnList.add(new InsnNode(RETURN));
        //add our label
        insnList.add(jmp);
        //insert the list of instructions at the top of the function
        methodNode.instructions.insert(insnList);
    }

    /**
     * Our setOpaqueCube hook
     * Cancel to prevent culling(this may cost a few frames)
     * @return
     */
    public static boolean setOpaqueCubeHook() {
        final EventSetOpaqueCube event = new EventSetOpaqueCube();
        Seppuku.INSTANCE.getEventManager().dispatchEvent(event);

        return event.isCanceled();
    }

}
