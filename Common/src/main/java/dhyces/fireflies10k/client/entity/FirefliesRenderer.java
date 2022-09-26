package dhyces.fireflies10k.client.entity;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import dhyces.fireflies10k.CommonFireflies;
import dhyces.fireflies10k.entity.FirefliesEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class FirefliesRenderer extends EntityRenderer<FirefliesEntity> {

    public static final ResourceLocation FIREFLIES_TEXTURE = new ResourceLocation(CommonFireflies.MODID, "textures/entity/fireflies.png");
    private static final float FIREFLY_WIDTH = 6F;
    private static final float FIREFLY_HEIGHT = 6F;
    private static final float TEXTURE_WIDTH = 16F;
    private static final float TEXTURE_HEIGHT = 48F;

    public FirefliesRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(@NotNull FirefliesEntity entity, float entityYaw, float partialTick, @NotNull PoseStack poseStack,
                       @NotNull MultiBufferSource bufferSource, int packedLight) {
        var buffer = bufferSource.getBuffer(RenderType.itemEntityTranslucentCull(getTextureLocation(entity)));
        for (FirefliesEntity.Firefly firefly : entity.fireflies) {
            var pos = firefly.getOldPosition().lerp(firefly.getPosition(), partialTick);
            poseStack.pushPose();
            poseStack.translate(pos.x, pos.y, pos.z);
            var last = poseStack.last();
            var look = new Vector3f(0, 1, 0);
            int textureState = Math.abs(7 - firefly.getBlinkTime());
            int light = packedLight;
            if (textureState != 7) {
                light = LightTexture.FULL_BRIGHT;
            }
            renderFace(entity, buffer, last, textureState, light, look);
            poseStack.popPose();
        }
        if (Minecraft.getInstance().getEntityRenderDispatcher().shouldRenderHitBoxes()) {
            debug(entity, poseStack, partialTick);
        }
    }

    private void renderFace(@NotNull FirefliesEntity entity, VertexConsumer buffer, PoseStack.Pose last, int textureState, int packedLight, Vector3f normal) {
        addVertex(entity, buffer, last, -0.1875F, 0.1875F, 0.375F, (textureState * FIREFLY_HEIGHT) / TEXTURE_HEIGHT, packedLight, normal);
        addVertex(entity, buffer, last, 0.1875F, 0.1875F, 0F, (textureState * FIREFLY_HEIGHT) / TEXTURE_HEIGHT, packedLight, normal);
        addVertex(entity, buffer, last, 0.1875F, -0.1875F,0F, ((textureState+1) * FIREFLY_HEIGHT) / TEXTURE_HEIGHT, packedLight, normal);
        addVertex(entity, buffer, last, -0.1875F, -0.1875F, 0.375F, ((textureState+1) * FIREFLY_HEIGHT) / TEXTURE_HEIGHT, packedLight, normal);
    }

    private void addVertex(@NotNull FirefliesEntity entity, VertexConsumer buffer, PoseStack.Pose last, float x, float y, float u, float v, int packedLight, Vector3f normal) {
        var vec = new Vector3f(x, y, 0F);
        var camera = Minecraft.getInstance().gameRenderer.getMainCamera();
        vec.transform(camera.rotation());
        buffer.vertex(last.pose(), vec.x(), vec.y(), vec.z()).color(0xFFFFFFFF).uv(u, v).overlayCoords(LivingEntityRenderer.getOverlayCoords(entity, 0)).uv2(packedLight).normal(last.normal(), normal.x(), normal.y(), normal.z()).endVertex();
    }

    private void debug(@NotNull FirefliesEntity entity, @NotNull PoseStack poseStack, float partialTick) {
        RenderSystem.setShader(GameRenderer::getRendertypeLinesShader);
        RenderSystem.lineWidth(3);
        var buffer = Tesselator.getInstance().getBuilder();
        buffer.begin(VertexFormat.Mode.DEBUG_LINES, DefaultVertexFormat.POSITION_COLOR);
        for (FirefliesEntity.Firefly firefly : entity.fireflies) {
            var pos = firefly.getOldPosition().lerp(firefly.getPosition(), partialTick);
            var worldPos = firefly.getPosition().add(entity.getX(), entity.getY(), entity.getZ());
            var entityEyePos = new Vec3(entity.getX(), entity.getEyeY(), entity.getZ());
            var toOrigin = worldPos.vectorTo(entityEyePos);
            var vel = firefly.getVelocity().normalize();

            var other = toOrigin;
            poseStack.pushPose();
            poseStack.translate(pos.x, pos.y, pos.z);
            var last = poseStack.last();
            buffer.vertex(last.pose(), 0, 0, 0).color(0.0F, 0.0F, 1.0F, 1.0F).endVertex();
            buffer.vertex(last.pose(), (float)vel.x, (float)vel.y, (float)vel.z).color(0.0F, 0.0F, 1.0F, 1.0F).endVertex();

            buffer.vertex(last.pose(), 0, 0, 0).color(0.0F, 1.0F, 1.0F, 1.0F).endVertex();
            buffer.vertex(last.pose(), (float)other.x, (float)other.y, (float)other.z).color(0.0F, 1.0F, 1.0F, 1.0F).endVertex();
            poseStack.popPose();
        }
        buffer.end();
        BufferUploader.end(buffer);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull FirefliesEntity firefliesEntity) {
        return FIREFLIES_TEXTURE;
    }
}
