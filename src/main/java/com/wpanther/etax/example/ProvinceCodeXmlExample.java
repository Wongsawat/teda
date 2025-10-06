package com.wpanther.etax.example;

import com.wpanther.etax.entity.ThaiProvinceCode;
import com.wpanther.etax.repository.ThaiProvinceCodeRepository;
import com.wpanther.etax.xml.province.ISOCountrySubdivisionCodeType;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.StringReader;
import java.io.StringWriter;

/**
 * Example demonstrating database-backed JAXB with ThaiProvinceCode
 *
 * This example shows:
 * 1. How to marshal ThaiProvinceCode entities to XML
 * 2. How to unmarshal XML back to ThaiProvinceCode entities
 * 3. How namespace prefixes are preserved exactly as per ETDA schema
 * 4. How database lookup happens transparently during unmarshalling
 */
@Component
public class ProvinceCodeXmlExample implements CommandLineRunner {

    @Autowired
    private ThaiProvinceCodeRepository repository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n========================================");
        System.out.println("Thai Province Code XML Example");
        System.out.println("========================================\n");

        // Example 1: Marshal to XML
        marshalExample();

        // Example 2: Unmarshal from XML
        unmarshalExample();

        // Example 3: Round-trip test
        roundTripExample();

        // Example 4: Query by region
        queryByRegion();
    }

    /**
     * Example 1: Marshal ThaiProvinceCode entity to XML
     */
    private void marshalExample() throws JAXBException {
        System.out.println("Example 1: Marshal to XML");
        System.out.println("--------------------------");

        // Fetch Bangkok from database
        ThaiProvinceCode bangkok = repository.findByCodeAndActive("10")
                .orElse(new ThaiProvinceCode("10", "กรุงเทพมหานคร", "Bangkok"));

        // Create JAXB type
        ISOCountrySubdivisionCodeType provinceType = ISOCountrySubdivisionCodeType.of(bangkok);

        // Marshal to XML
        String xml = marshal(provinceType);
        System.out.println("Marshalled XML:");
        System.out.println(xml);
        System.out.println();
    }

    /**
     * Example 2: Unmarshal XML to ThaiProvinceCode entity
     */
    private void unmarshalExample() throws JAXBException {
        System.out.println("Example 2: Unmarshal from XML");
        System.out.println("------------------------------");

        // Sample XML with Chiang Mai province code
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<ISOCountrySubdivisionCode xmlns=\"urn:un:unece:uncefact:identifierlist:standard:ISO:ISOTwo-letterCountryCode:SecondEdition2006\">50</ISOCountrySubdivisionCode>";

        // Unmarshal from XML
        ISOCountrySubdivisionCodeType provinceType = unmarshal(xml);

        System.out.println("Unmarshalled province:");
        System.out.println("  Code: " + provinceType.getCode());
        System.out.println("  Thai Name: " + provinceType.getNameTh());
        System.out.println("  English Name: " + provinceType.getNameEn());
        System.out.println("  Region: " + provinceType.getRegion());
        System.out.println("  Active: " + provinceType.isActive());
        System.out.println();
    }

    /**
     * Example 3: Round-trip test (Marshal -> Unmarshal)
     */
    private void roundTripExample() throws JAXBException {
        System.out.println("Example 3: Round-trip Test");
        System.out.println("--------------------------");

        // Original entity - Phuket
        ThaiProvinceCode original = repository.findByCodeAndActive("83")
                .orElse(new ThaiProvinceCode("83", "ภูเก็ต", "Phuket"));

        System.out.println("Original: " + original);

        // Marshal to XML
        ISOCountrySubdivisionCodeType provinceType1 = ISOCountrySubdivisionCodeType.of(original);
        String xml = marshal(provinceType1);
        System.out.println("\nXML:");
        System.out.println(xml);

        // Unmarshal back from XML
        ISOCountrySubdivisionCodeType provinceType2 = unmarshal(xml);
        ThaiProvinceCode restored = provinceType2.getValue();

        System.out.println("\nRestored: " + restored);
        System.out.println("\nRound-trip successful: " + original.getCode().equals(restored.getCode()));
        System.out.println();
    }

    /**
     * Example 4: Query provinces by region
     */
    private void queryByRegion() {
        System.out.println("Example 4: Query by Region");
        System.out.println("--------------------------");

        String[] regions = {"North", "Northeast", "Central", "South", "East", "West"};

        for (String region : regions) {
            java.util.List<ThaiProvinceCode> provinces = repository.findByRegion(region);
            System.out.println(region + " Region: " + provinces.size() + " provinces");
            if (!provinces.isEmpty()) {
                System.out.println("  First: " + provinces.get(0).getNameTh() + " (" + provinces.get(0).getNameEn() + ")");
            }
        }
        System.out.println();
    }

    /**
     * Helper: Marshal object to XML string
     */
    private String marshal(ISOCountrySubdivisionCodeType provinceType) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(ISOCountrySubdivisionCodeType.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

        StringWriter writer = new StringWriter();
        marshaller.marshal(provinceType, writer);
        return writer.toString();
    }

    /**
     * Helper: Unmarshal XML string to object
     */
    private ISOCountrySubdivisionCodeType unmarshal(String xml) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(ISOCountrySubdivisionCodeType.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        StringReader reader = new StringReader(xml);
        return (ISOCountrySubdivisionCodeType) unmarshaller.unmarshal(reader);
    }
}
