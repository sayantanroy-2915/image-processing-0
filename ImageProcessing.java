import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import java.util.Scanner;

class ImageProcessing
{
	static int image[][][];
	static int width,height;
	static Scanner scint;
	static Scanner scstr;

	static boolean readImage()
	{
		try
		{
			System.out.print("Enter input image file name with extension: ");
			String fn = scstr.nextLine();
			BufferedImage im = ImageIO.read(new File(fn));
			width = im.getWidth();
			height = im.getHeight();
			int p=0;
			image = new int[height][width][4];
			for(int i=0;i<height;i++)
				for(int j=0;j<width;j++)
				{
					p = im.getRGB(j,i);
					image[i][j][0] = (p>>16) & 255;		// red
					image[i][j][1] = (p>>8) & 255;		// green
					image[i][j][2] = p & 255;			// blue
					image[i][j][3] = p & 0xffffff;		// all (loss of transparency)
				}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}

	static void negative()
	{
		try
		{
			int r,g,b,p;
			int pix[];
			BufferedImage im1 = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
			for(int i=0;i<height;i++)
			{
				for(int j=0;j<width;j++)
				{
					pix = image[i][j];
					r = 255-pix[0];
					g = 255-pix[1];
					b = 255-pix[2];
					p = (r<<16) | (g<<8) | b;
					im1.setRGB(j,i,p);
				}
			}
			createFile(im1);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

	static void brightness()
	{
		try
		{
			int r,g,b,p,m,f;
			int pix[];
			System.out.print("\t1. Decrease brightness\n\t2. Increase brightness\nChoose (1/2): ");
			f = scint.nextInt();
			System.out.print("Enter intensity (>0): ");
			m = scint.nextInt();
			BufferedImage im1 = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
			if (f==1)
				for(int i=0;i<height;i++)
					for(int j=0;j<width;j++)
					{
						pix = image[i][j];
						r = pix[0]-m*10;
						r = r<0?0:r;
						g = pix[1]-m*10;
						g = g<0?0:g;
						b = pix[2]-m*10;
						b = b<0?0:b;
						p = (r<<16) | (g<<8) | b;
						im1.setRGB(j,i,p);
					}
			else if(f==2)
				for(int i=0;i<height;i++)
					for(int j=0;j<width;j++)
					{
						pix = image[i][j];
						r = pix[0]+m*10;
						r = r>255?255:r;
						g = pix[1]+m*10;
						g = g>255?255:g;
						b = pix[2]+m*10;
						b = b>255?255:b;
						p = (r<<16) | (g<<8) | b;
						im1.setRGB(j,i,p);
					}
			else
			{
				System.out.println("Wrong input!");
				return;
			}
			createFile(im1);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

	static void filter()
	{
		try
		{
			int r,g,b,p,f;
			int pix[];
			BufferedImage im1 = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
			System.out.print("\t1. Red\t\t2. Green\n\t3. Blue\t\t4. Yellow\n\t5. Magenta\t6. Orange\n\t7. Cyan\nChoose filter (1-7): ");
			f = scint.nextInt();
			switch(f)
			{
				case 1:
					for(int i=0;i<height;i++)
					{
						for(int j=0;j<width;j++)
						{
							pix = image[i][j];
							r = pix[0];
							p = r<<16;
							im1.setRGB(j,i,p);
						}
					}
					break;
				case 2:
					for(int i=0;i<height;i++)
					{
						for(int j=0;j<width;j++)
						{
							pix = image[i][j];
							g = pix[1];
							p = (g<<8);
							im1.setRGB(j,i,p);
						}
					}
					break;
				case 3:
					for(int i=0;i<height;i++)
					{
						for(int j=0;j<width;j++)
						{
							pix = image[i][j];
							b = pix[2];
							p = b;
							im1.setRGB(j,i,p);
						}
					}
					break;
				case 4:
					for(int i=0;i<height;i++)
					{
						for(int j=0;j<width;j++)
						{
							pix = image[i][j];
							r = pix[0];
							g = pix[1];
							p = (r<<16) | (g<<8);
							im1.setRGB(j,i,p);
						}
					}
					break;
				case 5:
					for(int i=0;i<height;i++)
					{
						for(int j=0;j<width;j++)
						{
							pix = image[i][j];
							r = pix[0];
							b = pix[2];
							p = (r<<16) | b;
							im1.setRGB(j,i,p);
						}
					}
					break;
				case 6:
					for(int i=0;i<height;i++)
					{
						for(int j=0;j<width;j++)
						{
							pix = image[i][j];
							r = pix[0];
							g = pix[1]/2;
							p = (r<<16) | (g<<8);
							im1.setRGB(j,i,p);
						}
					}
					break;
				case 7:
					for(int i=0;i<height;i++)
					{
						for(int j=0;j<width;j++)
						{
							pix = image[i][j];
							g = pix[1];
							b = pix[2];
							p = (g<<8) | b;
							im1.setRGB(j,i,p);
						}
					}
					break;
				default: System.out.println("Wrong input");
					return;
			}
			createFile(im1);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

	static void mono()
	{
		try
		{
			int gr,p,f;
			int pix[];
			BufferedImage im1 = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
			System.out.print("\t1. Grayscale\n\t2. Antique\nChoose (1/2): ");
			f = scint.nextInt();
			if(f==1)
				for(int i=0;i<height;i++)
					for(int j=0;j<width;j++)
					{
						pix = image[i][j];
						gr = (pix[0] + pix[1] + pix[2])/3;
						p = (gr<<16) | (gr<<8) | gr;
						im1.setRGB(j,i,p);
					}
			else if(f==2)
				for(int i=0;i<height;i++)
					for(int j=0;j<width;j++)
					{
						pix = image[i][j];
						gr = (pix[0] + pix[1] + pix[2])/3;
						p = (gr<<16) | (gr<<8) | (gr>>1);
						im1.setRGB(j,i,p);
					}
			else
			{
				System.out.println("Wrong input!");
				return;
			}
			createFile(im1);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

	static void rotate()
	{
		try
		{
			int w=0,h=0,x1,y1,p,q=0,t=0;
			double th,c=0,s=0;
			int pix;
			System.out.print("Enter angle in degrees (0-90): ");
			th = scint.nextDouble();
			c = Math.cos(Math.toRadians(th));
			s = Math.sin(Math.toRadians(th));
			w = (int)Math.round(width*c+height*s);
			h = (int)Math.round(width*s+height*c);
			BufferedImage im1 = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
			for(int y=0;y<height;y++)
			{
				for(int x=0;x<width;x++)
				{
					pix = image[y][x][3];
					x1 = (int)Math.round(x*c+y*s);
					y1 = (int)Math.round(-x*s+y*c+width*s);
					if(x1>=0&&y1>=0&&x1<w&&y1<h)
						im1.setRGB(x1,y1,pix);
				}
			}
			createFile(im1);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

	static void crop()
	{
		try
		{
			int sx,sy,h,w,pix;
			System.out.print("Enter start co-ordinate (x y): ");
			sx = scint.nextInt();
			sy = scint.nextInt();
			System.out.print("Enter dimension (w h): ");
			w = scint.nextInt();
			h = scint.nextInt();
			BufferedImage im1 = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
			for(int i=0;i<h;i++)
			{
				for(int j=0;j<w;j++)
				{
					pix = image[sy+i][sx+j][3];
					im1.setRGB(j,i,pix);
				}
			}
			createFile(im1);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

	static void flip()
	{
		try
		{
			int pix,f;
			System.out.print("\t1. Horizontal\n\t2. Vertical\nChoose (1/2): ");
			f = scint.nextInt();
			BufferedImage im1 = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
			if(f==1)
				for(int i=0;i<height;i++)
					for(int j=0;j<width;j++)
					{
						pix = image[i][j][3];
						im1.setRGB(width-j-1,i,pix);
				}
			else if(f==1)
				for(int i=0;i<height;i++)
					for(int j=0;j<width;j++)
					{
						pix = image[i][j][3];
						im1.setRGB(j,height-i-1,pix);
					}
			else
			{
				System.out.println("Wrong input!");
				return;
			}
			createFile(im1);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

	static void blur()
	{
		try
		{
			int pixr[][] = new int[height][width],pixg[][] = new int[height][width],pixb[][] = new int[height][width],pixr1[][] = new int[height][width],pixg1[][] = new int[height][width],pixb1[][] = new int[height][width],c,p;
			for(int i=0;i<height;i++)
				for(int j=0;j<width;j++)
				{
					pixr1[i][j] = image[i][j][0];
					pixg1[i][j] = image[i][j][1];
					pixb1[i][j] = image[i][j][2];
				}
			System.out.print("Enter intensity (>0): ");
			c = scint.nextInt();
			BufferedImage im1 = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
			while(c>0)
			{
				for(int i=0;i<height;i++)
					for(int j=0;j<width;j++)
					{
						pixr[i][j] = pixr1[i][j];
						pixg[i][j] = pixg1[i][j];
						pixb[i][j] = pixb1[i][j];
					}
				pixr1[0][0] = (pixr[0][0]+pixr[0][1]+pixr[1][0]+pixr[1][1])/4;
				pixr1[0][width-1] = (pixr[0][width-1]+pixr[0][width-1]+pixr[1][width-1]+pixr[1][width-2])/4;
				pixr1[height-1][0] = (pixr[height-1][0]+pixr[height-1][1]+pixr[height-2][0]+pixr[height-2][1])/4;
				pixr1[height-1][width-1] = (pixr[height-1][width-1]+pixr[height-1][width-2]+pixr[height-2][width-1]+pixr[height-2][width-2])/4;
				pixg1[0][0] = (pixg[0][0]+pixg[0][1]+pixg[1][0]+pixg[1][1])/4;
				pixg1[0][width-1] = (pixg[0][width-1]+pixg[0][width-1]+pixg[1][width-1]+pixg[1][width-2])/4;
				pixg1[height-1][0] = (pixg[height-1][0]+pixg[height-1][1]+pixg[height-2][0]+pixg[height-2][1])/4;
				pixg1[height-1][width-1] = (pixg[height-1][width-1]+pixg[height-1][width-2]+pixg[height-2][width-1]+pixg[height-2][width-2])/4;
				pixb1[0][0] = (pixb[0][0]+pixb[0][1]+pixb[1][0]+pixb[1][1])/4;
				pixb1[0][width-1] = (pixb[0][width-1]+pixb[0][width-1]+pixb[1][width-1]+pixb[1][width-2])/4;
				pixb1[height-1][0] = (pixb[height-1][0]+pixb[height-1][1]+pixb[height-2][0]+pixb[height-2][1])/4;
				pixb1[height-1][width-1] = (pixb[height-1][width-1]+pixb[height-1][width-2]+pixb[height-2][width-1]+pixb[height-2][width-2])/4;
				for(int i=1;i<width-1;i++)
				{
					pixr1[0][i] = (pixr[0][i-1]+pixr[0][i]+pixr[0][i+1]+pixr[1][i-1]+pixr[1][i]+pixr[1][i+1])/6;
					pixr1[height-1][i] = (pixr[height-1][i-1]+pixr[height-1][i]+pixr[height-1][i+1]+pixr[height-2][i-1]+pixr[height-2][i]+pixr[height-2][i+1])/6;
					pixg1[0][i] = (pixg[0][i-1]+pixg[0][i]+pixg[0][i+1]+pixg[1][i-1]+pixg[1][i]+pixg[1][i+1])/6;
					pixg1[height-1][i] = (pixg[height-1][i-1]+pixg[height-1][i]+pixg[height-1][i+1]+pixg[height-2][i-1]+pixg[height-2][i]+pixg[height-2][i+1])/6;
					pixb1[0][i] = (pixb[0][i-1]+pixb[0][i]+pixb[0][i+1]+pixb[1][i-1]+pixb[1][i]+pixb[1][i+1])/6;
					pixb1[height-1][i] = (pixb[height-1][i-1]+pixb[height-1][i]+pixb[height-1][i+1]+pixb[height-2][i-1]+pixb[height-2][i]+pixb[height-2][i+1])/6;
				}
				for(int i=1;i<height-1;i++)
				{
					pixr1[i][0] = (pixr[i-1][0]+pixr[i][0]+pixr[i+1][0]+pixr[i-1][1]+pixr[i][1]+pixr[i+1][1])/6;
					pixr1[i][width-1] = (pixr[i-1][width-1]+pixr[i][width-1]+pixr[i+1][width-1]+pixr[i-1][width-2]+pixr[i][width-2]+pixr[i+1][width-2])/6;
					pixg1[i][0] = (pixg[i-1][0]+pixg[i][0]+pixg[i+1][0]+pixg[i-1][1]+pixg[i][1]+pixg[i+1][1])/6;
					pixg1[i][width-1] = (pixg[i-1][width-1]+pixg[i][width-1]+pixg[i+1][width-1]+pixg[i-1][width-2]+pixg[i][width-2]+pixg[i+1][width-2])/6;
					pixb1[i][0] = (pixb[i-1][0]+pixb[i][0]+pixb[i+1][0]+pixb[i-1][1]+pixb[i][1]+pixb[i+1][1])/6;
					pixb1[i][width-1] = (pixb[i-1][width-1]+pixb[i][width-1]+pixb[i+1][width-1]+pixb[i-1][width-2]+pixb[i][width-2]+pixb[i+1][width-2])/6;
				}
				for(int i=1;i<height-1;i++)
					for(int j=1;j<width-1;j++)
					{
						pixr1[i][j] = (pixr[i-1][j-1]+pixr[i-1][j]+pixr[i+1][j-1]+pixr[i][j-1]+pixr[i][j]+pixr[i][j+1]+pixr[i+1][j-1]+pixr[i+1][j]+pixr[i+1][j+1])/9;
						pixg1[i][j] = (pixg[i-1][j-1]+pixg[i-1][j]+pixg[i+1][j-1]+pixg[i][j-1]+pixg[i][j]+pixg[i][j+1]+pixg[i+1][j-1]+pixg[i+1][j]+pixg[i+1][j+1])/9;
						pixb1[i][j] = (pixb[i-1][j-1]+pixb[i-1][j]+pixb[i+1][j-1]+pixb[i][j-1]+pixb[i][j]+pixb[i][j+1]+pixb[i+1][j-1]+pixb[i+1][j]+pixb[i+1][j+1])/9;
					}
				c--;
			}
			for(int i=0;i<height;i++)
				for(int j=0;j<width;j++)
				{
					p = (pixr1[i][j]<<16) | (pixg1[i][j]<<8) | pixb1[i][j];
					im1.setRGB(j,i,p);
				}
			createFile(im1);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

	static void edge()
	{
		try
		{
			int pix[][] = new int[height][width],pixr[][] = new int[height][width],pixg[][] = new int[height][width],pixb[][] = new int[height][width],c;
			System.out.print("Enter sensitivity (>0): ");
			c = scint.nextInt();
			BufferedImage im1 = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		//	FileWriter temp = new FileWriter(new File("temp.txt"));
			pixr[0][0] = (image[0][0][0]+image[0][1][0]+image[1][0][0]+image[1][1][0])/4;
			pixr[0][width-1] = (image[0][width-1][0]+image[0][width-1][0]+image[1][width-1][0]+image[1][width-2][0])/4;
			pixr[height-1][0] = (image[height-1][0][0]+image[height-1][1][0]+image[height-2][0][0]+image[height-2][1][0])/4;
			pixr[height-1][width-1] = (image[height-1][width-1][0]+image[height-1][width-2][0]+image[height-2][width-1][0]+image[height-2][width-2][0])/4;
			pixg[0][0] = (image[0][0][1]+image[0][1][1]+image[1][0][1]+image[1][1][1])/4;
			pixg[0][width-1] = (image[0][width-1][1]+image[0][width-1][1]+image[1][width-1][1]+image[1][width-2][1])/4;
			pixg[height-1][0] = (image[height-1][0][1]+image[height-1][1][1]+image[height-2][0][1]+image[height-2][1][1])/4;
			pixg[height-1][width-1] = (image[height-1][width-1][1]+image[height-1][width-2][1]+image[height-2][width-1][1]+image[height-2][width-2][1])/4;
			pixb[0][0] = (image[0][0][2]+image[0][1][2]+image[1][0][2]+image[1][1][2])/4;
			pixb[0][width-1] = (image[0][width-1][2]+image[0][width-1][2]+image[1][width-1][2]+image[1][width-2][2])/4;
			pixb[height-1][0] = (image[height-1][0][2]+image[height-1][1][2]+image[height-2][0][2]+image[height-2][1][2])/4;
			pixb[height-1][width-1] = (image[height-1][width-1][2]+image[height-1][width-2][2]+image[height-2][width-1][2]+image[height-2][width-2][2])/4;
			for(int i=1;i<width-1;i++)
			{
				pixr[0][i] = (image[0][i-1][0]+image[0][i][0]+image[0][i+1][0]+image[1][i-1][0]+image[1][i][0]+image[1][i+1][0])/6;
				pixr[height-1][i] = (image[height-1][i-1][0]+image[height-1][i][0]+image[height-1][i+1][0]+image[height-2][i-1][0]+image[height-2][i][0]+image[height-2][i+1][0])/6;
				pixg[0][i] = (image[0][i-1][1]+image[0][i][1]+image[0][i+1][1]+image[1][i-1][1]+image[1][i][1]+image[1][i+1][1])/6;
				pixg[height-1][i] = (image[height-1][i-1][1]+image[height-1][i][1]+image[height-1][i+1][1]+image[height-2][i-1][1]+image[height-2][i][1]+image[height-2][i+1][1])/6;
				pixb[0][i] = (image[0][i-1][2]+image[0][i][2]+image[0][i+1][2]+image[1][i-1][2]+image[1][i][2]+image[1][i+1][2])/6;
				pixb[height-1][i] = (image[height-1][i-1][2]+image[height-1][i][2]+image[height-1][i+1][2]+image[height-2][i-1][2]+image[height-2][i][2]+image[height-2][i+1][2])/6;
			}
			for(int i=1;i<height-1;i++)
			{
				pixr[i][0] = (image[i-1][0][0]+image[i][0][0]+image[i+1][0][0]+image[i-1][1][0]+image[i][1][0]+image[i+1][1][0])/6;
				pixr[i][width-1] = (image[i-1][width-1][0]+image[i][width-1][0]+image[i+1][width-1][0]+image[i-1][width-2][0]+image[i][width-2][0]+image[i+1][width-2][0])/6;
				pixg[i][0] = (image[i-1][0][1]+image[i][0][1]+image[i+1][0][1]+image[i-1][1][1]+image[i][1][1]+image[i+1][1][1])/6;
				pixg[i][width-1] = (image[i-1][width-1][1]+image[i][width-1][1]+image[i+1][width-1][1]+image[i-1][width-2][1]+image[i][width-2][1]+image[i+1][width-2][1])/6;
				pixb[i][0] = (image[i-1][0][2]+image[i][0][2]+image[i+1][0][2]+image[i-1][1][2]+image[i][1][2]+image[i+1][1][2])/6;
				pixb[i][width-1] = (image[i-1][width-1][2]+image[i][width-1][2]+image[i+1][width-1][2]+image[i-1][width-2][2]+image[i][width-2][2]+image[i+1][width-2][2])/6;
			}
			for(int i=1;i<height-1;i++)
				for(int j=1;j<width-1;j++)
				{
					pixr[i][j] = (image[i-1][j-1][0]+image[i-1][j][0]+image[i+1][j-1][0]+image[i][j-1][0]+image[i][j][0]+image[i][j+1][0]+image[i+1][j-1][0]+image[i+1][j][0]+image[i+1][j+1][0])/9;
					pixg[i][j] = (image[i-1][j-1][1]+image[i-1][j][1]+image[i+1][j-1][1]+image[i][j-1][1]+image[i][j][1]+image[i][j+1][1]+image[i+1][j-1][1]+image[i+1][j][1]+image[i+1][j+1][1])/9;
					pixb[i][j] = (image[i-1][j-1][2]+image[i-1][j][2]+image[i+1][j-1][2]+image[i][j-1][2]+image[i][j][2]+image[i][j+1][2]+image[i+1][j-1][2]+image[i+1][j][2]+image[i+1][j+1][2])/9;
				}
			for(int i=0;i<height;i++)
				for(int j=0;j<width;j++)
				{
					pixr[i][j] -= image[i][j][0];
					pixr[i][j] = pixr[i][j]<0?-pixr[i][j]:pixr[i][j];
					pixg[i][j] -= image[i][j][1];
					pixg[i][j] = pixg[i][j]<0?-pixg[i][j]:pixg[i][j];
					pixb[i][j] -= image[i][j][2];
					pixb[i][j] = pixb[i][j]<0?-pixb[i][j]:pixb[i][j];
				}
			for(int i=0;i<height;i++)
			{
				for(int j=0;j<width;j++)
				{
					if(pixr[i][j]>c||pixg[i][j]>c||pixb[i][j]>c)
						im1.setRGB(j,i,0);
					else
						im1.setRGB(j,i,0xffffff);
				//	temp.write(pix[i][j]+" ");
				}
			//	temp.write("\n");
			//	temp.flush();
			}
		//	temp.close();
			createFile(im1);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

	static void createFile(BufferedImage im)
	{
		try
		{
			File dir = new File("Output Files");
			if(!dir.exists())
				dir.mkdir();
			System.out.print("Enter output image file name with extension: ");
			String fn = "Output Files/"+scstr.nextLine();
			if(fn.endsWith(".jpg")||fn.endsWith(".jpeg"))
				ImageIO.write(im,"jpg",new File(fn));
			else if(fn.endsWith(".png"))
				ImageIO.write(im,"png",new File(fn));
			else if(fn.endsWith(".bmp"))
				ImageIO.write(im,"bmp",new File(fn));
			else if(fn.endsWith(".gif"))
				ImageIO.write(im,"gif",new File(fn));
			else
				System.out.println("Invalid codec...");
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

	public static void main(String arg[])
	{
		scint = new Scanner(System.in);
		scstr = new Scanner(System.in);
		int c1,c2;
		while(true)
		{
			System.out.print("\t1. Choose Image\n\t0. Quit\nEnter your choice: ");
			c1 = scint.nextInt();
			switch(c1)
			{
				case 1: 
					if(readImage())
					{
						System.out.print("\t1. Negative\t2. Change Brightness\n\t3. Filter\t4. Monochromatic\n\t5. Flip\t\t6. Rotate\n\t7. Crop\t\t8. Blur\n\t9. Sketch\nEnter your choice: ");
						c2 = scint.nextInt();
						switch(c2)
						{
							case 1: negative(); break;
							case 2: brightness(); break;
							case 3: filter(); break;
							case 4: mono(); break;
							case 5: flip(); break;
							case 6: rotate(); break;
							case 7: crop(); break;
							case 8: blur(); break;
							case 9: edge(); break;
							default: System.out.println("Wrong input!"); break;
						}
					}
					break;
				case 0:
					System.out.println("Thanks...");
					return;
				default:
					System.out.println("Wrong input!"); break;
			}
		}
	}
}
