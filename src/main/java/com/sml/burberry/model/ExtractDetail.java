package com.sml.burberry.model;

import org.w3c.dom.Node;

import java.util.ArrayList;

/**
 * Created by michaelgoode on 10/08/2017.
 */
public class ExtractDetail {
    String filename;
    String field;
    String value;
    ArrayList<Node> nodes;

    public ExtractDetail(String filename, String field, String value, ArrayList<Node> nodes) {
        this.filename = filename;
        this.field = field;
        this.value = value;
        this.nodes = nodes;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void addNode( Node node ) {
        nodes.add(node);
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<Node> nodes) {
        this.nodes = nodes;
    }
}


