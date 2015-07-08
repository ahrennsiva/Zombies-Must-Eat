import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.image.WritableRaster;
import java.awt.Color;

/**
 * Write a description of class FastImage here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class FastImage
{
    private WritableRaster raster;
    private byte[][] imageRed;
    private byte[][] imageGreen;
    private byte[][] imageBlue;
    private int[] color = {0,0,0,255};
    private int[] black = {0,0,0,255};
    private int[] tempColor = {0,0,0,255};
    private int width,height;

    public FastImage(GreenfootImage image)
    {
        raster = image.getAwtImage().getRaster();
        imageRed   = new byte[image.getWidth()][image.getHeight()];
        imageGreen = new byte[image.getWidth()][image.getHeight()];
        imageBlue  = new byte[image.getWidth()][image.getHeight()];
        for(int x=0;x<image.getWidth();x++){
            for(int y=0;y<image.getHeight();y++){
                imageRed[x][y]   = (byte)(image.getColorAt(x,y).getRed()-128);
                imageGreen[x][y] = (byte)(image.getColorAt(x,y).getGreen()-128);
                imageBlue[x][y]  = (byte)(image.getColorAt(x,y).getBlue()-128);
            }
        }
        height = image.getHeight();
        width = image.getWidth();
    }

    public void setColor(int[] c){
        color[0] = c[0];
        color[1] = c[1];
        color[2] = c[2];
    }

    public void setColor(Color c){
        color[0] = c.getRed();
        color[1] = c.getGreen();
        color[2] = c.getBlue();
    }

    public void setRed(int red){
        color[0] = red;
    }

    public void setGreen(int green){
        color[1] = green;
    }

    public void setBlue(int blue){
        color[2] = blue;
    }

    public void setPixel(int x, int y){
        if(x<0||x>=width  || y<0||y>=height)
            return;
        raster.setPixel(x,y,color);
        imageRed[x][y]   = (byte)(color[0]-128);
        imageGreen[x][y] = (byte)(color[1]-128);
        imageBlue[x][y]  = (byte)(color[2]-128);
    }

    public void setPixel(int x, int y, int[] c){
        if(x<0||x>=width  || y<0||y>=height)
            return;
        raster.setPixel(x,y,c);
        imageRed[x][y]   = (byte)(c[0]-128);
        imageGreen[x][y] = (byte)(c[1]-128);
        imageBlue[x][y]  = (byte)(c[2]-128);
    }

    public void setPixel(int x, int y, Color c){
        if(x<0||x>=width  || y<0||y>=height)
            return;
        tempColor[0] = c.getRed();
        tempColor[1] = c.getGreen();
        tempColor[2] = c.getBlue();
        raster.setPixel(x,y,tempColor);
        imageRed[x][y]   = (byte)(c.getRed()-128);
        imageGreen[x][y] = (byte)(c.getGreen()-128);
        imageBlue[x][y]  = (byte)(c.getBlue()-128);
    }

    public int[] getColorAt(int x,int y){
        if(x<0||x>=width  || y<0||y>=height)
            return black;
        tempColor[0] = imageRed[x][y]+128;
        tempColor[1] = imageGreen[x][y]+128;
        tempColor[2] = imageBlue[x][y]+128;
        return tempColor;
    }

    public int getRedAt(int x, int y){
        if(x<0||x>=width  || y<0||y>=height)
            return 0;
        return imageRed[x][y]+128;
    }

    public int getGreenAt(int x, int y){
        if(x<0||x>=width  || y<0||y>=height)
            return 0;
        return imageGreen[x][y]+128;
    }

    public int getBlueAt(int x, int y){
        if(x<0||x>=width  || y<0||y>=height)
            return 0;
        return imageBlue[x][y]+128;
    }
}
