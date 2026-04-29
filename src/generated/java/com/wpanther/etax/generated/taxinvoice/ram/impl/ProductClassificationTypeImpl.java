
package com.wpanther.etax.generated.taxinvoice.ram.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.wpanther.etax.generated.common.qdt.Max16CodeType;
import com.wpanther.etax.generated.common.qdt.impl.Max16CodeTypeImpl;
import com.wpanther.etax.generated.taxinvoice.ram.ProductClassificationType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProductClassificationType", propOrder = {
    "classCode",
    "className"
})
public class ProductClassificationTypeImpl
    implements Serializable, ProductClassificationType
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "ClassCode", type = Max16CodeTypeImpl.class)
    protected Max16CodeTypeImpl classCode;
    @XmlElement(name = "ClassName")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected List<String> className;

    public Max16CodeType getClassCode() {
        return classCode;
    }

    public void setClassCode(Max16CodeType value) {
        this.classCode = ((Max16CodeTypeImpl) value);
    }

    public List<String> getClassName() {
        if (className == null) {
            className = new ArrayList<String>();
        }
        return this.className;
    }

}
