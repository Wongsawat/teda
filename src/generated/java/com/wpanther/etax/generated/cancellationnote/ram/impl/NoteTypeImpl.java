
package com.wpanther.etax.generated.cancellationnote.ram.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.wpanther.etax.generated.cancellationnote.ram.NoteType;
import com.wpanther.etax.generated.common.qdt.Max500TextType;
import com.wpanther.etax.generated.common.qdt.impl.Max500TextTypeImpl;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NoteType", propOrder = {
    "subject",
    "content"
})
public class NoteTypeImpl
    implements Serializable, NoteType
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "Subject", type = Max500TextTypeImpl.class)
    protected Max500TextTypeImpl subject;
    @XmlElement(name = "Content", type = Max500TextTypeImpl.class)
    protected List<Max500TextType> content;

    public Max500TextType getSubject() {
        return subject;
    }

    public void setSubject(Max500TextType value) {
        this.subject = ((Max500TextTypeImpl) value);
    }

    public List<Max500TextType> getContent() {
        if (content == null) {
            content = new ArrayList<Max500TextType>();
        }
        return this.content;
    }

}
