package net.sf.anathema.character.reporting.pdf.rendering.boxes;

import net.sf.anathema.character.reporting.pdf.content.BasicContent;
import net.sf.anathema.character.reporting.pdf.rendering.general.box.ContentEncoder;
import net.sf.anathema.lib.resources.IResources;
import net.sf.anathema.lib.util.Identified;

public interface EncoderFactory extends Identified {

  ContentEncoder create(IResources resources, BasicContent content);

  boolean supports(BasicContent content);

  float getPreferredHeight(EncodingMetrics metrics, float width);
}
