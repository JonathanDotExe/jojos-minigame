package at.jojokobi.minigamesplugin.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Area {

	private Location pos;
	private double width;
	private double height;
	private double length;
	
	public Area(Location pos, double width, double height, double length) {
		super();
		this.pos = pos;
		this.width = width;
		this.height = height;
		this.length = length;
	}
	
	public List<Player> getPlayersInArea () {
		List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
		for (Iterator<Player> iterator = players.iterator(); iterator.hasNext();) {
			Player player = iterator.next();
			if (player.getWorld() != pos.getWorld() || player.getLocation().getX() < pos.getX() ||  player.getLocation().getX() > pos.getX() + width || player.getLocation().getY() < pos.getY() || player.getLocation().getY() > pos.getY() + height || player.getLocation().getZ() < pos.getZ() || player.getLocation().getZ() > pos.getZ() + length) {
				iterator.remove();
			}
		}
		return players;
	}

	public Location getPos() {
		return pos;
	}

	public void setPos(Location pos) {
		this.pos = pos;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

}
