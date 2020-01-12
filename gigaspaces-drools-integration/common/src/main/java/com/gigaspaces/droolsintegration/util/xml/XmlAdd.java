package com.gigaspaces.droolsintegration.util.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

@XStreamAlias("add")
public class XmlAdd {

    @XStreamImplicit
    private List<XmlResource> resources;

    public List<XmlResource> getResources() {
        return resources;
    }

    public void setResources(List<XmlResource> resources) {
        this.resources = resources;
    }

}