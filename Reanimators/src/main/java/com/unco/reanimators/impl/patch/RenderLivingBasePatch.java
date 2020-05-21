package me.rigamortis.seppuku.impl.patch;

import me.rigamortis.seppuku.Seppuku;
import me.rigamortis.seppuku.api.event.render.EventRenderName;
import me.rigamortis.seppuku.api.patch.ClassPatch;
import me.rigamortis.seppuku.api.patch.MethodPatch;
import me.rigamortis.seppuku.impl.management.PatchManager;
import net.minecraft.entity.EntityLivingBase;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import static org.objectweb.asm.Opcodes.*;

/**
 * Author Seth
 * 4/9/2019 @ 10:52 AM.
 */
public final class RenderLivingBasePatch extends ClassPatch {

    public RenderLivingBasePatch() {
        super("net.minecraft.client.renderer.entity.RenderLivingBase", "caa");
    }

    /**
     * This is where minecraft renders name plates
     * @param methodNode
     * @param env
     */
    @MethodPatch(
            mcpName = "renderName",
            notchName = "a",
            mcpDesc = "(Lnet/minecraft/entity/EntityLivingBase;DDD)V",
            notchDesc = "(Lvp;DDD)V")
    public void renderName(MethodNode methodNode, PatchManager.Environment env) {
        //create a list of instructions and add the needed instructions to call our hook function
        final InsnList insnList = new InsnList();
        //add ALOAD to pass the entity into our hook function
        insnList.add(new VarInsnNode(ALOAD, 1));
        //call our hook function
        insnList.add(new MethodInsnNode(INVOKESTATIC, Type.getInternalName(this.getClass()), "renderNameHook", env == PatchManager.Environment.IDE ? "(Lnet/minecraft/entity/EntityLivingBase;)Z" : "(Lvp;)Z", false));
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
     * This is our renderName hook
     * Used to disable rendering minecrafts default
     * name tags on certain entities
     * @param entity
     * @return
     */
    public static boolean renderNameHook(EntityLivingBase entity) {
        //dispatch our event and pass the entity in
        final EventRenderName event = new EventRenderName(entity);
        Seppuku.INSTANCE.getEventManager().dispatchEvent(event);

        return event.isCanceled();
    }

//    /**
//     * This is where minecraft renders living entity models
//     * @param methodNode
//     * @param env
//     */
//    @MethodPatch(
//            mcpName = "doRender",
//            notchName = "a",
//            mcpDesc = "(Lnet/minecraft/entity/EntityLivingBase;DDDFF)V",
//            notchDesc = "(Lvp;DDDFF)V")
//    public void doRender(MethodNode methodNode, PatchManager.Environment env) {
//        //create a list of instructions and add the needed instructions to call our hook function
//        final InsnList preInsn = new InsnList();
//        //add ALOAD to pass the entity into our hook function
//        preInsn.add(new VarInsnNode(ALOAD, 1));
//        //EventStageable.EventStage.PRE
//        preInsn.add(new FieldInsnNode(GETSTATIC, "me/rigamortis/seppuku/api/event/EventStageable$EventStage", "PRE", "Lme/rigamortis/seppuku/api/event/EventStageable$EventStage;"));
//        //call our hook function
//        preInsn.add(new MethodInsnNode(INVOKESTATIC, Type.getInternalName(this.getClass()), "doRenderHook", env == PatchManager.Environment.IDE ? "(Lnet/minecraft/entity/EntityLivingBase;Lme/rigamortis/seppuku/api/event/EventStageable$EventStage;)Z" : "(Lvp;Lme/rigamortis/seppuku/api/event/EventStageable$EventStage;)Z", false));
//        //add a label to jump to
//        final LabelNode jmp = new LabelNode();
//        //add if equals and pass the label
//        preInsn.add(new JumpInsnNode(IFEQ, jmp));
//        //add return so the rest of the function doesn't get called
//        preInsn.add(new InsnNode(RETURN));
//        //add our label
//        preInsn.add(jmp);
//        //insert the list of instructions at the top of the function
//        methodNode.instructions.insert(preInsn);
//
//        //create a list of instructions and add the needed instructions to call our hook function
//        final InsnList postInsn = new InsnList();
//        //add ALOAD to pass the entity into our hook function
//        postInsn.add(new VarInsnNode(ALOAD, 1));
//        //EventStageable.EventStage.POST
//        postInsn.add(new FieldInsnNode(GETSTATIC, "me/rigamortis/seppuku/api/event/EventStageable$EventStage", "POST", "Lme/rigamortis/seppuku/api/event/EventStageable$EventStage;"));
//        //call our hook function
//        preInsn.add(new MethodInsnNode(INVOKESTATIC, Type.getInternalName(this.getClass()), "doRenderHook", env == PatchManager.Environment.IDE ? "(Lnet/minecraft/entity/EntityLivingBase;Lme/rigamortis/seppuku/api/event/EventStageable$EventStage;)Z" : "(Lvp;Lme/rigamortis/seppuku/api/event/EventStageable$EventStage;)Z", false));
//        //insert the list of instructions at the bottom of the function
//        methodNode.instructions.insertBefore(ASMUtil.bottom(methodNode), postInsn);
//    }
//
//    /**
//     * Our doRender hook used to cancel rendering specific living
//     * entities or to modify the way they render
//     * @param entity
//     * @param stage
//     * @return
//     */
//    public static boolean doRenderHook(EntityLivingBase entity, EventStageable.EventStage stage) {
//        final EventRenderLivingEntity event = new EventRenderLivingEntity(stage, entity);
//        Seppuku.INSTANCE.getEventManager().dispatchEvent(event);
//
//        return event.isCanceled();
//    }

}
