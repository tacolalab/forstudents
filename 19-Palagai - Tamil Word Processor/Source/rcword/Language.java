package rcword;

import java.util.*;
import javax.swing.*;

/**
 *	Used to set the GUI with the currently selected language.
 *
 *  	@ version 5.12.2002
 * 	@ author RCILTS-Tamil,MIT.
 */
class Language
{
	/** a reference to the main <code> Word </code> object */
	Word parentword;
	/** contains the total number of words in the menubar,menu and menuitems*/
	int no_words = 55;
	/** contains all Tamil words in this application.*/
	String tamil_words[] = new String[no_words];
	/** contains all English words in this application.*/
	String english_words[] = new String[no_words];
	/** array list contains Tamil words in this application.*/
	ArrayList al_t_words = new ArrayList();
	/** array list contains English words in this application.*/
	ArrayList al_e_words = new ArrayList();

	/**
	* Constructs an object of <code> Language </code> with a reference to the
	* main <code> Word </code> object.
	*
	* @param word a reference to the main <code> Word </code> object
	*/
    public Language(Word word)
    {
		parentword = word;
		// store all words in their respective array of string to access from that.
		initiateStrings();
		for(int i = 0 ; i<no_words; i++)
		{	// stores all words in their respective arraylist to access from that.
			al_t_words.add(tamil_words[i]);
			al_e_words.add(english_words[i]);
		}
	}

	/**
	* Initiates all Tamil and English of this application in to their respective
	* array of strings to access from that.
	*/
	public void initiateStrings()
	 {
			tamil_words[0] = "File";				english_words[0] = "���";
			tamil_words[1] ="�";				english_words[1] =  "Edit";
			tamil_words[2] ="���";				english_words[2] = "View";
			tamil_words[3] = "���";				english_words[3] = "Insert";
			tamil_words[4] ="ܬ��";				english_words[4] = "Format";
			tamil_words[5] = "������";			english_words[5] = "Tools";
			tamil_words[6] = "������";		english_words[6] = "Table";
			tamil_words[7] =  "����";				english_words[7] = "Window";
			tamil_words[8] =  "����";				english_words[8] = "Help";
			tamil_words[9] ="���";					english_words[9] ="New";
			tamil_words[10] ="��";					english_words[10] ="Open";
			tamil_words[11] ="���";					english_words[11] ="Save";
			tamil_words[12]="����� ������ ���";		english_words[12]="Save As";
			tamil_words[13]="���ܬ��";		english_words[13]="Page Setup";
			tamil_words[14]="�����";			english_words[14]="Properties";
			tamil_words[15]="��������";		english_words[15]="Email";
			tamil_words[16]="���";				english_words[16]="Print";
			tamil_words[17]="�������";			english_words[17]="Exit";
			tamil_words[18]="�袰";					english_words[18]="Cancel";
			tamil_words[19]="English";					english_words[19]="����";
			tamil_words[20]="����";					english_words[20]="Undo";
			tamil_words[21]="��� ���";		english_words[21]="Redo";
			tamil_words[22]="��좴";				english_words[22]="Cut";
			tamil_words[23]="����";					english_words[23]="Copy";
		    tamil_words[24]="�좴";					english_words[24]="Paste";
			tamil_words[25]="ܬ��� ����";		english_words[25]="SellectAll";
			tamil_words[26]="���";			english_words[26]="Find";
			tamil_words[27]="����";				english_words[27]="Replace";
			tamil_words[28]="�����좬�";		english_words[28]="Toolbar";
			tamil_words[29]="ܬ����좬�";	english_words[29]="Formatbar";
			tamil_words[30]="郎���좬�";		english_words[30]="Statusbar";
			tamil_words[31]="���";						english_words[31]="Picture";
			tamil_words[32]="郎������";			english_words[32]="Change Case";
			tamil_words[33]="�������";				english_words[33]="Bullets";
			tamil_words[34]="�����";				english_words[34]="Numbering";
			tamil_words[35]="���� 郎�";			english_words[35]="Tab Stop";
			tamil_words[36]="���...";					english_words[36]="Paragraph";
			tamil_words[37]="����� ���袬�";	english_words[37]="Word Count";
			tamil_words[38]="�����";		english_words[38]="Sort";
			tamil_words[39]="���";						english_words[39]="Draw";
			tamil_words[40]=" ";							english_words[40]=" ";
			tamil_words[41]="�꼰 ";					english_words[41]="Insert";
			tamil_words[42]=" ���";						english_words[42]="Delete";
			tamil_words[43]="��袰 �����";					english_words[43]="Column to the left";
			tamil_words[44]="��袰 �����";		english_words[44]="Column to the right";
			tamil_words[45]="郎�袰 ��������";			english_words[45]="Rows above";
			tamil_words[46]="郎�袰 �������";		english_words[46]="Rows bellow";
			tamil_words[47]="����";						english_words[47]="Collumn";
			tamil_words[48]="郎�";						english_words[48]="Row";

			tamil_words[49]="�������� �����";		english_words[49]="About Word";
			tamil_words[50]="�������� ����";	english_words[50]="Word Help";
			tamil_words[51]="��� ��";		english_words[51]="Spell Checker";
			tamil_words[52]="���";						english_words[52]="Clear";
			tamil_words[53] ="��";				english_words[53]="Font";
			tamil_words[54] ="�������";				english_words[54]="Ruler";

	}

	/**
	* Receives a word of one language and returns the equivalent word of other language.
	*
	* @param word word of one language for which the equivalent word of other language
	*		  is required.
	* @return String	equivalent word for the given word in other language.
	*/
	public String getequivaleant(String word)
	{
		String equ_word = new String();
		// gets the index of the given word in list of tamil words.
		int index = al_t_words.indexOf(word);
		if(index == -1)
		{	// if the given word is in the list of English words.
			try
			{	// gets the index of the given word in the list of English words.
				index = al_e_words.indexOf(word);
				if(index == -1)  //if not return as such.
					return word;
				// gets the word in the list of Tamil words at the same index.
				equ_word = al_t_words.get(index).toString();
			}
			catch(Exception ex)
			{
				System.out.println(ex+"\n---->ex at get equivaleant word : Language");
			}
		}
		else  // if the given word is in the list of Tamil words.
		{	// gets the word in the list of English words at the same index.
			equ_word = al_e_words.get(index).toString();
		}
		return equ_word;
	}

	/**
	* Updates the menu bar with the current language.
	*/
	public void updateMenubarNew()
	{
		int no_menus = parentword.menubar.getMenuCount();

		try
		{
			ResourceBundle resources = ResourceBundle.getBundle ("Lang", parentword.current_locale);

			for(int i = 0; i <1 /*no_menus*/ ; i++)
			{
				JMenu temp_menu = (JMenu)parentword.menubar.getComponentAtIndex(i) ;
				String menu_name = temp_menu.getText();
				//System.out.println(" in English : "+getequivaleant(menu_name));
				//System.out.println(" in English-- : "+resources.getString (menu_name));
				temp_menu.setText(getequivaleant(menu_name));
				int no_menu_items = temp_menu.getItemCount();
				//System.out.println("  "+ i+"     NO of items :"+no_menu_items);

				/*
				for( int menu_item_order = 0 ; menu_item_order <no_menu_items ; menu_item_order++)
				{
					//System.out.println("                     item no : "+menu_item_order);
					JMenuItem temp_menu_item = temp_menu.getItem(menu_item_order);
					if( temp_menu.getItem(menu_item_order) instanceof JMenu)
				    {
							JMenu inner_temp_menu = (JMenu)temp_menu.getItem(menu_item_order) ;
							String inner_menu_name = inner_temp_menu.getText();
							inner_temp_menu.setText(getequivaleant(inner_menu_name));
							int no_inner_menu_items = inner_temp_menu.getItemCount();
							//System.out.println(" 					yes it issss "+no_inner_menu_items);
					}
					else if( temp_menu.getItem(menu_item_order) instanceof JMenuItem)
					{
						String menu_item_name = temp_menu_item.getText();
						////System.out.println(" >    "+menu_item_order+"    :"+menu_item_name+" : "+getequivaleant(menu_item_name));
						temp_menu_item.setText(getequivaleant(menu_item_name));
					}
					else if( temp_menu.getItem(menu_item_order) instanceof JMenu)
					{
						//System.out.println(" 					yes it is");
					}
				}
				*/

			}
		}
		catch(Exception e)
		{
			System.out.println(e+"-----> exception at heree : language");
			//e.printStackTrace();
		}

	}

	/**
	* Updates the menu bar with the current language.
	*/
	public void updateMenubar()
		{
			int no_menus = parentword.menubar.getMenuCount();

			try
			{
				for(int i = 0; i < no_menus ; i++)
				{
					JMenu temp_menu = (JMenu)parentword.menubar.getComponentAtIndex(i) ;
					String menu_name = temp_menu.getText();
					//System.out.print("\n"+" Menu		"+i+" : ");
					temp_menu.setText(getequivaleant(menu_name));
					int no_menu_items = temp_menu.getItemCount();

					for( int menu_item_order = 0 ; menu_item_order <no_menu_items ; menu_item_order++)
					{
						//System.out.print("\n 	Menu Item	"+menu_item_order+"  : ");
						JMenuItem temp_menu_item = temp_menu.getItem(menu_item_order);
						if( temp_menu.getItem(menu_item_order) instanceof JMenu)
					    {
								JMenu inner_temp_menu = (JMenu)temp_menu.getItem(menu_item_order) ;
								String inner_menu_name = inner_temp_menu.getText();
								inner_temp_menu.setText(getequivaleant(inner_menu_name));
								int no_inner_menu_items = inner_temp_menu.getItemCount();
								for(int innermenu_item_order = 0; innermenu_item_order<no_inner_menu_items ; innermenu_item_order++ )
								{
									JMenuItem in_inner_temp_menu_item = inner_temp_menu.getItem(innermenu_item_order);
									if(in_inner_temp_menu_item !=null)
									{
										String temp_name = in_inner_temp_menu_item.getText();
										in_inner_temp_menu_item.setText(getequivaleant(temp_name));
									}
								}
						}
						else if( temp_menu.getItem(menu_item_order) instanceof JMenuItem)
						{
							String menu_item_name = temp_menu_item.getText();
							temp_menu_item.setText(getequivaleant(menu_item_name));
						}
					}

				}
			}
			catch(Exception e)
			{
				System.out.println(e+"-----> exception at heree : language");
				e.printStackTrace();
			}

	}  //

	/**
	* Updates the toolbars with the current language.
	* First gets the wordbundle of current language and sets the tool tip to each
	* component in the toolbars.
	*/
	public void updateToolbars()
	{
		JToolBar temp_toolbar = parentword.toolbar;
		( (JComponent)temp_toolbar.getComponentAtIndex(0) ).setToolTipText(parentword.wordBundle.getString("New"));
		( (JComponent)temp_toolbar.getComponentAtIndex(1) ).setToolTipText(parentword.wordBundle.getString("Open"));
		( (JComponent)temp_toolbar.getComponentAtIndex(2) ).setToolTipText(parentword.wordBundle.getString("Save"));
		( (JComponent)temp_toolbar.getComponentAtIndex(4) ).setToolTipText(parentword.wordBundle.getString("Cut"));
		( (JComponent)temp_toolbar.getComponentAtIndex(5) ).setToolTipText(parentword.wordBundle.getString("Copy"));
		( (JComponent)temp_toolbar.getComponentAtIndex(6) ).setToolTipText(parentword.wordBundle.getString("Paste"));
		( (JComponent)temp_toolbar.getComponentAtIndex(8) ).setToolTipText(parentword.wordBundle.getString("Undo"));
		( (JComponent)temp_toolbar.getComponentAtIndex(9) ).setToolTipText(parentword.wordBundle.getString("Redo"));
		( (JComponent)temp_toolbar.getComponentAtIndex(11) ).setToolTipText(parentword.wordBundle.getString("TextColor"));
//		( (JComponent)temp_toolbar.getComponentAtIndex(12) ).setToolTipText(parentword.wordBundle.getString("BackGroundColor"));

		JToolBar temp_formatbar = parentword.formatbar;
		parentword.fontnamecombobox.setToolTipText(parentword.wordBundle.getString("FontName"));
		parentword.fontsizecombobox.setToolTipText(parentword.wordBundle.getString("FontSize"));
		parentword.jtb_bold.setToolTipText(parentword.wordBundle.getString("Bold"));
		parentword.jtb_italic.setToolTipText(parentword.wordBundle.getString("Italic"));
		parentword.jtb_underline.setToolTipText(parentword.wordBundle.getString("Underline"));

		( (JComponent)temp_formatbar.getComponentAtIndex(5) ).setToolTipText(parentword.wordBundle.getString("AlignLeft"));
		( (JComponent)temp_formatbar.getComponentAtIndex(6) ).setToolTipText(parentword.wordBundle.getString("AlignCenter"));
		( (JComponent)temp_formatbar.getComponentAtIndex(7) ).setToolTipText(parentword.wordBundle.getString("AlignRight"));

		parentword.jtb_subscript.setToolTipText(parentword.wordBundle.getString("Subscript"));
		parentword.jtb_superscript.setToolTipText(parentword.wordBundle.getString("Superscript"));
		parentword.jtb_strikeThrough.setToolTipText(parentword.wordBundle.getString("StrikeThrough"));

	}

}

