package net.sf.anathema.framework.presenter.action.preferences;

import net.sf.anathema.lib.gui.action.SmartAction;
import net.sf.anathema.lib.gui.dialog.core.IDialogResult;
import net.sf.anathema.lib.gui.dialog.userdialog.UserDialog;
import net.sf.anathema.lib.resources.IResources;

import javax.swing.Action;
import javax.swing.JOptionPane;
import java.awt.Component;

public class AnathemaPreferencesAction extends SmartAction {

  private static final long serialVersionUID = -7583368464399556246L;

  public static Action createMenuAction(IResources resources, IPreferencesElement[] elements) {
    SmartAction action = new AnathemaPreferencesAction(resources, elements);
    action.setName(resources.getString("AnathemaCore.Tools.Preferences.Name") + "\u2026"); //$NON-NLS-1$ //$NON-NLS-2$
    return action;
  }

  private final IResources resources;
  private final IPreferencesElement[] elements;

  public AnathemaPreferencesAction(IResources resources, IPreferencesElement[] elements) {
    this.resources = resources;
    this.elements = elements;
  }

  @Override
  protected void execute(Component parentComponent) {
    AnathemaPreferencesPage page = new AnathemaPreferencesPage(resources, elements);
    UserDialog userDialog = new UserDialog(parentComponent, page);
    
    boolean confirmed = false;
    boolean dirty = false;
    while (!confirmed) {
      IDialogResult result = userDialog.show();
      confirmed = true;
      
      if (result.isCanceled()) {
        for (IPreferencesElement element : elements) {
          if (element.isDirty()) {
            element.reset();
          }
        }
      }
      else {
        dirty = false;
        for (IPreferencesElement element : elements) {
          if (element.isDirty()) {
            if (element.isValid()) {
              dirty = true;
            }
            else {
              confirmed = false;
              continue;
            }
          }
        }
      }
    }
    for (IPreferencesElement element : elements) {
      if (element.isDirty()) {
        element.savePreferences();
        dirty = true;
      }
    }
    if (dirty) {
      JOptionPane.showMessageDialog(parentComponent, resources.getString("AnathemaCore.Tools.Preferences.Restart")); //$NON-NLS-1$
    }
  }
}