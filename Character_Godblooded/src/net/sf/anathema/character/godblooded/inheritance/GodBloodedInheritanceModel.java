package net.sf.anathema.character.godblooded.inheritance;

import net.sf.anathema.character.generic.additionaltemplate.AbstractAdditionalModelAdapter;
import net.sf.anathema.character.generic.additionaltemplate.AdditionalModelType;
import net.sf.anathema.character.generic.additionaltemplate.IAdditionalModelBonusPointCalculator;
import net.sf.anathema.character.generic.framework.additionaltemplate.model.ICharacterModelContext;
import net.sf.anathema.lib.control.IChangeListener;

public class GodBloodedInheritanceModel extends AbstractAdditionalModelAdapter {
  private final ICharacterModelContext context;
  private final GodBloodedInheritanceTemplate template;

  @Override
  public AdditionalModelType getAdditionalModelType() {
    return AdditionalModelType.Miscellaneous;
  }

  public GodBloodedInheritanceModel(GodBloodedInheritanceTemplate template, ICharacterModelContext context) {
    this.context = context;
    this.template = template;
  }

  @Override
  public String getTemplateId() {
    return template.getId();
  }

  @Override
  public IAdditionalModelBonusPointCalculator getBonusPointCalculator() {
    return new InheritanceModelBonusPointCalculator(context);
  }

  @Override
  public void addChangeListener(IChangeListener listener) {
    //nothing to do
  }
}