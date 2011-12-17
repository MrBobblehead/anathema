package net.sf.anathema.character.reporting.sheet.page;

import net.sf.anathema.character.reporting.common.encoder.IPdfContentBoxEncoder;
import net.sf.anathema.character.reporting.sheet.SimpleEncodingRegistry;
import net.sf.anathema.character.reporting.common.boxes.PdfEssenceEncoder;
import net.sf.anathema.lib.resources.IResources;

public abstract class AbstractFirstEditionExaltPdfPartEncoder extends AbstractFirstEditionPartEncoder {

  private final int essenceMax;

  public AbstractFirstEditionExaltPdfPartEncoder(IResources resources, SimpleEncodingRegistry registry, int essenceMax) {
    super(resources, registry.getBaseFont(), registry.getSymbolBaseFont());
    this.essenceMax = essenceMax;
  }

  public IPdfContentBoxEncoder getEssenceEncoder() {
    return new PdfEssenceEncoder(getBaseFont(), getResources(), essenceMax);
  }

  public IPdfContentBoxEncoder getOverdriveEncoder() {
    return null;
  }

  protected int getEssenceMax() {
    return essenceMax;
  }

  public boolean hasSecondPage() {
    return true;
  }

  protected final int getFontSize() {
    return FONT_SIZE;
  }
}
