package org.springframework.data.xap.integration;

import com.gigaspaces.document.DocumentProperties;
import com.gigaspaces.document.SpaceDocument;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.xap.repository.ProductDocumentRepository;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public abstract class BaseSpaceDocumentRepositoryTest {

    @Autowired
    protected ProductDocumentRepository productDocumentRepository;

    private SpaceDocument jetLogo;

    private SpaceDocument helicopter;

    private SpaceDocument wheel;

    private String typeName = "Product";

    @Test
    public void testFindOne(){
        SpaceDocument spaceDocument = productDocumentRepository.findOne("av-9876");
        assertEquals(jetLogo, spaceDocument);
    }

    @Test
    public void testFindAll(){
        List<SpaceDocument> all = (List<SpaceDocument>)productDocumentRepository.findAll();
        assertTrue(all.contains(jetLogo));
        assertTrue(all.contains(helicopter));
    }

    @Test
    public void testDelete(){
        productDocumentRepository.delete(jetLogo);
        assertNull(productDocumentRepository.findOne("av-9876"));
    }

    @Test
    public void testDeleteById(){
        productDocumentRepository.delete("av-9876");
        assertNull(productDocumentRepository.findOne("av-9876"));
    }

    @Before
    public void setUp() {
        DocumentProperties properties = new DocumentProperties()
                .setProperty("CatalogNumber", "av-9876")
                .setProperty("Category", "Aviation")
                .setProperty("Name", "Jet Propelled Pogo Stick")
                .setProperty("Price", 19.99f)
                .setProperty("Tags", new String[]{"New", "Cool", "Pogo", "Jet"})
                .setProperty("Features", new DocumentProperties()
                        .setProperty("Manufacturer", "Acme")
                        .setProperty("RequiresAssembly", true)
                        .setProperty("NumberOfParts", 42))
                .setProperty("Reviews", new DocumentProperties[]{
                        new DocumentProperties()
                                .setProperty("Name", "Wile E. Coyote")
                                .setProperty("Rate", 1),
                        new DocumentProperties()
                                .setProperty("Name", "Road Runner")
                                .setProperty("Rate", 5)});
        jetLogo = new SpaceDocument(typeName, properties);

        DocumentProperties properties2 = new DocumentProperties()
                .setProperty("CatalogNumber", "av-8765")
                .setProperty("Category", "Aviation")
                .setProperty("Name", "Helicopter")
                .setProperty("Price", 19.99f)
                .setProperty("Tags", new String[]{"New", "Cool", "Pogo", "Jet"})
                .setProperty("Features", new DocumentProperties()
                        .setProperty("Manufacturer", "Acme")
                        .setProperty("RequiresAssembly", true)
                        .setProperty("NumberOfParts", 42))
                .setProperty("Reviews", new DocumentProperties[]{
                        new DocumentProperties()
                                .setProperty("Name", "Wile E. Coyote")
                                .setProperty("Rate", 1),
                        new DocumentProperties()
                                .setProperty("Name", "Road Runner")
                                .setProperty("Rate", 5)});
        helicopter = new SpaceDocument(typeName, properties2);

        DocumentProperties properties3 = new DocumentProperties()
                .setProperty("CatalogNumber", "av-7654")
                .setProperty("Category", "Aviation")
                .setProperty("Name", "Wheel")
                .setProperty("Price", 19.99f)
                .setProperty("Tags", new String[]{"New", "Cool", "Pogo", "Jet"})
                .setProperty("Features", new DocumentProperties()
                        .setProperty("Manufacturer", "Acme")
                        .setProperty("RequiresAssembly", true)
                        .setProperty("NumberOfParts", 42))
                .setProperty("Reviews", new DocumentProperties[]{
                        new DocumentProperties()
                                .setProperty("Name", "Wile E. Coyote")
                                .setProperty("Rate", 1),
                        new DocumentProperties()
                                .setProperty("Name", "Road Runner")
                                .setProperty("Rate", 5)});
        wheel = new SpaceDocument(typeName, properties3);

        productDocumentRepository.save(jetLogo);
        productDocumentRepository.save(helicopter);
        productDocumentRepository.save(wheel);
    }

    public void tearDown(){

    }
}
