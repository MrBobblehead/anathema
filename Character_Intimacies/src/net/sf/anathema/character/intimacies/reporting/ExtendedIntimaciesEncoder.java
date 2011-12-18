package net.sf.anathema.character.intimacies.reporting;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import net.sf.anathema.character.generic.framework.configuration.AnathemaCharacterPreferences;
import net.sf.anathema.character.generic.traits.types.VirtueType;
import net.sf.anathema.character.intimacies.IIntimaciesAdditionalModel;
import net.sf.anathema.character.intimacies.model.IIntimacy;
import net.sf.anathema.character.intimacies.presenter.IIntimaciesModel;
import net.sf.anathema.character.intimacies.template.IntimaciesTemplate;
import net.sf.anathema.character.library.trait.ITrait;
import net.sf.anathema.character.reporting.pdf.content.ReportContent;
import net.sf.anathema.character.reporting.pdf.rendering.elements.Bounds;
import net.sf.anathema.character.reporting.pdf.rendering.elements.Position;
import net.sf.anathema.character.reporting.pdf.rendering.general.PdfGraphics;
import net.sf.anathema.character.reporting.pdf.rendering.general.box.IBoxContentEncoder;
import net.sf.anathema.character.reporting.pdf.rendering.general.traits.PdfTraitEncoder;

import static net.sf.anathema.character.reporting.pdf.rendering.page.IVoidStateFormatConstants.LINE_HEIGHT;

public class ExtendedIntimaciesEncoder implements IBoxContentEncoder {
  // TODO: Give this and PdfBackgroundEncoder a common base class, which may be more broadly useful.

  private final PdfTraitEncoder traitEncoder;

  public ExtendedIntimaciesEncoder(BaseFont baseFont) {
    this.traitEncoder = PdfTraitEncoder.createSmallTraitEncoder(baseFont);
  }

  public String getHeaderKey(ReportContent reportContent) {
    return "Intimacies"; //$NON-NLS-1$
  }

  public void encode(PdfGraphics graphics, ReportContent reportContent, Bounds bounds) throws DocumentException {
    float yPosition = bounds.getMaxY() - LINE_HEIGHT;
    
    int maxValue = reportContent.getCharacter().getTraitCollection().getTrait(VirtueType.Conviction).getCurrentValue();
    boolean printZeroIntimacies = AnathemaCharacterPreferences.getDefaultPreferences().printZeroIntimacies();
    
    IIntimaciesAdditionalModel additionalModel = (IIntimaciesAdditionalModel) reportContent.getCharacter().getAdditionalModel(IntimaciesTemplate.ID);
    IIntimaciesModel model = additionalModel.getIntimaciesModel();
    for (IIntimacy intimacy : model.getEntries()) {
      ITrait intimacyTrait = intimacy.getTrait();
      if (yPosition < bounds.getMinY()) {
        return;
      }
      if (!printZeroIntimacies && intimacyTrait.getCurrentValue() == 0) {
        continue;
      }
      traitEncoder.encodeWithText(graphics.getDirectContent(), intimacy.getName(),
                                  new Position(bounds.x, yPosition), bounds.width,
                                  intimacyTrait.getCurrentValue(), maxValue);
      yPosition -= LINE_HEIGHT;
    }
    encodeEmptyLines(graphics.getDirectContent(), bounds, yPosition, maxValue);
  }

  private void encodeEmptyLines(PdfContentByte directContent, Bounds bounds, float yPosition, int maxValue) {
    while (yPosition > bounds.getMinY()) {
      Position position = new Position(bounds.x, yPosition);
      traitEncoder.encodeWithLine(directContent, position, bounds.width, 0, maxValue);
      yPosition -= LINE_HEIGHT;
    }
  }
  
  public boolean hasContent(ReportContent content) {
	  return true;
  }
}
