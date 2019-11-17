package at.jojokobi.minigamesplugin.maps;

import java.util.concurrent.CompletableFuture;

import at.jojokobi.minigamesplugin.util.Area;

public interface MapGenerator {
	
	public CompletableFuture<Void> generate (Area area);

}
