/*
 * $Id: ViewMessages.java 471754 2006-11-06 14:55:09Z husted $
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.glaf.core.res;

import java.io.Serializable;
 
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * A class that encapsulates messages. Messages can be either global or they are
 * specific to a particular bean property.
 * </p>
 * 
 * <p>
 * Each individual message is described by an <code>ViewMessage</code> object,
 * which contains a message key (to be looked up in an appropriate message
 * resources database), and up to four placeholder arguments used for parametric
 * substitution in the resulting message.
 * </p>
 * 
 * <p>
 * <strong>IMPLEMENTATION NOTE</strong> - It is assumed that these objects are
 * created and manipulated only within the context of a single thread.
 * Therefore, no synchronization is required for access to internal collections.
 * </p>
 * 
 * @version $Rev: 471754 $ $Date: 2005-08-26 21:58:39 -0400 (Fri, 26 Aug 2005) $
 * @since Struts 1.1
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ViewMessages implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * <p>
	 * Compares ViewMessageItem objects.
	 * </p>
	 */
	private static final Comparator ACTION_ITEM_COMPARATOR = new Comparator() {
		public int compare(Object o1, Object o2) {
			return ((ViewMessageItem) o1).getOrder()
					- ((ViewMessageItem) o2).getOrder();
		}
	};

	// ----------------------------------------------------- Manifest Constants

	/**
	 * <p>
	 * The "property name" marker to use for global messages, as opposed to
	 * those related to a specific property.
	 * </p>
	 */
	public static final String GLOBAL_MESSAGE = "com.glaf.core.res.GLOBAL_MESSAGE";

	// ----------------------------------------------------- Instance Variables

	/**
	 * <p>
	 * Have the messages been retrieved from this object?
	 * </p>
	 * 
	 * <p>
	 * The controller uses this property to determine if session-scoped messages
	 * can be removed.
	 * </p>
	 * 
	 * @since Struts 1.2
	 */
	protected boolean accessed = false;

	/**
	 * <p>
	 * The accumulated set of <code>ViewMessage</code> objects (represented as
	 * an ArrayList) for each property, keyed by property name.
	 * </p>
	 */
	protected Map<String, ViewMessageItem> messages = new java.util.concurrent.ConcurrentHashMap<String, ViewMessageItem>();

	/**
	 * <p>
	 * The current number of the property/key being added. This is used to
	 * maintain the order messages are added.
	 * </p>
	 */
	protected int iCount = 0;

	// --------------------------------------------------------- Public Methods

	/**
	 * <p>
	 * Create an empty <code>ViewMessages</code> object.
	 * </p>
	 */
	public ViewMessages() {
		super();
	}

	/**
	 * <p>
	 * Create an <code>ViewMessages</code> object initialized with the given
	 * messages.
	 * </p>
	 * 
	 * @param messages
	 *            The messages to be initially added to this object. This
	 *            parameter can be <code>null</code>.
	 * @since Struts 1.1
	 */
	public ViewMessages(ViewMessages messages) {
		super();
		this.add(messages);
	}

	/**
	 * <p>
	 * Add a message to the set of messages for the specified property. An order
	 * of the property/key is maintained based on the initial addition of the
	 * property/key.
	 * </p>
	 * 
	 * @param property
	 *            Property name (or ViewMessages.GLOBAL_MESSAGE)
	 * @param message
	 *            The message to be added
	 */
	public void add(String property, ViewMessage message) {
		ViewMessageItem item = (ViewMessageItem) messages.get(property);
		List<Object> list;

		if (item == null) {
			list = new java.util.concurrent.CopyOnWriteArrayList<Object>();
			item = new ViewMessageItem(list, iCount++, property);

			messages.put(property, item);
		} else {
			list = item.getList();
		}

		list.add(message);
	}

	/**
	 * <p>
	 * Adds the messages from the given <code>ViewMessages</code> object to this
	 * set of messages. The messages are added in the order they are returned
	 * from the <code>properties</code> method. If a message's property is
	 * already in the current <code>ViewMessages</code> object, it is added to
	 * the end of the list for that property. If a message's property is not in
	 * the current list it is added to the end of the properties.
	 * </p>
	 * 
	 * @param actionMessages
	 *            The <code>ViewMessages</code> object to be added. This
	 *            parameter can be <code>null</code>.
	 * @since Struts 1.1
	 */
	public void add(ViewMessages viewMessages) {
		if (viewMessages == null) {
			return;
		}

		// loop over properties
		Iterator<?> props = viewMessages.properties();

		while (props.hasNext()) {
			String property = (String) props.next();

			// loop over messages for each property
			Iterator<?> msgs = viewMessages.get(property);

			while (msgs.hasNext()) {
				ViewMessage msg = (ViewMessage) msgs.next();

				this.add(property, msg);
			}
		}
	}

	/**
	 * <p>
	 * Clear all messages recorded by this object.
	 * </p>
	 */
	public void clear() {
		messages.clear();
	}

	/**
	 * <p>
	 * Return <code>true</code> if there are no messages recorded in this
	 * collection, or <code>false</code> otherwise.
	 * </p>
	 * 
	 * @return <code>true</code> if there are no messages recorded in this
	 *         collection; <code>false</code> otherwise.
	 * @since Struts 1.1
	 */
	public boolean isEmpty() {
		return (messages.isEmpty());
	}

	/**
	 * <p>
	 * Return the set of all recorded messages, without distinction by which
	 * property the messages are associated with. If there are no messages
	 * recorded, an empty enumeration is returned.
	 * </p>
	 * 
	 * @return An iterator over the messages for all properties.
	 */

	public Iterator get() {
		this.accessed = true;

		if (messages.isEmpty()) {
			return Collections.EMPTY_LIST.iterator();
		}

		List results = new java.util.ArrayList();
		List actionItems = new java.util.ArrayList();

		for (Iterator i = messages.values().iterator(); i.hasNext();) {
			actionItems.add(i.next());
		}

		// Sort ViewMessageItems based on the initial order the
		// property/key was added to ViewMessages.
		Collections.sort(actionItems, ACTION_ITEM_COMPARATOR);

		for (Iterator i = actionItems.iterator(); i.hasNext();) {
			ViewMessageItem ami = (ViewMessageItem) i.next();

			for (Iterator msgsIter = ami.getList().iterator(); msgsIter
					.hasNext();) {
				results.add(msgsIter.next());
			}
		}

		return results.iterator();
	}

	/**
	 * <p>
	 * Return the set of messages related to a specific property. If there are
	 * no such messages, an empty enumeration is returned.
	 * </p>
	 * 
	 * @param property
	 *            Property name (or ViewMessages.GLOBAL_MESSAGE)
	 * @return An iterator over the messages for the specified property.
	 */
	public Iterator get(String property) {
		this.accessed = true;

		ViewMessageItem item = (ViewMessageItem) messages.get(property);

		if (item == null) {
			return (Collections.EMPTY_LIST.iterator());
		} else {
			return (item.getList().iterator());
		}
	}

	/**
	 * <p>
	 * Returns <code>true</code> if the <code>get()</code> or
	 * <code>get(String)</code> methods are called.
	 * </p>
	 * 
	 * @return <code>true</code> if the messages have been accessed one or more
	 *         times.
	 * @since Struts 1.2
	 */
	public boolean isAccessed() {
		return this.accessed;
	}

	/**
	 * <p>
	 * Return the set of property names for which at least one message has been
	 * recorded. If there are no messages, an empty <code>Iterator</code> is
	 * returned. If you have recorded global messages, the <code>String</code>
	 * value of <code>ViewMessages.GLOBAL_MESSAGE</code> will be one of the
	 * returned property names.
	 * </p>
	 * 
	 * @return An iterator over the property names for which messages exist.
	 */
	public Iterator properties() {
		if (messages.isEmpty()) {
			return Collections.EMPTY_LIST.iterator();
		}

		List results = new java.util.ArrayList();
		List actionItems = new java.util.ArrayList();

		for (Iterator i = messages.values().iterator(); i.hasNext();) {
			actionItems.add(i.next());
		}

		// Sort ViewMessageItems based on the initial order the
		// property/key was added to ViewMessages.
		Collections.sort(actionItems, ACTION_ITEM_COMPARATOR);

		for (Iterator i = actionItems.iterator(); i.hasNext();) {
			ViewMessageItem ami = (ViewMessageItem) i.next();

			results.add(ami.getProperty());
		}

		return results.iterator();
	}

	/**
	 * <p>
	 * Return the number of messages recorded for all properties (including
	 * global messages). <strong>NOTE</strong> - it is more efficient to call
	 * <code>isEmpty</code> if all you care about is whether or not there are
	 * any messages at all.
	 * </p>
	 * 
	 * @return The number of messages associated with all properties.
	 */
	public int size() {
		int total = 0;

		for (Iterator i = messages.values().iterator(); i.hasNext();) {
			ViewMessageItem ami = (ViewMessageItem) i.next();

			total += ami.getList().size();
		}

		return (total);
	}

	/**
	 * <p>
	 * Return the number of messages associated with the specified property.
	 * </p>
	 * 
	 * @param property
	 *            Property name (or ViewMessages.GLOBAL_MESSAGE)
	 * @return The number of messages associated with the property.
	 */
	public int size(String property) {
		ViewMessageItem item = (ViewMessageItem) messages.get(property);

		return (item == null) ? 0 : item.getList().size();
	}

	/**
	 * <p>
	 * Returns a String representation of this ViewMessages' property
	 * name=message list mapping.
	 * </p>
	 * 
	 * @return String representation of the messages
	 * @see Object#toString()
	 */
	public String toString() {
		return this.messages.toString();
	}

	/**
	 * <p>
	 * This class is used to store a set of messages associated with a
	 * property/key and the position it was initially added to list.
	 * </p>
	 */
	protected class ViewMessageItem implements Serializable {

		private static final long serialVersionUID = 1L;

		/**
		 * <p>
		 * The list of <code>ViewMessage</code>s.
		 * </p>
		 */
		protected List list = null;

		/**
		 * <p>
		 * The position in the list of messages.
		 * </p>
		 */
		protected int iOrder = 0;

		/**
		 * <p>
		 * The property associated with <code>ViewMessage</code>.
		 * </p>
		 */
		protected String property = null;

		/**
		 * <p>
		 * Construct an instance of this class.
		 * </p>
		 * 
		 * @param list
		 *            The list of ViewMessages.
		 * @param iOrder
		 *            The position in the list of messages.
		 * @param property
		 *            The property associated with ViewMessage.
		 */
		public ViewMessageItem(List list, int iOrder, String property) {
			this.list = list;
			this.iOrder = iOrder;
			this.property = property;
		}

		/**
		 * <p>
		 * Retrieve the list of messages associated with this item.
		 * </p>
		 * 
		 * @return The list of messages associated with this item.
		 */
		public List getList() {
			return list;
		}

		/**
		 * <p>
		 * Set the list of messages associated with this item.
		 * </p>
		 * 
		 * @param list
		 *            The list of messages associated with this item.
		 */
		public void setList(List list) {
			this.list = list;
		}

		/**
		 * <p>
		 * Retrieve the position in the message list.
		 * </p>
		 * 
		 * @return The position in the message list.
		 */
		public int getOrder() {
			return iOrder;
		}

		/**
		 * <p>
		 * Set the position in the message list.
		 * </p>
		 * 
		 * @param iOrder
		 *            The position in the message list.
		 */
		public void setOrder(int iOrder) {
			this.iOrder = iOrder;
		}

		/**
		 * <p>
		 * Retrieve the property associated with this item.
		 * </p>
		 * 
		 * @return The property associated with this item.
		 */
		public String getProperty() {
			return property;
		}

		/**
		 * <p>
		 * Set the property associated with this item.
		 * </p>
		 * 
		 * @param property
		 *            The property associated with this item.
		 */
		public void setProperty(String property) {
			this.property = property;
		}

		/**
		 * <p>
		 * Construct a string representation of this object.
		 * </p>
		 * 
		 * @return A string representation of this object.
		 */
		public String toString() {
			return this.list.toString();
		}
	}
}
