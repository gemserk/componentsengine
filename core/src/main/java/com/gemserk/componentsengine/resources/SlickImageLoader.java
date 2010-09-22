package com.gemserk.componentsengine.resources;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.BufferedImageUtil;

public class SlickImageLoader implements ImageLoader<Image> {

	public Image load(String imagePath) {
		try {
			return new Image(imagePath);
		} catch (SlickException e) {
			throw new RuntimeException("failed to load image", e);
		}
	}

	public Image load(String imagePath, String alphaMaskPath) {
		try {
			BufferedImage sourceImage = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(imagePath));
			BufferedImage alphaMask = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(alphaMaskPath));
			String resourceName = MessageFormat.format("{0}-{1}", imagePath, alphaMask);
			return new Image(BufferedImageUtil.getTexture(resourceName, applyAlphaMask(sourceImage, alphaMask)));
		} catch (IOException e) {
			throw new RuntimeException("failed to load image mapping", e);
		}
	}
	
	// TODO: extract this logic to be used in all loaders

	ColorModel glAlphaColorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB), new int[] { 8, 8, 8, 8 }, true, false, ComponentColorModel.TRANSLUCENT, DataBuffer.TYPE_BYTE);

	@SuppressWarnings("unchecked")
	public BufferedImage applyAlphaMask(BufferedImage image, BufferedImage alphaMask) {
		WritableRaster raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE, image.getWidth(), image.getHeight(), 4, null);
		BufferedImage newImage = new BufferedImage(glAlphaColorModel, raster, false, new Hashtable());
		mergeImageWithAlphaMask(newImage, image, alphaMask);
		return newImage;
	}

	/**
	 * Merge in targetImage the sourceImageA with sourceImageB
	 * 
	 * @param targetImage
	 * @param sourceImage
	 * @param mask
	 */
	public void mergeImageWithAlphaMask(BufferedImage targetImage, BufferedImage sourceImage, BufferedImage mask) {
		final int width = targetImage.getWidth();

		int[] targetImageData = new int[width];
		int[] maskData = new int[width];

		for (int y = 0; y < sourceImage.getHeight(); y++) {
			// fetch a line of data from each image
			sourceImage.getRGB(0, y, width, 1, targetImageData, 0, 1);
			mask.getRGB(0, y, width, 1, maskData, 0, 1);
			// apply the mask
			for (int x = 0; x < width; x++) {
				int colorPixelValue = targetImageData[x] & 0x00FFFFFF; // mask away any alpha present
				int maskPixelValue = (maskData[x] & 0x00FF0000) << 8; // shift red into alpha bits
				colorPixelValue |= maskPixelValue;
				targetImageData[x] = colorPixelValue;
			}
			// replace the data
			targetImage.setRGB(0, y, width, 1, targetImageData, 0, 1);
		}
	}
}