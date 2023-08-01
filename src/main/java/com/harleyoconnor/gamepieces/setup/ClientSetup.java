package com.harleyoconnor.gamepieces.setup;

import com.harleyoconnor.gamepieces.GamePieces;
import com.harleyoconnor.gamepieces.block.CheckersPiece;
import com.harleyoconnor.gamepieces.block.ChessPiece;
import com.harleyoconnor.gamepieces.block.PieceColor;
import com.harleyoconnor.gamepieces.render.PieceModelManager;
import com.harleyoconnor.gamepieces.render.PieceTileEntityRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = GamePieces.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {

    private static final PieceModelManager<ChessPiece> CHESS_MODEL_MANAGER = new PieceModelManager<>("chess", ChessPiece.Type.values(), PieceColor.values());
    private static final PieceModelManager<CheckersPiece> CHECKERS_MODEL_MANAGER = new PieceModelManager<>("checkers", CheckersPiece.Type.values(), PieceColor.values());

    @SubscribeEvent
    public static void setup(FMLClientSetupEvent event) {
        registerRenderLayers();
        registerTileEntityRenderers();
    }

    private static void registerRenderLayers() {
        RenderTypeLookup.setRenderLayer(Registry.CHESS_BLOCK.get(), RenderType.cutout());
    }

    private static void registerTileEntityRenderers() {
        ClientRegistry.bindTileEntityRenderer(Registry.CHESS_TILE_ENTITY.get(), tileEntityRenderer -> new PieceTileEntityRenderer<>(
                tileEntityRenderer, CHESS_MODEL_MANAGER
        ));
        ClientRegistry.bindTileEntityRenderer(Registry.CHECKERS_TILE_ENTITY.get(), tileEntityRenderer -> new PieceTileEntityRenderer<>(
                tileEntityRenderer, CHECKERS_MODEL_MANAGER
        ));
    }

    @SubscribeEvent
    public static void onModelRegistry(ModelRegistryEvent event) {
        CHESS_MODEL_MANAGER.addModels();
        CHECKERS_MODEL_MANAGER.addModels();
    }

    @SubscribeEvent
    public static void onModelBake(ModelBakeEvent event) {
        CHESS_MODEL_MANAGER.cacheModels();
        CHECKERS_MODEL_MANAGER.cacheModels();
    }

}
