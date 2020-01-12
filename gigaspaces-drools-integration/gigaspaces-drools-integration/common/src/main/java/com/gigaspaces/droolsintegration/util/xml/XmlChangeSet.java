package com.gigaspaces.droolsintegration.util.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("change-set")
public class XmlChangeSet {

    private XmlAdd add;
    private XmlRemove remove;

    public XmlAdd getAdd() {
        return add;
    }

    public void setAdd(XmlAdd add) {
        this.add = add;
    }

	public XmlRemove getRemove() {
		return remove;
	}

	public void setRemove(XmlRemove remove) {
		this.remove = remove;
	}

}