
package un.unece.uncefact.codelist.standard.etda.thaimessagefunctioncode._2560;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ThaiMessageFunctionCodeContentType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;simpleType name="ThaiMessageFunctionCodeContentType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token"&gt;
 *     &lt;enumeration value="DBNG01"/&gt;
 *     &lt;enumeration value="DBNG02"/&gt;
 *     &lt;enumeration value="DBNG99"/&gt;
 *     &lt;enumeration value="DBNS01"/&gt;
 *     &lt;enumeration value="DBNS02"/&gt;
 *     &lt;enumeration value="DBNS99"/&gt;
 *     &lt;enumeration value="CDNG01"/&gt;
 *     &lt;enumeration value="CDNG02"/&gt;
 *     &lt;enumeration value="CDNG03"/&gt;
 *     &lt;enumeration value="CDNG04"/&gt;
 *     &lt;enumeration value="CDNG05"/&gt;
 *     &lt;enumeration value="CDNG99"/&gt;
 *     &lt;enumeration value="CDNS01"/&gt;
 *     &lt;enumeration value="CDNS02"/&gt;
 *     &lt;enumeration value="CDNS03"/&gt;
 *     &lt;enumeration value="CDNS04"/&gt;
 *     &lt;enumeration value="CDNS99"/&gt;
 *     &lt;enumeration value="TIVC01"/&gt;
 *     &lt;enumeration value="TIVC02"/&gt;
 *     &lt;enumeration value="TIVC99"/&gt;
 *     &lt;enumeration value="RCTC01"/&gt;
 *     &lt;enumeration value="RCTC02"/&gt;
 *     &lt;enumeration value="RCTC03"/&gt;
 *     &lt;enumeration value="RCTC04"/&gt;
 *     &lt;enumeration value="RCTC99"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ThaiMessageFunctionCodeContentType", namespace = "urn:un:unece:uncefact:codelist:standard:ETDA:ThaiMessageFunctionCode:2560")
@XmlEnum
public enum ThaiMessageFunctionCodeContentType {

    @XmlEnumValue("DBNG01")
    DBNG_01("DBNG01"),
    @XmlEnumValue("DBNG02")
    DBNG_02("DBNG02"),
    @XmlEnumValue("DBNG99")
    DBNG_99("DBNG99"),
    @XmlEnumValue("DBNS01")
    DBNS_01("DBNS01"),
    @XmlEnumValue("DBNS02")
    DBNS_02("DBNS02"),
    @XmlEnumValue("DBNS99")
    DBNS_99("DBNS99"),
    @XmlEnumValue("CDNG01")
    CDNG_01("CDNG01"),
    @XmlEnumValue("CDNG02")
    CDNG_02("CDNG02"),
    @XmlEnumValue("CDNG03")
    CDNG_03("CDNG03"),
    @XmlEnumValue("CDNG04")
    CDNG_04("CDNG04"),
    @XmlEnumValue("CDNG05")
    CDNG_05("CDNG05"),
    @XmlEnumValue("CDNG99")
    CDNG_99("CDNG99"),
    @XmlEnumValue("CDNS01")
    CDNS_01("CDNS01"),
    @XmlEnumValue("CDNS02")
    CDNS_02("CDNS02"),
    @XmlEnumValue("CDNS03")
    CDNS_03("CDNS03"),
    @XmlEnumValue("CDNS04")
    CDNS_04("CDNS04"),
    @XmlEnumValue("CDNS99")
    CDNS_99("CDNS99"),
    @XmlEnumValue("TIVC01")
    TIVC_01("TIVC01"),
    @XmlEnumValue("TIVC02")
    TIVC_02("TIVC02"),
    @XmlEnumValue("TIVC99")
    TIVC_99("TIVC99"),
    @XmlEnumValue("RCTC01")
    RCTC_01("RCTC01"),
    @XmlEnumValue("RCTC02")
    RCTC_02("RCTC02"),
    @XmlEnumValue("RCTC03")
    RCTC_03("RCTC03"),
    @XmlEnumValue("RCTC04")
    RCTC_04("RCTC04"),
    @XmlEnumValue("RCTC99")
    RCTC_99("RCTC99");
    private final String value;

    ThaiMessageFunctionCodeContentType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ThaiMessageFunctionCodeContentType fromValue(String v) {
        for (ThaiMessageFunctionCodeContentType c: ThaiMessageFunctionCodeContentType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
