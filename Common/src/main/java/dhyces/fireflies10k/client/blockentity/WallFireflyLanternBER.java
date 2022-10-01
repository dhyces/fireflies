package dhyces.fireflies10k.client.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import dhyces.fireflies10k.blockentity.WallFireflyLanternBlockEntity;
import dhyces.fireflies10k.client.entity.FirefliesRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import org.jetbrains.annotations.NotNull;

public class WallFireflyLanternBER implements BlockEntityRenderer<WallFireflyLanternBlockEntity> {

    private static final float FIREFLY_WIDTH = 6F;
    private static final float FIREFLY_HEIGHT = 6F;
    private static final float TEXTURE_WIDTH = 16F;
    private static final float TEXTURE_HEIGHT = 48F;

    public WallFireflyLanternBER(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(@NotNull WallFireflyLanternBlockEntity wallFireflyLanternBlockEntity, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int combinedLight, int combinedOverlay) {
        var buffer = multiBufferSource.getBuffer(RenderType.entityTranslucentCull(FirefliesRenderer.FIREFLIES_TEXTURE));
        poseStack.pushPose();
        poseStack.translate(0.5, 0.1, 0.5);
        renderFace(buffer, poseStack.last(), 0, combinedLight, new Vector3f(0, 1, 0));
        poseStack.popPose();
    }

    private void renderFace(VertexConsumer buffer, PoseStack.Pose last, int textureState, int packedLight, Vector3f normal) {
        addVertex(buffer, last, -0.1875F, 0.1875F, 0.375F, (textureState * FIREFLY_HEIGHT) / TEXTURE_HEIGHT, packedLight, normal);
        addVertex(buffer, last, 0.1875F, 0.1875F, 0F, (textureState * FIREFLY_HEIGHT) / TEXTURE_HEIGHT, packedLight, normal);
        addVertex(buffer, last, 0.1875F, -0.1875F,0F, ((textureState+1) * FIREFLY_HEIGHT) / TEXTURE_HEIGHT, packedLight, normal);
        addVertex(buffer, last, -0.1875F, -0.1875F, 0.375F, ((textureState+1) * FIREFLY_HEIGHT) / TEXTURE_HEIGHT, packedLight, normal);
    }

    private void addVertex(VertexConsumer buffer, PoseStack.Pose last, float x, float y, float u, float v, int packedLight, Vector3f normal) {
        var vec = new Vector3f(x, y, 0F);
        var camera = Minecraft.getInstance().gameRenderer.getMainCamera();
        vec.transform(camera.rotation());
        buffer.vertex(last.pose(), vec.x(), vec.y(), vec.z()).color(0xFFFFFFFF).uv(u, v).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(last.normal(), normal.x(), normal.y(), normal.z()).endVertex();
    }
}
