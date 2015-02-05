/** DataListViewModel.java.

	Purpose:
		
	Description:
		
	History:
		12:07:27 PM Jan 22, 2015, Created by jumperchen

Copyright (C) 2015 Potix Corporation. All Rights Reserved.
*/
package org.zkoss.blog.example.zuti;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.annotation.SmartNotifyChange;
import org.zkoss.zk.ui.event.InputEvent;

/**
 * @author jumperchen
 *
 */
public class DataListViewModel {
	private List<Item> dataList;
	private List<Item> dataAllList;
	private List<Author> allAuthors;
	private String mode = "grid";
	
	private static String[] items_48 = new String[] { "AjaxPushAnimation", "DataAndReport", "EnterpriseAndIntegration",
		"EventsAndScripts", "FormsAndInputs", "GridsAndTrees", "LayoutAndWindows", "Miscellaneous2",
		"Miscellaneous", "MultimediaAndUtilities", "ToolbarsAndMenus", "LayoutAndWindows"};
	private static String[] items_128 = new String[] { "Briefcase", "FolderABlue", "Globe",
		"MailboxFlag", "ReadingGlass", "Spyglass"};
	private static String[] authors_list = new String[] { "Alien", "Astronauta", "Bombero",
		"Comisario", "Dreds", "Hiphopper", "Mimo", "Mounstruo"};
	
	@Init
	public void init() {
		Random random = new Random();
		allAuthors = new LinkedList<Author>();
		for (String name : authors_list) {
			allAuthors.add(new Author(name));
		}
		dataAllList = new LinkedList<Item>();
		for (String name : items_128) {
			Item item = new Item();
			item.setName(name);
			item.setAuthor(allAuthors.get(random.nextInt(allAuthors.size())));
			dataAllList.add(item);
		}
		dataList = new LinkedList<Item>();
		dataList.addAll(dataAllList);
	}
	
	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public List<Item> getDataList() {
		return dataList;
	}
	public void setDataList(List<Item> dataList) {
		this.dataList = dataList;
	}
	public List<Author> getAuthors() {
		LinkedHashSet<Author> set = new LinkedHashSet<Author>();
		for (Item item : dataList) {
			set.add(item.author);
		}
		List<Author> list =  new ArrayList<Author>(set);
		Collections.sort(list, new Comparator<Author>() {
			public int compare(Author o1, Author o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		return list;
	}
	public void setAuthors(List<Author> authors) {
		this.allAuthors = authors;
	}
	
	@Command("changeMode")
	@SmartNotifyChange("mode")
	public void doChangeMode(@BindingParam("mode") String mode) {
		this.mode = mode;
	}

	@Command("filter")
	@NotifyChange({"dataList", "authors"})
	public void doFilterItem(@BindingParam("query") String query) {
		dataList.clear();
		if (query.trim().isEmpty()) {
			dataList.addAll(dataAllList);
		} else {
			for (Item item : dataAllList) {
				if (item.getName().toLowerCase().contains(query.toLowerCase()))
					dataList.add(item);
			}
		}
	}
	public static class Item {
		private String name;
		private Author author;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Author getAuthor() {
			return author;
		}
		public void setAuthor(Author author) {
			this.author = author;
		}
		
	}
	public static class Author {
		private String name;
		public Author(String name) {
			setName(name);
		}
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
}
