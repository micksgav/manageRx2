package swingHelper;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class AppIcon implements Icon {
	
	private final ImageIcon originalIcon;
	
	public AppIcon(String path) {
		this.originalIcon = new ImageIcon(path);
		
	}
	
	public AppIcon(ImageIcon icon) {
		this.originalIcon = icon;
		
	}
	
	public AppIcon setScale(double scaleFactor) {
		Image scaledImage = this.originalIcon.getImage().getScaledInstance((int)(this.originalIcon.getIconWidth()*scaleFactor), (int) (this.originalIcon.getIconHeight()*scaleFactor), Image.SCALE_SMOOTH);
        return new AppIcon(new ImageIcon(scaledImage));
	}
	
	public AppIcon setSize(int width, int height) {
        Image scaledImage = this.originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new AppIcon(new ImageIcon(scaledImage));
	}
	
	public void paintIcon(Component c, Graphics g, int x, int y) {
		this.originalIcon.paintIcon(c, g, x, y);
	}

	public int getIconWidth() {
		return this.originalIcon.getIconWidth();
	}

	public int getIconHeight() {
		return this.originalIcon.getIconHeight();
	}
	
}