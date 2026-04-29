
package org.w3._2000._09.xmldsig_.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import org.w3._2000._09.xmldsig_.TransformType;
import org.w3._2000._09.xmldsig_.TransformsType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransformsType", propOrder = {
    "transform"
})
public class TransformsTypeImpl
    implements Serializable, TransformsType
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "Transform", required = true, type = TransformTypeImpl.class)
    protected List<TransformType> transform;

    public List<TransformType> getTransform() {
        if (transform == null) {
            transform = new ArrayList<TransformType>();
        }
        return this.transform;
    }

}
