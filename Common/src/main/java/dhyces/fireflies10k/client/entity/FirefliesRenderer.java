package dhyces.fireflies10k.client.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import dhyces.fireflies10k.Fireflies;
import dhyces.fireflies10k.entity.FirefliesEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import org.jetbrains.annotations.NotNull;

public class FirefliesRenderer extends EntityRenderer<FirefliesEntity> {

    public static final ResourceLocation FIREFLIES_TEXTURE = new ResourceLocation(Fireflies.MODID, "textures/entity/fireflies.png");

    protected FirefliesRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(@NotNull FirefliesEntity entity, float entityYaw, float partialTick, @NotNull PoseStack poseStack,
                       @NotNull MultiBufferSource bufferSource, int packedLight) {
        var buffer = bufferSource.getBuffer(RenderType.entityTranslucent(getTextureLocation(entity)));
        var clientPlayer = Minecraft.getInstance().player;
        for (FirefliesEntity.Firefly firefly : entity.fireflies) {
            var pos = firefly.getPosition();
            poseStack.pushPose();
            poseStack.translate(pos.x, pos.y, pos.z);
            buffer.vertex(0, 0, 0).color(FastColor.ARGB32.color(255, 255, 255, 255)).uv(0,0).overlayCoords(LivingEntityRenderer.getOverlayCoords(entity, 0)).uv2(packedLight).normal(0,0,0).endVertex();
            buffer.vertex(0, 1, 0).color(FastColor.ARGB32.color(255, 255, 255, 255)).uv(0,0.0625F).overlayCoords(LivingEntityRenderer.getOverlayCoords(entity, 0)).uv2(packedLight).normal(0,0,0).endVertex();
            buffer.vertex(1, 1, 0).color(FastColor.ARGB32.color(255, 255, 255, 255)).uv(0.125F,0.0625F).overlayCoords(LivingEntityRenderer.getOverlayCoords(entity, 0)).uv2(packedLight).normal(0,0,0).endVertex();
            buffer.vertex(1, 0, 0).color(FastColor.ARGB32.color(255, 255, 255, 255)).uv(0.125F,0).overlayCoords(LivingEntityRenderer.getOverlayCoords(entity, 0)).uv2(packedLight).normal(0,0,0).endVertex();
        }
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull FirefliesEntity firefliesEntity) {
        return FIREFLIES_TEXTURE;
    }
}
