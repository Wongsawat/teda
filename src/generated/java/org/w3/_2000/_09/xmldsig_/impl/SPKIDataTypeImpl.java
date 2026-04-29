
package org.w3._2000._09.xmldsig_.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAnyElement;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlType;
import org.w3._2000._09.xmldsig_.SPKIDataType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SPKIDataType", propOrder = {
    "spkiSexpAndAny"
})
public class SPKIDataTypeImpl
    implements Serializable, SPKIDataType
{

    private final static long serialVersionUID = 1L;
    @XmlElementRef(name = "SPKISexp", namespace = "http://www.w3.org/2000/09/xmldsig#", type = JAXBElement.class)
    @XmlAnyElement(lax = true)
    protected List<Object> spkiSexpAndAny;

    public List<Object> getSPKISexpAndAny() {
        if (spkiSexpAndAny == null) {
            spkiSexpAndAny = new ArrayList<Object>();
        }
        return this.spkiSexpAndAny;
    }

}
