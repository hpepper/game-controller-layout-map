import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

/**
 * Image handling:
 *  http://stackoverflow.com/questions/10929524/how-to-add-text-to-an-image-in-java
 *  
 */

/**
 * 
 */
public class XboxButtonMapping {

	static char m_cLeftJustified = 'L';
	static char m_cRightJustified = 'R';
	static int m_nPaperPixelHeight = 1654;
	static int m_nPaperPixelWidth = 1165;

	/**
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		String sControllerLayoutFilename = "controllerlayout.xml";
		String sInputFileName = "";
		String sOutputFileName = "";

		int nArgIndex = 0;
		while (nArgIndex < args.length) {
			if (args[nArgIndex].contentEquals("-f")) {
				// TODO N Verify that there is an argument?
				sInputFileName = args[nArgIndex + 1];
				System.out.println("Filename to open: " + sInputFileName);
				nArgIndex += 2;
			} else if (args[nArgIndex].contentEquals("-o")) {
				// TODO N Verify that there is an argument?
				sOutputFileName = args[nArgIndex + 1];
				System.out
						.println("Output Filename to use: " + sOutputFileName);
				nArgIndex += 2;
			} else {
				System.out.println("!!! Argument " + args[nArgIndex]
						+ " not supported.");
				nArgIndex++;
			}
		}

		if (sInputFileName.isEmpty()) {
			throw new IOException(
					"You must specify an input file with '-f <FILENAME>'");
		}

		dbLayerXmlGameButtons cdbLayerXmlGameButtons = new dbLayerXmlGameButtons();
		cdbLayerXmlGameButtons.connectToDatabase(sInputFileName);

		List<GameButton> lGameButtons = cdbLayerXmlGameButtons.GetGameButtons();
		String sGameTitle = cdbLayerXmlGameButtons.GetGameTitle();

		String sGameNotes = cdbLayerXmlGameButtons.GetNotes();

		dbLayerXmlControllerLayout cdbLayerXmlControllerLayout = new dbLayerXmlControllerLayout();

		cdbLayerXmlControllerLayout
				.connectToDatabase(sControllerLayoutFilename);
		List<ButtonLayout> lButtonLayoutList = cdbLayerXmlControllerLayout
				.GetGameButtons();

		// Maps the lButtonLayoutList to the button name and use that to
		// do a lookup from the game buttons.
		Map<String, Integer> hButtonLayout = new HashMap<String, Integer>();

		for (Integer nIndex = 0; nIndex < lButtonLayoutList.size(); nIndex++) {
			hButtonLayout.put(lButtonLayoutList.get(nIndex).m_sButtonName,
					nIndex);
		}

		int nTemplateXOffset = 0;
		int nTemplateYOffset = 100;

		// Create an empty image, with the size of an A5 paper.
		BufferedImage cOutputImage = new BufferedImage(m_nPaperPixelWidth,
				m_nPaperPixelHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics graphicsOutputGraphics = cOutputImage.getGraphics();

		// The controller template file name.
		String sControllerTemplateName = cdbLayerXmlControllerLayout
				.GetTemplateFileName();

		try {
			// Read the template image from the file.
			BufferedImage cControllerTemplateImage = ImageIO.read(new File(
					sControllerTemplateName));
			;

			// Calculate the X-axis offset for the Template image.
			nTemplateXOffset = (cOutputImage.getWidth() - cControllerTemplateImage
					.getWidth()) / 2;
			// insert the template image into the empty image, centered.
			graphicsOutputGraphics.drawImage(cControllerTemplateImage,
					nTemplateXOffset, nTemplateYOffset,
					cControllerTemplateImage.getWidth(),
					cControllerTemplateImage.getHeight(), null);

			AddGameTitleToOuputImage(graphicsOutputGraphics,
					cOutputImage.getWidth(), sGameTitle);

			for (int nIndex = 0; nIndex < lGameButtons.size(); nIndex++) {
				if (hButtonLayout
						.containsKey(lGameButtons.get(nIndex).m_sButtonName)) {
					Integer cLayoutId = hButtonLayout.get(lGameButtons
							.get(nIndex).m_sButtonName);
					ButtonLayout cButtonLayout = lButtonLayoutList
							.get(cLayoutId.intValue());

					AddButtonDescriptionToOuputImage(graphicsOutputGraphics,
							cOutputImage.getWidth(),
							lGameButtons.get(nIndex).m_sButtonAction,
							cButtonLayout.m_nJustificationPositionX,
							cButtonLayout.m_nJustificationPositionY,
							cButtonLayout.m_cJustification,
							cButtonLayout.m_nLineToX, cButtonLayout.m_nLineToY);
				}
			}
			// TODO V For the left and right joystick I want an Arrow down icon
			// to symbolise push, and four direction arrows to symbolise the
			// joystick movement, possibly eight directions?

			// System.out.println(sGameNotes);
			AddGameNotesToOutputImage(graphicsOutputGraphics,
					m_nPaperPixelWidth, m_nPaperPixelHeight, sGameNotes);

			// Give the output file name the same name as the input file, just
			// replace xml with png.
			if (sOutputFileName.isEmpty()) {
				sOutputFileName = ChangeSuffixToPng(sInputFileName);
			}

			File outputfile = new File(sOutputFileName);
			// Write the output image as PNG.
			ImageIO.write(cOutputImage, "png", outputfile);

			// Clean up the graphics resources.
			graphicsOutputGraphics.dispose();
			System.out.println("Map generation done.");
		} catch (IOException e) {
			e.printStackTrace();
		}

	} // end main.

	private static void AddGameNotesToOutputImage(
			Graphics graphicsOutputGraphics, int nPaperPixelWidth,
			int nPaperPixelHeight, String sGameNotes) {
		
		int nXoffset = 10;

		Font font = graphicsOutputGraphics.getFont();

		// Scale the font and set the scaled font as the current font.
		graphicsOutputGraphics.setFont(font.deriveFont(30f));

		// Get the font information on the, now scaled, font.
		font = graphicsOutputGraphics.getFont();

		int nTextLineOffset = (int) nPaperPixelHeight / 2 + 5;
		int nTextHeight = 30;
		String[] arLines = sGameNotes.split("\n");
		int nItemListEntryNumber=0;
		for (String sLine : arLines) {
			if (sLine.startsWith("---+ ")) {
				nTextLineOffset = SectionText(graphicsOutputGraphics, nXoffset,
						nTextLineOffset, sLine.substring(5));
			} else if (sLine.startsWith("---++ ")) {
					nTextLineOffset = SubSectionText(graphicsOutputGraphics, nXoffset,
							nTextLineOffset, sLine.substring(6));
			} else if (sLine.startsWith("# ")) {
				nItemListEntryNumber++;
				nTextLineOffset = NumberedListText(graphicsOutputGraphics, nXoffset,
						nTextLineOffset, sLine.substring(1), nItemListEntryNumber);
			} else {
				// Put the text on the image.
				graphicsOutputGraphics.drawString(sLine, nXoffset,
						nTextLineOffset);
				nTextLineOffset += nTextHeight;
				nItemListEntryNumber = 0; // reset item number since we are not in a list anymore.
			}
		}
	} // end AddGameNotesToOutputImage.
	
	private static int NumberedListText(Graphics graphicsOutputGraphics,
			int nXoffset, int nTextLineOffset, String substring, int nItemListEntryNumber) {

		graphicsOutputGraphics.drawString( Integer.toString(nItemListEntryNumber) + " " + substring, nXoffset,nTextLineOffset);
		return (nTextLineOffset + 30);
	}


	private static int SectionText(Graphics graphicsOutputGraphics,
			int nXoffset, int nTextLineOffset, String substring) {

		Color tmpColor = graphicsOutputGraphics.getColor();
		// Set the font color to black.
		graphicsOutputGraphics.setColor(Color.blue);
		graphicsOutputGraphics.drawString(substring, nXoffset, nTextLineOffset);
		graphicsOutputGraphics.setColor(tmpColor);
		return (nTextLineOffset + 30);
	}

	private static int SubSectionText(Graphics graphicsOutputGraphics,
			int nXoffset, int nTextLineOffset, String substring) {

		Color tmpColor = graphicsOutputGraphics.getColor();
		// Set the font color to black.
		graphicsOutputGraphics.setColor(Color.green);
		graphicsOutputGraphics.drawString(substring, nXoffset, nTextLineOffset);
		graphicsOutputGraphics.setColor(tmpColor);
		return (nTextLineOffset + 30);
	}

	// replace the suffix with .png. The suffix is the last '.'(dot) an
	// everything after.
	private static String ChangeSuffixToPng(String sInputName) {
		String sOutputName = sInputName + ".png";
		int nDotPosition = sInputName.lastIndexOf('.');
		if (nDotPosition > 0) {
			sOutputName = sInputName.substring(0, nDotPosition) + ".png";
		}
		return (sOutputName);
	}

	private static void AddGameTitleToOuputImage(
			Graphics graphicsOutputGraphics, int nImageWidth, String sGameTitle) {
		// Set the font color to black.
		graphicsOutputGraphics.setColor(Color.black);

		// Get the current font.
		Font font = graphicsOutputGraphics.getFont();

		// Scale the font and set the scaled font as the current font.
		graphicsOutputGraphics.setFont(font.deriveFont(30f));

		// Get the font information on the, now scaled, font.
		font = graphicsOutputGraphics.getFont();

		// No idea what this does, just found it on the internet.
		FontRenderContext frcFontRenderContext = new FontRenderContext(null,
				true, true);

		TextLayout layout = new TextLayout(sGameTitle, font,
				frcFontRenderContext);
		// Get the rectangle of the text box.
		Rectangle cTextRectangle = layout.getPixelBounds(frcFontRenderContext,
				0, 0);
		// Calculate the starting point for the text when the text is to be
		// centered.
		int nTitleXOffset = (nImageWidth - cTextRectangle.width) / 2;

		// System.out.println("Rectangle width: " + cTextRectangle.width);

		// Put the text on the image.
		graphicsOutputGraphics.drawString(sGameTitle, nTitleXOffset,
				cTextRectangle.height + 5);
	}

	// TODO N allow optional Icons that can go to the left or the right of the
	// text and the Icon must be have the center line going through the text
	// center line.
	private static void AddButtonDescriptionToOuputImage(
			Graphics graphicsOutputGraphics, int nImageWidth,
			String sGameTitle, int nXJustificationPoint,
			int nYJustificationPoint, char cTextJustification, int nLineToX,
			int nLineToY) {

		// TODO N Make this nicer, like iffing the whole function.
		if ((sGameTitle != null) && (!sGameTitle.isEmpty())) {
			// Set the font color to black.
			graphicsOutputGraphics.setColor(Color.black);

			// Get the current font.
			Font font = graphicsOutputGraphics.getFont();

			// Scale the font and set the scaled font as the current font.
			graphicsOutputGraphics.setFont(font.deriveFont(30f));

			// Get the font information on the, now scaled, font.
			font = graphicsOutputGraphics.getFont();

			// No idea what this does, just found it on the internet.
			FontRenderContext frcFontRenderContext = new FontRenderContext(
					null, true, true);

			TextLayout layout = new TextLayout(sGameTitle, font,
					frcFontRenderContext);
			// Get the rectangle of the text box.
			Rectangle cTextRectangle = layout.getPixelBounds(
					frcFontRenderContext, 0, 0);
			// Calculate the starting point for the text when the text is to be
			// centered.

			int nTitleXOffset = nXJustificationPoint;
			if (cTextJustification == 'R') {
				nTitleXOffset = nXJustificationPoint - cTextRectangle.width;
			}
			// Put the text on the image.
			graphicsOutputGraphics.drawString(sGameTitle, nTitleXOffset,
					nYJustificationPoint + cTextRectangle.height);

			if ((nLineToX > 0) && (nLineToY > 0)) {
				int nTextToLineSepparation = 3;
				int nLineStartY = nYJustificationPoint - nTextToLineSepparation;
				if (nYJustificationPoint < nLineToY) {
					nLineStartY = nYJustificationPoint + cTextRectangle.height
							+ nTextToLineSepparation;
				}
				Graphics2D cGraphicsTwoDimenstional = (Graphics2D) graphicsOutputGraphics;
				BasicStroke cBasicStroke = new BasicStroke(2);
				cGraphicsTwoDimenstional.setStroke(cBasicStroke);
				cGraphicsTwoDimenstional.drawLine(nXJustificationPoint,
						nLineStartY, nLineToX, nLineToY);
			}
		}
	}
}
