package com.unco.reanimators.impl.module.render;

import com.unco.reanimators.api.event.render.EventRender3D;
import com.unco.reanimators.api.module.Module;
import com.unco.reanimators.api.util.MathUtil;
import com.unco.reanimators.api.util.RenderUtil;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import club.minnced.impl.annotated.handler.annotation.Listener;

/**
 * Author Seth
 * 8/14/2019 @ 1:13 AM.
 */
public final class BlockHighlightModule extends Module {

    public BlockHighlightModule() {
        super("BlockHighlight", new String[] {"BHighlight", "BlockHigh"}, "Highlights the block at your crosshair", "NONE", -1, ModuleType.RENDER);
    }

    @Listener
    public void render3D(EventRender3D event) {
        final Minecraft mc = Minecraft.getMinecraft();
        final RayTraceResult ray = mc.objectMouseOver;
        if(ray.typeOfHit == RayTraceResult.Type.BLOCK) {

            final BlockPos blockpos = ray.getBlockPos();
            final IBlockState iblockstate = mc.world.getBlockState(blockpos);

            if (iblockstate.getMaterial() != Material.AIR && mc.world.getWorldBorder().contains(blockpos)) {
                final Vec3d interp = MathUtil.interpolateEntity(mc.player, mc.getRenderPartialTicks());
                RenderUtil.drawBoundingBox(iblockstate.getSelectedBoundingBox(mc.world, blockpos).grow(0.0020000000949949026D).offset(-interp.x, -interp.y, -interp.z), 1.5f, 0xFF9900EE);
            }
        }
    }

}
